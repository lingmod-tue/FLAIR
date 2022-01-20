package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.shared.exerciseGeneration.InstructionsProperties;

public class MtWComparisonInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		ArrayList<String> constructionTypes = determineConstructionTypes(data);
		String formType = determineComparisonFormType(constructionTypes);

        String instructions = "Click all " + formType + " forms in the text.";
        if(data.getInstructionProperties().contains(InstructionsProperties.N_TARGETS)) {
        	instructions += " The text contains " + determineNConstructions(data) + " " + formType + " forms.";
        }

		data.setInstructions(instructions);				
	}
	
}
