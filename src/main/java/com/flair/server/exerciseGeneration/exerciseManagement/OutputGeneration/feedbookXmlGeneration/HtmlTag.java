package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration;

public class HtmlTag {
	private int startIndex;
	private int endIndex;
	private String tag;
	private String tagName;
	
	public HtmlTag(int startIndex, int endIndex, String tag) {
		super();
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.tag = tag;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public int getEndIndex() {
		return endIndex;
	}
	public String getTag() {
		return tag;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
}
