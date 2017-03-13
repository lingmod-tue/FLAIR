package com.flair.client.widgets;

import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.constants.IconType;

import com.flair.client.GrammaticalConstructionLocale;
import com.flair.client.localization.SimpleLocale;
import com.flair.client.widgets.GrammaticalConstructionWeightSlider.Locale;
import com.flair.shared.grammar.Language;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/*
 * Weight slider for keywords
 */
public class KeywordWeightSlider extends GenericWeightSlider implements CanReset
{
	static final class Locale extends SimpleLocale
	{
		static final String		DESC_toggleDefault = "toggleDefault";
		static final String		DESC_toggleCustom = "toggleCustom";
		static final String		DESC_toggleTooltip = "toggleTooltip";
		static final String		DESC_editTooltip = "editTooltip";
		static final String		DESC_resetTooltip = "resetTooltip";
		static final String		DESC_sliderTooltip = "sliderTooltip";
		
		@Override
		public void init()
		{
			// EN
			en.put(DESC_toggleDefault, "Academic Vocabulary");
			en.put(DESC_toggleCustom, "Custom Vocabulary");
			en.put(DESC_toggleTooltip, "check to highlight keywords");
			en.put(DESC_editTooltip, "edit vocabulary");
			en.put(DESC_resetTooltip, "use default academic vocabulary");
			en.put(DESC_sliderTooltip, "move right to rank texts with this construct higher");
			
			// DE
			de.put(DESC_toggleDefault, "Akademisch Vokabular");
			de.put(DESC_toggleCustom, "Benutzerdefiniert Vokabular");
			de.put(DESC_toggleTooltip, "auswählen, um Schlüsselwörter hervorzuheben");
			de.put(DESC_editTooltip, "Vokabular bearbeiten");
			de.put(DESC_resetTooltip, "Standardliste für benutzerdefiniertes Vokabular verwenden");
			de.put(DESC_sliderTooltip, "nach rechts bewegen, um Texte mit dieser Konstruktion höher zu bewerten");
		}
		
		private static final Locale		INSTANCE = new Locale();
	}
	
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
		registerLocale(Locale.INSTANCE.en);
		registerLocale(Locale.INSTANCE.de);
		
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
		setToggleText(getLocalizationData(lang).get(customVocab == false ? Locale.DESC_toggleDefault : Locale.DESC_toggleCustom));
		toggle.setTitle(getLocalizationData(lang).get(Locale.DESC_toggleTooltip));
		
		editKeywords.setTitle(getLocalizationData(lang).get(Locale.DESC_editTooltip));
		resetKeywords.setTitle(getLocalizationData(lang).get(Locale.DESC_resetTooltip));
		slider.setTitle(getLocalizationData(lang).get(Locale.DESC_sliderTooltip));
	}

	
	@Override
	public void resetState(boolean fireEvents) {
		// just reset the toggle
		setEnabled(false, fireEvents);
		setCustomVocab(false);
	}
}
