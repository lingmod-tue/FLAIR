
package com.flair.server.crawler.impl.azure;

import com.flair.server.crawler.impl.AbstractSearchAgentImplResult;

/**
 * Represents a web page in a Azure web search result
 */
public class AzureWebSearchResult extends AbstractSearchAgentImplResult {
	public AzureWebSearchResult(String name, String url, String displayUrl, String snippet) {
		super(name, url, displayUrl, snippet);
	}
}