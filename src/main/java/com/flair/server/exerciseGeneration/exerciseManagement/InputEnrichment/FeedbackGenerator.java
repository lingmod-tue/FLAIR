package com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.Distractor;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.resourceManagement.ResourceLoader;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.ExerciseTopic;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.Pair;

public class FeedbackGenerator {

	protected enum ExecutionMode {
		PARALLEL,
		SEQUENTIAL
	}

	protected static final Properties PROPERTIES = new Properties();
	protected static final int BATCH_SIZE = 5;
	protected static final ExecutionMode mode = ExecutionMode.SEQUENTIAL;

	static {
		try {
			PROPERTIES.load(FeedbackGenerator.class.getResourceAsStream("FeedbackGenerator.properties"));
		} catch (IOException e) {
			ServerLogger.get().error(e, "Couldn't load properties for FeedbackManager");
		}
	}
	
	public FeedbackGenerator() {
		try(InputStream content = ResourceLoader.loadFile("feedbackLinks.tsv"); 
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content))) {
		      String line;
		      while ((line = bufferedReader.readLine()) != null) {
		    	  String[] lineItems = line.split("\t");
		    	  if(lineItems.length == 2) {
		    		  feedbackLinks.put(lineItems[0], lineItems[1]);
		    	  }
		      }
		    } catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private ArrayList<Pair<ConstructionTextPart, Boolean>> feedbackSupport = new ArrayList<>();
	private HashMap<String, String> feedbackLinks = new HashMap<String, String>();
	protected ReentrantLock lock = new ReentrantLock();
	protected boolean isCancelled = false;
	
	public void cancelGeneration() {
		isCancelled = true;
	}
	
	/**
	 * Determines the exercise topic.
	 * @param settings The exercise settings.
	 * @return The topic
	 */
	protected String getTopic(String t) {
		if(t.equals(ExerciseTopic.CONDITIONALS)) {
			return "Conditional";
		} else if(t.equals(ExerciseTopic.COMPARISON)) {
			return "Comparison";
		} else if(t.equals(ExerciseTopic.PRESENT)) {
			return "Simple Present";
		} else if(t.equals(ExerciseTopic.PAST)) {
			return "Past tenses";
		} else if(t.equals(ExerciseTopic.PASSIVE)) {
			return "Passive";
		} else if(t.equals(ExerciseTopic.RELATIVES)) {
			return "Relative pronouns";
		} else {
			throw new IllegalArgumentException();
		}
	}
		
	/**
     * Generates feedback for distractors.
	 * @param usedConstructions	The constructions actually used as targets
	 * @param settings			The exercise settings
	 * @param nlpManager		The NLP manager
	 * @return					A list of distractors with associated feedback per construction
	 */
    public void generateFeedback(ExerciseSettings settings, NlpManager nlpManager, ExerciseData data, String type, 
    		String topic) {
    	JSONArray responseObject = new JSONArray();
    	ArrayList<Pair<List<ConstructionTextPart>, JSONArray>> feedbackBatches = new ArrayList<>();
    	ArrayList<ConstructionTextPart> usedConstructions = new ArrayList<>();
    	
    	for(TextPart part : data.getParts()) {
    		if(part instanceof ConstructionTextPart) {
    			usedConstructions.add((ConstructionTextPart) part);
    		}
    	}

    	if(settings.isGenerateFeedback() &&
    			(type.equals(ExerciseType.FILL_IN_THE_BLANKS) && !data.getBracketsProperties().contains(BracketsProperties.ACTIVE_SENTENCE)
    					|| type == ExerciseType.SINGLE_CHOICE)) {
    		int startIndex = 0;
    		while(startIndex < usedConstructions.size()) {
    			int endIndex = startIndex + BATCH_SIZE > usedConstructions.size() ? usedConstructions.size() : startIndex + BATCH_SIZE;
    			List<ConstructionTextPart> batchList = usedConstructions.subList(startIndex, endIndex);
    			feedbackBatches.add(new Pair<>(batchList, null));
        		startIndex = endIndex;	
    		}  
    		
    		for(Pair<List<ConstructionTextPart>, JSONArray> feedbackBatch : feedbackBatches) {
    			if(isCancelled) {
    				return;
    			}
    			
    			if(mode == ExecutionMode.PARALLEL) {
	    			Runnable r = new MicroserviceCall(feedbackBatch, nlpManager, getTopic(topic), type, data.getPlainText());
	    			new Thread(r).start();
    			} else {
    				callMicroservice(feedbackBatch, nlpManager, getTopic(topic), type, data.getPlainText());
    			}
    		}
    	}
    	
    	if(mode == ExecutionMode.PARALLEL) {
	    	while(feedbackBatches.stream().anyMatch(batch -> batch.second == null)) {
	    		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
	    	}
    	}
    	
    	for(Pair<List<ConstructionTextPart>, JSONArray> feedbackBatch : feedbackBatches) {
           	responseObject.addAll(feedbackBatch.second);
    	}
    	
    	int i = 0;
    	for(Object exerciseItem : responseObject) {
    		ConstructionTextPart construction = null;
    		while(i < feedbackSupport.size() && construction == null) {
    			if(feedbackSupport.get(i).second) {
    				construction = feedbackSupport.get(i).first;
    			}
    			i++;
    		}
    		
    		try {
    			addFeedbackToDistractors(construction, settings, (JSONObject)exerciseItem, type);
    		} catch(Exception e) { }        		
    	}    	
    }
    
    /**
     * Prepares the construction data to bring it into the format required by the FeedBook microservice.
     * @param settings			The exercise settings
     * @param usedConstructions	The constructions
     * @param nlpManager		The NLP manager
     * @return					The JSON object for the microservice request body
     */
    private JSONObject prepareJsonRequest(List<ConstructionTextPart> usedConstructions, NlpManager nlpManager, 
    		String topic, String contentType, String plainText) { 	
		JSONArray activityItems = new JSONArray();
		
		for(ConstructionTextPart construction : usedConstructions) {
			Pair<Pair<String, String[]>, Integer> sentenceTexts = 
					nlpManager.getSentenceTexts(construction.getIndicesInPlainText(), plainText);
			boolean supportsFeedback = false;
			if(sentenceTexts != null) {
				JSONObject item = new JSONObject();
				item.put("prompt", sentenceTexts.first.first);
				String targetAnswer = sentenceTexts.first.second[0] + sentenceTexts.first.second[1] + sentenceTexts.first.second[2];
				item.put("targetAnswer", targetAnswer);
				JSONArray targetSpan = new JSONArray();
				targetSpan.add(construction.getIndicesInPlainText().first - sentenceTexts.second);
				targetSpan.add(construction.getIndicesInPlainText().second - sentenceTexts.second);
				item.put("targetSpan", targetSpan);
				activityItems.add(item);
				supportsFeedback = true;
			}	
			lock.lock();
			feedbackSupport.add(new Pair<>(construction, supportsFeedback));
			lock.unlock();
		}
		
		if(activityItems.size() > 0) {
			JSONObject activitySpecification = new JSONObject();
			activitySpecification.put("items", activityItems);
						
			activitySpecification.put("exerciseTopic", topic);
			activitySpecification.put("exerciseType", contentType);    		   		
			 		   		
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
    		         
            // set a 15min timeout
            int timeout = 15 * 60 * 1000;
            RequestConfig config = RequestConfig.custom()
              .setConnectTimeout(timeout)
              .setConnectionRequestTimeout(timeout)
              .setSocketTimeout(timeout).build();
            CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
            
            
            HttpPost request = new HttpPost(PROPERTIES.getProperty("url"));
            request.setEntity(entity);

            HttpResponse response = null;
            try {
            	response = httpClient.execute(request);        
            } catch(SocketTimeoutException ex) {
            	ex.printStackTrace();
            }
            
            // Start block for deployment version
            if(response == null || response.getStatusLine().getStatusCode() != 200) {
            	if(response != null) {
            		ServerLogger.get().info("Feedback service returned with code " + response.getStatusLine().getStatusCode());
            	}
            	return null;
            }
            // End block for deployment version

            /*
            // Start block for evaluation version
            while(response != null && response.getStatusLine().getStatusCode() != 200) {
    			if(isCancelled) {
    				return null;
    			}
    			
            	try {
                	response = httpClient.execute(request);        
                } catch(SocketTimeoutException ex) {
                	ex.printStackTrace();
                }
            }
            // End block for evaluation version
            */
            
            String body = null;
            InputStream inputStream = response.getEntity().getContent();

            if (inputStream != null) {
	            try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
	                StringBuilder sb = new StringBuilder();
	                char[] charBuffer = new char[128];
	                int bytesRead = -1;
	                while ((bytesRead = reader.read(charBuffer)) > 0) {
	                    sb.append(charBuffer, 0, bytesRead);
	                }
	                body = sb.toString();
	            } catch (IOException ex) {
	            	ex.printStackTrace();
	            } 
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
    private void addFeedbackToDistractors(ConstructionTextPart construction, 
    		ExerciseSettings settings, JSONObject exerciseItemFeedbacks, String type) {
    	
    	for(Iterator<?> iterator = exerciseItemFeedbacks.keySet().iterator(); iterator.hasNext();) {
			String targetHypothesis = (String) iterator.next();
    		if(!(((JSONObject)exerciseItemFeedbacks.get(targetHypothesis)).containsKey("diagnosisCode") &&
    				((JSONObject)exerciseItemFeedbacks.get(targetHypothesis)).get("diagnosisCode").equals("TARGET_ANSWER"))) {
    			String feedback = (String) ((JSONObject)exerciseItemFeedbacks.get(targetHypothesis)).get("feedbackMessage");
    			for (String key : feedbackLinks.keySet()) {
    				feedback = feedback.replaceAll("href=\"/media/lif/WebContent/" + key + ".html", "href=\"" + feedbackLinks.get(key));
    			}
    			
    	    	if(type.equals(ExerciseType.SINGLE_CHOICE)) {
    				for(Distractor distractor : construction.getDistractors()) {
    					if(targetHypothesis.equals(distractor.getValue())) {
    						distractor.setFeedback(feedback);
    					} 
    				}
    			} else {
    				Distractor distractor = new Distractor(targetHypothesis);
    				distractor.setIllFormed(false);
    				distractor.setFeedback(feedback);
    			}
    		}		
		}      			
    }
    
    private class MicroserviceCall implements Runnable {

		public MicroserviceCall(Pair<List<ConstructionTextPart>, JSONArray> feedbackBatch, NlpManager nlpManager, String topic, 
				   String contentType, String plainText) {
			this.feedbackBatch = feedbackBatch;
			this.nlpManager = nlpManager;
			this.topic = topic;
			this.contentType = contentType;
			this.plainText = plainText;
		}
		   
		private final Pair<List<ConstructionTextPart>, JSONArray> feedbackBatch;
		private final NlpManager nlpManager;
		private final String topic;
		private final String contentType; 
		private final String plainText;
	
		/**
		 * Calls the microservice and stores the returned feedback with the corresponding Constructions
		 */
		public void run() {
			callMicroservice(feedbackBatch, nlpManager, topic, contentType, plainText);    		
		}
    }
    
    private void callMicroservice(Pair<List<ConstructionTextPart>, JSONArray> feedbackBatch, NlpManager nlpManager, String topic, 
			   String contentType, String plainText) {
    	JSONObject requestObject = prepareJsonRequest(feedbackBatch.first, nlpManager, topic, contentType, plainText);
		JSONArray r = null;
		
       	if(requestObject != null) {
       		r = getResponseFromMicroservice(requestObject);
       	}
       	
       	if(r == null) {
   			r = new JSONArray();
   			for(int j = 0; j < feedbackBatch.first.size(); j++) {
   				r.add(new JSONObject());
   			}
   		} 
		
		feedbackBatch.second = r;
    }
    
}
