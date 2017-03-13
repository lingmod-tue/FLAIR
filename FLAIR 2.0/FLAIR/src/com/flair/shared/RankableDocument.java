package com.flair.shared;

import java.util.ArrayList;
import java.util.Set;

import com.flair.shared.grammar.GrammaticalConstruction;

/*
 * Represents a document that's rankable by the RankerLogic class
 */
public interface RankableDocument
{
    public interface ConstructionRange extends Comparable<ConstructionRange>
    {
		public int						getStart();
		public int						getEnd();
		public GrammaticalConstruction	getConstruction();
    }
    
    public interface KeywordRange extends Comparable<KeywordRange>
    {
    	public int		getStart();
		public int		getEnd();
		public String	getKeyword();
    }

    public int									getOrgRank();			// original rank of the document (in its collection)
    
    public String								getTitle();
    public String								getUrl();
    public String								getDisplayUrl();
    public String								getSnippet();
    public String								getText();
    
    public Set<GrammaticalConstruction>			getConstructions();		// constructions found in this document
    public boolean								hasConstruction(GrammaticalConstruction gram);
    public double								getConstructionFreq(GrammaticalConstruction gram);
    public double								getConstructionRelFreq(GrammaticalConstruction gram);
    public Iterable<ConstructionRange>			getConstructionOccurrences(GrammaticalConstruction gram);
    
    public double								getKeywordCount();		// total number of hits
    public double								getKeywordRelFreq();	// relative to the number of words in the doc
    public Iterable<KeywordRange>				getKeywordOccurrences();
    
    public double								getNumWords();			// also the "length" of the document
    public double								getNumSentences();
    public double								getNumDependencies();
    public DocumentReadabilityLevel				getReadabilityLevel();
}
