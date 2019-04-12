package com.flair.client.presentation.interfaces;

import com.flair.shared.grammar.Language;

/*
 * Uploads custom files to the server for analysis
 */
public interface CorpusUploadService {
	interface UploadBeginHandler {
		void handle(Language lang);
	}

	interface UploadCompleteHandler {
		void handle(int numUploaded, boolean success);
	}

	void show();
	void hide();

	void setUploadBeginHandler(UploadBeginHandler handler);
	void setUploadCompleteHandler(UploadCompleteHandler handler);
}
