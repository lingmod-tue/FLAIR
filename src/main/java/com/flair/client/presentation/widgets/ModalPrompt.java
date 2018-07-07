package com.flair.client.presentation.widgets;

import com.flair.client.localization.CommonLocalizationTags;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedCommonField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.interfaces.UserPromptService;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTitle;

public class ModalPrompt extends LocalizedComposite implements UserPromptService {
	private static ModalPromptUiBinder uiBinder = GWT.create(ModalPromptUiBinder.class);

	interface ModalPromptUiBinder extends UiBinder<Widget, ModalPrompt> {
	}

	private static ModalPromptLocalizationBinder localeBinder = GWT.create(ModalPromptLocalizationBinder.class);

	interface ModalPromptLocalizationBinder extends LocalizationBinder<ModalPrompt> {}


	@UiField
	MaterialModal mdlPromptUI;
	@UiField
	MaterialTitle lblTextUI;
	@UiField
	@LocalizedCommonField(tag = CommonLocalizationTags.YES, type = LocalizedFieldType.TEXT_BUTTON)
	MaterialButton btnYesUI;
	@UiField
	@LocalizedCommonField(tag = CommonLocalizationTags.NO, type = LocalizedFieldType.TEXT_BUTTON)
	MaterialButton btnNoUI;

	YesHandler yesHandler;
	NoHandler noHandler;

	private void initHandlers() {
		btnYesUI.addClickHandler(e -> {
			mdlPromptUI.close();

			if (yesHandler != null)
				yesHandler.handle();
		});

		btnNoUI.addClickHandler(e -> {
			mdlPromptUI.close();

			if (noHandler != null)
				noHandler.handle();
		});
	}

	public ModalPrompt() {
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));
		initHandlers();
	}

	@Override
	public void yesNo(String title, String desc, YesHandler yes, NoHandler no) {
		yesHandler = yes;
		noHandler = no;

		lblTextUI.setTitle(title);
		lblTextUI.setDescription(desc);

		mdlPromptUI.open();
	}

}
