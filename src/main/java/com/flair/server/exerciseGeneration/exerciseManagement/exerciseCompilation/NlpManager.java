package com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.Pair;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.util.CoreMap;
import simplenlg.features.Feature;
import simplenlg.features.Tense;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.WordElement;
import simplenlg.phrasespec.SPhraseSpec;

public class NlpManager {

    public NlpManager(CoreNlpParser parser, SimpleNlgParser generator, String text) {
    	initializeAnnotations(parser, text);
    	this.generator = generator;
    }
    
    private SimpleNlgParser generator;

    private ArrayList<SentenceAnnotations> sentences = new ArrayList<>();

    /**
     * Annotates a text sentence-wise with tokenization, constituency and dependency parse
     * @param parser    The NLP parser
     * @param text      The text to annotate
     */
    private void initializeAnnotations(CoreNlpParser parser, String text) {
        Annotation docAnnotation = new Annotation(text);
        parser.pipeline().annotate(docAnnotation);

        for(CoreMap sentence : docAnnotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            SentenceAnnotations annotatedSentence = annotateSentence(sentence);
            if(annotatedSentence != null) {
                sentences.add(annotatedSentence);
            }
        }
    }

    /**
     * Annotates a sentence with tokenization, constituency and dependency parse
     * @param sentence  The sentence to annotate
     * @return          The annotated sentence
     */
    private SentenceAnnotations annotateSentence(CoreMap sentence) {
        if (sentence.size() == 0) {
            return null;
        }

        Tree constituentTree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
        List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);
        Collection<TypedDependency> dependencyGraph = sentence
                .get(SemanticGraphCoreAnnotations.CollapsedDependenciesAnnotation.class)
                .typedDependencies();

