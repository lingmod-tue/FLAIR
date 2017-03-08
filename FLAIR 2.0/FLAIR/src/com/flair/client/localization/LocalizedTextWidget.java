package com.flair.client.localization;

import com.google.gwt.user.client.ui.HasText;

/*
 * SetText update method specialization
 */
public class LocalizedTextWidget<T extends HasText> extends LocalizedWidget<T>
{
	public LocalizedTextWidget(T w, String desc)
	{
		super(w, desc, (widget, str) -> {
			widget.setText(str);
		});
	}
}
