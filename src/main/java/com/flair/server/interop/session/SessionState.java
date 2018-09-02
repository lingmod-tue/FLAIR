
package com.flair.server.interop.session;

import com.flair.server.crawler.SearchResult;
import com.flair.server.document.*;
import com.flair.server.grammar.DefaultVocabularyList;
import com.flair.server.interop.MessagePipeline;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.parser.KeywordSearcherOutput;
import com.flair.server.pipelines.PipelineOp;
import com.flair.server.pipelines.PipelineOpBuilder;
import com.flair.server.pipelines.gramparsing.GramParsingPipeline;
import com.flair.server.utilities.ServerLogger;
import com.flair.server.utilities.TextSegment;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.*;
import com.flair.shared.interop.ServerMessage.Type;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores the state of a client
 */
public class SessionState {
	static final class TemporaryCache {
		static final class CustomCorpus {
			Language lang;
			KeywordSearcherInput keywords;
			List<CustomCorpusFile> uploaded;

			public CustomCorpus(Language l, List<String> k) {
				lang = l;

				if (k.isEmpty())
					keywords = new KeywordSearcherInput(DefaultVocabularyList.get(lang));
				else
					keywords = new KeywordSearcherInput(k);

				uploaded = new ArrayList<>();
			}
		}

		CustomCorpus corpusData;
	}

	static final class UploadedFileDocumentSource extends StreamDocumentSource {
		private final int id;        // arbitrary identifier

		UploadedFileDocumentSource(InputStream source, String name, Language lang, int id) {
			super(source, name, lang);
			this.id = id;
		}

		int getId() {
			return id;
		}
	}

	private final ServerAuthenticationToken token;
	private final AbstractMesageSender messagePipeline;
	private PipelineOp currentOp;
	private TemporaryCache cache;

	public SessionState(ServerAuthenticationToken tok) {
		token = tok;
		messagePipeline = MessagePipeline.get().createSender();
		currentOp = null;
		cache = new TemporaryCache();

		messagePipeline.open(token);
	}

	private void beginOperation(PipelineOpBuilder opBuilder) {
		if (hasActiveOp())
			throw new RuntimeException("Previous state not cleared");

		// clear the message queue just in case any old messages ended up there
		messagePipeline.clearPendingMessages();

		currentOp = opBuilder.launch();
		ServerLogger.get().info("Pipeline operation '" + currentOp.name() + "' has begun");
	}

	private void endOperation(boolean cancel) {
		if (!hasActiveOp())
			throw new RuntimeException("No operation running");

		if (cancel) {
			currentOp.cancel();
			ServerLogger.get().info("Pipeline operation '" + currentOp.name() + "' was cancelled");
		} else
			ServerLogger.get().info("Pipeline operation '" + currentOp.name() + "' has ended");

		currentOp = null;
	}

	private boolean hasActiveOp() {
		return currentOp != null;
	}

	public synchronized void cancelOperation() {
		if (!hasActiveOp()) {
			sendErrorResponse("No active operation to cancel");
			return;
		}

		endOperation(true);
	}

	private RankableDocumentImpl generateRankableDocument(AbstractDocument source) {
		RankableDocumentImpl out = new RankableDocumentImpl();
		final int snippetMaxLen = 100;

		if (!source.isParsed())
			throw new IllegalStateException("Document not flagged as parsed");

		out.setLanguage(source.getLanguage());
		if (source.getDocumentSource() instanceof SearchResultDocumentSource) {
			SearchResultDocumentSource searchSource = (SearchResultDocumentSource) source.getDocumentSource();
			SearchResult searchResult = searchSource.getSearchResult();

			out.setTitle(searchResult.getTitle());
			out.setUrl(searchResult.getURL());
			out.setDisplayUrl(searchResult.getDisplayURL());
			out.setSnippet(searchResult.getSnippet());
			out.setRank(searchResult.getRank());
			out.setIdentifier(searchResult.getRank());        // ranks don't overlap, so we can use them as ids
		} else if (source.getDocumentSource() instanceof UploadedFileDocumentSource) {
			UploadedFileDocumentSource localSource = (UploadedFileDocumentSource) source.getDocumentSource();

			out.setTitle(localSource.getName());

			String textSnip = source.getText();
			if (textSnip.length() > snippetMaxLen)
				out.setSnippet(textSnip.substring(0, snippetMaxLen) + "...");
			else
				out.setSnippet(textSnip);

			out.setIdentifier(localSource.getId());        // use the id generated earlier
			out.setRank(localSource.getId());            // in the same order the files were uploaded to the server
		}

		out.setText(source.getText());

		for (GrammaticalConstruction itr : source.getSupportedConstructions()) {
			DocumentConstructionData data = source.getConstructionData(itr);
			if (data.hasConstruction()) {
				out.getConstructions().add(itr);
				out.getRelFrequencies().put(itr, data.getRelativeFrequency());
				out.getFrequencies().put(itr, data.getFrequency());

				ArrayList<RankableDocumentImpl.ConstructionOccurrence> highlights = new ArrayList<>();
				for (ConstructionOccurrence occr : data.getOccurrences())
					highlights.add(new RankableDocumentImpl.ConstructionOccurrence(occr.getStart(), occr.getEnd(), itr));

				out.getConstOccurrences().put(itr, highlights);
			}
		}

		KeywordSearcherOutput keywordData = source.getKeywordData();
		if (keywordData != null) {
			for (String itr : keywordData.getKeywords()) {
				for (TextSegment hit : keywordData.getHits(itr))
					out.getKeywordOccurrences().add(new RankableDocumentImpl.KeywordOccurrence(hit.getStart(), hit.getEnd(), itr));
			}

			out.setKeywordCount(keywordData.getTotalHitCount());
			out.setKeywordRelFreq(keywordData.getTotalHitCount() / source.getNumWords());
		}

		out.setRawTextLength(source.getText().length());
		out.setNumWords(source.getNumWords());
		out.setNumSentences(source.getNumSentences());
		out.setNumDependencies(source.getNumDependencies());
		out.setReadabilityLevel(source.getReadabilityLevel());
		out.setReadabilityScore(source.getReadabilityScore());

		return out;
	}

