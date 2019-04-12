package com.flair.client.presentation.interfaces;

/*
 * Implemented by document preview panes
 */
public interface AbstractDocumentPreviewPane {
	interface ShowHideHandler {
		void handle(boolean visible);
	}

	void preview(DocumentPreviewPaneInput.Rankable input);
	void preview(DocumentPreviewPaneInput.UnRankable input);

	void show();
	void hide();
	boolean isVisible();
	void setShowHideEventHandler(ShowHideHandler handler);
}
