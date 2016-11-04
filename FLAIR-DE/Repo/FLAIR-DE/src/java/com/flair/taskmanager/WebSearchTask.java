/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.taskmanager;

import com.flair.crawler.SearchResult;
import com.flair.crawler.WebSearchAgent;
import com.flair.utilities.FLAIRLogger;
import java.util.List;

/**
 * Fetchs search results for a given query
 * @author shadeMe
 */
class WebSearchTask extends AbstractTask
{
    private final WebSearchAgent	    input;
    private final int			    numResults;
    
    public WebSearchTask(AbstractJob job, AbstractTaskContinuation continuation, WebSearchAgent source, int numResults)
    {
	super(job, TaskType.FETCH_SEARCHRESULTS, continuation);
	this.input = source;
	this.numResults = numResults;
    }
    
    @Override
    protected AbstractTaskResult performTask()
    {
	List<SearchResult> hits = input.getNext(numResults);
	WebSearchTaskResult result = new WebSearchTaskResult(hits, input);
	FLAIRLogger.get().trace("Web Search for '" + input.getQuery() + "' fetched " + hits.size() + " results");
	return result;
    }
}

class WebSearchTaskResult extends AbstractTaskResult
{
    private final List<SearchResult>	    output;
    private final WebSearchAgent	    agent;
    
    public WebSearchTaskResult(List<SearchResult> output, WebSearchAgent agent)
    {
	super(TaskType.FETCH_SEARCHRESULTS);
	this.output = output;
	this.agent = agent;
    }
    
    public List<SearchResult> getOutput() {
	return output;
    }
    
    public WebSearchAgent getAgent() {
	return agent;
    }
}


class WebSearchTaskExecutor extends AbstractTaskExecutor
{
    public WebSearchTaskExecutor() {
	super(Constants.TEXTFETCHER_THREADPOOL_SIZE);
    }
    
    public void search(WebSearchTask task) {
	queue(task);
    }
}