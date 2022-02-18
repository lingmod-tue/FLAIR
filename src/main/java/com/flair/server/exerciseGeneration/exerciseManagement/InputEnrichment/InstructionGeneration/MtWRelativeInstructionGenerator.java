package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.shared.exerciseGeneration.InstructionsProperties;

public class MtWRelativeInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {     		
		String instructions = "Please find and click on all relative pronouns.";
        if(data.getInstructionProperties().contains(InstructionsProperties.N_TARGETS)) {
        	instructions += " The text contains " + determineNConstructions(data) + " relative pronouns.";
        }
        
		data.setInstructions(instructions);
	}
		
}
