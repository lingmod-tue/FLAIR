package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.DocumentParsing.WebpageParsing;

import com.flair.shared.exerciseGeneration.Construction;

public class Blank {
    /**
     * The boundary index of the blank in the entire text.
     * The text can be either normalized or non-normlaized, plain text or HTML text. The indices are adjusted accordingly.
     * Can be either a start index or an end index.
     */
    private int boundaryIndex;

    /**
     * The index of the construction in the original list of all constructions.
     * Only set for end indices.
     */
    int constructionIndex;
    
    /**
     * The construction to which the boundary belongs.
     * Only set for end indices.
     */
    private Construction construction;

    public Blank(int boundaryIndex, int constructionIndex) {
    	this(boundaryIndex, constructionIndex, null);
    }

    public Blank(int boundaryIndex, int constructionIndex, Construction construction) {
        this.boundaryIndex = boundaryIndex;
        this.constructionIndex = constructionIndex;
        this.construction = construction;
    }

    public int getBoundaryIndex() { return boundaryIndex; }
    public int getConstructionIndex() { return constructionIndex; }
    public Construction getConstruction() { return construction; }

	public void setBoundaryIndex(int boundaryIndex) { this.boundaryIndex = boundaryIndex; }
}
