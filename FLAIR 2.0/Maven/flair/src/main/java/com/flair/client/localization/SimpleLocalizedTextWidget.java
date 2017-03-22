package com.flair.client.localization;

import com.google.gwt.user.client.ui.HasText;

/*
 * SetText update method specialization
 */
public class SimpleLocalizedTextWidget<T extends HasText> extends SimpleLocalizedWidget<T>
{
	public SimpleLocalizedTextWidget(T w, String desc)
	{
		super(w, desc, (widget, str) -> {
			widget.setText(str);
		});
	}
}
