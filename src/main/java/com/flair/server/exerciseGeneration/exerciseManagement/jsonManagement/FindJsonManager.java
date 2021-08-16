package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.flair.shared.exerciseGeneration.Pair;

public class FindJsonManager extends SimpleExerciseJsonManager {

    @Override
    protected String getPlacehholderReplacement(String construction, ArrayList<Pair<String, String>> distractorList, String feedbackId, JSONObject jsonObject) {
        return " *" + construction.replaceAll("\\s", "\u00A0") + "* ";
    }
    
    @Override
   	String getTargetDummy(String constructionText) {
   		return " <label style=\"background-color:greenYellow; font-weight:bold;\"/> ";
   	}

}
