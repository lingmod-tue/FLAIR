package com.flair.client.localization;

import com.google.gwt.user.client.ui.HasHTML;

/*
 * SetHTML update method specialization
 */
public class LocalizedHTMLWidget<T extends HasHTML> extends LocalizedWidget<T>
{
	public LocalizedHTMLWidget(T w, String desc)
	{
		super(w, desc, (widget, str) -> {
			widget.setHTML(str);
		});
	}
}
