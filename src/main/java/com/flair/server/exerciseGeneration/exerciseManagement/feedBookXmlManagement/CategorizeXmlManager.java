package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.flair.server.exerciseGeneration.OutputComponents;
import com.flair.shared.exerciseGeneration.Pair;

public class CategorizeXmlManager extends SimpleExerciseXmlManager {
	
	@Override
	protected void addAttributes(ArrayList<Pair<String, Boolean>> parts, int index, String taskDescription,
			ArrayList<String> givenWords) {	
		super.addAttributes(parts, index, taskDescription, givenWords);

		attributes.put("task_type", "CATEGORIZE");
		attributes.put("task_orient", "Offen");
		attributes.put("task_focus", "Form");
		attributes.put("given_words_draggable", "true");
    	attributes.put("feedback_disabled", "false");
	}
	
	@Override
	protected void addTaskFieldAttributes(String prompt, int index, String target, ArrayList<Pair<String,String>> distractors) {
		super.addTaskFieldAttributes(prompt, index, target, distractors);

		taskFieldAttributes.put("input_type", target.matches(".*?[\\s\\h\\v].*?") ? "PHRASE" : "WORD");
		taskFieldAttributes.put("target", target);
		taskFieldAttributes.put("text", prompt);
	}
	
	@Override
    public HashMap<String,String> generateFeedBookInputXml(boolean escapeHtml, ArrayList<ArrayList<Pair<String,String>>> distractors, 
    		int index, String plainText, ArrayList<String> htmlElements, String taskDescription, String title,
    		ArrayList<OutputComponents> simpleExercises, ArrayList<String> givenWords, ArrayList<Pair<String, Integer>> targets,
    		ArrayList<Integer> conditionalTypes) {
    	StringBuilder xml = new StringBuilder();    	    	
    	
    	xml.append("<SubTask"); 	
    	
    	addAttributes(null, index, taskDescription, null);
    	    	
    	for(Entry<String, String> attribute : attributes.entrySet()) {
    		xml.append(" ").append(attribute.getKey()).append("=\"").append(attribute.getValue()).append("\"");
    	}
    	xml.append("><Prompts>");
    	xml.append(generateTaskFields(targets, conditionalTypes));
    	xml.append("</Prompts></SubTask>");
    	
    	HashMap<String, String> feedBookXml = new HashMap<>();
        feedBookXml.put(title, xml.toString().replace("\n", ""));
        
        return feedBookXml;
    }
	
	
	
	protected String generateTaskFields(ArrayList<Pair<String, Integer>> targets, ArrayList<Integer> conditionalTypes) {
		StringBuilder taskFields = new StringBuilder();
		ArrayList<String> sentence = new ArrayList<>();
		ArrayList<String> type1Sentences = new ArrayList<String>();
		ArrayList<String> type2Sentences = new ArrayList<String>();

		int previousSentence = 0;
		int sentenceIndex = 0;
		for(int i = 0; i < targets.size(); i++) {
			if(targets.get(i).second != previousSentence) {
				if(sentence.size() > 0) {
					String target = StringUtils.join(sentence, " ");
					if(conditionalTypes.get(sentenceIndex) == 1) {
						type1Sentences.add(target);
					} else {
						type2Sentences.add(target);
					}
					
					sentence = new ArrayList<>();
					sentenceIndex++;
				}
				previousSentence = targets.get(i).second;
			}
			sentence.add(targets.get(i).first);
		}
		if(sentence.size() > 0) {
			String target = StringUtils.join(sentence, " ");
			if(conditionalTypes.get(sentenceIndex) == 1) {
				type1Sentences.add(target);
			} else {
				type2Sentences.add(target);
			}
		}
		
		if(type1Sentences.size() > 0 && type2Sentences.size() > 0) {
			taskFields.append(generateTaskFieldDefinition("Conditional Type 1", StringUtils.join(type1Sentences, "|"), 
					1, null));
			taskFields.append(generateTaskFieldDefinition("Conditional Type 2", StringUtils.join(type2Sentences, "|"), 
					2, null));
		}
		
        return taskFields.toString();
	}
}
