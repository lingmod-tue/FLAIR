package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;
import com.flair.shared.grammar.Language;

public final class KeywordWeightSliderLocale extends SimpleLocale
{
	public static final String		DESC_toggleDefault = "toggleDefault";
	public static final String		DESC_toggleCustom = "toggleCustom";
	public static final String		DESC_toggleTooltip = "toggleTooltip";
	public static final String		DESC_editTooltip = "editTooltip";
	public static final String		DESC_resetTooltip = "resetTooltip";
	public static final String		DESC_sliderTooltip = "sliderTooltip";
	
	@Override
	public void init()
	{
		// EN
		en.put(DESC_toggleDefault, "Academic Vocabulary");
		en.put(DESC_toggleCustom, "Custom Vocabulary");
		en.put(DESC_toggleTooltip, "check to highlight keywords");
		en.put(DESC_editTooltip, "edit vocabulary");
		en.put(DESC_resetTooltip, "use default academic vocabulary");
		en.put(DESC_sliderTooltip, "move right to rank texts with this construct higher");
		
		// DE
		de.put(DESC_toggleDefault, "Akademisch Vokabular");
		de.put(DESC_toggleCustom, "Benutzerdefiniert Vokabular");
		de.put(DESC_toggleTooltip, "auswählen, um Schlüsselwörter hervorzuheben");
		de.put(DESC_editTooltip, "Vokabular bearbeiten");
		de.put(DESC_resetTooltip, "Standardliste für benutzerdefiniertes Vokabular verwenden");
		de.put(DESC_sliderTooltip, "nach rechts bewegen, um Texte mit dieser Konstruktion höher zu bewerten");
	}
	
	public String getLocalizedKeywordString(Language lang, boolean customKeyword) {
		return getLocalizationData(lang).get(customKeyword ? DESC_toggleCustom : DESC_toggleDefault);
	}
	
	public static final KeywordWeightSliderLocale		INSTANCE = new KeywordWeightSliderLocale();
}
