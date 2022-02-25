package com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement;

import java.util.ArrayList;

import com.flair.shared.exerciseGeneration.Pair;

import edu.stanford.nlp.ling.IndexedWord;

public class Clause {

	public Pair<Integer, Integer> indices = null;
    public IndexedWord root = null;
    public ArrayList<IndexedWord> verbs = new ArrayList<>();
    public IndexedWord mainVerb = null;
    ArrayList<Pair<Integer, Integer>> chunks = null;
    public boolean negated = false;
    public boolean subjectAuxiliaryInversion = false;
    public IndexedWord subject = null;
    public String extendedSubject = "";
    public boolean thirdSg = true;
    public boolean progressive = false;
    public boolean perfect = false;
    public boolean past = false;
    public String modal = null;
    public boolean future = false;
    ArrayList<String> distractors = new ArrayList<>();
    String translation = "";
    ArrayList<String> lemmatizedClause = new ArrayList<>();
    Pair<Integer, Integer> targetPosition = null;
    ArrayList<String> semanticDistractor = new ArrayList<>();
    
}
