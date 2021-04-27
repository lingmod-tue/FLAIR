
package com.flair.server.pipelines.gramparsing;

import com.flair.server.document.AbstractDocument;
import com.flair.server.document.ConstructionOccurrence;
import com.flair.server.grammar.EnglishGrammaticalConstants;
import com.flair.server.parser.CoreNlpParser;
import com.flair.shared.grammar.GrammaticalConstruction;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Implementation of English language parsing logic for the Stanford parser
 *
 * @author Maria
 */
class EnglishParsingLogic implements ParsingLogic {
	static ParsingLogic factory(AbstractDocument doc) {
		return new EnglishParsingLogic(doc);
	}


	private final AbstractDocument workingDoc;

	private int dependencyCount;    // count dependencies - correspond to token count without punctuation
	private int wordCount;            // count words (without numbers, symbols and punctuation)
	private int tokenCount;            // count tokens (incl. numbers, symbols and punctuation)
	private int sentenceCount;        // count sentences
	private int depthCount;            // count tree depthCount
	private int characterCount;        // count characters in words

	// for easier access; is reset in every inspectSentence():
	private boolean conditionalFound;
	private boolean usedFound;                // for the "used to" construction
	private int goingToFound;
	private boolean comparativeMoreFound;
	private boolean superlativeMostFound;

	private Tree treeOutput;        // syntax tree: output from parser (Stanford CoreNLP)
	private List<CoreLabel> wordsOutput;    // annotated words: output from parser (Stanford CoreNLP)
	private Collection<TypedDependency> depsOutput;        // dependencies: output from parser (Stanford CoreNLP)

	private EnglishParsingLogic(AbstractDocument doc) {
		workingDoc = doc;
		// treeOutput = null;
		// wordsOutput = null;
		// depsOutput = null;
		// conditionalFound = false;
		// usedFound = false;
		// comparativeMoreFound = false;
		// superlativeMostFound = false;
		wordCount = tokenCount = dependencyCount = sentenceCount = depthCount = characterCount = goingToFound = 0;
	}

	private int countSubstr(String substr, String str) {
		// ###TODO can be made faster?
		// the result of split() will contain one more element than the delimiter
		// the "-1" second argument makes it not discard trailing empty strings
		return str.split(Pattern.quote(substr), -1).length - 1;
	}

	private void addConstructionOccurrence(GrammaticalConstruction type, int start, int end, String expr) {
		workingDoc.getConstructionData(type).addOccurrence(start, end);
	}

	private void inspectSentence(Tree tree, List<CoreLabel> words, Collection<TypedDependency> deps) {
		if (words == null || words.isEmpty()) {
			return;
		}

		this.treeOutput = tree;
		this.wordsOutput = words;
		this.depsOutput = deps;

		conditionalFound = false; // to find a conditional clause (the order can be inverted)

		// boolean indirectQuestionFound = false; // Indirect Questions: disabled for now

		///////////////////////
		////// > SENTENCE level
		///////////////////////

		// start and end indices of this sentence
		int startInd = wordsOutput.get(0).beginPosition();
		int endInd = wordsOutput.get(wordsOutput.size() - 1).endPosition();

		identifySentencesAndClauses();
		
		// Relative pronouns
		addRelativePronouns(treeOutput, wordsOutput);

		// >>> questions or imperative
		findQuestionOrImperative(startInd, endInd);

		//////////////////
		////// > WORD LEVEL
		//////////////////
		// degrees of comparison of long adjectives: "more/RBR beautiful/JJ", "most/RBS beautiful/JJ"
		comparativeMoreFound = false;
		superlativeMostFound = false;

		usedFound = false;
		goingToFound = 0;

		// independent of type of sentence
		////// go through the wordsOutput (CoreLabels) for POS tags
		for (CoreLabel label : wordsOutput) {
			String labelTag = label.tag().toLowerCase();
			String labelWord = label.word().toLowerCase();
			if (labelTag != null && labelWord != null
					&& (!(labelTag.equalsIgnoreCase(".") || labelTag.equalsIgnoreCase(",")))) {

				// >>> negation (first part; the other part in TypedDependency itr
				// words that won't occur in a dependency "neg":
				// "neither", "nobody", "none", "nothing", "nor", "nowhere"
				// "hardly", "scarcely", "rarely", "seldom", "barely"
				if (EnglishGrammaticalConstants.NEGATION.contains(labelWord.toLowerCase())) {
					addConstructionOccurrence(GrammaticalConstruction.NEGATION_ALL, label.beginPosition(),
							label.endPosition(), labelWord);
				} else if (EnglishGrammaticalConstants.PARTIAL_NEGATION.contains(labelWord.toLowerCase())) {
					addConstructionOccurrence(GrammaticalConstruction.NEGATION_PARTIAL, label.beginPosition(),
							label.endPosition(), labelWord);
				}

				// >>> "used to"
				if (usedFound) {
					findUsedTo(label, labelWord, labelTag);
				}

				// >>> "going to"
				if (goingToFound > 0) {
					findGoingTo(label, labelWord, labelTag);
				}

				// >>> "a lot of" quantifier
				if (labelWord.equalsIgnoreCase("lot")) {
					int lotInd = label.index() - 1; // indices start at 1 !!!
					// conditions:
					// 1. it is not the first or the last word ("a" and "of" has to surround it)
					// 2. it is preceded by "a"
					// 3. it is followed by "of"
					if (lotInd > 0 && lotInd < wordsOutput.size() - 1
							&& wordsOutput.get(lotInd - 1).word().equalsIgnoreCase("a")
							&& wordsOutput.get(lotInd + 1).word().equalsIgnoreCase("of")) {
						addConstructionOccurrence(GrammaticalConstruction.DETERMINER_A_LOT_OF,
								wordsOutput.get(label.index() - 1).beginPosition(),
								wordsOutput.get(label.index() + 1).endPosition(), "a " + labelWord + " of");
					}
				}

				//// >>> degrees of comparison of LONG adjectives and adverbs: "more/RBR beautiful/JJ", "most/RBS beautiful/JJ" (PROBLEM ???)
				if (comparativeMoreFound && (labelTag.equalsIgnoreCase("jj") || labelTag.equalsIgnoreCase("rb"))) {
					addLongComparative(label, labelWord, labelTag);
				} else if (superlativeMostFound && (labelTag.equalsIgnoreCase("jj") || labelTag.equalsIgnoreCase("rb"))) {
					addLongSuperlative(label, labelWord, labelTag);
				}
				//// >>> degrees of comparison of SHORT adverbs
				else if (labelTag.equalsIgnoreCase("rbr")) {
					addShortComparativeAdv(label, labelWord);
				} else if (labelTag.equalsIgnoreCase("rbs")) {
					addShortSuperlativeAdv(label, labelWord);
				}
				//// >>> forms of adjectives (except for long comparative and superlative forms
				else if (labelTag.startsWith("jj")) {
					addAdjectiveForms(label, labelWord, labelTag);
				}

				//// >>> pronouns (reflexive or possessive)
				else if (labelTag.toLowerCase().startsWith("prp")) {
					addReflexiveAndPossessivePronouns(label, labelWord, labelTag);
				}
				// Stanford sometimes parses such sentences incorrectly (as PRP$: his, yours, its; as NN: mine, hers; as JJ: theirs): "This is mine/hers."
				// Therefore, there might be false positives in PRONOUNS ("This is mine." vs. "This is a mine." will both be added)
				else if (EnglishGrammaticalConstants.POSSESSIVE_ABSOLUTE_PRONOUNS.contains(labelWord)) {
					addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_POSSESSIVE_ABSOLUTE,
							label.beginPosition(), label.endPosition(), labelWord);
					addConstructionOccurrence(GrammaticalConstruction.PRONOUNS, label.beginPosition(),
							label.endPosition(), labelWord);
				}

				//// >>> conjunctions (all or simple)
				else if (labelTag.equalsIgnoreCase("in") || labelTag.equalsIgnoreCase("cc")
						|| labelTag.equalsIgnoreCase("rb") || labelTag.equalsIgnoreCase("wrb")) {
					addConjunctions(label, labelWord, labelTag);
				}

				//// NOT HERE : determiners (some, any) - see dependencies
				//// HERE: >>> articles (a, an, the)
				else if (labelTag.equalsIgnoreCase("dt")) {
					addArticles(label, labelWord);
				}

				//// >>> ing noun forms
				else if (labelTag.startsWith("nn")) {
					findIngNounForms(label, labelWord, labelTag);
				}

				//// >>> modals
				else if (labelTag.equalsIgnoreCase("md")) {
					// includes "will"
					addModalVerb(label, labelWord);
				}

				//// >>> verb forms
				else if (labelTag.startsWith("v") || labelTag.equalsIgnoreCase("md")) {
					// already in lower case // include modals "will" and "would" ++
					findVerbFormsPOS(label, labelWord, labelTag);
				}
			}
		}

		////// go through the DEPENDENCIES
		for (TypedDependency dependency : depsOutput) {
			String rel = dependency.reln().toString();

			IndexedWord dep = dependency.dep();
			IndexedWord gov = dependency.gov();

			// first gov is ROOT (no ind?)!
			// detect the order dep-gov or gov-dep for highlights
			int depBegin = dep.beginPosition();
			int govBegin = gov.beginPosition();
			int depEnd = dep.endPosition();
			int govEnd = gov.endPosition();

			// define start and end of a dependency in the sentence
			int start = -1;
			int end = -1;

			if (rel.equalsIgnoreCase("root")) {
				start = dep.beginPosition();
				end = dep.endPosition();
			} // if not a root dependency
			else if (depBegin < govBegin) {
				start = depBegin;
				end = govEnd;
			} else {
				start = govBegin;
				end = depEnd;
			}
			// by this point there has to be a value assigned to each start and end
			if (start < 0 || end < 0) {
				System.out.println("Wrong highlight indices for dependencies!: rel " + rel);
			}

			// if there is a null value anywhere (if rel is ROOT, gov.word() is null)
			if (gov.word() == null || gov.lemma() == null || gov.tag() == null || dep.word() == null
					|| dep.lemma() == null || dep.tag() == null) {
				// skip to next dependency
				continue;
			}

			// >>> negation
			//// >>> (continue) negation dependency (noNotNever, nt, not) (first part in CoreLabel itr)
			if (rel.equalsIgnoreCase("neg")) {
				addNegation(dependency, dep, depBegin, depEnd);
			}

			//// >>> there is / there are: expl( is-3 , there-2 )
			else if (rel.toLowerCase().equalsIgnoreCase("expl") && dep.word().equalsIgnoreCase("there")) {
				addExistentialThere(dependency, start, end, gov);
			}

			//// >>> prepositions (incl. simple)
			else if (rel.toLowerCase().startsWith("prep")) {
				findPrepositions(dependency, depBegin, depEnd, rel, dep, gov);
			}

			//// >>> objective and subjective pronouns
			// nsubj rel here!
			else if (rel.equalsIgnoreCase("iobj") || rel.equalsIgnoreCase("dobj") || rel.equalsIgnoreCase("nsubj")) {
				addSubjectiveAndObjectivePronouns(dependency, start, end, rel, dep, depBegin, depEnd);
			}

			//// >>> determinets some, any
			else if (rel.equalsIgnoreCase("det") || rel.equalsIgnoreCase("amod")) {
				addSomeAnyMuchMany(dependency, gov, dep, rel);
			}

			//// >>> verb forms
			else if (rel.equalsIgnoreCase("aux")) {
				// >>> emphatic do and to-infinitive
				findVerbFormsDep(dependency, start, end, gov, dep, depBegin, depEnd);
			} else if (rel.equalsIgnoreCase("cop")) {
				addConstructionOccurrence(GrammaticalConstruction.VERBFORM_COPULAR, start, end, dependency.toString());
			} else if (rel.equalsIgnoreCase("prt") && gov.tag().startsWith("V")) {
				addConstructionOccurrence(GrammaticalConstruction.VERBS_PHRASAL, start, end, dependency.toString());
			}
		}

