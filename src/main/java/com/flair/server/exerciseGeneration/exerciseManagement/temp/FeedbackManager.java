package com.flair.server.exerciseGeneration.exerciseManagement.temp;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.measure.spi.SystemOfUnits;

import org.apache.bcel.generic.NEW;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.beust.jcommander.internal.Console;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.Pair;

import java_cup.internal_error;

public class FeedbackManager {

	private static final Properties PROPERTIES = new Properties();
	private static final int BATCH_SIZE = 1;

	static {
		try {
			PROPERTIES.load(FeedbackManager.class.getResourceAsStream("FeedbackManager.properties"));
		} catch (IOException e) {
			ServerLogger.get().error(e, "Couldn't load properties for FeedbackManager");
		}
	}
	
	private ArrayList<Pair<Construction, Boolean>> feedbackSupport = new ArrayList<>();
	
	/**
	 * Determines the exercise topic.
	 * @param settings The exercise settings.
	 * @return The topic
	 */
	private String getTopic(ExerciseSettings settings) {
		String topic = "";
		if(settings.getConstructions().size() > 0) {
			if(settings.getConstructions().get(0).getConstruction().toString().startsWith("COND")) {
				topic = "Conditional";
			} else if(settings.getConstructions().get(0).getConstruction().toString().startsWith("ADJ") || 
					settings.getConstructions().get(0).getConstruction().toString().startsWith("ADV")) {
				topic = "Comparison";
			} else if(settings.getConstructions().get(0).getConstruction().toString().startsWith("QUEST") || 
					settings.getConstructions().get(0).getConstruction().toString().startsWith("STMT")) {
				topic = "Simple Present";
			} else if(settings.getConstructions().get(0).getConstruction().toString().startsWith("PAST") || 
					settings.getConstructions().get(0).getConstruction().toString().startsWith("PRES")) {
				topic = "Past tenses";
			} else if(settings.getConstructions().get(0).getConstruction().toString().startsWith("PASSIVE") || 
					settings.getConstructions().get(0).getConstruction().toString().startsWith("ACTIVE")) {
				topic = "Passive";
			} else {
				topic = "Relative pronouns";
			}
		}
		
		return topic;
	}
	
