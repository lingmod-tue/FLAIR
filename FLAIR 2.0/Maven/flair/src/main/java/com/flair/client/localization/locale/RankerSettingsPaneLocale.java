package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;


public final class RankerSettingsPaneLocale extends SimpleLocale
{
	public static final String		DESC_lblDocCountUI = "lblDocCountUI";
	public static final String		DESC_btnVisualizeUI = "btnVisualizeUI";
	public static final String		DESC_btnExportSettingsUI = "btnExportSettingsUI";
	public static final String		DESC_lblTextCharacteristicsUI = "lblTextCharacteristicsUI";
	public static final String		DESC_lblTextLengthUI = "lblTextLengthUI";
	public static final String		DESC_lblTextLevelUI = "lblTextLevelUI";
	public static final String		DESC_btnConstructionsListUI = "btnConstructionsListUI";
	public static final String		DESC_btnResetAllUI = "btnResetAllUI";

	@Override
	public void init()
	{
		// EN
		en.put(DESC_lblDocCountUI, "Results");
		en.put(DESC_btnVisualizeUI, "Visualize");
		en.put(DESC_btnExportSettingsUI, "Share Search Setup");
		en.put(DESC_lblTextCharacteristicsUI, "Text Characteristics:");
		en.put(DESC_lblTextLengthUI, "Length:");
		en.put(DESC_lblTextLevelUI, "Levels:");
		en.put(DESC_btnConstructionsListUI, "List of Constructions");
		en.put(DESC_btnResetAllUI, "Reset All");
		
		// DE
		de.put(DESC_lblDocCountUI, "Ergebnisse");
		de.put(DESC_btnVisualizeUI, "Visualisieren");
		de.put(DESC_btnExportSettingsUI, "Sucheinstellungen Exportieren");
		de.put(DESC_lblTextCharacteristicsUI, "Eigenschaften der Texte:");
		de.put(DESC_lblTextLengthUI, "Länge:");
		de.put(DESC_lblTextLevelUI, "Schwierigkeitsgrad:");
		de.put(DESC_btnConstructionsListUI, "Liste der Konstructionen");
		de.put(DESC_btnResetAllUI, "Alle Zurücksetzen");
	}
	
	public static final RankerSettingsPaneLocale		INSTANCE = new RankerSettingsPaneLocale();
}