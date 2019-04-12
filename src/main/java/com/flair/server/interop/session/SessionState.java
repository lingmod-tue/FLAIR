
package com.flair.server.interop.session;

import com.flair.server.crawler.SearchResult;
import com.flair.server.document.*;
import com.flair.server.grammar.DefaultVocabularyList;
import com.flair.server.interop.MessagePipe;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.parser.KeywordSearcherOutput;
import com.flair.server.pipelines.PipelineOp;
import com.flair.server.pipelines.gramparsing.GramParsingPipeline;
import com.flair.server.pipelines.questgen.GeneratedQuestion;
import com.flair.server.pipelines.questgen.QuestionGenerationPipeline;
import com.flair.server.utilities.ServerLogger;
import com.flair.server.utilities.TextSegment;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.*;
import com.flair.shared.interop.ServerMessage.Type;
import edu.stanford.nlp.util.Pair;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

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

	static final class PipelineOpCache {
		Map<String, PipelineOp<?, ?>> id2op;      // string UUID > op
		Pair<PipelineOp<?, ?>, String> currentOp;

		PipelineOpCache() {
			id2op = new HashMap<>();
			currentOp = null;
		}

		void newOp(PipelineOp<?, ?> op) {
			if (hasActiveOp())
				throw new RuntimeException("Previous state not cleared");

			String uuid = UUID.randomUUID().toString();
			if (id2op.containsKey(uuid)) {
				ServerLogger.get().warn("Pipeline op ID '" + uuid + "' has a hash collision!");
			}

			id2op.put(uuid, op);
			currentOp = new Pair<>(op, uuid);

			ServerLogger.get().info("Pipeline operation '" + op.name() + "' has begun");
		}

		void endActiveOp(boolean cancel) {
			if (!hasActiveOp())
				throw new RuntimeException("No operation running");

			if (cancel) {
				currentOp.first.cancel();
				ServerLogger.get().info("Pipeline operation '" + currentOp.first.name() + "' was cancelled");
			} else
				ServerLogger.get().info("Pipeline operation '" + currentOp.first.name() + "' has ended");

			currentOp = null;
		}

		PipelineOp<?, ?> lookupOp(String uuid) {
			return id2op.get(uuid);
		}

		PipelineOp<?, ?> activeOp() {
			if (currentOp != null)
				return currentOp.first;
			else
				return null;
		}

		String activeOpId() {
			if (currentOp != null)
				return currentOp.second;
			else
				return null;
		}

		boolean hasActiveOp() {
			return currentOp != null;
		}
	}

	static final class DocumentLinkingData {
		Map<PipelineOp<?, ?>, Map<Integer, AbstractDocument>> dataStore = new HashMap<>();      // op > (linking id > document)

		void put(PipelineOp<?, ?> op, AbstractDocument source, DocumentDTO dto) {
			Map<Integer, AbstractDocument> match = dataStore.computeIfAbsent(op, k -> new HashMap<>());
			if (match.containsKey(dto.getLinkingId()))
				ServerLogger.get().error("Linking ID collision for document '" + source.getDescription() + "'!");
			else
				match.put(dto.getLinkingId(), source);
		}

		AbstractDocument get(PipelineOp<?, ?> op, DocumentDTO dto) {
			Map<Integer, AbstractDocument> match = dataStore.get(op);
			if (match == null)
				return null;
			else
				return match.get(dto.getLinkingId());
		}
	}

	private final ServerAuthenticationToken token;
	private final AbstractMesageSender messagePipe;
	private final PipelineOpCache pipelineOpCache;
	private final DocumentLinkingData gramParsingLinkingData;   // GramParsingPipelineOp -> DocumentDTO -> Document (parsed by the GramParsingPipeline)
	private final DocumentLinkingData questGenLinkingData;      // GramParsingPipelineOp -> DocumentDTO -> Document (parsed by the QuestionGenerationPipeline)
	private final TemporaryCache temporaryCache;

	SessionState(ServerAuthenticationToken tok) {
		token = tok;
		messagePipe = MessagePipe.get().createSender();
		pipelineOpCache = new PipelineOpCache();
		gramParsingLinkingData = new DocumentLinkingData();
		questGenLinkingData = new DocumentLinkingData();
		temporaryCache = new TemporaryCache();

		messagePipe.open(token);
	}

	private void beginNewOperation(PipelineOp.PipelineOpBuilder opBuilder) {
		// clear the message queue just in case any old messages ended up there
		messagePipe.clearPendingMessages();

		pipelineOpCache.newOp(opBuilder.launch());
	}

	private void endActiveOperation(boolean cancel) {
		pipelineOpCache.endActiveOp(cancel);
	}


	public synchronized void cancelOperation() {
		if (!pipelineOpCache.hasActiveOp()) {
			sendErrorResponse("No active operation to cancel");
			return;
		}

		endActiveOperation(true);
	}

	private RankableDocumentImpl generateRankableDocument(AbstractDocument source, String opId) {
		RankableDocumentImpl out = new RankableDocumentImpl();
		final int snippetMaxLen = 100;

		if (!source.isParsed())
			throw new IllegalStateException("Document not flagged as parsed");

		out.setLanguage(source.getLanguage());
		out.setOperationId(opId);
		if (source.getDocumentSource() instanceof SearchResultDocumentSource) {
			SearchResultDocumentSource searchSource = (SearchResultDocumentSource) source.getDocumentSource();
			SearchResult searchResult = searchSource.getSearchResult();

			out.setTitle(searchResult.getTitle());
			out.setUrl(searchResult.getURL());
			out.setDisplayUrl(searchResult.getDisplayURL());
			out.setSnippet(searchResult.getSnippet());
			out.setRank(searchResult.getRank());
			out.setLinkingId(searchResult.getRank());        // ranks don't overlap, so we can use them as ids
		} else if (source.getDocumentSource() instanceof UploadedFileDocumentSource) {
			UploadedFileDocumentSource localSource = (UploadedFileDocumentSource) source.getDocumentSource();

			out.setTitle(localSource.getName());

			String textSnip = source.getText();
			if (textSnip.length() > snippetMaxLen)
				out.setSnippet(textSnip.substring(0, snippetMaxLen) + "...");
			else
				out.setSnippet(textSnip);

			out.setLinkingId(localSource.getId());        // use the id generated earlier
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

		gramParsingLinkingData.put(pipelineOpCache.lookupOp(opId), source, out);
		return out;
	}

	private RankableWebSearchResultImpl generateRankableWebSearchResult(SearchResult sr, String opId) {
		RankableWebSearchResultImpl out = new RankableWebSearchResultImpl();

		out.setOperationId(opId);
		out.setRank(sr.getRank());
		out.setTitle(sr.getTitle());
		out.setLang(sr.getLanguage());
		out.setUrl(sr.getURL());
		out.setDisplayUrl(sr.getDisplayURL());
		out.setSnippet(sr.getSnippet());
		out.setText(sr.getPageText());
		out.setLinkingId(sr.getRank());

		return out;
	}

	private ArrayList<UploadedDocument> generateUploadedDocs(Iterable<AbstractDocumentSource> source, String opId) {
		ArrayList<UploadedDocument> out = new ArrayList<>();

		for (AbstractDocumentSource itr : source) {
			UploadedFileDocumentSource sdr = (UploadedFileDocumentSource) itr;
			UploadedDocumentImpl u = new UploadedDocumentImpl();
			String snippet = itr.getSourceText();
			if (snippet.length() > 100)
				snippet = snippet.substring(0, 100);

			u.setOperationId(opId);
			u.setLanguage(itr.getLanguage());
			u.setTitle(sdr.getName());
			u.setSnippet(snippet);
			u.setText(itr.getSourceText());
			u.setLinkingId(sdr.getId());

			out.add(u);
		}

		return out;
	}

	private void sendMessageToClient(ServerMessage msg) {
		ServerLogger.get().info("Sent message to client: " + msg.toString());
		messagePipe.send(msg);
	}

	private synchronized void handleCorpusJobBegin(Iterable<AbstractDocumentSource> source) {
		if (!pipelineOpCache.hasActiveOp()) {
			ServerLogger.get().error("Invalid corpus job begin event");
			return;
		}

		ServerMessage msg = new ServerMessage(token);
		ServerMessage.CustomCorpus d = new ServerMessage.CustomCorpus(generateUploadedDocs(source, pipelineOpCache.activeOpId()));
		msg.setCustomCorpus(d);
		msg.setType(ServerMessage.Type.CUSTOM_CORPUS);

		sendMessageToClient(msg);
	}

	private synchronized void handleCrawlComplete(SearchResult sr) {
		if (!pipelineOpCache.hasActiveOp()) {
			ServerLogger.get().error("Invalid crawl complete event");
			return;
		}

		ServerMessage msg = new ServerMessage(token);
		ServerMessage.SearchCrawlParse d = new ServerMessage.SearchCrawlParse(generateRankableWebSearchResult(sr, pipelineOpCache.activeOpId()));
		msg.setSearchCrawlParse(d);
		msg.setType(ServerMessage.Type.SEARCH_CRAWL_PARSE);

		sendMessageToClient(msg);
	}

	private synchronized void handleParseComplete(ServerMessage.Type t, AbstractDocument doc) {
		if (!pipelineOpCache.hasActiveOp()) {
			ServerLogger.get().error("Invalid parse complete event for " + t);
			return;
		}

		ServerMessage msg = new ServerMessage(token);
		switch (t) {
		case CUSTOM_CORPUS:
			msg.setCustomCorpus(new ServerMessage.CustomCorpus(generateRankableDocument(doc, pipelineOpCache.activeOpId())));
			break;
		case SEARCH_CRAWL_PARSE:
			msg.setSearchCrawlParse(new ServerMessage.SearchCrawlParse(generateRankableDocument(doc, pipelineOpCache.activeOpId())));
			break;
		}

		msg.setType(t);
		sendMessageToClient(msg);
	}

	private synchronized void handleParseJobComplete(ServerMessage.Type t, DocumentCollection docs) {
		if (!pipelineOpCache.hasActiveOp()) {
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
		endActiveOperation(false);
	}

	private synchronized void handleGenerateQuestionsParseComplete(AbstractDocument parsedDoc, DocumentDTO linkingDoc, boolean usingCachedDoc) {
		if (!pipelineOpCache.hasActiveOp()) {
			ServerLogger.get().error("Invalid generate questions parse complete event!");
			return;
		}

		if (usingCachedDoc)
			return;

		// add to cache so that future QG requests for this document (in this session)
		// won't have to parse it anew
		questGenLinkingData.put(pipelineOpCache.lookupOp(linkingDoc.getOperationId()), parsedDoc, linkingDoc);
	}

	private synchronized void handleGenerateQuestionsJobComplete(Collection<GeneratedQuestion> questions) {
		if (!pipelineOpCache.hasActiveOp()) {
			ServerLogger.get().error("Invalid generate questions job complete event!");
			return;
		}

		ServerMessage msg = new ServerMessage(token);
		msg.setType(Type.GENERATE_QUESTIONS);
		msg.setGenerateQuestions(new ServerMessage.GenerateQuestions(ServerMessage.GenerateQuestions.Type.JOB_COMPLETE));
		msg.getGenerateQuestions().setGeneratedQuestions(new ArrayList<>(questions.stream().map(q -> new QuestionDTO(q.question, q.answer, q.distractors)).collect(Collectors.toList())));

		sendMessageToClient(msg);

		// reset operation state
		endActiveOperation(false);
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

		if (pipelineOpCache.hasActiveOp()) {
			sendErrorResponse("Another operation still running");
			return;
		} else if (temporaryCache.corpusData == null) {
			sendErrorResponse("Invalid params for custom corpus");
			return;
		}

		// save the uploaded file for later
		temporaryCache.corpusData.uploaded.addAll(corpus);
	}

	public synchronized void searchCrawlParse(String query, Language lang, int numResults, List<String> keywords) {
		ServerLogger.get().info("Begin search-crawl-parse -> Query: " + query + ", Language: " + lang.toString() + ", Results: " + numResults);

		if (pipelineOpCache.hasActiveOp()) {
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
				.onComplete(e -> handleParseJobComplete(ServerMessage.Type.SEARCH_CRAWL_PARSE, e));

		beginNewOperation(builder);
	}

	public synchronized void beginCustomCorpusUpload(Language lang, List<String> keywords) {
		ServerLogger.get().info("Begin custom corpus uploading -> Language: " + lang.toString());

		if (pipelineOpCache.hasActiveOp()) {
			ServerLogger.get().info("Another operation is in progress - Discarding file");
			return;
		}

		// cache params and await the upload servlet
		// discard the previous cache, if any
		temporaryCache.corpusData = new TemporaryCache.CustomCorpus(lang, keywords);
		ServerLogger.get().info("Cached corpus parse parameters");
	}

	public synchronized void endCustomCorpusUpload(boolean success) {
		ServerLogger.get().info("End custom corpus uploading | Success: " + success);

		if (pipelineOpCache.hasActiveOp()) {
			sendErrorResponse("Another operation still running");
			return;
		}

		if (!success) {
			// don't begin the parse op
			temporaryCache.corpusData = null;
			return;
		}

		// begin parsing operation
		List<AbstractDocumentSource> sources = new ArrayList<>();
		try {
			int i = 1;        // assign identifiers to the files for later use
			for (CustomCorpusFile itr : temporaryCache.corpusData.uploaded) {
				sources.add(new UploadedFileDocumentSource(itr.getStream(),
						itr.getFilename(),
						temporaryCache.corpusData.lang,
						i));
				i++;
			}
		} catch (Throwable ex) {
			sendErrorResponse("Couldn't read custom corpus files. Exception: " + ex.getMessage());
		}


		GramParsingPipeline.ParseOpBuilder builder = GramParsingPipeline.get().documentParse();
		builder.lang(temporaryCache.corpusData.lang)
				.docSource(sources)
				.keywords(temporaryCache.corpusData.keywords)
				.onParse(e -> handleParseComplete(ServerMessage.Type.CUSTOM_CORPUS, e))
				.onComplete(e -> handleParseJobComplete(ServerMessage.Type.CUSTOM_CORPUS, e));

		beginNewOperation(builder);

		// reset cache
		temporaryCache.corpusData = null;
		handleCorpusJobBegin(sources);
	}

	public synchronized void generateQuestions(RankableDocument doc, int numQuestions, boolean randomizeSelection) {
		PipelineOp<?, ?> sourceOp = pipelineOpCache.lookupOp(doc.getOperationId());
		if (sourceOp == null) {
			sendErrorResponse("Couldn't find pipeline op with id " + doc.getOperationId());
			return;
		}

		boolean usingCachedDoc = false;
		AbstractDocument sourceDoc = questGenLinkingData.get(sourceOp, doc);
		if (sourceDoc == null) {
			sourceDoc = gramParsingLinkingData.get(sourceOp, doc);
			if (sourceDoc == null) {
				sendErrorResponse("Couldn't find source doc with linking id " + doc.getLinkingId() + " and pipeline op id " + doc.getOperationId());
				return;
			}
		} else
			usingCachedDoc = true;

		ServerLogger.get().info("Begin question generation -> Doc" + (usingCachedDoc ? " (cached)" : "") + ": " + sourceDoc.getDescription() + ", Questions: " + numQuestions);

		if (pipelineOpCache.hasActiveOp()) {
			sendErrorResponse("Another operation still running");
			return;
		}

		boolean captureThrowaway = usingCachedDoc;
		QuestionGenerationPipeline.QuestionGenerationOpBuilder builder = QuestionGenerationPipeline.get().generateQuestions()
				.sourceDoc(sourceDoc)
				.sourceDocParsed(usingCachedDoc)
				.numQuestions(numQuestions)
				.randomizeSelection(randomizeSelection)
				.onParseComplete(e -> handleGenerateQuestionsParseComplete(e, doc, captureThrowaway))
				.onComplete(e -> handleGenerateQuestionsJobComplete(e.generatedQuestions));

		beginNewOperation(builder);
	}

	public synchronized void release() {
		if (pipelineOpCache.hasActiveOp()) {
			ServerLogger.get().warn("Pipeline operation is still executing at the time of shutdown. Status: "
					+ pipelineOpCache.activeOp().toString());

			endActiveOperation(true);
		}

		if (messagePipe.isOpen())
			messagePipe.close();
	}
}
