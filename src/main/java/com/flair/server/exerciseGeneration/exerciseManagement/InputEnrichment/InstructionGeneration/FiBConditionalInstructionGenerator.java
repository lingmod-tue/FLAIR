package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.InstructionsProperties;

public class FiBConditionalInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		ArrayList<String> constructionTypes = determineConstructionTypes(data);
		String conditionalType = determineConditionalType(constructionTypes);
    	boolean addLemmas = determineAddLemmas(data);
    	boolean hasLemmasInBrackets = determineHasLemmasInBrackets(data);
        String target = !addLemmas ?
                (hasLemmasInBrackets ? "the verbs in brackets" : "the verbs") :
                "one of the following verbs. Each word may be used only once";
		data.setInstructions("Insert the correct forms of " + target + " to form " + conditionalType + "sentences.");

        
        
		String instructions;
		if(data.getBracketsProperties().contains(BracketsProperties.DISTRACTOR_LEMMA) || 
				data.getInstructionProperties().contains(InstructionsProperties.LEMMA)) {
			instructions = "Read each sentence. Which verb fits? Fill in the gap with the correct form of the verb.";
		} else {
			instructions = "Fill in the gap to form a correct sentence.";
		}
		
		data.setInstructions(instructions);
	}
	
}
