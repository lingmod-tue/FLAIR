/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.shared.grammar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a grammatical construction.
 * IMPORTANT - ORDER-DEPENDENT! ADD NEW ITEMS TO THE END!
 * @author shadeMe
*/
public enum GrammaticalConstruction
{
    // (simple) constructions
    EXISTENTIAL_THERE("existentialThere", Language.ENGLISH),
    THERE_IS_ARE("thereIsAre", Language.ENGLISH),
    THERE_WAS_WERE("thereWasWere", Language.ENGLISH),
    
    ATTRIBUTES_PARTICIPLE_1("participle1Attribute", Language.GERMAN),
    ATTRIBUTES_PARTICIPLE_2("participle2Attribute", Language.GERMAN),
    ATTRIBUTES_ADJECTIVE("adjectiveAttribute", Language.GERMAN),
    ATTRIBUTES_PREPOSITION("prepositionalAttribute", Language.GERMAN),
    
    CONJUNCTIONS_ADVANCED("advancedConjunctions", Language.ENGLISH),
    CONJUNCTIONS_SIMPLE("simpleConjunctions", Language.ENGLISH),
    
    PREPOSITIONS("prepositions", Language.ENGLISH, Language.GERMAN),
    PREPOSITIONS_SIMPLE("simplePrepositions", Language.ENGLISH, Language.GERMAN),
    PREPOSITIONS_COMPLEX("complexPrepositions", Language.ENGLISH),
    PREPOSITIONS_ADVANCED("advancedPrepositions", Language.ENGLISH),
    
    // sentence structure
    CLAUSE_SUBORDINATE("subordinateClause", Language.ENGLISH, Language.GERMAN),
    CLAUSE_RELATIVE("relativeClause", Language.ENGLISH, Language.GERMAN),
    CLAUSE_RELATIVE_REDUCED("relativeClauseReduced", Language.ENGLISH),
    CLAUSE_ADVERBIAL("adverbialClause", Language.ENGLISH, Language.GERMAN),
    CLAUSE_DASS("dassClause", Language.GERMAN),
    SENTENCE_SIMPLE("simpleSentence", Language.ENGLISH, Language.GERMAN),
    SENTENCE_COMPLEX("complexSentence", Language.ENGLISH, Language.GERMAN),
    SENTENCE_COMPOUND("compoundSentence", Language.ENGLISH, Language.GERMAN),
    SENTENCE_INCOMPLETE("incompleteSentence", Language.ENGLISH, Language.GERMAN),
    
    OBJECT_DIRECT("directObject", Language.ENGLISH),	 // "give me"
    OBJECT_INDIRECT("indirectObject", Language.ENGLISH),	 // "give it toPrep me"
    
    PRONOUNS("pronouns", Language.ENGLISH, Language.GERMAN),
    PRONOUNS_PERSONAL("pronounsPersonal", Language.GERMAN),
    PRONOUNS_RELATIVE("pronounsRelative", Language.GERMAN),
    PRONOUNS_POSSESSIVE("pronounsPossessive", Language.ENGLISH, Language.GERMAN), // /PRP$ ("", my, your, their)
    PRONOUNS_DEMONSTRATIVE("pronounsDemonstrative", Language.GERMAN), // /JJ or PRP... ("", mine, yours, theirs)
    PRONOUNS_REFLEXIVE("pronounsReflexive", Language.ENGLISH, Language.GERMAN), // /PRP + myself, themselves, etc.
    PRONOUNS_INDEFINITE("pronounsIndefinite", Language.GERMAN),
    PRONOUNS_INTERROGATIVE("pronounsInterrogative", Language.GERMAN),
    PRONOUNS_SUBJECTIVE("pronounsSubjective", Language.ENGLISH),
    
    // quantifiers
    DETERMINER_SOME("someDet", Language.ENGLISH, Language.GERMAN),
    DETERMINER_ANY("anyDet", Language.ENGLISH, Language.GERMAN),
    DETERMINER_MUCH("muchDet", Language.ENGLISH),
    DETERMINER_MANY("manyDet", Language.ENGLISH, Language.GERMAN),
    DETERMINER_A_LOT_OF("aLotOfDet", Language.ENGLISH),
    
