package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Map.Entry;

import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.Blank;
import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.Fragment;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ComparisonSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConditionalConstruction;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.LemmatizedVerbCluster;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.TenseSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DataStructures.Distractor;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DataStructures.ExerciseComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DataStructures.TargetConstruction;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.DistractorProperties;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.Pair;

import edu.stanford.nlp.ling.CoreLabel;

public abstract class DistractorsFromTextGenerator extends DistractorGenerator {
	
	public DistractorsFromTextGenerator(NlpManager nlpManager) {
		this.nlpManager = nlpManager;
	}
	
	protected final NlpManager nlpManager;

    @Override
	public void generateDistractors(ExerciseSettings exerciseSettings, ExerciseComponents exerciseComponents) {
    	/*ArrayList<Construction> usedConstructions = new ArrayList<>();
		ArrayList<Integer> constructionsToRemove = new ArrayList<>();
		ArrayList<String> usedTargets = new ArrayList<>();

        if(exerciseSettings.getContentType().equals(ExerciseType.SINGLE_CHOICE)) {
        	for(Fragment fragment : fragments) {
                for(Blank blank : fragment.getBlanksBoundaries()) {
                	Construction construction = blank.getConstruction();
                	if(construction != null) {
	                	String constructionText = exerciseSettings.getPlainText().substring(construction.getConstructionIndices().first, construction.getConstructionIndices().second);
	                    HashSet<String> options = new HashSet<>();
	                    HashSet<String> incorrectFormOptions = new HashSet<>();
	
	                    String name = construction.getConstruction().toString();
	                    if(name.startsWith("COND")) {
	                        //TODO: call CondtitionalDistractorGenerator
	                    } else if(name.startsWith("ADJ") || name.startsWith("ADV")) {
	                        //TODO: comparison generator
	                    } else if(name.startsWith("QUEST") || name.startsWith("STMT")){
	                        //TODO: present
	                    } else if(name.startsWith("PAST") || name.startsWith("PRES")) {
	                        //TODO past
	                    } else if(construction.getConstruction() == DetailedConstruction.WHO ||
	                            construction.getConstruction() == DetailedConstruction.WHICH ||
	                            construction.getConstruction() == DetailedConstruction.THAT ||
	                            construction.getConstruction() == DetailedConstruction.OTHERPRN) {
	                        //TODO: relatives
	                    }
	
	                    removeCorrectForm(constructionText, options);
	                    removeCorrectForm(constructionText, incorrectFormOptions);
	
	                    if(options.size() == 0 && incorrectFormOptions.size() == 0) {
	                        constructionsToRemove.add(blank.getConstructionIndex());
	                    } else {
	                    	ArrayList<Pair<String, Boolean>> distractors = new ArrayList<>();
	                    	ArrayList<String> incorrectDistractors = capitalize(new ArrayList<>(incorrectFormOptions), constructionText);
	                        for(String incorrectDistractor : incorrectDistractors) {
	                        	distractors.add(new Pair<>(incorrectDistractor.replaceAll("[\\s\\h\\v]+", " ").trim(), true));
	                        }
	                        ArrayList<String> correctDistractors = capitalize(new ArrayList<>(options), constructionText);
	                        for(String correctDistractor : correctDistractors) {
	                        	distractors.add(new Pair<>(correctDistractor.replaceAll("[\\s\\h\\v]+", " ").trim(), false));
	                        }
	                    	
	                        // Add the distractors to the settings
	                        construction.setDistractors(distractors);
	                    }
                	}
                }
        	}             
        } else if(exerciseSettings.getContentType().equals(ExerciseType.DRAG_SINGLE)) {
        	//DD Single
        }  else if(exerciseSettings.getContentType().equals(ExerciseType.DRAG_MULTI)) {
        	// We won't have feedback for the draggables of possible merged sentences, but in those cases, the standard feedback 'wrong word' should be fine
        	// we set the values of the other draggables of the sub-exercise as "distractors"
        	ArrayList<Construction> usedConstructionList = new ArrayList<>();
        	for(Fragment fragment : fragments) {
                for(Blank blank : fragment.getBlanksBoundaries()) {
                	Construction construction = blank.getConstruction();
                	if(construction != null) {
                		usedConstructionList.add(construction);
                	}
                }
        	}
        	        	
        	for(Fragment fragment : fragments) {
                for(Blank blank : fragment.getBlanksBoundaries()) {
                	Construction construction = blank.getConstruction();
                	if(construction != null) {
        				String text = exerciseSettings.getPlainText().substring(construction.getConstructionIndices().first, construction.getConstructionIndices().second);
                		for(Construction otherConstruction : construction.getSentenceConstructions()) {
                			if(usedConstructionList.contains(otherConstruction)) {
                				String distractor = exerciseSettings.getPlainText().substring(otherConstruction.getConstructionIndices().first, otherConstruction.getConstructionIndices().second);
                        		if(!text.equals(distractor)) {
                        			construction.getDistractors().add(new Pair<>(distractor, false));
                        		}
                			}
                		}
                		if(construction.getDistractors().size() < 1) {
	                        constructionsToRemove.add(blank.getConstructionIndex());
                		} 
                	}
                }
        	}
        } else if(exerciseSettings.getContentType().equals(ExerciseType.MEMORY)) {
        	for(Fragment fragment : fragments) {
                for(Blank blank : fragment.getBlanksBoundaries()) {
                	Construction construction = blank.getConstruction();
                	if(construction != null) {
                		String name = construction.getConstruction().toString();
                		String lemma = null;
	                    if(name.startsWith("ADJ") || name.startsWith("ADV")) {
	                        lemma = nlpManager.getLemmaOfComparison(construction.getConstructionIndices());
						} else if(name.startsWith("QUEST") || name.startsWith("STMT") ||
								name.startsWith("PAST") || name.startsWith("PRES")){
							Pair<String, String> lemmaSubject =
	                                nlpManager.getVerbLemma(construction.getConstructionIndices(), false);	
							if(lemmaSubject != null) {
								lemma = lemmaSubject.first;
							}
						}
	                    
	                    // We cannot use ambiguous lemmas
	                	String constructionText = exerciseSettings.getPlainText().substring(construction.getConstructionIndices().first, construction.getConstructionIndices().second);
	                    if(usedTargets.contains(lemma) || usedTargets.contains(constructionText)) {
	                    	lemma = null;
	                    }
	                    
	                    if(lemma == null || lemma.isEmpty()) {
	                    	constructionsToRemove.add(blank.getConstructionIndex());
	                    } else {
	                    	construction.getDistractors().add(new Pair<>(lemma, false));
	                    	usedTargets.add(lemma);
	                    	usedTargets.add(constructionText);
	                    }
                	}
                }
        	}
        }
        
        if(!exerciseSettings.getContentType().equals(ExerciseType.DRAG_SINGLE)) {
	    	for(Fragment fragment : fragments) {
	    		ArrayList<Blank> blanksToRemove = new ArrayList<>();
	    		for(Blank blank : fragment.getBlanksBoundaries()) {
    				boolean isUsed = true;
    		        for(int constructionToRemove : constructionsToRemove) {
            			if(blank.getConstructionIndex() == constructionToRemove) {
            				blanksToRemove.add(blank);
            				isUsed = false;
            			}
    		        }
    		        if(blank.getConstruction() != null && isUsed) {
    		        	Pair<Integer, Integer> constructionIndices = blank.getConstruction().getConstructionIndices();
    		        	String constructionText = exerciseSettings.getPlainText().substring(constructionIndices.first, constructionIndices.second);
        				blank.getConstruction().setConstructionText(constructionText);
    		        	usedConstructions.add(blank.getConstruction());
    		        }
	    		}
	    		for(Blank blank : blanksToRemove) {
	    			fragment.getBlanksBoundaries().remove(blank);
	    		}
	    	}
        }
        
        return usedConstructions;*/
	}
    
    
	protected abstract Pair<HashSet<String>, HashSet<String>> generateSCDistractors(Construction construction, ExerciseType exerciseType,
			ArrayList<DistractorProperties> distractorProperties, String plainText);
	
