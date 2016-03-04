package com.flair.crawler;

import com.flair.grammar.Language;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for a web search executor
 * @author shadeMe
 */
public abstract class WebSearchAgent
{
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
	    BLACKLISTED_URLS.add("youtube.com");
	    BLACKLISTED_URLS.add("twitter.com");
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
    
    
    public static final ArrayList<String>	BLACKLISTED_URLS = new ArrayList<>();
    
    protected final Language			lang;
    protected final String			query;

    public WebSearchAgent(Language lang, String query)
    {
	this.lang = lang;
	this.query = query;
    }
    
    public String getQuery() {
	return query;
    }
    
    public Language getLanguage() {
	return lang;
    }
    
    public abstract List<SearchResult>		getNext(int numResults);	// returns the next n results
}
