package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.internal.StringUtil;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.shared.exerciseGeneration.Pair;

public class CategorizeXmlGenerator extends SimpleExerciseXmlGenerator {
	
	@Override
	protected XmlValues generateXMLValues(ExerciseData exerciseDefinition) {
		XmlValues v = new XmlValues(exerciseDefinition.getInstructions(), "CATEGORIZE");
		v.setGivenWordsDraggable(true);
		
		HashMap<String, ArrayList<String>> pool = new HashMap<>();
		HashMap<String, ArrayList<String>> feedback = new HashMap<>();

		for(TextPart part : exerciseDefinition.getParts()) {
			if(part instanceof ConstructionTextPart) {
				ConstructionTextPart construction = (ConstructionTextPart)part;
				
				if(!pool.containsKey(construction.getCategory())) {
					pool.put(construction.getCategory(), new ArrayList<>());
					feedback.put(construction.getCategory(), new ArrayList<>());
				}
				
				pool.get(construction.getCategory()).add(construction.getValue());
		
				String fb = construction.getFallbackFeedback() != null ? construction.getFallbackFeedback() : null;
				if(fb != null) {
					feedback.get(construction.getCategory()).add(fb);
				}
			}
		}
		
		ArrayList<Pair<String, ArrayList<String>>> sortedPool = new ArrayList<>();
		for(Entry<String, ArrayList<String>> el : pool.entrySet()) {
			sortedPool.add(new Pair<>(el.getKey(), el.getValue()));
		}
		Collections.sort(sortedPool, (i1, i2) -> 
		i1.first.charAt(i1.first.length() - 1) < i2.first.charAt(i2.first.length() - 1) ? 
			-1 : 1);
				
		int i = 1;
		ArrayList<String> elements = new ArrayList<>();
		for(Pair<String, ArrayList<String>> el : sortedPool) {
			for(String sentence : el.second) {
				elements.add(sentence + " (" + i + ")");
			}
			
			Item item = new Item();
			item.setText(el.first);
			item.setTarget(StringUtils.join(el.second, "|"));
			item.setInputType("PHRASE");
			item.setLanguageConstruct("RELATIVE_CLAUSE");
			
			if(feedback.get(el.first).size() > 0) {
				item.setFeedback(StringUtil.join(feedback.get(el.first), " "));
			}

			v.getItems().add(item);
			
			i++;
		}
						
		Collections.shuffle(elements);
		v.setGivenWords(StringUtils.join(elements, "|"));

		return v;
	}
	
}
