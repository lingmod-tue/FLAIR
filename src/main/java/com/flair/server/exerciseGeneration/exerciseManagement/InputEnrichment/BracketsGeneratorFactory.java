package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseTopic;

public class BracketsGeneratorFactory {
	
    public static BracketsGenerator getGenerator(ExerciseTopic topic) {
		if(topic == ExerciseTopic.CONDITIONALS) {     
			return new ConditionalBracketsGenerator();
        } else if(topic == ExerciseTopic.COMPARISON) {
        	return new ComparisonBracketsGenerator();
        } else if(topic == ExerciseTopic.PASSIVE) {
	    	return new PassiveBracketsGenerator();
        } else if(topic == ExerciseTopic.PRESENT || topic == ExerciseTopic.PAST) {
        	return new TenseBracketsGenerator();
    	} else {
    		throw new IllegalArgumentException();
        }      
    }
    
}
