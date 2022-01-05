package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

import com.flair.shared.exerciseGeneration.ExerciseType;

public class XmlGeneratorFactory {
	
	public static SimpleExerciseXmlGenerator getXmlGenerator(String exerciseType) {
		if(exerciseType.equals("0")) {
			return new MemoryXmlGenerator();
		} else if(exerciseType.equals("1") || exerciseType.equals("2")) {
			return new SCXmlGenerator();
		} else if(exerciseType.equals("3") || exerciseType.equals("4") || exerciseType.equals("5") || exerciseType.equals("9")) {
			return new FibXmlGenerator();
		} else if(exerciseType.equals("6")) {
			return new JumbledSentencesXmlGenerator();
		} else if(exerciseType.equals("7")) {
			return new CategorizeXmlGenerator();
		} else if(exerciseType.equals("8")) {
			return new FindXmlGenerator();
		} else {
			throw new IllegalArgumentException();
		}
	}
	
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
