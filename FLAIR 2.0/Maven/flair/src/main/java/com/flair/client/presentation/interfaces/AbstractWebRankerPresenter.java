package com.flair.client.presentation.interfaces;

/*
 * Interface between the web ranker core and the UI
 */
public interface AbstractWebRankerPresenter
{
	public AbstractRankerSettingsPane			getRankerSettingsPane();
	public AbstractDocumentResultsPane			getDocumentResultsPane();
	public AbstractDocumentPreviewPane			getDocumentPreviewPane();
	
	public CorpusUploadService					getCorpusUploadService();
	public CustomKeywordService					getCustomKeywordsService();
	public UserPromptService					getPromptService();
	public NotificationService					getNotificationService();
	public VisualizerService					getVisualizerService();
	public OperationCancelService				getCancelService();
	public OverlayService						getOverlayService();
	public SettingsUrlExporterView				getSettingsUrlExporterView();
	public DocumentCompareService				getDocumentCompareService();
	public HistoryViewerService					getHistoryViewerService();
	
	public void									showLoaderOverlay(boolean visible);		// over the entire viewport
	public void									showProgressBar(boolean visible);
	
	public void									showCancelPane(boolean visible);
	public void									showDefaultPane(boolean visible);
}
