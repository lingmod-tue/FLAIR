package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment;

import java.util.ArrayList;
import java.util.HashSet;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.NlpManager;

public abstract class DistractorGenerator {

	/**
	 * Generates distractors
	 * @param nlpManager	The NLP manager
	 * @param data			The exercise data
	 * @return				<code>true</code> if the exercise has valid elements; <code>false</code> if the exercise needs to be rejected
	 */
    public abstract boolean generateDistractors(NlpManager nlpManager, ExerciseData data);
    
    /**
     * Capitalizes all distractors if the correct form starts with an uppercase character.
     * @param options		The distractors
     * @param correctForm	The correct form
     * @return				The capitalized distractors if the correct form is capitalized, otherwise the original distractors
     */
    protected ArrayList<String> capitalize(ArrayList<String> options, String correctForm) {
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
    protected void removeCorrectForm(String correctFormText, HashSet<String> options) {
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
