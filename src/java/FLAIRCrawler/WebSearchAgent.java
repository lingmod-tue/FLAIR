package FLAIRCrawler;

import java.util.ArrayList;

/**
 *
 * @author shadeMe
 */
public interface WebSearchAgent
{
    public static final int			MAX_SEARCH_RESULTS = 100;
    public static final ArrayList<String>	BLACKLISTED_URLS = new ArrayList<>();
    
    public boolean			hasResults();
    public ArrayList<SearchResult>	getResults();
    public void				performSearch();
    
    public static boolean isBlacklistPopulated() {
	return BLACKLISTED_URLS.isEmpty() == false;
    }
    
    public static void populateBlacklist()
    {
	if (BLACKLISTED_URLS.isEmpty() == true)
	{
	    BLACKLISTED_URLS.add("tmz.com");
	    BLACKLISTED_URLS.add("thefreedictionary.com");
	}
    }
    
    public static boolean isURLBlacklisted(String URL)
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
