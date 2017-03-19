package com.flair.shared.interop;

import com.flair.shared.grammar.Language;
import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * Represents a web search result that is rankable
 */
public interface RankableWebSearchResult extends IsSerializable
{
	public Language		getLanguage();
	public int			getRank();		// rank in the search results
	
    public String		getTitle();
    public String		getUrl();
    public String		getDisplayUrl();
    public String		getSnippet();
    public String		getText();
}
