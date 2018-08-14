/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.

 */
package com.flair.server.document;

import com.flair.shared.grammar.Language;

/**
 * Represents a document source object that encapsulates a string
 *
 * @author shadeMe
 */
public class SimpleDocumentSource extends AbstractDocumentSource {
	private final String sourceString;

	public SimpleDocumentSource(String parent, Language lang) {
		super(lang);
		if (parent.isEmpty())
			throw new IllegalArgumentException("Empty string source");

		sourceString = preprocessText(parent);
	}

	@Override
	public String getSourceText() {
		return sourceString;
	}

	@Override
	public String getDescription() {
		if (sourceString.length() < 10)
			return "Simple String: " + sourceString.substring(0);
		else
			return "Simple String: " + sourceString.substring(0, 10) + "...";
	}

	@Override
	public int compareTo(AbstractDocumentSource t) {
		if (t instanceof SimpleDocumentSource == false)
			throw new IllegalArgumentException("Incompatible source type");

		SimpleDocumentSource rhs = (SimpleDocumentSource) t;

		// compare source strings
		return sourceString.compareTo(rhs.sourceString);
	}
}
