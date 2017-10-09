package com.flair.client.localization;

import java.util.EnumMap;
import java.util.HashMap;

import com.flair.client.localization.interfaces.LocalizationProvider;
import com.flair.client.utilities.ClientLogger;
import com.flair.shared.grammar.Language;

/*
 * Simple key-value data store that maps a descriptor to its localized string (for every supported language)
 */
public class BasicLocalizationProvider implements LocalizationProvider
{
	private static final String			PLACEHOLDER_STRING = "<LOCALIZED STRING NOTFOUND>";
	
	private static class Entry extends EnumMap<Language, String>
	{
		public Entry() {
			super(Language.class);
		}
	}
	
	private final String					name;
	private final HashMap<String, Entry>	data;		// tag > entry
	
	public BasicLocalizationProvider(String name)
	{
		this.name = name;
		this.data = new HashMap<>();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setLocalizedString(String tag, Language lang, String localizedStr)
	{
		Entry e = data.get(tag);
		if (e == null)
		{
			e = new Entry();
			data.put(tag, e);
		}
		else if (e.containsKey(lang))
			throw new RuntimeException("Localized string already exists for tag '" + tag + "' in langauge '" + lang + "'");
		
		e.put(lang, localizedStr);
	}

	@Override
	public String getLocalizedString(String tag, Language lang)
	{
		Entry e = data.get(tag);
		if (e != null && e.containsKey(lang))
			return e.get(lang);
		else
		{
			ClientLogger.get().error(new RuntimeException("Localization error"), "No localized string for tag '" + tag + "' in langauge '" + lang + "'");
			return PLACEHOLDER_STRING;
		}
	}
}
