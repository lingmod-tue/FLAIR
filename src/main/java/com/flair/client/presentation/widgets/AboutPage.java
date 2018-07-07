package com.flair.client.presentation.widgets;

import com.flair.client.localization.LocalizationEngine;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialModal;

public class AboutPage extends LocalizedComposite {

	private static AboutPageUiBinder uiBinder = GWT.create(AboutPageUiBinder.class);

	interface AboutPageUiBinder extends UiBinder<Widget, AboutPage> {
	}

	private static AboutPageLocalizationBinder localeBinder = GWT.create(AboutPageLocalizationBinder.class);

	interface AboutPageLocalizationBinder extends LocalizationBinder<AboutPage> {}

	@UiField
	MaterialModal mdlAboutEnUI;
	@UiField
	MaterialButton btnAboutEnCloseUI;
	@UiField
	MaterialModal mdlAboutDeUI;
	@UiField
	MaterialButton btnAboutDeCloseUI;

	private void initHandlers() {
		btnAboutEnCloseUI.addClickHandler(e -> {
			mdlAboutEnUI.close();
		});

		btnAboutDeCloseUI.addClickHandler(e -> {
			mdlAboutDeUI.close();
		});
	}

	public AboutPage() {
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));

		initHandlers();
	}

	public void show() {
		switch (LocalizationEngine.get().getLanguage()) {
		case ENGLISH:
			mdlAboutEnUI.open();
			break;
		case GERMAN:
			mdlAboutDeUI.open();
			break;
		}
	}

	public void hide() {
		mdlAboutEnUI.close();
		mdlAboutDeUI.close();
	}
}
