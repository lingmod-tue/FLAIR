package com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.Pair;

import edu.stanford.nlp.ling.CoreLabel;

public class ConstructionPreparer {

	/**
	 * Modifies construction indices in-place if necessary.
	 * @param exerciseSettings	The exercise settings
	 * @param parser			The Stanford CoreNLP parser
	 * @param generator			The Simple NLG generator
	 */
    public void prepareConstructions(ExerciseSettings exerciseSettings, NlpManager nlpManager) {
    	ArrayList<Construction> constructionsToAdd = new ArrayList<>();
        ArrayList<Construction> constructionsToRemove = new ArrayList<>();
        for(Construction construction : exerciseSettings.getConstructions()) {
            if(construction.getConstruction().toString().startsWith("COND")) {
                Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> clauses = nlpManager.getConditionalClauses(construction.getConstructionIndices());
                if(clauses != null) {                	
                    int r = 0;
                    if (exerciseSettings.getBrackets().contains(BracketsProperties.EITHER_CLAUSE)) {
                        r = new Random().nextInt(2) + 1;
                    }
                    
                    ArrayList<ConditionalConstruction> newConstructions = new ArrayList<>();
                    Pair<Integer, Integer> mainClauseConstructionIndices = null;
                    Pair<Integer, Integer> ifClauseConstructionIndices = null;

                    if (clauses.first != null && exerciseSettings.getContentType().equals("Select") || 
                    		exerciseSettings.getBrackets().contains(BracketsProperties.MAIN_CLAUSE) || r == 1) {
                        mainClauseConstructionIndices = nlpManager.extractVerbCluster(clauses.first);                        
                    }
                    if (clauses.second != null && exerciseSettings.getContentType().equals("Select") || 
                    		exerciseSettings.getBrackets().contains(BracketsProperties.IF_CLAUSE) || r == 2) {
                        ifClauseConstructionIndices = nlpManager.extractVerbCluster(clauses.second);                        
                    }
                    
                    if(mainClauseConstructionIndices != null) {
                        newConstructions.add(new ConditionalConstruction(construction.getConstruction(), mainClauseConstructionIndices, true, ifClauseConstructionIndices));
                    }
                    if(ifClauseConstructionIndices != null) {
                        newConstructions.add(new ConditionalConstruction(construction.getConstruction(), ifClauseConstructionIndices, false, mainClauseConstructionIndices));
                    }
                    
                    for(ConditionalConstruction newConstruction : newConstructions) {                    	
                    	// try to limit the construction to max. 30 characters
                    	if(exerciseSettings.getContentType().equals("SingleDrag") && 
                    			newConstruction.getConstructionIndices().second - newConstruction.getConstructionIndices().first > 30) {
                    		if(newConstruction.isMainClause()) {
                    			if(mainClauseConstructionIndices != null) {
                    				newConstruction.setConstructionIndices(mainClauseConstructionIndices);  
                    				newConstruction.setOriginalConstructionIndices(mainClauseConstructionIndices);  
                    			}
                    		} else {
                    			if(ifClauseConstructionIndices != null) {
                    				newConstruction.setConstructionIndices(ifClauseConstructionIndices);
                    				newConstruction.setOriginalConstructionIndices(ifClauseConstructionIndices);  
                    			}
                    		}
                    			
                			if(newConstruction.getConstructionIndices().second - newConstruction.getConstructionIndices().first > 30) {
            					CoreLabel mainVerb = nlpManager.getMainVerb(newConstruction.getConstructionIndices());
            					if(mainVerb != null) {
            						newConstruction.setConstructionIndices(new Pair<>(mainVerb.beginPosition(), mainVerb.endPosition()));
            						newConstruction.setOriginalConstructionIndices(new Pair<>(mainVerb.beginPosition(), mainVerb.endPosition()));
            					}
            				}
                    	}
                    	constructionsToAdd.add(newConstruction);
                    }
                }
                constructionsToRemove.add(construction);
            } else if(construction.getConstruction().toString().startsWith("ADJ") ||
                        construction.getConstruction().toString().startsWith("ADV")) {
                if (exerciseSettings.getContentType().equals("Mark")) {
                    //TODO: if we decide to allow the client to specify whether to split synthetic forms for mark, we have to do that here
                } else if(exerciseSettings.getContentType().equals("SingleDrag") && construction.getConstructionIndices().second - construction.getConstructionIndices().first > 30) {                	
					Pair<Integer,Integer> mainComparison = nlpManager.getMainComparison(construction.getConstructionIndices());
					if(mainComparison != null) {
						construction.setConstructionIndices(mainComparison);
						construction.setOriginalConstructionIndices(mainComparison);
					}
            	}
            } else if(construction.getConstruction().toString().startsWith("PASSIVE") ||
                    construction.getConstruction().toString().startsWith("ACTIVE")) {
                if(exerciseSettings.getContentType().equals("FiB") && exerciseSettings.getBrackets().contains(BracketsProperties.ACTIVE_SENTENCE)) {
                	Pair<Integer, Integer> sentenceIndices = nlpManager.getSentenceIndices(construction.getConstructionIndices());
                    if (sentenceIndices != null) {
                        construction.setConstructionIndices(sentenceIndices);
						construction.setOriginalConstructionIndices(sentenceIndices);
                    } else {
                        constructionsToRemove.add(construction);
                    }
                } else if(exerciseSettings.getContentType().endsWith("Drag")) {
                	ArrayList<Pair<Integer, Integer>> components = nlpManager.getPassiveSentenceComponents(construction.getConstructionIndices());
                	if(components != null) {
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
            } else if((construction.getConstruction().toString().startsWith("PAST") || construction.getConstruction().toString().startsWith("PRES")) && 
                    exerciseSettings.getContentType().equals("SingleDrag") && 
                    construction.getConstructionIndices().second - construction.getConstructionIndices().first > 30) {                	
            	CoreLabel mainVerb = nlpManager.getMainVerb(construction.getConstructionIndices());
				if(mainVerb != null) {
					construction.setConstructionIndices(new Pair<>(mainVerb.beginPosition(), mainVerb.endPosition()));
					construction.setOriginalConstructionIndices(new Pair<>(mainVerb.beginPosition(), mainVerb.endPosition()));
				}
        	}
            else if((construction.getConstruction() == DetailedConstruction.WHICH ||
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
            }
        }

        for(Construction construction : constructionsToRemove) {
            exerciseSettings.getConstructions().remove(construction);
        }

        for(Construction construction : constructionsToAdd) {
            exerciseSettings.getConstructions().add(construction);
        }
                
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
