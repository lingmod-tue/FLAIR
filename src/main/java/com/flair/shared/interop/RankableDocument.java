package com.flair.shared.interop;

import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.parser.DocumentReadabilityLevel;

import java.util.ArrayList;
import java.util.HashSet;

/*
 * Represents a document that's rankable
 */
public interface RankableDocument extends DocumentDTO {
	interface ConstructionRange {
		int getStart();
		int getEnd();
		GrammaticalConstruction getConstruction();
	}

	interface KeywordRange {
		int getStart();
		int getEnd();
		String getKeyword();
	}

	int getRank();            // original rank of the document (in its collection)
	void setRank(int rank);

	String getUrl();
	String getDisplayUrl();

	HashSet<GrammaticalConstruction> getConstructions();        // constructions found in this document
	boolean hasConstruction(GrammaticalConstruction gram);
	double getConstructionFreq(GrammaticalConstruction gram);
	double getConstructionRelFreq(GrammaticalConstruction gram);
	ArrayList<? extends ConstructionRange> getConstructionOccurrences(GrammaticalConstruction gram);

	double getKeywordCount();        // total number of hits
	double getKeywordRelFreq();    // relative to the number of words in the doc
	ArrayList<? extends KeywordRange> getKeywordOccurrences();

	int getRawTextLength();        // wrapper around getText().size()
	double getNumWords();            // also the "length" of the document
	double getNumSentences();
	double getNumDependencies();
	DocumentReadabilityLevel getReadabilityLevel();
	double getReadablilityScore();
}
