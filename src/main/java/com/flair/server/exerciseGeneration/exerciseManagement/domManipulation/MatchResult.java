package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;

import java.util.ArrayList;
import java.util.HashMap;

import com.flair.shared.exerciseGeneration.Pair;

public class MatchResult {

    public MatchResult(ArrayList<Boundaries> sentenceBoundaryElements, HashMap<Integer, String> plainTextElements,
    		ArrayList<Pair<String, Integer>> constructions) {
        this.sentenceBoundaryElements = sentenceBoundaryElements;
        this.plainTextElements = plainTextElements;
        this.constructions = constructions;
    }

    private ArrayList<Boundaries> sentenceBoundaryElements;
    private HashMap<Integer, String> plainTextElements;
    private ArrayList<Pair<String, Integer>> constructions;
    
    public ArrayList<Boundaries> getSentenceBoundaryElements() {
        return sentenceBoundaryElements;
    }

    public HashMap<Integer, String> getPlainTextElements() {
        return plainTextElements;
    }

    public ArrayList<Pair<String, Integer>> getConstructions() {
        return constructions;
    }
}
