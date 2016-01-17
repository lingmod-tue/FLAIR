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
import com.flair.parser.DocumentCollection;
import com.flair.parser.MasterParsingFactoryGenerator;
import com.flair.parser.ParserType;
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
    
    private final WebCrawlerTaskExecutor	    webCrawlerExecutor;
    private final DocumentParsingTaskExecutor	    docParsingExecutor;
    
    private final DocumentParserPool		    stanfordParserEnglishPool;
    private final AbstractParsingStrategyFactory    stanfordEnglishStrategy;

    private MasterJobPipeline()
    {
	this.webCrawlerExecutor = new WebCrawlerTaskExecutor();
	this.docParsingExecutor = new DocumentParsingTaskExecutor();
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
    
    public AbstractPipelineOperation parseSearchResults(Language lang, List<SearchResult> searchResults)
    {
	WebDocumentParserJobInput jobParams = new WebDocumentParserJobInput(lang,
									    searchResults, 
									    webCrawlerExecutor,
									    docParsingExecutor, 
									    getParserPoolForLanguage(lang),
									    getStrategyForLanguage(lang));
	WebDocumentParserJob newJob = new WebDocumentParserJob(jobParams);
	BasicPipelineOperation result = new BasicPipelineOperation(newJob);
	newJob.begin();
	
	return result;
    }
    
    public AbstractPipelineOperation parseDocumentSources(Language lang, List<AbstractDocumentSource> docSources)
    {
	LocalDocumentParserJobInput jobParams = new LocalDocumentParserJobInput(lang,
									    docSources, 
									    docParsingExecutor, 
									    getParserPoolForLanguage(lang),
									    getStrategyForLanguage(lang));
	LocalDocumentParserJob newJob = new LocalDocumentParserJob(jobParams);
	BasicPipelineOperation result = new BasicPipelineOperation(newJob);
	newJob.begin();
	
	return result;
    }
}

class BasicPipelineOperation implements AbstractPipelineOperation
{
    private final AbstractJob	job;

    public BasicPipelineOperation(AbstractJob job) {
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
    public DocumentCollection getOutput() 
    {
	assert BasicParsingJobOutput.class.isAssignableFrom(job.getClass()) == true;
	return ((BasicParsingJobOutput)job).getParsedDocuments();
    }
    
    @Override
    public String toString() {
	return job.toString();
    }
}