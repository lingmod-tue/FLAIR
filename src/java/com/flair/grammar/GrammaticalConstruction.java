/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    CONJUNCTIONS_ADVANCED("advancedConjunctions", "Advanced Conjunctions"),
    CONJUNCTIONS_SIMPLE("simpleConjunctions", "Simple Conjunctions"),
    
    PREPOSITIONS("prepositions", "Prepositions"),
    PREPOSITIONS_SIMPLE("simplePrepositions", "Simple Prepositions"),
    PREPOSITIONS_COMPLEX("complexPrepositions", "Complex Prepositions"),
    PREPOSITIONS_ADVANCED("advancedPrepositions", "Advanced Prepositions"),
    
    // sentence structure
    CLAUSE_SUBORDINATE("subordinateClause", "Subordinate Clause"),
    CLAUSE_RELATIVE("relativeClause", "Relative Clause"),
    CLAUSE_RELATIVE_REDUCED("relativeClauseReduced", "Reduced Relative Clause"),
    CLAUSE_ADVERBIAL("adverbialClause", "Adverbial Clause"),
    SENTENCE_SIMPLE("simpleSentence", "Simple Sentence"),
    SENTENCE_COMPLEX("complexSentence", "Complex Sentence"),
    SENTENCE_COMPOUND("compoundSentence", "Compound Sentence"),
    SENTENCE_INCOMPLETE("incompleteSentence", "Incomplete Sentence"),
    
    OBJECT_DIRECT("directObject", "Direct Object"),	 // "give me"
    OBJECT_INDIRECT("indirectObject", "Indirect Object"),	 // "give it toPrep me"
    
    PRONOUNS("pronouns", "Pronouns"),
    PRONOUNS_SUBJECTIVE("pronounsSubjective", "Subjective Pronouns"), // /PRP + I, you, they...
    PRONOUNS_OBJECTIVE("pronounsObjective", "Objective Pronouns"), // /PRP + me, you, them...
    PRONOUNS_POSSESSIVE("pronounsPossessive", "Posssessive Pronouns"), // /PRP$ ("", my, your, their)
    PRONOUNS_POSSESSIVE_ABSOLUTE("pronounsPossessiveAbsolute", "Absolute Possessive Pronouns"), // /JJ or PRP... ("", mine, yours, theirs)
    PRONOUNS_REFLEXIVE("pronounsReflexive", "Reflexive Pronouns"), // /PRP + myself, themselves, etc.
    
    // quantifiers
    DETERMINER_SOME("someDet", "Determiner 'Some'"),
    DETERMINER_ANY("anyDet", "Determiner 'Any'"),
    DETERMINER_MUCH("muchDet", "Determiner 'Much'"),
    DETERMINER_MANY("manyDet", "Determeiner 'Many'"),
    DETERMINER_A_LOT_OF("aLotOfDet", "Determiner 'A lot of'"),
    
    ARTICLES("articles", "Articles"),
    ARTICLE_THE("theArticle", "Article 'The'"),
    ARTICLE_A("aArticle", "Article 'A'"),
    ARTICLE_AN("anArticle", "Article 'An"),
    
    PLURAL_REGULAR("pluralRegular", "Regular Plural"),
    PLURAL_IRREGULAR("pluralIrregular", "Irregular Plural"),
    NOUNFORMS_ING("ingNounForms", "'-ing' Noun Forms"),
    
    NEGATION_ALL("negAll", "All Negation"), // nobody, nowhere, etc.
    NEGATION_PARTIAL("partialNegation", "Partial Negation"), // rarely, barely, seldom, hardly, scarcely
    NEGATION_NO_NOT_NEVER("noNotNever", "No, Not, Never"),
    NEGATION_NT("nt", "'-nt' Negation"),
    NEGATION_NOT("not", "'Not' Negation"),
    
    QUESTIONS_DIRECT("directQuestions", "Direct Questions"),
    QUESTIONS_INDIRECT("indirectQuestions", "Indirect Questions"),
    QUESTIONS_YESNO("yesNoQuestions", "'Yes/No' Questions"), // direct: "Are you ok?"
    QUESTIONS_WH("whQuestions", "'Wh-' Questions"), // direct: "What do you do?"
    QUESTIONS_TO_BE("toBeQuestions", "'To Be' Questions"), // direct: "What's this?"
    QUESTIONS_TO_DO("toDoQuestions", "'To Do' Questions"), // direct: "What do you do?"
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
    QUESTIONS_TAG("tagQuestions", "Question Tag"), // ", isn't it?"
    
    // conditionals - check first, before tenses
    CONDITIONALS("conditionals", "Conditionals"),
    CONDITIONALS_REAL("condReal", "Real Conditionals"),
    CONDITIONALS_UNREAL("condUnreal", "Unreal Conditionals"),
    
    // tenses - only if not conditional
    TENSE_PRESENT_SIMPLE("presentSimple", "Present Simple"),
    TENSE_PRESENT_PROGRESSIVE("presentProgressive", "Present Progressive"),
    TENSE_PRESENT_PERFECT("presentPerfect", "Present Perfect"),
    TENSE_PRESENT_PERFECT_PROGRESSIVE("presentPerfProg", "Present Perfect Progressive"),
    TENSE_PAST_SIMPLE("pastSimple", "Past Simple"),
    TENSE_PAST_PROGRESSIVE("pastProgressive", "Past Progressive"),
    TENSE_PAST_PERFECT("pastPerfect", "Past Perfect"),
    TENSE_PAST_PERFECT_PROGRESSIVE("pastPerfProg", "Past Perfect Progressive"),
    TENSE_FUTURE_SIMPLE("futureSimple", "Future Simple"),
    TENSE_FUTURE_PROGRESSIVE("futureProgressive", "Future Progressive"),
    TENSE_FUTURE_PERFECT("futurePerfect", "Future Perfect"),
    TENSE_FUTURE_PERFECT_PROGRESSIVE("futurePerfProg", "Future Perfect Progressive"),
    
    ASPECT_SIMPLE("simpleAspect", "Simple Aspect"),
    ASPECT_PROGRESSIVE("progressiveAspect", "Progressive Aspect"),
    ASPECT_PERFECT("perfectAspect", "Perfect Aspect"),
    ASPECT_PERFECT_PROGRESSIVE("perfProgAspect", "Perfect Progressive Aspect"),
    
    TIME_PRESENT("presentTime", "Present Time"),
    TIME_PAST("pastTime", "Past Time"),
    TIME_FUTURE("futureTime", "Future Time"),
    
    VERBCONST_GOING_TO("goingTo", "'Going To' Verb"),
    VERBCONST_USED_TO("usedTo", "'Used To' Verb"),
    
    VERBFORM_SHORT("shortVerbForms", "Short Verb Forms"), // 's, 're, 'm, 's, 've, 'd??!
    VERBFORM_LONG("longVerbForms", "Long Verb Forms"), // is, are, am, has, have, had??!
    VERBFORM_AUXILIARIES_BE_DO_HAVE("auxiliariesBeDoHave", "Auxiliary Verbs"), // be, do, have??! ("", got?), NOT modals!!! + V
    VERBFORM_COPULAR("copularVerbs", "Copular Verbs"), // be, stay, seem, etc. - CHECK the parser
    VERBFORM_ING("ingVerbForms", "'-ing' Verb Forms"), // gerund, participle, nouns 
    VERBFORM_TO_INFINITIVE("toInfinitiveForms", "'To Infinitive' Verb Forms"), // "I want toPrep do it."
    VERBFORM_EMPATHIC_DO("emphaticDo", "Empathic 'Do' Verb Form"), // "I do realize it": do/did/VBP followed by /VB
    
    MODALS("modals", "Modals"), // all
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
    
    IMPERATIVES("imperatives", "Imperatives"), // start with a Verb, often end with "!": "Do it yourself!"
    PASSIVE_VOICE("passiveVoice", "Passive Voice"),
    
    ADJECTIVE_POSITIVE("positiveAdj", "Positive Adjective"), // "nice"
    ADJECTIVE_COMPARATIVE_SHORT("comparativeAdjShort", "Comparative Adjective Short"), // "nicer"
    ADJECTIVE_SUPERLATIVE_SHORT("superlativeAdjShort", "Superlative Adjective Short"), // "nicest"
    ADJECTIVE_COMPARATIVE_LONG("comparativeAdjLong", "Comparative Adjective Long"), // "more beautiful"
    ADJECTIVE_SUPERLATIVE_LONG("superlativeAdjLong", "Superlative Adjective Long"), // "most beautiful"
    
    ADVERB_POSITIVE("positiveAdv", "Postive Adverb"), // "quickly"
    ADVERB_COMPARATIVE_SHORT("comparativeAdvShort", "Comparative Adverb Short"), // "faster"
    ADVERB_SUPERLATIVE_SHORT("superlativeAdvShort", "Superlative Adverb Short"), // "fastest"
    ADVERB_COMPARATIVE_LONG("comparativeAdvLong", "Comparative Adverb Long"), // "more quickly"
    ADVERB_SUPERLATIVE_LONG("superlativeAdvLong", "Superlative Adverb Long"), // "most quickly"
    ;

    private final String		legacyID;
    private final String		prettyName;
    
    GrammaticalConstruction(String legacyID, String prettyName) {
	this.legacyID = legacyID;
	this.prettyName = prettyName;
    }
    
    @Override
    public String toString() {
	return prettyName;
    }
    
    public String getLegacyID() {
	return this.legacyID;
    }
}
