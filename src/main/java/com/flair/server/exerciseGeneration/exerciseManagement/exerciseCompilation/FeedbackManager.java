package com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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
    	HashMap<Construction, String[]> sentenceMapping = new HashMap<>();
    	
    	JSONObject responseObject = new JSONObject();
    	
    	if(settings.isGenerateFeedback() && settings.getContentType().equals("FiB") || settings.getContentType().equals("Select") ) {
    		JSONArray activityItems = new JSONArray();
    		
    		for(Construction construction : usedConstructions) {
    			// We only support single-word constructions for now
    			if(!construction.getConstructionText().trim().matches(".*?[\\s\\h].*?")) {
    				com.flair.shared.exerciseGeneration.Pair<String, String[]> sentenceTexts = nlpManager.getSentenceTexts(construction.getConstructionIndices(), settings.getPlainText());
    				if(sentenceTexts != null) {
    					JSONObject item = new JSONObject();
    					item.put("prompt", sentenceTexts.first);
        				String targetAnswer = sentenceTexts.second[0] + sentenceTexts.second[1] + sentenceTexts.second[2];
    					item.put("targetAnswer", targetAnswer);
    					activityItems.add(item);
    					sentenceMapping.put(construction, sentenceTexts.second);
    				}
    			}		
    		}
    		
    		JSONObject activitySpecification = new JSONObject();
    		activitySpecification.put("items", activityItems);
    		JSONObject requestObject = new JSONObject();
    		requestObject.put("version", "1.0");
    		requestObject.put("activitySpecification", activitySpecification);
    		    		   		
    		//TODO: call microservice with requestObject in JSON body and fill feedback hashmap or just use JSON for feedback right from the start
    	}
    	
    	JSONObject inputMapping = null;
    	JSONObject feedbackMapping = null;
    	if(responseObject.containsKey("externalFeedbackResourceBundleJson")) {
			JSONObject feedbackObject = (JSONObject) responseObject.get("externalFeedbackResourceBundleJson");
			if(feedbackObject.containsKey("inputString2DiagnosticCodeTable")) {
				inputMapping = (JSONObject) responseObject.get("inputString2DiagnosticCodeTable");
				
				if(feedbackObject.containsKey("diagnosticCode2FeedbackMessageTable")) {
    				feedbackMapping = (JSONObject) responseObject.get("diagnosticCode2FeedbackMessageTable");
    			}
			}
		}
    	
    	for(Construction construction : usedConstructions) {
    		ArrayList<Pair<String, String>> currentDistractors = new ArrayList<>();
    		ArrayList<String> usedDistractors = new ArrayList<>();
    		    		
    		if(sentenceMapping.containsKey(construction)) {
    			String[] sentenceParts = sentenceMapping.get(construction);
				String sentence = sentenceParts[0] + sentenceParts[1] + sentenceParts[2];

    			if(inputMapping != null && inputMapping.containsKey(sentence)) {
    				JSONObject feedbacks = (JSONObject) inputMapping.get("sentence");
    				for(Iterator iterator = feedbacks.keySet().iterator(); iterator.hasNext();) {
    				    String targetHypotehsis = (String) iterator.next();
    				    String feedbackId = (String) feedbacks.get(targetHypotehsis);
    				    
    				    if(feedbackMapping != null && feedbackMapping.containsKey(feedbackId)) {
    				    	String feedback = (String) feedbackMapping.get(feedbackId);
    				    	
    				    	// extract the construction from the entire sentence
    				    	String distractorText = null;
    				    	if(sentenceParts[0].equals("") || targetHypotehsis.startsWith(sentenceParts[0]) && 
    				    			sentenceParts[2].equals("") || targetHypotehsis.endsWith(sentenceParts[2])) {
    				    		distractorText = targetHypotehsis.substring(sentenceParts[0].length(), targetHypotehsis.length() - sentenceParts[2].length());
    				    	}
    				    	
    				    	if(distractorText != null) {   		
	    				    	if(settings.getContentType().equals("Select")) {
	        						for(String distractor : construction.getDistractors()) {
	        							if(distractorText.equals(distractor)) {
	        								currentDistractors.add(new Pair<>(distractor, feedback));
	        								usedDistractors.add(distractor);
	        							} 
	        						}
	        					} else {
	        						currentDistractors.add(new Pair<>(distractorText, feedback));
	        					}
    				    	}
    				    }
    				}    				    				
    			}    			
    		}
    		
			if(settings.getContentType().equals("Select")) {
				for(String distractor : construction.getDistractors()) {
					if(!usedDistractors.contains(distractor)) {
						currentDistractors.add(new Pair<>(distractor, null));
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
