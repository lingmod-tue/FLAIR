package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DataStructures.ExerciseComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DataStructures.TargetConstruction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;

public abstract class BracketsGenerator {
	
	public abstract void generateBrackets(ExerciseSettings exerciseSettings, ExerciseComponents exerciseComponents);
	public abstract ArrayList<String> generateBracketsContent(int constructionId, TargetConstruction construction);
	
}
