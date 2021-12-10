package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.flair.shared.exerciseGeneration.Pair;

public class MemoryXmlManager extends SimpleExerciseXmlManager {
	
	@Override
	protected void addAttributes(ArrayList<Pair<String, Boolean>> parts, int index, String taskDescription,
			ArrayList<String> givenWords) {	
		super.addAttributes(parts, index, taskDescription, givenWords);
		attributes.put("task_type", "MEMORY");
		attributes.put("task_orient", "Offen");
		attributes.put("task_focus", "Form");
		attributes.put("given_words_draggable", "false");
    	attributes.put("feedback_disabled", "false");
	}
		
	@Override
	protected void addTaskFieldAttributes(String prompt, int index, String target, ArrayList<Pair<String,String>> distractors) {
		super.addTaskFieldAttributes(prompt, index, target, distractors);
		taskFieldAttributes.put("input_type", "MAPPING");
	}
	
	@Override
	protected String generateTaskFields(ArrayList<Pair<String, Boolean>> parts,
			ArrayList<Pair<Integer, Integer>> constructionIndices, 
			ArrayList<ArrayList<Pair<String,String>>> distractors, ArrayList<Pair<String, Integer>> targets) {
		ArrayList<String> pairs = new ArrayList<>();
		
		for(int i = 0; i < targets.size(); i++) {
			pairs.add(targets.get(i).first + " - " + distractors.get(i).get(0).first);
		}
		String prompt = StringUtils.join(pairs, "|");
        return generateTaskFieldDefinition(prompt, null, 1, null);        
	}

}
