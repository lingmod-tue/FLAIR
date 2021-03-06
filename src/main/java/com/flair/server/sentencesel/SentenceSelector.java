package com.flair.server.sentencesel;

import com.flair.server.document.AbstractDocument;
import com.flair.server.parser.ParserAnnotations;

import java.util.List;

/*
 * Selects sentences from one or more documents based on some criteria
 */
public interface SentenceSelector {
	enum Granularity {
		SENTENCE,      // (default) the model is trained exclusively on individual sentences
		DOCUMENT       // the model is trained on both sentences and the documents they occur in
	}

	enum Source {
		DOCUMENT,     // (default) the final sentences are selected from a specific document
		CORPUS,       // the final sentences are selected from the entire corpus of documents
	}

	enum SimilarityMeasure {
		COSINE,       // (default)
		BM25,
		JACCARD_COEFFICIENT
	}

	// all boolean parameters default to false
	interface Builder {
		Builder similarityMeasure(SimilarityMeasure val);
		Builder stemWords(boolean val);
		Builder ignoreStopwords(boolean val);
		Builder useSynsets(boolean val);
		Builder dropDuplicates(boolean val);                    // filter out lower-ranked sentences that are similar to higher-ranked ones
		Builder duplicateCooccurrenceThreshold(double val);     // lower-ranked sentences with a term-cooccurrence ratio larger than this value are dropped if 'dropDuplicates' is true
		// default value: 0.6
		Builder granularity(Granularity val);
		Builder source(Source val);

		Builder mainDocument(AbstractDocument doc);      // document from which sentences are selected
		Builder corpusDocument(AbstractDocument doc);    // additional corpus that can be used the selector

		SentenceSelector build();
	}

	interface SelectedSentence {
		double score();
		ParserAnnotations.Sentence annotation();
	}

	List<? extends SelectedSentence> topK(int k);    // returns a ranked list of the top-k sentences. returns all sentences if k == -1
}

