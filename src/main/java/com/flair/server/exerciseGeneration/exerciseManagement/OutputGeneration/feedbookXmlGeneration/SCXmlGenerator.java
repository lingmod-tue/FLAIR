package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.Distractor;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.shared.exerciseGeneration.DetailedConstruction;

public class SCXmlGenerator extends SimpleExerciseXmlGenerator {
	
	@Override
	protected XmlValues generateXMLValues(ExerciseData exerciseDefinition) {
		XmlValues v = new XmlValues(exerciseDefinition.getInstructions(), "FILL_IN_THE_BLANKS");
		v.setTaskOrient("Geschlossen");
		
		StringBuilder sb = new StringBuilder();
		Item previousGap = null;
		for(TextPart element : exerciseDefinition.getParts()) {
			if(!(element instanceof ConstructionTextPart)) {
				sb.append(element.getValue());
			} else {
				if(previousGap == null && sb.length() != 0) {
					previousGap = new Item();
				} 
				
				if(previousGap != null) {
					previousGap.setText(sb.toString());
					v.getItems().add(previousGap);
				}
				
				previousGap = new Item();
				previousGap.setTarget(element.getValue());
				previousGap.setInputType("MUL_CHOICE_BLANK");
				if(((ConstructionTextPart)element).getFallbackFeedback() != null) {
					previousGap.setFeedback(((ConstructionTextPart)element).getFallbackFeedback());
					
					if(((ConstructionTextPart)element).getConstructionType().equals(DetailedConstruction.REL_CLAUSE)) {
						previousGap.setLanguageConstruct("RELATIVE_CLAUSE");
					}
				}
				
				ArrayList<String> distractors = new ArrayList<>();
				for(Distractor d : ((ConstructionTextPart)element).getDistractors()) {
					distractors.add(d.getValue());
				}
				distractors.add(((ConstructionTextPart)element).getTargetIndex(), ((ConstructionTextPart)element).getValue());

				previousGap.setExample(StringUtils.join(distractors, "|"));
				previousGap.setTarget((distractors.indexOf(element.getValue()) + 1) + "");				
				
				sb = new StringBuilder();
			}
		}

		previousGap.setText(sb.toString());
		v.getItems().add(previousGap);

		return v;
	}
	
}