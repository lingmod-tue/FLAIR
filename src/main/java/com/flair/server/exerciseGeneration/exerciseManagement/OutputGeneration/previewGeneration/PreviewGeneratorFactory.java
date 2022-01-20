package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration;

import com.flair.shared.exerciseGeneration.ExerciseType;

public class PreviewGeneratorFactory {
		
	public static PreviewGenerator getPreviewGenerator(String exerciseType) {
		if(exerciseType.equals(ExerciseType.MEMORY)) {
			return new MemoryPreviewGenerator();
		} else if(exerciseType.equals(ExerciseType.SINGLE_CHOICE)) {
			return new SCPreviewGenerator();
		} else if(exerciseType.equals(ExerciseType.FILL_IN_THE_BLANKS)) {
			return new FiBPreviewGenerator();
		} else if(exerciseType.equals(ExerciseType.JUMBLED_SENTENCES)) {
			return new DDPreviewGenerator();
		} else if(exerciseType.equals(ExerciseType.CATEGORIZE)) {
			return new CategorizePreviewGenerator();
		} else if(exerciseType.equals(ExerciseType.MARK_THE_WORDS)) {
			return new MtWPreviewGenerator();
		} else if(exerciseType.equals(ExerciseType.DRAG_AND_DROP_SINGLE)) {
			return new DDPreviewGenerator();
		} else {
			throw new IllegalArgumentException();
		}
	}
	
}
