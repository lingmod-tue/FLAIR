package com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ExerciseWithInlineBlanksJsonManager  extends SimpleExerciseJsonManager {

	@Override
    protected void addQuestionsToJson(JSONObject jsonObject, ArrayList<String> plainTextElements,
                                      ArrayList<String> constructions, ArrayList<ArrayList<String>> distractors) {
        StringBuilder sb = new StringBuilder();
        for(String plainTextElement : plainTextElements) {
            plainTextElement = plainTextElement.replace("*", "**"); // escape asterisks used to designate blanks
                                    
            while(plainTextElement.contains("<span data-blank></span>")) {
            	ArrayList<String> distractorList = new ArrayList<>();
                if(distractors.size() > 0) {
                    distractorList = distractors.get(0);
                    distractors.remove(0);
                }
                
                plainTextElement = plainTextElement.replaceFirst("<span data-blank></span>", getPlacehholderReplacement(constructions.get(0), distractorList));
                constructions.remove(0);
            }
            sb.append(plainTextElement);
        }

        jsonObject.put("textField", sb.toString());
    }

    /**
     * Puts together the string for an inline blank definition.
     * @param construction  The correct solution
     * @return              The blank definition
     */
    protected String getPlacehholderReplacement(String construction, ArrayList<String> distractorList) {
        return "*" + construction + "*";
    }

    @Override
    protected void addBlanksToJson(JSONObject jsonObject, ArrayList<String> blanks, ArrayList<ArrayList<String>> distractors){}

    @Override
    protected void setExerciseSpecificAttributes(JSONObject jsonObject) {}

}
