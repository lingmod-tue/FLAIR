package com.flair.client.presentation.interfaces;

import com.flair.client.model.interfaces.AbstractWebRankerCore;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.dtos.RankableDocument;

import java.util.List;

/*
 * Provides an interface to mark and compare parsed documents
 */
public interface DocumentCompareService {
	public interface CompareHandler {
		public void handle(Language lang, List<RankableDocument> docs);
	}

	public void addToSelection(RankableDocument doc);
	public void removeFromSelection(RankableDocument doc);
	public boolean isInSelection(RankableDocument doc);    // checks the display url for equality
	public void clearSelection(Language lang);
	public int getSelectionCount(Language lang);

	public void setCompareHandler(CompareHandler handler);
	public void bindToWebRankerCore(AbstractWebRankerCore core);
}
