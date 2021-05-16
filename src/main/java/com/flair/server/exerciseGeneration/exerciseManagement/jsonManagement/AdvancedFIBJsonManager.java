package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import java.util.ArrayList;

import org.json.simple.JSONObject;

public class AdvancedFIBJsonManager extends ExerciseWithInlineBlanksJsonManager {

    public AdvancedFIBJsonManager(boolean useBlanks) {
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
    protected String getPlacehholderReplacement(String construction, ArrayList<String> distractorList) {
        //TODO: store feedback with distractors
        distractorList.add(0, construction);
        ArrayList<String> options = new ArrayList<>();
        for(String distractor : distractorList) {
            // escape special symbols
            options.add(distractor.replace("|", "||").replace(":", "::").replace("/", "//"));
        }
        return "*" + String.join("|", options) + "*";
    }
    
}
