/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.crawler.azurewebsearchv5;

import com.flair.utilities.FLAIRLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/**
 * Basic web search implementation that uses the Azure Search API
 * @author shadeMe
 */
public class AzureWebSearch
{     
    private static final String	    AZURESEARCH_SCHEME = "https";   
    private static final int	    AZURESEARCH_PORT = 443;   
    private static final String	    AZURESEARCH_HOSTNAME = "api.cognitive.microsoft.com";   
    private static final String	    AZURESEARCH_PATH = "/bing/v5.0/search";
    
    private static final String	    RESPONSE_FILTER = "Webpages";   
    private static final int	    RESULTS_PER_PAGE = 10;
    
    private final HttpHost			targetHost;
    private final AuthCache			authCache;
    private final BasicScheme			basicAuth;
    private final HttpContext			localContext;
    private final List<AzureWebSearchResult>	results;
    private final Gson				deserializer;
    
    private HttpResponse		responsePost;
    private HttpEntity			resEntity;
    
    private String	    apiKey;
    private String	    query;
    private String	    market;
    private int		    perPage;
    private int		    skip;

    public AzureWebSearch()
    {
	this.targetHost = new HttpHost(AZURESEARCH_HOSTNAME, AZURESEARCH_PORT, AZURESEARCH_SCHEME);
	this.authCache = new BasicAuthCache();
	this.basicAuth = new BasicScheme();
	this.localContext = new BasicHttpContext();
	this.results = new ArrayList<>();
	this.deserializer = new GsonBuilder().setPrettyPrinting().create();
	
	apiKey = query = market = "";
	perPage = RESULTS_PER_PAGE;
	skip = 0;	
	
	authCache.put(targetHost, basicAuth);
	localContext.setAttribute(ClientContext.AUTH_CACHE, authCache);
    }
    
    
    public String getApiKey() {
	return apiKey;
    }

    public void setApiKey(String apiKey) {
	this.apiKey = apiKey;
    }

    public String getQuery() {
	return query;
    }

    public void setQuery(String query) {
	this.query = query;
    }

    public String getMarket() {
	return market;
    }

    public void setMarket(String market) {
	this.market = market;
    }

    public int getPerPage() {
	return perPage;
    }

    public void setPerPage(int perPage) {
	this.perPage = perPage;
    }

    public int getSkip() {
	return skip;
    }

    public void setSkip(int skip)
    {
	this.skip = skip;
	if (this.skip < 0)
	    this.skip = 0;
    }
    
    public void nextPage() {
	setSkip(getSkip() + getPerPage());
    }
    
    public void setPage(int page) {
	setSkip(getPerPage() * (page - 1));
    }
    
    public List<AzureWebSearchResult> getResults() {
	return results;
    }
    
    private String getUrlQuery()
    {
	StringBuilder sb = new StringBuilder();
        sb.append("q=");
        sb.append(getQuery());
	
        sb.append("&count=");
	sb.append(getPerPage());
	sb.append("&offset=");
	sb.append(getSkip());
		
	if (market.isEmpty() == false)
	{
	    sb.append("&mkt=");
	    sb.append(getMarket());
	}
	
	sb.append("&responseFilter=");
	sb.append(RESPONSE_FILTER);
	
	return sb.toString();
    }
    
    private void loadResults(InputStream stream)
    {
	results.clear();
	
	AzureWebSearchResponse response = deserializer.fromJson(new InputStreamReader(stream),
								  AzureWebSearchResponse.class);
	if (response != null)
	{
	    WebAnswer answer = response.webPages;
	    if (answer != null && answer.value.length != 0)
	    {
		// ### presumably we don't 
		for (WebAnswer.Webpage itr : answer.value)
		{
		    AzureWebSearchResult newResult = new AzureWebSearchResult(itr.name,
						  itr.url, itr.displayUrl, itr.snippet);
		    results.add(newResult);
		}
	    }
	}
    }
    
    public void doQuery()
    {
	DefaultHttpClient client = new DefaultHttpClient();
	
        client.getCredentialsProvider()
                .setCredentials(
                        new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                        new UsernamePasswordCredentials(getApiKey(), getApiKey()));

        URI uri;
        try
	{
            String full_query = getUrlQuery();
            uri = new URI(AZURESEARCH_SCHEME, AZURESEARCH_HOSTNAME, AZURESEARCH_PATH, full_query, null);
            // Bing and java URI disagree about how to represent + in query
            // parameters. This is what we have to do instead...
            uri = new URI(uri.getScheme() + "://" + uri.getAuthority()
                    + uri.getPath() + "?"
                    + uri.getRawQuery().replace("+", "%2b")
            );
	}
	catch (URISyntaxException e1)
	{
            FLAIRLogger.get().error(e1, "Couldn't generate Azure Search URI");
            return;
        }

        HttpGet get = new HttpGet(uri);
        get.addHeader("Accept", "application/json");
	get.addHeader("Ocp-Apim-Subscription-Key", getApiKey());

        try 
	{
            responsePost = client.execute(get);
            resEntity = responsePost.getEntity();
	    loadResults(resEntity.getContent());
        } 
	catch (Exception ex) {
            FLAIRLogger.get().error(ex, "Couldn't fetch search results from Azure. Exception: " + ex.toString());
        }
    }
}