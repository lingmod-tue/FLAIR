package com.flair.server.pipelines.gramparsing;

import com.flair.server.document.AbstractDocumentFactory;
import com.flair.server.document.AbstractDocumentSource;
import com.flair.server.document.Document;
import com.flair.server.grammar.StopwordsList;
import com.flair.server.parser.AbstractKeywordSearcher;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.parser.SimpleSubstringKeywordSearcher;
import com.flair.server.parser.corenlp.StopwordAnnotator;
import com.flair.server.pipelines.PipelineOp;
import com.flair.server.scheduler.AsyncExecutorService;
import com.flair.server.scheduler.ThreadPool;
import com.flair.shared.grammar.Language;
import edu.stanford.nlp.util.Lazy;

import java.util.*;

/*
 * Pipeline that parses documents and collects information about grammatical constructions
 */
public final class GramParsingPipeline {
	private static GramParsingPipeline SINGLETON = null;

	public static GramParsingPipeline get() {
		if (SINGLETON == null) {
			synchronized (GramParsingPipeline.class) {
				if (SINGLETON == null)
					SINGLETON = new GramParsingPipeline();
			}
		}

		return SINGLETON;
	}

	public static void dispose() {
		if (SINGLETON != null) {
			SINGLETON.shutdown();
			SINGLETON = null;
		}
	}

	private final AsyncExecutorService webSearchExecutor;
	private final AsyncExecutorService webCrawlExecutor;
	private final AsyncExecutorService docParseExecutor;

	private final AbstractDocumentFactory docFactory;
	private final AbstractKeywordSearcher.Factory keywordSearchers;
	private final Map<Language, Lazy<CoreNlpParser>> parsers;


	private GramParsingPipeline() {
		ThreadPool.Builder threadPoolBuilder = ThreadPool.get().builder();
		webSearchExecutor = threadPoolBuilder
				.poolSize(Constants.WEB_SEARCH_TASK_THREADPOOL_SIZE)
				.poolName("Web Search")
				.build();
		webCrawlExecutor = threadPoolBuilder
				.poolSize(Constants.WEB_CRAWL_TASK_THREADPOOL_SIZE)
				.poolName("Web Crawl")
				.build();
		docParseExecutor = threadPoolBuilder
				.poolSize(Constants.PARSE_DOC_TASK_THREADPOOL_SIZE)
				.poolName("Doc Parse")
				.build();

		keywordSearchers = SimpleSubstringKeywordSearcher.factory();
		docFactory = Document.factory();

		parsers = new EnumMap<>(Language.class);
		CoreNlpParser.Factory parserFactory = CoreNlpParser.factory();
		for (Language lang : Language.values())
			parsers.put(lang, Lazy.of(() -> parserFactory.create(lang, createDefaultPipelineProperties(lang))));
	}

	private void shutdown() {
		// the thread pools are shutdown elsewhere
		// nothing else to do here
	}

	private CoreNlpParser getParser(Language lang) {
		if (!parsers.containsKey(lang))
			throw new IllegalArgumentException("Invalid lang " + lang);
		else
			return parsers.get(lang).get();
	}

	private static Properties createDefaultPipelineProperties(Language lang) {
		Properties pipelineProps = new Properties();
		switch (lang) {
		case ENGLISH:
			pipelineProps.put("annotators", "tokenize, ssplit, pos, lemma, stopword, parse");
			pipelineProps.put("parse.originalDependencies", "true");
			pipelineProps.setProperty("parse.model", com.flair.server.parser.corenlp.Constants.ENGLISH_SR_PARSER_MODEL);

			break;
		case GERMAN:
			pipelineProps.put("annotators", "tokenize, ssplit, pos, stopword, parse");
			pipelineProps.put("tokenize.language", "de");
			pipelineProps.setProperty("parse.model", com.flair.server.parser.corenlp.Constants.GERMAN_SR_PARSER_MODEL);
			pipelineProps.setProperty("pos.model", com.flair.server.parser.corenlp.Constants.GERMAN_POS_MODEL);
			break;
		default:
			throw new IllegalArgumentException("Invalid model language: " + lang + "");
		}

		pipelineProps.put("tokenize.options", "tokenizePerLine");
		pipelineProps.put("ssplit.newlineIsSentenceBreak", "two");

		StringBuilder stopwords = new StringBuilder();
		StopwordsList.get(lang).forEach(e -> stopwords.append(e).append(","));
		pipelineProps.setProperty("customAnnotatorClass.stopword", "com.flair.server.parser.corenlp.StopwordAnnotator");
		pipelineProps.setProperty(StopwordAnnotator.STOPWORDS_LIST, stopwords.toString());
		pipelineProps.setProperty(StopwordAnnotator.IGNORE_STOPWORD_CASE, "true");

		return pipelineProps;
	}


