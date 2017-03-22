package com.flair.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flair.client.ClientEndPoint;
import com.flair.client.interop.FuncCallback;
import com.flair.client.localization.LocalizationEngine;
import com.flair.client.localization.locale.WebRankerCoreLocale;
import com.flair.client.model.interfaces.AbstractDocumentAnnotator;
import com.flair.client.model.interfaces.AbstractDocumentRanker;
import com.flair.client.model.interfaces.AbstractWebRankerCore;
import com.flair.client.model.interfaces.DocumentRankerOutput;
import com.flair.client.presentation.interfaces.AbstractDocumentPreviewPane;
import com.flair.client.presentation.interfaces.AbstractDocumentResultsPane;
import com.flair.client.presentation.interfaces.AbstractRankerSettingsPane;
import com.flair.client.presentation.interfaces.AbstractResultItem;
import com.flair.client.presentation.interfaces.AbstractWebRankerPresenter;
import com.flair.client.presentation.interfaces.CompletedResultItem;
import com.flair.client.presentation.interfaces.InProgressResultItem;
import com.flair.client.presentation.interfaces.NotificationService;
import com.flair.client.presentation.interfaces.UserPromptService;
import com.flair.client.presentation.interfaces.AbstractResultItem.Type;
import com.flair.client.utilities.ClientLogger;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.AbstractMessageReceiver;
import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.BasicDocumentTransferObject;
import com.flair.shared.interop.RankableDocument;
import com.flair.shared.interop.RankableWebSearchResult;
import com.flair.shared.interop.ServerMessage;
import com.flair.shared.interop.UploadedDocument;
import com.flair.shared.interop.services.WebRankerService;
import com.flair.shared.interop.services.WebRankerServiceAsync;
import com.google.gwt.core.shared.GWT;

/*
 * Web ranker module
 */
public class WebRankerCore implements AbstractWebRankerCore
{
	enum OperationType
	{
		NONE,
		WEB_SEARCH,
		CUSTOM_CORPUS
	}
	
	private final class OperationParams
	{
		public final OperationType		type;
		public final Language			lang;
		public final String				query;
		public final int				numResults;
		public final List<String>		keywords;
		
		public OperationParams(Language l, String q, int num, List<String> k)
		{
			type = OperationType.WEB_SEARCH;
			lang = l;
			query = q;
			numResults = num;
			keywords = k;
		}
		
		public OperationParams(Language l, List<String> k)
		{
			type = OperationType.CUSTOM_CORPUS;
			lang = l;
			query = "";
			numResults = 0;
			keywords = k;			
		}
		
		public void exec()
		{
			if (type == OperationType.WEB_SEARCH)
				performWebSearch(lang, query, numResults, keywords);
			else
				performCorpusUpload(lang, keywords);
		}
	}
	
	private static final class CompletedResultItemImpl implements CompletedResultItem
	{
		private final RankableDocument		doc;
		private final int					newRank;
		private final int					oldRank;
		
		public CompletedResultItemImpl(RankableDocument d, int nr, int or) 
		{
			doc = d;
			newRank = nr;
			oldRank = or;
		}

		@Override
		public Type getType() {
			return Type.COMPLETED;
		}

		@Override
		public String getTitle() {
			return doc.getTitle();
		}

		@Override
		public boolean hasUrl() {
			return doc.getUrl().isEmpty() == false;
		}

		@Override
		public String getUrl() {
			return doc.getUrl();
		}

		@Override
		public String getDisplayUrl() {
			return doc.getDisplayUrl();
		}

		@Override
		public String getSnippet() {
			return doc.getSnippet();
		}

		@Override
		public int getOriginalRank() {
			return oldRank;
		}

		@Override
		public int getCurrentRank() {
			return newRank;
		}
	}
	
	private static final class InProgressResultItemImpl implements InProgressResultItem
	{
		private final RankableWebSearchResult		s;
		private final UploadedDocument				u;
		
		public InProgressResultItemImpl(RankableWebSearchResult sr)
		{
			s = sr;
			u = null;
		}
		
		public InProgressResultItemImpl(UploadedDocument ud)
		{
			s = null;
			u = ud;
		}

		@Override
		public Type getType() {
			return Type.IN_PROGRESS;
		}

		@Override
		public String getTitle() 
		{
			if (s != null)
				return s.getTitle();
			else
				return u.getTitle();
		}

		@Override
		public boolean hasUrl() 
		{
			if (s != null)
				return true;
			else
				return false;
		}

		@Override
		public String getUrl() 
		{
			if (s != null)
				return s.getUrl();
			else
				return "";
		}

		@Override
		public String getDisplayUrl() 
		{
			if (s != null)
				return s.getDisplayUrl();
			else
				return "";
		}

		@Override
		public String getSnippet() 
		{
			if (s != null)
				return s.getSnippet();
			else
				return u.getSnippet();
		}
	}
	
	
	private final class State
	{	
		OperationType				currentOp;
		OperationParams				params;
		
