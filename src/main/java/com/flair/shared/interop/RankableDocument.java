package com.flair.shared.interop;

import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.parser.DocumentReadabilityLevel;

import java.util.ArrayList;
import java.util.HashSet;

/*
 * Represents a document that's rankable
 */
public interface RankableDocument extends BasicDocumentTransferObject {
	public interface ConstructionRange {
		public int getStart();
		public int getEnd();
		public GrammaticalConstruction getConstruction();
	}

	public interface KeywordRange {
		public int getStart();
		public int getEnd();
		public String getKeyword();
	}

	public int getRank();            // original rank of the document (in its collection)
	public void setRank(int rank);

	public String getUrl();
	public String getDisplayUrl();

	public HashSet<GrammaticalConstruction> getConstructions();        // constructions found in this document
	public boolean hasConstruction(GrammaticalConstruction gram);
	public double getConstructionFreq(GrammaticalConstruction gram);
	public double getConstructionRelFreq(GrammaticalConstruction gram);
	public ArrayList<? extends ConstructionRange> getConstructionOccurrences(GrammaticalConstruction gram);

	public double getKeywordCount();        // total number of hits
	public double getKeywordRelFreq();    // relative to the number of words in the doc
	public ArrayList<? extends KeywordRange> getKeywordOccurrences();

	public int getRawTextLength();        // wrapper around getText().size()
	public double getNumWords();            // also the "length" of the document
	public double getNumSentences();
	public double getNumDependencies();
	public DocumentReadabilityLevel getReadabilityLevel();
	public double getReadablilityScore();
}
