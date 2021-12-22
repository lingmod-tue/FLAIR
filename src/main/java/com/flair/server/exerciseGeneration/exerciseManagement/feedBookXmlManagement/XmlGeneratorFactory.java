package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

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
	
}
