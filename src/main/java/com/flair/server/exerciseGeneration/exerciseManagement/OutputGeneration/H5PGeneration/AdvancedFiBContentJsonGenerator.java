package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.H5PGeneration;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.flair.server.exerciseGeneration.exerciseManagement.Distractor;

import edu.stanford.nlp.util.StringUtils;


public class AdvancedFiBContentJsonGenerator extends SimpleExerciseContentJsonGenerator {

    public AdvancedFiBContentJsonGenerator(boolean useBlanks) {
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
    protected String getPlacehholderReplacement(String construction, ArrayList<Distractor> distractorList, String feedbackId, JSONObject jsonObject, ArrayList<String> brackets) {

        construction = construction.replace(":", "::").replace("/", "//");
        if(addFeedbackToJson(jsonObject, feedbackId, distractorList)) {
            construction += ":" + feedbackId;
        }
        
        String ret = "*" + construction + "*";
        
        if(brackets.size() > 0) {
        	ret += " (" + StringUtils.join(brackets, ", ") + ")";
        }
        
        return ret;
    }
    
}
