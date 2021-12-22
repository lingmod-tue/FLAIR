package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ComparisonSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConditionalConstruction;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.LemmatizedVerbCluster;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.TenseSettings;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.DistractorProperties;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.Pair;

import edu.stanford.nlp.ling.CoreLabel;

public class PastDistractorsFromTextGenerator extends DistractorsFromTextGenerator {
	
	public PastDistractorsFromTextGenerator(NlpManager nlpManager) {
		super(nlpManager);
	}
	
	@Override
	protected Pair<HashSet<String>, HashSet<String>> generateSCDistractors(Construction construction, ExerciseType exerciseType, 
			ArrayList<DistractorProperties> distractorProperties, String plainText) {
        HashSet<String> options = new HashSet<>();
        HashSet<String> incorrectFormOptions = new HashSet<>();

        boolean isPerfect = construction.toString().startsWith("PASTPERF") || construction.toString().startsWith("PRESPERF");
        String tense = construction.getConstruction().toString().startsWith("PAST") ? "past" : "present";
    	boolean isProgressive = construction.getConstruction().toString().contains("PRG_");
        boolean isInterrogative = construction.getConstruction().toString().contains("_QUEST_");
        Pair<String, String> lemma =
                nlpManager.getVerbLemma(construction.getConstructionIndices(), isInterrogative);
        boolean isNegated = construction.getConstruction().toString().contains("_NEG_");

        if(lemma != null) {
        	//[((isPerfect, isProgessive), tense)]
            ArrayList<Pair<Pair<Boolean, Boolean>, String>> parameterConstellations = new ArrayList<>();

            if(distractorProperties.contains(DistractorProperties.OTHER_PAST)) {
                parameterConstellations.add(new Pair<>(new Pair<>(true, isProgressive), "present"));
                parameterConstellations.add(new Pair<>(new Pair<>(false, isProgressive), "present"));
                parameterConstellations.add(new Pair<>(new Pair<>(true, isProgressive), "past"));
            } else {
                parameterConstellations.add(new Pair<>(new Pair<>(isPerfect, isProgressive), tense));
            }
            if(distractorProperties.contains(DistractorProperties.OTHER_TENSE)) {
                parameterConstellations.add(new Pair<>(new Pair<>(true, isProgressive), "future"));
                parameterConstellations.add(new Pair<>(new Pair<>(false, isProgressive), "future"));
                parameterConstellations.add(new Pair<>(new Pair<>(false, isProgressive), "past"));
            }
            
            if(distractorProperties.contains(DistractorProperties.PROGRESSIVE)) {
                ArrayList<Pair<Pair<Boolean, Boolean>, String>> newParameterConstellations = new ArrayList<>();
                for(Pair<Pair<Boolean, Boolean>, String> parameterConstellation : parameterConstellations) {
                	newParameterConstellations.add(
                			new Pair<>(new Pair<>(parameterConstellation.first.first, !parameterConstellation.first.second), parameterConstellation.second));

                }
                parameterConstellations.addAll(newParameterConstellations);
            }
            	    	                    	    	                    
            // get other components excluding the verb and negation
            String otherComponents = "";
            LemmatizedVerbCluster verbCluster = nlpManager.getLemmatizedVerbConstruction(construction.getConstructionIndices(), true, false);
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
                    if (distractorProperties.contains(DistractorProperties.INCORRECT_FORMS)) {
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
        
        return new Pair<>(options, incorrectFormOptions);
	}
	
}