    ARTICLES("articles", Language.ENGLISH, Language.GERMAN),
    ARTICLE_THE("theArticle", Language.ENGLISH, Language.GERMAN),
    ARTICLE_A("aArticle", Language.ENGLISH, Language.GERMAN),
    ARTICLE_AN("anArticle", Language.ENGLISH),
    
    NOUNS_ISMUS("ismusNounForms", Language.GERMAN),
    NOUNS_TUR("turNounForms", Language.GERMAN),
    NOUNS_UNG("ungNounForms", Language.GERMAN),
    
    NEGATION_ALL("negAll", Language.ENGLISH, Language.GERMAN), // nobody, nowhere, etc.
    NEGATION_PARTIAL("partialNegation", Language.ENGLISH, Language.GERMAN), // rarely, barely, seldom, hardly, scarcely
    NEGATION_NO_NOT_NEVER("noNotNever", Language.ENGLISH, Language.GERMAN),
    NEGATION_NT("nt", Language.ENGLISH),
    NEGATION_NOT("not", Language.ENGLISH),
    
    QUESTIONS_DIRECT("directQuestions", Language.ENGLISH, Language.GERMAN),
    QUESTIONS_INDIRECT("indirectQuestions", Language.ENGLISH, Language.GERMAN),
    QUESTIONS_YESNO("yesNoQuestions", Language.ENGLISH, Language.GERMAN), // direct: "Are you ok?"
    QUESTIONS_WH("whQuestions", Language.ENGLISH, Language.GERMAN),// direct: "What do you do?"
    QUESTIONS_TO_BE("toBeQuestions", Language.ENGLISH),// direct: "What's this?"
    QUESTIONS_TO_DO("toDoQuestions", Language.ENGLISH), // direct: "What do you do?"
    QUESTIONS_TO_HAVE("toHaveQuestions", Language.ENGLISH),// direct: "What have you done?"
    QUESTIONS_MODAL("modalQuestions", Language.ENGLISH),// direct: "Should I go?", "What should I do?"
    QUESTIONS_WHAT("what", Language.ENGLISH, Language.GERMAN),
    QUESTIONS_WHO("who", Language.ENGLISH, Language.GERMAN),
    QUESTIONS_HOW("how", Language.ENGLISH, Language.GERMAN),
    QUESTIONS_WHY("why", Language.ENGLISH, Language.GERMAN),
    QUESTIONS_WHERE("where", Language.ENGLISH, Language.GERMAN),
    QUESTIONS_WHEN("when", Language.ENGLISH, Language.GERMAN),
    QUESTIONS_WHOSE("whose", Language.ENGLISH, Language.GERMAN),
    QUESTIONS_WHOM("whom", Language.ENGLISH, Language.GERMAN),
    QUESTIONS_WHICH("which", Language.ENGLISH, Language.GERMAN),
    QUESTIONS_TAG("tagQuestions", Language.ENGLISH, Language.GERMAN), // ", isn't it?"
    
    // conditionals - check first, before tenses
    CONDITIONALS("conditionals", Language.ENGLISH),
    CONDITIONALS_REAL("condReal", Language.ENGLISH),
    CONDITIONALS_UNREAL("condUnreal", Language.ENGLISH),
    
    // tenses - only if not conditional
    TENSE_PRESENT_SIMPLE("presentSimple", Language.ENGLISH),
    TENSE_PAST_SIMPLE("pastSimple", Language.ENGLISH),
    TENSE_PRESENT_PERFECT_HABEN("presentPerfectHaben", Language.GERMAN),
    TENSE_PRESENT_PERFECT_SEIN("presentPerfectSein", Language.GERMAN),
    TENSE_PAST_PERFECT_HABEN("pastPerfectHaben", Language.GERMAN),
    TENSE_PAST_PERFECT_SEIN("pastPerfectSein", Language.GERMAN),
    TENSE_FUTURE_SIMPLE("futureSimple", Language.ENGLISH, Language.GERMAN),
    TENSE_FUTURE_PERFECT("futurePerfect", Language.ENGLISH, Language.GERMAN),
    
