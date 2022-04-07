package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.Pair;

public class RelativeExerciseItemConfigData extends ExerciseItemConfigData {
	
	private String pronoun;
	private ArrayList<Pair<Integer, String>> positionsClause1 = new ArrayList<>();
	private ArrayList<Pair<Integer, String>> positionsClause2 = new ArrayList<>();
	private ArrayList<String> distractors = new ArrayList<>();
	private boolean contact;
	private String relativeSentence;
	private String contactRelativeSentence;
	private String alternativeRelativeSentence;
	private String feedback;
	private ArrayList<RelativeSentence> relativeSentences = new ArrayList<>();
	
	public String getPronoun() {
		return pronoun;
	}
	public void setPronoun(String pronoun) {
		this.pronoun = pronoun;
	}
	public ArrayList<Pair<Integer, String>> getPositionsClause1() {
		return positionsClause1;
	}
	public void setPositionsClause1(ArrayList<Pair<Integer, String>> positionsClause1) {
		this.positionsClause1 = positionsClause1;
	}
	public ArrayList<Pair<Integer, String>> getPositionsClause2() {
		return positionsClause2;
	}
	public void setPositionsClause2(ArrayList<Pair<Integer, String>> positionsClause2) {
		this.positionsClause2 = positionsClause2;
	}
	public ArrayList<String> getDistractors() {
		return distractors;
	}
	public boolean isContact() {
		return contact;
	}
	public void setContact(boolean contact) {
		this.contact = contact;
	}
	public String getRelativeSentence() {
		return relativeSentence;
	}
	public void setRelativeSentence(String relativeSentence) {
		this.relativeSentence = relativeSentence;
	}
	public String getContactRelativeSentence() {
		return contactRelativeSentence;
	}
	public void setContactRelativeSentence(String contactRelativeSentence) {
		this.contactRelativeSentence = contactRelativeSentence;
	}
	public String getAlternativeRelativeSentence() {
		return alternativeRelativeSentence;
	}
	public void setAlternativeRelativeSentence(String alternativeRelativeSentence) {
		this.alternativeRelativeSentence = alternativeRelativeSentence;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public ArrayList<RelativeSentence> getRelativeSentences() {
		return relativeSentences;
	}
	public void setDistractors(ArrayList<String> distractors) {
		this.distractors = distractors;
	}
										
}
