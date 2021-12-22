package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ComparisonSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConditionalConstruction;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.TenseSettings;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.DistractorProperties;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.Pair;

import edu.stanford.nlp.ling.CoreLabel;

public class PresentDistractorsFromTextGenerator extends DistractorsFromTextGenerator {
	
	public PresentDistractorsFromTextGenerator(NlpManager nlpManager) {
		super(nlpManager);
	}
	
	@Override
	protected Pair<HashSet<String>, HashSet<String>> generateSCDistractors(Construction construction, ExerciseType exerciseType, 
			ArrayList<DistractorProperties> distractorProperties, String plainText) {
        HashSet<String> options = new HashSet<>();
        HashSet<String> incorrectFormOptions = new HashSet<>();

        boolean is3Sg = construction.getConstruction().toString().endsWith("_3");
        CoreLabel mainVerb = nlpManager.getMainVerb(construction.getConstructionIndices());
        
        if(mainVerb != null) {
            ArrayList<Boolean> parameterConstellations = new ArrayList<>();
            parameterConstellations.add(is3Sg);

            if(distractorProperties.contains(DistractorProperties.WRONG_SUFFIX_USE)) {
                parameterConstellations.add(!is3Sg);
            }
            
            String subject = nlpManager.getSubject(mainVerb.beginPosition(), construction.getConstructionIndices());
            if(subject == null) {
            	subject = is3Sg ? "he" : "they";
            }
            	
            for(int i = 0; i < parameterConstellations.size(); i++) {
                boolean parameterConstellation = parameterConstellations.get(i);
                if(i != 0) {    // don't calculate the correct form, we already have it
                	String option = nlpManager.generateCorrectForm(new TenseSettings(mainVerb.lemma(), false,
                            false, parameterConstellation, subject, "present",
                            false, false));
                	option = plainText.substring(construction.getConstructionIndices().first, mainVerb.beginPosition()) +
                			option + plainText.substring(mainVerb.endPosition(), construction.getConstructionIndices().second);	                        			
                    options.add(option);
                }
                if(distractorProperties.contains(DistractorProperties.INCORRECT_FORMS)) {
                	HashSet<String> o = nlpManager.generateIncorrectForms(new TenseSettings(mainVerb.lemma(), false,
                            false, parameterConstellation, subject, "present",false, false));
                    for(String option : o) {
                    	option = plainText.substring(construction.getConstructionIndices().first, mainVerb.beginPosition()) +
                    			option + plainText.substring(mainVerb.endPosition(), construction.getConstructionIndices().second);	                        			
                    	incorrectFormOptions.add(option);
                    }	                        	
                }
            }
        } 
        
        return new Pair<>(options, incorrectFormOptions);
	}
	
}
