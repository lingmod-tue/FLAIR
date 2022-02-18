package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;

public class HalfOpenConditionalInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		String instructions = "Use the verbs in brackets to form a if-clause.";
		data.setInstructions(instructions);
	}
	
}
