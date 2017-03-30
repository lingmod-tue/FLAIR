package com.flair.client.presentation.widgets;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizedComposite;
import com.google.gwt.uibinder.client.UiConstructor;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialRange;

/*
 * Base class for custom weight slider widgets
 */
public abstract class GenericWeightSlider extends LocalizedComposite
{
	public interface ToggleHandler {
		public void handle(GenericWeightSlider source, boolean newValue);
	}
	
	public interface WeightChangeHandler {
		public void handle(GenericWeightSlider source, double newValue);
	}
		
	protected static final int		SLIDER_MIN_VAL = 0;
	protected static final int		SLIDER_MAX_VAL = 5;
	protected static final int		SLIDER_STEP = 1;
	
	public static int getSliderMin() {
		return SLIDER_MIN_VAL;
	}
	
	public static int getSliderMax() {
		return SLIDER_MAX_VAL;
	}
	
	protected final MaterialCheckBox	toggle;
	protected final MaterialRange 		slider;
	private ToggleHandler				toggleHandler;
	private WeightChangeHandler			weightHandler;
	
	private void onToggle()
	{
		slider.setEnabled(isEnabled());
		if (toggleHandler != null)
			toggleHandler.handle(this, isEnabled());
	}
	
	private void onWeightChange()
	{
		if (weightHandler != null)
			weightHandler.handle(this, getWeight());
	}
	
	private void initCtor()
	{
		toggleHandler = null;
		weightHandler = null;
		
		// setup components
		toggle.setValue(true);;
		slider.setMin(SLIDER_MIN_VAL);
		slider.setMax(SLIDER_MAX_VAL);
		slider.setValue(SLIDER_MIN_VAL);
		slider.setPadding(3);
		
		toggle.addValueChangeHandler(e -> {
			onToggle();
		});
		slider.addValueChangeHandler(e -> {
			onWeightChange();
		});
	}
	
	@UiConstructor
	public GenericWeightSlider()
	{
		super(ClientEndPoint.get().getLocalization());
		toggle = new MaterialCheckBox();
		slider = new MaterialRange();
		
		initCtor();
	}
	
	public final void setToggleHandler(ToggleHandler handler) {
		toggleHandler = handler;
	}
	
	public final void setWeightChangeHandler(WeightChangeHandler handler) {
		weightHandler = handler;
	}
	
	public boolean isEnabled() {
		return toggle.getValue();
	}
	
	public int getWeight() {
		return slider.getValue();
	}
	
	public boolean hasWeight() {
		return slider.getValue() != SLIDER_MIN_VAL;
	}
	
	public void setEnabled(boolean val, boolean fireEvent)
	{
		toggle.setValue(val, fireEvent);
		slider.setEnabled(val);
	}
	
	public void toggleEnabled(boolean fireEvent) {
		setEnabled(isEnabled() == false, fireEvent);
	}
	
	public void setWeight(int val, boolean fireEvent) {
		slider.setValue(val, fireEvent);
	}
	
	public void setToggleText(String val) {
		toggle.setText(val);
	}

	public boolean isSliderVisible() {
		return slider.isVisible();
	}
	
	public void setSliderVisible(boolean val) {
		slider.setVisible(val);
	}
}
