
package com.flair.server.grammar;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Stable constants for the English language grammar
 */
public class EnglishGrammaticalConstants {
	public static final Set<String> QUESTION_WORDS = new HashSet<>(Arrays.asList("what", "who", "how", "why", "where", "when", "whose", "whom", "which"));

	public static final Set<String> SIMPLE_PREPOSITIONS = new HashSet<>(Arrays.asList("in", "at", "on", "with", "after", "to", "for", "from", "of", "as", "by"));

	public static final Set<String> NEGATION = new HashSet<>(Arrays.asList("neither", "nobody", "none", "nothing", "nor", "nowhere")); // "never" is counted along with "n't" and "not" in neg() dependency , "neither...nor" will be counted as 2 negations
	public static final Set<String> PARTIAL_NEGATION = new HashSet<>(Arrays.asList("hardly", "scarcely", "rarely", "seldom", "barely"));

	public static final Set<String> ING_NOUNS = new HashSet<>(Arrays.asList("thing", "something", "anything", "nothing", "morning", "evening", "everything", "spring", "string", "swing", "darling", "ceiling", "clothing", "sterling", "earring", "viking", "fling")); // longer than 5 characters

	// around 40 advanced conjunctions
	public static final Set<String> ADVANCED_CONJUNCTIONS = new HashSet<>(Arrays.asList("finally", "nor", "yet", "though", "although", "if", "while", "unless", "until", "lest", "whether", "wheras", "once", "since", "till", "until", "whenever", "wherever", "besides", "further", "furthermore", "indeed", "likewise", "incidentally", "moreover", "however", "nevertheless", "nonetheless", "still", "conversely", "instead", "otherwise", "accordingly", "namely", "consequently", "hence", "thus", "meanwhile", "therefore"));
	public static final Set<String> SIMPLE_CONJUNCTIONS = new HashSet<>(Arrays.asList("and", "or", "but", "because", "so"));


	public static final Set<String> OBJECTIVE_PRONOUNS = new HashSet<>(Arrays.asList("me", "you", "him", "her", "them", "us", "it"));
	public static final Set<String> SUBJECTIVE_PRONOUNS = new HashSet<>(Arrays.asList("I", "you", "he", "she", "they", "we", "it"));
	public static final Set<String> POSSESSIVE_PRONOUNS = new HashSet<>(Arrays.asList("my", "your", "his", "her", "their", "our", "its"));
	public static final Set<String> POSSESSIVE_ABSOLUTE_PRONOUNS = new HashSet<>(Arrays.asList("mine", "yours", "hers", "theirs", "ours")); // his and its omitted
	public static final Set<String> REFLEXIVE_PRONOUNS = new HashSet<>(Arrays.asList("myself", "yourself", "himself", "herself", "themselves", "ourselves", "itself", "yourselves"));
	public static final Set<String> RELATIVE_PRONOUNS = new HashSet<>(Arrays.asList("who", "which", "that", "whose", "whom"));

	public static final Set<String> DEMONSTRATIVES = new HashSet<>(Arrays.asList("this", "that", "these", "those", "here", "there"));
	public static final Set<String> CONTRACTIONS = new HashSet<>(Arrays.asList("n't", "'ll", "'s", "'m", "'re", "'ve", "'d"));
	public static final Set<String> ARTICLES = new HashSet<>(Arrays.asList("a", "an", "the"));
}
