package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.DistractorGeneration;

import com.flair.shared.exerciseGeneration.ExerciseTopic;
import com.flair.shared.exerciseGeneration.ExerciseType;

public class DistractorGeneratorFactory {
	
    public static DistractorGenerator getGenerator(String topic, String type) {
        if(type.equals(ExerciseType.SINGLE_CHOICE)) {
        	if(topic.equals(ExerciseTopic.CONDITIONALS)) {     
    			return new ConditionalSCDistractorGenerator();
            } else if(topic.equals(ExerciseTopic.COMPARISON)) {
            	return new ComparisonSCDistractorGenerator();
            } else if(topic.equals(ExerciseTopic.PAST)) {
    	    	return new PastSCDistractorGenerator();
            } else if(topic.equals(ExerciseTopic.PRESENT)) {
            	return new PresentSCDistractorGenerator();
        	} else if(topic.equals(ExerciseTopic.RELATIVES)) {
            	return new RelativesSCDistractorGenerator();
        	} else {
        		throw new IllegalArgumentException();
            }            
        } else if(type.equals(ExerciseType.DRAG_AND_DROP_SINGLE)) {
        	return new DDSingleDistractorGenerator();
        } else if(type.equals(ExerciseType.DRAG_AND_DROP_MULTI)) {
        	return new DDMultiDistractorGenerator();
        } else if(type.equals(ExerciseType.MEMORY)) {
        	return new MemoryDistractorGenerator();
        } else {
        	return null;
        }
    }
    
}
