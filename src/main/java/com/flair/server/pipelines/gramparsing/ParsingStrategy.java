package com.flair.server.pipelines.gramparsing;

import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.CoreNlpParsingStrategy;

final class ParsingStrategy extends CoreNlpParsingStrategy<ParserInput, ParserOutput> {
	static final class Factory implements CoreNlpParsingStrategy.Factory<CoreNlpParsingStrategy, ParserInput> {
		@Override
		public ParsingStrategy create(ParserInput input) {
			return new ParsingStrategy(input);
		}
	}

	private final ParserInput input;
	private final ParserOutput output;

	private ParsingStrategy(ParserInput input) {
		this.input = input;
		this.output = new ParserOutput(input.source);
	}

	@Override
	public ParserInput input() {
		return input;
	}
	@Override
	public ParserOutput output() {
		return output;
	}
	@Override
	public void apply(CoreNlpParser parser) {
		switch (input.language()) {
		case ENGLISH:
			EnglishParsingLogic.factory(input.source).apply(parser);
			break;
		case GERMAN:
			GermanParsingLogic.factory(input.source).apply(parser);
			break;
		default:
			throw new IllegalStateException("GramParsingPipeline doesn't support the language " + input.language());
		}
	}

	static Factory factory() { return new Factory(); }
}
