package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.DetailedConstruction;

public class SCConditionalInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		ArrayList<String> constructionTypes = determineConstructionTypes(data);
		String conditionalType = determineConditionalType(constructionTypes);
		data.setInstructions("Pick the correct verb form for each gap to form " + conditionalType + "sentences.");

        
        
		String instructions = "Pick the correct answer for each gap.";
		
		if(data.getBracketsProperties().contains(BracketsProperties.CONDITIONAL_TYPE)) {
			int type = 2;
			if(data.getParts().stream().anyMatch(part -> part instanceof ConstructionTextPart && 
					((ConstructionTextPart)part).getConstructionType().startsWith(DetailedConstruction.CONDREAL))) {
				type = 1;
			}
			instructions += " Use conditional Type " + type + ".";
		}
		
		data.setInstructions(instructions);
	}
	
}
