package com.flair.client.model;

import com.flair.client.ClientEndPoint;
import com.flair.client.interop.FuncCallback;
import com.flair.client.localization.*;
import com.flair.client.model.interfaces.*;
import com.flair.client.presentation.interfaces.*;
import com.flair.client.presentation.interfaces.AbstractResultItem.Type;
import com.flair.client.presentation.widgets.GenericWeightSlider;
import com.flair.client.presentation.widgets.GrammaticalConstructionWeightSlider;
import com.flair.client.presentation.widgets.LanguageSpecificConstructionSliderBundle;
import com.flair.client.utilities.ClientLogger;
import com.flair.client.utilities.GwtUtil;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.*;
import com.flair.shared.interop.services.WebRankerServiceAsync;
import com.flair.shared.parser.DocumentReadabilityLevel;
import com.flair.shared.utilities.GenericEventSource;
import com.flair.shared.utilities.GenericEventSource.EventHandler;
import com.google.gwt.core.client.Duration;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.http.client.URL;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import gwt.material.design.client.constants.Color;

import java.util.*;

/*
 * Monolithic controller component for the various widgets and services
 */
public class WebRankerCore implements AbstractWebRankerCore {
	enum LocalizationTags {
		ANALYSIS_COMPLETE,
		SELECTED_FOR_COMPARISON,
		CUSTOM_CORPUS_TITLE,
		APPLIED_IMPORTED_SETTINGS,
		SERVER_ERROR,
		OP_TIMEDOUT,
		NO_RESULTS_FOR_FILTER,
		NO_SEARCH_RESULTS,
		IMPORTED_SETINGS,
		SEARCH_COOLDOWN,
		NO_PARSED_DOCS,
		MISSING_DOCS,
		MISSING_SEARCH_RESULTS,
		SERVER_PING_TIMEOUT,
	}

	private abstract class ProcessData implements WebRankerAnalysis {
		final OperationType type;
		final Language lang;
		final List<RankableDocument> parsedDocs;
		List<String> keywords;
		boolean complete;        // flagged after completion
		boolean invalid;        // set if cancelled or if there weren't any usable results

		ProcessData(OperationType t, Language l) {
			type = t;
			lang = l;
			complete = false;
			parsedDocs = new ArrayList<>();
			keywords = new ArrayList<>();
			invalid = false;
		}

		void setKeywords(List<String> kw) {
			keywords = new ArrayList<>(kw);
		}

		@Override
		public OperationType getType() {
			return type;
		}

		@Override
		public Language getLanguage() {
			return lang;
		}

		@Override
		public List<RankableDocument> getParsedDocs() {
			return parsedDocs;
		}

		@Override
		public boolean inProgress() {
			return complete == false;
		}
	}

	private final class WebSearchProcessData extends ProcessData {
		final String query;
		final int numResults;
		final List<RankableWebSearchResult> searchResults;

		WebSearchProcessData(Language l, String q, int r) {
			super(OperationType.WEB_SEARCH, l);
			query = q;
			numResults = r;
			searchResults = new ArrayList<>();
		}


		@Override
		public String getName() {
			return "'" + query + "'";
		}
	}

	private final class CorpusUploadProcessData extends ProcessData {
		final List<UploadedDocument> uploadedDocs;

		CorpusUploadProcessData(Language l) {
			super(OperationType.CUSTOM_CORPUS, l);
			uploadedDocs = new ArrayList<>();
		}

		@Override
		public String getName() {
			if (uploadedDocs.isEmpty())
				return "";

			StringBuilder sb = new StringBuilder();
			for (UploadedDocument itr : uploadedDocs)
				sb.append(itr.getTitle()).append(", ");

			String out = sb.toString();
			return out.substring(0, out.length() - 2);
		}
	}

	private final class CompareProcessData extends ProcessData {
		final class ComparisonWrapper implements RankableDocument {
			final RankableDocument doc;
			int rank;        // store the default rank separately to prevent the org doc from being modified

			public ComparisonWrapper(RankableDocument d) {
				doc = d;
				rank = -1;
			}
			ComparisonWrapper() {
				doc = null;
			}

			@Override
			public Language getLanguage() {
				return doc.getLanguage();
			}

			@Override
			public String getTitle() {
				return doc.getTitle();
			}

			@Override
			public String getSnippet() {
				return doc.getSnippet();
			}

			@Override
			public String getText() {
				return doc.getText();
			}
			@Override
			public String getOperationId() {
				return doc.getOperationId();
			}

			@Override
			public int getLinkingId() {
				return doc.getLinkingId();
			}

			@Override
			public int getRank() {
				return rank;
			}

			@Override
			public void setRank(int rank) {
				this.rank = rank;
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
			public HashSet<GrammaticalConstruction> getConstructions() {
				return doc.getConstructions();
			}

			@Override
			public boolean hasConstruction(GrammaticalConstruction gram) {
				return doc.hasConstruction(gram);
			}

			@Override
			public double getConstructionFreq(GrammaticalConstruction gram) {
				return doc.getConstructionFreq(gram);
			}

			@Override
			public double getConstructionRelFreq(GrammaticalConstruction gram) {
				return doc.getConstructionRelFreq(gram);
			}

			@Override
			public ArrayList<? extends ConstructionRange> getConstructionOccurrences(GrammaticalConstruction gram) {
				return doc.getConstructionOccurrences(gram);
			}

			@Override
			public double getKeywordCount() {
				return doc.getKeywordCount();
			}

			@Override
			public double getKeywordRelFreq() {
				return doc.getKeywordRelFreq();
			}

			@Override
			public ArrayList<? extends KeywordRange> getKeywordOccurrences() {
				// keyword weighting will be skewed if the comparison docs don't share the same keyword list
				// we'll support it regardless
				return doc.getKeywordOccurrences();
			}

			@Override
			public int getRawTextLength() {
				return doc.getRawTextLength();
			}

			@Override
			public double getNumWords() {
				return doc.getNumWords();
			}

			@Override
			public double getNumSentences() {
				return doc.getNumSentences();
			}

			@Override
			public double getNumDependencies() {
				return doc.getNumDependencies();
			}

			@Override
			public DocumentReadabilityLevel getReadabilityLevel() {
				return doc.getReadabilityLevel();
			}

			@Override
			public double getReadablilityScore() {
				return doc.getReadablilityScore();
			}
		}

