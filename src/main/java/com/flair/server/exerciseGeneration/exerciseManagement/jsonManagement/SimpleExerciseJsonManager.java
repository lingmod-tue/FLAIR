package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.flair.server.exerciseGeneration.exerciseManagement.JsonComponents;

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

        ArrayList<String> plainTextArray = new ArrayList<>();
        for(ArrayList<String> plainTextElement : jsonComponents.get(0).getPlainTextPerSentence()) {
            StringBuilder sb = new StringBuilder();
            for(String plainText : plainTextElement) {
                sb.append(plainText);
            }
            if(!sb.toString().equals("")) {
                plainTextArray.add(sb.toString());
            }
        }

        jsonObject.put("taskDescription", jsonComponents.get(0).getTaskDescription());

        ArrayList<String> constructions = orderBlanks(plainTextArray, jsonComponents.get(0).getConstructions());
        addQuestionsToJson(jsonObject, plainTextArray, constructions);
        addBlanksToJson(jsonObject, constructions, jsonComponents.get(0).getDistractors());
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
    private ArrayList<String> orderBlanks(ArrayList<String> plainTextArray, ArrayList<String> unorderedBlanks) {
        ArrayList<String> blanks = new ArrayList<>();

        for(int i = 0; i < plainTextArray.size(); i++) {
            String plainText = plainTextArray.get(i);
            int placeholderIndex = plainText.indexOf("<span data-blank=\"");
            while (placeholderIndex != -1) {
                int indexStart = placeholderIndex + 18;
                int indexEnd = plainText.indexOf("\"", indexStart);
                int blanksIndex = Integer.parseInt(plainText.substring(indexStart, indexEnd));

                blanks.add(unorderedBlanks.get(blanksIndex));
                plainText = plainText.substring(0, placeholderIndex) + "<span data-blank></span>" + plainText.substring(indexEnd + 9);

                placeholderIndex = plainText.indexOf("<span data-blank=\"", placeholderIndex + 1);
                plainTextArray.set(i, plainText);
            }
        }

        return blanks;
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
    protected abstract void addQuestionsToJson(JSONObject jsonObject, ArrayList<String> plainTextElements,
                                               ArrayList<String> constructions);

    /**
     * Adds the pure HTML elements to the JSON object.
     * @param jsonObject 	The JSON object
     * @param htmlElements 	The pure HTML elements
     */
    protected void addHtmlElementsToJson(JSONObject jsonObject, JSONArray htmlElements){
        ((JSONObject)jsonObject.get("htmlElementsGroup")).put("htmlElements", htmlElements);
    }

    /**
     * Adds the extracted blanks texts to the JSON object.
     * @param jsonObject 	The JSON object
     * @param blanks 		The extracted blanks texts
     * @param distractors	The distractors (wrong choices) for Single Choice exercises
     */
    protected abstract void addBlanksToJson(JSONObject jsonObject, ArrayList<String> blanks,
                                            ArrayList<ArrayList<String>> distractors);

    /**
     * Allows to set other attributes in the JSON file particular to a specific content type.
     * @param jsonObject	The JSON object
     */
    protected abstract void setExerciseSpecificAttributes(JSONObject jsonObject);

}
