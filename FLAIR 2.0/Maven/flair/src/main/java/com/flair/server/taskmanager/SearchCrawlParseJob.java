package com.flair.server.taskmanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import com.flair.server.crawler.SearchResult;
import com.flair.server.crawler.WebSearchAgent;
import com.flair.server.crawler.WebSearchAgentFactory;
import com.flair.server.parser.AbstractDocument;
import com.flair.server.parser.AbstractDocumentKeywordSearcherFactory;
import com.flair.server.parser.AbstractParsingStrategyFactory;
import com.flair.server.parser.DocumentCollection;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.parser.SearchResultDocumentSource;
import com.flair.shared.grammar.Language;

/*
 * Performs a web search, crawls the results and parses the text
 */
final class SearchCrawlParseJob extends AbstractJob<SearchCrawlParseJobOutput, SearchCrawlParseJobEvent>
{
	private static final int 						MINIMUM_TOKEN_COUNT = 100; 	// in the page text
	private static final int 						MAX_CRAWLS = 100; 			// anymore and we time-out
	
	private final SearchCrawlParseJobInput			input;
	private final SearchCrawlParseJobOutput			output;
	private WebSearchAgent							searchAgent;
	private int										numValidResults;
	private int										numActiveCrawlTasks;
	private int										numTotalCrawlsQueued;
	
	public SearchCrawlParseJob(SearchCrawlParseJobInput input)
	{
		this.input = input;
		this.output = new SearchCrawlParseJobOutput(input.sourceLanguage);
		this.searchAgent = null;
		this.numValidResults = this.numActiveCrawlTasks = this.numTotalCrawlsQueued = 0;
	}
	
	private void queueWebSearchTask(WebSearchAgent agent, int numResults)
	{
		WebSearchTask newTask = new WebSearchTask(this, agent, numResults);
		registerTask(newTask);
		input.webSearchExecutor.search(newTask);
	}
	
	private void queueWebCrawlTasks(List<SearchResult> results)
	{
		for (SearchResult itr : results)
		{
			WebCrawlTask newTask = new WebCrawlTask(this, itr);
			registerTask(newTask);
			input.webCrawlExecutor.crawl(newTask);
			numActiveCrawlTasks++;
		}
	}
	
	private void queueDocParseTask(SearchResult source)
	{
		DocumentParseTask newTask = new DocumentParseTask(this,
											new SearchResultDocumentSource(source),
											input.docParsingStrategy.create(),
											input.docParserPool,
											input.keywordSearcher.create(),
											input.keywordSearcherInput);
		registerTask(newTask);
		input.docParseExecutor.parse(newTask);
	}

	@Override
	public void begin()
	{
		if (isStarted())
			throw new IllegalStateException("Job has already begun");

		searchAgent = WebSearchAgentFactory.create(WebSearchAgentFactory.SearchAgent.BING,
												input.sourceLanguage,
												input.query);

		queueWebSearchTask(searchAgent, input.numResults);
		flagStarted();
	}
	
	@Override
	protected void handleTaskCompletion(AbstractTaskResult<?> completionResult)
	{
		switch (completionResult.getType())
		{
		case WEB_SEARCH:
			{
				WebSearchTaskResult result = (WebSearchTaskResult) completionResult.getResult();
				notifyListeners(new SearchCrawlParseJobEvent(result.getOutput().size()));
				// queue crawl tasks for the results
				queueWebCrawlTasks(result.getOutput());
				numActiveCrawlTasks += result.getOutput().size();
				
				break;
			}
		case WEB_CRAWL:
			{
				WebCrawlTaskResult result = (WebCrawlTaskResult) completionResult.getResult();
				numActiveCrawlTasks--;
				
				if (result.wasSuccessful())
				{
					SearchResult sr = result.getOutput();
					// check token count and queue parse task if valid
					StringTokenizer tokenizer = new StringTokenizer(sr.getPageText(), " ");
					if (MINIMUM_TOKEN_COUNT < tokenizer.countTokens())
					{
						notifyListeners(new SearchCrawlParseJobEvent(sr));
						numValidResults++;
						
						output.searchResults.add(sr);
						queueDocParseTask(sr);
					}
				}
				
				// queue search tasks as long as we need/have results or have timed-out
				// wait till the last crawl task is complete
				if (numActiveCrawlTasks == 0 && numTotalCrawlsQueued < MAX_CRAWLS)
				{
					if (numValidResults < input.numResults && searchAgent.hasNoMoreResults() == false)
					{
						int delta = input.numResults - numValidResults;
						if (delta > 0)
							queueWebSearchTask(searchAgent, delta);
					}
				}
				
				break;
			}
		case PARSE_DOCUMENT:
			{
				// add the result to the doc collection
				DocumentParseTaskResult result = (DocumentParseTaskResult) completionResult.getResult();
				
				if (result.getOutput() != null)
				{
					notifyListeners(new SearchCrawlParseJobEvent(result.getOutput()));
					
					// the ranks of the documents can be discontinuous if search results were discarded
					// the client should rerank the collection upon job completion manually
					// however, the general sort order wrt the search results will be preserved
				    output.parsedDocs.add(result.getOutput(), true);
				    output.parsedDocs.sort();
				}
				
				break;
			}
		}
	}

