package com.flair.client.model.interfaces;

import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.parser.DocumentReadabilityLevel;
import com.flair.shared.parser.RankableDocument;

/*
 * Output data of the RankerLogic class
 */
public interface DocumentRankerOutput
{
	public interface Rank 
	{
		public Iterable<RankableDocument>	getRankedDocuments();	// sorted and filtered according to input weights
		public double						getDocLevelDf(DocumentReadabilityLevel level);
		public double						getConstructionDf(GrammaticalConstruction gram);
		
		public double						getConstructionWeight(GrammaticalConstruction gram);
		public double						getKeywordWeight();
	}
}
