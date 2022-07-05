package com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
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
    
    public ArrayList<ConditionalSentence> analyzeConditionalSentence(ExerciseData data) {
    	ArrayList<Pair<Integer, Integer>> constructionIndices = new ArrayList<>();
    	for(TextPart d : data.getParts()) {
    		if(d instanceof ConstructionTextPart && ((ConstructionTextPart)d).getConstructionType().startsWith("cond")) {
    			constructionIndices.add(((ConstructionTextPart)d).getIndicesInPlainText());
    		}
    	}
    	
    	ArrayList<ConditionalSentence> condSentences = new ArrayList<>();
    	boolean previousSentenceWasAdded = false;
    	String contextBuffer = "";
    	for(int i = 0; i < sentences.size(); i++) {
    		boolean sentenceUsed = false;
    		SentenceAnnotations sentence = sentences.get(i);
    		if(constructionIndices.stream().anyMatch(idx -> idx.first >= sentence.getTokens().get(0).beginPosition() &&
    				idx.second <= sentence.getTokens().get(sentence.getTokens().size() - 1).endPosition())) {
    			try {
    	            ConditionalSentence clauses = getConditionalClauses(sentence);
    	
    	            if(clauses == null) {
    	                continue;
    	            }
    	            
    	            generateDistractors(clauses, sentence);
    	            getTranslation(clauses.mainClause);
    	            getTranslation(clauses.ifClause);
    	            getExtendedGivenWords(sentence, clauses.ifClause);
    	            getExtendedGivenWords(sentence, clauses.mainClause);
    	            getTargetPosition(clauses.mainClause);
    	            getTargetPosition(clauses.ifClause);
    	            getSemanticDistractors(clauses.mainClause, 1);
    	            getSemanticDistractors(clauses.ifClause, 1);
    	            determineConditionalType(clauses);
    	            
    	            if(!contextBuffer.isEmpty()) {
    	            	clauses.contextBefore = contextBuffer;
    		            contextBuffer = "";
    	            }
    	            
    	            condSentences.add(clauses);
    	            
    	            previousSentenceWasAdded = true;
    	            sentenceUsed = true;
        		} catch(Exception e) {}
    		} 

    		if(!sentenceUsed) {
    			// if we cannot use this sentences as target sentence, we check if we need it as context for the previous or succeeding sentence
    			// we add it as succeeding context to the previous sentences if the previous sentence was used as a target and there was no linebreak in-between
    			if(previousSentenceWasAdded && 
    					!plainText.substring(sentences.get(i - 1).getTokens().get(sentences.get(i - 1).getTokens().size() - 1).endPosition(), sentence.getTokens().get(0).beginPosition()).contains("\n")) {
    				condSentences.get(condSentences.size() - 1).contextAfter = plainText.substring(sentence.getTokens().get(0).beginPosition(), sentence.getTokens().get(sentence.getTokens().size() - 1).endPosition());
    			} else {
    				contextBuffer = plainText.substring(sentence.getTokens().get(0).beginPosition(), sentence.getTokens().get(sentence.getTokens().size() - 1).endPosition());
    			}
    			
    			previousSentenceWasAdded = false;
    		}
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
    private ConditionalSentence getConditionalClauses(SentenceAnnotations sentence) {
    	Collection<TypedDependency> dependencyGraph = sentence.getDependencyGraph();
    	List<CoreLabel> tokens = sentence.getTokens();
    	
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
        
        String lastToken = sentence.getTokens().get(sentence.getTokens().size() - 1).value();
        String puncutatioMark = lastToken.substring(lastToken.length() - 1);
        if(sentenceFinalPunctuations.contains(puncutatioMark)) {
        	condSent.punctuationMark = puncutatioMark;
        }
        
        return condSent;
    }

    /**
     * Removes punctuation at the beginning and at the end of the clause.
     * Modifies the clause indices in-place.
     * @param clause	The clause
     */
    private void removeSentenceInitialAndFinalPunctuation(Clause clause) {
        if (plainText.substring(clause.indices.second - 1, clause.indices.second).matches("[.,:!;?]")) {
            clause.indices = new Pair<>(clause.indices.first, clause.indices.second - 1);
        }
        if (plainText.substring(clause.indices.first, clause.indices.first + 1).matches("[.,:!;?]")) {
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
    private void determineTenseAndMode(Clause clause, SentenceAnnotations sentence) {
        ArrayList<CoreLabel> tokens = getRelevantTokens(sentence, new Pair<>(clause.verbs.get(0).beginPosition(), clause.verbs.get(clause.verbs.size() - 1).endPosition()));
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
        determineTenseAndMode(clause, sentence);
        
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
        ArrayList<CoreLabel> tokens = getRelevantTokens(sentence, clause.indices);
        CoreLabel ifToken = getIfToken(tokens);

        ArrayList<String> givenWords = new ArrayList<>();
    	for(CoreLabel token : getRelevantTokens(sentence, clause.indices)) {
    		if(ifToken == null || token.beginPosition() != ifToken.beginPosition()) {
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
    	int startIndex = clause.verbs.get(0).beginPosition();
    	int endIndex = clause.verbs.get(clause.verbs.size() - 1).endPosition();
    	Integer start = null;
    	Integer end = null;
    	for(int i = 0; i < clause.chunks.size(); i++) {
    		Pair<Integer, Integer> chunk = clause.chunks.get(i);
    		if(start == null && chunk.first >= startIndex) {
    			start = i  + 1;
    		}
    		if(chunk.second <= endIndex) {
    			end = i + 1;
    		}
    	}
    	clause.targetPosition = new Pair<>(start, end);
    }
    
    private void getSemanticDistractors(Clause clause, int number) {
    	ArrayList<String> verbList = new ArrayList<>();
    	InputStream dictionary = ResourceLoader.loadFile("dictionary.txt");
    	
    	try (CSVReader reader = new CSVReader(new InputStreamReader(dictionary))) {
    	      String[] lineInArray;
    	      while ((lineInArray = reader.readNext()) != null) {
    	    	  String[] cols = lineInArray[0].split("\t");
    	    	  if(cols[1].equals("VB") && !Character.isUpperCase(cols[0].charAt(0))) {
    	    		  verbList.add(cols[0]);
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
    
    
    private void getTranslation(Clause clause) {
    	String text = plainText.substring(clause.indices.first, clause.indices.second);
    	
        String url = null;
		try {
			url = "https://translate.googleapis.com/translate_a/single?"+"client=gtx&"+"sl=en&tl=de&dt=t&q=" + URLEncoder.encode(text, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
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
    
    public boolean isUppercaseWord(int startIndex) {
    	for(SentenceAnnotations sentence : sentences) {
    		for(CoreLabel token : sentence.getTokens()) {
    			if(token.beginPosition() == startIndex) {
    				if(token.tag().equalsIgnoreCase("NNP") || token.tag().equalsIgnoreCase("NNPS") || 
    						token.tag().equalsIgnoreCase("PRP") && token.value().equals("I")) {
    					return true;
    				}
    				return false;
    			}
    		}
    	}
    	
    	return false;
    }
    
}