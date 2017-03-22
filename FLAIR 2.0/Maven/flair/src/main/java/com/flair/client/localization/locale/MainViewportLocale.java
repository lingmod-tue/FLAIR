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
	
	public static final String		DESC_txtSearchBoxUI = "txtSearchBoxUI";
	public static final String		DESC_selResultCountItm10UI = "selResultCountItm10UI";
	public static final String		DESC_selResultCountItm20UI = "selResultCountItm20UI";
	public static final String		DESC_selResultCountItm30UI = "selResultCountItm30UI";
	public static final String		DESC_selResultCountItm40UI = "selResultCountItm40UI";
	public static final String		DESC_selResultCountItm50UI = "selResultCountItm50UI";
	public static final String		DESC_selResultLangItmEnUI = "selResultLangItmEnUI";
	public static final String		DESC_selResultLangItmDeUI = "selResultLangItmDeUI";

	@Override
	public void init()
	{
		// EN
		en.put(DESC_btnWebSearchUI, "Web Search");
		en.put(DESC_btnUploadUI, "Upload Corpus");
		en.put(DESC_btnAboutUI, "About FLAIR");
		en.put(DESC_btnSwitchLangUI, "Switch Inteface Language");
		en.put(DESC_btnLangEnUI, "English");
		en.put(DESC_btnLangDeUI, "German");
		en.put(DESC_txtSearchBoxUI, "Enter a query");
		en.put(DESC_selResultCountItm10UI, "10 Results");
		en.put(DESC_selResultCountItm20UI, "20 Results");
		en.put(DESC_selResultCountItm30UI, "30 Results");
		en.put(DESC_selResultCountItm40UI, "40 Results");
		en.put(DESC_selResultCountItm50UI, "50 Results");
		en.put(DESC_selResultLangItmEnUI, "English");
		en.put(DESC_selResultLangItmDeUI, "German");
		
		// DE
		de.put(DESC_btnWebSearchUI, "Internet Suche");
		de.put(DESC_btnUploadUI, "Text Hochladen");
		de.put(DESC_btnAboutUI, "Über FLAIR");
		de.put(DESC_btnSwitchLangUI, "Anzeigesprache wechseln");
		de.put(DESC_btnLangEnUI, "Englisch");
		de.put(DESC_btnLangDeUI, "Deutsch");
		de.put(DESC_txtSearchBoxUI, "Suchbegriff eingeben");
		de.put(DESC_selResultCountItm10UI, "10 Seiten");
		de.put(DESC_selResultCountItm20UI, "20 Seiten");
		de.put(DESC_selResultCountItm30UI, "30 Seiten");
		de.put(DESC_selResultCountItm40UI, "40 Seiten");
		de.put(DESC_selResultCountItm50UI, "50 Seiten");
		de.put(DESC_selResultLangItmEnUI, "Englisch");
		de.put(DESC_selResultLangItmDeUI, "Deutsch");
	}
	
	public static final MainViewportLocale		INSTANCE = new MainViewportLocale();
}