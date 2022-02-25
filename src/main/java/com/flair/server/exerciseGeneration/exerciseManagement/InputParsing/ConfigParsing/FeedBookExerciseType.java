package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

public class FeedBookExerciseType {

	public static final String MEMORY = "Memory";
	public static final String SINGLE_CHOICE_2D = "SingleChoice2options";
	public static final String SINGLE_CHOICE_4D = "SingleChoice4options";
	public static final String FIB_LEMMA_PARENTHESES = "Fill-in-the-Blankslemmainparentheses";
	public static final String FIB_LEMMA_DISTRACTOR_PARENTHESES = "Fill-in-the-Blankslemmadistractorinparentheses";
	public static final String FIB_LEMMA_INSTRUCTIONS = "Fill-in-the-Blankslemmasininstructions";
	public static final String JUMBLED_SENTENCES = "JumbledSentences";
	public static final String CATEGORIZATION = "Categorize";
	public static final String UNDERLINE = "MarktheWords";
	public static final String HALF_OPEN = "Half-open";
	
	public static String getContainedType(String str) {
		if(str.contains(MEMORY)) {
			return MEMORY;
		} else if(str.contains(SINGLE_CHOICE_2D)) {
			return SINGLE_CHOICE_2D;
		} else if(str.contains(SINGLE_CHOICE_4D)) {
			return SINGLE_CHOICE_4D;
		} else if(str.contains(FIB_LEMMA_PARENTHESES)) {
			return FIB_LEMMA_PARENTHESES;
		} else if(str.contains(FIB_LEMMA_DISTRACTOR_PARENTHESES)) {
			return FIB_LEMMA_DISTRACTOR_PARENTHESES;
		} else if(str.contains(FIB_LEMMA_INSTRUCTIONS)) {
			return FIB_LEMMA_INSTRUCTIONS;
		} else if(str.contains(JUMBLED_SENTENCES)) {
			return JUMBLED_SENTENCES;
		} else if(str.contains(CATEGORIZATION)) {
			return CATEGORIZATION;
		} else if(str.contains(UNDERLINE)) {
			return UNDERLINE;
		} else if(str.contains(HALF_OPEN)) {
			return HALF_OPEN;
		}
		
		return null;
	}
	
	public static int getFeedbookId(String exerciseType) {
		if(exerciseType.equals(MEMORY)) {
			return 0;
		} else if(exerciseType.equals(SINGLE_CHOICE_2D)) {
			return 1;
		} else if(exerciseType.equals(SINGLE_CHOICE_4D)) {
			return 2;
		} else if(exerciseType.equals(FIB_LEMMA_PARENTHESES)) {
			return 3;
		} else if(exerciseType.equals(FIB_LEMMA_DISTRACTOR_PARENTHESES)) {
			return 4;
		} else if(exerciseType.equals(FIB_LEMMA_INSTRUCTIONS)) {
			return 5;
		} else if(exerciseType.equals(JUMBLED_SENTENCES)) {
			return 6;
		} else if(exerciseType.equals(CATEGORIZATION)) {
			return 7;
		} else if(exerciseType.equals(UNDERLINE)) {
			return 8;
		} else if(exerciseType.equals(HALF_OPEN)) {
			return 9;
		} else {
			throw new IllegalArgumentException();
		}
	}
}
