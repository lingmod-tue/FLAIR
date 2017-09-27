package com.flair.client.presentation.widgets;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.SimpleLocalizedListBoxOptionWidget;
import com.flair.client.localization.SimpleLocalizedTextButtonWidget;
import com.flair.client.localization.SimpleLocalizedWidget;
import com.flair.client.localization.locale.LanguageLocale;
import com.flair.client.localization.locale.WebSearchModalLocale;
import com.flair.client.presentation.interfaces.WebSearchService;
import com.flair.shared.grammar.Language;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialListBox;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.html.Option;

public class WebSearchModal extends LocalizedComposite implements WebSearchService
{

	private static WebSearchModalUiBinder uiBinder = GWT.create(WebSearchModalUiBinder.class);

	interface WebSearchModalUiBinder extends UiBinder<Widget, WebSearchModal>
	{
	}

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
	SimpleLocalizedWidget<Option>				selResultLangItmEnLC;
	@UiField
	Option										selResultLangItmDeUI;
	SimpleLocalizedWidget<Option>				selResultLangItmDeLC;
	@UiField
	MaterialButton								btnSearchUI;
	SimpleLocalizedTextButtonWidget<MaterialButton> btnSearchLC;
	@UiField
	MaterialButton								btnCancelUI;
	SimpleLocalizedTextButtonWidget<MaterialButton> btnCancelLC;
	
	SearchHandler		searchHandler;
	
	private void invokeSearch()
	{
		int resultCount = Integer.parseInt(selResultCountUI.getSelectedValue());
		Language searchLang = Language.fromString(selResultLangUI.getSelectedValue());
		String query = txtSearchBoxUI.getText();
		
		searchHandler.handle(searchLang, query, resultCount);
	}
	
	private void initLocale()
	{
		btnSearchLC = new SimpleLocalizedTextButtonWidget<>(btnSearchUI, WebSearchModalLocale.DESC_btnSearchUI);
		btnCancelLC = new SimpleLocalizedTextButtonWidget<>(btnCancelUI, WebSearchModalLocale.DESC_btnCancelUI);
		
		txtSearchBoxLC = new SimpleLocalizedWidget<>(txtSearchBoxUI, WebSearchModalLocale.DESC_txtSearchBoxUI, (w,t) -> w.setLabel(t));
		selResultCountItm10LC = new SimpleLocalizedListBoxOptionWidget(selResultCountItm10UI, WebSearchModalLocale.DESC_selResultCountItm10UI);
		selResultCountItm20LC = new SimpleLocalizedListBoxOptionWidget(selResultCountItm20UI, WebSearchModalLocale.DESC_selResultCountItm20UI);
		selResultCountItm30LC = new SimpleLocalizedListBoxOptionWidget(selResultCountItm30UI, WebSearchModalLocale.DESC_selResultCountItm30UI);
		selResultCountItm40LC = new SimpleLocalizedListBoxOptionWidget(selResultCountItm40UI, WebSearchModalLocale.DESC_selResultCountItm40UI);
		selResultCountItm50LC = new SimpleLocalizedListBoxOptionWidget(selResultCountItm50UI, WebSearchModalLocale.DESC_selResultCountItm50UI);
		selResultLangItmEnLC = new SimpleLocalizedWidget<>(selResultLangItmEnUI, "", (w, s, d) -> {
			w.setText(LanguageLocale.get().getLocalizedName(Language.ENGLISH, d.getLanguage()));
		});
		selResultLangItmDeLC = new SimpleLocalizedWidget<>(selResultLangItmDeUI, "", (w, s, d) -> {
			w.setText(LanguageLocale.get().getLocalizedName(Language.GERMAN, d.getLanguage()));
		});
		
		registerLocale(WebSearchModalLocale.INSTANCE.en);
		registerLocale(WebSearchModalLocale.INSTANCE.de);
		
		registerLocalizedWidget(txtSearchBoxLC);
		registerLocalizedWidget(selResultCountItm10LC);
		registerLocalizedWidget(selResultCountItm20LC);
		registerLocalizedWidget(selResultCountItm30LC);
		registerLocalizedWidget(selResultCountItm40LC);
		registerLocalizedWidget(selResultCountItm50LC);
		registerLocalizedWidget(selResultLangItmEnLC);
		registerLocalizedWidget(selResultLangItmDeLC);
		registerLocalizedWidget(btnSearchLC);
		registerLocalizedWidget(btnCancelLC);

		refreshLocalization();
	}

	private void initHandlers()
	{
		btnSearchUI.addClickHandler(e -> {
			invokeSearch();
			hide();
		});
		btnCancelUI.addClickHandler(e -> hide());
		
		txtSearchBoxUI.addKeyDownHandler(e -> {
			switch (e.getNativeKeyCode())
			{
			case KeyCodes.KEY_ENTER:
				invokeSearch();
				hide();
				break;
			case KeyCodes.KEY_ESCAPE:
				hide();
				break;
			}
		});
	}

	public WebSearchModal()
	{
		super(ClientEndPoint.get().getLocalization());
		initWidget(uiBinder.createAndBindUi(this));
				
		searchHandler = null;

		initLocale();
		initHandlers();
	}

	@Override
	public void setLocalization(Language lang)
	{
		super.setLocalization(lang);

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
		
		// ### need to do this to force update the strings in the listboxes
		// the language listbox is taken care of above, so just select the default result count
		selResultCountUI.setValue(selResultCountItm10UI.getValue());
	}

	@Override
	public void show() {
		mdlWebSearchUI.open();
		txtSearchBoxUI.setFocus(true);
	}

	@Override
	public void hide() {
		mdlWebSearchUI.close();
	}

	@Override
	public void setSearchHandler(SearchHandler handler) {
		searchHandler = handler;
	}

	
}
