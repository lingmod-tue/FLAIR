package com.flair.client.localization.interfaces;

import com.flair.client.localization.LocalizationBinderData;
import com.flair.client.localization.LocalizedComposite;

/*
 * Binds widgets to their localization wrappers
 */
public interface LocalizationBinder<O extends LocalizedComposite>
{
	public LocalizationBinderData bind(O owner);
}
