package com.flair.client.presentation.widgets;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizationData;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.SimpleLocalizedTextButtonWidget;
import com.flair.client.localization.SimpleLocalizedTextWidget;
import com.flair.client.localization.locale.RankerSettingsPaneLocale;
import com.flair.client.model.interfaces.DocumentRankerOutput;
import com.flair.client.model.interfaces.DocumentRankerOutput.Rank;
import com.flair.client.presentation.interfaces.AbstractRankerSettingsPane;
import com.flair.client.presentation.widgets.sliderbundles.ConstructionSliderBundleEnglish;
import com.flair.client.presentation.widgets.sliderbundles.ConstructionSliderBundleGerman;
import com.flair.shared.grammar.Language;
import com.flair.shared.parser.DocumentReadabilityLevel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialBadge;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRow;

public class RankerSettingsPane extends LocalizedComposite implements AbstractRankerSettingsPane
{
	private static RankerSettingsPaneUiBinder uiBinder = GWT.create(RankerSettingsPaneUiBinder.class);

	interface RankerSettingsPaneUiBinder extends UiBinder<Widget, RankerSettingsPane>
	{
	}
	
	private static final double				PANEL_WIDTH = 400;
	
	@UiField
	MaterialRow								pnlSettingsContainerUI;
	@UiField
	MaterialLabel							lblDocCountUI;
	@UiField
	MaterialButton							btnVisualizeUI;
	@UiField
	MaterialButton							btnExportSettingsUI;
	@UiField
	MaterialCardTitle						lblTextCharacteristicsUI;
	@UiField			
	MaterialLabel							lblTextLengthUI;
	@UiField
	DocumentLengthSlider					sldDocLengthUI;
	@UiField
	MaterialLabel							lblTextLevelUI;
	@UiField
	MaterialCheckBox						chkTextLevelAUI;
	@UiField
	MaterialBadge							bdgTextLevelACountUI;
	@UiField
	MaterialCheckBox						chkTextLevelBUI;
	@UiField
	MaterialBadge							bdgTextLevelBCountUI;
	@UiField
	MaterialCheckBox						chkTextLevelCUI;
	@UiField
	MaterialBadge							bdgTextLevelCCountUI;
	@UiField
	KeywordWeightSlider						sldKeywordsUI;
	@UiField
	MaterialButton							btnConstructionsListUI;
	@UiField
	ConstructionSliderBundleEnglish			bdlEnglishSlidersUI;
	@UiField
	ConstructionSliderBundleGerman			bdlGermanSlidersUI;
	@UiField
	MaterialButton							btnResetAllUI;
	
	SimpleLocalizedTextButtonWidget<MaterialButton>			btnVisualizeLC;
	SimpleLocalizedTextButtonWidget<MaterialButton>			btnExportSettingsLC;
	SimpleLocalizedTextWidget<MaterialCardTitle>			lblTextCharacteristicsLC;
	SimpleLocalizedTextWidget<MaterialLabel>				lblTextLengthLC;
	SimpleLocalizedTextWidget<MaterialLabel>				lblTextLevelLC;
	SimpleLocalizedTextButtonWidget<MaterialButton>			btnConstructionsListLC;
	SimpleLocalizedTextButtonWidget<MaterialButton>			btnResetAllLC;
	
	State			state;
	
	private final class State
	{
		Language					sliderLanguage;
		DocumentRankerOutput.Rank	rankData;
		EventHandler				changeHandler;
		EventHandler				visualizeHandler;
		EventHandler				exportHandler;
		
		State()
		{
			sliderLanguage = Language.ENGLISH;
			rankData = null;
			changeHandler = visualizeHandler = exportHandler = null;
		}
		
		private void onSettingChange()
		{
			if (changeHandler != null)
				changeHandler.handle();
		}
		
		private void onVisualize()
		{
			if (visualizeHandler != null)
				visualizeHandler.handle();
		}
		
		private void onExport()
		{
			if (exportHandler != null)
				exportHandler.handle();
		}
		
