package com.flair.server.interop;

import com.flair.server.document.StreamDocumentSource;
import com.flair.shared.grammar.Language;

import java.io.InputStream;

final class UploadedFileDocumentSource extends StreamDocumentSource {
	// an arbitrary identifier
	private final int id;
	private final String extension;

	UploadedFileDocumentSource(InputStream source, String name, Language lang, int id, String extension) {
		super(source, name, lang);
		this.id = id;
		this.extension = extension;
	}

	int getId() {
		return id;
	}

	public String getExtension() {
		return extension;
	}
}
