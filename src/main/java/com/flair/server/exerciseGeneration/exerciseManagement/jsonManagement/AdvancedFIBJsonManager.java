package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import org.json.simple.JSONObject;

public class AdvancedFIBJsonManager extends ExerciseWithSeparateBlanksJsonManager {

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

}
