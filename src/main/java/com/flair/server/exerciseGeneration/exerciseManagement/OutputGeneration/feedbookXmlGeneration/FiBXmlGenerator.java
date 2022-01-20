package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;

public class FiBXmlGenerator extends SimpleExerciseXmlGenerator {
	
	@Override
	public byte[] generateXMLFile(ExerciseData exerciseDefinition) {
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
					previousGap.text = sb.toString();
					v.getItems().add(previousGap);
				}
				
				previousGap = new Item();
				previousGap.target = element.getValue();
				previousGap.inputType = previousGap.target.matches(".*?[\\s\\h\\v].*?") ? "PHRASE" : "WORD";	
				
				sb = new StringBuilder();

				if(((ConstructionTextPart)element).getBrackets().size() > 0) {
					sb.append("(").append(StringUtils.join(((ConstructionTextPart)element).getBrackets(), ", ")).append(") ");
				}
			}
		}
		
		previousGap.text = sb.toString();
		v.getItems().add(previousGap);
		
		if(exerciseDefinition.getInstructionLemmas().size() > 0) {
			Collections.shuffle(exerciseDefinition.getInstructionLemmas());
			v.setGivenWords(StringUtils.join(exerciseDefinition.getInstructionLemmas(), " | "));
		}

		return generateFeedBookInputXml(v).getBytes(StandardCharsets.UTF_8);
	}
	
}
