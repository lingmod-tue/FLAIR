package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.flair.server.exerciseGeneration.OutputComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.JsonComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.shared.exerciseGeneration.Pair;

public class MemoryJsonManager extends JsonManager {

	@Override
	public OutputComponents modifyJsonContent(ContentTypeSettings settings, ArrayList<JsonComponents> jsonComponents,
			String folderName) throws IOException, ParseException {
		
		InputStream inputStream = getContentFileContent(jsonComponents.get(0).getFolderName());

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        
        inputStream.close();

        String instructions = jsonComponents.get(0).getTaskDescription();
    	
        jsonObject.put("taskDescription", instructions);
        
        JSONArray cardsArray = new JSONArray();
        Pair<ArrayList<Pair<String, Integer>>, ArrayList<ArrayList<Pair<String, String>>>> orderedElements = 
        		orderBlanks(jsonComponents.get(0).getPlainTextElements(), 
        				jsonComponents.get(0).getConstructions(),
        				jsonComponents.get(0).getDistractors());
        for(int i = 0; i < orderedElements.first.size(); i++) {
        	JSONObject cardObject = new JSONObject();
        	cardObject.put("textField", orderedElements.first.get(i));
        	cardObject.put("match", orderedElements.second.get(i).get(0).first);
        	cardsArray.add(cardObject);
        }
        
    	jsonObject.put("cards", cardsArray);


        HashMap<String, String> previews = new HashMap<>();
        previews.put(settings.getName(), generatePreviewHtml(jsonObject));
                
        return new OutputComponents(jsonObject, previews, orderedElements.second, "", null, 
        		jsonComponents.get(0).getTaskDescription(), orderedElements.first, settings.getName());
	}

	private String generatePreviewHtml(JSONObject jsonObject) {
		StringBuilder htmlString = new StringBuilder();
		htmlString.append("<div>");
		for(Object card : (JSONArray)jsonObject.get("cards")) {
			htmlString.append(generateHtmlCard((String)((JSONObject)card).get("textField")));
			htmlString.append(generateHtmlCard((String)((JSONObject)card).get("match")));
		}
		htmlString.append("</div>");
		
		return htmlString.toString();
	}
	
	private String generateHtmlCard(String text) {
		return "<span style='width: 100px; height: 100px;border: 1px solid black; margin: 10px;'>" + text + "</span>";
	}
}
