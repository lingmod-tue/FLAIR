package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.shared.exerciseGeneration.Pair;

public class CategorizeXmlGenerator extends SimpleExerciseXmlGenerator {
	
	@Override
	public byte[] generateXMLFile(ExerciseData exerciseDefinition) {
		XmlValues v = new XmlValues();
		v.instructions = exerciseDefinition.getInstructions();
		v.taskType = "CATEGORIZE";
		v.givenWordsDraggable = true;
		
		HashMap<String, ArrayList<String>> pool = new HashMap<>();

		for(TextPart part : exerciseDefinition.getParts()) {
			if(part instanceof ConstructionTextPart) {
				ConstructionTextPart construction = (ConstructionTextPart)part;
				
				if(!pool.containsKey(construction.getCategory())) {
					pool.put(construction.getCategory(), new ArrayList<>());
				}
				
				pool.get(construction.getCategory()).add(construction.getValue());
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
			item.text = el.first;
			item.target = StringUtils.join(el.second, "|");
			item.inputType = "PHRASE";
			v.items.add(item);
			
			i++;
		}
						
		Collections.shuffle(elements);
		v.givenWords = StringUtils.join(elements, "|");

		
		return generateFeedBookInputXml(v).getBytes(StandardCharsets.UTF_8);
	}
	
}
