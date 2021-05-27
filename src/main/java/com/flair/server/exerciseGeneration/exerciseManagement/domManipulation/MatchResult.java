package com.flair.server.exerciseGeneration.exerciseManagement.domManipulation;

import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.nodes.Node;

import com.flair.shared.exerciseGeneration.Pair;

public class MatchResult {

    public MatchResult(ArrayList<Boundaries> sentenceBoundaryElements, HashMap<Integer, String> plainTextElements, Pair<Node, Node> textBoundaries) {
        this.sentenceBoundaryElements = sentenceBoundaryElements;
        this.plainTextElements = plainTextElements;
        this.textBoundaries = textBoundaries;
    }

    private ArrayList<Boundaries> sentenceBoundaryElements;
    private HashMap<Integer, String> plainTextElements;
    private Pair<Node, Node> textBoundaries;
    
    public ArrayList<Boundaries> getSentenceBoundaryElements() { return sentenceBoundaryElements; }
    public HashMap<Integer, String> getPlainTextElements() { return plainTextElements; }
    public Pair<Node, Node> getTextBoundaries() { return textBoundaries; }

}
