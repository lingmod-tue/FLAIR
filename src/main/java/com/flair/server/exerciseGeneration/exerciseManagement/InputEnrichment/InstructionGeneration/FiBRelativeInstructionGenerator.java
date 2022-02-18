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
        String instructions = "Insert the correct relative pronouns" + specification + ".";
        
		instructions = "Please fill the gap with the correct relative pronoun.";
		if(data.getSubtopic().equals("subject who/which") || data.getSubtopic().equals("object which/whom")) {
			instructions += " Use who or which.";
		}
		
        data.setInstructions(instructions);
	}
	
}
