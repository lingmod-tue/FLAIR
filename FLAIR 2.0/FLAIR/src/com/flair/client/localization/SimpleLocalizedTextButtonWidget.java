package com.flair.client.localization;

import gwt.material.design.client.base.AbstractButton;

/*
 * SetText update method specialization for Material buttons
 */
public class SimpleLocalizedTextButtonWidget<T extends AbstractButton> extends SimpleLocalizedWidget<T>
{
	public SimpleLocalizedTextButtonWidget(T w, String desc)
	{
		super(w, desc, (widget, str) -> {
			widget.setText(str);
		});
	}
}