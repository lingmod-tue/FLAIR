package com.flair.shared.interop;

import com.flair.shared.grammar.Language;
import com.google.gwt.user.client.rpc.IsSerializable;

/*
 * Base interface for all documents sent from the server to the client
 */
public interface BasicDocumentTransferObject extends IsSerializable
{
	public Language			getLanguage();
	
	public String			getTitle();
	public String			getSnippet();
	public String			getText();
	public int				hashCode();			// just hash the title, snippet and text to allow comparisons with other DTOs
												// not the most fool-proof method but saves us from manually mapping doc sources to their parsed versions
}
