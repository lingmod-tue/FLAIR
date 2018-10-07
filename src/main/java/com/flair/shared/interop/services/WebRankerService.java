package com.flair.shared.interop.services;

import com.flair.shared.grammar.Language;
import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.RankableDocument;
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
	                    ArrayList<String> keywords);

	void beginCorpusUpload(AuthToken token,
	                       Language lang,
	                       ArrayList<String> keywords);        // signals the start of the upload operation and caches params
	void endCorpusUpload(AuthToken token,
	                     boolean success);                    // signals the end of the uploading process, begins the parsing op if successful

	void generateQuestions(AuthToken token,
	                       RankableDocument doc,
	                       int numQuestions);

	void cancelCurrentOperation(AuthToken token);
}

