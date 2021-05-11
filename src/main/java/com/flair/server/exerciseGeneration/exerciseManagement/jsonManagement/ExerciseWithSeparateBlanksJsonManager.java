package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ExerciseWithSeparateBlanksJsonManager extends SimpleExerciseJsonManager {

    @Override
    protected void addQuestionsToJson(JSONObject jsonObject, ArrayList<String> plainTextElements,
                                      ArrayList<String> constructions) {
        StringBuilder sb = new StringBuilder();
        for(String plainTextElement : plainTextElements) {
            sb.append(plainTextElement);
        }

        String plainText = sb.toString().replace("*", "**") // escape asterisks used to designate blanks
                .replace("<span data-blank></span>", "*"); // replace blanks boundary placeholders with asterisks after escaping normal asterisks

        ((JSONObject)jsonObject.get("content")).put("blanksText", plainText);
    }

    @Override
    protected void addBlanksToJson(JSONObject jsonObject, ArrayList<String> blanks, ArrayList<ArrayList<String>> distractorStrings){
        //TODO: store feedback with distractors
        ArrayList<JSONObject> blanksList = new ArrayList<>();
        for(int i = 0; i < blanks.size(); i++) {
            String blank = blanks.get(i);
            JSONObject blanksObject = new JSONObject();
            blanksObject.put("correctAnswerText", blank);
            JSONArray distractors = new JSONArray();
            if(distractorStrings.size() > i) {
                for (String d : distractorStrings.get(i)) {
                    JSONObject distractor = new JSONObject();
                    distractor.put("showHighlight", false);
                    //distractor.put("highlight", "1");
                    distractor.put("incorrectAnswerText", d);
                    distractor.put("incorrectAnswerFeedback", "Feedback");
                    distractors.add(distractor);
                }
            }
            blanksObject.put("incorrectAnswersList", distractors);
            blanksList.add(blanksObject);
        }

        JSONArray blankList = (JSONArray)((JSONObject)jsonObject.get("content")).get("blanksList");
        blankList.addAll(blanksList);
    }

    @Override
    protected void setExerciseSpecificAttributes(JSONObject jsonObject) {}

}
