package com.flair.shared.exerciseGeneration;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Construction implements IsSerializable {

    private DetailedConstruction construction;
    private Pair<Integer, Integer> constructionIndices;
    private String bracketsText = "";

    public Construction() {}
    
    public Construction(DetailedConstruction construction, Pair<Integer, Integer> constructionIndices) {
        this.construction = construction;
        this.constructionIndices = constructionIndices;
    }

    public DetailedConstruction getConstruction() { return construction; }
    public Pair<Integer, Integer> getConstructionIndices() { return constructionIndices; }
    public String getBracketsText() { return bracketsText; }

    public void setBracketsText(String bracketsText) { this.bracketsText = bracketsText; }
    public void setConstructionIndices(Pair<Integer, Integer> constructionIndices) {
        this.constructionIndices = constructionIndices;
    }
}