		private void hideSliderBundles()
		{
			bdlEnglishSlidersUI.setVisible(false);
			bdlGermanSlidersUI.setVisible(false);
		}
		
		public void resetUI()
		{
			hideSliderBundles();
			getSliderBundle().setVisible(true);
		}
		
		public void init(DocumentRankerOutput.Rank rankerData)
		{
			rankData = rankerData;
			reloadUI();
		}
		
		public void reloadUI()
		{
			if (rankData == null)
				return;
			
			LocalizationData ld = getLocalizationData(localeCore.getLanguage());
			final int resultCount = rankData.getRankedDocuments().size();
			int levelA = (int)rankData.getDocLevelDf(DocumentReadabilityLevel.LEVEL_A);
			int levelB = (int)rankData.getDocLevelDf(DocumentReadabilityLevel.LEVEL_B);
			int levelC = (int)rankData.getDocLevelDf(DocumentReadabilityLevel.LEVEL_C);
					
			lblDocCountUI.setText(resultCount + " " + ld.get(RankerSettingsPaneLocale.DESC_lblDocCountUI));
			
			bdgTextLevelACountUI.setText(levelA + " / " + resultCount);
			bdgTextLevelBCountUI.setText(levelB + " / " + resultCount);
			bdgTextLevelCCountUI.setText(levelC + " / " + resultCount);
			
			LanguageSpecificConstructionSliderBundle current = getSliderBundle();
			current.forEachWeightSlider(s -> {
				int df = (int)rankData.getConstructionDf(s.getGram());
				s.setResultCount(df, resultCount);
			});
			
		}
		
		public void setSliderBundle(Language lang)
		{
			hideSliderBundles();
			sliderLanguage = lang;
			
			getSliderBundle().setVisible(true);
		}
		
		public LanguageSpecificConstructionSliderBundle getSliderBundle()
		{
			switch (sliderLanguage)
			{
			case ENGLISH:
				return bdlEnglishSlidersUI;
			case GERMAN:
				return bdlGermanSlidersUI;
			default:
				return null;			
			}
		}
		
		public void resetAll()
		{
			sldDocLengthUI.resetState(false);
			sldKeywordsUI.resetState(false);
			getSliderBundle().resetState(false);
			
			chkTextLevelAUI.setValue(true, false);
			chkTextLevelBUI.setValue(true, false);
			chkTextLevelCUI.setValue(true, false);
			
			onSettingChange();
		}
		
		public void setChangeHandler(EventHandler h) {
			changeHandler = h;
		}
		
		public void setVisualizeHandler(EventHandler h) {
			visualizeHandler = h;
		}
		
		public void setExportHandler(EventHandler h) {
			exportHandler = h;
		}
	}
	
	private void setPanelLeft(double l) {
		pnlSettingsContainerUI.setLeft(l);
	}
	
	private void setContainerVisible(boolean visible) {
		setPanelLeft(visible ? 0 : -PANEL_WIDTH);
	}
	
	private void initLocale()
	{
		btnVisualizeLC = new SimpleLocalizedTextButtonWidget<>(btnVisualizeUI, RankerSettingsPaneLocale.DESC_btnVisualizeUI);
		btnExportSettingsLC = new SimpleLocalizedTextButtonWidget<>(btnExportSettingsUI, RankerSettingsPaneLocale.DESC_btnExportSettingsUI);
		lblTextCharacteristicsLC = new SimpleLocalizedTextWidget<>(lblTextCharacteristicsUI, RankerSettingsPaneLocale.DESC_lblTextCharacteristicsUI);
		lblTextLengthLC = new SimpleLocalizedTextWidget<>(lblTextLengthUI, RankerSettingsPaneLocale.DESC_lblTextLengthUI);
		lblTextLevelLC = new SimpleLocalizedTextWidget<>(lblTextLevelUI, RankerSettingsPaneLocale.DESC_lblTextLevelUI);
		btnConstructionsListLC = new SimpleLocalizedTextButtonWidget<>(btnConstructionsListUI, RankerSettingsPaneLocale.DESC_btnConstructionsListUI);
		btnResetAllLC = new SimpleLocalizedTextButtonWidget<>(btnResetAllUI, RankerSettingsPaneLocale.DESC_btnResetAllUI);
		
		registerLocale(RankerSettingsPaneLocale.INSTANCE.en);
		registerLocale(RankerSettingsPaneLocale.INSTANCE.de);

		registerLocalizedWidget(btnVisualizeLC);
		registerLocalizedWidget(btnExportSettingsLC);
		registerLocalizedWidget(lblTextCharacteristicsLC);
		registerLocalizedWidget(lblTextLengthLC);
		registerLocalizedWidget(lblTextLevelLC);
		registerLocalizedWidget(btnConstructionsListLC);
		registerLocalizedWidget(btnResetAllLC);
		
		refreshLocalization();
	}
	
