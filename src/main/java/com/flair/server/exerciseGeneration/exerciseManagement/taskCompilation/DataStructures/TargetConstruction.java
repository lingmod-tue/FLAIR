package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DataStructures;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.DetailedConstruction;

public class TargetConstruction {
	
	/**
	 * The string value of the construction
	 */
	private final String value;
	
	/**
	 * The construction type
	 */
	private final DetailedConstruction type;
	
	/**
	 * The start index in the FLAIR plain text
	 */
	private final int startIndex;
	
	/**
	 * The end index in the FLAIR plain text
	 */
	private final int endIndex;
	
	/**
	 * The index of the sentence in which the construction is located
	 */
	private int sentenceIndex;
	
	/**
	 * Incorrect target hypotheses
	 */
	private ArrayList<Distractor> distractors;
	
	/**
	 * Comma delimited elements of brackets
	 */
	private ArrayList<String> bracketsComponents;

	public TargetConstruction(String value, int startIndex, int endIndex, DetailedConstruction type) {
		this.value = value;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.type = type;
	}

	public int getSentenceIndex() { return sentenceIndex; }
	public ArrayList<Distractor> getDistractors() { return distractors; }
	public String getValue() { return value; }
	public ArrayList<String> getBracketsComponents() { return bracketsComponents; }
	public int getStartIndex() { return startIndex; }
	public int getEndIndex() { return endIndex; }

	public void setSentenceIndex(int sentenceIndex) { this.sentenceIndex = sentenceIndex; }
	public void setDistractors(ArrayList<Distractor> distractors) { this.distractors = distractors; }
	public void setBracketsComponents(ArrayList<String> bracketsComponents) {
		this.bracketsComponents = bracketsComponents;
	}
	public DetailedConstruction getType() { return type; }
	
}
