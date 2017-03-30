/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.crawler;

import com.flair.crawler.impl.AbstractSearchAgentImplResult;
import com.flair.grammar.Language;
import com.flair.utilities.FLAIRLogger;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of WebSearchAgent that caches search results
 * @author shadeMe
 */
abstract class CachingSearchAgent extends WebSearchAgent
{
    protected int				nextPage;
    protected int				nextRank;
    protected final ArrayList<SearchResult>	cachedResults;
    protected boolean				noMoreResults;
    protected final ArrayList<SearchResult>	fetchedResults;	    // collection of all the results fetched by the agent
    
    public CachingSearchAgent(Language lang, String query)
    {
	super(lang, query);
	this.nextPage = 1;
	this.nextRank = 0;
	this.cachedResults = new ArrayList<>();
	this.noMoreResults = false;
	this.fetchedResults = new ArrayList<>();
    }
    
    @Override
    public final List<SearchResult> getNext(int numResults)
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
    
    protected final int consumeCache(List<SearchResult> dest, int numToConsume)
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
    
    protected final boolean isURLDuplicate(String url)
    {
	for (SearchResult itr: fetchedResults)
	{
	    if (itr.getDisplayURL().equalsIgnoreCase(url) == true)
		return true;
	}
	
	return false;
    }
    
    protected final void cacheNextPage()
    {
	if (noMoreResults == true)
	    return;
	
	List<? extends AbstractSearchAgentImplResult> apiResults = invokeSearchApi();
	if (apiResults.isEmpty())
	{
	    FLAIRLogger.get().info("No more results for query '" + query + "'");
	    noMoreResults = true;
	    return;
	}
	
	for (AbstractSearchAgentImplResult itr : apiResults)
	{
	    if (WebSearchAgent.isURLBlacklisted(itr.getDisplayUrl()) == true)
		FLAIRLogger.get().info("Blacklisted URL: " + itr.getDisplayUrl());
	    else if (isURLDuplicate(itr.getDisplayUrl()) == true)
		FLAIRLogger.get().info("Duplicate URL: " + itr.getDisplayUrl());
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
    public final boolean hasNoMoreResults() {
	return noMoreResults;
    }
    
    protected abstract List<? extends AbstractSearchAgentImplResult> invokeSearchApi();
}
