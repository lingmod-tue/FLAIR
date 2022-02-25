package com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.flair.server.exerciseGeneration.exerciseManagement.resourceManagement.ResourceLoader;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.Pair;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.trees.TypedDependency;

public class ConditionalNlpManager extends NlpManager {

    public ConditionalNlpManager(CoreNlpParser parser, SimpleNlgParser generator, String text, OpenNlpParser lemmatizer) {
    	super(parser, generator, text, lemmatizer);
    }
    
    public ArrayList<ConditionalSentence> analyzeConditionalSentence() {
    	ArrayList<ConditionalSentence> condSentences = new ArrayList<>();
    	for(SentenceAnnotations sentence : sentences) {
            ConditionalSentence clauses = getConditionalClauses(sentence.getDependencyGraph(), sentence.getTokens());

            if(clauses == null) {
                continue;
            }
            
            generateDistractors(clauses, sentence);
            getTranslation(clauses.mainClause, sentence.getSentence().toString());
            getTranslation(clauses.ifClause, sentence.getSentence().toString());
            getExtendedGivenWords(sentence, clauses.ifClause);
            getExtendedGivenWords(sentence, clauses.mainClause);
            getTargetPosition(clauses.mainClause);
            getTargetPosition(clauses.ifClause);
            getSemanticDistractors(clauses.mainClause, 2);
            getSemanticDistractors(clauses.ifClause, 2);
            determineConditionalType(clauses);
            
            condSentences.add(clauses);
    	}
    	
    	return condSentences;
    }
    
    /**
     * Extracts the main clause and the if-clause from a conditional sentence.
     * Determines the verbs and chunks the sentence into verbs (separate chunks), arguments and adjuncts
     * @param dependencyGraph	The dependencies
     * @param tokens			The tagged tokens
     * @return	The clauses, chunks and verbs of the main and if-clauses
     */
    private ConditionalSentence getConditionalClauses(Collection<TypedDependency> dependencyGraph, List<CoreLabel> tokens) {
        Clause ifClause = new Clause();
        Clause mainClause = new Clause();

        mainClause.root = getRoot(dependencyGraph);
        if(mainClause.root == null) {
            return null;
        }

        for(TypedDependency dep : dependencyGraph) {
            if(dep.reln().getShortName().equals("advcl")) {
                ifClause.root = dep.dep();

                ArrayList<IndexedWord> ifSpan = getDependents(dependencyGraph, dep.dep());
                ifClause.indices = new Pair<>(ifSpan.get(0).beginPosition(), ifSpan.get(ifSpan.size() - 1).endPosition());
                removeSentenceInitialAndFinalPunctuation(ifClause);

                getMainVerbCluster(dependencyGraph, ifClause);
                if(ifClause.verbs.size() == 0) {
                    return null;
                }

                ArrayList<IndexedWord> mainSpan = getDependents(dependencyGraph, dep.gov());
                mainClause.indices = new Pair<>(mainSpan.get(0).beginPosition(), mainSpan.get(mainSpan.size() - 1).endPosition());

                if(ifClause.indices.first > dep.gov().beginPosition()) {
                    // main clause before if-clause;
                    mainClause.indices = new Pair<>(mainClause.indices.first, ifClause.indices.first);
                } else {
                    mainClause.indices = new Pair<>(ifClause.indices.second, mainClause.indices.second);
                }
                removeSentenceInitialAndFinalPunctuation(mainClause);

                getMainVerbCluster(dependencyGraph, mainClause);

                CoreLabel ifToken = getIfToken(tokens);
                if (ifToken == null) {
                    return null;
                }
                ifClause.chunks = getRemainingChildren(dependencyGraph, ifClause, tokens, ifToken);
                mainClause.chunks = getRemainingChildren(dependencyGraph, mainClause, tokens, null);

                break;
            }
        }

        if(ifClause.chunks == null) {
            return null;
        }

        ConditionalSentence condSent = new ConditionalSentence();
        condSent.ifClause = ifClause;
        condSent.mainClause = mainClause;
        return condSent;
    }

