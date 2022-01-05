package com.flair.server.exerciseGeneration.InputEnrichment;

import com.flair.server.exerciseGeneration.ConstructionTextPart;
import com.flair.server.exerciseGeneration.Distractor;
import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.PlainTextPart;
import com.flair.server.exerciseGeneration.TextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;

public class DDMultiDistractorGenerator extends DistractorGenerator {

	@Override
	public boolean generateDistractors(NlpManager nlpManager, ExerciseData data) {
    	for(int i = 0; i < data.getParts().size(); i++) {
        	TextPart part = data.getParts().get(i);
        	
        	if(part instanceof ConstructionTextPart) {
        		ConstructionTextPart construction = (ConstructionTextPart)part;
        		
        		String constructionText = data.getPlainText().substring(construction.getIndicesInPlainText().first, 
        				construction.getIndicesInPlainText().second);

        		for(int j = 0; j < data.getParts().size(); j++) {
                	TextPart part2 = data.getParts().get(j);
                	
                	if(part2 instanceof ConstructionTextPart && 
                			((ConstructionTextPart)part2).getSentenceId() == part.getSentenceId()) {
        				String distractor = data.getPlainText().substring(((ConstructionTextPart)part2).getIndicesInPlainText().first, 
        						((ConstructionTextPart)part2).getIndicesInPlainText().second);
                		if(!constructionText.equals(distractor)) {
                			Distractor d = new Distractor(distractor);
                        	d.setIllFormed(false);
                        	construction.getDistractors().add(d); 
                		}
                	}
        		}
                		
        		if(construction.getDistractors().size() < 1) {
                	data.getParts().set(i, new PlainTextPart(construction.getValue(), construction.getSentenceId()));
        		}
        	}
    	}
        
        return true;
    }
    
}
