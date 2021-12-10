package com.flair.server.exerciseGeneration.exerciseManagement.configBasedGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ConfigBasedExerciseGenerator.ExerciseConfigData;
import com.flair.server.exerciseGeneration.exerciseManagement.JsonComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.FindSettings;
import com.flair.shared.exerciseGeneration.ConfigExerciseSettings;

public class FindJsonComponentGenerator extends JsonComponentGenerator {
	
	private static final String taskDescription = "";

	@Override
	public JsonComponents generateJsonComponents(ArrayList<ExerciseConfigData> config, JsonComponentPreparer preparer,
			ConfigExerciseSettings exerciseSettings) {		
		ContentTypeSettings settings = new FindSettings("exercise" + config.get(0).getActivity() + "_underline");
		settings.setExerciseSettings(exerciseSettings);
				
		return new JsonComponents(new ArrayList<>(preparer.getPlainTextElements()), preparer.getPureHtmlElements(), preparer.getConstructions(),
                settings.getJsonManager(), settings.getContentTypeLibrary(), settings.getResourceFolder(), 
                preparer.getDistractors(), taskDescription, null, settings);
	}
}
