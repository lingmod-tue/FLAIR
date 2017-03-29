package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;

public final class DocumentResultDisplayItemLocale extends SimpleLocale
{
	public static final String		DESC_ItemOrgRank = "ItemOrgRank";
	
	@Override
	public void init()
	{
		// EN
		en.put(DESC_ItemOrgRank, "Original Rank");

		// DE
		de.put(DESC_ItemOrgRank, "Origineller Rang");
	}
	
	public static final DocumentResultDisplayItemLocale		INSTANCE = new DocumentResultDisplayItemLocale();
}