    /**
     * Determines the dependents of the root and sorts them.
     * @param dependencies	The annotated dependencies
     * @param root			The item for which to determine the dependents
     * @return	The dependents (including the root) as sorted list
     */
    private ArrayList<IndexedWord> getDependents(Collection<TypedDependency> dependencies,
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

        components.add(root);
        components.sort(Comparator.comparingInt(IndexedWord::beginPosition));

        return components;
    }

    /**
     * Removes punctuation at the beginning and at the end of the clause.
     * Modifies the clause indices in-place.
     * @param clause	The clause
     */
    private void removeSentenceInitialAndFinalPunctuation(Clause clause) {
        if (sentences.get(0).getSentence().toString().substring(clause.indices.second - 1, clause.indices.second).matches("[.,:!;?]")) {
            clause.indices = new Pair<>(clause.indices.first, clause.indices.second - 1);
        }
        if (sentences.get(0).getSentence().toString().substring(clause.indices.first, clause.indices.first + 1).matches("[.,:!;?]")) {
            clause.indices = new Pair<>(clause.indices.first + 1, clause.indices.second);
        }
    }

    /**
     * Determines the verb cluster containing the main verb.
     * Adds it to the clause properties.
     * @param dependencyGraph	The annotated dependencies of the sentence
     * @param clause    The clause properties
     */
    private void getMainVerbCluster(Collection<TypedDependency> dependencyGraph, Clause clause) {
        IndexedWord root = clause.root;
        Pair<Integer, Integer> span = clause.indices;
        ArrayList<IndexedWord> verbs = new ArrayList<>();
        if(!(root.tag().equals("MD") || root.tag().startsWith("VB"))) {
            // The root is not the main verb, so we search its children for a verb
            ArrayList<IndexedWord> children = getDescendants(dependencyGraph, root);
            for(IndexedWord child : children) {
                if(child.beginPosition() >= span.first && child.endPosition() <= span.second && (child.tag().equals("MD") || child.tag().startsWith("VB"))) {
                    verbs.add(child);
                    verbs.addAll(getVerbChildren(dependencyGraph, child));
                }
            }
            clause.mainVerb = verbs.get(verbs.size() - 1);
        } else {
            clause.mainVerb = root;
            verbs.add(root);
            ArrayList<IndexedWord> verbChildren = getVerbChildren(dependencyGraph, root);
            for(IndexedWord verbChild : verbChildren) {
                if(verbChild.beginPosition() >= span.first && verbChild.endPosition() <= span.second) {
                    verbs.add(verbChild);
                }
            }
        }

        verbs.sort(Comparator.comparingInt(IndexedWord::beginPosition));
        clause.verbs = verbs;
    }

    /**
     * Determines the verbs depending on the parent.
     * @param dependencyGraph	The annotated dependencies
     * @param parent			The token on which the verbs must depend
     * @return	The verbs depending on the parent
     */
    private ArrayList<IndexedWord> getVerbChildren(Collection<TypedDependency> dependencyGraph, IndexedWord parent) {
        ArrayList<IndexedWord> verbs = new ArrayList<>();
        ArrayList<IndexedWord> children = getDescendants(dependencyGraph, parent);
        for(IndexedWord child : children) {
            if(child.tag().equals("MD") || child.tag().startsWith("VB")) {
                verbs.add(child);
                verbs.addAll(getVerbChildren(dependencyGraph, child));
            }
        }

        return verbs;
    }

    /**
     * Determines the token corresponding to the 'if' in an if-clause.
     * @param tokens	The tokens of the sentence
     * @return	The token corresponding to the 'if' in an if-clause
     */
    private CoreLabel getIfToken(List<CoreLabel> tokens) {
        for(CoreLabel token : tokens) {
            if(token.tag().equals("IN") && token.value().equalsIgnoreCase("if")) {
                return token;
            }
        }

        return null;
    }

