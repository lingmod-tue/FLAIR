/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.taskmanager;

import com.flair.crawler.SearchResult;
import com.flair.utilities.FLAIRLogger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Fetches a SearchResult's text
 * @author shadeMe
 */
class WebCrawlTask extends AbstractTask
{
    private final SearchResult		    input;
    private ExecutorService		    fetchExecutor;
    
    private static final int		    TIMEOUT_SECONDS = 10;
    
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
    
    public WebCrawlTask(AbstractJob job, AbstractTaskContinuation continuation, SearchResult source)
    {
	super(job, TaskType.FETCH_SEARCHRESULT_TEXT, continuation);
	
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
	
	WebCrawlTaskResult result = new WebCrawlTaskResult(input);
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

class WebCrawlTaskResult extends AbstractTaskResult
{
    private final SearchResult		    output;
    
    public WebCrawlTaskResult(SearchResult output)
    {
	super(TaskType.FETCH_SEARCHRESULT_TEXT);
	this.output = output;
    }
    
    public SearchResult getOutput() {
	return output;
    }
    
    public boolean wasSuccessful() {
	return output.isTextFetched();
    }
}


class WebCrawlTaskExecutor extends AbstractTaskExecutor
{
    private final ExecutorService	    auxThreadPool;	    // silly workaround to prevent the I/O op from blocking perpetually
								    // wouldn't be an issue if the boilerpipe API had a timeout param
    public WebCrawlTaskExecutor()
    {
	super(Constants.TEXTFETCHER_THREADPOOL_SIZE);
	auxThreadPool = Executors.newFixedThreadPool(Constants.TEXTFETCHER_THREADPOOL_SIZE);
    }
    
    public void crawl(List<WebCrawlTask> tasks)
    {
	// ### TODO needs to be fair when handling requests from multiple clients
	// e.g, the first client requests some 100 results but the second only wants 10 or so
	if (tasks.isEmpty())
	    return;
	
	for (WebCrawlTask itr : tasks)
	    itr.setFetchExecutor(auxThreadPool);
	
	List<AbstractTask> collection = new ArrayList<>();
	collection.addAll(tasks);
	queue(collection);
    }
}