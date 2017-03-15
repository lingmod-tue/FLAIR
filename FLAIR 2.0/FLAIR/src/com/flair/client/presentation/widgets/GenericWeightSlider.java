package com.flair.client.presentation.widgets;

import org.gwtbootstrap3.client.ui.InlineCheckBox;
import org.gwtbootstrap3.extras.slider.client.ui.Slider;
import org.gwtbootstrap3.extras.slider.client.ui.base.constants.HandleType;
import org.gwtbootstrap3.extras.slider.client.ui.base.constants.TooltipType;

import com.flair.client.localization.LocalizedComposite;
import com.flair.client.model.ClientEndPoint;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

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
	
	protected static final double		SLIDER_MIN_VAL = 0;
	protected static final double		SLIDER_MAX_VAL = 5;
	protected static final double		SLIDER_STEP = 1;
	
	protected final InlineCheckBox	toggle;
	protected final Slider			slider;
	protected final FlowPanel		sliderPanel;
	private ToggleHandler			toggleHandler;
	private WeightChangeHandler		weightHandler;
	
	private void onToggle()
	{
		if (toggleHandler != null)
			toggleHandler.handle(this, isEnabled());
	}
	
	private void onWeightChange()
	{
		if (weightHandler != null)
			weightHandler.handle(this, getValue());
	}
	
	private void initCtor(boolean hideSlider)
	{
		toggleHandler = null;
		weightHandler = null;
		
		// setup components
		toggle.setText("Generic Weight Slider");
		slider.setMin(SLIDER_MIN_VAL);
		slider.setMax(SLIDER_MAX_VAL);
		slider.setStep(SLIDER_STEP);
		slider.setTooltip(TooltipType.HIDE);
		slider.setHandle(HandleType.ROUND);
		slider.setValue(SLIDER_MIN_VAL);
		slider.addStyleName(STYLENAME_SLIDER);
		
		sliderPanel.setStyleName(STYLENAME_WRAPPER);
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
		toggle = new InlineCheckBox();
		slider = new Slider();
		sliderPanel = new FlowPanel();
		
		initCtor(false);
	}
	
	@UiConstructor
	public GenericWeightSlider(boolean hideSlider) 
	{
		super(ClientEndPoint.get().getLocalization());
		toggle = new InlineCheckBox();
		slider = new Slider();
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
	
	public double getValue() {
		return slider.getValue();
	}
	
	public void setEnabled(boolean val, boolean fireEvent) 
	{
		toggle.setValue(val, fireEvent);
		slider.setEnabled(val);
	}
	
	public void toggleEnabled() {
		slider.toggle();
	}
	
	public void setValue(double val, boolean fireEvent) {
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
