package com.flair.client.ranker;

import com.flair.shared.DocumentReadabilityLevel;
import com.flair.shared.RankableDocument;
import com.flair.shared.grammar.GrammaticalConstruction;

/*
 * Output data of the RankerLogic class
 */
public interface RankerLogicOutput
{
	public interface Rank 
	{
		public Iterable<RankableDocument>	getRankedDocuments();	// sorted and filtered according to input weights
		public double						getDocLevelDf(DocumentReadabilityLevel level);
		public double						getConstructionDf(GrammaticalConstruction gram);
	}
}
