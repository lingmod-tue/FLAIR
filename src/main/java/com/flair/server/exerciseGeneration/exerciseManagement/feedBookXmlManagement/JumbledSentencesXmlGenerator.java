package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;

import com.flair.server.exerciseGeneration.ConstructionTextPart;
import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.TextPart;

public class JumbledSentencesXmlGenerator extends SimpleExerciseXmlGenerator {
	
	@Override
	public byte[] generateXMLFile(ExerciseData exerciseDefinition, String exerciseType) {
		XmlValues v = new XmlValues();
		v.instructions = "Put the parts of a sentence into a correct order.";
		v.taskType = "JUMBLED_SENTENCES";
		
		int lastSentenceId = 0;
		ArrayList<String> sentenceParts = new ArrayList<>();
		for(TextPart element : exerciseDefinition.getParts()) {
			if(element instanceof ConstructionTextPart) {
				if(element.getSentenceId() != lastSentenceId) {
					if(sentenceParts.size() > 0) {
						Item item = new Item();
						item.target = StringUtils.join(sentenceParts, "|");
						Collections.shuffle(sentenceParts);
						item.text = StringUtils.join(sentenceParts, "|");
						item.inputType = "JUMBLED_SENTENCE_PARTS";
						v.items.add(item);
					}
					lastSentenceId = element.getSentenceId();
					sentenceParts.clear();
				}
				
				sentenceParts.add(element.getValue());
			}
		}
		
		if(sentenceParts.size() > 0) {
			Item item = new Item();
			item.target = StringUtils.join(sentenceParts, "|");
			Collections.shuffle(sentenceParts);
			item.text = StringUtils.join(sentenceParts, "|");
			item.inputType = "JUMBLED_SENTENCE_PARTS";
			v.items.add(item);
		}

		return generateFeedBookInputXml(v).getBytes(StandardCharsets.UTF_8);
	}
	
}
