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
import java.util.UUID;

public class QuizJsonManager extends JsonManager {

    @Override
    public JSONObject modifyJsonContent(ArrayList<JsonComponents> jsonComponents, String folderName)
            throws IOException, ParseException {
        InputStream inputStream = getContentFileContent(folderName);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));


        JSONArray questionsArray = (JSONArray)jsonObject.get("questions");

        for(JsonComponents exerciseJsonComponents : jsonComponents) {
            ArrayList<JsonComponents> helper = new ArrayList<>();
            helper.add(exerciseJsonComponents);
            JSONObject exerciseObject = exerciseJsonComponents.getJsonManager()
                    .modifyJsonContent(helper, null);
            JSONObject questionObject = new JSONObject();
            questionObject.put("params", exerciseObject);
            questionObject.put("library", exerciseJsonComponents.getContentTypeLibrary());
            questionObject.put("subContentId", UUID.randomUUID().toString());
            questionsArray.add(questionObject);

        }

        return jsonObject;
    }

}
