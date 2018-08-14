package com.flair.server.parser;

public abstract class CoreNlpParsingStrategy<I extends AbstractParser.Input,
		O extends AbstractParser.Output> implements
		AbstractParsingStrategy<CoreNlpParsingStrategy<?, ?>, CoreNlpParser, I, O> {

}
