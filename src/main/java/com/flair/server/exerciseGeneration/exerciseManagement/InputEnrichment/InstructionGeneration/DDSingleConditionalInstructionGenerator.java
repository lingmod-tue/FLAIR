package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;

public class DDSingleConditionalInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		ArrayList<String> constructionTypes = determineConstructionTypes(data);
		String conditionalType = determineConditionalType(constructionTypes);
		data.setInstructions("Drag the items into the correct gap to form " + conditionalType + "sentences.");
		
		data.setInstructions("Drag the items into the correct gap.");
	}
	
}
