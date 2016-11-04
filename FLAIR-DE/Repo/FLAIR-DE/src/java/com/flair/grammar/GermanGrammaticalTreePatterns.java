/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.grammar;

import edu.stanford.nlp.trees.tregex.TregexPattern;

/**
 * This class contains TRegEx patterns for constituency trees which identify
 * specific grammatical / syntactic patterns for German.
 *
 * Patterns are taken from Master thesis Hancke 2013 and BA thesis Weiss 2015
 *
 * @author zweiss
 */
public class GermanGrammaticalTreePatterns {

    //private static final String str = "";
    //public static final TregexPattern pattern = TregexPattern.compile(str);
    // find any verb
    private static final String STR_VERB = "/^V.(IMP|FIN|INF|PP)$/";
    public static TregexPattern patternVerb = TregexPattern.compile(STR_VERB);

    // find any noun or pronoun
    private static final String STR_NOUN_PRONOUN_ANSWER_PARTICLE = "/^(NN|NE|PDS|PIS|PPER|PPOSS|PRELS|PRF|PWS|PWAV|PAV|PTKANT)$/";
    public static TregexPattern patternNounOrPronounOrAnswerParticle = TregexPattern.compile(STR_NOUN_PRONOUN_ANSWER_PARTICLE);

    // count all S tags to get all clauses in a sentence exatcly once
    private static final String STR_CLAUSES_PER_SENTENCE = "(@S)";
    public static TregexPattern patternClausesPerSentence = TregexPattern.compile(STR_CLAUSES_PER_SENTENCE);

    // relative clauses all start with relative pronoun, which is tagged starting with PREL
    private static final String STR_RELATIVE_CLAUSES_D_PRONOUNS = "(/^PREL(S|AT)$/)";
    public static TregexPattern patternDPronounRelativeClauses = TregexPattern.compile(STR_RELATIVE_CLAUSES_D_PRONOUNS);
    // relative clauses might start with an indefinite pronoun: Wer/Der mit dem Wolf tanzt, ...
    // has to be governed by an AP or NP to discriminate against questions or comparatives
    private static final String STR_RELATIVE_CLAUSES_W_PRONOUNS = "(/^(A|N)P$/ < /^PW(AT|S|AV)$/)";
    public static TregexPattern patternWPronounRelativeClauses = TregexPattern.compile(STR_RELATIVE_CLAUSES_W_PRONOUNS);

    // any clause dominating a subordinating conjunction which is not "dass" or "ob"
    // @TODO check this pattern for robustness
    private static final String STR_ADVERBIAL_CLAUSES = "/^KOU(S|I)$/ !< /^(ob|dass)$/";
    public static TregexPattern patternAdverbialClauses = TregexPattern.compile(STR_ADVERBIAL_CLAUSES);

    // 'dass' clauses
    private static final String STR_DASS_CLAUSES = "/^KOU(S|I)$/ < /^dass$/";
    public static TregexPattern patternDassClauses = TregexPattern.compile(STR_DASS_CLAUSES);

    // 'ob' clauses
    private static final String STR_INDIRECT_QUESTIONS = "/^KOU(S|I)$/ < /^ob$/ !$, @KOKOM";
    public static TregexPattern patternObClauses = TregexPattern.compile(STR_INDIRECT_QUESTIONS);

    // tree contains subordinating construction, i.e. subordination (KOUS/KOUI) 
    private static final String STR_CLAUSES_WITH_SUBORDINATING_CONJUNCTIONS = "/^KOU(S|I)$/";
    public static TregexPattern patternClausalSubordination = TregexPattern.compile(STR_CLAUSES_WITH_SUBORDINATING_CONJUNCTIONS);

    // Tree-based: clause is sibling to other clause, when subject shared, second clause labeld as VP, i.e. include it here
    private static final String STR_CLAUSAL_COORDINATION_SYN = "(/^(CS|S|VP)$/ $ /^(CS|S|VP)$/)";
    public static TregexPattern patternClausalCoordinationSyn = TregexPattern.compile(STR_CLAUSAL_COORDINATION_SYN);
    // POS-based: only syndetic coordination with KON/KOKON POS tag
    private static final String STR_CLAUSAL_COORDINATION_POS = "/^KO(N|KOM)/ > @CS";
    public static TregexPattern patternClausalCoordinationPOS = TregexPattern.compile(STR_CLAUSAL_COORDINATION_POS);

