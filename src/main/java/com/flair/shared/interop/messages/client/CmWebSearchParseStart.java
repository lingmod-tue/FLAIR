package com.flair.shared.interop.messages.client;

import com.flair.shared.grammar.Language;
import com.flair.shared.interop.messages.BaseMessage;

import java.util.ArrayList;

public class CmWebSearchParseStart extends BaseMessage {
	private Language language = Language.ENGLISH;
	private String query = "";
	private int numResults = 10;
	private ArrayList<String> keywords = new ArrayList<>();

	public CmWebSearchParseStart() {}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("CmWebSearchParseStart{" + identifier() + "}[");
		sb.append("language=" + language).append(" | ");
		sb.append("query=" + query).append(" | ");
		sb.append("numResults=" + numResults).append(" | ");
		sb.append("numKeywords=" + keywords.size());
		return sb.append("]").toString();
	}

	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public int getNumResults() {
		return numResults;
	}
	public void setNumResults(int numResults) {
		this.numResults = numResults;
	}
	public ArrayList<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(ArrayList<String> keywords) {
		this.keywords = keywords;
	}
}
