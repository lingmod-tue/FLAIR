package com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.Blank;
import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.Fragment;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.DistractorProperties;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.Pair;

import edu.stanford.nlp.ling.CoreLabel;

public class DistractorManager {

	/**
     * Generates distractors for Single Choice exercises.
     * @param exerciseSettings  The exercise settings
     */
    public ArrayList<String> generateDistractors(ExerciseSettings exerciseSettings, NlpManager nlpManager, ArrayList<Fragment> fragments) {
    	ArrayList<String> usedConstructions = new ArrayList<>();
		ArrayList<Integer> constructionsToRemove = new ArrayList<>();

        if(exerciseSettings.getContentType().equals("Select")) {
        	for(Fragment fragment : fragments) {
                for(Blank blank : fragment.getBlanksBoundaries()) {
                	Construction construction = blank.getConstruction();
                	if(construction != null) {
	                	String constructionText = exerciseSettings.getPlainText().substring(construction.getOriginalConstructionIndices().first, construction.getOriginalConstructionIndices().second);
	                    HashSet<String> options = new HashSet<>();
	                    HashSet<String> incorrectFormOptions = new HashSet<>();
	
	                    String name = construction.getConstruction().toString();
	                    if(name.startsWith("COND")) {
	                        TenseSettings ownClauseSettings = nlpManager.getConditionalClauseSpecifics(construction.getConstructionIndices());
	                        
	                        if (exerciseSettings.getDistractors().contains(DistractorProperties.WRONG_CLAUSE)) {
	                            TenseSettings otherClauseSettings = null;
	                        	if (((ConditionalConstruction)construction).getOtherClauseIndices() != null) {
	                                otherClauseSettings = nlpManager.getConditionalClauseSpecifics(((ConditionalConstruction)construction).getOtherClauseIndices());
	                            }
	                        	
	                            if(ownClauseSettings != null && otherClauseSettings != null) {
	                                options.add(nlpManager.generateCorrectForm(new TenseSettings(ownClauseSettings.getLemma(), ownClauseSettings.isInterrogative(),
	                                		ownClauseSettings.isNegated(), ownClauseSettings.isThirdSingular(), ownClauseSettings.getSubject(), otherClauseSettings.getTense(),
	                                		otherClauseSettings.isProgressive(), otherClauseSettings.isPerfect(), otherClauseSettings.getModal())));
	                            }
	                        }
	                        if (exerciseSettings.getDistractors().contains(DistractorProperties.WRONG_CONDITIONAL) && ownClauseSettings != null) {
	                            if(((ConditionalConstruction)construction).isMainClause()) {
	                                if(construction.getConstruction() == DetailedConstruction.CONDREAL) {
	                                    if (ownClauseSettings.getModal() != null) {
	                                    	ownClauseSettings.setModal(nlpManager.getUnrealModal(ownClauseSettings.getModal()));
	                                    } else {
	                                    	ownClauseSettings.setModal("would");
	                                    	ownClauseSettings.setPerfect(!ownClauseSettings.getTense().equals("present"));
	                                    }
	                                } else {
	                                    if(ownClauseSettings.getModal() != null) {
	                                    	ownClauseSettings.setModal(nlpManager.getRealModal(ownClauseSettings.getModal()));
	                                    } else {
	                                    	ownClauseSettings.setPerfect(false);
	                                        if(!ownClauseSettings.getTense().equals("past")) {
	                                        	ownClauseSettings.setModal("will");
	                                        }
	                                    }
	                                }
	
	                                options.add(nlpManager.generateCorrectForm(ownClauseSettings));
	                            } else {
	                                if(construction.getConstruction() == DetailedConstruction.CONDREAL) {
	                                    if (ownClauseSettings.getTense().equals("past")) {
	                                    	ownClauseSettings.setPerfect(true);
	                                    } else {
	                                    	ownClauseSettings.setTense("past");
	                                    	ownClauseSettings.setPerfect(false);
	                                    }
	                                } else {
	                                	ownClauseSettings.setPerfect(false);
	                                    if(!(ownClauseSettings.getTense().equals("past") && ownClauseSettings.isPerfect())) {
	                                    	ownClauseSettings.setTense("present");
	                                    }
	                                }
	
	                                options.add(nlpManager.generateCorrectForm(ownClauseSettings));
	                            }
	                        }
	                    } else if(name.startsWith("ADJ") || name.startsWith("ADV")) {
	                        boolean isComparative = construction.getConstruction().toString().contains("_COMP_");
	                        boolean isSynthetic = construction.getConstruction().toString().endsWith("SYN");
	                        boolean isAdjective = name.startsWith("ADJ");
	                        String lemma = nlpManager.getLemmaOfComparison(construction.getOriginalConstructionIndices());
	
	                        ArrayList<Pair<Boolean, Boolean>> parameterConstellations = new ArrayList<>();
	                        parameterConstellations.add(new Pair<>(isSynthetic, isComparative));
	
	                        if(exerciseSettings.getDistractors().contains(DistractorProperties.OTHER_FORM)) {
	                        	ArrayList<Pair<Boolean, Boolean>> newParameterConstellations = new ArrayList<>();
	                            for(Pair<Boolean, Boolean> parameterConstellation : parameterConstellations) {
	                                newParameterConstellations.add(new Pair<>(parameterConstellation.first, !isComparative));
	                            }
	                            parameterConstellations.addAll(newParameterConstellations);
	                        }
	                        if(exerciseSettings.getDistractors().contains(DistractorProperties.OTHER_VARIANT)) {
	                        	ArrayList<Pair<Boolean, Boolean>> newParameterConstellations = new ArrayList<>();
	                            for(Pair<Boolean, Boolean> parameterConstellation : parameterConstellations) {
	                                newParameterConstellations.add(new Pair<>(!isSynthetic, parameterConstellation.second));
	                            }
	                            parameterConstellations.addAll(newParameterConstellations);
	                        }
	
	                        for(int i = 0; i < parameterConstellations.size(); i++) {
	                            Pair<Boolean, Boolean> parameterConstellation = parameterConstellations.get(i);
	                            if(i != 0) {    // don't calculate the correct form, we already have it
	                                options.add(nlpManager.generateCorrectForm(new ComparisonSettings(parameterConstellation.first,
	                                        parameterConstellation.second, lemma, isAdjective)));
	                            }
	                            if(exerciseSettings.getDistractors().contains(DistractorProperties.INCORRECT_FORMS)) {
	                                incorrectFormOptions.addAll(nlpManager.generateIncorrectForms(new ComparisonSettings(parameterConstellation.first,
	                                        parameterConstellation.second, lemma, constructionText)));
	                            }
	                        }
	                    } else if(name.startsWith("QUEST") || name.startsWith("STMT")){
	                        boolean is3Sg = construction.getConstruction().toString().endsWith("_3");
	                        CoreLabel mainVerb = nlpManager.getMainVerb(construction.getOriginalConstructionIndices());
	                        
	                        if(mainVerb != null) {
	    	                    ArrayList<Boolean> parameterConstellations = new ArrayList<>();
	    	                    parameterConstellations.add(is3Sg);
	    	
	    	                    if(exerciseSettings.getDistractors().contains(DistractorProperties.WRONG_SUFFIX_USE)) {
	    	                        parameterConstellations.add(!is3Sg);
	    	                    }
	    	                    
	    	                    String subject = nlpManager.getSubject(mainVerb.beginPosition(), construction.getOriginalConstructionIndices());
	    	                    if(subject == null) {
	    	                    	subject = is3Sg ? "he" : "they";
	    	                    }
	    	                    	
	    	                    for(int i = 0; i < parameterConstellations.size(); i++) {
	    	                        boolean parameterConstellation = parameterConstellations.get(i);
	    	                        if(i != 0) {    // don't calculate the correct form, we already have it
	    	                        	String option = nlpManager.generateCorrectForm(new TenseSettings(mainVerb.lemma(), false,
	    	                                    false, parameterConstellation, subject, "present",
	    	                                    false, false));
	    	                        	option = exerciseSettings.getPlainText().substring(construction.getOriginalConstructionIndices().first, mainVerb.beginPosition()) +
	    	                        			option + exerciseSettings.getPlainText().substring(mainVerb.endPosition(), construction.getOriginalConstructionIndices().second);	                        			
	    	                            options.add(option);
	    	                        }
	    	                        if(exerciseSettings.getDistractors().contains(DistractorProperties.INCORRECT_FORMS)) {
	    	                        	HashSet<String> o = nlpManager.generateIncorrectForms(new TenseSettings(mainVerb.lemma(), false,
	    	                                    false, parameterConstellation, subject, "present",false, false));
	    	                            for(String option : o) {
	    	                            	option = exerciseSettings.getPlainText().substring(construction.getOriginalConstructionIndices().first, mainVerb.beginPosition()) +
	    		                        			option + exerciseSettings.getPlainText().substring(mainVerb.endPosition(), construction.getOriginalConstructionIndices().second);	                        			
	    	                            	incorrectFormOptions.add(option);
	    	                            }	                        	
	    	                        }
	    	                    }
	                        } 
	                    } else if(name.startsWith("PAST") || name.startsWith("PRES")) {
	                        boolean isPerfect = construction.getConstruction().toString().contains("PERF_");
	                        String tense = construction.getConstruction().toString().startsWith("PAST") ? "past" : "present";
	                        boolean isInterrogative = construction.getConstruction().toString().contains("_QUEST_");
	                        Pair<String, String> lemma =
	                                nlpManager.getVerbLemma(construction.getOriginalConstructionIndices(), isInterrogative);
	                        boolean isNegated = construction.getConstruction().toString().contains("_NEG_");
	
	                        if(lemma != null) {
	    	                    ArrayList<Pair<Boolean, String>> parameterConstellations = new ArrayList<>();
	    	
	    	                    if(exerciseSettings.getDistractors().contains(DistractorProperties.OTHER_PAST)) {
	    	                        parameterConstellations.add(new Pair<>(true, "present"));
	    	                        parameterConstellations.add(new Pair<>(false, "present"));
	    	                        parameterConstellations.add(new Pair<>(true, "past"));
	    	                    } else {
	    	                        parameterConstellations.add(new Pair<>(isPerfect, tense));
	    	                    }
	    	                    if(exerciseSettings.getDistractors().contains(DistractorProperties.OTHER_TENSE)) {
	    	                        parameterConstellations.add(new Pair<>(true, "future"));
	    	                        parameterConstellations.add(new Pair<>(false, "future"));
	    	                        parameterConstellations.add(new Pair<>(false, "past"));
	    	                    }
	    	                    
	    	                    // get other components excluding the verb and negation
	    	                    String otherComponents = "";
	    	                    LemmatizedVerbCluster verbCluster = nlpManager.getLemmatizedVerbConstruction(construction.getOriginalConstructionIndices(), true, false);
	    	                    if(verbCluster != null) {
	    	                    	otherComponents = String.join(" ", verbCluster.getNonLemmatizedComponents());
	    	                    	otherComponents.replaceAll(" n[o']t ", " ");
	    	                    	if(otherComponents.startsWith("not ")) {
	    	                    		otherComponents = otherComponents.substring(3);
	    	                    	}
	    	                    	if(otherComponents.endsWith(" not")){
	    	                    		otherComponents = otherComponents.substring(0, otherComponents.length() - 4);
	    	                    	} else if(otherComponents.endsWith("n't")){
	    	                    		otherComponents = otherComponents.substring(0, otherComponents.length() - 3);
	    	                    	}
	    	                    	
	    	                    	if(!otherComponents.equals("")) {
	    	                    		otherComponents = " " + otherComponents;
	    	                    	}
	    	                    }
	    	                    
	    	                    for (Pair<Boolean, String> parameterConstellation : parameterConstellations) {
	    	                        for (int j = 0; j <= 1; j++) {
	    	                            // We don't know if we have a 3rd pers. sing. form or not, so we calculate both forms also for the correct parameter settings
	    	                            options.add(nlpManager.generateCorrectForm(new TenseSettings(lemma.first, isInterrogative,
	    	                                    isNegated, j == 0, lemma.second, parameterConstellation.second,
	    	                                    false, parameterConstellation.first)) + otherComponents);
	    	                            if (exerciseSettings.getDistractors().contains(DistractorProperties.INCORRECT_FORMS)) {
	    	                            	HashSet<String> incorrectForms = nlpManager.generateIncorrectForms(new TenseSettings(lemma.first, isInterrogative,
	    	                                        isNegated, j == 0, lemma.second, parameterConstellation.second,
	    	                                        false, parameterConstellation.first));
	    	                            	for(String incorrectForm : incorrectForms) {
	    	                            		incorrectFormOptions.add(incorrectForm + otherComponents);
	    	                            	}
	    	                            }
	    	                        }
	    	                    }
	                        }
	                    } else if(construction.getConstruction() == DetailedConstruction.WHO ||
	                            construction.getConstruction() == DetailedConstruction.WHICH ||
	                            construction.getConstruction() == DetailedConstruction.THAT ||
	                            construction.getConstruction() == DetailedConstruction.OTHERPRN) {
	                        // We make sure to only include those pronouns as distractors which actually occur in the text
	                        for(Construction c : exerciseSettings.getConstructions()) {
	                        	String text = exerciseSettings.getPlainText().substring(c.getOriginalConstructionIndices().first, c.getOriginalConstructionIndices().second);
	                            options.add(text);
	                        }
	                    }
	
	                    removeCorrectForm(constructionText, options);
	                    removeCorrectForm(constructionText, incorrectFormOptions);
	
	                    if(options.size() == 0 && incorrectFormOptions.size() == 0) {
	                        constructionsToRemove.add(blank.getConstructionIndex());
	                    } else {
	                        ArrayList<String> incorrectOptions = new ArrayList<>(incorrectFormOptions);
	                        Collections.shuffle(incorrectOptions);
	                        if(options.size() > 0) {
	                            // We don't want too many incorrect forms, so we take half as many as correctly formed variants and only pad if necessary
	                            int incorrectFormsToAdd = options.size() / 2;
	                            for(int i = 0; i < incorrectFormsToAdd && incorrectOptions.size() > 0; i++) {
	                                int randomIndex = new Random().nextInt(incorrectOptions.size());
	                                String randomForm = incorrectOptions.get(randomIndex);
	                                int previousSize = options.size();
	                                options.add(randomForm);
	                                if(previousSize == options.size()) {    // the option had already been in the set
	                                    i--;
	                                }
	                                incorrectOptions.remove(randomForm);
	                            }
	                        }
	
	                        // Add incorrect forms until we have the necessary distractor number or no more options to add
	                        while(options.size() < exerciseSettings.getnDistractors() && incorrectOptions.size() > 0) {
	                            int randomIndex = new Random().nextInt(incorrectOptions.size());
	                            String randomForm = incorrectOptions.get(randomIndex);
	                            options.add(randomForm);
	                            incorrectOptions.remove(randomForm);
	                        }
	                        
	                        ArrayList<String> distractors = new ArrayList<>(options);
	                        while(distractors.size() > exerciseSettings.getnDistractors()) {
	                        	int index = new Random().nextInt(distractors.size());
	                        	distractors.remove(index);
	                        }
	
	                        distractors = capitalize(distractors, constructionText);                    
	                        
	                        // Add the distractors to the settings
	                        Collections.shuffle(distractors);
	                        construction.setDistractors(distractors);
	                    }
                	}
                }
        	}             
        } else if(exerciseSettings.getContentType().equals("SingleDrag")) {
        	// we set the values of the other draggables as "distractors"
        	ArrayList<String> distractors = new ArrayList<>();
        	for(Fragment fragment : fragments) {
                for(Blank blank : fragment.getBlanksBoundaries()) {
                	Construction construction = blank.getConstruction();
                	if(construction != null) {
                    	String text = exerciseSettings.getPlainText().substring(construction.getOriginalConstructionIndices().first, construction.getOriginalConstructionIndices().second);
                		distractors.add(text);
                	}
                }
        	}
        	
        	if(distractors.size() < 1) {
        		// we cannot generate an exercise for Drag & Drop with less than 2 targets
        		return new ArrayList<String>();
        	}
        	
        	HashSet<String> uniqueDistractors = new HashSet<>(distractors);
        	for(Fragment fragment : fragments) {
                for(Blank blank : fragment.getBlanksBoundaries()) {
                	Construction construction = blank.getConstruction();
                	if(construction != null) {
                		for(String distractor : uniqueDistractors) {
                			String text = exerciseSettings.getPlainText().substring(construction.getOriginalConstructionIndices().first, construction.getOriginalConstructionIndices().second);
                    		if(!text.equals(distractor)) {
                    			construction.getDistractors().add(distractor);
                    		}
            				usedConstructions.add(text);
                		}
                	}
                }
        	}
        }  else if(exerciseSettings.getContentType().equals("MultiDrag")) {
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
        				String text = exerciseSettings.getPlainText().substring(construction.getOriginalConstructionIndices().first, construction.getOriginalConstructionIndices().second);
                		for(Construction otherConstruction : construction.getSentenceConstructions()) {
                			if(usedConstructionList.contains(otherConstruction)) {
                				String distractor = exerciseSettings.getPlainText().substring(otherConstruction.getOriginalConstructionIndices().first, otherConstruction.getOriginalConstructionIndices().second);
                        		if(!text.equals(distractor)) {
                        			construction.getDistractors().add(distractor);
                        		}
                			}
                		}
                		if(construction.getDistractors().size() < 1) {
	                        constructionsToRemove.add(blank.getConstructionIndex());
                		} 
                	}
                }
        	}
        } 
        
        if(!exerciseSettings.getContentType().equals("SingleDrag")) {
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
    		        	Pair<Integer, Integer> constructionIndices = blank.getConstruction().getOriginalConstructionIndices();
        				usedConstructions.add(exerciseSettings.getPlainText().substring(constructionIndices.first, constructionIndices.second));
    		        }
	    		}
	    		for(Blank blank : blanksToRemove) {
	    			fragment.getBlanksBoundaries().remove(blank);
	    		}
	    	}
        }
        
        return usedConstructions;
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
    
}
