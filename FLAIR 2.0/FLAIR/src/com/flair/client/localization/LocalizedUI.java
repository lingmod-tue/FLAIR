package com.flair.client.localization;

/*
 * Interface implemented by all localized views
 */
public interface LocalizedUI
{
	public LocalizationData			getLocalizationData(LocalizationLanguage lang);		// gets the locale data for the lang
	public void						setLocalization(LocalizationLanguage lang);			// updates the view's locale
}
