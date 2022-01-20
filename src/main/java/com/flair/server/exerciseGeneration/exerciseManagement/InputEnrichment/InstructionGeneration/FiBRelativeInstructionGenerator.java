package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;

public class FiBRelativeInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		ArrayList<String> constructionTypes = determineConstructionTypes(data);
        String specification = determinePronounList(constructionTypes);
        if(specification.length() > 0) {
        	specification = "(" + specification + ")";
        }

		data.setInstructions("Insert the correct relative pronouns" + specification + ".");
	}
	
}