    /**
     * Determines the tokens not yet covered by discovered chunks.
     * @param dependencyGraph	The annotated dependencies of the sentence
     * @param clause			The clause to cover
     * @param tokens			The annotated tokens of the sentence
     * @param ifToken			The token corresponding to 'if' for if-clauses, <code>null</code> for main clauses
     * @return	The tokens not previously covered by chunks, adjoining tokens merged into chunks
     */
    private ArrayList<Pair<Integer, Integer>> getRemainingChildren(Collection<TypedDependency> dependencyGraph, Clause clause, List<CoreLabel> tokens, CoreLabel ifToken) {
        ArrayList<IndexedWord> descendants = getDescendants(dependencyGraph, clause.root);
        ArrayList<Pair<Integer, Integer>> spans = new ArrayList<>();
        ArrayList<IndexedWord> coveredChildren = new ArrayList<>();

        for(IndexedWord verb : clause.verbs) {
            spans.add(new Pair<>(verb.beginPosition(), verb.endPosition()));
            coveredChildren.add(verb);
        }

        for(IndexedWord descendant : descendants) {
            ArrayList<IndexedWord> children = getDependents(dependencyGraph, descendant);
            Pair<Integer, Integer> span = new Pair<>(children.get(0).beginPosition(), children.get(children.size() - 1).endPosition());
            if (span.first >= clause.indices.first && span.second <= clause.indices.second && children.stream().noneMatch(child -> coveredChildren.contains(child) || ifToken != null && ifToken.beginPosition() == child.beginPosition())) {
                spans.add(span);
                coveredChildren.addAll(children);
            }
        }

        if(ifToken != null) {
            spans.add(new Pair<>(ifToken.beginPosition(), ifToken.endPosition()));
        }

        ArrayList<CoreLabel> currentSpan = new ArrayList<>();
        for(CoreLabel token : tokens) {
            if(token.beginPosition() >= clause.indices.first && token.endPosition() <= clause.indices.second && !token.equals(ifToken) && coveredChildren.stream().noneMatch(child -> token.beginPosition() == child.beginPosition())) {
                if(currentSpan.size() > 0 && currentSpan.get(currentSpan.size() - 1).index() + 1 != token.index()) {
                    spans.add(new Pair<>(currentSpan.get(0).beginPosition(), currentSpan.get(currentSpan.size() - 1).endPosition()));
                    currentSpan.clear();
                }
                currentSpan.add(token);
            }
        }
        if(currentSpan.size() > 0) {
            spans.add(new Pair<>(currentSpan.get(0).beginPosition(), currentSpan.get(currentSpan.size() - 1).endPosition()));
        }

        spans.sort(Comparator.comparingInt(span -> span.first));
        return spans;
    }

    /**
     * Determines the subject and the extended subject (string of subject for 1-token subjects, value of complex NN for multi-token subjects) of the clause.
     * Determines whether subject and verb are inverted.
     * Determines if the clause is negated.
     * Adds the values to the clause properties.
     * @param dependencyGraph   The annotated dependencies of the sentence.
     * @param clause            The clause properties
     */
    private void determineSubjectAuxiliaryInversionAndNegation(Collection<TypedDependency> dependencyGraph, Clause clause) {
        for(TypedDependency dep : dependencyGraph) {
            if(dep.gov().equals(clause.root) && dep.reln().getShortName().equalsIgnoreCase("nsubj")) {
                clause.subject = dep.dep();
                ArrayList<IndexedWord> dependents = getDependents(dependencyGraph, clause.subject);
                ArrayList<String> subjects = new ArrayList<>();
                for(IndexedWord  dependent : dependents) {
                    subjects.add(dependent.value());
                }
                clause.extendedSubject = String.join(" ", subjects);
            }

            if (dep.reln().getShortName().equalsIgnoreCase("neg") && dep.dep().beginPosition() >= clause.verbs.get(0).beginPosition() &&
                    dep.dep().endPosition() <= clause.verbs.get(clause.verbs.size() - 1).endPosition()) {
                clause.negated = true;
            }
        }

        if(clause.subject == null) {
            return;
        }

        clause.subjectAuxiliaryInversion = clause.verbs.get(0).beginPosition() < clause.subject.beginPosition();
    }

