package com.flair.shared.interop.services;

import java.util.ArrayList;

import com.flair.shared.grammar.Language;
import com.flair.shared.interop.AuthToken;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WebRankerServiceAsync
{
	public void			beginWebSearch(AuthToken token,
									Language lang,
									String query,
									int numResults,
									ArrayList<String> keywords,
									AsyncCallback<Void> callback);

	public void			beginCorpusUpload(AuthToken token,
									Language lang,
									ArrayList<String> keywords,
									AsyncCallback<Void> callback);
	
	public void			cancelCurrentOperation(AuthToken token, AsyncCallback<Void> callback);
}
