package com.flair.server.exerciseGeneration.exerciseManagement.exerciseCompilation;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;

import edu.stanford.nlp.util.Pair;

public class FeedbackManager {

	private static final Properties PROPERTIES = new Properties();

	static {
		try {
			PROPERTIES.load(FeedbackManager.class.getResourceAsStream("FeedbackManager.properties"));
		} catch (IOException e) {
			ServerLogger.get().error(e, "Couldn't load properties for FeedbackManager");
		}
	}
	
	ArrayList<ArrayList<Pair<String, String>>> distractors = new ArrayList<>();
	HashMap<Construction, String[]> sentenceMapping = new HashMap<>();
	
	/**
     * Generates feedback for distractors.
	 * @param usedConstructions	The constructions actually used as targets
	 * @param settings			The exercise settings
	 * @param nlpManager		The NLP manager
	 * @return					A list of distractors with associated feedback per construction
	 */
    public ArrayList<ArrayList<Pair<String, String>>> generateFeedback(ArrayList<Construction> usedConstructions, ExerciseSettings settings, NlpManager nlpManager) {
    	JSONObject responseObject = null;
    	
    	if(settings.isGenerateFeedback() && settings.getContentType().equals("FiB") || settings.getContentType().equals("Select") ) {
    		JSONObject requestObject = prepareJsonRequest(settings, usedConstructions, nlpManager);
    		
    		if(requestObject != null) {
    			responseObject = getResponseFromMicroservice(requestObject);
    		}
    	}
    	
    	Pair<JSONObject, JSONObject> feedbackObjects = getFeedbackFromJsonResponse(responseObject);
    	JSONObject inputMapping = feedbackObjects.first;
    	JSONObject feedbackMapping = feedbackObjects.second;
    	    	
    	for(Construction construction : usedConstructions) {
    		Pair<ArrayList<Pair<String, String>>, ArrayList<String>> generatedDistractors = addFeedbackToDistractors(construction, 
    	    		inputMapping, feedbackMapping, settings);
    		ArrayList<Pair<String, String>> currentDistractors = generatedDistractors.first;
    		ArrayList<String> usedDistractors = generatedDistractors.second;
    		
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
    
    /**
     * Prepares the construction data to bring it into the format required by the FeedBook microservice.
     * @param settings			The exercise settings
     * @param usedConstructions	The constructions
     * @param nlpManager		The NLP manager
     * @return					The JSON object for the microservice request body
     */
    private JSONObject prepareJsonRequest(ExerciseSettings settings, ArrayList<Construction> usedConstructions, NlpManager nlpManager) { 	
		JSONArray activityItems = new JSONArray();
		
		if(settings.getContentType().equals("Select") || settings.getContentType().equals("FiB") && !settings.getBrackets().contains(BracketsProperties.ACTIVE_SENTENCE)) {
			for(Construction construction : usedConstructions) {
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
		
		if(activityItems.size() > 0) {
			JSONObject activitySpecification = new JSONObject();
			activitySpecification.put("items", activityItems);
			JSONObject requestObject = new JSONObject();
			requestObject.put("version", "1.0");
			requestObject.put("activitySpecification", activitySpecification);
			    		   		
			return requestObject;    	
		}
		
		return null;
    }
    
    /**
     * Connects to the microservice and gets the feedback for the requestObject.
     * @param requestObject	The requestObject containing the information on the targets for which feedback needs to be generated
     * @return				The response from the microservice if successful; otherwise null
     */
    private JSONObject getResponseFromMicroservice(JSONObject requestObject) {
    	try {
			URL url = new URL(PROPERTIES.getProperty("url"));
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);
			
			try(OutputStream os = con.getOutputStream()) {
			    byte[] input = requestObject.toJSONString().getBytes("utf-8");
			    os.write(input, 0, input.length);			
			}
							
			try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			    StringBuilder response = new StringBuilder();
			    String responseLine = null;
			    while ((responseLine = br.readLine()) != null) {
			        response.append(responseLine.trim());
			    }
			    
			    JSONParser parser = new JSONParser();
			    return (JSONObject) parser.parse(response.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    /**
     * Extracts the feedback object and the feedback mapping object from the returned JSON object
     * @param responseObject	The returned JSON object
     * @return					The input mapping and the feedback mapping JSON objects
     */
    private Pair<JSONObject, JSONObject> getFeedbackFromJsonResponse(JSONObject responseObject) {
    	JSONObject inputMapping = null;
    	JSONObject feedbackMapping = null;
    	if(responseObject != null && responseObject.containsKey("externalFeedbackResourceBundleJson")) {
			JSONObject feedbackObject = (JSONObject) responseObject.get("externalFeedbackResourceBundleJson");
			if(feedbackObject.containsKey("inputString2DiagnosticCodeTable")) {
				inputMapping = (JSONObject) responseObject.get("inputString2DiagnosticCodeTable");
				
				if(feedbackObject.containsKey("diagnosticCode2FeedbackMessageTable")) {
    				feedbackMapping = (JSONObject) responseObject.get("diagnosticCode2FeedbackMessageTable");
    			}
			}
		}
    	
    	return new Pair<>(inputMapping, feedbackMapping);
    }
    
    /**
     * Adds the generated feedback to existing distractors or generates wrong answers with associated feedback from the generated feedback for the construction.
     * @param construction		The construction
     * @param inputMapping		The returned input mapping object
     * @param feedbackMapping	The returned feedback mapping object
     * @param settings			The exercise settings
     * @return					The distractors with associated feedback, and the strings of the distractors for which feedback was generated
     */
    private Pair<ArrayList<Pair<String, String>>, ArrayList<String>> addFeedbackToDistractors(Construction construction, 
    		JSONObject inputMapping, JSONObject feedbackMapping, ExerciseSettings settings) {
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
		
		return new Pair<>(currentDistractors, usedDistractors);
    }
   
}
