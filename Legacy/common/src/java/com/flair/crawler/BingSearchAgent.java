package com.flair.crawler;

import com.flair.crawler.impl.AbstractSearchAgentImplResult;
import com.flair.crawler.impl.azure.AzureWebSearch;
import com.flair.grammar.Language;
import com.flair.utilities.FLAIRLogger;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Bing Search engine
 * @author shadeMe
 */
class BingSearchAgent extends CachingSearchAgent
{
    private static final String			API_KEY = "68d6959bdcc34cfba34eb9d366ca8743";
    private static final int			RESULTS_PER_PAGE = 100;
    private static final int		MAX_API_REQUESTS	= 2;
    private final AzureWebSearch		pipeline;
    
    public BingSearchAgent(Language lang, String query)
    {
	super(lang, query, MAX_API_REQUESTS);
	this.pipeline = new AzureWebSearch();
	
	String qPrefix = "";
	String qPostfix = " language:en";
	String market = "";

	switch (lang)
	{
	    case ENGLISH:
		qPostfix = " language:en";
		market = "en-US";
		break;
	    case GERMAN:
		qPostfix = " language:de";
		market = "de-DE";
		break;
	    default:
		throw new IllegalArgumentException("Unsupported language " + lang);
	}

	pipeline.setApiKey(API_KEY);
	pipeline.setQuery(qPrefix + query + qPostfix);
	pipeline.setPerPage(RESULTS_PER_PAGE);
	pipeline.setMarket(market);
    }
    
    @Override
    protected List<? extends AbstractSearchAgentImplResult> invokeSearchApi()
    {
	List<? extends AbstractSearchAgentImplResult> azureResults = new ArrayList<>();
	if (noMoreResults == false)
	{
	    try 
	    {
		// the pipeline can potentially throw a java.net.UnknownHostException, so wrap it in EH to be safe 
		pipeline.setPage(nextPage);
		azureResults = pipeline.performSearch();
	    }
	    catch (Throwable e) 
	    {
		FLAIRLogger.get().error("Bing search API encountered a fatal error. Exception: " + e.getMessage());
		noMoreResults = true;
	    }
	}
	
	return azureResults;
    }
}
