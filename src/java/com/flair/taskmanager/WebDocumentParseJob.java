/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.taskmanager;

import com.flair.crawler.SearchResult;
import com.flair.grammar.Language;
import com.flair.parser.AbstractDocumentKeywordSearcherFactory;
import com.flair.parser.AbstractParsingStrategyFactory;
import com.flair.parser.DocumentCollection;
import com.flair.parser.KeywordSearcherInput;
import com.flair.parser.SearchResultDocumentSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Takes a list of search results, crawls their text and parses them
 * @author shadeMe
 */
class WebDocumentParseJobInput
{
    public final Language					sourceLanguage;
    public final List<SearchResult>				searchResults;
    public final WebCrawlTaskExecutor				webCrawlerExecutor;
    public final DocumentParseTaskExecutor			docParsingExecutor;
    public final DocumentParserPool				docParserPool;
    public final AbstractParsingStrategyFactory			docParsingStrategy;
    public final AbstractDocumentKeywordSearcherFactory		keywordSearcher;
    public final KeywordSearcherInput				keywordSearcherInput;
    
    public WebDocumentParseJobInput(Language sourceLanguage,
				     List<SearchResult> searchResults,
				     WebCrawlTaskExecutor webCrawler,
				     DocumentParseTaskExecutor docParser,
				     DocumentParserPool parserPool,
				     AbstractParsingStrategyFactory strategy,
				     AbstractDocumentKeywordSearcherFactory keywordSearcher,
				     KeywordSearcherInput keywordSearcherInput)
    {
	this.sourceLanguage = sourceLanguage;
	this.searchResults = searchResults;
	this.webCrawlerExecutor = webCrawler;
	this.docParsingExecutor = docParser;
	this.docParserPool = parserPool;
	this.docParsingStrategy = strategy;
	this.keywordSearcher = keywordSearcher;
	this.keywordSearcherInput = keywordSearcherInput;
    }
}

class WebDocumentParseJobOutput
{
    public final DocumentCollection	parsedDocs;
    public final List<SearchResult>	delinquents;
    
    public WebDocumentParseJobOutput()
    {
	this.parsedDocs = new DocumentCollection();
	this.delinquents = new ArrayList<>();
    }
}

class WebDocumentParseJob extends AbstractTaskLinkingJob
{
    private final WebDocumentParseJobInput	    input;
    private final WebDocumentParseJobOutput	    output;
    
    public WebDocumentParseJob(WebDocumentParseJobInput in)
    {
	super();
	this.input = in;
	this.output = new WebDocumentParseJobOutput();
    }
    
    @Override
    public WebDocumentParseJobOutput getOutput()
    {
	waitForCompletion();
	return output;
    }
    
    @Override
    public void begin() 
    {
	if (isStarted())
	    throw new IllegalStateException("Job has already begun");
	
	List<WebCrawlTask> tasks = new ArrayList<>();
	for (SearchResult itr : input.searchResults)
	{
	    if (itr.isTextFetched() == false)
	    {
		WebCrawlTask newTask = new WebCrawlTask(this, new BasicTaskChain(this), itr);
	        registerTask(newTask);
	        tasks.add(newTask);
	    }
	    else
	    {
		DocumentParseTask newTask = new DocumentParseTask(this,
								      new BasicTaskChain(this),
								      new SearchResultDocumentSource(itr),
								      input.docParsingStrategy.create(),
								      input.docParserPool,
								      input.keywordSearcher.create(),
								      input.keywordSearcherInput);

		registerTask(newTask);
		input.docParsingExecutor.queue(newTask);
	    }
	}
	
	input.webCrawlerExecutor.crawl(tasks);
	flagStarted();
    }

    @Override
    public void performLinking(TaskType previousType, AbstractTaskResult previousResult)
    {
	switch (previousType)
	{
	    case FETCH_SEARCHRESULT_TEXT:
	    {
		// crawled, now parse
		WebCrawlTaskResult result = (WebCrawlTaskResult)previousResult;
		if (result.wasSuccessful() == false)
		{
		    output.delinquents.add(result.getOutput());
		    break;
		}
		    
		DocumentParseTask newTask = new DocumentParseTask(this,
								      new BasicTaskChain(this),
								      new SearchResultDocumentSource(result.getOutput()),
								      input.docParsingStrategy.create(),
								      input.docParserPool,
								      input.keywordSearcher.create(),
								      input.keywordSearcherInput);

		registerTask(newTask);
		input.docParsingExecutor.queue(newTask);
		break;
	    }
	    case PARSE_DOCUMENT:
	    {
		// add the result to the doc collection
		DocumentParseTaskResult result = (DocumentParseTaskResult)previousResult;
		output.parsedDocs.add(result.getOutput(), true);
		output.parsedDocs.sort();
		break;
	    }
	}
    }
    
    @Override
    public String toString()
    {
	if (isStarted() == false)
	    return "WebDocumentParseJob has not started yet";
	else if (isCompleted() == false)
	    return "WebDocumentParseJob is still running";
	else if (isCancelled())
	    return "WebDocumentParseJob was cancelled";
	else
	    return "WebDocumentParserJob Output:\nInput:\n\tLanguage: " + input.sourceLanguage + "\n\tSearch Results: " + input.searchResults.size() + "\nOutput:\n\tParsed Docs: " + output.parsedDocs.size() + "\n\tDelinquents: " + output.delinquents.size();
    }
}
