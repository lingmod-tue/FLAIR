package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.flair.server.exerciseGeneration.exerciseManagement.JsonComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.QuizSettings;
import com.flair.shared.exerciseGeneration.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuizJsonManager extends JsonManager {

    @Override
    public Pair<JSONObject, HashMap<String, String>> modifyJsonContent(ContentTypeSettings settings, ArrayList<JsonComponents> jsonComponents, 
    		String folderName)
            throws IOException, ParseException {
        InputStream inputStream = getContentFileContent(folderName);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));


        JSONArray questionsArray = (JSONArray)jsonObject.get("questions");
        HashMap<String, String> previewTexts = new HashMap<>();

        int i = 0;
        for(JsonComponents exerciseJsonComponents : jsonComponents) {
            ArrayList<JsonComponents> helper = new ArrayList<>();
            helper.add(exerciseJsonComponents);
            Pair<JSONObject, HashMap<String, String>> exerciseObject = exerciseJsonComponents.getJsonManager()
                    .modifyJsonContent(((QuizSettings)settings).getExercises().get(i), helper, null);
            
            for (Entry<String, String> entry : exerciseObject.second.entrySet()) {
            	previewTexts.put(entry.getKey(), entry.getValue());
            }
            
            JSONObject questionObject = new JSONObject();
            questionObject.put("params", exerciseObject.first);
            questionObject.put("library", exerciseJsonComponents.getContentTypeLibrary());
            questionObject.put("subContentId", UUID.randomUUID().toString());
            questionsArray.add(questionObject);
            i++;
        }

        return new Pair<>(jsonObject, previewTexts);
    }

}
