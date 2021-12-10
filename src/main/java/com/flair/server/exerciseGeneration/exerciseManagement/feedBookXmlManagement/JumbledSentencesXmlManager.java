package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;

import com.flair.shared.exerciseGeneration.Pair;

public class JumbledSentencesXmlManager extends SimpleExerciseXmlManager {
	
	@Override
	protected void addAttributes(ArrayList<Pair<String, Boolean>> parts, int index, String taskDescription,
			ArrayList<String> givenWords) {	
		super.addAttributes(parts, index, taskDescription, givenWords);

		attributes.put("task_type", "JUMBLED_SENTENCES");
		attributes.put("task_orient", "Offen");
		attributes.put("task_focus", "Form");
		attributes.put("given_words_draggable", "false");
    	attributes.put("feedback_disabled", "false");
	}
	
	@Override
	protected void addTaskFieldAttributes(String prompt, int index, String target, ArrayList<Pair<String,String>> distractors) {
		super.addTaskFieldAttributes(prompt, index, target, distractors);

		taskFieldAttributes.put("input_type", "JUMBLED_SENTENCE_PARTS");
		taskFieldAttributes.put("target", target);
		taskFieldAttributes.put("text", prompt);
	}
	
	@Override
	protected String generateTaskFields(ArrayList<Pair<String, Boolean>> parts,
			ArrayList<Pair<Integer, Integer>> constructionIndices, 
			ArrayList<ArrayList<Pair<String,String>>> distractors, ArrayList<Pair<String, Integer>> targets) {
		StringBuilder taskFields = new StringBuilder();
		ArrayList<String> sentence = new ArrayList<>();
		int previousSentence = 0;
		for(int i = 0; i < targets.size(); i++) {
			if(targets.get(i).second != previousSentence) {
				if(sentence.size() > 0) {
					String target = StringUtils.join(sentence, "|");
					Collections.shuffle(sentence);
					taskFields.append(generateTaskFieldDefinition(StringUtils.join(sentence, "|"), 
							target, 1, null));
					sentence = new ArrayList<>();
				}
				previousSentence = targets.get(i).second;
			}
			sentence.add(targets.get(i).first);
		}
		if(sentence.size() > 0) {
			String target = StringUtils.join(sentence, "|");
			Collections.shuffle(sentence);
			taskFields.append(generateTaskFieldDefinition(StringUtils.join(sentence, "|"), 
					target, 1, null));
		}
        return taskFields.toString();
	}
}
