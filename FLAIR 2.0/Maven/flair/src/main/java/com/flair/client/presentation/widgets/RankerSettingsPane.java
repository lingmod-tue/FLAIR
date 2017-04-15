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
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.ConstructionSettingsProfile;
import com.flair.shared.interop.ConstructionSettingsProfileImpl;
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
	public interface ShowHideHandler {
		public void handle(boolean visible);
	}
	
	private static RankerSettingsPaneUiBinder uiBinder = GWT.create(RankerSettingsPaneUiBinder.class);

	interface RankerSettingsPaneUiBinder extends UiBinder<Widget, RankerSettingsPane>
	{
	}
	
	private static final int				PANEL_WIDTH = 400;
	
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
	
	State				state;
	ShowHideHandler		showhideHandler;
	boolean				visible;
	
	private final class State
	{
		Language					sliderLanguage;
		DocumentRankerOutput.Rank	rankData;
		EventHandler				changeHandler;
		EventHandler				visualizeHandler;
		EventHandler				exportHandler;
		EventHandler				resetHandler;
		
		State()
		{
			sliderLanguage = Language.ENGLISH;
			rankData = null;
			changeHandler = visualizeHandler = exportHandler = resetHandler = null;
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
		
		private void onReset()
		{
			if (resetHandler != null)
				resetHandler.handle();
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
			
			onReset();
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
		
		public void setResetHandler(EventHandler h) {
			resetHandler = h;
		}
	}
	
	private void setPanelLeft(double l) {
		pnlSettingsContainerUI.setLeft(l);
	}
	
	private void setContainerVisible(boolean visible)
	{
		this.visible = visible;
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
		
		sldDocLengthUI.setWeightChangeHandler((v) -> state.onSettingChange());
		
		sldKeywordsUI.setWeightChangeHandler((w, v) -> state.onSettingChange());
		sldKeywordsUI.setToggleHandler((w, v) -> state.onSettingChange());
		
		btnResetAllUI.addClickHandler(e -> state.resetAll());
		
		chkTextLevelAUI.addValueChangeHandler(e -> state.onSettingChange());
		chkTextLevelBUI.addValueChangeHandler(e -> state.onSettingChange());
		chkTextLevelCUI.addValueChangeHandler(e -> state.onSettingChange());
	}
	
	private void initUI()
	{
		pnlSettingsContainerUI.setWidth(PANEL_WIDTH + "px");
		state.resetUI();
		hide();
	}
	
	public RankerSettingsPane()
	{
		super(ClientEndPoint.get().getLocalization());
		
		initWidget(uiBinder.createAndBindUi(this));
		
		this.state = new State();
		showhideHandler = null;
		visible = false;
		
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
	public void setSettingsChangedHandler(EventHandler handler)
	{
		// update here as the sliders themselves aren't be available during the construction of the panel
		bdlEnglishSlidersUI.forEachWeightSlider(s -> {
			s.setWeightChangeHandler((w, v) -> state.onSettingChange());
			s.setToggleHandler((w, v) -> state.onSettingChange());
			s.refreshLocalization();
		});
		
		bdlGermanSlidersUI.forEachWeightSlider(s -> {
			s.setWeightChangeHandler((w, v) -> state.onSettingChange());
			s.setToggleHandler((w, v) -> state.onSettingChange());
			s.refreshLocalization();
		});
		
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

	public int getWidth() {
		return PANEL_WIDTH;
	}
	
	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public void setResetAllHandler(EventHandler handler) {
		state.setResetHandler(handler);
	}

	@Override
	public void applySettingsProfile(ConstructionSettingsProfile profile, boolean fireEvents)
	{
		chkTextLevelAUI.setValue(profile.isDocLevelEnabled(DocumentReadabilityLevel.LEVEL_A), false);
		chkTextLevelBUI.setValue(profile.isDocLevelEnabled(DocumentReadabilityLevel.LEVEL_B), false);
		chkTextLevelCUI.setValue(profile.isDocLevelEnabled(DocumentReadabilityLevel.LEVEL_C), false);
		
		sldDocLengthUI.setWeight(profile.getDocLengthWeight(), false);
		sldKeywordsUI.setEnabled(profile.isKeywordsEnabled(), false);
		sldKeywordsUI.setWeight(profile.getKeywordsWeight(), false);
		
		for (GrammaticalConstruction itr : GrammaticalConstruction.getForLanguage(getSliderBundle().getLanguage()))
		{
			GrammaticalConstructionWeightSlider slider = getSliderBundle().getWeightSlider(itr);
			if (slider != null && profile.hasConstruction(itr))
			{
				slider.setWeight(profile.getConstructionWeight(itr), false);
				slider.setEnabled(profile.isConstructionEnabled(itr), false);
			}
		}
		
		if (fireEvents)
			state.onSettingChange();
	}

	@Override
	public ConstructionSettingsProfile generateSettingsProfile()
	{
		ConstructionSettingsProfileImpl out = new ConstructionSettingsProfileImpl();
		
		out.setLanguage(getSliderBundle().getLanguage());
		out.setDocLengthWeight(sldDocLengthUI.getWeight());
		out.setKeywordsData(sldKeywordsUI.isEnabled(), sldKeywordsUI.getWeight());
		out.setDocLevelEnabled(DocumentReadabilityLevel.LEVEL_A, chkTextLevelAUI.getValue());
		out.setDocLevelEnabled(DocumentReadabilityLevel.LEVEL_B, chkTextLevelBUI.getValue());
		out.setDocLevelEnabled(DocumentReadabilityLevel.LEVEL_C, chkTextLevelCUI.getValue());
		
		for (GrammaticalConstruction itr : GrammaticalConstruction.getForLanguage(getSliderBundle().getLanguage()))
		{
			GrammaticalConstructionWeightSlider slider = getSliderBundle().getWeightSlider(itr);
			if (slider != null)
				out.setGramData(itr, slider.isEnabled(), slider.getWeight());
		}
		
		return out;
	}
}
