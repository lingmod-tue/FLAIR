package com.flair.client.model.interfaces;

import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.RankableDocument;
import com.flair.shared.parser.DocumentReadabilityLevel;

import java.util.Collection;

/*
 * Output data of the RankerLogic class
 */
public interface DocumentRankerOutput {
	public interface Rank {
		public Language getLanguage();

		public Collection<RankableDocument> getRankedDocuments();    // sorted and filtered according to input weights
		public int getNumFilteredDocuments();

		public double getDocLevelDf(DocumentReadabilityLevel level);
		public double getConstructionDf(GrammaticalConstruction gram);

		public boolean isConstructionWeighted(GrammaticalConstruction gram);
		public boolean isKeywordWeighted();

		public double getConstructionWeight(GrammaticalConstruction gram);
		public double getKeywordWeight();
	}
}
