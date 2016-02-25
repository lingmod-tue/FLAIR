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
class WebDocumentParserJob extends AbstractTaskLinkingJob implements BasicParsingJobOutput
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
	// ### TODO sort the doc collection according the original search ranking
	if (jobStarted)
	    throw new IllegalStateException("Job has already begun");
	
	jobStarted = true;
	
	List<WebCrawlerTask> tasks = new ArrayList<>();
	for (SearchResult itr : input.searchResults)
	{
	    WebCrawlerTask newTask = new WebCrawlerTask(this, new BasicTaskChain(this), itr);
	    registerTask(newTask);
	    tasks.add(newTask);
	}
	
	input.webCrawlerExecutor.crawl(tasks);
    }

    @Override
    public void performLinking(TaskType previousType, AbstractTaskResult previousResult)
    {
	switch (previousType)
	{
	    case FETCH_DOCUMENT_TEXT:
	    {
		// crawled, now parse
		WebCrawlerTaskResult result = (WebCrawlerTaskResult)previousResult;
		if (result.wasSuccessful() == false)
		{
		    output.delinquents.add(result.getOutput());
		    break;
		}
		    
		DocumentParsingTask newTask = new DocumentParsingTask(this,
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
		DocumentParsingTaskResult result = (DocumentParsingTaskResult)previousResult;
		output.parsedDocs.add(result.getOutput(), true);
		break;
	    }
	}
    }

    @Override
    public DocumentCollection getParsedDocuments() {
	return getOutput().parsedDocs;
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

class WebDocumentParserJobInput
{
    public final Language			    sourceLanguage;
    public final List<SearchResult>		    searchResults;
    public final WebCrawlerTaskExecutor		    webCrawlerExecutor;
    public final DocumentParsingTaskExecutor	    docParsingExecutor;
    public final DocumentParserPool		    docParserPool;
    public final AbstractParsingStrategyFactory	    docParsingStrategy;
    
    public WebDocumentParserJobInput(Language sourceLanguage,
				     List<SearchResult> searchResults,
				     WebCrawlerTaskExecutor webCrawler,
				     DocumentParsingTaskExecutor docParser,
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