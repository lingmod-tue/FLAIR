package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.Pair;

public class PassiveConstructionsFromTextExtractor extends ConstructionsFromTextExtractor {
	
	public PassiveConstructionsFromTextExtractor(NlpManager nlpManager) {
		super(nlpManager);
	}
	
    private ArrayList<Construction> newConstructions = new ArrayList<>();
    
	@Override
	protected void extractDDSingleConstructions(Construction construction, 
			ArrayList<BracketsProperties> bracketsProperties) {
		extractDDConstructions(construction, bracketsProperties);
	}

	@Override
	protected void extractDDMultiConstructions(Construction construction, 
			ArrayList<BracketsProperties> bracketsProperties) {
		extractDDConstructions(construction, bracketsProperties);	
		addConstructionsInSentence(newConstructions);
	}

	@Override
	protected void extractFiBConstructions(Construction construction, 
			ArrayList<BracketsProperties> bracketsProperties) {
		if(bracketsProperties.contains(BracketsProperties.ACTIVE_SENTENCE)) {
        	Pair<Integer, Integer> sentenceIndices = nlpManager.getSentenceIndices(construction.getConstructionIndices());
            if (sentenceIndices != null) {
                construction.setConstructionIndices(sentenceIndices);
            } else {
                constructionsToRemove.add(construction);
            }
        } 
	}
	
	private void extractDDConstructions(Construction construction, 
			ArrayList<BracketsProperties> bracketsProperties) {
		newConstructions = new ArrayList<>();
		
		ArrayList<Pair<Integer, Integer>> components = nlpManager.getPassiveSentenceComponents(construction.getConstructionIndices());
    	ArrayList<Construction> newConstructions = new ArrayList<>();
    	if(components != null) {
        	for(int i = 1; i <= 3; i++) {
        		if(components.get(i) != null) {
        			if(bracketsProperties.contains(BracketsProperties.VERB_SPLITTING) && i == 2) {
	                    ArrayList<Pair<Integer, Integer>> parts = nlpManager.splitParticiple(components.get(i));
	                    if(parts != null) { // if the splitting wasn't successful, we keep it as entire cluster
	                        for (Pair<Integer, Integer> part : parts) {
	                        	newConstructions.add(new Construction(construction.getConstruction(), part));
	                        }
	                    }
                	} else {                			
                		newConstructions.add(new Construction(construction.getConstruction(), components.get(i)));
                	}
                }
        	}
    	}   	
    	 
    	constructionsToAdd.addAll(newConstructions);
        constructionsToRemove.add(construction);
	}
	
}
