/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.grammar;

import java.util.Arrays;
import java.util.List;

/**
 * Stable constants for the English language grammar
 * @author shadeMe
 */
public class EnglishGrammaticalConstants
{
    public static final List<String> QUESTION_WORDS = Arrays.asList("was", "wer","wie","warum","wo","wann","wessen","wem","was","woher","welchen","weshalb","wozu","worauf");
    
    public static final List<String> SIMPLE_PREPOSITIONS = Arrays.asList("in", "auf", "mit", "nach", "zu");
    
    public static final List<String> NEGATION = Arrays.asList("nein", "nö", "nee", "weder", "niemand", "keiner", "nichts", "nirgends", "niemals", "nicht", "nie", "keine", "keinen", "keinem", "kein", "keines"); // "never" is counted along with "n't" and "not" in neg() dependency , "neither...nor" will be counted as 2 negations
    public static final List<String> PARTIAL_NEGATION = Arrays.asList("kaum", "selten", "vereizelt", "spärlich", "dürftig", "knapp");
    
    public static final List<String> ING_NOUNS = Arrays.asList("thing", "something", "anything", "nothing", "morning", "evening", "everything", "spring", "string", "swing", "darling", "ceiling", "clothing", "sterling", "earring", "viking", "fling"); // longer than 5 characters
    
    // around 40 advanced conjunctions
    public static final List<String> ADVANCED_CONJUNCTIONS = Arrays.asList("schließlich", "noch","obwohl","aber","wenn","während","außer","bis","falls","ob","wohingegen","sobald","seit","bis","to","wenn","wo","außerdem","weiterhin","zudem","tatsächlich","ebenso", "ebnfalls","übrigens","außerdem","ferner","jedoch","aber","hingegen","dennoch","trotzdem","dennoch","doch","dennoch","hingegen","stattdessen","sonst","ansonsten","andernfalls","dementsprechend","demnach", "demgemäß","folglich","demzufolge","nämlich","folglich","somit","daher","dadurch","hierdurch","inzwischen","mittlerweile","derweil","unterdess","indessen","einstweilen","derweilen","darum");
    public static final List<String> SIMPLE_CONJUNCTIONS = Arrays.asList("und", "oder", "aber", "weil", "so");
    
    
    public static final List<String> OBJECTIVE_PRONOUNS = Arrays.asList("mich", "dich", "ihn", "uns", "euch");
    public static final List<String> INDIRECT_OBJECTIVE_PRONOUNS = Arrays.asList("mir", "dir", "ihm", "uns", "euch");
    public static final List<String> SUBJECTIVE_PRONOUNS = Arrays.asList("ich", "du", "er", "wir", "ihr");
    public static final List<String> POSSESSIVE_PRONOUNS = Arrays.asList("mein", "dein", "sein", "ihr", "euer", "unser");
    public static final List<String> POSSESSIVE_ABSOLUTE_PRONOUNS = Arrays.asList("meins", "deins", "seins", "ihrs", "euers", "unsers"); // his and its omitted
    public static final List<String> REFLEXIVE_PRONOUNS = Arrays.asList("sich");
    
    public static final List<String> SOME_DETERMINERS = Arrays.asList("bisschen", "etwas");
    
    
}
