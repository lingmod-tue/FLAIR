/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.taskmanager;

import com.flair.crawler.SearchResult;
import com.flair.grammar.Language;
import com.flair.parser.AbstractDocumentSource;
import com.flair.parser.AbstractParsingStrategyFactory;
import com.flair.parser.MasterParsingFactoryGenerator;
import com.flair.parser.ParserType;
import com.flair.parser.SearchResultDocumentSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Job scheduler for the web crawling/local parser frameworks
 * @author shadeMe
 */
public final class MasterJobPipeline
{   
    private static MasterJobPipeline	    SINGLETON = new MasterJobPipeline();
    
    public static MasterJobPipeline get()
    {
	if (SINGLETON == null)
	{
	    synchronized(MasterJobPipeline.class)
	    {
		if (SINGLETON == null)
		    SINGLETON = new MasterJobPipeline();
	    }
	}
	
	return SINGLETON;
    }
    
    private final WebSearchTaskExecutor		    webSearchExecutor;
    private final WebCrawlTaskExecutor		    webCrawlExecutor;
    private final DocumentParseTaskExecutor	    docParseExecutor;
    
    private final DocumentParserPool		    stanfordParserEnglishPool;
    private final AbstractParsingStrategyFactory    stanfordEnglishStrategy;

    private MasterJobPipeline()
    {
	this.webSearchExecutor = new WebSearchTaskExecutor();
	this.webCrawlExecutor = new WebCrawlTaskExecutor();
	this.docParseExecutor = new DocumentParseTaskExecutor();
	
	this.stanfordParserEnglishPool = new DocumentParserPool(MasterParsingFactoryGenerator.createParser(ParserType.STANFORD_CORENLP, Language.ENGLISH));
	this.stanfordEnglishStrategy = MasterParsingFactoryGenerator.createParsingStrategy(ParserType.STANFORD_CORENLP, Language.ENGLISH);
    }
    
    private AbstractParsingStrategyFactory getStrategyForLanguage(Language lang)
    {
	switch (lang)
	{
	    case ENGLISH:
		return stanfordEnglishStrategy;
	    default:
		throw new IllegalArgumentException("Language "+ lang + " not supported");
	}
    }
    
    private DocumentParserPool getParserPoolForLanguage(Language lang)
    {
	switch (lang)
	{
	    case ENGLISH:
		return stanfordParserEnglishPool;
	    default:
		throw new IllegalArgumentException("Language "+ lang + " not supported");
	}
    }
    
    public AbstractPipelineOperation performWebSearch(Language lang, String query, int numResults)
    {
	BasicWebSearchAndCrawlJobInput jobParams = new BasicWebSearchAndCrawlJobInput(lang, query, numResults, webCrawlExecutor, webSearchExecutor);
	BasicWebSearchAndCrawlJob newJob = new BasicWebSearchAndCrawlJob(jobParams);
	BasicPipelineOperation result = new BasicPipelineOperation(newJob, PipelineOperationType.WEB_SEARCH_CRAWL);
	
	return result;
    }
    
    public AbstractPipelineOperation performSearchResultParsing(Language lang, List<SearchResult> searchResults)
    {
	List<AbstractDocumentSource> sources = new ArrayList<>();
	for (SearchResult itr : searchResults)
	    sources.add(new SearchResultDocumentSource(itr));
	
	BasicDocumentParseJobInput jobParams = new BasicDocumentParseJobInput(lang,
									    sources, 
									    docParseExecutor, 
									    getParserPoolForLanguage(lang),
									    getStrategyForLanguage(lang));
	BasicDocumentParseJob newJob = new BasicDocumentParseJob(jobParams);
	BasicPipelineOperation result = new BasicPipelineOperation(newJob, PipelineOperationType.PARSE_DOCUMENTS);
	
	return result;
    }
}

class BasicPipelineOperation implements AbstractPipelineOperation
{
    class JobCompletionListener implements Runnable
    {
	private final BasicPipelineOperation				source;
	private final AbstractPipelineOperationCompletionListener	callback;

	public JobCompletionListener(BasicPipelineOperation source, AbstractPipelineOperationCompletionListener callback)
	{
	    this.source = source;
	    this.callback = callback;
	}
	
	@Override
	public void run() {
	    callback.handleCompletion(source);
	}
    }
    
    private final AbstractJob		    job;
    private final PipelineOperationType	    type;
    
    public BasicPipelineOperation(AbstractJob job, PipelineOperationType type) 
    {
	this.job = job;
	this.type = type;
    }
    
    @Override
    public void begin() {
	job.begin();
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
    public String toString() {
	return job.toString();
    }

    @Override
    public boolean isCompleted() {
	return job.isCompleted();
    }

    @Override
    public void registerCompletionListener(AbstractPipelineOperationCompletionListener listener)
    {
	// immediately execute the callback if the job's already complete
	if (job.registerCompletionListener(new JobCompletionListener(this, listener)) == false)
	    listener.handleCompletion(this);
    }
    
    @Override
    public PipelineOperationType getType() {
	return type;
    }

    @Override
    public Object getOutput() {
	return job.getOutput();
    }
}