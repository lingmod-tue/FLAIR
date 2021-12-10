package com.flair.server.interop;

import java.io.InputStream;

public final class CustomCorpusFile {
	private InputStream stream;
	private final String fileName;
	private final String extension;

	CustomCorpusFile(InputStream input, String fileName, String extension) {
		this.stream = input;
		this.fileName = fileName;
		this.extension = extension;
	}

	public InputStream getStream() {
		return stream;
	}

	public String getFilename() {
		return fileName;
	}
	
	public void setStream(InputStream input)  {
		this.stream = input;
	}

	public String getExtension() {
		return extension;
	}
	
}
