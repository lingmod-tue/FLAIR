/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.crawler.impl.azure;

import com.flair.crawler.impl.AbstractSearchAgentImplResult;

/**
 * Represents a web page in a Azure web search result
 * @author shadeMe
 */
public class AzureWebSearchResult extends AbstractSearchAgentImplResult
{
    public AzureWebSearchResult(String name, String url, String displayUrl, String snippet) {
	// the regular url redirects through bing.com,
	// so use the diplay url (which is generally what we want anyway)
	super(name, displayUrl, displayUrl, snippet);
    }
}