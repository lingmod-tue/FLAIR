package com.flair.client.widgets;

import org.gwtbootstrap3.client.ui.Badge;

import com.flair.client.GrammaticalConstructionLocale;
import com.flair.client.localization.SimpleLocale;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

/*
 * Weight slider for grammatical constructions
 */
public class GrammaticalConstructionWeightSlider extends GenericWeightSlider implements CanReset
{
	static final class Locale extends SimpleLocale
	{
		static final String		DESC_toggleTooltip = "toggleTooltip";
		static final String		DESC_resultCountTooltip = "resultCountTooltip";
		static final String		DESC_sliderTooltip = "sliderTooltip";
		
		@Override
		public void init()
		{
			// EN
			en.put(DESC_toggleTooltip, "uncheck to exclude texts with this construct");
			en.put(DESC_resultCountTooltip, "results");
			en.put(DESC_sliderTooltip, "move right to rank texts with this construct higher");
			
			// DE
			de.put(DESC_toggleTooltip, "abwählen, um Texte mit dieser Konstruktion auszuschließen");
			de.put(DESC_resultCountTooltip, "Ergebnisse");
			de.put(DESC_sliderTooltip, "nach rechts bewegen, um Texte mit dieser Konstruktion höher zu bewerten");
		}
		
		private static final Locale		INSTANCE = new Locale();
	}
	
	private static final String					STYLENAME_BADGE = "construction-weight-slider-badge";
	private static final String					STYLENAME_WIDGET = "construction-weight-slider";
	
	private final Badge							resultCount;
	private GrammaticalConstruction				gramConstruction;
	
	private void initLocale()
	{
		registerLocale(Locale.INSTANCE.en);
		registerLocale(Locale.INSTANCE.de);
		
		refreshLocalization();
	}
	
	@UiConstructor
	public GrammaticalConstructionWeightSlider()
	{
		super();
		resultCount = new Badge("50/50");
		gramConstruction = null;
		
		// setup components
		resultCount.addStyleName(STYLENAME_BADGE);
		
		VerticalPanel container = new VerticalPanel();
		FlowPanel toggleWrapper = new FlowPanel();
		toggleWrapper.addStyleName(STYLENAME_TOGGLE);
		toggleWrapper.add(toggle);
		toggleWrapper.add(resultCount);
		container.add(toggleWrapper);
//		container.add(new HTML("<br/>"));
		container.add(sliderPanel);
		
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
		
		toggle.setTitle(getLocalizationData(lang).get(Locale.DESC_toggleTooltip));
		resultCount.setTitle(getLocalizationData(lang).get(Locale.DESC_resultCountTooltip));
		slider.setTitle(getLocalizationData(lang).get(Locale.DESC_sliderTooltip));
	}

	
	@Override
	public void resetState(boolean fireEvents) 
	{
		// enable toggle and reset weight
		setEnabled(true, fireEvents);
		setValue(SLIDER_MIN_VAL, fireEvents);
	}
}
