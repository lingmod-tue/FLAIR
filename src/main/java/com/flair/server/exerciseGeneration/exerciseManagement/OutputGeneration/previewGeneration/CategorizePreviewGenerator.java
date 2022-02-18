package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.shared.exerciseGeneration.Pair;

public class CategorizePreviewGenerator extends PreviewGenerator {

	@Override
	public String generatePreview(ExerciseData data) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div>");
		
		HashMap<String, ArrayList<String>> pool = new HashMap<>();
		for(TextPart part : data.getParts()) {
			if(part instanceof ConstructionTextPart) {
				ConstructionTextPart construction = (ConstructionTextPart)part;
				
				if(!pool.containsKey(construction.getCategory())) {
					pool.put(construction.getCategory(), new ArrayList<>());
				}
				
				pool.get(construction.getCategory()).add(getTargetDummy(construction.getValue()));
			}
		}
		
		ArrayList<Pair<String, ArrayList<String>>> sortedPool = new ArrayList<>();
		for(Entry<String, ArrayList<String>> el : pool.entrySet()) {
			sortedPool.add(new Pair<>(el.getKey(), el.getValue()));
		}
		Collections.sort(sortedPool, (i1, i2) -> 
		i1.first.charAt(i1.first.length() - 1) < i2.first.charAt(i2.first.length() - 1) ? 
			-1 : 1);
				
		for(Pair<String, ArrayList<String>> el : sortedPool) {
			sb.append("<h3>" + el.first + ":</h3>");
			sb.append("<div style='border: 1px solid black; margin: 10px;'>");
			sb.append(StringUtils.join(el.second, ""));
			sb.append("</div>");
		}
		
		sb.append("</div>");
		
		return sb.toString();
	}
    
    @Override
    protected String getTargetDummy(String constructionText) {
		return "<span style='border: 1px solid black; margin: 10px;'>" + constructionText + "</span>";
    }
    
}
