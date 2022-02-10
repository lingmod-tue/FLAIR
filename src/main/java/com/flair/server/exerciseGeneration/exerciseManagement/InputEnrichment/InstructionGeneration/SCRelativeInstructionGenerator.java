package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;

public class SCRelativeInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		data.setInstructions("Please select the correct response to form a relative clause.");
	}
	
}
