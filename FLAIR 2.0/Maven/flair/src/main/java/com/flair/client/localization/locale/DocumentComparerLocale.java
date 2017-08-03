package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;

public final class DocumentComparerLocale extends SimpleLocale
{
	public static final String		DESC_lblTitleUI = "lblTitleUI";
	public static final String		DESC_btnCompareUI = "btnCompareUI";
	public static final String		DESC_btnCancelUI = "btnCancelUI";
	public static final String		DESC_btnClearSelectionUI = "btnClearSelectionUI";
	public static final String		DESC_NotifyEmpty = "NotifyEmpty";
	public static final String		DESC_ResultCount = "ResultCount";

	@Override
	public void init()
	{
		// EN
		en.put(DESC_lblTitleUI, "Compare Results");
		en.put(DESC_btnCompareUI, "Compare");
		en.put(DESC_btnCancelUI, "Cancel");
		en.put(DESC_NotifyEmpty, "The clipboard is empty");
		en.put(DESC_btnClearSelectionUI, "Clear selection");
		en.put(DESC_ResultCount, "Results");
		
		// DE
		de.put(DESC_lblTitleUI, "Ergebnisse Vergleich");
		de.put(DESC_btnCompareUI, "Vergleichen");
		de.put(DESC_btnCancelUI, "Abbrechen");
		de.put(DESC_NotifyEmpty, "Die Zwischenablage ist leer");
		de.put(DESC_btnClearSelectionUI, "Zwischenablage leeren");
		de.put(DESC_ResultCount, "Ergebnisse");
	}
	
	public static final DocumentComparerLocale		INSTANCE = new DocumentComparerLocale();
}
