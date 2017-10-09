package com.flair.client.presentation.widgets.sliderbundles;

import com.flair.client.localization.annotations.LocalizedField;
import com.flair.client.localization.interfaces.LocalizationBinder;
import com.flair.client.presentation.widgets.GrammaticalConstructionPanelItem;
import com.flair.client.presentation.widgets.LanguageSpecificConstructionSliderBundle;
import com.flair.shared.grammar.Language;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/*
 * Gram const. weight sliders for German
 */
public class ConstructionSliderBundleGerman extends LanguageSpecificConstructionSliderBundle
{
	private static ConstructionSliderBundleGermanUiBinder uiBinder = GWT.create(ConstructionSliderBundleGermanUiBinder.class);

	interface ConstructionSliderBundleGermanUiBinder extends UiBinder<Widget, ConstructionSliderBundleGerman>
	{
	}

	private static ConstructionSliderBundleGermanLocalizationBinder localeBinder = GWT.create(ConstructionSliderBundleGermanLocalizationBinder.class);
	interface ConstructionSliderBundleGermanLocalizationBinder extends LocalizationBinder<ConstructionSliderBundleGerman> {}
	
	@UiField
	HTMLPanel		pnlRootUI;
	
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlSaetzeUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlFragenUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlSatztypenUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlNebensatztypenUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlWortartenUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlVerbenUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlVerbtypenUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlZeitenUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlPassivUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlSatzklammerUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlVerbformenUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlNegationUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlArtikelUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlIndefiniteQuantifiziererUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlAdjAdvZWUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlPronomenUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlPraepositionenUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlNomenUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlNominalisierungenUI;
	@UiField
	@LocalizedField
	GrammaticalConstructionPanelItem			pnlAttributeUI;
	
	public ConstructionSliderBundleGerman()
	{
		super(Language.GERMAN);
		
		initWidget(uiBinder.createAndBindUi(this));
		initLocale(localeBinder.bind(this));
		setRootContainer(pnlRootUI);
	}
}
