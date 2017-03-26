package com.flair.client.presentation.widgets.sliderbundles;

import com.flair.client.localization.SimpleLocalizedTextWidget;
import com.flair.client.localization.locale.ConstructionSliderPackageEnglishLocale;
import com.flair.client.presentation.widgets.GrammaticalConstructionPanelItem;
import com.flair.client.presentation.widgets.LanguageSpecificConstructionSliderBundle;
import com.flair.shared.grammar.Language;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/*
 * Gram const. weight sliders for English
 */
public class ConstructionSliderBundleEnglish extends LanguageSpecificConstructionSliderBundle
{
	private static ConstructionSliderBundleEnglishUiBinder uiBinder = GWT.create(ConstructionSliderBundleEnglishUiBinder.class);

	interface ConstructionSliderBundleEnglishUiBinder extends UiBinder<Widget, ConstructionSliderBundleEnglish>
	{
	}

	@UiField
	HTMLPanel									pnlRootUI;
	
	@UiField
	GrammaticalConstructionPanelItem			pnlSentencesUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlQuestionsUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlSentenceTypesUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlClauseTypesUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlPartsOfSpeechUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlVerbsUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlVerbFormsUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlTensesUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlAspectUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlTimeUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlVoiceUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlPhrasalUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlModalUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlTransitiveUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlImperativeUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlNegationUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlArticlesUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlQuantifiersUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlAdjectivesUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlAdverbsUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlPronounsUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlConjunctionsUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlPrepositionsUI;
	@UiField
	GrammaticalConstructionPanelItem			pnlNounsUI;
	
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlSentencesLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlQuestionsLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlSentenceTypesLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlClauseTypesLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlPartsOfSpeechLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlVerbsLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlVerbFormsLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlTensesLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlAspectLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlTimeLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlVoiceLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlPhrasalLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlModalLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlTransitiveLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlImperativeLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlNegationLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlArticlesLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlQuantifiersLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlAdjectivesLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlAdverbsLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlPronounsLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlConjunctionsLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlPrepositionsLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionPanelItem>			pnlNounsLC;
	
