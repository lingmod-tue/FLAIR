package com.flair.shared.interop;

import com.flair.shared.grammar.Language;

/*
 * Represents a web search result that is rankable
 */
public interface RankableWebSearchResult extends BasicDocumentTransferObject
{
	public int			getRank();		// rank in the search results

	public String		getUrl();
    public String		getDisplayUrl();
}
