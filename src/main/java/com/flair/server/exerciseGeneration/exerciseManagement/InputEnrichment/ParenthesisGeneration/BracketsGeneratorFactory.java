package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.ParenthesisGeneration;

import com.flair.shared.exerciseGeneration.ExerciseTopic;

public class BracketsGeneratorFactory {
	
    public static BracketsGenerator getGenerator(String topic) {
		if(topic.equals(ExerciseTopic.CONDITIONALS)) {     
			return new ConditionalBracketsGenerator();
        } else if(topic.equals(ExerciseTopic.COMPARISON)) {
        	return new ComparisonBracketsGenerator();
        } else if(topic.equals(ExerciseTopic.PASSIVE)) {
	    	return new PassiveBracketsGenerator();
        } else if(topic.equals(ExerciseTopic.PRESENT) || topic.equals(ExerciseTopic.PAST)) {
        	return new TenseBracketsGenerator();
    	} else {
    		return null;
        }      
    }
    
}
