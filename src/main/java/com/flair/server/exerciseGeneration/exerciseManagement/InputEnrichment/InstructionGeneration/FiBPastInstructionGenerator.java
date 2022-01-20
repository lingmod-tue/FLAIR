package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.shared.exerciseGeneration.InstructionsProperties;

public class FiBPastInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		ArrayList<String> constructionTypes = determineConstructionTypes(data);
        boolean addLemmas = determineAddLemmas(data);
    	boolean hasLemmasInBrackets = determineHasLemmasInBrackets(data);
        String target = !addLemmas ?
                (hasLemmasInBrackets ? "the verbs in brackets" : "the verbs") :
                "one of the following verbs. Each word may only be used once";                           
        String progressiveString = data.getInstructionProperties().contains(InstructionsProperties.PROGRESSIVE) ? 
        		determineProgressive(constructionTypes) : 
        			"";
                        
        String instructions = "Insert the correct " + progressiveString + "forms of " + target + ".";
     
        if (data.getInstructionProperties().contains(InstructionsProperties.TENSE)) {
            String tense = determineTenses(constructionTypes, true);
            instructions += " Use the correct tense (" + tense + ").";
        }               

		data.setInstructions(instructions);
	}
	
}
