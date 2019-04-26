package com.flair.server.interop.services;

import com.flair.server.interop.session.SessionManager;
import com.flair.shared.exceptions.InvalidAuthTokenException;
import com.flair.shared.exceptions.ServerRuntimeException;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.RankableDocument;
import com.flair.shared.interop.ServerAuthenticationToken;
import com.flair.shared.interop.services.WebRankerService;

import java.util.ArrayList;

public class WebRankerServiceImpl extends AbstractRemoteService implements WebRankerService {
	@Override
	public void beginWebSearch(AuthToken token,
	                           Language lang,
	                           String query,
	                           int numResults,
	                           ArrayList<String> keywords) throws InvalidAuthTokenException, ServerRuntimeException {
		ServerAuthenticationToken authToken = validateToken(token);
		SessionManager.get().getSessionState(authToken).searchCrawlParse(query, lang, numResults, keywords);
	}

	@Override
	public void beginCorpusUpload(AuthToken token, Language lang, ArrayList<String> keywords) throws InvalidAuthTokenException, ServerRuntimeException {
		ServerAuthenticationToken authToken = validateToken(token);
		SessionManager.get().getSessionState(authToken).beginCustomCorpusUpload(lang, keywords);
	}

	@Override
	public void endCorpusUpload(AuthToken token, boolean success) throws InvalidAuthTokenException {
		ServerAuthenticationToken authToken = validateToken(token);
		SessionManager.get().getSessionState(authToken).endCustomCorpusUpload(success);
	}
	@Override
	public void eagerParseForQuestionGen(AuthToken token, RankableDocument doc) throws InvalidAuthTokenException, ServerRuntimeException {
		ServerAuthenticationToken authToken = validateToken(token);
		SessionManager.get().getSessionState(authToken).eagerParseForQuestionGen(doc);
	}

	@Override
	public void generateQuestions(AuthToken token, RankableDocument doc, int numQuestions, boolean randomizeSelection) throws InvalidAuthTokenException, ServerRuntimeException {
		ServerAuthenticationToken authToken = validateToken(token);
		SessionManager.get().getSessionState(authToken).generateQuestions(doc, numQuestions, randomizeSelection);
	}

	@Override
	public void cancelCurrentOperation(AuthToken token) throws InvalidAuthTokenException, ServerRuntimeException {
		ServerAuthenticationToken authToken = validateToken(token);
		SessionManager.get().getSessionState(authToken).cancelOperation();
	}
}
