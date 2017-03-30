/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.crawler.impl.faroo;

import com.flair.crawler.impl.AbstractSearchAgentImplResult;

/**
 * Represents a web page in a Faroo web search result
 * @author shadeMe
 */
public class FarooSearchResult extends AbstractSearchAgentImplResult
{
    public FarooSearchResult(String name, String url, String displayUrl, String snippet) {
	super(name, url, displayUrl, snippet);
    }
}