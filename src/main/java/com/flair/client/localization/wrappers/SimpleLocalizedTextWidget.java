package com.flair.client.localization.wrappers;

import com.google.gwt.user.client.ui.HasText;

/*
 * SetText update method specialization
 */
public class SimpleLocalizedTextWidget<T extends HasText> extends SimpleLocalizedWidget<T>
{
	public SimpleLocalizedTextWidget(T w, String provider, String tag)
	{
		super(w, provider, tag, (widget, str) -> {
			widget.setText(str);
		});
	}
}
