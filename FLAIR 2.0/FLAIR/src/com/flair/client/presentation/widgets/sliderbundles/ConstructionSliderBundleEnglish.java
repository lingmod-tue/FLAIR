package com.flair.client.presentation.widgets.sliderbundles;

import com.flair.client.localization.SimpleLocalizedTextWidget;
import com.flair.client.localization.locale.ConstructionSliderPackageEnglishLocale;
import com.flair.client.presentation.widgets.GrammaticalConstructionSliderPanel;
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
	GrammaticalConstructionSliderPanel			pnlSentencesUI; 
	@UiField
	GrammaticalConstructionSliderPanel			pnlQuestionsUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlSentenceTypesUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlClauseTypesUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlPartsOfSpeechUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlVerbsUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlVerbFormsUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlTensesUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlAspectUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlTimeUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlVoiceUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlPhrasalUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlModalUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlTransitiveUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlImperativeUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlNegationUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlArticlesUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlQuantifiersUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlAdjectivesUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlAdverbsUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlPronounsUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlConjunctionsUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlPrepositionsUI;
	@UiField
	GrammaticalConstructionSliderPanel			pnlNounsUI;
	
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlSentencesLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlQuestionsLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlSentenceTypesLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlClauseTypesLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlPartsOfSpeechLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlVerbsLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlVerbFormsLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlTensesLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlAspectLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlTimeLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlVoiceLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlPhrasalLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlModalLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlTransitiveLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlImperativeLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlNegationLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlArticlesLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlQuantifiersLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlAdjectivesLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlAdverbsLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlPronounsLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlConjunctionsLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlPrepositionsLC;
	SimpleLocalizedTextWidget<GrammaticalConstructionSliderPanel>			pnlNounsLC;
	
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
}
