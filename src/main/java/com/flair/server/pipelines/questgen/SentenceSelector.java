package com.flair.server.pipelines.questgen;

import com.flair.server.document.AbstractDocument;
import com.flair.server.parser.ParserAnnotations;

import java.util.Collection;

/*
 * Selects sentences from one or more documents based on some criteria
 */
interface SentenceSelector {
	enum Granularity {
		SENTENCE,      // (default) the model is trained exclusively on individual sentences
		DOCUMENT       // the model is trained on both sentences and the documents they occur in
	}

	enum Source {
		DOCUMENT,     // (default) the final sentences are selected from a specific document
		CORPUS,       // the final sentences are selected from the entire corpus of documents
	}

	// all boolean parameters default to false
	interface Builder {
		Builder stemWords(boolean val);
		Builder ignoreStopwords(boolean val);
		Builder useSynsets(boolean val);
		Builder granularity(Granularity val);
		Builder source(Source val);

		Builder mainDocument(AbstractDocument doc);     // document from which sentences are selected
		Builder copusDocument(AbstractDocument doc);    // additional corpus that can be used the selector

		SentenceSelector build();
	}

	interface SelectedSentence {
		double score();
		ParserAnnotations.Sentence annotation();
	}

	Collection<? extends SelectedSentence> topK(int k);    // returns a ranked list of the top-k sentences. returns all sentences if k == -1
}

