package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;

public final class ModalPromptLocale extends SimpleLocale
{
	public static final String		DESC_btnYesUI = "btnYesUI";
	public static final String		DESC_btnNoUI = "btnNoUI";

	@Override
	public void init()
	{
		// EN
		en.put(DESC_btnYesUI, "Yes");
		en.put(DESC_btnNoUI, "No");
		
		// DE
		de.put(DESC_btnYesUI, "Ja");
		de.put(DESC_btnNoUI, "Nein");
	}
	
	public static final ModalPromptLocale		INSTANCE = new ModalPromptLocale();
}
