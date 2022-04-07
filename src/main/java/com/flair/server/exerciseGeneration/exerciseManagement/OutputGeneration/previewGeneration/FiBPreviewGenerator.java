package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

public class FiBPreviewGenerator extends PreviewGenerator {

    
    @Override
    protected String getTargetDummy(String constructionText, ArrayList<String> brackets) {
    	String parentheses = "";
    	if(brackets.size() > 0) {
    		parentheses = "(" + StringUtils.join(brackets, ", ") + ") ";
    	}
		return " <input type=\"text\" style=\"background-color:greenYellow; width:60px;\" placeholder=\"" + constructionText + "\"> " + parentheses;
    }
    
}
