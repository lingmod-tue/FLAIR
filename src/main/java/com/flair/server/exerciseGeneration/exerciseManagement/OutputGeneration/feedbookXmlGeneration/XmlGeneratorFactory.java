package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration;

import com.flair.shared.exerciseGeneration.ExerciseType;

public class XmlGeneratorFactory {
	
	public static SimpleExerciseXmlGenerator getXmlGenerator(String exerciseType) {
		if(exerciseType.equals(ExerciseType.MEMORY)) {
			return new MemoryXmlGenerator();
		} else if(exerciseType.equals(ExerciseType.SINGLE_CHOICE)) {
			return new SCXmlGenerator();
		} else if(exerciseType.equals(ExerciseType.FILL_IN_THE_BLANKS) || exerciseType.equals(ExerciseType.HALF_OPEN)) {
			return new FiBXmlGenerator();
		} else if(exerciseType.equals(ExerciseType.JUMBLED_SENTENCES)) {
			return new JSXmlGenerator();
		} else if(exerciseType.equals(ExerciseType.CATEGORIZE)) {
			return new CategorizeXmlGenerator();
		} else if(exerciseType.equals(ExerciseType.MARK_THE_WORDS)) {
			return new MtWXmlGenerator();
		} else if(exerciseType.equals(ExerciseType.DRAG_AND_DROP_SINGLE)) {
			return new DDSingleXmlGenerator();
		} else if(exerciseType.equals(ExerciseType.SHORT_ANSWER)) {
			return new SAXmlGenerator();
		} else {
			throw new IllegalArgumentException();
		}
	}
	
}
