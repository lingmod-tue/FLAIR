package com.flair.client.presentation.widgets;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizationData;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.SimpleLocalizedTextButtonWidget;
import com.flair.client.localization.SimpleLocalizedTooltipWidget;
import com.flair.client.localization.locale.DocumentPreviewPaneLocale;
import com.flair.client.localization.locale.GrammaticalConstructionLocale;
import com.flair.client.localization.locale.KeywordWeightSliderLocale;
import com.flair.client.presentation.interfaces.AbstractDocumentPreviewPane;
import com.flair.client.presentation.interfaces.DocumentPreviewPaneInput;
import com.flair.client.presentation.interfaces.DocumentPreviewPaneInput.Rankable;
import com.flair.client.presentation.interfaces.DocumentPreviewPaneInput.UnRankable;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.data.SelectionType;
import gwt.material.design.client.data.component.RowComponent;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialCollapsibleBody;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.Column;
import gwt.material.design.client.ui.table.cell.TextColumn;
import gwt.material.design.client.ui.table.cell.WidgetColumn;

public class DocumentPreviewPane extends LocalizedComposite implements AbstractDocumentPreviewPane
{
	public interface ShowHideHandler {
		public void handle(boolean visible);
	}
	
	private static DocumentPreviewPaneUiBinder uiBinder = GWT.create(DocumentPreviewPaneUiBinder.class);

	interface DocumentPreviewPaneUiBinder extends UiBinder<Widget, DocumentPreviewPane>
	{
	}

	private static final int				PANEL_WIDTH = 450;
	
	@UiField
	MaterialPanel				pnlRootUI;
	@UiField
	MaterialRow					pnlPreviewContainerUI;
	@UiField
	MaterialIcon				icoCloseUI;
	@UiField
	MaterialLabel				lblDocTitleUI;
	@UiField
	MaterialChip				lblDocLevelUI;
	@UiField
	MaterialChip				lblDocNumSentencesUI;
	@UiField
	MaterialChip				lblDocNumWordsUI;
	@UiField
	FlowPanel					pnlWeightSelectionUI;
	@UiField
	MaterialIcon				icoHelpTextUI;
	@UiField
	ScrollPanel					pnlDocTextPreviewUI;
	@UiField
	MaterialRow					pnlFooterUI;
	@UiField
	MaterialLink				tglConstructionDetailsUI;
	@UiField
	MaterialCollapsibleBody		pnlConstructionDetailsUI;

	SimpleLocalizedTooltipWidget<MaterialIcon>			icoHelpTextLC;
	SimpleLocalizedTextButtonWidget<MaterialLink>		tglConstructionDetailsLC;
	
	State						state;
	ShowHideHandler				showhideHandler;
	boolean						visible;
	
	private enum TableType
	{
		WEIGHT_SELECTION,
		ALL_CONSTRUCTIONS
	}
	
	private enum InputType
	{
		RANKABLE,
		UNRANKABLE
	}
	
	private static final class TableData
	{
		private final GrammaticalConstruction		gram;
		private final boolean						keyword;
		private final boolean						customKeyword;
		private final int							hits;
		private final double						weight;
		private final double						relFreq;
		
		TableData(GrammaticalConstruction gram, int hits, double weight, double relFreq)
		{
			this.gram = gram;
			this.hits = hits;
			this.weight = weight;
			this.relFreq = relFreq;
			
			keyword = customKeyword = false;
		}
		
		TableData(boolean customKeyword, int hits, double weight, double relFreq)
		{
			this.gram = null;
			this.hits = hits;
			this.weight = weight;
			this.relFreq = relFreq;
			
			this.keyword = true;
			this.customKeyword = customKeyword;
		}
		
