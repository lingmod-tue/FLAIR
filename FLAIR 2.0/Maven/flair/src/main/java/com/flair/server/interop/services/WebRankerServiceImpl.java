package com.flair.server.interop.services;

import java.util.ArrayList;

import com.flair.server.interop.session.SessionManager;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.ServerAuthenticationToken;
import com.flair.shared.interop.services.WebRankerService;

public class WebRankerServiceImpl extends AbstractRemoteService implements WebRankerService
{
	@Override
	public void beginWebSearch(AuthToken token,
							Language lang,
							String query,
							int numResults,
							ArrayList<String> keywords)
	{
		ServerAuthenticationToken authToken = validateToken(token);
		SessionManager.get().getSessionState(authToken).searchCrawlParse(query, lang, numResults, keywords);
	}

	@Override
	public void beginCorpusUpload(AuthToken token, Language lang, ArrayList<String> keywords)
	{
		ServerAuthenticationToken authToken = validateToken(token);
		SessionManager.get().getSessionState(authToken).beginCustomCorpusUpload(lang, keywords);
	}

	@Override
	public void endCorpusUpload(AuthToken token, boolean success)
	{
		ServerAuthenticationToken authToken = validateToken(token);
		SessionManager.get().getSessionState(authToken).endCustomCorpusUpload(success);
	}

	@Override
	public void cancelCurrentOperation(AuthToken token)
	{
		ServerAuthenticationToken authToken = validateToken(token);
		SessionManager.get().getSessionState(authToken).cancelOperation();
	}
}
