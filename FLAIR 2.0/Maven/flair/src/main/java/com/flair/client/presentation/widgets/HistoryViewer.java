package com.flair.client.presentation.widgets;

import java.util.List;

import com.flair.client.localization.CommonLocalizationTags;
import com.flair.client.localization.DefaultLocalizationProviders;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
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

	private static HistoryViewerLocalizationBinder localeBinder = GWT.create(HistoryViewerLocalizationBinder.class);
	interface HistoryViewerLocalizationBinder extends LocalizationBinder<HistoryViewer> {}
	
	static enum LocalizationTags
	{
		NUM_ANALYSES,
	}
	
	@UiField
	MaterialModal			mdlRootUI;
	@UiField
	@LocalizedField
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
	@LocalizedField(type=LocalizedFieldType.TITLE)
	MaterialEmptyState		lblPlaceholderUI;
	
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
		
		lblSelCountUI.setText(analyses.size() + " " + getLocalizedString(LocalizationTags.NUM_ANALYSES.toString()));
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
			
			String langTag = "";
			switch (itr.getLanguage())
			{
			case ENGLISH:
				langTag = CommonLocalizationTags.LANGUAGE_ENGLISH.toString();
				break;
			case GERMAN:
				langTag = CommonLocalizationTags.LANGUAGE_GERMAN.toString();
				break;
			default:
				langTag = CommonLocalizationTags.INVALID.toString();
			}
			
			MaterialLabel numResults = new MaterialLabel(getLocalizedString(DefaultLocalizationProviders.COMMON.toString(), langTag) +
														" (" + itr.getParsedDocs().size() +
														" " + getLocalizedString(DefaultLocalizationProviders.COMMON.toString(),
																				CommonLocalizationTags.RESULTS.toString()) +
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
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));

		fetchAnalysesHandler = null;
		restoreAnalysisHandler = null;

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