	private RankableWebSearchResultImpl generateRankableWebSearchResult(SearchResult sr) {
		RankableWebSearchResultImpl out = new RankableWebSearchResultImpl();

		out.setRank(sr.getRank());
		out.setTitle(sr.getTitle());
		out.setLang(sr.getLanguage());
		out.setUrl(sr.getURL());
		out.setDisplayUrl(sr.getDisplayURL());
		out.setSnippet(sr.getSnippet());
		out.setText(sr.getPageText());
		out.setIdentifier(sr.getRank());

		return out;
	}

	private ArrayList<UploadedDocument> generateUploadedDocs(Iterable<AbstractDocumentSource> source) {
		ArrayList<UploadedDocument> out = new ArrayList<>();

		for (AbstractDocumentSource itr : source) {
			UploadedFileDocumentSource sdr = (UploadedFileDocumentSource) itr;
			UploadedDocumentImpl u = new UploadedDocumentImpl();
			String snippet = itr.getSourceText();
			if (snippet.length() > 100)
				snippet = snippet.substring(0, 100);

			u.setLanguage(itr.getLanguage());
			u.setTitle(sdr.getName());
			u.setSnippet(snippet);
			u.setText(itr.getSourceText());
			u.setIdentifier(sdr.getId());

			out.add(u);
		}

		return out;
	}

	private void sendMessageToClient(ServerMessage msg) {
		ServerLogger.get().info("Sent message to client: " + msg.toString());
		messagePipeline.send(msg);
	}

	private synchronized void handleCorpusJobBegin(Iterable<AbstractDocumentSource> source) {
		if (hasActiveOp() == false) {
			ServerLogger.get().error("Invalid corpus job begin event");
			return;
		}

		ServerMessage msg = new ServerMessage(token);
		ServerMessage.CustomCorpus d = new ServerMessage.CustomCorpus(generateUploadedDocs(source));
		msg.setCustomCorpus(d);
		msg.setType(ServerMessage.Type.CUSTOM_CORPUS);

		sendMessageToClient(msg);
	}

	private synchronized void handleCrawlComplete(SearchResult sr) {
		if (hasActiveOp() == false) {
			ServerLogger.get().error("Invalid crawl complete event");
			return;
		}

		ServerMessage msg = new ServerMessage(token);
		ServerMessage.SearchCrawlParse d = new ServerMessage.SearchCrawlParse(generateRankableWebSearchResult(sr));
		msg.setSearchCrawlParse(d);
		msg.setType(ServerMessage.Type.SEARCH_CRAWL_PARSE);

		sendMessageToClient(msg);
	}

	private synchronized void handleParseComplete(ServerMessage.Type t, AbstractDocument doc) {
		if (hasActiveOp() == false) {
			ServerLogger.get().error("Invalid parse complete event for " + t);
			return;
		}

		ServerMessage msg = new ServerMessage(token);
		switch (t) {
		case CUSTOM_CORPUS:
			msg.setCustomCorpus(new ServerMessage.CustomCorpus(generateRankableDocument(doc)));
			break;
		case SEARCH_CRAWL_PARSE:
			msg.setSearchCrawlParse(new ServerMessage.SearchCrawlParse(generateRankableDocument(doc)));
			break;
		}

		msg.setType(t);
		sendMessageToClient(msg);
	}

