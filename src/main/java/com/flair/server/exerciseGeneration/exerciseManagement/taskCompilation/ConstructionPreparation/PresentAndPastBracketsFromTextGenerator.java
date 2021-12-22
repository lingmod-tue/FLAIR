package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.LemmatizedVerbCluster;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DataStructures.TargetConstruction;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Pair;

public class PresentAndPastBracketsFromTextGenerator extends BracketsFromTextGenerator {
	
	public PresentAndPastBracketsFromTextGenerator(NlpManager nlpManager, 
			ArrayList<BracketsProperties> bracketsProperties) {
		super(nlpManager, bracketsProperties);
	}
		
	@Override
	public ArrayList<String> generateBracketsContent(int constructionId, TargetConstruction construction) {
		ArrayList<String> brackets = new ArrayList<>();

		LemmatizedVerbCluster lemmatizedVerb = 
				nlpManager.getLemmatizedVerbConstruction(
						new Pair<>(construction.getStartIndex(), construction.getEndIndex()), true, false);
        if(bracketsProperties.contains(BracketsProperties.LEMMA)) {
            if(lemmatizedVerb != null) {
            	String lemmaCluster = lemmatizedVerb.getLemmatizedCluster();
            	if(construction.getType().toString().contains("_NEG_")) {
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
                		brackets.add(generateDistractorLemma(lemmaCluster));
                	} else {
                        brackets.add(lemmaCluster);
                	}
            	}
            } else {
                constructionsToRemove.add(constructionId);
                return brackets;
            }
        } else {
        	if(lemmatizedVerb != null) {
        		String lemmaCluster = String.join(" ", lemmatizedVerb.getNonLemmatizedComponents());
        		if(!lemmaCluster.trim().equals("")) {
        			brackets.add(lemmaCluster);
        		}
        	}
        }
        if(construction.getType().toString().contains("QUEST")) {
            brackets.add("interrog");
        }
        if(construction.getType().toString().contains("_NEG_")) {
            brackets.add("neg");
        }
        if((construction.getType().toString().startsWith("PAST") || construction.getType().toString().startsWith("PRES"))) {
        	if(bracketsProperties.contains(BracketsProperties.TENSE)) {
            	String tense = construction.getType().toString().startsWith("PASTSMP") ? "simple past" :
                        construction.getType().toString().startsWith("PRESPERF") ? "present perfect" : "past perfect";
                brackets.add(tense);
        	}
            
            if(bracketsProperties.contains(BracketsProperties.PROGRESSIVE)) {
                String prog = construction.getType().toString().contains("PRG_") ? "progressive" : "simple";			                       
                brackets.add(prog);
            }
        }

        
        return brackets;
	}
}
