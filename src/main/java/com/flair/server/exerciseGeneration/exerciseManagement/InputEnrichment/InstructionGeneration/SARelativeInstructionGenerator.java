package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;

public class SARelativeInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		String instructions = "Please use the information given to write sentences with relative clauses.";
		if(data.getSubtopic().equals("contact clauses")) {
			instructions += " Leave out the relative pronouns if possible.";
		}
		
        data.setInstructions(instructions);
	}
	
}
