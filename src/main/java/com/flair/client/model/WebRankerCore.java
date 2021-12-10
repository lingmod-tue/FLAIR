package com.flair.client.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.flair.client.ClientEndPoint;
import com.flair.client.interop.messaging.ClientMessageChannel;
import com.flair.client.interop.messaging.MessagePoller;
import com.flair.client.localization.CommonLocalizationTags;
import com.flair.client.localization.DefaultLocalizationProviders;
import com.flair.client.localization.GrammaticalConstructionLocalizationProvider;
import com.flair.client.localization.LocalizationEngine;
import com.flair.client.localization.LocalizationStringTable;
import com.flair.client.model.interfaces.AbstractDocumentAnnotator;
import com.flair.client.model.interfaces.AbstractDocumentRanker;
import com.flair.client.model.interfaces.AbstractWebRankerCore;
import com.flair.client.model.interfaces.ConstructionSettingsProfile;
import com.flair.client.model.interfaces.DocumentAnnotatorInput;
import com.flair.client.model.interfaces.DocumentAnnotatorOutput;
import com.flair.client.model.interfaces.DocumentRankerInput;
import com.flair.client.model.interfaces.DocumentRankerOutput;
import com.flair.client.model.interfaces.SettingsExportService;
import com.flair.client.model.interfaces.WebRankerAnalysis;
import com.flair.client.presentation.ToastNotification;
import com.flair.client.presentation.interfaces.AbstractDocumentPreviewPane;
import com.flair.client.presentation.interfaces.AbstractDocumentResultsPane;
import com.flair.client.presentation.interfaces.AbstractRankerSettingsPane;
import com.flair.client.presentation.interfaces.AbstractResultItem;
import com.flair.client.presentation.interfaces.AbstractResultItem.Type;
import com.flair.client.presentation.interfaces.AbstractWebRankerPresenter;
import com.flair.client.presentation.interfaces.CompletedResultItem;
import com.flair.client.presentation.interfaces.CorpusUploadService;
import com.flair.client.presentation.interfaces.CustomKeywordService;
import com.flair.client.presentation.interfaces.DocumentCompareService;
import com.flair.client.presentation.interfaces.DocumentPreviewPaneInput;
import com.flair.client.presentation.interfaces.ExerciseGenerationService;
import com.flair.client.presentation.interfaces.HistoryViewerService;
import com.flair.client.presentation.interfaces.InProgressResultItem;
import com.flair.client.presentation.interfaces.OperationCancelService;
import com.flair.client.presentation.interfaces.QuestionGeneratorPreviewService;
import com.flair.client.presentation.interfaces.SettingsUrlExporterView;
import com.flair.client.presentation.interfaces.UserPromptService;
import com.flair.client.presentation.interfaces.VisualizerService;
import com.flair.client.presentation.interfaces.WebSearchService;
import com.flair.client.presentation.widgets.DocumentPreviewPane;
import com.flair.client.presentation.widgets.GenericWeightSlider;
import com.flair.client.presentation.widgets.GrammaticalConstructionWeightSlider;
import com.flair.client.presentation.widgets.LanguageSpecificConstructionSliderBundle;
import com.flair.client.utilities.ClientLogger;
import com.flair.client.utilities.GwtUtil;
import com.flair.shared.exceptions.InvalidClientIdentificationTokenException;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.IExerciseSettings;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.dtos.DocumentDTO;
import com.flair.shared.interop.dtos.RankableDocument;
import com.flair.shared.interop.dtos.RankableWebSearchResult;
import com.flair.shared.interop.dtos.UploadedDocument;
import com.flair.shared.interop.messaging.client.CmActiveOperationCancel;
import com.flair.shared.interop.messaging.client.CmCustomCorpusParseStart;
import com.flair.shared.interop.messaging.client.CmExGenStart;
import com.flair.shared.interop.messaging.client.CmQuestionGenEagerParse;
import com.flair.shared.interop.messaging.client.CmQuestionGenStart;
import com.flair.shared.interop.messaging.client.CmUpdateKeepAliveTimer;
import com.flair.shared.interop.messaging.client.CmWebSearchParseStart;
import com.flair.shared.interop.messaging.server.SmCustomCorpusEvent;
import com.flair.shared.interop.messaging.server.SmError;
import com.flair.shared.interop.messaging.server.SmExGenEvent;
import com.flair.shared.interop.messaging.server.SmQuestionGenEvent;
import com.flair.shared.interop.messaging.server.SmWebSearchParseEvent;
import com.flair.shared.parser.DocumentReadabilityLevel;
import com.flair.shared.utilities.GenericEventSource;
import com.flair.shared.utilities.GenericEventSource.EventHandler;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialToast;

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

    /**
     * Whether or not to use the question generator.
     */
    protected static final boolean USE_QUESTION_GENERATION = false;


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
                case URL:
                    if (hasUrl())
                        Window.open(getUrl(), "_blank", "");
                    else
                        rankPreviewModule.preview(this);
                case TITLE:

                    if (doc.getLanguage() == Language.ENGLISH && USE_QUESTION_GENERATION)
                        questgenpreview.show(doc, parentWidget);
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
            ToastNotification.fire(getLocalizedString(LocalizationTags.SELECTED_FOR_COMPARISON.toString()));
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

            RankerInput() {
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
                ToastNotification.fire(getLocalizedString(LocalizationTags.APPLIED_IMPORTED_SETTINGS.toString()));
            }
        }
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

        void onSmWebSearchParseEvent(SmWebSearchParseEvent msg) {
            if (data.type != OperationType.WEB_SEARCH)
                throw new RuntimeException("Unexpected web-search-parse event for operation " + data.type);

            WebSearchProcessData websearchData = (WebSearchProcessData) data;
            switch (msg.getEvent()) {
                case CRAWL_COMPLETE:
                    websearchData.searchResults.add(msg.getCrawlResult());
                    addInProgressItem(new InProgressResultItemImpl(msg.getCrawlResult()), msg.getCrawlResult());
                    break;
                case PARSE_COMPLETE:
                    addParsedDoc(msg.getParseResult());
                    break;
                case JOB_COMPLETE:
                    finalizeProcess();
                    break;
            }
        }

        void onSmCustomCorpusEvent(SmCustomCorpusEvent msg) {
            if (data.type != OperationType.CUSTOM_CORPUS)
                throw new RuntimeException("Unexpected custom corpus event for operation " + data.type);

            CorpusUploadProcessData uploadData = (CorpusUploadProcessData) data;
            switch (msg.getEvent()) {
                case UPLOAD_COMPLETE:
                    for (UploadedDocument itr : msg.getUploadResult()) {
                        uploadData.uploadedDocs.add(itr);
                        addInProgressItem(new InProgressResultItemImpl(itr), itr);
                    }
                    break;
                case PARSE_COMPLETE:
                    addParsedDoc(msg.getParseResult());
                    break;
                case JOB_COMPLETE:
                    finalizeProcess();
                    break;
            }
        }

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

            if (!inProgress.isEmpty())
                ClientLogger.get().error("Job completed with delinquent in-progress items. Count: " + inProgress.size());

            // check result counts
            if (numReceivedInprogress == 0) {
                if (data.type == OperationType.WEB_SEARCH)
                    ToastNotification.fire(getLocalizedString(LocalizationTags.NO_SEARCH_RESULTS.toString()));
                else
                    ToastNotification.fire(getLocalizedString(LocalizationTags.NO_PARSED_DOCS.toString()));

                if (!data.parsedDocs.isEmpty())
                    ClientLogger.get().error("Eh? We received no in-progress items but have parsed docs regardless?!");

                success = false;
            } else if (data.parsedDocs.isEmpty()) {
                ToastNotification.fire(getLocalizedString(LocalizationTags.NO_PARSED_DOCS.toString()));
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
                    ToastNotification.fire(getLocalizedString(LocalizationTags.MISSING_DOCS.toString()));
            }

            reset(success);
        }

        ProcessData data;
        Map<Integer, InProgressData> inProgress;            // DTO ids -> result items
        int numReceivedInprogress;
        SuccessfulParseEventHandler parseHandler;
        ProcessCompletionEventHandler completionHandler;
        MessagePoller poller;

        TransientParsingProcessManager() {
            data = null;
            inProgress = new HashMap<>();
            numReceivedInprogress = 0;

            parseHandler = null;
            completionHandler = null;

            poller = serverMessageChannel.messagePoller()
                    .interval(POLLING_INTERVAL)
                    .timeout(TIMEOUT_INTERVAL)
                    .onTimeout(() -> {
                        if (data != null) {
                            cancel();
                            ToastNotification.fire(getLocalizedString(LocalizationTags.OP_TIMEDOUT.toString()), 5000);
                            ClientLogger.get().error("The current operation timed-out!");
                        }
                    })
                    .onMessage(SmWebSearchParseEvent.class, this::onSmWebSearchParseEvent)
                    .onMessage(SmCustomCorpusEvent.class, this::onSmCustomCorpusEvent)
                    .onMessage(SmError.class, WebRankerCore.this::onSmError)
                    .build();
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

            poller.start();

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

                ToastNotification.fire(getLocalizedString(LocalizationTags.ANALYSIS_COMPLETE.toString()));
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

            poller.stop();
            data.complete = true;
            data.invalid = !success;
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

            serverMessageChannel.send(new CmActiveOperationCancel(), () -> {
            }, (e, m) -> {
            });
            reset(false);
        }

        boolean isBusy() {
            return poller.isRunning();
        }
    }

    private static final int POLLING_INTERVAL = 2;         // in seconds
    private static final int TIMEOUT_INTERVAL = 5 * 60;    // in seconds

    private final ClientMessageChannel serverMessageChannel;
    private AbstractWebRankerPresenter presenter;
    private final AbstractDocumentRanker ranker;
    private final AbstractDocumentAnnotator annotator;
    private final SettingsExportService exporter;
    private AbstractRankerSettingsPane settings;
    private AbstractDocumentResultsPane results;
    private AbstractDocumentPreviewPane preview;
    private WebSearchService search;
    private UserPromptService prompt;
    private CorpusUploadService upload;
    private ExerciseGenerationService exGen;
    private CustomKeywordService keywords;
    private VisualizerService visualizer;
    private OperationCancelService cancel;
    private SettingsUrlExporterView urlExport;
    private DocumentCompareService comparer;
    private HistoryViewerService history;
    private QuestionGeneratorPreviewService questgenpreview;
    private final RankPreviewModule rankPreviewModule;
    private final TransientParsingProcessManager transientParsingProcessManager;
    private final ProcessHistory processHistory;
    private ConstructionSettingsProfile importedSettings;
    private final WebSearchCooldownTimer searchCooldown;
    private final MessagePoller questionGenPoller;
    private final MessagePoller exGenPoller;
    private boolean rerankFlag;
    private Timer keepAliveTimer;

    private final GenericEventSource<BeginOperation> eventBeginProc;
    private final GenericEventSource<EndOperation> eventEndProc;

    public WebRankerCore(ClientMessageChannel m, AbstractDocumentRanker r, AbstractDocumentAnnotator a) {
        serverMessageChannel = m;
        presenter = null;

        ranker = r;
        annotator = a;
        exporter = new SettingsUrlExporter();

        settings = null;
        results = null;
        preview = null;
        prompt = null;
        search = null;
        upload = null;
        keywords = null;
        visualizer = null;
        cancel = null;
        urlExport = null;
        comparer = null;
        history = null;

        rankPreviewModule = new RankPreviewModule();
        transientParsingProcessManager = new TransientParsingProcessManager();
        processHistory = new ProcessHistory();
        searchCooldown = new WebSearchCooldownTimer();

        importedSettings = null;
        rerankFlag = false;
        questionGenPoller = serverMessageChannel.messagePoller()
                .interval(POLLING_INTERVAL)
                .timeout(TIMEOUT_INTERVAL)
                .onTimeout(() -> {
                    ToastNotification.fire(getLocalizedString(LocalizationTags.OP_TIMEDOUT.toString()), 5000);
                    questgenpreview.display(new ArrayList<>());
                })
                .onMessage(SmQuestionGenEvent.class, this::onSmQuestionGenEvent)
                .onMessage(SmError.class, WebRankerCore.this::onSmError)
                .build();
        
        exGenPoller = serverMessageChannel.messagePoller()
                .interval(POLLING_INTERVAL)
                .timeout(TIMEOUT_INTERVAL * 5)
                .onTimeout(() -> {
                	CmActiveOperationCancel msg = new CmActiveOperationCancel();
                    msg.setActiveOperationExpected(false);
                    serverMessageChannel.send(msg, () -> {}, (e, me) -> {});
             
                    ToastNotification.fire(getLocalizedString(LocalizationTags.OP_TIMEDOUT.toString()), 5000);
                    exGen.enableButton();
                })
                .onMessage(SmExGenEvent.class, this::onSmExGenEvent)
                .onMessage(SmError.class, WebRankerCore.this::onSmError)
                .build();

        eventBeginProc = new GenericEventSource<>();
        eventEndProc = new GenericEventSource<>();
        
        
        
        keepAliveTimer = new Timer() {
            @Override
            public void run() {
            	// send keep alive message every 5mins
				CmUpdateKeepAliveTimer msg = new CmUpdateKeepAliveTimer();
				serverMessageChannel.send(msg, () -> {}, (e, me) -> {});
            }
          };

          // Schedule the timer to run once in 5 seconds.
          keepAliveTimer.scheduleRepeating(5*60*1000);
    }

    private void bindToPresenter(AbstractWebRankerPresenter presenter) {
        settings = presenter.getRankerSettingsPane();
        results = presenter.getDocumentResultsPane();
        preview = presenter.getDocumentPreviewPane();
        prompt = presenter.getPromptService();
        search = presenter.getWebSearchService();
        upload = presenter.getCorpusUploadService();
        exGen = presenter.getExerciseGenerationService();
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
                ToastNotification.fire(getLocalizedString(DefaultLocalizationProviders.COMMON.toString(), CommonLocalizationTags.WAIT_TILL_COMPLETION.toString()));
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
        questgenpreview.setInterruptHandler(this::onInterruptQuestionGen);
        questgenpreview.setShowHandler(this::onQuestGenPreviewShow);
        exGen.setGenerateHandler(this::onGenerateExercises);
        exGen.setInterruptHandler(this::onInterruptExGen);

        LocalizationEngine.get().addLanguageChangeHandler(l -> rankPreviewModule.refreshLocalization(l.newLang));

        // reset the settings pane
        rankPreviewModule.rerank();

        searchCooldown.start();
    }

    private void onSmError(SmError msg) {
        ClientLogger.get().error(msg.getException(), "Unexpected server error. Exception: " + msg.getMessage());
        if (msg.isFatal())
            ClientEndPoint.get().fatalServerError();
    }

    private void onSmQuestionGenEvent(SmQuestionGenEvent msg) {
        switch (msg.getEvent()) {
            case JOB_COMPLETE:
                questionGenPoller.stop();
                questgenpreview.display(msg.getGenerationResult());
                break;
        }
    }
    
    private void onSmExGenEvent(SmExGenEvent msg) {
        exGenPoller.stop();  
	    exGen.provideForDownload(msg.getFile(), msg.getFileName(), msg.getPreviews());
    }

    private void onRestoreProcess(WebRankerAnalysis p) {
        if (!(p instanceof ProcessData))
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
            ToastNotification.fire(getLocalizedString(DefaultLocalizationProviders.COMMON.toString(), CommonLocalizationTags.WAIT_TILL_COMPLETION.toString()));
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
        if (proc != null && !OperationType.isTransient(proc.type)) {
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
            ToastNotification.fire(getLocalizedString(LocalizationTags.NO_SEARCH_RESULTS.toString()));
            return;
        } else if (!searchCooldown.tryBeginOperation()) {
            ToastNotification.fire(getLocalizedString(LocalizationTags.SEARCH_COOLDOWN.toString()));
            return;
        }

        doProcessHousekeeping();

        WebSearchProcessData proc = new WebSearchProcessData(lang, query, numResults);
        proc.setKeywords(keywords.getCustomKeywords());

        presenter.showLoaderOverlay(true);

        CmWebSearchParseStart msg = new CmWebSearchParseStart();
        msg.setQuery(query);
        msg.setLanguage(lang);
        msg.setNumResults(numResults);
        msg.setKeywords(new ArrayList<>(keywords.getCustomKeywords()));

        serverMessageChannel.send(msg,
                () -> {
                    // ### hide the loader overlay before the process starts
                    // ### otherwise, the progress bar doesn't show
                    presenter.showLoaderOverlay(false);
                    processHistory.push(proc);
                    transientParsingProcessManager.begin(proc,
                            this::onTransientProcessEnd,
                            (p, d) -> {
                                // refresh the parsed results
                                if (p.parsedDocs.size() != ((WebSearchProcessData) p).numResults) {
                                    rankPreviewModule.rerank();
                                    rankPreviewModule.refreshResults();
                                }
                            });

                    onTransientProcessBegin(proc);
                },
                (e, m) -> {
                    ClientLogger.get().error(e, "Couldn't begin web search operation");
                    ToastNotification.fire(getLocalizedString(LocalizationTags.SERVER_ERROR.toString()));
                    presenter.showLoaderOverlay(false);

                    if (e instanceof InvalidClientIdentificationTokenException)
                        ClientEndPoint.get().fatalServerError();
                });
    }

    private void onUploadComplete(Language corpusLang, int numUploaded) {
        // signal the end of the upload operation
        if (numUploaded == 0)
            return;

        doProcessHousekeeping();

        CorpusUploadProcessData proc = new CorpusUploadProcessData(corpusLang);
        proc.setKeywords(keywords.getCustomKeywords());

        presenter.showLoaderOverlay(true);

        CmCustomCorpusParseStart msg = new CmCustomCorpusParseStart();
        msg.setLanguage(corpusLang);
        msg.setNumUploadedFiles(numUploaded);
        msg.setKeywords(new ArrayList<>(keywords.getCustomKeywords()));

        serverMessageChannel.send(msg,
                () -> {
                    processHistory.push(proc);
                    presenter.showLoaderOverlay(false);
                    transientParsingProcessManager.begin(proc,
                            this::onTransientProcessEnd,
                            (p, d) -> {
                                // refresh the parsed results
                                if (p.parsedDocs.size() != numUploaded) {
                                    rankPreviewModule.rerank();
                                    rankPreviewModule.refreshResults();
                                }
                            });

                    onTransientProcessBegin(proc);
                },
                (e, m) -> {
                    ClientLogger.get().error(e, "Couldn't begin corpus upload operation");
                    ToastNotification.fire(getLocalizedString(LocalizationTags.SERVER_ERROR.toString()));
                    presenter.showLoaderOverlay(false);

                    if (e instanceof InvalidClientIdentificationTokenException)
                        ClientEndPoint.get().fatalServerError();
                });
    }

    private boolean onGenerateQuestions(RankableDocument doc, int numQuestions, boolean randomizeSelection) {
        if (questionGenPoller.isRunning()) {
            ToastNotification.fire(getLocalizedString(DefaultLocalizationProviders.COMMON.toString(), CommonLocalizationTags.WAIT_TILL_COMPLETION.toString()));
            return false;
        } else if (Language.ENGLISH != doc.getLanguage()) {
            ToastNotification.fire(getLocalizedString(DefaultLocalizationProviders.COMMON.toString(), CommonLocalizationTags.FEATURE_NOT_SUPPORTED.toString()));
            return false;
        }

        if (doc instanceof CompareProcessData.ComparisonWrapper)
            doc = ((CompareProcessData.ComparisonWrapper) doc).doc;

        CmQuestionGenStart msg = new CmQuestionGenStart();
        msg.setNumQuestions(numQuestions);
        msg.setRandomizeSelection(randomizeSelection);
        msg.setSourceDoc(doc);

        serverMessageChannel.send(msg,
                questionGenPoller::start,
                (e, m) -> {
                    ClientLogger.get().error(e, "Couldn't begin question generation operation");
                    ToastNotification.fire(getLocalizedString(LocalizationTags.SERVER_ERROR.toString()));
                    questgenpreview.display(new ArrayList<>());

                    if (e instanceof InvalidClientIdentificationTokenException)
                        ClientEndPoint.get().fatalServerError();
                });

        return true;
    }
    
    private void onInterruptQuestionGen() {
        if (questionGenPoller.isRunning())
            questionGenPoller.stop();

        CmActiveOperationCancel msg = new CmActiveOperationCancel();
        msg.setActiveOperationExpected(false);
        serverMessageChannel.send(msg, () -> {
        }, (e, m) -> {
        });
    }

    private void onQuestGenPreviewShow(RankableDocument doc) {
        if (isOperationInProgress())
            return;

        if (doc instanceof CompareProcessData.ComparisonWrapper)
            doc = ((CompareProcessData.ComparisonWrapper) doc).doc;

        CmQuestionGenEagerParse msg = new CmQuestionGenEagerParse();
        msg.setSourceDoc(doc);
        serverMessageChannel.send(msg, () -> {
        }, (e, m) -> {
        });
    }
    
    private boolean onGenerateExercises(ArrayList<IExerciseSettings> settings) {
        if (exGenPoller.isRunning()) {
            ToastNotification.fire(getLocalizedString(DefaultLocalizationProviders.COMMON.toString(), CommonLocalizationTags.WAIT_TILL_COMPLETION.toString()));
            return false;
        }

        CmExGenStart msg = new CmExGenStart();
        msg.setSettings(settings);

        serverMessageChannel.send(msg,
                exGenPoller::start,
                (e, m) -> {
                    ClientLogger.get().error(e, "Couldn't begin exercise generation operation");
                    ToastNotification.fire(getLocalizedString(LocalizationTags.SERVER_ERROR.toString()));
                    exGen.enableButton();

                    if (e instanceof InvalidClientIdentificationTokenException)
                        ClientEndPoint.get().fatalServerError();
                });

        return true;
    }
    
    private void onInterruptExGen() {
        if (exGenPoller.isRunning())
        	exGenPoller.stop();

        exGen.enableButton();
        
        CmActiveOperationCancel msg = new CmActiveOperationCancel();
        msg.setActiveOperationExpected(false);
        serverMessageChannel.send(msg, () -> {
        }, (e, m) -> {
        });
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
    public void init(AbstractWebRankerPresenter presenter) {
        this.presenter = presenter;
        bindToPresenter(this.presenter);

        // load custom settings from url
        importedSettings = exporter.importSettings();
        if (importedSettings != null)
            ToastNotification.fire(getLocalizedString(LocalizationTags.IMPORTED_SETINGS.toString()));
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

	@Override
	public void handleServerTimeout() {
		if(exGenPoller.isRunning()) {
			exGenPoller.stop();
			ToastNotification.fire(getLocalizedString(LocalizationTags.OP_TIMEDOUT.toString()), 5000);
			exGen.enableButton();
		}
	}

}