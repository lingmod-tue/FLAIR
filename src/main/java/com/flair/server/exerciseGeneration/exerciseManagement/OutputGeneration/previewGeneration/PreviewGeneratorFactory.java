package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration;

import com.flair.shared.exerciseGeneration.ExerciseType;

public class PreviewGeneratorFactory {
		
	public static PreviewGenerator getPreviewGenerator(ExerciseType exerciseType) {
		if(exerciseType == ExerciseType.MEMORY) {
			return new MemoryPreviewGenerator();
		} else if(exerciseType == ExerciseType.SINGLE_CHOICE) {
			return new SCPreviewGenerator();
		} else if(exerciseType == ExerciseType.FIB) {
			return new FIBPreviewGenerator();
		} else if(exerciseType == ExerciseType.JUMBLED_SENTENCES) {
			return new DDPreviewGenerator();
		} else if(exerciseType == ExerciseType.CATEGORIZE) {
			return new CategorizePreviewGenerator();
		} else if(exerciseType == ExerciseType.MARK) {
			return new MtWPreviewGenerator();
		} else if(exerciseType == ExerciseType.DRAG_SINGLE) {
			return new DDPreviewGenerator();
		} else {
			throw new IllegalArgumentException();
		}
	}
	
}
