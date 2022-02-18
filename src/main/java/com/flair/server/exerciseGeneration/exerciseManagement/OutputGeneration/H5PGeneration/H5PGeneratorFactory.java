package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.H5PGeneration;

import com.flair.shared.exerciseGeneration.ExerciseType;

public class H5PGeneratorFactory {
		
	public static ContentJsonGenerator getContentJsonGenerator(String type) {
		if(type.equals(ExerciseType.FILL_IN_THE_BLANKS) || type.equals(ExerciseType.SHORT_ANSWER) || 
				type.equals(ExerciseType.HALF_OPEN) || type.equals(ExerciseType.SHORT_ANSWER)) {
    		return new AdvancedFiBContentJsonGenerator(true);  	
        } else if(type.equals(ExerciseType.SINGLE_CHOICE)) {
        	return new AdvancedFiBContentJsonGenerator(false);
        } else if(type.equals(ExerciseType.DRAG_AND_DROP_SINGLE)) {
        	return new DDContentJsonGenerator();
        } else if(type.equals(ExerciseType.DRAG_AND_DROP_MULTI)) {
        	return new DDContentJsonGenerator();
        } else if(type.equals(ExerciseType.MARK_THE_WORDS)) {
        	return new MtWContentJsonGenerator();
        } else if(type.equals(ExerciseType.MEMORY)) {
        	return new MemoryContentJsonGenerator();
    	} else if(type.equals(ExerciseType.JUMBLED_SENTENCES)) {
    		return new DDContentJsonGenerator();
        } else if(type.equals(ExerciseType.CATEGORIZE)) {
        	return new DDContentJsonGenerator();
        } else if(type.equals(ExerciseType.QUIZ)) {
        	return new QuizContentJsonGenerator();
        } else {
            throw new IllegalArgumentException();
        }
	}
	
}
