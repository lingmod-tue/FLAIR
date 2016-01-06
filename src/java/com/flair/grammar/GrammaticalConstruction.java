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
    EXISTENTIAL_THERE,
    THERE_IS_ARE,
    THERE_WAS_WERE,
    
    CONJUNCTIONS_ADVANCED,
    CONJUNCTIONS_SIMPLE,
    
    PREPOSITIONS,
    PREPOSITIONS_SIMPLE,
    PREPOSITIONS_COMPLEX,
    PREPOSITIONS_ADVANCED,
    
    // sentence structure
    CLAUSE_SUBORDINATE,
    CLAUSE_RELATIVE,
    CLAUSE_RELATIVE_REDUCED,
    CLAUSE_ADVERBIAL,
    SENTENCE_SIMPLE,
    SENTENCE_COMPLEX,
    SENTENCE_COMPOUND,
    SENTENCE_INCOMPLETE,
    
    OBJECT_DIRECT,	 // "give me"
    OBJECT_INDIRECT,	 // "give it toPrep me"
    
    PRONOUNS,
    PRONOUNS_SUBJECTIVE, // /PRP + I, you, they...
    PRONOUNS_OBJECTIVE, // /PRP + me, you, them...
    PRONOUNS_POSSESSIVE, // /PRP$ (my, your, their)
    PRONOUNS_POSSESSIVE_ABSOLUTE, // /JJ or PRP... (mine, yours, theirs)
    PRONOUNS_REFLEXIVE, // /PRP + myself, themselves, etc.
    
    // quantifiers
    DETERMINER_SOME,
    DETERMINER_ANY,
    DETERMINER_MUCH,
    DETERMINER_MANY,
    DETERMINER_A_LOT_OF,
    
    ARTICLES,
    ARTICLE_THE,
    ARTICLE_A,
    ARTICLE_AN,
    
    PLURAL_REGULAR,
    PLURAL_IRREGULAR,
    NOUNFORMS_ING,
    
    NEGATION_ALL, // nobody, nowhere, etc.
    NEGATION_PARTIAL, // rarely, barely, seldom, hardly, scarcely
    NEGATION_NO_NOT_NEVER,
    NEGATION_NT,
    NEGATION_NOT,
    
    QUESTIONS_DIRECT,
    QUESTIONS_INDIRECT,
    QUESTIONS_YESNO, // direct: "Are you ok?"
    QUESTIONS_WH, // direct: "What do you do?"
    QUESTIONS_TO_BE, // direct: "What's this?"
    QUESTIONS_TO_DO, // direct: "What do you do?"
    QUESTIONS_TO_HAVE, // direct: "What have you done?"
    QUESTIONS_MODAL, // direct: "Should I go?", "What should I do?"
    QUESTIONS_WHAT,
    QUESTIONS_WHO,
    QUESTIONS_HOW,
    QUESTIONS_WHY,
    QUESTIONS_WHERE,
    QUESTIONS_WHEN,
    QUESTIONS_WHOSE,
    QUESTIONS_WHOM,
    QUESTIONS_WHICH,
    QUESTIONS_TAG, // ", isn't it?"
    
    // conditionals - check first, before tenses
    CONDITIONALS,
    CONDITIONALS_REAL,
    CONDITIONALS_UNREAL,
    
    // tenses - only if not conditional
    TENSE_PRESENT_SIMPLE,
    TENSE_PRESENT_PROGRESSIVE,
    TENSE_PRESENT_PERFECT,
    TENSE_PRESENT_PERFECT_PROGRESSIVE,
    TENSE_PAST_SIMPLE,
    TENSE_PAST_PROGRESSIVE,
    TENSE_PAST_PERFECT,
    TENSE_PAST_PERFECT_PROGRESSIVE,
    TENSE_FUTURE_SIMPLE,
    TENSE_FUTURE_PROGRESSIVE,
    TENSE_FUTURE_PERFECT,
    TENSE_FUTURE_PERFECT_PROGRESSIVE,
    
    ASPECT_SIMPLE,
    ASPECT_PROGRESSIVE,
    ASPECT_PERFECT,
    ASPECT_PERFECT_PROGRESSIVE,
    
    TIME_PRESENT,
    TIME_PAST,
    TIME_FUTURE,
    
    VERBCONST_GOING_TO,
    VERBCONST_USED_TO,
    
    VERBFORM_SHORT, // 's, 're, 'm, 's, 've, 'd??!
    VERBFORM_LONG, // is, are, am, has, have, had??!
    VERBFORM_AUXILIARIES_BE_DO_HAVE, // be, do, have??! (got?), NOT modals!!! + V
    VERBFORM_COPULAR, // be, stay, seem, etc. - CHECK the parser
    VERBFORM_ING, // gerund, participle, nouns 
    VERBFORM_TO_INFINITIVE, // "I want toPrep do it."
    VERBFORM_EMPATHIC_DO, // "I do realize it": do/did/VBP followed by /VB
    
    MODALS, // all
    MODALS_SIMPLE, // can, must, need, may
    MODALS_ADVANCED, // the others
    MODALS_CAN, // Klasse 6
    MODALS_MUST, // Klasse 6
    MODALS_NEED, // Klasse 6
    MODALS_MAY, // Klasse 6
    MODALS_COULD, // Klasse 10
    MODALS_MIGHT, // Klasse 10
    MODALS_OUGHT, // Klasse 10
    MODALS_ABLE, // Klasse 10 (annotated as JJ)
    MODALS_HAVE_TO, // ??
    
    VERBS_IRREGULAR, // past tense or past participle not ending with -ed
    VERBS_REGULAR, // past tense or past participle ending with -ed
    VERBS_PHRASAL,// phrasal verbs (& verbs with prepositions: look atPrep)
    
    IMPERATIVES, // start with a Verb, often end with "!": "Do it yourself!"
    PASSIVE_VOICE,
    
    ADJECTIVE_POSITIVE, // "nice"
    ADJECTIVE_COMPARATIVE_SHORT, // "nicer"
    ADJECTIVE_SUPERLATIVE_SHORT, // "nicest"
    ADJECTIVE_COMPARATIVE_LONG, // "more beautiful"
    ADJECTIVE_SUPERLATIVE_LONG, // "most beautiful"
    
    ADVERB_POSITIVE, // "quickly"
    ADVERB_COMPARATIVE_SHORT, // "faster"
    ADVERB_SUPERLATIVE_SHORT, // "fastest"
    ADVERB_COMPARATIVE_LONG, // "more quickly"
    ADVERB_SUPERLATIVE_LONG, // "most quickly"

}
