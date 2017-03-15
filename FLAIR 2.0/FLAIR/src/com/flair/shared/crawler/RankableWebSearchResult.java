package com.flair.shared.crawler;

import com.flair.shared.grammar.Language;

/*
 * Represents a web search result that is rankable
 */
public interface RankableWebSearchResult
{
	public Language		getLanguage();
	public int			getRank();		// rank in the search results
	
    public String		getTitle();
    public String		getUrl();
    public String		getDisplayUrl();
    public String		getSnippet();
    public String		getText();
}
