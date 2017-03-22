package com.flair.shared.interop;

import com.flair.shared.grammar.Language;

/*
 * Serializable contents of an uploaded document
 */
public class UploadedDocumentImpl implements UploadedDocument
{
	Language	language;
	String		title;
	String		snippet;
	String		text;
	
	public UploadedDocumentImpl()
	{
		language = null;
		title = snippet = text = "";
	}

	@Override
	public Language getLanguage() {
		return language;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getSnippet() {
		return snippet;
	}

	@Override
	public String getText() {
		return text;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((snippet == null) ? 0 : snippet.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
}
