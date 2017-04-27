package com.flair.client.presentation.widgets;

import com.flair.client.presentation.interfaces.AbstractWeightSlider;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialRange;

/*
 * Base class for custom weight slider widgets
 */
public class GenericWeightSlider implements AbstractWeightSlider
{
	protected static final int		SLIDER_MIN_VAL = 0;
	protected static final int		SLIDER_MAX_VAL = 5;
	protected static final int		SLIDER_STEP = 1;
	
	public static int getSliderMin() {
		return SLIDER_MIN_VAL;
	}
	
	public static int getSliderMax() {
		return SLIDER_MAX_VAL;
	}
	
	private final AbstractWeightSlider	parent;
	protected MaterialCheckBox			toggle;
	protected MaterialRange 			slider;
	private ToggleHandler				toggleHandler;
	private WeightChangeHandler			weightHandler;
	
	private void onToggle()
	{
		slider.setEnabled(isEnabled());
		if (toggleHandler != null)
			toggleHandler.handle(parent, isEnabled());
	}
	
	private void onWeightChange()
	{
		if (weightHandler != null)
			weightHandler.handle(parent, getWeight());
	}
		
	public GenericWeightSlider(AbstractWeightSlider p, MaterialCheckBox t, MaterialRange r)
	{
		parent = p;
		toggle = t;
		slider = r;
		toggleHandler = null;
		weightHandler = null;
		
		// setup components
		toggle.setValue(true);
		slider.setMin(SLIDER_MIN_VAL);
		slider.setMax(SLIDER_MAX_VAL);
		slider.setValue(SLIDER_MIN_VAL);
		
		toggle.addValueChangeHandler(e -> {
			onToggle();
		});
		slider.addValueChangeHandler(e -> {
			onWeightChange();
		});
	}
	
	@Override
	public void setToggleHandler(ToggleHandler handler) {
		toggleHandler = handler;
	}
	
	@Override
	public void setWeightChangeHandler(WeightChangeHandler handler) {
		weightHandler = handler;
	}
	
	@Override
	public boolean isEnabled() {
		return toggle.getValue();
	}
	
	@Override
	public int getWeight() {
		return slider.getValue();
	}
	
	@Override
	public boolean hasWeight() {
		return slider.getValue() != SLIDER_MIN_VAL;
	}
	
	@Override
	public void setEnabled(boolean val, boolean fireEvent)
	{
		toggle.setValue(val, fireEvent);
		slider.setEnabled(val);
	}
	
	@Override
	public void toggleEnabled(boolean fireEvent) {
		setEnabled(isEnabled() == false, fireEvent);
	}
	
	@Override
	public void setWeight(int val, boolean fireEvent) {
		slider.setValue(val, fireEvent);
	}
	
	@Override
	public void setToggleText(String val) {
		toggle.setText(val);
	}

	@Override
	public boolean isSliderVisible() {
		return slider.isVisible();
	}
	
	@Override
	public void setSliderVisible(boolean val) {
		slider.setVisible(val);
	}
}
