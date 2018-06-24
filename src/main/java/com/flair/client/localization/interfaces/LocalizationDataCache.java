package com.flair.client.localization.interfaces;

import com.flair.shared.grammar.Language;

/*
 * Exposes localization data to consumers
 */
public interface LocalizationDataCache
{
	public boolean					hasProvider(String name);
	public LocalizationProvider		getProvider(String name);
	public String					getLocalizedString(String provider, String tag, Language lang);
}