		public String getLocalizedName(Language lang)
		{
			if (keyword)
				return KeywordWeightSliderLocale.INSTANCE.getLocalizedKeywordString(lang, customKeyword);
			else
				return GrammaticalConstructionLocale.get().getLocalizedPath(gram, lang);
		}
	}
	
	
	private class State
	{
		class Table
		{
			private static final int		PAGE_SIZE = 200;		// > no. of gram consts to disable pagination
			
			private static final String		WEIGHT_SEL_HEIGHT = "150px";
			private static final String		ALL_CONST_HEIGHT = "350px";
			
			TableType						type;
			MaterialDataTable<TableData>	table;
			List<TableData>					dataProvider;
			Column<TableData, ?>			colFirst;
			TextColumn<TableData>			colSecond;
			TextColumn<TableData>			colThird;
			
			Table(TableType type, List<TableData> data)
			{
				this.type = type;
				table = new MaterialDataTable<>();
				table.setUseCategories(false);
				table.setUseRowExpansion(false);
				table.setSelectionType(SelectionType.SINGLE);
				dataProvider = data;
				if (dataProvider == null)
					dataProvider = new ArrayList<>();
				
				switch (type)
				{
				case WEIGHT_SELECTION:
					table.setHeight(WEIGHT_SEL_HEIGHT);
					
					colFirst = new WidgetColumn<TableData, MaterialChip>() {
						@Override
						public MaterialChip getValue(TableData d)
						{
							MaterialChip out = new MaterialChip();
							Color wc = d.keyword ? rankable.getKeywordAnnotationColor() : rankable.getConstructionAnnotationColor(d.gram);
							
							out.setText(d.getLocalizedName(localeCore.getLanguage()));
							out.setLetter("C");
							out.setLetterBackgroundColor(wc);
							
							return out;
						}
						
						@Override
			            public Comparator<? super RowComponent<TableData>> getSortComparator() {
			                return (o1, o2) -> {
			                	TableData a = o1.getData(), b = o2.getData();
			                	return a.getLocalizedName(localeCore.getLanguage()).compareToIgnoreCase(b.getLocalizedName(localeCore.getLanguage()));
			                };
			            }
					};
					
					colThird = new TextColumn<TableData>() {
						@Override
						public String getValue(TableData item) {
							return "(" + item.weight + ")";
						}
						
						@Override
			            public Comparator<? super RowComponent<TableData>> getSortComparator() {
			                return (o1, o2) -> {
			                	TableData a = o1.getData(), b = o2.getData();
			                	return Double.compare(a.weight, b.weight);
			                };
			            }
					};
					
					break;
				case ALL_CONSTRUCTIONS:
					table.setHeight(ALL_CONST_HEIGHT);
					
					colFirst = new TextColumn<TableData>() {
						@Override
						public String getValue(TableData item) {
							return item.getLocalizedName(localeCore.getLanguage());
						}
						
						@Override
			            public Comparator<? super RowComponent<TableData>> getSortComparator() {
			                return (o1, o2) -> {
			                	TableData a = o1.getData(), b = o2.getData();
			                	return a.getLocalizedName(localeCore.getLanguage()).compareToIgnoreCase(b.getLocalizedName(localeCore.getLanguage()));
			                };
			            }
					};
					
					colThird = new TextColumn<TableData>() {
						@Override
						public String getValue(TableData item) {
							return "(" + item.relFreq + ")";
						}
						
						@Override
			            public Comparator<? super RowComponent<TableData>> getSortComparator() {
			                return (o1, o2) -> {
			                	TableData a = o1.getData(), b = o2.getData();
			                	return Double.compare(a.relFreq, b.relFreq);
			                };
			            }
					};
					
					break;
				}
				
				colSecond = new TextColumn<TableData>() {
					@Override
					public String getValue(TableData item) {
						return ((Integer)item.hits).toString();
					}
					
					@Override
		            public Comparator<? super RowComponent<TableData>> getSortComparator() {
		                return (o1, o2) -> {
		                	TableData a = o1.getData(), b = o2.getData();
		                	return Integer.compare(a.hits, b.hits);
		                };
		            }
				};
			}
			
