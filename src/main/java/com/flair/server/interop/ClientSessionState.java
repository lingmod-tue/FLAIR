package com.flair.server.interop;

import com.flair.server.crawler.SearchResult;
import com.flair.server.document.AbstractDocument;
import com.flair.server.document.AbstractDocumentSource;
import com.flair.server.document.DocumentCollection;
import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.ZipManager;
import com.flair.server.grammar.DefaultVocabularyList;
import com.flair.server.interop.messaging.ServerMessageChannel;
import com.flair.server.interop.messaging.ServerMessagingSwitchboard;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.pipelines.common.PipelineOp;
import com.flair.server.pipelines.exgen.ExerciseGenerationPipeline;
import com.flair.server.pipelines.gramparsing.GramParsingPipeline;
import com.flair.server.pipelines.questgen.GeneratedQuestion;
import com.flair.server.pipelines.questgen.QuestionGenerationPipeline;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exceptions.ServerRuntimeException;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.interop.ClientIdToken;
import com.flair.shared.interop.dtos.DocumentDTO;
import com.flair.shared.interop.dtos.QuestionDTO;
import com.flair.shared.interop.dtos.RankableDocument;
import com.flair.shared.interop.dtos.RankableDocumentImpl;
import com.flair.shared.interop.messaging.client.*;
import com.flair.shared.interop.messaging.server.SmCustomCorpusEvent;
import com.flair.shared.interop.messaging.server.SmExGenEvent;
import com.flair.shared.interop.messaging.server.SmQuestionGenEvent;
import com.flair.shared.interop.messaging.server.SmWebSearchParseEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

class ClientSessionState {
	private final ClientIdToken clientId;
	private final ServerMessageChannel messageChannel;
	private final ClientPipelineOpCache pipelineOpCache;
	// GramParsingPipelineOp -> DocumentDTO -> Document (parsed by the GramParsingPipeline)
	private final ClientPipelineOp2DocumentMap gramParsingLinkingData;
	// GramParsingPipelineOp -> DocumentDTO -> Document (parsed by the QuestionGenerationPipeline)
	private final ClientPipelineOp2DocumentMap questGenLinkingData;
	private final TemporaryClientData temporaryClientData;
    private final ArrayList<byte[]> generatedPackages = new ArrayList<>();

	ClientSessionState(ClientIdToken tok) {
		clientId = tok;
		messageChannel = ServerMessagingSwitchboard.get().openChannel(clientId);
		pipelineOpCache = new ClientPipelineOpCache();
		gramParsingLinkingData = new ClientPipelineOp2DocumentMap();
		questGenLinkingData = new ClientPipelineOp2DocumentMap();
		temporaryClientData = new TemporaryClientData();

		initClientMessageReceiveHandlers();
	}

	private void initClientMessageReceiveHandlers() {
		messageChannel.addReceiveHandler(CmWebSearchParseStart.class, this::onCmWebSearchParseStart);
		messageChannel.addReceiveHandler(CmCustomCorpusParseStart.class, this::onCmCustomCorpusParseStart);
		messageChannel.addReceiveHandler(CmQuestionGenEagerParse.class, this::onCmQuestionGenEagerParse);
		messageChannel.addReceiveHandler(CmQuestionGenStart.class, this::onCmQuestionGenStart);
		messageChannel.addReceiveHandler(CmExGenStart.class, this::onCmExGenStart);
		messageChannel.addReceiveHandler(CmActiveOperationCancel.class, this::onCmActiveOperationCancel);
	}

	private String beginNewOperation(PipelineOp.PipelineOpBuilder opBuilder) {
		// clear the message queue just in case any old messages ended up there
		messageChannel.clearPendingMessages();

		PipelineOp<?, ?> newOp = opBuilder.build();
		String opId = pipelineOpCache.newOp(newOp);
		newOp.launch();
		return opId;
	}

	private void endActiveOperation(boolean cancel) {
		pipelineOpCache.endActiveOp(cancel);
	}

