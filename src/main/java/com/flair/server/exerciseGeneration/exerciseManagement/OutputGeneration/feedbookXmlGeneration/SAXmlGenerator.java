package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.PlainTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.shared.exerciseGeneration.DetailedConstruction;

import edu.stanford.nlp.util.StringUtils;

public class SAXmlGenerator extends SimpleExerciseXmlGenerator {
	
	@Override
	public byte[] generateXMLFile(ExerciseData exerciseDefinition) {
		XmlValues v = new XmlValues(exerciseDefinition.getInstructions(), "SHORT_ANSWERS");
		
		StringBuilder sb = new StringBuilder();
		for(TextPart element : exerciseDefinition.getParts()) {
			if(element instanceof PlainTextPart) {
				sb.append(element.getValue());
			} else if(element instanceof ConstructionTextPart) {
				Item item = new Item();
				item.setText(sb.toString());	
				
				ArrayList<String> targets = new ArrayList<>();
				targets.add(element.getValue());
				targets.addAll(((ConstructionTextPart)element).getTargetAlternatives());
				item.setTarget(StringUtils.join(targets, "|"));
				item.setInputType("SENTENCE");	
				if(((ConstructionTextPart)element).getDistractors().size() > 0 && ((ConstructionTextPart)element).getDistractors().get(0).getFeedback() != null) {
					item.setFeedback(((ConstructionTextPart)element).getDistractors().get(0).getFeedback());
					if(((ConstructionTextPart)element).getConstructionType().equals(DetailedConstruction.REL_CLAUSE)) {
						item.setLanguageConstruct("RELATIVE_CLAUSE");
					}
				}
				v.getItems().add(item);

				sb = new StringBuilder();
			}
		}

		return generateFeedBookInputXml(v).getBytes(StandardCharsets.UTF_8);
	}
	
}
