package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.util.ArrayList;

public class RelativeExerciseItemConfigData extends ExerciseItemConfigData {
	
	private ArrayList<String> pronouns = new ArrayList<>();
	private String clause1 = "";
	private String clause2 = "";
	private ArrayList<String> distractors = new ArrayList<>();
	private ArrayList<RelativeSentence> relativeSentences = new ArrayList<>();
	
	public ArrayList<String> getPronouns() {
		return pronouns;
	}
	public void setPronouns(ArrayList<String> pronoun) {
		this.pronouns = pronoun;
	}
	public String getClause1() {
		return clause1;
	}
	public void setClause1(String positionsClause1) {
		this.clause1 = positionsClause1;
	}
	public String getClause2() {
		return clause2;
	}
	public void setClause2(String positionsClause2) {
		this.clause2 = positionsClause2;
	}
	public ArrayList<String> getDistractors() {
		return distractors;
	}
	public ArrayList<RelativeSentence> getRelativeSentences() {
		return relativeSentences;
	}
	public void setRelativeSentences(ArrayList<RelativeSentence> relativeSentences) {
		this.relativeSentences = relativeSentences;
	}
	public void setDistractors(ArrayList<String> distractors) {
		this.distractors = distractors;
	}
										
}
