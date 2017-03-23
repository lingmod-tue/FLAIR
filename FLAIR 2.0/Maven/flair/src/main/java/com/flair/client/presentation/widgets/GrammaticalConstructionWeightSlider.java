package com.flair.client.presentation.widgets;

import com.flair.client.localization.locale.GrammaticalConstructionLocale;
import com.flair.client.localization.locale.GrammaticalConstructionWeightSliderLocale;
import com.flair.client.presentation.interfaces.CanReset;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.VerticalPanel;

import gwt.material.design.client.ui.MaterialBadge;

/*
 * Weight slider for grammatical constructions
 */
public class GrammaticalConstructionWeightSlider extends GenericWeightSlider implements CanReset
{
	private static final String					STYLENAME_BADGE = "construction-weight-slider-badge";
	private static final String					STYLENAME_WIDGET = "construction-weight-slider";
	
	private final MaterialBadge					resultCount;
	private GrammaticalConstruction				gramConstruction;
	
	private void initLocale()
	{
		registerLocale(GrammaticalConstructionWeightSliderLocale.INSTANCE.en);
		registerLocale(GrammaticalConstructionWeightSliderLocale.INSTANCE.de);
		
		refreshLocalization();
	}
	
	@UiConstructor
	public GrammaticalConstructionWeightSlider()
	{
		super();
		resultCount = new MaterialBadge("50/50");
		gramConstruction = null;
		
		// setup components
		resultCount.addStyleName(STYLENAME_BADGE);
		
		VerticalPanel container = new VerticalPanel();
//		togglePanel.add(toggle);
//		togglePanel.add(resultCount);
//		container.add(togglePanel);
		container.add(toggle);
		container.add(resultCount);
		container.add(slider);
		
		initWidget(container);
		setStyleName(STYLENAME_WIDGET);
		
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
		resultCount.setText(count.toString() + "/" + total.toString());
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
		
		toggle.setTitle(getLocalizationData(lang).get(GrammaticalConstructionWeightSliderLocale.DESC_toggleTooltip));
		resultCount.setTooltip(getLocalizationData(lang).get(GrammaticalConstructionWeightSliderLocale.DESC_resultCountTooltip));
		slider.setTooltip(getLocalizationData(lang).get(GrammaticalConstructionWeightSliderLocale.DESC_sliderTooltip));
	}

	
	@Override
	public void resetState(boolean fireEvents) 
	{
		// enable toggle and reset weight
		setEnabled(true, fireEvents);
		setWeight(SLIDER_MIN_VAL, fireEvents);
	}
}
