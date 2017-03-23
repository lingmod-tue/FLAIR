package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;

public final class WebRankerCoreLocale extends SimpleLocale
{
	public static final String		DESC_OpInProgessTitle = "OpInProgessTitle";
	public static final String		DESC_OpInProgessCaption = "OpInProgessCaption";
	public static final String		DESC_ServerError = "ServerError";
	public static final String		DESC_NoSearchResults = "NoSearchResults";
	public static final String		DESC_MissingSearchResult = "MissingSearchResult";
	public static final String		DESC_NoParsedDocs = "NoParsedDocs";
	public static final String		DESC_MissingDoc = "MissingDoc";
	public static final String		DESC_AnalysisComplete = "AnalysisComplete";
	public static final String		DESC_KeywordTitle = "KeywordTitle";
	public static final String		DESC_OpTimeout = "OpTimeout";

	@Override
	public void init()
	{
		// EN
		en.put(DESC_OpInProgessTitle, "Confirmation");
		en.put(DESC_OpInProgessCaption, "A previous operation is in progress. Are you sure you want to continue? This will cancel the currently executing operation.");
		en.put(DESC_ServerError, "Blimey! Something went wrong on our end. Please try again later.");
		en.put(DESC_NoSearchResults, "Sorry, our search engine didn't return any results for your query.");
		en.put(DESC_MissingSearchResult, "Some results couldn't be analyzed due to connectivity issues.");
		en.put(DESC_NoParsedDocs, "None of the results/files could be analyzed.");
		en.put(DESC_MissingDoc, "Some results/files couldn't be analyzed due to technical issues.");
		en.put(DESC_AnalysisComplete, "Analysis complete.");
		en.put(DESC_KeywordTitle, "keyword");
		en.put(DESC_OpTimeout, "The operation timed-out");
		
		// DE
		de.put(DESC_OpInProgessTitle, "Bestätigung");
		de.put(DESC_OpInProgessCaption, "Ein vorheriger Vorgang läuft noch. Möchten Sie wirklich fortsetzen? Der andere Vorgang wird storniert.");
		de.put(DESC_ServerError, "Schade! Wir sind auf einen schweren Fehler gestoßen. Bitte versuchen Sie es später.");
		de.put(DESC_NoSearchResults, "Keine Ergebnisse für den Suchbegriff.");
		de.put(DESC_MissingSearchResult, "Einige Webseiten konnten aufgrund von Konnektivitätsproblemen nicht analysiert werden.");
		de.put(DESC_NoParsedDocs, "Wir konnten keine der Webseiten/Dateien verarbeiten.");
		de.put(DESC_MissingDoc, "Einige Webseiten/Dateien konnten nicht analysiert werden.");
		de.put(DESC_AnalysisComplete, "Analyse abgeschlossen.");
		de.put(DESC_KeywordTitle, "Schlüsselwort");
		de.put(DESC_OpTimeout, "Der Vorgang wurde storniert, weil er zu lange gedauert hat");
	}
	
	public static final WebRankerCoreLocale		INSTANCE = new WebRankerCoreLocale();
}
