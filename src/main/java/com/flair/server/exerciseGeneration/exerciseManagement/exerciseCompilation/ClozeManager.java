package com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.Pair;

public class ClozeManager {

	/**
	 * Modifies construction indices in-place if necessary and generates brackets texts for Fib tasks.
	 * @param exerciseSettings	The exercise settings
	 * @param parser			The Stanford CoreNLP parser
	 * @param generator			The Simple NLG generator
	 */
    public void prepareBlanks(ExerciseSettings exerciseSettings, NlpManager nlpManager) {
    	ArrayList<Construction> constructionsToAdd = new ArrayList<>();
        ArrayList<Construction> constructionsToRemove = new ArrayList<>();
        boolean recheckForOverlappingConstructions = false;
        for(Construction construction : exerciseSettings.getConstructions()) {
            if(construction.getConstruction().toString().startsWith("COND")) {
                Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> clauses = nlpManager.getConditionalClauses(construction.getConstructionIndices());
                if(clauses != null) {
                	recheckForOverlappingConstructions = true;
                	
                    int r = 0;
                    if (exerciseSettings.getBrackets().contains(BracketsProperties.EITHER_CLAUSE)) {
                        r = new Random().nextInt(2) + 1;
                    }
                    
                    ArrayList<Pair<Construction, Boolean>> newConstructions = new ArrayList<>();
                    if (clauses.first != null && exerciseSettings.getBrackets().contains(BracketsProperties.MAIN_CLAUSE) || r == 1) {
                        Pair<Integer, Integer> mainClauseConstructionIndices = nlpManager.extractVerbCluster(clauses.first);
                        if(mainClauseConstructionIndices != null) {
                            newConstructions.add(new Pair<>(new Construction(construction.getConstruction(), mainClauseConstructionIndices), true));
                        }
                    }
                    if (clauses.second != null && exerciseSettings.getBrackets().contains(BracketsProperties.IF_CLAUSE) || r == 2) {
                        Pair<Integer, Integer> ifClauseConstructionIndices = nlpManager.extractVerbCluster(clauses.second);
                        if(ifClauseConstructionIndices != null) {
                            newConstructions.add(new Pair<>(new Construction(construction.getConstruction(), ifClauseConstructionIndices), false));
                        }
                    }
                    
                    for(Pair<Construction, Boolean> newConstruction : newConstructions) {
                    	if (exerciseSettings.getContentType().equals("FiB")) {
                            ArrayList<String> brackets = new ArrayList<>();
                            if(addBracketsToConditionals(exerciseSettings, newConstruction.first, newConstruction.second, brackets, nlpManager)) {
                            	if(brackets.size() > 0) {
                            		newConstruction.first.setBracketsText("(" + String.join(", ", brackets) + ")");
                            	}
                            } else {
                            	newConstruction = null;
                            }                               
                        }
                        if(newConstruction != null) {
                        	constructionsToAdd.add(newConstruction.first);
                        }
                    }
                }
                constructionsToRemove.add(construction);
            } else if(construction.getConstruction().toString().startsWith("ADJ") ||
                        construction.getConstruction().toString().startsWith("ADV")) {
                if (exerciseSettings.getContentType().equals("Mark")) {
                    //TODO: if we decide to allow the client to specify whether to split synthetic forms for mark, we have to do that here
                } else if(exerciseSettings.getContentType().equals("FiB")){
                    ArrayList<String> brackets = new ArrayList<>();
                    if(exerciseSettings.getBrackets().contains(BracketsProperties.LEMMA)) {
                        String lemma = nlpManager.getLemmaOfComparison(construction.getConstructionIndices());
                        if(lemma != null) {
                            brackets.add(lemma);
                        } else {
                            constructionsToRemove.add(construction);
                            break;
                        }
                    }
                    if(exerciseSettings.getBrackets().contains(BracketsProperties.POS)) {
                        String pos = construction.getConstruction().toString().startsWith("ADJ") ? "adjective" : "adverb";
                        brackets.add(pos);
                    }

                    if(exerciseSettings.getBrackets().contains(BracketsProperties.COMPARISON_FORM)) {
                        String form = construction.getConstruction().toString().contains("_COMP_") ? "comparative" : "superlative";
                        brackets.add(form);
                    }
                    if(brackets.size() > 0) {
                    	construction.setBracketsText("(" + String.join(", ", brackets) + ")");
                    }
                }
            } else if(construction.getConstruction().toString().startsWith("PASSIVE") ||
                    construction.getConstruction().toString().startsWith("ACTIVE")) {
                if(exerciseSettings.getContentType().equals("FiB")) {
                    ArrayList<String> brackets = new ArrayList<>();
                    if (exerciseSettings.getBrackets().contains(BracketsProperties.ACTIVE_SENTENCE)) {
                    	recheckForOverlappingConstructions = true;

                        Pair<String, Pair<Integer, Integer>> activeSentence = nlpManager.getActiveSentence(construction.getConstructionIndices(), exerciseSettings.getPlainText(), construction.getConstruction());
                        if (activeSentence != null) {
                            construction.setConstructionIndices(activeSentence.second);
                            brackets.add(activeSentence.first);
                        } else {
                            constructionsToRemove.add(construction);
                        }
                    } else {
                    	LemmatizedVerbCluster lemmatizedVerb = nlpManager.getLemmatizedVerbConstruction(construction.getConstructionIndices(), true, true);
                        if(exerciseSettings.getBrackets().contains(BracketsProperties.LEMMA)) {
                            if(lemmatizedVerb != null) {
                                brackets.add(lemmatizedVerb.getLemmatizedCluster());
                            } else {
                                constructionsToRemove.add(construction);
                                break;
                            }
                        } else {
                        	if(lemmatizedVerb != null) {
                        		brackets.add(String.join(" ", lemmatizedVerb.getNonLemmatizedComponents()));
                        	}
                        }
                    }
                    
                    if(exerciseSettings.getBrackets().contains(BracketsProperties.TENSE)) {
                        String tense;
                        if(construction.getConstruction().toString().endsWith("PRESSMP")) {
                        	tense = "simple present";
                        } else if(construction.getConstruction().toString().endsWith("FUTSMP")) {
                        	tense = "future simple";
                        } else if(construction.getConstruction().toString().endsWith("PRESPRG")) {
                        	tense = "present progressive";
                        } else if(construction.getConstruction().toString().endsWith("PASTPRG")) {
                        	tense = "past progressive";
                        } else if(construction.getConstruction().toString().endsWith("FUTPRG")) {
                        	tense = "future progressive";
                        } else if(construction.getConstruction().toString().endsWith("FUTPERF")) {
                        	tense = "future perfect";
                        } else if(construction.getConstruction().toString().endsWith("PRESPERFPRG")) {
                        	tense = "present perfect progressive";
                        } else if(construction.getConstruction().toString().endsWith("PASTPERFPRG")) {
                        	tense = "past perfect progressive";
                        } else if(construction.getConstruction().toString().endsWith("FUTPERFPRG")) {
                        	tense = "future perfect progressive";
                        } else if(construction.getConstruction().toString().endsWith("PASTSMP")) {
                        	tense = "simple past";
                        } else if(construction.getConstruction().toString().endsWith("PRESPERF")) {
                        	tense = "present perfect";
                        } else {
                        	tense = "past perfect";
                        }
                        brackets.add(tense);
                    }
                    
                    if(exerciseSettings.getBrackets().contains(BracketsProperties.SENTENCE_TYPE)) {
                    	brackets.add(construction.getConstruction().toString().startsWith("PASSIVE") ? "passive" : "active");
                    }
                    
                    if(brackets.size() > 0) {
                    	construction.setBracketsText("(" + String.join(", ", brackets) + ")");
                    }
                } else if(exerciseSettings.getContentType().endsWith("Drag")) {
                	ArrayList<Pair<Integer, Integer>> components = nlpManager.getPassiveSentenceComponents(construction.getConstructionIndices());
                	if(components != null) {
                		recheckForOverlappingConstructions = true;
	                	for(int i = 1; i <= 3; i++) {
	                		if(components.get(i) != null) {
	                			if(exerciseSettings.getBrackets().contains(BracketsProperties.VERB_SPLITTING) && i == 2) {
	        	                    ArrayList<Pair<Integer, Integer>> parts = nlpManager.splitParticiple(components.get(i));
	        	                    if(parts != null) { // if the splitting wasn't successful, we keep it as entire cluster
	        	                        for (Pair<Integer, Integer> part : parts) {
	        	                            constructionsToAdd.add(new Construction(construction.getConstruction(), part));
	        	                        }
	        	                    }
	                        	} else {                			
	                        		constructionsToAdd.add(new Construction(construction.getConstruction(), components.get(i)));
	                        	}
	                        }
	                	}
                	}
                	                	                	
                    constructionsToRemove.add(construction);
                }
            } else if((construction.getConstruction().toString().startsWith("QUEST") ||
                            construction.getConstruction().toString().startsWith("STMT") ||
                            construction.getConstruction().toString().startsWith("PAST") ||
                            construction.getConstruction().toString().startsWith("PRES")) &&
                    exerciseSettings.getContentType().equals("FiB")) {
                ArrayList<String> brackets = new ArrayList<>();
                LemmatizedVerbCluster lemmatizedVerb = nlpManager.getLemmatizedVerbConstruction(construction.getConstructionIndices(), true, false);
                if(exerciseSettings.getBrackets().contains(BracketsProperties.LEMMA)) {
                    if(lemmatizedVerb != null) {
                    	String lemmaCluster = lemmatizedVerb.getLemmatizedCluster();
                    	if(construction.getConstruction().toString().contains("_NEG_")) {
                    		// remove simple negation if contained in lemmas
                    		lemmaCluster = lemmaCluster.replaceAll(" n[o']t ", " ");
                    		if(lemmaCluster.startsWith("not ")) {
                    			lemmaCluster = lemmaCluster.substring(3);
                    		}
                    		if(lemmaCluster.endsWith(" not")) {
                    			lemmaCluster = lemmaCluster.substring(0, lemmaCluster.length() - 4);
                    		} else if(lemmaCluster.endsWith("n't")) {
                    			lemmaCluster = lemmaCluster.substring(0, lemmaCluster.length() - 3);
                    		}
                    	}
                    	if(!lemmaCluster.trim().equals("")) {
                    		brackets.add(lemmaCluster);
                    	}
                    } else {
                        constructionsToRemove.add(construction);
                        break;
                    }
                } else {
                	if(lemmatizedVerb != null) {
                		String lemmaCluster = String.join(" ", lemmatizedVerb.getNonLemmatizedComponents());
                		if(!lemmaCluster.trim().equals("")) {
                			brackets.add(lemmaCluster);
                		}
                	}
                }
                if(construction.getConstruction().toString().contains("QUEST")) {
                    brackets.add("interrog");
                }
                if(construction.getConstruction().toString().contains("_NEG_")) {
                    brackets.add("neg");
                }
                if((construction.getConstruction().toString().startsWith("PAST") || construction.getConstruction().toString().startsWith("PRES")) &&
                        exerciseSettings.getBrackets().contains(BracketsProperties.TENSE)) {
                    String tense = construction.getConstruction().toString().startsWith("PASTSMP") ? "simple past" :
                            construction.getConstruction().toString().startsWith("PRESPERF") ? "present perfect" : "past perfect";
                    brackets.add(tense);
                }
                if(brackets.size() > 0) {
                	construction.setBracketsText("(" + String.join(", ", brackets) + ")");
                }
            } else if((construction.getConstruction() == DetailedConstruction.WHICH ||
            		construction.getConstruction() == DetailedConstruction.WHO ||
            		construction.getConstruction() == DetailedConstruction.THAT ||
            		construction.getConstruction() == DetailedConstruction.OTHERPRN) &&
            		exerciseSettings.getContentType().equals("MultiDrag")) {
            	ArrayList<Pair<Integer,Integer>> components = nlpManager.getRelativeClauseComponents(construction.getConstructionIndices());
            	for(Pair<Integer, Integer> component : components) {
            		if(!exerciseSettings.getPlainText().substring(component.first, component.second).matches("[\\p{Punct}\\s\\h]*")) {
            			constructionsToAdd.add(new Construction(construction.getConstruction(), component));
            		}
            	}
            	constructionsToRemove.add(construction);
            	recheckForOverlappingConstructions = true;
            }
        }

        for(Construction construction : constructionsToRemove) {
            exerciseSettings.getConstructions().remove(construction);
        }

        for(Construction construction : constructionsToAdd) {
            exerciseSettings.getConstructions().add(construction);
        }
                
        if(recheckForOverlappingConstructions) {
	        // Remove overlapping constructions (can occur e.g. with synthetic and analytic comparatives)
	        HashSet<Construction> activeConstructionsToRemove = new HashSet<>();
	        for(Construction construction : exerciseSettings.getConstructions()) {
	        	for(Construction otherConstruction : exerciseSettings.getConstructions()) {
	        		if(construction != otherConstruction && (Math.max(construction.getConstructionIndices().first, otherConstruction.getConstructionIndices().first) < 
    						Math.min(construction.getConstructionIndices().second, otherConstruction.getConstructionIndices().second))) {
	        					if(construction.getConstructionIndices().second - construction.getConstructionIndices().first > 
	        							otherConstruction.getConstructionIndices().second - otherConstruction.getConstructionIndices().first) {
	        						activeConstructionsToRemove.add(otherConstruction);
	        					} else {
	        						activeConstructionsToRemove.add(construction);
	        					}
	        		}
	
	        	}
	        }
        
	        for(Construction construction : activeConstructionsToRemove) {
	        	exerciseSettings.getConstructions().remove(construction);
	        }
        }
    }

