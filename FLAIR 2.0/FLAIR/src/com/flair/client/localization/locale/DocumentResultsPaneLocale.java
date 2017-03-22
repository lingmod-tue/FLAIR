package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;

public final class DocumentResultsPaneLocale extends SimpleLocale
{
	public static final String		DESC_lblCompletedPlaceholderUI = "lblCompletedPlaceholderUI";
	public static final String		DESC_lblInProgressPlaceholderUI = "lblInProgressPlaceholderUI";

	@Override
	public void init()
	{
		// EN
		en.put(DESC_lblCompletedPlaceholderUI, "Analyzed results will appear here.");
		en.put(DESC_lblInProgressPlaceholderUI, "Results that are being analyzed will appear here.");
		
		// DE
		de.put(DESC_lblCompletedPlaceholderUI, "Analysierte Ergebnisse werden hier angezeigt.");
		de.put(DESC_lblInProgressPlaceholderUI, "Zurzeit verarbeitende Ergebnisse werden hier angezeigt.");
	}
	
	public static final DocumentResultsPaneLocale		INSTANCE = new DocumentResultsPaneLocale();
}