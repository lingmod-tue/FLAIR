package com.flair.client.presentation.interfaces;

/*
 * Uploads custom files to the server for analysis
 */
public interface CorpusUploadService
{
	public interface UploadBeginHandler {
		public void handle();
	}
	
	public interface UploadCompleteHandler {
		public void handle(int numUploaded);
	}
	
	public void			show();
	public void			hide();
	
	public void			setUploadBeginHandler(UploadBeginHandler handler);
	public void			setUploadCompleteHandler(UploadCompleteHandler handler);
}
