package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration;

import java.util.ArrayList;

public class MtWPreviewGenerator extends PreviewGenerator {

    
    @Override
    protected String getTargetDummy(String constructionText, ArrayList<String> brackets) {
   		return " <label style=\"background-color:greenYellow; font-weight:bold;\"/> ";
    }
    
}
