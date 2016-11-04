/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.crawler;

import com.flair.grammar.Language;

/**
 * Creates WebSearchAgent obejcts
 * @author shadeMe
 */
public class WebSearchAgentFactory
{
    public enum SearchAgent
    {
	BING
    }
    
    public static WebSearchAgent create(SearchAgent type, Language lang, String query)
    {
	switch (type)
	{
	    case BING:
		return new BingSearchAgent(lang, query);
	}
	
	return null;
    }
}
