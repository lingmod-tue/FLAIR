package com.flair.server.exerciseGeneration.exerciseManagement.configBasedGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ConfigBasedExerciseGenerator.ExerciseConfigData;
import com.flair.server.exerciseGeneration.exerciseManagement.JsonComponents;
import com.flair.shared.exerciseGeneration.ConfigExerciseSettings;

public abstract class JsonComponentGenerator {

	public abstract JsonComponents generateJsonComponents(ArrayList<ExerciseConfigData> config, JsonComponentPreparer preparer,
			ConfigExerciseSettings exerciseSettings);
	
}
