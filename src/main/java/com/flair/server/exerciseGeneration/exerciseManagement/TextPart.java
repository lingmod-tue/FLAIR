package com.flair.server.exerciseGeneration.exerciseManagement;

public class TextPart {

	public TextPart(String value, int sentenceId) {
		this.value = value;
		this.sentenceId = sentenceId;
	}

	private String value;
	private int sentenceId;
	public String getValue() {
		return value;
	}
	public int getSentenceId() {
		return sentenceId;
	}
	
}