	/**
     * Generates feedback for distractors.
	 * @param usedConstructions	The constructions actually used as targets
	 * @param settings			The exercise settings
	 * @param nlpManager		The NLP manager
	 * @return					A list of distractors with associated feedback per construction
	 */
    public ArrayList<ArrayList<Pair<Pair<String, Boolean>, String>>> generateFeedback(ArrayList<Construction> usedConstructions, 
    		ExerciseSettings settings, NlpManager nlpManager) {
    	ArrayList<ArrayList<Pair<Pair<String, Boolean>, String>>> distractors = new ArrayList<>();
    	JSONArray responseObject = new JSONArray();

    	if(settings.isGenerateFeedback() && 
    			(settings.getContentType().equals(ExerciseType.FIB) && !settings.getBrackets().contains(BracketsProperties.ACTIVE_SENTENCE)
    					|| settings.getContentType().equals(ExerciseType.SINGLE_CHOICE)
    			)) {
    		
    		int startIndex = 0;
    		while(startIndex < usedConstructions.size()) {
    			int endIndex = startIndex + BATCH_SIZE > usedConstructions.size() ? usedConstructions.size() : startIndex + BATCH_SIZE;
    			List<Construction> batchList = usedConstructions.subList(startIndex, endIndex);
        		startIndex = endIndex;
    			JSONObject requestObject = 
        				prepareJsonRequest(batchList, nlpManager, getTopic(settings), settings.getContentType(), settings.getPlainText());
        		
        		if(requestObject != null) {
        			JSONArray r = getResponseFromMicroservice(requestObject);
        			if(r != null) {
        				responseObject.addAll(r);
        			} else {
        				for(int j = 0; j < usedConstructions.size(); j++) {
        					responseObject.add(new JSONObject());
        				}
        			}
        		}
    		}   		
    	}
    	
    	if(responseObject != null) {
	    	int i = 0;
	    	for(Object exerciseItem : responseObject) {
	    		Construction construction = null;
	    		while(i < feedbackSupport.size() && construction == null) {
	    			if(feedbackSupport.get(i).second) {
	    				construction = feedbackSupport.get(i).first;
	    			}
	    			i++;
	    		}
	    		
	    		try {
	    			addFeedbackToDistractors(construction, settings, (JSONObject)exerciseItem, distractors);
	    		} catch(Exception e) { }        		
	    	}
    	}
    	
    	// If we don't get distractors from the feedback generation, we use the ones we generated ourselves
		if(distractors == null || distractors.size() == 0) {
			for(Construction construction : settings.getConstructions()) {
				ArrayList<Pair<Pair<String, Boolean>, String>> currentDistractors = new ArrayList<>();
				for(Pair<String, Boolean> distractor : construction.getDistractors()) {
					currentDistractors.add(new Pair<>(distractor, null));
				}
				distractors.add(currentDistractors);
			}
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
    private JSONObject prepareJsonRequest(List<Construction> usedConstructions, NlpManager nlpManager, 
    		String topic, ExerciseType contentType, String plainText) { 	
		JSONArray activityItems = new JSONArray();
		
		for(Construction construction : usedConstructions) {
			Pair<Pair<String, String[]>, Integer> sentenceTexts = 
					nlpManager.getSentenceTexts(construction.getConstructionIndices(), plainText);
			boolean supportsFeedback = false;
			if(sentenceTexts != null) {
				JSONObject item = new JSONObject();
				item.put("prompt", sentenceTexts.first.first);
				String targetAnswer = sentenceTexts.first.second[0] + sentenceTexts.first.second[1] + sentenceTexts.first.second[2];
				item.put("targetAnswer", targetAnswer);
				JSONArray targetSpan = new JSONArray();
				targetSpan.add(construction.getConstructionIndices().first - sentenceTexts.second);
				targetSpan.add(construction.getConstructionIndices().second - sentenceTexts.second);
				item.put("targetSpan", targetSpan);
				activityItems.add(item);
				supportsFeedback = true;
			}	
			feedbackSupport.add(new Pair<>(construction, supportsFeedback));
		}
		
		if(activityItems.size() > 0) {
			JSONObject activitySpecification = new JSONObject();
			activitySpecification.put("items", activityItems);
						
			activitySpecification.put("exerciseTopic", topic);
			activitySpecification.put("exerciseType", contentType.toString());    		   		
			 		   		
			return activitySpecification;
		}
		
		return null;
    }
    
    /**
     * Connects to the microservice and gets the feedback for the requestObject.
     * @param requestObject	The requestObject containing the information on the targets for which feedback needs to be generated
     * @return				The response from the microservice if successful; otherwise null
     */
    private JSONArray getResponseFromMicroservice(JSONObject activitySpec) {
    	try {    	
    		JSONObject requestObject = new JSONObject();
    		requestObject.put("activitySpecification", activitySpec.toJSONString());
    		
    		String payload = requestObject.toJSONString();
    		StringEntity entity = new StringEntity(payload, ContentType.APPLICATION_JSON);  
    		
    		//List <NameValuePair> parameters = new ArrayList <NameValuePair>();
    		//parameters.add(new BasicNameValuePair("activitySpecification", payload));
    		            
            // set a 5min timeout
            int timeout = 5 * 60 * 1000;
            RequestConfig config = RequestConfig.custom()
              .setConnectTimeout(timeout)
              .setConnectionRequestTimeout(timeout)
              .setSocketTimeout(timeout).build();
            CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
            
            
            HttpPost request = new HttpPost(PROPERTIES.getProperty("url"));
            //request.setEntity(new UrlEncodedFormEntity(parameters));
            request.setEntity(entity);

            HttpResponse response = null;
            try {
            	response = httpClient.execute(request);        
            } catch(SocketTimeoutException ex) {
            	ex.printStackTrace();
            }
            
            if(response == null || response.getStatusLine().getStatusCode() == 500) {
            	return null;
            }
            
            String body = null;
            InputStream inputStream = response.getEntity().getContent();

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                if (inputStream != null) {
                    StringBuilder sb = new StringBuilder();
                    char[] charBuffer = new char[128];
                    int bytesRead = -1;
                    while ((bytesRead = reader.read(charBuffer)) > 0) {
                        sb.append(charBuffer, 0, bytesRead);
                    }
                    body = sb.toString();
                }
            } catch (IOException ex) {
            	ex.printStackTrace();
            } 

            if(body != null) {
	            JSONParser parser = new JSONParser();
	            JSONObject responseObject = null;
	            try {
	                responseObject = (JSONObject) parser.parse(body);
	                if(responseObject != null && responseObject.containsKey("message")) {
	                	ServerLogger.get().info((String)responseObject.get("message"));
	                }
	                if(responseObject != null && responseObject.containsKey("externalFeedbackResourceBundlesJson")) {
	                	JSONArray feedbacksArray = (JSONArray) parser.parse((String)responseObject.get("externalFeedbackResourceBundlesJson"));
	                	if(((JSONArray)activitySpec.get("items")).size() == feedbacksArray.size()) {
	                		return feedbacksArray;
	                	} else {
	                		return null;
	                	}
				    }
	            } catch (ParseException e) {
	                e.printStackTrace();
	            }
            }
            
            /*
			
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
			    JSONObject responseObject = (JSONObject) parser.parse(response.toString());
			    if(responseObject != null && responseObject.containsKey("externalFeedbackResourceBundlesJson")) {
			    	return (JSONArray)responseObject.get("externalFeedbackResourceBundlesJson");
			    }
			} catch (ParseException e) {
				e.printStackTrace();
			}
			*/
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    
    /**
     * Adds the generated feedback to existing distractors or generates wrong answers with associated feedback from the generated feedback for the construction.
     * @param construction			The construction
     * @param settings				The exercise settings
     * @param exerciseItemFeedbacks	The feedback items from the microservice
     */
    private void addFeedbackToDistractors(Construction construction, 
    		ExerciseSettings settings, JSONObject exerciseItemFeedbacks, 
    		ArrayList<ArrayList<Pair<Pair<String, Boolean>, String>>> distractors) {
    	
    	ArrayList<Pair<Pair<String, Boolean>, String>> currentDistractors = new ArrayList<>();
    	ArrayList<Pair<String, Boolean>> usedDistractors = new ArrayList<>();

    	for(Iterator iterator = exerciseItemFeedbacks.keySet().iterator(); iterator.hasNext();) {
			String targetHypothesis = (String) iterator.next();
			String feedback = (String) ((JSONObject)exerciseItemFeedbacks.get(targetHypothesis)).get("feedbackMessage");
			
	    	if(settings.getContentType().equals(ExerciseType.SINGLE_CHOICE)) {
				for(Pair<String, Boolean> distractor : construction.getDistractors()) {
					if(targetHypothesis.equals(distractor.first)) {
						currentDistractors.add(new Pair<>(distractor, feedback));
						usedDistractors.add(distractor);
					} 
				}
			} else {
				currentDistractors.add(new Pair<>(new Pair<>(targetHypothesis, false), feedback));
			}
		}  
    	
    	// Add distractors without feedback for Single Choice exercises
    	if(settings.getContentType().equals(ExerciseType.SINGLE_CHOICE)) {
			for(Pair<String, Boolean> distractor : construction.getDistractors()) {
				if(!usedDistractors.contains(distractor)) {
					currentDistractors.add(new Pair<>(distractor, null));
				} 
			}
		}
    			
		distractors.add(currentDistractors);
    }
   
}
