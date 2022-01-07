package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration;

public class SCPreviewGenerator extends PreviewGenerator {

    
    @Override
    protected String getTargetDummy(String constructionText) {
    	return " <select style=\"background-color:greenYellow;\"><option>" + constructionText + "</option></select> ";
    }
    
}
