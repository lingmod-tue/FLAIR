package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;


public class SentenceManager {

    /**
     * Replaces the identified HTML elements which make up a sentence with a placeholder element later used for splitting.
     * Elements belonging to a sentence are considered to be any elements between the sentence start element and the sentence end element.
     * If there are elements which need to be included in order to get a connected DOM part including start and end elements, they are included in the sentence.
     * @param sentenceBoundaryElements 	The DOM elements per sentence marking its start and end
     * @return 							The HTML strings of the replaced sentences.
     */
    public ArrayList<String> extractSentencesFromDom(ArrayList<Boundaries> sentenceBoundaryElements) {
        ArrayList<ArrayList<Element>> sentences = new ArrayList<>();

        for(Boundaries sentenceBoundaries : sentenceBoundaryElements) {
            ArrayList<Element> boundaryElements = new ArrayList<>();
            boundaryElements.add(sentenceBoundaries.getStartElement());
            boundaryElements.add(sentenceBoundaries.getEndElement());
            ArrayList<Element> sentenceParts = getMinimalAdjoiningElements(boundaryElements);

            if(sentenceParts != null) {
                sentences = mergeSentences(sentences, sentenceParts);
            }
        }

        return replaceSentencesInDom(sentences);
    }

    /**
     * Replaces the sentence elements in the DOM with placeholder elements.
     * @param sentences	The sentences consisting of adjoining DOM elements
     * @return 			The HTML strings of the replaced sentences
     */
    private ArrayList<String> replaceSentencesInDom(ArrayList<ArrayList<Element>> sentences) {
        ArrayList<String> sentenceHtml = new ArrayList<>();

        int sentenceCounter = 0;
        for(ArrayList<Element> sentence : sentences) {
            StringBuilder htmlText = new StringBuilder();

            htmlText.append(sentence.get(0).toString());
            sentence.get(0).replaceWith(ElementCreator.createSentencePlaceholderElement(sentenceCounter++));
            for(int k = 1; k < sentence.size(); k++) {
                htmlText.append(sentence.get(k).toString());
                sentence.get(k).remove();
            }

            sentenceHtml.add(htmlText.toString());
        }

        return sentenceHtml;
    }

    /**
     * Checks if sentences overlap and merges them if this is the case.
     * @param previousSentences	The already identified elements of sentences
     * @param newSentenceParts 	The elements of the newly identified sentence
     * @return 					Non-overlapping sentences
     */
    private ArrayList<ArrayList<Element>> mergeSentences(ArrayList<ArrayList<Element>> previousSentences, 
    		ArrayList<Element> newSentenceParts){
        ArrayList<Integer> overlapSentenceIndices = new ArrayList<>();

        for(Element sentenceElement : newSentenceParts) {
            for(int j = 0; j < previousSentences.size(); j++) {
                for(Element otherSentenceElement : previousSentences.get(j)) {
                    // check if the new sentence contains any element(s) of previous sentences
                    if (sentenceElement.getAllElements().contains(otherSentenceElement)){
                        if(!overlapSentenceIndices.contains(j)) {
                            overlapSentenceIndices.add(j);
                        }
                    }

                    // check if any previous sentence contains any element(s) of the new sentence
                    if (otherSentenceElement.getAllElements().contains(sentenceElement)){
                        if(!overlapSentenceIndices.contains(j)) {
                            overlapSentenceIndices.add(j);
                        }
                    }
                }
            }
        }

        ArrayList<ArrayList<Element>> newSentences = new ArrayList<>();
        for(int i = 0; i < previousSentences.size(); i++) {
            if(!overlapSentenceIndices.contains(i)) {
                newSentences.add(previousSentences.get(i));
            }
        }

        if(overlapSentenceIndices.size() > 0) {
            for(int sentenceIndex : overlapSentenceIndices) {
                newSentenceParts.addAll(previousSentences.get(sentenceIndex));
            }
            ArrayList<Element> mergedSentence = getMinimalAdjoiningElements(newSentenceParts);
            if(mergedSentence != null) {
                newSentences = mergeSentences(newSentences, mergedSentence);
            }
        } else {
            newSentences.add(newSentenceParts);
        }

        return newSentences;
    }

    /**
     * Retrieves the adjoining DOM elements which contain all the elementsToMerge but no more.
     * @param elementsToMerge	The elements to be contained in the adjoining elements
     * @return 					The adjoining elements
     */
    private ArrayList<Element> getMinimalAdjoiningElements(ArrayList<Element> elementsToMerge) {
        Element startElement = elementsToMerge.get(0);
        Element startElementParent = startElement.parent();
        boolean allElementsContained = false;
        while (startElementParent != null && !allElementsContained) {
            allElementsContained = true;
            for(int i = 1; i < elementsToMerge.size(); i++) {
                Elements elementAncestors = elementsToMerge.get(i).parents();
             // If the parent element is not contained in the ancestors, the dom was modified and we're at the top level
                if (startElement.parents().contains(startElementParent) && !elementAncestors.contains(startElementParent)) {                    
                    startElementParent = startElementParent.parent();
                    allElementsContained = false;
                }
            }
        }

        if(startElementParent != null) { //we've found a common ancestor (at least the doc should always be a common ancestor)
            ArrayList<Element> sentenceParts = new ArrayList<>();

            // we don't need the entire parent element, but only those children starting from the start element and ending with the end element
            Elements siblings = startElementParent.children();
            boolean addToSentenceHtml = false;
            ArrayList<Element> buffer = new ArrayList<>();
            for (Element sibling : siblings) {
                addToSentenceHtml = false;
                for(Element mergeElement : elementsToMerge) {
                    if (sibling.getAllElements().contains(mergeElement)) {
                        addToSentenceHtml = true;
                    }
                }

                if (addToSentenceHtml) {
                    sentenceParts.addAll(buffer);
                    buffer.clear();
                    sentenceParts.add(sibling);
                } else { // this might be an element that we don't actually need (only if another contained element comes afterwards, we need to add it as well
                    if(sentenceParts.size() > 0 ) { // we only even consider elements if a merge part has already been found (no siblings before that)
                        buffer.add(sibling);
                    }
                }
            }
            if(addToSentenceHtml) {
                sentenceParts.addAll(buffer);
            }

            return sentenceParts;
        }

        return null;
    }

}