	private void initLocale()
	{
		pnlSentencesLC = new SimpleLocalizedTextWidget<>(pnlSentencesUI, ConstructionSliderPackageEnglishLocale.DESC_Sentences);
		pnlQuestionsLC = new SimpleLocalizedTextWidget<>(pnlQuestionsUI, ConstructionSliderPackageEnglishLocale.DESC_Questions);
		pnlSentenceTypesLC = new SimpleLocalizedTextWidget<>(pnlSentenceTypesUI, ConstructionSliderPackageEnglishLocale.DESC_SentenceTypes);
		pnlClauseTypesLC = new SimpleLocalizedTextWidget<>(pnlClauseTypesUI, ConstructionSliderPackageEnglishLocale.DESC_ClauseTypes);
		pnlPartsOfSpeechLC = new SimpleLocalizedTextWidget<>(pnlPartsOfSpeechUI, ConstructionSliderPackageEnglishLocale.DESC_PartsOfSpeech);
		pnlVerbsLC = new SimpleLocalizedTextWidget<>(pnlVerbsUI, ConstructionSliderPackageEnglishLocale.DESC_Verbs);
		pnlVerbFormsLC = new SimpleLocalizedTextWidget<>(pnlVerbFormsUI, ConstructionSliderPackageEnglishLocale.DESC_VerbForms);
		pnlTensesLC = new SimpleLocalizedTextWidget<>(pnlTensesUI, ConstructionSliderPackageEnglishLocale.DESC_Tenses);
		pnlAspectLC = new SimpleLocalizedTextWidget<>(pnlAspectUI, ConstructionSliderPackageEnglishLocale.DESC_Aspect);
		pnlTimeLC = new SimpleLocalizedTextWidget<>(pnlTimeUI, ConstructionSliderPackageEnglishLocale.DESC_Time);
		pnlVoiceLC = new SimpleLocalizedTextWidget<>(pnlVoiceUI, ConstructionSliderPackageEnglishLocale.DESC_Voice);
		pnlPhrasalLC = new SimpleLocalizedTextWidget<>(pnlPhrasalUI, ConstructionSliderPackageEnglishLocale.DESC_Phrasal);
		pnlModalLC = new SimpleLocalizedTextWidget<>(pnlModalUI, ConstructionSliderPackageEnglishLocale.DESC_Modal);
		pnlTransitiveLC = new SimpleLocalizedTextWidget<>(pnlTransitiveUI, ConstructionSliderPackageEnglishLocale.DESC_Transitive);
		pnlImperativeLC = new SimpleLocalizedTextWidget<>(pnlImperativeUI, ConstructionSliderPackageEnglishLocale.DESC_Imperative);
		pnlNegationLC = new SimpleLocalizedTextWidget<>(pnlNegationUI, ConstructionSliderPackageEnglishLocale.DESC_Negation);
		pnlArticlesLC = new SimpleLocalizedTextWidget<>(pnlArticlesUI, ConstructionSliderPackageEnglishLocale.DESC_Articles);
		pnlQuantifiersLC = new SimpleLocalizedTextWidget<>(pnlQuantifiersUI, ConstructionSliderPackageEnglishLocale.DESC_Quantifiers);
		pnlAdjectivesLC = new SimpleLocalizedTextWidget<>(pnlAdjectivesUI, ConstructionSliderPackageEnglishLocale.DESC_Adjectives);
		pnlAdverbsLC = new SimpleLocalizedTextWidget<>(pnlAdverbsUI, ConstructionSliderPackageEnglishLocale.DESC_Adverbs);
		pnlPronounsLC = new SimpleLocalizedTextWidget<>(pnlPronounsUI, ConstructionSliderPackageEnglishLocale.DESC_Pronouns);
		pnlConjunctionsLC = new SimpleLocalizedTextWidget<>(pnlConjunctionsUI, ConstructionSliderPackageEnglishLocale.DESC_Conjunctions);
		pnlPrepositionsLC = new SimpleLocalizedTextWidget<>(pnlPrepositionsUI, ConstructionSliderPackageEnglishLocale.DESC_Prepositions);
		pnlNounsLC = new SimpleLocalizedTextWidget<>(pnlNounsUI, ConstructionSliderPackageEnglishLocale.DESC_Nouns);
		
		registerLocale(ConstructionSliderPackageEnglishLocale.INSTANCE.en);
		registerLocale(ConstructionSliderPackageEnglishLocale.INSTANCE.de);
		
		registerLocalizedWidget(pnlSentencesLC);
		registerLocalizedWidget(pnlQuestionsLC);
		registerLocalizedWidget(pnlSentenceTypesLC);
		registerLocalizedWidget(pnlClauseTypesLC);
		registerLocalizedWidget(pnlPartsOfSpeechLC);
		registerLocalizedWidget(pnlVerbsLC);
		registerLocalizedWidget(pnlVerbFormsLC);
		registerLocalizedWidget(pnlTensesLC);
		registerLocalizedWidget(pnlAspectLC);
		registerLocalizedWidget(pnlTimeLC);
		registerLocalizedWidget(pnlVoiceLC);
		registerLocalizedWidget(pnlPhrasalLC);
		registerLocalizedWidget(pnlModalLC);
		registerLocalizedWidget(pnlTransitiveLC);
		registerLocalizedWidget(pnlImperativeLC);
		registerLocalizedWidget(pnlNegationLC);
		registerLocalizedWidget(pnlArticlesLC);
		registerLocalizedWidget(pnlQuantifiersLC);
		registerLocalizedWidget(pnlAdjectivesLC);
		registerLocalizedWidget(pnlAdverbsLC);
		registerLocalizedWidget(pnlPronounsLC);
		registerLocalizedWidget(pnlConjunctionsLC);
		registerLocalizedWidget(pnlPrepositionsLC);
		registerLocalizedWidget(pnlNounsLC);
		
		refreshLocalization();
	}
	
	public ConstructionSliderBundleEnglish()
	{
		super(Language.ENGLISH);
		
		initWidget(uiBinder.createAndBindUi(this));
		setRootContainer(pnlRootUI);
		initLocale();
	}
	
	@Override
	public LanguageSpecificConstructionSliderBundle copySelf() {
		return new ConstructionSliderBundleEnglish();
	}
}