	private synchronized void onCmWebSearchParseStart(CmWebSearchParseStart msg) {
		ServerLogger.get().info("Begin search-crawl-parse -> Query: '" + msg.getQuery() +
				"', Language: " + msg.getLanguage() + ", Results: " + msg.getNumResults());

		if (pipelineOpCache.hasActiveOp())
			throw new ServerRuntimeException("Another operation still running");

		KeywordSearcherInput keywordInput;
		if (msg.getKeywords().isEmpty())
			keywordInput = new KeywordSearcherInput(DefaultVocabularyList.get(msg.getLanguage()));
		else
			keywordInput = new KeywordSearcherInput(msg.getKeywords());

		GramParsingPipeline.SearchCrawlParseOpBuilder builder = GramParsingPipeline.get().searchCrawlParse();
		builder.lang(msg.getLanguage())
				.query(msg.getQuery())
				.results(msg.getNumResults())
				.keywords(keywordInput)
				.onCrawl(this::onSearchCrawlParseOpCrawlComplete)
				.onParse(this::onSearchCrawlParseOpParseComplete)
				.onComplete(this::onSearchCrawlParseOpJobComplete);

		beginNewOperation(builder);
	}

	private synchronized void onCmCustomCorpusParseStart(CmCustomCorpusParseStart msg) {
		List<CustomCorpusFile> uploadCache = temporaryClientData.customCorpusData.uploaded;
		int numUploadedFiles = msg.getNumUploadedFiles();

		if (numUploadedFiles > temporaryClientData.customCorpusData.uploaded.size()) {
			ServerLogger.get().warn("Upload cache for client " + clientId + " is smaller than expected").indent()
					.warn("Expected " + numUploadedFiles + ", found " + temporaryClientData.customCorpusData.uploaded.size()).exdent();
		}

		int start = uploadCache.size() - numUploadedFiles, end = uploadCache.size();
		if (start < 0)
			start = 0;
		List<CustomCorpusFile> uploadedFiles = new ArrayList<>(temporaryClientData.customCorpusData.uploaded.subList(start, end));
		if (uploadedFiles.isEmpty())
			throw new ServerRuntimeException("No files uploaded for corpus-upload-parse operation for client " + clientId);

		ServerLogger.get().info("Begin corpus-upload-parse -> Language: " + msg.getLanguage() + ", Uploaded Files: " + uploadedFiles.size());

		if (pipelineOpCache.hasActiveOp()) {
			ServerLogger.get().info("Another operation is in progress - Discarding file");
			return;
		}

		KeywordSearcherInput keywordInput;
		if (msg.getKeywords().isEmpty())
			keywordInput = new KeywordSearcherInput(DefaultVocabularyList.get(msg.getLanguage()));
		else
			keywordInput = new KeywordSearcherInput(msg.getKeywords());

		// reset the cache as we have the files we need for the current op
		uploadCache.clear();

		List<AbstractDocumentSource> sources = new ArrayList<>();
		try {
			int i = 1;        // assign identifiers to the files for later use
			for (CustomCorpusFile itr : uploadedFiles) {
				sources.add(new UploadedFileDocumentSource(itr.getStream(),
						itr.getFilename(),
						msg.getLanguage(),
						i));
				i++;
			}
		} catch (Throwable ex) {
			ServerLogger.get().error(ex, "Couldn't read custom corpus files");
		}

		GramParsingPipeline.ParseOpBuilder builder = GramParsingPipeline.get().documentParse();
		builder.lang(msg.getLanguage())
				.docSource(sources)
				.keywords(keywordInput)
				.onBegin(this::onParseOpJobBegin)
				.onParse(this::onParseOpParseComplete)
				.onComplete(this::onParseOpJobComplete);

		beginNewOperation(builder);
	}

