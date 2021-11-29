package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;

import com.flair.shared.exerciseGeneration.Pair;

public class FindXmlManager extends SimpleExerciseXmlManager {

	public FindXmlManager() {
		removeVoidElements = true;
	}
	
	@Override
	protected void addAttributes(ArrayList<Pair<String, Boolean>> parts, int index, String taskDescription) {	
		super.addAttributes(parts, index, taskDescription);
		attributes.put("task_type", "UNDERLINE");
		attributes.put("task_orient", "Offen");
		attributes.put("task_focus", "Form");
		attributes.put("given_words_draggable", "false");
    	attributes.put("feedback_disabled", "false");
	}
		
	@Override
	protected void addTaskFieldAttributes(String prompt, int index, String target, ArrayList<Pair<String,String>> distractors) {
		super.addTaskFieldAttributes(prompt, index, target, distractors);
    	taskFieldAttributes.put("target", target);
		if(!target.equals("")) {
			taskFieldAttributes.put("input_type", "UNDERLINE");
		} else {
			taskFieldAttributes.put("input_type", "null");
		}
	}
	
	@Override
	protected String generateTaskFields(ArrayList<Pair<String, Boolean>> parts,
			ArrayList<Pair<Integer, Integer>> constructionIndices, ArrayList<ArrayList<Pair<String,String>>> distractors) {
		ArrayList<String> text = new ArrayList<>();
    	for(Pair<String, Boolean> part : parts) {
    		text.add(part.first);
    	}
    	
		String prompt = StringUtils.join(text, "");
		prompt = removeStrandedTags(prompt, constructionIndices);
	    
	    ArrayList<String> indices = new ArrayList<>();
	    for(Pair<Integer, Integer> constructionIndex : constructionIndices) {
	    	indices.add(constructionIndex.first + "-" + constructionIndex.second);
	    }
	    String target = StringUtils.join(indices, ",");
	    
        return generateTaskFieldDefinition(prompt, target, 1, null);        
	}

	@Override
	protected Pair<String, ArrayList<String>> preprocess(ArrayList<String> htmlElements, String plainText) {
		StringBuilder sb = new StringBuilder();
		ArrayList<String> dummyHtmlElements = new ArrayList<>();
        dummyHtmlElements.add("");

    	for(String htmlString : htmlElements) {
    		if(htmlString.startsWith("sentenceHtml ")) {
    			int startIndex = htmlString.indexOf(" ", 13) + 1;
    			String htmlText = htmlString.substring(startIndex).trim().replace("*", "**")
    					.replace("ltRep;", " <").replace("quotRep;", "\"").replace("gtRep;", "> ")
    					.replace("#039Rep;", "'").replace("ampRep;", "&");

    			sb.append(htmlText);
    		} else {   	
    			String question = "";
    			int firstQuestionIndex = plainText.indexOf("<span data-internal");
    	        if (firstQuestionIndex != -1) {
    	            int nextQuestionIndex = plainText.indexOf("<span data-internal", firstQuestionIndex + 1);
    	            if (nextQuestionIndex != -1) {
    	                question = plainText.substring(0, nextQuestionIndex);
    	                plainText = plainText.substring(nextQuestionIndex);
    	            } else {
    	            	question = plainText;
    	                plainText = "";
    	            }
    	        }
    	        sb.append(Jsoup.parse(question).text().trim());	
    		}
    	}
    	
    	String prompt = Jsoup.parse(sb.toString()).select("section.bodyreplacement").toString().replace("  ", " ");
    	return super.preprocess(dummyHtmlElements, "<span data-internal=\"1\">" + prompt + "</span>");
	}
}
