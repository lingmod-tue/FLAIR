package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;

public class DDMultiConditionalInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		ArrayList<String> constructionTypes = determineConstructionTypes(data);
		String conditionalType = determineConditionalType(constructionTypes);
		data.setInstructions("Drag the items into the correct gap to form " + conditionalType + "sentences.");
	}
	
}
