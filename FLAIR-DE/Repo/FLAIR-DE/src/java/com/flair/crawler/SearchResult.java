package com.flair.crawler;

import com.flair.grammar.Language;
import com.flair.utilities.FLAIRLogger;
import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.DefaultExtractor;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.xml.sax.InputSource;

/**
 * Represents a single search result for a specific query
 * @author shadeMe
 */
public class SearchResult implements Comparable<SearchResult>
{
    private final Language	    lang;
    private final String            query;
    private final String            title;
    private final String            URL;
    private final String            displayURL;
    private final String            snippet;
    private int			    rank;	    // as returned by the search engine
    private String                  pageText;       // page text without any markup
    
    public SearchResult(Language lang, String query, String title, String URL, String displayURL, String snippet)
    {
	this.lang = lang;
        this.query = query;
        this.title = title;
        this.URL = URL;
        this.displayURL = displayURL;
        this.snippet = snippet;
        this.pageText = "";
	this.rank = -1;
    }
    
    public Language getLanguage() {
	return lang;
    }
    
    public String getQuery() {
        return query;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getURL() {
        return URL;
    }
    
    public String getDisplayURL() {
        return displayURL;
    }
    
    public String getSnippet() {
        return snippet;
    }
    
    public String getPageText() {
        return pageText;
    }
    
    public int getRank() {
	return rank;
    }
    
    public void setRank(int rank) {
	this.rank = rank;
    }
    
    public boolean isTextFetched() {
        return pageText.isEmpty() == false;
    }
    
    public boolean fetchPageText(boolean forceFetch)
    {
        if (isTextFetched() == true && forceFetch == false)
            return false;
        
        try
        {
            URL pageURL = new URL(URL);
            HttpURLConnection connection = (HttpURLConnection)pageURL.openConnection();
            
            if (connection != null)
            {
                connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
                connection.addRequestProperty("Accept-Language", "de-DE,de;q=0.8");
                connection.addRequestProperty("User-Agent", "Mozilla/4.76");
                connection.addRequestProperty("Referer", "google.com");

                boolean redirect = false;
                // normally, 3xx is redirect
                int status = connection.getResponseCode();
                if (status == HttpURLConnection.HTTP_MOVED_TEMP ||
                    status == HttpURLConnection.HTTP_MOVED_PERM ||
                    status == HttpURLConnection.HTTP_SEE_OTHER)
                {
                    redirect = true;
                }

                if (redirect) 
                {
                    // get redirect url from "location" header field
                    String newUrl = connection.getHeaderField("Location");
                    
                    pageURL = new URL(newUrl);
                    // open the new connnection again
                    connection = (HttpURLConnection)pageURL.openConnection();
                    connection.setReadTimeout(5000);
		    connection.setConnectTimeout(5000);
                    connection.addRequestProperty("Accept-Language", "de-DE,de;q=0.8");
                    connection.addRequestProperty("User-Agent", "Mozilla/4.76");
                    connection.addRequestProperty("Referer", "google.com");
                }
            }
            
            if (connection != null)
            {
                // fetch text
                // TODO: check for encoding!!!
                InputSource stream = new InputSource();
		stream.setEncoding("UTF-8");
		stream.setByteStream(pageURL.openStream());
		
		pageText = DefaultExtractor.INSTANCE.getText(stream);
		return !pageText.isEmpty();
            }
        } catch (IOException | BoilerpipeProcessingException ex) {
            FLAIRLogger.get().error("Couldn't fetch page text. Exception: " + ex.getMessage());
        }

        return false;
    }
    
    @Override
    public int compareTo(SearchResult t) {
	if (rank < t.rank)
	    return -1;
	else if (rank > t.rank)
	    return 1;
	else
	    return 0;
    }
}
