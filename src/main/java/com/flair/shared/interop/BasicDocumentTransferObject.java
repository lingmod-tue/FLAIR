package com.flair.shared.interop;

import com.flair.shared.grammar.Language;
import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * Base interface for all documents sent from the server to the client
 */
public interface BasicDocumentTransferObject extends IsSerializable {
	public Language getLanguage();

	public String getTitle();
	public String getSnippet();
	public String getText();

	public int getIdentifier();        // a unique ID that links multiple DTOs
	// DTOs with the same ID are related (e.g., a web search result can be linked to a parsed document)
}
