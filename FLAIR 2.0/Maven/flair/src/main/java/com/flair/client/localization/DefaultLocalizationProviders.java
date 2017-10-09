package com.flair.client.localization;

import com.flair.client.localization.interfaces.LocalizationProvider;

/*
 * Default localization providers
 */
public enum DefaultLocalizationProviders
{
	COMMON("_common-strings_"),
	GRAMMATICAL_CONSTRUCTION_NAME("_gram-name_"),
	GRAMMATICAL_CONSTRUCTION_PATH("_gram-path_"),
	GRAMMATICAL_CONSTRUCTION_HELPTEXT("_gram-helpText_"),
	WEBRANKERCORE("_webrankercore_")
	;
	
	private final String			providerName;
	
	private DefaultLocalizationProviders(String provider) {
		providerName = provider;
	}
	
	@Override
	public String toString() {
		return providerName;
	}
	
	public LocalizationProvider get() {
		return LocalizationStringTable.get().getProvider(providerName);
	}
}