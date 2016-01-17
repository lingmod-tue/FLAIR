package com.flair.crawler;

import com.flair.grammar.Language;
import java.util.ArrayList;

/**
 * Abstract base class for a web search executor
 * @author shadeMe
 */
public abstract class WebSearchAgent
{
    public static final int			MAX_SEARCH_RESULTS = 100;
    public static final ArrayList<String>	BLACKLISTED_URLS = new ArrayList<>();
    
    protected final Language			lang;
    protected final String			query;
    protected final int				numResults;

    public WebSearchAgent(Language lang, String query, int numResults)
    {
	this.lang = lang;
	this.query = query;
	this.numResults = numResults;
    }
    
    public abstract boolean			hasResults();
    public abstract ArrayList<SearchResult>	getResults();
    public abstract void			performSearch();
    
    protected static boolean isBlacklistPopulated() {
	return BLACKLISTED_URLS.isEmpty() == false;
    }
    
    protected static void populateBlacklist()
    {
	if (BLACKLISTED_URLS.isEmpty() == true)
	{
	    BLACKLISTED_URLS.add("tmz.com");
	    BLACKLISTED_URLS.add("thefreedictionary.com");
	    BLACKLISTED_URLS.add("imdb.com");
	    BLACKLISTED_URLS.add("facebook.com");
	    BLACKLISTED_URLS.add("buzzfeed.com");
	}
    }
    
    protected static boolean isURLBlacklisted(String URL)
    {
	if (isBlacklistPopulated() == false)
	    populateBlacklist();
	
	for (String itr : BLACKLISTED_URLS)
	{
	    if (URL.contains(itr))
		return true;
	}
	
	return false;
    }
}
