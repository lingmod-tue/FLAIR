package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;

public class DDMultiPastInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		ArrayList<String> constructionTypes = determineConstructionTypes(data);
        String tense = determineTenses(constructionTypes, true);
		data.setInstructions("Drag the " + tense.replace(" or ", " and ") + " forms into the empty slots to form correct sentences.");
	}
	
}
