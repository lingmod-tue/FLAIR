package com.flair.client.localization;

import com.flair.client.model.interfaces.AbstractWebRankerCore;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;

/*
 * Basic wrapper for retrieving localized grammatical construction strings
 */
public class GrammaticalConstructionLocalizationProvider
{
	private static Language activeLang = Language.ENGLISH;		// the language of the active operation
	
	public static void bindToWebRankerCore(AbstractWebRankerCore ranker)
	{
		ranker.addBeginOperationHandler(p -> {
			activeLang = p.lang;
		});
		
		ranker.addEndOperationHandler(p -> {
			if (p.success)
				activeLang = p.lang;
			else
				activeLang = Language.ENGLISH;
		});
	}
	
	public static String getName(GrammaticalConstruction gram) {
		return getName(gram, activeLang);
	}
	
	public static String getPath(GrammaticalConstruction gram) {
		return getPath(gram, activeLang);
	}
	
	public static String getHelpText(GrammaticalConstruction gram) {
		return getHelpText(gram, activeLang);
	}
	
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
