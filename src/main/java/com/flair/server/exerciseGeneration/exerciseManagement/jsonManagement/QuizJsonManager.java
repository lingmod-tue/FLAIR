package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.flair.server.exerciseGeneration.OutputComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.JsonComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.ResultComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.QuizSettings;
import com.flair.shared.exerciseGeneration.Pair;

public class QuizJsonManager extends JsonManager {

    @Override
    public OutputComponents modifyJsonContent(ContentTypeSettings settings, ArrayList<JsonComponents> jsonComponents, 
    		String folderName)
            throws IOException, ParseException {
        InputStream inputStream = getContentFileContent(folderName);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        
        inputStream.close();


        JSONArray questionsArray = (JSONArray)jsonObject.get("questions");
        HashMap<String, String> previewTexts = new HashMap<>();
        ArrayList<OutputComponents> containedExercises = new ArrayList<>();

        int i = 0;
        for(JsonComponents exerciseJsonComponents : jsonComponents) {
            ArrayList<JsonComponents> helper = new ArrayList<>();
            helper.add(exerciseJsonComponents);
            OutputComponents exerciseObject = exerciseJsonComponents.getJsonManager()
                    .modifyJsonContent(((QuizSettings)settings).getExercises().get(i), helper, null);
            containedExercises.add(exerciseObject);
            
            for (Entry<String, String> entry : exerciseObject.getPreviews().entrySet()) {
            	previewTexts.put(entry.getKey(), entry.getValue());
            }
                        
            JSONObject questionObject = new JSONObject();
            questionObject.put("params", exerciseObject.getH5pJson());
            questionObject.put("library", exerciseJsonComponents.getContentTypeLibrary());
            questionObject.put("subContentId", UUID.randomUUID().toString());
            questionsArray.add(questionObject);
            i++;
        }

        OutputComponents output = new OutputComponents(jsonObject, previewTexts, null, null, null, null);
        output.setSimpleExercises(containedExercises);
        return output;
    }

}
