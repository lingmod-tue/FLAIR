package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration;

import com.flair.shared.exerciseGeneration.ExerciseType;

public class XmlGeneratorFactory {
	
	public static SimpleExerciseXmlGenerator getXmlGenerator(ExerciseType exerciseType) {
		if(exerciseType == ExerciseType.MEMORY) {
			return new MemoryXmlGenerator();
		} else if(exerciseType == ExerciseType.SINGLE_CHOICE) {
			return new SCXmlGenerator();
		} else if(exerciseType == ExerciseType.FIB) {
			return new FibXmlGenerator();
		} else if(exerciseType == ExerciseType.JUMBLED_SENTENCES) {
			return new JumbledSentencesXmlGenerator();
		} else if(exerciseType == ExerciseType.CATEGORIZE) {
			return new CategorizeXmlGenerator();
		} else if(exerciseType == ExerciseType.MARK) {
			return new FindXmlGenerator();
		} else if(exerciseType == ExerciseType.DRAG_SINGLE) {
			return new DDSingleXmlGenerator();
		} else {
			throw new IllegalArgumentException();
		}
	}
	
}
