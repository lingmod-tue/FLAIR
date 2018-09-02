
package com.flair.server.parser;

public interface AbstractKeywordSearcher {
	interface Factory {
		AbstractKeywordSearcher create();
	}

	KeywordSearcherOutput search(String sourceText, KeywordSearcherInput input);
}
