package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import java.util.ArrayList;

import org.json.simple.JSONObject;

public class FindJsonManager extends SimpleExerciseJsonManager {

    @Override
    protected String getPlacehholderReplacement(String construction, ArrayList<String> distractorList, String feedbackId, JSONObject jsonObject) {
        return " *" + construction.replaceAll("\\s", "\u00A0") + "* ";
    }

}
