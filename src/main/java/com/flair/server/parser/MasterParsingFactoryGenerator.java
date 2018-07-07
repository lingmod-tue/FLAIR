/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.

 */
package com.flair.server.parser;

import com.flair.shared.grammar.Language;

/**
 * Omnibus factory generator for component classes
 *
 * @author shadeMe
 */
public final class MasterParsingFactoryGenerator {
	private MasterParsingFactoryGenerator() {}

	public static AbstractDocumentParserFactory createParser(ParserType type, Language lang) {
		switch (type) {
		case STANFORD_CORENLP:
			return new StanfordDocumentParserFactory(new DocumentFactory(), lang);
		default:
			throw new IllegalArgumentException("Couldn't create parser of type " + type);
		}
	}

	public static AbstractParsingStrategyFactory createParsingStrategy(ParserType type, Language lang) {
		switch (type) {
		case STANFORD_CORENLP:
			return new StanfordDocumentParserStrategyFactory(lang);
		default:
			throw new IllegalArgumentException("Couldn't create parsing strategy of type " + type);
		}
	}

	public static AbstractDocumentKeywordSearcherFactory createKeywordSearcher(KeywordSearcherType type) {
		switch (type) {
		case NAIVE_SUBSTRING:
			return new NaiveSubstringKeywordSearcherFactory();
		default:
			throw new IllegalArgumentException("Couldn't create keyword searcher of type " + type);
		}
	}
}
