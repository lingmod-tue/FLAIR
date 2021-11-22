package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

import com.flair.server.exerciseGeneration.OutputComponents;
import com.flair.server.exerciseGeneration.util.Utilities;
import com.flair.shared.exerciseGeneration.Pair;

public class SimpleExerciseXmlManager implements XmlManager {

    protected String taskType = "NOT_SUPPPORTED";

	private static final String[] voidElements = {"area", "base", "br", "col", "embed", "hr", "img", "input", "link", 
    		"meta", "param", "source", "track", "wbr", "command", "keygen", "menuitem"};
    
	@Override
    public HashMap<String,String> generateFeedBookInputXml(boolean escapeHtml, ArrayList<ArrayList<Pair<String,String>>> distractors, 
    		int index, String plainText, ArrayList<String> htmlElements, String taskDescription, String title,
    		ArrayList<OutputComponents> simpleExercises) {
    	StringBuilder xml = new StringBuilder();
    	xml.append("<SubTask index=\"" + index + "\" instruction=\"" + taskDescription + 
    			"\" task_type=\"" + taskType + 
    			"\" task_orient=\"" + (taskType.equals("FILL_IN_THE_BLANKS") ? "Offen" : "Geschlossen") + 
    			"\" task_focus=\"Form\" non_en_input=\"false\" feedback_disabled=\"false\" given_words_draggable=\"false\" grammar_topics=\"\" task_topics=\"\"><Prompts>");
    	
    	ArrayList<Pair<String, Boolean>> parts = identifyTaskFields(htmlElements, escapeHtml, plainText);
    	
    	StringBuilder currentPrompt = new StringBuilder();
    	String currentTarget = "";
    	ArrayList<String> voidTags = new ArrayList<>(Arrays.asList(voidElements));
    	int id = 0;
    	for(Pair<String, Boolean> part : parts) {
    		if(part.second == true) {	// it's a target construction
    			if(currentPrompt.length() > 0) {
    				ArrayList<HtmlTag> xmlParts = new ArrayList<>();
    				Pattern pattern = Pattern.compile("&amp;lt;.*?&amp;gt;");
    			    Matcher matcher = pattern.matcher(currentPrompt);
    			    while (matcher.find()) {
    			    	xmlParts.add(new HtmlTag(matcher.start(), matcher.end(), matcher.group()));
    			    }
    				
    			    ArrayList<HtmlTag> tagsToRemove = identifyStrandedTags(xmlParts, voidTags);
    			    Collections.sort(tagsToRemove,
    		                (t1, t2) -> t1.getStartIndex() > t2.getStartIndex() ? -1 : 1);
    			    String prompt = currentPrompt.toString();
    			    for(HtmlTag tag : tagsToRemove) {
    			    	prompt = prompt.substring(0, tag.getStartIndex()) + prompt.substring(tag.getEndIndex());
    			    }
    				
    		        String taskField = generateTaskFieldDefinition(currentPrompt.toString(), currentTarget, ++id);
        	        currentPrompt = new StringBuilder();
    				xml.append(taskField);
    			}
    			
    			currentTarget = part.first;
    		} else {
    			currentPrompt.append(part.first);
    		}
    	}
    	
    	if(currentPrompt.length() > 0) {
	        String taskField = generateTaskFieldDefinition(currentPrompt.toString(), currentTarget, ++id);
			xml.append(taskField);
		}
    	
    	xml.append("</Prompts>\n</SubTask>");
    	
    	HashMap<String, String> feedBookXml = new HashMap<>();
        feedBookXml.put(title, xml.toString().replace("\n", ""));
        
        return feedBookXml;
    }
	
	/**
	 * Identifies HTML tags which do not have a corresponding opening or closing tag within the same task field
	 * @param xmlParts	All opening and closing tags of a task field in chronological order
	 * @return	The list of incomplete HTML tags
	 */
	private ArrayList<HtmlTag> identifyStrandedTags(ArrayList<HtmlTag> xmlParts, ArrayList<String> voidTags) {
		Stack<HtmlTag> tags = new Stack<>();
	    ArrayList<HtmlTag> tagsToRemove = new ArrayList<>();
	    for(HtmlTag tag : xmlParts) {
	    	String tagName = getTagName(tag);
	    	tag.setTagName(tagName);
	    	    			    	
	    	if(tag.getTag().startsWith("&amp;lt;/")) {	// it's a closing tag
	    		if(!tags.empty() && tags.peek().getTagName().equals(tagName)) {	// the entire tag is within this prompt, so we keep it
	    			tags.pop();
	    		} else {	// only the closing tag is within this prompt, so we remove it
	    			tagsToRemove.add(tag);
	    		}
	    	} else {	// it's an opening tag or a self closing tag
	    		if(!(tag.getTag().endsWith("/&amp;lt;") || voidTags.contains(tagName))) {	// it's not a self closing tag
	    			tags.push(tag);
	    		}
	    	}
	    }
	    
	    tagsToRemove.addAll(tags);
	    
	    return tagsToRemove;
	}
	
