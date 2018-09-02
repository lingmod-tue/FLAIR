
package com.flair.server.crawler.impl;

/**
 * Indirection interface b'ween search API implementations and the rest of FLAIR
 */
public abstract class AbstractSearchAgentImplResult {
	private final String title;
	private final String url;
	private final String displayUrl;
	private final String description;

	public AbstractSearchAgentImplResult(String name, String url, String displayUrl, String snippet) {
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
