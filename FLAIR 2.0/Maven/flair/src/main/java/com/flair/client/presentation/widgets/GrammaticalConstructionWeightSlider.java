package com.flair.client.presentation.widgets;

import com.flair.client.ClientEndPoint;
import com.flair.client.localization.LocalizedComposite;
import com.flair.client.localization.locale.GrammaticalConstructionLocale;
import com.flair.client.localization.locale.GrammaticalConstructionWeightSliderLocale;
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
	
	
	public interface ResetHandler {
		public void handle(GrammaticalConstructionWeightSlider source, boolean fireEvents);
	}
	
	@UiField
	MaterialCheckBox				chkToggleUI;
	@UiField
	MaterialRange					sldWeightUI;
	@UiField
	MaterialBadge					bdgResultCountUI;
	
	GenericWeightSlider				base;
	private GrammaticalConstruction	gramConstruction;
	private ResetHandler			resetHandler;
	
	private void initLocale()
	{
		registerLocale(GrammaticalConstructionWeightSliderLocale.INSTANCE.en);
		registerLocale(GrammaticalConstructionWeightSliderLocale.INSTANCE.de);
		
		refreshLocalization();
	}
	
	public GrammaticalConstructionWeightSlider()
	{
		super(ClientEndPoint.get().getLocalization());
		initWidget(uiBinder.createAndBindUi(this));
		
		base = new GenericWeightSlider(this, chkToggleUI, sldWeightUI);
		gramConstruction = null;
		resetHandler = null;

		initLocale();
	}
	
	public GrammaticalConstruction getGram() {
		return gramConstruction;
	}
	
	public void setGram(GrammaticalConstruction gram)
	{
		gramConstruction = gram;
		setToggleText(gramConstruction.toString());
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
	public void setLocalization(Language lang)
	{
		super.setLocalization(lang);
		
		// update toggle and tooltip
		if (gramConstruction == null)
			setToggleText("INVALID_GRAM_CONST");
		else
			setToggleText(GrammaticalConstructionLocale.get().getLocalizedName(gramConstruction, lang));
		
		chkToggleUI.setTitle(getLocalizationData(lang).get(GrammaticalConstructionWeightSliderLocale.DESC_toggleTooltip));
		bdgResultCountUI.setTooltip(getLocalizationData(lang).get(GrammaticalConstructionWeightSliderLocale.DESC_resultCountTooltip));
		sldWeightUI.setTooltip(getLocalizationData(lang).get(GrammaticalConstructionWeightSliderLocale.DESC_sliderTooltip));
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
}
