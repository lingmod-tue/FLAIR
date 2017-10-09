package com.flair.client.localization;

import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;

/*
 * Basic wrapper for retrieving localized grammatical construction strings
 */
public class GrammaticalConstructionLocalizationProvider
{
	public static String getName(GrammaticalConstruction gram, Language lang) {
		return LocalizationStringTable.get().getLocalizedString(DefaultLocalizationProviders.GRAMMATICAL_CONSTRUCTION_NAME.toString(), gram.name(), lang);
	}
	
	public static String getPath(GrammaticalConstruction gram, Language lang) {
		return LocalizationStringTable.get().getLocalizedString(DefaultLocalizationProviders.GRAMMATICAL_CONSTRUCTION_PATH.toString(), gram.name(), lang);
	}
	
	public static String getHelpText(GrammaticalConstruction gram, Language lang) {
		return LocalizationStringTable.get().getLocalizedString(DefaultLocalizationProviders.GRAMMATICAL_CONSTRUCTION_HELPTEXT.toString(), gram.name(), lang);
	}
}
