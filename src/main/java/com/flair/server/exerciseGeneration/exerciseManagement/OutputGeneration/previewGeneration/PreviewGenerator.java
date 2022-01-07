package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;

public abstract class PreviewGenerator {

	public String generatePreview(ExerciseData data) {
		StringBuilder sb = new StringBuilder();
		
		for(TextPart part : data.getParts()) {
			if(part instanceof ConstructionTextPart) {
				sb.append(getTargetDummy(part.getValue()));
			} else {
				sb.append(part.getValue());
			}
		}
		
		return "<iframe style='position:absolute; width: 95%; height:100%;padding-bottom:80px;border:none;' srcdoc='" + 
				sb.toString().replace("'", "\"") + "'> IFrames are not supported by your browser.</iframe>";		
	}
    
    /**
     * Provides a HTML element definition for a target construction.
     * @param constructionText	The target answer
     * @return	A HTML element definition for the given construction text
     */
    protected abstract String getTargetDummy(String constructionText);
    
}
