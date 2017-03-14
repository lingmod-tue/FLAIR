package com.flair.client.views;

import org.gwtbootstrap3.client.ui.AnchorButton;

import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.Modal;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizationEngine;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.SimpleLocalizedTextWidget;
import com.flair.client.localization.locale.MainViewportLocale;
import com.flair.client.localization.SimpleLocale;
import com.flair.shared.grammar.Language;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class MainViewport extends LocalizedComposite
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
	SimpleLocalizedTextWidget<AnchorListItem>	btnWebSearchLC;
	@UiField
	AnchorListItem								btnUploadUI;
	SimpleLocalizedTextWidget<AnchorListItem>	btnUploadLC;
	@UiField
	AnchorListItem								btnAboutUI;
	SimpleLocalizedTextWidget<AnchorListItem>	btnAboutLC;
	@UiField
	AnchorButton								btnSwitchLangUI;
	SimpleLocalizedTextWidget<AnchorButton>		btnSwitchLangLC;
	@UiField
	AnchorListItem								btnLangEnUI;
	SimpleLocalizedTextWidget<AnchorListItem>	btnLangEnLC;
	@UiField
	AnchorListItem								btnLangDeUI;
	SimpleLocalizedTextWidget<AnchorListItem>	btnLangDeLC;
	@UiField
	Modal										mdlAboutEnUI;
	@UiField
	Modal										mdlAboutDeUI;
	@UiField
	Modal										mdlThrobberUI;
	
	private void clearMainPanel() {
		pnlMainUI.clear();
	}
	
	private void attachMainPanel(LocalizedComposite view)
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
		
		refreshLocalization();
	}
	
	private void initHandlers()
	{
		btnWebSearchUI.addClickHandler(e -> {
			attachMainPanel(new WebSearchView(ClientEndPoint.get().getWebRanker()));
		});
		
		btnLangEnUI.addClickHandler(e -> {
			localeCore.setLanguage(Language.ENGLISH);
		});
		
		btnLangDeUI.addClickHandler(e -> {
			localeCore.setLanguage(Language.GERMAN);
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
	}
	
	@UiConstructor
	public MainViewport() 
	{
		super(ClientEndPoint.get().getLocalization());
		initWidget(uiBinder.createAndBindUi(this));

		initLocale();
		initHandlers();
		initUI();
		
		attachMainPanel(new WebSearchView(ClientEndPoint.get().getWebRanker()));
	}
	
}
