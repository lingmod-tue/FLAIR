package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import java.util.ArrayList;

public class DragJsonManager extends SimpleExerciseJsonManager {

    @Override
    protected String getPlacehholderReplacement(String construction, ArrayList<String> distractorList) {
        construction = construction.replace(":", "::");	// colon introduces feedback

    	//TODO: store feedback with distractors; we here assume that the distractor list contains the values of the other droppables of the task with feedback
        ArrayList<String> options = new ArrayList<>();
        for(String distractor : distractorList) {
            // escape special symbols
        	//  | and = are only special characters within feedback, not in the construction
            options.add(distractor.replace("|", "||").replace(":", "::").replace("=", "=="));	
        }
        String feedback = String.join("|", options);
        if(!feedback.trim().equals("")) {
            construction = construction + ":" + feedback;
        }
        
        return "*" + construction + "*";
    }
    
}