    ASPECT_SIMPLE("simpleAspect", Language.ENGLISH),
    ASPECT_PROGRESSIVE("progressiveAspect", Language.ENGLISH),
    ASPECT_PERFECT("perfectAspect", Language.ENGLISH),
    ASPECT_PERFECT_PROGRESSIVE("perfProgAspect", Language.ENGLISH),
    
    TIME_PRESENT("presentTime", Language.ENGLISH),
    TIME_PAST("pastTime", Language.ENGLISH),
    TIME_FUTURE("futureTime", Language.ENGLISH),
    
    VERBCONST_GOING_TO("goingTo", Language.ENGLISH),
    VERBCONST_USED_TO("usedTo", Language.ENGLISH),
    
    VERBTYP_MAIN("mainVerbs", Language.GERMAN),
    VERBTYP_AUXILIARIES("auxiliaryVerbs", Language.GERMAN),
    VERBTYP_MODAL("modalVerbs", Language.GERMAN),
    
    VERBFORM_TO_INFINITIVE("toInfinitiveForms", Language.ENGLISH, Language.GERMAN),
    VERBFORM_INFINITIVE("infinitiveForms", Language.GERMAN),// "I want toPrep do it."
    VERBFORM_PARTICIPLE("participleForms", Language.GERMAN),// "I want toPrep do it."
    
    VERB_CLUSTER("verbCluster", Language.GERMAN),
    VERB_BRACKETS("verbBrackets", Language.GERMAN),
    
    MODALS("modals", Language.ENGLISH),// all
    MODALS_SIMPLE("simpleModals", Language.ENGLISH),// can, must, need, may
    MODALS_ADVANCED("advancedModals", Language.ENGLISH),// the others
    MODALS_CAN("can", Language.ENGLISH),// Klasse 6
    MODALS_MUST("must", Language.ENGLISH),// Klasse 6
    MODALS_NEED("need", Language.ENGLISH),// Klasse 6
    MODALS_MAY("may", Language.ENGLISH),// Klasse 6
    MODALS_COULD("could", Language.ENGLISH),// Klasse 10
    MODALS_MIGHT("might", Language.ENGLISH),// Klasse 10
    MODALS_OUGHT("ought", Language.ENGLISH),// Klasse 10
    MODALS_ABLE("able", Language.ENGLISH),// Klasse 10 ("", annotated as JJ)
    MODALS_HAVE_TO("haveTo", Language.ENGLISH),// ??
    
    VERBS_IRREGULAR("irregularVerbs", Language.ENGLISH),// past tense or past participle not ending with -ed
    VERBS_REGULAR("regularVerbs", Language.ENGLISH),// past tense or past participle ending with -ed
    VERBS_PHRASAL("phrasalVerbs", Language.ENGLISH),// phrasal verbs ("", & verbs with prepositions: look atPrep)
    
    IMPERATIVES("imperatives", Language.ENGLISH, Language.GERMAN),// start with a Verb, often end with "!": "Do it yourself!"
    PASSIVE_VOICE_WERDEN("passiveVoiceWerden", Language.GERMAN),
    PASSIVE_VOICE_SEIN("passiveVoiceSein", Language.GERMAN),
    
    ADJECTIVE_POSITIVE("positiveAdj", Language.ENGLISH, Language.GERMAN),// "nice"
    ADJECTIVE_COMPARATIVE_SHORT("comparativeAdjShort", Language.ENGLISH),// "nicer"
    ADJECTIVE_SUPERLATIVE_SHORT("superlativeAdjShort", Language.ENGLISH),// "nicest"
    ADJECTIVE_COMPARATIVE_LONG("comparativeAdjLong", Language.ENGLISH),// "more beautiful"
    ADJECTIVE_SUPERLATIVE_LONG("superlativeAdjLong", Language.ENGLISH),// "most beautiful"
    
    ADVERB_POSITIVE("positiveAdv", Language.ENGLISH, Language.GERMAN),// "quickly"
    ADVERB_COMPARATIVE_SHORT("comparativeAdvShort", Language.ENGLISH),// "faster"
    ADVERB_SUPERLATIVE_SHORT("superlativeAdvShort", Language.ENGLISH),// "fastest"
    ADVERB_COMPARATIVE_LONG("comparativeAdvLong", Language.ENGLISH),// "more quickly"
    ADVERB_SUPERLATIVE_LONG("superlativeAdvLong", Language.ENGLISH),// "most quickly"
    
