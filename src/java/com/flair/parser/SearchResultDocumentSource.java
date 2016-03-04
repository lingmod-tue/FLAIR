/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.parser;

import com.flair.crawler.SearchResult;
import com.flair.grammar.Language;

/**
 * Represents a document source object that encapsulates a search result
 * @author shadeMe
 */
public class SearchResultDocumentSource implements AbstractDocumentSource
{
    private final SearchResult		   parentSearchResult;
    private final Language		   language;
    
    public SearchResultDocumentSource(SearchResult parent)
    {
	if (parent.isTextFetched() == false)
	    throw new IllegalArgumentException("Search result doesn't have text");
	
	parentSearchResult = parent;
	language = parent.getLanguage();
    }
    
    public SearchResult getSearchResult() {
	return parentSearchResult;
    }
    
    @Override
    public String getSourceText() {
	return parentSearchResult.getPageText();
    }

    @Override
    public Language getLanguage() {
	return language;
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
