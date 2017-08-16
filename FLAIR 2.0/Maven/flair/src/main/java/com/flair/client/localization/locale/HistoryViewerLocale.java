package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;

public final class HistoryViewerLocale extends SimpleLocale
{
	public static final String		DESC_lblTitleUI = "lblTitleUI";
	public static final String		DESC_ResultCount = "ResultCount";
	public static final String		DESC_AnalysesCount = "AnalysesCount";
	public static final String		DESC_NotifyEmpty = "NotifyEmpty";
	
	@Override
	public void init()
	{
		// EN
		en.put(DESC_lblTitleUI, "Analysis History");
		en.put(DESC_ResultCount, "Results");
		en.put(DESC_AnalysesCount, "Analyses");
		en.put(DESC_NotifyEmpty, "No analyses so far");
		
		// DE
		de.put(DESC_lblTitleUI, "Analysenchronik");
		de.put(DESC_ResultCount, "Ergebnisse");
		de.put(DESC_AnalysesCount, "Analysen");
		de.put(DESC_NotifyEmpty, "Keine Analysen bisher");
	}
	
	public static final HistoryViewerLocale		INSTANCE = new HistoryViewerLocale();
}
