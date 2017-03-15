/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.shared.grammar;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a (generally) language-agnostic grammatical construction.
 * IMPORTANT - ORDER-DEPENDENT! ADD NEW ITEMS TO THE END!
 * @author shadeMe
*/
public enum GrammaticalConstruction
{
    // (simple) constructions 
    EXISTENTIAL_THERE("existentialThere"),
    THERE_IS_ARE("thereIsAre"),
    THERE_WAS_WERE("thereWasWere"),
    
    ATTRIBUTES_PARTICIPLE_1("participle1Attribute"),
    ATTRIBUTES_PARTICIPLE_2("participle2Attribute"),
    ATTRIBUTES_ADJECTIVE("adjectiveAttribute"),
    ATTRIBUTES_PREPOSITION("prepositionalAttribute"),
    
    CONJUNCTIONS_ADVANCED("advancedConjunctions"),
    CONJUNCTIONS_SIMPLE("simpleConjunctions"),
    
    PREPOSITIONS("prepositions"),
    PREPOSITIONS_SIMPLE("simplePrepositions"),
    PREPOSITIONS_COMPLEX("complexPrepositions"),
    PREPOSITIONS_ADVANCED("advancedPrepositions"),
    
    // sentence structure
    CLAUSE_SUBORDINATE("subordinateClause"),
    CLAUSE_RELATIVE("relativeClause"),
    CLAUSE_RELATIVE_REDUCED("relativeClauseReduced"),
    CLAUSE_ADVERBIAL("adverbialClause"),
    CLAUSE_DASS("dassClause"),
    SENTENCE_SIMPLE("simpleSentence"),
    SENTENCE_COMPLEX("complexSentence"),
    SENTENCE_COMPOUND("compoundSentence"),
    SENTENCE_INCOMPLETE("incompleteSentence"),
    
    OBJECT_DIRECT("directObject"),	 // "give me"
    OBJECT_INDIRECT("indirectObject"),	 // "give it toPrep me"
    
    PRONOUNS("pronouns"),
    PRONOUNS_PERSONAL("pronounsPersonal"), 
    PRONOUNS_RELATIVE("pronounsRelative"), 
    PRONOUNS_POSSESSIVE("pronounsPossessive"), // /PRP$ ("", my, your, their)
    PRONOUNS_DEMONSTRATIVE("pronounsDemonstrative"), // /JJ or PRP... ("", mine, yours, theirs)
    PRONOUNS_REFLEXIVE("pronounsReflexive"), // /PRP + myself, themselves, etc.
    PRONOUNS_INDEFINITE("pronounsIndefinite"), 
    PRONOUNS_INTERROGATIVE("pronounsInterrogative"), 
    PRONOUNS_SUBJECTIVE("pronounsSubjective"),
    
    // quantifiers
    DETERMINER_SOME("someDet"),
    DETERMINER_ANY("anyDet"),
    DETERMINER_MUCH("muchDet"),
    DETERMINER_MANY("manyDet"),
    DETERMINER_A_LOT_OF("aLotOfDet"),
    
    ARTICLES("articles"),
    ARTICLE_THE("theArticle"),
    ARTICLE_A("aArticle"),
    ARTICLE_AN("anArticle"),
    
    NOUNS_ISMUS("ismusNounForms"),
    NOUNS_TUR("turNounForms"),
    NOUNS_UNG("ungNounForms"),
    
    NEGATION_ALL("negAll"), // nobody, nowhere, etc.
    NEGATION_PARTIAL("partialNegation"), // rarely, barely, seldom, hardly, scarcely
    NEGATION_NO_NOT_NEVER("noNotNever"),
    NEGATION_NT("nt"),
    NEGATION_NOT("not"),
    
    QUESTIONS_DIRECT("directQuestions"),
    QUESTIONS_INDIRECT("indirectQuestions"),
    QUESTIONS_YESNO("yesNoQuestions"), // direct: "Are you ok?"
    QUESTIONS_WH("whQuestions"),// direct: "What do you do?"
    QUESTIONS_TO_BE("toBeQuestions"),// direct: "What's this?"
    QUESTIONS_TO_DO("toDoQuestions"), // direct: "What do you do?"
    QUESTIONS_TO_HAVE("toHaveQuestions"),// direct: "What have you done?"
    QUESTIONS_MODAL("modalQuestions"),// direct: "Should I go?", "What should I do?"
    QUESTIONS_WHAT("what"),
    QUESTIONS_WHO("who"),
    QUESTIONS_HOW("how"),
    QUESTIONS_WHY("why"),
    QUESTIONS_WHERE("where"),
    QUESTIONS_WHEN("when"),
    QUESTIONS_WHOSE("whose"),
    QUESTIONS_WHOM("whom"),
    QUESTIONS_WHICH("which"),
    QUESTIONS_TAG("tagQuestions"), // ", isn't it?"
    
    // conditionals - check first, before tenses
    CONDITIONALS("conditionals"),
    CONDITIONALS_REAL("condReal"),
    CONDITIONALS_UNREAL("condUnreal"),
    
    // tenses - only if not conditional
    TENSE_PRESENT_SIMPLE("presentSimple"),
    TENSE_PAST_SIMPLE("pastSimple"),
    TENSE_PRESENT_PERFECT_HABEN("presentPerfectHaben"),
    TENSE_PRESENT_PERFECT_SEIN("presentPerfectSein"),
    TENSE_PAST_PERFECT_HABEN("pastPerfectHaben"),
    TENSE_PAST_PERFECT_SEIN("pastPerfectSein"),
    TENSE_FUTURE_SIMPLE("futureSimple"),
    TENSE_FUTURE_PERFECT("futurePerfect"),
    
