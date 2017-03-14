package com.flair.client.views;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.widgets.KeywordWeightSlider;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

public class RankerView extends LocalizedComposite 
{
	private static RankerViewUiBinder uiBinder = GWT.create(RankerViewUiBinder.class);

	interface RankerViewUiBinder extends UiBinder<Widget, RankerView> {
	}

	//  ### NOTE! some of the gram constructions will change when switching to DE
	// switch these too inside the update method
	@UiField
	KeywordWeightSlider sldKeywordsUI;

	public RankerView() 
	{
		super(ClientEndPoint.get().getLocalization());
		initWidget(uiBinder.createAndBindUi(this));
	}

}