    // clausal coordination concerns either matrix clause coordinations or suboridnate clause coordinations
    private static final String STR_COORDINATED_SUBORDINATE_CLAUSES = "(/^(CS|S|VP)$/ <-  /^V.(FIN|IMP)$/) $  (/^(CS|S|VP)$/ <-  /^V.(FIN|IMP)$/)";
    public static TregexPattern patternCoordinatedSubordinateClauses = TregexPattern.compile(STR_COORDINATED_SUBORDINATE_CLAUSES);
    private static final String STR_COORDINATED_MAIN_CLAUSES = "(/^(S|VP)$/ !<-  /^V.(FIN|IMP)$/) $ (/^(S|VP)$/ !<-  /^V.(FIN|IMP)$/)";
    public static TregexPattern patternCoordinatedMainClauses = TregexPattern.compile(STR_COORDINATED_MAIN_CLAUSES);

    // imperatives have a V.IMP label
    private static final String STR_IMPERATIVES = "(/^V(A|V|M)IMP$/)";
    public static TregexPattern patternImperatives = TregexPattern.compile(STR_IMPERATIVES);

    /*
    * Periphrastic constructions
     */
    private static final String STR_WERDEN_PRESENT = "/^[Ww]((u?e|ü)rde(n|(s)?t)?|(ir(st|d)))$/";
    private static final String STR_VERBAL_PART2 = "(/^VP/ < /^V[AMV]PP$/)";
    private static final String STR_PAST = "> (/.+/ $ " + STR_VERBAL_PART2 + " !$ (/.+/ << " + STR_WERDEN_PRESENT + ") !> (/.+/ $ (/.+/ << " + STR_WERDEN_PRESENT + ")))";

    // present perfect: haben
    private static final String STR_PERFECT_HABEN = "/^[Hh]a(b(e(n|(s)?t)?|t)|(s)?t)$/ " + STR_PAST;
    public static TregexPattern patternPerfectHaben = TregexPattern.compile(STR_PERFECT_HABEN);
    // present perfect: sein
    private static final String STR_PERFECT_SEIN = "/^(([Bb]i(st|n))|([Ii]st)|([Ss](ind|ei((e)?[td]|en|st)?)))$/ " + STR_PAST;
    public static TregexPattern patternPerfectSein = TregexPattern.compile(STR_PERFECT_SEIN);

    // past perfect: haben
    private static final String STR_PLUSQUAMPERFECT_HABEN = "/^[Hh](a(e)?|ä)tte((st)|n|t)?$/ " + STR_PAST;
    public static TregexPattern patternPlusquamperfectHaben = TregexPattern.compile(STR_PLUSQUAMPERFECT_HABEN);
    // past perfect: sein
    private static final String STR_PLUSQUAMPERFECT_SEIN = "/^[Ww]((ar((s)?t|en)?)|((ae|ä)r((e((s)?t|n)?)|(s)?t)?))$/ " + STR_PAST;
    public static TregexPattern patternPlusquamperfectSein = TregexPattern.compile(STR_PLUSQUAMPERFECT_SEIN);

    // future 1
    private static final String STR_FUTURE1 = STR_WERDEN_PRESENT + " > (/.+/ $ (/^VP/ < /^V.INF$/ !< " + STR_VERBAL_PART2 + ") | $ (/^VP/ < (/^V.INF$/ < werden) < " + STR_VERBAL_PART2 + "))";
    public static TregexPattern patternFuture1 = TregexPattern.compile(STR_FUTURE1);
    // future 2
    private static final String STR_FUTURE2 = STR_WERDEN_PRESENT + " > (/.+/ $ (/^VP/ < (/^V.INF$/ !< werden) < " + STR_VERBAL_PART2 + ") | $ ((/^V.INF$/ !< werden) $ " + STR_VERBAL_PART2 + "))";
    public static TregexPattern patternFuture2 = TregexPattern.compile(STR_FUTURE2);

    // passive: werden
    private static final String STR_PASSIVE_WERDEN = "/^[Ww](((ü|u(e)?)rde(n|(s)?t)?|ir(d|st)|erde(n|(s)?t)?)|orden)$/ > (/.+/ $ (/^VP/ < (/^V[AMV]PP$/ !< worden)))";
    public static TregexPattern patternPassiveWerden = TregexPattern.compile(STR_PASSIVE_WERDEN);
    // passive: sein
    private static final String STR_PASSIVE_SEIN = "/^([Ss](ei(n|d|st|e(t|n(d)?))?|ind)|[Bb]i(n|st)|[Ii]st|[Ww]ar((s)?t|en)?|[Ww](ä|ae)re((s)?t|n)?|[Gg]ewesen)$/ > (/.+/ $ (/^VP/ < (/^V[AMV]PP$/ !< worden)))";
    public static TregexPattern patternPassiveSein = TregexPattern.compile(STR_PASSIVE_SEIN);

