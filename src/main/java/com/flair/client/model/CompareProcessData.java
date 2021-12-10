package com.flair.client.model;

import com.flair.client.model.interfaces.AbstractWebRankerCore;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.dtos.RankableDocument;
import com.flair.shared.parser.DocumentReadabilityLevel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

final class CompareProcessData extends ProcessData {
	final class ComparisonWrapper implements RankableDocument {
		final RankableDocument doc;
		int rank;        // store the default rank separately to prevent the org doc from being modified

		public ComparisonWrapper(RankableDocument d) {
			doc = d;
			rank = -1;
		}
		ComparisonWrapper() {
			doc = null;
		}

		@Override
		public Language getLanguage() {
			return doc.getLanguage();
		}

		@Override
		public String getTitle() {
			return doc.getTitle();
		}

		@Override
		public String getSnippet() {
			return doc.getSnippet();
		}

		@Override
		public String getText() {
			return doc.getText();
		}
		@Override
		public String getOperationId() {
			return doc.getOperationId();
		}

		@Override
		public int getLinkingId() {
			return doc.getLinkingId();
		}

		@Override
		public int getRank() {
			return rank;
		}

		@Override
		public void setRank(int rank) {
			this.rank = rank;
		}

		@Override
		public String getUrl() {
			return doc.getUrl();
		}

		@Override
		public String getDisplayUrl() {
			return doc.getDisplayUrl();
		}

		@Override
		public HashSet<GrammaticalConstruction> getConstructions() {
			return doc.getConstructions();
		}

		@Override
		public boolean hasConstruction(GrammaticalConstruction gram) {
			return doc.hasConstruction(gram);
		}

		@Override
		public double getConstructionFreq(GrammaticalConstruction gram) {
			return doc.getConstructionFreq(gram);
		}

		@Override
		public double getConstructionRelFreq(GrammaticalConstruction gram) {
			return doc.getConstructionRelFreq(gram);
		}

		@Override
		public ArrayList<? extends ConstructionRange> getConstructionOccurrences(GrammaticalConstruction gram) {
			return doc.getConstructionOccurrences(gram);
		}

		@Override
		public double getKeywordCount() {
			return doc.getKeywordCount();
		}

		@Override
		public double getKeywordRelFreq() {
			return doc.getKeywordRelFreq();
		}

		@Override
		public ArrayList<? extends KeywordRange> getKeywordOccurrences() {
			// keyword weighting will be skewed if the comparison docs don't share the same keyword list
			// we'll support it regardless
			return doc.getKeywordOccurrences();
		}

		@Override
		public int getRawTextLength() {
			return doc.getRawTextLength();
		}

		@Override
		public double getNumWords() {
			return doc.getNumWords();
		}

		@Override
		public double getNumSentences() {
			return doc.getNumSentences();
		}

		@Override
		public double getNumDependencies() {
			return doc.getNumDependencies();
		}

		@Override
		public DocumentReadabilityLevel getReadabilityLevel() {
			return doc.getReadabilityLevel();
		}

		@Override
		public double getReadablilityScore() {
			return doc.getReadablilityScore();
		}
		@Override
		public String getFileExtension() {
			return doc.getFileExtension();
		}
		
		@Override
		public void setFileExtension(String fileExtension) {
			doc.setFileExtension(fileExtension);
		}
	}

	CompareProcessData(Language l, List<RankableDocument> sel) {
		super(AbstractWebRankerCore.OperationType.COMPARE, l);
		complete = true;        // comparison ops are never transient

		// fixup the default ranks
		int i = 1;
		for (RankableDocument itr : sel) {
			ComparisonWrapper wrap = new ComparisonWrapper(itr);
			wrap.setRank(i);
			parsedDocs.add(wrap);
			i++;
		}
	}

	@Override
	public String getName() {
		return "";
	}
}
