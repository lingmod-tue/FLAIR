package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;

public class CategorizeRelativeInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		data.setInstructions("Please move each of these sentences to the box they belong to, depending on whether they can be written with, a normal relative clause, or without a relative pronoun, a contact clause.");
	}
	
}
