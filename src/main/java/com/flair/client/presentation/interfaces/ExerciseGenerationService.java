package com.flair.client.presentation.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.IExerciseSettings;

/*
 * Generates exercises for the selected document
 */
public interface ExerciseGenerationService {
	
	interface GenerateHandler {
		boolean handle(ArrayList<IExerciseSettings> settings);
	}
	
	interface InterruptHandler {
		void handle();
	}


	void provideForDownload(byte[] file, String fileName, HashMap<String, String> previews);

	void setGenerateHandler(GenerateHandler handler);
	void setInterruptHandler(InterruptHandler handler);

	void enableButton();
}
