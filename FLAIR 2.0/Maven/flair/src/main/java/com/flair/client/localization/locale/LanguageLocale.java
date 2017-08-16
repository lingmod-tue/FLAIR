package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;
import com.flair.shared.grammar.Language;

/*
* Locale data for grammatical constructions
* Strings that are the same as the enum are not used in the client
*/
public class LanguageLocale extends SimpleLocale
{
	private static final LanguageLocale		INSTANCE = new LanguageLocale();
	
	public static LanguageLocale get() {
		return INSTANCE;
	}
	
	private LanguageLocale() {
		super();
	}
	
	private void putString(Language localeLang, Language lang, String name) {
		getLocalizationData(localeLang).put(lang.toString(), name);
	}
	
	@Override
	public void init()
	{
		for (Language itr : Language.values())
		{
			switch (itr)
			{
			case ENGLISH:
				putString(Language.ENGLISH, itr, "English");
				putString(Language.GERMAN, itr, "Englisch");
				break;
			case GERMAN:
				putString(Language.ENGLISH, itr, "German");
				putString(Language.GERMAN, itr, "Deutsch");
				break;
			default:
				break;
			}
		}
	}
	
	public String getLocalizedName(Language lang, Language locale) {
		return getLocalizationData(locale).get(lang.toString());
	}
}
