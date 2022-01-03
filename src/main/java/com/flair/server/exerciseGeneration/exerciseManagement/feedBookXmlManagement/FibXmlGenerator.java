package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;

import com.flair.server.exerciseGeneration.ConstructionTextPart;
import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.TextPart;

public class FibXmlGenerator extends SimpleExerciseXmlGenerator {
	
	@Override
	public byte[] generateXMLFile(ExerciseData exerciseDefinition, String exerciseType) {
		XmlValues v = new XmlValues();
		if(exerciseType.equals("3")) {
			v.instructions = "Fill in the gap to form a correct sentence.";
		} else if(exerciseType.equals("4")) {
			v.instructions = "Read each sentence. Which verb fits? Fill in the gap with the correct form of the verb.";
		} else if(exerciseType.equals("5")) {
			v.instructions = "Read each sentence. Which verb fits? Fill in the gap with the correct form of the verb.";
		} else if(exerciseType.equals("9")) {
			v.instructions = "Use the verbs in brackets to form a if-clause.";
		}
								
		v.taskType = "FILL_IN_THE_BLANKS";
		
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
					previousGap.text = sb.toString();
					v.items.add(previousGap);
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
		v.items.add(previousGap);
		
		if(exerciseDefinition.getInstructionLemmas().size() > 0) {
			Collections.shuffle(exerciseDefinition.getInstructionLemmas());
			v.givenWords = StringUtils.join(exerciseDefinition.getInstructionLemmas(), " | ");
		}

		return generateFeedBookInputXml(v).getBytes(StandardCharsets.UTF_8);
	}
	
}
