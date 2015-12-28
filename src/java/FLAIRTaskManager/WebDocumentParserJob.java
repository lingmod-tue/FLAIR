/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRTaskManager;

import FLAIRCrawler.SearchResult;
import FLAIRParser.AbstractParsingStrategy;
import FLAIRParser.DocumentCollection;
import FLAIRParser.SearchResultDocumentSource;
import FLAIRParser.StanfordDocumentParserEnglishStrategy;
import java.util.ArrayList;
import java.util.List;

/**
 * Takes a list of search results, crawls their text and parses them
 * @author shadeMe
 */
class WebDocumentParserJob extends AbstractTaskLinkingJob
{
    private final WebDocumentParserJobInput	    input;
    private final WebDocumentParserJobOutput	    output;
    
    public WebDocumentParserJob(WebDocumentParserJobInput in)
    {
	super();
	this.input = in;
	this.output = new WebDocumentParserJobOutput();
    }
    
    public WebDocumentParserJobOutput getOutput()
    {
	waitForCompletion();
	return output;
    }
    
    @Override
    public void begin() 
    {
	assert jobStarted == false;
	jobStarted = true;
	
	List<WebCrawlerTask> tasks = new ArrayList<>();
	for (SearchResult itr : input.searchResults)
	{
	    WebCrawlerTask newTask = new WebCrawlerTask(this, new GenericTaskChain(this), itr);
	    registerTask(newTask);
	    tasks.add(newTask);
	}
	
	input.webCrawlerExecutor.crawl(tasks);
    }

    @Override
    public void performLinking(TaskType previousType, AbstractTaskResult previousResult)
    {
	switch (previousType)
	{
	    case FETCH_DOCUMENT_TEXT:
	    {
		// crawled, now parse
		// ### TODO refactor to be more generic
		WebCrawlerTaskResult result = (WebCrawlerTaskResult)previousResult;
		AbstractParsingStrategy strategy = new StanfordDocumentParserEnglishStrategy();

		DocumentParsingTask newTask = new DocumentParsingTask(this,
								      new GenericTaskChain(this),
								      new SearchResultDocumentSource(result.getOutput()),
								      strategy);


		registerTask(newTask);
		input.docParsingExecutor.queue(newTask);
		break;
	    }
	    case PARSE_DOCUMENT:
	    {
		// add the result to the doc collection
		DocumentParsingTaskResult result = (DocumentParsingTaskResult)previousResult;
		output.parsedDocs.add(result.getOutput(), true);
		break;
	    }
	}
    }
}

class WebDocumentParserJobInput
{
    public final List<SearchResult>		    searchResults;
    public final WebCrawlerTaskExecutor		    webCrawlerExecutor;
    public final DocumentParsingTaskExecutor	    docParsingExecutor;
    
    public WebDocumentParserJobInput(List<SearchResult> searchResults,
				     WebCrawlerTaskExecutor webCrawler,
				     DocumentParsingTaskExecutor docParser)
    {
	this.searchResults = searchResults;
	this.webCrawlerExecutor = webCrawler;
	this.docParsingExecutor = docParser;
    }
}

class WebDocumentParserJobOutput
{
    public final DocumentCollection	parsedDocs;
    
    public WebDocumentParserJobOutput() {
	this.parsedDocs = new DocumentCollection();
    }
}