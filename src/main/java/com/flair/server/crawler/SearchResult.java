package com.flair.server.crawler;

import com.flair.server.utilities.textextraction.AbstractTextExtractor;
import com.flair.server.utilities.textextraction.TextExtractorFactory;
import com.flair.shared.grammar.Language;

/**
 * Represents a single search result for a specific query
 */
public class SearchResult implements Comparable<SearchResult> {
	private final Language lang;
	private final String query;
	private final String title;
	private final String URL;
	private final String displayURL;
	private final String snippet;
	private int rank;        // as returned by the search engine
	private String pageText;    // page text without any markup

	public SearchResult(Language lang, String query, String title, String URL, String displayURL, String snippet) {
		this.lang = lang;
		this.query = query;
		this.title = title;
		this.URL = URL;
		this.displayURL = displayURL;
		this.snippet = snippet;
		this.pageText = "";
		this.rank = -1;
	}

	public Language getLanguage() {
		return lang;
	}

	public String getQuery() {
		return query;
	}

	public String getTitle() {
		return title;
	}

	public String getURL() {
		return URL;
	}

	public String getDisplayURL() {
		return displayURL;
	}

	public String getSnippet() {
		return snippet;
	}

	public String getPageText() {
		return pageText;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public boolean isTextFetched() {
		return pageText.isEmpty() == false;
	}

	public boolean fetchPageText(boolean forceFetch) {
		if (isTextFetched() == true && forceFetch == false)
			return false;

		AbstractTextExtractor extractor = TextExtractorFactory.create(TextExtractorFactory.Type.AUTODETECT);
		AbstractTextExtractor.Output output = extractor.extractText(new AbstractTextExtractor.Input(URL));

		if (output.success == false)
			return false;
		else {
			pageText = output.extractedText;
			return pageText.isEmpty() == false;
		}
	}

	@Override
	public int compareTo(SearchResult t) {
		if (rank < t.rank)
			return -1;
		else if (rank > t.rank)
			return 1;
		else
			return 0;
	}

	@Override
	public String toString() {
		return "Search Result [" + title + "] URL: " + displayURL + ", Lang: " + lang;
	}
}
