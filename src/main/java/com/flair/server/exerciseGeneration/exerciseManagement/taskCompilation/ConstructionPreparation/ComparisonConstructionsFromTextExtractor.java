package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.Pair;

public class ComparisonConstructionsFromTextExtractor extends ConstructionsFromTextExtractor {
	
	public ComparisonConstructionsFromTextExtractor(NlpManager nlpManager) {
		super(nlpManager);
	}
    
	@Override
	protected void extractMtWConstructions(Construction construction) {
        //TODO: if we decide to allow the client to specify whether to split synthetic forms for mark, we have to do that here		
	}

	@Override
	protected void extractDDSingleConstructions(Construction construction, 
			ArrayList<BracketsProperties> bracketsProperties) {
		if(construction.getConstructionIndices().second - construction.getConstructionIndices().first > 30) {                	
			Pair<Integer,Integer> mainComparison = nlpManager.getMainComparison(construction.getConstructionIndices());
			if(mainComparison != null) {
				construction.setConstructionIndices(mainComparison);
			}
		}
	}
	
}
