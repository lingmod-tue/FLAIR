package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import com.flair.shared.exerciseGeneration.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Matcher {

    public Matcher(ArrayList<Fragment> matchedFragments){
        this.indexedSentences = matchedFragments;
    }

    private ArrayList<Fragment> indexedSentences;
    private ArrayList<Boundaries> sentenceBoundaryElements = new ArrayList<>();
    private HashMap<Integer, String> plainTextElements = new HashMap<>();
    private ArrayList<String> constructions = new ArrayList<>();
    private ArrayList<Integer> usedConstructionIndices = new ArrayList<>();

    /**
     * Indicates if a construction has been opened but not yet been closed.
     */
    private boolean inConstruction = false;

    /**
     * The index in the HTML text at which the currently processed text node starts
     */
    private int textIndex = 0;

    /**
     * Counter for unique naming of plain text elements
     */
    private int plainTextCounter = 1;

    /**
     * Extracts plain text fragments, sentences and constructions from a HTML document.
     * @param doc	The HTML document
     * @return 		The start and end elements of each identified sentence, the extracted plain text elements and the extracted constructions
     */
    public MatchResult prepareDomForSplitting(Element doc){
        replacePlainText(doc);
        ArrayList<Pair<String, Integer>> usedConstructions = new ArrayList<>();
        for(int i = 0; i < constructions.size(); i++) {
            usedConstructions.add(new Pair<>(constructions.get(i), usedConstructionIndices.get(i)));
        }
        return new MatchResult(sentenceBoundaryElements, plainTextElements, usedConstructions);
    }

    /**
     * Recursively traverses the DOM and extracts all text nodes.
     * Identifies plain text elements and sentences.
     * Replaces the text nodes with spans.
     * If a text node consists of multiple fragments, multiple spans are created.
     * If a text node contains a sentence boundary, multiple spans are created at that position.
     * @param element	The element whose DOM we want to traverse
     */
    private void replacePlainText(Element element) {
        ArrayList<Pair<TextNode, ArrayList<Element>>> replacements = new ArrayList<>();
        List<Node> childNodes = element.childNodes();
        for(Node node : childNodes){
            if(node instanceof TextNode){
                ArrayList<Element> replacementElements = handleTextNode(((TextNode) node).getWholeText());

                // Replace the TextNode with the newly created Elements
                replacements.add(new Pair<>((TextNode)node, replacementElements));

                textIndex += ((TextNode) node).getWholeText().length();
            } else if(node instanceof Element) {
                replacePlainText((Element)node);
            }
        }

        for(Pair<TextNode, ArrayList<Element>> replacement : replacements){
            performDomModifications(replacement.second, replacement.first);
        }
    }

    /**
     * Replaces the text node with the newly created span elements once they have all been created.
     * We cannot modify the DOM while we are still iterating through the children, so we need to do this all afterwards in 1 go.
     * @param replacementElements	The newly created span elements
     * @param oldElement 			The text node which needs to be replaced
     */
    private void performDomModifications(ArrayList<Element> replacementElements, TextNode oldElement){
        Node previousSibling = oldElement;
        for (Element replacementElement : replacementElements) {
            previousSibling.after(replacementElement);
            previousSibling = replacementElement;
        }
        oldElement.remove();
    }

    /**
     * Retrieves the index of the first indexed sentence that is (partially) contained in the text node.
     * @param nodeStartIndex	The start index of the node in the entire HTML text
     * @param nodeEndIndex 		The end index of the node in the entire HTML text
     * @return 					The index of the first fragment(indices) that is contained in the text node.
     */
    private Integer getIndexedSentencesIndex(int nodeStartIndex, int nodeEndIndex) {
        for(int i = 0; i < indexedSentences.size(); i++) {
            Fragment indexedSentence = indexedSentences.get(i);

            if(Math.max(indexedSentence.getStartIndex(), nodeStartIndex) < Math.min(indexedSentence.getEndIndex(), nodeEndIndex)) {
                return i;
            }
        }

        return null;
    }

    /**
     * Splits the text node into span elements.
     * Fragment and sentence boundary indicate that a new span element is needed.
     * @param nodeText	The text of the text node
     * @return 			The generated span elements which replace the node text
     */
    private ArrayList<Element> handleTextNode(String nodeText) {
        ArrayList<Element> replacementElements = new ArrayList<>();

        Integer indexedSentencesIndex = getIndexedSentencesIndex(textIndex, textIndex + nodeText.length());
        if (indexedSentencesIndex != null) {
            handleNodeTextInFragment(nodeText, indexedSentencesIndex, textIndex, textIndex + nodeText.length(),
                    replacementElements);
        } else { // no part of the TextNode is (part of) a fragment
            Element replacement = ElementCreator.createHtmlReplacementElement(nodeText);
            replacementElements.add(replacement);
        }

        return replacementElements;
    }

    /**
     * Checks whether a fragment boundary is in the node text.
     * If this is the case, anything before the fragment is put into a span element and the fragment text into another one.
     * The fragment is also checked for sentence boundaries and treated accordingly.
     * If there is anything left after the fragment, this is repeated recursively.
     * @param nodeText				The text of the node (which has not yet been converted into a span element)
     * @param indexedSentencesIndex	The index in the fragmentIndices list of the relevant fragment indices
     * @param textStartIndex 		The start index of the node text in the entire HTML text
     * @param textEndIndex 			The end index of the node text in the entire HTML text
     * @param replacementElements 	The already generated span elements with which the text node will be replaced
     */
    private void handleNodeTextInFragment(String nodeText, int indexedSentencesIndex, int textStartIndex, int textEndIndex,
                                          ArrayList<Element> replacementElements) {
        Fragment indexedSentence = indexedSentences.get(indexedSentencesIndex);
        if (textStartIndex < indexedSentence.getStartIndex()) { // there's something before the match that we want as a non-question element
            Element replacement = ElementCreator.createHtmlReplacementElement(nodeText.substring(0, indexedSentence.getStartIndex() - textStartIndex));
            replacementElements.add(replacement);
        }

        int endIndex = Math.min(indexedSentence.getEndIndex(), textEndIndex);
        int startIndex = Math.max(indexedSentence.getStartIndex(), textStartIndex);

        if(indexedSentence.isDisplay()) {
            // we only keep the text if the fragment that covers the text is in the selected document part
            String fragmentPart = endIndex - textStartIndex < nodeText.length() ?
                    nodeText.substring(startIndex - textStartIndex, endIndex - textStartIndex) :
                    nodeText.substring(startIndex - textStartIndex);

            Element replacement = createPlainTextReplacementElement(fragmentPart, startIndex,
            		indexedSentence.getBlanksBoundaries());
            replacementElements.add(replacement);

            if (indexedSentence.isSentenceStart() && startIndex == indexedSentence.getStartIndex()) {
                addElementToSentenceBoundaryElements(true, replacement, indexedSentence.getSentenceIndex());
            }
            if (indexedSentence.isSentenceEnd() && endIndex == indexedSentence.getEndIndex()) {
                addElementToSentenceBoundaryElements(false, replacement, indexedSentence.getSentenceIndex());
            }
        }

        if (textEndIndex > indexedSentence.getEndIndex()) { // there's something after the match
            // there might be another fragment in the succeeding fragmentIndex. We need to do this recursively until there is nothing left
            if (indexedSentences.size() > indexedSentencesIndex + 1 &&
                    textEndIndex > indexedSentences.get(indexedSentencesIndex + 1).getStartIndex()) {
                handleNodeTextInFragment(nodeText.substring(indexedSentence.getEndIndex() - textStartIndex),
                        indexedSentencesIndex + 1, indexedSentence.getEndIndex(), textEndIndex, replacementElements);
            } else { // if the next fragment starts only after our text node, we need to put the remaining parts of our text node into a span
                Element replacement = ElementCreator.createHtmlReplacementElement(nodeText.substring(indexedSentence.getEndIndex() - textStartIndex));
                replacementElements.add(replacement);
            }
        }
    }

    /**
     * Adds the element representing a sentence boundary to the global list.
     * @param isStartElement	True if we are dealing with a sentence start
     * @param boundaryElement 	The element representing the sentence boundary
     * @param sentenceIndex 	The index of the sentence to which the fragment belongs
     */
    private void addElementToSentenceBoundaryElements(boolean isStartElement, Element boundaryElement, int sentenceIndex){
        if(isStartElement) {
            sentenceBoundaryElements.add(new Boundaries(boundaryElement, sentenceIndex));
        } else {
            for(Boundaries sentenceBoundaryElement : sentenceBoundaryElements) {
                if(sentenceBoundaryElement.getSentenceIndex() == sentenceIndex) {
                    sentenceBoundaryElement.setEndElement(boundaryElement);
                }
            }
        }
    }

    /**
     * Creates a DOM element with the attribute data-plaintextplaceholder.
     * The syntax is incorrect, but we remove this later anyway (we just need it for splitting).
     * Adds the HTML string for a plain text element to the global list.
     * Extracts constructions (blanks) from the plain text and replaces them with a placeholder.
     * Adds the text of a construction to a single string. This might result in empty HTML tags
     * (we might e.g. lose a hyperlink), but we cannot have split blanks.
     * @param text 				The text to be inserted as content (the node text)
     * @param startIndex 		The index in the entire HTML text at which the text starts
     * @param boundaryIndices	The start and end indices of constructions
     * @return 					The generated DOM element <span data-plaintextplaceholder></span>
     */
    private Element createPlainTextReplacementElement(String text, int startIndex,
                                                      ArrayList<Blank> boundaryIndices){
        ArrayList<Blank> containedBoundaries = new ArrayList<>();
        for(Blank constructionBoundary : boundaryIndices) {
            int constructionBoundaryIndex = constructionBoundary.getBoundaryIndex();
            boolean isStartIndex = constructionBoundary.getBlankIndex() == null;
            if(isStartIndex && constructionBoundaryIndex >= startIndex && constructionBoundaryIndex < startIndex + text.length() ||
            		!isStartIndex && constructionBoundaryIndex > startIndex && constructionBoundaryIndex <= startIndex + text.length()) {
                containedBoundaries.add(constructionBoundary);
            }
        }

        StringBuilder sb = new StringBuilder();
        while(text.length() > 0) {
            if (inConstruction) {
                if(containedBoundaries.size() > 0) {
                    // add the beginning of this text until the construction boundary to the last construction
                    constructions.set(constructions.size() - 1,
                            constructions.get(constructions.size() - 1) +
                            text.substring(0, containedBoundaries.get(0).getBoundaryIndex() - startIndex));
                    text = text.substring(containedBoundaries.get(0).getBoundaryIndex() - startIndex);
                    startIndex = containedBoundaries.get(0).getBoundaryIndex();

                    // it's a closing tag, so we add the brackets
                    sb.append(containedBoundaries.get(0).getText()).append(" ");
                    usedConstructionIndices.add(containedBoundaries.get(0).getBlankIndex());

                    containedBoundaries.remove(0);
                    inConstruction = false;
                } else {
                    // add the entire text to the last construction
                    constructions.set(constructions.size() - 1,
                            constructions.get(constructions.size() - 1) + text);
                    text = "";
                }
            } else {
                if (containedBoundaries.size() > 0) {
                	sb.append(text, 0, containedBoundaries.get(0).getBoundaryIndex() - startIndex);
                    sb.append(ElementCreator.createBlanksPlaceholderElement(constructions.size()));
                    inConstruction = true;
                    constructions.add("");
                    text = text.substring(containedBoundaries.get(0).getBoundaryIndex() - startIndex);
                    startIndex = containedBoundaries.get(0).getBoundaryIndex();
                    containedBoundaries.remove(0);
                } else {
                    sb.append(text);
                    text = "";
                }
            }
        }

        String plainText = "<span data-internal=\"" + plainTextCounter + "\">" + sb.toString() + "</span>";
        plainTextElements.put(plainTextCounter, plainText);

        return ElementCreator.createPlainTextReplacementElement(plainTextCounter++);
    }

}
