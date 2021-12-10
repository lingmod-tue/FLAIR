package com.flair.server.exerciseGeneration.exerciseManagement.configBasedGeneration;

import java.util.ArrayList;
import java.util.Collections;

import com.flair.server.exerciseGeneration.exerciseManagement.ConfigBasedExerciseGenerator.ExerciseConfigData;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.JsonManager;
import com.flair.shared.exerciseGeneration.Pair;
import com.flair.server.exerciseGeneration.exerciseManagement.JsonComponents;

public class JsonComponentPreparer {
	
	private static final String punctuations = ".,:;!?";
	
	public JsonComponentPreparer(ArrayList<ExerciseConfigData> config) {
		prepareJsonComponents(config);
	}
	
	private ArrayList<String> plainTextElements = new ArrayList<>();
	private ArrayList<String> pureHtmlElements = new ArrayList<>();
	private ArrayList<Pair<String, Integer>> constructions = new ArrayList<>();
	private ArrayList<String> lemmas = new ArrayList<>();
	private ArrayList<String> distractorLemmas = new ArrayList<>();
	private ArrayList<ArrayList<Pair<String, String>>> distractors = new ArrayList<>();
	private ArrayList<ArrayList<String>> parts = new ArrayList<>();
			
	public ArrayList<String> getPlainTextElements() { return plainTextElements; }
	public ArrayList<String> getPureHtmlElements() { return pureHtmlElements; }
	public ArrayList<Pair<String, Integer>> getConstructions() { return constructions; }
	public ArrayList<String> getLemmas() { return lemmas; }
	public ArrayList<String> getDistractorLemmas() { return distractorLemmas; }
	public ArrayList<ArrayList<Pair<String, String>>> getDistractors() { return distractors; }
	public ArrayList<ArrayList<String>> getParts() { return parts; }

	private void prepareJsonComponents(ArrayList<ExerciseConfigData> config) {		
		Collections.sort(config,
                (c1, c2) -> c1.getItem() < c2.getItem() ? -1 : 1);
		
		int lineCounter = 1;
		for(ExerciseConfigData line : config) {
			StringBuilder sb = new StringBuilder();
			sb.append("<span data-internal=\"" + lineCounter + "\">");
			parts.add(new ArrayList<>());
			
			ArrayList<Pair<String, String>> currentDistractors = new ArrayList<>();
			for(String distractor : line.getDistractors()) {
				currentDistractors.add(new Pair<>(distractor, ""));
			}
			distractors.add(currentDistractors);
			
			// Construct the plain text and html elements
			ArrayList<Pair<Integer, String>> positions = line.getPositions();
			Collections.sort(positions,
	                (p1, p2) -> p1.first < p2.first ? -1 : 1);
			StringBuilder construction = new StringBuilder();
			for(Pair<Integer, String> position : positions) {
				if(!position.second.isEmpty()) {
					parts.get(parts.size() - 1).add(position.second);
					
					if(!(position.first == 1) && !punctuations.contains(position.second.charAt(0) + "")) {
						sb.append(" ");
					}
					if(position.first == line.getGap().second) {
						// The position is the beginning of the construction
						sb.append("<span data-blank=\"" + constructions.size() + "\"></span>");
					} 
					
					if(position.first >= line.getGap().first && position.first <= line.getGap().second) {
						construction.append(position.second);
					}
					
					if(position.first < line.getGap().first || position.first > line.getGap().second) {
						sb.append(position.second);
					}
				}
			}
			
			if(construction.length() > 0) {
				constructions.add(new Pair<>(construction.toString(), constructions.size()));
			}
			
			lemmas.add(line.getLemma());
			distractorLemmas.add(line.getDistractorLemma());
			
			plainTextElements.add(sb.toString().trim().replaceAll(" +", " "));
			pureHtmlElements.add(lineCounter + " sentenceHtml " + lineCounter);
			pureHtmlElements.add("sentenceHtml " + lineCounter + " ltRep;brgtRep;");
			lineCounter++;
			sb.append("</span>");
		}
		
		if(pureHtmlElements.size() > 0) {
			pureHtmlElements.remove(pureHtmlElements.size() - 1);
		}			
	}
}
