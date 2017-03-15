package com.flair.client.presentation.widgets;

import org.gwtbootstrap3.extras.slider.client.ui.Slider;
import org.gwtbootstrap3.extras.slider.client.ui.base.constants.OrientationType;
import org.gwtbootstrap3.extras.slider.client.ui.base.constants.TooltipType;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

/*
 * Document length weight slider widget
 */
public class DocumentLengthSlider extends Composite
{
	public interface ChangeHandler {
		public void handle(double newVal);
	}
	
	private static final double		SLIDER_MIN_VAL = 0;
	private static final double		SLIDER_MAX_VAL = 5;
	private static final double		SLIDER_STEP = 1;
	
	private static final String		STYLENAME_WRAPPER = "doc-length-slider-wrapper";
	private static final String		STYLENAME_SLIDER = "doc-length-slider-slider";
	private static final String		STYLENAME_WIDGET = "doc-length-slider";
	
	private final Slider			slider;
	private ChangeHandler			changeHandler;
	
	public DocumentLengthSlider()
	{
		changeHandler = null;
		slider = new Slider(SLIDER_MIN_VAL, SLIDER_MAX_VAL, SLIDER_MAX_VAL);
		slider.setStep(SLIDER_STEP);
		slider.setTooltip(TooltipType.HIDE);
		slider.addStyleName(STYLENAME_SLIDER);
		slider.setOrientation(OrientationType.VERTICAL);
		
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
	
	public void setValue(double val, boolean fireEvent) {
		slider.setValue(val, fireEvent);
	}
	
	public void setChangeHandler(ChangeHandler handler) {
		changeHandler = handler;
	}
}
