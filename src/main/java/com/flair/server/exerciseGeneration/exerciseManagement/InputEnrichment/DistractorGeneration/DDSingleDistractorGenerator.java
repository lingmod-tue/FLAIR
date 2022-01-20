package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.DistractorGeneration;

import java.util.ArrayList;
import java.util.HashSet;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.Distractor;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.NlpManager;

public class DDSingleDistractorGenerator extends DistractorGenerator {

	@Override
	public boolean generateDistractors(NlpManager nlpManager, ExerciseData data) {        
    	// we set the values of the other draggables as "distractors"
    	ArrayList<String> distractors = new ArrayList<>();

    	for(int i = 0; i < data.getParts().size(); i++) {
        	TextPart part = data.getParts().get(i);
        	
        	if(part instanceof ConstructionTextPart) {
        		ConstructionTextPart construction = (ConstructionTextPart)part;
        		
        		String constructionText = data.getPlainText().substring(construction.getIndicesInPlainText().first, construction.getIndicesInPlainText().second);
        		distractors.add(constructionText);
            }
    	}
    	
    	if(distractors.size() < 1) {
    		// we cannot generate an exercise for Drag & Drop with less than 2 targets
    		return false;
    	}
    	
    	HashSet<String> uniqueDistractors = new HashSet<>(distractors);
    	for(int i = 0; i < data.getParts().size(); i++) {
        	TextPart part = data.getParts().get(i);
        	
        	if(part instanceof ConstructionTextPart) {
        		ConstructionTextPart construction = (ConstructionTextPart)part;
        		
        		String constructionText = data.getPlainText().substring(construction.getIndicesInPlainText().first, 
        				construction.getIndicesInPlainText().second);

        		for(String distractor : uniqueDistractors) {
            		if(!constructionText.equals(distractor)) {
            			Distractor d = new Distractor(distractor);
                    	d.setIllFormed(false);
                    	construction.getDistractors().add(d);                        	
            		}
        		}
            }
    	}
        
        return true;
    }
    
}
