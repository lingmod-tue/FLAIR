package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.shared.exerciseGeneration.DetailedConstruction;


public class DDSingleXmlGenerator extends SimpleExerciseXmlGenerator {
	
	@Override
	protected XmlValues generateXMLValues(ExerciseData exerciseDefinition) {
		XmlValues v = new XmlValues(exerciseDefinition.getInstructions(), "FILL_IN_THE_BLANKS");
		v.setGivenWordsDraggable(true);
		v.setFeedbackDisabled(true);
				
		StringBuilder sb = new StringBuilder();
		Item previousGap = null;
		ArrayList<String> draggables = new ArrayList<>();
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
				draggables.add(element.getValue());
				previousGap.setInputType(previousGap.getTarget().matches(".*?[\\s\\h\\v].*?") ? "PHRASE" : "WORD");	
				if(((ConstructionTextPart)element).getDistractors().size() > 0 && ((ConstructionTextPart)element).getDistractors().get(0).getFeedback() != null) {
					previousGap.setFeedback(((ConstructionTextPart)element).getDistractors().get(0).getFeedback());
					if(((ConstructionTextPart)element).getConstructionType().equals(DetailedConstruction.REL_CLAUSE)) {
						previousGap.setLanguageConstruct("RELATIVE_CLAUSE");
					}
				}
				
				sb = new StringBuilder();

				if(((ConstructionTextPart)element).getBrackets().size() > 0) {
					sb.append("(").append(StringUtils.join(((ConstructionTextPart)element).getBrackets(), ", ")).append(") ");
				}
			}
		}
		
		previousGap.setText(sb.toString());
		v.getItems().add(previousGap);
		
		Collections.shuffle(draggables);
		v.setGivenWords(StringUtils.join(draggables, " | "));

		return v;
	}
	
}
