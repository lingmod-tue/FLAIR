package com.flair.client.presentation.interfaces;

import com.flair.client.model.interfaces.WebRankerAnalysis;

import java.util.List;

/*
 * Provides an interface to view and restore previously completed analyses
 */
public interface HistoryViewerService {
	public interface FetchAnalysesHandler {
		public List<? extends WebRankerAnalysis> handle();
	}

	public interface RestoreAnalysisHandler {
		public void handle(WebRankerAnalysis analysis);
	}

	public void setFetchAnalysesHandler(FetchAnalysesHandler handler);
	public void setRestoreAnalysisHandler(RestoreAnalysisHandler handler);
}
