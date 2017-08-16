package com.flair.client.presentation.widgets;

import java.util.List;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.SimpleLocalizedTextWidget;
import com.flair.client.localization.SimpleLocalizedWidget;
import com.flair.client.localization.locale.HistoryViewerLocale;
import com.flair.client.localization.locale.LanguageLocale;
import com.flair.client.model.interfaces.WebRankerAnalysis;
import com.flair.client.presentation.interfaces.HistoryViewerService;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.emptystate.MaterialEmptyState;
import gwt.material.design.addins.client.subheader.MaterialSubHeader;
import gwt.material.design.client.constants.CollectionType;
import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconPosition;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialCollection;
import gwt.material.design.client.ui.MaterialCollectionItem;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialRow;

public class HistoryViewer extends LocalizedComposite implements HistoryViewerService
{
	private static HistoryViewerUiBinder uiBinder = GWT.create(HistoryViewerUiBinder.class);

	interface HistoryViewerUiBinder extends UiBinder<Widget, HistoryViewer>
	{
	}

	@UiField
	MaterialModal			mdlRootUI;
	@UiField
	MaterialLabel			lblTitleUI;
	@UiField
	MaterialIcon			btnCloseUI;
	@UiField
	MaterialRow				pnlListContainerUI;
	@UiField
	MaterialSubHeader		lblSelCountUI;
	@UiField
	MaterialCollection		pnlSelectionUI;
	@UiField
	MaterialEmptyState		lblPlaceholderUI;

	SimpleLocalizedTextWidget<MaterialLabel>			lblTitleLC;
	SimpleLocalizedWidget<MaterialEmptyState>			lblPlaceholderLC;
	
	FetchAnalysesHandler	fetchAnalysesHandler;
	RestoreAnalysisHandler	restoreAnalysisHandler;
	
	private void reloadUI()
	{
		pnlSelectionUI.clear();
		
		List<? extends WebRankerAnalysis> analyses = fetchAnalysesHandler.handle();
		
		if (analyses.isEmpty())
		{
			lblPlaceholderUI.setVisible(true);
			pnlListContainerUI.setVisible(false);
		}
		else
		{
			lblPlaceholderUI.setVisible(false);
			pnlListContainerUI.setVisible(true);
		}
		
		lblSelCountUI.setText(analyses.size() + " " + getLocalizedString(HistoryViewerLocale.DESC_AnalysesCount));
		for (WebRankerAnalysis itr : analyses)
		{
			MaterialCollectionItem wrapper = new MaterialCollectionItem();
			wrapper.setType(CollectionType.CHECKBOX);
			wrapper.setWaves(WavesType.DEFAULT);

			MaterialLink title = new MaterialLink(itr.getName());
	//		title.setFontSize(1.0, Unit.EM);
			title.setTruncate(true);
			title.setIconPosition(IconPosition.LEFT);
			switch (itr.getType())
			{
			case CUSTOM_CORPUS:
				title.setIconType(IconType.FILE_UPLOAD);
				title.setIconColor(Color.BLUE);
				break;
			case WEB_SEARCH:
				title.setIconType(IconType.SEARCH);
				title.setIconColor(Color.ORANGE);
				break;
			default:
				throw new RuntimeException("Invalid analysis type");
			}
			
			
			MaterialLabel numResults = new MaterialLabel(LanguageLocale.get().getLocalizedName(itr.getLanguage(), localeCore.getLanguage()) +
														" (" + itr.getParsedDocs().size() +
														" " + getLocalizedString(HistoryViewerLocale.DESC_ResultCount) +
														")",
														Color.GREY);
			numResults.setFontSize(0.8, Unit.EM);
			wrapper.add(title);
			wrapper.add(numResults);
			wrapper.addClickHandler(e -> {
				restoreAnalysisHandler.handle(itr);
				hide();
			});
			
			pnlSelectionUI.add(wrapper);
		}
	}

	private void initLocale()
	{
		lblTitleLC = new SimpleLocalizedTextWidget<>(lblTitleUI, HistoryViewerLocale.DESC_lblTitleUI);
		lblPlaceholderLC = new SimpleLocalizedWidget<>(lblPlaceholderUI, HistoryViewerLocale.DESC_NotifyEmpty, (w,s) -> w.setTitle(s));
		
		registerLocale(HistoryViewerLocale.INSTANCE.en);
		registerLocale(HistoryViewerLocale.INSTANCE.de);

		registerLocalizedWidget(lblTitleLC);
		registerLocalizedWidget(lblPlaceholderLC);

		refreshLocalization();
	}

	private void initHandlers()
	{
		btnCloseUI.addClickHandler(e -> hide());
	}
	
	private void initUI()
	{
		;//
	}

	public HistoryViewer()
	{
		super(ClientEndPoint.get().getLocalization());
		initWidget(uiBinder.createAndBindUi(this));

		fetchAnalysesHandler = null;
		restoreAnalysisHandler = null;

		initLocale();
		initHandlers();
		initUI();
	}

	public void show()
	{
		reloadUI();
		mdlRootUI.open();
	}

	public void hide() {
		mdlRootUI.close();
	}

	@Override
	public void setFetchAnalysesHandler(FetchAnalysesHandler handler) {
		fetchAnalysesHandler = handler;
	}

	@Override
	public void setRestoreAnalysisHandler(RestoreAnalysisHandler handler) {
		restoreAnalysisHandler = handler;
	}
}
