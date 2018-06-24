package com.flair.client.presentation.widgets;

import com.flair.client.localization.CommonLocalizationTags;
import com.flair.client.localization.GrammaticalConstructionLocalizationProvider;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.LocalizedFieldType;
import com.flair.client.localization.annotations.LocalizedCommonField;
import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.interfaces.AbstractWeightSlider;
import com.flair.client.presentation.interfaces.CanReset;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialBadge;
import gwt.material.design.client.ui.MaterialCheckBox;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialRange;

/*
 * Weight slider for grammatical constructions
 */
public class GrammaticalConstructionWeightSlider extends LocalizedComposite implements AbstractWeightSlider, CanReset
{
	private static GrammaticalConstructionWeightSliderUiBinder uiBinder = GWT.create(GrammaticalConstructionWeightSliderUiBinder.class);

	interface GrammaticalConstructionWeightSliderUiBinder extends UiBinder<Widget, GrammaticalConstructionWeightSlider>
	{
	}
	
	private static GrammaticalConstructionWeightSliderLocalizationBinder localeBinder = GWT.create(GrammaticalConstructionWeightSliderLocalizationBinder.class);
	interface GrammaticalConstructionWeightSliderLocalizationBinder extends LocalizationBinder<GrammaticalConstructionWeightSlider> {}
	
	public interface ResetHandler {
		public void handle(GrammaticalConstructionWeightSlider source, boolean fireEvents);
	}
	
	private static final String			PLACEHOLDER_STRING ="<INVALID GRAM CONST>";
	
	@UiField
	@LocalizedField(type=LocalizedFieldType.TOOLTIP_BASIC)
	MaterialCheckBox				chkToggleUI;
	@UiField
	MaterialIcon					icoHelpTextUI;
	@UiField
	@LocalizedField(type=LocalizedFieldType.TOOLTIP_BASIC)
	MaterialRange					sldWeightUI;
	@UiField
	@LocalizedCommonField(tag=CommonLocalizationTags.RESULTS, type=LocalizedFieldType.TOOLTIP_BASIC)
	MaterialBadge					bdgResultCountUI;
	
	private GenericWeightSlider	    base;
	private GrammaticalConstruction	gramConstruction;
	private Language				parentLang;				// language this construction/slider is associated with
	private ResetHandler			resetHandler;
	
	public GrammaticalConstructionWeightSlider()
	{
		initWidget(uiBinder.createAndBindUi(this));
		
		base = new GenericWeightSlider(this, chkToggleUI, sldWeightUI);
		gramConstruction = null;
		resetHandler = null;
		parentLang = null;
		
		initLocale(localeBinder.bind(this));
	}
	
	public GrammaticalConstruction getGram() {
		return gramConstruction;
	}
	
	public void setGram(GrammaticalConstruction gram)
	{
		gramConstruction = gram;
		setToggleText(gramConstruction.toString());
	}
	
	public void setParentLanguage(Language l) {
		parentLang = l;
	}
	
	public void setResultCount(Integer count, Integer total) {
		bdgResultCountUI.setText(count.toString() + "/" + total.toString());
	}
	
	public void setResultCountVisible(boolean val) {
		bdgResultCountUI.setVisible(val);
	}
	
	public boolean isResultCountVisible() {
		return bdgResultCountUI.isVisible();
	}
	
	public void setResetHandler(ResetHandler resetHandler) {
		this.resetHandler = resetHandler;
	}

	@Override
	public void setLocale(Language lang)
	{
		super.setLocale(lang);
		
		// update toggle and tooltip based on the parent language
		if (gramConstruction == null || parentLang == null)
		{
			setToggleText(PLACEHOLDER_STRING);
			icoHelpTextUI.setTooltip(PLACEHOLDER_STRING);
		}
		else
		{
			setToggleText(GrammaticalConstructionLocalizationProvider.getName(gramConstruction, parentLang));
			icoHelpTextUI.setTooltip(GrammaticalConstructionLocalizationProvider.getHelpText(gramConstruction, parentLang));
		}
	}
	
	@Override
	public void resetState(boolean fireEvents)
	{
		// use custom handler if available
		if (resetHandler != null)
			resetHandler.handle(this, fireEvents);
		else
		{
			// enable toggle and reset weight
			setEnabled(true, fireEvents);
			setWeight(GenericWeightSlider.getSliderMin(), fireEvents);
		}
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

	@Override
	public void setAnimateSliderOnToggle(boolean val) {
		base.setAnimateSliderOnToggle(val);
	}
}
