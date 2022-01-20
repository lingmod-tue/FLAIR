package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.DistractorGeneration;

import java.util.HashSet;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.NlpManager;

public class RelativesSCDistractorGenerator extends SCDistractorGenerator {
	
	@Override
	protected void generateDistractorValues(NlpManager nlpManager, ConstructionTextPart construction, HashSet<String> options, 
		HashSet<String> incorrectFormOptions, ExerciseData data) {
    	// We make sure to only include those pronouns as distractors which actually occur in the text
    	for(int j = 0; j < data.getParts().size(); j++) {
        	TextPart part2 = data.getParts().get(j);
        	
        	if(part2 instanceof ConstructionTextPart) {
        		ConstructionTextPart construction2 = (ConstructionTextPart)part2;
        		
        		String constructionText2 = data.getPlainText().substring(construction2.getIndicesInPlainText().first, construction2.getIndicesInPlainText().second);
        		options.add(constructionText2);
        	}
    	}
	}

}
