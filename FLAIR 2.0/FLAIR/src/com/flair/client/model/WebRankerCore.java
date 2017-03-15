package com.flair.client.model;

import com.flair.client.presentation.RankerView;
import com.flair.shared.grammar.Language;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/*
 * Web ranker module. Implements the ranking state and logic
 */
public class WebRankerCore
{
	private final RankerView		rankerView;
	
	private void init()
	{
		
	}
	
	public WebRankerCore()
	{
		rankerView = new RankerView();
		
		init();
	}

	public void performSearch(String query, int resultCount, Language lang)
	{
		Window.alert("Query: " + query + "\nCount: " + resultCount + "\nLang: " + lang);
	}
	
	public Widget getView() {
		return null;
	}
}
