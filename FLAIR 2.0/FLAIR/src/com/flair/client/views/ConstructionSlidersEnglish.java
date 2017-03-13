package com.flair.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ConstructionSlidersEnglish extends Composite
{

	private static ConstructionSlidersEnglishUiBinder uiBinder = GWT.create(ConstructionSlidersEnglishUiBinder.class);

	interface ConstructionSlidersEnglishUiBinder extends UiBinder<Widget, ConstructionSlidersEnglish>
	{
	}

	public ConstructionSlidersEnglish()
	{
		initWidget(uiBinder.createAndBindUi(this));
	}

}
