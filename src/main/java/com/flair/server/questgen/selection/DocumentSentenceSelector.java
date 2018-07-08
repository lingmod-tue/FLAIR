package com.flair.server.questgen.selection;

import com.flair.server.parser.AbstractDocument;
import com.flair.server.parser.TextSegment;

/*
 * Selects sentences from a document based on some criteria
 */
public interface DocumentSentenceSelector {
	interface Builder {
		Builder stemWords(boolean val);
		Builder ignoreStopwords(boolean val);
		Builder addDocument(AbstractDocument doc);
		DocumentSentenceSelector build();
	}

	interface SelectedSentence {
		TextSegment getSpan();
		AbstractDocument getSource();
		default String getText() {
			return getSource().getText().substring(getSpan().getStart(), getSpan().getEnd());
		}
	}

	Iterable<? extends SelectedSentence> topK(int k);    // returns a ranked list of the top-k sentence spans
}

