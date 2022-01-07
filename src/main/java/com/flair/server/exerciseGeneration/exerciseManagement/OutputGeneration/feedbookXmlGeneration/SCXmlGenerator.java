package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.lang.StringUtils;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.Distractor;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;

public class SCXmlGenerator extends SimpleExerciseXmlGenerator {
	
	@Override
	public byte[] generateXMLFile(ExerciseData exerciseDefinition) {
		XmlValues v = new XmlValues();
		v.instructions = exerciseDefinition.getInstructions();
		v.taskOrient = "Geschlossen";
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
				previousGap.inputType = "MUL_CHOICE_BLANK";
				
				ArrayList<String> distractors = new ArrayList<>();
				for(Distractor d : ((ConstructionTextPart)element).getDistractors()) {
					distractors.add(d.getValue());
				}
				distractors.add(((ConstructionTextPart)element).getValue());
				Collections.shuffle(distractors);

				previousGap.example = StringUtils.join(distractors, "|");
				previousGap.target = (distractors.indexOf(element.getValue()) + 1) + "";				
				
				sb = new StringBuilder();
			}
		}

		previousGap.text = sb.toString();
		v.items.add(previousGap);

		return generateFeedBookInputXml(v).getBytes(StandardCharsets.UTF_8);
	}
	
}
