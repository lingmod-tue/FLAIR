package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.H5PGeneration;

import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.shared.exerciseGeneration.ExerciseType;

public class QuizContentJsonGenerator extends ContentJsonGenerator {

    @Override
    public ArrayList<JSONObject> modifyJsonContent(ArrayList<ExerciseData> exerciseDefinitions) {
    	JSONObject jsonObject = getContentJson(ExerciseType.QUIZ);
        if(jsonObject == null) {
        	return null;
        }

        JSONArray questionsArray = (JSONArray)jsonObject.get("questions");
        ArrayList<JSONObject> containedExercises = new ArrayList<>();

        for(ExerciseData data : exerciseDefinitions) {
            ArrayList<ExerciseData> helper = new ArrayList<>();
            helper.add(data);
            ArrayList<JSONObject> exerciseObject = H5PGeneratorFactory.getContentJsonGenerator(data.getExerciseType())
	        		.modifyJsonContent(helper);
            containedExercises.addAll(exerciseObject);
                        
            JSONObject questionObject = new JSONObject();
            questionObject.put("params", exerciseObject);
            questionObject.put("library", H5PConstantsManager.getContentTypeLibrary(data.getExerciseType()));
            questionObject.put("subContentId", UUID.randomUUID().toString());
            questionsArray.add(questionObject);
        }

        return containedExercises;
    }

}
