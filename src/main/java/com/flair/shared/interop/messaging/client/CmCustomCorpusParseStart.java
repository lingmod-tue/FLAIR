package com.flair.shared.interop.messaging.client;

import com.flair.shared.grammar.Language;
import com.flair.shared.interop.messaging.Message;

import java.util.ArrayList;

public class CmCustomCorpusParseStart implements Message.Payload {
	private Language language = Language.ENGLISH;
	private ArrayList<String> keywords = new ArrayList<>();
	private int numUploadedFiles = 0;

	public CmCustomCorpusParseStart() {}

	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public ArrayList<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(ArrayList<String> keywords) {
		this.keywords = keywords;
	}
	public int getNumUploadedFiles() {
		return numUploadedFiles;
	}
	public void setNumUploadedFiles(int numUploadedFiles) {
		this.numUploadedFiles = numUploadedFiles;
	}
	@Override
	public String name() {
		return "CmCustomCorpusParseStart";
	}
	@Override
	public String desc() {
		StringBuilder sb = new StringBuilder();
		sb.append("language=").append(language).append(" | ");
		sb.append("numUploadedFiles=").append(numUploadedFiles).append(" | ");
		sb.append("numKeywords=").append(keywords.size());
		return sb.toString();
	}
}
