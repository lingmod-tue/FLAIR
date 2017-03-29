package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;

public final class CorpusFileUploaderLocale extends SimpleLocale
{
	public static final String		DESC_Title = "Title";
	public static final String		DESC_Description = "Description";
	public static final String		DESC_UploadFailed = "UploadFailed";
	public static final String		DESC_WaitTitle = "WaitTitle";
	public static final String		DESC_WaitDescription = "WaitDescription";
	public static final String		DESC_MaxFiles = "MaxFiles";
	public static final String		DESC_OpInProgress = "OpInProgress";
	public static final String		DESC_stpLangUI = "stpLangUI";
	public static final String		DESC_rdoEnglishUI = "rdoEnglishUI";
	public static final String		DESC_rdoGermanUI = "rdoGermanUI";
	public static final String		DESC_btnToUploaderUI = "btnToUploaderUI";
	public static final String		DESC_btnCancel1UI = "btnCancel1UI";
	public static final String		DESC_stpUploadUI = "stpUploadUI";
	public static final String		DESC_btnFinishUI = "btnFinishUI";
	public static final String		DESC_btnCancel2UI = "btnCancel2UI";
	public static final String		DESC_UploadInProgress = "UploadInProgress";

	@Override
	public void init()
	{
		// EN
		en.put(DESC_Title, "Upload Your Corpus");
		en.put(DESC_Description, "Drag and drop files here");
		en.put(DESC_UploadFailed, "The following file couldn't be uploaded");
		en.put(DESC_WaitTitle, "Please Wait");
		en.put(DESC_WaitDescription, "Your files are being uploaded");
		en.put(DESC_MaxFiles, "You have uploaded too many files at a time");
		en.put(DESC_OpInProgress, "Please wait until the current operation is complete");
		en.put(DESC_stpLangUI, "Choose Language");
		en.put(DESC_rdoEnglishUI, "English");
		en.put(DESC_rdoGermanUI, "German");
		en.put(DESC_btnToUploaderUI, "Next");
		en.put(DESC_btnCancel1UI, "Cancel");
		en.put(DESC_stpUploadUI, "Upload");
		en.put(DESC_btnFinishUI, "Finish");
		en.put(DESC_btnCancel2UI, "Cancel");
		en.put(DESC_UploadInProgress, "Please wait until the upload process is complete");

		// DE
		de.put(DESC_Title, "Eigenes Korpus hochladen");
		de.put(DESC_Description, "Hier können Sie Dateien ziehen und ablegen");
		de.put(DESC_UploadFailed, "Diese Datei konnte nicht hochgeladen werden");
		de.put(DESC_WaitTitle, "Bitte Warten");
		de.put(DESC_WaitDescription, "Die Dateien werden hochgeladen");
		de.put(DESC_MaxFiles, "Sie dürfen nicht so viele Dateien auf einmal hochladen");
		de.put(DESC_OpInProgress, "Bitte warten Sie, bis die bereits angefangene Analyse abschliesst");
		de.put(DESC_stpLangUI, "Textsprache Wählen");
		de.put(DESC_rdoEnglishUI, "Englisch");
		de.put(DESC_rdoGermanUI, "Deutsch");
		de.put(DESC_btnToUploaderUI, "Weiter");
		de.put(DESC_btnCancel1UI, "Abbrechen");
		de.put(DESC_stpUploadUI, "Hochladen");
		de.put(DESC_btnFinishUI, "Fertig");
		de.put(DESC_btnCancel2UI, "Abbrechen");
		de.put(DESC_UploadInProgress, "Bitte warten Sie, bis der Hochladensvorgang abschliesst");
	}

	public static final CorpusFileUploaderLocale		INSTANCE = new CorpusFileUploaderLocale();
}
