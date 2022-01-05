package com.flair.server.exerciseGeneration.InputEnrichment;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.ConstructionTextPart;
import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.PlainTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.LemmatizedVerbCluster;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.shared.exerciseGeneration.BracketsProperties;

public class PassiveBracketsGenerator extends BracketsGenerator {

	@Override
	protected void generateBracketsContents(NlpManager nlpManager, ConstructionTextPart construction, 
			ArrayList<BracketsProperties> bracketsProperties, ExerciseData data, ArrayList<String> lemmas, int i) {
		if (bracketsProperties.contains(BracketsProperties.ACTIVE_SENTENCE)) {
            String activeSentence = nlpManager.getActiveSentence(construction.getIndicesInPlainText(), data.getPlainText(), construction.getConstructionType());
            if (activeSentence != null) {
            	construction.getBrackets().add(activeSentence);
            } else {
            	data.getParts().set(i, new PlainTextPart(construction.getValue(), construction.getSentenceId()));
                return;
            }
        } else {
        	LemmatizedVerbCluster lemmatizedVerb = nlpManager.getLemmatizedVerbConstruction(construction.getIndicesInPlainText(), true, true);
            if(bracketsProperties.contains(BracketsProperties.LEMMA)) {
                if(lemmatizedVerb != null) {
                	construction.getBrackets().add(lemmatizedVerb.getLemmatizedCluster());
                } else {
                	data.getParts().set(i, new PlainTextPart(construction.getValue(), construction.getSentenceId()));
                    return;
                }
            } else {
            	if(lemmatizedVerb != null) {
            		construction.getBrackets().add(String.join(" ", lemmatizedVerb.getNonLemmatizedComponents()));
            	}
            }
        }
        
        if(bracketsProperties.contains(BracketsProperties.TENSE)) {
            String tense;
            if(construction.getConstructionType().toString().endsWith("PRESSMP")) {
            	tense = "simple present";
            } else if(construction.getConstructionType().toString().endsWith("FUTSMP")) {
            	tense = "future simple";
            } else if(construction.getConstructionType().toString().endsWith("PRESPRG")) {
            	tense = "present progressive";
            } else if(construction.getConstructionType().toString().endsWith("PASTPRG")) {
            	tense = "past progressive";
            } else if(construction.getConstructionType().toString().endsWith("FUTPRG")) {
            	tense = "future progressive";
            } else if(construction.getConstructionType().toString().endsWith("FUTPERF")) {
            	tense = "future perfect";
            } else if(construction.getConstructionType().toString().endsWith("PRESPERFPRG")) {
            	tense = "present perfect progressive";
            } else if(construction.getConstructionType().toString().endsWith("PASTPERFPRG")) {
            	tense = "past perfect progressive";
            } else if(construction.getConstructionType().toString().endsWith("FUTPERFPRG")) {
            	tense = "future perfect progressive";
            } else if(construction.getConstructionType().toString().endsWith("PASTSMP")) {
            	tense = "simple past";
            } else if(construction.getConstructionType().toString().endsWith("PRESPERF")) {
            	tense = "present perfect";
            } else {
            	tense = "past perfect";
            }
            construction.getBrackets().add(tense);
        }
        
        if(bracketsProperties.contains(BracketsProperties.SENTENCE_TYPE)) {
        	construction.getBrackets().add(construction.getConstructionType().toString().startsWith("PASSIVE") ? "passive" : "active");
        }
	}
	
}