    /**
     * Determines whether the main verb cluster is third person singular.
     * Adds the result to the clause properties.
     * @param clause            The clause under discussion
     * @param dependencyGraph   The annotated dependencies of the sentence
     */
    private void isThirdSingular(Clause clause, Collection<TypedDependency> dependencyGraph, SentenceAnnotations sentence) {
        // If it's tagged as 3rd person, we can just go for the POS tag
        ArrayList<CoreLabel> tokens = getRelevantTokens(sentence, new Pair<>(clause.verbs.get(0).beginPosition(), clause.verbs.get(clause.verbs.size() - 1).endPosition()));
        for(CoreLabel token : tokens) {
            if(token.tag().equals("VBZ")) {
                return;
            }
        }
        // we cannot only check for VBZ because we might want to generate present from e.g. future where have another tag
        // but still need to know if it's 3sg.
        for(TypedDependency dep : dependencyGraph) {
            System.out.println(dep.reln().getShortName());
            if(dep.gov().equals(clause.subject) && dep.reln().getShortName().equalsIgnoreCase("conj") && dep.reln().getSpecific().equalsIgnoreCase("and")) {
                // The subject is a NN with conjunction 'and'
                clause.thirdSg = false;
                return;
            }
            if(dep.dep().equals(clause.subject) && dep.dep().tag().equalsIgnoreCase("PRP") &&
                    (dep.dep().value().equalsIgnoreCase("I") || dep.dep().value().equalsIgnoreCase("you") ||
                            dep.dep().value().equalsIgnoreCase("we") || dep.dep().value().equalsIgnoreCase("they"))) {
                // It's a pronoun (not he/she/it)
                clause.thirdSg = false;
                return;
            }
        }
    }

    /**
     * Determines the modal of the main verb cluster, if any.
     * Determines if it's in future tense.
     * Determines if it's in past tense.
     * Determines if it's in perfect mode.
     * Determines if it's in progressive mode.
     * Adds the values to the clause properties.
     * @param clause    The properties of the clause under discussion
     */
    private void determineTenseAndMode(Clause clause) {
        ArrayList<CoreLabel> tokens = getRelevantTokens(sentences.get(0), new Pair<>(clause.verbs.get(0).beginPosition(), clause.verbs.get(clause.verbs.size() - 1).endPosition()));
        for(CoreLabel token : tokens) {
            if(token.tag().equalsIgnoreCase("MD")) {
                if(token.value().equalsIgnoreCase("will") || token.value().equalsIgnoreCase("wo")) {
                    clause.future = true;
                } else {
                    clause.modal = token.value();
                }
            } else if(token.tag().equalsIgnoreCase("VBD")) {
                clause.past = true;
            } else if(token.tag().equalsIgnoreCase("VBN")) {
                clause.perfect = true;
            } else if(token.tag().equalsIgnoreCase("VBG")) {
                clause.progressive = true;
            }
        }
    }
    
    
    private void generateDistractors(ConditionalSentence condSent,SentenceAnnotations sentence) { 
    	getDistractorsForClause(sentence.getDependencyGraph(), sentence, condSent.ifClause);
    	getDistractorsForClause(sentence.getDependencyGraph(), sentence, condSent.mainClause);
    }
    
