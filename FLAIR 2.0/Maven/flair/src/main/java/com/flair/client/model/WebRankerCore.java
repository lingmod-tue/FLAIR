package com.flair.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.flair.client.interop.FuncCallback;
import com.flair.client.localization.LocalizationEngine;
import com.flair.client.localization.locale.GrammaticalConstructionLocale;
import com.flair.client.localization.locale.WebRankerCoreLocale;
import com.flair.client.model.interfaces.AbstractDocumentAnnotator;
import com.flair.client.model.interfaces.AbstractDocumentRanker;
import com.flair.client.model.interfaces.AbstractWebRankerCore;
import com.flair.client.model.interfaces.DocumentAnnotatorInput;
import com.flair.client.model.interfaces.DocumentAnnotatorOutput;
import com.flair.client.model.interfaces.DocumentRankerInput;
import com.flair.client.model.interfaces.DocumentRankerOutput;
import com.flair.client.presentation.interfaces.AbstractDocumentPreviewPane;
import com.flair.client.presentation.interfaces.AbstractDocumentResultsPane;
import com.flair.client.presentation.interfaces.AbstractRankerSettingsPane;
import com.flair.client.presentation.interfaces.AbstractResultItem;
import com.flair.client.presentation.interfaces.AbstractResultItem.Type;
import com.flair.client.presentation.interfaces.AbstractWebRankerPresenter;
import com.flair.client.presentation.interfaces.CompletedResultItem;
import com.flair.client.presentation.interfaces.CorpusUploadService;
import com.flair.client.presentation.interfaces.CustomKeywordService;
import com.flair.client.presentation.interfaces.DocumentPreviewPaneInput;
import com.flair.client.presentation.interfaces.InProgressResultItem;
import com.flair.client.presentation.interfaces.NotificationService;
import com.flair.client.presentation.interfaces.UserPromptService;
import com.flair.client.presentation.widgets.GenericWeightSlider;
import com.flair.client.utilities.ClientLogger;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.AbstractMessageReceiver;
import com.flair.shared.interop.AuthToken;
import com.flair.shared.interop.BasicDocumentTransferObject;
import com.flair.shared.interop.RankableDocument;
import com.flair.shared.interop.RankableWebSearchResult;
import com.flair.shared.interop.ServerMessage;
import com.flair.shared.interop.UploadedDocument;
import com.flair.shared.interop.services.WebRankerServiceAsync;
import com.flair.shared.parser.DocumentReadabilityLevel;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Timer;

/*
 * Web ranker module
 */
public class WebRankerCore implements AbstractWebRankerCore
{
	enum OperationType
	{
		NONE, WEB_SEARCH, CUSTOM_CORPUS
	}

	private final class OperationParams
	{
		public final OperationType	type;
		public final Language		lang;
		public final String			query;
		public final int			numResults;

		public OperationParams(Language l, String q, int num)
		{
			type = OperationType.WEB_SEARCH;
			lang = l;
			query = q;
			numResults = num;
		}

		public OperationParams(Language l)
		{
			type = OperationType.CUSTOM_CORPUS;
			lang = l;
			query = "";
			numResults = 0;
		}

		public void exec()
		{
			// corpus uploading is triggered elsewhere
			if (type == OperationType.WEB_SEARCH)
				performWebSearch(lang, query, numResults);
		}
	}

	private final class State
	{
		private static final int			TIMEOUT_MS = 10 * 60 * 1000;
		
		final class InProgressData
		{
			BasicDocumentTransferObject	inProgressItem;
			InProgressResultItem		resultItem;

			InProgressData(BasicDocumentTransferObject dto, InProgressResultItem itm)
			{
				inProgressItem = dto;
				resultItem = itm;
			}
		}

		Language			lastUsedLang;
		OperationType		currentOp;
		OperationParams		params;
		Timer				timeout;

		List<RankableDocument>			parsedDocs;			// main data store
		Map<Integer, InProgressData>	inProgress;			// DTO ids -> result items
		int								receivedInprogress;
		List<RankableDocument>			filteredDocs;

		DocumentRankerOutput.Rank rankData; // cached after each reranking

		State()
		{
			lastUsedLang = Language.ENGLISH;
			currentOp = OperationType.NONE;
			params = null;
			parsedDocs = new ArrayList<>();
			inProgress = new HashMap<>();
			rankData = null;
			receivedInprogress = 0;
			filteredDocs = new ArrayList<>();
			
			timeout = new Timer() {
				@Override
				public void run()
				{
					if (hasOperation())
						cancelCurrentOperation();
					else
						reset();
					
					notification.notify(getLocalizedString(WebRankerCoreLocale.DESC_OpTimeout), 5000);
					ClientLogger.get().error("The current operation timed-out!");
				}
			};
		}

