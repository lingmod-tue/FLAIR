package com.flair.server.exerciseGeneration;

import java.util.ArrayList;


public class ExerciseData {

	public ExerciseData(ArrayList<TextPart> parts) {
		this.parts = parts;
	}

	private ArrayList<TextPart> parts;		
	ArrayList<String> instructionLemmas = new ArrayList<>();
	
	public ArrayList<String> getInstructionLemmas() {
		return instructionLemmas;
	}
	public void setInstructionLemmas(ArrayList<String> instructionLemmas) {
		this.instructionLemmas = instructionLemmas;
	}
	public ArrayList<TextPart> getParts() {
		return parts;
	}
	
}
