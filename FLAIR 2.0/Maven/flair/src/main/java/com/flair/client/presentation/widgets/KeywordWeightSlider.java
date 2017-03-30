package com.flair.client.presentation.widgets;

import com.flair.client.localization.locale.KeywordWeightSliderLocale;
import com.flair.client.presentation.interfaces.CanReset;
import com.flair.shared.grammar.Language;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.constants.IconType;
import gwt.material.design.client.constants.WavesType;
import gwt.material.design.client.ui.MaterialIcon;

/*
 * Weight slider for keywords
 */
public class KeywordWeightSlider extends GenericWeightSlider implements CanReset
{
	public interface ClickHandler {
		public void handle(KeywordWeightSlider source);
	}
	
	private final MaterialIcon		editKeywords;
	private final MaterialIcon 		resetKeywords;
	private ClickHandler			editHandler;
	private ClickHandler			resetHandler;
	private boolean					customVocab;
	
	private void initLocale()
	{
		registerLocale(KeywordWeightSliderLocale.INSTANCE.en);
		registerLocale(KeywordWeightSliderLocale.INSTANCE.de);
		
		refreshLocalization();
	}
	
	@UiConstructor
	public KeywordWeightSlider()
	{
		editKeywords = new MaterialIcon(IconType.MODE_EDIT);
		resetKeywords = new MaterialIcon(IconType.UNDO);
		editHandler = resetHandler = null;
		customVocab = false;
		
		// setup components
		setEnabled(false, false);
		
		editKeywords.setWaves(WavesType.DEFAULT);
		editKeywords.setCircle(true);
		editKeywords.setIconColor(Color.BLUE);

		resetKeywords.setWaves(WavesType.DEFAULT);
		resetKeywords.setCircle(true);
		resetKeywords.setIconColor(Color.BLUE);
		
		editKeywords.addClickHandler(e -> {
			if (editHandler != null)
				editHandler.handle(this);
		});

		resetKeywords.addClickHandler(e -> {
			if (resetHandler != null)
				resetHandler.handle(this);
		});
		
		VerticalPanel container = new VerticalPanel();
		HorizontalPanel togglePanel = new HorizontalPanel();
		togglePanel.add(toggle);
		togglePanel.add(editKeywords);
		togglePanel.add(resetKeywords);
		
		container.add(togglePanel);
		container.add(slider);
		
		initWidget(container);
		initLocale();
	}
	
	public boolean hasCustomVocab() {
		return customVocab;
	}
	
	public void setCustomVocab(boolean val)
	{
		customVocab = val;
		refreshLocalization();
	}

	public void setEditHander(ClickHandler handler) {
		editHandler = handler;
	}
	
	public void setResetHandler(ClickHandler handler) {
		resetHandler = handler;
	}

	@Override
	public void setLocalization(Language lang)
	{
		super.setLocalization(lang);
		
		// update components
		setToggleText(getLocalizationData(lang).get(customVocab == false ? KeywordWeightSliderLocale.DESC_toggleDefault : KeywordWeightSliderLocale.DESC_toggleCustom));
		toggle.setTitle(getLocalizationData(lang).get(KeywordWeightSliderLocale.DESC_toggleTooltip));
		
		editKeywords.setTooltip(getLocalizationData(lang).get(KeywordWeightSliderLocale.DESC_editTooltip));
		resetKeywords.setTooltip(getLocalizationData(lang).get(KeywordWeightSliderLocale.DESC_resetTooltip));
		slider.setTooltip(getLocalizationData(lang).get(KeywordWeightSliderLocale.DESC_sliderTooltip));
	}

	
	@Override
	public void resetState(boolean fireEvents)
	{
		setEnabled(false, fireEvents);
		setWeight(SLIDER_MIN_VAL, fireEvents);
	}
}
