package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.Blank;
import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.Fragment;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseType;

public class ClozeManager {

	/**
	 * Generates brackets texts for FiB tasks.
	 * @param exerciseSettings	The exercise settings
	 * @param parser			The Stanford CoreNLP parser
	 * @param generator			The Simple NLG generator
	 */
    public void prepareBlanks(ExerciseSettings exerciseSettings, NlpManager nlpManager, ArrayList<Fragment> fragments) {
        if(exerciseSettings.getContentType().equals(ExerciseType.FIB)){
	        ArrayList<Integer> constructionsToRemove = new ArrayList<>();
	        ArrayList<String> lemmas = new ArrayList<>();
	
	        for(Fragment fragment : fragments) {
	        	for(Blank blank : fragment.getBlanksBoundaries()) {
                	Construction construction = blank.getConstruction();
                	if(construction != null) {
		                ArrayList<String> brackets = new ArrayList<>();
	
			            if(construction.getConstruction().toString().startsWith("COND")) {     
		                	LemmatizedVerbCluster lemmatizedVerb = nlpManager.getLemmatizedVerbConstruction(construction.getConstructionIndices(), false, false);
		                	if(exerciseSettings.getBrackets().contains(BracketsProperties.LEMMA)) {
		                        if(lemmatizedVerb != null) {
		                        	String lemma = lemmatizedVerb.getLemmatizedCluster();
		                        	if(exerciseSettings.getBrackets().contains(BracketsProperties.DISTRACTOR_LEMMA)) {
		                        		lemmas.add(lemma);
		                        		brackets.add(generateDistractorLemma(lemma));
		                        	} else {
			                            brackets.add(lemma);
		                        	}
		                        } else {
		                        	constructionsToRemove.add(blank.getConstructionIndex());
		                        	break;
		                        }
		                    } else {
		                    	if(lemmatizedVerb != null) {
		                    		brackets.add(String.join(" ", lemmatizedVerb.getNonLemmatizedComponents()));
		                    	}
		                    }
		                    
		                    if (exerciseSettings.getBrackets().contains(BracketsProperties.CONDITIONAL_TYPE)) {
		                        brackets.add(construction.getConstruction() == DetailedConstruction.CONDREAL ? "real" : "unreal");
		                    }
		                    if (((ConditionalConstruction)construction).isMainClause() && exerciseSettings.getBrackets().contains(BracketsProperties.WILL)) {
		                        brackets.add(lemmatizedVerb.getModal());
		                    }  
			            } else if(construction.getConstruction().toString().startsWith("ADJ") ||
			                        construction.getConstruction().toString().startsWith("ADV")) {
		                    if(exerciseSettings.getBrackets().contains(BracketsProperties.LEMMA)) {
		                        String lemma = nlpManager.getLemmaOfComparison(construction.getConstructionIndices());
		                        if(lemma != null) {
		                            if(exerciseSettings.getBrackets().contains(BracketsProperties.DISTRACTOR_LEMMA)) {
		                        		lemmas.add(lemma);
		                        		brackets.add(generateDistractorLemma(lemma));
		                        	} else {
			                            brackets.add(lemma);
		                        	}
		                        } else {
		                            constructionsToRemove.add(blank.getConstructionIndex());
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
			            } else if(construction.getConstruction().toString().startsWith("PASSIVE") ||
			                    construction.getConstruction().toString().startsWith("ACTIVE")) {
		                    if (exerciseSettings.getBrackets().contains(BracketsProperties.ACTIVE_SENTENCE)) {
		                        String activeSentence = nlpManager.getActiveSentence(construction.getConstructionIndices(), exerciseSettings.getPlainText(), construction.getConstruction());
		                        if (activeSentence != null) {
		                            brackets.add(activeSentence);
		                        } else {
		                            constructionsToRemove.add(blank.getConstructionIndex());
		                            break;
		                        }
		                    } else {
		                    	LemmatizedVerbCluster lemmatizedVerb = nlpManager.getLemmatizedVerbConstruction(construction.getConstructionIndices(), true, true);
		                        if(exerciseSettings.getBrackets().contains(BracketsProperties.LEMMA)) {
		                            if(lemmatizedVerb != null) {
		                                brackets.add(lemmatizedVerb.getLemmatizedCluster());
		                            } else {
		                                constructionsToRemove.add(blank.getConstructionIndex());
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
			            } else if(construction.getConstruction().toString().startsWith("QUEST") ||
			                            construction.getConstruction().toString().startsWith("STMT") ||
			                            construction.getConstruction().toString().startsWith("PAST") ||
			                            construction.getConstruction().toString().startsWith("PRES")) {
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
			                    		if(exerciseSettings.getBrackets().contains(BracketsProperties.DISTRACTOR_LEMMA)) {
			                    			lemmas.add(lemmaCluster);
			                        		brackets.add(generateDistractorLemma(lemmaCluster));
			                        	} else {
				                            brackets.add(lemmaCluster);
			                        	}
			                    	}
			                    } else {
			                        constructionsToRemove.add(blank.getConstructionIndex());
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
			                if((construction.getConstruction().toString().startsWith("PAST") || construction.getConstruction().toString().startsWith("PRES"))) {
			                	if(exerciseSettings.getBrackets().contains(BracketsProperties.TENSE)) {
				                	String tense = construction.getConstruction().toString().startsWith("PASTSMP") ? "simple past" :
				                            construction.getConstruction().toString().startsWith("PRESPERF") ? "present perfect" : "past perfect";
				                    brackets.add(tense);
			                	}
			                    
			                    if(exerciseSettings.getBrackets().contains(BracketsProperties.PROGRESSIVE)) {
			                        String prog = construction.getConstruction().toString().contains("PRG_") ? "progressive" : "simple";			                       
			                        brackets.add(prog);
			                    }
			                }
			            } 
	
		                if(brackets.size() > 0) {
		                	construction.setBracketsText("(" + String.join(", ", brackets) + ")");
		                }
			        }	
	        	}
	        }
	        
	        for(int constructionToRemove : constructionsToRemove) {
            	for(Fragment fragment : fragments) {
            		ArrayList<Blank> blanksToRemove = new ArrayList<>();
            		for(Blank blank : fragment.getBlanksBoundaries()) {
            			if(blank.getConstructionIndex() == constructionToRemove) {
            				blanksToRemove.add(blank);
            			}
            		}
            		for(Blank blank : blanksToRemove) {
            			fragment.getBlanksBoundaries().remove(blank);
            		}
            	}
            }  	
	        
	        if(exerciseSettings.getBrackets().contains(BracketsProperties.DISTRACTOR_LEMMA)) {
	        	for(Fragment fragment : fragments) {
		        	for(Blank blank : fragment.getBlanksBoundaries()) {
	                	Construction construction = blank.getConstruction();

		        		if(construction != null && construction.getBracketsText() != null) {
		                	String distractorLemma = "";
			        		if(lemmas.size() > 1) {
			        			while(distractorLemma.equals("") || 
			        					construction.getBracketsText().contains(distractorLemma + "|<distractor_placeholder>") || 
			        					construction.getBracketsText().contains("<distractor_placeholder>|" + distractorLemma)) {
				        			Collections.shuffle(lemmas);
				        			distractorLemma = lemmas.get(0);
			        			}
			        			
			                	construction.setBracketsText(construction.getBracketsText().replace("<distractor_placeholder>", distractorLemma));
			        		} else {
			                	construction.setBracketsText(construction.getBracketsText().replace("|<distractor_placeholder>", "").replace("<distractor_placeholder>|", ""));
			        		}
		        		}
		        	}
	        	}
        	}
        }
    }
    
    private String generateDistractorLemma(String lemma) {
    	int order = new Random().nextInt(2);
		if(order == 0) {
			return "<distractor_placeholder>|" + lemma;
		} else {
			return lemma + "|<distractor_placeholder>";
		}
    }

}
