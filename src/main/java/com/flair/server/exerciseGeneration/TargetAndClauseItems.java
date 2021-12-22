package com.flair.server.exerciseGeneration;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.Pair;

public class TargetAndClauseItems {

	public TargetAndClauseItems(ArrayList<Pair<Integer, String>> positions,
			ArrayList<Pair<Integer, Integer>> targetPositions,
			ArrayList<ArrayList<Pair<Integer, String>>> targetDistractors, ArrayList<String> lemmas,
			ArrayList<String> distractorLemmas, ArrayList<ArrayList<String>> givenLemmas) {
		super();
		this.positions = positions;
		this.targetPositions = targetPositions;
		this.targetDistractors = targetDistractors;
		this.lemmas = lemmas;
		this.distractorLemmas = distractorLemmas;
		this.givenLemmas = givenLemmas;
	}
	
	private ArrayList<Pair<Integer,String>> positions;
	private ArrayList<Pair<Integer, Integer>> targetPositions = new ArrayList<>();
	private ArrayList<ArrayList<Pair<Integer, String>>> targetDistractors;
	ArrayList<String> lemmas = new ArrayList<>();
	ArrayList<String> distractorLemmas = new ArrayList<>();
	ArrayList<ArrayList<String>> givenLemmas = new ArrayList<>();
	
	public ArrayList<Pair<Integer, String>> getPositions() {
		return positions;
	}
	public ArrayList<Pair<Integer, Integer>> getTargetPositions() {
		return targetPositions;
	}
	public ArrayList<ArrayList<Pair<Integer, String>>> getTargetDistractors() {
		return targetDistractors;
	}
	public ArrayList<String> getLemmas() {
		return lemmas;
	}
	public ArrayList<String> getDistractorLemmas() {
		return distractorLemmas;
	}
	public ArrayList<ArrayList<String>> getGivenLemmas() {
		return givenLemmas;
	}
	
}
