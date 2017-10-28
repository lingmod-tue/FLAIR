package com.flair.client.presentation;


import com.flair.client.ClientEndPoint;
import com.flair.client.localization.CommonLocalizationTags;
import com.flair.client.localization.LocalizationEngine;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedCommonField;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.interfaces.AbstractDocumentPreviewPane;
import com.flair.client.presentation.interfaces.AbstractDocumentResultsPane;
import com.flair.client.presentation.interfaces.AbstractRankerSettingsPane;
import com.flair.client.presentation.interfaces.AbstractWebRankerPresenter;
import com.flair.client.presentation.interfaces.CorpusUploadService;
import com.flair.client.presentation.interfaces.CustomKeywordService;
import com.flair.client.presentation.interfaces.DocumentCompareService;
import com.flair.client.presentation.interfaces.HistoryViewerService;
import com.flair.client.presentation.interfaces.NotificationService;
import com.flair.client.presentation.interfaces.OperationCancelService;
import com.flair.client.presentation.interfaces.OverlayService;
import com.flair.client.presentation.interfaces.SettingsUrlExporterView;
import com.flair.client.presentation.interfaces.UserPromptService;
import com.flair.client.presentation.interfaces.VisualizerService;
import com.flair.client.presentation.interfaces.WebSearchService;
import com.flair.client.presentation.widgets.CorpusFileUploader;
import com.flair.client.presentation.widgets.CustomKeywordsEditor;
import com.flair.client.presentation.widgets.DocumentCollectionVisualizer;
import com.flair.client.presentation.widgets.DocumentComparer;
import com.flair.client.presentation.widgets.DocumentPreviewPane;
import com.flair.client.presentation.widgets.DocumentResultsPane;
import com.flair.client.presentation.widgets.HistoryViewer;
import com.flair.client.presentation.widgets.ModalPrompt;
import com.flair.client.presentation.widgets.RankerSettingsPane;
import com.flair.client.presentation.widgets.SettingsExporter;
import com.flair.client.presentation.widgets.WebSearchModal;
import com.flair.client.utilities.GlobalWidgetAnimator;
import com.flair.shared.grammar.Language;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.iconmorph.MaterialIconMorph;
import gwt.material.design.addins.client.overlay.MaterialOverlay;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.Position;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSplashScreen;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;

public class MainViewport extends LocalizedComposite implements AbstractWebRankerPresenter
{
	static final class ToastNotifications implements NotificationService
	{
		private static final int			DEFAULT_TIMEOUT_MS = 3 * 1000;
		@Override
		public void notify(String text) {
			MaterialToast.fireToast(text, DEFAULT_TIMEOUT_MS);
		}

		@Override
		public void notify(String text, int timeout) {
			MaterialToast.fireToast(text, timeout);
		}
	}
	
	static final class BasicOverlay implements OverlayService
	{
		private final MaterialOverlay			overlay;
		
		public BasicOverlay(MaterialOverlay o) {
			overlay = o;
		}

		@Override
		public MaterialWidget getOverlay() {
			return overlay;
		}

		@Override
		public void show(MaterialWidget source, Widget content)
		{
			overlay.clear();
			overlay.add(content);
			overlay.open(source);
		}

		@Override
		public void hide() {
			overlay.close();
		}
	}
	
	private static MainViewportUiBinder uiBinder = GWT.create(MainViewportUiBinder.class);
	interface MainViewportUiBinder extends UiBinder<Widget, MainViewport> {
	}
	
	private static MainViewportLocalizationBinder localeBinder = GWT.create(MainViewportLocalizationBinder.class);
	interface MainViewportLocalizationBinder extends LocalizationBinder<MainViewport> {}
	