    PARTICLE_PLUS_ADJ_ADV("particleAdjAdv", Language.GERMAN),
    
    CARDINALS("cardinals", Language.GERMAN),
    
    PLURAL_REGULAR("pluralRegular", Language.ENGLISH),
    PLURAL_IRREGULAR("pluralIrregular", Language.ENGLISH),
    NOUNFORMS_ING("ingNounForms", Language.ENGLISH),
	
    TENSE_PRESENT_PROGRESSIVE("presentProgressive", Language.ENGLISH),
    TENSE_PRESENT_PERFECT_PROGRESSIVE("presentPerfProg", Language.ENGLISH),
    TENSE_PAST_PROGRESSIVE("pastProgressive", Language.ENGLISH),
    TENSE_PAST_PERFECT_PROGRESSIVE("pastPerfProg", Language.ENGLISH),
    TENSE_FUTURE_PROGRESSIVE("futureProgressive", Language.ENGLISH),
    TENSE_FUTURE_PERFECT_PROGRESSIVE("futurePerfProg", Language.ENGLISH),
	
    VERBFORM_SHORT("shortVerbForms", Language.ENGLISH), // 's, 're, 'm, 's, 've, 'd??!
    VERBFORM_LONG("longVerbForms", Language.ENGLISH), // is, are, am, has, have, had??!
    VERBFORM_AUXILIARIES_BE_DO_HAVE("auxiliariesBeDoHave", Language.ENGLISH), // be, do, have??! ("", got?), NOT modals!!! + V
    VERBFORM_COPULAR("copularVerbs", Language.ENGLISH), // be, stay, seem, etc. - CHECK the parser
    VERBFORM_ING("ingVerbForms", Language.ENGLISH), // gerund, participle, nouns
    VERBFORM_EMPATHIC_DO("emphaticDo", Language.ENGLISH), // "I do realize it": do/did/VBP followed by /VB
    
    PRONOUNS_POSSESSIVE_ABSOLUTE("pronounsPossessiveAbsolute", Language.ENGLISH), // /JJ or PRP... ("", mine, yours, theirs)
    PASSIVE_VOICE("passiveVoice", Language.ENGLISH),
    TENSE_PRESENT_PERFECT("presentPerfect", Language.ENGLISH),
    TENSE_PAST_PERFECT("pastPerfect", Language.ENGLISH),
    PRONOUNS_OBJECTIVE("pronounsObjective", Language.ENGLISH), // /PRP + me, you, them...
    POSTPOSITION("postposition", Language.GERMAN),
    
    ;
    
	
	static final class Helper
	{
		private static final Map<String, GrammaticalConstruction>	UNIQUE_IDS = new HashMap<>();
		
		private static void registerID(String id, GrammaticalConstruction gram)
	    {
	    	if (UNIQUE_IDS.containsKey(id))
	    		throw new RuntimeException("Grammatical construction ID already registered");
	    	else
	    		UNIQUE_IDS.put(id, gram);
		}
	}
	
    private final String			id;			// unique ID
    private final Set<Language>		langs;		// languages that use the construction
    
	GrammaticalConstruction(String id, Language... languages)
	{
		this.id = id;
		this.langs = new HashSet<>();
		
		Helper.registerID(id, this);
		for (Language itr : languages)
			langs.add(itr);
	}

	@Override
	public String toString() {
		return getID();
	}

	public String getID() {
		return this.id;
	}
	
	public boolean hasLanguage(Language lang) {
		return langs.contains(lang);
	}
	
	public static GrammaticalConstruction lookup(String id) {
		return Helper.UNIQUE_IDS.get(id);
	}
	
	public static Set<GrammaticalConstruction> getForLanguage(Language lang)
	{
		HashSet<GrammaticalConstruction> out = new HashSet<>();
		
		for (GrammaticalConstruction itr : GrammaticalConstruction.values())
		{
			if (itr.hasLanguage(lang))
				out.add(itr);
		}
		
		return out;
	}
}

