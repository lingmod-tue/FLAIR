package com.flair.server.sentencesel;

import com.flair.server.utilities.InvertedIndex;
import com.flair.server.utilities.SparseDoubleVector;

import java.util.Collection;

class SentenceSimilarityScorer {
	interface HasVector {
		SparseDoubleVector vec();
	}

	interface HasTerms {
		Collection<InvIdxTerm> terms();
	}

	private final InvertedIndex<InvIdxTerm, InvIdxDocument> invertedIndex;

	SentenceSimilarityScorer(InvertedIndex<InvIdxTerm, InvIdxDocument> invertedIndex) {
		this.invertedIndex = invertedIndex;
	}

	double cosine(HasVector sent1, HasVector sent2) {
		if (!sent1.vec().isNormalized() || !sent2.vec().isNormalized())
			throw new IllegalArgumentException("Sentence vectors need to be normalized");

		return sent1.vec().dot(sent2.vec());
	}

	double bm25(HasTerms sent1, HasTerms sent2) {
		double score = 0;
		int numDocs = invertedIndex.numDocuments();
		double avgDocLen = invertedIndex.avgDocLength(false);
		Collection<InvIdxTerm> terms1 = sent1.terms();
		Collection<InvIdxTerm> terms2 = sent2.terms();

		for (InvIdxTerm term : terms2) {
			double df = invertedIndex.termDocumentFrequency(term);
			double idf;
			if (df > numDocs / 2.)
				idf = Math.log(numDocs - df + 0.5) - Math.log(df + 0.5);
			else
				idf = Constants.SIMILARITY_BM25_PARAM_EPSILON * invertedIndex.avgIdf(false);
			long tf = terms1.stream().filter(t -> t.equals(term)).count();

			double multiplier = idf;
			multiplier *= tf * (Constants.SIMILARITY_BM25_PARAM_K + 1);
			multiplier /= (tf + Constants.SIMILARITY_BM25_PARAM_K * (1 - Constants.SIMILARITY_BM25_PARAM_B + (Constants.SIMILARITY_BM25_PARAM_B * terms1.size() / avgDocLen)));

			score += multiplier;
		}

		return score;
	}
}
