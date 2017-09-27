package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;

public final class WebSearchModalLocale extends SimpleLocale
{
	public static final String		DESC_txtSearchBoxUI = "txtSearchBoxUI";
	public static final String		DESC_selResultCountItm10UI = "selResultCountItm10UI";
	public static final String		DESC_selResultCountItm20UI = "selResultCountItm20UI";
	public static final String		DESC_selResultCountItm30UI = "selResultCountItm30UI";
	public static final String		DESC_selResultCountItm40UI = "selResultCountItm40UI";
	public static final String		DESC_selResultCountItm50UI = "selResultCountItm50UI";

	public static final String		DESC_btnSearchUI = "btnSearchUI";
	public static final String		DESC_btnCancelUI = "btnCancelUI";
	
	@Override
	public void init()
	{
		// EN
		en.put(DESC_txtSearchBoxUI, "Enter a query");
		en.put(DESC_selResultCountItm10UI, "10 Results");
		en.put(DESC_selResultCountItm20UI, "20 Results");
		en.put(DESC_selResultCountItm30UI, "30 Results");
		en.put(DESC_selResultCountItm40UI, "40 Results");
		en.put(DESC_selResultCountItm50UI, "50 Results");
		en.put(DESC_btnSearchUI, "Search");
		en.put(DESC_btnCancelUI, "Cancel");
		
		// DE
		de.put(DESC_txtSearchBoxUI, "Suchbegriff eingeben");
		de.put(DESC_selResultCountItm10UI, "10 Ergebnisse");
		de.put(DESC_selResultCountItm20UI, "20 Ergebnisse");
		de.put(DESC_selResultCountItm30UI, "30 Ergebnisse");
		de.put(DESC_selResultCountItm40UI, "40 Ergebnisse");
		de.put(DESC_selResultCountItm50UI, "50 Ergebnisse");
		de.put(DESC_btnSearchUI, "Suchen");
		de.put(DESC_btnCancelUI, "Abbrechen");
	}
	
	public static final WebSearchModalLocale		INSTANCE = new WebSearchModalLocale();
}
