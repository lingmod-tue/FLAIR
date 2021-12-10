package com.flair.shared.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Construction implements IsSerializable {

    private DetailedConstruction construction;
    private Pair<Integer, Integer> constructionIndices;
    private String bracketsText = "";
    /**
     * The distractor texts and whether they are ill-formed forms (true) or not (false)
     */
    private ArrayList<Pair<String, Boolean>> distractors = new ArrayList<>();
    private ArrayList<Construction> sentenceConstructions = new ArrayList<>();
    private String constructionText = "";
    private int sentenceIndex = 0;

    public Construction() {}
    
    public Construction(DetailedConstruction construction, Pair<Integer, Integer> constructionIndices) {
        this.construction = construction;
        this.constructionIndices = constructionIndices;
    }

    public DetailedConstruction getConstruction() { return construction; }
    public Pair<Integer, Integer> getConstructionIndices() { return constructionIndices; }
    public String getBracketsText() { return bracketsText; }
    public ArrayList<Pair<String, Boolean>> getDistractors() { return distractors; }
	public ArrayList<Construction> getSentenceConstructions() { return sentenceConstructions; }
	public String getConstructionText() { return constructionText; }
    public int getSentenceIndex() { return sentenceIndex; }
	public void setBracketsText(String bracketsText) { this.bracketsText = bracketsText; }
    public void setConstructionIndices(Pair<Integer, Integer> constructionIndices) {
        this.constructionIndices = constructionIndices;
    }
    public void setDistractors(ArrayList<Pair<String, Boolean>> distractors) { this.distractors = distractors; }
	public void setConstruction(DetailedConstruction construction) { this.construction = construction; }
	public void setConstructionText(String constructionText) { this.constructionText = constructionText; }
	public void setSentenceIndex(int sentenceIndex) { this.sentenceIndex = sentenceIndex; }

}