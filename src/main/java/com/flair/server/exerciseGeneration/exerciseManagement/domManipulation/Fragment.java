package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.Pair;

public class Fragment {

    private String text;
    private int startIndex;
    private int endIndex;
    private boolean isSentenceStart = false;
    private boolean isSentenceEnd = false;
    private int sentenceIndex;
    private int plainTextStartIndex;
    private boolean isUnambiguousMatch = false;
    private ArrayList<Blank> blanksBoundaries = new ArrayList<>();
    private boolean display = false;

    public Fragment(String text, int startIndex, int endIndex, int sentenceIndex, int plainTextStartIndex, boolean isUnambiguousMatch,
    		ArrayList<Blank> blanksBoundaryIndices, boolean display) {
        this.text = text;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.sentenceIndex = sentenceIndex;
        this.plainTextStartIndex = plainTextStartIndex;
        this.isUnambiguousMatch = isUnambiguousMatch;
        this.blanksBoundaries = blanksBoundaryIndices;
        this.display = display;
    }

    public Fragment(String text, int sentenceIndex, int plainTextStartIndex, boolean display) {
        this.text = text;
        this.sentenceIndex = sentenceIndex;
        this.plainTextStartIndex = plainTextStartIndex;
        this.display = display;
    }

    public Fragment(String text, int startIndex, int endIndex, int sentenceIndex, boolean isUnambiguousMatch,
                    int plainTextStartIndex, boolean display) {
        this.text = text;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.sentenceIndex = sentenceIndex;
        this.isUnambiguousMatch = isUnambiguousMatch;
        this.plainTextStartIndex = plainTextStartIndex;
        this.display = display;
    }

    public Fragment(int startIndex, int endIndex, boolean isSentenceStart, boolean isSentenceEnd,
                    int sentenceIndex, boolean display) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.isSentenceStart = isSentenceStart;
        this.isSentenceEnd = isSentenceEnd;
        this.sentenceIndex = sentenceIndex;
        this.display = display;
    }

    public int getPlainTextStartIndex() {
        return plainTextStartIndex;
    }
    public String getText() {
        return text;
    }
    public int getSentenceIndex() {
        return sentenceIndex;
    }
    public int getStartIndex() {
        return startIndex;
    }
    public int getEndIndex() {
        return endIndex;
    }
    public boolean isUnambiguousMatch() {
        return isUnambiguousMatch;
    }
    public ArrayList<Blank> getBlanksBoundaries() { return blanksBoundaries; }
    public boolean isSentenceStart() { return isSentenceStart; }
    public boolean isSentenceEnd() { return isSentenceEnd; }
    public boolean isDisplay() { return display; }

    public void setStartIndex(int startIndex) {this.startIndex = startIndex; }
    public void setEndIndex(int endIndex) {this.endIndex = endIndex; }
    public void setPlainTextStartIndex(int plainTextStartIndex) {
        this.plainTextStartIndex = plainTextStartIndex;
    }
    public void setText(String text) {
        this.text = text;
    }
    public void setBlanksBoundaries(ArrayList<Blank> blanksBoundaries) { this.blanksBoundaries = blanksBoundaries; }
    public void setSentenceStart(boolean sentenceStart) { isSentenceStart = sentenceStart; }
    public void setSentenceEnd(boolean sentenceEnd) { isSentenceEnd = sentenceEnd; }
    public void setSentenceIndex(int sentenceIndex) { this.sentenceIndex = sentenceIndex;}

}
