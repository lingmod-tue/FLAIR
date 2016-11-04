/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.parser;

import com.flair.crawler.SearchResult;

/**
 * Represents a document source object that encapsulates a search result
 * @author shadeMe
 */
public class SearchResultDocumentSource extends AbstractDocumentSource
{
    private final SearchResult		   parentSearchResult;
    private final String		   pageText;
    
    public SearchResultDocumentSource(SearchResult parent)
    {
	super(parent.getLanguage());
	if (parent.isTextFetched() == false)
	    throw new IllegalArgumentException("Search result doesn't have text");
	
	parentSearchResult = parent;
	pageText = preprocessText(parent.getPageText());
    }
    
    public SearchResult getSearchResult() {
	return parentSearchResult;
    }
    
    @Override
    public String getSourceText() {
	return pageText;
    }

    @Override
    public String getDescription() {
	return parentSearchResult.getTitle() + " (" + parentSearchResult.getDisplayURL() + ")";
    }

    @Override
    public int compareTo(AbstractDocumentSource t) {
	if (t instanceof SearchResultDocumentSource == false)
	    throw new IllegalArgumentException("Incompatible source type");
	
	SearchResultDocumentSource rhs = (SearchResultDocumentSource)t;
	if (parentSearchResult.getRank() < rhs.parentSearchResult.getRank())
	    return -1;
	else if (parentSearchResult.getRank() > rhs.parentSearchResult.getRank())
	    return 1;
	else
	    return 0;
    }
}
