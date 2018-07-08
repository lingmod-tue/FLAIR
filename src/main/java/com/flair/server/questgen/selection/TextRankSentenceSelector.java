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
import java.util.List;
import java.util.stream.Collectors;

/*
 * Implements the TextRank algorithm to select salient sentences
 */
public class TextRankSentenceSelector implements DocumentSentenceSelector {
	private static final int MAX_ITERATIONS = 10000;

	static final class Node {
		final Preprocessor.PreprocessedSentence source;
		SparseDoubleVector vector;

		Node(Preprocessor.PreprocessedSentence s, SparseDoubleVector v) {
			source = s;
			vector = v;
		}
	}

	static final class RankedSentence implements SelectedSentence {
		final Preprocessor.PreprocessedSentence sent;
		final double score;

		RankedSentence(Preprocessor.PreprocessedSentence sent, double score) {
			this.sent = sent;
			this.score = score;
		}

		@Override
		public TextSegment getSpan() {
			return sent.sourceSentence;
		}
		@Override
		public AbstractDocument getSource() {
			return sent.source;
		}
	}

	private final InvertedIndex index;
	private final Graph<Node, DefaultWeightedEdge> graph;
	private List<RankedSentence> rankedOutput;

	private void init(DocumentSentenceSelectorParams params) {
		List<Preprocessor.PreprocessedSentence> allSents = new ArrayList<>();

		// add terms to the index
		for (AbstractDocument doc : params.docs) {
			List<Preprocessor.PreprocessedSentence> docSents = Preprocessor.INSTANCE.get().preprocessDocument(doc, params);
			docSents.forEach(sent -> {
				sent.tokens.forEach(tok -> index.addTerm(tok, sent.source));
			});
			allSents.addAll(docSents);
		}

		// create tf-idf sentence vectors
		List<Node> nodes = allSents.stream().map(sent -> {
			Node newNode = new Node(sent, new SparseDoubleVector(index.size()));
			sent.tokens.forEach(tok -> {
				newNode.vector.set(index.getTermId(tok),
						index.getTermTfIdf(tok, sent.source, false));
			});
			return newNode;
		}).collect(Collectors.toList());

		// generate sentence graph
		for (List<Node> pair : new Combinator<>(nodes, 2)) {
			Node first = pair.get(0), second = pair.get(1);
			if (!graph.addVertex(first)) {
				ServerLogger.get().warn("Couldn't add sentence " + first.source.sentId +
						" in document " + first.source.source.toString() + " to TextRank graph");
			}

			if (!graph.addVertex(second)) {
				ServerLogger.get().warn("Couldn't add sentence " + second.source.sentId +
						" in document " + second.source.source.toString() + " to TextRank graph");
			}

			DefaultWeightedEdge edge = graph.addEdge(first, second);
			if (edge == null) {
				ServerLogger.get().warn("Couldn't add edge between sentences " + first.source.sentId +
						" and " + second.source.sentId + " in document " + first.source.source.toString() + " to TextRank graph");
			}

			double cosineDist = 1 - (first.vector.dot(second.vector) / first.vector.magnitude() * second.vector.magnitude());
			graph.setEdgeWeight(edge, cosineDist);
		}

		// execute PageRank and rank results
		PageRank<Node, DefaultWeightedEdge> pageRank = new PageRank<>(graph, PageRank.DAMPING_FACTOR_DEFAULT, MAX_ITERATIONS);
		pageRank.getScores().forEach((n, s) -> rankedOutput.add(new RankedSentence(n.source, s)));
		rankedOutput.sort((a, b) -> Double.compare(a.score, b.score));
	}

	private TextRankSentenceSelector(DocumentSentenceSelectorParams p) {
		index = new InvertedIndex();
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
		public DocumentSentenceSelector.Builder addDocument(AbstractDocument doc) {
			params.docs.add(doc);
			return this;
		}
		@Override
		public DocumentSentenceSelector build() {
			return new TextRankSentenceSelector(params);
		}
	}
}