        return new SentenceAnnotations(sentence, constituentTree, tokens, dependencyGraph);
    }

    /**
     * Identifies the sentence which contains the construction delimited by the given indices
     * @param constructionIndices   The start and end indices of the construction
     * @return                      The sentence which contains the construction
     */
    private SentenceAnnotations getRelevantSentence(Pair<Integer, Integer> constructionIndices) {
        // We always operate on the entire sentence to facilitate NLP processing.
        for (SentenceAnnotations sent : sentences) {

            if (sent.getTokens().get(0).beginPosition() <= constructionIndices.first && 
            		sent.getTokens().get(sent.getTokens().size() - 1).endPosition() >= constructionIndices.second) {
                return sent;
            }
        }
        return null;
    }

    /**
     * Determines the tokens within the construction boundaries.
     * @param sentence              The sentence containing the construction
     * @param constructionIndices   The start and end indices of the construction
     * @return                      The tokens belonging to the construction
     */
    private ArrayList<CoreLabel> getRelevantTokens(SentenceAnnotations sentence, Pair<Integer, Integer> constructionIndices) {
        ArrayList<CoreLabel> relevantTokens = new ArrayList<>();
        for (CoreLabel token : sentence.getTokens()) {
            if (token.beginPosition() >= constructionIndices.first && token.endPosition() <= constructionIndices.second) {
                relevantTokens.add(token);
            }
        }

        return relevantTokens;
    }

    /**
     * Extracts the indices of the if-clause and the main clause of a conditional sentence.
     * @param constructionIndices   The start and end indices of the conditional sentence
     * @return                      The start and end indices of the main clause and the if-clause
     */
    public Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> getConditionalClauses(Pair<Integer, Integer> constructionIndices){
        SentenceAnnotations sent = getRelevantSentence(constructionIndices);
        if(sent == null) {
            return null;
        }

        // if clause: SBAR node with first direct child IN with only direct child if
        TregexPattern pattern = TregexPattern.compile("SBAR <, (IN <: /[Ii]f/)");
        List<Tree> leaves = sent.getConstituentTree().getLeaves();

        sent.getConstituentTree().pennPrint();
        TregexMatcher matcher = pattern.matcher(sent.getConstituentTree());
        if(matcher.find()) {
            Tree match = matcher.getMatch();

            // We don't have the indices in the tree, so we need to get them by mapping the tree leaves to the tokenized sentence
            Pair<Integer, Integer> clauseIndices = getIndices(leaves, match.getLeaves(), null, sent.getTokens());
            int ifClauseStartIndex = clauseIndices.first;
            int ifClauseEndIndex = clauseIndices.second;

            // main clause: minimal S that contains the if-clause
            pattern = TregexPattern.compile("S|SQ << SBAR");
            matcher = pattern.matcher(sent.getConstituentTree());
            while(matcher.find()) {
                Tree sMatch = matcher.getMatch();
                // The S node contains the if-clause
                if(sMatch.dominates(match)) {
                    List<Tree> dominationPath = sMatch.dominationPath(match);
                    boolean containsS = false;
                    if(dominationPath.size() > 2) { // the domination path includes match and sMatch
                        for (int i = 1; i < dominationPath.size() - 1; i++) {
                            Tree t = dominationPath.get(i);
                            t.pennPrint();
                            if ((t.nodeString().equals("S") || t.nodeString().equals("SQ")) && !match.dominates(t)) {
                                containsS = true;
                                break;
                            }
                        }
                    }
                    if(!containsS) {
                        //This is the main clause which also includes the if-clause
                        // we do not want to include the if-clause
                        clauseIndices = getIndices(leaves, sMatch.getLeaves(), match, sent.getTokens());
                        int mainClauseStartIndex = clauseIndices.first;
                        int mainClauseEndIndex = clauseIndices.second;

                        return new Pair<>(new Pair<>(mainClauseStartIndex, mainClauseEndIndex), new Pair<>(ifClauseStartIndex, ifClauseEndIndex));
                    }
                }
            }
        }

        return null;
    }

    /**
     * Determines the start and end indices of a tree
     * @param sentenceLeaves        The leave nodes of the entire sentence
     * @param constructionLeaves    The leave nodes of the tree to index
     * @param excludedTree          A tree which is to be excluded from the construction
     * @param tokens                The tokens of the sentence
     * @return                      The start and end index of the tree, excluding the excluded part
     */
    private Pair<Integer, Integer> getIndices(List<Tree> sentenceLeaves, List<Tree> constructionLeaves, Tree excludedTree, List<CoreLabel> tokens) {
        int nLeftLeaves = 0;
        int nConstructionPos = 0;
        boolean inClause = false;
        boolean startedClause = false;
        boolean endedClause = false;
        int i = 0;
        while(!(startedClause && endedClause) && i < sentenceLeaves.size()) {
            Tree leaf = sentenceLeaves.get(i++);

            if(leaf == constructionLeaves.get(0)) {
                inClause = true;
            }

            if(startedClause && excludedTree != null && excludedTree.dominates(leaf)) {
                endedClause = true;
            }

            if(!inClause || excludedTree != null && excludedTree.dominates(leaf) && !endedClause) {
                nLeftLeaves++;
            } else {
                startedClause = true;
            }

            if(startedClause && !endedClause) {
                nConstructionPos++;
            }

            if(leaf == constructionLeaves.get(constructionLeaves.size() - 1)) {
                endedClause = true;
            }
        }

        int startIndex = tokens.get(nLeftLeaves).beginPosition();
        int endIndex = tokens.get(nLeftLeaves + nConstructionPos - 1).endPosition();

        return new Pair<>(startIndex, endIndex);
    }

    /**
     * Determines the cluster of verbs within the construction boundaries.
     * Also encompasses anything between the first and last verb (e.g. negation)
     * @param constructionIndices   The start and end indices of the construction
     * @return                      The start and end indices of the verb cluster
     */
    public Pair<Integer, Integer> extractVerbCluster(Pair<Integer, Integer> constructionIndices) {
        SentenceAnnotations sent = getRelevantSentence(constructionIndices);
        if(sent == null) {
            return null;
        }

        ArrayList<CoreLabel> tokens = getRelevantTokens(sent, constructionIndices);
        String previousPos = getPreviousPos(sent, tokens);

        Integer firstVerbStartIndex = null;
        Integer lastVerbEndIndex = null;
        for (CoreLabel token : tokens) {
            String pos = token.tag();

            if(pos.startsWith("VB") && !pos.equals("VB") || pos.equals("MD") || pos.equals("VB") && !previousPos.equals("TO")) {
                if(firstVerbStartIndex == null) {
                    firstVerbStartIndex = token.beginPosition();
                }
                lastVerbEndIndex = token.endPosition();
            }
        }

        if(firstVerbStartIndex != null) {
            return new Pair<>(firstVerbStartIndex, lastVerbEndIndex);
        } else {
            return null;
        }
    }

    /**
     * Determines the POS of the previous token, if any.
     * @param sent      The current sentence
     * @param tokens    The tokens of the current sentence
     * @return          The POS of the previous token, if any; otherwise the empty string
     */
    private String getPreviousPos(SentenceAnnotations sent, ArrayList<CoreLabel> tokens) {
        // get the POS of the last token not contained in the construction, if any
        String previousPos = "";
        int firstTokenIndex = sent.getTokens().indexOf(tokens.get(0));
        if(firstTokenIndex > 0) {
            previousPos = sent.getTokens().get(firstTokenIndex - 1).tag();
        }

        // if there is no previous token in this sentence, get the POS of the last token of the previous sentence, if any
        if(previousPos.equals("")) {
            int sentenceIndex = sentences.indexOf(sent);
            if (sentenceIndex > 0) {
                SentenceAnnotations previousSentence = sentences.get(sentenceIndex - 1);
                previousPos = previousSentence.getTokens().get(previousSentence.getTokens().size() - 1).tag();
            }
        }

        return previousPos;
    }

    /**
     * Lemmatizes a verb construction with the given boundary indices.
     * Anything other than verbs contained within the construction (e.g. negation) is preserved.
     * @param constructionIndices   The start and end index of the verb construction
     * @return                      The lemmatized verb construction
     */
    public String getLemmatizedVerbConstruction(Pair<Integer, Integer> constructionIndices){
        SentenceAnnotations sent = getRelevantSentence(constructionIndices);
        if(sent == null) {
            return null;
        }

        ArrayList<CoreLabel> tokens = getRelevantTokens(sent, constructionIndices);
        String previousPos = getPreviousPos(sent, tokens);

        ArrayList<Pair<String, String>> constructionParts = new ArrayList<>();
        for (CoreLabel token : tokens) {
            String pos = token.tag();
            String lemma = null;

            if(pos.equals("VBN")) {
                // If we have a participle, we lemmatize it and remove all other verbs except for to-infinitives
                lemma = token.lemma();
                constructionParts = removeTokensStartingWith(constructionParts, "VB");
            } else if(pos.startsWith("VB") && !pos.equals("VB")) {
                // If we don't have a participle, we take the last found VB
                // Since the participle comes after conjugated verb forms, this will just be overwritten if we find a participle after all
                lemma = token.lemma();
                constructionParts = removeTokens(constructionParts, "MD");
            } else if(pos.equals("MD")) {
                // If we don't have a participle or conjugated verb or infinitive (not to-infinitive), we take the modal
                // The modal always comes first, so it can just be overwritten
                lemma = token.lemma();
            } else if(pos.equals("VB") && !previousPos.equals("TO")) {
                // If we have an infinitive with a modal, we take this
                lemma = token.lemma();
                constructionParts = removeTokens(constructionParts, "MD");
            }

            if(lemma == null) {
                // if it wasn't any token we want to lemmatize, we take the word form instead
                lemma = token.word();
            }

            constructionParts.add(new Pair<>(lemma, token.tag()));
            previousPos = token.tag();
        }

        StringBuilder sb = new StringBuilder();
        for(Pair<String, String> constructionPart : constructionParts) {
            sb.append(constructionPart.first);
            sb.append(" ");
        }

        return sb.toString().trim();
    }

    /**
     * Removes tokens whose POS starts with but does not equal the provided string from the list.
     * @param constructionParts The tokens list
     * @param posToRemove       The start of the POS of the tokens to remove
     * @return                  The tokens list without tokens having the provided POS
     */
    private ArrayList<Pair<String, String>> removeTokensStartingWith(ArrayList<Pair<String, String>> constructionParts,
                                                                     String posToRemove) {
        ArrayList<Pair<String, String>> remainingParts = new ArrayList<>();
        for(Pair<String, String> constructionPart : constructionParts) {
            if(!(constructionPart.second.startsWith(posToRemove) && !constructionPart.second.equals(posToRemove))) {
                remainingParts.add(constructionPart);
            }
        }

        return remainingParts;
    }

    /**
     * Removes tokens whose POS equals the provided string from the list.
     * @param constructionParts The tokens list
     * @param posToRemove       The POS of the tokens to remove
     * @return                  The tokens list without tokens having the provided POS
     */
    private ArrayList<Pair<String, String>> removeTokens(ArrayList<Pair<String, String>> constructionParts, String posToRemove) {
        ArrayList<Pair<String, String>> remainingParts = new ArrayList<>();
        for(Pair<String, String> constructionPart : constructionParts) {
            if(!constructionPart.second.equals(posToRemove)) {
                remainingParts.add(constructionPart);
            }
        }

        return remainingParts;
    }

    /**
     * Determines the lemma of a comparison form of an adjective.
     * @param constructionIndices   The start and end indices of the comparison form in the plain text
     * @return                      The positive form of the adjective
     */
    public String getLemmaOfComparison(Pair<Integer, Integer> constructionIndices){
        SentenceAnnotations sent = getRelevantSentence(constructionIndices);
        if(sent == null) {
            return null;
        }

        ArrayList<CoreLabel> tokens = getRelevantTokens(sent, constructionIndices);

        // "more" and "most" are also tagged RBR, so we take the last relevant token
        String lemma = null;
        for (CoreLabel token : tokens) {
            String pos = token.tag();
            if (pos.startsWith("RB") || pos.startsWith("JJ")) {
                lemma = token.lemma();
                // TODO: synthetic forms are lemmatized to themselves, so we need to form the positive ourselves
                String tentativeLemma = null;
                if(lemma.endsWith("er")) {
                    tentativeLemma = token.word().substring(0, token.word().length() - 2);
                } else if(lemma.endsWith("est")) {
                    tentativeLemma = token.word().substring(0, token.word().length() - 3);
                }

                if(tentativeLemma != null) {
                    if(tentativeLemma.endsWith("i")) {
                        tentativeLemma = tentativeLemma.substring(0, tentativeLemma.length() - 1) + "y";
                    }
                    WordElement w = generator.lexicon().getWord(tentativeLemma);
                    if(w.getCategory() == LexicalCategory.ADJECTIVE || w.getCategory() == LexicalCategory.ADVERB) {
                        lemma = tentativeLemma;
                    } else {
                        tentativeLemma = tentativeLemma + "e";
                        w = generator.lexicon().getWord(tentativeLemma);
                        if(w.getCategory() == LexicalCategory.ADJECTIVE || w.getCategory() == LexicalCategory.ADVERB) {
                            lemma = tentativeLemma;
                        }
                    }
                }
            }
        }

        return lemma;
    }

    /**
     * Generates an active sentence from the provided passive sentence.
     * @param constructionIndices   The start and end indices of the passive sentence
     * @param plainText             The text containing the passive sentence
     * @param construction          The type of grammatical construction
     * @return                      The active sentence corresponding to the passive construction at the specified indices
     */
    public Pair<String, Pair<Integer, Integer>> getActiveSentence(Pair<Integer, Integer> constructionIndices,
                                                                   String plainText, DetailedConstruction construction) {
        ArrayList<Pair<Integer, Integer>> components = getPassiveSentenceComponents(constructionIndices);
        if(components == null) {
            return  null;
        }

        SentenceAnnotations sent = getRelevantSentence(constructionIndices);
        if(sent == null) {
            return null;
        }

        ArrayList<CoreLabel> verbTokens = getRelevantTokens(sent, components.get(2));
        String modal = null;
        String verb = "";
        for(CoreLabel token : verbTokens) {
            if(token.tag().equals("MD")) {
                modal = token.word();
            }
            verb = token.lemma();
        }

        String patient = decapitalize(plainText.substring(components.get(1).first, components.get(1).second));
        // remove "by"
        String agent = components.get(3) == null ? "Someone or something" : plainText.substring(components.get(3).first + 2, components.get(3).second).trim();

        Tense tense;
        if(construction.toString().contains("_PRES")) {
            tense = Tense.PRESENT;
        } else if(construction.toString().contains("_FUT")) {
            tense = Tense.FUTURE;
        } else {
            tense = Tense.PAST;
        }

        boolean perfect = false;
        if(construction.toString().contains("PERF")) {
            perfect = true;
        }

        boolean progressive = false;
        if(construction.toString().endsWith("PRG")) {
            progressive = true;
        }

        SPhraseSpec p = generator.nlgFactory().createClause();
        p.setFeature(Feature.TENSE, tense);
        p.setFeature(Feature.PASSIVE, false);
        p.setFeature(Feature.PERFECT, perfect);
        p.setFeature(Feature.PROGRESSIVE, progressive);
        if(modal != null) {
            p.setFeature(Feature.MODAL, modal);
        }
        p.setSubject(agent);
        p.setVerb(verb);
        p.setObject(patient);
        String activeSentence = generator.realiser().realiseSentence(p);


        if(components.get(0) != null) {
            activeSentence = plainText.substring(components.get(0).first, components.get(0).second) + decapitalize(activeSentence);
        }
        if(components.get(4) != null) {
            activeSentence = activeSentence.substring(0, activeSentence.length() - 1) +
                    plainText.substring(components.get(4).first, components.get(4).second);
        }

        int sentenceStartindex = sent.getTokens().get(0).beginPosition();
        int sentenceEndIndex = sent.getTokens().get(sent.getTokens().size() - 1).endPosition();

        return new Pair<>(activeSentence, new Pair<>(sentenceStartindex, sentenceEndIndex));
    }

    /**
     * Determines the start and end indices of the tokens governed directly or indirectly by the root, including the root itself
     * @param dependencies  The dependency graph
     * @param root          The governing root node
     * @return              The start and end indices of the text goverened by the root
     */
    private Pair<Pair<Integer, Integer>, IndexedWord> getDependencySpan(Collection<TypedDependency> dependencies,
                                                                        IndexedWord root) {
        ArrayList<IndexedWord> components = new ArrayList<>();
        ArrayList<IndexedWord> newDescendants = getDescendants(dependencies, root);
        while(newDescendants.size() > 0) {
            ArrayList<IndexedWord> currentDescendants = new ArrayList<>();
            for(IndexedWord descendant : newDescendants) {
                currentDescendants.addAll(getDescendants(dependencies, descendant));
            }
            components.addAll(newDescendants);
            newDescendants = currentDescendants;
        }

        int startIndex = root.beginPosition();
        int endIndex = root.endPosition();
        IndexedWord firstWord = root;
        for(IndexedWord component : components) {
            if(component.beginPosition() < startIndex) {
                startIndex = component.beginPosition();
                firstWord = component;
            }
            if(component.endPosition() > endIndex) {
                endIndex = component.endPosition();
            }
        }

        return new Pair<>(new Pair<>(startIndex, endIndex), firstWord);
    }

    /**
     * Identifies all nodes governed by the given governor.
     * @param dependencies  The dependency graph
     * @param governor      The governing token
     * @return              The nodes governed by the governor
     */
    ArrayList<IndexedWord> getDescendants(Collection<TypedDependency> dependencies, IndexedWord governor) {
        ArrayList<IndexedWord> descendants = new ArrayList<>();
        for(TypedDependency dependency : dependencies) {
            if(dependency.gov().equals(governor)) {
                descendants.add(dependency.dep());
            }
        }

        return descendants;
    }

    /**
     * Turns the first character of the given text to lower-case.
     * @param text  The text to decapitalize
     * @return      The decapitalized text
     */
    private String decapitalize(String text) {
        return text.substring(0, 1).toLowerCase() + text.substring(1);
    }

    /**
     * Determines the agent, patient, verb and any other components of a passive sentence.
     * @param constructionIndices   The indices of the passive sentence in the plain text
     * @return                      The indices of the part preceding the patient, the patient, the verb cluster, the agent and the part succeeding the agent
     */
    private ArrayList<Pair<Integer, Integer>> getPassiveSentenceComponents(Pair<Integer, Integer> constructionIndices) {
        Pair<Integer, Integer> verbIndices;
        Pair<Integer, Integer> agentIndices = null;
        Pair<Integer, Integer> patientIndices;
        Pair<Integer, Integer> previousTextIndices = null;
        Pair<Integer, Integer> succeedingTextIndices = null;

        SentenceAnnotations sent = getRelevantSentence(constructionIndices);
        if(sent == null) {
            return null;
        }

        Collection<TypedDependency> dependencyGraph = sent.getDependencyGraph();
        TypedDependency patientRelation = null;
        ArrayList<TypedDependency> agents = new ArrayList<>();
        for(TypedDependency dependency : dependencyGraph) {
            if(dependency.reln().getShortName().equals("nsubjpass")) {
                patientRelation = dependency;
            } else if(dependency.reln().getShortName().equals("nmod") && dependency.reln().getSpecific().equals("agent")) {
                agents.add(dependency);
            }
        }

        // We should find exactly 1 patient
        if(patientRelation == null) {
            return null;
        }

        IndexedWord mainVerb = patientRelation.gov();

        int startIndex = mainVerb.beginPosition();
        int endIndex = mainVerb.endPosition();
        for(TypedDependency dependency : dependencyGraph) {
            if(dependency.gov().equals(mainVerb) && dependency.reln().getShortName().startsWith("aux")) {
                if(dependency.dep().beginPosition() < startIndex) {
                    startIndex = dependency.dep().beginPosition();
                }
                if(dependency.dep().endPosition() > endIndex) {
                    endIndex = dependency.dep().endPosition();
                }
            }
        }
        verbIndices = new Pair<>(startIndex, endIndex);

        IndexedWord patientRoot = patientRelation.dep();
        Pair<Pair<Integer, Integer>, IndexedWord> patientSpan = getDependencySpan(dependencyGraph, patientRoot);
        if(patientSpan.first == null) {
            return null;
        }
        patientIndices = patientSpan.first;

        for(TypedDependency possibleAgent : agents) {
            IndexedWord possibleAgentRoot = possibleAgent.dep();
            Pair<Pair<Integer, Integer>, IndexedWord> possibleAgentSpan = getDependencySpan(dependencyGraph, possibleAgentRoot);
            if(possibleAgentSpan.second.word().equals("by")) {
                // This is our by-clause
                agentIndices = possibleAgentSpan.first;
                break;
            }
        }

        int sentenceStartIndex = sent.getTokens().get(0).beginPosition();
        int sentenceEndIndex = sent.getTokens().get(sent.getTokens().size() - 1).endPosition();
        if(sentenceStartIndex < patientSpan.first.first) {
            previousTextIndices = new Pair<>(sentenceStartIndex, patientSpan.first.first);
        }
        if(agentIndices != null && sentenceEndIndex > agentIndices.second) {
            succeedingTextIndices = new Pair<>(agentIndices.second, sentenceEndIndex);
        } else if(agentIndices == null && sentenceEndIndex > endIndex) {
            succeedingTextIndices = new Pair<>(verbIndices.second, sentenceEndIndex);
        }

        ArrayList<Pair<Integer, Integer>> sentenceParts = new ArrayList<>();
        sentenceParts.add(previousTextIndices);
        sentenceParts.add(patientIndices);
        sentenceParts.add(verbIndices);
        sentenceParts.add(agentIndices);
        sentenceParts.add(succeedingTextIndices);

        return sentenceParts;
    }

    /**
     * Splits a verb cluster into the last participle and the rest.
     * @param constructionIndices   The start and end indices of the verb cluster in the plain text
     * @return                      The inflected verbs and the participle
     */
    public ArrayList<Pair<Integer, Integer>> splitParticiple(Pair<Integer, Integer> constructionIndices) {
        SentenceAnnotations sent = getRelevantSentence(constructionIndices);
        if(sent == null) {
            return null;
        }

        ArrayList<CoreLabel> tokens = getRelevantTokens(sent, constructionIndices);

        Integer participleStartIndex = null;
        for (CoreLabel token : tokens) {
            if(token.tag().equals("VBN")) {
                participleStartIndex = token.beginPosition();
            }
        }

        if(participleStartIndex == null) {
            return null;
        }

        ArrayList<Pair<Integer, Integer>> constructionParts = new ArrayList<>();
        constructionParts.add(new Pair<>(constructionIndices.first, participleStartIndex));
        constructionParts.add(new Pair<>(participleStartIndex, constructionIndices.second));

        return constructionParts;
    }
    
    /**
     * Gets the lemma of a verb construction.
     * Anything other than verbs contained within the construction (e.g. negation) is preserved.
     * @param constructionIndices   The start and end index of the verb construction
     * @param getSubject            <c>true</c> if the subject is also to be determined; otherwise <c>false</c>
     * @return                      The lemma of the main verb and the subject if it is to be determined, otherwise <c>null</c>
     */
    public Pair<String, String> getVerbLemma(Pair<Integer, Integer> constructionIndices, boolean getSubject){
        SentenceAnnotations sent = getRelevantSentence(constructionIndices);
        if(sent == null) {
            return null;
        }

        ArrayList<CoreLabel> tokens = getRelevantTokens(sent, constructionIndices);
        String previousPos = getPreviousPos(sent, tokens);

        CoreLabel mainVerb = null;
        for (CoreLabel token : tokens) {
            String pos = token.tag();

            // The last verb in a verb cluster is the main verb, so we just lemmatize that
            if(pos.equals("VBN") || pos.equals("VBZ") || pos.startsWith("VB") && !pos.equals("VB") || pos.equals("MD") ||
                    pos.equals("VB") && !previousPos.equals("TO")) {
                mainVerb = token;
            }

            previousPos = token.tag();
        }

        if(mainVerb == null) {
        	return null;
        }
        if(getSubject) {
            return new Pair<>(mainVerb.lemma(), getSubject(mainVerb.beginPosition(), sent.getDependencyGraph()));
        }
        return new Pair<>(mainVerb.lemma(), null);
    }

    /**
     * Determines the subject of a main verb.
     * @param mainVerbStartIndex    The start index of the main verb
     * @param dependencyGraph       The dependencies
     * @return                      The subject
     */
    private String getSubject(int mainVerbStartIndex,
                              Collection<TypedDependency> dependencyGraph) {
        String subject = null;
        IndexedWord copulaGovernor = null;
        for(TypedDependency dependency : dependencyGraph) {
            if(dependency.reln().getShortName().startsWith("nsubj") && dependency.gov().beginPosition() == mainVerbStartIndex) {
                subject = dependency.dep().word();
            } else if(dependency.reln().getShortName().equals("cop") && dependency.dep().beginPosition() == mainVerbStartIndex) {
                copulaGovernor = dependency.gov();
            }
        }

        // If the main verb is a copula, the subject is the subject of the governing object
        if(subject == null && copulaGovernor != null) {
            for(TypedDependency dependency : dependencyGraph) {
                if(dependency.reln().getShortName().startsWith("nsubj") && dependency.gov().equals(copulaGovernor)) {
                    subject = dependency.dep().word();
                }
            }
        }

        return subject;
    }

    /**
     * Generates the correct form for the given parameters.
     * @param settings  The parameter settings
     * @return          The generated form
     */
    public String generateCorrectForm(ParameterSettings settings) {
        return new FormGeneratorFactory().getGenerator(settings, generator).generateCorrectForm(settings);
    }

    /**
     * Generates incorrect forms for the given parameters.
     * @param settings  The parameter settings
     * @return          The generated incorrect forms, possibly also including the correct form
     */
    public HashSet<String> generateIncorrectForms(ParameterSettings settings) {
        return new FormGeneratorFactory().getGenerator(settings, generator).generateIncorrectForms(settings);
    }

    /**
     * Determines the tense specifics of a conditional clause.
     * We differentiate only between present and past, and perfect or not (we are not interested in progressive).
     * Determines the modal if any exists.
     * Determines whether the clause is 3rd person sg.
     * Determines whether it's a negate clause and/or an interrogative clause.
     * @param constructionIndices   The start and end indices of the verb cluster of the clause
     * @return                      The settings for this clause
     */
    public TenseSettings getConditionalClauseSpecifics(Pair<Integer, Integer> constructionIndices){
        SentenceAnnotations sent = getRelevantSentence(constructionIndices);
        if(sent == null) {
            return null;
        }

        String modal = null;
        boolean hasInflectedPresent = false;
        boolean hasPastParticiple = false;
        boolean hasHave = false;
        boolean hasNegation = false;
        boolean isThirdPerson = false;
        List<CoreLabel> tokens = getRelevantTokens(sent, constructionIndices);
        for(int i = 0; i < tokens.size(); i++) {
            CoreLabel token = tokens.get(i);
            if(token.tag().equals("MD")) {
                modal = token.word();
            } else if(token.tag().equals("VBP") || token.tag().equals("VBZ")){
                if(token.word().matches("[hH]a(ve|s)}")) {
                    hasHave = true;
                }
                hasInflectedPresent = true;

                if(token.tag().equals("VBZ")) {
                    isThirdPerson = true;
                }
            } else if(token.tag().equals("VBN")) {
                hasPastParticiple = true;
            } else if(token.tag().equals("VBD")) {
                if(token.word().equals("had")) {
                    hasHave = true;
                }
            } else if(token.tag().equals("VB")){
                if(token.word().matches("[hH]ave")) {
                    hasHave = true;
                }
            } else if(token.tag().equals("RB") && token.word().equals("n't") || token.word().equals("not")) {
                hasNegation = true;
            }
        }

        boolean isInterrogative = false;
        for(Tree node : sent.getConstituentTree()) {
            if(node.toString().equals("SQ")) {
                isInterrogative = true;
            }
        }

        boolean isPerfect = hasHave && hasPastParticiple;
        boolean isPresent = hasInflectedPresent || modal != null && !isPerfect;

        Pair<String, String> lemma = getVerbLemma(constructionIndices, true);
        return new TenseSettings(lemma.first, isInterrogative, hasNegation, isThirdPerson,
                lemma.second, isPresent ? "present" : "past", false, isPerfect, modal);
    }

    /**
     * Determines the real equivalent of an unreal modal.
     * @param modal The unreal modal
     * @return      The real modal
     */
    public String getRealModal(String modal) {
        if(modal.equals("could")) {
            return "can";
        } else if(modal.equals("should")) {
            return "shall";
        } else if(modal.equals("might")) {
            return "may";
        } else if(modal.equals("would")) {
            return "will";
        } else return "will";
    }

    /**
     * Determines the unreal equivalent of a real modal.
     * @param modal The real modal
     * @return      The unreal modal
     */
    public String getUnrealModal(String modal) {
        if(modal.equals("can")) {
            return "could";
        } else if(modal.equals("may")) {
            return "might";
        } else if(modal.equals("must")) {
            return "would have to";
        } else if(modal.equals("need")) {
            return "would need to";
        } else if(modal.equals("shall")) {
            return "should";
        } else if(modal.equals("will")) {
            return "would";
        } else return "would";
    }
    
}