package com.flair.client.presentation.widgets;


import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialIcon;

/**
* NumberSpinner Custom Control
*
* @author Pavan Andhukuri
* Source: https://pavanandhukuri.wordpress.com/2012/01/28/gwt-number-spinner-control/
*
*/
public class NumberSpinner extends Composite {

	interface NumberSpinnerUiBinder extends UiBinder<Widget, NumberSpinner> {
    }

    private static NumberSpinnerUiBinder ourUiBinder = GWT.create(NumberSpinnerUiBinder.class);


    @UiField
    AbsolutePanel pnlAbsolutePanel;
    @UiField
    MaterialIcon btnUp;
    @UiField
    MaterialIcon btnDown;
    @UiField
    IntegerBox boxInteger;
   
    
    /**
     * Initializes all handlers.
     */
    private void initHandlers() {
    	btnUp.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
            	if (getValue() + RATE > max)
                	setValue(max);
                else
                	setValue(getValue() + RATE);
            }
        });
    	
    	btnDown.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (getValue() - RATE < min)
                	setValue(min);
                else
                	setValue(getValue() - RATE);
            }
        });
    	
    	boxInteger.addValueChangeHandler(new ValueChangeHandler<Integer>() {

			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				if(event.getValue() < min) {
					boxInteger.setValue(min);
				} else if(event.getValue() > max) {
					boxInteger.setValue(max);
				}
				
			}
    		
    	});
    }
    
    private int RATE = 1;
    private int min = 0;
    private int max = 10;
    private int defaultValue = 1;


    public NumberSpinner() {
        initWidget(ourUiBinder.createAndBindUi(this));
        this.initHandlers();
    }

    /**
     * Returns the value being held.
     *
     * @return
     */
    public int getValue() {
        return boxInteger.getValue() == null ? 0 : boxInteger.getValue();
    }

    /**
     * Sets the value to the control
     *
     * @param value
     *            Value to be set
     */
    public void setValue(int value) {
    	boxInteger.setValue(value);
    }

    /**
     * Sets the rate at which increment or decrement is done.
     *
     * @param rate
     */
    public void setRate(int rate) {
        this.RATE = rate;
    }
    
    /**
     * Sets the minimum value.
     *
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }
    
    /**
     * Sets the maximum value.
     *
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }
}