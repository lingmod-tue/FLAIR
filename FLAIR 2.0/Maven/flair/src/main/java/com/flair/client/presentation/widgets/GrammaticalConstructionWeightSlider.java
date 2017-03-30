package com.flair.client.presentation.widgets;

import com.flair.client.localization.locale.GrammaticalConstructionLocale;
import com.flair.client.localization.locale.GrammaticalConstructionWeightSliderLocale;
import com.flair.client.presentation.interfaces.CanReset;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.VerticalPanel;

import gwt.material.design.client.ui.MaterialBadge;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialRow;

/*
 * Weight slider for grammatical constructions
 */
public class GrammaticalConstructionWeightSlider extends GenericWeightSlider implements CanReset
{
	public interface ResetHandler {
		public void handle(GrammaticalConstructionWeightSlider source, boolean fireEvents);
	}
	
	private final MaterialBadge					resultCount;
	private GrammaticalConstruction				gramConstruction;
	private ResetHandler						resetHandler;
	
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
		resetHandler = null;
		
		// setup components
		resultCount.setRight(35);
		
		MaterialRow firstRow = new MaterialRow();
		MaterialColumn pnlToggle = new MaterialColumn();
		MaterialColumn pnlBadge = new MaterialColumn();
		pnlToggle.add(toggle);
		pnlBadge.add(resultCount);
		firstRow.add(pnlToggle);
		firstRow.add(pnlBadge);
		VerticalPanel container = new VerticalPanel();
		container.add(firstRow);
		container.add(slider);
		
		initWidget(container);
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
	
	public void setResultCountVisible(boolean val) {
		resultCount.setVisible(val);
	}
	
	public boolean isResultCountVisible() {
		return resultCount.isVisible();
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
		
		toggle.setTitle(getLocalizationData(lang).get(GrammaticalConstructionWeightSliderLocale.DESC_toggleTooltip));
		resultCount.setTooltip(getLocalizationData(lang).get(GrammaticalConstructionWeightSliderLocale.DESC_resultCountTooltip));
		slider.setTooltip(getLocalizationData(lang).get(GrammaticalConstructionWeightSliderLocale.DESC_sliderTooltip));
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
			setWeight(SLIDER_MIN_VAL, fireEvents);
		}
	}
}
