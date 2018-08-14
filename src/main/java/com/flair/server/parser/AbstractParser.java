package com.flair.server.parser;

import com.flair.shared.grammar.Language;

import java.util.Properties;

/*
 * Represent a NLP parser that operates on a particular parsing strategy
 */
public interface AbstractParser<P extends AbstractParser<?, ?>,
		S extends AbstractParsingStrategy<?, P, ?, ?>> {
	interface Factory<P extends AbstractParser> {
		P create(Language lang, Properties properties);
	}

	interface Input {
		Language language();
	}

	interface Output {
		boolean valid();
	}

	ParserKind type();
	Language language();
	void parse(S strategy);
}