	public final class SearchCrawlParseOpBuilder implements PipelineOp.PipelineOpBuilder {
		Language lang;
		String query;
		int numResults;
		KeywordSearcherInput keywords;

		SearchCrawlParseOp.CrawlComplete crawlComplete;
		SearchCrawlParseOp.ParseComplete parseComplete;
		SearchCrawlParseOp.JobComplete jobComplete;

		private SearchCrawlParseOpBuilder() {}

		public SearchCrawlParseOpBuilder lang(Language lang) {
			this.lang = lang;
			return this;
		}

		public SearchCrawlParseOpBuilder query(String query) {
			this.query = query;
			return this;
		}

		public SearchCrawlParseOpBuilder results(int numResults) {
			this.numResults = numResults;
			return this;
		}

		public SearchCrawlParseOpBuilder keywords(KeywordSearcherInput keywords) {
			this.keywords = keywords;
			return this;
		}

		public SearchCrawlParseOpBuilder onCrawl(SearchCrawlParseOp.CrawlComplete handler) {
			this.crawlComplete = handler;
			return this;
		}

		public SearchCrawlParseOpBuilder onParse(SearchCrawlParseOp.ParseComplete handler) {
			this.parseComplete = handler;
			return this;
		}

		public SearchCrawlParseOpBuilder onComplete(SearchCrawlParseOp.JobComplete handler) {
			this.jobComplete = handler;
			return this;
		}

		public PipelineOp launch() {
			if (lang == null)
				throw new IllegalStateException("Invalid lang");
			else if (query == null || query.isEmpty())
				throw new IllegalStateException("Invalid query");
			else if (numResults == 0)
				throw new IllegalStateException("Invalid number of results");
			else if (keywords == null)
				throw new IllegalStateException("Invalid keyword data");

			if (jobComplete == null)
				throw new IllegalStateException("No completion handler set");

			SearchCrawlParseOp.Input input = new SearchCrawlParseOp.Input(lang,
					query,
					numResults,
					webSearchExecutor,
					webCrawlExecutor,
					docParseExecutor,
					docFactory,
					getParser(lang),
					ParsingStrategy.factory(),
					keywordSearchers,
					keywords,
					crawlComplete,
					parseComplete,
					jobComplete);

			return new SearchCrawlParseOp(input);
		}
	}

	public final class ParseOpBuilder implements PipelineOp.PipelineOpBuilder {
		Language lang;
		List<AbstractDocumentSource> sourceDocs;
		KeywordSearcherInput keywords;

		ParseOp.ParseComplete parseComplete;
		ParseOp.JobComplete jobComplete;

		private ParseOpBuilder() {
			sourceDocs = new ArrayList<>();
		}

		public ParseOpBuilder lang(Language lang) {
			this.lang = lang;
			return this;
		}

		public ParseOpBuilder docSource(AbstractDocumentSource source) {
			if (lang == null)
				throw new IllegalStateException("Language needs to be set first");
			else if (source.getLanguage() != lang)
				throw new IllegalArgumentException("Source document language mismatch");

			this.sourceDocs.add(source);
			return this;
		}

		public ParseOpBuilder docSource(Iterable<AbstractDocumentSource> sources) {
			for (AbstractDocumentSource itr : sources)
				docSource(itr);
			return this;
		}

		public ParseOpBuilder keywords(KeywordSearcherInput keywords) {
			this.keywords = keywords;
			return this;
		}

		public ParseOpBuilder onParse(ParseOp.ParseComplete handler) {
			this.parseComplete = handler;
			return this;
		}

		public ParseOpBuilder onComplete(ParseOp.JobComplete handler) {
			this.jobComplete = handler;
			return this;
		}

		public PipelineOp launch() {
			if (lang == null)
				throw new IllegalStateException("Invalid lang");
			else if (sourceDocs.isEmpty())
				throw new IllegalStateException("No document sources to parse");

			if (jobComplete == null)
				throw new IllegalStateException("No completion handler set");

			ParseOp.Input input = new ParseOp.Input(lang,
					sourceDocs,
					docParseExecutor,
					docFactory,
					getParser(lang),
					ParsingStrategy.factory(),
					keywordSearchers,
					keywords,
					parseComplete,
					jobComplete);

			return new ParseOp(input);
		}
	}

	public SearchCrawlParseOpBuilder searchCrawlParse() {
		return new SearchCrawlParseOpBuilder();
	}

	public ParseOpBuilder documentParse() {
		return new ParseOpBuilder();
	}
}
