package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.DistractorGeneration;

import java.util.ArrayList;
import java.util.HashSet;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.LemmatizedVerbCluster;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.TenseSettings;
import com.flair.shared.exerciseGeneration.DistractorProperties;
import com.flair.shared.exerciseGeneration.Pair;

public class PastSCDistractorGenerator extends SCDistractorGenerator {
	
	@Override
	protected void generateDistractorValues(NlpManager nlpManager, ConstructionTextPart construction, HashSet<String> options, 
			HashSet<String> incorrectFormOptions, ExerciseData data) {
    	boolean isPerfect = construction.toString().startsWith("PASTPERF") || construction.toString().startsWith("PRESPERF");
        String tense = construction.getConstructionType().toString().startsWith("PAST") ? "past" : "present";
    	boolean isProgressive = construction.getConstructionType().toString().contains("PRG_");
        boolean isInterrogative = construction.getConstructionType().toString().contains("_QUEST_");
        Pair<String, String> lemma =
                nlpManager.getVerbLemma(construction.getIndicesInPlainText(), isInterrogative);
        boolean isNegated = construction.getConstructionType().toString().contains("_NEG_");

        if(lemma != null) {
        	//[((isPerfect, isProgessive), tense)]
            ArrayList<Pair<Pair<Boolean, Boolean>, String>> parameterConstellations = new ArrayList<>();

            if(data.getDistractorProperties().contains(DistractorProperties.OTHER_PAST)) {
                parameterConstellations.add(new Pair<>(new Pair<>(true, isProgressive), "present"));
                parameterConstellations.add(new Pair<>(new Pair<>(false, isProgressive), "present"));
                parameterConstellations.add(new Pair<>(new Pair<>(true, isProgressive), "past"));
            } else {
                parameterConstellations.add(new Pair<>(new Pair<>(isPerfect, isProgressive), tense));
            }
            if(data.getDistractorProperties().contains(DistractorProperties.OTHER_TENSE)) {
                parameterConstellations.add(new Pair<>(new Pair<>(true, isProgressive), "future"));
                parameterConstellations.add(new Pair<>(new Pair<>(false, isProgressive), "future"));
                parameterConstellations.add(new Pair<>(new Pair<>(false, isProgressive), "past"));
            }
            
            if(data.getDistractorProperties().contains(DistractorProperties.PROGRESSIVE)) {
                ArrayList<Pair<Pair<Boolean, Boolean>, String>> newParameterConstellations = new ArrayList<>();
                for(Pair<Pair<Boolean, Boolean>, String> parameterConstellation : parameterConstellations) {
                	newParameterConstellations.add(
                			new Pair<>(new Pair<>(parameterConstellation.first.first, !parameterConstellation.first.second), parameterConstellation.second));

                }
                parameterConstellations.addAll(newParameterConstellations);
            }
            	    	                    	    	                    
            // get other components excluding the verb and negation
            String otherComponents = "";
            LemmatizedVerbCluster verbCluster = nlpManager.getLemmatizedVerbConstruction(construction.getIndicesInPlainText(), true, false);
            if(verbCluster != null) {
            	otherComponents = String.join(" ", verbCluster.getNonLemmatizedComponents());
            	otherComponents.replaceAll(" n[o']t ", " ");
            	if(otherComponents.startsWith("not ")) {
            		otherComponents = otherComponents.substring(3);
            	}
            	if(otherComponents.endsWith(" not")){
            		otherComponents = otherComponents.substring(0, otherComponents.length() - 4);
            	} else if(otherComponents.endsWith("n't")){
            		otherComponents = otherComponents.substring(0, otherComponents.length() - 3);
            	}
            	
            	if(!otherComponents.equals("")) {
            		otherComponents = " " + otherComponents;
            	}
            }
            
            for (Pair<Pair<Boolean, Boolean>, String> parameterConstellation : parameterConstellations) {
                for (int j = 0; j <= 1; j++) {
                	// We don't know if we have a 3rd pers. sing. form or not, so we calculate both forms also for the correct parameter settings
            		options.add(nlpManager.generateCorrectForm(new TenseSettings(lemma.first, isInterrogative,
                            isNegated, j == 0, lemma.second, parameterConstellation.second,
                            parameterConstellation.first.second, parameterConstellation.first.first)) + otherComponents);
                    if (data.getDistractorProperties().contains(DistractorProperties.INCORRECT_FORMS)) {
                    	HashSet<String> incorrectForms = nlpManager.generateIncorrectForms(new TenseSettings(lemma.first, isInterrogative,
                                isNegated, j == 0, lemma.second, parameterConstellation.second,
                                parameterConstellation.first.second, parameterConstellation.first.first));
                    	for(String incorrectForm : incorrectForms) {
                    		incorrectFormOptions.add(incorrectForm + otherComponents);
                    	}
                    }	    	                        	
                }
            }
        }
	}

}