		List<RankableDocument>		parsedDocs;		// main data store
		Map<BasicDocumentTransferObject, InProgressResultItem> 	inProgress;
		int							receivedInprogress;
		
		DocumentRankerOutput.Rank	rankData;		// cached after each reranking
		
		State()
		{
			currentOp = OperationType.NONE;
			params = null;
			parsedDocs = new ArrayList<>();
			inProgress = new HashMap<>();
			rankData = null;
			receivedInprogress = 0;
		}
		
		void begin(OperationParams p)
		{
			currentOp = p.type;
			params = p;
			rankData = null;
			
			parsedDocs.clear();
			inProgress.clear();
			receivedInprogress = 0;
			
			results.clearCompleted();
			results.clearInProgress();
			presenter.showCancelPane(true);
			presenter.showProgressBar(true, true);
			
			// init and set up message pipeline
			settings.setSliderBundle(params.lang);
			messagePipeline.setHandler(m -> webSearchMessageReceiver(m));
			messagePipeline.open(token);
		}
		
		void reset()
		{
			currentOp = OperationType.NONE;
			params = null;
			rankData = null;
			
			parsedDocs.clear();
			inProgress.clear();
			
			results.clearCompleted();
			results.clearInProgress();
			
			presenter.showLoaderOverlay(false);
			presenter.showProgressBar(false, false);
			presenter.showCancelPane(false);
			presenter.showDefaultPane(true);
			
			if (messagePipeline.isOpen())
				messagePipeline.close();
		}
		
		boolean hasOperation() {
			return currentOp != OperationType.NONE;
		}
		
		boolean isWebSearch() {
			return currentOp == OperationType.WEB_SEARCH;
		}
		
		boolean isCorpusUpload() {
			return currentOp == OperationType.CUSTOM_CORPUS;
		}
		
		void rerank()
		{
			
		}
		
		void refreshParsedResultsList()
		{
			results.clearCompleted();
			
			// add from ranked data, if available
			int i = 1;
			if (rankData != null)
			{
				for (RankableDocument itr : rankData.getRankedDocuments())
				{
					results.addCompleted(new CompletedResultItemImpl(itr, i, itr.getRank()));
					i++;
				}
			}
			else for (RankableDocument itr : parsedDocs)
			{
				results.addCompleted(new CompletedResultItemImpl(itr, i, itr.getRank()));
				i++;
			}
		}
		
		void addInProgressItem(InProgressResultItem item, BasicDocumentTransferObject dto)
		{
			if (inProgress.containsKey(dto))
			{
				ClientLogger.get().error("DTO hash collision!");
				return;
			}
			
			inProgress.put(dto, item);			
			results.addInProgress(item);
			receivedInprogress++;
		}
		
		void addSearchResult(RankableWebSearchResult sr) {
			addInProgressItem(new InProgressResultItemImpl(sr), sr);
		}
		
		void addUploadedFile(UploadedDocument doc) {
			addInProgressItem(new InProgressResultItemImpl(doc), doc);
		}
		
		void addParsedDoc(RankableDocument doc) 
		{
			// remove the corresponding inprogress item
			if (inProgress.containsKey(doc) == false)
				ClientLogger.get().error("DTO hash miss!");
			else
			{
				InProgressResultItem item = inProgress.get(doc);
				inProgress.remove(doc);
				results.removeInProgress(item);
			}
			
			parsedDocs.add(doc);
			if (params.numResults != 0)
				presenter.setProgressBarValue(parsedDocs.size() * 100 / params.numResults);
			
			// rerank the parsed docs as their original ranks can be discontinuous
			// sort the parsed docs by their original rank first and then rerank them
			Collections.sort(parsedDocs, (a, b) -> {
				return Integer.compare(a.getRank(), b.getRank());
			});
			
			int i = 1;
			for (RankableDocument itr : parsedDocs)
			{
				itr.setRank(i);
				i++;
			}
			
			// refresh the parsed results
			if (parsedDocs.size() != params.numResults)
			{
				rerank();
				refreshParsedResultsList();
			}
		}
		
