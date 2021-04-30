package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class SingleExerciseWithInlineBlanksJsonManager  extends SimpleExerciseJsonManager {

    @Override
    protected void addQuestionsToJson(JSONObject jsonObject, ArrayList<String> plainTextElements,
                                      ArrayList<String> constructions) {
        StringBuilder sb = new StringBuilder();
        for(String plainTextElement : plainTextElements) {
            plainTextElement = plainTextElement.replace("*", "**"); // escape asterisks used to designate blanks
            while(plainTextElement.contains("<span data-blank></span>")) {
                plainTextElement = plainTextElement.replaceFirst("<span data-blank></span>", "*" + constructions.get(0) + "*");
                constructions.remove(0);
            }
            sb.append(plainTextElement);
        }

        jsonObject.put("textField", sb.toString());
    }

    @Override
    protected void addBlanksToJson(JSONObject jsonObject, ArrayList<String> blanks, ArrayList<ArrayList<String>> distractors){}

    @Override
    protected void setExerciseSpecificAttributes(JSONObject jsonObject) {}

}
