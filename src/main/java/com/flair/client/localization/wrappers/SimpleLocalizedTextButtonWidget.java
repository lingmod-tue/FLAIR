package com.flair.client.localization.wrappers;

import gwt.material.design.client.base.AbstractButton;

/*
 * SetText update method specialization for Material buttons
 */
public class SimpleLocalizedTextButtonWidget<T extends AbstractButton> extends SimpleLocalizedWidget<T> {
	public SimpleLocalizedTextButtonWidget(T w, String provider, String tag) {
		super(w, provider, tag, (widget, str) -> {
			widget.setText(str);
		});
	}
}