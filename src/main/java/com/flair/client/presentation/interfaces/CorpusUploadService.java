package com.flair.client.presentation.interfaces;

import com.flair.shared.grammar.Language;

/*
 * Uploads custom files to the server for analysis
 */
public interface CorpusUploadService {
	interface UploadCompleteHandler {
		void handle(Language lang, int numUploaded);
	}

	void show();
	void hide();

	void setUploadCompleteHandler(UploadCompleteHandler handler);
}
