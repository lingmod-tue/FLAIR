/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.server.crawler.impl.faroo;

import com.flair.server.crawler.impl.AbstractSearchAgentImpl;
import com.flair.server.crawler.impl.AbstractSearchAgentImplResult;
import com.flair.server.utilities.ServerLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * Web search impelementation of the FAROO search API
 * @author shadeMe
 */
public class FarooSearch implements AbstractSearchAgentImpl
{
    private static final String	    FAROOSEARCH_SCHEME = "http";   
    private static final String	    FAROOSEARCH_HOSTNAME = "www.faroo.com";   
    private static final String	    FAROOSEARCH_PATH = "/api";
    
    private static final int	    RESULTS_PER_PAGE = 10;
    private static final boolean    KEYWORDS_IN_CONTEXT = true;
    
    public static enum SearchSource
    {
	WEB("web"),
	NEWS("news"),
	TOPICS("topics"),
	TRENDS("trends"),
	SUGGEST("suggest")
	;

	private final String	name;
	
	private SearchSource(String name) {
	    this.name = name;
	}
	
	@Override
	public String toString() {
	    return name;
	}
    }
    
    public static enum Language
    {
	ENGLISH("en"),
	GERMAN("de"),
	CHINESE("zh")
	;
	
	private final String	name;
	
	private Language(String name) {
	    this.name = name;
	}
	
	@Override
	public String toString() {
	    return name;
	}
    }
    
    private final Gson	    deserializer;
    
    private HttpResponse    responsePost;
    private HttpEntity	    resEntity;
    
    private String	    query;
    private String	    apiKey;
    private int		    perPage;
    private int		    skip;
    private Language	    lang;
    private SearchSource    source;

    public FarooSearch()
    {
	this.deserializer = new GsonBuilder().setPrettyPrinting().create();
	
	query = apiKey = "";
	perPage = RESULTS_PER_PAGE;
	skip = 0;	
	
	lang = Language.ENGLISH;
	source = SearchSource.WEB;
    }
    
    public boolean isTrending() {
	return query.isEmpty();
    }

    public void setTrending(boolean trending) 
    {
	if (trending)
	    query = "";
    }

    public Language getLang() {
	return lang;
    }

    public void setLang(Language lang) {
	this.lang = lang;
    }

    public SearchSource getSource() {
	return source;
    }

    public void setSource(SearchSource source) {
	this.source = source;
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
    
    private String getUrlQuery()
    {
	StringBuilder sb = new StringBuilder();
        sb.append("q=");
        sb.append(EncodingUtil.encodeURIComponent(getQuery()));
	
        sb.append("&length=");
	sb.append(getPerPage());
	sb.append("&start=");
	sb.append(getSkip());
	
	sb.append("&src=");
	sb.append(getSource().toString());
	
	sb.append("&l=");
	sb.append(getLang().toString());
	
	sb.append("&kwic=");
	sb.append(KEYWORDS_IN_CONTEXT);
	
	sb.append("&key=");
	sb.append(getApiKey());
	
	return sb.toString();
    }
    
    private List<FarooSearchResult> loadResults(InputStream stream)
    {
	List<FarooSearchResult> results = new ArrayList<>();
	FarooSearchResponse response = deserializer.fromJson(new InputStreamReader(stream),
								  FarooSearchResponse.class);
	if (response != null)
	{
	    for (FarooSearchResponse.SearchResult itr : response.results)
	    {
		FarooSearchResult newRes = new FarooSearchResult(itr.title,
								itr.url,
								itr.url,
								itr.kwic);
		results.add(newRes);
	    }
	}
	
	return results;
    }

    @Override
    public List<? extends AbstractSearchAgentImplResult> performSearch() 
    {
	try
	{   
	    if (apiKey.isEmpty())
		throw new IllegalStateException("Invalid API key");
	    
	    String full_query = getUrlQuery();
            URI uri = new URI(FAROOSEARCH_SCHEME, FAROOSEARCH_HOSTNAME, FAROOSEARCH_PATH, full_query, null);
            
	    HttpGet get = new HttpGet(uri);
	    HttpClient client = new DefaultHttpClient();    
	    responsePost = client.execute(get);
	    resEntity = responsePost.getEntity();

	    return loadResults(resEntity.getContent());
        } 
	catch (Throwable ex) {
	    ServerLogger.get().error(ex, "Couldn't fetch search results from Faroo. Exception: " + ex.toString());
	}
	
	return new ArrayList<>();
    }
}
