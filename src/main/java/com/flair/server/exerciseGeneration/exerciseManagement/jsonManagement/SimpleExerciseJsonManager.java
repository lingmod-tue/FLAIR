package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;

import com.flair.server.exerciseGeneration.OutputComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.JsonComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.util.Utilities;
import com.flair.shared.exerciseGeneration.Pair;

public abstract class SimpleExerciseJsonManager extends JsonManager {

    /**
     * Counter for exercises so that they get a unique id.
     * Necessary for Advanced FiB exercises within a quiz.
     */
    protected static int internalId = 0;
    
    @Override
    public OutputComponents modifyJsonContent(ContentTypeSettings settings, ArrayList<JsonComponents> jsonComponents, String folderName)
            throws IOException, ParseException {
        internalId++;

        InputStream inputStream = getContentFileContent(jsonComponents.get(0).getFolderName());

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        
        inputStream.close();

        String instructions = jsonComponents.get(0).getTaskDescription();
    	if(jsonComponents.get(0).getInstructionLemmas() != null && jsonComponents.get(0).getInstructionLemmas().size() > 0) {
        	instructions += "</br><em>" + String.join("</em>, <em>", jsonComponents.get(0).getInstructionLemmas()) + "</em>";
    	}
    	
        jsonObject.put("taskDescription", instructions);

        Pair<ArrayList<Pair<String, Integer>>, ArrayList<ArrayList<Pair<String, String>>>> orderedElements = orderBlanks(jsonComponents.get(0).getPlainTextElements(), jsonComponents.get(0).getConstructions(),
        		jsonComponents.get(0).getDistractors());
        String plainText = addQuestionsToJson(jsonObject, jsonComponents.get(0).getPlainTextElements(), orderedElements.first, orderedElements.second);
        JSONArray htmlArray = new JSONArray();
        htmlArray.addAll(jsonComponents.get(0).getPureHtmlElements());
        ArrayList<String> htmlElements = addHtmlElementsToJson(jsonObject, htmlArray);
        setExerciseSpecificAttributes(jsonObject);
                
        HashMap<String, String> previews = new HashMap<>();
        previews.put(settings.getName(), generatePreviewHtml(jsonObject, settings.isEscapeAsterisksInHtml()));
                
        return new OutputComponents(jsonObject, previews, orderedElements.second, plainText, htmlElements, 
        		jsonComponents.get(0).getTaskDescription(), orderedElements.first, settings.getName());
    }
    
    /**
     * Generates the Preview HTML for display in FLAIR.
     * @param jsonObject The JSON object containing the HTML and plain text elements
     * @return The HTML string
     */
    private String generatePreviewHtml(JSONObject jsonObject, boolean escapeHtml) {
    	StringBuilder htmlPreview = new StringBuilder();
    	String plainText = (String)jsonObject.get("textField");
    	for(Object htmlElement : (JSONArray)jsonObject.get("htmlElements")) {
    		String htmlString = (String)htmlElement;
    		if(htmlString.startsWith("sentenceHtml ")) {
    			int startIndex = htmlString.indexOf(" ", 13) + 1;
    			if(escapeHtml) {
    				htmlString = htmlString.replace("**", "*");
    			}
    			htmlPreview.append(htmlString.substring(startIndex).replace("ltRep;", "<").replace("quotRep;", "\"")
    					.replace("gtRep;", ">").replace("#039Rep;", "'").replace("ampRep;", "&"));
    		} else {
    			String question = "";
    			int firstQuestionIndex = plainText.indexOf("<span data-internal");
    	        if (firstQuestionIndex != -1) {
    	            int nextQuestionIndex = plainText.indexOf("<span data-internal", firstQuestionIndex + 1);
    	            if (nextQuestionIndex != -1) {
    	                question = plainText.substring(0, nextQuestionIndex);
    	                plainText = plainText.substring(nextQuestionIndex);
    	            } else {
    	            	question = plainText;
    	                plainText = "";
    	            }
    	        }
    	        
    	        question = insertTargetDummies(question);    	        
    	        htmlPreview.append(question);
    		}
    	}
    	
    	return htmlPreview.toString();
    }
    
    /**
     * Provides a HTML element definition for a target construction.
     * @param constructionText	The target answer
     * @return	A HTML element definition for the given construction text
     */
    abstract String getTargetDummy(String constructionText);

