/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRParser;

import FLAIRCrawler.SearchResult;

/**
 * Represents a document source object that encapsulates a search result
 * @author shadeMe
 */
public class SearchResultDocumentSource implements AbstractDocumentSource
{
    private final SearchResult		   parentSearchResult;
    
    public SearchResultDocumentSource(SearchResult parent)
    {
	assert parent.isTextFetched() == true;
	parentSearchResult = parent;
    }
    
    @Override
    public String getSourceText() {
	return parentSearchResult.getPageText();
    }
}
