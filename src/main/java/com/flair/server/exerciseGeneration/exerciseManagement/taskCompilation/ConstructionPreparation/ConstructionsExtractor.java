package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DataStructures.ExerciseComponents;
import com.flair.shared.exerciseGeneration.ExerciseSettings;

public abstract class ConstructionsExtractor {

	public abstract ExerciseComponents prepareConstructions(ExerciseSettings exerciseSettings);
	
}
