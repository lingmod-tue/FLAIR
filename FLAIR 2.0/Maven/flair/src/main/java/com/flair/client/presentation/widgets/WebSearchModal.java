package com.flair.client.presentation.widgets;

import com.flair.client.localization.CommonLocalizationTags;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedCommonField;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
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

	private static WebSearchModalLocalizationBinder localeBinder = GWT.create(WebSearchModalLocalizationBinder.class);
	interface WebSearchModalLocalizationBinder extends LocalizationBinder<WebSearchModal> {}
	
	
	@UiField
	MaterialModal								mdlWebSearchUI;
	@UiField
	@LocalizedField(type=LocalizedFieldType.TEXTBOX_LABEL)
	MaterialTextBox								txtSearchBoxUI;
	@UiField
	MaterialListBox								selResultCountUI;
	@UiField
	@LocalizedField(type=LocalizedFieldType.LISTBOX_OPTION)
	Option										selResultCountItm10UI;
	@UiField
	@LocalizedField(type=LocalizedFieldType.LISTBOX_OPTION)
	Option										selResultCountItm20UI;
	@UiField
	@LocalizedField(type=LocalizedFieldType.LISTBOX_OPTION)
	Option										selResultCountItm30UI;
	@UiField
	@LocalizedField(type=LocalizedFieldType.LISTBOX_OPTION)
	Option										selResultCountItm40UI;
	@UiField
	@LocalizedField(type=LocalizedFieldType.LISTBOX_OPTION)
	Option										selResultCountItm50UI;
	@UiField
	MaterialListBox								selResultLangUI;
	@UiField
	@LocalizedCommonField(tag=CommonLocalizationTags.LANGUAGE_ENGLISH, type=LocalizedFieldType.LISTBOX_OPTION)
	Option										selResultLangItmEnUI;
	@UiField
	@LocalizedCommonField(tag=CommonLocalizationTags.LANGUAGE_GERMAN, type=LocalizedFieldType.LISTBOX_OPTION)
	Option										selResultLangItmDeUI;
	@UiField
	@LocalizedCommonField(tag=CommonLocalizationTags.SEARCH, type=LocalizedFieldType.TEXT_BUTTON)
	MaterialButton								btnSearchUI;
	@UiField
	@LocalizedCommonField(tag=CommonLocalizationTags.CANCEL, type=LocalizedFieldType.TEXT_BUTTON)
	MaterialButton								btnCancelUI;
	
	SearchHandler		searchHandler;
	
	private void invokeSearch()
	{
		int resultCount = Integer.parseInt(selResultCountUI.getSelectedValue());
		Language searchLang = Language.fromString(selResultLangUI.getSelectedValue());
		String query = txtSearchBoxUI.getText();
		
		searchHandler.handle(searchLang, query, resultCount);
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
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));
				
		searchHandler = null;
		initHandlers();
	}

	@Override
	public void setLocale(Language lang)
	{
		super.setLocale(lang);

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