	@Override
	public SearchCrawlParseJobOutput getOutput()
	{
		waitForCompletion();
		return output;
	}

	@Override
	public String toString()
	{
		if (isStarted() == false)
			return "SearchCrawlParseJob has not started yet";
		else if (isCompleted() == false)
			return "SearchCrawlParseJob is still running";
		else if (isCancelled())
			return "SearchCrawlParseJob was cancelled";
		else
			return "SearchCrawlParseJob Output:\nInput:\n\tLanguage: " + input.sourceLanguage + "\n\tQuery: "
					+ input.query + "\n\tRequired Results: " + input.numResults + "\nOutput\n\tSearch Results: "
					+ output.searchResults.size() + "\n\tParsed Docs: " + output.parsedDocs.size();
	}

	@Override
	protected SearchCrawlParseJobEvent createCompletionEvent() {
		return new SearchCrawlParseJobEvent(output);
	}
}

final class SearchCrawlParseJobInput
{
	public final Language						sourceLanguage;
	public final String							query;
	public final int							numResults;
	
	public final WebSearchTask.Executor			webSearchExecutor;
	public final WebCrawlTask.Executor			webCrawlExecutor;
	public final DocumentParseTask.Executor		docParseExecutor;
	
    public final DocumentParserPool			   			docParserPool;
    public final AbstractParsingStrategyFactory		    docParsingStrategy;
    public final AbstractDocumentKeywordSearcherFactory	keywordSearcher;
    public final KeywordSearcherInput			    	keywordSearcherInput;

	public SearchCrawlParseJobInput(Language sourceLanguage,
									String query,
									int numResults,
									WebSearchTask.Executor webSearchExecutor,
									WebCrawlTask.Executor webCrawlExecutor,
									DocumentParseTask.Executor docParseExecutor,
									DocumentParserPool parserPool,
								    AbstractParsingStrategyFactory strategy,
								    AbstractDocumentKeywordSearcherFactory keywordSearcher,
								    KeywordSearcherInput keywordSearcherInput)
	{
		this.sourceLanguage = sourceLanguage;
		this.query = query;
		this.numResults = numResults;
		
		this.webCrawlExecutor = webCrawlExecutor;
		this.webSearchExecutor = webSearchExecutor;
		this.docParseExecutor = docParseExecutor;
		
		this.docParserPool = parserPool;
		this.docParsingStrategy = strategy;
		this.keywordSearcher = keywordSearcher;
		this.keywordSearcherInput = keywordSearcherInput;
	}
}

final class SearchCrawlParseJobOutput
{
	public final List<SearchResult> 	searchResults;
	public final DocumentCollection		parsedDocs;
	
	public SearchCrawlParseJobOutput(Language sourceLang)
	{
		this.searchResults = new ArrayList<>();
		this.parsedDocs = new DocumentCollection(sourceLang);
	}
}

final class SearchCrawlParseJobEvent implements AbstractJobEvent<SearchCrawlParseJobOutput>
{
	enum Type
	{
		WEB_SEARCH_COMPLETE,
		WEB_CRAWL_COMPLETE,
		PARSE_COMPLETE,
		JOB_COMPLETE
	}
	
	public final Type							type;
	public final int							numSearchResults;
	public final SearchResult					crawledResult;
	public final AbstractDocument				parsedDoc;
	public final SearchCrawlParseJobOutput		jobOutput;
	
	public SearchCrawlParseJobEvent(int numSearchResults)
	{
		type = Type.WEB_SEARCH_COMPLETE;
		this.numSearchResults = numSearchResults;
		crawledResult = null;
		parsedDoc = null;
		jobOutput = null;
	}
	
	public SearchCrawlParseJobEvent(SearchResult r)
	{
		type = Type.WEB_CRAWL_COMPLETE;
		numSearchResults = 0;
		crawledResult = r;
		parsedDoc = null;
		jobOutput = null;
	}
	
	public SearchCrawlParseJobEvent(AbstractDocument d)
	{
		type = Type.PARSE_COMPLETE;
		numSearchResults = 0;
		crawledResult = null;
		parsedDoc = d;
		jobOutput = null;
	}
	
	public SearchCrawlParseJobEvent(SearchCrawlParseJobOutput o)
	{
		type = Type.JOB_COMPLETE;
		numSearchResults = 0;
		crawledResult = null;
		parsedDoc = null;
		jobOutput = o;
			
		// sort and anneal search results
		Collections.sort(jobOutput.searchResults);
		int i = 1;
		for (SearchResult itr : jobOutput.searchResults)
		{
			itr.setRank(i);
			i++;
		}
	}

	@Override
	public boolean isCompletionEvent() {
		return type == Type.JOB_COMPLETE;
	}

	@Override
	public SearchCrawlParseJobOutput getOutput() {
		return jobOutput;
	}
}