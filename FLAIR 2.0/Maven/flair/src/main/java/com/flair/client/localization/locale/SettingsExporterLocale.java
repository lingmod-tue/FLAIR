package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;

public final class SettingsExporterLocale extends SimpleLocale
{
	public static final String		DESC_lblTitleUI = "lblTitleUI";
	public static final String		DESC_lblCaptionUI = "lblCaptionUI";
	public static final String		DESC_btnCloseUI = "btnCloseUI";

	@Override
	public void init()
	{
		// EN
		en.put(DESC_lblTitleUI, "Export Settings");
		en.put(DESC_lblCaptionUI, "You can use this link to apply your current settings to future searches.");
		en.put(DESC_btnCloseUI, "Close");
		
		// DE
		de.put(DESC_lblTitleUI, "Einstellungen Exportieren");
		de.put(DESC_lblCaptionUI, "Sie können diesen Link benutzen, um Ihre derzeigen Einstellungen auf zukünftige Suchen anzuwenden.");
		de.put(DESC_btnCloseUI, "Schliessen");
	}
	
	public static final SettingsExporterLocale		INSTANCE = new SettingsExporterLocale();
}
