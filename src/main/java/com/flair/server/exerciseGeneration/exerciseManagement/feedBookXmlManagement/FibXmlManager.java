package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.flair.shared.exerciseGeneration.Pair;

public class FibXmlManager extends SimpleExerciseXmlManager {
	
	@Override
	protected void addAttributes(ArrayList<Pair<String, Boolean>> parts, int index, String taskDescription,
			ArrayList<String> givenWords) {	
		super.addAttributes(parts, index, taskDescription, givenWords);

		attributes.put("task_type", "FILL_IN_THE_BLANKS");
		attributes.put("task_orient", "Offen");
		attributes.put("task_focus", "Form");
		attributes.put("given_words_draggable", "false");
    	attributes.put("feedback_disabled", "false");
    	
    	if(givenWords != null && givenWords.size() > 0) {
        	attributes.put("given_words", StringUtils.join(givenWords, "|"));
    	}
	}
	
	@Override
	protected void addTaskFieldAttributes(String prompt, int index, String target, ArrayList<Pair<String,String>> distractors) {
		super.addTaskFieldAttributes(prompt, index, target, distractors);

		if(!target.equals("")) {
			taskFieldAttributes.put("input_type", target.matches(".*?[\\s\\h\\v].*?") ? "PHRASE" : "WORD");
		} else {
			taskFieldAttributes.put("input_type", "null");
		}
		taskFieldAttributes.put("target", target);
	}
}
