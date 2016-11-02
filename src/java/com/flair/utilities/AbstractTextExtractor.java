/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Extracts plain text from a given URI
 * @author shadeMe
 */
public abstract class AbstractTextExtractor
{
    private final TextExtractorType	type;
    
    public AbstractTextExtractor(TextExtractorType type) {
	this.type = type;
    }
	    
    public TextExtractorType getType() {
	return type;
    }
    
    public abstract Output extractText(String url);
    
    protected InputStream openURLStream(String url) throws IOException
    {
	URL pageURL = new URL(url);
	HttpURLConnection connection = (HttpURLConnection)pageURL.openConnection();

	if (connection != null)
	{
	    connection.setConnectTimeout(5000);
	    connection.setReadTimeout(5000);
	    connection.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
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
		connection.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
		connection.addRequestProperty("User-Agent", "Mozilla/4.76");
		connection.addRequestProperty("Referer", "google.com");
	    }
	}
	
	if (connection == null)
	    throw new IOException("Invalid UrlConnection for " + pageURL);
	else
	    return connection.getInputStream();
    }
    
    public class Output
    {
	public final boolean	    success;	    // true if the text was extracted successfully, false otherwise
	public final String	    sourceURL;
	public final String	    extractedText;
	
	public Output(boolean success, String url, String extract)
	{
	    this.success = success;
	    this.sourceURL = url;
	    this.extractedText = extract;
	}
    }
}
