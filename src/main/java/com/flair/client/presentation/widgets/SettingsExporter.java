package com.flair.client.presentation.widgets;

import com.flair.client.localization.CommonLocalizationTags;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedCommonField;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.interfaces.SettingsUrlExporterView;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialTitle;

public class SettingsExporter extends LocalizedComposite implements SettingsUrlExporterView {

	private static SettingsExporterUiBinder uiBinder = GWT.create(SettingsExporterUiBinder.class);

	interface SettingsExporterUiBinder extends UiBinder<Widget, SettingsExporter> {
	}

	private static SettingsExporterLocalizationBinder localeBinder = GWT.create(SettingsExporterLocalizationBinder.class);

	interface SettingsExporterLocalizationBinder extends LocalizationBinder<SettingsExporter> {}


	@UiField
	MaterialModal mdlExporterUI;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_TITLE)
	@LocalizedField(tag = "lblTitleUI_Desc", type = LocalizedFieldType.TEXT_DESCRIPTION)
	MaterialTitle lblTitleUI;
	@UiField
	MaterialTextBox txtURLUI;
	@UiField
	@LocalizedCommonField(tag = CommonLocalizationTags.CLOSE, type = LocalizedFieldType.TEXT_BUTTON)
	MaterialButton btnCloseUI;

	private void initHandlers() {
		btnCloseUI.addClickHandler(e -> {
			hide();
		});
	}

	public SettingsExporter() {
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));
		initHandlers();
	}

	@Override
	public void show(String url) {
		txtURLUI.setText(url);
		mdlExporterUI.open();
	}

	@Override
	public void hide() {
		mdlExporterUI.close();
	}

}
