package com.flair.client.localization;

import com.google.gwt.user.client.ui.HasHTML;

/*
 * SetHTML update method specialization
 */
public class SimpleLocalizedHTMLWidget<T extends HasHTML> extends SimpleLocalizedWidget<T>
{
	public SimpleLocalizedHTMLWidget(T w, String desc)
	{
		super(w, desc, (widget, str) -> {
			widget.setHTML(str);
		});
	}
}
