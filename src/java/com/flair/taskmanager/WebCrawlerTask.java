/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.taskmanager;

import com.flair.crawler.SearchResult;
import com.flair.utilities.FLAIRLogger;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Fetches a SearchResult's text
 * @author shadeMe
 */
class WebCrawlerTask extends AbstractTask
{
    private final SearchResult		    input;
    private ExecutorService		    fetchExecutor;
    
    private static final int		    TIMEOUT_SECONDS = 15;
    
    class FetchRunnable implements Callable<Boolean>
    {
	private final SearchResult	    source;

	public FetchRunnable(SearchResult source) {
	    this.source = source;
	}
	
	@Override
	public Boolean call()
	{
	    if (source.isTextFetched() == false)
		return input.fetchPageText(false);
	    else
		return true;
	}
    }
    
    public WebCrawlerTask(AbstractJob job, AbstractTaskContinuation continuation, SearchResult source)
    {
	super(job, TaskType.FETCH_DOCUMENT_TEXT, continuation);
	
	this.input = source;
	this.fetchExecutor = null;
    }
    
    public void setFetchExecutor(ExecutorService executor) {
	fetchExecutor = executor;
    }
    
    @Override
    protected AbstractTaskResult performTask()
    {
	if (fetchExecutor == null)
	    throw new IllegalStateException("Auxiliary threadpool not set");
	
	WebCrawlerTaskResult result = new WebCrawlerTaskResult(input);
	FutureTask<Boolean> fetchWrapper = new FutureTask<>(new FetchRunnable(input));
	try {
	    fetchExecutor.submit(fetchWrapper).get(TIMEOUT_SECONDS, TimeUnit.SECONDS);
	}
	catch (TimeoutException ex) {
	    FLAIRLogger.get().error("Fetch text timed out for URL: " + input.getURL());
	}
	catch (InterruptedException | ExecutionException ex) {
	}
	
	FLAIRLogger.get().trace("Search Result (" + input.getURL() + ") text fetched: " + result.wasSuccessful());
	return result;
    }
}

class WebCrawlerTaskResult extends AbstractTaskResult
{
    private final SearchResult		    output;
    
    public WebCrawlerTaskResult(SearchResult output)
    {
	super(TaskType.FETCH_DOCUMENT_TEXT);
	this.output = output;
    }
    
    public SearchResult getOutput() {
	return output;
    }
    
    public boolean wasSuccessful() {
	return output.isTextFetched();
    }
}
