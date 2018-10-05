package com.flair.server.pipelines.questgen;

import com.flair.server.document.AbstractDocument;
import com.flair.shared.grammar.Language;

import java.util.ArrayList;
import java.util.List;

class SentenceSelectorParams {
	Language lang;
	SentenceSelector.Source source;
	SentenceSelector.Granularity granularity;
	boolean stemWords;
	boolean ignoreStopwords;
	boolean useSynsets;

	AbstractDocument main;
	List<AbstractDocument> corpus;

	SentenceSelectorPreprocessor preprocessor;

	SentenceSelectorParams(Language l, SentenceSelectorPreprocessor p) {
		lang = l;
		source = SentenceSelector.Source.DOCUMENT;
		granularity = SentenceSelector.Granularity.SENTENCE;
		stemWords = ignoreStopwords = useSynsets = false;
		main = null;
		corpus = new ArrayList<>();
		preprocessor = p;
	}

	void validate() {
		switch (source) {
		case DOCUMENT: {
			if (main == null)
				throw new IllegalStateException("Primary document source missing");
			else if (granularity == SentenceSelector.Granularity.DOCUMENT && corpus.size() < 2)
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
