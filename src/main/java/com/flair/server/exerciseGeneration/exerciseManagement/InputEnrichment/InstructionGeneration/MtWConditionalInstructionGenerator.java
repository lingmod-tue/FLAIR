package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.shared.exerciseGeneration.DetailedConstruction;

public class MtWConditionalInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		if(data.getParts().stream().anyMatch(c -> c instanceof ConstructionTextPart && 
				(((ConstructionTextPart)c).getConstructionType().equals(DetailedConstruction.CONDREAL_MAIN) ||
				((ConstructionTextPart)c).getConstructionType().equals(DetailedConstruction.CONDUNREAL_MAIN)))) {
			data.setInstructions("Underline the verb in the main clause.");
		} else {
			data.setInstructions("Underline the verb in the if clause.");
		}
	}
	
}