		void begin(OperationParams p)
		{
			lastUsedLang = p.lang;
			currentOp = p.type;
			params = p;
			rankData = null;

			parsedDocs.clear();
			inProgress.clear();
			filteredDocs.clear();
			receivedInprogress = 0;

			results.setPanelTitle("\"" + params.query + "\"");
			results.clearCompleted();
			results.clearInProgress();
			presenter.showCancelPane(true);
			presenter.showProgressBar(true, true);

			// init and set up message pipeline
			settings.setSliderBundle(params.lang);
			if (currentOp == OperationType.WEB_SEARCH)
				messagePipeline.setHandler(m -> webSearchMessageReceiver(m));
			else
				messagePipeline.setHandler(m -> customCorpusMessageReceiver(m));

			messagePipeline.open(token);
			
			timeout.schedule(TIMEOUT_MS);
		}

		void reset()
		{
			currentOp = OperationType.NONE;
			params = null;
			rankData = null;

			parsedDocs.clear();
			inProgress.clear();
			filteredDocs.clear();

			results.clearCompleted();
			results.clearInProgress();

			presenter.showLoaderOverlay(false);
			presenter.showProgressBar(false, false);
			presenter.showCancelPane(false);
			presenter.showDefaultPane(true);

			if (messagePipeline.isOpen())
				messagePipeline.close();
			
			timeout.cancel();
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
			preview.hide();
			rankData = ranker.rerank(new RankerInput());
		}

		boolean isDocFiltered(RankableDocument doc)
		{
			for (RankableDocument itr : filteredDocs)
			{
				if (itr == doc)
					return true;
			}

			return false;
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
			} else
				for (RankableDocument itr : parsedDocs)
				{
					results.addCompleted(new CompletedResultItemImpl(itr, i, itr.getRank()));
					i++;
				}
		}

