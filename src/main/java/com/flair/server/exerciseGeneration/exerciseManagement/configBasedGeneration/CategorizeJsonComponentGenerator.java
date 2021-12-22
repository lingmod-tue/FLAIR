package com.flair.server.exerciseGeneration.exerciseManagement.configBasedGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ConfigBasedExerciseGenerator.ExerciseConfigData;
import com.flair.server.exerciseGeneration.exerciseManagement.JsonComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.CategorizeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.JumbledSentencesSettings;
import com.flair.shared.exerciseGeneration.ConfigExerciseSettings;
import com.flair.shared.exerciseGeneration.Pair;

public class CategorizeJsonComponentGenerator extends JsonComponentGenerator {
	
	private static final String taskDescription = "Put the items in the correct category.";
	
	@Override
	public JsonComponents generateJsonComponents(ArrayList<ExerciseConfigData> config, JsonComponentPreparer preparer,
			ConfigExerciseSettings exerciseSettings) {	
		if(preparer instanceof ConditionalJsonComponentPreparer) {
			ContentTypeSettings settings = new CategorizeSettings("exercise" + config.get(0).getActivity() + "_categorize");
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
			
			JsonComponents jsonComponents = new JsonComponents(plainTextElements, preparer.getPureHtmlElements(), constructions,
	                settings.getJsonManager(), settings.getContentTypeLibrary(), settings.getResourceFolder(), 
	                new ArrayList<>(), taskDescription, null, settings);
			jsonComponents.setConditionalType(((ConditionalJsonComponentPreparer)preparer).getConditionalTypes());
			
			return jsonComponents;
		}

		return null;
	}
}
