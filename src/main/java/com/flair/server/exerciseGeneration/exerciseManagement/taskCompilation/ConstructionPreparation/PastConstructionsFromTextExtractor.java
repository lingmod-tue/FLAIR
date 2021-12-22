package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.Pair;

import edu.stanford.nlp.ling.CoreLabel;

public class PastConstructionsFromTextExtractor extends ConstructionsFromTextExtractor {
	
	public PastConstructionsFromTextExtractor(NlpManager nlpManager) {
		super(nlpManager);
	}

	@Override
	protected void extractDDSingleConstructions(Construction construction, 
			ArrayList<BracketsProperties> bracketsProperties) {
		if(construction.getConstructionIndices().second - construction.getConstructionIndices().first > 30) {
			CoreLabel mainVerb = nlpManager.getMainVerb(construction.getConstructionIndices());
			if(mainVerb != null) {
				construction.setConstructionIndices(new Pair<>(mainVerb.beginPosition(), mainVerb.endPosition()));
			}
		}
	}
	
}
