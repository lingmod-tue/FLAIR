package com.flair.client.localization;

import java.util.HashMap;
import java.util.Map;

/*
 * Simple key-value data store that maps a descriptor to its localized string
 */
public class LocalizationData 
{
	private final LocalizationLanguage			lang;
	private final Map<String, String>			store;
	
	public LocalizationData(LocalizationLanguage lang)
	{
		this.lang = lang;
		this.store = new HashMap<>();
	}
	
	public void put(String desc, String val)
	{
		if (store.containsKey(desc))
			throw new RuntimeException("Descriptor already registered");
		else
			store.put(desc, val);
	}
	
	public String get(String desc)
	{
		if (store.containsKey(desc) == false)
			throw new RuntimeException("Descriptor not found");
		else
			return store.get(desc);
	}
	
	public LocalizationLanguage getLanguage() {
		return lang;
	}
}
