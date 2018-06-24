package com.flair.client.localization.wrappers;

import gwt.material.design.client.base.HasTitle;

/*
 * SetText update method specialization for widgets with titles
 */
public class SimpleLocalizedTitleWidget<T extends HasTitle> extends SimpleLocalizedWidget<T>
{
	public SimpleLocalizedTitleWidget(T w, String provider, String tag, boolean updateDesc)
	{
		super(w, provider, tag, (widget, str) -> {
			if (updateDesc)
				widget.setDescription(str);
			else
				widget.setTitle(str);
		});
	}
}