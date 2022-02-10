package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import com.flair.shared.exerciseGeneration.ExerciseTopic;

public class ExcelFileReaderFactory {
	
    public static ExcelFileReader getReader(String topic, int sheet) {
		if(topic.equals(ExerciseTopic.CONDITIONALS)) {   
			if(sheet == 0) {
				return new ConditionalExcelFileReader();
			} else {
				return null;
			}
        } else if(topic.equals(ExerciseTopic.RELATIVES)) {
        	if(sheet == 0) {
        		return new RelativeExcelFileReader();
        	} else if(sheet == 1) {
        		return new RelativeContactExcelFileReader();
        	} else {
        		return null;
        	}
        } else {
    		throw new IllegalArgumentException();
        }      
    }
    
}
