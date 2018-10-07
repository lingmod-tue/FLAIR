package com.flair.shared.interop;

import com.flair.shared.grammar.Language;
import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * Base interface for all documents sent from the server to the client
 */
public interface DocumentDTO extends IsSerializable {
	Language getLanguage();

	String getTitle();
	String getSnippet();
	String getText();

	// the unique ID of the parent operation
	String getOperationId();

	// a unique ID that links multiple DTOs
	// DTOs with the same ID are related (e.g., a web search result can be linked to a parsed document)
	int getLinkingId();
}
