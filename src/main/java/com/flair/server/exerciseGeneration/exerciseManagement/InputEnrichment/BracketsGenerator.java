package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.NlpManager;
import com.flair.shared.exerciseGeneration.BracketsProperties;

public abstract class BracketsGenerator {

	protected abstract void generateBracketsContents(NlpManager nlpManager, ConstructionTextPart construction, 
			ArrayList<BracketsProperties> bracketsProperties, ExerciseData data, ArrayList<String> lemmas, int i);
		
	/**
	 * Generates brackets texts for FiB tasks.
	 * @param exerciseSettings	The exercise settings
	 * @param parser			The Stanford CoreNLP parser
	 * @param generator			The Simple NLG generator
	 */
    public void generateBrackets(NlpManager nlpManager, ExerciseData data) {
        ArrayList<String> lemmas = new ArrayList<>();

        for(int i = 0; i < data.getParts().size(); i++) {
        	TextPart part = data.getParts().get(i);
        	
        	if(part instanceof ConstructionTextPart) {
        		ConstructionTextPart construction = (ConstructionTextPart)part;
        		generateBracketsContents(nlpManager, construction, data.getBracketsProperties(), data, lemmas, i);
        	}
        }
        
        if(data.getBracketsProperties().contains(BracketsProperties.DISTRACTOR_LEMMA)) {
        	for(int i = 0; i < data.getParts().size(); i++) {
            	TextPart part = data.getParts().get(i);
            	
            	if(part instanceof ConstructionTextPart) {
            		ConstructionTextPart construction = (ConstructionTextPart)part;
            
                	String distractorLemma = "";
	        		if(lemmas.size() > 1) {
	        			// we search for a distractor lemma that is different from the correct lemma
	        			while(distractorLemma.equals("") || 
	        					construction.getBrackets().contains(distractorLemma + "|" + DISTRACTOR_PLACEHOLDER) || 
	        					construction.getBrackets().contains(DISTRACTOR_PLACEHOLDER + "|" + distractorLemma)) {
		        			Collections.shuffle(lemmas);
		        			distractorLemma = lemmas.get(0);
	        			}
	        			
	        			for(int j = 0; j < construction.getBrackets().size(); j++) {
	        				if(construction.getBrackets().get(j).contains(DISTRACTOR_PLACEHOLDER)) {
	        					construction.getBrackets().set(j, construction.getBrackets().get(j).replace(DISTRACTOR_PLACEHOLDER, distractorLemma));
	        				}
	        			}
	        		} else {
	        			for(int j = construction.getBrackets().size() - 1; j >= 0; j--) {
	        				if(construction.getBrackets().get(j).contains(DISTRACTOR_PLACEHOLDER)) {
	        					construction.getBrackets().remove(j);
	        				}
	        			}
	        		}
	        	}
        	}
    	}
    }
    
    private static final String DISTRACTOR_PLACEHOLDER = "<distractor_placeholder>";
    
    protected String generateDistractorLemma(String lemma) {
    	int order = new Random().nextInt(2);
		if(order == 0) {
			return DISTRACTOR_PLACEHOLDER + "|" + lemma;
		} else {
			return lemma + "|" + DISTRACTOR_PLACEHOLDER;
		}
    }
    
}
