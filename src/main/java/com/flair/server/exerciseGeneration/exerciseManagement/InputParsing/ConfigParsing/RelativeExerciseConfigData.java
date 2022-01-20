package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.Pair;

public class RelativeExerciseConfigData extends ExerciseConfigData {
	
	private String pronoun;
	private String functionReferenceClause1;
	private String functionReferenceClause2;
	private Pair<Integer, Integer> commonReferenceClause1;
	private Pair<Integer, Integer> commonReferenceClause2;
	private ArrayList<Pair<Integer, String>> positionsClause1 = new ArrayList<>();
	private ArrayList<Pair<Integer, String>> positionsClause2 = new ArrayList<>();
	private boolean isDefining;
	private ArrayList<String> distractors = new ArrayList<>();
	
	public String getPronoun() {
		return pronoun;
	}
	public void setPronoun(String pronoun) {
		this.pronoun = pronoun;
	}
	public String getFunctionReferenceClause1() {
		return functionReferenceClause1;
	}
	public void setFunctionReferenceClause1(String functionReferenceClause1) {
		this.functionReferenceClause1 = functionReferenceClause1;
	}
	public String getFunctionReferenceClause2() {
		return functionReferenceClause2;
	}
	public void setFunctionReferenceClause2(String functionReferenceClause2) {
		this.functionReferenceClause2 = functionReferenceClause2;
	}
	public Pair<Integer,Integer> getCommonReferenceClause1() {
		return commonReferenceClause1;
	}
	public void setCommonReferenceClause1(Pair<Integer, Integer> commonReferenceClause1) {
		this.commonReferenceClause1 = commonReferenceClause1;
	}
	public Pair<Integer,Integer> getCommonReferenceClause2() {
		return commonReferenceClause2;
	}
	public void setCommonReferenceClause2(Pair<Integer, Integer> commonReferenceClause2) {
		this.commonReferenceClause2 = commonReferenceClause2;
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
	public boolean isDefining() {
		return isDefining;
	}
	public void setDefining(boolean isDefining) {
		this.isDefining = isDefining;
	}
	public ArrayList<String> getDistractors() {
		return distractors;
	}
									
}
