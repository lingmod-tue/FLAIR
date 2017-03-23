/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.

 */
package com.flair.server.interop.session;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.flair.server.crawler.SearchResult;
import com.flair.server.grammar.DefaultVocabularyList;
import com.flair.server.interop.MessagePipeline;
import com.flair.server.parser.AbstractDocument;
import com.flair.server.parser.AbstractDocumentSource;
import com.flair.server.parser.DocumentCollection;
import com.flair.server.parser.DocumentConstructionData;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.parser.KeywordSearcherOutput;
import com.flair.server.parser.SearchResultDocumentSource;
import com.flair.server.parser.StreamDocumentSource;
import com.flair.server.parser.TextSegment;
import com.flair.server.taskmanager.AbstractPipelineOperation;
import com.flair.server.taskmanager.CustomParseOperation;
import com.flair.server.taskmanager.MasterJobPipeline;
import com.flair.server.taskmanager.PipelineOperationType;
import com.flair.server.taskmanager.SearchCrawlParseOperation;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.AbstractMesageSender;
import com.flair.shared.interop.RankableDocumentImpl;
import com.flair.shared.interop.RankableWebSearchResultImpl;
import com.flair.shared.interop.ServerAuthenticationToken;
import com.flair.shared.interop.ServerMessage;
import com.flair.shared.interop.ServerMessage.Type;
import com.flair.shared.interop.UploadedDocument;
import com.flair.shared.interop.UploadedDocumentImpl;

/**
 * Stores the state of a client
 *
 * @author shadeMe
 */
public class SessionState
{
	static final class OperationState
	{
		public final PipelineOperationType			type;
		public final SearchCrawlParseOperation		searchCrawlParse;
		public final CustomParseOperation			customParse;

		OperationState(SearchCrawlParseOperation op)
		{
			type = op.getType();
			searchCrawlParse = op;
			customParse = null;
		}

		OperationState(CustomParseOperation op)
		{
			type = op.getType();
			searchCrawlParse = null;
			customParse = op;
		}

		public AbstractPipelineOperation get()
		{
			switch (type)
			{
			case SEARCH_CRAWL_PARSE:
				return searchCrawlParse;
			case CUSTOM_PARSE:
				return customParse;
			}

			return null;
		}
	}

	static final class TemporaryCache
	{
		static final class CustomCorpus
		{
			Language					lang;
			KeywordSearcherInput		keywords;
			List<CustomCorpusFile>		uploaded;


			public CustomCorpus(Language l, List<String> k)
			{
				lang = l;

				if (k.isEmpty())
					keywords = new KeywordSearcherInput(DefaultVocabularyList.get(lang));
				else
					keywords = new KeywordSearcherInput(k);

				uploaded = new ArrayList<>();
			}
		}

		CustomCorpus			corpusData;
	}

	static final class UploadedFileDocumentSource extends StreamDocumentSource
	{
		private final int			id;		// arbitrary identifier

		public UploadedFileDocumentSource(InputStream source, String name, Language lang, int id)
		{
			super(source, name, lang);
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}

	private final ServerAuthenticationToken		token;
	private final AbstractMesageSender			messagePipeline;
	private OperationState						currentOperation;
	private TemporaryCache						cache;

	public SessionState(ServerAuthenticationToken tok)
	{
		token = tok;
		messagePipeline = MessagePipeline.get().createSender();
		currentOperation = null;
		cache = new TemporaryCache();

		messagePipeline.open(token);
	}

	private void beginOperation(OperationState state)
	{
		if (hasOperation())
			throw new RuntimeException("Previous state not cleared");

		currentOperation = state;

		// clear the message queue just in case any old messages ended up there
		messagePipeline.clearPendingMessages();
		currentOperation.get().begin();
		
		ServerLogger.get().info("Pipeline operation " + currentOperation.type + " has begun");
	}

	private void endOperation(boolean cancel)
	{
		if (hasOperation() == false)
			throw new RuntimeException("No operation running");

		if (cancel)
			currentOperation.get().cancel();

		ServerLogger.get().info("Pipeline operation " + currentOperation.type + " has ended | Cancelled = " + cancel);
		currentOperation = null;
	}

	public synchronized boolean hasOperation() {
		return currentOperation != null;
	}

	public synchronized void cancelOperation()
	{
		if (hasOperation() == false)
		{
			sendErrorResponse("No active operation to cancel");
			return;
		}

		endOperation(true);
	}