			public void initColumns(String col1, String col2, String col3)
			{
				table.addColumn(colFirst, col1);
				table.addColumn(colSecond, col2);
				table.addColumn(colThird, col3);
			}
		}
		
		InputType								type;
		DocumentPreviewPaneInput.Rankable 		rankable;
		DocumentPreviewPaneInput.UnRankable 	unrankable;
		Table									weightSelection;
		Table 									constructionDetails;
		
		State()
		{
			type = null;
			rankable = null;
			unrankable = null;
			weightSelection = null;
			constructionDetails = null;
		}
		
		public void refreshTables()
		{
			weightSelection.table.setRowData(0, weightSelection.dataProvider);
			constructionDetails.table.setRowData(0, constructionDetails.dataProvider);
			
			weightSelection.table.setRedraw(true);
			weightSelection.table.refreshView();
			constructionDetails.table.setRedraw(true);
			constructionDetails.table.refreshView();
		}
		
		private void genTableData(List<TableData> ws, List<TableData> cd)
		{
			ws.clear();
			cd.clear();
			
			// update data
			if (rankable.shouldShowKeywords())
			{
				ws.add(new TableData(rankable.hasCustomKeywords(),
						(int)rankable.getDocument().getKeywordCount(),
						rankable.getKeywordWeight(), 0));
			}
			
			for (GrammaticalConstruction itr : rankable.getConstructions())
			{
				TableData d = new TableData(itr,
						(int)rankable.getDocument().getConstructionFreq(itr),
						rankable.getConstructionWeight(itr),
						rankable.getDocument().getConstructionRelFreq(itr));
				
				cd.add(d);
				if (rankable.isConstructionWeighted(itr))
					ws.add(d);
			}
		}
		
		public void init(DocumentPreviewPaneInput.Rankable input)
		{
			type = InputType.RANKABLE;
			rankable = input;
			unrankable = null;
			
			reload(true);
		}
		
		public void init(DocumentPreviewPaneInput.UnRankable input)
		{
			type = InputType.UNRANKABLE;
			rankable = null;
			unrankable = input;
					
			reload(true);
		}
		
		public void resetUI()
		{
			// reset component visibility
			pnlWeightSelectionUI.setVisible(true);
			pnlFooterUI.setVisible(true);
			
			lblDocTitleUI.setVisible(true);
			lblDocLevelUI.setVisible(true);
			lblDocNumSentencesUI.setVisible(true);
			lblDocNumWordsUI.setVisible(true);
			icoHelpTextUI.setVisible(true);
		}
		
