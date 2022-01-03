package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.flair.server.exerciseGeneration.ConstructionTextPart;
import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.TextPart;
import com.flair.shared.exerciseGeneration.Pair;

public class FindXmlGenerator extends SimpleExerciseXmlGenerator {
	
	@Override
	public byte[] generateXMLFile(ExerciseData exerciseDefinition, String exerciseType) {
		XmlValues v = new XmlValues();
		v.taskType = "UNDERLINE";
		v.instructions = "Underline the verb in the if clause";
		Item item = new Item();
		item.inputType = "UNDERLINE";
		
		ArrayList<Pair<Integer, Integer>> indices = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		for(TextPart element : exerciseDefinition.getParts()) {
			if(element instanceof ConstructionTextPart) {
				sb.append(" ");
				int startIndex = sb.length();
				sb.append(element.getValue().replace(" ", "%"));				
				int endIndex = sb.length();
				sb.append(" ");
				indices.add(new Pair<>(startIndex, endIndex));
			} else {
				sb.append(element.getValue());
			}
		}
		
		ArrayList<String> indexRanges = new ArrayList<>();
		for(Pair<Integer, Integer> index : indices) {
			indexRanges.add(index.first + "-" + index.second);
		}
		
		item.target = StringUtils.join(indexRanges, ",");
		item.text = sb.toString();
		
		v.items.add(item);


		return generateFeedBookInputXml(v).getBytes(StandardCharsets.UTF_8);
	}
	
}
