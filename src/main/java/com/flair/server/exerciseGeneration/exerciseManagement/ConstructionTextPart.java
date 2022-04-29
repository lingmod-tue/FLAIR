package com.flair.server.exerciseGeneration.exerciseManagement;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.Pair;

/**
 * A part of the HTML string of the web page which represents a target construction
 * @author taheck
 *
 */
public class ConstructionTextPart extends TextPart {
	
	private ArrayList<String> brackets = new ArrayList<>();
	private ArrayList<Distractor> distractors = new ArrayList<>();
	private String category = "";
	private Pair<Integer, Integer> indicesInPlainText;
	private String constructionType;
	private Pair<Integer, Integer> indicesRelatedConstruction = null;
	private int targetIndex = 0;
	private ArrayList<String> targetAlternatives = new ArrayList<>();
	private String fallbackFeedback = null;

	public Pair<Integer, Integer> getIndicesRelatedConstruction() {
		return indicesRelatedConstruction;
	}

	public void setIndicesRelatedConstruction(Pair<Integer, Integer> indicesRelatedConstruction) {
		this.indicesRelatedConstruction = indicesRelatedConstruction;
	}

	public String getConstructionType() {
		return constructionType;
	}

	public void setConstructionType(String constructionType) {
		this.constructionType = constructionType;
	}

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
	
	public Pair<Integer, Integer> getIndicesInPlainText() {
		return indicesInPlainText;
	}

	public void setIndicesInPlainText(Pair<Integer, Integer> indicesInPlainText) {
		this.indicesInPlainText = indicesInPlainText;
	}

	public int getTargetIndex() {
		return targetIndex;
	}

	public void setTargetIndex(int targetIndex) {
		this.targetIndex = targetIndex;
	}

	public ArrayList<String> getTargetAlternatives() {
		return targetAlternatives;
	}

	public String getFallbackFeedback() {
		return fallbackFeedback;
	}

	public void setFallbackFeedback(String fallbackFeedback) {
		this.fallbackFeedback = fallbackFeedback;
	}

}
