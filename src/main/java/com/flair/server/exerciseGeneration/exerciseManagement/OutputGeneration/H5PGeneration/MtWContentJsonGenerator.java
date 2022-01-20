package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.H5PGeneration;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import com.flair.server.exerciseGeneration.exerciseManagement.Distractor;


public class MtWContentJsonGenerator extends SimpleExerciseContentJsonGenerator {

    @Override
    protected String getPlacehholderReplacement(String construction, ArrayList<Distractor> distractorList, String feedbackId, JSONObject jsonObject) {
        return " *" + construction.replaceAll("\\s", "\u00A0") + "* ";
    }

	@Override
	protected String escapeAsterisksInHtml(String htmlString) {
		return htmlString;
	}
	
}
