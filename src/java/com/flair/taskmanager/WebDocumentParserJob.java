/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.taskmanager;

import com.flair.crawler.SearchResult;
import com.flair.grammar.Language;
import com.flair.parser.AbstractParsingStrategyFactory;
import com.flair.parser.DocumentCollection;
import com.flair.parser.SearchResultDocumentSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Takes a list of search results, crawls their text and parses them
 * @author shadeMe
 */
class WebDocumentParserJobInput
{
    public final Language			    sourceLanguage;
    public final List<SearchResult>		    searchResults;
    public final WebCrawlTaskExecutor		    webCrawlerExecutor;
    public final DocumentParseTaskExecutor	    docParsingExecutor;
    public final DocumentParserPool		    docParserPool;
    public final AbstractParsingStrategyFactory	    docParsingStrategy;
    
    public WebDocumentParserJobInput(Language sourceLanguage,
				     List<SearchResult> searchResults,
				     WebCrawlTaskExecutor webCrawler,
				     DocumentParseTaskExecutor docParser,
				     DocumentParserPool parserPool,
				     AbstractParsingStrategyFactory strategy)
    {
	this.sourceLanguage = sourceLanguage;
	this.searchResults = searchResults;
	this.webCrawlerExecutor = webCrawler;
	this.docParsingExecutor = docParser;
	this.docParserPool = parserPool;
	this.docParsingStrategy = strategy;
    }
}

class WebDocumentParserJobOutput
{
    public final DocumentCollection	parsedDocs;
    public final List<SearchResult>	delinquents;
    
    public WebDocumentParserJobOutput()
    {
	this.parsedDocs = new DocumentCollection();
	this.delinquents = new ArrayList<>();
    }
}

class WebDocumentParserJob extends AbstractTaskLinkingJob
{
    private final WebDocumentParserJobInput	    input;
    private final WebDocumentParserJobOutput	    output;
    
    public WebDocumentParserJob(WebDocumentParserJobInput in)
    {
	super();
	this.input = in;
	this.output = new WebDocumentParserJobOutput();
    }
    
    public WebDocumentParserJobOutput getOutput()
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
								      input.docParserPool);

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
								      input.docParserPool);

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
	if (isCompleted() == false)
	    return "WebDocumentParserJob is still running";
	else if (isCancelled())
	    return "WebDocumentParserJob was cancelled";
	else
	    return "WebDocumentParserJob Output:\nInput:\n\tLanguage: " + input.sourceLanguage + "\n\tSearch Results: " + input.searchResults.size() + "\nOutput:\n\tParsed Docs: " + output.parsedDocs.size() + "\n\tDelinquents: " + output.delinquents.size();
    }
}
