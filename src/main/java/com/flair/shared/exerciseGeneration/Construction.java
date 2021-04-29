package com.flair.shared.exerciseGeneration;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Construction implements IsSerializable {

    private ArrayList<DistractorProperties> distractors;
    private ArrayList<BracketsProperties> brackets;
    private int nDistractors;
    private DetailedConstruction construction;
    private Pair<Integer, Integer> constructionIndices;
    private String bracketsText = "";

    public Construction() {}
    
    public Construction(ArrayList<DistractorProperties> distractors, ArrayList<BracketsProperties> brackets, int nDistractors,
                        DetailedConstruction construction, Pair<Integer, Integer> constructionIndices) {
        this.distractors = distractors;
        this.brackets = brackets;
        this.nDistractors = nDistractors;
        this.construction = construction;
        this.constructionIndices = constructionIndices;
    }

    public ArrayList<DistractorProperties> getDistractors() { return distractors; }
    public ArrayList<BracketsProperties> getBrackets() { return brackets; }
    public int getnDistractors() { return nDistractors; }
    public DetailedConstruction getConstruction() { return construction; }
    public Pair<Integer, Integer> getConstructionIndices() { return constructionIndices; }
    public String getBracketsText() { return bracketsText; }

    public void setBracketsText(String bracketsText) { this.bracketsText = bracketsText; }
    public void setConstructionIndices(Pair<Integer, Integer> constructionIndices) {
        this.constructionIndices = constructionIndices;
    }
}