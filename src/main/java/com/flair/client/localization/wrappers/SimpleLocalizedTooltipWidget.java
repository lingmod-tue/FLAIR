package com.flair.client.localization.wrappers;

import gwt.material.design.client.base.HasTooltip;

/*
 * SetTooltip update method specialization
 */
public class SimpleLocalizedTooltipWidget<T extends HasTooltip> extends SimpleLocalizedWidget<T> {
	public SimpleLocalizedTooltipWidget(T w, String provider, String tag) {
		super(w, provider, tag, (widget, str) -> {
			widget.setTooltip(str);
		});
	}
}
