package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.H5PGeneration;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.flair.server.exerciseGeneration.exerciseManagement.Distractor;


public class AdvancedFIBContentJsonGenerator extends SimpleExerciseContentJsonGenerator {

    public AdvancedFIBContentJsonGenerator(boolean useBlanks) {
        this.useBlanks = useBlanks;
    }
    
    /**
     * True if we want to generate an FIB exercise.
     * False if we want to generate a single choice exercise.
     */
    private boolean useBlanks;

    @Override
    protected void setExerciseSpecificAttributes(JSONObject jsonObject) {
        if(useBlanks) {
            ((JSONObject)jsonObject.get("behaviour")).put("mode", "typing");
        } else {
            ((JSONObject)jsonObject.get("behaviour")).put("mode", "selection");
        }

        jsonObject.put("internalId", internalId + "");
    }

    @Override
    protected String getPlacehholderReplacement(String construction, ArrayList<Distractor> distractorList, String feedbackId, JSONObject jsonObject) {

        construction = construction.replace(":", "::").replace("/", "//");
        if(addFeedbackToJson(jsonObject, feedbackId, distractorList)) {
            construction += ":" + feedbackId;
        }

        return "*" + construction + "*";
    }
    
}
