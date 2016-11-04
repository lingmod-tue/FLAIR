/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.grammar;

/**
 * Represents a (generally) language-agnostic grammatical construction. 
 * @author shadeMe
*/
public enum GrammaticalConstruction
{
    // (simple) constructions 
// (simple) constructions 
    EXISTENTIAL_THERE("existentialThere", "Existential There"),
    THERE_IS_ARE("thereIsAre", "There, Is, Are"),
    THERE_WAS_WERE("thereWasWere", "There, Was, Were"),
    
    ATTRIBUTES_PARTICIPLE_1("participle1Attribute", "Partizipialattriibut I"),
    ATTRIBUTES_PARTICIPLE_2("participle2Attribute", "Partizipialattriibut II"),
    ATTRIBUTES_ADJECTIVE("adjectiveAttribute", "Adjektivattribut"),
    ATTRIBUTES_PREPOSITION("prepositionalAttribute", "Präpositionalattribut"),
    
    CONJUNCTIONS_ADVANCED("advancedConjunctions", "Advanced Conjunctions"),
    CONJUNCTIONS_SIMPLE("simpleConjunctions", "Simple Conjunctions"),
    
    PREPOSITIONS("prepositions", "Präpositionen"),
    PREPOSITIONS_SIMPLE("simplePrepositions", "Präpositionen"),
    PREPOSITIONS_COMPLEX("complexPrepositions", "Postpositionen"),
    PREPOSITIONS_ADVANCED("advancedPrepositions", "Advanced Prepositions"),
    
    // sentence structure
    CLAUSE_SUBORDINATE("subordinateClause", "subordinierte Sätze"),
    CLAUSE_RELATIVE("relativeClause", "Relativsätze"),
    CLAUSE_RELATIVE_REDUCED("relativeClauseReduced", "Reduced Relative Clause"),
    CLAUSE_ADVERBIAL("adverbialClause", "Adverbialsätze"),
    CLAUSE_DASS("dassClause", "'dass'-Sätze"),
    SENTENCE_SIMPLE("simpleSentence", "einfache Sätze"),
    SENTENCE_COMPLEX("complexSentence", "komplexe Sätze"),
    SENTENCE_COMPOUND("compoundSentence", "koordinierte Sätze"),
    SENTENCE_INCOMPLETE("incompleteSentence", "Satzfragmente"),
    
    OBJECT_DIRECT("directObject", "Direct Object"),	 // "give me"
    OBJECT_INDIRECT("indirectObject", "Indirect Object"),	 // "give it toPrep me"
    
    PRONOUNS("pronouns", "Pronomen"),
    PRONOUNS_PERSONAL("pronounsPersonal", "Personalpronomen"), 
    PRONOUNS_RELATIVE("pronounsRelative", "Relativpronomen"), 
    PRONOUNS_POSSESSIVE("pronounsPossessive", "Posssessivpronomen"), // /PRP$ ("", my, your, their)
    PRONOUNS_DEMONSTRATIVE("pronounsDemonstrative", "Demonstrativpronomen"), // /JJ or PRP... ("", mine, yours, theirs)
    PRONOUNS_REFLEXIVE("pronounsReflexive", "Reflexivpronomen"), // /PRP + myself, themselves, etc.
    PRONOUNS_INDEFINITE("pronounsIndefinite", "Indefinitpronomen"), 
    PRONOUNS_INTERROGATIVE("pronounsInterrogative", "Interrogativpronomen"), 
    
    // quantifiers
    DETERMINER_SOME("someDet", "Determiner 'Some'"),
    DETERMINER_ANY("anyDet", "Determiner 'Any'"),
    DETERMINER_MUCH("muchDet", "Determiner 'Much'"),
    DETERMINER_MANY("manyDet", "Determeiner 'Many'"),
    DETERMINER_A_LOT_OF("aLotOfDet", "Determiner 'A lot of'"),
    
    ARTICLES("articles", "Artikel"),
    ARTICLE_THE("theArticle", "bestimmte Artikel"),
    ARTICLE_A("aArticle", "unbestimmte Artikel"),
    ARTICLE_AN("anArticle", "Article 'An"),
    
