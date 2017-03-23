package com.flair.client.presentation;


import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.SimpleLocalizedListBoxOptionWidget;
import com.flair.client.localization.SimpleLocalizedTextButtonWidget;
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
import com.flair.client.presentation.interfaces.UserPromptService;
import com.flair.client.presentation.widgets.CorpusFileUploader;
import com.flair.client.presentation.widgets.CustomKeywordsEditor;
import com.flair.client.presentation.widgets.DocumentPreviewPane;
import com.flair.client.presentation.widgets.DocumentResultsPane;
import com.flair.client.presentation.widgets.ModalPrompt;
import com.flair.client.presentation.widgets.RankerSettingsPane;
import com.flair.shared.grammar.Language;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.constants.ProgressType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialLoader;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialNavBar;
import gwt.material.design.client.ui.MaterialNavBrand;
import gwt.material.design.client.ui.MaterialNavSection;
import gwt.material.design.client.ui.MaterialPanel;
import gwt.material.design.client.ui.MaterialSplashScreen;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.animate.MaterialAnimation;
import gwt.material.design.client.ui.animate.Transition;
import gwt.material.design.client.ui.html.Option;

public class MainViewport extends LocalizedComposite implements AbstractWebRankerPresenter
{
	/*
	 * Performs a modal operation that disables the UI during execution
	 */
	class ModalOperation
	{
		private final Runnable	task;
		
		public ModalOperation(Runnable task) {
			this.task = task;
		}
		
		public void run()
		{
			MaterialLoader.showLoading(true);
			{
				task.run();
			}
			MaterialLoader.showLoading(false);
		}
	}
	
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
	MaterialNavSection							pnlNavMainUI;
	@UiField
	MaterialNavSection							pnlNavSearchUI;
	@UiField
	MaterialLink								btnWebSearchUI;
	SimpleLocalizedWidget<MaterialLink>			btnWebSearchLC;
	@UiField
	MaterialLink								btnUploadUI;
	SimpleLocalizedWidget<MaterialLink>			btnUploadLC;
	@UiField
	MaterialLink								btnSwitchLangUI;
	SimpleLocalizedWidget<MaterialLink>			btnSwitchLangLC;
	@UiField
	MaterialNavBrand							btnAboutUI;
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
	MaterialTextBox								txtSearchBoxUI;
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
	MaterialLink								btnCloseSearchUI;
	@UiField
	MaterialLink								tglSettingsPaneUI;
	@UiField
	MaterialLink								tglDocPreviewPaneUI;
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
	
	ToastNotifications							notificationService;
	boolean										settingsVisible;
	boolean										previewVisible;
	
	private static final double					SETTINGS_PANEL_WIDTH = 400;
	
	private void showSearchNav(boolean visible)
	{
		MaterialAnimation fadeout = new MaterialAnimation(), fadein = new MaterialAnimation();
		
		fadeout.setTransition(Transition.FADEOUTLEFT);
		fadeout.setDelayMillis(10);
		fadeout.setDurationMillis(250);
		fadeout.setInfinite(false);
		
		fadein.setTransition(Transition.FADEINRIGHT);
		fadein.setDelayMillis(10);
		fadein.setDurationMillis(250);
		fadein.setInfinite(false);
		
		if (visible)
		{	
			fadeout.animate(pnlNavMainUI, () -> {
				pnlNavMainUI.setVisible(false);
				pnlNavSearchUI.setVisible(true);
				
				fadein.animate(pnlNavSearchUI, () -> {
					pnlNavSearchUI.setVisible(true);
					txtSearchBoxUI.setFocus(true);
				});
			});
		}
		else
		{
			fadeout.animate(pnlNavSearchUI, () -> {
				pnlNavSearchUI.setVisible(false);
				pnlNavMainUI.setVisible(true);
				
				fadein.animate(pnlNavMainUI, () -> {
					pnlNavMainUI.setVisible(true);
				});
			});
		}
	}
	
	private void showUploadModal() {
		mdlCorpusUploadUI.show();
	}
	
	private void updateResultsListGrid()
	{
//		if (settingsVisible && previewVisible)
//			pnlResultsContainerUI.setGrid("l4 m4 s12");
//		else if (settingsVisible ^ previewVisible)
//			pnlResultsContainerUI.setGrid("l8 m8 s12");
//		else
//			pnlResultsContainerUI.setGrid("l12 m12 s12");
	}
	
	private void showSettingsPane(boolean visible)
	{
		if (visible == settingsVisible)
			return;
		
		settingsVisible = visible;
		
		if (settingsVisible)
		{
//			pnlResultsContainerUI.setLeft(SETTINGS_PANEL_WIDTH);
			pnlConstructionsSettingsUI.show();
		}
		else
		{
//			pnlResultsContainerUI.setLeft(0);
			pnlConstructionsSettingsUI.hide();;
		}
		
		updateResultsListGrid();
	}
	
	private void showPreviewPane(boolean visible)
	{
		if (visible == previewVisible)
			return;
		
		previewVisible = visible;
		
		if (previewVisible)
			pnlDocPreviewUI.show();
		else
			pnlDocPreviewUI.hide();
		
		updateResultsListGrid();
	}
	
	private void switchLanguage(Language lang)
	{
		localeCore.setLanguage(lang);
	}
	
	private void invokeSearch()
	{
		int resultCount = Integer.parseInt(selResultCountUI.getSelectedValue());
		Language searchLang = Language.fromString(selResultLangUI.getSelectedValue());
		String query = txtSearchBoxUI.getText();
		
		ClientEndPoint.get().getWebRanker().performWebSearch(searchLang, query, resultCount);
	}
	
