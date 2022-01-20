package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration;

public class FiBPreviewGenerator extends PreviewGenerator {

    
    @Override
    protected String getTargetDummy(String constructionText) {
		return " <input type=\"text\" style=\"background-color:greenYellow; width:60px;\" placeholder=\"" + constructionText + "\"> ";
    }
    
}
