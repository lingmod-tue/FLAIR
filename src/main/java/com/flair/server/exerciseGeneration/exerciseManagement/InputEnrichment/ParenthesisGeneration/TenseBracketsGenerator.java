package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.ParenthesisGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.PlainTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.LemmatizedVerbCluster;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.NlpManager;
import com.flair.shared.exerciseGeneration.BracketsProperties;

public class TenseBracketsGenerator extends BracketsGenerator {

	@Override
	protected void generateBracketsContents(NlpManager nlpManager, ConstructionTextPart construction, 
			ArrayList<String> bracketsProperties, ExerciseData data, ArrayList<String> lemmas, int i) {
		LemmatizedVerbCluster lemmatizedVerb = nlpManager.getLemmatizedVerbConstruction(construction.getIndicesInPlainText(), true, false);
        if(bracketsProperties.contains(BracketsProperties.LEMMA)) {
            if(lemmatizedVerb != null) {
            	String lemmaCluster = lemmatizedVerb.getLemmatizedCluster();
            	if(construction.getConstructionType().toString().contains("_NEG_")) {
            		// remove simple negation if contained in lemmas
            		lemmaCluster = lemmaCluster.replaceAll(" n[o']t ", " ");
            		if(lemmaCluster.startsWith("not ")) {
            			lemmaCluster = lemmaCluster.substring(3);
            		}
            		if(lemmaCluster.endsWith(" not")) {
            			lemmaCluster = lemmaCluster.substring(0, lemmaCluster.length() - 4);
            		} else if(lemmaCluster.endsWith("n't")) {
            			lemmaCluster = lemmaCluster.substring(0, lemmaCluster.length() - 3);
            		}
            	}
            	if(!lemmaCluster.trim().equals("")) {
            		if(bracketsProperties.contains(BracketsProperties.DISTRACTOR_LEMMA)) {
            			lemmas.add(lemmaCluster);
            			construction.getBrackets().add(generateDistractorLemma(lemmaCluster));
                	} else {
                		construction.getBrackets().add(lemmaCluster);
                	}
            	}
            } else {
            	data.getParts().set(i, new PlainTextPart(construction.getValue(), construction.getSentenceId()));
                return;
            }
        } else {
        	if(lemmatizedVerb != null) {
        		String lemmaCluster = String.join(" ", lemmatizedVerb.getNonLemmatizedComponents());
        		if(!lemmaCluster.trim().equals("")) {
        			construction.getBrackets().add(lemmaCluster);
        		}
        	}
        }
        if(construction.getConstructionType().toString().contains("QUEST")) {
        	construction.getBrackets().add("interrog");
        }
        if(construction.getConstructionType().toString().contains("_NEG_")) {
        	construction.getBrackets().add("neg");
        }
        if((construction.getConstructionType().toString().startsWith("PAST") || construction.getConstructionType().toString().startsWith("PRES"))) {
        	if(bracketsProperties.contains(BracketsProperties.TENSE)) {
            	String tense = construction.getConstructionType().toString().startsWith("PASTSMP") ? "simple past" :
                        construction.getConstructionType().toString().startsWith("PRESPERF") ? "present perfect" : "past perfect";
            	construction.getBrackets().add(tense);
        	}
            
            if(bracketsProperties.contains(BracketsProperties.PROGRESSIVE)) {
                String prog = construction.getConstructionType().toString().contains("PRG_") ? "progressive" : "simple";			                       
                construction.getBrackets().add(prog);
            }
        }
	}
    
}
