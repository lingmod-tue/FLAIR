package com.flair.server.pipelines.questgen;

import com.flair.server.document.AbstractDocument;
import com.flair.server.parser.ParserAnnotations;
import com.flair.server.utilities.ServerLogger;
import com.flair.server.utilities.SparseDoubleVector;
import com.flair.shared.grammar.Language;
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
public class TextRankSentenceSelector implements SentenceSelector {
	// represents the concept of a document wrt the inverted index
	private static final class BaseDocument {
		final SentenceSelectorPreprocessor.PreprocessedSentence sent;
		final AbstractDocument doc;

		BaseDocument(SentenceSelectorPreprocessor.PreprocessedSentence sent) {
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
			int result = sent != null ? sent.hashCode() : 0;
			result = 31 * result + (doc != null ? doc.hashCode() : 0);
			return result;
		}
	}

	private static final class Node {
		final SentenceSelectorPreprocessor.PreprocessedSentence source;
		final SparseDoubleVector vector;

		Node(SentenceSelectorPreprocessor.PreprocessedSentence s, SparseDoubleVector v) {
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

	private final InvertedIndex<SentenceSelectorPreprocessor.PreprocessedSentence.Token, BaseDocument> index;
	private final Graph<Node, DefaultWeightedEdge> graph;
	private final List<RankedSentence> rankedOutput;

	private BaseDocument getBaseDocument(SentenceSelectorPreprocessor.PreprocessedSentence sent, SentenceSelectorParams params) {
		switch (params.granularity) {
		case SENTENCE:
			return new BaseDocument(sent);
		case DOCUMENT:
			return new BaseDocument(sent.sourceDoc);
		}

		return null;
	}

	private void init(SentenceSelectorParams params) {
		List<SentenceSelectorPreprocessor.PreprocessedSentence> allSents = new ArrayList<>();

		// add terms to the index
		if (params.main != null)
			params.corpus.add(params.main);

		for (AbstractDocument doc : params.corpus) {
			List<SentenceSelectorPreprocessor.PreprocessedSentence> docSents = params.preprocessor.preprocess(doc, params);
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
				ServerLogger.get().trace("Invalid cosine similarity between sentences " + first.source.id +
						" and " + second.source.id + " in document " + first.source.sourceDoc.getDescription());
				continue;
			} else if (cosineSimilarity == 0)
				continue;

			graph.addVertex(first);
			graph.addVertex(second);

			DefaultWeightedEdge edge = graph.addEdge(first, second);
			if (edge == null) {
				ServerLogger.get().warn("Couldn't add edge between sentences: Sentence " + first.source.id + " in " + first.source.sourceDoc.getDescription()
						+ " and Sentence " + second.source.id + " in " + second.source.sourceDoc.getDescription());
			} else
				graph.setEdgeWeight(edge, cosineSimilarity);
		}

		// execute PageRank and rank results
		PageRank<Node, DefaultWeightedEdge> pageRank = new PageRank<>(graph);
		pageRank.getScores().forEach((n, s) -> rankedOutput.add(new RankedSentence(n.source, s)));
		rankedOutput.sort(Comparator.comparingDouble(a -> -a.score));
	}

	private TextRankSentenceSelector(SentenceSelectorParams p) {
		index = new InvertedIndex<>();
		graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		rankedOutput = new ArrayList<>();

		init(p);
	}

	@Override
	public Collection<? extends SelectedSentence> topK(int k) {
		return rankedOutput.subList(0, rankedOutput.size() < k ? rankedOutput.size() : k);
	}


	static class Builder implements SentenceSelector.Builder {
		static SentenceSelector.Builder factory(Language l, SentenceSelectorPreprocessor p) {
			return new Builder(l, p);
		}

		SentenceSelectorParams params;

		private Builder(Language l, SentenceSelectorPreprocessor p) {
			params = new SentenceSelectorParams(l, p);
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
		public SentenceSelector.Builder copusDocument(AbstractDocument doc) {
			params.corpus.add(doc);
			return this;
		}
		@Override
		public SentenceSelector build() {
			params.validate();
			return new TextRankSentenceSelector(params);
		}
	}
}

