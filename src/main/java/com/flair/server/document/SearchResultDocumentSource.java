
package com.flair.server.document;

import com.flair.server.crawler.SearchResult;

import java.net.URL;

/**
 * Represents a document source object that encapsulates a search result
 */
public class SearchResultDocumentSource extends AbstractDocumentSource {
	private final SearchResult parentSearchResult;
	private final String pageText;

	public SearchResultDocumentSource(SearchResult parent) {
		super(parent.getLanguage());
		if (parent.isTextFetched() == false)
			throw new IllegalArgumentException("Search result doesn't have text");

		parentSearchResult = parent;
		pageText = preprocessText(parent.getPageText(), input -> {
			// do some basic website-specific preprocessing
			String out = input;
			try {
				URL url = new URL(parentSearchResult.getDisplayURL());
				if (url.getHost().contains("wikipedia.org")) {
					out = out.replaceAll("\\[[0-9]+\\]|\\[[a-z]+\\]", "");      // citation bubbles
					out = out.replaceAll("\\[ [a-zA-Z]* \\]", "");              // "edit" buttons
					out = out.replaceAll("\\^", "");                            // citation up-tick
				}
			} catch (Throwable e) {
			}

			return out;
		});
	}

	public SearchResult getSearchResult() {
		return parentSearchResult;
	}

	@Override
	public String getSourceText() {
		return pageText;
	}

	@Override
	public String getDescription() {
		return parentSearchResult.getTitle() + " (" + parentSearchResult.getDisplayURL() + ")";
	}

	@Override
	public int compareTo(AbstractDocumentSource t) {
		if (t instanceof SearchResultDocumentSource == false)
			throw new IllegalArgumentException("Incompatible source type");

		SearchResultDocumentSource rhs = (SearchResultDocumentSource) t;
		if (parentSearchResult.getRank() < rhs.parentSearchResult.getRank())
			return -1;
		else if (parentSearchResult.getRank() > rhs.parentSearchResult.getRank())
			return 1;
		else
			return 0;
	}
}
