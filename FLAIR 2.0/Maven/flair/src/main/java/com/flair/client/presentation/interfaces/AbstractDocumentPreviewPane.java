package com.flair.client.presentation.interfaces;

/*
 * Implemented by document preview panes
 */
public interface AbstractDocumentPreviewPane
{
	public interface ShowHideHandler {
		public void handle(boolean visible);
	}
	
	public void			preview(DocumentPreviewPaneInput.Rankable input);
	public void			preview(DocumentPreviewPaneInput.UnRankable input);
	
	public void			show();
	public void			hide();
	public boolean		isVisible();
	public void 		setShowHideEventHandler(ShowHideHandler handler);
}
