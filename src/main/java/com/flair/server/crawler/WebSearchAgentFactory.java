
package com.flair.server.crawler;

import com.flair.shared.grammar.Language;

/**
 * Creates WebSearchAgent objects
 */
public class WebSearchAgentFactory {
	public enum SearchAgent {
		BING
	}

	public static WebSearchAgent create(SearchAgent type, Language lang, String query) {
		switch (type) {
		case BING:
			return new BingSearchAgent(lang, query);
		}

		return null;
	}
}
