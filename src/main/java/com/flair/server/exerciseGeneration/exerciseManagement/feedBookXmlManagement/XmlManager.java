package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

import java.util.ArrayList;
import java.util.HashMap;

import com.flair.server.exerciseGeneration.OutputComponents;
import com.flair.shared.exerciseGeneration.Pair;

public interface XmlManager {

	/**
	 * Creates a XML string from the exercise components to be used as input to FeedBook.
	 * @param escapeHtml		<c>true</c> if asterisks are escape within the HTML for this content type
	 * @param distractors		The incorrect answers
	 * @param index				The successive index of the sub-task
	 * @param plainText			The plain text of the exercise text
	 * @param htmlElements		The HTML elements of the exercise text
	 * @param taskDescription	The instructions
	 * @param title				The name of the configured exercise
	 * @return	The name of the configured exercise and corresponding generated XML string
	 */
   HashMap<String,String> generateFeedBookInputXml(boolean escapeHtml, ArrayList<ArrayList<Pair<String,String>>> distractors, 
   		int index, String plainText, ArrayList<String> htmlElements, String taskDescription, String title, 
   		ArrayList<OutputComponents> simpleExercises);
}