	@UiField
	MaterialPanel								pnlRootUI;
	@UiField
	MaterialSplashScreen						splSplashUI;
	@UiField
	MaterialTitle								lblSplashStatus;
	@UiField
	HTML										htmlSplashLogoUI;
	@UiField
	MaterialNavBar								navMainUI;
	@UiField
	@LocalizedField(type=LocalizedFieldType.TOOLTIP_MATERIAL)
	MaterialButton								btnWebSearchUI;
	@UiField
	@LocalizedField(type=LocalizedFieldType.TOOLTIP_MATERIAL)
	MaterialAnchorButton						btnUploadUI;
	@UiField
	@LocalizedField(type=LocalizedFieldType.TOOLTIP_MATERIAL)
	MaterialLink								btnHistoryUI;
	@UiField
	@LocalizedField(type=LocalizedFieldType.TOOLTIP_MATERIAL)
	MaterialLink								btnSwitchLangUI;
	@UiField
	@LocalizedField(type=LocalizedFieldType.TOOLTIP_MATERIAL)
	MaterialNavBrand							btnAboutUI;
	@UiField
	@LocalizedCommonField(tag=CommonLocalizationTags.LANGUAGE_ENGLISH, type=LocalizedFieldType.TEXT_BUTTON)
	MaterialLink								btnLangEnUI;
	@UiField
	@LocalizedCommonField(tag=CommonLocalizationTags.LANGUAGE_GERMAN, type=LocalizedFieldType.TEXT_BUTTON)
	MaterialLink								btnLangDeUI;
	@UiField
	MaterialModal								mdlAboutEnUI;
	@UiField
	MaterialButton								btnAboutEnCloseUI;
	@UiField
	MaterialModal								mdlAboutDeUI;
	@UiField
	MaterialButton								btnAboutDeCloseUI;
	@UiField
	WebSearchModal								mdlWebSearchUI;
	@UiField
	MaterialLink								tglSettingsPaneUI;
	@UiField
	MaterialIconMorph							icoSettingsMorphUI;
	@UiField
	RankerSettingsPane							pnlConstructionsSettingsUI;
	@UiField
	DocumentResultsPane							pnlDocResultsUI;
	@UiField
	DocumentPreviewPane							pnlDocPreviewUI;
	@UiField
	ModalPrompt 								mdlPromptUI;
	@UiField
	CorpusFileUploader 							mdlCorpusUploadUI;
	@UiField
	CustomKeywordsEditor 						mdlCustomKeywordsUI;
	@UiField
	MaterialPanel								pnlResultsContainerUI;
	@UiField
	DocumentCollectionVisualizer				mdlVisualizerUI;
	@UiField
	MaterialRow									pnlDefaultPlaceholderUI;
	@UiField
	@LocalizedCommonField(tag=CommonLocalizationTags.SEARCH)
	MaterialCardTitle							lblDefaultSearchTitleUI;
	@UiField
	@LocalizedField
	MaterialLabel								lblDefaultSearchCaptionUI;
	@UiField
	@LocalizedField
	MaterialCardTitle							lblDefaultConfigTitleUI;
	@UiField
	@LocalizedField
	MaterialLabel								lblDefaultConfigCaptionUI;
	@UiField
	@LocalizedCommonField(tag=CommonLocalizationTags.UPLOAD)
	MaterialCardTitle							lblDefaultUploadTitleUI;
	@UiField
	@LocalizedField
	MaterialLabel								lblDefaultUploadCaptionUI;
	@UiField
	MaterialOverlay								mdlOverlayUI;
	@UiField
	SettingsExporter							mdlExporterUI;
	@UiField
	DocumentComparer							mdlComparerUI;
	@UiField
	HistoryViewer								mdlHistoryUI;
	ToastNotifications							notificationService;
	BasicOverlay								overlayService;
	
	private void invokeAtomicOperation(Runnable handler)
	{
		if (ClientEndPoint.get().getWebRanker().isOperationInProgress())
		{
			// prompt the user if they want to cancel the currently running operation
			String title = getLocalizedString(CommonLocalizationTags.OPERATION_INPROGRESS_CONFIRM_TITLE.toString());
			String caption = getLocalizedString(CommonLocalizationTags.OPERATION_INPROGRESS_CONFIRM_CAPTION.toString());

			mdlPromptUI.yesNo(title, caption, () -> {
				ClientEndPoint.get().getWebRanker().cancelCurrentOperation();
				handler.run();
			}, () -> {});
		}
		else
			handler.run();
	}
	