		CompareProcessData(Language l, List<RankableDocument> sel) {
			super(OperationType.COMPARE, l);
			complete = true;        // comparison ops are never transient

			// fixup the default ranks
			int i = 1;
			for (RankableDocument itr : sel) {
				ComparisonWrapper wrap = new ComparisonWrapper(itr);
				wrap.setRank(i);
				parsedDocs.add(wrap);
				i++;
			}
		}

		@Override
		public String getName() {
			return "";
		}
	}

	interface ProcessCompletionEventHandler {
		void handle(ProcessData d, boolean success);
	}

	interface SuccessfulParseEventHandler {
		void handle(ProcessData proc, RankableDocument d);
	}

	private final class CompletedResultItemImpl implements CompletedResultItem {
		private final RankableDocument doc;
		private final int newRank;
		private final int oldRank;
		private final boolean overflow;

		public CompletedResultItemImpl(RankableDocument d, int nr, int or, boolean menu) {
			doc = d;
			newRank = nr;
			oldRank = or;
			overflow = menu;
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

		@Override
		public void selectItem(SelectionType selectionType, Element parentWidget) {
			switch (selectionType) {
			case TITLE:
				if (doc.getLanguage() == Language.ENGLISH)
					questgenpreview.show(doc, parentWidget);
				else if (hasUrl())
					Window.open(getUrl(), "_blank", "");
				else
					rankPreviewModule.preview(this);
				break;
			case DEFAULT:
				rankPreviewModule.preview(this);
				break;
			}
		}

		@Override
		public boolean hasOverflowMenu() {
			return overflow;
		}

		@Override
		public void addToCompare() {
			comparer.addToSelection(doc);
			notification.notify(getLocalizedString(LocalizationTags.SELECTED_FOR_COMPARISON.toString()));
		}
	}

	private final class InProgressResultItemImpl implements InProgressResultItem {
		private final RankableWebSearchResult s;
		private final UploadedDocument u;

		public InProgressResultItemImpl(RankableWebSearchResult sr) {
			s = sr;
			u = null;
		}

		public InProgressResultItemImpl(UploadedDocument ud) {
			s = null;
			u = ud;
		}

		public DocumentDTO getDTO() {
			return s != null ? s : u;
		}

		@Override
		public Type getType() {
			return Type.IN_PROGRESS;
		}

		@Override
		public String getTitle() {
			if (s != null)
				return s.getTitle();
			else
				return u.getTitle();
		}

		@Override
		public boolean hasUrl() {
			if (s != null)
				return true;
			else
				return false;
		}

		@Override
		public String getUrl() {
			if (s != null)
				return s.getUrl();
			else
				return "";
		}

		@Override
		public String getDisplayUrl() {
			if (s != null)
				return s.getDisplayUrl();
			else
				return "";
		}

		@Override
		public String getSnippet() {
			if (s != null)
				return s.getSnippet();
			else
				return u.getSnippet();
		}


		@Override
		public void selectItem(SelectionType selectionType, Element parentWidget) {
			switch (selectionType) {
			case TITLE:
				if (hasUrl())
					Window.open(getUrl(), "_blank", "");

				break;
			case DEFAULT:
				rankPreviewModule.preview(this);
				break;
			}
		}


		@Override
		public boolean hasOverflowMenu() {
			return false;
		}


		@Override
		public void addToCompare() {
			throw new RuntimeException("Not implemented");
		}
	}

	private final class RankPreviewModule {
		final class RankerInput implements DocumentRankerInput.Rank {
			private final Set<GrammaticalConstruction> langConstructions;

			public RankerInput() {
				langConstructions = GrammaticalConstruction.getForLanguage(getLanguage());
			}

			@Override
			public Language getLanguage() {
				return data.lang;
			}

			@Override
			public Iterable<GrammaticalConstruction> getConstructions() {
				return langConstructions;
			}

			@Override
			public double getMaxWeight() {
				return GenericWeightSlider.getSliderMax();
			}

			@Override
			public double getConstructionWeight(GrammaticalConstruction gram) {
				GrammaticalConstructionWeightSlider slider = settings.getSliderBundle().getWeightSlider(gram);
				if (slider != null)
					return slider.getWeight();
				else
					return 0;
			}

			@Override
			public boolean isConstructionEnabled(GrammaticalConstruction gram) {
				GrammaticalConstructionWeightSlider slider = settings.getSliderBundle().getWeightSlider(gram);
				if (slider != null)
					return slider.isEnabled();
				else
					return false;
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
				return settings.getLengthConfig().getWeight();
			}

			@Override
			public boolean isDocLevelEnabled(DocumentReadabilityLevel level) {
				return settings.isDocLevelEnabled(level);
			}

			@Override
			public Iterable<RankableDocument> getDocuments() {
				return data.parsedDocs;
			}

			@Override
			public boolean isDocumentFiltered(RankableDocument doc) {
				return isInFilter(doc);
			}

			@Override
			public boolean hasConstructionSlider(GrammaticalConstruction gram) {
				return settings.getSliderBundle().hasConstruction(gram);
			}
		}

		final class PreviewRankableInput implements DocumentAnnotatorInput.HighlightText, DocumentPreviewPaneInput.Rankable {
			private final Color[] COLORS = new Color[]
					{
							Color.GREEN_LIGHTEN_3,
							Color.LIGHT_BLUE_LIGHTEN_3,
							Color.PINK_LIGHTEN_4,
							Color.CYAN_LIGHTEN_3,
							Color.DEEP_PURPLE_LIGHTEN_3,
							Color.BROWN_LIGHTEN_2,
							Color.RED_LIGHTEN_2
					};

			private final Color COLOR_KEYWORDS = Color.AMBER;

			private final RankableDocument doc;
			private final List<GrammaticalConstruction> annotatedConstructions;
			private final Map<GrammaticalConstruction, Color> annotationColors;
			private DocumentAnnotatorOutput.HighlightText annotation;
			private final Set<GrammaticalConstruction> langConstructions;

