package com.flair.server.interop;

import com.flair.shared.grammar.Language;
import com.flair.shared.interop.RankableWebSearchResult;

/*
 * Serializable version of a web search result
 */
public class RankableWebSearchResultImpl implements RankableWebSearchResult
{
	Language		language;
	String			title;
	String			url;
	String			displayUrl;
	String			snippet;
	int				rank;
	String			text;

	public RankableWebSearchResultImpl()
	{
		rank = -1;
		language = null;
		title = url = displayUrl = snippet = text = "";
	}

	@Override
	public Language getLanguage() {
		return language;
	}

	public void setLang(Language lang) {
		this.language = lang;
	}

	@Override
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getDisplayUrl() {
		return displayUrl;
	}

	public void setDisplayUrl(String displayUrl) {
		this.displayUrl = displayUrl;
	}

	@Override
	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	@Override
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	@Override
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
