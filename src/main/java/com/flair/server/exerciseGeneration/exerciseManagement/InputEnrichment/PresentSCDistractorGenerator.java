package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment;

import java.util.ArrayList;
import java.util.HashSet;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.TenseSettings;
import com.flair.shared.exerciseGeneration.DistractorProperties;

import edu.stanford.nlp.ling.CoreLabel;

public class PresentSCDistractorGenerator extends SCDistractorGenerator {
	
	@Override
	protected void generateDistractorValues(NlpManager nlpManager, ConstructionTextPart construction, HashSet<String> options, 
			HashSet<String> incorrectFormOptions, ExerciseData data) {
    	boolean is3Sg = construction.getConstructionType().toString().endsWith("_3");
        CoreLabel mainVerb = nlpManager.getMainVerb(construction.getIndicesInPlainText());
        
        if(mainVerb != null) {
            ArrayList<Boolean> parameterConstellations = new ArrayList<>();
            parameterConstellations.add(is3Sg);

            if(data.getDistractorProperties().contains(DistractorProperties.WRONG_SUFFIX_USE)) {
                parameterConstellations.add(!is3Sg);
            }
            
            String subject = nlpManager.getSubject(mainVerb.beginPosition(), construction.getIndicesInPlainText());
            if(subject == null) {
            	subject = is3Sg ? "he" : "they";
            }
            	
            for(int j = 0; j < parameterConstellations.size(); j++) {
                boolean parameterConstellation = parameterConstellations.get(j);
                if(j != 0) {    // don't calculate the correct form, we already have it
                	String option = nlpManager.generateCorrectForm(new TenseSettings(mainVerb.lemma(), false,
                            false, parameterConstellation, subject, "present",
                            false, false));
                	option = data.getPlainText().substring(construction.getIndicesInPlainText().first, mainVerb.beginPosition()) +
                			option + data.getPlainText().substring(mainVerb.endPosition(), construction.getIndicesInPlainText().second);	                        			
                    options.add(option);
                }
                if(data.getDistractorProperties().contains(DistractorProperties.INCORRECT_FORMS)) {
                	HashSet<String> o = nlpManager.generateIncorrectForms(new TenseSettings(mainVerb.lemma(), false,
                            false, parameterConstellation, subject, "present",false, false));
                    for(String option : o) {
                    	option = data.getPlainText().substring(construction.getIndicesInPlainText().first, mainVerb.beginPosition()) +
                    			option + data.getPlainText().substring(mainVerb.endPosition(), construction.getIndicesInPlainText().second);	                        			
                    	incorrectFormOptions.add(option);
                    }	                        	
                }
            }
        } 
	}

}
