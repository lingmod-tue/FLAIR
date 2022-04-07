package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.H5PGeneration;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.flair.server.exerciseGeneration.exerciseManagement.Distractor;


public class DDContentJsonGenerator extends SimpleExerciseContentJsonGenerator {

    @Override
    protected String getPlacehholderReplacement(String construction, ArrayList<Distractor> distractorList, String feedbackId, JSONObject jsonObject, ArrayList<String> brackets) {
    
        construction = construction.replace(":", "::");
        if(addFeedbackToJson(jsonObject, feedbackId, distractorList)) {
            construction += ":" + feedbackId;
        }

        return "*" + construction + "*";
    }

}
