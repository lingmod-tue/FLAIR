package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration;

public class DDPreviewGenerator extends PreviewGenerator {

    
    @Override
    protected String getTargetDummy(String constructionText) {
		return " <button style=\"background-color:greenYellow;\">" + constructionText + "</button> ";
    }
    
}
