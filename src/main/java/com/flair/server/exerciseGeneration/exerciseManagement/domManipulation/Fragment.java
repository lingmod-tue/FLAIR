package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;

import java.util.ArrayList;

public class Fragment {

    private String text;
    private int startIndex;
    private int endIndex;
    private boolean isSentenceStart;
    private boolean isSentenceEnd;
    private Integer sentenceIndex;
    private int plainTextStartIndex;
    private boolean isUnambiguousMatch;
    private ArrayList<Blank> blanksBoundaries;
    private boolean display;

    public Fragment(String text, int startIndex, int endIndex, Integer sentenceIndex, int plainTextStartIndex,
            boolean isUnambiguousMatch, ArrayList<Blank> blanksBoundaryIndices, boolean display,
            boolean isSentenceStart, boolean isSentenceEnd) {
		this.text = text;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.sentenceIndex = sentenceIndex;
		this.plainTextStartIndex = plainTextStartIndex;
		this.isUnambiguousMatch = isUnambiguousMatch;
		this.blanksBoundaries = blanksBoundaryIndices;
		this.display = display;
		this.isSentenceStart = isSentenceStart;
		this.isSentenceEnd = isSentenceEnd;
	}
	
	public Fragment(String text, int startIndex, int endIndex, Integer sentenceIndex, int plainTextStartIndex, boolean isUnambiguousMatch,
	            ArrayList<Blank> blanksBoundaryIndices, boolean display) {
		this(text, startIndex, endIndex, sentenceIndex, plainTextStartIndex, isUnambiguousMatch, blanksBoundaryIndices, display,
		        false, false);
	}
	
	public Fragment(String text, Integer sentenceIndex, int plainTextStartIndex, boolean display) {
		this(text, 0, 0, sentenceIndex, plainTextStartIndex, false,
		        new ArrayList<>(), display, false, false);
	}
	
	public Fragment(String text, int startIndex, int endIndex, Integer sentenceIndex, boolean isUnambiguousMatch,
	            int plainTextStartIndex, boolean display) {
		this(text, startIndex, endIndex, sentenceIndex, plainTextStartIndex, isUnambiguousMatch, new ArrayList<>(),
		        display, false, false);
	}
	
	public Fragment(int startIndex, int endIndex, boolean isSentenceStart, boolean isSentenceEnd,
	            Integer sentenceIndex, boolean display) {
		this(null, startIndex, endIndex, sentenceIndex, 0, false, new ArrayList<>(),
		        display, isSentenceStart, isSentenceEnd);
	}

    public int getPlainTextStartIndex() { return plainTextStartIndex; }
    public String getText() { return text; }
    public int getSentenceIndex() { return sentenceIndex; }
    public int getStartIndex() { return startIndex; }
    public int getEndIndex() { return endIndex; }
    public boolean isUnambiguousMatch() { return isUnambiguousMatch; }
    public ArrayList<Blank> getBlanksBoundaries() { return blanksBoundaries; }
    public boolean isSentenceStart() { return isSentenceStart; }
    public boolean isSentenceEnd() { return isSentenceEnd; }
    public boolean isDisplay() { return display; }

	public void setStartIndex(int startIndex) {this.startIndex = startIndex; }
    public void setEndIndex(int endIndex) {this.endIndex = endIndex; }
    public void setPlainTextStartIndex(int plainTextStartIndex) { this.plainTextStartIndex = plainTextStartIndex; }
    public void setText(String text) { this.text = text; }
    public void setBlanksBoundaries(ArrayList<Blank> blanksBoundaries) { this.blanksBoundaries = blanksBoundaries; }
    public void setSentenceStart(boolean sentenceStart) { isSentenceStart = sentenceStart; }
    public void setSentenceEnd(boolean sentenceEnd) { isSentenceEnd = sentenceEnd; }
    public void setSentenceIndex(int sentenceIndex) { this.sentenceIndex = sentenceIndex;}

}
