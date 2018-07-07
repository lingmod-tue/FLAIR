/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.server.parser;

import com.flair.server.utilities.AbstractTextExtractor;
import com.flair.server.utilities.TextExtractorFactory;
import com.flair.server.utilities.TextExtractorType;
import com.flair.shared.grammar.Language;

import java.io.InputStream;

/**
 * Represents a document source that encapsulates an arbitrary InputStream
 *
 * @author shadeMe
 */
public class StreamDocumentSource extends AbstractDocumentSource {
	private final String sourceString;
	private final String name;

	public StreamDocumentSource(InputStream source, String name, Language lang) {
		super(lang);
		this.name = name;

		AbstractTextExtractor extractor = TextExtractorFactory.create(TextExtractorType.TIKA);
		AbstractTextExtractor.Output output = extractor.extractText(new AbstractTextExtractor.Input(source, lang));

		if (output.success == false)
			throw new IllegalArgumentException("Cannot read from source stream");
		else
			sourceString = preprocessText(output.extractedText);
	}

	@Override
	public String getSourceText() {
		return sourceString;
	}

	@Override
	public String getDescription() {
		return "Stream: " + name;
	}

	@Override
	public int compareTo(AbstractDocumentSource t) {
		if (t instanceof StreamDocumentSource == false)
			throw new IllegalArgumentException("Incompatible source type");

		StreamDocumentSource rhs = (StreamDocumentSource) t;

		// compare source strings
		return sourceString.compareTo(rhs.sourceString);
	}

	public String getName() {
		return name;
	}
}
