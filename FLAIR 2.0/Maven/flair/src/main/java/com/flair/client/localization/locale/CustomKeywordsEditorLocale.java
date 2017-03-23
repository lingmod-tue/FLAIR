package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;

public final class CustomKeywordsEditorLocale extends SimpleLocale
{
	public static final String		DESC_lblTitleUI = "lblTitleUI";
	public static final String		DESC_txtKeywordsUI = "txtKeywordsUI";
	public static final String		DESC_btnApplyUI = "btnApplyUI";
	public static final String		DESC_btnCancelUI = "btnCancelUI";
	public static final String		DESC_NotifyEmpty = "NotifyEmpty";
	public static final String		DESC_NotifySuccess = "NotifySuccess";

	@Override
	public void init()
	{
		// EN
		en.put(DESC_lblTitleUI, "Edit Custom Vocabulary");
		en.put(DESC_txtKeywordsUI, "Enter your keywords here, separated by commas or line breaks");
		en.put(DESC_btnApplyUI, "Apply");
		en.put(DESC_btnCancelUI, "Cancel");
		en.put(DESC_NotifyEmpty, "The word list is empty");
		en.put(DESC_NotifySuccess, "The new vocabulary will be used during the next analysis");
		
		// DE
		de.put(DESC_lblTitleUI, "Benutzerdefiniertes Vokabular bearbeiten");
		de.put(DESC_txtKeywordsUI, "Wörter können durch Kommas oder Zeilenumbrüche getrennt sein.");
		de.put(DESC_btnApplyUI, "Übernehmen");
		de.put(DESC_btnCancelUI, "Abbrechen");
		de.put(DESC_NotifyEmpty, "Die Wörterliste ist leer");
		de.put(DESC_NotifySuccess, "Das neue Vokabular wird beim nächsten Suchvorgang angewendet");
	}
	
	public static final CustomKeywordsEditorLocale		INSTANCE = new CustomKeywordsEditorLocale();
}
