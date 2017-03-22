package com.flair.client.presentation.widgets;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.SimpleLocalizedTextButtonWidget;
import com.flair.client.localization.locale.ModalPromptLocale;
import com.flair.client.presentation.interfaces.UserPromptService;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTitle;

public class ModalPrompt extends LocalizedComposite implements UserPromptService
{
	private static ModalPromptUiBinder uiBinder = GWT.create(ModalPromptUiBinder.class);

	interface ModalPromptUiBinder extends UiBinder<Widget, ModalPrompt>
	{
	}
	
	@UiField
	MaterialModal			mdlPromptUI;
	@UiField
	MaterialTitle			lblTextUI;
	@UiField
	MaterialButton			btnYesUI;
	@UiField
	MaterialButton			btnNoUI;
	
	SimpleLocalizedTextButtonWidget<MaterialButton>		btnYesLC;
	SimpleLocalizedTextButtonWidget<MaterialButton>		btnNoLC;
	
	YesHandler				yesHandler;
	NoHandler				noHandler;
	
	private void initLocale()
	{
		btnYesLC = new SimpleLocalizedTextButtonWidget<>(btnYesUI, ModalPromptLocale.DESC_btnYesUI);
		btnNoLC = new SimpleLocalizedTextButtonWidget<>(btnNoUI, ModalPromptLocale.DESC_btnNoUI);
		
		registerLocale(ModalPromptLocale.INSTANCE.en);
		registerLocale(ModalPromptLocale.INSTANCE.de);
		
		registerLocalizedWidget(btnYesLC);
		registerLocalizedWidget(btnNoLC);
		
		refreshLocalization();
	}
	
	private void initHandlers()
	{
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

	public ModalPrompt()
	{
		super(ClientEndPoint.get().getLocalization());
		initWidget(uiBinder.createAndBindUi(this));
		
		initLocale();
		initHandlers();
	}

	@Override
	public void yesNo(String title, String desc, YesHandler yes, NoHandler no) 
	{
		yesHandler = yes;
		noHandler = no;
		
		lblTextUI.setTitle(title);
		lblTextUI.setDescription(desc);
		
		mdlPromptUI.open();
	}

}
