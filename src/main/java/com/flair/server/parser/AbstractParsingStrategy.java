
package com.flair.server.parser;

/**
 * Encapsulates a triple of some input, logic to parse it and generate some output given a particular parser
 */
public interface AbstractParsingStrategy<S extends AbstractParsingStrategy<?, ?, ?, ?>,
		P extends AbstractParser<?, ?>,
		I extends AbstractParser.Input,
		O extends AbstractParser.Output> {
	interface Factory<S extends AbstractParsingStrategy, I> {
		S create(I input);
	}

	I input();
	O output();
	void apply(P parser);
}