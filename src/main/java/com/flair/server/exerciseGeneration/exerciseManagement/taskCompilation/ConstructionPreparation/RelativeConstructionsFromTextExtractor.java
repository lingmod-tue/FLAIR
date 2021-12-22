package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.Pair;

public class RelativeConstructionsFromTextExtractor extends ConstructionsFromTextExtractor {
	
	public RelativeConstructionsFromTextExtractor(NlpManager nlpManager, String plainText) {
		super(nlpManager);
		this.plainText = plainText;
	}

    private final String plainText;
    
	@Override
	protected void extractDDMultiConstructions(Construction construction, 
			ArrayList<BracketsProperties> bracketsProperties) {
		ArrayList<Pair<Integer,Integer>> components = nlpManager.getRelativeClauseComponents(construction.getConstructionIndices());
    	ArrayList<Construction> newConstructions = new ArrayList<>();
    	for(Pair<Integer, Integer> component : components) {
    		if(!plainText.substring(component.first, component.second).matches("[\\p{Punct}\\s\\h]*")) {
    			newConstructions.add(new Construction(construction.getConstruction(), component));
    		}
    	}
    	
    	addConstructionsInSentence(newConstructions);
    	
    	constructionsToAdd.addAll(newConstructions);
    	constructionsToRemove.add(construction);
	}

}