	private void initHandlers()
	{
		btnVisualizeUI.addClickHandler(e -> state.onVisualize());
		btnExportSettingsUI.addClickHandler(e -> state.onExport());
		
		sldDocLengthUI.setChangeHandler((v) -> state.onSettingChange());
		
		sldKeywordsUI.setWeightChangeHandler((w, v) -> state.onSettingChange());
		sldKeywordsUI.setToggleHandler((w, v) -> state.onSettingChange());
		
		bdlEnglishSlidersUI.forEachWeightSlider(s -> {
			s.setWeightChangeHandler((w, v) -> state.onSettingChange());
			s.setToggleHandler((w, v) -> state.onSettingChange());
		});
		
		bdlGermanSlidersUI.forEachWeightSlider(s -> {
			s.setWeightChangeHandler((w, v) -> state.onSettingChange());
			s.setToggleHandler((w, v) -> state.onSettingChange());
		});
		
		btnResetAllUI.addClickHandler(e -> state.resetAll());
		
		chkTextLevelAUI.addValueChangeHandler(e -> state.onSettingChange());
		chkTextLevelBUI.addValueChangeHandler(e -> state.onSettingChange());
		chkTextLevelCUI.addValueChangeHandler(e -> state.onSettingChange());
	}
	
	private void initUI()
	{
		state.resetUI();		
		hide();
	}
	
	public RankerSettingsPane()
	{
		super(ClientEndPoint.get().getLocalization());
		
		initWidget(uiBinder.createAndBindUi(this));
		
		this.state = new State();
		
		initLocale();
		initHandlers();
		initUI();
	}
	
	
	@Override
	public void setLocalization(Language lang)
	{
		super.setLocalization(lang);
		state.reloadUI();
	}

	@Override
	public void setSliderBundle(Language lang) {
		state.setSliderBundle(lang);
	}

	@Override
	public void updateSettings(Rank rankData) {
		state.init(rankData);	
	}

	@Override
	public void show() {
		setContainerVisible(true);
	}

	@Override
	public void hide() {
		setContainerVisible(false);	
	}

	@Override
	public void setSettingsChangedHandler(EventHandler handler) {
		state.setChangeHandler(handler);
	}

	@Override
	public void setVisualizeHandler(EventHandler handler) {
		state.setVisualizeHandler(handler);
	}

	@Override
	public void setExportSettingsHandler(EventHandler handler) {
		state.setExportHandler(handler);
	}

	@Override
	public LanguageSpecificConstructionSliderBundle getSliderBundle() {
		return state.getSliderBundle();
	}

	@Override
	public DocumentLengthSlider getLengthSlider() {
		return sldDocLengthUI;
	}

	@Override
	public KeywordWeightSlider getKeywordSlider() {
		return sldKeywordsUI;
	}

	@Override
	public boolean isDocLevelEnabled(DocumentReadabilityLevel level) 
	{
		switch (level)
		{
		case LEVEL_A:
			return chkTextLevelAUI.getValue();
		case LEVEL_B:
			return chkTextLevelBUI.getValue();
		case LEVEL_C:
			return chkTextLevelCUI.getValue();
		default:
			return false;
		}
	}

	
}
