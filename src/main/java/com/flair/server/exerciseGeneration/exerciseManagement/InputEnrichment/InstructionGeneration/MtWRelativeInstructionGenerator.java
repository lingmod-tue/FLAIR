package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.shared.exerciseGeneration.InstructionsProperties;

public class MtWRelativeInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		String instructions = "Click all relative pronouns in the text.";
        if(data.getInstructionProperties().contains(InstructionsProperties.N_TARGETS)) {
        	instructions += " The text contains " + determineNConstructions(data) + " relative pronouns.";
        }
		
        instructions = "Please find and click on all the occurrences of the words “who”, “which”, “whose” or “where” to form a relative clause.";
		data.setInstructions(instructions);
	}
		
}
