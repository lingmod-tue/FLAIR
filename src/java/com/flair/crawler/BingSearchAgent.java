package com.flair.crawler;

import com.flair.grammar.Language;
import com.flair.utilities.FLAIRLogger;
import java.util.ArrayList;
import java.util.List;
import net.billylieurance.azuresearch.AzureSearchResultSet;
import net.billylieurance.azuresearch.AzureSearchWebQuery;
import net.billylieurance.azuresearch.AzureSearchWebResult;

/**
 * Implementation of the Bing Search engine
 * @author shadeMe
 */
class BingSearchAgent extends WebSearchAgent
{
    private static final String			API_KEY = "CV3dQG6gOI3fO9wOHdArFimFprbt1Q3ZjMzYGhJaTFA";
    private static final int			RESULTS_PER_PAGE = 15;
    
    private final AzureSearchWebQuery		pipeline;
    private int					nextPage;
    private int					nextRank;
    private final ArrayList<SearchResult>	cachedResults;
    
    public BingSearchAgent(Language lang, String query)
    {
	super(lang, query);
	this.pipeline = new AzureSearchWebQuery();
	this.nextPage = 1;
	this.nextRank = 0;
	this.cachedResults = new ArrayList<>();
	
	String qPrefix = "";
	String qPostfix = " language:en";

	switch (lang)
	{
	    case ENGLISH:
		qPostfix = " language:en";
		break;
	    default:
		throw new IllegalStateException("Unsupported language " + lang);
	}

	pipeline.setAppid(API_KEY);
	pipeline.setQuery(qPrefix + query + qPostfix);
	pipeline.setPerPage(RESULTS_PER_PAGE);
    }
    

    @Override
    public List<SearchResult> getNext(int numResults)
    {
	List<SearchResult> output = new ArrayList<>();
	int consumedResults = consumeCache(output, numResults);
	while (consumedResults != numResults)
	{
	    cacheNextPage();
	    consumedResults = consumeCache(output, numResults - consumedResults);
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
    
    private void cacheNextPage()
    {
	pipeline.setPage(nextPage);
	pipeline.doQuery();
	nextPage++;
	
	AzureSearchResultSet<AzureSearchWebResult> azureResults = pipeline.getQueryResult();
	for (AzureSearchWebResult itr : azureResults)
	{
	    if (WebSearchAgent.isURLBlacklisted(itr.getUrl()) == true)
		FLAIRLogger.get().info("Blacklisted URL: " + itr.getUrl());
	    else
	    {
		SearchResult newResult = new SearchResult(lang,
							  query,
							  itr.getTitle(),
							  itr.getUrl(),
							  itr.getDisplayUrl(),
							  itr.getDescription());

		cachedResults.add(newResult);
		newResult.setRank(nextRank);
		nextRank++;
		
		FLAIRLogger.get().info("Result " + (nextRank - 1)  + ": " + itr.getTitle() + ", URL: " + itr.getUrl());
	    }
	}
    }
}
