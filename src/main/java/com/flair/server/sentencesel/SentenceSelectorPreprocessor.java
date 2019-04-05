package com.flair.server.sentencesel;

import com.flair.server.document.AbstractDocument;
import com.flair.server.parser.ParserAnnotations;
import com.flair.server.utilities.ServerLogger;
import com.flair.server.utilities.dictionary.SynSetDictionary;
import com.flair.server.utilities.dictionary.WordNetDictionary;
import com.flair.shared.grammar.Language;

import java.util.*;

/*
 * Performs various preprocessing tasks for sentence selectors
 */
final class SentenceSelectorPreprocessor {
	private static final SentenceSelectorPreprocessor DEFAULT_INSTANCE = new SentenceSelectorPreprocessor();
	public static SentenceSelectorPreprocessor defaultInstance() {
		return DEFAULT_INSTANCE;
	}

	private static final class LanguageSpecificData {
		final SynSetDictionary synsetDict;

		private LanguageSpecificData(SynSetDictionary synsetDict) {
			this.synsetDict = synsetDict;
		}
	}

	private Map<Language, LanguageSpecificData> languageSpecificData;

	private SentenceSelectorPreprocessor() {
		languageSpecificData = new EnumMap<>(Language.class);

		LanguageSpecificData eng = new LanguageSpecificData(WordNetDictionary.defaultInstance());
		languageSpecificData.put(Language.ENGLISH, eng);

		// ensure immutablity
		languageSpecificData = Collections.unmodifiableMap(languageSpecificData);
	}

	private boolean isLanguageSupported(Language lang) {
		return languageSpecificData.get(lang) != null;
	}

	List<PreprocessedSentence> preprocess(AbstractDocument doc, SentenceSelectorParams params) {
		List<PreprocessedSentence> out = new ArrayList<>();

		if (!doc.isParsed())
			throw new IllegalArgumentException("Couldn't preprocess unparsed document");
		else if (!isLanguageSupported(doc.getLanguage()))
			throw new IllegalArgumentException("Couldn't preprocess for language " + doc.getLanguage());
		else if (!isLanguageSupported(params.lang))
			throw new IllegalArgumentException("Couldn't preprocess for language " + params.lang);
		else if (doc.getLanguage() != params.lang)
			throw new IllegalArgumentException("Mismatching languages for preprocessor");

		try {
			LanguageSpecificData data = languageSpecificData.get(doc.getLanguage());
			int i = 0;
			for (ParserAnnotations.Sentence sent : doc.getParserAnnotations().sentences()) {
				++i;
				if (sent.tokenCount() > Constants.SELECTOR_MAX_TOKEN_COUNT)
					continue;

				PreprocessedSentence preprocSent = new PreprocessedSentence(doc, sent, i);
				for (ParserAnnotations.Token token : sent.tokens()) {
					String lemma = token.lemma().replaceAll("\\p{P}", "").trim().toLowerCase();
					String word = token.word().replaceAll("\\p{P}", "").trim().toLowerCase();

					if (word.isEmpty())
						continue;

					if (params.ignoreStopwords && token.isStopword())
						continue;

					boolean synsetFound = false;
					if (params.useSynsets && !lemma.isEmpty()) {
						List<? extends SynSetDictionary.SynSet> concepts = data.synsetDict.lookup(lemma, token.pos());
						concepts.forEach(e -> preprocSent.tokens.add(new PreprocessedSentence.Token(e)));
						synsetFound = !concepts.isEmpty();
					}

					if (!synsetFound) {
						if (params.stemWords && !lemma.isEmpty())
							preprocSent.tokens.add(new PreprocessedSentence.Token(lemma));
						else
							preprocSent.tokens.add(new PreprocessedSentence.Token(word));
					}
				}

				if (preprocSent.tokens.size() >= Constants.SELECTOR_MIN_UNIQUE_TOKEN_COUNT)
					out.add(preprocSent);

			}
		} catch (Throwable e) {
			ServerLogger.get().error(e, "Error while preprocessing document " + doc.toString());
		}

		return out;
	}

	static final class PreprocessedSentence {
		// a unique, atomic unit of a sentence
		static final class Token {
			final String word;
			final SynSetDictionary.SynSet concept;

			Token(String word) {
				this.word = word;
				this.concept = null;
			}

			Token(SynSetDictionary.SynSet concept) {
				this.word = null;
				this.concept = concept;
			}

			@Override
			public boolean equals(Object o) {
				if (this == o) return true;
				if (o == null || getClass() != o.getClass()) return false;

				Token token = (Token) o;

				if (word != null ? !word.equals(token.word) : token.word != null) return false;
				return concept != null ? concept == token.concept : token.concept == null;
			}
			@Override
			public int hashCode() {
				int result = word != null ? word.hashCode() : 0;
				result = 31 * result + (concept != null ? concept.hashCode() : 0);
				return result;
			}
		}

		final AbstractDocument sourceDoc;
		final ParserAnnotations.Sentence annotation;
		final int id;               // sentence number as found in the source
		final Set<Token> tokens;    // unique tokens only

		PreprocessedSentence(AbstractDocument sourceDoc, ParserAnnotations.Sentence annotation, int id) {
			this.sourceDoc = sourceDoc;
			this.annotation = annotation;
			this.id = id;
			this.tokens = new HashSet<>();
		}
	}
}