		void addInProgressItem(InProgressResultItem item, BasicDocumentTransferObject dto)
		{
			if (inProgress.containsKey(dto.getIdentifier()))
			{
				ClientLogger.get().error("DTO hash collision!");
				return;
			}

			inProgress.put(dto.getIdentifier(), new InProgressData(dto, item));
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
			if (inProgress.containsKey(doc.getIdentifier()) == false)
				ClientLogger.get().error("DTO hash miss!");
			else
			{
				InProgressResultItem item = inProgress.get(doc.getIdentifier()).resultItem;
				inProgress.remove(doc.getIdentifier());
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
				ClientLogger.get()
				.error("Job completed with delinquent in-progress items. Count: " + inProgress.size());

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
			} else if (parsedDocs.size() < params.numResults)
				notification.notify(getLocalizedString(WebRankerCoreLocale.DESC_MissingDoc));

			// cleanup
			currentOp = OperationType.NONE;
			receivedInprogress = 0;
			messagePipeline.close();
			inProgress.clear();
			results.clearInProgress();
			presenter.showCancelPane(false);
			presenter.showProgressBar(false, false);

			// rerank and display
			rerank();
			refreshParsedResultsList();

			notification.notify(getLocalizedString(WebRankerCoreLocale.DESC_AnalysisComplete), 5000);
		}
	}

	private final class RankerInput implements DocumentRankerInput.Rank
	{
		private final List<GrammaticalConstruction> weightedConstructions;

		public RankerInput()
		{
			weightedConstructions = new ArrayList<>();

			// populate from settings pane
			settings.getSliderBundle().forEachWeightSlider(w -> {
				if (w.isEnabled() && w.hasWeight())
					weightedConstructions.add(w.getGram());
			});
		}

		@Override
		public Language getLanguage() {
			return state.lastUsedLang;
		}

		@Override
		public Iterable<GrammaticalConstruction> getConstructions() {
			return weightedConstructions;
		}

		@Override
		public double getMaxWeight() {
			return GenericWeightSlider.getSliderMax();
		}

		@Override
		public double getConstructionWeight(GrammaticalConstruction gram) {
			return settings.getSliderBundle().getWeightSlider(gram).getWeight();
		}

		@Override
		public boolean isConstructionEnabled(GrammaticalConstruction gram) {
			return settings.getSliderBundle().getWeightSlider(gram).isEnabled();
		}

		@Override
		public double getKeywordWeight() {
			return settings.getKeywordSlider().getWeight();
		}

		@Override
		public boolean isKeywordEnabled() {
			return settings.getKeywordSlider().isEnabled();
		}

		@Override
		public double getDocLengthWeight() {
			return settings.getLengthSlider().getValue();
		}

		@Override
		public boolean isDocLevelEnabled(DocumentReadabilityLevel level) {
			return settings.isDocLevelEnabled(level);
		}

		@Override
		public Iterable<RankableDocument> getDocuments() {
			return state.parsedDocs;
		}

		@Override
		public boolean isDocumentFiltered(RankableDocument doc) {
			return state.isDocFiltered(doc);
		}
	}

	final class PreviewRankableInput implements DocumentAnnotatorInput.HighlightText, DocumentPreviewPaneInput.Rankable
	{
		private final String[]		COLORS	= new String[] { "lightgreen", "lightblue", "lightpink",
				"lightcyan", "lightsalmon", "lightgrey", "lightyellow" };
		private static final String	COLOR_KEYWORDS	= "gold";

		private final RankableDocument					doc;
		private final List<GrammaticalConstruction>		annotatedConstructions;
		private final Map<GrammaticalConstruction, String>	annotationColors;
		private DocumentAnnotatorOutput.HighlightText	annotation;

		PreviewRankableInput(RankableDocument d)
		{
			doc = d;
			annotatedConstructions = new ArrayList<>();
			annotationColors = new EnumMap<>(GrammaticalConstruction.class);
			annotation = null;

			// populate from settings pane
			settings.getSliderBundle().forEachWeightSlider(w -> {
				if (w.isEnabled() && w.hasWeight())
					annotatedConstructions.add(w.getGram());
			});

			// setup colors
			int availableColors = COLORS.length - 1;
			for (int i = 0; i < annotatedConstructions.size(); i++)
			{
				String color;
				if (availableColors >= 0)
					color = COLORS[availableColors--];
				else
					color = COLORS[COLORS.length - 1];

				annotationColors.put(annotatedConstructions.get(i), color);
			}
		}

		@Override
		public RankableDocument getDocument() {
			return doc;
		}

		@Override
		public Iterable<GrammaticalConstruction> getAnnotatedConstructions() {
			return annotatedConstructions;
		}

		@Override
		public String getConstructionAnnotationColor(GrammaticalConstruction gram) {
			return annotationColors.get(gram);
		}

		@Override
		public String getConstructionTitle(GrammaticalConstruction gram) {
			return GrammaticalConstructionLocale.get().getLocalizedName(gram, LocalizationEngine.get().getLanguage());
		}

		@Override
		public boolean shouldAnnotateKeywords() {
			return settings.getKeywordSlider().isEnabled();
		}

		@Override
		public String getKeywordAnnotationColor() {
			return COLOR_KEYWORDS;
		}

		@Override
		public String getKeywordTitle() {
			return getLocalizedString(WebRankerCoreLocale.DESC_KeywordTitle);
		}

		@Override
		public Iterable<GrammaticalConstruction> getWeightedConstructions() {
			return annotatedConstructions;
		}

		@Override
		public boolean isConstructionWeighted(GrammaticalConstruction gram) {
			return state.rankData.isConstructionWeighted(gram);
		}

		@Override
		public SafeHtml getPreviewMarkup() {
			return annotation.getHighlightedText();
		}

		@Override
		public boolean shouldShowKeywords() {
			return shouldAnnotateKeywords();
		}

		@Override
		public boolean hasCustomKeywords() {
			return settings.getKeywordSlider().hasCustomVocab();
		}

		@Override
		public double getConstructionWeight(GrammaticalConstruction gram) {
			return state.rankData.getConstructionWeight(gram);
		}

		@Override
		public double getKeywordWeight() {
			return state.rankData.getKeywordWeight();
		}
	}

	final class PreviewUnrankableInput implements DocumentPreviewPaneInput.UnRankable
	{
		private final BasicDocumentTransferObject		dto;

		public PreviewUnrankableInput(BasicDocumentTransferObject dto) {
			this.dto = dto;
		}

		@Override
		public String getTitle() {
			return dto.getTitle();
		}

		@Override
		public String getText() {
			return dto.getText();
		}
	}

	private AuthToken						token;
	private AbstractWebRankerPresenter		presenter;
	private final AbstractDocumentRanker	ranker;
	private final AbstractDocumentAnnotator	annotator;
	private final AbstractMessageReceiver	messagePipeline;
	private AbstractRankerSettingsPane		settings;
	private AbstractDocumentResultsPane		results;
	private AbstractDocumentPreviewPane		preview;
	private UserPromptService				prompt;
	private NotificationService				notification;
	private CorpusUploadService				upload;
	private CustomKeywordService			keywords;
	private final WebRankerServiceAsync		service;
	private State							state;

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
		upload = null;
		keywords = null;

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
		upload = presenter.getCorpusUploadService();
		keywords = presenter.getCustomKeywordsService();

		// settings.setExportSettingsHandler(null);
		// settings.setVisualizeHandler(null);

		settings.setSettingsChangedHandler(() -> onSettingsChanged());
		results.setSelectHandler(e -> onSelectResultItem(e));
		upload.setUploadBeginHandler(() -> onUploadBegin());
		upload.setUploadCompleteHandler(() -> onUploadComplete());
	}

	private void onSettingsChanged() {
		state.rerank();
	}

	private void onSelectResultItem(AbstractResultItem item)
	{
		if (item.getType() == Type.COMPLETED)
		{
			// annotate the doc and preview it
			CompletedResultItemImpl citem = (CompletedResultItemImpl)item;
			RankableDocument doc = citem.getDoc();
			PreviewRankableInput data = new PreviewRankableInput(doc);
			data.annotation = annotator.hightlightText(data);

			preview.show();
			preview.preview(data);
		}
		else
		{
			// preview the title and text
			InProgressResultItemImpl citem = (InProgressResultItemImpl)item;

			preview.show();
			preview.preview(new PreviewUnrankableInput(citem.getDTO()));
		}
	}

	private void onUploadBegin()
	{
		// begin operation and wait for the server
		if (state.hasOperation())
			throw new RuntimeException("Cannot start upload until the current operation is complete");
		
		OperationParams params = new OperationParams(state.lastUsedLang);
		presenter.showLoaderOverlay(true);
		service.beginCorpusUpload(token, state.lastUsedLang, new ArrayList<>(keywords.getCustomKeywords()), FuncCallback.get(
				e -> {
					state.begin(params);
					presenter.showLoaderOverlay(false);
				},
				e -> {
					ClientLogger.get().error(e, "Couldn't begin corpus upload operation");
					notification.notify(getLocalizedString(WebRankerCoreLocale.DESC_ServerError));
					presenter.showLoaderOverlay(false);
				}));


		ClientLogger.get().info("Upload operation has begun...");
	}

	private void onUploadComplete()
	{
		// signal the end of the upload operation
		service.endCorpusUpload(token, FuncCallback.get(e -> {}));
		ClientLogger.get().info("Upload operation has ended - Waiting for the server");
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

	private void customCorpusMessageReceiver(ServerMessage msg)
	{
		if (msg.getType() != ServerMessage.Type.CUSTOM_CORPUS)
			throw new RuntimeException("Invalid message type for custom corpus operation: " + msg.getType());

		ServerMessage.CustomCorpus data = msg.getCustomCorpus();
		switch (data.getType())
		{
		case UPLOAD_COMPLETE:
			for (UploadedDocument itr : data.getUploaded())
				state.addUploadedFile(itr);
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
	public void performWebSearch(Language lang, String query, int numResults)
	{
		OperationParams params = new OperationParams(lang, query, numResults);
		if (checkRunningOperation(params) == false)
			return;

		presenter.showLoaderOverlay(true);
		service.beginWebSearch(token, lang, query, numResults, new ArrayList<>(keywords.getCustomKeywords()), FuncCallback.get(
				e -> {
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
	public void cancelCurrentOperation()
	{
		service.cancelCurrentOperation(token, FuncCallback.get(e -> {}));
		state.reset();
	}

	@Override
	public boolean isOperationInProgress() {
		return state.hasOperation();
	}
}

final class CompletedResultItemImpl implements CompletedResultItem
{
	private final RankableDocument	doc;
	private final int				newRank;
	private final int				oldRank;

	public CompletedResultItemImpl(RankableDocument d, int nr, int or)
	{
		doc = d;
		newRank = nr;
		oldRank = or;
	}

	public RankableDocument getDoc() {
		return doc;
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

final class InProgressResultItemImpl implements InProgressResultItem
{
	private final RankableWebSearchResult	s;
	private final UploadedDocument			u;

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

	public BasicDocumentTransferObject getDTO() {
		return s != null ? s : u;
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
