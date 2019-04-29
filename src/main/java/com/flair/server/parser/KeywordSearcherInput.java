
package com.flair.server.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Search parameters for an AbstractKeywordSearcher
 */
public class KeywordSearcherInput implements Iterable<String> {
	private final List<String> keywords;

	public KeywordSearcherInput() {
		this.keywords = new ArrayList<>();
	}
	public KeywordSearcherInput(KeywordSearcherInput rhs) {
		this.keywords = new ArrayList<>(rhs.keywords);
	}
	public KeywordSearcherInput(List<String> keywords) {
		this.keywords = new ArrayList<>(keywords);
	}

	@Override
	public Iterator<String> iterator() {
		return keywords.iterator();
	}
}
