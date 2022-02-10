package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.ExerciseTopic;

public class JSXmlGenerator extends SimpleExerciseXmlGenerator {
	
	@Override
	protected XmlValues generateXMLValues(ExerciseData exerciseDefinition) {
		XmlValues v = new XmlValues(exerciseDefinition.getInstructions(), "JUMBLED_SENTENCES");

		if(exerciseDefinition.getTopic().equals(ExerciseTopic.RELATIVES)) {
			v.setEvaluateAtComplete(true);
		}
		
		int lastSentenceId = 0;
		ConstructionTextPart previousElement = null;
		ArrayList<ArrayList<String>> sentenceParts = new ArrayList<>();
		for(TextPart element : exerciseDefinition.getParts()) {
			if(element instanceof ConstructionTextPart) {
				if(element.getSentenceId() != lastSentenceId) {
					if(sentenceParts.size() > 0) {
						Item item = new Item();
						ArrayList<String> alternatives = new ArrayList<>();
						for(ArrayList<String> sentencePart : sentenceParts) {
							alternatives.add(StringUtils.join(sentencePart, "|"));
						}
						item.setTarget(StringUtils.join(alternatives, "#"));
						Collections.shuffle(sentenceParts.get(0));
						item.setText(StringUtils.join(sentenceParts.get(0), "|"));
						item.setInputType("JUMBLED_SENTENCE_PARTS");
						if(previousElement.getDistractors().size() > 0 && previousElement.getDistractors().get(0).getFeedback() != null) {
							item.setFeedback(previousElement.getDistractors().get(0).getFeedback());
							if(previousElement.getConstructionType().equals(DetailedConstruction.REL_CLAUSE)) {
								item.setLanguageConstruct("RELATIVE_CLAUSE");
							}
						}
						v.getItems().add(item);
					}
					previousElement = (ConstructionTextPart)element;
					lastSentenceId = element.getSentenceId();
					sentenceParts.clear();
				}
				
				if(sentenceParts.size() == 0) {
					sentenceParts.add(new ArrayList<>());
					for(int i = 0; i < ((ConstructionTextPart)element).getTargetAlternatives().size(); i++) {
						sentenceParts.add(new ArrayList<>());
					}
				}
				sentenceParts.get(0).add(element.getValue());
				for(int i = 0; i < ((ConstructionTextPart)element).getTargetAlternatives().size(); i++) {
					sentenceParts.get(i + 1).add(((ConstructionTextPart)element).getTargetAlternatives().get(i));
				}
			}
		}
		
		if(sentenceParts.size() > 0) {
			Item item = new Item();
			ArrayList<String> alternatives = new ArrayList<>();
			for(ArrayList<String> sentencePart : sentenceParts) {
				alternatives.add(StringUtils.join(sentencePart, "|"));
			}
			item.setTarget(StringUtils.join(alternatives, "#"));
			Collections.shuffle(sentenceParts.get(0));
			item.setText(StringUtils.join(sentenceParts.get(0), "|"));
			item.setInputType("JUMBLED_SENTENCE_PARTS");
			if(previousElement.getDistractors().size() > 0 && previousElement.getDistractors().get(0).getFeedback() != null) {
				item.setFeedback(previousElement.getDistractors().get(0).getFeedback());
				if(previousElement.getConstructionType().equals(DetailedConstruction.REL_CLAUSE)) {
					item.setLanguageConstruct("RELATIVE_CLAUSE");
				}
			}
			v.getItems().add(item);
		}

		return v;
	}
	
}