	private synchronized void onCmQuestionGenEagerParse(CmQuestionGenEagerParse msg) {
		RankableDocument doc = msg.getSourceDoc();
		PipelineOp<?, ?> sourceOp = pipelineOpCache.lookupOp(doc.getOperationId());
		if (sourceOp == null)
			throw new ServerRuntimeException("Couldn't find pipeline op with id " + doc.getOperationId());

		boolean usingCachedDoc = false;
		AbstractDocument sourceDoc = questGenLinkingData.get(sourceOp, doc);
		if (sourceDoc == null) {
			sourceDoc = gramParsingLinkingData.get(sourceOp, doc);
			if (sourceDoc == null) {
				throw new ServerRuntimeException("Couldn't find source doc with linking id " + doc.getLinkingId()
						+ " and pipeline op id " + doc.getOperationId());
			}
		} else
			usingCachedDoc = true;

		if (usingCachedDoc) {
			ServerLogger.get().trace("Found cached document for question gen; skipping eager parsing...");
			return;
		}

		ServerLogger.get().info("Begin eager parsing for question generation -> Doc: " + sourceDoc.getDescription());
		if (pipelineOpCache.hasActiveOp())
			throw new ServerRuntimeException("Another operation still running");

		QuestionGenerationPipeline.QuestionGenerationOpBuilder builder = QuestionGenerationPipeline.get().generateQuestions()
				.sourceDoc(sourceDoc)
				.sourceDocParsed(false)
				.numQuestions(1)
				.randomizeSelection(false)
				.onParseComplete(e -> onQuestionGenerationOpEagerParseComplete(e, doc))
				.onComplete(e -> onQuestionGenerationOpEagerJobComplete(e.generatedQuestions));

		// needs to be set first to prevent a race condition
		temporaryClientData.questGenData = new TemporaryClientData.QuestionGen(sourceDoc, doc);
		temporaryClientData.questGenData.eagerParsingOpId = beginNewOperation(builder);
	}

	private synchronized void onCmQuestionGenStart(CmQuestionGenStart msg) {
		RankableDocument doc = msg.getSourceDoc();
		PipelineOp<?, ?> sourceOp = pipelineOpCache.lookupOp(doc.getOperationId());
		if (sourceOp == null)
			throw new ServerRuntimeException("Couldn't find pipeline op with id " + doc.getOperationId());

		boolean usingCachedDoc = false, eagerParsingInProgress = false;
		AbstractDocument sourceDoc = questGenLinkingData.get(sourceOp, doc);
		if (sourceDoc == null) {
			if (temporaryClientData.questGenData == null || !temporaryClientData.questGenData.eagerLinkingDoc.equals(doc)) {
				ServerLogger.get().warn(doc.toString() + " was not eagerly parsed for question generation!");
				sourceDoc = gramParsingLinkingData.get(sourceOp, doc);
			} else
				eagerParsingInProgress = true;
		} else
			usingCachedDoc = true;

		ServerLogger.get().info("Begin question generation -> Doc" + (usingCachedDoc ? " (cached)" : "") + ": "
				+ (sourceDoc != null ? sourceDoc.getDescription() : temporaryClientData.questGenData.eagerSourceDoc.getDescription())
				+ ", Questions: " + msg.getNumQuestions());

		if (pipelineOpCache.hasActiveOp() && (!eagerParsingInProgress || !pipelineOpCache.activeOpId().equals(temporaryClientData.questGenData.eagerParsingOpId)))
			throw new ServerRuntimeException("Another operation still running");

		QuestionGenerationPipeline.QuestionGenerationOpBuilder builder = QuestionGenerationPipeline.get().generateQuestions()
				.sourceDoc(sourceDoc)
				.sourceDocParsed(usingCachedDoc)
				.numQuestions(msg.getNumQuestions())
				.randomizeSelection(msg.getRandomizeSelection())
				.onParseComplete(e -> onQuestionGenerationOpParseComplete(e, doc))
				.onComplete(e -> onQuestionGenerationOpJobComplete(e.generatedQuestions));

		if (eagerParsingInProgress) {
			if (temporaryClientData.questGenData.queuedOperation != null)
				throw new ServerRuntimeException("Multiple queued question generation operations!");

			temporaryClientData.questGenData.queuedOperation = builder;
			ServerLogger.get().trace("Waiting for question gen eager parse operation to complete...");
		} else
			beginNewOperation(builder);
	}
	
	private synchronized void onCmExGenStart(CmExGenStart msg) {
		ArrayList<ExerciseSettings> settings = msg.getSettings();
		
		ServerLogger.get().info("Begin exercise generation");

		if (pipelineOpCache.hasActiveOp())
			throw new ServerRuntimeException("Another operation is still running");

		ExerciseGenerationPipeline.ExerciseGenerationOpBuilder builder = ExerciseGenerationPipeline.get().generateExercises()
				.settings(settings)
				.onExGenComplete(e -> onExerciseGenerationOpGenerationComplete(e))
				.onComplete(e -> onExerciseGenerationOpJobComplete());

		beginNewOperation(builder);
	}

