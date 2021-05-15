package com.flair.client.presentation.interfaces;

import java.util.ArrayList;

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


	void provideForDownload(byte[] file, String fileName);

	void setGenerateHandler(GenerateHandler handler);
	void setInterruptHandler(InterruptHandler handler);

	void enableButton();
}
