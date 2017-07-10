package com.flair.client.presentation;


import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.SimpleLocalizedListBoxOptionWidget;
import com.flair.client.localization.SimpleLocalizedTextButtonWidget;
import com.flair.client.localization.SimpleLocalizedTextWidget;
import com.flair.client.localization.SimpleLocalizedTooltipWidget;
import com.flair.client.localization.SimpleLocalizedWidget;
import com.flair.client.localization.locale.MainViewportLocale;
import com.flair.client.presentation.interfaces.AbstractDocumentPreviewPane;
import com.flair.client.presentation.interfaces.AbstractDocumentResultsPane;
import com.flair.client.presentation.interfaces.AbstractRankerSettingsPane;
import com.flair.client.presentation.interfaces.AbstractWebRankerPresenter;
import com.flair.client.presentation.interfaces.CorpusUploadService;
import com.flair.client.presentation.interfaces.CustomKeywordService;
import com.flair.client.presentation.interfaces.NotificationService;
import com.flair.client.presentation.interfaces.OperationCancelService;
import com.flair.client.presentation.interfaces.OverlayService;
import com.flair.client.presentation.interfaces.SettingsUrlExporterView;
import com.flair.client.presentation.interfaces.UserPromptService;
import com.flair.client.presentation.interfaces.VisualizerService;
import com.flair.client.presentation.widgets.CorpusFileUploader;
import com.flair.client.presentation.widgets.CustomKeywordsEditor;
import com.flair.client.presentation.widgets.DocumentCollectionVisualizer;
import com.flair.client.presentation.widgets.DocumentPreviewPane;
import com.flair.client.presentation.widgets.DocumentResultsPane;
import com.flair.client.presentation.widgets.ModalPrompt;
import com.flair.client.presentation.widgets.RankerSettingsPane;
import com.flair.client.presentation.widgets.SettingsExporter;
import com.flair.shared.grammar.Language;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.iconmorph.MaterialIconMorph;
import gwt.material.design.addins.client.overlay.MaterialOverlay;
import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.constants.Position;
import gwt.material.design.client.constants.ProgressType;
import gwt.material.design.client.ui.MaterialAnchorButton;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialRow;
import gwt.material.design.client.ui.MaterialSplashScreen;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;
import gwt.material.design.client.ui.html.Option;

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
	MaterialButton								btnWebSearchUI;
	SimpleLocalizedWidget<MaterialButton>		btnWebSearchLC;
	@UiField
	MaterialAnchorButton						btnUploadUI;
	SimpleLocalizedWidget<MaterialAnchorButton>	btnUploadLC;
	@UiField
	MaterialLink								btnSwitchLangUI;
	SimpleLocalizedWidget<MaterialLink>			btnSwitchLangLC;
	@UiField
	MaterialNavBrand							btnAboutUI;
	SimpleLocalizedWidget<MaterialNavBrand>		btnAboutLC;
	@UiField
	MaterialLink								btnLangEnUI;
	SimpleLocalizedWidget<MaterialLink>			btnLangEnLC;
	@UiField
	MaterialLink								btnLangDeUI;
	SimpleLocalizedWidget<MaterialLink>			btnLangDeLC;
	@UiField
	MaterialModal								mdlAboutEnUI;
	@UiField
	MaterialButton								btnAboutEnCloseUI;
	@UiField
	MaterialModal								mdlAboutDeUI;
	@UiField
	MaterialButton								btnAboutDeCloseUI;
	@UiField
	MaterialModal								mdlWebSearchUI;
	@UiField
	MaterialTextBox								txtSearchBoxUI;
	SimpleLocalizedWidget<MaterialTextBox>		txtSearchBoxLC;
	@UiField
	MaterialListBox								selResultCountUI;
	@UiField
	Option										selResultCountItm10UI;
	SimpleLocalizedListBoxOptionWidget			selResultCountItm10LC;
	@UiField
	Option										selResultCountItm20UI;
	SimpleLocalizedListBoxOptionWidget			selResultCountItm20LC;
	@UiField
	Option										selResultCountItm30UI;
	SimpleLocalizedListBoxOptionWidget			selResultCountItm30LC;
	@UiField
	Option										selResultCountItm40UI;
	SimpleLocalizedListBoxOptionWidget			selResultCountItm40LC;
	@UiField
	Option										selResultCountItm50UI;
	SimpleLocalizedListBoxOptionWidget			selResultCountItm50LC;
	@UiField
	MaterialListBox								selResultLangUI;
	@UiField
	Option										selResultLangItmEnUI;
	SimpleLocalizedListBoxOptionWidget			selResultLangItmEnLC;
	@UiField
	Option										selResultLangItmDeUI;
	SimpleLocalizedListBoxOptionWidget			selResultLangItmDeLC;
	@UiField
	MaterialButton								btnDoWebSearchUI;
	SimpleLocalizedTextButtonWidget<MaterialButton> btnDoWebSearchLC;
	@UiField
	MaterialButton								btnCloseWebSearchUI;
	SimpleLocalizedTextButtonWidget<MaterialButton> btnCloseWebSearchLC;
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
	MaterialRow										pnlDefaultPlaceholderUI;
	@UiField
	MaterialCardTitle								lblDefaultSearchTitleUI;
	SimpleLocalizedTextWidget<MaterialCardTitle>	lblDefaultSearchTitleLC;
	@UiField
	MaterialLabel									lblDefaultSearchCaptionUI;
	SimpleLocalizedTextWidget<MaterialLabel>		lblDefaultSearchCaptionLC;
	@UiField
	MaterialCardTitle								lblDefaultConfigTitleUI;
	SimpleLocalizedTextWidget<MaterialCardTitle>	lblDefaultConfigTitleLC;
	@UiField
	MaterialLabel									lblDefaultConfigCaptionUI;
	SimpleLocalizedTextWidget<MaterialLabel>		lblDefaultConfigCaptionLC;
	@UiField
	MaterialCardTitle								lblDefaultUploadTitleUI;
	SimpleLocalizedTextWidget<MaterialCardTitle>	lblDefaultUploadTitleLC;
	@UiField
	MaterialLabel									lblDefaultUploadCaptionUI;
	SimpleLocalizedTextWidget<MaterialLabel>		lblDefaultUploadCaptionLC;
	@UiField
	MaterialOverlay									mdlOverlayUI;
	@UiField
	SettingsExporter								mdlExporterUI;
	
	ToastNotifications								notificationService;
	BasicOverlay									overlayService;
	
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
	
	private void showSearchModal(boolean visible)
	{
		if (visible)
		{
			mdlWebSearchUI.open();
			txtSearchBoxUI.setFocus(true);
		}
		else
			mdlWebSearchUI.close();
	}
	
	private void showUploadModal() {
		mdlCorpusUploadUI.show();
	}
	
	private void updateResultsListGrid()
	{
		boolean settingsVisible = pnlConstructionsSettingsUI.isVisible();
		boolean previewVisible = pnlDocPreviewUI.isVisible();

		if (settingsVisible && previewVisible)
			pnlResultsContainerUI.setGrid("l4 m4 s12");
		else if (settingsVisible ^ previewVisible)
			pnlResultsContainerUI.setGrid("l8 m8 s12");
		else
			pnlResultsContainerUI.setGrid("l12 m12 s12");
	}
	
	private void switchLanguage(Language lang)
	{
		localeCore.setLanguage(lang);
		
		// switch the default search language as well
		switch (lang)
		{
		case ENGLISH:
			selResultLangUI.setValue(selResultLangItmEnUI.getValue());
			break;
		case GERMAN:
			selResultLangUI.setValue(selResultLangItmDeUI.getValue());
			break;
		}
		
		// ### need to do this to force update the strings in the search modal's listboxes
		// the language listbox is taken care of above, so just select the default result count
		selResultCountUI.setValue(selResultCountItm10UI.getValue());
	}
	
	private void invokeSearch()
	{
		int resultCount = Integer.parseInt(selResultCountUI.getSelectedValue());
		Language searchLang = Language.fromString(selResultLangUI.getSelectedValue());
		String query = txtSearchBoxUI.getText();
		
		showSearchModal(false);
		ClientEndPoint.get().getWebRanker().performWebSearch(searchLang, query, resultCount);
	}
	
	private void initLocale()
	{
		btnAboutLC = new SimpleLocalizedTooltipWidget<>(btnAboutUI, MainViewportLocale.DESC_btnAboutUI);
		btnWebSearchLC = new SimpleLocalizedTooltipWidget<>(btnWebSearchUI, MainViewportLocale.DESC_btnWebSearchUI);
		btnUploadLC = new SimpleLocalizedTooltipWidget<>(btnUploadUI, MainViewportLocale.DESC_btnUploadUI);
		btnSwitchLangLC = new SimpleLocalizedTooltipWidget<>(btnSwitchLangUI, MainViewportLocale.DESC_btnSwitchLangUI);
		btnLangEnLC = new SimpleLocalizedTextButtonWidget<>(btnLangEnUI, MainViewportLocale.DESC_btnLangEnUI);
		btnLangDeLC = new SimpleLocalizedTextButtonWidget<>(btnLangDeUI, MainViewportLocale.DESC_btnLangDeUI);
		
		btnDoWebSearchLC = new SimpleLocalizedTextButtonWidget<>(btnDoWebSearchUI, MainViewportLocale.DESC_defSearchTitle);
		btnCloseWebSearchLC = new SimpleLocalizedTextButtonWidget<>(btnCloseWebSearchUI, MainViewportLocale.DESC_btnCloseWebSearchUI);
		
		txtSearchBoxLC = new SimpleLocalizedWidget<>(txtSearchBoxUI, MainViewportLocale.DESC_txtSearchBoxUI, (w,t) -> w.setLabel(t));
		selResultCountItm10LC = new SimpleLocalizedListBoxOptionWidget(selResultCountItm10UI, MainViewportLocale.DESC_selResultCountItm10UI);
		selResultCountItm20LC = new SimpleLocalizedListBoxOptionWidget(selResultCountItm20UI, MainViewportLocale.DESC_selResultCountItm20UI);
		selResultCountItm30LC = new SimpleLocalizedListBoxOptionWidget(selResultCountItm30UI, MainViewportLocale.DESC_selResultCountItm30UI);
		selResultCountItm40LC = new SimpleLocalizedListBoxOptionWidget(selResultCountItm40UI, MainViewportLocale.DESC_selResultCountItm40UI);
		selResultCountItm50LC = new SimpleLocalizedListBoxOptionWidget(selResultCountItm50UI, MainViewportLocale.DESC_selResultCountItm50UI);
		selResultLangItmEnLC = new SimpleLocalizedListBoxOptionWidget(selResultLangItmEnUI, MainViewportLocale.DESC_selResultLangItmEnUI);
		selResultLangItmDeLC = new SimpleLocalizedListBoxOptionWidget(selResultLangItmDeUI, MainViewportLocale.DESC_selResultLangItmDeUI);
		
		lblDefaultSearchTitleLC = new SimpleLocalizedTextWidget<>(lblDefaultSearchTitleUI, MainViewportLocale.DESC_defSearchTitle);
		lblDefaultSearchCaptionLC = new SimpleLocalizedTextWidget<>(lblDefaultSearchCaptionUI, MainViewportLocale.DESC_defSearchCaption);
		lblDefaultConfigTitleLC = new SimpleLocalizedTextWidget<>(lblDefaultConfigTitleUI, MainViewportLocale.DESC_defConfigTitle);
		lblDefaultConfigCaptionLC = new SimpleLocalizedTextWidget<>(lblDefaultConfigCaptionUI, MainViewportLocale.DESC_defConfigCaption);
		lblDefaultUploadTitleLC = new SimpleLocalizedTextWidget<>(lblDefaultUploadTitleUI, MainViewportLocale.DESC_defUploadTitle);
		lblDefaultUploadCaptionLC = new SimpleLocalizedTextWidget<>(lblDefaultUploadCaptionUI, MainViewportLocale.DESC_defUploadCaption);
		
		registerLocale(MainViewportLocale.INSTANCE.en);
		registerLocale(MainViewportLocale.INSTANCE.de);
		
		registerLocalizedWidget(btnAboutLC);
		registerLocalizedWidget(btnWebSearchLC);
		registerLocalizedWidget(btnUploadLC);
		registerLocalizedWidget(btnSwitchLangLC);
		registerLocalizedWidget(btnLangEnLC);
		registerLocalizedWidget(btnLangDeLC);
		registerLocalizedWidget(btnDoWebSearchLC);
		registerLocalizedWidget(btnCloseWebSearchLC);
		registerLocalizedWidget(txtSearchBoxLC);
		registerLocalizedWidget(selResultCountItm10LC);
		registerLocalizedWidget(selResultCountItm20LC);
		registerLocalizedWidget(selResultCountItm30LC);
		registerLocalizedWidget(selResultCountItm40LC);
		registerLocalizedWidget(selResultCountItm50LC);
		registerLocalizedWidget(selResultLangItmEnLC);
		registerLocalizedWidget(selResultLangItmDeLC);
		registerLocalizedWidget(lblDefaultSearchTitleLC);
		registerLocalizedWidget(lblDefaultSearchCaptionLC);
		registerLocalizedWidget(lblDefaultConfigTitleLC);
		registerLocalizedWidget(lblDefaultConfigCaptionLC);
		registerLocalizedWidget(lblDefaultUploadTitleLC);
		registerLocalizedWidget(lblDefaultUploadCaptionLC);
		
		refreshLocalization();
	}
	
	private void initHandlers()
	{
		btnWebSearchUI.addClickHandler(e -> {
			showSearchModal(true);
		});
		
		btnUploadUI.addClickHandler(e -> {
			showUploadModal();
		});
		
		btnLangEnUI.addClickHandler(e -> {
			switchLanguage(Language.ENGLISH);
		});
		
		btnLangDeUI.addClickHandler(e -> {
			switchLanguage(Language.GERMAN);
		});
		
		btnAboutUI.addClickHandler(e -> {
			switch (localeCore.getLanguage())
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
		
		btnDoWebSearchUI.addClickHandler(e-> {
			invokeSearch();
		});
		
		btnCloseWebSearchUI.addClickHandler(e-> {
			showSearchModal(false);
		});
		
		txtSearchBoxUI.addKeyDownHandler(e -> {
			switch (e.getNativeKeyCode())
			{
			case KeyCodes.KEY_ENTER:
				invokeSearch();
				break;
			case KeyCodes.KEY_ESCAPE:
				showSearchModal(false);
				break;
			}
		});
		
		tglSettingsPaneUI.addClickHandler(e -> toggleSettingsPane());
		
		lblDefaultSearchTitleUI.addClickHandler(e -> showSearchModal(true));
		lblDefaultConfigTitleUI.addClickHandler(e -> toggleSettingsPane());
		lblDefaultUploadTitleUI.addClickHandler(e -> showUploadModal());
		
		pnlConstructionsSettingsUI.setShowHideEventHandler(v -> {
			updateResultsListGrid();
			icoSettingsMorphUI.getElement().removeClassName("morphed");
		});
		pnlDocPreviewUI.setShowHideEventHandler(v -> updateResultsListGrid());
		
		mdlCustomKeywordsUI.bindToSlider(pnlConstructionsSettingsUI.getKeywordSlider());
	}
	
	private void initUI()
	{
		selResultLangUI.setMultipleSelect(false);
		
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
		super(ClientEndPoint.get().getLocalization());
		initWidget(uiBinder.createAndBindUi(this));
		
		notificationService = new ToastNotifications();
		overlayService = new BasicOverlay(mdlOverlayUI);
		
		initLocale();
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
	public void showProgressBar(boolean visible, boolean indeterminate)
	{
		if (visible)
			navMainUI.showProgress(indeterminate ? ProgressType.INDETERMINATE : ProgressType.DETERMINATE);
		else
			navMainUI.hideProgress();
	}

	@Override
	public void setProgressBarValue(double val)
	{
		if (val < 0)
			val = 0;
		else if (val > 100)
			val = 100;
		
		navMainUI.setPercent(val);
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
}