	private RankableDocumentImpl generateRankableDocument(AbstractDocument source)
	{
		RankableDocumentImpl out = new RankableDocumentImpl();

		if (source.isParsed() == false)
			throw new IllegalStateException("Document not flagged as parsed");

		if (source.getDocumentSource() instanceof SearchResultDocumentSource)
		{
			SearchResultDocumentSource searchSource = (SearchResultDocumentSource) source.getDocumentSource();
			SearchResult searchResult = searchSource.getSearchResult();

			out.setTitle(searchResult.getTitle());
			out.setUrl(searchResult.getURL());
			out.setDisplayUrl(searchResult.getDisplayURL());
			out.setSnippet(searchResult.getSnippet());
			out.setRank(searchResult.getRank());
			out.setIdentifier(searchResult.getRank());		// ranks don't overlap, so we can use them as ids
		}
		else if (source.getDocumentSource() instanceof UploadedFileDocumentSource)
		{
			UploadedFileDocumentSource localSource = (UploadedFileDocumentSource) source.getDocumentSource();

			out.setTitle(localSource.getName());

			String textSnip = source.getText();
			if (textSnip.length() > 35)
				out.setSnippet(textSnip.substring(0, 35) + "...");
			else
				out.setSnippet(textSnip);

			out.setIdentifier(localSource.getId());		// use the id generated earlier
		}

		out.setText(source.getText());

		for (GrammaticalConstruction itr : GrammaticalConstruction.values())
		{
			DocumentConstructionData data = source.getConstructionData(itr);
			if (data.hasConstruction())
			{
				out.getConstructions().add(itr);
				out.getRelFrequencies().put(itr, data.getRelativeFrequency());
				out.getFrequencies().put(itr, data.getFrequency());

				ArrayList<RankableDocumentImpl.ConstructionOccurrence> highlights = new ArrayList<>();
				for (com.flair.server.parser.ConstructionOccurrence occr : data.getOccurrences())
					highlights.add(new RankableDocumentImpl.ConstructionOccurrence(occr.getStart(), occr.getEnd(), itr));

				out.getConstOccurrences().put(itr, highlights);
			}
		}

		KeywordSearcherOutput keywordData = source.getKeywordData();
		if (keywordData != null)
		{
			for (String itr : keywordData.getKeywords())
			{
				List<TextSegment> hits = keywordData.getHits(itr);
				for (TextSegment hit : hits)
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

		return out;
	}

	private RankableWebSearchResultImpl generateRankableWebSearchResult(SearchResult sr)
	{
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

	private ArrayList<UploadedDocument> generateUploadedDocs(Iterable<AbstractDocumentSource> source)
	{
		ArrayList<UploadedDocument> out = new ArrayList<>();

		for (AbstractDocumentSource itr : source)
		{
			UploadedFileDocumentSource sdr = (UploadedFileDocumentSource)itr;
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

	private void sendMessageToClient(ServerMessage msg)
	{
		ServerLogger.get().info("Sent message to client: " + msg.toString());
		messagePipeline.send(msg);
	}
	
	private synchronized void handleCorpusJobBegin(Iterable<AbstractDocumentSource> source)
	{
		if (hasOperation() == false)
		{
			ServerLogger.get().error("Invalid corpus job begin event");
			return;
		}

		ServerMessage msg = new ServerMessage(token);
		ServerMessage.CustomCorpus d = new ServerMessage.CustomCorpus(generateUploadedDocs(source));
		msg.setCustomCorpus(d);
		msg.setType(ServerMessage.Type.CUSTOM_CORPUS);

		sendMessageToClient(msg);
	}

	private synchronized void handleCrawlComplete(SearchResult sr)
	{
		
		if (hasOperation() == false)
		{
			ServerLogger.get().error("Invalid crawl complete event");
			return;
		}

		ServerMessage msg = new ServerMessage(token);
		ServerMessage.SearchCrawlParse d = new ServerMessage.SearchCrawlParse(generateRankableWebSearchResult(sr));
		msg.setSearchCrawlParse(d);
		msg.setType(ServerMessage.Type.SEARCH_CRAWL_PARSE);

		sendMessageToClient(msg);
	}

	private synchronized void handleParseComplete(ServerMessage.Type t, AbstractDocument doc)
	{
		if (hasOperation() == false)
		{
			ServerLogger.get().error("Invalid parse complete event for " + t);
			return;
		}

		ServerMessage msg = new ServerMessage(token);
		switch (t)
		{
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

	private synchronized void handleJobComplete(ServerMessage.Type t, DocumentCollection docs)
	{
		if (hasOperation() == false)
		{
			ServerLogger.get().error("Invalid job complete event for " + t);
			return;
		}

		ServerMessage msg = new ServerMessage(token);
		switch (t)
		{
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

	private void sendErrorResponse(String err)
	{
		ServerLogger.get().error(err);

		ServerMessage msg = new ServerMessage(token);
		ServerMessage.Error d = new ServerMessage.Error(err);
		msg.setError(d);
		msg.setType(Type.ERROR);

		sendMessageToClient(msg);
	}

	public synchronized void handleCorpusUpload(List<CustomCorpusFile> corpus)
	{
		ServerLogger.get().info("Received custom corpus from client");

		if (hasOperation())
		{
			sendErrorResponse("Another operation still running");
			return;
		}
		else if (cache.corpusData == null)
		{
			sendErrorResponse("Invalid params for custom corpus");
			return;
		}

		// save the uploaded file for later
		for (CustomCorpusFile itr : corpus)
			cache.corpusData.uploaded.add(itr);
	}

	public synchronized void searchCrawlParse(String query, Language lang, int numResults, List<String> keywords)
	{
		ServerLogger.get().info("Begin search-crawl-parse -> Query: " + query + ", Language: " + lang.toString() + ", Results: " + numResults);

		if (hasOperation())
		{
			sendErrorResponse("Another operation still running");
			return;
		}

		KeywordSearcherInput k;
		if (keywords.isEmpty())
			k = new KeywordSearcherInput(DefaultVocabularyList.get(lang));
		else
			k = new KeywordSearcherInput(keywords);

		SearchCrawlParseOperation op = MasterJobPipeline.get().doSearchCrawlParse(lang, query, numResults, k);
		op.setCrawlCompleteHandler(e -> {
			handleCrawlComplete(e);
		});
		op.setParseCompleteHandler(e -> {
			handleParseComplete(ServerMessage.Type.SEARCH_CRAWL_PARSE, e);
		});
		op.setJobCompleteHandler(e -> {
			handleJobComplete(ServerMessage.Type.SEARCH_CRAWL_PARSE, e);
		});

		beginOperation(new OperationState(op));
	}

	public synchronized void beginCustomCorpusUpload(Language lang, List<String> keywords)
	{
		ServerLogger.get().info("Begin custom corpus uploading -> Language: " + lang.toString());

		if (hasOperation())
		{
			ServerLogger.get().info("Another operation is in progress - Discarding file");
			return;
		}
		else if (cache.corpusData != null)
		{
			sendErrorResponse("Previous custom corpus parameters weren't cleared");
			return;
		}

		// cache params and await the upload servlet
		cache.corpusData = new TemporaryCache.CustomCorpus(lang, keywords);
		ServerLogger.get().info("Cached corpus parse parameters");
	}

	public synchronized void endCustomCorpusUpload()
	{
		ServerLogger.get().info("End custom corpus uploading");
		
		if (hasOperation())
		{
			sendErrorResponse("Another operation still running");
			return;
		}

		// begin parsing operation
		List<AbstractDocumentSource> sources = new ArrayList<>();
		try
		{
			int i = 1;		// assign identifiers to the files for later use
			for (CustomCorpusFile itr : cache.corpusData.uploaded)
			{
				sources.add(new UploadedFileDocumentSource(itr.getStream(),
									itr.getFilename(),
									cache.corpusData.lang,
									i));
				i++;
			}
		} catch (Throwable ex)
		{
			sendErrorResponse("Couldn't read custom corpus files. Exception: " + ex.getMessage());
		}


		CustomParseOperation op = MasterJobPipeline.get().doDocumentParsing(cache.corpusData.lang,
																		sources,
																		cache.corpusData.keywords);
		// register event handlers and start the op
		op.setJobBeginHandler(e -> {
			handleCorpusJobBegin(e);;
		});
		op.setParseCompleteHandler(e -> {
			handleParseComplete(ServerMessage.Type.CUSTOM_CORPUS, e);
		});
		op.setJobCompleteHandler(e -> {
			handleJobComplete(ServerMessage.Type.CUSTOM_CORPUS, e);
		});

		beginOperation(new OperationState(op));
		
		// reset cache
		cache.corpusData = null;
	}

	public synchronized void release()
	{
		if (hasOperation())
		{
			ServerLogger.get().warn("Pipeline operation is still executing at the time of shutdown. Status: "
					+ currentOperation.get().toString());

			endOperation(true);
		}

		if (messagePipeline.isOpen())
			messagePipeline.close();
	}
}
