package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.DetailedConstruction;

public class HalfOpenConditionalInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		String instructions;
		if(data.getParts().stream().anyMatch(c -> c instanceof ConstructionTextPart && 
				(((ConstructionTextPart)c).getConstructionType().equals(DetailedConstruction.CONDREAL_MAIN) ||
				((ConstructionTextPart)c).getConstructionType().equals(DetailedConstruction.CONDUNREAL_MAIN)))) {
			instructions = "Use the verbs in brackets to form a main clause.";
		} else {
			instructions = "Use the verbs in brackets to form an if-clause.";
		}
		
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