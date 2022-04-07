package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration;

import java.util.ArrayList;

public class SCPreviewGenerator extends PreviewGenerator {

    
    @Override
    protected String getTargetDummy(String constructionText, ArrayList<String> brackets) {
    	return " <select style=\"background-color:greenYellow;\"><option>" + constructionText + "</option></select> ";
    }
    
}
