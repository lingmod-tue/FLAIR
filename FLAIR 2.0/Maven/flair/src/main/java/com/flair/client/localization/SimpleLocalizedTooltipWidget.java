package com.flair.client.localization;

import gwt.material.design.client.base.HasTooltip;

/*
 * SetTooltip update method specialization
 */
public class SimpleLocalizedTooltipWidget<T extends HasTooltip> extends SimpleLocalizedWidget<T>
{
	public SimpleLocalizedTooltipWidget(T w, String desc)
	{
		super(w, desc, (widget, str) -> {
			widget.setTooltip(str);
		});
	}
}
