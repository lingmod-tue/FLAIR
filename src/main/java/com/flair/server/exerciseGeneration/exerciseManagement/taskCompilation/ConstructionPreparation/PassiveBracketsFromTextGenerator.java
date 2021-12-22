package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.LemmatizedVerbCluster;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DataStructures.TargetConstruction;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Pair;

public class PassiveBracketsFromTextGenerator extends BracketsFromTextGenerator {
	
	public PassiveBracketsFromTextGenerator(NlpManager nlpManager, ArrayList<BracketsProperties> bracketsProperties,
			String plainText) {
		super(nlpManager, bracketsProperties);
		
		this.plainText = plainText;
	}
	
	private final String plainText;
	
	@Override
	public ArrayList<String> generateBracketsContent(int constructionId, TargetConstruction construction) {
		ArrayList<String> brackets = new ArrayList<>();

		if (bracketsProperties.contains(BracketsProperties.ACTIVE_SENTENCE)) {
            String activeSentence = nlpManager.getActiveSentence(new Pair<>(construction.getStartIndex(), construction.getEndIndex()), 
            		plainText, construction.getType());
            if (activeSentence != null) {
                brackets.add(activeSentence);
            } else {
                constructionsToRemove.add(constructionId);
                return brackets;
            }
        } else {
        	LemmatizedVerbCluster lemmatizedVerb = 
        			nlpManager.getLemmatizedVerbConstruction(
        					new Pair<>(construction.getStartIndex(), construction.getEndIndex()), true, true);
            if(bracketsProperties.contains(BracketsProperties.LEMMA)) {
                if(lemmatizedVerb != null) {
                    brackets.add(lemmatizedVerb.getLemmatizedCluster());
                } else {
                    constructionsToRemove.add(constructionId);
                    return brackets;
                }
            } else {
            	if(lemmatizedVerb != null) {
            		brackets.add(String.join(" ", lemmatizedVerb.getNonLemmatizedComponents()));
            	}
            }
        }
        
        if(bracketsProperties.contains(BracketsProperties.TENSE)) {
            String tense;
            if(construction.getType().toString().endsWith("PRESSMP")) {
            	tense = "simple present";
            } else if(construction.getType().toString().endsWith("FUTSMP")) {
            	tense = "future simple";
            } else if(construction.getType().toString().endsWith("PRESPRG")) {
            	tense = "present progressive";
            } else if(construction.getType().toString().endsWith("PASTPRG")) {
            	tense = "past progressive";
            } else if(construction.getType().toString().endsWith("FUTPRG")) {
            	tense = "future progressive";
            } else if(construction.getType().toString().endsWith("FUTPERF")) {
            	tense = "future perfect";
            } else if(construction.getType().toString().endsWith("PRESPERFPRG")) {
            	tense = "present perfect progressive";
            } else if(construction.getType().toString().endsWith("PASTPERFPRG")) {
            	tense = "past perfect progressive";
            } else if(construction.getType().toString().endsWith("FUTPERFPRG")) {
            	tense = "future perfect progressive";
            } else if(construction.getType().toString().endsWith("PASTSMP")) {
            	tense = "simple past";
            } else if(construction.getType().toString().endsWith("PRESPERF")) {
            	tense = "present perfect";
            } else {
            	tense = "past perfect";
            }
            brackets.add(tense);
        }
        
        if(bracketsProperties.contains(BracketsProperties.SENTENCE_TYPE)) {
        	brackets.add(construction.getType().toString().startsWith("PASSIVE") ? "passive" : "active");
        }
        
        return brackets;
	}
}
