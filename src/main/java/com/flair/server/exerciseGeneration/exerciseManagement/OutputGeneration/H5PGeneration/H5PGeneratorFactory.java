package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.H5PGeneration;

import com.flair.shared.exerciseGeneration.ExerciseType;

public class H5PGeneratorFactory {
		
	public static ContentJsonGenerator getContentJsonGenerator(ExerciseType type) {
		if(type == ExerciseType.FIB) {
    		return new AdvancedFIBContentJsonGenerator(true);  	
        } else if(type == ExerciseType.SINGLE_CHOICE) {
        	return new AdvancedFIBContentJsonGenerator(false);
        } else if(type == ExerciseType.DRAG_SINGLE) {
        	return new DDContentJsonGenerator();
        } else if(type == ExerciseType.DRAG_MULTI) {
        	return new DDContentJsonGenerator();
        } else if(type == ExerciseType.MARK) {
        	return new MtWContentJsonGenerator();
        } else if(type == ExerciseType.MEMORY) {
        	return new MemoryContentJsonGenerator();
    	} else if(type == ExerciseType.JUMBLED_SENTENCES) {
    		return new DDContentJsonGenerator();
        } else if(type == ExerciseType.CATEGORIZE) {
        	return new DDContentJsonGenerator();
        } else if(type == ExerciseType.QUIZ) {
        	return new QuizContentJsonGenerator();
        } else {
            throw new IllegalArgumentException();
        }
	}
	
}
