/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.server.interop.session;

import java.util.ArrayList;
import java.util.List;

import com.flair.server.crawler.SearchResult;
import com.flair.server.grammar.DefaultVocabularyList;
import com.flair.server.interop.MessagePipeline;
import com.flair.server.interop.RankableDocumentImpl;
import com.flair.server.interop.RankableWebSearchResultImpl;
import com.flair.server.interop.ServerAuthenticationToken;
import com.flair.server.parser.AbstractDocument;
import com.flair.server.parser.AbstractDocumentSource;
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
import com.flair.server.utilities.FLAIRLogger;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.AbstractMesageSender;
import com.flair.shared.interop.ServerMessage;
import com.flair.shared.interop.ServerMessage.Type;

/**
 * Stores the state of a client
 * 
 * @author shadeMe
 */
class SessionState
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
			public final Language					lang;
			public final KeywordSearcherInput		keywords;
			
			public CustomCorpus(Language l, List<String> k)
			{
				lang = l;
				
				if (k.isEmpty())
					keywords = new KeywordSearcherInput(DefaultVocabularyList.get(lang));
				else
					keywords = new KeywordSearcherInput(k);
			}
		}
		
		CustomCorpus			corpusData;
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
		} 
		else if (source.getDocumentSource() instanceof StreamDocumentSource)
		{
			StreamDocumentSource localSource = (StreamDocumentSource) source.getDocumentSource();

			out.setTitle(localSource.getName());

			String textSnip = source.getText();
			if (textSnip.length() > 35)
				out.setSnippet(textSnip.substring(0, 35) + "...");
			else
				out.setSnippet(textSnip);

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
			out.setKeywordRelFreq(keywordData.getTotalHitCount() / (double) source.getNumWords());
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
		
		return out;
	}
	
	private synchronized void handleCrawlComplete(SearchResult sr)
	{
		if (hasOperation() == false)
		{
			FLAIRLogger.get().error("Invalid crawl complete event");
			return;
		}
		
		ServerMessage msg = new ServerMessage(token);
		ServerMessage.SearchCrawlParse d = new ServerMessage.SearchCrawlParse(generateRankableWebSearchResult(sr));
		msg.setSearchCrawlParse(d);
		msg.setType(ServerMessage.Type.SEARCH_CRAWL_PARSE);
		
		messagePipeline.send(msg);
	}
	
	private synchronized void handleParseComplete(ServerMessage.Type t, AbstractDocument doc)
	{
		if (hasOperation() == false)
		{
			FLAIRLogger.get().error("Invalid parse complete event for " + t);
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
		messagePipeline.send(msg);
	}
	
	private synchronized void handleJobComplete(ServerMessage.Type t)
	{
		if (hasOperation() == false)
		{
			FLAIRLogger.get().error("Invalid job complete event for " + t);
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
		messagePipeline.send(msg);
		
		// reset operation state
		currentOperation = null;
	}
	
	private void sendErrorResponse(String err)
	{
		FLAIRLogger.get().error(err);
		
		ServerMessage msg = new ServerMessage(token);
		ServerMessage.Error d = new ServerMessage.Error(err);
		msg.setError(d);
		msg.setType(Type.ERROR);
		
		messagePipeline.send(msg);
	}

	public synchronized void handleCorpusUpload(List<CustomCorpusFile> corpus)
	{
		FLAIRLogger.get().info("Received custom corpus from client");

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
		
		List<AbstractDocumentSource> sources = new ArrayList<>();
		try
		{
			for (CustomCorpusFile itr : corpus)
				sources.add(new StreamDocumentSource(itr.getStream(), itr.getFilename(), cache.corpusData.lang));
		} catch (Throwable ex)
		{
			sendErrorResponse("Couldn't read custom corpus files. Exception: " + ex.getMessage());
		}
		

		CustomParseOperation op = MasterJobPipeline.get().doDocumentParsing(cache.corpusData.lang,
																		sources,
																		cache.corpusData.keywords);
		currentOperation = new OperationState(op);
		
		// register event handlers and start the op
		op.setParseCompleteHandler(e -> {
			handleParseComplete(ServerMessage.Type.CUSTOM_CORPUS, e);
		});
		op.setJobCompleteHandler(e -> {
			handleJobComplete(ServerMessage.Type.CUSTOM_CORPUS);
		});
		
		op.begin();
	}
	
	public synchronized void searchCrawlParse(String query, Language lang, int numResults, List<String> keywords) 
	{
		FLAIRLogger.get().info("Begin Search-Crawl-Parse -> Query: " + query + ", Language: " + lang.toString() + ", Results: " + numResults);
		
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
		currentOperation = new OperationState(op);
		
		op.setCrawlCompleteHandler(e -> {
			handleCrawlComplete(e);
		});
		op.setParseCompleteHandler(e -> {
			handleParseComplete(ServerMessage.Type.SEARCH_CRAWL_PARSE, e);
		});
		op.setJobCompleteHandler(e -> {
			handleJobComplete(ServerMessage.Type.SEARCH_CRAWL_PARSE);
		});
		
		op.begin();
	}

	public synchronized void parseCustomCorpus(Language lang, List<String> keywords)
	{
		FLAIRLogger.get().info("Begin Custom Corpus Parsing -> Language: " + lang.toString());
		
		if (hasOperation())
		{
			sendErrorResponse("Another operation still running");
			return;
		}
		else if (cache.corpusData != null)
		{
			sendErrorResponse("Previous custom corpus parameters weren't cleared");
			return;
		}
		
		// cache params and await the upload servlet
		cache.corpusData = new TemporaryCache.CustomCorpus(lang, keywords);
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
		
		// cancel and reset
		currentOperation.get().cancel();
		currentOperation = null;
	}

	public synchronized void release() 
	{
		if (hasOperation())
		{
			FLAIRLogger.get().warn("Pipeline operation is still executing at the time of shutdown. Status: "
					+ currentOperation.get().toString());
			
			currentOperation.get().cancel();
		}
		
		messagePipeline.close();
	}	
}
