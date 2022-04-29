package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang3.StringUtils;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.shared.exerciseGeneration.DetailedConstruction;

public class FiBXmlGenerator extends SimpleExerciseXmlGenerator {
	
	@Override
	protected XmlValues generateXMLValues(ExerciseData exerciseDefinition) {
		XmlValues v = new XmlValues(exerciseDefinition.getInstructions(), "FILL_IN_THE_BLANKS");
		
		StringBuilder sb = new StringBuilder();
		Item previousGap = null;
				
		for(TextPart element : exerciseDefinition.getParts()) {
			if(!(element instanceof ConstructionTextPart)) {
				sb.append(element.getValue());
			} else {
				if(previousGap == null) {
					previousGap = new Item();
				} 
				
				if(previousGap != null) {
					previousGap.setText(sb.toString());
					v.getItems().add(previousGap);
				}
				
				previousGap = new Item();
				ArrayList<String> targets = new ArrayList<>();
				targets.add(element.getValue());
				targets.addAll(((ConstructionTextPart)element).getTargetAlternatives());
				previousGap.setTarget(StringUtils.join(targets, "|"));
				previousGap.setInputType(previousGap.getTarget().matches(".*?[\\s\\h\\v].*?") ? "PHRASE" : "WORD");	
				if(((ConstructionTextPart)element).getFallbackFeedback() != null) {
					previousGap.setFeedback(((ConstructionTextPart)element).getFallbackFeedback());
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
		
		if(exerciseDefinition.getInstructionLemmas().size() > 0) {
			Collections.shuffle(exerciseDefinition.getInstructionLemmas());
			v.setGivenWords(StringUtils.join(exerciseDefinition.getInstructionLemmas(), " | "));
		}

		return v;
	}
	
}
