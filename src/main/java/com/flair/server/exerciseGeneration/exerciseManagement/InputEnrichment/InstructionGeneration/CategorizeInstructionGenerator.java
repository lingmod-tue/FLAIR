package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.shared.exerciseGeneration.ExerciseTopic;

public class CategorizeInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		if(data.getTopic().equals(ExerciseTopic.CONDITIONALS)) {
			data.setInstructions("Read the sentences and decide if they are type 1 or type 2.");
		} 
	}
	
}
