package com.flair.client.presentation.widgets;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.CommonLocalizationTags;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedCommonField;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.interfaces.CorpusUploadService;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.AuthToken;
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
import gwt.material.design.client.ui.MaterialDialog;
import gwt.material.design.client.ui.MaterialRadioButton;
import gwt.material.design.client.ui.MaterialToast;

public class CorpusFileUploader extends LocalizedComposite implements CorpusUploadService {
	private static CorpusFileUploaderUiBinder uiBinder = GWT.create(CorpusFileUploaderUiBinder.class);

	interface CorpusFileUploaderUiBinder extends UiBinder<Widget, CorpusFileUploader> {
	}

	private static CorpusFileUploaderLocalizationBinder localeBinder = GWT.create(CorpusFileUploaderLocalizationBinder.class);

	interface CorpusFileUploaderLocalizationBinder extends LocalizationBinder<CorpusFileUploader> {}

	static enum LocalizationTags {
		UPLOAD_INPROGRESS,
		UPLOAD_FAILED,
		MAX_FILE_LIMIT
	}

	@UiField
	MaterialDialog mdlUploadUI;
	@UiField
	MaterialFileUploader uplUploaderUI;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_DESCRIPTION)
	MaterialUploadLabel lblUploadTextUI;
	@UiField
	MaterialStepper stprUploaderUI;
	@UiField
	@LocalizedField(type = LocalizedFieldType.TEXT_TITLE)
	MaterialStep stpLangUI;
	@UiField
	@LocalizedCommonField(tag = CommonLocalizationTags.LANGUAGE_ENGLISH)
	MaterialRadioButton rdoEnglishUI;
	@UiField
	@LocalizedCommonField(tag = CommonLocalizationTags.LANGUAGE_GERMAN)
	MaterialRadioButton rdoGermanUI;
	@UiField
	@LocalizedCommonField(tag = CommonLocalizationTags.NEXT, type = LocalizedFieldType.TEXT_BUTTON)
	MaterialButton btnToUploaderUI;
	@UiField
	@LocalizedCommonField(tag = CommonLocalizationTags.CANCEL, type = LocalizedFieldType.TEXT_BUTTON)
	MaterialButton btnCancel1UI;
	@UiField
	@LocalizedCommonField(tag = CommonLocalizationTags.UPLOAD, type = LocalizedFieldType.TEXT_TITLE)
	MaterialStep stpUploadUI;
	@UiField
	@LocalizedCommonField(tag = CommonLocalizationTags.FINISH, type = LocalizedFieldType.TEXT_BUTTON)
	MaterialButton btnFinishUI;
	@UiField
	@LocalizedCommonField(tag = CommonLocalizationTags.CANCEL, type = LocalizedFieldType.TEXT_BUTTON)
	MaterialButton btnCancel2UI;

	UploadBeginHandler beginHandler;
	UploadCompleteHandler completeHandler;

	boolean uploadInProgress;
	int numUploaded;
	Language corpusLang;
	boolean initialized;

	// hack to work around the incomplete Dropzone API MaterialFileUploader provides
	private native boolean hasPendingUploads(MaterialFileUploader u) /*-{
        var dropzone = u.@gwt.material.design.addins.client.fileuploader.MaterialFileUploader::uploader;
        return dropzone.getUploadingFiles().length !== 0 || dropzone.getQueuedFiles().length !== 0;
    }-*/;

	private native void setupDropzone(AuthToken t, MaterialFileUploader u) /*-{
        var dropzone = u.@gwt.material.design.addins.client.fileuploader.MaterialFileUploader::uploader;
        var uuid = t.toString();

        // tag uploaded files with the client token's uuid
        dropzone.on('sending', function (file, xhr, formData) {
            formData.append('AuthToken', uuid);
        });
    }-*/;

	private void onBeginUpload(Language lang) {
		// deferred init to ensure that we have a valid token
		if (!initialized) {
			initialized = true;
			setupDropzone(ClientEndPoint.get().getClientToken(), uplUploaderUI);
		}

		if (uploadInProgress)
			throw new RuntimeException("Previous upload operation not complete");

		if (beginHandler != null)
			beginHandler.handle(lang);

		uploadInProgress = true;
		corpusLang = lang;
		numUploaded = 0;

		stprUploaderUI.nextStep();
	}

	private void onEndUpload(boolean success) {
		if (!uploadInProgress && success)
			throw new RuntimeException("Upload hasn't started yet");
		else if (hasPendingUploads(uplUploaderUI)) {
			MaterialToast.fireToast(getLocalizedString(LocalizationTags.UPLOAD_INPROGRESS.toString()));
			return;
		}

		if (uploadInProgress && completeHandler != null)
			completeHandler.handle(numUploaded, success);

		uploadInProgress = false;
		numUploaded = 0;
		corpusLang = Language.ENGLISH;

		hide();
		resetUI();
	}

	private void onUploadError(UploadFile file) {
		MaterialToast.fireToast(getLocalizedString(LocalizationTags.UPLOAD_FAILED.toString()) + ": " + file.getName());
	}

	private void onMaxFilesReached() {
		MaterialToast.fireToast(getLocalizedString(LocalizationTags.MAX_FILE_LIMIT.toString()));
	}

	private void resetUI() {
		stprUploaderUI.reset();
		uplUploaderUI.getUploadPreview().setVisible(false);
	}


	private void initHandlers() {
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

	public CorpusFileUploader() {
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));

		beginHandler = null;
		completeHandler = null;
		uploadInProgress = false;
		initialized = false;

		initHandlers();
		resetUI();
	}

	@Override
	public void show() {
		if (ClientEndPoint.get().getWebRanker().isOperationInProgress())
			throw new RuntimeException("Invalid atomic operation invokation");

		mdlUploadUI.open();
		uplUploaderUI.getUploadPreview().setVisible(true);
	}

	@Override
	public void hide() {
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
