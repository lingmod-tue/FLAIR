package com.flair.client.localization.wrappers;

import gwt.material.design.client.ui.MaterialNavBrand;

/*
 * SetText update method specialization
 */
public class SimpleLocalizedNavBrandWidget<T extends MaterialNavBrand> extends SimpleLocalizedWidget<T> {
	public SimpleLocalizedNavBrandWidget(T w, String provider, String tag) {
		super(w, provider, tag, (widget, str) -> {
			widget.setText(str);
		});
	}
}