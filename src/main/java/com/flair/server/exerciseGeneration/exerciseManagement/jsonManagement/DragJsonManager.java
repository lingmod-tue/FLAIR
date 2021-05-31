package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import java.util.ArrayList;

import org.json.simple.JSONObject;

public class DragJsonManager extends SimpleExerciseJsonManager {

    @Override
    protected String getPlacehholderReplacement(String construction, ArrayList<String> distractorList, String feedbackId, JSONObject jsonObject) {        
        construction = construction.replace(":", "::");
        if(addFeedbackToJson(jsonObject, feedbackId, distractorList)) {
            construction += ":" + feedbackId;
        }

        return "*" + construction + "*";
    }
    
}
