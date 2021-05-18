package com.flair.shared.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Construction implements IsSerializable {

    private DetailedConstruction construction;
    private Pair<Integer, Integer> constructionIndices;
    private Pair<Integer, Integer> originalConstructionIndices;
    private String bracketsText = "";
    private String constructionText = "";
    private ArrayList<String> distractors = new ArrayList<>();

    public Construction() {}
    
    public Construction(DetailedConstruction construction, Pair<Integer, Integer> constructionIndices) {
        this.construction = construction;
        this.constructionIndices = constructionIndices;
        this.originalConstructionIndices = constructionIndices;
    }

    public DetailedConstruction getConstruction() { return construction; }
    public Pair<Integer, Integer> getConstructionIndices() { return constructionIndices; }
    public Pair<Integer, Integer> getOriginalConstructionIndices() { return originalConstructionIndices; }
    public String getBracketsText() { return bracketsText; }
    public String getConstructionText() { return constructionText; }
    public ArrayList<String> getDistractors() { return distractors; }

    public void setBracketsText(String bracketsText) { this.bracketsText = bracketsText; }
    public void setConstructionIndices(Pair<Integer, Integer> constructionIndices) {
        this.constructionIndices = constructionIndices;
    }
    public void setOriginalConstructionIndices(Pair<Integer, Integer> constructionIndices) {
        this.originalConstructionIndices = constructionIndices;
    }
    public void setConstructionText(String constructionText) { this.constructionText = constructionText; }
    public void setDistractors(ArrayList<String> distractors) { this.distractors = distractors; }
	public void setConstruction(DetailedConstruction construction) { this.construction = construction; }
    
}