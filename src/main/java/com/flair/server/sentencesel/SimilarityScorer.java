package com.flair.server.sentencesel;

import com.flair.server.utilities.InvertedIndex;
import com.flair.server.utilities.SparseDoubleVector;

import java.util.Collection;
import java.util.Set;

class SimilarityScorer {
	interface HasVector {
		SparseDoubleVector vec();
	}

	interface HasInvertedIndexTerms {
		Collection<InvIdxTerm> terms();
	}

	private final InvertedIndex<InvIdxTerm, InvIdxDocument> invertedIndex;

	SimilarityScorer(InvertedIndex<InvIdxTerm, InvIdxDocument> invertedIndex) {
		this.invertedIndex = invertedIndex;
	}

	SimilarityScorer() {
		this.invertedIndex = null;
	}

	double cosine(HasVector vec1, HasVector vec2) {
		if (!vec1.vec().isNormalized() || !vec2.vec().isNormalized())
			throw new IllegalArgumentException("Sentence vectors need to be normalized");

		return vec1.vec().dot(vec2.vec());
	}

	double bm25(HasInvertedIndexTerms doc1, HasInvertedIndexTerms doc2) {
		if (invertedIndex == null)
			throw new IllegalStateException("Inverted index not initialized for BM25 calculation");

		double score = 0;
		int numDocs = invertedIndex.numDocuments();
		double avgDocLen = invertedIndex.avgDocLength(false);
		Collection<InvIdxTerm> terms1 = doc1.terms();
		Collection<InvIdxTerm> terms2 = doc2.terms();

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

	double jaccardCoefficient(Set<?> set1, Set<?> set2) {
		if (set1.size() == 0 && set2.size() == 0)
			return 1;

		Set<?> larger = set1.size() > set2.size() ? set1 : set2;
		Set<?> smaller = larger == set1 ? set2 : set1;

		long intersection = smaller.stream().filter(larger::contains).count();
		long union = larger.size() + smaller.size() - intersection;

		return intersection / (double) union;
	}
}
