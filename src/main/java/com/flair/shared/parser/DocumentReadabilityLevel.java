package com.flair.shared.parser;

/*
 * Readability level of a document
 */
public enum DocumentReadabilityLevel {
	LEVEL_A("A1-A2"),        // A1 - A2
	LEVEL_B("B1-B2"),        // B1 - B2
	LEVEL_C("C1-C2"),        // C1 - C2
	;

	private final String title;

	private DocumentReadabilityLevel(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return title;
	}
}