	protected void generateDDSingleDistractors(
			ArrayList<TargetConstruction> constructions,
			String plainText) {
		// we set the values of the other draggables as "distractors"
    	ArrayList<String> distractors = new ArrayList<>();
    	for(TargetConstruction construction : constructions) {
        	String text = plainText.substring(construction.getStartIndex(), construction.getEndIndex());
    		distractors.add(text);
    	}
    	
    	if(distractors.size() < 1) {
    		// we cannot generate an exercise for Drag & Drop with less than 2 targets
    		constructions.clear();
    	}
    	
    	HashSet<String> uniqueDistractors = new HashSet<>(distractors);
    	for(TargetConstruction construction : constructions) {
        	String text = plainText.substring(construction.getStartIndex(), construction.getEndIndex());
    		for(String distractor : uniqueDistractors) {
        		if(!text.equals(distractor)) {
        			construction.getDistractors().add(new Distractor(distractor, false));
        		}
    		}
    	}   
	}
	
    /**
     * Removes the correct form from the incorrect options
     * @param correctFormText   The text of the correct form
     * @param options           The incorrect options
     */
    private void removeCorrectForm(String correctFormText, HashSet<String> options) {
        String correctOption = null;
        for(String option : options) {
            if(option.equals(correctFormText)) {
                correctOption = option;
            }
        }
        if(correctOption != null) {
            options.remove(correctOption);
        }
    }
    
    /**
     * Capitalizes all distractors if the correct form starts with an uppercase character.
     * @param options		The distractors
     * @param correctForm	The correct form
     * @return				The capitalized distractors if the correct form is capitalized, otherwise the original distractors
     */
    private ArrayList<String> capitalize(ArrayList<String> options, String correctForm) {
       	if(Character.isUpperCase(correctForm.charAt(0))) {
            ArrayList<String> distractors = new ArrayList<>();
            for(String option: options) {
            	distractors.add(option.substring(0, 1).toUpperCase() + option.substring(1));
            }
            return distractors;
    	} else {
    		return new ArrayList<>(options);
    	}
    }
	
}
