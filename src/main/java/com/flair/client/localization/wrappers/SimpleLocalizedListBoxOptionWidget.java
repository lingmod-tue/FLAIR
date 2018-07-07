package com.flair.client.localization.wrappers;

import gwt.material.design.client.ui.html.Option;

/*
 * SetText update method specialization for Material listbox options
 */
public class SimpleLocalizedListBoxOptionWidget<T extends Option> extends SimpleLocalizedWidget<T> {
	public SimpleLocalizedListBoxOptionWidget(T w, String provider, String tag) {
		super(w, provider, tag, (widget, str) -> {
			widget.setText(str);
		});
	}
}