		void finalizeOp()
		{
			if (inProgress.isEmpty() == false)
				ClientLogger.get().error("Job completed with delinquent in-progress items. Count: " + inProgress.size());
			
			// check result counts
			if (receivedInprogress == 0)
			{
				if (isWebSearch())
					notification.notify(getLocalizedString(WebRankerCoreLocale.DESC_NoSearchResults));
				else
					notification.notify(getLocalizedString(WebRankerCoreLocale.DESC_NoParsedDocs));
				
				if (parsedDocs.isEmpty() == false)
					ClientLogger.get().error("Eh? We received no in-progress items but have parsed docs regardless?!");
				
				reset();
				return;
			}
			
			if (parsedDocs.isEmpty())
			{
				notification.notify(getLocalizedString(WebRankerCoreLocale.DESC_NoParsedDocs));
				reset();
				return;
			}
			else if (parsedDocs.size() < params.numResults)
				notification.notify(getLocalizedString(WebRankerCoreLocale.DESC_MissingDoc));
			
			// cleanup
			currentOp = OperationType.NONE;
			receivedInprogress = 0;
			messagePipeline.close();
			inProgress.clear();
			results.clearCompleted();
			results.clearInProgress();
			presenter.showCancelPane(false);
			presenter.showProgressBar(false, false);	
			
			// rerank and display
			rerank();
			refreshParsedResultsList();
		}
	}
	
	
	private AuthToken							token;
	private AbstractWebRankerPresenter			presenter;
	private final AbstractDocumentRanker		ranker;
	private final AbstractDocumentAnnotator		annotator;
	private final AbstractMessageReceiver		messagePipeline;
	private AbstractRankerSettingsPane			settings;
	private AbstractDocumentResultsPane			results;
	private AbstractDocumentPreviewPane			preview;
	private UserPromptService					prompt;
	private NotificationService					notification;
	private final WebRankerServiceAsync			service;
	private State								state;
	
	
	public WebRankerCore(AbstractDocumentRanker r, AbstractDocumentAnnotator a, AbstractMessageReceiver m)
	{
		token = null;
		presenter = null;
		
		ranker = r;
		annotator = a;
		messagePipeline = m;
		
		settings = null;
		results = null;
		preview = null;
		prompt = null;
		notification = null;
		service = WebRankerServiceAsync.Util.getInstance();
		state = new State();
	}

	private void bindToPresenter(AbstractWebRankerPresenter presenter)
	{
		settings = presenter.getRankerSettingsPane();
		results = presenter.getDocumentResultsPane();
		preview = presenter.getDocumentPreviewPane();
		prompt = presenter.getPromptService();
		notification = presenter.getNotificationService();
		
//		settings.setExportSettingsHandler(null);
//		settings.setVisualizeHandler(null);
		
		settings.setSettingsChangedHandler(() -> onSettingsChanged());
		results.setSelectHandler(e -> onSelectResultItem(e));
	}
	
	private void onSettingsChanged()
	{
		
	}
	
	private void onSelectResultItem(AbstractResultItem item)
	{
		
	}
	
	private String getLocalizedString(String desc)
	{
		Language lang = LocalizationEngine.get().getLanguage();
		return WebRankerCoreLocale.INSTANCE.lookup(lang, desc);
	}
	
	private boolean checkRunningOperation(OperationParams newOp)
	{
		if (state.hasOperation())
		{
			// prompt the user if they want to cancel the currently running operation
			String title = getLocalizedString(WebRankerCoreLocale.DESC_OpInProgessTitle);
			String caption = getLocalizedString(WebRankerCoreLocale.DESC_OpInProgessCaption);
			
			prompt.yesNo(title, caption, () -> newOp.exec(), () -> {});
			return false;
		}
		
		return true;
	}
	
	private void webSearchMessageReceiver(ServerMessage msg)
	{
		if (msg.getType() != ServerMessage.Type.SEARCH_CRAWL_PARSE)
			throw new RuntimeException("Invalid message type for web search operation: " + msg.getType());
			
		ServerMessage.SearchCrawlParse data = msg.getSearchCrawlParse();
		switch (data.getType())
		{
		case CRAWL_COMPLETE:
			state.addSearchResult(data.getCrawled());
			break;
		case PARSE_COMPLETE:
			state.addParsedDoc(data.getParsed());
			break;
		case JOB_COMPLETE:
			state.finalizeOp();
			break;
		default:
			ClientLogger.get().error("Unknown message from server: " + msg.getType());
		}
	}

	@Override
	public void setAuthToken(AuthToken token) 
	{
		if (this.token != null)
			throw new RuntimeException("Token already set");
		
		this.token = token;
	}

	@Override
	public void setPresenter(AbstractWebRankerPresenter presenter) 
	{
		if (this.presenter != null)
			throw new RuntimeException("Presenter already set");
		
		this.presenter = presenter;
		bindToPresenter(this.presenter);
	}

	@Override
	public void performWebSearch(Language lang, String query, int numResults, List<String> keywords) 
	{
		OperationParams params = new OperationParams(lang, query, numResults, keywords);
		if (checkRunningOperation(params) == false)
			return;
		
		presenter.showLoaderOverlay(true);
		service.beginWebSearch(token, lang, query, numResults, new ArrayList<>(keywords),
				FuncCallback.get(e -> {
					state.begin(params);
					presenter.showLoaderOverlay(false);
				},
				e -> {
					ClientLogger.get().error(e, "Couldn't begin web search operation");
					notification.notify(getLocalizedString(WebRankerCoreLocale.DESC_ServerError));
					presenter.showLoaderOverlay(false);
				}));
	}

	@Override
	public void performCorpusUpload(Language lang, List<String> keywords) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelCurrentOperation() {
		// TODO Auto-generated method stub
		
	}
}
