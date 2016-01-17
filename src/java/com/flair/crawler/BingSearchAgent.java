package com.flair.crawler;

import com.flair.grammar.Language;
import com.flair.utilities.FLAIRLogger;
import java.util.ArrayList;
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
    
    private int					parsedResults;
    private final ArrayList<SearchResult>	cachedResults;
    
    public BingSearchAgent(Language lang, String query, int noOfResults)
    {
	super(lang, query, noOfResults);
	this.parsedResults = 0;
	this.cachedResults = new ArrayList<>();
    }
    
    @Override
    public boolean hasResults() {
	return cachedResults.isEmpty() == false;
    }
    
    @Override
    public ArrayList<SearchResult> getResults() {
	return cachedResults;
    }
    
    private void querySearch(AzureSearchWebQuery azureQuery, int pageNo)
    {
	azureQuery.setPage(pageNo);
	azureQuery.doQuery();

	AzureSearchResultSet<AzureSearchWebResult> azureResults = azureQuery.getQueryResult();
	for (AzureSearchWebResult itr : azureResults)
	{
	    if (parsedResults < numResults)
	    {
		SearchResult newResult = new SearchResult(lang,
							  query,
							  itr.getTitle(),
							  itr.getUrl(),
							  itr.getDisplayUrl(),
							  itr.getDescription());

		cachedResults.add(newResult);
		parsedResults++;
		
		FLAIRLogger.get().info("Result " + parsedResults + ": " + itr.getTitle() + ", URL: " + itr.getUrl());
	    }
	    else
		break;
	}
    }
    
    @Override
    public void performSearch()
    {
	long startTime = System.currentTimeMillis();
	FLAIRLogger.get().trace("Peforming BING search on query: " + query);
	FLAIRLogger.get().indent();
	{
	    AzureSearchWebQuery azureQuery = new AzureSearchWebQuery();
	    String qPrefix = "about ";
	    String qPostfix = " language:en";

	    switch (lang)
	    {
		case ENGLISH:
		    qPostfix = " language:en";
		    break;
		default:
		    throw new IllegalStateException("Unsupported language " + lang);
	    }

	    azureQuery.setAppid(API_KEY);
	    azureQuery.setQuery(qPrefix + query + qPostfix);
	    azureQuery.setPerPage(RESULTS_PER_PAGE);

	    parsedResults = 0;
	    int pageNo = 1;
	    ArrayList<SearchResult> delinquents = new ArrayList<>();

	    while (parsedResults < numResults)
	    {
		for (;parsedResults < numResults; pageNo++)
		   querySearch(azureQuery, pageNo);

		// run the results through the blacklist, remove delinquents, repeat 'til we have the reqd. no of results
		for (SearchResult itr : cachedResults)
		{
		    if (WebSearchAgent.isURLBlacklisted(itr.getURL()) == true)
		    {
			delinquents.add(itr);
			FLAIRLogger.get().info("Blacklisted URL: " + itr.getURL());
		    }
		}

		cachedResults.removeAll(delinquents);
		parsedResults -= delinquents.size();
		delinquents.clear();
	    }
	}
	FLAIRLogger.get().exdent();
	
	long endTime = System.currentTimeMillis();
	FLAIRLogger.get().trace("BING search complete in " + (endTime - startTime) + " ms");
    }
}