	private void toggleSettingsPane()
	{
		if (pnlConstructionsSettingsUI.isVisible())
		{
			pnlConstructionsSettingsUI.hide();
			icoSettingsMorphUI.getElement().removeClassName("morphed");
		}
		else
		{
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
	
	private void initHandlers()
	{
		btnWebSearchUI.addClickHandler(e -> showSearchModal());
		btnUploadUI.addClickHandler(e -> showUploadModal());
		btnHistoryUI.addClickHandler(e -> mdlHistoryUI.show());
		
		btnLangEnUI.addClickHandler(e -> {
			switchDisplayLanguage(Language.ENGLISH);
		});
		
		btnLangDeUI.addClickHandler(e -> {
			switchDisplayLanguage(Language.GERMAN);
		});
		
		btnAboutUI.addClickHandler(e -> {
			switch (LocalizationEngine.get().getLanguage())
			{
			case ENGLISH:
				mdlAboutEnUI.open();
				break;
			case GERMAN:
				mdlAboutDeUI.open();
				break;
			}
		});
		
		btnAboutEnCloseUI.addClickHandler(e-> {
			mdlAboutEnUI.close();
		});
		
		btnAboutDeCloseUI.addClickHandler(e-> {
			mdlAboutDeUI.close();
		});
		
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
	
	private void initUI()
	{
		btnWebSearchUI.setTooltipPosition(Position.LEFT);
		btnUploadUI.setTooltipPosition(Position.LEFT);
		btnSwitchLangUI.setTooltipPosition(Position.LEFT);
		
		// disable the settings toggle icon morph's default behaviour (this ought to be a part of the API)
		icoSettingsMorphUI.getElement().removeAttribute("onclick");
		
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
	}
		
	@UiConstructor
	public MainViewport()
	{
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));
		
		notificationService = new ToastNotifications();
		overlayService = new BasicOverlay(mdlOverlayUI);
		
		initHandlers();
		initUI();
	}
	
	public void showSplash(boolean visible)
	{
		if (visible)
		{
			splSplashUI.show();
			
			MaterialAnimation pulse = new MaterialAnimation(htmlSplashLogoUI);
			pulse.setTransition(Transition.PULSE);
			pulse.setDelay(10);
			pulse.setDuration(2000);
			pulse.setInfinite(true);
			pulse.animate();
		}
		else
		{
			MaterialAnimation fadeout = new MaterialAnimation();
			
			fadeout.setTransition(Transition.SLIDEOUTDOWN);
			fadeout.setDelay(1500);
			fadeout.setDuration(850);
			fadeout.setInfinite(false);
			fadeout.animate(splSplashUI, () -> {
				splSplashUI.hide();
			});
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
	public NotificationService getNotificationService() {
		return notificationService;
	}

	@Override
	public void showLoaderOverlay(boolean visible) {
		MaterialLoader.showLoading(visible);
	}

	@Override
	public void showProgressBar(boolean visible) {
		MaterialLoader.showProgress(visible);
	}
	
	@Override
	public void showCancelPane(boolean visible) {
		pnlDocResultsUI.setCancelVisible(visible);
	}

	@Override
	public void showDefaultPane(boolean visible)
	{
		if (visible)
		{
			GlobalWidgetAnimator.get().animateWithStart(pnlDefaultPlaceholderUI,
					Transition.ZOOMINUP, 10, 800, () -> pnlDefaultPlaceholderUI.setVisible(true));
		}
		else
		{
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
	public OverlayService getOverlayService() {
		return overlayService;
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
	public WebSearchService getWebSearchService() {
		return mdlWebSearchUI;
	}
}
