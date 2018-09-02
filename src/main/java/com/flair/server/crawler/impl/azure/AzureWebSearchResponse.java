
package com.flair.server.crawler.impl.azure;

/**
 * POJOs that map to an Azure search response (webpage search only)
 */
class AzureWebSearchResponse {
	String _type;
	WebAnswer webPages;
	RankingResponse rankingResponse;
}

class MetaTag {
	String content;
	String name;
}

class Identifiable {
	String id;
}

class WebAnswer {
	static class Webpage {
		Object about;
		String dateLastCrawled;
		Webpage[] deepLinks;
		String displayUrl;
		String id;
		String name;
		Object mentions;
		MetaTag[] searchTags;
		String snippet;
		String url;
		String urlPingSuffix;
	}

	String webSearchUrl;
	Long totalEstimatedMatches;
	Webpage[] value;
	String webSearchUrlPingSuffix;
}

class RankingResponse {
	static class RankingItem {
		String answerType;
		Integer resultIndex;
		Identifiable value;
	}

	static class RankingGroup {
		RankingItem[] items;
	}

	RankingGroup mainline;
	RankingGroup pole;
	RankingGroup sidebar;
}