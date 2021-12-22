package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DataStructures.TargetConstruction;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Pair;

public class ComparisonBracketsFromTextGenerator extends BracketsFromTextGenerator {
	
	public ComparisonBracketsFromTextGenerator(NlpManager nlpManager, ArrayList<BracketsProperties> bracketsProperties) {
		super(nlpManager, bracketsProperties);
	}
	
	@Override
	public ArrayList<String> generateBracketsContent(int constructionId, TargetConstruction construction) {
		ArrayList<String> brackets = new ArrayList<>();

		if(bracketsProperties.contains(BracketsProperties.LEMMA)) {
            String lemma = nlpManager.getLemmaOfComparison(new Pair<>(construction.getStartIndex(), construction.getEndIndex()));
            if(lemma != null) {
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
        }
        if(bracketsProperties.contains(BracketsProperties.POS)) {
            String pos = construction.getType().toString().startsWith("ADJ") ? "adjective" : "adverb";
            brackets.add(pos);
        }

        if(bracketsProperties.contains(BracketsProperties.COMPARISON_FORM)) {
            String form = construction.getType().toString().contains("_COMP_") ? "comparative" : "superlative";
            brackets.add(form);
        }
        
        return brackets;
	}
}
