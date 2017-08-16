package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;

public final class DocumentResultDisplayItemLocale extends SimpleLocale
{
	public static final String		DESC_ItemOrgRank = "ItemOrgRank";
	public static final String		DESC_btnAddToCompareUI = "btnAddToCompareUI";
	
	@Override
	public void init()
	{
		// EN
		en.put(DESC_ItemOrgRank, "Original Rank");
		en.put(DESC_btnAddToCompareUI, "Compare");

		// DE
		de.put(DESC_ItemOrgRank, "Origineller Rang");
		de.put(DESC_btnAddToCompareUI, "Vergleichen");
	}
	
	public static final DocumentResultDisplayItemLocale		INSTANCE = new DocumentResultDisplayItemLocale();
}