    private void getDistractorsForClause(Collection<TypedDependency> dependencyGraph, 
    		SentenceAnnotations sentence, Clause clause) {    	
    	determineSubjectAuxiliaryInversionAndNegation(dependencyGraph, clause);
        if (clause.subject == null) {
            return;
        }

        isThirdSingular(clause, dependencyGraph, sentence);
        determineTenseAndMode(clause);
        
    	String[] tensesToGenerate = new String[] {"present", "past", "future"};
    	if(clause.modal == null) {
    		if(clause.future) {
    			tensesToGenerate = new String[] {"present", "past"};
    		} else if(clause.past) {
    			tensesToGenerate = new String[] {"present", "future"};
    		} else {
    			tensesToGenerate = new String[] {"past", "future"};
    		}
    	}
    	
    	ArrayList<String> distractors = new ArrayList<>();

    	for(String tense : tensesToGenerate) {
    		distractors.add(generateCorrectForm(new TenseSettings(clause.mainVerb.lemma(), clause.subjectAuxiliaryInversion,
    				clause.negated, clause.thirdSg, clause.extendedSubject, tense,
    				clause.progressive, clause.perfect, null)));
    	}
    	
    	if(tensesToGenerate.length == 2) {
    		distractors.add(generateCorrectForm(new TenseSettings(clause.mainVerb.lemma(), clause.subjectAuxiliaryInversion,
    				clause.negated, clause.thirdSg, clause.extendedSubject, "present",
    				clause.progressive, clause.perfect, "would")));
    	}
    	
    	clause.distractors = distractors;
    }
    
    
    private void getExtendedGivenWords(SentenceAnnotations sentence, Clause clause) {
        ArrayList<CoreLabel> tokens = getRelevantTokens(sentence, new Pair<>(clause.verbs.get(0).beginPosition(), clause.verbs.get(clause.verbs.size() - 1).endPosition()));

        ArrayList<String> givenWords = new ArrayList<>();
    	for(CoreLabel token : getRelevantTokens(sentence, clause.indices)) {
    		if(token.beginPosition() != getIfToken(tokens).beginPosition()) {
    			if(token.beginPosition() >= clause.verbs.get(0).beginPosition() && token.endPosition() <= clause.verbs.get(clause.verbs.size() - 1).endPosition()) {
    				// lemmatize main verb
    				if(token.beginPosition() == clause.mainVerb.beginPosition()) {
    					givenWords.add(token.lemma());
    				}
    			} else {
    				givenWords.add(token.value());
    			}
    		}
    	}
    	
    	clause.lemmatizedClause = givenWords;
    }
    
    
    private void getTargetPosition(Clause clause) {
    	clause.targetPosition = new Pair<>(clause.verbs.get(0).index(), clause.verbs.get(clause.verbs.size() - 1).index());
    }
    
    private void getSemanticDistractors(Clause clause, int number) {
    	ArrayList<String> verbList = new ArrayList<>();
    	InputStream dictionary = ResourceLoader.loadFile("dictionary.txt");
    	
    	try (CSVReader reader = new CSVReader(new InputStreamReader(dictionary))) {
    	      String[] lineInArray;
    	      while ((lineInArray = reader.readNext()) != null) {
    	    	  if(lineInArray[1].equals("VB") && !Character.isUpperCase(lineInArray[0].charAt(0))) {
    	    		  verbList.add(lineInArray[0]);
    	    	  }
    	      }
    	  } catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (CsvValidationException e) {
			e.printStackTrace();
		}
    	
    	ArrayList<String> distractors = new ArrayList<>();
    	while(distractors.size() < number) {
    		String verb = verbList.get((int)(Math.random() * verbList.size()));
    		if(!distractors.contains(verb)) {
    			distractors.add(verb);
    		}
    	}
    	
    	clause.semanticDistractor = distractors;
    }
    
    
    private void getTranslation(Clause clause, String plainText) {
    	String text = plainText.substring(clause.indices.first, clause.indices.second);
    	
        String url = "https://translate.googleapis.com/translate_a/single?"+"client=gtx&"+"sl=en&tl=de&dt=t&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8);
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
        } catch (IOException e) {
            return;
        }

        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        StringBuilder response = new StringBuilder();
        try(BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        } catch (IOException e) {
            return;
        }

        String resp = response.toString();
    	JSONParser parser = new JSONParser();
    	JSONArray main;
		try {
			main = (JSONArray)parser.parse(resp);
		} catch (ParseException e) {
			return;
		}
        JSONArray total = (JSONArray) main.get(0);
        StringBuilder translation = new StringBuilder();
        for (int i = 0; i < total.size(); i++) {
            JSONArray currentLine = (JSONArray) total.get(i);
            translation.append(currentLine.get(0).toString());
        }
        if(translation.length() > 2) {
            clause.translation = translation.toString();
        }
    }
    
    private void determineConditionalType(ConditionalSentence condSent) {
    	if(condSent.ifClause.past && condSent.mainClause.modal != null) {
    		condSent.conditionalType = "Type 2";
    	} 
    }
    
}