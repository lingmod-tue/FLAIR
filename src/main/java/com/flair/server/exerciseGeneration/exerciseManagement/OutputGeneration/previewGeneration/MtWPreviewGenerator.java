package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration;

public class MtWPreviewGenerator extends PreviewGenerator {

    
    @Override
    protected String getTargetDummy(String constructionText) {
   		return " <label style=\"background-color:greenYellow; font-weight:bold;\"/> ";
    }
    
}
