package com.flair.client.localization.wrappers;

import com.google.gwt.user.client.ui.Widget;

/*
 * SetText update method specialization for widget alternate texts (native tooltip)
 */
public class SimpleLocalizedAltTextWidget<T extends Widget> extends SimpleLocalizedWidget<T> {
	public SimpleLocalizedAltTextWidget(T w, String provider, String tag) {
		super(w, provider, tag, (widget, str) -> {
			widget.setTitle(str);
		});
	}
}