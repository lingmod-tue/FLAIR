package com.flair.server.questgen.selection;

import com.flair.server.document.AbstractDocument;
import com.flair.server.parser.ParserAnnotations;
import com.flair.server.utilities.ServerLogger;
import com.flair.server.utilities.SynSetDictionary;
import com.flair.server.utilities.TextSegment;
import com.flair.server.utilities.WordNetDictionary;
import com.flair.shared.grammar.Language;

import java.util.*;

/*
 * Performs various preprocessing tasks for sentence selectors
 */
final class Preprocessor {
	private static final int MIN_UNIQUE_TOKEN_COUNT = 5;

	private static final class LanguageSpecificData {
		final SynSetDictionary synsetDict;

		private LanguageSpecificData(SynSetDictionary synsetDict) {
			this.synsetDict = synsetDict;
		}
	}

	private static Map<Language, LanguageSpecificData> LANGUAGE_SPECIFIC;

	static {
		LANGUAGE_SPECIFIC = new EnumMap<>(Language.class);

		LanguageSpecificData eng = new LanguageSpecificData(WordNetDictionary.defaultInstance());
		LANGUAGE_SPECIFIC.put(Language.ENGLISH, eng);

		// ensure immutablity
		LANGUAGE_SPECIFIC = Collections.unmodifiableMap(LANGUAGE_SPECIFIC);
	}

	private static boolean isLanguageSupported(Language lang) {
		return LANGUAGE_SPECIFIC.get(lang) != null;
	}

	static List<PreprocessedSentence> preprocess(AbstractDocument doc, DocumentSentenceSelectorParams params) {
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
			LanguageSpecificData data = LANGUAGE_SPECIFIC.get(doc.getLanguage());
			int i = 0;
			for (ParserAnnotations.Sentence sent : doc.getParserAnnotations().sentences()) {
				++i;
				PreprocessedSentence preprocSent = new PreprocessedSentence(doc, new TextSegment(sent.start(), sent.end()), i);

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

				if (preprocSent.tokens.size() >= MIN_UNIQUE_TOKEN_COUNT)
					out.add(preprocSent);

			}
		} catch (Throwable e) {
			ServerLogger.get().error(e, "Error while preprocessing document " + doc.toString());
		}

		return out;
	}
}
