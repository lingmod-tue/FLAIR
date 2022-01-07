package com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement;

import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.Pair;

public class ConditionalConstruction extends Construction {

    public ConditionalConstruction() {}
    
    public ConditionalConstruction(DetailedConstruction construction, Pair<Integer, Integer> constructionIndices, 
    		boolean isMainClause, Pair<Integer, Integer> otherClauseIndices) {
    	super(construction, constructionIndices);
    	
    	this.isMainClause = isMainClause;
    	this.otherClauseIndices = otherClauseIndices;
    }
    
	private boolean isMainClause;
	private Pair<Integer, Integer> otherClauseIndices;

    public boolean isMainClause() { return isMainClause; }

	public Pair<Integer, Integer> getOtherClauseIndices() { return otherClauseIndices; }

}