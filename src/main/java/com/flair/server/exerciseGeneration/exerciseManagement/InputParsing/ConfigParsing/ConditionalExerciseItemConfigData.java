package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.Pair;

public class ConditionalExerciseItemConfigData extends ExerciseItemConfigData {
	
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
	private ArrayList<Pair<Integer, String>> differingValuesIfClause = new ArrayList<>();
	private ArrayList<Pair<Integer, String>> differingValuesMainClause = new ArrayList<>();
	private boolean forceIfFirst = false;
	private String alternativeTarget = null;
	private String punctuationMark = ".";

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
	public ArrayList<Pair<Integer, String>> getDifferingValuesIfClause() {
		return differingValuesIfClause;
	}
	public void setDifferingValuesIfClause(ArrayList<Pair<Integer, String>> differingValuesIfClause) {
		this.differingValuesIfClause = differingValuesIfClause;
	}
	public ArrayList<Pair<Integer, String>> getDifferingValuesMainClause() {
		return differingValuesMainClause;
	}
	public void setDifferingValuesMainClause(ArrayList<Pair<Integer, String>> differengValuesMainClause) {
		this.differingValuesMainClause = differengValuesMainClause;
	}
	public boolean isForceIfFirst() {
		return forceIfFirst;
	}
	public void setForceIfFirst(boolean forceIfFirst) {
		this.forceIfFirst = forceIfFirst;
	}
	public String getAlternativeTarget() {
		return alternativeTarget;
	}
	public void setAlternativeTarget(String alternativeTarget) {
		this.alternativeTarget = alternativeTarget;
	}
	public String getPunctuationMark() {
		return punctuationMark;
	}
	public void setPunctuationMark(String punctuationMark) {
		this.punctuationMark = punctuationMark;
	}
									
}
