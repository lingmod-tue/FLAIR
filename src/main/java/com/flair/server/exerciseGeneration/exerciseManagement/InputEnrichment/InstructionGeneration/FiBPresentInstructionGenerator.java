package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;

public class FiBPresentInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		boolean addLemmas = determineAddLemmas(data);
    	boolean hasLemmasInBrackets = determineHasLemmasInBrackets(data);
        String target = !addLemmas ?
        		(hasLemmasInBrackets ? "the verbs in brackets" : "the verbs") :
                "one of the following verbs. Each word may only be used once";
        
		data.setInstructions("Insert the correct simple present forms of " + target + ".");
	}
	
}
