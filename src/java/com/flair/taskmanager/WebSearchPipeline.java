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
import com.flair.parser.ParserType;
import com.flair.parser.ParsingFactoryGenerator;
import java.util.List;

/**
 * Job scheduler for the web search results framework
 * @author shadeMe
 */
public final class WebSearchPipeline
{   
    private static volatile WebSearchPipeline	    singleton = null;
    
    public static WebSearchPipeline get()
    {
	if (singleton == null)
	{
	    synchronized(WebSearchPipeline.class)
	    {
		if (singleton == null)
		    singleton = new WebSearchPipeline();
	    }
	}
	
	return singleton;
    }
    
    private final WebCrawlerTaskExecutor	    webCrawlerExecutor;
    private final DocumentParsingTaskExecutor	    docParsingExecutor;
    
    private final DocumentParserPool		    stanfordParserPool;
    private final AbstractParsingStrategyFactory    stanfordEnglishStrategy;

    private WebSearchPipeline()
    {
	this.webCrawlerExecutor = new WebCrawlerTaskExecutor();
	this.docParsingExecutor = new DocumentParsingTaskExecutor();
	this.stanfordParserPool = new DocumentParserPool(ParsingFactoryGenerator.createParser(ParserType.STANFORD_CORENLP));
	this.stanfordEnglishStrategy = ParsingFactoryGenerator.createParsingStrategy(ParserType.STANFORD_CORENLP, Language.ENGLISH);
    }
    
    public BasicPipelineOperation scheduleOperation(Language lang, List<SearchResult> searchResults)
    {
	AbstractParsingStrategyFactory strategy = null;
	switch (lang)
	{
	    case ENGLISH:
		strategy = stanfordEnglishStrategy;
	}
	assert strategy != null;
	
	WebDocumentParserJobInput jobParams = new WebDocumentParserJobInput(lang,
									    searchResults, 
									    webCrawlerExecutor,
									    docParsingExecutor, 
									    stanfordParserPool,
									    strategy);
	WebDocumentParserJob newJob = new WebDocumentParserJob(jobParams);
	WebSearchPipelineOperation result = new WebSearchPipelineOperation(newJob);
	newJob.begin();
	
	return result;
    }
}

class WebSearchPipelineOperation implements BasicPipelineOperation
{
    private final WebDocumentParserJob		    job;

    public WebSearchPipelineOperation(WebDocumentParserJob job) {
	this.job = job;
    }
    
    @Override
    public boolean isCancelled() {
	return job.isCancelled();
    }

    @Override
    public void cancel() {
	job.cancel();
    }

    @Override
    public DocumentCollection getOutput() {
	return job.getOutput().parsedDocs;
    }
}
