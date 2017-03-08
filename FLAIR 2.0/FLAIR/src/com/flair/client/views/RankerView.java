package com.flair.client.views;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RankerView extends Composite {

	private static RankerViewUiBinder uiBinder = GWT.create(RankerViewUiBinder.class);

	interface RankerViewUiBinder extends UiBinder<Widget, RankerView> {
	}

	public RankerView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
