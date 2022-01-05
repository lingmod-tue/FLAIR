package com.flair.server.exerciseGeneration.InputParsing.DocumentParsing.WebpageParsing;

import java.util.ArrayList;
import java.util.HashMap;

import com.flair.server.exerciseGeneration.ConstructionProperties;
import com.flair.server.exerciseGeneration.ConstructionTextPart;
import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.HtmlTextPart;
import com.flair.server.exerciseGeneration.PlainTextPart;
import com.flair.server.exerciseGeneration.TextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConditionalConstruction;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;

public class HtmlSplitter {

	/**
     * Splits the HTML string at the placeholder elements and creates the list of html elmenets
     * (including placeholder elements for questions) with the syntax needed by the H5P exercises
     * @return The list of pure HTML elements as required by the H5P exercises
     */
    public static ExerciseData preparePureHtmlElements(WebpageData data, ExerciseSettings settings) {    
		ArrayList<TextPart> parts = new ArrayList<>();
        int sentenceCounter = 0;
        String htmlString = data.getDocument().toString();

        int placeholderIndex = htmlString.indexOf("<span data-sentenceplaceholder=\"");
        while(placeholderIndex != -1){
            sentenceCounter++;

            // add preceding HTML element
            if(placeholderIndex > 0) {
                String sentence = htmlString.substring(0, placeholderIndex);
                extractPlaintextElementsForSentence(sentence, 0, parts, data, settings);
            }

            // add sentence
            int indexStart = placeholderIndex + 32;
            int indexEnd = htmlString.indexOf("\"", indexStart);
            int sentenceIndex = Integer.parseInt(htmlString.substring(indexStart, indexEnd));
            String sentence = data.getSentenceHtml().get(sentenceIndex);
            extractPlaintextElementsForSentence(sentence, sentenceCounter, parts, data, settings);

            htmlString = htmlString.substring(indexEnd + 9);
            placeholderIndex = htmlString.indexOf("<span data-sentenceplaceholder=\"");
        }

        if(!htmlString.trim().equals("")) {
            // add remaining HTML elements
            extractPlaintextElementsForSentence(htmlString, 0, parts, data, settings);
        }

        return new ExerciseData(parts);
    }

    /**
     * Extract plaintext placeholders from a sentence and split it into parts.
     * @param sentence                  The HTML string with the placeholders for plaintext
     * @param plainTextElements         The list of indexed plain text elements
     * @param sentenceId                The index of the current sentence
     */
    private static void extractPlaintextElementsForSentence(String sentence,
                                                     int sentenceId, ArrayList<TextPart> parts, WebpageData data, ExerciseSettings settings) {
    	HashMap<Integer, String> plainTextElements = data.getPlainTextElements();
    	int startIndex = sentence.indexOf("<span data-plaintextplaceholder>");
        while(startIndex > -1) {
            if(startIndex > 0) {
                // add preceding HTML elements
                parts.add(new HtmlTextPart(sentence.substring(0, startIndex).replaceAll("<!--.*?-->", "").replaceAll("\\h+", " "), sentenceId));
            }

            // add plain text element
            String questionNumber = sentence.substring(sentence.indexOf(">", startIndex) + 1, sentence.indexOf("<", startIndex + 1));          
            
            String plainText = plainTextElements.get(Integer.parseInt(questionNumber));
            splitAtBlanks(plainText, parts, sentenceId, data, settings);
            	        
	        sentence = sentence.substring(sentence.indexOf("</span>", startIndex) + 7);
            startIndex = sentence.indexOf("<span data-plaintextplaceholder>");
        }

        if(!sentence.trim().equals("")) {
            // add remaining HTML elements
            parts.add(new HtmlTextPart(sentence.replaceAll("<!--.*?-->", "").replaceAll("\\h+", " "), sentenceId));
        }
    }
    
    /**
     * Splits the sentence into plain text and construction elements.
     * @param plainTextElement	The current plain text element of a sentence
     * @param parts				The parts of the web page
     * @param sentenceId		The index of the current sentence
     * @param data				The Webpage data
     * @param settings			The exercise settings
     */
    private static void splitAtBlanks(String plainTextElement, ArrayList<TextPart> parts, int sentenceId, WebpageData data, 
    		ExerciseSettings settings) {
    	ArrayList<Blank> constructions = new ArrayList<>();
    	for(Fragment fragment : data.getFragments()) {
    		for(Blank c : fragment.getBlanksBoundaries()) {
    			if(c.getConstruction() != null) {
    	    		constructions.add(c);
    			}
    		}
    	}
    	
    	String plainText = settings.getPlainText();
    	
        while (plainTextElement.length() > 0) {
            int placeholderIndex = plainTextElement.indexOf("<span data-blank=\"");

            if(placeholderIndex == -1) {
	            parts.add(new PlainTextPart(plainTextElement, sentenceId));
	            plainTextElement = "";
        	} else  {
        		if(placeholderIndex > 0) {
    	            parts.add(new PlainTextPart(plainTextElement.substring(0, placeholderIndex), sentenceId));
        		}
        		
        		int indexStart = placeholderIndex + 18;
                int indexEnd = plainTextElement.indexOf("\"", indexStart);
                int blanksIndex = Integer.parseInt(plainTextElement.substring(indexStart, indexEnd));

	            plainTextElement = plainTextElement.substring(indexEnd + 9);

                Construction construction = constructions.get(blanksIndex).getConstruction();
                ConstructionTextPart c = new ConstructionTextPart(plainText.substring(construction.getConstructionIndices().first, construction.getConstructionIndices().second), sentenceId);
	            c.setIndicesInPlainText(construction.getConstructionIndices());
	            c.setConstructionType(construction.getConstruction());
	            if(construction instanceof ConditionalConstruction && ((ConditionalConstruction)construction).isMainClause()) {
	            	c.getConstructionProperties().add(ConstructionProperties.CONDITIONAL_MAIN_CLAUSE);
	            }
	            parts.add(c);
        	}
        }
    }

}
