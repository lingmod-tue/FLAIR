package com.flair.client.presentation.interfaces;

/*
 * Represents a slider widget that has a toggle and a slider input
 */
public interface AbstractWeightSlider
{
	public interface ToggleHandler {
		public void handle(AbstractWeightSlider source, boolean newValue);
	}
	
	public interface WeightChangeHandler {
		public void handle(AbstractWeightSlider source, double newValue);
	}
	

	public void 		setToggleHandler(ToggleHandler handler);
	public void 		setWeightChangeHandler(WeightChangeHandler handler);
	
	public boolean 		isEnabled();
	public void 		setEnabled(boolean val, boolean fireEvent);
	public void 		toggleEnabled(boolean fireEvent);
	
	public int 			getWeight();
	public boolean 		hasWeight();
	public void 		setWeight(int val, boolean fireEvent);
	
	public void 		setToggleText(String val);

	public boolean 		isSliderVisible();
	public void 		setSliderVisible(boolean val);
}
