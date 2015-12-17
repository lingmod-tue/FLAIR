package FLAIRCrawler;

import java.util.ArrayList;
import net.billylieurance.azuresearch.AzureSearchResultSet;
import net.billylieurance.azuresearch.AzureSearchWebQuery;
import net.billylieurance.azuresearch.AzureSearchWebResult;

/**
 *
 * @author shadeMe
 */
public class BingSearchAgent implements WebSearchAgent
{
    private static final String		API_KEY = "CV3dQG6gOI3fO9wOHdArFimFprbt1Q3ZjMzYGhJaTFA";
    private static final int		RESULTS_PER_PAGE = 15;
    
    private final String			query;
    private final int				numResults;
    private int					parsedResults;
    
    private final ArrayList<SearchResult>	cachedResults;
    
    public BingSearchAgent(String query, int noOfResults)
    {
	this.query = query;
	this.numResults = noOfResults;
	this.parsedResults = 0;
	
	this.cachedResults = new ArrayList<>();
    }
    
    
    public boolean hasResults() {
	return cachedResults.isEmpty() == false;
    }
    
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
		SearchResult newResult = new SearchResult(query,
							  itr.getTitle(),
							  itr.getUrl(),
							  itr.getDisplayUrl(),
							  itr.getDescription());

		cachedResults.add(newResult);
		parsedResults++;
	    }
	    else
		break;
	}
    }
    
    public void performSearch()
    {
	AzureSearchWebQuery azureQuery = new AzureSearchWebQuery();
        String qPrefix = "about ";
        String qPostfix = " language:en";

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
		    delinquents.add(itr);
	    }
	    
	    cachedResults.removeAll(delinquents);
	    parsedResults -= delinquents.size();
	    delinquents.clear();
	}
    }
}
