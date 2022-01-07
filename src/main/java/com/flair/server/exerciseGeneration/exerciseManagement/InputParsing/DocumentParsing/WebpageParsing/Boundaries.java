package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.DocumentParsing.WebpageParsing;

import org.jsoup.nodes.Element;

public class Boundaries {

    public Boundaries(Element startElement, int sentenceIndex) {
        this.startElement = startElement;
        this.sentenceIndex = sentenceIndex;
    }

    private Element startElement;
    private Element endElement = null;
    int sentenceIndex;

    public Element getStartElement() { return startElement; }
    public Element getEndElement() {
        return endElement;
    }
    public int getSentenceIndex() {
        return sentenceIndex;
    }

    public void setEndElement(Element endElement) {
        this.endElement = endElement;
    }

}
