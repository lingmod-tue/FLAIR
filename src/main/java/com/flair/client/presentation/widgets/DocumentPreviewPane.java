package com.flair.client.presentation.widgets;

import com.flair.client.localization.*;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.interfaces.AbstractDocumentPreviewPane;
import com.flair.client.presentation.interfaces.DocumentPreviewPaneInput;
import com.flair.client.presentation.interfaces.DocumentPreviewPaneInput.Rankable;
import com.flair.client.presentation.interfaces.DocumentPreviewPaneInput.UnRankable;
import com.flair.client.presentation.widgets.exerciseGeneration.ExerciseGenerationWidget;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.dtos.RankableDocument;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.addins.client.overlay.MaterialOverlay;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.*;
import gwt.material.design.client.ui.html.Br;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocumentPreviewPane extends LocalizedComposite implements AbstractDocumentPreviewPane {
    private static DocumentPreviewPane INSTANCE;

    public static DocumentPreviewPane getInstance() {
        return INSTANCE;
    }

    private static DocumentPreviewPaneUiBinder uiBinder = GWT.create(DocumentPreviewPaneUiBinder.class);

    interface DocumentPreviewPaneUiBinder extends UiBinder<Widget, DocumentPreviewPane> {
    }

    private static DocumentPreviewPaneLocalizationBinder localeBinder = GWT.create(DocumentPreviewPaneLocalizationBinder.class);

    interface DocumentPreviewPaneLocalizationBinder extends LocalizationBinder<DocumentPreviewPane> {
    }

    static enum LocalizationTags {
        NUM_WORDS,
        NUM_SENTENCES,
        COLUMN_CONSTRUCTION,
        COLUMN_WEIGHT,
        COLUMN_HITS,
        COLUMN_RELFREQ,
    }

    private static final int PANEL_WIDTH = 450;

    @UiField
    MaterialPanel pnlRootUI;
    @UiField
    MaterialRow pnlPreviewContainerUI;
    @UiField
    MaterialIcon icoCloseUI;
    @UiField
    MaterialLabel lblDocTitleUI;
    @UiField
    MaterialLabel lblSelectExGenDoc;
    @UiField
    MaterialChip lblDocLevelUI;
    @UiField
    MaterialChip lblDocNumSentencesUI;
    @UiField
    MaterialChip lblDocNumWordsUI;
    @UiField
    @LocalizedField(type = LocalizedFieldType.TOOLTIP_MATERIAL)
    MaterialIcon icoHelpTextUI;
    @UiField
    ScrollPanel pnlDocTextPreviewUI;
    @UiField
    MaterialOverlay mdlAllConstUI;
    @UiField
    MaterialRow pnlAllConstUI;
    @UiField
    MaterialRow pnlWeightSelectionUI;
    @UiField
    MaterialColumn pnlWeightSelectionCol1UI;
    @UiField
    MaterialColumn pnlWeightSelectionCol2UI;
    @UiField
    MaterialColumn pnlWeightSelectionCol3UI;
    @UiField
    @LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
    MaterialButton btnShowAllConstUI;
    @UiField
    MaterialIcon icoCloseModalUI;
    @UiField
    MaterialColumn pnlAllConstCol1UI;
    @UiField
    MaterialColumn pnlAllConstCol2UI;
    @UiField
    MaterialColumn pnlAllConstCol3UI;
    @UiField
	public ExerciseGenerationWidget wdgtExerciseGeneration;
    @UiField
    MaterialCollapsibleItem expExerciseGeneration;
    @UiField
    MaterialCollapsibleItem expDocPreview;

    State state;
    ShowHideHandler showhideHandler;
    boolean visible;

    private enum TableType {
        WEIGHT_SELECTION,
        ALL_CONSTRUCTIONS
    }

    enum ColumnType {
        COLUMN_1,
        COLUMN_2,
        COLUMN_3
    }

    private enum InputType {
        RANKABLE,
        UNRANKABLE
    }

    static final class TableData {
        protected final GrammaticalConstruction gram;
        private final boolean keyword;
        private final boolean customKeyword;
        protected final int hits;
        protected final double weight;
        /**
         * The scaled weight that is displayed to the user.
         */
        protected final double displayedWeight;
        /**
         * The scaling factor used to scale the internally used weight for displaying to the user.
         */
        private final double weightScaleFactor = 5;
        private final double relFreq;

        TableData(GrammaticalConstruction gram, int hits, double weight, double relFreq) {
            this.gram = gram;
            this.hits = hits;
            this.weight = weight;
            this.relFreq = relFreq;
            this.displayedWeight = Math.round(weight*weightScaleFactor);

            keyword = customKeyword = false;
        }

        TableData(boolean customKeyword, int hits, double weight, double relFreq) {
            this.gram = null;
            this.hits = hits;
            this.weight = weight;
            this.relFreq = relFreq;
            this.displayedWeight = Math.round(weight*weightScaleFactor);

            this.keyword = true;
            this.customKeyword = customKeyword;
        }
        public double getDisplayedWeight(){
            return Math.round(this.weight*this.weightScaleFactor);
        }

        public String getLocalizedName(Language lang) {
            if (keyword) {
                return DefaultLocalizationProviders.COMMON.get()
                        .getLocalizedString(customKeyword ? CommonLocalizationTags.KEYWORDS.toString() :
                                CommonLocalizationTags.ACADEMIC_VOCAB.toString(), lang);
            } else
                return GrammaticalConstructionLocalizationProvider.getPath(gram);
        }
    }

    private class State {
        class Table {
            TableType type;
            List<TableData> dataProvider;
            MaterialColumn colFirst;
            MaterialColumn colSecond;
            MaterialColumn colThird;

            Table(TableType type) {
                this.type = type;
                dataProvider = new ArrayList<>();

                switch (type) {
                    case WEIGHT_SELECTION:
                        colFirst = pnlWeightSelectionCol1UI;
                        colSecond = pnlWeightSelectionCol2UI;
                        colThird = pnlWeightSelectionCol3UI;

                        break;
                    case ALL_CONSTRUCTIONS:
                        colFirst = pnlAllConstCol1UI;
                        colSecond = pnlAllConstCol2UI;
                        colThird = pnlAllConstCol3UI;

                        break;
                }
                ;
            }

            private Widget generateColumnHeader(String name) {
                MaterialLabel out = new MaterialLabel(name);
                out.setFontWeight(FontWeight.BOLD);
                out.setPaddingTop(10);
                out.setPaddingBottom(10);
                return out;
            }

            private Widget generateColumnData(ColumnType col, TableData data) {
                Widget out = null;

                switch (col) {
                    case COLUMN_1: {
                        // construction name
                        if (type == TableType.WEIGHT_SELECTION) {
                            String name = data.getLocalizedName(getCurrentLocale());
                            String tooltip = name;
                            if (name.length() > 25)
                                name = name.substring(0, 25) + "...";
                            MaterialChip chip = new MaterialChip(name);
                            chip.setLetter("");
                            chip.setLetterBackgroundColor(data.keyword ?
                                    rankable.getKeywordAnnotationColor() : rankable.getConstructionAnnotationColor(data.gram));
                            chip.setBackgroundColor(Color.WHITE);
                            chip.setTruncate(true);
                            chip.setTooltip(tooltip);

                            FlowPanel container = new FlowPanel();
                            container.add(chip);
                            container.add(new Br());
                            out = container;
                        } else {
                            MaterialLabel name = new MaterialLabel(data.getLocalizedName(getCurrentLocale()));
                            name.setFontSize(1, Unit.EM);
                            out = name;
                        }

                        break;
                    }
                    case COLUMN_2: {
                        // freq
                        if (type == TableType.WEIGHT_SELECTION) {
                            MaterialChip chip = new MaterialChip();
                            chip.setLetter("" + data.hits);
                            chip.setBackgroundColor(Color.WHITE);
                            FlowPanel container = new FlowPanel();
                            container.add(chip);
                            container.add(new Br());
                            out = container;
                        } else {
                            MaterialLabel freq = new MaterialLabel("" + data.hits);
                            freq.setFontSize(1, Unit.EM);
                            out = freq;
                        }

                        break;
                    }
                    case COLUMN_3: {
                        if (type == TableType.WEIGHT_SELECTION) {
                            // weight
                            MaterialChip chip = new MaterialChip();
                            chip.setLetter("(" + data.displayedWeight + ")");
                            chip.setBackgroundColor(Color.WHITE);
                            FlowPanel container = new FlowPanel();
                            container.add(chip);
                            container.add(new Br());
                            out = container;
                        } else {
                            // rel freq
                            MaterialLabel relFreq = new MaterialLabel("(" + data.relFreq + ")");
                            relFreq.setFontSize(1, Unit.EM);
                            out = relFreq;
                        }

                        break;
                    }
                }

                return out;
            }

            private void initColumns(String col1, String col2, String col3) {
                // hide columns if there are no weights to show
                if (type == TableType.WEIGHT_SELECTION) {
                    if (dataProvider.isEmpty())
                        return;
                }

                colFirst.add(generateColumnHeader(col1));
                colSecond.add(generateColumnHeader(col2));
                colThird.add(generateColumnHeader(col3));
            }

            public void generateData() {
                switch (type) {
                    case WEIGHT_SELECTION: {
                        if (rankable.shouldShowKeywords()) {
                            dataProvider.add(new TableData(rankable.hasCustomKeywords(),
                                    (int) rankable.getDocument().getKeywordCount(),
                                    rankable.getKeywordWeight(), 0));
                        }

                        for (GrammaticalConstruction itr : rankable.getConstructions()) {
                            if (rankable.isConstructionWeighted(itr)) {
                                TableData d = new TableData(itr,
                                        (int) rankable.getDocument().getConstructionFreq(itr),
                                        rankable.getConstructionWeight(itr),
                                        rankable.getDocument().getConstructionRelFreq(itr));

                                dataProvider.add(d);
                            }
                        }

                        break;
                    }
                    case ALL_CONSTRUCTIONS: {
                        for (GrammaticalConstruction itr : rankable.getConstructions()) {
                            RankableDocument doc = rankable.getDocument();
                            TableData d = new TableData(itr,
                                    (int) doc.getConstructionFreq(itr),
                                    rankable.getConstructionWeight(itr),
                                    doc.getConstructionRelFreq(itr));

                            dataProvider.add(d);
                        }

                        break;
                    }
                }

                // sort by construction name
                Collections.sort(dataProvider, (a, b) -> {
                    return a.getLocalizedName(getCurrentLocale()).compareToIgnoreCase(b.getLocalizedName(getCurrentLocale()));
                });
            }

            public void updateViewData() {
                // add data to the containers
                for (TableData itr : dataProvider) {
                    colFirst.add(generateColumnData(ColumnType.COLUMN_1, itr));
                    colSecond.add(generateColumnData(ColumnType.COLUMN_2, itr));
                    colThird.add(generateColumnData(ColumnType.COLUMN_3, itr));
                }
            }

            public void clear(boolean allData) {
                if (allData)
                    dataProvider.clear();

                colFirst.clear();
                colSecond.clear();
                colThird.clear();
            }

            public void reload(boolean full) {
                clear(full);
                if (full)
                    generateData();

                switch (type) {
                    case WEIGHT_SELECTION:
                        initColumns(getLocalizedString(LocalizationTags.COLUMN_CONSTRUCTION.toString()),
                                getLocalizedString(LocalizationTags.COLUMN_HITS.toString()),
                                getLocalizedString(LocalizationTags.COLUMN_WEIGHT.toString()));
                        break;
                    case ALL_CONSTRUCTIONS:
                        initColumns(getLocalizedString(LocalizationTags.COLUMN_CONSTRUCTION.toString()),
                                getLocalizedString(LocalizationTags.COLUMN_HITS.toString()),
                                getLocalizedString(LocalizationTags.COLUMN_RELFREQ.toString()));
                        break;
                }

                updateViewData();
            }

            public boolean hasData() {
                return dataProvider.isEmpty() == false;
            }
        }

        InputType type;
        DocumentPreviewPaneInput.Rankable rankable;
        DocumentPreviewPaneInput.UnRankable unrankable;
        Table weightSelection;
        Table constructionDetails;

        State() {
            type = null;
            rankable = null;
            unrankable = null;
            weightSelection = null;
            constructionDetails = null;
        }

        public void init(DocumentPreviewPaneInput.Rankable input) {
            type = InputType.RANKABLE;
            rankable = input;
            unrankable = null;

            reload(true);
        }

        public void init(DocumentPreviewPaneInput.UnRankable input) {
            type = InputType.UNRANKABLE;
            rankable = null;
            unrankable = input;

            reload(true);
        }

        public void resetUI() {
            pnlDocTextPreviewUI.clear();

            // reset component visibility
            pnlWeightSelectionUI.setVisible(true);
            btnShowAllConstUI.setVisible(true);

            lblDocTitleUI.setVisible(true);
            lblDocLevelUI.setVisible(true);
            lblDocNumSentencesUI.setVisible(true);
            lblDocNumWordsUI.setVisible(true);
            icoHelpTextUI.setVisible(false);

            lblSelectExGenDoc.setVisible(false);
            wdgtExerciseGeneration.setVisible(true);
        }

        public void reload(boolean fullReload) {
            if (type == null)
                return;

            resetUI();

            // deferred init until the columns are fully initialized
            if (weightSelection == null)
                weightSelection = new Table(TableType.WEIGHT_SELECTION);
            if (constructionDetails == null)
                constructionDetails = new Table(TableType.ALL_CONSTRUCTIONS);

            switch (type) {
                case RANKABLE: {
                    // set up the tables
                    weightSelection.reload(fullReload);
                    constructionDetails.reload(fullReload);

                    // set up the remaining fields
                    lblDocTitleUI.setText(rankable.getDocument().getTitle());
                    lblDocLevelUI.setText(rankable.getDocument().getReadabilityLevel().toString());
                    lblDocNumSentencesUI.setText(rankable.getDocument().getNumSentences() + " " + getLocalizedString(LocalizationTags.NUM_SENTENCES.toString()));
                    lblDocNumWordsUI.setText(rankable.getDocument().getNumWords() + " " + getLocalizedString(LocalizationTags.NUM_WORDS.toString()));

                    pnlDocTextPreviewUI.add(new HTML(rankable.getPreviewMarkup()));
                    pnlDocTextPreviewUI.scrollToTop();

                    if (weightSelection.hasData())
                        icoHelpTextUI.setVisible(true);
                    
                    // We only use the document if it's in the selected language
                	if(DocumentPreviewPane.getInstance().getCurrentlyPreviewedDocument().getDocument().getLanguage().equals(DocumentPreviewPane.getInstance().getCurrentLocale())) {
                        wdgtExerciseGeneration.initConstructionsOccurrences();
                	} else {
                		wdgtExerciseGeneration.setVisible(wdgtExerciseGeneration.hasValidTasks());
                        lblSelectExGenDoc.setVisible(!wdgtExerciseGeneration.hasValidTasks());
                	}

                    break;
                }
                case UNRANKABLE: {
                    // hide unused widgets
                    pnlWeightSelectionUI.setVisible(false);
                    btnShowAllConstUI.setVisible(false);
                    lblDocLevelUI.setVisible(false);
                    lblDocNumSentencesUI.setVisible(false);
                    lblDocNumWordsUI.setVisible(false);
                    icoHelpTextUI.setVisible(false);
                    
                    wdgtExerciseGeneration.setVisible(wdgtExerciseGeneration.hasValidTasks());
                    lblSelectExGenDoc.setVisible(!wdgtExerciseGeneration.hasValidTasks());
                    

                    // update the rest
                    lblDocTitleUI.setText(unrankable.getTitle());
                    pnlDocTextPreviewUI.add(new HTML(new SafeHtmlBuilder().appendEscapedLines(unrankable.getText()).toSafeHtml()));
                    pnlDocTextPreviewUI.scrollToTop();

                    break;
                }
            }

        }
    }

    private void setPanelRight(double right) {
        pnlPreviewContainerUI.setRight(right);
    }

    private void setContainerVisible(boolean visible) {
        this.visible = visible;
        setPanelRight(visible ? 0 : -PANEL_WIDTH);
    }

    private void initHandlers() {
        icoCloseUI.addClickHandler(e -> hide());
        icoCloseModalUI.addClickHandler(e -> mdlAllConstUI.close());
        btnShowAllConstUI.addClickHandler(e -> mdlAllConstUI.open(btnShowAllConstUI));
    }

    private void initUI() {
        pnlPreviewContainerUI.setWidth(PANEL_WIDTH + "px");
        expDocPreview.expand();
        hide();
    }

    public DocumentPreviewPane() {
        state = new State();
        showhideHandler = null;
        visible = false;
        INSTANCE = this;

        initWidget(uiBinder.createAndBindUi(this));
        initLocale(localeBinder.bind(this));

        initHandlers();
        initUI();
    }

    @Override
    public void setLocale(Language lang) {
        super.setLocale(lang);
        setExerciseGenerationVisibility(lang);
        state.reload(false);
    }
    
    private void setExerciseGenerationVisibility(Language lang) {
    	if(lang == Language.ENGLISH) {
        	expExerciseGeneration.setVisible(true);
        } else {
        	expExerciseGeneration.setVisible(false);
        }
    }

    @Override
    public void preview(Rankable input) {
        state.init(input);
    }

    @Override
    public void preview(UnRankable input) {
        state.init(input);
    }

    @Override
    public void show() {
        setContainerVisible(true);

        if (showhideHandler != null)
            showhideHandler.handle(visible);
    }

    @Override
    public void hide() {
        setContainerVisible(false);

        if (showhideHandler != null)
            showhideHandler.handle(visible);
    }

    @Override
    public void setShowHideEventHandler(ShowHideHandler handler) {
        showhideHandler = handler;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public int getWidth() {
        return PANEL_WIDTH;
    }

    public Rankable getCurrentlyPreviewedDocument() {
        return state.rankable;
    }

    public State.Table getConstructionsDetailsTable() {
        return state.weightSelection;
    }

    public ScrollPanel getPnlDocTextPreviewUI() {
        return pnlDocTextPreviewUI;
    }
}
