package com.flair.server.exerciseGeneration.InputParsing.ConfigParsing;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.Pair;

public class ConditionalExerciseConfigData extends ExerciseConfigData {
	
	public ConditionalExerciseConfigData() {
		super();
	}
	
	/**
	 * Copy constructor performing deep copy
	 * @param configData
	 */
	public ConditionalExerciseConfigData(ExerciseConfigData configData) {
		super(configData);

		if(!(configData instanceof ConditionalExerciseConfigData)) {
			throw new IllegalArgumentException("The argument of the copy constructor must be of type ConditionalExerciseConfigData!");
		}
		
		ConditionalExerciseConfigData cd = (ConditionalExerciseConfigData)configData;
		this.conditionalType = cd.conditionalType;
		this.translationIfClause = cd.translationIfClause;
		this.translationMainClause = cd.translationMainClause;
		this.bracketsIfClause = new ArrayList<>(cd.bracketsIfClause);
		this.bracketsMainClause = new ArrayList<>(cd.bracketsMainClause);
		this.distractorsMainClause = new ArrayList<>(cd.distractorsMainClause);
		this.distractorsIfClause = new ArrayList<>(cd.distractorsIfClause);
		this.lemmaIfClause = cd.lemmaIfClause;
		this.lemmaMainClause = cd.lemmaMainClause;
		this.distractorLemmaIfClause = cd.distractorLemmaIfClause;
		this.distractorLemmaMainClause = cd.distractorLemmaMainClause;
		this.gapIfClause = new ArrayList<>(cd.gapIfClause);
		this.gapMainClause = new ArrayList<>(cd.gapMainClause);
		this.underlineIfClause = new ArrayList<>(cd.underlineIfClause);
		this.underlineMainClause = new ArrayList<>(cd.underlineMainClause);
		this.positionsIfClause = new ArrayList<>(cd.positionsIfClause);
		this.positionsMainClause = new ArrayList<>(cd.positionsMainClause);
		this.isType1VsType2 = cd.isType1VsType2;
	}
	
	private int conditionalType;
	private String translationIfClause;
	private String translationMainClause;
	private ArrayList<Pair<Integer, String>> distractorsIfClause = new ArrayList<>();
	private ArrayList<Pair<Integer, String>> distractorsMainClause = new ArrayList<>();
	private String lemmaIfClause;
	private String lemmaMainClause;
	private String distractorLemmaIfClause;
	private String distractorLemmaMainClause;
	private ArrayList<String> bracketsIfClause = new ArrayList<>();
	private ArrayList<String> bracketsMainClause = new ArrayList<>();
	private ArrayList<Pair<Integer, Integer>> gapIfClause = new ArrayList<>();
	private ArrayList<Pair<Integer, Integer>> gapMainClause = new ArrayList<>();
	private ArrayList<Pair<Integer, Integer>> underlineIfClause = new ArrayList<>();
	private ArrayList<Pair<Integer, Integer>> underlineMainClause = new ArrayList<>();
	private ArrayList<Pair<Integer, String>> positionsIfClause = new ArrayList<>();
	private ArrayList<Pair<Integer, String>> positionsMainClause = new ArrayList<>();
	private boolean isType1VsType2;

	public boolean isType1VsType2() { return isType1VsType2; }
	public int getConditionalType() { return conditionalType; }
	public String getTranslationIfClause() { return translationIfClause; }
	public String getTranslationMainClause() { return translationMainClause; }
	public ArrayList<Pair<Integer, String>> getDistractorsIfClause() { return distractorsIfClause; }
	public ArrayList<Pair<Integer, String>> getDistractorsMainClause() { return distractorsMainClause; }
	public String getLemmaIfClause() { return lemmaIfClause; }
	public String getLemmaMainClause() { return lemmaMainClause; }
	public String getDistractorLemmaIfClause() { return distractorLemmaIfClause; }
	public String getDistractorLemmaMainClause() { return distractorLemmaMainClause; }
	public ArrayList<String> getBracketsIfClause() { return bracketsIfClause; }
	public ArrayList<String> getBracketsMainClause() { return bracketsMainClause; }
	public ArrayList<Pair<Integer, Integer>> getGapIfClause() { return gapIfClause; }
	public ArrayList<Pair<Integer, Integer>> getGapMainClause() { return gapMainClause; }
	public ArrayList<Pair<Integer, Integer>> getUnderlineIfClause() { return underlineIfClause; }
	public ArrayList<Pair<Integer, Integer>> getUnderlineMainClause() { return underlineMainClause; }
	public ArrayList<Pair<Integer, String>> getPositionsIfClause() { return positionsIfClause; }
	public ArrayList<Pair<Integer, String>> getPositionsMainClause() { return positionsMainClause; }

	public void setType1VsType2(boolean isType1VsType2) { this.isType1VsType2 = isType1VsType2; }
	public void setConditionalType(int conditionalType) { this.conditionalType = conditionalType; }
	public void setTranslationIfClause(String translationIfClause) { this.translationIfClause = translationIfClause; }
	public void setTranslationMainClause(String translationMainClause) { this.translationMainClause = translationMainClause; }
	public void setDistractorsIfClause(ArrayList<Pair<Integer, String>> distractorsIfClause) {
		this.distractorsIfClause = distractorsIfClause;
	}
	public void setDistractorsMainClause(ArrayList<Pair<Integer, String>> distractorsMainClause) {
		this.distractorsMainClause = distractorsMainClause;
	}
	public void setLemmaIfClause(String lemmaIfClause) { this.lemmaIfClause = lemmaIfClause; }
	public void setLemmaMainClause(String lemmaMainClause) { this.lemmaMainClause = lemmaMainClause; }
	public void setDistractorLemmaIfClause(String distractorLemmaIfClause) {
		this.distractorLemmaIfClause = distractorLemmaIfClause;
	}
	public void setDistractorLemmaMainClause(String distractorLemmaMainClause) {
		this.distractorLemmaMainClause = distractorLemmaMainClause;
	}
	public void setBracketsIfClause(ArrayList<String> bracketsIfClause) { this.bracketsIfClause = bracketsIfClause; }
	public void setBracketsMainClause(ArrayList<String> bracketsMainClause) {
		this.bracketsMainClause = bracketsMainClause;
	}
	public void setGapIfClause(ArrayList<Pair<Integer, Integer>> gapIfClause) { this.gapIfClause = gapIfClause; }
	public void setGapMainClause(ArrayList<Pair<Integer, Integer>> gapMainClause) {
		this.gapMainClause = gapMainClause;
	}
	public void setUnderlineIfClause(ArrayList<Pair<Integer, Integer>> underlineIfClause) {
		this.underlineIfClause = underlineIfClause;
	}
	public void setUnderlineMainClause(ArrayList<Pair<Integer, Integer>> underlineMainClause) {
		this.underlineMainClause = underlineMainClause;
	}
	public void setPositionsIfClause(ArrayList<Pair<Integer, String>> positionsIfClause) {
		this.positionsIfClause = positionsIfClause;
	}
	public void setPositionsMainClause(ArrayList<Pair<Integer, String>> positionsMainClause) {
		this.positionsMainClause = positionsMainClause;
	}
									
}
