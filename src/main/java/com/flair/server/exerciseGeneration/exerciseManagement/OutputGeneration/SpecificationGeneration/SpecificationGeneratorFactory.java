package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.SpecificationGeneration;

import com.flair.shared.exerciseGeneration.ExerciseTopic;

public class SpecificationGeneratorFactory {
		
	public static SpecificationGenerator getGenerator(String topic) {
		if(topic.equals(ExerciseTopic.CONDITIONALS)) {
    		return new ConditionalSpecificationGenerator();  	
        } else {
            throw new IllegalArgumentException();
        }
	}
	
}
