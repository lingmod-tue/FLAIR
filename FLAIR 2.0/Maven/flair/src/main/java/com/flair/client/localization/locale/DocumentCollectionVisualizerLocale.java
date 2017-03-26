package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;

public final class DocumentCollectionVisualizerLocale extends SimpleLocale
{
	public static final String		DESC_lblTitleUI = "lblTitleUI";
	public static final String		DESC_lblTitleCaptionUI = "lblTitleCaptionUI";
	public static final String		DESC_btnResetUI = "btnResetUI";
	public static final String		DESC_chkAxisWordsUI = "chkAxisWordsUI";
	public static final String		DESC_chkAxisSentencesUI = "chkAxisSentencesUI";
	public static final String		DESC_chkAxisComplexityUI = "chkAxisComplexityUI";
	public static final String		DESC_chkAxisKeywordsUI = "chkAxisKeywordsUI";
	
	public static final String		DESC_btnApplyUI = "btnApplyUI";
	public static final String		DESC_btnCancelUI = "btnCancelUI";
	
	public static final String		DESC_axisResult = "axisResult";
	public static final String		DESC_axisSentences = "axisSentences";
	public static final String		DESC_axisWords = "axisWords";
	public static final String		DESC_axisComplexity = "axisComplexity";
	public static final String		DESC_axisKeywords = "axisKeywords";

	public static final String		DESC_NoFilteredDocs = "NoFilteredDocs";
	public static final String		DESC_HasFilteredDocs = "HasFilteredDocs";

	@Override
	public void init()
	{
		// EN
		en.put(DESC_lblTitleUI, "Interactive Visualization");
		en.put(DESC_lblTitleCaptionUI, "Select ranges by dragging the pointers up and down. Change the order of axes by dragging them left and right. Add or remove axes via checkboxes on the right.");
		en.put(DESC_btnResetUI, "Reset");
		en.put(DESC_chkAxisWordsUI, "# of Words");
		en.put(DESC_chkAxisSentencesUI, "# of Sentences");
		en.put(DESC_chkAxisComplexityUI, "Complexity");
		en.put(DESC_chkAxisKeywordsUI, "Keywords");

		en.put(DESC_btnApplyUI, "Filter");
		en.put(DESC_btnCancelUI, "Cancel");
		
		en.put(DESC_axisResult, "Result");
		en.put(DESC_axisSentences, "# of Sentences");
		en.put(DESC_axisWords, "# of Words");
		en.put(DESC_axisComplexity, "Complexity");
		en.put(DESC_axisKeywords, "Keywords");
		
		en.put(DESC_NoFilteredDocs, "None of the results were filtered.");
		en.put(DESC_HasFilteredDocs, "Filter applied. Filtered results");
		
		// DE
		de.put(DESC_lblTitleUI, "Interaktive Visualisierung");
		de.put(DESC_lblTitleCaptionUI, "Bereiche auswählen durch Hoch- und Runterziehen des Zeigers. Reihenfolge der Achsen umtauschen durch Rechts- und Linksbewegen der Achsen. Achsen hinzufügen oder entfernen durch An- und Abwählen der Kontrollkästchen rechts.");
		de.put(DESC_btnResetUI, "Zurücksetzen");
		de.put(DESC_chkAxisWordsUI, "# von Wörter");
		de.put(DESC_chkAxisSentencesUI, "# von Sätze");
		de.put(DESC_chkAxisComplexityUI, "Komplexität");
		de.put(DESC_chkAxisKeywordsUI, "Schlüsselwörter");

		de.put(DESC_btnApplyUI, "Übernehmen");
		de.put(DESC_btnCancelUI, "Abbrechen");
		
		de.put(DESC_axisResult, "Ergebnis");
		de.put(DESC_axisSentences, "# von Sätze");
		de.put(DESC_axisWords, "# von Wörter");
		de.put(DESC_axisComplexity, "Komplexität");
		de.put(DESC_axisKeywords, "Schlüsselwörter");
		
		de.put(DESC_NoFilteredDocs, "Keine der Ergebnisse wurden gefiltert.");
		de.put(DESC_HasFilteredDocs, "Gefilterte Ergebnisse");
	}
	
	public static final DocumentCollectionVisualizerLocale		INSTANCE = new DocumentCollectionVisualizerLocale();
}
