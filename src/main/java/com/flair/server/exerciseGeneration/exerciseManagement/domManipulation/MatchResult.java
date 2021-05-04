package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;

import java.util.ArrayList;
import java.util.HashMap;

public class MatchResult {

    public MatchResult(ArrayList<Boundaries> sentenceBoundaryElements, HashMap<Integer, String> plainTextElements,
                       ArrayList<String> constructions) {
        this.sentenceBoundaryElements = sentenceBoundaryElements;
        this.plainTextElements = plainTextElements;
        this.constructions = constructions;
    }

    private ArrayList<Boundaries> sentenceBoundaryElements;
    private HashMap<Integer, String> plainTextElements;
    private ArrayList<String> constructions;

    public ArrayList<Boundaries> getSentenceBoundaryElements() {
        return sentenceBoundaryElements;
    }

    public HashMap<Integer, String> getPlainTextElements() {
        return plainTextElements;
    }

    public ArrayList<String> getConstructions() {
        return constructions;
    }
}
