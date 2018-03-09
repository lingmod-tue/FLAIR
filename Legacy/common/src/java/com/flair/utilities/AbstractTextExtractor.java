/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.utilities;

import com.flair.grammar.Language;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

/**
 * Extracts plain text from a given source
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
    
    public abstract Output extractText(Input input);
    
    protected static InputStream openURLStream(String url, Language lang) throws IOException, URISyntaxException
    {
        String langStr = "en-US,en;q=0.8";

        switch (lang)
        {
        case ENGLISH:
                langStr = "en-US,en;q=0.8";
                break;
        case GERMAN:
                langStr = "de-DE,de;q=0.8";
                break;
        }

        URI uri = new URI(url);
        HttpGet get = new HttpGet(uri);
        get.setHeader("Accept-Language", langStr);
        get.setHeader("User-Agent", "Mozilla/4.76");
        get.setHeader("Referer", "google.com");

        HttpClient client = HttpClientFactory.get().create();
        return client.execute(get).getEntity().getContent();
    }
    
    public static class Input
    {
	public enum SourceType
	{
	    URL,
	    STREAM
	}
	
	public final SourceType	    sourceType;
	public final String	    url;
	public final InputStream    stream;
	public final Language	    lang;

	public Input(String url, Language lang)
	{
	    this.sourceType = SourceType.URL;
	    this.url = url;
	    this.stream = null;
	    this.lang = lang;
	}

	public Input(InputStream stream, Language lang)
	{
	    this.sourceType = SourceType.STREAM;
	    this.url = null;
	    this.stream = stream;
	    this.lang = lang;
	}
    }    
    
    public static class Output
    {
	public final Input	    input;
	public final boolean	    success;	    // true if the text was extracted successfully, false otherwise
	public final String	    extractedText;
	public final boolean	    isHTML;
	
	public Output(Input input, boolean success, String extract, boolean isHTML)
	{
	    this.input = input;
	    this.success = success;
	    this.extractedText = extract;
	    this.isHTML = isHTML;
	}
    }
    
    public static String doBoilerpipePass(String html) {
	return BoilerpipeTextExtractor.parse(html, false);
    }
}
