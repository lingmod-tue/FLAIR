package com.flair.client.localization;

import com.flair.client.ClientEndPoint;
import com.flair.client.model.interfaces.WebRankerAnalysis;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;

/*
 * Basic wrapper for retrieving localized grammatical construction strings
 */
public class GrammaticalConstructionLocalizationProvider
{
	private static Language getGramConstLanguage()
	{
		Language out = Language.ENGLISH;
		if (ClientEndPoint.get().getWebRanker() != null)
		{
			WebRankerAnalysis currentOp = ClientEndPoint.get().getWebRanker().getCurrentOperation();
			if (currentOp != null)
				out = currentOp.getLanguage();
		}
		
		return out;
	}
	
	public static String getName(GrammaticalConstruction gram) {
		return LocalizationStringTable.get().getLocalizedString(DefaultLocalizationProviders.GRAMMATICAL_CONSTRUCTION_NAME.toString(), gram.name(), getGramConstLanguage());
	}
	
	public static String getPath(GrammaticalConstruction gram) {
		return LocalizationStringTable.get().getLocalizedString(DefaultLocalizationProviders.GRAMMATICAL_CONSTRUCTION_PATH.toString(), gram.name(), getGramConstLanguage());
	}
	
	public static String getHelpText(GrammaticalConstruction gram) {
		return LocalizationStringTable.get().getLocalizedString(DefaultLocalizationProviders.GRAMMATICAL_CONSTRUCTION_HELPTEXT.toString(), gram.name(), getGramConstLanguage());
	}
}
