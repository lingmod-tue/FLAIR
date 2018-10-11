package com.flair.server.crawler;

import com.flair.server.utilities.ServerLogger;
import com.flair.shared.grammar.Language;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Abstract base class for a web search executor
 */
public abstract class WebSearchAgent {
	private static final Properties PROPERTIES = new Properties();
	private static List<String> BLACKLISTED_URLS;

	static {
		try {
			PROPERTIES.load(WebSearchAgent.class.getResourceAsStream("WebSearchAgent.properties"));
		} catch (IOException e) {
			ServerLogger.get().error(e, "Couldn't load properties for BingSearchAgent");
		}

		BLACKLISTED_URLS = Collections.unmodifiableList(Arrays.stream(PROPERTIES.getProperty("blacklist", "").split(";")).distinct().collect(Collectors.toList()));
	}

	protected static boolean isURLBlacklisted(String URL) {
		for (String itr : BLACKLISTED_URLS) {
			if (URL.contains(itr))
				return true;
		}

		return false;
	}



	protected final Language lang;
	protected final String query;

	public WebSearchAgent(Language lang, String query) {
		if (query.length() == 0)
			throw new IllegalArgumentException("Invalid/empty query");

		this.lang = lang;
		this.query = query;
	}

	public String getQuery() {
		return query;
	}

	public Language getLanguage() {
		return lang;
	}

	public abstract List<SearchResult> getNext(int numResults);    // returns the next n results
	public abstract boolean hasNoMoreResults();        // returns true if there are no more results, false otherwise
}
