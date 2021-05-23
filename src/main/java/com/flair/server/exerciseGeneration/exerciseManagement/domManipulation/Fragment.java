package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;

import java.util.ArrayList;

public class Fragment {

    private String text;
    private int startIndex;
    private Integer endIndex;
    private boolean isSentenceStart;
    private boolean isSentenceEnd;
    private Integer sentenceIndex;
    private int plainTextStartIndex;
    private ArrayList<Blank> blanksBoundaries;
    private boolean display;

    public Fragment(String text, int startIndex, Integer endIndex, Integer sentenceIndex, int plainTextStartIndex,
            ArrayList<Blank> blanksBoundaryIndices, boolean display,
            boolean isSentenceStart, boolean isSentenceEnd) {
		this.text = text;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.sentenceIndex = sentenceIndex;
		this.plainTextStartIndex = plainTextStartIndex;
		this.blanksBoundaries = blanksBoundaryIndices;
		this.display = display;
		this.isSentenceStart = isSentenceStart;
		this.isSentenceEnd = isSentenceEnd;
	}

	public Fragment(String text, int startIndex, int endIndex, int plainTextStartIndex, boolean display) {
		this(text, startIndex, endIndex, null, plainTextStartIndex, new ArrayList<>(), display, false, false);
	}
	
	/**
	 * Only applicable at creation time in text comparer.
	 */
	public Fragment(String text, int startIndex, int plainTextStartIndex) {
		this(text, startIndex, null, null, plainTextStartIndex, new ArrayList<>(), true, false, false);
	}
	
	/**
	 * Only applicable for non-matched fragments.
	 */
	public Fragment(int startIndex, int endIndex, boolean display) {
		this(null, startIndex, endIndex, null, 0, new ArrayList<>(), display, false, false);
	}

    public int getPlainTextStartIndex() { return plainTextStartIndex; }
    public String getText() { return text; }
    public int getSentenceIndex() { return sentenceIndex; }
    public int getStartIndex() { return startIndex; }
    public Integer getEndIndex() { return endIndex; }
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
	public void setDisplay(boolean display) { this.display = display; }

}