	private synchronized void onCmActiveOperationCancel(CmActiveOperationCancel msg) {
		if (!pipelineOpCache.hasActiveOp()) {
			if (msg.getActiveOperationExpected())
				ServerLogger.get().warn("No active operation to cancel on client " + clientId);
			return;
		}

		endActiveOperation(true);
		temporaryClientData.questGenData = null;
	}


	private synchronized void onParseOpJobBegin(Iterable<AbstractDocumentSource> source) {
		if (!pipelineOpCache.hasActiveOp())
			throw new ServerRuntimeException("Invalid corpus job begin event");

		SmCustomCorpusEvent msg = new SmCustomCorpusEvent();
		msg.setEvent(SmCustomCorpusEvent.EventType.UPLOAD_COMPLETE);
		msg.setUploadResult(DtoGenerator.uploadedDocs(source, pipelineOpCache.activeOpId()));
		messageChannel.send(msg);
	}

	private synchronized void onParseOpParseComplete(AbstractDocument doc) {
		if (!pipelineOpCache.hasActiveOp())
			throw new ServerRuntimeException("Invalid corpus parse complete event");

		RankableDocumentImpl dto = DtoGenerator.rankableDocument(doc, pipelineOpCache.activeOpId());
		gramParsingLinkingData.put(pipelineOpCache.activeOp(), doc, dto);

		SmCustomCorpusEvent msg = new SmCustomCorpusEvent();
		msg.setEvent(SmCustomCorpusEvent.EventType.PARSE_COMPLETE);
		msg.setParseResult(dto);
		messageChannel.send(msg);
	}

	private synchronized void onParseOpJobComplete(DocumentCollection docs) {
		if (!pipelineOpCache.hasActiveOp())
			throw new ServerRuntimeException("Invalid corpus job complete event");

		SmCustomCorpusEvent msg = new SmCustomCorpusEvent();
		msg.setEvent(SmCustomCorpusEvent.EventType.JOB_COMPLETE);
		messageChannel.send(msg);

		endActiveOperation(false);
	}

	private synchronized void onSearchCrawlParseOpCrawlComplete(SearchResult sr) {
		if (!pipelineOpCache.hasActiveOp())
			throw new ServerRuntimeException("Invalid crawl complete event");

		SmWebSearchParseEvent msg = new SmWebSearchParseEvent();
		msg.setEvent(SmWebSearchParseEvent.EventType.CRAWL_COMPLETE);
		msg.setCrawlResult(DtoGenerator.rankableWebSearchResult(sr, pipelineOpCache.activeOpId()));
		messageChannel.send(msg);
	}

	private synchronized void onSearchCrawlParseOpParseComplete(AbstractDocument doc) {
		if (!pipelineOpCache.hasActiveOp())
			throw new ServerRuntimeException("Invalid search-crawl-parse parse complete event");

		RankableDocumentImpl dto = DtoGenerator.rankableDocument(doc, pipelineOpCache.activeOpId());
		gramParsingLinkingData.put(pipelineOpCache.activeOp(), doc, dto);

		SmWebSearchParseEvent msg = new SmWebSearchParseEvent();
		msg.setEvent(SmWebSearchParseEvent.EventType.PARSE_COMPLETE);
		msg.setParseResult(dto);
		messageChannel.send(msg);
	}

	private synchronized void onSearchCrawlParseOpJobComplete(DocumentCollection docs) {
		if (!pipelineOpCache.hasActiveOp())
			throw new ServerRuntimeException("Invalid search-crawl-parse job complete event");

		SmWebSearchParseEvent msg = new SmWebSearchParseEvent();
		msg.setEvent(SmWebSearchParseEvent.EventType.JOB_COMPLETE);
		messageChannel.send(msg);

		endActiveOperation(false);
	}