	/**
	 * Composes the list of task fields from the HTML elements and the plain
	 * @param htmlElements	The HTML elements
	 * @param escapeHtml	<c>true</c> if * is escaped in the HTML elements; otherwise <c>false</c>
	 * @param plainText		The plain text
	 * @return	The task fields including plain text and HTML elements
	 */
	private ArrayList<Pair<String, Boolean>> identifyTaskFields(ArrayList<String> htmlElements, boolean escapeHtml, 
			String plainText) {
		ArrayList<Pair<String, Boolean>> parts = new ArrayList<>();
    	for(String htmlString : htmlElements) {
    		if(htmlString.startsWith("sentenceHtml ")) {
    			int startIndex = htmlString.indexOf(" ", 13) + 1;
    			if(escapeHtml) {
    				htmlString = htmlString.replace("**", "*");
    			}
    			parts.add(new Pair<>(htmlString.substring(startIndex)
    					.replace("ltRep;", "&lt;").replace("quotRep;", "&quot;")
    	    			.replace("gtRep;", "&gt;").replace("#039Rep;", "&apos;")
    	    			.replace("ampRep;", "&amp;"), 
    	    		false));
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
    	        parts.addAll(splitAtTargetConstructions(Jsoup.parse(question).text().replace("<", "&lt;").replace("\"", "&quot;")
    	    			.replace(">", "&gt;").replace("'", "&apos;")
    	    			.replace("&", "&amp;")));
    		}
    	}
    	
    	return parts;
	}
    
    /**
     * Generates a xml definition for a FeedBook TaskField
     * @param prompt	The prompt text of the task field
     * @param target	The correct target answer
     * @param index		The successive index of the task field within the (sub) task
     * @return	The generated xml definition
     */
    private String generateTaskFieldDefinition(String prompt, String target, int index) {
    	String inputType = target.matches(".*?[\\s\\h\\v].?") ? "PHRASE" : "WORD";

    	return "<TaskField text=\"" + prompt + 
        		"\" target=\"" + target + 
        		"\" input_type=\"" + inputType + 
        		"\" sort_index=\"" + index + "\"/>";
    }
    
    /**
     * Extracts the name of a html tag from the tag definition
     * @param tag	The tag definition
     * @return		The name of the tag
     */
    private String getTagName(HtmlTag tag) {
    	String tagName = tag.getTag();
    	if(tagName.startsWith("&amp;lt;")) {
    		tagName = tagName.substring(8);
    	}
    	if(tagName.startsWith("/")) {
    		tagName = tagName.substring(1);
    	}
    	if(tagName.endsWith("&amp;gt;")) {
    		tagName = tagName.substring(0, tagName.length() - 8);
    	}
    	if(tagName.endsWith("/")) {
    		tagName = tagName.substring(0, tagName.length() - 1);
    	}
    	int blankIndex = tagName.indexOf(" ");
    	if(blankIndex != -1) {
    		tagName = tagName.substring(0, blankIndex);
    	}
    	
    	return tagName;
    }
    
    /**
     * Splits the plain text elements at target constructions.
     * @param plainText	The plain text
     * @return	A list of the non-target-elements and target constructions in the order in which they occur
     * 			with an inidication of whether it's a target construction (<c>true</c>) or not (<c>false</c>
     */
    protected ArrayList<Pair<String, Boolean>> splitAtTargetConstructions(String plainText) {
    	ArrayList<Pair<String, Boolean>> parts = new ArrayList<>();
		int indexAfterLastAsterisk = 0;
		int nextAsteriskIndex = Utilities.getNextNotEscapedCharacter(plainText, 0, "*");
		while(nextAsteriskIndex != -1) {
			int constructionStartIndex = nextAsteriskIndex;
			nextAsteriskIndex = Utilities.getNextNotEscapedCharacter(plainText, nextAsteriskIndex + 1, "*");
			
			if(nextAsteriskIndex != -1) {	
				String constructionText = plainText.substring(constructionStartIndex + 1, nextAsteriskIndex);
				parts.add(new Pair<>(plainText.substring(indexAfterLastAsterisk, constructionStartIndex).replace("**", "*"), false));
				parts.add(new Pair<>(constructionText.replace("**", "*"), true));

				indexAfterLastAsterisk = nextAsteriskIndex + 1;
				nextAsteriskIndex = Utilities.getNextNotEscapedCharacter(plainText, nextAsteriskIndex + 1, "*");
			}
		}
		
		if(indexAfterLastAsterisk < plainText.length()) {
			parts.add(new Pair<>(plainText.substring(indexAfterLastAsterisk).replace("**", "*"), false));
		}
		
		return parts;
    }
    
}
