package com.flair.client.presentation.interfaces;

import com.flair.shared.interop.RankableDocument;

/*
 * Implemented by document preview panes
 */
public interface AbstractDocumentPreviewPane {
	interface ShowHideHandler {
		void handle(boolean visible);
	}

	interface GenerateQuestionsHandler {
		void handle(RankableDocument doc);
	}

	void preview(DocumentPreviewPaneInput.Rankable input);
	void preview(DocumentPreviewPaneInput.UnRankable input);

	void show();
	void hide();
	boolean isVisible();
	void setShowHideEventHandler(ShowHideHandler handler);
	void setGenerateQuestionsHandler(GenerateQuestionsHandler handler);
}
