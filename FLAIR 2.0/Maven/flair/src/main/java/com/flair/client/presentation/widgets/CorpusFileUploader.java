package com.flair.client.presentation.widgets;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.SimpleLocalizedTextButtonWidget;
import com.flair.client.localization.locale.CorpusFileUploaderLocale;
import com.flair.client.presentation.interfaces.CorpusUploadService;
import com.flair.shared.grammar.Language;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.addins.client.fileuploader.MaterialFileUploader;
import gwt.material.design.addins.client.fileuploader.MaterialUploadLabel;
import gwt.material.design.addins.client.fileuploader.base.UploadFile;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialToast;

public class CorpusFileUploader extends LocalizedComposite implements CorpusUploadService
{
	private static CorpusFileUploaderUiBinder uiBinder = GWT.create(CorpusFileUploaderUiBinder.class);

	interface CorpusFileUploaderUiBinder extends UiBinder<Widget, CorpusFileUploader>
	{
	}

	@UiField
	MaterialModal			mdlUploadUI;
	@UiField
	MaterialIcon			icoCloseUI;
	@UiField
	MaterialFileUploader 	uplUploaderUI;
	@UiField
	MaterialUploadLabel		lblUploadTextUI;
	@UiField
	MaterialButton			btnStartUploadUI;
	@UiField
	MaterialButton			btnEndUploadUI;
	
	SimpleLocalizedTextButtonWidget<MaterialButton>		btnStartUploadLC;
	SimpleLocalizedTextButtonWidget<MaterialButton>		btnEndUploadLC;

	UploadBeginHandler		beginHandler;
	UploadCompleteHandler	completeHandler;

	boolean					uploadInProgress;

	private void onBeginUpload()
	{
		if (uploadInProgress)
			throw new RuntimeException("Previous upload operation not complete");

		if (beginHandler != null)
			beginHandler.handle();
		
		uploadInProgress = true;

		btnStartUploadUI.setEnabled(false);
		uplUploaderUI.setEnabled(true);
		btnEndUploadUI.setEnabled(true);
		icoCloseUI.setVisible(false);
	}

	private void onEndUpload()
	{
		if (uploadInProgress == false)
			throw new RuntimeException("Upload hasn't started yet");

		if (completeHandler != null)
			completeHandler.handle();
		
		uploadInProgress = false;

		hide();
		resetUI();
	}

	private void onUploadError(UploadFile file) {
		MaterialToast.fireToast(getLocalizedString(CorpusFileUploaderLocale.DESC_UploadFailed) + ": " + file.getName());
	}

	private void onMaxFilesReached() {
		MaterialToast.fireToast(getLocalizedString(CorpusFileUploaderLocale.DESC_MaxFiles));
	}
	
	private void resetUI()
	{
		btnStartUploadUI.setEnabled(true);
		uplUploaderUI.setEnabled(false);
		btnEndUploadUI.setEnabled(false);
		icoCloseUI.setVisible(true);
	}

	private void initLocale()
	{
		btnStartUploadLC = new SimpleLocalizedTextButtonWidget<>(btnStartUploadUI, CorpusFileUploaderLocale.DESC_StartButton);
		btnEndUploadLC = new SimpleLocalizedTextButtonWidget<>(btnEndUploadUI, CorpusFileUploaderLocale.DESC_EndButton);
		
		registerLocale(CorpusFileUploaderLocale.INSTANCE.en);
		registerLocale(CorpusFileUploaderLocale.INSTANCE.de);
		
		registerLocalizedWidget(btnStartUploadLC);
		registerLocalizedWidget(btnEndUploadLC);

		refreshLocalization();
	}

	private void initHandlers()
	{
		icoCloseUI.addClickHandler(e -> {
			if (uploadInProgress == false)
				hide();
		});
		
		btnStartUploadUI.addClickHandler(e -> {
			onBeginUpload();
		});
		
		btnEndUploadUI.addClickHandler(e -> {
			onEndUpload();
		});

		uplUploaderUI.addErrorHandler(e -> {
			onUploadError(e.getTarget());
		});

		uplUploaderUI.addMaxFilesReachHandler(e -> {
			onMaxFilesReached();
		});
	}

	public CorpusFileUploader()
	{
		super(ClientEndPoint.get().getLocalization());
		initWidget(uiBinder.createAndBindUi(this));

		beginHandler = null;
		completeHandler = null;
		uploadInProgress = false;

		initLocale();
		initHandlers();
		resetUI();
	}

	@Override
	public void setLocalization(Language lang)
	{
		super.setLocalization(lang);

		lblUploadTextUI.setTitle(getLocalizedString(CorpusFileUploaderLocale.DESC_Title));
		lblUploadTextUI.setDescription(getLocalizedString(CorpusFileUploaderLocale.DESC_Description));
	}

	@Override
	public void show()
	{
		if (ClientEndPoint.get().getWebRanker().isOperationInProgress())
			MaterialToast.fireToast(getLocalizedString(CorpusFileUploaderLocale.DESC_OpInProgress));
		else
			mdlUploadUI.open();
	}

	@Override
	public void hide() {
		mdlUploadUI.close();
	}

	@Override
	public void setUploadBeginHandler(UploadBeginHandler handler) {
		beginHandler = handler;
	}

	@Override
	public void setUploadCompleteHandler(UploadCompleteHandler handler) {
		completeHandler = handler;
	}
}
