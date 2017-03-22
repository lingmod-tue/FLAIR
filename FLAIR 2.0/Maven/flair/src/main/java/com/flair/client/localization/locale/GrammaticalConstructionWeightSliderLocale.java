package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;

public final class GrammaticalConstructionWeightSliderLocale extends SimpleLocale
{
	public static final String		DESC_toggleTooltip = "toggleTooltip";
	public static final String		DESC_resultCountTooltip = "resultCountTooltip";
	public static final String		DESC_sliderTooltip = "sliderTooltip";
	
	@Override
	public void init()
	{
		// EN
		en.put(DESC_toggleTooltip, "uncheck to exclude texts with this construct");
		en.put(DESC_resultCountTooltip, "results");
		en.put(DESC_sliderTooltip, "move right to rank texts with this construct higher");
		
		// DE
		de.put(DESC_toggleTooltip, "abwählen, um Texte mit dieser Konstruktion auszuschließen");
		de.put(DESC_resultCountTooltip, "Ergebnisse");
		de.put(DESC_sliderTooltip, "nach rechts bewegen, um Texte mit dieser Konstruktion höher zu bewerten");
	}
	
	public static final GrammaticalConstructionWeightSliderLocale		INSTANCE = new GrammaticalConstructionWeightSliderLocale();
}
