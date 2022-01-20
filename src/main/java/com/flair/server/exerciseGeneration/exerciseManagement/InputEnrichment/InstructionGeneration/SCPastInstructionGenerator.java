package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;

public class SCPastInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		ArrayList<String> constructionTypes = determineConstructionTypes(data);
        String tense = determineTenses(constructionTypes, true);	 	            	            
		data.setInstructions("Pick the correct " + tense + " form for each gap.");
	}
	
}
