package com.flair.server.questgen.selection;

import com.flair.shared.grammar.Language;

/*
 * Factory generator for document selectors
 */
public final class DocumentSentenceSelectorGenerator {
	private DocumentSentenceSelectorGenerator() {}

	public enum SelectorType {
		TEXTRANK
	}

	public static DocumentSentenceSelector.Builder create(SelectorType type, Language lang) {
		switch (type) {
		case TEXTRANK:
			return new TextRankSentenceSelector.Builder(lang);
		default:
			throw new IllegalArgumentException("Unknown sentence selector type " + type);
		}
	}
}
