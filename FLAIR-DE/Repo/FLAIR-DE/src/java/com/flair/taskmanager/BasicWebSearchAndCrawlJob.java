/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.taskmanager;

import com.flair.crawler.SearchResult;
import com.flair.crawler.WebSearchAgent;
import com.flair.crawler.WebSearchAgentFactory;
import com.flair.grammar.Language;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Searches the web for a particular query in a given language
 * @author shadeMe
 */
class BasicWebSearchAndCrawlJobInput
{
    public final Language			    sourceLanguage;
    public final String				    query;
    public final int				    numResults;
    public final WebCrawlTaskExecutor		    webCrawlerExecutor;
    public final WebSearchTaskExecutor		    webSearchExecutor;

    public BasicWebSearchAndCrawlJobInput(Language sourceLanguage,
					  String query,
					  int numResults,
					  WebCrawlTaskExecutor webCrawlerExecutor,
					  WebSearchTaskExecutor webSearchExecutor)
    {
	this.sourceLanguage = sourceLanguage;
	this.query = query;
	this.numResults = numResults;
	this.webCrawlerExecutor = webCrawlerExecutor;
	this.webSearchExecutor = webSearchExecutor;
    }
}

class BasicWebSearchAndCrawlJobOutput
{
    public final List<SearchResult>	searchResults;
    
    public BasicWebSearchAndCrawlJobOutput() {
	this.searchResults = new ArrayList<>();
    }
}

class BasicWebSearchAndCrawlJob extends AbstractTaskLinkingJob 
{
    private static final int				    MINIMUM_TOKEN_COUNT = 100;	    // in the page text
    
    private final BasicWebSearchAndCrawlJobInput	    input;
    private final BasicWebSearchAndCrawlJobOutput	    output;
    
    public BasicWebSearchAndCrawlJob(BasicWebSearchAndCrawlJobInput in)
    {
	super();
	this.input = in;
	this.output = new BasicWebSearchAndCrawlJobOutput();
    }
    
    @Override
    public Object getOutput()
    {
	waitForCompletion();
	return output.searchResults;
    }
    
    @Override
    public void begin() 
    {
	if (isStarted())
	    throw new IllegalStateException("Job has already begun");
	
	WebSearchAgent agent = WebSearchAgentFactory.create(WebSearchAgentFactory.SearchAgent.BING,
							    input.sourceLanguage,
							    input.query);
	
	WebSearchTask newTask = new WebSearchTask(this, new BasicTaskChain(this), agent, input.numResults);
	registerTask(newTask);
	input.webSearchExecutor.search(newTask);
	flagStarted();
    }

    @Override
    public void performLinking(TaskType previousType, AbstractTaskResult previousResult)
    {
	switch (previousType)
	{
	    case FETCH_SEARCHRESULTS:
	    {
		// create a new web crawl job with the output and wait for completion
		// if there are any delinquents, create a new search task with the remainder
		WebSearchTaskResult result = (WebSearchTaskResult)previousResult;
		BasicWebCrawlJobInput crawlParams = new BasicWebCrawlJobInput(result.getOutput(), input.webCrawlerExecutor);
		BasicWebCrawlJob crawlJob = new BasicWebCrawlJob(crawlParams);
		crawlJob.begin();
		
		BasicWebCrawlJobOutput crawlOutput = (BasicWebCrawlJobOutput)crawlJob.getOutput();
		synchronized(output)
		{
		    for (SearchResult itr : crawlOutput.crawledResults)
		    {
			// quick/naive word count
			StringTokenizer tokenizer = new StringTokenizer(itr.getPageText(), " ");
			if (MINIMUM_TOKEN_COUNT < tokenizer.countTokens())
			    output.searchResults.add(itr);
		    }
		}
		
		if (result.getAgent().hasNoMoreResults() == false &&
		    output.searchResults.size() < input.numResults)
		{
		    int delta = input.numResults - output.searchResults.size();
		    if (delta > 0)
		    {
			WebSearchTask newTask = new WebSearchTask(this, new BasicTaskChain(this), result.getAgent(), delta);
			registerTask(newTask);
			input.webSearchExecutor.search(newTask);
		    }
		}
		else
		{
		    int i = 1;
		    for (SearchResult itr : output.searchResults)
		    {
			itr.setRank(i);
			i++;
		    }
		}
		
		break;
	    }
	}
    }

    @Override
    public String toString()
    {
	if (isStarted() == false)
	    return "BasicWebSearchAndCrawlJob has not started yet";
	else if (isCompleted() == false)
	    return "BasicWebSearchAndCrawlJob is still running";
	else if (isCancelled())
	    return "BasicWebSearchAndCrawlJob was cancelled";
	else
	    return "BasicWebSearchAndCrawlJob Output:\nInput:\n\tLanguage: " + input.sourceLanguage + "\n\tQuery: " + input.query + "\n\tRequired Results: " + input.numResults + "\nOutput\n\tSearch Results: " + output.searchResults.size();
    }
}

