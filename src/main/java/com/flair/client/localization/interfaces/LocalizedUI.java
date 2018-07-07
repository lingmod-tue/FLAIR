package com.flair.client.localization.interfaces;

import com.flair.shared.grammar.Language;

/*
 * Interface implemented by all localized views
 */
public interface LocalizedUI {
	public LocalizationProvider getLocalizationProvider();

	public void setLocale(Language lang);
	public void refreshLocale();
}