    ASPECT_SIMPLE("simpleAspect"),
    ASPECT_PROGRESSIVE("progressiveAspect"),
    ASPECT_PERFECT("perfectAspect"),
    ASPECT_PERFECT_PROGRESSIVE("perfProgAspect"),
    
    TIME_PRESENT("presentTime"),
    TIME_PAST("pastTime"),
    TIME_FUTURE("futureTime"),
    
    VERBCONST_GOING_TO("goingTo"),
    VERBCONST_USED_TO("usedTo"),
    
    VERBTYP_MAIN("mainVerbs"),
    VERBTYP_AUXILIARIES("auxiliaryVerbs"),
    VERBTYP_MODAL("modalVerbs"),
    
    VERBFORM_TO_INFINITIVE("toInfinitiveForms"),
    VERBFORM_INFINITIVE("infinitiveForms"),// "I want toPrep do it."
    VERBFORM_PARTICIPLE("participleForms"),// "I want toPrep do it."
    VERBFORM_PARTICIPLE_1("participleForms1"),
    VERBFORM_PARTICIPLE_2("participleForms2"),
    
    VERB_CLUSTER("verbCluster"),
    VERB_BRACKETS("verbBrackets"),
    
    MODALS("modals"),// all
    MODALS_SIMPLE("simpleModals"),// can, must, need, may
    MODALS_ADVANCED("advancedModals"),// the others
    MODALS_CAN("can"),// Klasse 6
    MODALS_MUST("must"),// Klasse 6
    MODALS_NEED("need"),// Klasse 6
    MODALS_MAY("may"),// Klasse 6
    MODALS_COULD("could"),// Klasse 10
    MODALS_MIGHT("might"),// Klasse 10
    MODALS_OUGHT("ought"),// Klasse 10
    MODALS_ABLE("able"),// Klasse 10 ("", annotated as JJ)
    MODALS_HAVE_TO("haveTo"),// ??
    
    VERBS_IRREGULAR("irregularVerbs"),// past tense or past participle not ending with -ed
    VERBS_REGULAR("regularVerbs"),// past tense or past participle ending with -ed
    VERBS_PHRASAL("phrasalVerbs"),// phrasal verbs ("", & verbs with prepositions: look atPrep)
    
    IMPERATIVES("imperatives"),// start with a Verb, often end with "!": "Do it yourself!"
    PASSIVE_VOICE_WERDEN("passiveVoiceWerden"),
    PASSIVE_VOICE_SEIN("passiveVoiceSein"),
    
    ADJECTIVE_POSITIVE("positiveAdj"),// "nice"
    ADJECTIVE_COMPARATIVE_SHORT("comparativeAdjShort"),// "nicer"
    ADJECTIVE_SUPERLATIVE_SHORT("superlativeAdjShort"),// "nicest"
    ADJECTIVE_COMPARATIVE_LONG("comparativeAdjLong"),// "more beautiful"
    ADJECTIVE_SUPERLATIVE_LONG("superlativeAdjLong"),// "most beautiful"
    
    ADVERB_POSITIVE("positiveAdv"),// "quickly"
    ADVERB_COMPARATIVE_SHORT("comparativeAdvShort"),// "faster"
    ADVERB_SUPERLATIVE_SHORT("superlativeAdvShort"),// "fastest"
    ADVERB_COMPARATIVE_LONG("comparativeAdvLong"),// "more quickly"
    ADVERB_SUPERLATIVE_LONG("superlativeAdvLong"),// "most quickly"
    
    PARTICLE_PLUS_ADJ_ADV("particleAdjAdv"),
    
    CARDINALS("cardinals"), 
    
    PLURAL_REGULAR("pluralRegular"),
    PLURAL_IRREGULAR("pluralIrregular"),
    NOUNFORMS_ING("ingNounForms"),
	
    TENSE_PRESENT_PROGRESSIVE("presentProgressive"),
    TENSE_PRESENT_PERFECT_PROGRESSIVE("presentPerfProg"),
    TENSE_PAST_PROGRESSIVE("pastProgressive"),
    TENSE_PAST_PERFECT_PROGRESSIVE("pastPerfProg"),
    TENSE_FUTURE_PROGRESSIVE("futureProgressive"),
    TENSE_FUTURE_PERFECT_PROGRESSIVE("futurePerfProg"),
	
    VERBFORM_SHORT("shortVerbForms"), // 's, 're, 'm, 's, 've, 'd??!
    VERBFORM_LONG("longVerbForms"), // is, are, am, has, have, had??!
    VERBFORM_AUXILIARIES_BE_DO_HAVE("auxiliariesBeDoHave"), // be, do, have??! ("", got?), NOT modals!!! + V
    VERBFORM_COPULAR("copularVerbs"), // be, stay, seem, etc. - CHECK the parser
    VERBFORM_ING("ingVerbForms"), // gerund, participle, nouns 
    VERBFORM_EMPATHIC_DO("emphaticDo"), // "I do realize it": do/did/VBP followed by /VB
    
    PRONOUNS_POSSESSIVE_ABSOLUTE("pronounsPossessiveAbsolute"), // /JJ or PRP... ("", mine, yours, theirs)
    PASSIVE_VOICE("passiveVoice"),
    TENSE_PRESENT_PERFECT("presentPerfect"),
    TENSE_PAST_PERFECT("pastPerfect"),
    PRONOUNS_OBJECTIVE("pronounsObjective"), // /PRP + me, you, them...
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
	
    private final String		id;	// unique ID
    
	GrammaticalConstruction(String id)
	{
		this.id = id;
		Helper.registerID(id, this);
	}

	@Override
	public String toString() {
		return getID();
	}

	public String getID() {
		return this.id;
	}
	
	public static GrammaticalConstruction lookup(String id) {
		return Helper.UNIQUE_IDS.get(id);
	}
}

