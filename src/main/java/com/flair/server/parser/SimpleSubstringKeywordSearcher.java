/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.server.parser;

/**
 * Keyword searcher that uses a basic substring search
 *
 * @author shadeMe
 */
public class SimpleSubstringKeywordSearcher implements AbstractKeywordSearcher {
	@Override
	public KeywordSearcherOutput search(String sourceText, KeywordSearcherInput input) {
		if (input == null)
			throw new IllegalArgumentException("No keyword search input");

		KeywordSearcherOutput output = new KeywordSearcherOutput(input);
		// force to lowercase
		sourceText = sourceText.toLowerCase();

		for (String keyword : input) {
			int startIdx = sourceText.indexOf(keyword, 0);
			while (startIdx != -1) {
				int endIdx = startIdx + keyword.length();

				// check if it's at a word boundary
				boolean validStartBoundary = false, validEndBoundary = false;
				if (startIdx - 1 < 0 ||
						sourceText.charAt(startIdx - 1) == '.' ||
						sourceText.charAt(startIdx - 1) == '-' ||
						sourceText.charAt(startIdx - 1) == '\n' ||
						Character.isWhitespace(sourceText.charAt(startIdx - 1))) {
					validStartBoundary = true;
				}

				if (endIdx >= sourceText.length() ||
						sourceText.charAt(endIdx) == '.' ||
						sourceText.charAt(endIdx) == '-' ||
						sourceText.charAt(endIdx) == '\n' ||
						Character.isWhitespace(sourceText.charAt(endIdx))) {
					validEndBoundary = true;
				}

				if (validStartBoundary && validEndBoundary)
					output.addHit(keyword, startIdx, endIdx);

				startIdx = sourceText.indexOf(keyword, endIdx);
			}

		}

		return output;
	}

	private static class Factory implements AbstractKeywordSearcher.Factory {
		@Override
		public AbstractKeywordSearcher create() {
			return new SimpleSubstringKeywordSearcher();
		}
	}

	public static AbstractKeywordSearcher.Factory factory() {
		return new SimpleSubstringKeywordSearcher.Factory();
	}
}


