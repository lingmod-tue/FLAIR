package com.flair.client.presentation.interfaces;

/*
 * Implemented by document preview panes
 */
public interface AbstractDocumentPreviewPane
{
	public interface EventHandler {
		public void handle();
	}
	
	public void			preview(DocumentPreviewPaneInput.Rankable input);
	public void			preview(DocumentPreviewPaneInput.UnRankable input);
	
	public void			show();
	public void			hide();
	
	public void			setClosingEventHandler(EventHandler handler);		// when closed from inside the preview pane
}
