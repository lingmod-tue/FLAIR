package com.flair.server.sentencesel;

import com.flair.server.document.AbstractDocument;
import com.flair.shared.grammar.Language;

import java.util.ArrayList;
import java.util.List;

class SentenceSelectorParams {
	Language lang;
	SentenceSelector.SimilarityMeasure similarityMeasure;
	SentenceSelector.Source source;
	SentenceSelector.Granularity granularity;
	boolean stemWords;
	boolean ignoreStopwords;
	boolean useSynsets;

	AbstractDocument main;
	List<AbstractDocument> corpus;

	SentenceSelectorPreprocessor preprocessor;

	SentenceSelectorParams() {
		lang = null;
		similarityMeasure = SentenceSelector.SimilarityMeasure.COSINE;
		source = SentenceSelector.Source.DOCUMENT;
		granularity = SentenceSelector.Granularity.SENTENCE;
		stemWords = ignoreStopwords = useSynsets = false;
		main = null;
		corpus = new ArrayList<>();
		preprocessor = SentenceSelectorPreprocessor.defaultInstance();
	}

	SentenceSelectorParams copy() {
		SentenceSelectorParams out = new SentenceSelectorParams();
		out.lang = lang;
		out.similarityMeasure = similarityMeasure;
		out.source = source;
		out.granularity = granularity;
		out.stemWords = stemWords;
		out.ignoreStopwords = ignoreStopwords;
		out.useSynsets = useSynsets;
		out.main = main;
		out.corpus = new ArrayList<>(corpus);
		out.preprocessor = preprocessor;
		return out;
	}

	void validate() {
		switch (source) {
		case DOCUMENT: {
			if (main == null)
				throw new IllegalStateException("Primary document source missing");
			else if (granularity == SentenceSelector.Granularity.DOCUMENT && corpus.size() < 2)
				throw new IllegalStateException("Corpus numTerms is too small for document-level granularity");

			lang = main.getLanguage();
			for (AbstractDocument itr : corpus) {
				if (main == itr)
					throw new IllegalStateException("Primary document also found in optional corpus");
				else if (lang != itr.getLanguage())
					throw new IllegalStateException("Documents are not of the same language. Expected " + lang + "but found " + itr.getLanguage());
			}

			break;
		}
		case CORPUS: {
			if (main != null)
				throw new IllegalStateException("Cannot use a primary document source when selecting from a corpus");
			else if (corpus.isEmpty())
				throw new IllegalStateException("No documents found in corpus");

			lang = corpus.get(0).getLanguage();
			for (AbstractDocument doc : corpus) {
				if (lang != doc.getLanguage())
					throw new IllegalStateException("Documents are not of the same language. Expected " + lang + "but found " + doc.getLanguage());
			}

			break;
		}
		}
	}

}