    // verb bracket
    private static final String STR_VERB_BRACKET = "S < (/^V(M|V|A)(FIN|IMP|INF|PP)$/ $.. /^V((P|Z)|(M|A|V)(FIN|INF|IMP|PP))$/)";
    public static TregexPattern patternVerbBracket = TregexPattern.compile(STR_VERB_BRACKET);

    /*
    * Questions
     */
    // tag questions are V2 clauses ending in a question mark. The second to last element is some phrase, the third to last element has to be a comma
    private static final String STR_TAG_QUESTION = "S <2 /^V.(INF|IMP|FIN|PP)$/ <-1 (/\\$\\./ < /^\\?$/)  <-3 (/\\$\\,/ < /^,$/)";
    public static TregexPattern patternTagQuestion = TregexPattern.compile(STR_TAG_QUESTION);

    // question mark
    private static final String STR_QUESTION_MARK = "(/\\$\\./ < /^\\?$/)";
    public static TregexPattern patternQuestionMark = TregexPattern.compile(STR_QUESTION_MARK);

    // indirect question
    private static final String STR_INDIRECT_QUESTION = "(/^KOU(S|I)$/ < /^ob$/)";
    public static TregexPattern patternIndirectQuestion = TregexPattern.compile(STR_INDIRECT_QUESTION);

    /*
    * Complex Noun Phrases
     */
    // helper
    private static final String ATTR_PART2 = "/(^([gb]e|[vz]?er|ent|emp|hinter).+[nt]|iert)e[nrs]?$/ >> /^C?[NP]P/";
    private static final String ATTR_PART1 = "/end(e[nrs]?)?$/";
    private static final String NN_NE_PRONOUN = "/^(N[NE])|(P(((D|I|W|REL)S)|RF|PER))$/";
    //private static final String ADJ_ADV_CARD = "/^(AD(J[AD]|V)|CARD)$/";
    private static final String ADJ = "/^ADJ/";

    // participle 1 attributes @TODO merge these patterns
    private static final String STR_PARTICIPLE_1_ATTRIBUTE_A = ADJ+" < "+ATTR_PART1+" $.. "+NN_NE_PRONOUN;
    public static TregexPattern patternParticiple1AttributeA = TregexPattern.compile(STR_PARTICIPLE_1_ATTRIBUTE_A);
    private static final String STR_PARTICIPLE_1_ATTRIBUTE_B = ADJ+" < "+ATTR_PART1+" >> (/^C?AP/ $.. "+NN_NE_PRONOUN+")";
    public static TregexPattern patternParticiple1AttributeB = TregexPattern.compile(STR_PARTICIPLE_1_ATTRIBUTE_B);
    
    // participle 2 attributes @TODO merge these patterns
    private static final String STR_PARTICIPLE_2_ATTRIBUTE_A = ADJ+" < "+ATTR_PART2+" $.. "+NN_NE_PRONOUN;
    public static TregexPattern patternParticiple2Attribute_A = TregexPattern.compile(STR_PARTICIPLE_2_ATTRIBUTE_A);
    private static final String STR_PARTICIPLE_2_ATTRIBUTE_B = ADJ+" < "+ATTR_PART2+" >> (/^C?AP/ $.. "+NN_NE_PRONOUN+")";
    public static TregexPattern patternParticiple2Attribute_B = TregexPattern.compile(STR_PARTICIPLE_2_ATTRIBUTE_B);

    // adjective attributes @TODO merge these patterns
    private static final String STR_ADJECTIVE_ATTRIBUTES_A = ADJ+" !< "+ATTR_PART2+" !< "+ATTR_PART1+" $.. "+NN_NE_PRONOUN;
    public static final TregexPattern patternAdjectiveAttributes_A = TregexPattern.compile(STR_ADJECTIVE_ATTRIBUTES_A);
    private static final String STR_ADJECTIVE_ATTRIBUTES_B = ADJ +" !< "+ATTR_PART2+" !< "+ATTR_PART1+" $.. "+NN_NE_PRONOUN;
    public static final TregexPattern patternAdjectiveAttributes_B = TregexPattern.compile(STR_ADJECTIVE_ATTRIBUTES_B);

    // prepositional attributes @TODO merge these patterns
    private static final String STR_PREPOSITIONAL_ATTRIBUTES = "(/^C?(NP|PP|ADV|MPN)/ !< /^KOKOM/) $, "+NN_NE_PRONOUN;
    public static final TregexPattern patternPrepositionalAttributes = TregexPattern.compile(STR_PREPOSITIONAL_ATTRIBUTES);

}
