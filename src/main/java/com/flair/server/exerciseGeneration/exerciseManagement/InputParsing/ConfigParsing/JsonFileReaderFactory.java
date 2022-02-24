package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import com.flair.shared.exerciseGeneration.ExerciseTopic;

public class JsonFileReaderFactory {
	
    public static JsonFileReader getReader(String topic) {
		if(topic.equals(ExerciseTopic.CONDITIONALS)) {   
			return new ConditionalJsonReader();
        } else {
    		throw new IllegalArgumentException();
        }      
    }
    
}
