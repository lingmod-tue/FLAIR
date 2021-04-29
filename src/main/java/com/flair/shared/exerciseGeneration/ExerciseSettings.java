package com.flair.shared.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ExerciseSettings implements IsSerializable{

    private ArrayList<Pair<String, Boolean>> sentences;
    private ArrayList<Integer> sentenceStartIndices;
    private ArrayList<Construction> constructions;
    private String url;
    private String plainText;
    private int selectionStartIndex;
    private int selectionEndIndex;
    private String contentType;
    private String quiz;

    public ExerciseSettings() {}
    
    public ExerciseSettings(ArrayList<Construction> constructions, String url,
                            String plainText, int selectionStartIndex, int selectionEndIndex,
                            String contentType, String quiz) {
        this.constructions = constructions;
        this.url = url;
        this.plainText = plainText;
        this.selectionStartIndex = selectionStartIndex;
        this.selectionEndIndex = selectionEndIndex;
        this.contentType = contentType;
        this.quiz = quiz;
    }

    public void setSentences(ArrayList<Pair<String, Boolean>> sentences) { this.sentences = sentences; }
    public void setSentenceStartIndices(ArrayList<Integer> sentenceStartIndices) {
        this.sentenceStartIndices = sentenceStartIndices; }

    public ArrayList<Pair<String, Boolean>> getSentences() { return sentences; }
    public ArrayList<Integer> getSentenceStartIndices(){ return sentenceStartIndices; }
    public ArrayList<Construction> getConstructions() { return constructions; }
    public String getUrl() { return url; }
    public String getPlainText() { return plainText; }
    public int getSelectionStartIndex() { return selectionStartIndex; }
    public int getSelectionEndIndex() { return selectionEndIndex; }
    public String getContentType() { return contentType; }
    public String getQuiz() { return quiz; }

}