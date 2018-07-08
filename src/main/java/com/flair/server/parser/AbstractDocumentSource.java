/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.server.parser;

import com.flair.shared.grammar.Language;

/**
 * Represents the source of a document object
 *
 * @author shadeMe
 */
public abstract class AbstractDocumentSource implements Comparable<AbstractDocumentSource> {
	private final Language language;

	public AbstractDocumentSource(Language lang) {
		language = lang;
	}

	protected final String preprocessText(String input) {
		// ideally, we'd fix up the text to make it easier for the parser to parse
		// in particular, text extracted from web sources might contain malformed sentences due to markup issues
		// however, we'll do nothing here since the parser is usually clever enough
		// to overcome the most egregious instances
		return input;
	}

	public final Language getLanguage() {
		return language;
	}

	abstract public String getSourceText();

	abstract public String getDescription();
}
