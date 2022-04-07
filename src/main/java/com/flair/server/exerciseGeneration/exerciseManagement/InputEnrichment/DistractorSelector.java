package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.Distractor;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;


public class DistractorSelector {
	    
    /**
     * Reduces the amount of distractors to the specified number for Single Choice exercises.
     * @param nDistractors	The specified number of distractors
     * @return				The selected distractors with feedback per construction
     */
    public static void chooseDistractors(ExerciseData data, int nDistractors) {    	
    	for(TextPart part : data.getParts()) {        	
        	if(part instanceof ConstructionTextPart) {
        		ConstructionTextPart construction = (ConstructionTextPart)part;

				HashMap<String, String> feedbackMapping = new HashMap<>();
				HashSet<String> options = new HashSet<>();
		    	ArrayList<String> incorrectOptions = new ArrayList<>();
		    	ArrayList<String> optionsWithoutFeedback = new ArrayList<>();
		    	ArrayList<String> incorrectOptionsWithoutFeedback = new ArrayList<>();
		    	
		    	for(Distractor distractor : construction.getDistractors()) {
		    		feedbackMapping.put(distractor.getValue(), distractor.getFeedback());
		    		
		    		if(distractor.isIllFormed()) {
		    			if(distractor.getFeedback() == null || distractor.getFeedback().isEmpty()) {
		        			incorrectOptionsWithoutFeedback.add(distractor.getValue());
		    			} else {
		        			incorrectOptions.add(distractor.getValue());
		    			}
		    		} else {
		    			if(distractor.getFeedback() == null || distractor.getFeedback().isEmpty()) {
		    				optionsWithoutFeedback.add(distractor.getValue());
		    			} else {
		    				options.add(distractor.getValue());
		    			}
		    		}
		    	}
		    	
		    	Collections.shuffle(incorrectOptions);
		    	Collections.shuffle(incorrectOptionsWithoutFeedback);
		    	Collections.shuffle(optionsWithoutFeedback);
		
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
		        
		        // Add correct forms without feedback until we have the necessary distractor number or no more options to add
		        while(options.size() < nDistractors && optionsWithoutFeedback.size() > 0) {
		            int randomIndex = new Random().nextInt(optionsWithoutFeedback.size());
		            String randomForm = optionsWithoutFeedback.get(randomIndex);
		            options.add(randomForm);
		            optionsWithoutFeedback.remove(randomForm);
		        }
		
		        // Add incorrect forms until we have the necessary distractor number or no more options to add
		        while(options.size() < nDistractors && incorrectOptions.size() > 0) {
		            int randomIndex = new Random().nextInt(incorrectOptions.size());
		            String randomForm = incorrectOptions.get(randomIndex);
		            options.add(randomForm);
		            incorrectOptions.remove(randomForm);
		        }
		        
		        // Add incorrect forms without feedback until we have the necessary distractor number or no more options to add
		        while(options.size() < nDistractors && incorrectOptionsWithoutFeedback.size() > 0) {
		            int randomIndex = new Random().nextInt(incorrectOptionsWithoutFeedback.size());
		            String randomForm = incorrectOptionsWithoutFeedback.get(randomIndex);
		            options.add(randomForm);
		            incorrectOptionsWithoutFeedback.remove(randomForm);
		        }
		        
		        ArrayList<String> usedDistractors = new ArrayList<>(options);
		        while(usedDistractors.size() > nDistractors) {
		        	int index = new Random().nextInt(usedDistractors.size());
		        	usedDistractors.remove(index);
		        }
		        
		        // Add the distractors to the settings
		        Collections.shuffle(usedDistractors);
		        ArrayList<Distractor> distractorsWithFeedback = new ArrayList<>();
		        for(String usedDistractor : usedDistractors) {
		        	Distractor d = new Distractor(usedDistractor);
		        	d.setFeedback(feedbackMapping.get(usedDistractor));
		        	distractorsWithFeedback.add(d);
		        }
		        construction.setDistractors(distractorsWithFeedback);
		        if(distractorsWithFeedback.size() == 1) {
		        	construction.setTargetIndex(0) ;
		        } else {
		        	construction.setTargetIndex(new Random().nextInt(distractorsWithFeedback.size() - 1));	// correct answer is to be inserted at random position in distractor list
		        }
        	}
    	}    	
    }
    
}
