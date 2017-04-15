package com.flair.client.presentation.widgets;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.locale.SettingsExporterLocale;
import com.flair.client.presentation.interfaces.SettingsUrlExporterView;
import com.flair.shared.grammar.Language;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;

public class SettingsExporter extends LocalizedComposite implements SettingsUrlExporterView
{

	private static SettingsExporterUiBinder uiBinder = GWT.create(SettingsExporterUiBinder.class);

	interface SettingsExporterUiBinder extends UiBinder<Widget, SettingsExporter>
	{
	}
	
	@UiField
	MaterialModal			mdlExporterUI;
	@UiField
	MaterialTitle			lblTitleUI;
	@UiField
	MaterialTextBox			txtURLUI;
	@UiField
	MaterialButton			btnCloseUI;
	
	private void initLocale()
	{
		registerLocale(SettingsExporterLocale.INSTANCE.en);
		registerLocale(SettingsExporterLocale.INSTANCE.de);
		
		refreshLocalization();
	}
	
	private void initHandlers()
	{
		btnCloseUI.addClickHandler(e -> {
			hide();
		});
	}

	public SettingsExporter()
	{
		super(ClientEndPoint.get().getLocalization());
		initWidget(uiBinder.createAndBindUi(this));
		
		initLocale();
		initHandlers();
	}

	@Override
	public void setLocalization(Language lang)
	{
		super.setLocalization(lang);
		
		lblTitleUI.setTitle(getLocalizedString(SettingsExporterLocale.DESC_lblTitleUI));
		lblTitleUI.setDescription(getLocalizedString(SettingsExporterLocale.DESC_lblCaptionUI));
		btnCloseUI.setText(getLocalizedString(SettingsExporterLocale.DESC_btnCloseUI));
	}

	@Override
	public void show(String url)
	{
		txtURLUI.setText(url);
		mdlExporterUI.open();
	}

	@Override
	public void hide() {
		mdlExporterUI.close();
	}
	
}