			PreviewRankableInput(RankableDocument d) {
				doc = d;
				annotatedConstructions = new ArrayList<>();
				annotationColors = new EnumMap<>(GrammaticalConstruction.class);
				annotation = null;

				// populate from settings pane
				settings.getSliderBundle().forEachWeightSlider(w -> {
					if (w.isEnabled() && w.hasWeight())
						annotatedConstructions.add(w.getGram());
				});

				langConstructions = GrammaticalConstruction.getForLanguage(getLanguage());
				// setup colors
				int availableColors = 0;
				for (int i = 0; i < annotatedConstructions.size(); i++) {
					Color color;
					if (availableColors < COLORS.length)
						color = COLORS[availableColors++];
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
			public Color getConstructionAnnotationColor(GrammaticalConstruction gram) {
				return annotationColors.get(gram);
			}

			@Override
			public String getConstructionTitle(GrammaticalConstruction gram) {
				return GrammaticalConstructionLocalizationProvider.getName(gram);
			}

			@Override
			public boolean shouldAnnotateKeywords() {
				return settings.getKeywordSlider().isEnabled();
			}

			@Override
			public Color getKeywordAnnotationColor() {
				return COLOR_KEYWORDS;
			}

			@Override
			public String getKeywordTitle() {
				return getLocalizedString(DefaultLocalizationProviders.COMMON.toString(), CommonLocalizationTags.KEYWORDS.toString());
			}

			@Override
			public Iterable<GrammaticalConstruction> getWeightedConstructions() {
				return annotatedConstructions;
			}

			@Override
			public boolean isConstructionWeighted(GrammaticalConstruction gram) {
				return rankData.isConstructionWeighted(gram);
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
				return rankData.getConstructionWeight(gram);
			}

			@Override
			public double getKeywordWeight() {
				return rankData.getKeywordWeight();
			}

			@Override
			public Language getLanguage() {
				return doc.getLanguage();
			}

			@Override
			public Iterable<GrammaticalConstruction> getConstructions() {
				return langConstructions;
			}
		}

		final class PreviewUnrankableInput implements DocumentPreviewPaneInput.UnRankable {
			private final DocumentDTO dto;

			public PreviewUnrankableInput(DocumentDTO dto) {
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

		final class VisualizeInput implements VisualizerService.Input {
			private final Set<GrammaticalConstruction> langConstructions;

			public VisualizeInput() {
				langConstructions = GrammaticalConstruction.getForLanguage(data.lang);
			}

			@Override
			public Iterable<GrammaticalConstruction> getConstructions() {
				return langConstructions;
			}

			@Override
			public Iterable<RankableDocument> getDocuments() {
				return data.parsedDocs;
			}

			@Override
			public LanguageSpecificConstructionSliderBundle getSliders() {
				return settings.getSliderBundle();
			}

			@Override
			public boolean isDocumentFiltered(RankableDocument doc) {
				return isInFilter(doc);
			}
		}

		ProcessData data;
		final List<RankableDocument> filteredDocs;
		DocumentRankerOutput.Rank rankData;
		AbstractResultItem lastSelection;

		RankPreviewModule() {
			data = null;
			filteredDocs = new ArrayList<>();
			rankData = null;
			lastSelection = null;

			reset();
		}

		void set(ProcessData d) {
			reset();

			data = d;
			settings.setSliderBundle(data.lang);
		}

		void reset() {
			// init the ranker with some default, empty data
			data = new CompareProcessData(Language.ENGLISH, new ArrayList<>());
			filteredDocs.clear();
			rankData = null;
			lastSelection = null;
		}

		void rerank() {
			rankData = ranker.rerank(new RankerInput());

			// update both panes
			settings.updateSettings(rankData);
			if (lastSelection != null && preview.isVisible())
				preview(lastSelection);
		}

		boolean isInFilter(RankableDocument doc) {
			for (RankableDocument itr : filteredDocs) {
				if (itr == doc)
					return true;
			}

			return false;
		}

		void addToFilter(RankableDocument doc) {
			filteredDocs.add(doc);
		}

		void resetFilter() {
			filteredDocs.clear();
		}

		void preview(AbstractResultItem item) {
			if (item.getType() == Type.COMPLETED) {
				// annotate the doc and preview it
				CompletedResultItemImpl citem = (CompletedResultItemImpl) item;
				RankableDocument doc = citem.getDoc();
				PreviewRankableInput data = new PreviewRankableInput(doc);
				data.annotation = annotator.hightlightText(data);

				preview.show();
				preview.preview(data);
			} else {
				// preview the title and text
				InProgressResultItemImpl citem = (InProgressResultItemImpl) item;

				preview.show();
				preview.preview(new PreviewUnrankableInput(citem.getDTO()));
			}

			lastSelection = item;
			results.setSelection(lastSelection);
		}

		void refreshResultsTitle() {
			switch (data.type) {
			case COMPARE:
				results.setPanelTitle(getLocalizedString(DefaultLocalizationProviders.COMMON.toString(), CommonLocalizationTags.COMPARE.toString()));
				break;
			case CUSTOM_CORPUS:
				results.setPanelTitle(getLocalizedString(LocalizationTags.CUSTOM_CORPUS_TITLE.toString()));
				break;
			case WEB_SEARCH:
				results.setPanelTitle("'" + ((WebSearchProcessData) data).query + "'");
				break;
			}

			results.setPanelSubtitle("");
			if (data.complete) {
				if ((rankData != null && rankData.getRankedDocuments().isEmpty()) ||
						data.parsedDocs.isEmpty()) {
					results.setPanelSubtitle(getLocalizedString(LocalizationTags.NO_RESULTS_FOR_FILTER.toString()));
				}
			}
		}

		void refreshResults() {
			results.clearCompleted();
			RankableDocument sel = null;
			if (lastSelection != null && preview.isVisible()) {
				if (lastSelection.getType() == Type.COMPLETED)
					sel = ((CompletedResultItemImpl) lastSelection).getDoc();
			}

			// add from ranked data, if available
			int i = 1;
			if (rankData != null) {
				for (RankableDocument itr : rankData.getRankedDocuments()) {
					CompletedResultItem addend = new CompletedResultItemImpl(itr, i, itr.getRank(), data.type != OperationType.COMPARE);
					if (itr == sel)
						lastSelection = addend;
					results.addCompleted(addend);
					i++;
				}
			} else for (RankableDocument itr : data.parsedDocs) {
				CompletedResultItem addend = new CompletedResultItemImpl(itr, i, itr.getRank(), data.type != OperationType.COMPARE);
				if (itr == sel)
					lastSelection = addend;
				results.addCompleted(addend);
				i++;
			}

			refreshResultsTitle();
			if (sel != null)
				results.setSelection(lastSelection);
		}

		void refreshLocalization(Language l) {
			refreshResultsTitle();
		}

		void visualize() {
			preview.hide();
			visualizer.visualize(new VisualizeInput());
			visualizer.show();
		}

		void applySettings(ConstructionSettingsProfile profile) {
			if (profile.getLanguage() == data.lang) {
				settings.applySettingsProfile(importedSettings, false);
				notification.notify(getLocalizedString(LocalizationTags.APPLIED_IMPORTED_SETTINGS.toString()));
			}
		}
	}

	private final class ServerMessagePoller {
		private static final int TIMEOUT_MS = 10 * 60 * 1000;

		Timer timeout;
		Runnable timeoutHandler;
		boolean running;

		ServerMessagePoller() {
			timeout = new Timer() {
				@Override
				public void run() {
					if (timeoutHandler != null)
						timeoutHandler.run();

					if (running)
						endPolling();
				}
			};

			timeoutHandler = null;
			running = false;
		}

		void beginPolling(AbstractMessageReceiver.MessageHandler messageHandler, Runnable timeoutHandler, boolean timeoutEnabled) {
			if (running)
				throw new RuntimeException("Message poller is busy");

			running = true;
			this.timeoutHandler = timeoutHandler;
			messagePipe.setHandler(messageHandler);
			messagePipe.open(token);

			if (timeoutEnabled)
				timeout.schedule(TIMEOUT_MS);
		}

		void endPolling() {
			if (!running)
				throw new RuntimeException("Message poller is inactive");

			running = false;
			this.timeoutHandler = null;

			if (messagePipe.isOpen())
				messagePipe.close();

			timeout.cancel();
		}

		boolean isBusy() { return running; }
	}

	private final class TransientParsingProcessManager {
		final class InProgressData {
			DocumentDTO inProgressItem;
			InProgressResultItem resultItem;

			InProgressData(DocumentDTO dto, InProgressResultItem itm) {
				inProgressItem = dto;
				resultItem = itm;
			}
		}

		final class ServerMessageHandler implements AbstractMessageReceiver.MessageHandler {
			void addInProgressItem(InProgressResultItem item, DocumentDTO dto) {
				if (inProgress.containsKey(dto.getLinkingId())) {
					ClientLogger.get().error("DTO hash collision!");
					return;
				}

				inProgress.put(dto.getLinkingId(), new InProgressData(dto, item));
				results.addInProgress(item);
				numReceivedInprogress++;
			}

			void addParsedDoc(RankableDocument doc) {
				// remove the corresponding inprogress item
				if (!inProgress.containsKey(doc.getLinkingId()))
					ClientLogger.get().error("DTO hash miss!");
				else {
					InProgressResultItem item = inProgress.get(doc.getLinkingId()).resultItem;
					inProgress.remove(doc.getLinkingId());
					results.removeInProgress(item);
				}

				data.parsedDocs.add(doc);
				parseHandler.handle(data, doc);
			}

			void finalizeProcess() {
				boolean success = true;

				if (inProgress.isEmpty() == false)
					ClientLogger.get().error("Job completed with delinquent in-progress items. Count: " + inProgress.size());

				// check result counts
				if (numReceivedInprogress == 0) {
					if (data.type == OperationType.WEB_SEARCH)
						notification.notify(getLocalizedString(LocalizationTags.NO_SEARCH_RESULTS.toString()));
					else
						notification.notify(getLocalizedString(LocalizationTags.NO_PARSED_DOCS.toString()));

					if (data.parsedDocs.isEmpty() == false)
						ClientLogger.get().error("Eh? We received no in-progress items but have parsed docs regardless?!");

					success = false;
				} else if (data.parsedDocs.isEmpty()) {
					notification.notify(getLocalizedString(LocalizationTags.NO_PARSED_DOCS.toString()));
					success = false;
				} else {
					int expectedResults = 0;
					switch (data.type) {
					case WEB_SEARCH:
						expectedResults = ((WebSearchProcessData) data).numResults;
						break;
					case CUSTOM_CORPUS:
						expectedResults = ((CorpusUploadProcessData) data).uploadedDocs.size();
						break;
					}

					if (data.parsedDocs.size() < expectedResults)
						notification.notify(getLocalizedString(LocalizationTags.MISSING_DOCS.toString()));
				}

				reset(success);
			}

			@Override
			public void handle(ServerMessage msg) {
				if (data.type == OperationType.WEB_SEARCH && msg.getType() != ServerMessage.Type.SEARCH_CRAWL_PARSE)
					throw new RuntimeException("Invalid message type for web search operation: " + msg.getType());
				else if (data.type == OperationType.CUSTOM_CORPUS && msg.getType() != ServerMessage.Type.CUSTOM_CORPUS)
					throw new RuntimeException("Invalid message type for custom corpus operation: " + msg.getType());

				switch (msg.getType()) {
				case SEARCH_CRAWL_PARSE: {
					WebSearchProcessData websearchData = (WebSearchProcessData) data;
					ServerMessage.SearchCrawlParse serverdata = msg.getSearchCrawlParse();
					switch (serverdata.getType()) {
					case CRAWL_COMPLETE:
						websearchData.searchResults.add(serverdata.getCrawled());
						addInProgressItem(new InProgressResultItemImpl(serverdata.getCrawled()), serverdata.getCrawled());
						break;
					case PARSE_COMPLETE:
						addParsedDoc(serverdata.getParsed());
						break;
					case JOB_COMPLETE:
						finalizeProcess();
						break;
					default:
						ClientLogger.get().error("Unknown message from server: " + msg.getType());
					}

					break;
				}

				case CUSTOM_CORPUS: {
					CorpusUploadProcessData uploadData = (CorpusUploadProcessData) data;
					ServerMessage.CustomCorpus serverdata = msg.getCustomCorpus();
					switch (serverdata.getType()) {
					case UPLOAD_COMPLETE:
						for (UploadedDocument itr : serverdata.getUploaded()) {
							uploadData.uploadedDocs.add(itr);
							addInProgressItem(new InProgressResultItemImpl(itr), itr);
						}

						break;
					case PARSE_COMPLETE:
						addParsedDoc(serverdata.getParsed());
						break;
					case JOB_COMPLETE:
						finalizeProcess();
						break;
					default:
						ClientLogger.get().error("Unknown message from server: " + msg.getType());
					}

					break;
				}

				case ERROR:
					break;
				default:
					break;

				}
			}
		}

		ProcessData data;
		Map<Integer, InProgressData> inProgress;            // DTO ids -> result items
		int numReceivedInprogress;
		SuccessfulParseEventHandler parseHandler;
		ProcessCompletionEventHandler completionHandler;

		TransientParsingProcessManager() {
			data = null;
			inProgress = new HashMap<>();
			numReceivedInprogress = 0;

			parseHandler = null;
			completionHandler = null;
		}

		void begin(ProcessData d, ProcessCompletionEventHandler completion, SuccessfulParseEventHandler parse) {
			if (isBusy())
				throw new RuntimeException("Process manager is busy");
			else if (d.complete)
				throw new RuntimeException("Process is already complete");
			else if (!OperationType.isTransient(d.type))
				throw new RuntimeException("Process " + d.type + " is not transient");

			// init and set up message pipeline
			data = d;
			parseHandler = parse;
			completionHandler = completion;

			results.clearCompleted();
			results.clearInProgress();
			results.setPanelTitle("");
			results.setPanelSubtitle("");
			results.show();

			presenter.showDefaultPane(false);
			presenter.showCancelPane(true);
			presenter.showProgressBar(true);
			settings.hide();
			preview.hide();

			// no timeout for the upload op as the corpus uploader manages its own state (that needs cleanup)
			messagePoller.beginPolling(new ServerMessageHandler(), () -> {
				if (data != null) {
					cancel();
					notification.notify(getLocalizedString(LocalizationTags.OP_TIMEDOUT.toString()), 5000);
					ClientLogger.get().error("The current operation timed-out!");
				}
			}, data.type == OperationType.WEB_SEARCH);

			if (data.type == OperationType.WEB_SEARCH)
				results.setPanelTitle("'" + ((WebSearchProcessData) data).query + "'");
			else
				results.setPanelTitle(getLocalizedString(LocalizationTags.CUSTOM_CORPUS_TITLE.toString()));
		}

		void reset(boolean success) {
			if (success) {
				results.clearInProgress();
				presenter.showCancelPane(false);
				presenter.showProgressBar(false);

				if (!GwtUtil.isSmallScreen())
					settings.show();

				// rerank the parsed docs as their original ranks can be discontinuous
				// sort the parsed docs by their original rank first and then rerank them
				data.parsedDocs.sort(Comparator.comparingInt(RankableDocument::getRank));
				int i = 1;
				for (RankableDocument itr : data.parsedDocs) {
					itr.setRank(i);
					i++;
				}

				notification.notify(getLocalizedString(LocalizationTags.ANALYSIS_COMPLETE.toString()));
			} else {
				results.clearCompleted();
				results.clearInProgress();
				results.setPanelTitle("");
				results.setPanelSubtitle("");
				results.hide();

				presenter.showLoaderOverlay(false);
				presenter.showProgressBar(false);
				presenter.showCancelPane(false);
				presenter.showDefaultPane(true);
				settings.hide();
				preview.hide();
				upload.hide();
			}

			messagePoller.endPolling();
			data.complete = true;
			data.invalid = success == false;
			completionHandler.handle(data, success);

			data = null;
			inProgress.clear();
			numReceivedInprogress = 0;
			parseHandler = null;
			completionHandler = null;
		}

		void cancel() {
			if (!isBusy())
				return;

			service.cancelCurrentOperation(token, FuncCallback.get(e -> {}));
			reset(false);
		}

		boolean isBusy() {
			return messagePoller.isBusy();
		}
	}

	private final class ProcessHistory {
		final LinkedList<ProcessData> stack;

		ProcessHistory() {
			stack = new LinkedList<>();
		}

		ProcessData poll() {
			if (stack.isEmpty())
				return null;
			else
				return stack.getFirst();
		}

		void push(ProcessData d) {
			stack.push(d);
		}

		ProcessData pop() {
			return stack.pop();
		}

		List<ProcessData> asList() {
			List<ProcessData> out = new ArrayList<>();
			for (ProcessData itr : stack) {
				if (OperationType.isTransient(itr.getType()) == false)
					continue;
				else if (itr.complete == false || itr.invalid)
					continue;

				out.add(itr);
			}
			return out;
		}

		boolean isEmpty() {
			return stack.isEmpty();
		}
	}

	private final class SettingsUrlExporter implements SettingsExportService {
		private final String PARAM_SIGIL = "encodedSettings";
		private final String PARAM_LANGUAGE = "lang";
		private final String PARAM_DOCLEVEL_A = "docLevelA";
		private final String PARAM_DOCLEVEL_B = "docLevelB";
		private final String PARAM_DOCLEVEL_C = "docLevelC";
		private final String PARAM_KEYWORDS = "keywords";
		private final String PARAM_DOCLENGTH = "docLength";

		private final String PARAM_VAL_SEPARATOR = "_";

		private String generateParamVal(boolean enabled, int weight) {
			return enabled + PARAM_VAL_SEPARATOR + weight;
		}

		private boolean isWeightEnabled(String paramVal) {
			int idx = paramVal.indexOf(PARAM_VAL_SEPARATOR);
			if (idx == -1)
				return false;
			else
				return paramVal.substring(0, idx).equalsIgnoreCase("true") ? true : false;
		}

		private int getWeight(String paramVal) {
			int idx = paramVal.indexOf(PARAM_VAL_SEPARATOR);
			if (idx == -1)
				return 0;
			else
				return Integer.parseInt(paramVal.substring(idx + 1, paramVal.length()));
		}

		private String getConstructionTag(GrammaticalConstruction gram) {
			return gram.getID();
		}

		private void buildUrl(StringBuilder sb, String param, String value) {
			sb.append(param).append("=").append(value).append("&");
		}

		@Override
		public ConstructionSettingsProfile importSettings() {
			ConstructionSettingsProfileImpl out = null;

			// check for the export sigil
			if (Window.Location.getParameter(PARAM_SIGIL) != null) {
				String ls = Window.Location.getParameter(PARAM_LANGUAGE);
				if (ls != null) {
					Language l = Language.fromString(ls);
					out = new ConstructionSettingsProfileImpl();

					out.setLanguage(l);

					String lvla = Window.Location.getParameter(PARAM_DOCLEVEL_A),
							lvlb = Window.Location.getParameter(PARAM_DOCLEVEL_B),
							lvlc = Window.Location.getParameter(PARAM_DOCLEVEL_C),
							kw = Window.Location.getParameter(PARAM_KEYWORDS),
							docl = Window.Location.getParameter(PARAM_DOCLENGTH);

					if (lvla != null)
						out.setDocLevelEnabled(DocumentReadabilityLevel.LEVEL_A, lvla.equalsIgnoreCase("true") ? true : false);

					if (lvlb != null)
						out.setDocLevelEnabled(DocumentReadabilityLevel.LEVEL_B, lvlb.equalsIgnoreCase("true") ? true : false);

					if (lvlc != null)
						out.setDocLevelEnabled(DocumentReadabilityLevel.LEVEL_C, lvlc.equalsIgnoreCase("true") ? true : false);

					if (kw != null) {
						boolean enabled = isWeightEnabled(kw);
						int weight = getWeight(kw);

						out.getKeywords().setEnabled(enabled);
						out.getKeywords().setWeight(weight);
					}

					if (docl != null)
						out.setDocLengthWeight(Integer.parseInt(docl));

					// run through all of the lang's grams
					for (GrammaticalConstruction itr : GrammaticalConstruction.getForLanguage(l)) {
						String g = Window.Location.getParameter(getConstructionTag(itr));
						if (g != null)
							out.setGramData(itr, isWeightEnabled(g), getWeight(g));
					}
				}
			}

			return out;
		}

		@Override
		public String exportSettings(ConstructionSettingsProfile settings) {
			StringBuilder sb = new StringBuilder();

			// append the base URL
			sb.append(Window.Location.getHost())
					.append(Window.Location.getPath())
					.append("?");

			// append sigil, language, doc levels and keyword settings
			buildUrl(sb, PARAM_SIGIL, "1");

			buildUrl(sb, PARAM_LANGUAGE, settings.getLanguage().toString());
			buildUrl(sb, PARAM_DOCLENGTH, "" + settings.getDocLengthWeight());
			buildUrl(sb, PARAM_DOCLEVEL_A, "" + settings.isDocLevelEnabled(DocumentReadabilityLevel.LEVEL_A));
			buildUrl(sb, PARAM_DOCLEVEL_B, "" + settings.isDocLevelEnabled(DocumentReadabilityLevel.LEVEL_B));
			buildUrl(sb, PARAM_DOCLEVEL_C, "" + settings.isDocLevelEnabled(DocumentReadabilityLevel.LEVEL_C));
			buildUrl(sb, PARAM_KEYWORDS, generateParamVal(settings.isKeywordsEnabled(), settings.getKeywordsWeight()));

			// append all of the language's gram constructions that have non-default values (either disabled or have non-zero weights)
			for (GrammaticalConstruction itr : GrammaticalConstruction.getForLanguage(settings.getLanguage())) {
				if (settings.hasConstruction(itr)) {
					boolean enabled = settings.isConstructionEnabled(itr);
					int weight = settings.getConstructionWeight(itr);

					if (enabled == false || weight != 0)
						buildUrl(sb, getConstructionTag(itr), generateParamVal(enabled, weight));
				}
			}

			// remove the trailing ampersand and encode the URL
			String out = sb.toString();
			return URL.encode(out.substring(0, out.length() - 1));
		}

	}

	private final class WebSearchCooldownTimer {
		private static final int COOLDOWN_MS = 5 * 60 * 1000;
		private static final int MAX_QUERIES = 15;                // no of allowed queries before the cooldown is triggered

		private final Timer cooldownTimer;
		private int elapsedQueries;
		private Duration lastResetTimestamp;

		public WebSearchCooldownTimer() {
			cooldownTimer = new Timer() {
				@Override
				public void run() {
					// reset elapsed queries
					elapsedQueries = 0;
					lastResetTimestamp = new Duration();
				}
			};

			elapsedQueries = 0;
			lastResetTimestamp = new Duration();
		}

		boolean tryBeginOperation() {
			if (elapsedQueries >= MAX_QUERIES)
				return false;

			elapsedQueries++;
			return true;
		}

		void start() {
			cooldownTimer.scheduleRepeating(COOLDOWN_MS);
		}

		void stop() {
			cooldownTimer.cancel();
		}

		long getNextResetTime() {
			long delta = COOLDOWN_MS - lastResetTimestamp.elapsedMillis();
			return delta > 0 ? delta : 0;
		}
	}


	private AuthToken token;
	private AbstractWebRankerPresenter presenter;
	private final AbstractDocumentRanker ranker;
	private final AbstractDocumentAnnotator annotator;
	private final AbstractMessageReceiver messagePipe;
	private final SettingsExportService exporter;
	private AbstractRankerSettingsPane settings;
	private AbstractDocumentResultsPane results;
	private AbstractDocumentPreviewPane preview;
	private WebSearchService search;
	private UserPromptService prompt;
	private NotificationService notification;
	private CorpusUploadService upload;
	private CustomKeywordService keywords;
	private VisualizerService visualizer;
	private OperationCancelService cancel;
	private SettingsUrlExporterView urlExport;
	private DocumentCompareService comparer;
	private HistoryViewerService history;
	private QuestionGeneratorPreviewService questgenpreview;
	private final WebRankerServiceAsync service;
	private final ServerMessagePoller messagePoller;
	private final RankPreviewModule rankPreviewModule;
	private final TransientParsingProcessManager transientParsingProcessManager;
	private final ProcessHistory processHistory;
	private ConstructionSettingsProfile importedSettings;
	private final WebSearchCooldownTimer searchCooldown;
	private boolean rerankFlag;

	private final GenericEventSource<BeginOperation> eventBeginProc;
	private final GenericEventSource<EndOperation> eventEndProc;

	public WebRankerCore(AbstractDocumentRanker r, AbstractDocumentAnnotator a, AbstractMessageReceiver m) {
		token = null;
		presenter = null;

		ranker = r;
		annotator = a;
		messagePipe = m;
		exporter = new SettingsUrlExporter();

		settings = null;
		results = null;
		preview = null;
		prompt = null;
		notification = null;
		search = null;
		upload = null;
		keywords = null;
		visualizer = null;
		cancel = null;
		urlExport = null;
		comparer = null;
		history = null;

		service = WebRankerServiceAsync.Util.getInstance();
		messagePoller = new ServerMessagePoller();
		rankPreviewModule = new RankPreviewModule();
		transientParsingProcessManager = new TransientParsingProcessManager();
		processHistory = new ProcessHistory();
		searchCooldown = new WebSearchCooldownTimer();

		importedSettings = null;
		rerankFlag = false;

		eventBeginProc = new GenericEventSource<>();
		eventEndProc = new GenericEventSource<>();
	}

	private void bindToPresenter(AbstractWebRankerPresenter presenter) {
		settings = presenter.getRankerSettingsPane();
		results = presenter.getDocumentResultsPane();
		preview = presenter.getDocumentPreviewPane();
		prompt = presenter.getPromptService();
		notification = presenter.getNotificationService();
		search = presenter.getWebSearchService();
		upload = presenter.getCorpusUploadService();
		keywords = presenter.getCustomKeywordsService();
		visualizer = presenter.getVisualizerService();
		cancel = presenter.getCancelService();
		urlExport = presenter.getSettingsUrlExporterView();
		comparer = presenter.getDocumentCompareService();
		history = presenter.getHistoryViewerService();
		questgenpreview = presenter.getQuestionGeneratorPreviewService();

		settings.setExportSettingsHandler(() -> {
			String url = exporter.exportSettings(settings.generateSettingsProfile());
			urlExport.show(url);
		});
		settings.setVisualizeHandler(() -> {
			if (transientParsingProcessManager.isBusy())
				notification.notify(getLocalizedString(DefaultLocalizationProviders.COMMON.toString(), CommonLocalizationTags.WAIT_TILL_COMPLETION.toString()));
			else
				rankPreviewModule.visualize();
		});

		settings.setSettingsChangedHandler(this::onSettingsChanged);
		settings.setResetAllHandler(this::onSettingsReset);
		search.setSearchHandler(this::onWebSearch);
		preview.setShowHideEventHandler(v -> {
			if (!v)
				results.clearSelection();
		});
		preview.setGenerateQuestionsHandler(this::onGenerateQuestions);
		upload.setUploadBeginHandler(this::onUploadBegin);
		upload.setUploadCompleteHandler(this::onUploadComplete);
		visualizer.setApplyFilterHandler(d -> {
			for (RankableDocument itr : d)
				rankPreviewModule.addToFilter(itr);

			onSettingsChanged();
		});
		visualizer.setResetFilterHandler(() -> {
			rankPreviewModule.resetFilter();

			// reset weights
			settings.getSliderBundle().resetState(false);
			onSettingsChanged();
		});
		cancel.setCancelHandler(this::onCancelOp);

		comparer.setCompareHandler(this::onCompare);
		comparer.bindToWebRankerCore(this);
		history.setFetchAnalysesHandler(processHistory::asList);
		history.setRestoreAnalysisHandler(this::onRestoreProcess);
		questgenpreview.setGenerateHandler(this::onGenerateQuestions);
		questgenpreview.setInterruptHandler(() -> {
			if (!messagePoller.isBusy())
				return;

			messagePoller.endPolling();
			service.cancelCurrentOperation(token, FuncCallback.get(e -> {}));
		});

		LocalizationEngine.get().addLanguageChangeHandler(l -> rankPreviewModule.refreshLocalization(l.newLang));

		// reset the settings pane
		rankPreviewModule.rerank();

		searchCooldown.start();
	}

	private void onRestoreProcess(WebRankerAnalysis p) {
		if (p instanceof ProcessData == false)
			throw new RuntimeException("Invalid analysis process");

		doProcessHousekeeping();

		// the process ought to be on the stack, so just update the ranker
		ProcessData proc = (ProcessData) p;
		rankPreviewModule.set(proc);
		rankPreviewModule.rerank();
		rankPreviewModule.refreshResults();
		preview.hide();

		eventEndProc.raiseEvent(new EndOperation(OperationType.RESTORE, proc.lang, true));
	}

	private void onCompare(Language lang, List<RankableDocument> docs) {
		if (docs.isEmpty())
			return;
		else if (transientParsingProcessManager.isBusy()) {
			notification.notify(getLocalizedString(DefaultLocalizationProviders.COMMON.toString(), CommonLocalizationTags.WAIT_TILL_COMPLETION.toString()));
			return;
		}

		doProcessHousekeeping();

		CompareProcessData proc = new CompareProcessData(lang, docs);
		processHistory.push(proc);

		rankPreviewModule.set(proc);
		rankPreviewModule.rerank();
		rankPreviewModule.refreshResults();
		preview.hide();

		eventEndProc.raiseEvent(new EndOperation(OperationType.COMPARE, lang, true));
	}

	private void doProcessHousekeeping() {
		if (isOperationInProgress())
			throw new RuntimeException("Invalid atomic operation invokation");

		// cleanup the previous non-transient processes, if any
		ProcessData proc = processHistory.poll();
		if (proc != null && OperationType.isTransient(proc.type) == false) {
			processHistory.pop();
		}
	}

	private void doDeferredReranking() {
		if (rerankFlag) {
			rerankFlag = false;
			rankPreviewModule.rerank();
			rankPreviewModule.refreshResults();
		}
	}

	private void onSettingsChanged() {
		// this handler can be spammed under certain circumstances
		// to prevent the rerank calls from accumulating, defer the execution until the browser event loop returns
		rerankFlag = true;
		Scheduler.get().scheduleDeferred(this::doDeferredReranking);
	}

	private void onSettingsReset() {
		// clear filtered documents
		rankPreviewModule.resetFilter();
	}

	private void onTransientProcessBegin(ProcessData d) {
		rankPreviewModule.set(d);
		eventBeginProc.raiseEvent(new BeginOperation(d.type, d.lang));
	}

	private void onTransientProcessEnd(ProcessData d, boolean success) {
		if (success) {
			// apply custom settings, if any, rerank and display
			if (importedSettings != null)
				rankPreviewModule.applySettings(importedSettings);

			rankPreviewModule.rerank();
			rankPreviewModule.refreshResults();
		} else {
			processHistory.pop();
			rankPreviewModule.reset();
			rankPreviewModule.rerank();
		}

		eventEndProc.raiseEvent(new EndOperation(d.type, d.lang, success));
	}

	private void onWebSearch(Language lang, String query, int numResults) {
		if (query.length() == 0) {
			notification.notify(getLocalizedString(LocalizationTags.NO_SEARCH_RESULTS.toString()));
			return;
		} else if (searchCooldown.tryBeginOperation() == false) {
			notification.notify(getLocalizedString(LocalizationTags.SEARCH_COOLDOWN.toString()));
			return;
		}

		doProcessHousekeeping();

		WebSearchProcessData proc = new WebSearchProcessData(lang, query, numResults);
		proc.setKeywords(keywords.getCustomKeywords());

		presenter.showLoaderOverlay(true);
		service.beginWebSearch(token, lang, query, numResults, new ArrayList<>(keywords.getCustomKeywords()),
				FuncCallback.get(e -> {
					// ### hide the loader overlay before the process starts
					// ### otherwise, the progress bar doesn't show
					presenter.showLoaderOverlay(false);
					processHistory.push(proc);
					transientParsingProcessManager.begin(proc,
							(p, s) -> onTransientProcessEnd(p, s),
							(p, d) -> {
								// refresh the parsed results
								if (p.parsedDocs.size() != ((WebSearchProcessData) p).numResults) {
									rankPreviewModule.rerank();
									rankPreviewModule.refreshResults();
								}
							});

					onTransientProcessBegin(proc);
				}, e -> {
					ClientLogger.get().error(e, "Couldn't begin web search operation");
					notification.notify(getLocalizedString(LocalizationTags.SERVER_ERROR.toString()));
					presenter.showLoaderOverlay(false);

					if (e instanceof InvalidAuthTokenException)
						ClientEndPoint.get().fatalServerError();
				}));
	}

	private void onUploadBegin(Language corpusLang) {
		// begin operation and wait for the server
		doProcessHousekeeping();

		CorpusUploadProcessData proc = new CorpusUploadProcessData(corpusLang);
		proc.setKeywords(keywords.getCustomKeywords());

		presenter.showLoaderOverlay(true);
		service.beginCorpusUpload(token, corpusLang, new ArrayList<>(keywords.getCustomKeywords()),
				FuncCallback.get(e -> {
					processHistory.push(proc);
					presenter.showLoaderOverlay(false);
				}, e -> {
					ClientLogger.get().error(e, "Couldn't begin corpus upload operation");
					notification.notify(getLocalizedString(LocalizationTags.SERVER_ERROR.toString()));
					presenter.showLoaderOverlay(false);

					if (e instanceof InvalidAuthTokenException)
						ClientEndPoint.get().fatalServerError();
				}));


		ClientLogger.get().info("Upload operation has begun...");
	}

	private void onUploadComplete(int numUploaded, boolean success) {
		// signal the end of the upload operation
		ProcessData proc = processHistory.poll();
		if (proc instanceof CorpusUploadProcessData == false)
			throw new RuntimeException("Invalid process data");

		if (success) {
			transientParsingProcessManager.begin(proc,
					(p, s) -> onTransientProcessEnd(p, s),
					(p, d) -> {
						// refresh the parsed results
						if (p.parsedDocs.size() != numUploaded) {
							rankPreviewModule.rerank();
							rankPreviewModule.refreshResults();
						}
					});

			onTransientProcessBegin(proc);
			service.endCorpusUpload(token, success, FuncCallback.get(e -> {}));
			ClientLogger.get().info("Upload operation has ended - Waiting for the server");
		} else {
			processHistory.pop();
			ClientLogger.get().info("Upload operation was cancelled");
		}
	}

	private boolean onGenerateQuestions(RankableDocument doc) {
		if (messagePoller.isBusy()) {
			notification.notify(getLocalizedString(DefaultLocalizationProviders.COMMON.toString(), CommonLocalizationTags.WAIT_TILL_COMPLETION.toString()));
			return false;
		} else if (Language.ENGLISH != doc.getLanguage()) {
			notification.notify(getLocalizedString(DefaultLocalizationProviders.COMMON.toString(), CommonLocalizationTags.FEATURE_NOT_SUPPORTED.toString()));
			return false;
		}

		if (doc instanceof CompareProcessData.ComparisonWrapper)
			doc = ((CompareProcessData.ComparisonWrapper) doc).doc;

		service.generateQuestions(token, doc, 5,
				FuncCallback.get(e -> messagePoller.beginPolling(msg -> {
							if (msg.getType() != ServerMessage.Type.GENERATE_QUESTIONS)
								throw new RuntimeException("Invalid message type for generate questions operation: " + msg.getType());

							switch (msg.getGenerateQuestions().getType()) {
							case SENTENCE_SELECTION_COMPLETE:
								break;
							case JOB_COMPLETE:
								messagePoller.endPolling();
								questgenpreview.display(msg.getGenerateQuestions().getGeneratedQuestions());
								break;
							}
						}, () -> {
							notification.notify(getLocalizedString(LocalizationTags.OP_TIMEDOUT.toString()), 5000);
						}, true),
						e -> {
							ClientLogger.get().error(e, "Couldn't begin question generation operation");
							notification.notify(getLocalizedString(LocalizationTags.SERVER_ERROR.toString()));
							questgenpreview.display(new ArrayList<>());

							if (e instanceof InvalidAuthTokenException)
								ClientEndPoint.get().fatalServerError();
						}));
		return true;
	}

	private void onCancelOp() {
		if (isOperationInProgress())
			cancelCurrentOperation();
	}

	private String getLocalizedString(String provider, String tag) {
		Language lang = LocalizationEngine.get().getLanguage();
		return LocalizationStringTable.get().getLocalizedString(provider, tag, lang);
	}

	private String getLocalizedString(String tag) {
		return getLocalizedString(DefaultLocalizationProviders.WEBRANKERCORE.toString(), tag);
	}

	@Override
	public void init(AuthToken token, AbstractWebRankerPresenter presenter) {
		if (this.token != null)
			throw new RuntimeException("Token already set");

		this.token = token;
		this.presenter = presenter;

		bindToPresenter(this.presenter);

		// load custom settings from url
		importedSettings = exporter.importSettings();
		if (importedSettings != null)
			notification.notify(getLocalizedString(LocalizationTags.IMPORTED_SETINGS.toString()));
	}

	@Override
	public WebRankerAnalysis getCurrentOperation() {
		return processHistory.isEmpty() ? null : processHistory.poll();
	}

	@Override
	public void cancelCurrentOperation() {
		transientParsingProcessManager.cancel();
	}

	@Override
	public boolean isOperationInProgress() {
		return transientParsingProcessManager.isBusy();
	}

	@Override
	public void addBeginOperationHandler(EventHandler<BeginOperation> handler) {
		eventBeginProc.addHandler(handler);
	}

	@Override
	public void addEndOperationHandler(EventHandler<EndOperation> handler) {
		eventEndProc.addHandler(handler);
	}
}