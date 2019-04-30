package com.flair.shared.interop.messaging.client;

import com.flair.shared.grammar.Language;
import com.flair.shared.interop.messaging.Message;

import java.util.ArrayList;

public class CmWebSearchParseStart implements Message.Payload {
	private Language language = Language.ENGLISH;
	private String query = "";
	private int numResults = 10;
	private ArrayList<String> keywords = new ArrayList<>();

	public CmWebSearchParseStart() {}


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
	@Override
	public String name() {
		return "CmWebSearchParseStart";
	}
	@Override
	public String desc() {
		StringBuilder sb = new StringBuilder();
		sb.append("language=" + language).append(" | ");
		sb.append("query=" + query).append(" | ");
		sb.append("numResults=" + numResults).append(" | ");
		sb.append("numKeywords=" + keywords.size());
		return sb.toString();
	}
}
