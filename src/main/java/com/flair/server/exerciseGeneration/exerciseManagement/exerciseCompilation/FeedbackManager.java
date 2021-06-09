package com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation;


import java.util.ArrayList;
import java.util.HashMap;

import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;

import edu.stanford.nlp.util.Pair;

public class FeedbackManager {

	/**
     * Generates feedback for distractors.
	 * @param usedConstructions	The constructions actually used as targets
	 * @param settings			The exercise settings
	 * @param nlpManager		The NLP manager
	 * @return					A list of distractors with associated feedback per construction
	 */
    public ArrayList<ArrayList<Pair<String, String>>> generateFeedback(ArrayList<Construction> usedConstructions, ExerciseSettings settings, NlpManager nlpManager) {
    	ArrayList<ArrayList<Pair<String, String>>> distractors = new ArrayList<>();
    	HashMap<String, String> feedback = new HashMap<>();
    	HashMap<Construction, String> sentenceMapping = new HashMap<>();
    	
    	if(settings.isGenerateFeedback() && settings.getContentType().equals("FiB") || settings.getContentType().equals("Select") ) {
    		//TODO: define how to mark up constructions
    		ArrayList<String> sentences = new ArrayList<>();
    		for(Construction construction : usedConstructions) {
    			// We only support single-word constructions for now
    			if(!construction.getConstructionText().trim().matches(".*?[\\s\\h].*?")) {
    				String sentenceText = nlpManager.getSentenceText(construction.getConstructionIndices(), settings.getPlainText());
        			if(sentenceText != null) {
        				sentences.add(sentenceText);
        				sentenceMapping.put(construction, sentenceText);
        			}
    			}		
    		}
    		
    		//TODO: make sure that sentences are unique
    		//TODO: call microservice with sentences in JSON body and fill feedback hashmap or just use JSON for feedback right from the start
    	}
    	
    	for(Construction construction : usedConstructions) {
    		ArrayList<Pair<String, String>> currentDistractors = new ArrayList<>();
    		if(sentenceMapping.containsKey(construction)) {
    			String sentence = sentenceMapping.get(construction);
    			if(feedback.containsKey(sentence)) {
    				String f = feedback.get(sentence);
    				
    				//TODO: iterate through all feedbacks for this construction
    				if(f != null && !f.endsWith("")) {
    					//TODO: map to target hypothesis
    					
    					if(settings.getContentType().equals("Select")) {
    						for(String distractor : construction.getDistractors()) {
    							//TODO: correctly check if the distractor matches any target hypothesis
    							if(f.equals(distractor)) {
    								currentDistractors.add(new Pair<>(distractor, f));
    							} else {
    								currentDistractors.add(new Pair<>(distractor, null));
    							}
    						}
    					} else {
    						currentDistractors.add(new Pair<>("", f));
    					}
    				}
    			}
    		}
    		
    		// If we don't get distractors from the feedback generation, we use the ones we generated ourselves
    		if(currentDistractors.size() == 0) {
    			for(String distractor : construction.getDistractors()) {
    				currentDistractors.add(new Pair<>(distractor, null));
    			}
    		}
    		
    		distractors.add(currentDistractors);
    	}
    	
    	
    	return distractors;
    }
   
}
