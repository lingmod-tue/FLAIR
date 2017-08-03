package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;

public final class WebRankerCoreLocale extends SimpleLocale
{
	public static final String		DESC_ServerError = "ServerError";
	public static final String		DESC_NoSearchResults = "NoSearchResults";
	public static final String		DESC_MissingSearchResult = "MissingSearchResult";
	public static final String		DESC_NoParsedDocs = "NoParsedDocs";
	public static final String		DESC_MissingDoc = "MissingDoc";
	public static final String		DESC_AnalysisComplete = "AnalysisComplete";
	public static final String		DESC_KeywordTitle = "KeywordTitle";
	public static final String		DESC_OpTimeout = "OpTimeout";
	public static final String		DESC_CustomCorpusTitle = "CustomCorpusTitle";
	public static final String		DESC_VisualizeWait = "VisualizeWait";
	public static final String		DESC_NoResultsForFilter = "NoResultsForFilter";
	public static final String		DESC_ImportedSettings = "ImportedSettings";
	public static final String		DESC_AppliedImportedSettings = "AppliedImportedSettings";
	public static final String		DESC_CompareResultsTitle = "CompareResultsTitle";
	public static final String		DESC_AddToCompareSel = "AddToCompareSel";

	@Override
	public void init()
	{
		// EN
		
		en.put(DESC_ServerError, "Blimey! Something went wrong on our end. Please try again later.");
		en.put(DESC_NoSearchResults, "Sorry, our search engine didn't return any results for your query.");
		en.put(DESC_MissingSearchResult, "Some results couldn't be analyzed due to connectivity issues.");
		en.put(DESC_NoParsedDocs, "None of the results/files could be analyzed.");
		en.put(DESC_MissingDoc, "Some results/files couldn't be analyzed due to technical issues.");
		en.put(DESC_AnalysisComplete, "Analysis complete.");
		en.put(DESC_KeywordTitle, "keyword");
		en.put(DESC_OpTimeout, "The operation timed-out");
		en.put(DESC_CustomCorpusTitle, "Uploaded Files");
		en.put(DESC_VisualizeWait, "Please wait until the current analysis is complete.");
		en.put(DESC_NoResultsForFilter, "No results for the current settings");
		en.put(DESC_ImportedSettings, "FLAIR will automatically apply your custom settings");
		en.put(DESC_AppliedImportedSettings, "Applied custom settings");
		en.put(DESC_CompareResultsTitle, "Compare");
		en.put(DESC_AddToCompareSel, "Selected for comparison");
		
		// DE
		de.put(DESC_ServerError, "Schade! Wir sind auf einen schweren Fehler gestoßen. Bitte versuchen Sie es später.");
		de.put(DESC_NoSearchResults, "Keine Ergebnisse für den Suchbegriff.");
		de.put(DESC_MissingSearchResult, "Einige Webseiten konnten aufgrund von Konnektivitätsproblemen nicht analysiert werden.");
		de.put(DESC_NoParsedDocs, "Wir konnten keine der Webseiten/Dateien verarbeiten.");
		de.put(DESC_MissingDoc, "Einige Webseiten/Dateien konnten nicht analysiert werden.");
		de.put(DESC_AnalysisComplete, "Analyse abgeschlossen.");
		de.put(DESC_KeywordTitle, "Schlüsselwort");
		de.put(DESC_OpTimeout, "Der Vorgang wurde storniert, weil er zu lange gedauert hat");
		de.put(DESC_CustomCorpusTitle, "Hochgeladene Dateien");
		de.put(DESC_VisualizeWait, "Bitte warten Sie, bis die bereits angefangene Analyse abschliesst");
		de.put(DESC_NoResultsForFilter, "Keine Ergebnisse für die aktuellen Sucheinstellungen");
		de.put(DESC_ImportedSettings, "FLAIR wird Ihre benutzerdefinierten Einstellungen automatisch übernehmen");
		de.put(DESC_AppliedImportedSettings, "Benutzerdefinierte Einstellungen wurden übernommen");
		de.put(DESC_CompareResultsTitle, "Vergleichen");
		de.put(DESC_AddToCompareSel, "Zum Vergleichen ausgewählt");
	}
	
	public static final WebRankerCoreLocale		INSTANCE = new WebRankerCoreLocale();
}
