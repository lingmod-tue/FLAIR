/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.

 */
package com.flair.server.document;

import com.flair.shared.grammar.Language;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Represents a document source object that encapsulates the contents of a local file
 *
 * @author shadeMe
 */
public class LocalFileDocumentSource extends AbstractDocumentSource {
	private final StreamDocumentSource source;
	private final String filePath;

	public LocalFileDocumentSource(File sourceFile, Language lang) {
		this(sourceFile, sourceFile.getName(), lang);
	}

	public LocalFileDocumentSource(File sourceFile, String filename, Language lang) {
		super(lang);
		if (sourceFile.canRead() == false)
			throw new IllegalArgumentException("Cannot read from source file at " + sourceFile.getAbsolutePath());
		else if (sourceFile.isFile() == false)
			throw new IllegalArgumentException("Invalid source file at " + sourceFile.getAbsolutePath());

		try {
			source = new StreamDocumentSource(new FileInputStream(sourceFile), filename, lang);
			filePath = sourceFile.getAbsolutePath();
			if (source.getSourceText().isEmpty())
				throw new IllegalArgumentException("Empty source file at " + sourceFile.getAbsolutePath());
		} catch (IOException ex) {
			throw new IllegalArgumentException("Cannot read from source file at " + sourceFile.getAbsolutePath() + ". Exception: " + ex.getMessage());
		}
	}

	@Override
	public String getSourceText() {
		return source.getSourceText();
	}

	@Override
	public String getDescription() {
		return "Local File: " + source.getName();
	}

	@Override
	public int compareTo(AbstractDocumentSource t) {
		if (t instanceof LocalFileDocumentSource == false)
			throw new IllegalArgumentException("Incompatible source type");

		LocalFileDocumentSource rhs = (LocalFileDocumentSource) t;
		return source.compareTo(rhs.source);
	}
}
