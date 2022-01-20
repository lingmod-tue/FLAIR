package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;

public class MemoryXmlGenerator extends SimpleExerciseXmlGenerator {
	
	@Override
	public byte[] generateXMLFile(ExerciseData exerciseDefinition) {
		XmlValues v = new XmlValues(exerciseDefinition.getInstructions(), "MEMORY");
		v.setSupport("[12/3 1:49 PM] Florian Nuxoll\n"
				+ "<hr>Reminder:<ul><li>Simple past: often goes with <em>yesterday, ago, last</em>...</li><li>Present\n"
				+ " perfect: often goes with with <em>since, for, ever, yet</em>…</li></ul>\n"
				+ "\n"
				+ "");
		
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
		v.getItems().add(item);

		return generateFeedBookInputXml(v).getBytes(StandardCharsets.UTF_8);
	}
	
}
