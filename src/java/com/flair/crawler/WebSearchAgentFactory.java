/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
