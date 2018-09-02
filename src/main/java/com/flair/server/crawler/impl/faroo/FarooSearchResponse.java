
package com.flair.server.crawler.impl.faroo;

/**
 * POJOs that map to a Faroo search response
 */
class FarooSearchResponse {
	static class SearchResult {
		String title;
		String kwic;        // keyword in context
		String url;
		String iurl;        // main article image url
		String domain;
		String author;
		boolean news;        // true if the article is from newspapers, magazines and blogs
		long date;        // publishing time in ms

		static class RelatedResult {
			String title;
			String url;
			String domain;
		}

		RelatedResult[] related;    // for trending news only
		String query;        // might differ from original query if instant search is on
		int count;        // total no of results
		int start;        // start pos of results
		int length;        // no of results requested
		long time;        // search time in ms
		String[] suggestions;    // query suggestions for instant search
	}

	SearchResult[] results;
}


