package com.flair.client.model.interfaces;

import com.flair.client.presentation.interfaces.AbstractWebRankerPresenter;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.AuthToken;

/*
 * Implements the core client-server communication and ranking presentation logic
 */
public interface AbstractWebRankerCore
{
	public void						init(AuthToken token, AbstractWebRankerPresenter presenter);
	public void						performWebSearch(Language lang,
													String query,
													int numResults);

	public void						cancelCurrentOperation();
	public boolean					isOperationInProgress();
}
