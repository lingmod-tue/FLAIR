package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DataStructures;

import java.util.ArrayList;
import java.util.HashMap;

import com.flair.shared.exerciseGeneration.Pair;

public class ExerciseComponents {

	private ArrayList<ArrayList<Pair<String,String>>> distractors;
	
	/**
	 * The HTML elements at the index of successive numbering of plain text, constructions and HTML elements
	 */
	private HashMap<Integer, String> htmlElements;
	
	/**
	 * The plain text elements at the index of successive numbering of plain text, constructions and HTML elements
	 */
	private HashMap<Integer, String> plainTextElements;

	/**
	 * The target constructions at the index of successive numbering of plain text, constructions and HTML elements
	 */
	private HashMap<Integer, TargetConstruction> targetConstructions;

	/**
	 * The instructions displayed to students
	 */
	private String instructions;
	
	/**
	 * The given target words to display in the instructions
	 */
	private String instructionWords;

	public ExerciseComponents(HashMap<Integer, TargetConstruction> targetConstructions) {
		super();
		this.targetConstructions = targetConstructions;
	}
	
	public ArrayList<ArrayList<Pair<String, String>>> getDistractors() { return distractors; }
	public HashMap<Integer, String> getHtmlElements() { return htmlElements; }
	public HashMap<Integer, String> getPlainTextElements() { return plainTextElements; }
	public String getInstructions() { return instructions; }
	public String getInstructionWords() { return instructionWords; }
	public HashMap<Integer, TargetConstruction> getTargetConstructions() { return targetConstructions; }

	public void setDistractors(ArrayList<ArrayList<Pair<String, String>>> distractors) {
		this.distractors = distractors;
	}
	public void setHtmlElements(HashMap<Integer, String> htmlElements) { this.htmlElements = htmlElements; }
	public void setPlainTextElements(HashMap<Integer, String> plainTextElements) {
		this.plainTextElements = plainTextElements;
	}
	public void setInstructions(String instructions) { this.instructions = instructions; }
	public void setInstructionWords(String instructionWords) { this.instructionWords = instructionWords; }

}
