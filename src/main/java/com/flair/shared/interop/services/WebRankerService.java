package com.flair.shared.interop.services;

import com.flair.shared.grammar.Language;
import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.InvalidAuthTokenException;
import com.flair.shared.interop.RankableDocument;
import com.flair.shared.interop.ServerRuntimeException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.ArrayList;

/*
 * Interface used by the client domain to perform tasks
 */
@RemoteServiceRelativePath("WebRanker")
public interface WebRankerService extends RemoteService {
	void beginWebSearch(AuthToken token,
	                    Language lang,
	                    String query,
	                    int numResults,
	                    ArrayList<String> keywords) throws InvalidAuthTokenException, ServerRuntimeException;

	void beginCorpusUpload(AuthToken token,
	                       Language lang,
	                       ArrayList<String> keywords) throws InvalidAuthTokenException, ServerRuntimeException;        // signals the start of the upload operation and caches params
	void endCorpusUpload(AuthToken token,
	                     boolean success) throws InvalidAuthTokenException, ServerRuntimeException;                     // signals the end of the uploading process, begins the parsing op if successful

	void eagerParseForQuestionGen(AuthToken token,
	                             RankableDocument doc) throws InvalidAuthTokenException, ServerRuntimeException;        // eagerly parses a document for question generation
	void generateQuestions(AuthToken token,
	                       RankableDocument doc,
	                       int numQuestions,
	                       boolean randomizeSelection) throws InvalidAuthTokenException, ServerRuntimeException;

	void cancelCurrentOperation(AuthToken token) throws InvalidAuthTokenException, ServerRuntimeException;
}

