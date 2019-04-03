package com.flair.server.pipelines.questgen;

import com.flair.server.document.AbstractDocument;
import com.flair.server.parser.AbstractParser;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.CoreNlpParsingStrategy;
import com.flair.shared.grammar.Language;

final class ParsingStrategy extends CoreNlpParsingStrategy<ParsingStrategy.ParserInput, ParsingStrategy.ParserOutput> {
	static final class Factory implements CoreNlpParsingStrategy.Factory<CoreNlpParsingStrategy, ParserInput> {
		@Override
		public ParsingStrategy create(ParserInput input) {
			return new ParsingStrategy(input);
		}
	}

	static final class ParserInput implements AbstractParser.Input {
		final AbstractDocument source;
		ParserInput(AbstractDocument source) {
			this.source = source;
		}

		@Override
		public Language language() {
			return source.getLanguage();
		}
	}

	static final class ParserOutput implements AbstractParser.Output {
		final AbstractDocument parsedDoc;
		ParserOutput(AbstractDocument parsedDoc) {this.parsedDoc = parsedDoc;}

		@Override
		public boolean valid() {
			return parsedDoc.isParsed();
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
		if (input.language() == Language.ENGLISH) {
			new ParsingLogic(input.source).apply(parser);
		} else {
			throw new IllegalStateException("QuestionGenerationPipeline doesn't support the language " + input.language());
		}
	}

	static Factory factory() { return new Factory(); }

}
