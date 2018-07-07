package com.flair.client.localization;

import com.flair.client.localization.interfaces.LocalizationDataCache;
import com.flair.client.localization.interfaces.LocalizationProvider;
import com.flair.client.localization.resources.LocalizedResources;
import com.flair.client.localization.resources.StringTableReader;
import com.flair.shared.grammar.Language;
import com.google.gwt.resources.client.TextResource;

import java.util.HashMap;

/*
 * Primary storage for all localized strings in the app
 */
public class LocalizationStringTable implements LocalizationDataCache {
	private static final LocalizationStringTable INSTANCE = new LocalizationStringTable();
	public static LocalizationStringTable get() {
		return INSTANCE;
	}

	private final HashMap<String, BasicLocalizationProvider> providers;        // key - provider name

	private LocalizationStringTable() {
		this.providers = new HashMap<>();
	}

	private BasicLocalizationProvider getProvider(String name, boolean add) {
		BasicLocalizationProvider p = providers.get(name);
		if (p == null && add == false)
			throw new RuntimeException("No localization provider for name '" + name + "'");
		else if (p == null) {
			p = new BasicLocalizationProvider(name);
			providers.put(name, p);
		}

		return p;
	}

	private void parseStringTableEntry(Language lang, String provider, String tag, String localizedStr) {
		getProvider(provider, true).setLocalizedString(tag, lang, localizedStr);
	}

	public void init() {
		StringTableReader reader = new StringTableReader();
		for (Language lang : Language.values()) {
			TextResource general = LocalizedResources.get().getGeneralStrings(lang);
			reader.parse(general, (p, t, l) -> parseStringTableEntry(lang, p, t, l));

			TextResource gram = LocalizedResources.get().getGramConstructionStrings(lang);
			reader.parse(gram, (p, t, l) -> parseStringTableEntry(lang, p, t, l));
		}

		// second pass to resolve inline references
		for (BasicLocalizationProvider provider : providers.values())
			provider.resolveReferences(this);
	}

	@Override
	public LocalizationProvider getProvider(String name) {
		return getProvider(name, false);
	}

	@Override
	public String getLocalizedString(String provider, String tag, Language lang) {
		return getProvider(provider).getLocalizedString(tag, lang);
	}

	@Override
	public boolean hasProvider(String name) {
		return providers.containsKey(name);
	}
}
