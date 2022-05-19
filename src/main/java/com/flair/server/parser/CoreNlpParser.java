package com.flair.server.parser;

import com.flair.shared.grammar.Language;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.ArrayList;
import java.util.Properties;

public class CoreNlpParser implements ThreadSafeParser<CoreNlpParser,
		CoreNlpParsingStrategy<? extends AbstractParser.Input,
				? extends AbstractParser.Output>> {
	public static final class Factory implements AbstractParser.Factory {
		private Factory() {}
		@Override
		public CoreNlpParser create(Language lang, Properties properties) {
			for(InstantiatedParser ip : instantiatedParsers) {
				if(ip.language.equals(lang) && ip.props.equals(properties)) {
					return ip.parser;
				}
			}
			
			InstantiatedParser ip = new InstantiatedParser(new CoreNlpParser(lang, properties), lang, properties);
			instantiatedParsers.add(ip);
			return ip.parser;
		}
		
		private static final class InstantiatedParser {
			CoreNlpParser parser;
			Language language;
			Properties props;
			
			public InstantiatedParser(CoreNlpParser parser, Language language, Properties props) {
				this.parser = parser;
				this.language = language;
				this.props = props;
			}
		}
		
		private static final ArrayList<InstantiatedParser> instantiatedParsers = new ArrayList<>();
	}

	private final StanfordCoreNLP pipeline;
	private final Language language;

	private CoreNlpParser(Language lang, Properties props) {
		this.language = lang;
		this.pipeline = new StanfordCoreNLP(props);
	}

	public StanfordCoreNLP pipeline() {
		return pipeline;
	}

	public CoreNlpParserAnnotations createAnnotations(Annotation parseAnnotations) {
		return new CoreNlpParserAnnotations(parseAnnotations);
	}

	@Override
	public ParserKind type() {
		return ParserKind.STANFORD_CORENLP;
	}
	@Override
	public Language language() {
		return language;
	}

	@Override
	public void parse(CoreNlpParsingStrategy<?, ?> strategy) {
		try {
			if (language != strategy.input().language()) {
				throw new IllegalArgumentException("Document language " + strategy.input().language()
						+ " not supported (Model language: " + language + ")");
			}

			strategy.apply(this);
		} catch (Throwable e) {
			throw e;
		}
	}

	public static Factory factory() { return new Factory(); }
}
