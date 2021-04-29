package com.flair.client.presentation.interfaces;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.ExerciseSettings;

/*
 * Generates exercises for the selected document
 */
public interface ExerciseGenerationService {
	interface GenerationCompleteHandler {
		void handle(byte[] file);
	}
	
	interface GenerateHandler {
		boolean handle(ArrayList<ExerciseSettings> settings);
	}


	void provideForDownload(byte[] file);

	void setGenerateHandler(GenerateHandler handler);
	
	void setGenerationCompleteHandler(GenerationCompleteHandler handler);
	void enableButton();
}
