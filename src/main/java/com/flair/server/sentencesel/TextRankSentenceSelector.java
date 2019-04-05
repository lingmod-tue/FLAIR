package com.flair.server.sentencesel;

import com.flair.server.document.AbstractDocument;
import com.flair.server.parser.ParserAnnotations;
import com.flair.server.utilities.InvertedIndex;
import com.flair.server.utilities.ServerLogger;
import com.flair.server.utilities.SparseDoubleVector;
import com.github.dakusui.combinatoradix.Combinator;
import org.jgrapht.Graph;
import org.jgrapht.alg.scoring.PageRank;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Implements the TextRank algorithm to select salient sentences
 */
class TextRankSentenceSelector implements SentenceSelector {
	private static final class Node implements SentenceSimilarityScorer.HasVector, SentenceSimilarityScorer.HasTerms {
		final SentenceSelectorPreprocessor.PreprocessedSentence source;
		final SparseDoubleVector vector;

		Node(SentenceSelectorPreprocessor.PreprocessedSentence s, int vecDim) {
			source = s;
			vector = new SparseDoubleVector(vecDim);
		}

		Node(SentenceSelectorPreprocessor.PreprocessedSentence s) {
			source = s;
			vector = null;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Node node = (Node) o;
			return source == node.source;
		}
		@Override
		public SparseDoubleVector vec() {
			return vector;
		}
		@Override
		public Collection<InvIdxTerm> terms() {
			return source.terms;
		}
	}

	private static final class RankedSentence implements SelectedSentence {
		final SentenceSelectorPreprocessor.PreprocessedSentence sent;
		final double score;

		RankedSentence(SentenceSelectorPreprocessor.PreprocessedSentence sent, double score) {
			this.sent = sent;
			this.score = score;
		}

		@Override
		public double score() {
			return score;
		}
		@Override
		public ParserAnnotations.Sentence annotation() {
			return sent.annotation;
		}
	}

	private final SentenceSelectorParams params;
	private final InvertedIndex<InvIdxTerm, InvIdxDocument> invertedIndex;
	private final Graph<Node, DefaultWeightedEdge> graph;
	private final List<RankedSentence> rankedOutput;
	private boolean initialized;

	private InvIdxDocument getBaseDocument(SentenceSelectorPreprocessor.PreprocessedSentence sent, SentenceSelectorParams params) {
		switch (params.granularity) {
		case SENTENCE:
			return new InvIdxDocument(sent);
		case DOCUMENT:
			return new InvIdxDocument(sent.sourceDoc);
		}

		return null;
	}

	private void rank() {
		if (initialized)
			return;

		List<SentenceSelectorPreprocessor.PreprocessedSentence> allSents = new ArrayList<>();

		// add terms to the invertedIndex
		if (params.main != null)
			params.corpus.add(params.main);

		for (AbstractDocument doc : params.corpus) {
			Collection<SentenceSelectorPreprocessor.PreprocessedSentence> docSents = params.preprocessor.preprocess(doc, params);
			docSents.forEach(sent -> sent.terms.forEach(tok -> invertedIndex.addTerm(tok, getBaseDocument(sent, params))));

			switch (params.source) {
			case DOCUMENT:
				if (doc != params.main)
					break;
			case CORPUS:
				allSents.addAll(docSents);
				break;
			}
		}

		if (allSents.isEmpty()) {
			initialized = true;
			return;
		}

		// create vertices for the sentence graph
		List<Node> nodes = allSents.stream().map(sent -> {
			Node newNode;
			switch (params.similarityMeasure) {
			case COSINE:
				newNode = new Node(sent, invertedIndex.numTerms());
				sent.terms.forEach(tok -> newNode.vector.set(invertedIndex.termId(tok), invertedIndex.termTfIdf(tok, getBaseDocument(sent, params), true)));
				newNode.vector.normalize();
				break;
			case BM25:
			default:
				newNode = new Node(sent);
				break;
			}
			return newNode;
		}).collect(Collectors.toList());

		// generate sentence graph
		SentenceSimilarityScorer scorer = new SentenceSimilarityScorer(invertedIndex);
		for (List<Node> pair : new Combinator<>(nodes, 2)) {
			Node first = pair.get(0), second = pair.get(1);
			double similarityScore = 0;
			switch (params.similarityMeasure) {
			case COSINE:
				similarityScore = scorer.cosine(first, second);
				break;
			case BM25:
				similarityScore = scorer.bm25(first, second);
				break;
			}

			if (!Double.isFinite(similarityScore)) {
				ServerLogger.get().trace("Invalid similarity score between sentences " + first.source.id +
						" and " + second.source.id + " in document " + first.source.sourceDoc.getDescription());
				continue;
			} else if (similarityScore == 0)
				continue;

			graph.addVertex(first);
			graph.addVertex(second);

			DefaultWeightedEdge edge = graph.addEdge(first, second);
			if (edge == null) {
				ServerLogger.get().warn("Couldn't add edge between sentences: Sentence " + first.source.id + " in " + first.source.sourceDoc.getDescription()
						+ " and Sentence " + second.source.id + " in " + second.source.sourceDoc.getDescription());
			} else
				graph.setEdgeWeight(edge, similarityScore);
		}

		// execute PageRank and rank results
		PageRank<Node, DefaultWeightedEdge> pageRank = new PageRank<>(graph);
		pageRank.getScores().forEach((n, s) -> rankedOutput.add(new RankedSentence(n.source, s)));
		rankedOutput.sort(Comparator.comparingDouble(a -> -a.score));
		initialized = true;
	}

	private TextRankSentenceSelector(SentenceSelectorParams p) {
		params = p;
		invertedIndex = new InvertedIndex<>();
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		rankedOutput = new ArrayList<>();
		initialized = false;
	}

	@Override
	public Collection<? extends SelectedSentence> topK(int k) {
		rank();
		return rankedOutput.subList(0, rankedOutput.size() < k || k == -1 ? rankedOutput.size() : k);
	}


	static class Builder implements SentenceSelector.Builder {
		SentenceSelectorParams params;

		Builder() {
			params = new SentenceSelectorParams();
		}

		@Override
		public SentenceSelector.Builder similarityMeasure(SimilarityMeasure val) {
			params.similarityMeasure = val;
			return this;
		}
		@Override
		public SentenceSelector.Builder stemWords(boolean val) {
			params.stemWords = val;
			return this;
		}
		@Override
		public SentenceSelector.Builder ignoreStopwords(boolean val) {
			params.ignoreStopwords = val;
			return this;
		}
		@Override
		public SentenceSelector.Builder useSynsets(boolean val) {
			params.useSynsets = val;
			return this;
		}
		@Override
		public SentenceSelector.Builder granularity(Granularity val) {
			params.granularity = val;
			return this;
		}
		@Override
		public SentenceSelector.Builder source(Source val) {
			params.source = val;
			return this;
		}
		@Override
		public SentenceSelector.Builder mainDocument(AbstractDocument doc) {
			params.main = doc;
			return this;
		}
		@Override
		public SentenceSelector.Builder corpusDocument(AbstractDocument doc) {
			params.corpus.add(doc);
			return this;
		}
		@Override
		public SentenceSelector build() {
			params.validate();
			return new TextRankSentenceSelector(params.copy());
		}
	}
}

