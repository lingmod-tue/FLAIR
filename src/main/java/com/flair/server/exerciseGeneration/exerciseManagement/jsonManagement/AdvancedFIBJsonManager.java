package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.flair.shared.exerciseGeneration.Pair;

public class AdvancedFIBJsonManager extends SimpleExerciseJsonManager {

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
    protected String getPlacehholderReplacement(String construction, ArrayList<Pair<String, String>> distractorList, String feedbackId, JSONObject jsonObject) {
        construction = construction.replace(":", "::").replace("/", "//");
        if(addFeedbackToJson(jsonObject, feedbackId, distractorList)) {
            construction += ":" + feedbackId;
        }

        return "*" + construction + "*";
    }

	@Override
	String getTargetDummy(String constructionText) {
		if(useBlanks) {
			return " <input type=\"text\" style=\"background-color:greenYellow; width:60px;\" placeholder=\"" + constructionText + "\"> ";
		} else {
			return " <select style=\"background-color:greenYellow;\"><option>" + constructionText + "</option></select> ";
		}
	}
    
}