    /**
     * compiles the text for brackets of conditional exercises.
     * @param exerciseSettings  The exercise settings
     * @param construction      The construction of the blank
     * @param isMain            <c>true</c> if the blank is in a main clause; otherwise <c>false</c>
     * @param brackets          The array into which to write the brackets components
     * @param nlpManager		The NLP manager
     */
    private boolean addBracketsToConditionals(ExerciseSettings exerciseSettings, Construction construction, boolean isMain, 
    		ArrayList<String> brackets, NlpManager nlpManager) {
        if (exerciseSettings.getContentType().equals("FiB")) {
        	LemmatizedVerbCluster lemmatizedVerb = nlpManager.getLemmatizedVerbConstruction(construction.getConstructionIndices(), false, false);
            if(exerciseSettings.getBrackets().contains(BracketsProperties.LEMMA)) {
                if(lemmatizedVerb != null) {
                    brackets.add(lemmatizedVerb.getLemmatizedCluster());
                } else {
                    return false;
                }
            } else {
            	if(lemmatizedVerb != null) {
            		brackets.add(String.join(" ", lemmatizedVerb.getNonLemmatizedComponents()));
            	}
            }
            
            if (exerciseSettings.getBrackets().contains(BracketsProperties.CONDITIONAL_TYPE)) {
                brackets.add(construction.getConstruction() == DetailedConstruction.CONDREAL ? "real" : "unreal");
            }
            if (isMain && exerciseSettings.getBrackets().contains(BracketsProperties.WILL)) {
                brackets.add(lemmatizedVerb.getModal());
            }
        }
        
        return true;
    }

}
