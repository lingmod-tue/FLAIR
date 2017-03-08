package com.flair.client.views;

import org.gwtbootstrap3.client.ui.AnchorButton;
import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.Modal;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizationEngine;
import com.flair.client.localization.LocalizationLanguage;
import com.flair.client.localization.LocalizedCompositeView;
import com.flair.client.localization.SimpleLocalizedTextWidget;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class MainViewport extends LocalizedCompositeView
{
	static final class MainViewportLocale extends SimpleViewLocale
	{
		static final String		DESC_btnWebSearchUI = "btnWebSearchUI";
		static final String		DESC_btnUploadUI = "btnUploadUI";
		static final String		DESC_btnAboutUI = "btnAboutUI";
		static final String		DESC_btnSwitchLangUI = "btnSwitchLangUI";
		static final String		DESC_btnLangEnUI = "btnLangEnUI";
		static final String		DESC_btnLangDeUI = "btnLangDeUI";

		@Override
		void init()
		{
			// EN
			en.put(DESC_btnWebSearchUI, "Web Search");
			en.put(DESC_btnUploadUI, "Upload Corpus");
			en.put(DESC_btnAboutUI, "About FLAIR");
			en.put(DESC_btnSwitchLangUI, "Language");
			en.put(DESC_btnLangEnUI, "English");
			en.put(DESC_btnLangDeUI, "German");
			
			// DE
			de.put(DESC_btnWebSearchUI, "Internet Suche");
			de.put(DESC_btnUploadUI, "Text Hochladen");
			de.put(DESC_btnAboutUI, "Über FLAIR");
			de.put(DESC_btnSwitchLangUI, "Sprache");
			de.put(DESC_btnLangEnUI, "Englisch");
			de.put(DESC_btnLangDeUI, "Deutsch");
		}
		
		private static final MainViewportLocale		INSTANCE = new MainViewportLocale();
	}
	
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
			mdlThrobberUI.show();
			{
				task.run();
			}
			mdlThrobberUI.hide();
		}
	}
	
	private static MainViewportUiBinder uiBinder = GWT.create(MainViewportUiBinder.class);

	interface MainViewportUiBinder extends UiBinder<Widget, MainViewport> {
	}
	
	@UiField
	SimplePanel									pnlMainUI;
	@UiField
	AnchorListItem								btnWebSearchUI;
	SimpleLocalizedTextWidget<AnchorListItem>			btnWebSearchLC;
	@UiField
	AnchorListItem								btnUploadUI;
	SimpleLocalizedTextWidget<AnchorListItem>			btnUploadLC;
	@UiField
	AnchorListItem								btnAboutUI;
	SimpleLocalizedTextWidget<AnchorListItem>			btnAboutLC;
	@UiField
	AnchorButton								btnSwitchLangUI;
	SimpleLocalizedTextWidget<AnchorButton>			btnSwitchLangLC;
	@UiField
	AnchorListItem								btnLangEnUI;
	SimpleLocalizedTextWidget<AnchorListItem>			btnLangEnLC;
	@UiField
	AnchorListItem								btnLangDeUI;
	SimpleLocalizedTextWidget<AnchorListItem>			btnLangDeLC;
	@UiField
	Modal										mdlAboutEnUI;
	@UiField
	Modal										mdlAboutDeUI;
	@UiField
	Modal										mdlThrobberUI;
	
	private void clearMainPanel() {
		pnlMainUI.clear();
	}
	
	private void attachMainPanel(LocalizedCompositeView view)
	{
		clearMainPanel();
		pnlMainUI.add(view);
	}
	
	private void initLocale()
	{	
		btnWebSearchLC = new SimpleLocalizedTextWidget<>(btnWebSearchUI, MainViewportLocale.DESC_btnWebSearchUI);
		btnUploadLC = new SimpleLocalizedTextWidget<>(btnUploadUI, MainViewportLocale.DESC_btnUploadUI);
		btnAboutLC = new SimpleLocalizedTextWidget<>(btnAboutUI, MainViewportLocale.DESC_btnAboutUI);
		btnSwitchLangLC = new SimpleLocalizedTextWidget<>(btnSwitchLangUI, MainViewportLocale.DESC_btnSwitchLangUI);
		btnLangEnLC = new SimpleLocalizedTextWidget<>(btnLangEnUI, MainViewportLocale.DESC_btnLangEnUI);
		btnLangDeLC = new SimpleLocalizedTextWidget<>(btnLangDeUI, MainViewportLocale.DESC_btnLangDeUI);
		
		registerLocale(MainViewportLocale.INSTANCE.en);
		registerLocale(MainViewportLocale.INSTANCE.de);
		
		registerLocalizedWidget(btnWebSearchLC);
		registerLocalizedWidget(btnUploadLC);
		registerLocalizedWidget(btnAboutLC);
		registerLocalizedWidget(btnSwitchLangLC);
		registerLocalizedWidget(btnLangEnLC);
		registerLocalizedWidget(btnLangDeLC);
	}
	
	private void initHandlers()
	{
		btnWebSearchUI.addClickHandler(e -> {
			attachMainPanel(new WebSearchView(ClientEndPoint.get().getWebRanker()));
		});
		
		btnLangEnUI.addClickHandler(e -> {
			localeCore.setLanguage(LocalizationLanguage.ENGLISH);
		});
		
		btnLangDeUI.addClickHandler(e -> {
			localeCore.setLanguage(LocalizationLanguage.GERMAN);
		});
		
		btnAboutUI.addClickHandler(e -> {
			switch (localeCore.getLanguage())
			{
			case ENGLISH:
				mdlAboutEnUI.show();
				break;
			case GERMAN:
				mdlAboutDeUI.show();
				break;
			}
		});
	}
	
	private void initUI()
	{
		// workaround for the bug that prevents the modal from being closed when it's in the process of fading-in
		mdlThrobberUI.setFade(false);
		
		refreshLocalization();
	}
	
	@UiConstructor
	public MainViewport() 
	{
		super(ClientEndPoint.get().getLocalization());
		initWidget(uiBinder.createAndBindUi(this));

		initLocale();
		initHandlers();
		initUI();
	}
	
}
