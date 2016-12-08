package com.flair.crawler;

import com.flair.crawler.azurewebsearchv5.AzureWebSearch;
import com.flair.crawler.azurewebsearchv5.AzureWebSearchResult;
import com.flair.grammar.Language;
import com.flair.utilities.FLAIRLogger;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Bing Search engine
 * @author shadeMe
 */
class BingSearchAgent extends WebSearchAgent
{
    private static final String			API_KEY = "d7df76de918b4e9ab4700492a3c65b77";
    private static final int			RESULTS_PER_PAGE = 10;
    
    private final AzureWebSearch		pipeline;
    private int					nextPage;
    private int					nextRank;
    private final ArrayList<SearchResult>	cachedResults;
    private boolean				noMoreResults;
    private final ArrayList<SearchResult>	fetchedResults;	    // collection of all the results fetched by the agent
    
    public BingSearchAgent(Language lang, String query)
    {
	super(lang, query);
	this.pipeline = new AzureWebSearch();
	this.nextPage = 1;
	this.nextRank = 0;
	this.cachedResults = new ArrayList<>();
	this.noMoreResults = false;
	this.fetchedResults = new ArrayList<>();
	
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
    public List<SearchResult> getNext(int numResults)
    {
	List<SearchResult> output = new ArrayList<>();
	int consumedResults = consumeCache(output, numResults);
	while (noMoreResults == false && consumedResults != numResults)
	{
	    cacheNextPage();
	    consumedResults += consumeCache(output, numResults - consumedResults);
	}
	
	return output;
    }
    
    private int consumeCache(List<SearchResult> dest, int numToConsume)
    {
	List<SearchResult> toRemove = new ArrayList<>();
	int numLeft = numToConsume;
	
	for (SearchResult itr : cachedResults)
	{
	    if (numLeft > 0)
	    {
		dest.add(itr);
		toRemove.add(itr);
		numLeft--;
	    }
	    else
		break;
	}
	
	for (SearchResult itr : toRemove)
	    cachedResults.remove(itr);
	
	return numToConsume - numLeft;
    }
    
    private boolean isURLDuplicate(String url)
    {
	for (SearchResult itr: fetchedResults)
	{
	    if (itr.getURL().equalsIgnoreCase(url) == true)
		return true;
	}
	
	return false;
    }
    
    private void cacheNextPage()
    {
	if (noMoreResults == true)
	    return;
	
	List<AzureWebSearchResult> azureResults = null;
	try 
	{
	    // the pipeline can potentially throw a java.net.UnknownHostException, so wrap it in EH to be safe 
	    pipeline.setPage(nextPage);
	    pipeline.doQuery();
	    azureResults = pipeline.getResults();
	}
	catch (Exception e) {
	    FLAIRLogger.get().error("Bing search API encountered a fatal error. Exception: " + e.getMessage());
	    noMoreResults = true;
	    return;
	}
	
	if (azureResults.isEmpty())
	{
	    FLAIRLogger.get().info("No more results for query '" + query + "'");
	    noMoreResults = true;
	    return;
	}
	
	for (AzureWebSearchResult itr : azureResults)
	{
	    // ### the regular url redirects through bing.com, so use the diplay url (which is generally what we want any way)
	    if (WebSearchAgent.isURLBlacklisted(itr.getDisplayUrl()) == true)
		FLAIRLogger.get().info("Blacklisted URL: " + itr.getUrl());
	    else if (isURLDuplicate(itr.getDisplayUrl()) == true)
		FLAIRLogger.get().info("Duplicate URL: " + itr.getUrl());
	    else
	    {
		SearchResult newResult = new SearchResult(lang,
							  query,
							  itr.getTitle(),
							  itr.getUrl(),
							  itr.getDisplayUrl(),
							  itr.getDescription());

		cachedResults.add(newResult);
		fetchedResults.add(newResult);
		newResult.setRank(nextRank);
		nextRank++;

		FLAIRLogger.get().info("Result " + (nextRank - 1)  + ": " + itr.getTitle() + ", URL: " + itr.getDisplayUrl());
	    }
	}

	nextPage++;	
    }

    @Override
    public boolean hasNoMoreResults() {
	return noMoreResults;
    }
}
