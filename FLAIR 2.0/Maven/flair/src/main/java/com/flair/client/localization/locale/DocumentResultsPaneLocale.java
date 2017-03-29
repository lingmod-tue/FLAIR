package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;

public final class DocumentResultsPaneLocale extends SimpleLocale
{
	public static final String		DESC_btnCancelOpUI = "btnCancelOpUI";
	
	@Override
	public void init()
	{
		// EN
		en.put(DESC_btnCancelOpUI, "Cancel Analysis");

		// DE
		de.put(DESC_btnCancelOpUI, "Analyse Stornieren");
	}
	
	public static final DocumentResultsPaneLocale		INSTANCE = new DocumentResultsPaneLocale();
}