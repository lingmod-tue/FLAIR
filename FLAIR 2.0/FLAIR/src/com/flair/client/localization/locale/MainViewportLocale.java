package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;

public final class MainViewportLocale extends SimpleLocale
{
	public static final String		DESC_btnWebSearchUI = "btnWebSearchUI";
	public static final String		DESC_btnUploadUI = "btnUploadUI";
	public static final String		DESC_btnAboutUI = "btnAboutUI";
	public static final String		DESC_btnSwitchLangUI = "btnSwitchLangUI";
	public static final String		DESC_btnLangEnUI = "btnLangEnUI";
	public static final String		DESC_btnLangDeUI = "btnLangDeUI";

	@Override
	public void init()
	{
		// EN
		en.put(DESC_btnWebSearchUI, "Web Search");
		en.put(DESC_btnUploadUI, "Upload Corpus");
		en.put(DESC_btnAboutUI, "About FLAIR");
		en.put(DESC_btnSwitchLangUI, "Language");
		en.put(DESC_btnLangEnUI, "English");
		en.put(DESC_btnLangDeUI, "German");
		
		// DE
		de.put(DESC_btnWebSearchUI, "Internet Suche");
		de.put(DESC_btnUploadUI, "Text Hochladen");
		de.put(DESC_btnAboutUI, "Über FLAIR");
		de.put(DESC_btnSwitchLangUI, "Sprache");
		de.put(DESC_btnLangEnUI, "Englisch");
		de.put(DESC_btnLangDeUI, "Deutsch");
	}
	
	public static final MainViewportLocale		INSTANCE = new MainViewportLocale();
}