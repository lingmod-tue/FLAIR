package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;

import java.util.ArrayList;
import java.util.HashMap;

public class MatchResult {

    public MatchResult(ArrayList<Boundaries> sentenceBoundaryElements, HashMap<Integer, String> plainTextElements) {
        this.sentenceBoundaryElements = sentenceBoundaryElements;
        this.plainTextElements = plainTextElements;
    }

    private ArrayList<Boundaries> sentenceBoundaryElements;
    private HashMap<Integer, String> plainTextElements;
    
    public ArrayList<Boundaries> getSentenceBoundaryElements() {
        return sentenceBoundaryElements;
    }

    public HashMap<Integer, String> getPlainTextElements() {
        return plainTextElements;
    }

}
