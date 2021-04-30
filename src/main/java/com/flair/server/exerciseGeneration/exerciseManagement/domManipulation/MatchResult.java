package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;

import java.util.ArrayList;

public class MatchResult {

    public MatchResult(ArrayList<Boundaries> sentenceBoundaryElements, ArrayList<String> plainTextElements,
                       ArrayList<String> constructions) {
        this.sentenceBoundaryElements = sentenceBoundaryElements;
        this.plainTextElements = plainTextElements;
        this.constructions = constructions;
    }

    private ArrayList<Boundaries> sentenceBoundaryElements;
    private ArrayList<String> plainTextElements;
    private ArrayList<String> constructions;

    public ArrayList<Boundaries> getSentenceBoundaryElements() {
        return sentenceBoundaryElements;
    }

    public ArrayList<String> getPlainTextElements() {
        return plainTextElements;
    }

    public ArrayList<String> getConstructions() {
        return constructions;
    }
}
