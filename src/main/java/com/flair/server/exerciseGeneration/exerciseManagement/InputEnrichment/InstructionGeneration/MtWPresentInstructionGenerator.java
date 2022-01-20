package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.shared.exerciseGeneration.InstructionsProperties;

public class MtWPresentInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		String instructions = "Click all simple present forms in the text.";
        if(data.getInstructionProperties().contains(InstructionsProperties.N_TARGETS)) {
        	instructions += " The text contains " + determineNConstructions(data) + " simple present forms.";
        }

		data.setInstructions(instructions);	
	}
	
}
