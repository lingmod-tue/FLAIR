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
    EXISTENTIAL_THERE("Existential There"),
    THERE_IS_ARE("There, Is, Are"),
    THERE_WAS_WERE("There, Was, Were"),
    
    CONJUNCTIONS_ADVANCED("Advanced Conjunctions"),
    CONJUNCTIONS_SIMPLE("Simple Conjunctions"),
    
    PREPOSITIONS("Prepositions"),
    PREPOSITIONS_SIMPLE("Simple Prepositions"),
    PREPOSITIONS_COMPLEX("Complex Prepositions"),
    PREPOSITIONS_ADVANCED("Advanced Prepositions"),
    
    // sentence structure
    CLAUSE_SUBORDINATE("Subordinate Clause"),
    CLAUSE_RELATIVE("Relative Clause"),
    CLAUSE_RELATIVE_REDUCED("Reduced Relative Clause"),
    CLAUSE_ADVERBIAL("Adverbial Clause"),
    SENTENCE_SIMPLE("Simple Sentence"),
    SENTENCE_COMPLEX("Complex Sentence"),
    SENTENCE_COMPOUND("Compound Sentence"),
    SENTENCE_INCOMPLETE("Incomplete Sentence"),
    
    OBJECT_DIRECT("Direct Object"),	 // "give me"
    OBJECT_INDIRECT("Indirect Object"),	 // "give it toPrep me"
    
    PRONOUNS("Pronouns"),
    PRONOUNS_SUBJECTIVE("Subjective Pronouns"), // /PRP + I, you, they...
    PRONOUNS_OBJECTIVE("Objective Pronouns"), // /PRP + me, you, them...
    PRONOUNS_POSSESSIVE("Posssessive Pronouns"), // /PRP$ (my, your, their)
    PRONOUNS_POSSESSIVE_ABSOLUTE("Absolute Possessive Pronouns"), // /JJ or PRP... (mine, yours, theirs)
    PRONOUNS_REFLEXIVE("Reflexive Pronouns"), // /PRP + myself, themselves, etc.
    
    // quantifiers
    DETERMINER_SOME("Determiner 'Some'"),
    DETERMINER_ANY("Determiner 'Any'"),
    DETERMINER_MUCH("Determiner 'Much'"),
    DETERMINER_MANY("Determeiner 'Many'"),
    DETERMINER_A_LOT_OF("Determiner 'A lot of'"),
    
    ARTICLES("Articles"),
    ARTICLE_THE("Article 'The'"),
    ARTICLE_A("Article 'A'"),
    ARTICLE_AN("Article 'An"),
    
    PLURAL_REGULAR("Regular Plural"),
    PLURAL_IRREGULAR("Irregular Plural"),
    NOUNFORMS_ING("'-ing' Noun Forms"),
    
    NEGATION_ALL("All Negation"), // nobody, nowhere, etc.
    NEGATION_PARTIAL("Partial Negation"), // rarely, barely, seldom, hardly, scarcely
    NEGATION_NO_NOT_NEVER("No, Not, Never"),
    NEGATION_NT("'-nt' Negation"),
    NEGATION_NOT("'Not' Negation"),
    
    QUESTIONS_DIRECT("Direct Questions"),
    QUESTIONS_INDIRECT("Indirect Questions"),
    QUESTIONS_YESNO("'Yes/No' Questions"), // direct: "Are you ok?"
    QUESTIONS_WH("'Wh-' Questions"), // direct: "What do you do?"
    QUESTIONS_TO_BE("'To Be' Questions"), // direct: "What's this?"
    QUESTIONS_TO_DO("'To Do' Questions"), // direct: "What do you do?"
    QUESTIONS_TO_HAVE("'To Have' Questions"), // direct: "What have you done?"
    QUESTIONS_MODAL("Modal Questions"), // direct: "Should I go?", "What should I do?"
    QUESTIONS_WHAT("'What' Questions"),
    QUESTIONS_WHO("'Who' Questions"),
    QUESTIONS_HOW("'How' Questions"),
    QUESTIONS_WHY("'Why' Questions"),
    QUESTIONS_WHERE("'Where' Questions"),
    QUESTIONS_WHEN("'When' Questions"),
    QUESTIONS_WHOSE("'Whose' Questions"),
    QUESTIONS_WHOM("'Whom' Questions"),
    QUESTIONS_WHICH("'Which' Questions"),
    QUESTIONS_TAG("Question Tag"), // ", isn't it?"
    
    // conditionals - check first, before tenses
    CONDITIONALS("Conditionals"),
    CONDITIONALS_REAL("Real Conditionals"),
    CONDITIONALS_UNREAL("Unreal Conditionals"),
    
    // tenses - only if not conditional
    TENSE_PRESENT_SIMPLE("Present Simple"),
    TENSE_PRESENT_PROGRESSIVE("Present Progressive"),
    TENSE_PRESENT_PERFECT("Present Perfect"),
    TENSE_PRESENT_PERFECT_PROGRESSIVE("Present Perfect Progressive"),
    TENSE_PAST_SIMPLE("Past Simple"),
    TENSE_PAST_PROGRESSIVE("Past Progressive"),
    TENSE_PAST_PERFECT("Past Perfect"),
    TENSE_PAST_PERFECT_PROGRESSIVE("Past Perfect Progressive"),
    TENSE_FUTURE_SIMPLE("Future Simple"),
    TENSE_FUTURE_PROGRESSIVE("Future Progressive"),
    TENSE_FUTURE_PERFECT("Future Perfect"),
    TENSE_FUTURE_PERFECT_PROGRESSIVE("Future Perfect Progressive"),
    
    ASPECT_SIMPLE("Simple Aspect"),
    ASPECT_PROGRESSIVE("Progressive Aspect"),
    ASPECT_PERFECT("Perfect Aspect"),
    ASPECT_PERFECT_PROGRESSIVE("Perfect Progressive Aspect"),
    
    TIME_PRESENT("Present Time"),
    TIME_PAST("Past Time"),
    TIME_FUTURE("Future Time"),
    
    VERBCONST_GOING_TO("'Going To' Verb"),
    VERBCONST_USED_TO("'Used To' Verb"),
    
    VERBFORM_SHORT("Short Verb Forms"), // 's, 're, 'm, 's, 've, 'd??!
    VERBFORM_LONG("Long Verb Forms"), // is, are, am, has, have, had??!
    VERBFORM_AUXILIARIES_BE_DO_HAVE("Auxiliary Verbs"), // be, do, have??! (got?), NOT modals!!! + V
    VERBFORM_COPULAR("Copular Verbs"), // be, stay, seem, etc. - CHECK the parser
    VERBFORM_ING("'-ing' Verb Forms"), // gerund, participle, nouns 
    VERBFORM_TO_INFINITIVE("'To Infinitive' Verb Forms"), // "I want toPrep do it."
    VERBFORM_EMPATHIC_DO("Empathic 'Do' Verb Form"), // "I do realize it": do/did/VBP followed by /VB
    
    MODALS("Modals"), // all
    MODALS_SIMPLE("Simple Modals"), // can, must, need, may
    MODALS_ADVANCED("Advanced Modals"), // the others
    MODALS_CAN("'Can' Modal"), // Klasse 6
    MODALS_MUST("'Must' Modal"), // Klasse 6
    MODALS_NEED("'Need' Modal"), // Klasse 6
    MODALS_MAY("'May' Modal"), // Klasse 6
    MODALS_COULD("'Could' Modal"), // Klasse 10
    MODALS_MIGHT("'Might' Modal"), // Klasse 10
    MODALS_OUGHT("'Ought' Modal"), // Klasse 10
    MODALS_ABLE("'Able' Modal"), // Klasse 10 (annotated as JJ)
    MODALS_HAVE_TO("'Have To' Modal"), // ??
    
    VERBS_IRREGULAR("Irregular Verbs"), // past tense or past participle not ending with -ed
    VERBS_REGULAR("Regular Verbs"), // past tense or past participle ending with -ed
    VERBS_PHRASAL("Phrasal Verbs"),// phrasal verbs (& verbs with prepositions: look atPrep)
    
    IMPERATIVES("Imperatives"), // start with a Verb, often end with "!": "Do it yourself!"
    PASSIVE_VOICE("Passive Voice"),
    
    ADJECTIVE_POSITIVE("Positive Adjective"), // "nice"
    ADJECTIVE_COMPARATIVE_SHORT("Comparative Adjective Short"), // "nicer"
    ADJECTIVE_SUPERLATIVE_SHORT("Superlative Adjective Short"), // "nicest"
    ADJECTIVE_COMPARATIVE_LONG("Comparative Adjective Long"), // "more beautiful"
    ADJECTIVE_SUPERLATIVE_LONG("Superlative Adjective Long"), // "most beautiful"
    
    ADVERB_POSITIVE("Postive Adverb"), // "quickly"
    ADVERB_COMPARATIVE_SHORT("Comparative Adverb Short"), // "faster"
    ADVERB_SUPERLATIVE_SHORT("Superlative Adverb Short"), // "fastest"
    ADVERB_COMPARATIVE_LONG("Comparative Adverb Long"), // "more quickly"
    ADVERB_SUPERLATIVE_LONG("Superlative Adverb Long"), // "most quickly"
    ;

    private final String		prettyName;
    
    GrammaticalConstruction(String prettyName) {
	this.prettyName = prettyName;
    }
    
    @Override
    public String toString() {
	return prettyName;
    }
}
