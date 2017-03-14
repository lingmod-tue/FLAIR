package com.flair.client.widgets;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.constants.IconType;

import com.flair.client.localization.locale.KeywordWeightSliderLocale;
import com.flair.client.widgets.interfaces.CanReset;
import com.flair.shared.grammar.Language;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/*
 * Weight slider for keywords
 */
public class KeywordWeightSlider extends GenericWeightSlider implements CanReset
{
	public interface ClickHandler {
		public void handle(KeywordWeightSlider source);
	}
	
	private static final String		STYLENAME_WIDGET = "construction-weight-slider";
	
	private final Anchor			editKeywords;
	private final Anchor			resetKeywords;
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
		editKeywords = new Anchor();
		resetKeywords = new Anchor();
		editHandler = resetHandler = null;
		customVocab = false;
		
		// setup components
		editKeywords.setIcon(IconType.EDIT);
		resetKeywords.setIcon(IconType.ROTATE_LEFT);
		
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
		togglePanel.add(new HTML("&nbsp;&nbsp;&nbsp;"));
		togglePanel.add(editKeywords);
		togglePanel.add(new HTML("&nbsp;&nbsp;"));
		togglePanel.add(resetKeywords);
		
		container.add(togglePanel);
		container.add(new HTML("<br/>"));
		container.add(sliderPanel);
		
		initWidget(container);
		setStyleName(STYLENAME_WIDGET);
		
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
		
		editKeywords.setTitle(getLocalizationData(lang).get(KeywordWeightSliderLocale.DESC_editTooltip));
		resetKeywords.setTitle(getLocalizationData(lang).get(KeywordWeightSliderLocale.DESC_resetTooltip));
		slider.setTitle(getLocalizationData(lang).get(KeywordWeightSliderLocale.DESC_sliderTooltip));
	}

	
	@Override
	public void resetState(boolean fireEvents) {
		// just reset the toggle
		setEnabled(false, fireEvents);
		setCustomVocab(false);
	}
}
