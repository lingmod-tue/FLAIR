package com.flair.server.taskmanager;

import com.flair.server.crawler.SearchResult;
import com.flair.server.parser.AbstractDocument;
import com.flair.server.parser.DocumentCollection;

public interface SearchCrawlParseOperation extends AbstractPipelineOperation
{
	public interface CrawlComplete {
		public void handle(SearchResult result);
	}
	
	public interface ParseComplete {
		public void handle(AbstractDocument result);
	}
	
	public interface JobComplete {
		public void handle(DocumentCollection result);
	}
	
	public void			setCrawlCompleteHandler(CrawlComplete handler);
	public void			setParseCompleteHandler(ParseComplete handler);
	public void			setJobCompleteHandler(JobComplete handler);
}

class SearchCrawlParseOperationImpl extends BasicPipelineOperation implements SearchCrawlParseOperation
{
	private CrawlComplete		crawlC;
	private ParseComplete		parseC;
	private JobComplete			jobC;
	
	public SearchCrawlParseOperationImpl(SearchCrawlParseJobInput input) 
	{
		super(new SearchCrawlParseJob(input), PipelineOperationType.SEARCH_CRAWL_PARSE);
		
		crawlC = null;
		parseC = null;
		jobC = null;
	}
	
	@Override
	public void begin()
	{
		// register listener
		SearchCrawlParseJob j = (SearchCrawlParseJob)job;
		j.addListener(e -> {
			switch (e.type)
			{
			case JOB_COMPLETE:
				if (jobC != null)
					jobC.handle(e.jobOutput.parsedDocs);
				
				break;
			case PARSE_COMPLETE:
				if (parseC != null)
					parseC.handle(e.parsedDoc);
				
				break;
			case WEB_CRAWL_COMPLETE:
				if (crawlC != null)
					crawlC.handle(e.crawledResult);
				
				break;			
			}
		});;
		
		super.begin();
	}

	@Override
	public void setCrawlCompleteHandler(CrawlComplete handler) {
		crawlC = handler;
	}

	@Override
	public void setParseCompleteHandler(ParseComplete handler) {
		parseC = handler;	
	}

	@Override
	public void setJobCompleteHandler(JobComplete handler) {
		jobC = handler;
	}
}