package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.flair.shared.exerciseGeneration.Pair;

public class DragJsonManager extends SimpleExerciseJsonManager {

    @Override
    protected String getPlacehholderReplacement(String construction, ArrayList<Pair<String, String>> distractorList, String feedbackId, JSONObject jsonObject) {        
        construction = construction.replace(":", "::");
        if(addFeedbackToJson(jsonObject, feedbackId, distractorList)) {
            construction += ":" + feedbackId;
        }

        return "*" + construction + "*";
    }

    @Override
	String getTargetDummy(String constructionText) {
		return " <button style=\"background-color:greenYellow;\">" + constructionText + "</button> ";
	}

}
