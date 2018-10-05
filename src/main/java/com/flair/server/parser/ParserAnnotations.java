package com.flair.server.parser;

/*
 * Parser-specific metadata with some helper methods
 */
public interface ParserAnnotations {
	interface Token {
		String word();
		String lemma();  // empty string if there is no lemma
		String pos();
		boolean isStopword();
		<T extends ParserAnnotations.Token> T data(Class<T> typeClass);
	}

	interface Sentence {
		int start();
		int end();
		String text();
		Iterable<? extends Token> tokens();
		int tokenCount();
		<T extends ParserAnnotations.Sentence> T data(Class<T> typeClass);
	}

	Iterable<? extends Sentence> sentences();

	ParserKind type();
	<T extends ParserAnnotations> T data(Class<T> typeClass);
}
