package com.flair.server.interop;

import java.io.InputStream;

public final class CustomCorpusFile {
	private final InputStream stream;
	private final String fileName;

	CustomCorpusFile(InputStream input, String fileName) {
		this.stream = input;
		this.fileName = fileName;
	}

	public InputStream getStream() {
		return stream;
	}

	public String getFilename() {
		return fileName;
	}
}
