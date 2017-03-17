package com.flair.client.presentation.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

import gwt.material.design.client.ui.MaterialRange;

/*
 * Document length weight slider widget
 */
public class DocumentLengthSlider extends Composite
{
	public interface ChangeHandler {
		public void handle(double newVal);
	}
	
	private static final int		SLIDER_MIN_VAL = 0;
	private static final int		SLIDER_MAX_VAL = 5;
	private static final int		SLIDER_STEP = 1;
	
	private static final String		STYLENAME_WRAPPER = "doc-length-slider-wrapper";
	private static final String		STYLENAME_SLIDER = "doc-length-slider-slider";
	private static final String		STYLENAME_WIDGET = "doc-length-slider";
	
	private final MaterialRange		slider;
	private ChangeHandler			changeHandler;
	
	public DocumentLengthSlider()
	{
		changeHandler = null;
		slider = new MaterialRange(SLIDER_MIN_VAL, SLIDER_MAX_VAL, SLIDER_MAX_VAL);
		slider.addStyleName(STYLENAME_SLIDER);
		
		FlowPanel container = new FlowPanel();
		container.setStyleName(STYLENAME_WRAPPER);
		container.add(slider);
		
		initWidget(container);
		setStyleName(STYLENAME_WIDGET);
		
		slider.addValueChangeHandler(e -> {
			if (changeHandler != null)
				changeHandler.handle(getValue());
		});
	}

	public double getValue() {
		return slider.getValue();
	}
	
	public void setValue(int val, boolean fireEvent) {
		slider.setValue(val, fireEvent);
	}
	
	public void setChangeHandler(ChangeHandler handler) {
		changeHandler = handler;
	}
}
