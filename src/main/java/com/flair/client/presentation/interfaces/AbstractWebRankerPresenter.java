package com.flair.client.presentation.interfaces;

/*
 * Interface between the web ranker core and the UI
 */
public interface AbstractWebRankerPresenter {
	AbstractRankerSettingsPane getRankerSettingsPane();
	AbstractDocumentResultsPane getDocumentResultsPane();
	AbstractDocumentPreviewPane getDocumentPreviewPane();

	WebSearchService getWebSearchService();
	CorpusUploadService getCorpusUploadService();
	CustomKeywordService getCustomKeywordsService();
	UserPromptService getPromptService();
	NotificationService getNotificationService();
	VisualizerService getVisualizerService();
	OperationCancelService getCancelService();
	OverlayService getOverlayService();
	SettingsUrlExporterView getSettingsUrlExporterView();
	DocumentCompareService getDocumentCompareService();
	HistoryViewerService getHistoryViewerService();
	QuestionGeneratorPreviewService getQuestionGeneratorPreviewService();

	void showLoaderOverlay(boolean visible);        // over the entire viewport
	void showProgressBar(boolean visible);

	void showCancelPane(boolean visible);
	void showDefaultPane(boolean visible);
}
