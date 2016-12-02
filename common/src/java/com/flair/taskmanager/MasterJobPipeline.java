/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.taskmanager;

import com.flair.grammar.Language;
import com.flair.parser.AbstractDocumentKeywordSearcherFactory;
import com.flair.parser.AbstractDocumentSource;
import com.flair.parser.AbstractParsingStrategyFactory;
import com.flair.parser.KeywordSearcherInput;
import com.flair.parser.KeywordSearcherType;
import com.flair.parser.MasterParsingFactoryGenerator;
import com.flair.parser.ParserType;
import com.flair.utilities.FLAIRLogger;
import java.util.List;

/**
 * Job scheduler for the web crawling/local parser frameworks
 * @author shadeMe
 */
public final class MasterJobPipeline
{   
    private static MasterJobPipeline	    SINGLETON = null;
    
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
    
    public static void dispose()
    {
	if (SINGLETON != null)
	{
	    SINGLETON.shutdown();
	    SINGLETON = null;
	}
    }
    
    private final WebSearchTaskExecutor				webSearchExecutor;
    private final WebCrawlTaskExecutor				webCrawlExecutor;
    private final DocumentParseTaskExecutor			docParseExecutor;
    
    private final DocumentParserPool				stanfordParserEnglishPool;
    private final DocumentParserPool				stanfordParserGermanPool;
    private final AbstractParsingStrategyFactory		stanfordEnglishStrategy;
    private final AbstractParsingStrategyFactory		stanfordGermanStrategy;
    
    private final AbstractDocumentKeywordSearcherFactory	naiveSubstringSearcher;
    
    private MasterJobPipeline()
    {
	this.webSearchExecutor = new WebSearchTaskExecutor();
	this.webCrawlExecutor = new WebCrawlTaskExecutor();
	this.docParseExecutor = new DocumentParseTaskExecutor();
	
	this.stanfordParserEnglishPool = new DocumentParserPool(MasterParsingFactoryGenerator.createParser(ParserType.STANFORD_CORENLP, Language.ENGLISH));
	this.stanfordEnglishStrategy = MasterParsingFactoryGenerator.createParsingStrategy(ParserType.STANFORD_CORENLP, Language.ENGLISH);
	
	this.stanfordParserGermanPool = new DocumentParserPool(MasterParsingFactoryGenerator.createParser(ParserType.STANFORD_CORENLP, Language.GERMAN));
	this.stanfordGermanStrategy = MasterParsingFactoryGenerator.createParsingStrategy(ParserType.STANFORD_CORENLP, Language.GERMAN);
//	this.stanfordParserGermanPool = null;
//	this.stanfordGermanStrategy = null;
	
	this.naiveSubstringSearcher = MasterParsingFactoryGenerator.createKeywordSearcher(KeywordSearcherType.NAIVE_SUBSTRING);
    }
    
    private void shutdown() 
    {
	webSearchExecutor.shutdown(false);
	webCrawlExecutor.shutdown(false);
	docParseExecutor.shutdown(false);
    }
    
    private AbstractParsingStrategyFactory getStrategyForLanguage(Language lang)
    {
	switch (lang)
	{
	    case ENGLISH:
		return stanfordEnglishStrategy;
	    case GERMAN:
		return stanfordGermanStrategy;
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
	    case GERMAN:
		return stanfordParserGermanPool;
	    default:
		throw new IllegalArgumentException("Language "+ lang + " not supported");
	}
    }
    
    public AbstractPipelineOperation performWebSearch(Language lang, String query, int numResults)
    {
	BasicWebSearchAndCrawlJobInput jobParams = new BasicWebSearchAndCrawlJobInput(lang,
										    query,
										    numResults,
										    webCrawlExecutor,
										    webSearchExecutor);
	BasicWebSearchAndCrawlJob newJob = new BasicWebSearchAndCrawlJob(jobParams);
	BasicPipelineOperation result = new BasicPipelineOperation(newJob, PipelineOperationType.WEB_SEARCH_CRAWL);
	
	return result;
    }
    
    public AbstractPipelineOperation performDocumentParsing(Language lang, List<AbstractDocumentSource> docsSources, KeywordSearcherInput keywords)
    {
	BasicDocumentParseJobInput jobParams = new BasicDocumentParseJobInput(lang,
									    docsSources, 
									    docParseExecutor, 
									    getParserPoolForLanguage(lang),
									    getStrategyForLanguage(lang),
									    naiveSubstringSearcher,
									    keywords);
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
    public void cancel() 
    {
	if (isCompleted() == false)
	{
	    job.cancel();
	    FLAIRLogger.get().info("Pipeline operation " + getType() + " was cancelled");
	}
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