package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;

public class HalfOpenRelativeInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		String instructions = "Please complete the relative clauses.";
		if(data.getSubtopic().equals("contact clauses")) {
			instructions += " Leave out the relative pronouns if possible.";
		} else {
			instructions += " Use the information from the two sentences given.";
		}
		
        data.setInstructions(instructions);
	}
	
}
