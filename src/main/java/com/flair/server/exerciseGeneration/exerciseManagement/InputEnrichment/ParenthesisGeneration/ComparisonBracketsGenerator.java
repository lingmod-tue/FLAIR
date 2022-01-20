package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.ParenthesisGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.PlainTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.NlpManager;
import com.flair.shared.exerciseGeneration.BracketsProperties;

public class ComparisonBracketsGenerator extends BracketsGenerator {

	@Override
	protected void generateBracketsContents(NlpManager nlpManager, ConstructionTextPart construction, 
			ArrayList<String> bracketsProperties, ExerciseData data, ArrayList<String> lemmas, int i) {
		if(bracketsProperties.contains(BracketsProperties.LEMMA)) {
            String lemma = nlpManager.getLemmaOfComparison(construction.getIndicesInPlainText());
            if(lemma != null) {
                if(bracketsProperties.contains(BracketsProperties.DISTRACTOR_LEMMA)) {
            		lemmas.add(lemma);
            		construction.getBrackets().add(generateDistractorLemma(lemma));
            	} else {
            		construction.getBrackets().add(lemma);
            	}
            } else {
            	data.getParts().set(i, new PlainTextPart(construction.getValue(), construction.getSentenceId()));
                return;
            }
        }
        if(bracketsProperties.contains(BracketsProperties.POS)) {
            String pos = construction.getConstructionType().toString().startsWith("ADJ") ? "adjective" : "adverb";
            construction.getBrackets().add(pos);
        }

        if(bracketsProperties.contains(BracketsProperties.COMPARISON_FORM)) {
            String form = construction.getConstructionType().toString().contains("_COMP_") ? "comparative" : "superlative";
            construction.getBrackets().add(form);
        }
	}
	
}
