package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;

public class Blank {
    /**
     * The boundary index of the blank in the entire text.
     * The text can be either normalized or non-normlaized, plain text or HTML text. The indices are adjusted accordingly.
     * Can be either a start index or an end index.
     */
    private int boundaryIndex;

    /**
     * The correct blanks text.
     * Only set for end indices.
     */
    private String text = "";

    /**
     * The index of the construction in the original list of all constructions.
     * Only set for end indices.
     */
    private Integer blankIndex = null;

    public Blank(int boundaryIndex) {
        this.boundaryIndex = boundaryIndex;
    }

    public Blank(int boundaryIndex, String text, int blankIndex) {
        this.boundaryIndex = boundaryIndex;
        this.text = text;
        this.blankIndex = blankIndex;
    }

    public int getBoundaryIndex() { return boundaryIndex; }
    public String getText() { return text; }
    public int getBlankIndex() { return blankIndex; }

    public void setBoundaryIndex(int boundaryIndex) { this.boundaryIndex = boundaryIndex; }
}
