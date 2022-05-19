package com.flair.client.presentation.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import com.flair.shared.exerciseGeneration.DocumentExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseSettings;

/*
 * Generates exercises for the selected document
 */
public interface ExerciseGenerationService {
	
	interface GenerateHandler {
		boolean handle(ArrayList<ExerciseSettings> settings);
	}
	
	interface InterruptHandler {
		void handle();
	}


	void provideForDownload(byte[] file, String fileName, HashMap<String, String> previews, Integer linkingId);

	void setGenerateHandler(GenerateHandler handler);
	void setInterruptHandler(InterruptHandler handler);

	void enableButton();
}
