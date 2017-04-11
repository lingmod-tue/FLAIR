package com.flair.client.presentation.widgets;

import com.flair.client.presentation.interfaces.CanReset;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

import gwt.material.design.client.ui.MaterialRange;

/*
 * Document length weight slider widget
 */
public class DocumentLengthSlider extends Composite implements CanReset
{
	public interface WeightChangeHandler {
		public void handle(int newVal);
	}
	
	private static final int		SLIDER_MIN_VAL = 0;
	private static final int		SLIDER_MAX_VAL = 5;
	
	public static int getSliderMin() {
		return SLIDER_MIN_VAL;
	}
	
	public static int getSliderMax() {
		return SLIDER_MAX_VAL;
	}
	
	private final MaterialRange		slider;
	private WeightChangeHandler		changeHandler;
	
	public DocumentLengthSlider()
	{
		changeHandler = null;
		slider = new MaterialRange(SLIDER_MIN_VAL, SLIDER_MAX_VAL, SLIDER_MAX_VAL);
		
		FlowPanel container = new FlowPanel();
		container.add(slider);
		
		initWidget(container);
		
		slider.addValueChangeHandler(e -> {
			if (changeHandler != null)
				changeHandler.handle(getWeight());
		});
	}

	public int getWeight() {
		return slider.getValue();
	}
	
	public void setWeight(int val, boolean fireEvent) {
		slider.setValue(val, fireEvent);
	}
	
	public void setWeightChangeHandler(WeightChangeHandler handler) {
		changeHandler = handler;
	}

	@Override
	public void resetState(boolean fireEvents) {
		setWeight(SLIDER_MAX_VAL, fireEvents);
	}
}
