package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.H5PGeneration;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;

public class MemoryContentJsonGenerator extends SimpleExerciseContentJsonGenerator {
	
	@Override
	protected void addTextPartsToJson(JSONObject jsonObject, ExerciseData data) {
		JSONArray cardsArray = new JSONArray();
        for(TextPart part : data.getParts()) {
        	if(part instanceof ConstructionTextPart) {
        		if(((ConstructionTextPart)part).getDistractors().size() > 0 && 
        				((ConstructionTextPart)part).getDistractors().get(0) != null && 
        				!((ConstructionTextPart)part).getDistractors().get(0).getValue().isEmpty()) {
        			JSONObject cardObject = new JSONObject();
                	cardObject.put("textField", ((ConstructionTextPart)part).getValue());
                	cardObject.put("match", ((ConstructionTextPart)part).getDistractors().get(0).getValue());
                	cardsArray.add(cardObject);
        		}
        	}
        }
        
    	jsonObject.put("cards", cardsArray);
	}

}
