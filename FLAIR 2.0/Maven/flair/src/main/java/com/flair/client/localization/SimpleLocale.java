package com.flair.client.localization;

import com.flair.shared.grammar.Language;

/*
 * Locale storage for supported languages
 */
public abstract class SimpleLocale
{
	public final LocalizationData		en;
	public final LocalizationData		de;
	
	public SimpleLocale()
	{
		en = new LocalizationData(Language.ENGLISH);
		de = new LocalizationData(Language.GERMAN);		
		
		init();
	}
	
	protected LocalizationData getLocalizationData(Language lang)
	{
		switch (lang)
		{
		case ENGLISH:
			return en;
		case GERMAN:
			return de;
		default:
			return null;
		}
	}
	
	public String lookup(Language lang, String desc) {
		return getLocalizationData(lang).get(desc);
	}
	
	public abstract void				init();
}
