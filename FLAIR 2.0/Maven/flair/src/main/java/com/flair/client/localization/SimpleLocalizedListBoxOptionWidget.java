package com.flair.client.localization;

import gwt.material.design.client.ui.html.Option;

/*
 * SetText update method specialization for Material listbox options
 */
public class SimpleLocalizedListBoxOptionWidget extends SimpleLocalizedWidget<Option>
{
	public SimpleLocalizedListBoxOptionWidget(Option w, String desc)
	{
		super(w, desc, (widget, str) -> {
			widget.setText(str);
		});
	}
}
