package com.flair.client.views;

import com.flair.client.localization.LocalizationData;
import com.flair.client.localization.LocalizationLanguage;

/*
 * Locale storage for views
 */
abstract class SimpleViewLocale
{
	final LocalizationData		en;
	final LocalizationData		de;
	
	SimpleViewLocale()
	{
		en = new LocalizationData(LocalizationLanguage.ENGLISH);
		de = new LocalizationData(LocalizationLanguage.GERMAN);		
		
		init();
	}
	
	abstract void				init();
}
