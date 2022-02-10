package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration;

import com.flair.shared.exerciseGeneration.ExerciseTopic;

public class HintGeneratorFactory {
	
    public static HintGenerator getGenerator(String topic) {
		if(topic.equals(ExerciseTopic.RELATIVES)) {
			return new RelativeHintGenerator();
		} 
					
    	return null;
    }
    
}
