package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;

public class SCConditionalInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		ArrayList<String> constructionTypes = determineConstructionTypes(data);
		String conditionalType = determineConditionalType(constructionTypes);
		data.setInstructions("Pick the correct verb form for each gap to form " + conditionalType + "sentences.");

        
        
		data.setInstructions("Pick the correct answer for each gap.");
	}
	
}
