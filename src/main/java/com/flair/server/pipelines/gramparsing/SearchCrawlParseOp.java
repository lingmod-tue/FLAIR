package com.flair.server.pipelines.gramparsing;

import com.flair.server.crawler.SearchResult;
import com.flair.server.crawler.WebSearchAgent;
import com.flair.server.crawler.WebSearchAgentFactory;
import com.flair.server.document.AbstractDocument;
import com.flair.server.document.AbstractDocumentFactory;
import com.flair.server.document.DocumentCollection;
import com.flair.server.document.SearchResultDocumentSource;
import com.flair.server.parser.AbstractKeywordSearcher;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.pipelines.PipelineOp;
import com.flair.server.scheduler.AsyncExecutorService;
import com.flair.server.scheduler.AsyncJob;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.grammar.Language;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class SearchCrawlParseOp extends PipelineOp<SearchCrawlParseOp.Input, SearchCrawlParseOp.Output> {
	public interface CrawlComplete extends EventHandler<SearchResult> {}

	public interface ParseComplete extends EventHandler<AbstractDocument> {}

	public interface JobComplete extends EventHandler<DocumentCollection> {}

	static final class Input {
		final Language sourceLanguage;
		final String query;
		final int numResults;

		final AsyncExecutorService webSearchExecutor;
		final AsyncExecutorService webCrawlExecutor;
		final AsyncExecutorService docParseExecutor;

		final AbstractDocumentFactory docFactory;
		final CoreNlpParser docParser;
		final ParsingStrategy.Factory docParsingStrategy;
		final AbstractKeywordSearcher.Factory keywordSearcher;
		final KeywordSearcherInput keywordSearcherInput;

		final SearchCrawlParseOp.CrawlComplete crawlComplete;
		final SearchCrawlParseOp.ParseComplete parseComplete;
		final SearchCrawlParseOp.JobComplete jobComplete;

		Input(Language sourceLanguage,
		      String query,
		      int numResults,
		      AsyncExecutorService webSearchExecutor,
		      AsyncExecutorService webCrawlExecutor,
		      AsyncExecutorService docParseExecutor,
		      AbstractDocumentFactory docFactory,
		      CoreNlpParser docParser,
		      ParsingStrategy.Factory strategy,
		      AbstractKeywordSearcher.Factory keywordSearcher,
		      KeywordSearcherInput keywordSearcherInput,
		      CrawlComplete crawlComplete,
		      ParseComplete parseComplete,
		      JobComplete jobComplete) {
			this.sourceLanguage = sourceLanguage;
			this.query = query;
			this.numResults = numResults;

			this.webCrawlExecutor = webCrawlExecutor;
			this.webSearchExecutor = webSearchExecutor;
			this.docParseExecutor = docParseExecutor;

			this.docFactory = docFactory;
			this.docParser = docParser;
			this.docParsingStrategy = strategy;
			this.keywordSearcher = keywordSearcher;
			this.keywordSearcherInput = keywordSearcherInput;

			this.crawlComplete = crawlComplete != null ? crawlComplete : e -> {};
			this.parseComplete = parseComplete != null ? parseComplete : e -> {};
			this.jobComplete = jobComplete != null ? jobComplete : e -> {};
		}
	}

	public static final class Output {
		public final List<SearchResult> searchResults;
		public final DocumentCollection parsedDocs;

		Output(Language sourceLang) {
			this.searchResults = new ArrayList<>();
			this.parsedDocs = new DocumentCollection(sourceLang);
		}
	}

	private WebSearchAgent searchAgent;
	private int numValidResults;
	private int numActiveCrawlTasks;
	private int numTotalCrawlsQueued;

	@Override
	protected String desc() {
		return name + " Output:\nInput:\n\tLanguage: " + input.sourceLanguage + "\n\tQuery: "
				+ input.query + "\n\tRequired Results: " + input.numResults + "\nOutput\n\tSearch Results: "
				+ output.searchResults.size() + "\n\tParsed Docs: " + output.parsedDocs.size();
	}

	private void initTaskSyncHandlers() {
		taskLinker.addHandler(WebSearchTask.Result.class, (j, r) -> {
			AsyncJob.Scheduler scheduler = AsyncJob.Scheduler.existingJob(j);
			for (SearchResult itr : r.output) {
				scheduler.newTask(WebCrawlTask.factory(itr))
						.with(input.webCrawlExecutor)
						.then(this::linkTasks)
						.queue();

				numActiveCrawlTasks++;
				numTotalCrawlsQueued++;
			}

			if (scheduler.hasTasks())
				scheduler.fire();
		});

		taskLinker.addHandler(WebCrawlTask.Result.class, (j, r) -> {
			numActiveCrawlTasks--;

			SearchResult sr = r.output;
			AsyncJob.Scheduler scheduler = AsyncJob.Scheduler.existingJob(j);

			if (sr.isTextFetched()) {
				// check token count and queue parse task if valid
				StringTokenizer tokenizer = new StringTokenizer(sr.getPageText(), " ");
				int tokCount = tokenizer.countTokens();
				if (tokCount > Constants.SEARCH_RESULT_MINIMUM_TOKEN_COUNT) {
					safeInvoke(() -> input.crawlComplete.handle(sr), "Exception in crawl complete handler");
					numValidResults++;

					output.searchResults.add(sr);

					// parse the document
					AbstractDocument docToParse = input.docFactory.create(new SearchResultDocumentSource(sr));
					scheduler.newTask(
							DocParseTask.factory(input.docParsingStrategy.create(new ParserInput(docToParse)),
									input.docParser,
									input.keywordSearcher.create(),
									input.keywordSearcherInput)
					)
							.with(input.docParseExecutor)
							.then(this::linkTasks)
							.queue();
				} else
					ServerLogger.get().trace(sr.toString() + " - Discarded for low token count (" + tokCount + ")");
			}

			// queue search tasks as long as we need/have results or have timed-out
			// wait till the last crawl task is complete
			if (numActiveCrawlTasks == 0 && numTotalCrawlsQueued < Constants.MAX_WEB_CRAWLS_PER_OP) {
				if (numValidResults < input.numResults && !searchAgent.hasNoMoreResults()) {
					int delta = input.numResults - numValidResults;
					if (delta > 0) {
						scheduler.newTask(WebSearchTask.factory(searchAgent, delta))
								.with(input.webSearchExecutor)
								.then(this::linkTasks)
								.queue();
					}
				}
			}

			if (scheduler.hasTasks())
				scheduler.fire();
		});

		taskLinker.addHandler(DocParseTask.Result.class, (j, r) -> {
			// add the result to the doc collection
			if (r.output != null) {
				safeInvoke(() -> input.parseComplete.handle(r.output),
						"Exception in parse complete handler");
				// the ranks of the documents can be discontinuous if search results were discarded
				// the client should rerank the collection upon job completion manually
				// however, the general sort order wrt the search results will be preserved
				output.parsedDocs.add(r.output, true);
				output.parsedDocs.sort();
			}
		});
	}

	SearchCrawlParseOp(Input input) {
		super("SearchCrawlParseOp", input, new Output(input.sourceLanguage));
		this.numValidResults = this.numActiveCrawlTasks = this.numTotalCrawlsQueued = 0;
		initTaskSyncHandlers();

		this.searchAgent = WebSearchAgentFactory.create(WebSearchAgentFactory.SearchAgent.BING,
				input.sourceLanguage,
				input.query);

		this.job = AsyncJob.Scheduler.newJob(j -> {
			if (j.isCancelled())
				return;

			Collections.sort(output.searchResults);
			int i = 1;
			for (SearchResult itr : output.searchResults) {
				itr.setRank(i);
				i++;
			}
			safeInvoke(() -> input.jobComplete.handle(output.parsedDocs),
					"Exception in job complete handler");
		})
				.newTask(WebSearchTask.factory(searchAgent, input.numResults))
				.with(input.webSearchExecutor)
				.then(this::linkTasks)
				.queue()
				.fire();
	}
}
