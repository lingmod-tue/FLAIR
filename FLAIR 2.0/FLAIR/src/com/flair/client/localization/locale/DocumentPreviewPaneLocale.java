package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;

public final class DocumentPreviewPaneLocale extends SimpleLocale
{
	public static final String		DESC_lblPlaceholderText = "lblPlaceholderText";
	public static final String		DESC_lblDocNumSentences = "lblDocNumSentences";
	public static final String		DESC_lblDocNumWords = "lblDocNumWords";
	public static final String		DESC_icoHelpText = "icoHelpText";
	public static final String		DESC_tglConstructionDetails = "tglConstructionDetails";
	public static final String		DESC_tableColConstruction = "tableColConstruction";
	public static final String		DESC_tableColHits = "tableColHits";
	public static final String		DESC_tableColWeight = "tableColWeight";
	public static final String		DESC_tableColRelFreq = "tableColRelFreq";
	
	@Override
	public void init()
	{
		// EN
		en.put(DESC_lblPlaceholderText, "Select a result to view its analysis");
		en.put(DESC_lblDocNumSentences, "Sentence(s)");
		en.put(DESC_lblDocNumWords, "Word(s)");
		en.put(DESC_icoHelpText, "Highlights may overlap - Mouse over a highlight to see a tooltip with the names of all embedded constructions");
		en.put(DESC_tglConstructionDetails, "All Constructions");
		en.put(DESC_tableColConstruction, "Construction");
		en.put(DESC_tableColHits, "Count");
		en.put(DESC_tableColWeight, "Weight");
		en.put(DESC_tableColRelFreq, "Relative Frequency %");
		
		// DE
		de.put(DESC_lblPlaceholderText, "Klicken Sie auf ein Suchergebnis, um hier den Text anzuzeigen");
		de.put(DESC_lblDocNumSentences, "Sätze");
		de.put(DESC_lblDocNumWords, "Wörter");
		de.put(DESC_icoHelpText, "Hervorhebungen können sich überlappen - Bewegen Sie die Maus über eine Hervorhebung, um einen Tooltip mit den Namen aller eingebetteten Konstruktionen zu sehen");
		de.put(DESC_tglConstructionDetails, "Alle Konstruktionen");
		de.put(DESC_tableColConstruction, "Konstruktion");
		de.put(DESC_tableColHits, "Anzahl");
		de.put(DESC_tableColWeight, "Gewichtung");
		de.put(DESC_tableColRelFreq, "Relative Häufigkeit in %");
	}
	
	public static final DocumentPreviewPaneLocale		INSTANCE = new DocumentPreviewPaneLocale();
}
