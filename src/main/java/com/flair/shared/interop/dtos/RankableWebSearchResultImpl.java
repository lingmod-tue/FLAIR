package com.flair.shared.interop.dtos;

import com.flair.shared.grammar.Language;

/*
 * Serializable version of a web search result
 */
public class RankableWebSearchResultImpl implements RankableWebSearchResult {
	String operationId;
	int linkingId;
	Language language;
	String title;
	String url;
	String displayUrl;
	String snippet;
	int rank;
	String text;

	public RankableWebSearchResultImpl() {
		linkingId = 0;
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

	@Override
	public int getLinkingId() {
		return linkingId;
	}

	public void setLinkingId(int id) {
		linkingId = id;
	}

	@Override
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
}
