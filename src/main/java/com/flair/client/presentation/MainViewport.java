package com.flair.client.presentation;


import com.flair.client.ClientEndPoint;
import com.flair.client.localization.*;
import com.flair.client.localization.annotations.LocalizedCommonField;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.interfaces.*;
import com.flair.client.presentation.widgets.*;
import com.flair.client.utilities.GlobalWidgetAnimator;
import com.flair.shared.grammar.Language;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.addins.client.iconmorph.MaterialIconMorph;
import gwt.material.design.client.constants.HideOn;
import gwt.material.design.client.constants.Position;
import gwt.material.design.client.ui.*;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

public class MainViewport extends LocalizedComposite implements AbstractWebRankerPresenter {

	private static MainViewportUiBinder uiBinder = GWT.create(MainViewportUiBinder.class);

	interface MainViewportUiBinder extends UiBinder<Widget, MainViewport> {
	}

	private static MainViewportLocalizationBinder localeBinder = GWT.create(MainViewportLocalizationBinder.class);

	interface MainViewportLocalizationBinder extends LocalizationBinder<MainViewport> {}

	@UiField
	MaterialPanel pnlRootUI;
	@UiField
	MaterialSplashScreen splSplashUI;
	@UiField
	MaterialTitle lblSplashStatus;
	@UiField
	HTML htmlSplashLogoUI;
	@UiField
	MaterialNavBar navMainUI;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TOOLTIP_MATERIAL)
	MaterialButton btnWebSearchUI;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TOOLTIP_MATERIAL)
	MaterialAnchorButton btnUploadUI;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TOOLTIP_MATERIAL)
	MaterialLink btnHistoryUI;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TOOLTIP_MATERIAL)
	MaterialLink btnSwitchLangUI;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TOOLTIP_MATERIAL)
	@LocalizedCommonField(tag = CommonLocalizationTags.BRANDING, type = LocalizedFieldType.TEXT_NAVBRAND)
	MaterialNavBrand btnAboutUI;
	@UiField
	@LocalizedCommonField(tag = CommonLocalizationTags.LANGUAGE_ENGLISH, type = LocalizedFieldType.TEXT_BUTTON)
	MaterialLink btnLangEnUI;
	@UiField
	@LocalizedCommonField(tag = CommonLocalizationTags.LANGUAGE_GERMAN, type = LocalizedFieldType.TEXT_BUTTON)
	MaterialLink btnLangDeUI;
	@UiField
	WebSearchModal mdlWebSearchUI;
	@UiField
	MaterialLink tglSettingsPaneUI;
	@UiField
	MaterialIconMorph icoSettingsMorphUI;
	@UiField
	RankerSettingsPane pnlConstructionsSettingsUI;
	@UiField
	DocumentResultsPane pnlDocResultsUI;
	@UiField
	DocumentPreviewPane pnlDocPreviewUI;
	@UiField
	ModalPrompt mdlPromptUI;
	@UiField
	CorpusFileUploader mdlCorpusUploadUI;
	@UiField
	CustomKeywordsEditor mdlCustomKeywordsUI;
	@UiField
	MaterialPanel pnlResultsContainerUI;
	@UiField
	DocumentCollectionVisualizer mdlVisualizerUI;
	@UiField
	MaterialRow pnlDefaultPlaceholderUI;
	@UiField
	@LocalizedCommonField(tag = CommonLocalizationTags.SEARCH)
	MaterialCardTitle lblDefaultSearchTitleUI;
	@UiField
	@LocalizedField
	MaterialLabel lblDefaultSearchCaptionUI;
	@UiField
	@LocalizedField
	MaterialCardTitle lblDefaultConfigTitleUI;
	@UiField
	@LocalizedField
	MaterialLabel lblDefaultConfigCaptionUI;
	@UiField
	@LocalizedCommonField(tag = CommonLocalizationTags.UPLOAD)
	MaterialCardTitle lblDefaultUploadTitleUI;
	@UiField
	@LocalizedField
	MaterialLabel lblDefaultUploadCaptionUI;
	@UiField
	SettingsExporter mdlExporterUI;
	@UiField
	DocumentComparer mdlComparerUI;
	@UiField
	HistoryViewer mdlHistoryUI;
	@UiField
	QuestionGeneratorPreview mdlQuestGenUI;
	@UiField
	AboutPage mdlAboutUI;

	private void invokeAtomicOperation(Runnable handler) {
		if (ClientEndPoint.get().getWebRanker().isOperationInProgress()) {
			// prompt the user if they want to cancel the currently running operation
			String title = getLocalizedString(DefaultLocalizationProviders.COMMON.toString(), CommonLocalizationTags.OPERATION_INPROGRESS_CONFIRM_TITLE.toString());
			String caption = getLocalizedString(DefaultLocalizationProviders.COMMON.toString(), CommonLocalizationTags.OPERATION_INPROGRESS_CONFIRM_CAPTION.toString());

			mdlPromptUI.yesNo(title, caption, () -> {
				ClientEndPoint.get().getWebRanker().cancelCurrentOperation();
				handler.run();
			}, () -> {});
		} else
			handler.run();
	}

	private void toggleSettingsPane() {
		if (pnlConstructionsSettingsUI.isVisible()) {
			pnlConstructionsSettingsUI.hide();
			icoSettingsMorphUI.getElement().removeClassName("morphed");
		} else {
			pnlConstructionsSettingsUI.show();
			icoSettingsMorphUI.getElement().addClassName("morphed");
		}
	}

	private void showUploadModal() {
		invokeAtomicOperation(() -> mdlCorpusUploadUI.show());
	}

	private void showSearchModal() {
		invokeAtomicOperation(() -> mdlWebSearchUI.show());
	}

	private void switchDisplayLanguage(Language lang) {
		LocalizationEngine.get().setLanguage(lang);
	}

	private void initHandlers() {
		btnWebSearchUI.addClickHandler(e -> showSearchModal());
		btnUploadUI.addClickHandler(e -> showUploadModal());
		btnHistoryUI.addClickHandler(e -> mdlHistoryUI.show());
		btnAboutUI.addClickHandler(e -> mdlAboutUI.show());

		btnLangEnUI.addClickHandler(e -> switchDisplayLanguage(Language.ENGLISH));
		btnLangDeUI.addClickHandler(e -> switchDisplayLanguage(Language.GERMAN));

		tglSettingsPaneUI.addClickHandler(e -> toggleSettingsPane());

		lblDefaultSearchTitleUI.addClickHandler(e -> showSearchModal());
		lblDefaultConfigTitleUI.addClickHandler(e -> toggleSettingsPane());
		lblDefaultUploadTitleUI.addClickHandler(e -> showUploadModal());

		pnlConstructionsSettingsUI.setShowHideEventHandler(v -> {
			if (v)
				icoSettingsMorphUI.getElement().addClassName("morphed");
			else
				icoSettingsMorphUI.getElement().removeClassName("morphed");
		});

		mdlCustomKeywordsUI.bindToSlider(pnlConstructionsSettingsUI.getKeywordSlider());
	}

	private void initUI() {
		btnWebSearchUI.setTooltipPosition(Position.LEFT);
		btnUploadUI.setTooltipPosition(Position.LEFT);
		btnSwitchLangUI.setTooltipPosition(Position.LEFT);

		// disable the settings toggle icon morph's default behaviour (this ought to be a part of the API)
		icoSettingsMorphUI.getElement().removeAttribute("onclick");
		// hide the useless left padding on medium-sized devices and smaller
		navMainUI.getNavMenu().setHideOn(HideOn.HIDE_ON_MED_DOWN);

		// animate FABs slightly
		MaterialAnimation pulseSearch = new MaterialAnimation();
		pulseSearch.setTransition(Transition.PULSE);
		pulseSearch.setDelay(10);
		pulseSearch.setDuration(3000);
		pulseSearch.setInfinite(true);
		pulseSearch.animate(btnWebSearchUI);

		MaterialAnimation pulseUpload = new MaterialAnimation();
		pulseUpload.setTransition(Transition.PULSE);
		pulseUpload.setDelay(3000);
		pulseUpload.setDuration(3000);
		pulseUpload.setInfinite(true);
		pulseUpload.animate(btnUploadUI);

		MaterialAnimation pulseSplash = new MaterialAnimation(htmlSplashLogoUI);
		pulseSplash.setTransition(Transition.PULSE);
		pulseSplash.setDelay(10);
		pulseSplash.setDuration(2000);
		pulseSplash.setInfinite(true);
		pulseSplash.animate();
	}

	@UiConstructor
	public MainViewport() {
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));
		initHandlers();
		initUI();
	}

	public void showSplash(boolean visible) {
		if (visible)
			splSplashUI.show();
		else {
			GlobalWidgetAnimator.get().animateWithStop(splSplashUI, Transition.FADEOUT, 0, 1200,
					() -> splSplashUI.hide());
		}
	}

	public void setSplashTitle(String text) {
		lblSplashStatus.setTitle(text);
	}

	public void setSplashSubtitle(String text) {
		lblSplashStatus.setDescription(text);
	}


	@Override
	public AbstractRankerSettingsPane getRankerSettingsPane() {
		return pnlConstructionsSettingsUI;
	}

	@Override
	public AbstractDocumentResultsPane getDocumentResultsPane() {
		return pnlDocResultsUI;
	}

	@Override
	public AbstractDocumentPreviewPane getDocumentPreviewPane() {
		return pnlDocPreviewUI;
	}

	@Override
	public UserPromptService getPromptService() {
		return mdlPromptUI;
	}

	@Override
	public void showLoaderOverlay(boolean visible) {
		MaterialLoader.loading(visible);
	}

	@Override
	public void showProgressBar(boolean visible) {
		MaterialLoader.progress(visible);
	}

	@Override
	public void showCancelPane(boolean visible) {
		pnlDocResultsUI.setCancelVisible(visible);
	}

	@Override
	public void showDefaultPane(boolean visible) {
		if (visible) {
			GlobalWidgetAnimator.get().animateWithStart(pnlDefaultPlaceholderUI,
					Transition.ZOOMINUP, 10, 800, () -> pnlDefaultPlaceholderUI.setVisible(true));
		} else {
			GlobalWidgetAnimator.get().animateWithStartStop(pnlDefaultPlaceholderUI,
					Transition.ZOOMOUTUP, 10, 800,
					() -> pnlDefaultPlaceholderUI.setVisible(true),
					() -> pnlDefaultPlaceholderUI.setVisible(false));
		}
	}

	@Override
	public CorpusUploadService getCorpusUploadService() {
		return mdlCorpusUploadUI;
	}

	@Override
	public CustomKeywordService getCustomKeywordsService() {
		return mdlCustomKeywordsUI;
	}

	@Override
	public VisualizerService getVisualizerService() {
		return mdlVisualizerUI;
	}

	@Override
	public OperationCancelService getCancelService() {
		return pnlDocResultsUI;
	}

	@Override
	public SettingsUrlExporterView getSettingsUrlExporterView() {
		return mdlExporterUI;
	}

	@Override
	public DocumentCompareService getDocumentCompareService() {
		return mdlComparerUI;
	}

	@Override
	public HistoryViewerService getHistoryViewerService() {
		return mdlHistoryUI;
	}
	@Override
	public QuestionGeneratorPreviewService getQuestionGeneratorPreviewService() {
		return mdlQuestGenUI;
	}

	@Override
	public WebSearchService getWebSearchService() {
		return mdlWebSearchUI;
	}

	@Override
	public ExerciseGenerationService getExerciseGenerationService() {
		return pnlDocPreviewUI.wdgtExerciseGeneration;
	}
}
