
package com.flair.server.crawler.impl.faroo;

import com.flair.server.crawler.impl.AbstractSearchAgentImplResult;

/**
 * Represents a web page in a Faroo web search result
 */
public class FarooSearchResult extends AbstractSearchAgentImplResult {
	public FarooSearchResult(String name, String url, String displayUrl, String snippet) {
		super(name, url, displayUrl, snippet);
	}
}