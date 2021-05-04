package com.flair.server.parser;

public abstract class SimpleNlgParsingStrategy<I extends AbstractParser.Input,
        O extends AbstractParser.Output> implements
        AbstractParsingStrategy<SimpleNlgParsingStrategy<?, ?>, SimpleNlgParser, I, O> {
}