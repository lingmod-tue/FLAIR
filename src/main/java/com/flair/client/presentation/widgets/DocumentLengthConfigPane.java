package com.flair.client.presentation.widgets;

import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.interfaces.CanReset;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialRange;
import gwt.material.design.client.ui.MaterialRow;

public class DocumentLengthConfigPane extends LocalizedComposite implements CanReset {
	private static DocumentLengthConfigUiBinder uiBinder = GWT.create(DocumentLengthConfigUiBinder.class);

	interface DocumentLengthConfigUiBinder extends UiBinder<Widget, DocumentLengthConfigPane> {
	}

	private static DocumentLengthConfigPaneLocalizationBinder localeBinder = GWT.create(DocumentLengthConfigPaneLocalizationBinder.class);

	interface DocumentLengthConfigPaneLocalizationBinder extends LocalizationBinder<DocumentLengthConfigPane> {}

	private static final int SLIDER_MIN_VAL = 0;
	private static final int SLIDER_MAX_VAL = 5;

	public static int getSliderMin() {
		return SLIDER_MIN_VAL;
	}

	public static int getSliderMax() {
		return SLIDER_MAX_VAL;
	}

	public interface WeightChangeHandler {
		public void handle(int newVal);
	}

	@UiField
	@LocalizedField
	MaterialLabel lblTextLengthUI;
	@UiField
	MaterialRow pnlSliderUI;
	@UiField
	MaterialRange sldLengthUI;
	@UiField
	MaterialRow pnlSwitchUI;
	@UiField
	@LocalizedField
	MaterialCheckBox chkShorterTextsUI;

	WeightChangeHandler changeHandler;

	private void updateSwitch() {
		// don't trigger the change handler to avoid recursion
		int weight = getWeight();
		boolean shorterPreferred = weight == SLIDER_MAX_VAL;

		chkShorterTextsUI.setValue(shorterPreferred, false);
	}

	private void initUI() {
		setWeight(SLIDER_MIN_VAL, false);
		setUseSlider(true);
	}

	private void initHandlers() {
		chkShorterTextsUI.addValueChangeHandler(e -> {
			boolean enabled = e.getValue();
			if (enabled)
				setWeight(SLIDER_MAX_VAL, true);
			else
				setWeight(SLIDER_MIN_VAL, true);
		});

		sldLengthUI.addValueChangeHandler(e -> {
			updateSwitch();

			if (changeHandler != null)
				changeHandler.handle(getWeight());
		});
	}

	public DocumentLengthConfigPane() {
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));

		changeHandler = null;
		sldLengthUI.setMax(SLIDER_MAX_VAL);
		sldLengthUI.setMin(SLIDER_MIN_VAL);
		sldLengthUI.setValue(SLIDER_MIN_VAL);

		initHandlers();
		initUI();
	}

	public int getWeight() {
		return sldLengthUI.getValue();
	}

	public void setWeight(int val, boolean fireEvent) {
		sldLengthUI.setValue(val, fireEvent);
		updateSwitch();
	}

	public void setWeightChangeHandler(WeightChangeHandler handler) {
		changeHandler = handler;
	}

	@Override
	public void resetState(boolean fireEvents) {
		setWeight(SLIDER_MIN_VAL, fireEvents);
	}

	public void setUseSlider(boolean val) {
		pnlSwitchUI.setVisible(val == false);
		pnlSliderUI.setVisible(val);
	}
}
