package com.flair.server.sentencesel;

public class SentenceSelectorFactory {
	public enum Type {
		TEXTRANK,
	}

	public static SentenceSelector.Builder create(Type type) {
		switch (type) {
		case TEXTRANK:
			return new TextRankSentenceSelector.Builder();
		default:
			throw new IllegalArgumentException("Invalid sentence selector " + type);
		}
	}
}