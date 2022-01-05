package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.flair.server.exerciseGeneration.ConstructionTextPart;
import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.TextPart;

public class MemoryXmlGenerator extends SimpleExerciseXmlGenerator {
	
	@Override
	public byte[] generateXMLFile(ExerciseData exerciseDefinition, String exerciseType) {
		XmlValues v = new XmlValues();
		v.instructions = "Find pairs.";
		v.support = "[12/3 1:49 PM] Florian Nuxoll\n"
				+ "<hr>Reminder:<ul><li>Simple past: often goes with <em>yesterday, ago, last</em>...</li><li>Present\n"
				+ " perfect: often goes with with <em>since, for, ever, yet</em>…</li></ul>\n"
				+ "\n"
				+ "";
		v.taskType = "MEMORY";
		
		Item item = new Item();
		ArrayList<String> textElements = new ArrayList<>();
		for(TextPart element : exerciseDefinition.getParts()) {
			if(element instanceof ConstructionTextPart) {
				ConstructionTextPart el = (ConstructionTextPart)element;
				textElements.add(el.getValue() + " - " + el.getDistractors().get(0).getValue());
			}
		}
		item.text = StringUtils.join(textElements, "|");
		item.inputType = "MAPPING";
		v.items.add(item);

		return generateFeedBookInputXml(v).getBytes(StandardCharsets.UTF_8);
	}
	
}
