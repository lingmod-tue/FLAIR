package com.flair.server.questgen.selection;

import com.flair.server.document.AbstractDocument;
import com.flair.shared.grammar.Language;

import java.util.ArrayList;
import java.util.List;

class DocumentSentenceSelectorParams {
	Language lang;
	DocumentSentenceSelector.Source source;
	DocumentSentenceSelector.Granularity granularity;
	boolean stemWords;
	boolean ignoreStopwords;
	boolean useSynsets;

	AbstractDocument main;
	List<AbstractDocument> corpus;

	DocumentSentenceSelectorParams(Language l) {
		lang = l;
		source = DocumentSentenceSelector.Source.DOCUMENT;
		granularity = DocumentSentenceSelector.Granularity.SENTENCE;
		stemWords = ignoreStopwords = useSynsets = false;
		main = null;
		corpus = new ArrayList<>();
	}

	void validate() {
		switch (source) {
		case DOCUMENT: {
			if (main == null)
				throw new IllegalStateException("Primary document source missing");
			else if (granularity == DocumentSentenceSelector.Granularity.DOCUMENT && corpus.size() < 2)
				throw new IllegalStateException("Corpus size is too small for document-level granularity");

			for (AbstractDocument itr : corpus) {
				if (main == itr)
					throw new IllegalStateException("Primary document also found in optional corpus");
			}
			break;
		}
		case CORPUS: {
			if (main != null)
				throw new IllegalStateException("Cannot use a primary document source when selecting from a corpus");
			else if (corpus.isEmpty())
				throw new IllegalStateException("No documents found in corpus");
			break;
		}
		}
	}

}
