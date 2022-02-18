package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;

public class MemoryXmlGenerator extends SimpleExerciseXmlGenerator {
	
	@Override
	protected XmlValues generateXMLValues(ExerciseData exerciseDefinition) {
		XmlValues v = new XmlValues(exerciseDefinition.getInstructions(), "MEMORY");
		Item item = new Item();
		ArrayList<String> textElements = new ArrayList<>();
		for(TextPart element : exerciseDefinition.getParts()) {
			if(element instanceof ConstructionTextPart) {
				ConstructionTextPart el = (ConstructionTextPart)element;
				textElements.add(el.getValue() + " - " + el.getDistractors().get(0).getValue());
			}
		}
		item.setText(StringUtils.join(textElements, "|"));
		item.setInputType("MAPPING");

		v.getItems().add(item);

		return v;
	}
	
}
