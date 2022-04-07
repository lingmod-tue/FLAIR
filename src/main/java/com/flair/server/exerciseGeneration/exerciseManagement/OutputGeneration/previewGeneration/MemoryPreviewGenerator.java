package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;

public class MemoryPreviewGenerator extends PreviewGenerator {

	@Override
	public String generatePreview(ExerciseData data) {
		StringBuilder sb = new StringBuilder();
		sb.append("<div>");
		
		for(TextPart part : data.getParts()) {
			if(part instanceof ConstructionTextPart) {
				sb.append(getTargetDummy(part.getValue(), ((ConstructionTextPart)part).getBrackets()));
				sb.append(getTargetDummy(((ConstructionTextPart)part).getDistractors().get(0).getValue(), ((ConstructionTextPart)part).getBrackets()));
			}
		}

		sb.append("</div>");
		
		return sb.toString();
	}
    
    @Override
    protected String getTargetDummy(String constructionText, ArrayList<String> brackets) {
		return "<span style='width: 100px; height: 100px;border: 1px solid black; margin: 10px;'>" + constructionText + "</span>";
    }
    
}
