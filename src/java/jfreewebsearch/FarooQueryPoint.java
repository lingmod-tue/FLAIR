package jfreewebsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;


import com.google.gson.Gson;



/**
 * 
 * @author Simon Holthausen
 * 
 * Use this class to query Faroo. The java library gson ist needed ( http://code.google.com/p/google-gson/ )
 *
 */
public class FarooQueryPoint{
	
	private static Date lastAccessed;
	private String key;
	
	
	/**
	 * 
	 * @author Simon Holthausen
	 *
	 * languages supported by the querypoint (english, german, chinese)
	 */
	public static enum language {
		en, de, zh;
	}
	
	public FarooQueryPoint(String key){
		this.key = key;
	}
	
	/**
	 * query faroo web search with the default query parameters
	 * @param query the search term
	 * @return 
	 */
	public FarooResultSet query(String query) {
			
		handleOneSecondBetweenQueriesRule();
				
		String encodedQuery = encodeQuery(query);
					
		String json = runQuery("http://www.faroo.com/api?q="+encodedQuery+"&key="+key);
		
		FarooResultSet resultsSet = new Gson().fromJson(json, FarooResultSet.class);
		return resultsSet;
		
	}


	
	/**
	 * query faroo web search with given parameters
	 * @param query the search term
	 * @param startPointOfResults start point of the resultslist (max is 100). default 1
	 * @param nrOfResults number of results (max is 100). default 20
	 * @param language the language of the results. either en, de or zh. default en
	 * @param snippetFromArticleBeginning true if snippet from the article should always be from the beginning. default false
	 * @param instantSearch false if faroo should search with given query. true if faroo should first check for spelling and suggestions and take the best match. default false
	 * @return
	 */
	public FarooResultSet query(String query, int startPointOfResults, int nrOfResults, language language, boolean snippetFromArticleBeginning, boolean instantSearch) {
		
		handleOneSecondBetweenQueriesRule();
		
		String encodedQuery = encodeQuery(query);
		
		
		String queryStr = "http://www.faroo.com/api?q="+encodedQuery;
		if(startPointOfResults > 0)
			queryStr += "&start="+startPointOfResults;
		if(nrOfResults > 0)
			queryStr += "&length="+nrOfResults;
		if(language != null)
			queryStr += "&l="+language;
		if(snippetFromArticleBeginning)
			queryStr += "&kwic="+snippetFromArticleBeginning;
		if(instantSearch)
			queryStr += "&i="+instantSearch;
		queryStr += "&key="+key;
		
		
		String json = runQuery(queryStr);
		
		FarooResultSet resultsSet = new Gson().fromJson(json, FarooResultSet.class);
		return resultsSet;
	}

	private String encodeQuery(String query) {
		String encodedQuery = "";
		try {
			encodedQuery = URLEncoder.encode(query, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encodedQuery;
	}
	
	private void handleOneSecondBetweenQueriesRule() {
		if(!lastQueryMoreThanOneSecondAway()) {
			try {
				Thread.sleep(timeToWaitUntilNextQuery());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private String runQuery(String url) {
		try {
			
			URL theUrl = new URL(url);
	
		    BufferedReader in = new BufferedReader(
		    new InputStreamReader(theUrl.openStream()));
		
		    String jsonString = "";
		    String line;
		    while ((line = in.readLine()) != null)
		    	jsonString += line;
		    in.close();
		    
		    blockAccessForNextSecond();
		    
		    return jsonString;
		    
		    
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void blockAccessForNextSecond() {
		lastAccessed = new Date();
	}
	
	private boolean lastQueryMoreThanOneSecondAway() {
		if(lastAccessed == null || (new Date().getTime() - lastAccessed.getTime()) > 1000)
			return true;
		return false;
	}
	
	private long timeToWaitUntilNextQuery() {
		if(lastAccessed == null)
			return 0;
		
		long timeToWait = 1000 - (new Date().getTime() - lastAccessed.getTime());
		if(timeToWait < 0)
			return 0;
		else
			return timeToWait;
	}

}
