package com.flair.client.presentation.widgets;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.SimpleLocalizedTextButtonWidget;
import com.flair.client.localization.SimpleLocalizedTextWidget;
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
import gwt.material.design.addins.client.stepper.MaterialStep;
import gwt.material.design.addins.client.stepper.MaterialStepper;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialRadioButton;
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
	MaterialFileUploader 	uplUploaderUI;
	@UiField
	MaterialUploadLabel		lblUploadTextUI;
	@UiField
	MaterialStepper			stprUploaderUI;
	@UiField
	MaterialStep			stpLangUI;
	@UiField
	MaterialRadioButton		rdoEnglishUI;
	@UiField
	MaterialRadioButton		rdoGermanUI;
	@UiField
	MaterialButton			btnToUploaderUI;
	@UiField
	MaterialButton			btnCancel1UI;
	@UiField
	MaterialStep			stpUploadUI;
	@UiField
	MaterialButton			btnFinishUI;
	@UiField
	MaterialButton			btnCancel2UI;
	
	SimpleLocalizedTextWidget<MaterialRadioButton>		rdoEnglishLC;
	SimpleLocalizedTextWidget<MaterialRadioButton>		rdoGermanLC;
	SimpleLocalizedTextButtonWidget<MaterialButton>		btnToUploaderLC;
	SimpleLocalizedTextButtonWidget<MaterialButton>		btnCancel1LC;
	SimpleLocalizedTextButtonWidget<MaterialButton>		btnFinishLC;
	SimpleLocalizedTextButtonWidget<MaterialButton>		btnCancel2LC;

	UploadBeginHandler		beginHandler;
	UploadCompleteHandler	completeHandler;

	boolean					uploadInProgress;
	int						numUploaded;
	Language				corpusLang;
	
	// hack to work around the incomplete Dropzone API MaterialFileUploader provides
	private native boolean hasPendingUploads(MaterialFileUploader u) /*-{
		var dropzone = u.@gwt.material.design.addins.client.fileuploader.MaterialFileUploader::uploader;
		return dropzone.getUploadingFiles().length !== 0 || dropzone.getQueuedFiles().length !== 0;
	}-*/;

	private void onBeginUpload(Language lang)
	{
		if (uploadInProgress)
			throw new RuntimeException("Previous upload operation not complete");

		if (beginHandler != null)
			beginHandler.handle(lang);
		
		uploadInProgress = true;
		corpusLang = lang;
		numUploaded = 0;
		
		stprUploaderUI.nextStep();
	}

	private void onEndUpload(boolean success)
	{
		if (uploadInProgress == false && success)
			throw new RuntimeException("Upload hasn't started yet");
		else if (hasPendingUploads(uplUploaderUI))
		{
			MaterialToast.fireToast(getLocalizedString(CorpusFileUploaderLocale.DESC_UploadInProgress));
			return;
		}

		if (completeHandler != null)
			completeHandler.handle(numUploaded, success);
		
		uploadInProgress = false;
		numUploaded = 0;
		corpusLang = Language.ENGLISH;

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
		stprUploaderUI.reset();
		uplUploaderUI.getUploadPreview().setVisible(false);
	}

	private void initLocale()
	{
		rdoEnglishLC = new SimpleLocalizedTextWidget<>(rdoEnglishUI, CorpusFileUploaderLocale.DESC_rdoEnglishUI);
		rdoGermanLC = new SimpleLocalizedTextWidget<>(rdoGermanUI, CorpusFileUploaderLocale.DESC_rdoGermanUI);
		btnToUploaderLC = new SimpleLocalizedTextButtonWidget<>(btnToUploaderUI, CorpusFileUploaderLocale.DESC_btnToUploaderUI);
		btnCancel1LC = new SimpleLocalizedTextButtonWidget<>(btnCancel1UI, CorpusFileUploaderLocale.DESC_btnCancel1UI);
		btnFinishLC = new SimpleLocalizedTextButtonWidget<>(btnFinishUI, CorpusFileUploaderLocale.DESC_btnFinishUI);
		btnCancel2LC = new SimpleLocalizedTextButtonWidget<>(btnCancel2UI, CorpusFileUploaderLocale.DESC_btnCancel2UI);
		
		registerLocale(CorpusFileUploaderLocale.INSTANCE.en);
		registerLocale(CorpusFileUploaderLocale.INSTANCE.de);
		
		registerLocalizedWidget(rdoEnglishLC);
		registerLocalizedWidget(rdoGermanLC);
		registerLocalizedWidget(btnToUploaderLC);
		registerLocalizedWidget(btnCancel1LC);
		registerLocalizedWidget(btnFinishLC);
		registerLocalizedWidget(btnCancel2LC);

		refreshLocalization();
	}

	private void initHandlers()
	{
		btnToUploaderUI.addClickHandler(e -> {
			Language lang = Language.ENGLISH;
			if (rdoEnglishUI.getValue())
				;//
			else if (rdoGermanUI.getValue())
				lang = Language.GERMAN;
			
			onBeginUpload(lang);
		});
		
		btnFinishUI.addClickHandler(e -> onEndUpload(true));
		btnCancel1UI.addClickHandler(e -> onEndUpload(false));
		btnCancel2UI.addClickHandler(e -> onEndUpload(false));
		
		uplUploaderUI.addErrorHandler(e -> onUploadError(e.getTarget()));
		uplUploaderUI.addMaxFilesReachHandler(e -> onMaxFilesReached());
		uplUploaderUI.addSuccessHandler(e -> numUploaded++);
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

		lblUploadTextUI.setDescription(getLocalizedString(CorpusFileUploaderLocale.DESC_Description));
		stpLangUI.setTitle(getLocalizedString(CorpusFileUploaderLocale.DESC_stpLangUI));
		stpUploadUI.setTitle(getLocalizedString(CorpusFileUploaderLocale.DESC_stpUploadUI));
	}

	@Override
	public void show()
	{
		if (ClientEndPoint.get().getWebRanker().isOperationInProgress())
			MaterialToast.fireToast(getLocalizedString(CorpusFileUploaderLocale.DESC_OpInProgress));
		else
		{
			mdlUploadUI.open();
			uplUploaderUI.getUploadPreview().setVisible(true);
		}
	}

	@Override
	public void hide()
	{
		resetUI();
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
