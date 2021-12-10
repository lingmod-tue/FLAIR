package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;

import com.flair.shared.exerciseGeneration.Pair;

public class SingleChoiceXmlManager extends SimpleExerciseXmlManager {

	@Override
	protected void addAttributes(ArrayList<Pair<String, Boolean>> parts, int index, String taskDescription,
			ArrayList<String> givenWords) {	
		super.addAttributes(parts, index, taskDescription, givenWords);

		attributes.put("task_type", "FILL_IN_THE_BLANKS");
		attributes.put("task_orient", "Geschlossen");
		attributes.put("task_focus", "Form");
		attributes.put("given_words_draggable", "false");
    	attributes.put("feedback_disabled", "false");
	}
	
	@Override
	protected void addTaskFieldAttributes(String prompt, int index, String target, ArrayList<Pair<String,String>> distractors) {
		super.addTaskFieldAttributes(prompt, index, target, distractors);
		
		if(!target.equals("")) {
			taskFieldAttributes.put("input_type", "MUL_CHOICE_BLANK");
			
	    	ArrayList<String> options = new ArrayList<>();
	    	
	    	options.add(target);
	    	
	    	for(Pair<String, String> distractor : distractors) {
	    		options.add(distractor.first);
	    	}
	    	Collections.shuffle(options);
	    	
	    	taskFieldAttributes.put("example", StringUtils.join(options, "|"));
	    	taskFieldAttributes.put("target", (options.indexOf(target) + 1) + "");
		} else {
			taskFieldAttributes.put("input_type", "null");
		}
	}
		
}