    NOUNS_ISMUS("ismusNounForms", "Nomen auf '-ismus'"),
    NOUNS_TUR("turNounForms", "Nomen auf '-tur'"),
    NOUNS_UNG("ungNounForms", "Nomen auf '-ung'"),
    
    NEGATION_ALL("negAll", "Negation"), // nobody, nowhere, etc.
    NEGATION_PARTIAL("partialNegation", "Partielle Negation"), // rarely, barely, seldom, hardly, scarcely
    NEGATION_NO_NOT_NEVER("noNotNever", "Volle Negation"),
    NEGATION_NT("nt", "'-nt' Negation"),
    NEGATION_NOT("not", "'Not' Negation"),
    
    QUESTIONS_DIRECT("directQuestions", "direke Fragen"),
    QUESTIONS_INDIRECT("indirectQuestions", "indirekte Fragen"),
    QUESTIONS_YESNO("yesNoQuestions", "'Ja/Nein' Fragen"), // direct: "Are you ok?"
    QUESTIONS_WH("whQuestions", "W-Fragen"), // direct: "What do you do?"
    QUESTIONS_TO_BE("toBeQuestions", "'To Be' Questions"), // direct: "What's this?"
//    QUESTIONS_TO_DO("toDoQuestions", "'To Do' Questions"), // direct: "What do you do?"
    QUESTIONS_TO_HAVE("toHaveQuestions", "'To Have' Questions"), // direct: "What have you done?"
    QUESTIONS_MODAL("modalQuestions", "Modal Questions"), // direct: "Should I go?", "What should I do?"
    QUESTIONS_WHAT("what", "'What' Questions"),
    QUESTIONS_WHO("who", "'Who' Questions"),
    QUESTIONS_HOW("how", "'How' Questions"),
    QUESTIONS_WHY("why", "'Why' Questions"),
    QUESTIONS_WHERE("where", "'Where' Questions"),
    QUESTIONS_WHEN("when", "'When' Questions"),
    QUESTIONS_WHOSE("whose", "'Whose' Questions"),
    QUESTIONS_WHOM("whom", "'Whom' Questions"),
    QUESTIONS_WHICH("which", "'Which' Questions"),
    QUESTIONS_TAG("tagQuestions", "Nachziehfragen"), // ", isn't it?"
    
    // conditionals - check first, before tenses
    CONDITIONALS("conditionals", "Conditionals"),
    CONDITIONALS_REAL("condReal", "Real Conditionals"),
    CONDITIONALS_UNREAL("condUnreal", "Unreal Conditionals"),
    
    // tenses - only if not conditional
    TENSE_PRESENT_SIMPLE("presentSimple", "Präsens"),
    TENSE_PAST_SIMPLE("pastSimple", "Präteritum"),
    TENSE_PRESENT_PERFECT_HABEN("presentPerfectHaben", "'haben'-Perfekt"),
    TENSE_PRESENT_PERFECT_SEIN("presentPerfectSein", "'sein'-Perfekt"),
    TENSE_PAST_PERFECT_HABEN("pastPerfectHaben", "'haben'-Plusquamperfekt"),
    TENSE_PAST_PERFECT_SEIN("pastPerfectSein", "'sein'-Plusquamperfekt"),
    TENSE_FUTURE_SIMPLE("futureSimple", "Futur I"),
    TENSE_FUTURE_PERFECT("futurePerfect", "Futur II"),
    
    ASPECT_SIMPLE("simpleAspect", "Simple Aspect"),
    ASPECT_PROGRESSIVE("progressiveAspect", "Progressive Aspect"),
    ASPECT_PERFECT("perfectAspect", "Perfect Aspect"),
    ASPECT_PERFECT_PROGRESSIVE("perfProgAspect", "Perfect Progressive Aspect"),
    
    TIME_PRESENT("presentTime", "Present Time"),
    TIME_PAST("pastTime", "Past Time"),
    TIME_FUTURE("futureTime", "Future Time"),
    
    VERBCONST_GOING_TO("goingTo", "'Going To' Verb"),
//    VERBCONST_USED_TO("usedTo", "'Used To' Verb"),
    
    VERBTYP_MAIN("mainVerbs", "Vollverben"), 
    VERBTYP_AUXILIARIES("auxiliaryVerbs", "Hilfsverben"), 
    VERBTYP_MODAL("modalVerbs", "Modalverben"), 
    
