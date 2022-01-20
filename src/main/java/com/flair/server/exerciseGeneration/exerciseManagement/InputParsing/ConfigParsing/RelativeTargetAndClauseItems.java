package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.Pair;

public class RelativeTargetAndClauseItems {
	
	public RelativeTargetAndClauseItems(ArrayList<Pair<Integer, String>> positionsMainClause,
			ArrayList<Pair<Integer, String>> positionsRelativeClause, Pair<Integer, Integer> commonReferentMainClause,
			Pair<Integer, Integer> commonReferentRelativeClause, String sentenceClause1, String sentenceClause2) {
		this.positionsMainClause = positionsMainClause;
		this.positionsRelativeClause = positionsRelativeClause;
		this.commonReferentMainClause = commonReferentMainClause;
		this.commonReferentRelativeClause = commonReferentRelativeClause;
		this.sentenceClause1 = sentenceClause1;
		this.sentenceClause2 = sentenceClause2;
	}
	
	private ArrayList<Pair<Integer,String>> positionsMainClause;
	private ArrayList<Pair<Integer,String>> positionsRelativeClause;
	private Pair<Integer,Integer> commonReferentMainClause;
	private Pair<Integer,Integer> commonReferentRelativeClause;
	private String sentenceClause1;
	private String sentenceClause2;
	
	public ArrayList<Pair<Integer, String>> getPositionsMainClause() {
		return positionsMainClause;
	}
	public ArrayList<Pair<Integer, String>> getPositionsRelativeClause() {
		return positionsRelativeClause;
	}
	public Pair<Integer, Integer> getCommonReferentMainClause() {
		return commonReferentMainClause;
	}
	public Pair<Integer, Integer> getCommonReferentRelativeClause() {
		return commonReferentRelativeClause;
	}
	public String getSentenceClause2() {
		return sentenceClause2;
	}
	public String getSentenceClause1() {
		return sentenceClause1;
	}

}
