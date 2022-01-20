package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;

public class FiBComparisonInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		ArrayList<String> constructionTypes = determineConstructionTypes(data);
		String formType = determineComparisonFormType(constructionTypes);
        String pos = determineComparisonPos(constructionTypes);
    	boolean addLemmas = determineAddLemmas(data);
    	boolean hasLemmasInBrackets = determineHasLemmasInBrackets(data);
        String target = !addLemmas ?
        		(hasLemmasInBrackets ? ("the " + pos + " in brackets") : ("the " + pos)) :
                "one of the following " + pos + ". Each word may only be used once";
        
		data.setInstructions("Insert the correct " + formType + " forms of " + target + ".");
	}
	
}
