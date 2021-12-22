package com.flair.server.exerciseGeneration;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Pair;

public class ConstructionTextPart extends TextPart {
	
	private ArrayList<String> brackets = new ArrayList<>();
	private ArrayList<Distractor> distractors = new ArrayList<>();
	private String category = "";
	private String translation = "";

	public ConstructionTextPart(String value, int sentenceId) {
		super(value, sentenceId);
	}

	public ArrayList<String> getBrackets() {
		return brackets;
	}

	public void setBrackets(ArrayList<String> brackets) {
		this.brackets = brackets;
	}

	public ArrayList<Distractor> getDistractors() {
		return distractors;
	}

	public void setDistractors(ArrayList<Distractor> distractors) {
		this.distractors = distractors;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTranslation() {
		return translation;
	}

	public void setTranslation(String translation) {
		this.translation = translation;
	}

}
