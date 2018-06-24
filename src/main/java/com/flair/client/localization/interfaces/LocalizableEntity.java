package com.flair.client.localization.interfaces;

import com.flair.shared.grammar.Language;

/*
 * Interface for localizable entities
 */
public interface LocalizableEntity
{
	public void		setLocale(Language lang, LocalizationDataCache data);
}
