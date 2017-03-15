package com.flair.client.presentation.interfaces;

/*
 * Implemented by document preview panes
 */
public interface AbstractDocumentPreviewPane
{
	public void			show(DocumentPreviewPaneInput.Rankable input);
	public void			show(DocumentPreviewPaneInput.UnRankable input);
	public void			hide();
}
