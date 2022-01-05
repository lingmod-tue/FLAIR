package com.flair.server.exerciseGeneration;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.Pair;

public class ConstructionTextPart extends TextPart {
	
	private ArrayList<String> brackets = new ArrayList<>();
	private ArrayList<Distractor> distractors = new ArrayList<>();
	private String category = "";
	private Pair<Integer, Integer> indicesInPlainText;
	private ArrayList<ConstructionProperties> constructionProperties = new ArrayList<>();
	private DetailedConstruction constructionType;
	private Pair<Integer, Integer> indicesRelatedConstruction = null;

	public Pair<Integer, Integer> getIndicesRelatedConstruction() {
		return indicesRelatedConstruction;
	}

	public void setIndicesRelatedConstruction(Pair<Integer, Integer> indicesRelatedConstruction) {
		this.indicesRelatedConstruction = indicesRelatedConstruction;
	}

	public DetailedConstruction getConstructionType() {
		return constructionType;
	}

	public void setConstructionType(DetailedConstruction constructionType) {
		this.constructionType = constructionType;
	}

	public ArrayList<ConstructionProperties> getConstructionProperties() {
		return constructionProperties;
	}

	public void setConstructionProperties(ArrayList<ConstructionProperties> constructionProperties) {
		this.constructionProperties = constructionProperties;
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

}
