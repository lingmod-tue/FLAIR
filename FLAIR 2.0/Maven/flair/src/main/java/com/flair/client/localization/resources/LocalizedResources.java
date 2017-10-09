package com.flair.client.localization.resources;

import com.flair.shared.grammar.Language;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

/*
 * Provides an interface to retrieve external locale data
 */
public class LocalizedResources
{
	private static final LocalizedResources 	INSTANCE = new LocalizedResources();
	public static LocalizedResources get() {
		return INSTANCE;
	}
	
	private interface EnglishStrings extends ClientBundle
	{
		@Source("strings-en-constructions.txt")
		public TextResource			getGramConstructionStrings();
		
		@Source("strings-en-general.txt")
		public TextResource			getGeneralStrings();
	}
	
	private interface GermanStrings extends ClientBundle
	{
		@Source("strings-de-constructions.txt")
		public TextResource			getGramConstructionStrings();
		
		@Source("strings-de-general.txt")
		public TextResource			getGeneralStrings();
	}
	
	private final EnglishStrings		englishStrings;
	private final GermanStrings			germanStrings;
	
	private LocalizedResources()
	{
		englishStrings = GWT.create(EnglishStrings.class);
		germanStrings = GWT.create(GermanStrings.class);
	}
	
	public TextResource getGramConstructionStrings(Language lang)
	{
		switch (lang)
		{
		case ENGLISH:
			return englishStrings.getGramConstructionStrings();
		case GERMAN:
			return germanStrings.getGramConstructionStrings();
		default:
			throw new RuntimeException("No localized gramconst strings for language " + lang);
		}
	}
	
	public TextResource getGeneralStrings(Language lang)
	{
		switch (lang)
		{
		case ENGLISH:
			return englishStrings.getGeneralStrings();
		case GERMAN:
			return germanStrings.getGeneralStrings();
		default:
			throw new RuntimeException("No localized general strings for language " + lang);
		}
	}
}

