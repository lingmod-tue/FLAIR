package com.flair.server.parser;

public abstract class OpenNlpParsingStrategy<I extends AbstractParser.Input,
        O extends AbstractParser.Output> implements
        AbstractParsingStrategy<OpenNlpParsingStrategy<?, ?>, OpenNlpParser, I, O> {
}