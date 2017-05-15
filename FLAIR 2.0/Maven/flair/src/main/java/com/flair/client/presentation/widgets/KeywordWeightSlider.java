package com.flair.client.presentation.widgets;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.locale.KeywordWeightSliderLocale;
import com.flair.client.presentation.interfaces.AbstractWeightSlider;
import com.flair.client.presentation.interfaces.CanReset;
import com.flair.shared.grammar.Language;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialRange;

/*
 * Weight slider for keywords
 */
public class KeywordWeightSlider extends LocalizedComposite implements AbstractWeightSlider, CanReset
{
	private static KeywordWeightSliderUiBinder uiBinder = GWT.create(KeywordWeightSliderUiBinder.class);

	interface KeywordWeightSliderUiBinder extends UiBinder<Widget, KeywordWeightSlider>
	{
	}
	
	
	public interface ClickHandler {
		public void handle(KeywordWeightSlider source);
	}
			
	@UiField
	MaterialCheckBox		chkToggleUI;
	@UiField
	MaterialRange			sldWeightUI;
	@UiField
	MaterialIcon			btnEditKeywordsUI;
	@UiField
	MaterialIcon 			btnResetKeywordsUI;
	
	GenericWeightSlider		base;
	ClickHandler			editHandler;
	ClickHandler			resetHandler;
	boolean					customVocab;
	
	private void initLocale()
	{
		registerLocale(KeywordWeightSliderLocale.INSTANCE.en);
		registerLocale(KeywordWeightSliderLocale.INSTANCE.de);
		
		refreshLocalization();
	}
	
	private void initUI()
	{
		setEnabled(false, false);
	}
	
	public KeywordWeightSlider()
	{
		super(ClientEndPoint.get().getLocalization());
		initWidget(uiBinder.createAndBindUi(this));
		
		base = new GenericWeightSlider(this, chkToggleUI, sldWeightUI);
		editHandler = resetHandler = null;
		customVocab = false;
		
		btnEditKeywordsUI.addClickHandler(e -> {
			if (editHandler != null)
				editHandler.handle(this);
		});

		btnResetKeywordsUI.addClickHandler(e -> {
			if (resetHandler != null)
				resetHandler.handle(this);
		});
		
		initLocale();
		initUI();
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
		chkToggleUI.setTitle(getLocalizationData(lang).get(KeywordWeightSliderLocale.DESC_toggleTooltip));
		
		btnEditKeywordsUI.setTooltip(getLocalizationData(lang).get(KeywordWeightSliderLocale.DESC_editTooltip));
		btnResetKeywordsUI.setTooltip(getLocalizationData(lang).get(KeywordWeightSliderLocale.DESC_resetTooltip));
		sldWeightUI.setTitle(getLocalizationData(lang).get(KeywordWeightSliderLocale.DESC_sliderTooltip));
	}

	@Override
	public void resetState(boolean fireEvents)
	{
		setEnabled(false, fireEvents);
		setWeight(GenericWeightSlider.getSliderMin(), fireEvents);
	}

	@Override
	public void setToggleHandler(ToggleHandler handler) {
		base.setToggleHandler(handler);
	}

	@Override
	public void setWeightChangeHandler(WeightChangeHandler handler) {
		base.setWeightChangeHandler(handler);
	}

	@Override
	public boolean isEnabled() {
		return base.isEnabled();
	}

	@Override
	public void setEnabled(boolean val, boolean fireEvent) {
		base.setEnabled(val, fireEvent);
	}

	@Override
	public void toggleEnabled(boolean fireEvent) {
		base.toggleEnabled(fireEvent);
	}

	@Override
	public int getWeight() {
		return base.getWeight();
	}

	@Override
	public boolean hasWeight() {
		return base.hasWeight();
	}

	@Override
	public void setWeight(int val, boolean fireEvent) {
		base.setWeight(val, fireEvent);
	}

	@Override
	public void setToggleText(String val) {
		base.setToggleText(val);
	}

	@Override
	public boolean isSliderVisible() {
		return base.isSliderVisible();
	}

	@Override
	public void setSliderVisible(boolean val) {
		base.setSliderVisible(val);
	}
}
