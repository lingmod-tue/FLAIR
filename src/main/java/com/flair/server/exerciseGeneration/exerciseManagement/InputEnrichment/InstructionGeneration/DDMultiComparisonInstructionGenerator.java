package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;

public class DDMultiComparisonInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		ArrayList<String> constructionTypes = determineConstructionTypes(data);
		String formType = determineComparisonFormType(constructionTypes);
		data.setInstructions("Drag the " + formType + " forms into the correct gap to form correct sentences.");
	}
	
}