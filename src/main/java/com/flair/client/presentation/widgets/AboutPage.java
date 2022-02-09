package com.flair.client.presentation.widgets;

import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;

public class AboutPage extends LocalizedComposite {

	private static AboutPageUiBinder uiBinder = GWT.create(AboutPageUiBinder.class);

	interface AboutPageUiBinder extends UiBinder<Widget, AboutPage> {
	}

	private static AboutPageLocalizationBinder localeBinder = GWT.create(AboutPageLocalizationBinder.class);

	interface AboutPageLocalizationBinder extends LocalizationBinder<AboutPage> {}

	@UiField
	MaterialDialog mdlAboutUI;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_BASIC)
	MaterialLabel lblDescIntro;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_BASIC)
	MaterialLabel lblSearchBullet;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_BASIC)
	MaterialLabel lblAnaylzeBullet;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_BASIC)
	MaterialLabel lblRerankBullet;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_BASIC)
	MaterialLabel lblPapers;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
	MaterialLink lblThesis;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
	MaterialLink lblBEA;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
	MaterialLink lblSystem;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_BASIC)
	MaterialLabel lblAdditional;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
	MaterialLink lblAWL;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_BASIC)
	MaterialLabel lblThirdParty;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_BASIC)
	MaterialLabel lblLicense;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_BASIC)
	MaterialLabel lblLicenseStmt;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_BUTTON)
	MaterialButton btnAboutCloseUI;

	private void initHandlers() {
		btnAboutCloseUI.addClickHandler(e -> {
			mdlAboutUI.close();
		});

	}

	public AboutPage() {
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));

		initHandlers();
	}

	public void show() {
		mdlAboutUI.open();
	}

}