	private synchronized void handleJobComplete(ServerMessage.Type t, DocumentCollection docs) {
		if (hasActiveOp() == false) {
			ServerLogger.get().error("Invalid job complete event for " + t);
			return;
		}

		ServerMessage msg = new ServerMessage(token);
		switch (t) {
		case CUSTOM_CORPUS:
			msg.setCustomCorpus(new ServerMessage.CustomCorpus(ServerMessage.CustomCorpus.Type.JOB_COMPLETE));
			break;
		case SEARCH_CRAWL_PARSE:
			msg.setSearchCrawlParse(new ServerMessage.SearchCrawlParse(ServerMessage.SearchCrawlParse.Type.JOB_COMPLETE));
			break;
		}

		msg.setType(t);
		sendMessageToClient(msg);

		// reset operation state
		endOperation(false);
	}

	private void sendErrorResponse(String err) {
		ServerLogger.get().error(err);

		ServerMessage msg = new ServerMessage(token);
		ServerMessage.Error d = new ServerMessage.Error(err);
		msg.setError(d);
		msg.setType(Type.ERROR);

		sendMessageToClient(msg);
	}

	public synchronized void handleCorpusUpload(List<CustomCorpusFile> corpus) {
		ServerLogger.get().info("Received custom corpus from client");

		if (hasActiveOp()) {
			sendErrorResponse("Another operation still running");
			return;
		} else if (cache.corpusData == null) {
			sendErrorResponse("Invalid params for custom corpus");
			return;
		}

		// save the uploaded file for later
		for (CustomCorpusFile itr : corpus)
			cache.corpusData.uploaded.add(itr);
	}

	public synchronized void searchCrawlParse(String query, Language lang, int numResults, List<String> keywords) {
		ServerLogger.get().info("Begin search-crawl-parse -> Query: " + query + ", Language: " + lang.toString() + ", Results: " + numResults);

		if (hasActiveOp()) {
			sendErrorResponse("Another operation still running");
			return;
		}

		KeywordSearcherInput keywordInput;
		if (keywords.isEmpty())
			keywordInput = new KeywordSearcherInput(DefaultVocabularyList.get(lang));
		else
			keywordInput = new KeywordSearcherInput(keywords);

		GramParsingPipeline.SearchCrawlParseOpBuilder builder = GramParsingPipeline.get().searchCrawlParse();
		builder.lang(lang)
				.query(query)
				.results(numResults)
				.keywords(keywordInput)
				.onCrawl(this::handleCrawlComplete)
				.onParse(e -> handleParseComplete(ServerMessage.Type.SEARCH_CRAWL_PARSE, e))
				.onComplete(e -> handleJobComplete(ServerMessage.Type.SEARCH_CRAWL_PARSE, e));

		beginOperation(builder);
	}

	public synchronized void beginCustomCorpusUpload(Language lang, List<String> keywords) {
		ServerLogger.get().info("Begin custom corpus uploading -> Language: " + lang.toString());

		if (hasActiveOp()) {
			ServerLogger.get().info("Another operation is in progress - Discarding file");
			return;
		}

		// cache params and await the upload servlet
		// discard the previous cache, if any
		cache.corpusData = new TemporaryCache.CustomCorpus(lang, keywords);
		ServerLogger.get().info("Cached corpus parse parameters");
	}

	public synchronized void endCustomCorpusUpload(boolean success) {
		ServerLogger.get().info("End custom corpus uploading | Success: " + success);

		if (hasActiveOp()) {
			sendErrorResponse("Another operation still running");
			return;
		}

		if (!success) {
			// don't begin the parse op
			cache.corpusData = null;
			return;
		}

		// begin parsing operation
		List<AbstractDocumentSource> sources = new ArrayList<>();
		try {
			int i = 1;        // assign identifiers to the files for later use
			for (CustomCorpusFile itr : cache.corpusData.uploaded) {
				sources.add(new UploadedFileDocumentSource(itr.getStream(),
						itr.getFilename(),
						cache.corpusData.lang,
						i));
				i++;
			}
		} catch (Throwable ex) {
			sendErrorResponse("Couldn't read custom corpus files. Exception: " + ex.getMessage());
		}


		GramParsingPipeline.ParseOpBuilder builder = GramParsingPipeline.get().documentParse();
		builder.lang(cache.corpusData.lang)
				.docSource(sources)
				.keywords(cache.corpusData.keywords)
				.onParse(e -> handleParseComplete(ServerMessage.Type.CUSTOM_CORPUS, e))
				.onComplete(e -> handleJobComplete(ServerMessage.Type.CUSTOM_CORPUS, e));

		beginOperation(builder);

		// reset cache
		cache.corpusData = null;
	}

	public synchronized void release() {
		if (hasActiveOp()) {
			ServerLogger.get().warn("Pipeline operation is still executing at the time of shutdown. Status: "
					+ currentOp.toString());

			endOperation(true);
		}

		if (messagePipeline.isOpen())
			messagePipeline.close();
	}
}
