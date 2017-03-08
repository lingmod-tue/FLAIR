package com.flair.client;

import com.flair.server.grammar.Language;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/*
 * Web ranker module. Implements the ranking state and logic
 */
public class WebRankerCore
{

	public WebRankerCore() {
		// TODO Auto-generated constructor stub
	}

	public void performSearch(String query, int resultCount, Language lang)
	{
		Window.alert("Query: " + query + "\nCount: " + resultCount + "\nLang: " + lang);
	}
	
	public Widget getView() {
		return null;
	}
}
