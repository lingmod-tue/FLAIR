/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRTaskManager;

import FLAIRCrawler.SearchResult;

/**
 * Fetches a SearchResult's text
 * @author shadeMe
 */
class WebCrawlerTask extends AbstractTask
{
    private final SearchResult		    input;
    
    public WebCrawlerTask(AbstractJob job, AbstractTaskContinuation continuation, SearchResult source)
    {
	super(job, TaskType.FETCH_DOCUMENT_TEXT, continuation);
	
	this.input = source;
    }
    
    @Override
    protected AbstractTaskResult performTask()
    {
	WebCrawlerTaskResult result = new WebCrawlerTaskResult(input);
	if (input.isTextFetched() == false)
	    input.fetchPageText(false);
	
	return result;
    }
}

class WebCrawlerTaskResult extends AbstractTaskResult
{
    private final SearchResult		    output;
    // ### TODO store the language here too, as the parsing strategy is dependent on it (when multiple lang support is implemented)
    
    public WebCrawlerTaskResult(SearchResult output)
    {
	super(TaskType.FETCH_DOCUMENT_TEXT);
	this.output = output;
    }
    
    public SearchResult getOutput() {
	return output;
    }
}
