package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;

import com.flair.server.exerciseGeneration.OutputComponents;
import com.flair.server.exerciseGeneration.util.Utilities;
import com.flair.shared.exerciseGeneration.Pair;

public abstract class SimpleExerciseXmlManager implements XmlManager {

	protected static final ArrayList<String> voidTags = new ArrayList<String>();

	static{
        voidTags.add("area");
        voidTags.add("base");
        voidTags.add("br");
        voidTags.add("col");
        voidTags.add("embed");
        voidTags.add("hr");
        voidTags.add("img");
        voidTags.add("input");
        voidTags.add("link");
        voidTags.add("meta");
        voidTags.add("param");
        voidTags.add("source");
        voidTags.add("track");
        voidTags.add("wbr");
        voidTags.add("command");
        voidTags.add("keygen");
        voidTags.add("menuitem");
    }
	
	protected HashMap<String, String> attributes = new HashMap<>();
	protected HashMap<String, String> taskFieldAttributes = new HashMap<>();
	
	protected boolean removeVoidElements = false;
    
	@Override
    public HashMap<String,String> generateFeedBookInputXml(boolean escapeHtml, ArrayList<ArrayList<Pair<String,String>>> distractors, 
    		int index, String plainText, ArrayList<String> htmlElements, String taskDescription, String title,
    		ArrayList<OutputComponents> simpleExercises) {
    	StringBuilder xml = new StringBuilder();
    	Pair<String,ArrayList<String>> preprocessResult = preprocess(htmlElements, plainText);
    	htmlElements = preprocessResult.second;
    	plainText = preprocessResult.first;
    	    	
    	Pair<ArrayList<Pair<String,Boolean>>,ArrayList<Pair<Integer,Integer>>> ret = identifyTaskFields(htmlElements, escapeHtml, plainText);
    	
    	xml.append("<SubTask"); 	
    	
    	addAttributes(ret.first, index, taskDescription);
    	    	
    	for(Entry<String, String> attribute : attributes.entrySet()) {
    		xml.append(" ").append(attribute.getKey()).append("=\"").append(attribute.getValue()).append("\"");
    	}
    	xml.append("><Prompts>");
    	xml.append(generateTaskFields(ret.first, ret.second, distractors));
    	xml.append("</Prompts>\n</SubTask>");
    	
    	HashMap<String, String> feedBookXml = new HashMap<>();
        feedBookXml.put(title, xml.toString().replace("\n", ""));
        
        return feedBookXml;
    }
	
	protected String generateTaskFields(ArrayList<Pair<String, Boolean>> parts,
			ArrayList<Pair<Integer, Integer>> constructionIndices, ArrayList<ArrayList<Pair<String,String>>> distractors) {
		StringBuilder xml = new StringBuilder();
		StringBuilder currentPrompt = new StringBuilder();
    	String currentTarget = "";
    	int id = 0;
    	int targetIndex = 0;
    	for(Pair<String, Boolean> part : parts) {
    		if(part.second == true) {	// it's a target construction
    			if(currentPrompt.length() > 0) {
    	    		String prompt = removeStrandedTags(currentPrompt.toString(), null);
    				
    			    String taskField = generateTaskFieldDefinition(prompt, currentTarget, ++id, 
    		        		distractors.size() > targetIndex ? distractors.get(targetIndex) : null);
    		        if(!currentTarget.equals("")) {
    		        	targetIndex++;
    		        }
        	        currentPrompt = new StringBuilder();
    				xml.append(taskField);
    			}
    			
    			currentTarget = part.first;
    		} else {
    			currentPrompt.append(part.first);
    		}
    	}
    	
    	if(currentPrompt.length() > 0) {
    		String prompt = removeStrandedTags(currentPrompt.toString(), null);
    		
    		String taskField = generateTaskFieldDefinition(prompt, currentTarget, ++id,
	        		distractors.size() > targetIndex ? distractors.get(targetIndex) : null);
			xml.append(taskField);
		}
    	
    	return xml.toString();
	}
	
