package com.flair.server.parser;

import java.util.Collection;

/*
 * Parser-specific metadata with some helper methods
 */
public interface ParserAnnotations {
	interface Token {
		String word();
		String lemma();             // empty string if there is no lemma
		String lemmaOrWord();       // lemma if present, word otherwise
		String pos();
		boolean isStopword();
		<T extends ParserAnnotations.Token> T data(Class<T> typeClass);
	}

	interface Sentence {
		int index();
		int start();
		int end();
		String text();
		Collection<? extends Token> tokens();
		int tokenCount();
		<T extends ParserAnnotations.Sentence> T data(Class<T> typeClass);
	}

	Collection<? extends Sentence> sentences();

	ParserKind type();
	<T extends ParserAnnotations> T data(Class<T> typeClass);
}
