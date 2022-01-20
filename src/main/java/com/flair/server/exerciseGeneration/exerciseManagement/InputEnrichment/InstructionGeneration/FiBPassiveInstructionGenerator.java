package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.shared.exerciseGeneration.InstructionsProperties;

public class FiBPassiveInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		ArrayList<String> constructionTypes = determineConstructionTypes(data);
        String voice = determineVoice(constructionTypes);

        String instructions = "Insert the correct " + voice + " forms of the verbs in brackets.";

        if (data.getInstructionProperties().contains(InstructionsProperties.TENSE)) {
            String tense = determineTenses(constructionTypes, false);
            instructions += " Use the correct tense (" + tense + ").";
        }

		data.setInstructions(instructions);
	}
	
}
