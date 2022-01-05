package com.flair.server.exerciseGeneration.InputEnrichment;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.ConstructionProperties;
import com.flair.server.exerciseGeneration.ConstructionTextPart;
import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.PlainTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.LemmatizedVerbCluster;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.DetailedConstruction;

public class ConditionalBracketsGenerator extends BracketsGenerator {

	@Override
	protected void generateBracketsContents(NlpManager nlpManager, ConstructionTextPart construction, 
			ArrayList<BracketsProperties> bracketsProperties, ExerciseData data, ArrayList<String> lemmas, int i) {
		LemmatizedVerbCluster lemmatizedVerb = nlpManager.getLemmatizedVerbConstruction(construction.getIndicesInPlainText(), false, false);
    	if(bracketsProperties.contains(BracketsProperties.LEMMA)) {
            if(lemmatizedVerb != null) {
            	String lemma = lemmatizedVerb.getLemmatizedCluster();
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
        } else {
        	if(lemmatizedVerb != null) {
        		construction.getBrackets().add(String.join(" ", lemmatizedVerb.getNonLemmatizedComponents()));
        	}
        }
        
        if (bracketsProperties.contains(BracketsProperties.CONDITIONAL_TYPE)) {
        	construction.getBrackets().add(construction.getConstructionType() == DetailedConstruction.CONDREAL ? "real" : "unreal");
        }
        if (construction.getConstructionProperties().contains(ConstructionProperties.CONDITIONAL_MAIN_CLAUSE) && 
        		bracketsProperties.contains(BracketsProperties.WILL)) {
        	construction.getBrackets().add(lemmatizedVerb.getModal());
        }  
	}
	
}
