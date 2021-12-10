package com.flair.server.exerciseGeneration.exerciseManagement.configBasedGeneration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.flair.server.exerciseGeneration.exerciseManagement.ConfigBasedExerciseGenerator.ExerciseConfigData;
import com.flair.server.exerciseGeneration.exerciseManagement.JsonComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.FillInTheBlanksSettings;
import com.flair.shared.exerciseGeneration.ConfigExerciseSettings;
import com.flair.shared.exerciseGeneration.Pair;

public class FillInTheBlanksJsonComponentGenerator extends JsonComponentGenerator {
	public FillInTheBlanksJsonComponentGenerator(boolean lemmasInBrackets, boolean useDistractorLemmas) {
		this.lemmasInBrackets = lemmasInBrackets;
		this.useDistractorLemmas = useDistractorLemmas;
	}
	
	private static final String taskDescription = "Fill in the gap with the correct answer.";
	private final boolean lemmasInBrackets;
	private final boolean useDistractorLemmas;

	@Override
	public JsonComponents generateJsonComponents(ArrayList<ExerciseConfigData> config, JsonComponentPreparer preparer,
			ConfigExerciseSettings exerciseSettings) {		
		ContentTypeSettings settings = new FillInTheBlanksSettings("exercise" + config.get(0).getActivity() + "_fill_the_gap_" + 
				(lemmasInBrackets ? 
						(useDistractorLemmas ? "v+d_gap" : "v_gap") :
						"v+d_above")
				);
		settings.setExerciseSettings(exerciseSettings);
		
		ArrayList<String> instructionLemmas = null;
		if(!lemmasInBrackets) {
			instructionLemmas = new ArrayList<>(preparer.getLemmas());
			if(useDistractorLemmas) {
				instructionLemmas.addAll(preparer.getDistractorLemmas());
			}
		}
		
		ArrayList<String> plainTextElements;
		if(lemmasInBrackets) {
			plainTextElements = new ArrayList<>();
			int lemmaCounter = 0;
			for(String plainText : preparer.getPlainTextElements()) {
				ArrayList<Integer> constructionEndIndices = new ArrayList<>();
				Matcher m = Pattern.compile("<span data-blank=.+?</span>").matcher(plainText);
				while (m.find()) {
					constructionEndIndices.add(m.end());
				}
				Collections.sort(constructionEndIndices,
			                (c1, c2) -> c1 > c2 ? -1 : 1);
				 
				int constructionCounter = 1;
				for(int constructionIndex : constructionEndIndices) {
					int lemmaIndex = lemmaCounter + constructionEndIndices.size() - constructionCounter;
					StringBuilder brackets = new StringBuilder();
					brackets.append("(");
					brackets.append(preparer.getLemmas().get(lemmaIndex));
					if(useDistractorLemmas) {
						brackets.append("|").append(preparer.getDistractorLemmas().get(lemmaIndex));
					}
					brackets.append(")");
					
					plainText = plainText.substring(0, constructionIndex) + " " + brackets.toString() + plainText.substring(constructionIndex);
					constructionCounter++;
				}
				
				plainTextElements.add(plainText);
				 
				lemmaCounter += constructionEndIndices.size();
			}
		
		} else {
			plainTextElements = new ArrayList<>(preparer.getPlainTextElements());
		}
		
		
		return new JsonComponents(plainTextElements, preparer.getPureHtmlElements(), preparer.getConstructions(),
                settings.getJsonManager(), settings.getContentTypeLibrary(), settings.getResourceFolder(), 
                preparer.getDistractors(), taskDescription, instructionLemmas, settings);
	}
}
