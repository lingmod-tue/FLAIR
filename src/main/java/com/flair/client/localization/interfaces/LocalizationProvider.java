package com.flair.client.localization.interfaces;

import com.flair.shared.grammar.Language;

/*
 * Provides localization data to consumers
 */
public interface LocalizationProvider
{
	public String			getName();
	
	public void				setLocalizedString(String tag, Language lang, String localizedStr);
	public String			getLocalizedString(String tag, Language lang);
}
