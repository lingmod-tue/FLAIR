package com.flair.client.model.interfaces;

import com.flair.client.model.interfaces.AbstractWebRankerCore.OperationType;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.dtos.RankableDocument;

import java.util.List;

/*
 * Represents a executing or completed analysis operation
 */
public interface WebRankerAnalysis {
	OperationType getType();
	String getName();        // web search - <query>
	// corpus - <list of file names>

	Language getLanguage();
	List<RankableDocument> getParsedDocs();
	boolean inProgress();
}