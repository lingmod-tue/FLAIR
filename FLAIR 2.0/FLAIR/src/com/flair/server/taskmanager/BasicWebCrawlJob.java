/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.taskmanager;

import java.util.ArrayList;
import java.util.List;

import com.flair.server.crawler.SearchResult;

/**
 * Fetches the text of search results
 * @author shadeMe
 */
class BasicWebCrawlJobInput
{
    public final List<SearchResult>		    searchResults;
    public final WebCrawlTaskExecutor		    webCrawlerExecutor;
    
    public BasicWebCrawlJobInput(List<SearchResult> searchResults, WebCrawlTaskExecutor webCrawler)
    {
	this.searchResults = searchResults;
	this.webCrawlerExecutor = webCrawler;
    }
}

class BasicWebCrawlJobOutput
{
    public final List<SearchResult>	crawledResults;
    public final List<SearchResult>	delinquents;
    
    public BasicWebCrawlJobOutput()
    {
	this.crawledResults = new ArrayList<>();
	this.delinquents = new ArrayList<>();
    }
}

class BasicWebCrawlJob extends AbstractTaskLinkingJob
{
    private final BasicWebCrawlJobInput	    input;
    private final BasicWebCrawlJobOutput    output;
    
    public BasicWebCrawlJob(BasicWebCrawlJobInput in)
    {
	super();
	this.input = in;
	this.output = new BasicWebCrawlJobOutput();
    }
    
    @Override
    public Object getOutput()
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
		WebCrawlTaskResult result = (WebCrawlTaskResult)previousResult;
		if (result.wasSuccessful() == false)
		    output.delinquents.add(result.getOutput());
		else
		    output.crawledResults.add(result.getOutput());
		
		break;
	    }
	}
    }

    @Override
    public String toString()
    {
	if (isStarted() == false)
	    return "BasicWebCrawlJob has not started yet";
	else if (isCompleted() == false)
	    return "BasicWebCrawlJob is still running";
	else if (isCancelled())
	    return "BasicWebCrawlJob was cancelled";
	else
	    return "BasicWebCrawlJob Output:\nInput:\n\tSearch Results: " + input.searchResults.size() + "\nOutput:\n\tCrawled Results: " + output.crawledResults.size() + "\n\tDelinquents: " + output.delinquents.size();
    }
}