	private synchronized void onQuestionGenerationOpEagerParseComplete(AbstractDocument parsedDoc, DocumentDTO linkingDoc) {
		if (!pipelineOpCache.hasActiveOp())
			throw new ServerRuntimeException("Invalid question gen eager parse complete event");

		// add to cache so that future QG requests for this document (in this session) won't have to parse it anew
		questGenLinkingData.put(pipelineOpCache.lookupOp(linkingDoc.getOperationId()), parsedDoc, linkingDoc);
		temporaryClientData.questGenData.eagerParsedDoc = parsedDoc;
	}

	private synchronized void onQuestionGenerationOpEagerJobComplete(Collection<GeneratedQuestion> unused) {
		if (!pipelineOpCache.hasActiveOp())
			throw new ServerRuntimeException("Invalid question gen eager job complete event!");

		endActiveOperation(false);

		QuestionGenerationPipeline.QuestionGenerationOpBuilder queuedOp = temporaryClientData.questGenData.queuedOperation;
		if (queuedOp != null) {
			queuedOp.sourceDoc(temporaryClientData.questGenData.eagerParsedDoc).sourceDocParsed(true);
			beginNewOperation(temporaryClientData.questGenData.queuedOperation);
		}

		temporaryClientData.questGenData = null;
	}

	private synchronized void onQuestionGenerationOpParseComplete(AbstractDocument parsedDoc, DocumentDTO linkingDoc) {
		if (!pipelineOpCache.hasActiveOp())
			throw new ServerRuntimeException("Invalid question gen parse complete event");

		PipelineOp<?, ?> sourceOp = pipelineOpCache.lookupOp(linkingDoc.getOperationId());
		if (!questGenLinkingData.contains(sourceOp, linkingDoc))
			questGenLinkingData.put(sourceOp, parsedDoc, linkingDoc);
	}

	private synchronized void onQuestionGenerationOpJobComplete(Collection<GeneratedQuestion> questions) {
		if (!pipelineOpCache.hasActiveOp())
			throw new ServerRuntimeException("Invalid question gen parse job complete event!");

		SmQuestionGenEvent msg = new SmQuestionGenEvent();
		msg.setEvent(SmQuestionGenEvent.EventType.JOB_COMPLETE);
		msg.setGenerationResult(questions.stream().map(q -> new QuestionDTO(q.question, q.answer, q.distractors)).collect(Collectors.toCollection(ArrayList::new)));
		messageChannel.send(msg);

		// reset operation state
		endActiveOperation(false);
	}
	
	
	private synchronized void onExerciseGenerationOpGenerationComplete(byte[] file) {
		if (!pipelineOpCache.hasActiveOp())
			throw new ServerRuntimeException("Invalid exercise generation generation complete event!");

		if(file != null) {
            generatedPackages.add(file);
        }
	}
	
	private synchronized void onExerciseGenerationOpJobComplete() {
		if (!pipelineOpCache.hasActiveOp())
			throw new ServerRuntimeException("Invalid exercise generation job complete event!");

		ServerLogger.get().info("Generated " + generatedPackages.size() + " exercises");
		
		byte[] outputFile = new byte[] {};
        String name = "";
        if(generatedPackages.size() > 1) {
            outputFile = ZipManager.zipH5PPackages(generatedPackages);
            name = "exercises.zip";
        } else if(generatedPackages.size() > 0) {
            outputFile = generatedPackages.get(0);
            name = "task1.h5p";
        }
        
        
		SmExGenEvent msg = new SmExGenEvent();
		msg.setEvent(SmExGenEvent.EventType.JOB_COMPLETE);
		msg.setFile(outputFile);
		msg.setFileName(name);
		messageChannel.send(msg);

		generatedPackages.clear();
		// reset operation state
		endActiveOperation(false);
	}

	synchronized void handleCorpusUpload(List<CustomCorpusFile> corpus) {
		ServerLogger.get().info("Received custom corpus from client " + clientId);

		// save the uploaded file for later
		temporaryClientData.customCorpusData.uploaded.addAll(corpus);
	}

	synchronized void release() {
		if (pipelineOpCache.hasActiveOp()) {
			ServerLogger.get().warn("Pipeline operation is still executing at the time of shutdown. Status: "
					+ pipelineOpCache.activeOp());

			endActiveOperation(true);
		}

		ServerMessagingSwitchboard.get().closeChannel(messageChannel);
	}
}
