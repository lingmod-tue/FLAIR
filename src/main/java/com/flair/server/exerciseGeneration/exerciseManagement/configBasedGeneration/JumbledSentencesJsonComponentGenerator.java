package com.flair.server.exerciseGeneration.exerciseManagement.configBasedGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ConfigBasedExerciseGenerator.ExerciseConfigData;
import com.flair.server.exerciseGeneration.exerciseManagement.JsonComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.JumbledSentencesSettings;
import com.flair.shared.exerciseGeneration.ConfigExerciseSettings;
import com.flair.shared.exerciseGeneration.Pair;

public class JumbledSentencesJsonComponentGenerator extends JsonComponentGenerator {
	
	private static final String taskDescription = "Put the following words in the correct order.";
	
	@Override
	public JsonComponents generateJsonComponents(ArrayList<ExerciseConfigData> config, JsonComponentPreparer preparer,
			ConfigExerciseSettings exerciseSettings) {		
		ContentTypeSettings settings = new JumbledSentencesSettings("exercise" + config.get(0).getActivity() + "_jumbled_sentences");
		settings.setExerciseSettings(exerciseSettings);
		
		ArrayList<Pair<String, Integer>> constructions = new ArrayList<>();
		ArrayList<String> plainTextElements = new ArrayList<>();
		for(int i = 0; i < preparer.getParts().size(); i++) {
			ArrayList<String> line = preparer.getParts().get(i);
			StringBuilder sb = new StringBuilder();
			for(String part : line) {
				sb.append("<span data-blank=\"" + constructions.size() + "\"></span>");
				constructions.add(new Pair<>(part, i + 1));
			}
			plainTextElements.add(sb.toString().replaceAll(" +", " ").trim());
		}
		
		return new JsonComponents(plainTextElements, preparer.getPureHtmlElements(), constructions,
                settings.getJsonManager(), settings.getContentTypeLibrary(), settings.getResourceFolder(), 
                new ArrayList<>(), taskDescription, null, settings);
	}
}
