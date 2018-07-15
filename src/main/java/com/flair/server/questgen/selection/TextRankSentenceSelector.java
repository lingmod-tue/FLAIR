package com.flair.server.questgen.selection;

import com.flair.server.parser.AbstractDocument;
import com.flair.server.parser.TextSegment;
import com.flair.server.utilities.ServerLogger;
import com.flair.server.utilities.SparseDoubleVector;
import com.flair.shared.grammar.Language;
import com.github.dakusui.combinatoradix.Combinator;
import org.jgrapht.Graph;
import org.jgrapht.alg.scoring.PageRank;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/*
 * Implements the TextRank algorithm to select salient sentences
 */
public class TextRankSentenceSelector implements DocumentSentenceSelector {
	private static final int MAX_ITERATIONS = 1000;

	// represents the concept of a document wrt the inverted index
	private static final class BaseDocument {
		final PreprocessedSentence sent;
		final AbstractDocument doc;

		BaseDocument(PreprocessedSentence sent) {
			this.sent = sent;
			this.doc = null;
		}
		BaseDocument(AbstractDocument doc) {
			this.doc = doc;
			this.sent = null;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			BaseDocument that = (BaseDocument) o;
			return sent == that.sent && doc == that.doc;
		}
		@Override
		public int hashCode() {
			if (doc != null)
				return doc.hashCode();
			else if (sent != null)
				return sent.hashCode();
			else
				throw new IllegalStateException("Invalid BaseDocument!");
		}
	}

	private static final class Node {
		final PreprocessedSentence source;
		final SparseDoubleVector vector;

		Node(PreprocessedSentence s, SparseDoubleVector v) {
			source = s;
			vector = v;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Node node = (Node) o;
			return source == node.source;
		}
	}

	private static final class RankedSentence implements SelectedSentence {
		final PreprocessedSentence sent;
		final double score;

		RankedSentence(PreprocessedSentence sent, double score) {
			this.sent = sent;
			this.score = score;
		}

		@Override
		public TextSegment getSpan() {
			return sent.span;
		}
		@Override
		public AbstractDocument getSource() {
			return sent.source;
		}
		@Override
		public double getScore() {
			return score;
		}
	}

	private final InvertedIndex<BaseDocument> index;
	private final Graph<Node, DefaultWeightedEdge> graph;
	private final List<RankedSentence> rankedOutput;

	private BaseDocument getBaseDocument(PreprocessedSentence sent, DocumentSentenceSelectorParams params) {
		switch (params.granularity) {
		case SENTENCE:
			return new BaseDocument(sent);
		case DOCUMENT:
			return new BaseDocument(sent.source);
		}

		return null;
	}

	private void init(DocumentSentenceSelectorParams params) {
		List<PreprocessedSentence> allSents = new ArrayList<>();

		// add terms to the index
		if (params.main != null)
			params.corpus.add(params.main);

		for (AbstractDocument doc : params.corpus) {
			List<PreprocessedSentence> docSents = Preprocessor.preprocess(doc, params);
			docSents.forEach(sent -> sent.tokens.forEach(tok -> index.addTerm(tok, getBaseDocument(sent, params))));

			switch (params.source) {
			case DOCUMENT:
				if (doc != params.main)
					break;
			case CORPUS:
				allSents.addAll(docSents);
				break;
			}
		}

		// create tf-idf sentence vectors
		List<Node> nodes = allSents.stream().map(sent -> {
			Node newNode = new Node(sent, new SparseDoubleVector(index.size()));
			sent.tokens.forEach(tok -> newNode.vector.set(index.getTermId(tok), index.getTermTfIdf(tok, getBaseDocument(sent, params), false)));
			return newNode;
		}).collect(Collectors.toList());

		// generate sentence graph
		for (List<Node> pair : new Combinator<>(nodes, 2)) {
			Node first = pair.get(0), second = pair.get(1);
			double cosineSimilarity = first.vector.dot(second.vector) / (first.vector.magnitude() * second.vector.magnitude());
			if (!Double.isFinite(cosineSimilarity)) {
				ServerLogger.get().warn("Invalid cosine similarity between sentences " + first.source.id +
						" and " + second.source.id + " in document " + first.source.source.getDescription());
				continue;
			} else if (cosineSimilarity == 0)
				continue;

			graph.addVertex(first);
			graph.addVertex(second);

			DefaultWeightedEdge edge = graph.addEdge(first, second);
			if (edge == null) {
				ServerLogger.get().warn("Couldn't add edge between sentences: Sentence " + first.source.id + " in " + first.source.source.getDescription()
						+ " and Sentence " + second.source.id + " in " + second.source.source.getDescription());
			} else
				graph.setEdgeWeight(edge, cosineSimilarity);
		}

		// execute PageRank and rank results
		PageRank<Node, DefaultWeightedEdge> pageRank = new PageRank<>(graph, PageRank.DAMPING_FACTOR_DEFAULT, MAX_ITERATIONS);
		pageRank.getScores().forEach((n, s) -> rankedOutput.add(new RankedSentence(n.source, s)));
		rankedOutput.sort(Comparator.comparingDouble(a -> -a.score));
	}

	private TextRankSentenceSelector(DocumentSentenceSelectorParams p) {
		index = new InvertedIndex<>();
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		rankedOutput = new ArrayList<>();

		init(p);
	}

	@Override
	public Iterable<? extends SelectedSentence> topK(int k) {
		return rankedOutput.subList(0, rankedOutput.size() < k ? rankedOutput.size() : k);
	}


	static class Builder implements DocumentSentenceSelector.Builder {
		DocumentSentenceSelectorParams params;

		Builder(Language l) {
			params = new DocumentSentenceSelectorParams(l);
		}

		@Override
		public DocumentSentenceSelector.Builder stemWords(boolean val) {
			params.stemWords = val;
			return this;
		}
		@Override
		public DocumentSentenceSelector.Builder ignoreStopwords(boolean val) {
			params.ignoreStopwords = val;
			return this;
		}
		@Override
		public DocumentSentenceSelector.Builder useSynsets(boolean val) {
			params.useSynsets = val;
			return this;
		}
		@Override
		public DocumentSentenceSelector.Builder granularity(Granularity val) {
			params.granularity = val;
			return this;
		}
		@Override
		public DocumentSentenceSelector.Builder source(Source val) {
			params.source = val;
			return this;
		}
		@Override
		public DocumentSentenceSelector.Builder mainDocument(AbstractDocument doc) {
			params.main = doc;
			return this;
		}
		@Override
		public DocumentSentenceSelector.Builder copusDocument(AbstractDocument doc) {
			params.corpus.add(doc);
			return this;
		}
		@Override
		public DocumentSentenceSelector build() {
			params.validate();
			return new TextRankSentenceSelector(params);
		}
	}
}

