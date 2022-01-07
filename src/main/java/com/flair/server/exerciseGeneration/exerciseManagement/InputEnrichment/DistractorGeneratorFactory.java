package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseTopic;
import com.flair.shared.exerciseGeneration.ExerciseType;

public class DistractorGeneratorFactory {
	
    public static DistractorGenerator getGenerator(ExerciseTopic topic, ExerciseType type) {
        if(type == ExerciseType.SINGLE_CHOICE) {
        	if(topic == ExerciseTopic.CONDITIONALS) {     
    			return new ConditionalSCDistractorGenerator();
            } else if(topic == ExerciseTopic.COMPARISON) {
            	return new ComparisonSCDistractorGenerator();
            } else if(topic == ExerciseTopic.PAST) {
    	    	return new PastSCDistractorGenerator();
            } else if(topic == ExerciseTopic.PRESENT) {
            	return new PresentSCDistractorGenerator();
        	} else {
        		throw new IllegalArgumentException();
            }            
        } else if(type == ExerciseType.DRAG_SINGLE) {
        	return new DDSingleDistractorGenerator();
        } else if(type == ExerciseType.DRAG_MULTI) {
        	return new DDMultiDistractorGenerator();
        } else if(type == ExerciseType.MEMORY) {
        	return new MemoryDistractorGenerator();
        } else {
        	return null;
        }
    }
    
}
