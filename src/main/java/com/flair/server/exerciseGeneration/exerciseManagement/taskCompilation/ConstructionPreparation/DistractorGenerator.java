package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DataStructures.ExerciseComponents;
import com.flair.shared.exerciseGeneration.ExerciseSettings;

public abstract class DistractorGenerator {
	
	public abstract void generateDistractors(ExerciseSettings exerciseSettings, ExerciseComponents exerciseComponents);
	
}
