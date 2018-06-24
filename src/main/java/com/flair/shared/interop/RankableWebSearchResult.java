package com.flair.shared.interop;

/*
 * Represents a web search result that is rankable
 */
public interface RankableWebSearchResult extends BasicDocumentTransferObject
{
	public int			getRank();		// rank in the search results

	public String		getUrl();
    public String		getDisplayUrl();
}
