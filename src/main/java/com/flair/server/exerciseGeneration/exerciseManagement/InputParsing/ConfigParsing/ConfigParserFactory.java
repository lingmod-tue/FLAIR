package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import com.flair.shared.exerciseGeneration.ExerciseTopic;

public class ConfigParserFactory {
	
    public static ConfigParser getParser(String topic) {
		if(topic.equals(ExerciseTopic.CONDITIONALS)) {     
			return new ConditionalConfigParser();
        } else if(topic.equals(ExerciseTopic.RELATIVES)) {
        	return new LegacyRelativeConfigParser();
        } else {
    		throw new IllegalArgumentException();
        }      
    }
    
}
