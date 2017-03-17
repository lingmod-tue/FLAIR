package com.flair.client.presentation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MatViewport extends Composite
{

	private static MatViewportUiBinder uiBinder = GWT.create(MatViewportUiBinder.class);

	interface MatViewportUiBinder extends UiBinder<Widget, MatViewport>
	{
	}

	public MatViewport()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}

}
