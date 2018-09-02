
package com.flair.shared.grammar;

/**
 * Represents the languages FLAIR supports
 */
public enum Language {
	ENGLISH,
	GERMAN,;

	public static Language fromString(String lang) {
		if (lang.equalsIgnoreCase(ENGLISH.name()))
			return ENGLISH;
		else if (lang.equalsIgnoreCase(GERMAN.name()))
			return GERMAN;
		else
			throw new RuntimeException("Invalid language string " + lang);
	}
}
