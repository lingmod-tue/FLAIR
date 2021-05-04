package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ElementCreator {

    /**
     * Creates a DOM element with the attribute data-blank.
     * @param blanksIndex	The index of the extracted blanks text in the blanks list
     * @return 				The generated DOM element <span data-blank="n"></span>
     */
    public static Element createBlanksPlaceholderElement(int blanksIndex) {
        return createHtmlElement("data-blank=\"" + blanksIndex + "\"", "");
    }

    /**
     * Creates a DOM element with the attribute data-sentenceplaceholder.
     * @param sentenceCounter	The index of the extracted plain text fragment in the plain text list
     * @return 					The generated DOM element <span data-sentenceplaceholder="n"></span>
     */
    public static Element createSentencePlaceholderElement(int sentenceCounter) {
        return createHtmlElement("data-sentenceplaceholder=\"" + sentenceCounter + "\"", "");
    }

	/**
     * Creates a DOM element with the attribute data-plaintextplaceholder.
	 * @param id	The number of the question
     * @return		The generated DOM element <span data-plaintextplaceholder></span>
	 */
    public static Element createPlainTextReplacementElement(int id){
        return createHtmlElement("data-plaintextplaceholder", id + "");
    }

    /**
     * Creates a DOM element with the given text as content.
     * @param content	The text to be inserted as content (the node text)
     * @return 			The generated DOM element <span >content</span>
     */
    public static Element createHtmlReplacementElement(String content) {
        return createHtmlElement("", content);
    }

    /**
     * Creates a new DOM element.
     * @param attribute	The attribute(s) to include in the tag
     * @param content 	The text content of the element
     * @return 			The generated DOM element
     */
    private static Element createHtmlElement(String attribute, String content){
        String replacement = "<span " + attribute + ">" + content + "</span>";
        Document d = Jsoup.parse(replacement);
        Element el = d.select("span").first();
        return el;
    }
}