    /**
     * Replaces target construction placeholders with HTML elements in the preview HTML string.
     * @param htmlPreview	The preview HTML string with placeholders for target constructions
     * @return	The preview HTML string with HTML elements for target constructions
     */
    protected String insertTargetDummies(String htmlPreview) {
    	StringBuilder modifiedPreview = new StringBuilder();
		int indexAfterLastAsterisk = 0;
		int nextAsteriskIndex = Utilities.getNextNotEscapedCharacter(htmlPreview, 0, "*");
		while(nextAsteriskIndex != -1) {
			int constructionStartIndex = nextAsteriskIndex;
			nextAsteriskIndex = Utilities.getNextNotEscapedCharacter(htmlPreview, nextAsteriskIndex + 1, "*");
			
			if(nextAsteriskIndex != -1) {	
				String constructionText = htmlPreview.substring(constructionStartIndex + 1, nextAsteriskIndex);
				modifiedPreview.append(htmlPreview.substring(indexAfterLastAsterisk, constructionStartIndex));
				modifiedPreview.append(getTargetDummy(constructionText));

				indexAfterLastAsterisk = nextAsteriskIndex + 1;
				nextAsteriskIndex = Utilities.getNextNotEscapedCharacter(htmlPreview, nextAsteriskIndex + 1, "*");
			}
		}
		
		if(indexAfterLastAsterisk < htmlPreview.length()) {
			modifiedPreview.append(htmlPreview.substring(indexAfterLastAsterisk));
		}
		
		return modifiedPreview.toString().replace("**", "*");
    }  

    /**
     * Replaces single asterisks in a string with double asterisks.
     * @param textToEscape	The strings in which asterisks are to be escaped
     */
    public void escapeAsterisks(ArrayList<String> textToEscape) {
        for(int i = 0; i < textToEscape.size(); i++) {
            String text = textToEscape.get(i);
            textToEscape.set(i, text.replace("*", "**"));
        }
    }

    /**
     * Adds the extracted plain text which is modifyable in the authoring tool to the JSON object.
     * @param jsonObject 		The JSON object
     * @param plainTextElements	The extracted plain text
     * @param constructions 	The extracted constructions
     */
    protected String addQuestionsToJson(JSONObject jsonObject, ArrayList<String> plainTextElements,
            ArrayList<Pair<String, Integer>> c, ArrayList<ArrayList<Pair<String, String>>> distractors) {
		StringBuilder sb = new StringBuilder();
		ArrayList<Pair<String, Integer>> constructions = new ArrayList<>(c);
		int feedbackId = 1;
		ArrayList<ArrayList<Pair<String, String>>> distractorsCopy = new ArrayList<>(distractors);
		
		for(String plainTextElement : plainTextElements) {
			plainTextElement = plainTextElement.replace("*", "**"); // escape asterisks used to designate blanks
			          
			while(plainTextElement.contains("<span data-blank></span>")) {
				ArrayList<Pair<String, String>> distractorList = new ArrayList<>();
				if(distractorsCopy.size() > 0) {
					distractorList = distractorsCopy.get(0);
					distractorsCopy.remove(0);
				}
				
				plainTextElement = plainTextElement.replaceFirst("<span data-blank></span>", getPlacehholderReplacement(constructions.get(0).first, distractorList, "feedback" + feedbackId, jsonObject));
				feedbackId++;
				constructions.remove(0);
			}
			sb.append(plainTextElement);
		}
		
		String plainText = sb.toString();
		jsonObject.put("textField", plainText);
		
		return plainText;
	}

    /**
     * Adds the pure HTML elements to the JSON object.
     * @param jsonObject 	The JSON object
     * @param htmlElements 	The pure HTML elements
     */
    protected ArrayList<String> addHtmlElementsToJson(JSONObject jsonObject, JSONArray htmlElements){
    	jsonObject.put("htmlElements", htmlElements);
    	
    	return new ArrayList<>(htmlElements);
    }

    /**
     * Allows to set other attributes in the JSON file particular to a specific content type.
     * @param jsonObject	The JSON object
     */
    protected void setExerciseSpecificAttributes(JSONObject jsonObject) {}
    
    /**
     * Puts together the string for an inline blank definition.
     * @param construction  The correct solution
     * @return              The blank definition
     */
    protected String getPlacehholderReplacement(String construction, ArrayList<Pair<String, String>> distractorList, String feedbackId, JSONObject jsonObject) {
        return "*" + construction + "*";
    }
    
    /**
     * Adds feedback elements per blank to JSON.
     * @param jsonObject	The JSON object
     * @param feedbackId	The feedback id referenced in the inline specification
     * @param distractors	The distractors of the current blank
     * @return				<c>true</c> if any feedback elements were added; otherwise <c>false</c>
     */
    protected boolean addFeedbackToJson(JSONObject jsonObject, String feedbackId, ArrayList<Pair<String, String>> distractors) {
        JSONArray incorrectAnswers = new JSONArray();
        for(int i = 0; i < distractors.size(); i++) {
        	Pair<String, String> distractor = distractors.get(i);

            if(distractor.first.trim().length() > 0) {
                JSONObject incorrectAnswer = new JSONObject();
                incorrectAnswer.put("incorrectAnswerText", distractor.first);
                if(distractor.second != null && !distractor.second.trim().equals("")) {
                    incorrectAnswer.put("incorrectAnswerFeedback", distractor.second);
                }
                incorrectAnswers.add(incorrectAnswer);
            }
        }

        if(incorrectAnswers.size() > 0) {
            JSONObject feedbackObject = new JSONObject();
            feedbackObject.put("feedbackId", feedbackId);
            feedbackObject.put("incorrectAnswersList", incorrectAnswers);
            ((JSONArray) jsonObject.get("blanksFeedback")).add(feedbackObject);

            return true;
        }

        return false;
    }

}
