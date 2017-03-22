package com.flair.client.presentation.widgets.sliderbundles;

import com.flair.client.localization.SimpleLocalizedTextWidget;
import com.flair.client.localization.locale.ConstructionSliderPackageGermanLocale;
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

	@UiField
	HTMLPanel		pnlRootUI;
	
	@UiField
	GrammaticalConstructionPanelItem			pnlSaetzeUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlFragenUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlSatztypenUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlNebensatztypenUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlWortartenUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlVerbenUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlVerbtypenUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlZeitenUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlPassivUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlSatzklammerUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlVerbformenUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlNegationUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlArtikelUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlIndefiniteQuantifiziererUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlAdjAdvZWUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlPronomenUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlPraepositionenUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlNomenUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlNominalisierungenUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlAttributeUI;
	
	
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlSaetzeLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlFragenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlSatztypenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlNebensatztypenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlWortartenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlVerbenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlVerbtypenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlZeitenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlPassivLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlSatzklammerLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlVerbformenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlNegationLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlArtikelLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlIndefiniteQuantifiziererLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlAdjAdvZWLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlPronomenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlPraepositionenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlNomenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlNominalisierungenLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlAttributeLC;
	
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
