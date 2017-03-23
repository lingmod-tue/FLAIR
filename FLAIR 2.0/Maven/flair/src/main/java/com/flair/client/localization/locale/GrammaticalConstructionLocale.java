package com.flair.client.localization.locale;

import com.flair.client.localization.SimpleLocale;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;

/*
 * Locale data for grammatical constructions
 * Strings that are the same as the enum are not used in the client
 */
public class GrammaticalConstructionLocale extends SimpleLocale
{
	private static final String			DESC_PATH_SUFFIX = "-path";
	
	private static final GrammaticalConstructionLocale		INSTANCE = new GrammaticalConstructionLocale();
	public static GrammaticalConstructionLocale get() {
		return INSTANCE;
	}
	
	private GrammaticalConstructionLocale() {
		super();
	}
	
	private void putString(Language lang, GrammaticalConstruction gram, String name, String path) 
	{
		getLocalizationData(lang).put(gram.toString(), name);
		getLocalizationData(lang).put(gram.toString() + DESC_PATH_SUFFIX, path);
	}
	
	@Override
	public void init() 
	{
		for (GrammaticalConstruction itr : GrammaticalConstruction.values())
		{
			switch (itr)
			{
			case ADJECTIVE_COMPARATIVE_LONG:
				putString(Language.ENGLISH, itr, "comparative long (more difficult)", "adjectives > comparative long");
				putString(Language.GERMAN, itr, "ADJECTIVE_COMPARATIVE_LONG", "ADJECTIVE_COMPARATIVE_LONG");
				break;
			case ADJECTIVE_COMPARATIVE_SHORT:
				putString(Language.ENGLISH, itr, "comparative short (nicer)", "adjectives > comparative short");
				putString(Language.GERMAN, itr, "ADJECTIVE_COMPARATIVE_SHORT", "ADJECTIVE_COMPARATIVE_SHORT");
				break;
			case ADJECTIVE_POSITIVE:
				putString(Language.ENGLISH, itr, "positive (nice)", "adjectives > positive");
				putString(Language.GERMAN, itr, "Adjektive", "Adjektiven, Adverben und Zahlworte > Adjektive");
				break;
			case ADJECTIVE_SUPERLATIVE_LONG:
				putString(Language.ENGLISH, itr, "superlative long (most difficut)", "adjectives > superlative long");
				putString(Language.GERMAN, itr, "ADJECTIVE_SUPERLATIVE_LONG", "ADJECTIVE_SUPERLATIVE_LONG");
				break;
			case ADJECTIVE_SUPERLATIVE_SHORT:
				putString(Language.ENGLISH, itr, "superlative short (nicest)", "adjectives > superlative short");
				putString(Language.GERMAN, itr, "ADJECTIVE_SUPERLATIVE_SHORT", "ADJECTIVE_SUPERLATIVE_SHORT");
				break;
			case ADVERB_COMPARATIVE_LONG:
				putString(Language.ENGLISH, itr, "comparative long (more easily)", "adverbs > comparative long");
				putString(Language.GERMAN, itr, "ADVERB_COMPARATIVE_LONG", "ADVERB_COMPARATIVE_LONG");
				break;
			case ADVERB_COMPARATIVE_SHORT:
				putString(Language.ENGLISH, itr, "comparative short (faster)", "adverbs > comparative short");
				putString(Language.GERMAN, itr, "ADVERB_COMPARATIVE_SHORT", "ADVERB_COMPARATIVE_SHORT");
				break;
			case ADVERB_POSITIVE:
				putString(Language.ENGLISH, itr, "positive (fast)", "adverbs > positive");
				putString(Language.GERMAN, itr, "Adverben", "adjektiven, adverben und zahlworte > adverben");
				break;
			case ADVERB_SUPERLATIVE_LONG:
				putString(Language.ENGLISH, itr, "superlative long (most)", "adverbs > superlative long");
				putString(Language.GERMAN, itr, "ADVERB_SUPERLATIVE_LONG", "ADVERB_SUPERLATIVE_LONG");
				break;
			case ADVERB_SUPERLATIVE_SHORT:
				putString(Language.ENGLISH, itr, "superlative short (fastest)", "adverbs > superlative short");
				putString(Language.GERMAN, itr, "ADVERB_SUPERLATIVE_SHORT", "ADVERB_SUPERLATIVE_SHORT");
				break;
			case ARTICLES:
				putString(Language.ENGLISH, itr, "all articles", "articles > all articles");
				putString(Language.GERMAN, itr, "alle Artikel", "artikel > alle artikel");
				break;
			case ARTICLE_A:
				putString(Language.ENGLISH, itr, "a", "articles > a");
				putString(Language.GERMAN, itr, "unbestimmte Artikel", "artikel > unbestimmte artikel");
				break;
			case ARTICLE_AN:
				putString(Language.ENGLISH, itr, "an", "articles > an");
				putString(Language.GERMAN, itr, "ARTICLE_AN", "ARTICLE_AN");
				break;
			case ARTICLE_THE:
				putString(Language.ENGLISH, itr, "the", "articles > the");
				putString(Language.GERMAN, itr, "bestimmte Artikel", "artikel > bestimmte artikel");
				break;
			case ASPECT_PERFECT:
				putString(Language.ENGLISH, itr, "perfect", "verbs > aspects > perfect");
				putString(Language.GERMAN, itr, "ASPECT_PERFECT", "ASPECT_PERFECT");
				break;
			case ASPECT_PERFECT_PROGRESSIVE:
				putString(Language.ENGLISH, itr, "perfect progressive", "verbs > aspects > perfect progressive");
				putString(Language.GERMAN, itr, "ASPECT_PERFECT_PROGRESSIVE", "ASPECT_PERFECT_PROGRESSIVE");
				break;
			case ASPECT_PROGRESSIVE:
				putString(Language.ENGLISH, itr, "progressive", "verbs > aspects > progressive");
				putString(Language.GERMAN, itr, "ASPECT_PROGRESSIVE", "ASPECT_PROGRESSIVE");
				break;
			case ASPECT_SIMPLE:
				putString(Language.ENGLISH, itr, "simple", "verbs > aspects > simple");
				putString(Language.GERMAN, itr, "ASPECT_SIMPLE", "ASPECT_SIMPLE");
				break;
			case ATTRIBUTES_ADJECTIVE:
				putString(Language.ENGLISH, itr, "ATTRIBUTES_ADJECTIVE", "ATTRIBUTES_ADJECTIVE");
				putString(Language.GERMAN, itr, "Adjektivattribut (der schwarze Kater, etc.)", "nomen > attribute > adjektivattribut");
				break;
			case ATTRIBUTES_PARTICIPLE_1:
				putString(Language.ENGLISH, itr, "ATTRIBUTES_PARTICIPLE_1", "ATTRIBUTES_PARTICIPLE_1");
				putString(Language.GERMAN, itr, "Partizipialattribut I (das rennende Kanninchen, etc.)", "nomen > attribute > partizipialattribut I");
				break;
			case ATTRIBUTES_PARTICIPLE_2:
				putString(Language.ENGLISH, itr, "ATTRIBUTES_PARTICIPLE_2", "ATTRIBUTES_PARTICIPLE_2");
				putString(Language.GERMAN, itr, "Partizipialattribut II (der gelaufene Marathon, etc.)", "nomen > attribute > partizipialattribut II");
				break;
			case ATTRIBUTES_PREPOSITION:
				putString(Language.ENGLISH, itr, "ATTRIBUTES_PREPOSITION", "ATTRIBUTES_PREPOSITION");
				putString(Language.GERMAN, itr, "Präpositionalattribut (die Zerstörung von der Stadt, etc.)", "nomen > attribute > präpositionalattribut");
				break;
			case CARDINALS:
				putString(Language.ENGLISH, itr, "CARDINALS", "CARDINALS");
				putString(Language.GERMAN, itr, "Zahlworte", "adjektive, adverben und zahlworte > zahlworte");
				break;
			case CLAUSE_ADVERBIAL:
				putString(Language.ENGLISH, itr, "adverbial", "clauses > adverbial");
				putString(Language.GERMAN, itr, "adverbial", "nebensatztypen > adverbial");
				break;
			case CLAUSE_DASS:
				putString(Language.ENGLISH, itr, "CLAUSE_DASS", "CLAUSE_DASS");
				putString(Language.GERMAN, itr, "dass", "nebensatztypen > dass");
				break;
			case CLAUSE_RELATIVE:
				putString(Language.ENGLISH, itr, "relative", "clauses > relative");
				putString(Language.GERMAN, itr, "relativ", "nebensatztypen > relativ");
				break;
			case CLAUSE_RELATIVE_REDUCED:
				putString(Language.ENGLISH, itr, "CLAUSE_RELATIVE_REDUCED", "CLAUSE_RELATIVE_REDUCED");
				putString(Language.GERMAN, itr, "CLAUSE_RELATIVE_REDUCED", "CLAUSE_RELATIVE_REDUCED");
				break;
			case CLAUSE_SUBORDINATE:
				putString(Language.ENGLISH, itr, "subordinate", "structure > subordinate");
				putString(Language.GERMAN, itr, "subordiniert", "satztypen > subordiniert");
				break;
			case CONDITIONALS:
				putString(Language.ENGLISH, itr, "CONDITIONALS", "CONDITIONALS");
				putString(Language.GERMAN, itr, "CONDITIONALS", "CONDITIONALS");
				break;
			case CONDITIONALS_REAL:
				putString(Language.ENGLISH, itr, "real conditional", "clauses > real conditional");
				putString(Language.GERMAN, itr, "CONDITIONALS_REAL", "CONDITIONALS_REAL");
				break;
			case CONDITIONALS_UNREAL:
				putString(Language.ENGLISH, itr, "unreal conditional", "clauses > unreal conditional");
				putString(Language.GERMAN, itr, "CONDITIONALS_UNREAL", "CONDITIONALS_UNREAL");
				break;
			case CONJUNCTIONS_ADVANCED:
				putString(Language.ENGLISH, itr, "advanced (therefore, until, besides, etc.)", "conjunctions > advanced");
				putString(Language.GERMAN, itr, "CONJUNCTIONS_ADVANCED", "CONJUNCTIONS_ADVANCED");
				break;
			case CONJUNCTIONS_SIMPLE:
				putString(Language.ENGLISH, itr, "simple (and, or, but, because, so)", "conjunctions > simple");
				putString(Language.GERMAN, itr, "CONJUNCTIONS_SIMPLE", "CONJUNCTIONS_SIMPLE");
				break;
			case DETERMINER_ANY:
				putString(Language.ENGLISH, itr, "any", "determiner > any");
				putString(Language.GERMAN, itr, "irgend...", "indefinite quantifizierer > irgend");
				break;
			case DETERMINER_A_LOT_OF:
				putString(Language.ENGLISH, itr, "DETERMINER_A_LOT_OF", "DETERMINER_A_LOT_OF");
				putString(Language.GERMAN, itr, "DETERMINER_A_LOT_OF", "DETERMINER_A_LOT_OF");
				break;
			case DETERMINER_MANY:
				putString(Language.ENGLISH, itr, "many", "determiner > many");
				putString(Language.GERMAN, itr, "viel, viele, ...", "indefinite quantifizierer > viel, viele");
				break;
			case DETERMINER_MUCH:
				putString(Language.ENGLISH, itr, "much", "determiner > much");
				putString(Language.GERMAN, itr, "DETERMINER_MUCH", "DETERMINER_MUCH");
				break;
			case DETERMINER_SOME:
				putString(Language.ENGLISH, itr, "some", "determiner > some");
				putString(Language.GERMAN, itr, "einige, manche, ...", "indefinite quantifizierer > einige, manche");
				break;
			case EXISTENTIAL_THERE:
				putString(Language.ENGLISH, itr, "EXISTENTIAL_THERE", "EXISTENTIAL_THERE");
				putString(Language.GERMAN, itr, "EXISTENTIAL_THERE", "EXISTENTIAL_THERE");
				break;
			case IMPERATIVES:
				putString(Language.ENGLISH, itr, "imperatives", "verbs > imperative > imperatives");
				putString(Language.GERMAN, itr, "Imperative", "verbformen > imperative");
				break;
			case MODALS:
				putString(Language.ENGLISH, itr, "MODALS", "MODALS");
				putString(Language.GERMAN, itr, "MODALS", "MODALS");
				break;
			case MODALS_ABLE:
				putString(Language.ENGLISH, itr, "MODALS_ABLE", "MODALS_ABLE");
				putString(Language.GERMAN, itr, "MODALS_ABLE", "MODALS_ABLE");
				break;
			case MODALS_ADVANCED:
				putString(Language.ENGLISH, itr, "advanced", "verbs > modals > advanced");
				putString(Language.GERMAN, itr, "MODALS_ADVANCED", "MODALS_ADVANCED");
				break;
			case MODALS_CAN:
				putString(Language.ENGLISH, itr, "MODALS_CAN", "MODALS_CAN");
				putString(Language.GERMAN, itr, "MODALS_CAN", "MODALS_CAN");
				break;
			case MODALS_COULD:
				putString(Language.ENGLISH, itr, "MODALS_COULD", "MODALS_COULD");
				putString(Language.GERMAN, itr, "MODALS_COULD", "MODALS_COULD");
				break;
			case MODALS_HAVE_TO:
				putString(Language.ENGLISH, itr, "MODALS_HAVE_TO", "MODALS_HAVE_TO");
				putString(Language.GERMAN, itr, "MODALS_HAVE_TO", "MODALS_HAVE_TO");
				break;
			case MODALS_MAY:
				putString(Language.ENGLISH, itr, "MODALS_MAY", "MODALS_MAY");
				putString(Language.GERMAN, itr, "MODALS_MAY", "MODALS_MAY");
				break;
			case MODALS_MIGHT:
				putString(Language.ENGLISH, itr, "MODALS_MIGHT", "MODALS_MIGHT");
				putString(Language.GERMAN, itr, "MODALS_MIGHT", "MODALS_MIGHT");
				break;
			case MODALS_MUST:
				putString(Language.ENGLISH, itr, "MODALS_MUST", "MODALS_MUST");
				putString(Language.GERMAN, itr, "MODALS_MUST", "MODALS_MUST");
				break;
			case MODALS_NEED:
				putString(Language.ENGLISH, itr, "MODALS_NEED", "MODALS_NEED");
				putString(Language.GERMAN, itr, "MODALS_NEED", "MODALS_NEED");
				break;
			case MODALS_OUGHT:
				putString(Language.ENGLISH, itr, "MODALS_OUGHT", "MODALS_OUGHT");
				putString(Language.GERMAN, itr, "MODALS_OUGHT", "MODALS_OUGHT");
				break;
			case MODALS_SIMPLE:
				putString(Language.ENGLISH, itr, "simple (can, must, need, may)", "verbs > modals > simple");
				putString(Language.GERMAN, itr, "MODALS_SIMPLE", "MODALS_SIMPLE");
				break;
			case NEGATION_ALL:
				putString(Language.ENGLISH, itr, "all negation (nothing, nowhere, no, etc.)", "negation > all negation");
				putString(Language.GERMAN, itr, "Negation (kein, kaum, nie, etc.)", "negation > negation");
				break;
			case NEGATION_NOT:
				putString(Language.ENGLISH, itr, "not (full form)", "negation > not");
				putString(Language.GERMAN, itr, "NEGATION_NOT", "NEGATION_NOT");
				break;
			case NEGATION_NO_NOT_NEVER:
				putString(Language.ENGLISH, itr, "no, not, never", "negation > no, not, never");
				putString(Language.GERMAN, itr, "Volle Negation (kein, nichts, nirgends, etc.)", "negation > volle negation");
				break;
			case NEGATION_NT:
				putString(Language.ENGLISH, itr, "n't (contracted form)", "negation > n't");
				putString(Language.GERMAN, itr, "NEGATION_NT", "NEGATION_NT");
				break;
			case NEGATION_PARTIAL:
				putString(Language.ENGLISH, itr, "partial negation (hardly, barely, scarcely, rarely, seldom)", "negation > partial negation");
				putString(Language.GERMAN, itr, "Partielle Negation (kaum, selten, etc.)", "negation > partielle negation");
				break;
			case NOUNFORMS_ING:
				putString(Language.ENGLISH, itr, "-ing forms (skiing, being, etc. ALSO building BUT NOT king, something) ", "nouns > -ing forms");
				putString(Language.GERMAN, itr, "NOUNFORMS_ING", "NOUNFORMS_ING");
				break;
			case NOUNS_ISMUS:
				putString(Language.ENGLISH, itr, "NOUNS_ISMUS", "NOUNS_ISMUS");
				putString(Language.GERMAN, itr, ";-ismus Nominalisierungen (Naturalismus, Pluralismus, etc.)", "nomen > nominalisierungen > -ismus");
				break;
			case NOUNS_TUR:
				putString(Language.ENGLISH, itr, "NOUNS_TUR", "NOUNS_TUR");
				putString(Language.GERMAN, itr, "-tur Nominalisierungen (Natur, Statur, etc.)", "nomen > nominalisierungen > -tur");
				break;
			case NOUNS_UNG:
				putString(Language.ENGLISH, itr, "NOUNS_UNG", "NOUNS_UNG");
				putString(Language.GERMAN, itr, "-ung Nominalisierungen (Schenkung, Schulung, etc.)", "nomen > nominalisierungen > -ung");
				break;
			case OBJECT_DIRECT:
				putString(Language.ENGLISH, itr, "transitive (drive a car)", "verbs > transitive > transitive");
				putString(Language.GERMAN, itr, "OBJECT_DIRECT", "OBJECT_DIRECT");
				break;
			case OBJECT_INDIRECT:
				putString(Language.ENGLISH, itr, "ditransitive (give it to me)", "verbs > transitive > ditransitive");
				putString(Language.GERMAN, itr, "OBJECT_INDIRECT", "OBJECT_INDIRECT");
				break;
			case PARTICLE_PLUS_ADJ_ADV:
				putString(Language.ENGLISH, itr, "PARTICLE_PLUS_ADJ_ADV", "PARTICLE_PLUS_ADJ_ADV");
				putString(Language.GERMAN, itr, "Adjektiv- und Adverbpartikel", "adjektive, adverben und zahlworte > adjektiv- und adverbpartikel");
				break;
			case PASSIVE_VOICE:
				putString(Language.ENGLISH, itr, "passive", "verbs > voice > passive");
				putString(Language.GERMAN, itr, "PASSIVE_VOICE", "PASSIVE_VOICE");
				break;
			case PASSIVE_VOICE_SEIN:
				putString(Language.ENGLISH, itr, "PASSIVE_VOICE_SEIN", "PASSIVE_VOICE_SEIN");
				putString(Language.GERMAN, itr, "sein-Passiv", "verben > passive > sein");
				break;
			case PASSIVE_VOICE_WERDEN:
				putString(Language.ENGLISH, itr, "PASSIVE_VOICE_WERDEN", "PASSIVE_VOICE_WERDEN");
				putString(Language.GERMAN, itr, "werden-Passiv", "verben > passive > werden");
				break;
			case PLURAL_IRREGULAR:
				putString(Language.ENGLISH, itr, "plural irregular (children, women, etc.) ", "nouns > plural irregular");
				putString(Language.GERMAN, itr, "PLURAL_IRREGULAR", "PLURAL_IRREGULAR");
				break;
			case PLURAL_REGULAR:
				putString(Language.ENGLISH, itr, "plural regular (cars, flowers, etc.)", "nouns > plural regular");
				putString(Language.GERMAN, itr, "PLURAL_REGULAR", "PLURAL_REGULAR");
				break;
			case PREPOSITIONS:
				putString(Language.ENGLISH, itr, "PREPOSITIONS", "PREPOSITIONS");
				putString(Language.GERMAN, itr, "PREPOSITIONS", "PREPOSITIONS");
				break;
			case PREPOSITIONS_ADVANCED:
				putString(Language.ENGLISH, itr, "PREPOSITIONS_ADVANCED", "PREPOSITIONS_ADVANCED");
				putString(Language.GERMAN, itr, "PREPOSITIONS_ADVANCED", "PREPOSITIONS_ADVANCED");
				break;
			case PREPOSITIONS_COMPLEX:
				putString(Language.ENGLISH, itr, "advanced", "prepositions > advanced");
				putString(Language.GERMAN, itr, "Postposition (zufolge, wegen, etc.)", "präpositionen > postposition");
				break;
			case PREPOSITIONS_SIMPLE:
				putString(Language.ENGLISH, itr, "simple (at, on, in, to, with, after)", "prepositions > simple");
				putString(Language.GERMAN, itr, "Präposition (auf, unter, neben, etc.)", "präpositionen > präposition");
				break;
			case PRONOUNS:
				putString(Language.ENGLISH, itr, "PRONOUNS", "PRONOUNS");
				putString(Language.GERMAN, itr, "PRONOUNS", "PRONOUNS");
				break;
			case PRONOUNS_DEMONSTRATIVE:
				putString(Language.ENGLISH, itr, "PRONOUNS_DEMONSTRATIVE", "PRONOUNS_DEMONSTRATIVE");
				putString(Language.GERMAN, itr, "Demonstrativpronomen (dieser, jener, ...)", "pronomen > demonstrativ");
				break;
			case PRONOUNS_INDEFINITE:
				putString(Language.ENGLISH, itr, "PRONOUNS_INDEFINITE", "PRONOUNS_INDEFINITE");
				putString(Language.GERMAN, itr, "Indefinitpronomen (kein, irgendein)", "pronomen > indefinit");
				break;
			case PRONOUNS_INTERROGATIVE:
				putString(Language.ENGLISH, itr, "PRONOUNS_INTERROGATIVE", "PRONOUNS_INTERROGATIVE");
				putString(Language.GERMAN, itr, "Interrogativpronomen (welche, wobei)", "pronomen > interrogativ");
				break;
			case PRONOUNS_OBJECTIVE:
				putString(Language.ENGLISH, itr, "object (me)", "pronouns > object");
				putString(Language.GERMAN, itr, "PRONOUNS_OBJECTIVE", "PRONOUNS_OBJECTIVE");
				break;
			case PRONOUNS_PERSONAL:
				putString(Language.ENGLISH, itr, "PRONOUNS_PERSONAL", "PRONOUNS_PERSONAL");
				putString(Language.GERMAN, itr, "Personalpronomen (ich, du, ...)", "pronomen > personal");
				break;
			case PRONOUNS_POSSESSIVE:
				putString(Language.ENGLISH, itr, "possessive (my)", "pronouns > possessive");
				putString(Language.GERMAN, itr, "Possessivpronomen (mein, dein, ...)", "pronomen > possessive");
				break;
			case PRONOUNS_POSSESSIVE_ABSOLUTE:
				putString(Language.ENGLISH, itr, "absolute possessive (mine)", "pronouns > absolute possessive");
				putString(Language.GERMAN, itr, "PRONOUNS_POSSESSIVE_ABSOLUTE", "PRONOUNS_POSSESSIVE_ABSOLUTE");
				break;
			case PRONOUNS_REFLEXIVE:
				putString(Language.ENGLISH, itr, "reflexive (myself)", "pronouns > reflexive");
				putString(Language.GERMAN, itr, "Reflexivpronomen (mich, sich)", "pronomen > reflexiv");
				break;
			case PRONOUNS_RELATIVE:
				putString(Language.ENGLISH, itr, "PRONOUNS_RELATIVE", "PRONOUNS_RELATIVE");
				putString(Language.GERMAN, itr, "Relativpronomen (der, dessen, ...)", "pronomen > relativ");
				break;
			case PRONOUNS_SUBJECTIVE:
				putString(Language.ENGLISH, itr, "subject (I)", "pronouns > subject");
				putString(Language.GERMAN, itr, "PRONOUNS_SUBJECTIVE", "PRONOUNS_SUBJECTIVE");
				break;
			case QUESTIONS_DIRECT:
				putString(Language.ENGLISH, itr, "direct questions", "questions > direct questions");
				putString(Language.GERMAN, itr, "direkte Fragen", "fragen > direkt");
				break;
			case QUESTIONS_HOW:
				putString(Language.ENGLISH, itr, "QUESTIONS_HOW", "QUESTIONS_HOW");
				putString(Language.GERMAN, itr, "QUESTIONS_HOW", "QUESTIONS_HOW");
				break;
			case QUESTIONS_INDIRECT:
				putString(Language.ENGLISH, itr, "QUESTIONS_INDIRECT", "QUESTIONS_INDIRECT");
				putString(Language.GERMAN, itr, "indirekte Fragen", "fragen > indirekt");
				break;
			case QUESTIONS_MODAL:
				putString(Language.ENGLISH, itr, "QUESTIONS_MODAL", "QUESTIONS_MODAL");
				putString(Language.GERMAN, itr, "QUESTIONS_MODAL", "QUESTIONS_MODAL");
				break;
			case QUESTIONS_TAG:
				putString(Language.ENGLISH, itr, "tag questions", "questions > tag questions");
				putString(Language.GERMAN, itr, "QUESTIONS_TAG", "QUESTIONS_TAG");
				break;
			case QUESTIONS_TO_BE:
				putString(Language.ENGLISH, itr, "be- questions", "questions > be- questions");
				putString(Language.GERMAN, itr, "QUESTIONS_TO_BE", "QUESTIONS_TO_BE");
				break;
			case QUESTIONS_TO_DO:
				putString(Language.ENGLISH, itr, "do- questions", "questions > do- questions");
				putString(Language.GERMAN, itr, "QUESTIONS_TO_DO", "QUESTIONS_TO_DO");
				break;
			case QUESTIONS_TO_HAVE:
				putString(Language.ENGLISH, itr, "have- questions", "questions > have- questions");
				putString(Language.GERMAN, itr, "QUESTIONS_TO_HAVE", "QUESTIONS_TO_HAVE");
				break;
			case QUESTIONS_WH:
				putString(Language.ENGLISH, itr, "wh- questions", "questions > wh- questions");
				putString(Language.GERMAN, itr, "W-Fragen", "fragen > w-fragen");
				break;
			case QUESTIONS_WHAT:
				putString(Language.ENGLISH, itr, "QUESTIONS_WHAT", "QUESTIONS_WHAT");
				putString(Language.GERMAN, itr, "QUESTIONS_WHAT", "QUESTIONS_WHAT");
				break;
			case QUESTIONS_WHEN:
				putString(Language.ENGLISH, itr, "QUESTIONS_WHEN", "QUESTIONS_WHEN");
				putString(Language.GERMAN, itr, "QUESTIONS_WHEN", "QUESTIONS_WHEN");
				break;
			case QUESTIONS_WHERE:
				putString(Language.ENGLISH, itr, "QUESTIONS_WHERE", "QUESTIONS_WHERE");
				putString(Language.GERMAN, itr, "QUESTIONS_WHERE", "QUESTIONS_WHERE");
				break;
			case QUESTIONS_WHICH:
				putString(Language.ENGLISH, itr, "QUESTIONS_WHICH", "QUESTIONS_WHICH");
				putString(Language.GERMAN, itr, "QUESTIONS_WHICH", "QUESTIONS_WHICH");
				break;
			case QUESTIONS_WHO:
				putString(Language.ENGLISH, itr, "QUESTIONS_WHO", "QUESTIONS_WHO");
				putString(Language.GERMAN, itr, "QUESTIONS_WHO", "QUESTIONS_WHO");
				break;
			case QUESTIONS_WHOM:
				putString(Language.ENGLISH, itr, "QUESTIONS_WHOM", "QUESTIONS_WHOM");
				putString(Language.GERMAN, itr, "QUESTIONS_WHOM", "QUESTIONS_WHOM");
				break;
			case QUESTIONS_WHOSE:
				putString(Language.ENGLISH, itr, "QUESTIONS_WHOSE", "QUESTIONS_WHOSE");
				putString(Language.GERMAN, itr, "QUESTIONS_WHOSE", "QUESTIONS_WHOSE");
				break;
			case QUESTIONS_WHY:
				putString(Language.ENGLISH, itr, "QUESTIONS_WHY", "QUESTIONS_WHY");
				putString(Language.GERMAN, itr, "QUESTIONS_WHY", "QUESTIONS_WHY");
				break;
			case QUESTIONS_YESNO:
				putString(Language.ENGLISH, itr, "yes/no questions", "questions > yes/no questions");
				putString(Language.GERMAN, itr, "Ja/Nein Fragen", "fragen > ja/nein");
				break;
			case SENTENCE_COMPLEX:
				putString(Language.ENGLISH, itr, "SENTENCE_COMPLEX", "SENTENCE_COMPLEX");
				putString(Language.GERMAN, itr, "SENTENCE_COMPLEX", "SENTENCE_COMPLEX");
				break;
			case SENTENCE_COMPOUND:
				putString(Language.ENGLISH, itr, "coordinate", "structure > coordinate");
				putString(Language.GERMAN, itr, "koordiniert", "satztypen > koordiniert");
				break;
			case SENTENCE_INCOMPLETE:
				putString(Language.ENGLISH, itr, "incomplete sentences", "structure > incomplete sentences");
				putString(Language.GERMAN, itr, "Satzfragmente", "satztypen > satzfragment");
				break;
			case SENTENCE_SIMPLE:
				putString(Language.ENGLISH, itr, "simple", "structure > simple");
				putString(Language.GERMAN, itr, "einfach", "satztypen > einfach");
				break;
			case TENSE_FUTURE_PERFECT:
				putString(Language.ENGLISH, itr, "future perfect", "verbs > tenses > future perfect");
				putString(Language.GERMAN, itr, "Futur 2", "verben > zeiten > futur 2");
				break;
			case TENSE_FUTURE_PERFECT_PROGRESSIVE:
				putString(Language.ENGLISH, itr, "perfect progressive", "verbs > tenses > perfect progressive");
				putString(Language.GERMAN, itr, "TENSE_FUTURE_PERFECT_PROGRESSIVE", "TENSE_FUTURE_PERFECT_PROGRESSIVE");
				break;
			case TENSE_FUTURE_PROGRESSIVE:
				putString(Language.ENGLISH, itr, "future progressive", "verbs > tenses > future progressive");
				putString(Language.GERMAN, itr, "TENSE_FUTURE_PROGRESSIVE", "TENSE_FUTURE_PROGRESSIVE");
				break;
			case TENSE_FUTURE_SIMPLE:
				putString(Language.ENGLISH, itr, "future simple", "verbs > tenses > future simple");
				putString(Language.GERMAN, itr, "Futur 1", "verben > zeiten > futur 1");
				break;
			case TENSE_PAST_PERFECT:
				putString(Language.ENGLISH, itr, "future progressive", "verbs > tenses > future progressive");
				putString(Language.GERMAN, itr, "TENSE_PAST_PERFECT", "TENSE_PAST_PERFECT");
				break;
			case TENSE_PAST_PERFECT_HABEN:
				putString(Language.ENGLISH, itr, "TENSE_PAST_PERFECT_HABEN", "TENSE_PAST_PERFECT_HABEN");
				putString(Language.GERMAN, itr, "haben-Plusquamperfekt", "verben > zeiten > haben-plusquamperfekt");
				break;
			case TENSE_PAST_PERFECT_PROGRESSIVE:
				putString(Language.ENGLISH, itr, "past perfect progressive", "verbs > tenses > past perfect progressive");
				putString(Language.GERMAN, itr, "TENSE_PAST_PERFECT_PROGRESSIVE", "TENSE_PAST_PERFECT_PROGRESSIVE");
				break;
			case TENSE_PAST_PERFECT_SEIN:
				putString(Language.ENGLISH, itr, "TENSE_PAST_PERFECT_SEIN", "TENSE_PAST_PERFECT_SEIN");
				putString(Language.GERMAN, itr, "sein-Plusquamperfekt", "verben > zeiten > sein-plusquamperfekt");
				break;
			case TENSE_PAST_PROGRESSIVE:
				putString(Language.ENGLISH, itr, "past progressive", "verbs > tenses > past progressive");
				putString(Language.GERMAN, itr, "TENSE_PAST_PROGRESSIVE", "TENSE_PAST_PROGRESSIVE");
				break;
			case TENSE_PAST_SIMPLE:
				putString(Language.ENGLISH, itr, "past simple", "verbs > tenses > past simple");
				putString(Language.GERMAN, itr, "TENSE_PAST_SIMPLE", "TENSE_PAST_SIMPLE");
				break;
			case TENSE_PRESENT_PERFECT:
				putString(Language.ENGLISH, itr, "present perfect", "verbs > tenses > present perfect");
				putString(Language.GERMAN, itr, "TENSE_PRESENT_PERFECT", "TENSE_PRESENT_PERFECT");
				break;
			case TENSE_PRESENT_PERFECT_HABEN:
				putString(Language.ENGLISH, itr, "TENSE_PRESENT_PERFECT_HABEN", "TENSE_PRESENT_PERFECT_HABEN");
				putString(Language.GERMAN, itr, "haben-Perfekt", "verben > zeiten > haben-perfekt");
				break;
			case TENSE_PRESENT_PERFECT_PROGRESSIVE:
				putString(Language.ENGLISH, itr, "perfect progressive", "verbs > tenses > present perfect progressive");
				putString(Language.GERMAN, itr, "TENSE_PRESENT_PERFECT_PROGRESSIVE", "TENSE_PRESENT_PERFECT_PROGRESSIVE");
				break;
			case TENSE_PRESENT_PERFECT_SEIN:
				putString(Language.ENGLISH, itr, "TENSE_PRESENT_PERFECT_SEIN", "TENSE_PRESENT_PERFECT_SEIN");
				putString(Language.GERMAN, itr, "sein-Perfekt", "verben > zeiten > sein-perfekt");
				break;
			case TENSE_PRESENT_PROGRESSIVE:
				putString(Language.ENGLISH, itr, "present progressive", "verbs > tenses > present progressive");
				putString(Language.GERMAN, itr, "TENSE_PRESENT_PROGRESSIVE", "TENSE_PRESENT_PROGRESSIVE");
				break;
			case TENSE_PRESENT_SIMPLE:
				putString(Language.ENGLISH, itr, "present simple", "verbs > tenses > present simple");
				putString(Language.GERMAN, itr, "TENSE_PRESENT_SIMPLE", "TENSE_PRESENT_SIMPLE");
				break;
			case THERE_IS_ARE:
				putString(Language.ENGLISH, itr, "there is/are", "clauses > there is/are");
				putString(Language.GERMAN, itr, "THERE_IS_ARE", "THERE_IS_ARE");
				break;
			case THERE_WAS_WERE:
				putString(Language.ENGLISH, itr, "there was/were", "clauses > there was/were");
				putString(Language.GERMAN, itr, "THERE_WAS_WERE", "THERE_WAS_WERE");
				break;
			case TIME_FUTURE:
				putString(Language.ENGLISH, itr, "future", "verbs > time > future");
				putString(Language.GERMAN, itr, "TIME_FUTURE", "TIME_FUTURE");
				break;
			case TIME_PAST:
				putString(Language.ENGLISH, itr, "past", "verbs > time > past");
				putString(Language.GERMAN, itr, "TIME_PAST", "TIME_PAST");
				break;
			case TIME_PRESENT:
				putString(Language.ENGLISH, itr, "present", "verbs > time > present");
				putString(Language.GERMAN, itr, "TIME_PRESENT", "TIME_PRESENT");
				break;
			case VERBCONST_GOING_TO:
				putString(Language.ENGLISH, itr, "VERBCONST_GOING_TO", "VERBCONST_GOING_TO");
				putString(Language.GERMAN, itr, "VERBCONST_GOING_TO", "VERBCONST_GOING_TO");
				break;
			case VERBCONST_USED_TO:
				putString(Language.ENGLISH, itr, "VERBCONST_USED_TO", "VERBCONST_USED_TO");
				putString(Language.GERMAN, itr, "VERBCONST_USED_TO", "VERBCONST_USED_TO");
				break;
			case VERBFORM_AUXILIARIES_BE_DO_HAVE:
				putString(Language.ENGLISH, itr, "auxiliaries (to be and to have: short and full forms)", "verbs > forms > auxiliaries");
				putString(Language.GERMAN, itr, "VERBFORM_AUXILIARIES_BE_DO_HAVE", "VERBFORM_AUXILIARIES_BE_DO_HAVE");
				break;
			case VERBFORM_COPULAR:
				putString(Language.ENGLISH, itr, "copula (be, seem, look, stay, etc.: \"She looks upset\")", "verbs > forms > copula");
				putString(Language.GERMAN, itr, "VERBFORM_COPULAR", "VERBFORM_COPULAR");
				break;
			case VERBFORM_EMPATHIC_DO:
				putString(Language.ENGLISH, itr, "emphatic do (\"I did tell the truth\")", "verbs > forms > emphatic do");
				putString(Language.GERMAN, itr, "VERBFORM_EMPATHIC_DO", "VERBFORM_EMPATHIC_DO");
				break;
			case VERBFORM_INFINITIVE:
				putString(Language.ENGLISH, itr, "VERBFORM_INFINITIVE", "VERBFORM_INFINITIVE");
				putString(Language.GERMAN, itr, "Infinitive", "verbformen > infinitive");
				break;
			case VERBFORM_ING:
				putString(Language.ENGLISH, itr, "-ing (gerund and present participle)", "verbs > forms > -ing");
				putString(Language.GERMAN, itr, "VERBFORM_ING", "VERBFORM_ING");
				break;
			case VERBFORM_LONG:
				putString(Language.ENGLISH, itr, "full (to be and to have: is, are, had)", "verbs > forms > full");
				putString(Language.GERMAN, itr, "VERBFORM_LONG", "VERBFORM_LONG");
				break;
			case VERBFORM_PARTICIPLE:
				putString(Language.ENGLISH, itr, "VERBFORM_PARTICIPLE", "VERBFORM_PARTICIPLE");
				putString(Language.GERMAN, itr, "Partizipien", "verbformen > partizipien");
				break;
			case VERBFORM_PARTICIPLE_1:
				putString(Language.ENGLISH, itr, "VERBFORM_PARTICIPLE_1", "VERBFORM_PARTICIPLE_1");
				putString(Language.GERMAN, itr, "VERBFORM_PARTICIPLE_1", "VERBFORM_PARTICIPLE_1");
				break;
			case VERBFORM_PARTICIPLE_2:
				putString(Language.ENGLISH, itr, "VERBFORM_PARTICIPLE_2", "VERBFORM_PARTICIPLE_2");
				putString(Language.GERMAN, itr, "VERBFORM_PARTICIPLE_2", "VERBFORM_PARTICIPLE_2");
				break;
			case VERBFORM_SHORT:
				putString(Language.ENGLISH, itr, "contracted (to be and to have: 'm, 's, 'd)", "verbs > forms > contracted");
				putString(Language.GERMAN, itr, "VERBFORM_SHORT", "VERBFORM_SHORT");
				break;
			case VERBFORM_TO_INFINITIVE:
				putString(Language.ENGLISH, itr, "to- infinitive", "verbs > forms > to- infinitive");
				putString(Language.GERMAN, itr, "zu-Infinitive", "verbformen > zu-infinitive");
				break;
			case VERBS_IRREGULAR:
				putString(Language.ENGLISH, itr, "irregular (2nd and 3rd form)", "verbs > forms > irregular");
				putString(Language.GERMAN, itr, "VERBS_IRREGULAR", "VERBS_IRREGULAR");
				break;
			case VERBS_PHRASAL:
				putString(Language.ENGLISH, itr, "phrasal verbs", "verbs > phrasal ");
				putString(Language.GERMAN, itr, "VERBS_PHRASAL", "VERBS_PHRASAL");
				break;
			case VERBS_REGULAR:
				putString(Language.ENGLISH, itr, "regular (2nd and 3rd form)", "verbs > forms > regular");
				putString(Language.GERMAN, itr, "VERBS_REGULAR", "VERBS_REGULAR");
				break;
			case VERBTYP_AUXILIARIES:
				putString(Language.ENGLISH, itr, "VERBTYP_AUXILIARIES", "VERBTYP_AUXILIARIES");
				putString(Language.GERMAN, itr, "Hilfsverben (sein, haben)", "verbtypen > hilfsverben");
				break;
			case VERBTYP_MAIN:
				putString(Language.ENGLISH, itr, "VERBTYP_MAIN", "VERBTYP_MAIN");
				putString(Language.GERMAN, itr, "Vollverben (gehen, sprechen, etc.)", "verbtypen > vollverben");
				break;
			case VERBTYP_MODAL:
				putString(Language.ENGLISH, itr, "VERBTYP_MODAL", "VERBTYP_MODAL");
				putString(Language.GERMAN, itr, "Modalverben (können, müssen, etc.)", "verbtypen > modalverben");
				break;
			case VERB_BRACKETS:
				putString(Language.ENGLISH, itr, "VERB_BRACKETS", "VERB_BRACKETS");
				putString(Language.GERMAN, itr, "Besetzte Satzklammer", "satzklammer > besetzte satzklammer");
				break;
			case VERB_CLUSTER:
				putString(Language.ENGLISH, itr, "VERB_CLUSTER", "VERB_CLUSTER");
				putString(Language.GERMAN, itr, "Verbcluster", "satzklammer > verbcluster");
				break;
			default:
				break;
			
			}
		}
		
	}
	
	public String getLocalizedName(GrammaticalConstruction gram, Language lang) {
		return getLocalizationData(lang).get(gram.toString());
	}
	
	public String getLocalizedPath(GrammaticalConstruction gram, Language lang) {
		return getLocalizationData(lang).get(gram.toString() + DESC_PATH_SUFFIX);
	}
}
