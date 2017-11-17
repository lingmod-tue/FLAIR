package com.flair.client.localization;

import java.util.EnumMap;
import java.util.HashMap;

import com.flair.client.localization.interfaces.LocalizationDataCache;
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

	public void resolveReferences(LocalizationDataCache stringTable)
	{
		// replace all inline references with their corresponding localized string
		for (java.util.Map.Entry<String, Entry> itr : data.entrySet())
		{
			String tag = itr.getKey();
			Entry entry = itr.getValue();
			for (Language l : Language.values())
			{
				// naive replacement
				String toReplace = entry.get(l);
				int start = 0, end = 0;
				do
				{
					start = toReplace.indexOf("${");
					end = toReplace.indexOf("}");
					
					if (start != -1 && end != -1)
					{
						String ref = toReplace.substring(start + 2, end);
						String[] splits = ref.split("\\.");
						if (splits.length != 2)
							throw new RuntimeException("Invalid inline reference in locale string " + tag + ", " + l);
						
						String refprovider = splits[0];
						String reftag = splits[1];
						String replacement = null;
						
						if (refprovider.equals(name))
							replacement = getLocalizedString(reftag, l);
						else
							replacement = stringTable.getLocalizedString(refprovider, reftag, l);

						toReplace = toReplace.substring(0, start)
									+ replacement
									+ (end < toReplace.length() - 1 ? toReplace.substring(end + 1) : "");
					}
				} while (start != -1);
				
				entry.put(l, toReplace);
			}
		}
	}
}
