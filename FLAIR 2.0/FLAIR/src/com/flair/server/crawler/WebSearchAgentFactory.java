/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.crawler;

import com.flair.shared.grammar.Language;

/**
 * Creates WebSearchAgent objects
 * @author shadeMe
 */
public class WebSearchAgentFactory
{
    public enum SearchAgent
    {
	BING, FAROO
    }
    
    public static WebSearchAgent create(SearchAgent type, Language lang, String query)
    {
	switch (type)
	{
	    case BING:
		return new BingSearchAgent(lang, query);
	    case FAROO:
		return new FarooSearchAgent(lang, query);
	}
	
	return null;
    }
}
