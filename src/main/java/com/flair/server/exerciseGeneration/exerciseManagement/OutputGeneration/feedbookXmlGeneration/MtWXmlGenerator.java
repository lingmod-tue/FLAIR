package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.HtmlTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.shared.exerciseGeneration.Pair;

public class MtWXmlGenerator extends SimpleExerciseXmlGenerator {
	
	@Override
	protected XmlValues generateXMLValues(ExerciseData exerciseDefinition) {
		XmlValues v = new XmlValues(exerciseDefinition.getInstructions(), "UNDERLINE");
		Item item = new Item();
		item.setInputType("UNDERLINE");
		
		ArrayList<Pair<Integer, Integer>> indices = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		boolean previousWasHtml = false;
		for(TextPart element : exerciseDefinition.getParts()) {
			if(element instanceof ConstructionTextPart) {
				sb.append(" ");
				int startIndex = sb.length();
				sb.append(element.getValue().replace(" ", "%"));				
				int endIndex = sb.length();
				sb.append(" ");
				indices.add(new Pair<>(startIndex, endIndex));
				previousWasHtml = false;
			} else if(element instanceof HtmlTextPart) {
				String text = element.getValue();
				if(!previousWasHtml && text.startsWith(" ")) {
					sb.append(" ");
					text = text.substring(1);
				}
				sb.append(text.replace(" ", "%"));
				previousWasHtml = true;
			} else {
				sb.append(element.getValue());
				previousWasHtml = false;
			}
		}
		
		ArrayList<String> indexRanges = new ArrayList<>();
		for(Pair<Integer, Integer> index : indices) {
			indexRanges.add(index.first + "-" + index.second);
		}
		
		item.setTarget(StringUtils.join(indexRanges, ","));
		item.setText(sb.toString());
		
		v.getItems().add(item);

		return v;
	}
	
}
