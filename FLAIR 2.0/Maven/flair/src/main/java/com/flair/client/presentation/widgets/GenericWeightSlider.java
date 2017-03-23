package com.flair.client.presentation.widgets;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizedComposite;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

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
	
	protected static final String		STYLENAME_TOGGLE = "generic-weight-slider-toggle";
	protected static final String		STYLENAME_WRAPPER = "generic-weight-slider-wrapper";
	protected static final String		STYLENAME_SLIDER = "generic-weight-slider-slider";
	
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
	protected final HorizontalPanel		togglePanel;
	protected final FlowPanel			sliderPanel;
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
	
	private void initCtor(boolean hideSlider)
	{
		toggleHandler = null;
		weightHandler = null;
		
		// setup components
		toggle.setValue(true);;
		slider.setMin(SLIDER_MIN_VAL);
		slider.setMax(SLIDER_MAX_VAL);
		slider.setValue(SLIDER_MIN_VAL);
		
		slider.addStyleName(STYLENAME_SLIDER);
		sliderPanel.setStyleName(STYLENAME_WRAPPER);
		togglePanel.setStyleName(STYLENAME_TOGGLE);
		
		if (hideSlider == false)
			sliderPanel.add(slider);
		
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
		togglePanel = new HorizontalPanel();
		sliderPanel = new FlowPanel();
		
		initCtor(false);
	}
	
	@UiConstructor
	public GenericWeightSlider(boolean hideSlider) 
	{
		super(ClientEndPoint.get().getLocalization());
		toggle = new MaterialCheckBox();
		slider = new MaterialRange();
		togglePanel = new HorizontalPanel();
		sliderPanel = new FlowPanel();
		
		initCtor(hideSlider);
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
	
	public double getWeight() {
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
		return slider.isAttached();
	}
	
	public void setSliderVisible(boolean val)
	{
		if (isSliderVisible() && val == false)
			sliderPanel.remove(slider);
		else if (isSliderVisible() == false && val)
			sliderPanel.add(slider);
	}
}
