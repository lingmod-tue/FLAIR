package com.flair.server.questgen.selection;

import com.flair.server.document.AbstractDocument;
import com.flair.server.utilities.TextSegment;

/*
 * Selects sentences from one or more documents based on some criteria
 */
public interface DocumentSentenceSelector {
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

		DocumentSentenceSelector build();
	}

	interface SelectedSentence {
		TextSegment getSpan();
		AbstractDocument getSource();
		default String getText() {
			return getSource().getSpanText(getSpan());
		}
		double getScore();
	}

	Iterable<? extends SelectedSentence> topK(int k);    // returns a ranked list of the top-k sentence spans
}

