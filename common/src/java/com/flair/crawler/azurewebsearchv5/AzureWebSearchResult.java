/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.crawler.azurewebsearchv5;

/**
 * Represents a web page in a Azure web search result
 * @author shadeMe
 */
public class AzureWebSearchResult
{
    private final String	    title;
    private final String	    url;
    private final String	    displayUrl;
    private final String	    description;

    public AzureWebSearchResult(String name, String url, String displayUrl, String snippet)
    {
	this.title = name;
	this.url = url;
	this.displayUrl = displayUrl;
	this.description = snippet;
    }	

    public String getTitle() {
	return title;
    }

    public String getUrl() {
	return url;
    }

    public String getDisplayUrl() {
	return displayUrl;
    }

    public String getDescription() {
	return description;
    }
}