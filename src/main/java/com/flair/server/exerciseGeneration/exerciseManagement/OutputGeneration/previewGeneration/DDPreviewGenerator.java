package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration;

import java.util.ArrayList;

public class DDPreviewGenerator extends PreviewGenerator {

    
    @Override
    protected String getTargetDummy(String constructionText, ArrayList<String> brackets) {
		return " <button style=\"background-color:greenYellow;\">" + constructionText + "</button> ";
    }
    
}
