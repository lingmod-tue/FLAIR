package com.flair.server.parser;

/*
 * Wrapper interface around a thread-safe implementation of a potentially not-threads-safe parser
 */
public interface ThreadSafeParser<P extends AbstractParser<?, ?>,
		S extends AbstractParsingStrategy<?, P, ?, ?>>
		extends AbstractParser<P, S> {}
