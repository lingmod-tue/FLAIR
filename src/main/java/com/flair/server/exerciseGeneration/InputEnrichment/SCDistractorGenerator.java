package com.flair.server.exerciseGeneration.InputEnrichment;

import java.util.ArrayList;
import java.util.HashSet;

import com.flair.server.exerciseGeneration.ConstructionTextPart;
import com.flair.server.exerciseGeneration.Distractor;
import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.PlainTextPart;
import com.flair.server.exerciseGeneration.TextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;

public abstract class SCDistractorGenerator extends DistractorGenerator {

	protected abstract void generateDistractorValues(NlpManager nlpManager, ConstructionTextPart construction, HashSet<String> options, 
			HashSet<String> incorrectFormOptions, ExerciseData data);
	
	@Override
	public boolean generateDistractors(NlpManager nlpManager, ExerciseData data) {
        for(int i = 0; i < data.getParts().size(); i++) {
        	TextPart part = data.getParts().get(i);
        	
        	if(part instanceof ConstructionTextPart) {
        		ConstructionTextPart construction = (ConstructionTextPart)part;
        		
        		String constructionText = data.getPlainText().substring(construction.getIndicesInPlainText().first, construction.getIndicesInPlainText().second);
                HashSet<String> options = new HashSet<>();
                HashSet<String> incorrectFormOptions = new HashSet<>();
                
                generateDistractorValues(nlpManager, construction, options, incorrectFormOptions, data);
                
                removeCorrectForm(constructionText, options);
                removeCorrectForm(constructionText, incorrectFormOptions);

                if(options.size() == 0 && incorrectFormOptions.size() == 0) {
                	data.getParts().set(i, new PlainTextPart(construction.getValue(), construction.getSentenceId()));
                } else {
                	ArrayList<String> incorrectDistractors = capitalize(new ArrayList<>(incorrectFormOptions), constructionText);
                    for(String incorrectDistractor : incorrectDistractors) {
                    	Distractor distractor = new Distractor(incorrectDistractor.replaceAll("[\\s\\h\\v]+", " ").trim());
                    	distractor.setIllFormed(true);
                    	construction.getDistractors().add(distractor);
                    }
                    ArrayList<String> correctDistractors = capitalize(new ArrayList<>(options), constructionText);
                    for(String correctDistractor : correctDistractors) {
                    	Distractor distractor = new Distractor(correctDistractor.replaceAll("[\\s\\h\\v]+", " ").trim());
                    	distractor.setIllFormed(false);
                    	construction.getDistractors().add(distractor);
                    }
                }
        	}
        }
        
        return true;
    }
    
}