	protected String removeStrandedTags(String prompt, ArrayList<Pair<Integer, Integer>> constructionIndices) {
		ArrayList<HtmlTag> xmlParts = new ArrayList<>();
		Pattern pattern = Pattern.compile("<.*?>");
	    Matcher matcher = pattern.matcher(prompt);
	    while (matcher.find()) {
	    	xmlParts.add(new HtmlTag(matcher.start(), matcher.end(), matcher.group()));
	    }
		
	    ArrayList<HtmlTag> tagsToRemove = identifyStrandedTags(xmlParts);
	    Collections.sort(tagsToRemove,
                (t1, t2) -> t1.getStartIndex() > t2.getStartIndex() ? -1 : 1);
		ArrayList<Pair<Integer, Integer>> tempIndices = constructionIndices == null ? null : new ArrayList<>(constructionIndices);
	    for(HtmlTag tag : tagsToRemove) {
	    	prompt = prompt.substring(0, tag.getStartIndex()) + prompt.substring(tag.getEndIndex());
	    	
	    	if(tempIndices != null) {
	    		ArrayList<Pair<Integer, Integer>> adjustedConstructionIndices = new ArrayList<>();
	
		    	for(Pair<Integer, Integer> constructionIndex : tempIndices) {
		    		if(tag.getStartIndex() <= constructionIndex.first) {
		    			int offset = tag.getEndIndex() - tag.getStartIndex();
		    			int newStartIndex = constructionIndex.first - offset;
		    			int newEndIndex = constructionIndex.second - offset;
		    			adjustedConstructionIndices.add(new Pair<>(newStartIndex, newEndIndex));
		    		} else if(tag.getStartIndex() <= constructionIndex.second) {
		    			int newEndIndex = constructionIndex.second - (tag.getEndIndex() - tag.getStartIndex());
		    			adjustedConstructionIndices.add(new Pair<>(constructionIndex.first, newEndIndex));
		    		} else {
		    			adjustedConstructionIndices.add(constructionIndex);
		    		}
		    	}
		    	tempIndices = adjustedConstructionIndices;
	    	}
	    }
	    
	    if(constructionIndices != null) {
		    constructionIndices.clear();
	    	for(Pair<Integer, Integer> constructionIndex : tempIndices) {
	    		constructionIndices.add(constructionIndex);
	    	}
	    }
    	
	    return StringEscapeUtils.escapeXml(prompt);
	}
	
	protected void addAttributes(ArrayList<Pair<String, Boolean>> parts, int index, String taskDescription) { 
		attributes.put("index", index + "");
    	attributes.put("instruction", taskDescription);
    	attributes.put("non_en_input", "false");
    	attributes.put("grammar_topics", "");
    	attributes.put("task_topics", "");
	}
	
