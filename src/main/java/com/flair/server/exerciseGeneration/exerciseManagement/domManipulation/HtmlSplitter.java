package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;

import java.util.ArrayList;
import java.util.HashMap;

public class HtmlSplitter {

	/**
	 * Splits the HTML string at the placeholder elements and creates the list of
	 * HTML elements (including placeholder elements for questions) with the syntax
	 * needed by the H5P exercises.
	 * 
	 * @param htmlString           The HTML string
	 * @param unorderedSentences   The HTML strings of the extracted sentences, in
	 *                             random order
	 * @param plainTextElements    The extracted DOM parts corresponding to plain
	 *                             text fragments
	 * @param plainTextPerSentence The array in which to write the ordered sentences
	 * @return The list of pure HTML elements as required by the H5P exercises
	 */
	public ArrayList<String> preparePureHtmlElements(String htmlString, ArrayList<String> unorderedSentences,
			HashMap<Integer, String> plainTextElements, ArrayList<ArrayList<String>> plainTextPerSentence) {
		ArrayList<String> pureHtmlElements = new ArrayList<>();
		ArrayList<String> sentences = new ArrayList<>();

		int placeholderIndex = htmlString.indexOf("<span data-sentenceplaceholder=\"");
		while (placeholderIndex != -1) {
			int indexStart = placeholderIndex + 32;
			int indexEnd = htmlString.indexOf("\"", indexStart);
			int sentenceIndex = Integer.parseInt(htmlString.substring(indexStart, indexEnd));

			sentences.add(unorderedSentences.get(sentenceIndex));
			htmlString = htmlString.substring(0, placeholderIndex) + "<span data-sentenceplaceholder></span>"
					+ htmlString.substring(indexEnd + 9);

			placeholderIndex = htmlString.indexOf("<span data-sentenceplaceholder=\"", placeholderIndex + 1);
		}

		ArrayList<String> plainTextElementsOfCurrentSentence = new ArrayList<>();

		String[] htmlElements = htmlString.split("<span data-sentenceplaceholder></span>");
		// we receive an empty string for the first pureHTML part if the htmlString
		// starts with the delimiter, so we always start with that element
		for (int i = 0; i < htmlElements.length; i++) {
			if(!htmlElements[i].equals("")) {
                String sentence = htmlElements[i];
                StringBuilder sb = new StringBuilder();
                int startIndex = sentence.indexOf("<span data-plaintextplaceholder>");
                while(startIndex > -1) {
                    if(startIndex > 0) {
                        // add preceding HTML elements
                        sb.append(sentence.substring(0, startIndex));
                    }

                    // add plain text element
                    String questionNumber = sentence.substring(sentence.indexOf(">", startIndex) + 1, sentence.indexOf("<", startIndex + 1));
                    sb.append(plainTextElements.get(Integer.parseInt(questionNumber)));

                    sentence = sentence.substring(sentence.indexOf("</span>", startIndex));
                    startIndex = sentence.indexOf("<span data-plaintextplaceholder>");
                }

                if(!sentence.trim().equals("")) {
                    // add remaining HTML elements
                    sb.append(sentence);
                }
                pureHtmlElements.add("pureHtml " + prepareHtmlElement(sb.toString()));
            }

			if (sentences.size() > i) {
				if (plainTextElementsOfCurrentSentence.size() > 0) {
					plainTextPerSentence.add(plainTextElementsOfCurrentSentence);
					plainTextElementsOfCurrentSentence = new ArrayList<>();
				}
				String sentence = sentences.get(i);

				int startIndex = sentence.indexOf("<span data-plaintextplaceholder>");
				while (startIndex > -1) {
					if (startIndex > 0) {
						// add preceding HTML elements
						pureHtmlElements.add("sentenceHtml " + (i + 1) + " "
								+ prepareHtmlElement(sentence.substring(0, startIndex)));
					}

					// add plain text element
					String questionNumber = sentence.substring(sentence.indexOf(">", startIndex) + 1,
							sentence.indexOf("<", startIndex + 1));
					pureHtmlElements.add(questionNumber + " sentenceHtml " + (i + 1));
					plainTextElementsOfCurrentSentence.add(plainTextElements.get(Integer.parseInt(questionNumber)));

					sentence = sentence.substring(sentence.indexOf("</span>", startIndex));
					startIndex = sentence.indexOf("<span data-plaintextplaceholder>");
				}

				if (!sentence.trim().equals("")) {
					// add remaining HTML elements
					pureHtmlElements.add("sentenceHtml " + (i + 1) + " " + prepareHtmlElement(sentence));
				}
			}
		}

		if (plainTextElementsOfCurrentSentence.size() > 0) {
			plainTextPerSentence.add(plainTextElementsOfCurrentSentence);
		}

		return pureHtmlElements;
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
