package com.flair.server.exerciseGeneration.exerciseManagement.temp;

import com.flair.server.parser.SimpleNlgParser;

public class FormGeneratorFactory {
	
    public FormGenerator getGenerator(ParameterSettings settings, SimpleNlgParser generator) {
    	if(settings instanceof TenseSettings) {
    		if(((TenseSettings) settings).getTense().equals("present")) {
                if(((TenseSettings) settings).isPerfect()) {
                	if(((TenseSettings) settings).isProgressive()) {
                		return new PresentPerfectProgressiveGenerator(generator);
                	} else {
                		return new PresentPerfectGenerator(generator);
                	}
                } else {
                    return new SimplePresentGenerator(generator);
                }
            } else if(((TenseSettings) settings).getTense().equals("past")) {
                if(((TenseSettings) settings).isPerfect()) {
                	if(((TenseSettings) settings).isProgressive()) {
                		return new PastPerfectProgressiveGenerator(generator);
                	} else {
                		return new PastPerfectGenerator(generator);
                	}
                } else {
                	if(((TenseSettings) settings).isProgressive()) {
                		return new PastProgressiveGenerator(generator);
                	} else {
                		return new SimplePastGenerator(generator);
                	}
                }
            } else if(((TenseSettings) settings).getTense().equals("future")) {
                if(((TenseSettings) settings).isPerfect()) {
                    return new FuturePerfectGenerator(generator);
                } else {
                    return new FutureSimpleGenerator(generator);
                }
            } else {
                throw new IllegalArgumentException();
            }
    	} else {
    		return new ComparisonGenerator(generator);
        }      
    }
    
}
