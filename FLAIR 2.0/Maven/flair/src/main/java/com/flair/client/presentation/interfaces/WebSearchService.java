package com.flair.client.presentation.interfaces;

import com.flair.shared.grammar.Language;

/*
 * Interface for initiating web search operations
 */
public interface WebSearchService
{
	public interface SearchHandler {
		public void handle(Language lang, String query, int numResults);
	}
	
	public void		show();
	public void		hide();
	
	public void		setSearchHandler(SearchHandler handler);
}
