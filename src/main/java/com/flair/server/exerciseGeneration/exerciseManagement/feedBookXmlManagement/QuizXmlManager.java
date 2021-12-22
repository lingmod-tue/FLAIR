package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import com.flair.server.exerciseGeneration.OutputComponents;
import com.flair.shared.exerciseGeneration.Pair;

public class QuizXmlManager implements XmlManager {
	
	@Override
    public HashMap<String,String> generateFeedBookInputXml(boolean escapeHtml, ArrayList<ArrayList<Pair<String,String>>> distractors, 
    		int index, String plainText, ArrayList<String> htmlElements, String taskDescription, String title, 
    		ArrayList<OutputComponents> simpleExercises, ArrayList<String> givenWords, 
    		ArrayList<Pair<String, Integer>> targets, ArrayList<Integer> conditionalTypes) {
        HashMap<String, String> xmls = new HashMap<>();

        for(OutputComponents simpleExercise : simpleExercises) {
	        for (Entry<String, String> entry : simpleExercise.getFeedBookXml().entrySet()) {
	        	xmls.put(entry.getKey(), entry.getValue());
	        }
        }
        
        return xmls;
    }
}
