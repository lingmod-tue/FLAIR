package com.flair.client.presentation.interfaces;

import com.flair.shared.grammar.Language;

/*
 * Uploads custom files to the server for analysis
 */
public interface CorpusUploadService {
	public interface UploadBeginHandler {
		public void handle(Language lang);
	}

	public interface UploadCompleteHandler {
		public void handle(int numUploaded, boolean success);
	}

	public void show();
	public void hide();

	public void setUploadBeginHandler(UploadBeginHandler handler);
	public void setUploadCompleteHandler(UploadCompleteHandler handler);
}
