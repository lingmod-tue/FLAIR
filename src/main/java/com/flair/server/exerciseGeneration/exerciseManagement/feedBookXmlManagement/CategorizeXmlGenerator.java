package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.flair.server.exerciseGeneration.ConstructionTextPart;
import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.TextPart;

public class CategorizeXmlGenerator extends SimpleExerciseXmlGenerator {
	
	@Override
	public byte[] generateXMLFile(ExerciseData exerciseDefinition, String exerciseType) {
		XmlValues v = new XmlValues();
		v.instructions = "Read the sentences and decide if they are type 1 or type 2.";
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
		
		int i = 1;
		ArrayList<String> elements = new ArrayList<>();
		for(Entry<String, ArrayList<String>> el : pool.entrySet()) {

			for(String sentence : el.getValue()) {
				elements.add(sentence + " (" + i + ")");
			}
			
			Item item = new Item();
			item.text = el.getKey();
			item.target = StringUtils.join(el.getValue(), "|");
			item.inputType = "PHRASE";
			v.items.add(item);
			
			i++;
		}
				
		Collections.shuffle(elements);
		v.givenWords = StringUtils.join(elements, "|");

		
		return generateFeedBookInputXml(v).getBytes(StandardCharsets.UTF_8);
	}
	
}
