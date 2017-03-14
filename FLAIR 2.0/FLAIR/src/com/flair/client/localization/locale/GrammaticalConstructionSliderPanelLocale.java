package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;

public final class GrammaticalConstructionSliderPanelLocale extends SimpleLocale
{
	public static final String		DESC_resetTooltip = "resetTooltip";
	
	@Override
	public void init()
	{
		// EN
		en.put(DESC_resetTooltip, "reset");

		// DE
		de.put(DESC_resetTooltip, "zurücksetzen");
	}
	
	public static final GrammaticalConstructionSliderPanelLocale		INSTANCE = new GrammaticalConstructionSliderPanelLocale();
}