    VERBFORM_TO_INFINITIVE("toInfinitiveForms", "'zu' Infinitive"), 
    VERBFORM_INFINITIVE("infinitiveForms", "Infinitive"), // "I want toPrep do it."
    VERBFORM_PARTICIPLE("participleForms", "Partizipien"), // "I want toPrep do it."
    VERBFORM_PARTICIPLE_1("participleForms1", "Participle 1 Verb Forms"),
    VERBFORM_PARTICIPLE_2("participleForms2", "Participle 2 Verb Forms"),
    
    VERB_CLUSTER("verbCluster", "Verbcluster"),
    VERB_BRACKETS("verbBrackets", "besetzte Satzklammer"),
    
    MODALS("modals", "Modalverben"), // all
    MODALS_SIMPLE("simpleModals", "Simple Modals"), // can, must, need, may
    MODALS_ADVANCED("advancedModals", "Advanced Modals"), // the others
    MODALS_CAN("can", "'Can' Modal"), // Klasse 6
    MODALS_MUST("must", "'Must' Modal"), // Klasse 6
    MODALS_NEED("need", "'Need' Modal"), // Klasse 6
    MODALS_MAY("may", "'May' Modal"), // Klasse 6
    MODALS_COULD("could", "'Could' Modal"), // Klasse 10
    MODALS_MIGHT("might", "'Might' Modal"), // Klasse 10
    MODALS_OUGHT("ought", "'Ought' Modal"), // Klasse 10
    MODALS_ABLE("able", "'Able' Modal"), // Klasse 10 ("", annotated as JJ)
    MODALS_HAVE_TO("haveTo", "'Have To' Modal"), // ??
    
    VERBS_IRREGULAR("irregularVerbs", "Irregular Verbs"), // past tense or past participle not ending with -ed
    VERBS_REGULAR("regularVerbs", "Regular Verbs"), // past tense or past participle ending with -ed
    VERBS_PHRASAL("phrasalVerbs", "Phrasal Verbs"),// phrasal verbs ("", & verbs with prepositions: look atPrep)
    
    IMPERATIVES("imperatives", "Imperative"), // start with a Verb, often end with "!": "Do it yourself!"
    PASSIVE_VOICE_WERDEN("passiveVoiceWerden", "Passiv mit 'werden'"),
    PASSIVE_VOICE_SEIN("passiveVoiceSein", "Passiv mit 'sein'"),
    
    ADJECTIVE_POSITIVE("positiveAdj", "Adjektive"), // "nice"
    ADJECTIVE_COMPARATIVE_SHORT("comparativeAdjShort", "Comparative Adjective Short"), // "nicer"
    ADJECTIVE_SUPERLATIVE_SHORT("superlativeAdjShort", "Superlative Adjective Short"), // "nicest"
    ADJECTIVE_COMPARATIVE_LONG("comparativeAdjLong", "Comparative Adjective Long"), // "more beautiful"
    ADJECTIVE_SUPERLATIVE_LONG("superlativeAdjLong", "Superlative Adjective Long"), // "most beautiful"
    
    ADVERB_POSITIVE("positiveAdv", "Adverben"), // "quickly"
    ADVERB_COMPARATIVE_SHORT("comparativeAdvShort", "Comparative Adverb Short"), // "faster"
    ADVERB_SUPERLATIVE_SHORT("superlativeAdvShort", "Superlative Adverb Short"), // "fastest"
    ADVERB_COMPARATIVE_LONG("comparativeAdvLong", "Comparative Adverb Long"), // "more quickly"
    ADVERB_SUPERLATIVE_LONG("superlativeAdvLong", "Superlative Adverb Long"), // "most quickly"
    
    PARTICLE_PLUS_ADJ_ADV("particleAdjAdv", "Adjektiv- und Adverbpartikel"),
    
    CARDINALS("cardinals", "Zahlworte"), 
    
    ;

    private final String		frontendID;	// identifier used in the front-end
    private final String		prettyName;
    
    GrammaticalConstruction(String legacyID, String prettyName) {
	this.frontendID = legacyID;
	this.prettyName = prettyName;
    }
    
    @Override
    public String toString() {
	return prettyName;
    }
    
    public String getFrontendID() {
	return this.frontendID;
    }
}
