package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;

public class MemoryRelativeInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		data.setInstructions("Match each pair of sentences to the corresponding sentence including a pronoun “who”, “which”, “whose” or “where” to form a relative clause.");
	}
	
}
