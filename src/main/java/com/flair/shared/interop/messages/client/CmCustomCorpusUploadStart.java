package com.flair.shared.interop.messages.client;

import com.flair.shared.grammar.Language;
import com.flair.shared.interop.messages.BaseMessage;

import java.util.ArrayList;

// ### TODO just have a single message for custom corpus by uploading continuously to the server and specifying the number of
//          uploaded files in the message. we can then get a slice of the uploaded files and reverse them
public class CmCustomCorpusUploadStart extends BaseMessage {
	private Language language = Language.ENGLISH;
	private ArrayList<String> keywords = new ArrayList<>();

	public CmCustomCorpusUploadStart() {}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("CmCustomCorpusUploadStart{" + identifier() + "}[");
		sb.append("language=" + language).append(" | ");
		sb.append("numKeywords=" + keywords.size());
		return sb.append("]").toString();
	}

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
}
