package com.flair.shared.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Construction implements IsSerializable {

    private DetailedConstruction construction;
    private Pair<Integer, Integer> constructionIndices;
    private String bracketsText = "";
    private ArrayList<String> distractors = new ArrayList<>();
    private ArrayList<Construction> sentenceConstructions = new ArrayList<>();
    private String constructionText = "";

    public Construction() {}
    
    public Construction(DetailedConstruction construction, Pair<Integer, Integer> constructionIndices) {
        this.construction = construction;
        this.constructionIndices = constructionIndices;
    }

    public DetailedConstruction getConstruction() { return construction; }
    public Pair<Integer, Integer> getConstructionIndices() { return constructionIndices; }
    public String getBracketsText() { return bracketsText; }
    public ArrayList<String> getDistractors() { return distractors; }
	public ArrayList<Construction> getSentenceConstructions() { return sentenceConstructions; }
	public String getConstructionText() { return constructionText; }

    public void setBracketsText(String bracketsText) { this.bracketsText = bracketsText; }
    public void setConstructionIndices(Pair<Integer, Integer> constructionIndices) {
        this.constructionIndices = constructionIndices;
    }
    public void setDistractors(ArrayList<String> distractors) { this.distractors = distractors; }
	public void setConstruction(DetailedConstruction construction) { this.construction = construction; }
	public void setConstructionText(String constructionText) { this.constructionText = constructionText; }
    
}