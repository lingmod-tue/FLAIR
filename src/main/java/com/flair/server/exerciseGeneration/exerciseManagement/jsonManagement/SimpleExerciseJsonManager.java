package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.flair.server.exerciseGeneration.exerciseManagement.JsonComponents;
import com.flair.shared.exerciseGeneration.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public abstract class SimpleExerciseJsonManager extends JsonManager {

    /**
     * Counter for exercises so that they get a unique id.
     * Necessary for Advanced FiB exercises within a quiz.
     */
    protected static int internalId = 0;

    @Override
    public JSONObject modifyJsonContent(ArrayList<JsonComponents> jsonComponents, String folderName)
            throws IOException, ParseException {
        internalId++;

        InputStream inputStream = getContentFileContent(jsonComponents.get(0).getFolderName());

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        jsonObject.put("taskDescription", jsonComponents.get(0).getTaskDescription());

        Pair<ArrayList<String>, ArrayList<ArrayList<Pair<String, String>>>> orderedElements = orderBlanks(jsonComponents.get(0).getPlainTextElements(), jsonComponents.get(0).getConstructions(),
        		jsonComponents.get(0).getDistractors());
        addQuestionsToJson(jsonObject, jsonComponents.get(0).getPlainTextElements(), orderedElements.first, orderedElements.second);
        JSONArray htmlArray = new JSONArray();
        htmlArray.addAll(jsonComponents.get(0).getPureHtmlElements());
        addHtmlElementsToJson(jsonObject, htmlArray);
        setExerciseSpecificAttributes(jsonObject);

        return jsonObject;
    }

    /**
     * Reorders the blanks to make sure that the order is chronological.
     * @param plainTextArray 	The plain text sentences
     * @param unorderedBlanks 	The blanks texts in the order in which they were extracted
     * @return 					The ordered blanks list
     */
    private Pair<ArrayList<String>, ArrayList<ArrayList<Pair<String, String>>>> orderBlanks(ArrayList<String> plainTextArray, 
    		ArrayList<String> unorderedBlanks, ArrayList<ArrayList<Pair<String, String>>> unorderedDistractors) {
        ArrayList<String> blanks = new ArrayList<>();
        ArrayList<ArrayList<Pair<String, String>>> distractors = new ArrayList<>();

        for(int i = 0; i < plainTextArray.size(); i++) {
            String plainText = plainTextArray.get(i);
            int placeholderIndex = plainText.indexOf("<span data-blank=\"");
            while (placeholderIndex != -1) {
                int indexStart = placeholderIndex + 18;
                int indexEnd = plainText.indexOf("\"", indexStart);
                int blanksIndex = Integer.parseInt(plainText.substring(indexStart, indexEnd));

                blanks.add(unorderedBlanks.get(blanksIndex));
                if(unorderedDistractors.size() > 0) {
                	distractors.add(unorderedDistractors.get(blanksIndex));
                }
                plainText = plainText.substring(0, placeholderIndex) + "<span data-blank></span>" + plainText.substring(indexEnd + 9);

                placeholderIndex = plainText.indexOf("<span data-blank=\"", placeholderIndex + 1);
                plainTextArray.set(i, plainText);
            }
        }

        return new Pair<>(blanks, distractors);
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
    protected void addQuestionsToJson(JSONObject jsonObject, ArrayList<String> plainTextElements,
            ArrayList<String> constructions, ArrayList<ArrayList<Pair<String, String>>> distractors) {
		StringBuilder sb = new StringBuilder();
		int feedbackId = 1;
		
		for(String plainTextElement : plainTextElements) {
			plainTextElement = plainTextElement.replace("*", "**"); // escape asterisks used to designate blanks
			          
			while(plainTextElement.contains("<span data-blank></span>")) {
				ArrayList<Pair<String, String>> distractorList = new ArrayList<>();
				if(distractors.size() > 0) {
					distractorList = distractors.get(0);
					distractors.remove(0);
				}
				
				plainTextElement = plainTextElement.replaceFirst("<span data-blank></span>", getPlacehholderReplacement(constructions.get(0), distractorList, "feedback" + feedbackId, jsonObject));
				feedbackId++;
				constructions.remove(0);
			}
			sb.append(plainTextElement);
		}
		
		jsonObject.put("textField", sb.toString());
	}

    /**
     * Adds the pure HTML elements to the JSON object.
     * @param jsonObject 	The JSON object
     * @param htmlElements 	The pure HTML elements
     */
    protected void addHtmlElementsToJson(JSONObject jsonObject, JSONArray htmlElements){
    	jsonObject.put("htmlElements", htmlElements);
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
