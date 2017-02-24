/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.crawler;

import com.flair.crawler.impl.AbstractSearchAgentImplResult;
import com.flair.crawler.impl.faroo.FarooSearch;
import com.flair.grammar.Language;
import com.flair.utilities.FLAIRLogger;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Faroo search engine 
 * @author shadeMe
 */
public class FarooSearchAgent extends CachingSearchAgent
{
    private static final String		API_KEY = "";
    private static final int		RESULTS_PER_PAGE = 10;
    
    public static enum Source
    {
	WEB, NEWS, TOPICS, TRENDS
    }
    
    private final FarooSearch		pipeline;
    
    public FarooSearchAgent(Language lang, String query)
    {
	super(lang, query);
	this.pipeline = new FarooSearch();
	
	switch (lang)
	{
	    case ENGLISH:
		pipeline.setLang(FarooSearch.Language.ENGLISH);
		break;
	    case GERMAN:
		pipeline.setLang(FarooSearch.Language.GERMAN);
		break;
	    default:
		throw new IllegalArgumentException("Unsupported language " + lang);
	}

	pipeline.setApiKey(API_KEY);
	pipeline.setQuery(query);
	pipeline.setPerPage(RESULTS_PER_PAGE);
    }
    
    public boolean isTrending() {
	return pipeline.isTrending();
    }

    public void setTrending(boolean trending) {
	pipeline.setTrending(trending);
    }
    
    public void setSearchSource(Source source)
    {
	switch (source)
	{
	    case WEB:
		pipeline.setSource(FarooSearch.SearchSource.WEB);
		break;
	    case NEWS:
		pipeline.setSource(FarooSearch.SearchSource.NEWS);
		break;
	    case TOPICS:
		pipeline.setSource(FarooSearch.SearchSource.TOPICS);
		break;
	    case TRENDS:
		pipeline.setSource(FarooSearch.SearchSource.TRENDS);
		break;
	}
    }
    
    @Override
    protected List<? extends AbstractSearchAgentImplResult> invokeSearchApi()
    {
	List<? extends AbstractSearchAgentImplResult> azureResults = new ArrayList<>();
	if (noMoreResults == false)
	{
	    try 
	    {
		pipeline.setPage(nextPage);
		azureResults = pipeline.performSearch();
	    }
	    catch (Throwable e) 
	    {
		FLAIRLogger.get().error("Faroo search API encountered a fatal error. Exception: " + e.getMessage());
		noMoreResults = true;
	    }
	}
	
	return azureResults;
    }
}
