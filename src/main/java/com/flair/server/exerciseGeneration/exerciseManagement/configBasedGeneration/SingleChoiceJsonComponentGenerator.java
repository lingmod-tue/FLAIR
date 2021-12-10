package com.flair.server.exerciseGeneration.exerciseManagement.configBasedGeneration;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.flair.server.exerciseGeneration.exerciseManagement.ConfigBasedExerciseGenerator.ExerciseConfigData;
import com.flair.server.exerciseGeneration.exerciseManagement.JsonComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.SingleChoiceSettings;
import com.flair.shared.exerciseGeneration.ConfigExerciseSettings;
import com.flair.shared.exerciseGeneration.Pair;

public class SingleChoiceJsonComponentGenerator extends JsonComponentGenerator {
	public SingleChoiceJsonComponentGenerator(int nDistractors) {
		this.nDistractors = nDistractors;
	}
	
	private static final String taskDescription = "Choose the correct answer.";
	private int nDistractors;
	
	@Override
	public JsonComponents generateJsonComponents(ArrayList<ExerciseConfigData> config, JsonComponentPreparer preparer,
			ConfigExerciseSettings exerciseSettings) {
		ContentTypeSettings settings = new SingleChoiceSettings("exercise" + config.get(0).getActivity() + "_mulitple_choice_" + (nDistractors + 1));
		settings.setExerciseSettings(exerciseSettings);
		
		ArrayList<ArrayList<Pair<String, String>>> distractors = new ArrayList<>();
		for(ArrayList<Pair<String, String>> currentDistractors : preparer.getDistractors()) {
			ArrayList<Pair<String, String>> selectedDistractors = new ArrayList<>(currentDistractors);
			while(nDistractors < selectedDistractors.size()) {
				int i = ThreadLocalRandom.current().nextInt(0, selectedDistractors.size());
				selectedDistractors.remove(i);
			}
			distractors.add(selectedDistractors);
		}
		
		return new JsonComponents(new ArrayList<>(preparer.getPlainTextElements()), preparer.getPureHtmlElements(), preparer.getConstructions(),
                settings.getJsonManager(), settings.getContentTypeLibrary(), settings.getResourceFolder(), 
                distractors, taskDescription, null, settings);
	}
}