		public void reload(boolean fullReload)
		{
			if (type == null)
				return;
			
			resetUI();
			
			switch (type)
			{
			case RANKABLE:
				{
					if (weightSelection == null)
						weightSelection = new Table(TableType.WEIGHT_SELECTION, null);
					if (constructionDetails == null)
						constructionDetails = new Table(TableType.ALL_CONSTRUCTIONS, null);
					
					// remove current tables from the pane
					pnlWeightSelectionUI.remove(weightSelection.table);
					pnlConstructionDetailsUI.remove(constructionDetails.table);
					
					if (fullReload)
						genTableData(weightSelection.dataProvider, constructionDetails.dataProvider);
					
					// re-init tables
					weightSelection = new Table(TableType.WEIGHT_SELECTION, weightSelection.dataProvider);
					constructionDetails = new Table(TableType.ALL_CONSTRUCTIONS, constructionDetails.dataProvider);
					
					// add them to the pane
					pnlWeightSelectionUI.add(weightSelection.table);
					pnlConstructionDetailsUI.add(constructionDetails.table);
					
					LocalizationData ld = getLocalizationData(localeCore.getLanguage());
					
					weightSelection.initColumns(ld.get(DocumentPreviewPaneLocale.DESC_tableColConstruction),
							ld.get(DocumentPreviewPaneLocale.DESC_tableColHits),
							ld.get(DocumentPreviewPaneLocale.DESC_tableColWeight));
					constructionDetails.initColumns(ld.get(DocumentPreviewPaneLocale.DESC_tableColConstruction),
							ld.get(DocumentPreviewPaneLocale.DESC_tableColHits),
							ld.get(DocumentPreviewPaneLocale.DESC_tableColRelFreq));

					refreshTables();
					
					// set up the remaining fields
					lblDocTitleUI.setText(rankable.getDocument().getTitle());
					lblDocLevelUI.setText(rankable.getDocument().getReadabilityLevel().toString());
					lblDocNumSentencesUI.setText(rankable.getDocument().getNumSentences() + " " + ld.get(DocumentPreviewPaneLocale.DESC_lblDocNumSentences));
					lblDocNumWordsUI.setText(rankable.getDocument().getNumWords() + " " + ld.get(DocumentPreviewPaneLocale.DESC_lblDocNumWords));
					
					pnlDocTextPreviewUI.clear();
					pnlDocTextPreviewUI.add(new HTML(rankable.getPreviewMarkup()));
					
					
					break;
				}
			case UNRANKABLE:
				{
					// hide unused widgets
					pnlWeightSelectionUI.setVisible(false);
					pnlFooterUI.setVisible(false);
					lblDocLevelUI.setVisible(false);
					lblDocNumSentencesUI.setVisible(false);
					lblDocNumWordsUI.setVisible(false);
					icoHelpTextUI.setVisible(false);
					
					// update the rest
					lblDocTitleUI.setText(unrankable.getTitle());
					pnlDocTextPreviewUI.clear();
					pnlDocTextPreviewUI.add(new HTML(new SafeHtmlBuilder().appendEscapedLines(unrankable.getText()).toSafeHtml()));
					
					break;
				}
			}
		
		}
	}
	
	private void setPanelRight(double right) {
		pnlPreviewContainerUI.setRight(right);
	}
	
	private void setContainerVisible(boolean visible)
	{
		this.visible = visible;
		setPanelRight(visible ? 0 : -PANEL_WIDTH);
	}
	
	private void initLocale()
	{
		icoHelpTextLC = new SimpleLocalizedTooltipWidget<>(icoHelpTextUI, DocumentPreviewPaneLocale.DESC_icoHelpText);
		tglConstructionDetailsLC = new SimpleLocalizedTextButtonWidget<>(tglConstructionDetailsUI, DocumentPreviewPaneLocale.DESC_tglConstructionDetails);
		
		registerLocale(DocumentPreviewPaneLocale.INSTANCE.en);
		registerLocale(DocumentPreviewPaneLocale.INSTANCE.de);

		registerLocalizedWidget(icoHelpTextLC);
		registerLocalizedWidget(tglConstructionDetailsLC);
		
		refreshLocalization();
	}
	
	private void initHandlers()
	{
		icoCloseUI.addClickHandler(e -> hide());
	}
	
	private void initUI()
	{
		pnlPreviewContainerUI.setWidth(PANEL_WIDTH + "px");
		hide();
	}
	
	public DocumentPreviewPane()
	{
		super(ClientEndPoint.get().getLocalization());
		state = new State();
		showhideHandler = null;
		visible = false;
		
		initWidget(uiBinder.createAndBindUi(this));
		
		initLocale();
		initHandlers();
		initUI();
	}
	
	
	@Override
	public void setLocalization(Language lang)
	{
		super.setLocalization(lang);
		state.reload(false);
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
	public void show()
	{
		setContainerVisible(true);
		
		if (showhideHandler != null)
			showhideHandler.handle(visible);
	}
	
	@Override
	public void hide()
	{
		setContainerVisible(false);
		
		if (showhideHandler != null)
			showhideHandler.handle(visible);
	}

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
}
