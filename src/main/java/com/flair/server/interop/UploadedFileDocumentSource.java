package com.flair.server.interop;

import com.flair.server.document.StreamDocumentSource;
import com.flair.shared.grammar.Language;

import java.io.InputStream;

final class UploadedFileDocumentSource extends StreamDocumentSource {
	// an arbitrary identifier
	private final int id;

	UploadedFileDocumentSource(InputStream source, String name, Language lang, int id) {
		super(source, name, lang);
		this.id = id;
	}

	int getId() {
		return id;
	}
}
