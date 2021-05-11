package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;

import java.util.ArrayList;
import java.util.HashMap;

public class HtmlSplitter {

	/**
     * Splits the HTML string at the placeholder elements and creates the list of html elmenets
     * (including placeholder elements for questions) with the syntax needed by the H5P exercises
     * @param htmlString The HTML string
     * @param unorderedSentences The HTML strings of the extracted sentences, in random order
     * @return The list of pure HTML elements as required by the H5P exercises
     */
    public ArrayList<String> preparePureHtmlElements(String htmlString, ArrayList<String> unorderedSentences,
                                                     HashMap<Integer, String> plainTextElements,
                                                     ArrayList<String> orderedPlainTextElements) {
        ArrayList<String> pureHtmlElements = new ArrayList<>();
        int sentenceCounter = 0;

        int placeholderIndex = htmlString.indexOf("<span data-sentenceplaceholder=\"");
        while(placeholderIndex != -1){
            sentenceCounter++;

            // add preceding HTML element
            if(placeholderIndex > 0) {
                String sentence = htmlString.substring(0, placeholderIndex);
                extractPlaintextElementsForSentence(sentence, pureHtmlElements, plainTextElements,
                        orderedPlainTextElements, "sentenceHtml 0");
            }

            // add sentence
            int indexStart = placeholderIndex + 32;
            int indexEnd = htmlString.indexOf("\"", indexStart);
            int sentenceIndex = Integer.parseInt(htmlString.substring(indexStart, indexEnd));
            String sentence = unorderedSentences.get(sentenceIndex);
            extractPlaintextElementsForSentence(sentence, pureHtmlElements, plainTextElements,
                    orderedPlainTextElements, "sentenceHtml " + sentenceCounter);

            htmlString = htmlString.substring(indexEnd + 9);
            placeholderIndex = htmlString.indexOf("<span data-sentenceplaceholder=\"");
        }

        if(!htmlString.trim().equals("")) {
            // add remaining HTML elements
            extractPlaintextElementsForSentence(htmlString, pureHtmlElements, plainTextElements,
                    orderedPlainTextElements, "sentenceHtml 0");
        }

        return pureHtmlElements;
    }

    /**
     * Extract plaintext placeholders from a sentence and split it into parts.
     * @param sentence                  The HTML string with the placeholders for plaintext
     * @param pureHtmlElements          The list of split HTML elements
     * @param plainTextElements         The list of indexed plain text elements
     * @param orderedPlainTextElements  The list of ordered plain text elements
     * @param prefix                    The prefix to use for pure HTML elements (sentenceHtml <n>)
     */
    private void extractPlaintextElementsForSentence(String sentence, ArrayList<String> pureHtmlElements,
                                                     HashMap<Integer, String> plainTextElements,
                                                     ArrayList<String> orderedPlainTextElements, String prefix) {
        int startIndex = sentence.indexOf("<span data-plaintextplaceholder>");
        while(startIndex > -1) {
            if(startIndex > 0) {
                // add preceding HTML elements
                pureHtmlElements.add(prefix + " " + prepareHtmlElement(sentence.substring(0, startIndex)));
            }

            // add plain text element
            String questionNumber = sentence.substring(sentence.indexOf(">", startIndex) + 1, sentence.indexOf("<", startIndex + 1));
            pureHtmlElements.add(questionNumber + " " + prefix);
            orderedPlainTextElements.add(plainTextElements.get(Integer.parseInt(questionNumber)));

            sentence = sentence.substring(sentence.indexOf("</span>", startIndex) + 7);
            startIndex = sentence.indexOf("<span data-plaintextplaceholder>");
        }

        if(!sentence.trim().equals("")) {
            // add remaining HTML elements
            pureHtmlElements.add(prefix + " " + prepareHtmlElement(sentence));
        }
    }

	/**
	 * Replaces all HTML symbols which may not be used if we don't want H5P to
	 * recognize it as HTML. Removes HTML comments. Replaces blanks placeholders
	 * with asterisks. Normalizes all whitespaces but linebreaks to single spaces.
	 * 
	 * @param htmlString The string to normalize
	 * @return The normalized string
	 */
	private String prepareHtmlElement(String htmlString) {
		return htmlString.replaceAll("<!--.*?-->", "") // remove comments
				.replace("<", "ltRep;").replace("\"", "quotRep;").replace(">", "gtRep;").replace("'", "#039Rep;")
				.replace("&", "ampRep;").replaceAll("\\h+", " ");
	}
}
