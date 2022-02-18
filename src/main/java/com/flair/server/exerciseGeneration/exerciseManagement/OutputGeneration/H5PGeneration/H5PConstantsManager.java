package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.H5PGeneration;

import com.flair.shared.exerciseGeneration.ExerciseType;

public class H5PConstantsManager {
    
    /**
	 * Determines the H5P resource folder.
	 * @param type	The exercise type
	 * @return	The name of the resource folder
	 */
	public static String getResourceFolder(String type) {
    	if(type.equals(ExerciseType.FILL_IN_THE_BLANKS) || type.equals(ExerciseType.SINGLE_CHOICE) || 
				type.equals(ExerciseType.HALF_OPEN) || type.equals(ExerciseType.SHORT_ANSWER)) {
        	return "advanced_fib.h5p";
        } else if(type.equals(ExerciseType.DRAG_AND_DROP_SINGLE)) {
        	return "drag_the_words_1task.h5p";
        } else if(type.equals(ExerciseType.DRAG_AND_DROP_MULTI) || type.equals(ExerciseType.JUMBLED_SENTENCES) || 
        		type.equals(ExerciseType.CATEGORIZE)) {
        	return "drag_the_words.h5p";
        } else if(type.equals(ExerciseType.MARK_THE_WORDS)) {
        	return "mark_the_words.h5p";
        } else if(type.equals(ExerciseType.MEMORY)) {
        	return "memory.h5p";
    	} else if(type.equals(ExerciseType.QUIZ)) {
        	return "quiz.h5p";
    	} else {
            throw new IllegalArgumentException();
        }
	}
	
	/**
	 * Determines the H5P content type library.
	 * @param type	The exercise type
	 * @return	The name of the content type library
	 */
    public static String getContentTypeLibrary(String type) {
		if(type.equals(ExerciseType.FILL_IN_THE_BLANKS) || type.equals(ExerciseType.SINGLE_CHOICE) || 
				type.equals(ExerciseType.HALF_OPEN) || type.equals(ExerciseType.SHORT_ANSWER)) {
    		return "H5P.XAdvancedBlanks 0.1";
        } else if(type.equals(ExerciseType.DRAG_AND_DROP_SINGLE)) {
        	return "H5P.XXDragText 0.1";
        } else if(type.equals(ExerciseType.DRAG_AND_DROP_MULTI) || type.equals(ExerciseType.JUMBLED_SENTENCES) || 
        		type.equals(ExerciseType.CATEGORIZE)) {
        	return "H5P.XDragText 1.1";
        } else if(type.equals(ExerciseType.MARK_THE_WORDS)) {
        	return "H5P.XMarkTheWords 0.1";
        } else if(type.equals(ExerciseType.MEMORY)) {
        	return "H5P.XMemoryGame 0.1";
    	} else {
            throw new IllegalArgumentException();
        }
	}
	
}