	/**
	 * Identifies HTML tags which do not have a corresponding opening or closing tag within the same task field
	 * @param xmlParts	All opening and closing tags of a task field in chronological order
	 * @return	The list of incomplete HTML tags
	 */
	protected ArrayList<HtmlTag> identifyStrandedTags(ArrayList<HtmlTag> xmlParts) {
		Stack<HtmlTag> tags = new Stack<>();
	    ArrayList<HtmlTag> tagsToRemove = new ArrayList<>();
	    for(HtmlTag tag : xmlParts) {
	    	String tagName = getTagName(tag);
	    	tag.setTagName(tagName);

	    	if(tag.getTag().startsWith("</")) {	// it's a closing tag
	    		if(removeVoidElements) {
	    			tagsToRemove.add(tag);
	    		} else {
		    		if(!tags.empty() && tags.peek().getTagName().equals(tagName)) {	// the entire tag is within this prompt, so we keep it
		    			tags.pop();
		    		} else {	// only the closing tag is within this prompt, so we remove it
		    			tagsToRemove.add(tag);
		    		}
	    		}
	    	} else {	// it's an opening tag or a self closing tag
	    		if(!(tag.getTag().endsWith("/>") || voidTags.contains(tagName))) {	// it's not a self closing tag
	    			if(removeVoidElements) {
		    			tagsToRemove.add(tag);
		    		} else {
		    			tags.push(tag);
		    		}
	    		} else if(removeVoidElements){
	    			// we cannot have tags with blanks either
	    			if(tag.getTag().contains(" ")) {
	    				tagsToRemove.add(tag);
	    			}
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
	 * @return	The task fields including plain text and HTML elements and the list of construction indices
	 */
	private Pair<ArrayList<Pair<String, Boolean>>, ArrayList<Pair<Integer, Integer>>> identifyTaskFields(ArrayList<String> htmlElements, boolean escapeHtml, 
			String plainText) {
		ArrayList<Pair<Integer, Integer>> constructionIndices = new ArrayList<>();
		ArrayList<Pair<String, Boolean>> parts = new ArrayList<>();
		
		int constructionStartIndex = 0;
    	for(String htmlString : htmlElements) {
    		if(htmlString.startsWith("sentenceHtml ")) {
    			int startIndex = htmlString.indexOf(" ", 13) + 1;
    			if(escapeHtml) {
    				htmlString = htmlString.replace("**", "*");
    			}
    			    			
    			String htmlText = htmlString.substring(startIndex)
    					.replace("ltRep;", "<").replace("quotRep;", "\"").replace("gtRep;", ">")
    					.replace("#039Rep;", "'").replace("ampRep;", "&");
    			constructionStartIndex += htmlText.length();
    			parts.add(new Pair<>(htmlText, false));
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
    	        String currentPlainText = question;
    	        if(!removeVoidElements) {
    	        	currentPlainText = Jsoup.parse(currentPlainText).text();
    	        }
	        
    	        constructionStartIndex = splitAtTargetConstructions(currentPlainText, constructionIndices, parts, constructionStartIndex);
    		}
    	}
    	
    	return new Pair<>(parts, constructionIndices);
	}
    
    /**
     * Generates a xml definition for a FeedBook TaskField
     * @param prompt	The prompt text of the task field
     * @param target	The correct target answer
     * @param index		The successive index of the task field within the (sub) task
     * @return	The generated xml definition
     */
    protected String generateTaskFieldDefinition(String prompt, String target, int index, ArrayList<Pair<String,String>> distractors) {
    	addTaskFieldAttributes(prompt, index, target, distractors);
    	    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("<TaskField");
    	for(Entry<String, String> attribute : taskFieldAttributes.entrySet()) {
    		sb.append(" ").append(attribute.getKey()).append("=\"").append(attribute.getValue()).append("\"");
    	}
    	sb.append("/>");
    	
    	return sb.toString();
    }
    
    /**
     * Adds content type specific attributes for task fields.
     * @param target		The target construction
     * @param distractors	The list of distractors for the current target
     */
    protected void addTaskFieldAttributes(String prompt, int index, String target, ArrayList<Pair<String,String>> distractors) { 
    	taskFieldAttributes.put("text", prompt);
    	taskFieldAttributes.put("sort_index", index + "");
    }
    
    /**
     * Extracts the name of a html tag from the tag definition
     * @param tag	The tag definition
     * @return		The name of the tag
     */
    private String getTagName(HtmlTag tag) {
    	String tagName = tag.getTag();
    	if(tagName.startsWith("<")) {
    		tagName = tagName.substring(1);
    	}
    	if(tagName.startsWith("/")) {
    		tagName = tagName.substring(1);
    	}
    	if(tagName.endsWith(">")) {
    		tagName = tagName.substring(0, tagName.length() - 1);
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
     * 			with an indication of whether it's a target construction (<c>true</c>) or not (<c>false</c>
     */
    protected int splitAtTargetConstructions(String plainText, 
    		ArrayList<Pair<Integer, Integer>> constructionIndices, ArrayList<Pair<String, Boolean>> parts,
    		int constructionStart) {    	
		int indexAfterLastAsterisk = 0;
		int nextAsteriskIndex = Utilities.getNextNotEscapedCharacter(plainText, 0, "*");
		while(nextAsteriskIndex != -1) {
			int constructionStartIndex = nextAsteriskIndex;
			nextAsteriskIndex = Utilities.getNextNotEscapedCharacter(plainText, nextAsteriskIndex + 1, "*");
			
			if(nextAsteriskIndex != -1) {	
				String constructionText = plainText.substring(constructionStartIndex + 1, nextAsteriskIndex);
				String nonConstructionText = plainText.substring(indexAfterLastAsterisk, constructionStartIndex).replace("**", "*");
				parts.add(new Pair<>(nonConstructionText, false));
				constructionStart += nonConstructionText.length();

				constructionText = constructionText.replace("**", "*");
				if(constructionText.contains(":feedback")) {
					constructionText = constructionText.split(":feedback")[0];
    			}
				if(removeVoidElements) {
					constructionText = constructionText.replace(" ", "%%");
				}
				
				parts.add(new Pair<>(constructionText, true));
				constructionIndices.add(new Pair<>(constructionStart, constructionStart + constructionText.length()));
				constructionStart += constructionText.length();

				indexAfterLastAsterisk = nextAsteriskIndex + 1;
				nextAsteriskIndex = Utilities.getNextNotEscapedCharacter(plainText, nextAsteriskIndex + 1, "*");
			}
		}
		
		if(indexAfterLastAsterisk < plainText.length()) {
			String nonConstructionText = plainText.substring(indexAfterLastAsterisk).replace("**", "*");
			parts.add(new Pair<>(nonConstructionText, false));
			constructionStart += nonConstructionText.length();
		}
		
		return constructionStart;
    }

	protected Pair<String, ArrayList<String>> preprocess(ArrayList<String> htmlElements, String plainText) {
		ArrayList<String> normalizedHtmlElements = new ArrayList<>();
    	for(String htmlElement : htmlElements) {
    		normalizedHtmlElements.add(htmlElement.replace("\n", ""));
    	}
    	plainText = plainText.replace("\n", "");
    	
		return new Pair<>(plainText, normalizedHtmlElements);
	}
    
}