		//// second round for: conditionals
		if (conditionalFound) {
			identifyConditionals(wordsOutput, depsOutput);
		} // look for tenses only 'outside' of conditionals!
		// after the first iteration over all dependencies
		else {
			// if conditional not found -> look for tenses/times/aspects/voice
			identifyTenses(depsOutput);
		}
	}

	private void identifySentencesAndClauses() {
		// >>> simple/complex sentence + subordinate clauses
		String treeStr = treeOutput.toString(); // don't use toLowerCase() here

		int startInd = wordsOutput.get(0).beginPosition();
		int endInd = wordsOutput.get(wordsOutput.size() - 1).endPosition();

		int numSBAR = countSubstr("SBAR ", treeStr); // the whitespace is important!
		if (numSBAR > 0) {
			addConstructionOccurrence(GrammaticalConstruction.SENTENCE_COMPLEX, startInd, endInd, treeStr); // highlight the whole sentence
			for (int i = 0; i < numSBAR; i++) {
				addConstructionOccurrence(GrammaticalConstruction.CLAUSE_SUBORDINATE, startInd, endInd, treeStr); // highlight the whole sentence
			}

			int withCC = countSubstr("(sbar (whnp ", treeStr.toLowerCase()); // with a conjunction // TODO: can be an indirect question: "She asked me what I like" -> check for reporting wordsOutput in dependency?
			int withoutCC = countSubstr("(sbar (s ", treeStr.toLowerCase()); // reduced relative clauses "The man I saw" // TODO: check
			for (int i = 0; i < withCC; i++) {
				addConstructionOccurrence(GrammaticalConstruction.CLAUSE_RELATIVE, startInd, endInd, treeStr); // highlight the whole sentence
			}
			for (int i = 0; i < withoutCC; i++) {
				addConstructionOccurrence(GrammaticalConstruction.CLAUSE_RELATIVE_REDUCED, startInd, endInd, treeStr); // highlight the whole sentence
			}

			int numOfAdvCl = countSubstr("(sbar (in", treeStr.toLowerCase());
			int numOfAdvClWhether = countSubstr("(sbar (in whether", treeStr.toLowerCase()); // indirect questions

			int numOfRelClThat = countSubstr("(sbar (in that", treeStr); // indirect questions

			int numOfAdvClIF = countSubstr("(sbar (in if", treeStr.toLowerCase()); // TODO check: "if" is also IN! -> an indirect question or conditional
			int numOfAdvUnless = countSubstr("(sbar (in unless", treeStr.toLowerCase());

			for (int i = 0; i < numOfAdvCl - numOfRelClThat - numOfAdvClIF - numOfAdvUnless; i++) // don't include the indirect questions and conditionals
			{
				addConstructionOccurrence(GrammaticalConstruction.CLAUSE_ADVERBIAL, startInd, endInd, treeStr); // highlight the whole sentence
			}
			// Indirect questions (disabled for now)
			// if (numOfAdvClIF > 0 || numOfAdvClWhether > 0) {
			// indirectQuestionFound = true;
			// }

			if (numOfAdvClIF > 0 || numOfAdvUnless > 0) {
				conditionalFound = true;
			}
		} else if (numSBAR == 0) {
			int numOfS = countSubstr("S ", treeStr); // whitespace is important!
			if (numOfS == 1) {
				// incomplete sentence (no VP or NP)
				if (!(treeStr.contains("VP") && treeStr.contains("NP"))) {
					addConstructionOccurrence(GrammaticalConstruction.SENTENCE_INCOMPLETE, startInd, endInd, treeStr); // highlight the whole sentence
				} else {
					addConstructionOccurrence(GrammaticalConstruction.SENTENCE_SIMPLE, startInd, endInd, treeStr); // highlight the whole sentence
				}
			} else if (numOfS > 1) {
				// changed: also when numOfS > 1, it can be incomplete
				if (!(treeStr.contains("VP") && treeStr.contains("NP"))) {
					addConstructionOccurrence(GrammaticalConstruction.SENTENCE_INCOMPLETE, startInd, endInd, treeStr); // highlight the whole sentence
				} else {
					addConstructionOccurrence(GrammaticalConstruction.SENTENCE_COMPOUND, startInd, endInd, treeStr);
				}
			} else {
				addConstructionOccurrence(GrammaticalConstruction.SENTENCE_INCOMPLETE, startInd, endInd, treeStr);
			}
		}
	}

	private void identifyTenses(Collection<TypedDependency> dependencies) {
		boolean pastPartFound = false;
		boolean ingFound = false;
		boolean willDoingFound = false;
		boolean baseFormFound = false; // to check in : Past Simple (negation), Future Simple, Present Simple (negation)
		boolean willDoneFound = false;
		boolean haveHasDoneFound = false;
		boolean hadDoneFound = false;
		boolean willHaveDoneFound = false;
		boolean willCopulaFound = false;
		int verbIndex = -1;
		int willStart = -1;
		int haveStart = -1;
		IndexedWord theVerb = null;

		for (TypedDependency dependency : dependencies) {
			String rel = dependency.reln().getShortName().toLowerCase(); // dependency relation
			IndexedWord gov = dependency.gov();
			IndexedWord dep = dependency.dep();

			// detect the order dep-gov or gov-dep for highlights
			int depBegin = dep.beginPosition();
			int govBegin = gov.beginPosition();
			int depEnd = dep.endPosition();
			int govEnd = gov.endPosition();
			int start = -1;
			int end = -1;
			if (depBegin < govBegin) {
				start = depBegin;
				end = govEnd;
			} else {
				start = govBegin;
				end = depEnd;
			}

			if (start < 0 || end < 0) {
				if (rel.equalsIgnoreCase("root")) {
					start = dep.beginPosition();
					end = dep.endPosition();
				}
				if (start < 0 || end < 0) {
					System.out.println("Wrong highlight indices for dependencies - identifyTenses!");
				}
			}

			// first examine the root (the main verb phrase)
			if (rel.equalsIgnoreCase("root") && verbIndex != dep.index()) {
				// roughly identify the aspect (any Time)
				if (dep.tag().toLowerCase().startsWith("v")) {
					verbIndex = dep.index();
					theVerb = dep;

					// >>> ing form, past participle, base form
					boolean[] found = new boolean[3];

					found[0] = ingFound;
					found[1] = pastPartFound;
					found[2] = baseFormFound;

					// update ing, past participle, base form
					found = findIngPastPartBaseForTenses(dependency, dep, found);

					ingFound = found[0];
					pastPartFound = found[1];
					baseFormFound = found[2];

				}
			} // don't only look at the root, find other verb phrases
			else if ((rel.equalsIgnoreCase("nsubj") || rel.equalsIgnoreCase("nsubjpass"))
					&& gov.tag().toLowerCase().startsWith("v") && gov.index() != verbIndex) {
				// != condition: don't count 1 verb twice: as root() and nsubj()
				verbIndex = gov.index();
				theVerb = gov;

				// >>> ing form, past participle, base form
				boolean[] found = new boolean[3];

				found[0] = ingFound;
				found[1] = pastPartFound;
				found[2] = baseFormFound;

				// update ing, past participle, base form
				found = findIngPastPartBaseForTenses(dependency, gov, found);

				ingFound = found[0];
				pastPartFound = found[1];
				baseFormFound = found[2];

			} else if (rel.equalsIgnoreCase("aux")) {

				if (dep.word().equalsIgnoreCase("will") && (!gov.tag().toLowerCase().startsWith("v"))) {
					willCopulaFound = true;
					willStart = dep.beginPosition();
				}

				// simple Aspect
				if (baseFormFound && theVerb != null & verbIndex == gov.index()) {
					// ! simpleAspect - already added above ???
					// future
					if (dep.word().equalsIgnoreCase("will")
							|| (dep.word().equalsIgnoreCase("shall") && dep.index() > 1)) {
						// Future Simple
						addConstructionOccurrence(GrammaticalConstruction.ASPECT_SIMPLE, depBegin,
								theVerb.endPosition(), dependency.toString());
						addConstructionOccurrence(GrammaticalConstruction.TIME_FUTURE, depBegin, theVerb.endPosition(),
								dependency.toString());
						addConstructionOccurrence(GrammaticalConstruction.TENSE_FUTURE_SIMPLE, depBegin,
								theVerb.endPosition(), dependency.toString());
					} // past (negation or question)
					else if (dep.word().equalsIgnoreCase("did")) {
						// Past Simple negation or question
						addConstructionOccurrence(GrammaticalConstruction.ASPECT_SIMPLE, depBegin,
								theVerb.endPosition(), dependency.toString());
						addConstructionOccurrence(GrammaticalConstruction.TIME_PAST, depBegin, theVerb.endPosition(),
								dependency.toString());
						addConstructionOccurrence(GrammaticalConstruction.TENSE_PAST_SIMPLE, depBegin,
								theVerb.endPosition(), dependency.toString());
					} // present (negation or question)
					else if (dep.word().equalsIgnoreCase("do") || dep.word().equalsIgnoreCase("does")) {
						// no modals!
						// NOT incl. emphatic do
						List<ConstructionOccurrence> imperativeOccs = workingDoc
								.getConstructionData(GrammaticalConstruction.IMPERATIVES).getOccurrences();
						boolean imperativeFound = false;
						for (ConstructionOccurrence occ : imperativeOccs) {
							if (occ.getStart() == depBegin) {
								imperativeFound = true;
								break;
							}
						}
						if (!imperativeFound) {
							// Present Simple negation or question
							addConstructionOccurrence(GrammaticalConstruction.ASPECT_SIMPLE, depBegin,
									theVerb.endPosition(), dependency.toString());
							addConstructionOccurrence(GrammaticalConstruction.TIME_PRESENT, depBegin,
									theVerb.endPosition(), dependency.toString());
							addConstructionOccurrence(GrammaticalConstruction.TENSE_PRESENT_SIMPLE, depBegin,
									theVerb.endPosition(), dependency.toString());
						}
					}

					baseFormFound = false;
					verbIndex = -1; // tense found - Future/Past/Present Simple
					theVerb = null;
				}

				//// >>> Perfect aspect
				// after aux(done,will) is found: Future Perfect, Passive
				if (willDoneFound) {
					if (dep.word().equalsIgnoreCase("have")) {
						// Future Perfect, Passive (perfect)
						willHaveDoneFound = true;
						haveHasDoneFound = true;
					} // Passive (simple): just in case: normally under auxpass() dependency
					else if (dep.word().equalsIgnoreCase("be") || dep.word().equalsIgnoreCase("get")) {
						addConstructionOccurrence(GrammaticalConstruction.PASSIVE_VOICE, start, end,
								dependency.toString());
						willDoneFound = false;
						willHaveDoneFound = false;
						willStart = -1;
					}

				} // after willHaveDoneFound is found : Future Perfect, Passive (perfect)
				else if (willHaveDoneFound) {
					// >>> Passive (perfect): just in case: normally under auxpass() dependency
					if (dep.word().equalsIgnoreCase("been")) {
						addConstructionOccurrence(GrammaticalConstruction.PASSIVE_VOICE, start, end,
								dependency.toString());
					} else if (willStart == -1) {
						addConstructionOccurrence(GrammaticalConstruction.TIME_FUTURE, willStart, end,
								dependency.toString());
						addConstructionOccurrence(GrammaticalConstruction.TENSE_FUTURE_PERFECT, willStart, end,
								dependency.toString());
						addConstructionOccurrence(GrammaticalConstruction.ASPECT_PERFECT, willStart, end,
								dependency.toString());
					}

					willHaveDoneFound = false;
					willDoneFound = false;
					// TODO: check if it should be here
					pastPartFound = false;
					verbIndex = -1;
					theVerb = null;
					haveStart = -1;
					willStart = -1;
				} // after aux(done,had) is found : Past Perfect, Passive
				// but we're at aux() dependency now: in case of Past Perfect, we wouldn't be here
				else if (hadDoneFound) {
					// Passive (perfect) : just in case: normally under auxpass() dependency
					if (dep.word().equalsIgnoreCase("been") || dep.word().equalsIgnoreCase("got")
							|| dep.word().equalsIgnoreCase("gotten")) {
						if (gov.tag().equalsIgnoreCase("vbn")) {
							addConstructionOccurrence(GrammaticalConstruction.PASSIVE_VOICE, start, end,
									dependency.toString());
						}
					}
				} // after aux(done,have/has) is found : Present Perfect, Passive
				else if (haveHasDoneFound) {
					// Passive (perfect) : just in case: normally under auxpass() dependency
					if (dep.word().equalsIgnoreCase("been") || dep.word().equalsIgnoreCase("got")
							|| dep.word().equalsIgnoreCase("gotten")) {
						if (gov.tag().equalsIgnoreCase("vbn")) {
							addConstructionOccurrence(GrammaticalConstruction.PASSIVE_VOICE, start, end,
									dependency.toString());
						}
					}
				} // ? right after "done" as main verb is found
				else if (!(willDoneFound || hadDoneFound || haveHasDoneFound)) {
					// future
					if (pastPartFound && dep.word().equalsIgnoreCase("will")
							|| (dep.word().equalsIgnoreCase("shall") && dep.index() > 1)) { // indices start at 1 !!!
						// can still be: Future Perfect, Passive
						willDoneFound = true;
						willStart = dep.beginPosition();
					} // past
					else if (dep.word().equalsIgnoreCase("had")) {

						// can still be: Past Perfect, Passive
						// sometimes the order is different:
						// root(ROOT-0, renting-10)
						// rcmod(foreigners-4, moved-7)
						// nsubj(moved-7, who-5)
						// aux(moved-7, have-6)
						if (pastPartFound && theVerb != null && gov.beginPosition() == theVerb.beginPosition()) {
							haveStart = dep.beginPosition();
							addConstructionOccurrence(GrammaticalConstruction.TIME_PAST, haveStart,
									theVerb.endPosition(), dependency.toString());
							addConstructionOccurrence(GrammaticalConstruction.TENSE_PAST_PERFECT, haveStart,
									theVerb.endPosition(), dependency.toString());
							addConstructionOccurrence(GrammaticalConstruction.ASPECT_PERFECT, haveStart,
									theVerb.endPosition(), dependency.toString());
							// hadDoneFound = false;
							// haveStart = -1;
							// pastPartFound = false;
						} else {
							hadDoneFound = true;
							haveStart = dep.beginPosition();
							theVerb = gov;
							verbIndex = gov.index();
						}
					} // present
					else if (dep.word().equalsIgnoreCase("have") || dep.word().equalsIgnoreCase("has")) {
						// no modals!
						// can still be: Present Perfect, Passive
						// sometimes the order is different:
						// root(ROOT-0, renting-10)
						// rcmod(foreigners-4, moved-7)
						// nsubj(moved-7, who-5)
						// aux(moved-7, have-6)
						if (pastPartFound && theVerb != null && gov.beginPosition() == theVerb.beginPosition()) {
							haveStart = dep.beginPosition();
							addConstructionOccurrence(GrammaticalConstruction.TIME_PRESENT, haveStart,
									theVerb.endPosition(), dependency.toString());
							addConstructionOccurrence(GrammaticalConstruction.TENSE_PRESENT_PERFECT, haveStart,
									theVerb.endPosition(), dependency.toString());
							addConstructionOccurrence(GrammaticalConstruction.ASPECT_PERFECT, haveStart,
									theVerb.endPosition(), dependency.toString());
							// haveHasDoneFound = false;
							// haveStart = -1;
							// pastPartFound = false;
						} else {
							haveHasDoneFound = true;
							haveStart = dep.beginPosition();
							theVerb = gov;
							verbIndex = gov.index();
						}
					}
				}

				//// >>> Progressive aspect : Present/Past/Future Progressive, Present/Past/Future Perfect Progressive,
				if ((ingFound && theVerb != null) || gov.tag().equalsIgnoreCase("vbg")) {
					if (willDoingFound) {
						if (dep.word().equalsIgnoreCase("be")) {
							// Future Progressive
							addConstructionOccurrence(GrammaticalConstruction.TIME_FUTURE, willStart, gov.endPosition(),
									dependency.toString());
							addConstructionOccurrence(GrammaticalConstruction.TENSE_FUTURE_PROGRESSIVE, willStart,
									gov.endPosition(), dependency.toString());
							addConstructionOccurrence(GrammaticalConstruction.ASPECT_PROGRESSIVE, willStart,
									gov.endPosition(), dependency.toString());
						} else if (dep.word().equalsIgnoreCase("have")) {
							addConstructionOccurrence(GrammaticalConstruction.TIME_FUTURE, willStart, gov.endPosition(),
									dependency.toString());
							addConstructionOccurrence(GrammaticalConstruction.TENSE_FUTURE_PERFECT_PROGRESSIVE,
									willStart, gov.endPosition(), dependency.toString());
							addConstructionOccurrence(GrammaticalConstruction.ASPECT_PERFECT_PROGRESSIVE, willStart,
									gov.endPosition(), dependency.toString());
						}

						willDoingFound = false;
						willStart = -1;
						verbIndex = -1;
						theVerb = null;
					} else if (dep.word().equalsIgnoreCase("will")) {
						// >>> Future Progressive, Future Perfect Progressive
						willDoingFound = true;
						willStart = dep.beginPosition();
					} else if (dep.word().equalsIgnoreCase("am") || dep.word().equalsIgnoreCase("are")
							|| dep.word().equalsIgnoreCase("is")) {
						// >>> Present Progressive
						addConstructionOccurrence(GrammaticalConstruction.TIME_PRESENT, depBegin, gov.endPosition(),
								dependency.toString());
						addConstructionOccurrence(GrammaticalConstruction.ASPECT_PROGRESSIVE, depBegin,
								gov.endPosition(), dependency.toString());
						addConstructionOccurrence(GrammaticalConstruction.TENSE_PRESENT_PROGRESSIVE, depBegin,
								gov.endPosition(), dependency.toString());
						verbIndex = -1;
						theVerb = null;
					} else if (dep.word().equalsIgnoreCase("was") || dep.word().equalsIgnoreCase("were")) {
						// >>> Past Progressive
						addConstructionOccurrence(GrammaticalConstruction.TIME_PAST, depBegin, gov.endPosition(),
								dependency.toString());
						addConstructionOccurrence(GrammaticalConstruction.ASPECT_PROGRESSIVE, depBegin,
								gov.endPosition(), dependency.toString());
						addConstructionOccurrence(GrammaticalConstruction.TENSE_PAST_PROGRESSIVE, depBegin,
								gov.endPosition(), dependency.toString());
						verbIndex = -1;
						theVerb = null;
					} else if ((!willDoingFound)
							&& (dep.word().equalsIgnoreCase("have") || dep.word().equalsIgnoreCase("has"))) {
						// >>> Present Perfect Progressive
						addConstructionOccurrence(GrammaticalConstruction.TIME_PRESENT, depBegin, gov.endPosition(),
								dependency.toString());
						addConstructionOccurrence(GrammaticalConstruction.ASPECT_PERFECT_PROGRESSIVE, depBegin,
								gov.endPosition(), dependency.toString());
						addConstructionOccurrence(GrammaticalConstruction.TENSE_PRESENT_PERFECT_PROGRESSIVE, depBegin,
								gov.endPosition(), dependency.toString());
						verbIndex = -1;
						theVerb = null;
						haveHasDoneFound = false;
						haveStart = -1;
					} else if ((!willDoingFound) && (dep.word().equalsIgnoreCase("had"))) {
						// >>> Past Perfect Progressive
						addConstructionOccurrence(GrammaticalConstruction.TIME_PAST, depBegin, gov.endPosition(),
								dependency.toString());
						addConstructionOccurrence(GrammaticalConstruction.ASPECT_PERFECT_PROGRESSIVE, depBegin,
								gov.endPosition(), dependency.toString());
						addConstructionOccurrence(GrammaticalConstruction.TENSE_PAST_PERFECT_PROGRESSIVE, depBegin,
								gov.endPosition(), dependency.toString());
						verbIndex = -1;
						theVerb = null;
						hadDoneFound = false;
						haveStart = -1;
					}

					ingFound = false;
				}
				//// >>> passive
			} else if (rel.equalsIgnoreCase("auxpass")) {
				// only with verb==root! TODO change!

				if (dep.word().equalsIgnoreCase("was") || dep.word().equalsIgnoreCase("were")) {
					addConstructionOccurrence(GrammaticalConstruction.ASPECT_SIMPLE, dep.beginPosition(),
							gov.endPosition(), dependency.toString());
					addConstructionOccurrence(GrammaticalConstruction.TIME_PAST, dep.beginPosition(), gov.endPosition(),
							dependency.toString());
					addConstructionOccurrence(GrammaticalConstruction.TENSE_PAST_SIMPLE, dep.beginPosition(),
							gov.endPosition(), dependency.toString());
				} else if (dep.word().equalsIgnoreCase("is") || dep.word().equalsIgnoreCase("am")
						|| dep.word().equalsIgnoreCase("are")) {
					addConstructionOccurrence(GrammaticalConstruction.ASPECT_SIMPLE, dep.beginPosition(),
							gov.endPosition(), dependency.toString());
					addConstructionOccurrence(GrammaticalConstruction.TIME_PRESENT, dep.beginPosition(),
							gov.endPosition(), dependency.toString());
					addConstructionOccurrence(GrammaticalConstruction.TENSE_PRESENT_SIMPLE, dep.beginPosition(),
							gov.endPosition(), dependency.toString());
				}

				addConstructionOccurrence(GrammaticalConstruction.PASSIVE_VOICE, start, end, dependency.toString());
				pastPartFound = false;
				verbIndex = -1;
				theVerb = null;

				if (willHaveDoneFound) {
					addConstructionOccurrence(GrammaticalConstruction.TIME_FUTURE, willStart, end,
							dependency.toString());
					addConstructionOccurrence(GrammaticalConstruction.ASPECT_PERFECT, willStart, end,
							dependency.toString());
					addConstructionOccurrence(GrammaticalConstruction.TENSE_FUTURE_PERFECT, willStart, end,
							dependency.toString());
					willHaveDoneFound = false;
					willDoneFound = false;
					haveHasDoneFound = false;
					willStart = -1;
					haveStart = -1;
				}

				if (haveHasDoneFound) {
					haveHasDoneFound = false;
					haveStart = -1;
				}

				if (hadDoneFound) {
					hadDoneFound = false;
					haveStart = -1;
				}

			} else if (theVerb != null && verbIndex > 0 && haveHasDoneFound && (!rel.equalsIgnoreCase("auxpass"))) {
				if (willHaveDoneFound) {
					// >>> Future Perfect
					addConstructionOccurrence(GrammaticalConstruction.TIME_FUTURE, willStart, theVerb.endPosition(),
							dependency.toString());
					addConstructionOccurrence(GrammaticalConstruction.ASPECT_PERFECT, willStart, theVerb.endPosition(),
							dependency.toString());
					addConstructionOccurrence(GrammaticalConstruction.TENSE_FUTURE_PERFECT, willStart,
							theVerb.endPosition(), dependency.toString());
					willHaveDoneFound = false;
					willDoneFound = false;
					willStart = -1;
				} else {
					// >>> Present Perfect
					addConstructionOccurrence(GrammaticalConstruction.TIME_PRESENT, haveStart, theVerb.endPosition(),
							dependency.toString());
					addConstructionOccurrence(GrammaticalConstruction.ASPECT_PERFECT, haveStart, theVerb.endPosition(),
							dependency.toString());
					addConstructionOccurrence(GrammaticalConstruction.TENSE_PRESENT_PERFECT, haveStart,
							theVerb.endPosition(), dependency.toString());
				}
				haveHasDoneFound = false;

				verbIndex = -1;
				theVerb = null;
				haveStart = -1;
			} else if (theVerb != null && verbIndex > 0 && hadDoneFound && (!rel.equalsIgnoreCase("auxpass"))) {
				// >>> Past Perfect
				addConstructionOccurrence(GrammaticalConstruction.TIME_PAST, haveStart, theVerb.endPosition(),
						dependency.toString());
				addConstructionOccurrence(GrammaticalConstruction.ASPECT_PERFECT, haveStart, theVerb.endPosition(),
						dependency.toString());
				addConstructionOccurrence(GrammaticalConstruction.TENSE_PAST_PERFECT, haveStart, theVerb.endPosition(),
						dependency.toString());
				hadDoneFound = false;
				haveStart = -1;
				verbIndex = -1;
				theVerb = null;
			} else if (rel.equalsIgnoreCase("cop")) {
				if (haveHasDoneFound) {
					addConstructionOccurrence(GrammaticalConstruction.TIME_PRESENT, start, end, dependency.toString());
					addConstructionOccurrence(GrammaticalConstruction.TENSE_PRESENT_PERFECT, start, end,
							dependency.toString());
					addConstructionOccurrence(GrammaticalConstruction.ASPECT_PERFECT, start, end,
							dependency.toString());
				} else if (hadDoneFound) {
					addConstructionOccurrence(GrammaticalConstruction.TIME_PAST, start, end, dependency.toString());
					addConstructionOccurrence(GrammaticalConstruction.TENSE_PAST_PERFECT, start, end,
							dependency.toString());
					addConstructionOccurrence(GrammaticalConstruction.ASPECT_PERFECT, start, end,
							dependency.toString());
				} else {
					switch (dep.tag().toLowerCase()) {
					case "vbp":
					case "vbz":
						addConstructionOccurrence(GrammaticalConstruction.ASPECT_SIMPLE, start, end,
								dependency.toString());
						addConstructionOccurrence(GrammaticalConstruction.TENSE_PRESENT_SIMPLE, start, end,
								dependency.toString());
						addConstructionOccurrence(GrammaticalConstruction.TIME_PRESENT, start, end,
								dependency.toString());
						break;
					case "vbd":
						addConstructionOccurrence(GrammaticalConstruction.ASPECT_SIMPLE, start, end,
								dependency.toString());
						addConstructionOccurrence(GrammaticalConstruction.TENSE_PAST_SIMPLE, start, end,
								dependency.toString());
						addConstructionOccurrence(GrammaticalConstruction.TIME_PAST, start, end, dependency.toString());
						break;
					case "vb":
						if (willCopulaFound) {
							addConstructionOccurrence(GrammaticalConstruction.ASPECT_SIMPLE, start, end,
									"will " + dependency.toString());
							addConstructionOccurrence(GrammaticalConstruction.TENSE_FUTURE_SIMPLE, willStart, end,
									"will " + dependency.toString());
							addConstructionOccurrence(GrammaticalConstruction.TIME_FUTURE, willStart, end,
									"will " + dependency.toString());
						}
						willCopulaFound = false;
						willStart = -1;
						break;
					}
				}
			}
		}
	}

	private boolean[] findIngPastPartBaseForTenses(TypedDependency dependency, IndexedWord verb, boolean[] found) {

		int verbBegin = verb.beginPosition();
		int verbEnd = verb.endPosition();

		boolean ingFound = found[0];
		boolean pastPartFound = found[1];
		boolean baseFormFound = found[2];

		if (verb.tag().equalsIgnoreCase("vbg")) {
			// check for Progressive aspect
			// can still be: Present/Past/Future Progressive, Present/Past/Future Perfect Progressive
			ingFound = true;
			pastPartFound = false;
			baseFormFound = false;
		} else if (verb.tag().equalsIgnoreCase("vbn")) {
			// check for Perfect aspect
			// can still be: Past Perfect, Present Perfect, Future Perfect, Passive
			ingFound = false;
			pastPartFound = true;
			baseFormFound = false;
		} else if (verb.tag().equalsIgnoreCase("vb")) {
			// base form
			// can still be: Past Simple (negation), Future Simple, Present Simple (negation)
			// cannot be Present Simple : VBP or VBZ instead
			// examples: I didn't do it // I will do it // I don't know
			// addConstructionOccurrence(GrammaticalConstruction.ASPECT_SIMPLE, verbBegin, verbEnd, dependency.toString());
			ingFound = false;
			pastPartFound = false;
			baseFormFound = true;
		} else if (verb.tag().equalsIgnoreCase("vbp") || verb.tag().equalsIgnoreCase("vbz")) {
			// single or plural present
			addConstructionOccurrence(GrammaticalConstruction.ASPECT_SIMPLE, verbBegin, verbEnd, dependency.toString());
			addConstructionOccurrence(GrammaticalConstruction.TIME_PRESENT, verbBegin, verbEnd, dependency.toString());
			addConstructionOccurrence(GrammaticalConstruction.TENSE_PRESENT_SIMPLE, verbBegin, verbEnd,
					dependency.toString());
			ingFound = false;
			pastPartFound = false;
			baseFormFound = false;
		} else if (verb.tag().equalsIgnoreCase("vbd")) {
			// past form
			addConstructionOccurrence(GrammaticalConstruction.ASPECT_SIMPLE, verbBegin, verbEnd, dependency.toString());
			addConstructionOccurrence(GrammaticalConstruction.TIME_PAST, verbBegin, verbEnd, dependency.toString());
			addConstructionOccurrence(GrammaticalConstruction.TENSE_PAST_SIMPLE, verbBegin, verbEnd,
					dependency.toString());
			ingFound = false;
			pastPartFound = false;
			baseFormFound = false;
		}

		found[0] = ingFound;
		found[1] = pastPartFound;
		found[2] = baseFormFound;

		return found;
	}

	private void identifyConditionals(List<CoreLabel> labeledWords, Collection<TypedDependency> dependencies) {
		int verbBegin = -1;
		int verbInd = -1;
		int continuousInd = -1;
		boolean continuous = false;
		boolean mark_found = false;
		boolean would_found = false;
		int startInd = labeledWords.get(0).beginPosition();
		int endInd = labeledWords.get(labeledWords.size() - 1).endPosition();

		for (TypedDependency dependency : dependencies) {
			String rel = dependency.reln().getShortName().toLowerCase(); // dependency relation
			IndexedWord gov = dependency.gov();
			IndexedWord dep = dependency.dep();

			if ((dep.word() != null) && (gov.tag() != null)) {
				// retrieve all real conditionals using the conditional 'if' clause
				if (rel.equalsIgnoreCase("mark")) {
					verbBegin = gov.beginPosition(); // mark(do-2,if-1)
					verbInd = gov.index();
					// e.g. If they call, ... // If it rains ... // If I should go ...
					// but: "If you didn't have a child now ..." -> mark(have,if) :/ - adds to condReal imediately, skips the would-clause
					// TODO: but: "I don't know if he knows it." - treated as conditional! (ambiguous!)
					if (gov.tag().equalsIgnoreCase("vb") || gov.tag().equalsIgnoreCase("vbp")
							|| gov.tag().equalsIgnoreCase("vbz") || gov.tag().equalsIgnoreCase("should")) {
						// filter out: "If you didn't have a child now, you wouldn't..." as well as "If you did know, you would..."
						// i.e. don't skip the would-clause

						// indices start at 1 !!!
						if (!((verbInd >= 2 && labeledWords.get(verbInd - 2).word().equalsIgnoreCase("did"))
								|| ((verbInd >= 3) && labeledWords.get(verbInd - 3).word().equalsIgnoreCase("did")))) {
							addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS_REAL, startInd, endInd,
									labeledWords.toString());
							addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS, startInd, endInd,
									labeledWords.toString());
							break; // don't look at the 'would' clause
						}
					} else if (gov.tag().equalsIgnoreCase("vbg")) {
						// continuous tense
						continuous = true;
						continuousInd = verbInd;
					}

					mark_found = true;
				} // retrieve all unreal conditionals using the 'would' clause
				else if (rel.equals("aux")) {
					// aux(watched-4, would-2) // aux(travel-4, to-3)
					// conditionals
					if (dep.word().equalsIgnoreCase("would") // or only would?
							|| dep.word().equalsIgnoreCase("should") || dep.word().equalsIgnoreCase("could")
							|| dep.word().equalsIgnoreCase("might") || dep.word().equalsIgnoreCase("may")) {
						would_found = true;
						if (gov.tag().equalsIgnoreCase("VBN")) {
							// past participle form: "would have gone"
							addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS_UNREAL, startInd, endInd,
									labeledWords.toString());
							addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS, startInd, endInd,
									labeledWords.toString());
							break; // with fetch only 1 conditional per sentence - which is fine
							// but: (won't catch mixedConditionals: If you had done it, you wouldn't regret it now.)
							// TODO
						} else if (gov.tag().equalsIgnoreCase("VB")) {
							// base form: "would go"
							// constructions.get("condII").incrementCount(docNum, labeledWords.toString());
							addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS_UNREAL, startInd, endInd,
									labeledWords.toString());
							addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS, startInd, endInd,
									labeledWords.toString());
							break; // with fetch only 1 conditional per sentence - which is fine
							// but: (won't catch mixedConditionals: If you had done it, you wouldn't regret it now.)
							// TODO
						}
					} // fetch Present Perfect: "If you have lost...", "If he's won..."
					else if ((dep.tag().equalsIgnoreCase("vb") || dep.tag().equalsIgnoreCase("vbp")
							|| dep.tag().equalsIgnoreCase("vbz")) && (dep.lemma().equalsIgnoreCase("have"))) {
						if (gov.beginPosition() == verbBegin) {
							mark_found = false;
							addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS_REAL, startInd, endInd,
									labeledWords.toString());
							addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS, startInd, endInd,
									labeledWords.toString());
							break; // don't look at the 'would' clause
						}
					} // fetch: continuous (present and past)
					else if (continuous && (gov.index() == continuousInd)) {
						if (dep.tag().equalsIgnoreCase("vbd") || dep.tag().equalsIgnoreCase("vbn")) {
							// past
							mark_found = false;
							addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS_UNREAL, startInd, endInd,
									labeledWords.toString());
							addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS, startInd, endInd,
									labeledWords.toString());
							break;
						} else if (dep.tag().equalsIgnoreCase("vb") || dep.tag().equalsIgnoreCase("vbp")
								|| dep.tag().equalsIgnoreCase("vbz")) {
							// present
							mark_found = false;
							addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS_REAL, startInd, endInd,
									labeledWords.toString());
							addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS, startInd, endInd,
									labeledWords.toString());
							break;
						}
					}
				}
			}
		}

		// fetch present and past real conditional: "If you didn't go there, you didn't see him"
		if (mark_found && !would_found) {
			addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS_REAL, startInd, endInd,
					labeledWords.toString());
			addConstructionOccurrence(GrammaticalConstruction.CONDITIONALS, startInd, endInd, labeledWords.toString());
		} else if (continuous) {
			// TODO ???
		}
	}

	private void findQuestionOrImperative(int startInd, int endInd) {
		// find the first and the last word (skip bullets, quotes, etc.)
		CoreLabel firstWord = null;
		for (CoreLabel w : wordsOutput) {
			if (w.word() != null && w.word().toLowerCase().matches("[a-z]*")) {
				// first real word
				firstWord = w;
				break;
			}
		}

		if (firstWord == null) {
			return;
		}

		// find the last token (punctuation mark) (don't take quotes into account)
		CoreLabel lastWord = null;
		for (int i = wordsOutput.size() - 1; i > 0; i--) {
			if (wordsOutput.get(i).tag() != null && wordsOutput.get(i).tag().equalsIgnoreCase(".")) {
				lastWord = wordsOutput.get(i);
				break;
			}
		}

		//// find questions and imperatives
		if (lastWord != null && lastWord.word() != null) {
			// questions
			if (lastWord.word().toLowerCase().endsWith("?")) {
				// question (not indirect!)
				// indirectQuestions - ??? not here!
				inspectQuestion(firstWord);
			} else {
				// use tregex to find imperatives within the sentence
				// a sentence that has a vp that is NOT preceded by a sister NP
				String pattern = "/^S.*/ !$ /NP$/ < (VP=imperativeVerb !$-- /^N.*/)";
				TregexPattern impPattern = TregexPattern.compile(pattern);
				TregexMatcher tregexImperative = impPattern.matcher(treeOutput);
				while (tregexImperative.find()) {
					Tree imperativeVerb = tregexImperative.getNode("imperativeVerb");
					CoreLabel impFirstWord = imperativeVerb.taggedLabeledYield().get(0);
					if (impFirstWord.tag().equalsIgnoreCase("vb") || impFirstWord.tag().equalsIgnoreCase("vbp")) { // the verb is sometimes annotated as VBP by the parser
						int impStart = impFirstWord.beginPosition();
						int impEnd = imperativeVerb.taggedLabeledYield()
								.get(imperativeVerb.taggedLabeledYield().size() - 1).endPosition();
						addConstructionOccurrence(GrammaticalConstruction.IMPERATIVES, impStart, impEnd,
								wordsOutput.toString());
					}
				}
			}
		}
	}

	private void inspectQuestion(CoreLabel firstWord) {
		int startInd = wordsOutput.get(0).beginPosition();
		int endInd = wordsOutput.get(wordsOutput.size() - 1).endPosition();

		//// direct question
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_DIRECT, startInd, endInd, wordsOutput.toString());

		if (firstWord == null) {
			return;
		}

		String firstWordTag = firstWord.tag();
		String firstWordWord = firstWord.word().toLowerCase();

		//// tag questions
		findTagQuestion(endInd);

		//// wh-question
		if (firstWordTag.toLowerCase().startsWith("w")) {
			findWhQuestion(firstWordWord, startInd, endInd);
		} //// yesNoQuestions (starts with a verb (or modal))
		else if (firstWordTag.toLowerCase().startsWith("vb")) {
			findYesNoQuestion(firstWord, startInd, endInd);
		} // modal question
		else if (firstWordTag.toLowerCase().contains("md")) {
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_YESNO, startInd, endInd,
					wordsOutput.toString());
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_MODAL, startInd, endInd,
					wordsOutput.toString());
		} else {
			// it can still be the case that it is a wh- or yes-no question but it doesn't start at the beginning of a sentence
			// e.g.: That person has to wonder 'What did I do wrong?'
			// TODO: At ..., _do_ you know _what_ I mean?"
			findInlineQuestion(startInd, endInd);
		}
	}

	private void findTagQuestion(int endInd) {
		// look at the word right before the "?"
		CoreLabel lastWord = null;
		CoreLabel qMark = null; // it must be there
		int count = 0; // count the words between ? and ,
		for (int i = wordsOutput.size() - 1; i > 0; i--) {
			CoreLabel label = wordsOutput.get(i);
			String tag = label.tag();
			String word = label.word().toLowerCase();

			if (qMark == null && tag.equalsIgnoreCase(".") && word.contains("?")) {
				qMark = label;
				count++;
			} else if (qMark != null && (tag.equalsIgnoreCase("prp") || word.equalsIgnoreCase("not"))) {
				// check for the pronoun or the negation (do they not?)
				lastWord = label;
				count++;
			} else if (lastWord != null && (!tag.equalsIgnoreCase(","))) // check for the negation or for the verb
			{
				count++;
			} else if (lastWord != null && tag.equalsIgnoreCase(",")) {
				if (count < 5) {
					addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TAG, label.beginPosition(), endInd,
							wordsOutput.toString()); // highlight only the tag
				} else {
					break;
				}
			} else {
				break;
			}
		}
	}

	private void findWhQuestion(String firstWordWord, int startInd, int endInd) {
		// only for wh-questions
		boolean rootFound = false;
		// in case there is a question phrase at the beginning ("What kind of music do you like?") - go from the ROOT to find the verb
		// toBeQuestions; toDoQuestions; toHaveQuestions // direct: "What's this?" - look into the dependencies: the root should be a verb -> aux(verb, ??)
		int depStartInd = 0;
		for (TypedDependency dependency : depsOutput) {
			String rel = dependency.reln().getShortName();
			IndexedWord dep = dependency.dep();
			IndexedWord gov = dependency.gov();

			if ((!rootFound) && rel.equalsIgnoreCase("root")) {
				rootFound = true;
				depStartInd = dep.beginPosition();
			}

			if (rootFound
					&& (rel.equalsIgnoreCase("aux") || rel.equalsIgnoreCase("cop") || rel.equalsIgnoreCase("auxpass"))
					&& (dep.beginPosition() == depStartInd || dependency.gov().beginPosition() == depStartInd)) {
				// check the dep of aux()
				if (dep.lemma().equalsIgnoreCase("be") || gov.lemma().equalsIgnoreCase("be")) {
					// it might not get here
					addWhQuestion(firstWordWord, startInd, endInd);
					addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TO_BE, startInd, endInd,
							wordsOutput.toString());
				} else if (dep.lemma().equalsIgnoreCase("do") || gov.lemma().equalsIgnoreCase("do")) {
					addWhQuestion(firstWordWord, startInd, endInd);
					addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TO_DO, startInd, endInd,
							wordsOutput.toString());
				} else if (dep.lemma().equalsIgnoreCase("have") || gov.lemma().equalsIgnoreCase("have")) {
					addWhQuestion(firstWordWord, startInd, endInd);
					addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TO_HAVE, startInd, endInd,
							wordsOutput.toString());
				}

				break;
			}
		}
	}

	private void addWhQuestion(String qWord, int startInd, int endInd) {
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WH, startInd, endInd, qWord);
		switch (qWord.toLowerCase()) {
		case "what":
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHAT, startInd, endInd, qWord);
			break;
		case "how":
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_HOW, startInd, endInd, qWord);
			break;
		case "why":
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHY, startInd, endInd, qWord);
			break;
		case "who":
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHO, startInd, endInd, qWord);
			break;
		case "where":
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHERE, startInd, endInd, qWord);
			break;
		case "when":
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHEN, startInd, endInd, qWord);
			break;
		case "whose":
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHOSE, startInd, endInd, qWord);
			break;
		case "whom":
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHOM, startInd, endInd, qWord);
			break;
		case "which":
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHICH, startInd, endInd, qWord);
			break;
		default:
			System.out.println("Unknown wh- word: " + qWord);

		}
	}

	private void findYesNoQuestion(CoreLabel firstWord, int startInd, int endInd) {
		if (firstWord.lemma().equalsIgnoreCase("be")) {
			// toBeQuestions;
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_YESNO, startInd, endInd,
					wordsOutput.toString());
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TO_BE, startInd, endInd,
					wordsOutput.toString());
		} else if (firstWord.lemma().equalsIgnoreCase("do")) {
			// simple
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_YESNO, startInd, endInd,
					wordsOutput.toString());
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TO_DO, startInd, endInd,
					wordsOutput.toString());
		} else if (firstWord.lemma().equalsIgnoreCase("have")) {
			// perfect
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_YESNO, startInd, endInd,
					wordsOutput.toString());
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TO_HAVE, startInd, endInd,
					wordsOutput.toString());
		}

	}

	private void findInlineQuestion(int startInd, int endInd) {
		CoreLabel questionWord = null;
		for (CoreLabel aWord : wordsOutput) {
			// look for a question word
			if ((questionWord == null) && (aWord.tag().toLowerCase().startsWith("w"))) {
				questionWord = aWord;
			} else if (questionWord != null) {
				// if question word found
				if (aWord.tag().toLowerCase().startsWith("vb")) {
					if (aWord.lemma().equalsIgnoreCase("be")) {
						// toBeQuestions;
						addWhQuestion(questionWord.word().toLowerCase(), startInd, endInd);
						addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TO_BE, startInd, endInd,
								wordsOutput.toString());
						break;
					} else if (aWord.lemma().equalsIgnoreCase("do")) {
						// simple
						addWhQuestion(questionWord.word().toLowerCase(), startInd, endInd);
						addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TO_DO, startInd, endInd,
								wordsOutput.toString());
						break;
					} else if (aWord.lemma().equalsIgnoreCase("have")) {
						// perfect
						addWhQuestion(questionWord.word().toLowerCase(), startInd, endInd);
						addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TO_HAVE, startInd, endInd,
								wordsOutput.toString());
						break;
					}
				} else if (aWord.tag().toLowerCase().startsWith("md")) {
					addWhQuestion(questionWord.word().toLowerCase(), startInd, endInd);
					addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_MODAL, startInd, endInd,
							wordsOutput.toString());
				}
			}
		}
	}

	private void findUsedTo(CoreLabel label, String labelWord, String labelTag) {
		if (labelTag.equalsIgnoreCase("to")) // allows for elliptical structures, e.g., "yes, I used to."
		{
			addConstructionOccurrence(GrammaticalConstruction.VERBCONST_USED_TO,
					wordsOutput.get(label.index() - 2).beginPosition(), label.endPosition(), "used " + labelWord);
		}
		usedFound = false;
	}

	private void findGoingTo(CoreLabel label, String labelWord, String labelTag) {
		switch (goingToFound) {
		case 1:
			// only "going" found
			if (labelTag.equalsIgnoreCase("to")) {
				goingToFound++;
				// indices start at 1 !!! : wordsOutput.get(label.index()) will be the word following the current "label" word
				if (label.index() < wordsOutput.size() - 1 && ".,!?;:)".contains(wordsOutput.get(label.index()).tag())) {
					// catches "yes, I'm going to."
					// indices start at 1 !!!
					addConstructionOccurrence(GrammaticalConstruction.VERBCONST_GOING_TO,
							wordsOutput.get(label.index() - 2).beginPosition(), label.endPosition(),
							"going " + labelWord);
					goingToFound = 0;
				}
			} else {
				goingToFound = 0;
			}
			break;
		case 2:
			// "going to" found
			if (labelTag.equalsIgnoreCase("vb")) {
				// can tell it from "I'm going to France" // can't catch "I'm going to _slowly_ start packing" - use Tregex?
				// indices start at 1 !!!
				addConstructionOccurrence(GrammaticalConstruction.VERBCONST_GOING_TO,
						wordsOutput.get(label.index() - 3).beginPosition(), label.endPosition(),
						"going to " + labelWord); // "going to V"
				goingToFound = 0;
			}
			break;
		default:
			goingToFound = 0;
			System.out.println("Too many goingToFound! Check!");
			break;
		}
	}

	private void findIngNounForms(CoreLabel label, String labelWord, String labelTag) {
		if (labelWord.endsWith("ing") && labelWord.length() > 4
				&& (!EnglishGrammaticalConstants.ING_NOUNS.contains(labelWord))) {
			addConstructionOccurrence(GrammaticalConstruction.NOUNFORMS_ING, label.beginPosition(), label.endPosition(),
					labelWord);
		}
		// plural noun forms
		if (labelTag.equalsIgnoreCase("nns")) {
			if (labelWord.endsWith("s")) {
				addConstructionOccurrence(GrammaticalConstruction.PLURAL_REGULAR, label.beginPosition(),
						label.endPosition(), labelWord);
			} else {
				addConstructionOccurrence(GrammaticalConstruction.PLURAL_IRREGULAR, label.beginPosition(),
						label.endPosition(), labelWord);
			}
		}
	}

	private void addModalVerb(CoreLabel label, String labelWord) {
		addConstructionOccurrence(GrammaticalConstruction.MODALS, label.beginPosition(), label.endPosition(),
				labelWord);
		switch (labelWord) {
		case "can":
		case "ca": // for negation: ca/MD n't/RB
			addConstructionOccurrence(GrammaticalConstruction.MODALS_SIMPLE, label.beginPosition(), label.endPosition(),
					labelWord);
			addConstructionOccurrence(GrammaticalConstruction.MODALS_CAN, label.beginPosition(), label.endPosition(),
					labelWord);
			break;
		case "must":
			addConstructionOccurrence(GrammaticalConstruction.MODALS_SIMPLE, label.beginPosition(), label.endPosition(),
					labelWord);
			addConstructionOccurrence(GrammaticalConstruction.MODALS_MUST, label.beginPosition(), label.endPosition(),
					labelWord);
			break;
		case "need":
			addConstructionOccurrence(GrammaticalConstruction.MODALS_SIMPLE, label.beginPosition(), label.endPosition(),
					labelWord);
			addConstructionOccurrence(GrammaticalConstruction.MODALS_NEED, label.beginPosition(), label.endPosition(),
					labelWord);
			break;
		case "may":
			addConstructionOccurrence(GrammaticalConstruction.MODALS_SIMPLE, label.beginPosition(), label.endPosition(),
					labelWord);
			addConstructionOccurrence(GrammaticalConstruction.MODALS_MAY, label.beginPosition(), label.endPosition(),
					labelWord);
			break;
		case "could":
			addConstructionOccurrence(GrammaticalConstruction.MODALS_ADVANCED, label.beginPosition(),
					label.endPosition(), labelWord);
			addConstructionOccurrence(GrammaticalConstruction.MODALS_COULD, label.beginPosition(), label.endPosition(),
					labelWord);
			break;
		case "might":
			addConstructionOccurrence(GrammaticalConstruction.MODALS_ADVANCED, label.beginPosition(),
					label.endPosition(), labelWord);
			addConstructionOccurrence(GrammaticalConstruction.MODALS_MIGHT, label.beginPosition(), label.endPosition(),
					labelWord);
			break;
		case "ought":
			addConstructionOccurrence(GrammaticalConstruction.MODALS_ADVANCED, label.beginPosition(),
					label.endPosition(), labelWord);
			addConstructionOccurrence(GrammaticalConstruction.MODALS_OUGHT, label.beginPosition(), label.endPosition(),
					labelWord);
			break;
		default:
			if (!(labelWord.equalsIgnoreCase("will") || labelWord.equalsIgnoreCase("shall")
					|| labelWord.equalsIgnoreCase("wo") || labelWord.equalsIgnoreCase("sha"))) {
				addConstructionOccurrence(GrammaticalConstruction.MODALS_ADVANCED, label.beginPosition(),
						label.endPosition(), labelWord);
			}
			break;
		}
	}

	private void findVerbFormsPOS(CoreLabel label, String labelWord, String labelTag) {
		if (labelWord.startsWith("'")) {
			addConstructionOccurrence(GrammaticalConstruction.VERBFORM_SHORT, label.beginPosition(),
					label.endPosition(), labelWord);
			// do NOT add auxiiaries here (can be main verb as well)
		} else {
			switch (labelWord) {
			case "are":
			case "is":
			case "am":
				addConstructionOccurrence(GrammaticalConstruction.VERBFORM_LONG, label.beginPosition(),
						label.endPosition(), labelWord);
				// do NOT add auxiiaries here (can be main verb as well)
				break;
			case "has":
			case "have":
			case "had":
				addConstructionOccurrence(GrammaticalConstruction.VERBFORM_LONG, label.beginPosition(),
						label.endPosition(), labelWord);
				// add modal "have to" here if the tag is MD
				int thisInd = label.index() - 1;
				if (wordsOutput.size() > thisInd + 1) {
					if (wordsOutput.get(thisInd + 1).tag().equalsIgnoreCase("to")) {
						// catch the elliptical "I have to." BUT not "I will give everything I have to John"
						addConstructionOccurrence(GrammaticalConstruction.MODALS, label.beginPosition(),
								wordsOutput.get(thisInd + 1).endPosition(), labelWord + " to");
						addConstructionOccurrence(GrammaticalConstruction.MODALS_ADVANCED, label.beginPosition(),
								wordsOutput.get(thisInd + 1).endPosition(), labelWord + " to");
						addConstructionOccurrence(GrammaticalConstruction.MODALS_HAVE_TO, label.beginPosition(),
								wordsOutput.get(thisInd + 1).endPosition(), labelWord + " to");
					}
				}

				break;
			case "used":
				if (labelTag.equalsIgnoreCase("vbd")) // excludes passive "it is used to"
				{
					usedFound = true;
				}
			}

			if (labelTag.equalsIgnoreCase("vbg")) {
				addConstructionOccurrence(GrammaticalConstruction.VERBFORM_ING, label.beginPosition(),
						label.endPosition(), labelWord);
				if (labelWord.equalsIgnoreCase("going") || labelWord.equalsIgnoreCase("gon")) {
					goingToFound++;
				}
			} else if (labelTag.equalsIgnoreCase("vbd") || labelTag.equalsIgnoreCase("vbn")) {
				if (!labelWord.endsWith("ed")) {
					addConstructionOccurrence(GrammaticalConstruction.VERBS_IRREGULAR, label.beginPosition(),
							label.endPosition(), labelWord);
				} else {
					addConstructionOccurrence(GrammaticalConstruction.VERBS_REGULAR, label.beginPosition(),
							label.endPosition(), labelWord);
				}
			}

		}
	}

	private void findVerbFormsDep(TypedDependency dependency, int start, int end, IndexedWord gov, IndexedWord dep,
	                              int depBegin, int depEnd) {
		// emphatic do (separately)
		if ((dep.lemma().equalsIgnoreCase("do")
				&& (gov.tag().equalsIgnoreCase("vb") || gov.tag().equalsIgnoreCase("vbp"))
				&& (dep.index() == gov.index() - 1))) {
			// "do" is followed by a verb
			addConstructionOccurrence(GrammaticalConstruction.VERBFORM_EMPATHIC_DO, start, end, dependency.toString());
		} else if (dep.lemma().equalsIgnoreCase("be") || dep.lemma().equalsIgnoreCase("have")
				|| dep.lemma().equalsIgnoreCase("do")) {
			addConstructionOccurrence(GrammaticalConstruction.VERBFORM_AUXILIARIES_BE_DO_HAVE, depBegin, depEnd,
					dependency.toString());
		}

		// to-infinitives
		if (dep.tag().equalsIgnoreCase("to")) {
			if (gov.tag().equalsIgnoreCase("vb")) {
				// "He wants to sleep"
				addConstructionOccurrence(GrammaticalConstruction.VERBFORM_TO_INFINITIVE, start, end,
						dependency.toString());
			} else {
				// "He wants to be a pilot" -> aux ( pilot-6 *!!!* , to-3 ) + cop ( pilot-6 , be-4 ) ** only with "be" as copula **
				// indices start at 1 !!!
				int toInd = dep.index() - 1;
				int govInd = gov.index();
				if (govInd - toInd > 1 && wordsOutput.get(toInd + 1).word().equalsIgnoreCase("be")) {
					addConstructionOccurrence(GrammaticalConstruction.VERBFORM_TO_INFINITIVE, dep.beginPosition(),
							wordsOutput.get(toInd + 1).endPosition(), dependency.toString());
				}
			}
		}
	}

	private void findPrepositions(TypedDependency dependency, int depBegin, int depEnd, String rel, IndexedWord dep,
	                              IndexedWord gov) {
		if (rel.contains("_")) {
			// collapsed dependencies
			int startPrep = -1;
			int endPrep = -1;

			// identify the start and end indices of this preposition/these prepositions
			if (rel.indexOf("_") == rel.lastIndexOf("_")) {
				// 1-word preposition
				String prep = rel.substring(rel.indexOf("_") + 1).toLowerCase();

				// go through wordsOutput to find the indices
				for (CoreLabel l : wordsOutput) {
					if (l.word().equalsIgnoreCase(prep)) {
						startPrep = l.beginPosition();
						endPrep = l.endPosition();

						// add constructions here: in case of a duplicate, it'll not be added anyway
						if (l.tag().equalsIgnoreCase("in")
								&& EnglishGrammaticalConstants.SIMPLE_PREPOSITIONS.contains(prep)) {
							addConstructionOccurrence(GrammaticalConstruction.PREPOSITIONS, startPrep, endPrep,
									dependency.toString());
							addConstructionOccurrence(GrammaticalConstruction.PREPOSITIONS_SIMPLE, startPrep, endPrep,
									dependency.toString());
						} else {
							addConstructionOccurrence(GrammaticalConstruction.PREPOSITIONS, startPrep, endPrep,
									dependency.toString());
							addConstructionOccurrence(GrammaticalConstruction.PREPOSITIONS_ADVANCED, startPrep, endPrep,
									dependency.toString());
						}
					}
				}

			} else if (rel.indexOf("_") > 0 && rel.indexOf("_") < rel.lastIndexOf("_")) {
				// n-word preposition

				// take the begin position of the 1st prep and the end position of the last prep
				String prep1 = rel.substring(rel.indexOf("_") + 1, rel.indexOf("_", rel.indexOf("_") + 1));
				String prep2 = rel.substring(rel.lastIndexOf("_") + 1);

				for (CoreLabel l : wordsOutput) {
					if (l.word().equalsIgnoreCase(prep1)) {
						// at first find the first prep - don't add constructions yet
						startPrep = l.beginPosition();
					} else if (l.tag().equalsIgnoreCase("in") && l.word().equalsIgnoreCase(prep2)) {
						endPrep = l.endPosition();
						if (startPrep > -1 && endPrep > -1 && endPrep > startPrep) {
							// add constructions here: in case of a duplicate, it'll not be added anyway
							addConstructionOccurrence(GrammaticalConstruction.PREPOSITIONS, startPrep, endPrep,
									dependency.toString());
							addConstructionOccurrence(GrammaticalConstruction.PREPOSITIONS_COMPLEX, startPrep, endPrep,
									dependency.toString());
							addConstructionOccurrence(GrammaticalConstruction.PREPOSITIONS_ADVANCED, startPrep, endPrep,
									dependency.toString());
							startPrep = -1;
							endPrep = -1;
						}
					}
				}

			}
		}

		/// objective pronouns after a preposition
		if (dep.tag().equalsIgnoreCase("prp")
				&& EnglishGrammaticalConstants.OBJECTIVE_PRONOUNS.contains(dep.word().toLowerCase())) {
			addConstructionOccurrence(GrammaticalConstruction.PRONOUNS, depBegin, depEnd, dep.word());
			addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_OBJECTIVE, depBegin, depEnd, dep.word());
		}
	}

	private void addNegation(TypedDependency dependency, IndexedWord dep, int depBegin, int depEnd) {
		addConstructionOccurrence(GrammaticalConstruction.NEGATION_NO_NOT_NEVER, depBegin, depEnd,
				dependency.toString());
		addConstructionOccurrence(GrammaticalConstruction.NEGATION_ALL, depBegin, depEnd, dependency.toString());

		if (dep.word().equalsIgnoreCase("n't")) {
			addConstructionOccurrence(GrammaticalConstruction.NEGATION_NT, depBegin, depEnd, dependency.toString());
		} else if (dep.word().equalsIgnoreCase("not")) {
			addConstructionOccurrence(GrammaticalConstruction.NEGATION_NOT, depBegin, depEnd, dependency.toString());
		}
	}

	private void addExistentialThere(TypedDependency dependency, int start, int end, IndexedWord gov) {
		addConstructionOccurrence(GrammaticalConstruction.EXISTENTIAL_THERE, start, end, dependency.toString());

		if (gov.word().equalsIgnoreCase("is") || gov.word().equalsIgnoreCase("are")) {
			addConstructionOccurrence(GrammaticalConstruction.THERE_IS_ARE, start, end, dependency.toString());
		} else if (gov.word().equalsIgnoreCase("was") || gov.word().equalsIgnoreCase("were")) {
			addConstructionOccurrence(GrammaticalConstruction.THERE_WAS_WERE, start, end, dependency.toString());
		}
	}

	private void addSubjectiveAndObjectivePronouns(TypedDependency dependency, int start, int end, String rel,
	                                               IndexedWord dep, int depBegin, int depEnd) {
		if (rel.equalsIgnoreCase("iobj")) {
			// indirect object
			addConstructionOccurrence(GrammaticalConstruction.OBJECT_INDIRECT, start, end, dependency.toString());

			if (dep.tag().equalsIgnoreCase("prp")
					&& EnglishGrammaticalConstants.OBJECTIVE_PRONOUNS.contains(dep.word().toLowerCase())) {
				addConstructionOccurrence(GrammaticalConstruction.PRONOUNS, depBegin, depEnd, dependency.toString());
				addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_OBJECTIVE, depBegin, depEnd,
						dependency.toString());
			}
		} else if (rel.equalsIgnoreCase("dobj")) {
			// direct object
			addConstructionOccurrence(GrammaticalConstruction.OBJECT_DIRECT, start, end, dependency.toString());

			if (dep.tag().equalsIgnoreCase("prp")
					&& EnglishGrammaticalConstants.OBJECTIVE_PRONOUNS.contains(dep.word().toLowerCase())) {
				addConstructionOccurrence(GrammaticalConstruction.PRONOUNS, depBegin, depEnd, dependency.toString());
				addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_OBJECTIVE, depBegin, depEnd,
						dependency.toString());
			}
		} else if (rel.equalsIgnoreCase("nsubj")) {
			// subjective pronouns
			if (dep.tag().equalsIgnoreCase("prp")
					&& EnglishGrammaticalConstants.SUBJECTIVE_PRONOUNS.contains(dep.word().toLowerCase())) {
				addConstructionOccurrence(GrammaticalConstruction.PRONOUNS, depBegin, depEnd, dep.word());
				addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_SUBJECTIVE, depBegin, depEnd, dep.word());
			}
		}
	}

	private void addSomeAnyMuchMany(TypedDependency dependency, IndexedWord gov, IndexedWord dep, String rel) {
		if (rel.equalsIgnoreCase("det")) {
			if (dep.lemma().equalsIgnoreCase("some")) {
				addConstructionOccurrence(GrammaticalConstruction.DETERMINER_SOME, dep.beginPosition(),
						gov.endPosition(), dependency.toString());
			} else if (dep.lemma().equalsIgnoreCase("any")) {
				addConstructionOccurrence(GrammaticalConstruction.DETERMINER_ANY, dep.beginPosition(),
						gov.endPosition(), dependency.toString());
			}
		} else if (rel.equalsIgnoreCase("amod")) {
			if (dep.lemma().equalsIgnoreCase("many")) {
				addConstructionOccurrence(GrammaticalConstruction.DETERMINER_MANY, dep.beginPosition(),
						gov.endPosition(), dependency.toString());
			} else if (dep.lemma().equalsIgnoreCase("much")) {
				addConstructionOccurrence(GrammaticalConstruction.DETERMINER_MUCH, dep.beginPosition(),
						gov.endPosition(), dependency.toString());
			}
		}
	}

	private void addAdjectiveForms(CoreLabel label, String labelWord, String labelTag) {
		// already in lower case
		switch (labelTag) {
		case "jj":
			if (labelWord.equalsIgnoreCase("much") || labelWord.equalsIgnoreCase("many")) {
				break;
			} else if (labelWord.equalsIgnoreCase("able") || labelWord.equalsIgnoreCase("unable")) {
				int ableInd = label.index() - 1;

				if (wordsOutput.size() >= ableInd && wordsOutput.size() > (ableInd + 1)
						&& wordsOutput.get(ableInd + 1).tag().equalsIgnoreCase("to")) {
					addConstructionOccurrence(GrammaticalConstruction.MODALS_ADVANCED, label.beginPosition(),
							label.endPosition(), labelWord);
					addConstructionOccurrence(GrammaticalConstruction.MODALS, label.beginPosition(),
							label.endPosition(), labelWord);
					addConstructionOccurrence(GrammaticalConstruction.MODALS_ABLE, label.beginPosition(),
							label.endPosition(), labelWord);
				}
				// otherwise it's an adjective: He's an able student.
			} else {
				// if it doesn't contain numbers
				if (!labelWord.matches(".*\\d.*")) {
					addConstructionOccurrence(GrammaticalConstruction.ADJECTIVE_POSITIVE, label.beginPosition(),
							label.endPosition(), labelWord);
				}
			}

			break;
		case "jjr": // "more" is covered above -> not included here
			addConstructionOccurrence(GrammaticalConstruction.ADJECTIVE_COMPARATIVE_SHORT, label.beginPosition(),
					label.endPosition(), labelWord);
			break;
		case "jjs": // "most" is covered above -> not included here
			addConstructionOccurrence(GrammaticalConstruction.ADJECTIVE_SUPERLATIVE_SHORT, label.beginPosition(),
					label.endPosition(), labelWord);
			break;
		}
	}

	private void addLongComparative(CoreLabel label, String labelWord, String labelTag) {
		if (labelTag.equalsIgnoreCase("jj") && labelWord.length() > 5) // add comparativeAdjLong
		{
			addConstructionOccurrence(GrammaticalConstruction.ADJECTIVE_COMPARATIVE_LONG,
					wordsOutput.get(label.index() - 2).beginPosition(), label.endPosition(), "more " + labelWord);
		} else if (labelTag.equalsIgnoreCase("rb") && labelWord.length() > 5) // add comparativeAdvLong
		{
			addConstructionOccurrence(GrammaticalConstruction.ADVERB_COMPARATIVE_LONG,
					wordsOutput.get(label.index() - 2).beginPosition(), label.endPosition(), "more " + labelWord);
		}
		comparativeMoreFound = false;
	}

	private void addLongSuperlative(CoreLabel label, String labelWord, String labelTag) {
		if (labelTag.equalsIgnoreCase("jj") && labelWord.length() > 5) // add superlativeAdjLong
		{
			addConstructionOccurrence(GrammaticalConstruction.ADJECTIVE_SUPERLATIVE_LONG,
					wordsOutput.get(label.index() - 2).beginPosition(), label.endPosition(), "more " + labelWord);
		} else if (labelTag.equalsIgnoreCase("rb") && labelWord.length() > 5) // add superlativeAdvLong
		{
			addConstructionOccurrence(GrammaticalConstruction.ADVERB_SUPERLATIVE_LONG,
					wordsOutput.get(label.index() - 2).beginPosition(), label.endPosition(), "more " + labelWord);
		}
		superlativeMostFound = false;
	}

	private void addShortComparativeAdv(CoreLabel label, String labelWord) {
		if (labelWord.equalsIgnoreCase("more")) {
			comparativeMoreFound = true;
		}
		// it might be any other short adverb in comparative form
		addConstructionOccurrence(GrammaticalConstruction.ADVERB_COMPARATIVE_SHORT, label.beginPosition(),
				label.endPosition(), labelWord); // "more" is counted here

	}

	private void addShortSuperlativeAdv(CoreLabel label, String labelWord) {
		if (labelWord.equalsIgnoreCase("most")) {
			superlativeMostFound = true;
		}
		// it might be any other short adverb in superlative form
		addConstructionOccurrence(GrammaticalConstruction.ADVERB_SUPERLATIVE_SHORT, label.beginPosition(),
				label.endPosition(), labelWord); // "most" is counted here
	}

	private void addConjunctions(CoreLabel label, String labelWord, String labelTag) {
		if (EnglishGrammaticalConstants.ADVANCED_CONJUNCTIONS.contains(labelWord)) {
			addConstructionOccurrence(GrammaticalConstruction.CONJUNCTIONS_ADVANCED, label.beginPosition(),
					label.endPosition(), labelWord);
		} else if (EnglishGrammaticalConstants.SIMPLE_CONJUNCTIONS.contains(labelWord)) {
			addConstructionOccurrence(GrammaticalConstruction.CONJUNCTIONS_SIMPLE, label.beginPosition(),
					label.endPosition(), labelWord);
		}

		if (labelTag.equalsIgnoreCase("rb")) {
			addConstructionOccurrence(GrammaticalConstruction.ADVERB_POSITIVE, label.beginPosition(),
					label.endPosition(), labelWord);
		}
	}

	private void addArticles(CoreLabel label, String labelWord) {
		switch (labelWord) {
		case "a":
			addConstructionOccurrence(GrammaticalConstruction.ARTICLES, label.beginPosition(), label.endPosition(),
					labelWord);
			addConstructionOccurrence(GrammaticalConstruction.ARTICLE_A, label.beginPosition(), label.endPosition(),
					labelWord);
			break;
		case "an":
			addConstructionOccurrence(GrammaticalConstruction.ARTICLES, label.beginPosition(), label.endPosition(),
					labelWord);
			addConstructionOccurrence(GrammaticalConstruction.ARTICLE_AN, label.beginPosition(), label.endPosition(),
					labelWord);
			break;
		case "the":
			addConstructionOccurrence(GrammaticalConstruction.ARTICLES, label.beginPosition(), label.endPosition(),
					labelWord);
			addConstructionOccurrence(GrammaticalConstruction.ARTICLE_THE, label.beginPosition(), label.endPosition(),
					labelWord);
			break;
		}
	}

	private void addReflexiveAndPossessivePronouns(CoreLabel label, String labelWord, String labelTag) {
		if (labelTag.equalsIgnoreCase("prp") && EnglishGrammaticalConstants.REFLEXIVE_PRONOUNS.contains(labelWord)) {
			addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_REFLEXIVE, label.beginPosition(),
					label.endPosition(), labelWord);
			addConstructionOccurrence(GrammaticalConstruction.PRONOUNS, label.beginPosition(), label.endPosition(),
					labelWord);
		} else if (labelTag.equalsIgnoreCase("prp$")
				&& EnglishGrammaticalConstants.POSSESSIVE_PRONOUNS.contains(labelWord)) {
			// it can actually be either possessive or objective (stanford parser crashes on that!), or absolute possessive (his)
			// TODO check if it is followed by a noun or an adj?
			addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_POSSESSIVE, label.beginPosition(),
					label.endPosition(), labelWord);
			addConstructionOccurrence(GrammaticalConstruction.PRONOUNS, label.beginPosition(), label.endPosition(),
					labelWord);
		}
	}

	private void addRelativePronouns(Tree tree, List<CoreLabel> words) {
		TregexPattern pattern = TregexPattern.compile("WP|WDT >> SBAR");
		List<Tree> leaves = tree.getLeaves();

		TregexMatcher matcher = pattern.matcher(tree);
		Tree lastMatchingRootNode = null;
		while(matcher.find()) {
			Tree match = matcher.getMatch();
			
			// We get the same result multiple times multiple matches apply to the same WP/WDT node (e.g. when it's in a nested SBAR construction)
			if(lastMatchingRootNode != match) {
				lastMatchingRootNode = match;
			
				// We don't have the indices in the tree, so we need to get them by mapping the tree leaves to the tokenized sentence			
				String leftText = "";
				for(int i = 0; i < leaves.size(); i++) {
					Tree leaf = leaves.get(i);
					if(leaf != match.getLeaves().get(0)) {
						leftText += leaf.toString();
					} else {
						break;
					}
				}
				
				String wordString = "";
				int startIndex = 0;
				int endIndex = 0;
				String targetWord = null;
				for(int i = 0; i < words.size(); i++) {
					if(i != words.size() - 1) {
						wordString += words.get(i).value();
						CoreLabel word = words.get(i + 1);
	
						if(wordString.equals(leftText)) {
							startIndex = word.beginPosition();
							endIndex = word.endPosition();
							targetWord = word.value();
							break;
						}
					}
				}
				
				if(targetWord != null) {
					addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_RELATIVE,
							startIndex, endIndex, targetWord);
				}
			}
		}
	}
	
	@Override
	public void apply(CoreNlpParser parser) {
		Annotation docAnnotation = new Annotation(workingDoc.getText());
		parser.pipeline().annotate(docAnnotation);

		List<CoreMap> sentences = docAnnotation.get(CoreAnnotations.SentencesAnnotation.class);
		for (CoreMap itr : sentences) {
			if (itr.size() > 0) {
				Tree tree = itr.get(TreeCoreAnnotations.TreeAnnotation.class);
				List<CoreLabel> words = itr.get(CoreAnnotations.TokensAnnotation.class);
				Collection<TypedDependency> dependencies = itr
						.get(SemanticGraphCoreAnnotations.CollapsedDependenciesAnnotation.class)
						.typedDependencies();

				sentenceCount++;
				dependencyCount += dependencies.size();
				depthCount += tree.depth();

				// changed: only count words (no punctuation)
				for (CoreLabel cl : words) {
					tokenCount++;
					if (cl.tag().toLowerCase().matches("[a-z]*")) {
						wordCount++;
						characterCount += cl.word().length();
					}
				}

				// extract gram.structures
				inspectSentence(tree, words, dependencies);
			}
		}

		// update doc properties
		workingDoc.setAvgSentenceLength((double) wordCount / (double) sentenceCount);
		workingDoc.setAvgTreeDepth((double) depthCount / (double) sentenceCount);
		workingDoc.setAvgWordLength((double) characterCount / (double) wordCount);
		workingDoc.setLength(wordCount);
		workingDoc.setNumDependencies(dependencyCount);
		workingDoc.setNumWords(wordCount);
		workingDoc.setNumTokens(tokenCount);
		workingDoc.setNumSentences(sentenceCount);
		workingDoc.setNumCharacters(characterCount);
		workingDoc.flagAsParsed(parser.createAnnotations(docAnnotation));
	}
}
