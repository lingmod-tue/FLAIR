package com.flair.client.presentation.widgets.sliderbundles;

import com.flair.client.localization.SimpleLocalizedTextWidget;
import com.flair.client.localization.locale.ConstructionSliderPackageGermanLocale;
import com.flair.client.presentation.widgets.GrammaticalConstructionSliderPanel;
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

	@UiField
	HTMLPanel		pnlRootUI;
	
	@UiField
	GrammaticalConstructionSliderPanel			pnlSaetzeUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlFragenUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlSatztypenUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlNebensatztypenUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlWortartenUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlVerbenUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlVerbtypenUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlZeitenUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlPassivUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlSatzklammerUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlVerbformenUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlNegationUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlArtikelUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlIndefiniteQuantifiziererUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlAdjAdvZWUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlPronomenUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlPraepositionenUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlNomenUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlNominalisierungenUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlAttributeUI;
	
	
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlSaetzeLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlFragenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlSatztypenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlNebensatztypenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlWortartenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlVerbenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlVerbtypenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlZeitenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlPassivLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlSatzklammerLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlVerbformenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlNegationLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlArtikelLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlIndefiniteQuantifiziererLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlAdjAdvZWLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlPronomenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlPraepositionenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlNomenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlNominalisierungenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlAttributeLC;
	
	private void initLocale()
	{
		pnlSaetzeLC = new SimpleLocalizedTextWidget<>(pnlSaetzeUI, ConstructionSliderPackageGermanLocale.DESC_Saetze);
		pnlFragenLC = new SimpleLocalizedTextWidget<>(pnlFragenUI, ConstructionSliderPackageGermanLocale.DESC_Fragen);
		pnlSatztypenLC = new SimpleLocalizedTextWidget<>(pnlSatztypenUI, ConstructionSliderPackageGermanLocale.DESC_Satztypen);
		pnlNebensatztypenLC = new SimpleLocalizedTextWidget<>(pnlNebensatztypenUI, ConstructionSliderPackageGermanLocale.DESC_Nebensatztypen);
		pnlWortartenLC = new SimpleLocalizedTextWidget<>(pnlWortartenUI, ConstructionSliderPackageGermanLocale.DESC_Wortarten);
		pnlVerbenLC = new SimpleLocalizedTextWidget<>(pnlVerbenUI, ConstructionSliderPackageGermanLocale.DESC_Verben);
		pnlVerbtypenLC = new SimpleLocalizedTextWidget<>(pnlVerbtypenUI, ConstructionSliderPackageGermanLocale.DESC_Verbtypen);
		pnlZeitenLC = new SimpleLocalizedTextWidget<>(pnlZeitenUI, ConstructionSliderPackageGermanLocale.DESC_Zeiten);
		pnlPassivLC = new SimpleLocalizedTextWidget<>(pnlPassivUI, ConstructionSliderPackageGermanLocale.DESC_Passiv);
		pnlSatzklammerLC = new SimpleLocalizedTextWidget<>(pnlSatzklammerUI, ConstructionSliderPackageGermanLocale.DESC_Satzklammer);
		pnlVerbformenLC = new SimpleLocalizedTextWidget<>(pnlVerbformenUI, ConstructionSliderPackageGermanLocale.DESC_Verbformen);
		pnlNegationLC = new SimpleLocalizedTextWidget<>(pnlNegationUI, ConstructionSliderPackageGermanLocale.DESC_Negation);
		pnlArtikelLC = new SimpleLocalizedTextWidget<>(pnlArtikelUI, ConstructionSliderPackageGermanLocale.DESC_Artikel);
		pnlIndefiniteQuantifiziererLC = new SimpleLocalizedTextWidget<>(pnlIndefiniteQuantifiziererUI, ConstructionSliderPackageGermanLocale.DESC_IndefiniteQuantifizierer);
		pnlAdjAdvZWLC = new SimpleLocalizedTextWidget<>(pnlAdjAdvZWUI, ConstructionSliderPackageGermanLocale.DESC_AdjAdvZW);
		pnlPronomenLC = new SimpleLocalizedTextWidget<>(pnlPronomenUI, ConstructionSliderPackageGermanLocale.DESC_Pronomen);
		pnlPraepositionenLC = new SimpleLocalizedTextWidget<>(pnlPraepositionenUI, ConstructionSliderPackageGermanLocale.DESC_Praepositionen);
		pnlNomenLC = new SimpleLocalizedTextWidget<>(pnlNomenUI, ConstructionSliderPackageGermanLocale.DESC_Nomen);
		pnlNominalisierungenLC = new SimpleLocalizedTextWidget<>(pnlNominalisierungenUI, ConstructionSliderPackageGermanLocale.DESC_Nominalisierungen);
		pnlAttributeLC = new SimpleLocalizedTextWidget<>(pnlAttributeUI, ConstructionSliderPackageGermanLocale.DESC_Attribute);
		
		registerLocale(ConstructionSliderPackageGermanLocale.INSTANCE.en);
		registerLocale(ConstructionSliderPackageGermanLocale.INSTANCE.de);
		
		registerLocalizedWidget(pnlSaetzeLC);
		registerLocalizedWidget(pnlFragenLC);
		registerLocalizedWidget(pnlSatztypenLC);
		registerLocalizedWidget(pnlNebensatztypenLC);
		registerLocalizedWidget(pnlWortartenLC);
		registerLocalizedWidget(pnlVerbenLC);
		registerLocalizedWidget(pnlVerbtypenLC);
		registerLocalizedWidget(pnlZeitenLC);
		registerLocalizedWidget(pnlPassivLC);
		registerLocalizedWidget(pnlSatzklammerLC);
		registerLocalizedWidget(pnlVerbformenLC);
		registerLocalizedWidget(pnlNegationLC);
		registerLocalizedWidget(pnlArtikelLC);
		registerLocalizedWidget(pnlIndefiniteQuantifiziererLC);
		registerLocalizedWidget(pnlAdjAdvZWLC);
		registerLocalizedWidget(pnlPronomenLC);
		registerLocalizedWidget(pnlPraepositionenLC);
		registerLocalizedWidget(pnlNomenLC);
		registerLocalizedWidget(pnlNominalisierungenLC);
		registerLocalizedWidget(pnlAttributeLC);
		
		refreshLocalization();
	}
	
	public ConstructionSliderBundleGerman()
	{
		super(Language.GERMAN);
		
		initWidget(uiBinder.createAndBindUi(this));
		setRootContainer(pnlRootUI);
		initLocale();
	}
}
