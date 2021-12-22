package com.flair.server.exerciseGeneration.exerciseManagement.configBasedGeneration;

import java.util.ArrayList;
import java.util.Collections;

import com.flair.server.exerciseGeneration.exerciseManagement.ConfigBasedExerciseGenerator.ExerciseConfigData;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.JsonManager;
import com.flair.shared.exerciseGeneration.Pair;
import com.flair.server.exerciseGeneration.exerciseManagement.JsonComponents;

public class ConditionalJsonComponentPreparer extends JsonComponentPreparer {
	
	private static final String punctuations = ".,:;!?";
	
	public ConditionalJsonComponentPreparer(ArrayList<ExerciseConfigData> config) {
		super(config);
	}
	
	private ArrayList<Integer> conditionalTypes = new ArrayList<>();

	public ArrayList<Integer> getConditionalTypes() { return conditionalTypes; }
	
}
