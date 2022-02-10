package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;

public class FiBRelativeInstructionGenerator extends InstructionGenerator {

	@Override
	public void generateInstructions(ExerciseData data) {
		ArrayList<String> constructionTypes = determineConstructionTypes(data);
        String specification = determinePronounList(constructionTypes);
        if(specification.length() > 0) {
        	specification = "(" + specification + ")";
        }
        String instructions = "Insert the correct relative pronouns" + specification + ".";
        
        instructions = "Please fill the gap with a relative pronoun (“who”, “which”, “whose” or “where”) to form a relative clause.";
		if(data.getExerciseTitle().endsWith("/4")) {
			instructions = "Please complete the sentences below (using among others “who”, “which”, “whose” or “where”) to form a relative clause.";
			if(data.getExerciseTitle().startsWith("contact clauses")) {
				instructions += " If possible, write contact clauses, that is, a relative clause that has no relative pronoun.";
			}
		} else if(data.getExerciseTitle().endsWith("/5")) {
			instructions = "Please use the information given to write a relative clause (using among others “who”, “which”, “whose” or “where”).";
			if(data.getExerciseTitle().startsWith("contact clauses")) {
				instructions += " If possible, write contact clauses, that is, a relative clause that has no relative pronoun.";
			}
		}
        data.setInstructions(instructions);
	}
	
}
