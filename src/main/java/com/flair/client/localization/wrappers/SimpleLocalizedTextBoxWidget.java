package com.flair.client.localization.wrappers;

import gwt.material.design.client.ui.MaterialValueBox;

/*
 * SetLabel/Placeholder update method specialization for Material value boxes
 */
public class SimpleLocalizedTextBoxWidget<T extends MaterialValueBox<?>> extends SimpleLocalizedWidget<T> {
	public SimpleLocalizedTextBoxWidget(T w, String provider, String tag, boolean placeholder) {
		super(w, provider, tag, (widget, str) -> {
			if (placeholder)
				widget.setPlaceholder(str);
			else
				widget.setLabel(str);
		});
	}
}