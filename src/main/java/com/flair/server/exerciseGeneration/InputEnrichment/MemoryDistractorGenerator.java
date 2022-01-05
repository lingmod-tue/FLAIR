package com.flair.server.exerciseGeneration.InputEnrichment;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.ConstructionTextPart;
import com.flair.server.exerciseGeneration.Distractor;
import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.PlainTextPart;
import com.flair.server.exerciseGeneration.TextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.server.exerciseGeneration.util.ExerciseTopic;
import com.flair.shared.exerciseGeneration.Pair;

public class MemoryDistractorGenerator extends DistractorGenerator {

	@Override
	public boolean generateDistractors(NlpManager nlpManager, ExerciseData data) {
		ArrayList<String> usedTargets = new ArrayList<>();
     
    	for(int i = 0; i < data.getParts().size(); i++) {
        	TextPart part = data.getParts().get(i);
        	
        	if(part instanceof ConstructionTextPart) {
        		ConstructionTextPart construction = (ConstructionTextPart)part;
        		
        		String lemma = null;
                if(data.getTopic() == ExerciseTopic.COMPARISON) {
                    lemma = nlpManager.getLemmaOfComparison(construction.getIndicesInPlainText());
                } else if(data.getTopic() == ExerciseTopic.PRESENT || data.getTopic() == ExerciseTopic.PAST) {
					Pair<String, String> lemmaSubject = nlpManager.getVerbLemma(construction.getIndicesInPlainText(), false);	
					if(lemmaSubject != null) {
						lemma = lemmaSubject.first;
					}
				}
                
                // We cannot use ambiguous lemmas
                String constructionText = data.getPlainText().substring(construction.getIndicesInPlainText().first, 
        				construction.getIndicesInPlainText().second);
                if(usedTargets.contains(lemma) || usedTargets.contains(constructionText)) {
                	lemma = null;
                }
                
                if(lemma == null || lemma.isEmpty()) {
                	data.getParts().set(i, new PlainTextPart(construction.getValue(), construction.getSentenceId()));
                } else {
                	Distractor d = new Distractor(lemma);
                	d.setIllFormed(false);
                	construction.getDistractors().add(d); 
                	
                	usedTargets.add(lemma);
                	usedTargets.add(constructionText);
                }
        	}
    	}
        
        return true;
    }
    
}
