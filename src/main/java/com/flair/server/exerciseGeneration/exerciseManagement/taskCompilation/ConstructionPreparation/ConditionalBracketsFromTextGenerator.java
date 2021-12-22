package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.LemmatizedVerbCluster;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DataStructures.TargetConstruction;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.Pair;

public class ConditionalBracketsFromTextGenerator extends BracketsFromTextGenerator {
	
	public ConditionalBracketsFromTextGenerator(NlpManager nlpManager, 
			ArrayList<BracketsProperties> bracketsProperties) {
		super(nlpManager, bracketsProperties);
	}
	
	@Override
	public ArrayList<String> generateBracketsContent(int constructionId, TargetConstruction construction) {
		ArrayList<String> brackets = new ArrayList<>();
		
		LemmatizedVerbCluster lemmatizedVerb = 
				nlpManager.getLemmatizedVerbConstruction(new Pair<>(construction.getStartIndex(), construction.getEndIndex()), false, false);
    	if(bracketsProperties.contains(BracketsProperties.LEMMA)) {
            if(lemmatizedVerb != null) {
            	String lemma = lemmatizedVerb.getLemmatizedCluster();
            	if(bracketsProperties.contains(BracketsProperties.DISTRACTOR_LEMMA)) {
            		lemmas.add(lemma);
            		brackets.add(generateDistractorLemma(lemma));
            	} else {
                    brackets.add(lemma);
            	}
            } else {
            	constructionsToRemove.add(constructionId);
            	return brackets;
            }
        } else {
        	if(lemmatizedVerb != null) {
        		brackets.add(String.join(" ", lemmatizedVerb.getNonLemmatizedComponents()));
        	}
        }
        
        if (bracketsProperties.contains(BracketsProperties.CONDITIONAL_TYPE)) {
            brackets.add(construction.getType() == DetailedConstruction.CONDREAL ? "real" : "unreal");
        }
        if (construction.getType() == DetailedConstruction.CONDREAL_MAIN && 
        		bracketsProperties.contains(BracketsProperties.WILL)) {
            brackets.add(lemmatizedVerb.getModal());
        }  
        
        return brackets;
	}
}