	private void initLocale()
	{	
		btnWebSearchLC = new SimpleLocalizedTooltipWidget<>(btnWebSearchUI, MainViewportLocale.DESC_btnWebSearchUI);
		btnUploadLC = new SimpleLocalizedTooltipWidget<>(btnUploadUI, MainViewportLocale.DESC_btnUploadUI);
		btnSwitchLangLC = new SimpleLocalizedTooltipWidget<>(btnSwitchLangUI, MainViewportLocale.DESC_btnSwitchLangUI);
		btnLangEnLC = new SimpleLocalizedTextButtonWidget<>(btnLangEnUI, MainViewportLocale.DESC_btnLangEnUI);
		btnLangDeLC = new SimpleLocalizedTextButtonWidget<>(btnLangDeUI, MainViewportLocale.DESC_btnLangDeUI);
	
		selResultCountItm10LC = new SimpleLocalizedListBoxOptionWidget(selResultCountItm10UI, MainViewportLocale.DESC_selResultCountItm10UI);
		selResultCountItm20LC = new SimpleLocalizedListBoxOptionWidget(selResultCountItm20UI, MainViewportLocale.DESC_selResultCountItm20UI);
		selResultCountItm30LC = new SimpleLocalizedListBoxOptionWidget(selResultCountItm30UI, MainViewportLocale.DESC_selResultCountItm30UI);
		selResultCountItm40LC = new SimpleLocalizedListBoxOptionWidget(selResultCountItm40UI, MainViewportLocale.DESC_selResultCountItm40UI);
		selResultCountItm50LC = new SimpleLocalizedListBoxOptionWidget(selResultCountItm50UI, MainViewportLocale.DESC_selResultCountItm50UI);
		selResultLangItmEnLC = new SimpleLocalizedListBoxOptionWidget(selResultLangItmEnUI, MainViewportLocale.DESC_selResultLangItmEnUI);
		selResultLangItmDeLC = new SimpleLocalizedListBoxOptionWidget(selResultLangItmDeUI, MainViewportLocale.DESC_selResultLangItmDeUI);
		
		registerLocale(MainViewportLocale.INSTANCE.en);
		registerLocale(MainViewportLocale.INSTANCE.de);
		
		registerLocalizedWidget(btnWebSearchLC);
		registerLocalizedWidget(btnUploadLC);
		registerLocalizedWidget(btnSwitchLangLC);
		registerLocalizedWidget(btnLangEnLC);
		registerLocalizedWidget(btnLangDeLC);
		registerLocalizedWidget(selResultCountItm10LC);
		registerLocalizedWidget(selResultCountItm20LC);
		registerLocalizedWidget(selResultCountItm30LC);
		registerLocalizedWidget(selResultCountItm40LC);
		registerLocalizedWidget(selResultCountItm50LC);
		registerLocalizedWidget(selResultLangItmEnLC);
		registerLocalizedWidget(selResultLangItmDeLC);
		
		refreshLocalization();
	}
	
	private void initHandlers()
	{
		btnWebSearchUI.addClickHandler(e -> {
			showSearchNav(true);
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
		
		btnCloseSearchUI.addClickHandler(e-> {
			showSearchNav(false);	
			txtSearchBoxUI.setText("");
		});
		
		txtSearchBoxUI.addKeyDownHandler(e -> {
			switch (e.getNativeKeyCode())
			{
			case KeyCodes.KEY_ENTER:
				invokeSearch();
				break;
			case KeyCodes.KEY_ESCAPE:
				showSearchNav(false);
				txtSearchBoxUI.setText("");
				break;
			} 
		});
		
		tglSettingsPaneUI.addClickHandler(e -> {
			showSettingsPane(settingsVisible == false);
		});
		
		tglDocPreviewPaneUI.addClickHandler(e -> {
			showPreviewPane(previewVisible == false);
		});		
		
		mdlCustomKeywordsUI.bindToSlider(pnlConstructionsSettingsUI.getKeywordSlider());
	}
	
	private void initUI()
	{		
		showSearchNav(false);
		showSettingsPane(false);
		showPreviewPane(false);
	}
	
	@UiConstructor
	public MainViewport() 
	{
		super(ClientEndPoint.get().getLocalization());
		initWidget(uiBinder.createAndBindUi(this));
		
		notificationService = new ToastNotifications();
		
		initLocale();
		initHandlers();
		initUI();
	}
	
	@Override
	public void setLocalization(Language lang)
	{
		super.setLocalization(lang);
	}
	
	public void showSplash() 
	{
		splSplashUI.show();
		
		MaterialAnimation pulse = new MaterialAnimation(htmlSplashLogoUI);
		pulse.setTransition(Transition.PULSE);
		pulse.setDelayMillis(10);
		pulse.setDurationMillis(20);
		pulse.setInfinite(true);
		pulse.animate();
	}
	
	public void hideSplash() 
	{
		MaterialAnimation fadeout = new MaterialAnimation();
		
		fadeout.setTransition(Transition.FLIPOUTX);
		fadeout.setDelayMillis(30);
		fadeout.setDurationMillis(10);
		fadeout.setInfinite(false);
		fadeout.animate(splSplashUI, () -> {
			splSplashUI.hide();
		});		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showDefaultPane(boolean visible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CorpusUploadService getCorpusUploadService() {
		return mdlCorpusUploadUI;
	}

	@Override
	public CustomKeywordService getCustomKeywordsService() {
		return mdlCustomKeywordsUI;
	}
}
