/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.

 */
package com.flair.server.parser;

import java.util.List;
import java.util.regex.Pattern;

import com.flair.server.grammar.GermanGrammaticalConstants;
import com.flair.server.grammar.GermanGrammaticalTreePatterns;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.grammar.Language;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.util.CoreMap;

/**
 * Implementation of German language parsing logic for the Stanford parser
 *
 * @author zweiss
 */
class StanfordDocumentParserGermanStrategy extends BasicStanfordDocumentParserStrategy
{

	private AbstractDocument workingDoc;

	private int	dependencyCount;	// count dependencies - correspond to token count without punctuation
	private int	wordCount;			// count words (without numbers, symbols and punctuation)
	private int	tokenCount;			// count tokens (incl. numbers, symbols and punctuation)
	private int	sentenceCount;		// count sentences
	private int	depthCount;			// count tree depthCount
	private int	characterCount;		// count characters in words

	private static final String WORD_PATTERN = "[a-z\\u00e4\\u00f6\\u00fc\\u00df]+";

	public StanfordDocumentParserGermanStrategy()
	{
		workingDoc = null;
		wordCount = tokenCount = dependencyCount = sentenceCount = depthCount = characterCount = 0;
	}

	private void initializeState(AbstractDocument doc) {
		if (pipeline == null)
		{
			throw new IllegalStateException("Parser not set");
		} else if (isLanguageSupported(doc.getLanguage()) == false)
		{
			throw new IllegalArgumentException("Document language " + doc.getLanguage()
					+ " not supported (Strategy language: " + Language.GERMAN + ")");
		}

		workingDoc = doc;
	}

	private void resetState() {
		wordCount = tokenCount = dependencyCount = sentenceCount = depthCount = characterCount = 0;
		pipeline = null;
		workingDoc = null;
	}

	private int countSubstr(String substr, String str) {
		// ###TODO can be made faster?
		// the result of split() will contain one more element than the delimiter
		// the "-1" second argument makes it not discard trailing empty strings
		return str.split(Pattern.quote(substr), -1).length - 1;
	}

	private int countMatches(TregexPattern pattern, Tree tree) {
		int matches = 0;
		TregexMatcher matcher = pattern.matcher(tree);
		while (matcher.findNextMatchingNode())
		{
			matches++;
		}
		return matches;
	}

	private void addConstructionOccurrence(GrammaticalConstruction type, int start, int end, String expr) {
		workingDoc.getConstructionData(type).addOccurrence(start, end);
	}

	private void inspectSentence(Tree tree, List<CoreLabel> words) {
		if (words == null || words.isEmpty())
		{
			return;
		}

		String treeStr = tree.toString(); // don't use toLowerCase() here
		int startInd = words.get(0).beginPosition();
		int endInd = words.get(words.size() - 1).endPosition();

		// get sentential counts ************************************************************************************************
		// check if it is an incomplete sentence
		int numVPs = countMatches(GermanGrammaticalTreePatterns.patternVerb, tree);
		int numNounsPronounsAnswerParticles = countMatches(
				GermanGrammaticalTreePatterns.patternNounOrPronounOrAnswerParticle, tree);
		if (numVPs < 1)
		{
			// an incomplete sentence contains no verb, but some sort of noun, pronoun or answering particle
			if (numNounsPronounsAnswerParticles > 0)
			{
				addConstructionOccurrence(GrammaticalConstruction.SENTENCE_INCOMPLETE, startInd, endInd, treeStr); // highlight the whole sentence
			}
		} // if there is at least one verb, count clausal features
		else
		{
			// calculate number of clauses in a tree
			int numClausesPerSentence = countMatches(GermanGrammaticalTreePatterns.patternClausesPerSentence, tree);

			// complex sentences contain more than one clause
			if (numClausesPerSentence > 1)
			{
				addConstructionOccurrence(GrammaticalConstruction.SENTENCE_COMPLEX, startInd, endInd, treeStr); // highlight the whole sentence

				// relative clauses
				int numDPronounRelativeClauses = countMatches(
						GermanGrammaticalTreePatterns.patternDPronounRelativeClauses, tree);
				int numWPronounRelativeClauses = countMatches(
						GermanGrammaticalTreePatterns.patternWPronounRelativeClauses, tree);
				int numRelativeClauses = numDPronounRelativeClauses + numWPronounRelativeClauses;
				for (int i = 0; i < numRelativeClauses; i++)
				{
					addConstructionOccurrence(GrammaticalConstruction.CLAUSE_RELATIVE, startInd, endInd, treeStr); // highlight the whole sentence
				}
				// ***************************************************************************************************************
				// @TODO add separate feature for d and w relative pronouns
				// ***************************************************************************************************************

				// adverbial clauses, i.e. clauses with subordinating conjunctions disregarding 'dass' and 'ob'
				int numAdverbialClauses = countMatches(GermanGrammaticalTreePatterns.patternAdverbialClauses, tree);
				for (int i = 0; i < numAdverbialClauses; i++)
				{
					addConstructionOccurrence(GrammaticalConstruction.CLAUSE_ADVERBIAL, startInd, endInd, treeStr); // highlight the whole sentence
				}

				// dass clauses
				int numDassClauses = countMatches(GermanGrammaticalTreePatterns.patternDassClauses, tree);
				for (int i = 0; i < numDassClauses; i++)
				{
					addConstructionOccurrence(GrammaticalConstruction.CLAUSE_DASS, startInd, endInd, treeStr); // highlight the whole sentence
				}

				// general subordinate clauses, i.e. relative clauses and adverbial clauses + 'dass'/'ob' clauses
				int numClausalSubordination = numRelativeClauses
						+ countMatches(GermanGrammaticalTreePatterns.patternClausalSubordination, tree);
				for (int i = 0; i < numClausalSubordination; i++)
				{
					addConstructionOccurrence(GrammaticalConstruction.CLAUSE_SUBORDINATE, startInd, endInd, treeStr); // highlight the whole sentence
				}
				// ***************************************************************************************************************
				// @TODO add series of subordiation clause types from Hancke 2013, Weiss 2015
				// ***************************************************************************************************************

				// clausal coordination
				int numCoordinationSubClauses = countMatches(
						GermanGrammaticalTreePatterns.patternCoordinatedSubordinateClauses, tree);
				int numCoordinationMainClauses = countMatches(
						GermanGrammaticalTreePatterns.patternCoordinatedMainClauses, tree);
				for (int i = 0; i < (numCoordinationSubClauses + numCoordinationMainClauses); i++)
				{
					addConstructionOccurrence(GrammaticalConstruction.SENTENCE_COMPOUND, startInd, endInd, treeStr); // highlight the whole sentence
				}
			} // simple sentences contain only one clause, but given the shitty data, we settle for at least one verb
			else
			{
				addConstructionOccurrence(GrammaticalConstruction.SENTENCE_SIMPLE, startInd, endInd, treeStr); // highlight the whole sentence
			}
		}

		/*
		 * Periphrastic constructions
		 */
		// perfect tense
		int numPerfectHaben = countMatches(GermanGrammaticalTreePatterns.patternPerfectHaben, tree);
		for (int i = 0; i < numPerfectHaben; i++)
		{
			addConstructionOccurrence(GrammaticalConstruction.TENSE_PRESENT_PERFECT_HABEN, startInd, endInd, treeStr);
		}
		int numPerfectSein = countMatches(GermanGrammaticalTreePatterns.patternPerfectSein, tree);
		for (int i = 0; i < numPerfectSein; i++)
		{
			addConstructionOccurrence(GrammaticalConstruction.TENSE_PRESENT_PERFECT_SEIN, startInd, endInd, treeStr);
		}

		// plusquamperfect
		int numPlsqmHaben = countMatches(GermanGrammaticalTreePatterns.patternPlusquamperfectHaben, tree);
		for (int i = 0; i < numPlsqmHaben; i++)
		{
			addConstructionOccurrence(GrammaticalConstruction.TENSE_PAST_PERFECT_HABEN, startInd, endInd, treeStr);
		}
		int numPlsqmSein = countMatches(GermanGrammaticalTreePatterns.patternPlusquamperfectSein, tree);
		for (int i = 0; i < numPlsqmSein; i++)
		{
			addConstructionOccurrence(GrammaticalConstruction.TENSE_PAST_PERFECT_SEIN, startInd, endInd, treeStr);
		}

		// futur
		int numFuture1 = countMatches(GermanGrammaticalTreePatterns.patternFuture1, tree);
		for (int i = 0; i < numFuture1; i++)
		{
			addConstructionOccurrence(GrammaticalConstruction.TENSE_FUTURE_SIMPLE, startInd, endInd, treeStr);
		}
		int numFuture2 = countMatches(GermanGrammaticalTreePatterns.patternFuture2, tree);
		for (int i = 0; i < numFuture2; i++)
		{
			addConstructionOccurrence(GrammaticalConstruction.TENSE_FUTURE_PERFECT, startInd, endInd, treeStr);
		}

		// passive
		int numPassiveWerden = countMatches(GermanGrammaticalTreePatterns.patternPassiveWerden, tree);
		for (int i = 0; i < numPassiveWerden; i++)
		{
			addConstructionOccurrence(GrammaticalConstruction.PASSIVE_VOICE_WERDEN, startInd, endInd, treeStr);
		}
		int numPassiveSein = countMatches(GermanGrammaticalTreePatterns.patternPassiveSein, tree);
		for (int i = 0; i < numPassiveSein; i++)
		{
			addConstructionOccurrence(GrammaticalConstruction.PASSIVE_VOICE_SEIN, startInd, endInd, treeStr);
		}

		// verb brackets
		int numVerbBrackets = countMatches(GermanGrammaticalTreePatterns.patternVerbBracket, tree);
		for (int i = 0; i < numVerbBrackets; i++)
		{
			addConstructionOccurrence(GrammaticalConstruction.VERB_BRACKETS, startInd, endInd, treeStr);
		}

		/*
		 * Complex noun phrases
		 */

		// participle attributes
		int numParticiple1Attributes = countMatches(GermanGrammaticalTreePatterns.patternParticiple1AttributeA, tree)
				+ countMatches(GermanGrammaticalTreePatterns.patternParticiple1AttributeB, tree);
		for (int i = 0; i < numParticiple1Attributes; i++)
		{
			addConstructionOccurrence(GrammaticalConstruction.ATTRIBUTES_PARTICIPLE_1, startInd, endInd, treeStr);
		}
		int numParticiple2Attributes = countMatches(GermanGrammaticalTreePatterns.patternParticiple2Attribute_A, tree)
				+ countMatches(GermanGrammaticalTreePatterns.patternParticiple2Attribute_B, tree);
		for (int i = 0; i < numParticiple2Attributes; i++)
		{
			addConstructionOccurrence(GrammaticalConstruction.ATTRIBUTES_PARTICIPLE_2, startInd, endInd, treeStr);
		}

		// adjective attributes
		int numAdjectiveAttributes = countMatches(GermanGrammaticalTreePatterns.patternAdjectiveAttributes_A, tree)
				+ countMatches(GermanGrammaticalTreePatterns.patternAdjectiveAttributes_B, tree);
		for (int i = 0; i < numAdjectiveAttributes; i++)
		{
			addConstructionOccurrence(GrammaticalConstruction.ATTRIBUTES_ADJECTIVE, startInd, endInd, treeStr);
		}

		// preposition attributes
		int numPrepositionAttributes = countMatches(GermanGrammaticalTreePatterns.patternPrepositionalAttributes, tree);
		for (int i = 0; i < numPrepositionAttributes; i++)
		{
			addConstructionOccurrence(GrammaticalConstruction.ATTRIBUTES_PREPOSITION, startInd, endInd, treeStr);
		}

		/*
		 * Questions ********************************************************************************************************
		 */
		// get questions
		int numQuestions = countMatches(GermanGrammaticalTreePatterns.patternQuestionMark, tree);
		if (numQuestions > 0)
		{
			inspectQuestion(words, tree);
		} else
		{
			int numIndirectObQuestions = countMatches(GermanGrammaticalTreePatterns.patternObClauses, tree);
			for (int i = 0; i < numIndirectObQuestions; i++)
			{
				addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_INDIRECT, startInd, endInd, treeStr);
			}
		}

		/*
		 * POS counts ***************************************************************************************************************************************
		 */
		boolean foundToParticle = false;
		int toParticleBeginPosition = -1;

		boolean foundAdjectiveParticle = false;
		int adjectiveParticleBeginPosition = -1;

		boolean foundAdjacentVerb = false;
		int verbClusterBeginPosition = -1;
		int verbClusterEndPosition = -1;
		int sizeVerbCluster = -1;
		StringBuilder verbClusterString = new StringBuilder();

		// find the first and the last word (skip bullets, quotes, etc.)
		CoreLabel firstWord = null;
		for (CoreLabel w : words)
		{
			if (w.word() != null && w.word().toLowerCase().matches("[a-z]*"))
			{
				// first real word
				firstWord = w;
				break;
			}
		}
		if (firstWord == null)
		{
			return;
		}
		// find the last token (punctuation mark) (don't take quotes into account)
		CoreLabel lastWord = null;
		for (int i = words.size() - 1; i > 0; i--)
		{
			if (words.get(i).tag() != null && words.get(i).tag().equalsIgnoreCase("."))
			{
				lastWord = words.get(i);
				break;
			}
		}

		// independent of type of sentence
		// go through the words (CoreLabels) for POS tags
		for (CoreLabel label : words)
		{
			String labelTag = label.tag().toLowerCase();
			String labelWord = label.word().toLowerCase();
			if (labelTag != null && labelWord != null && (!(labelTag.equalsIgnoreCase("$.")
					|| labelTag.equalsIgnoreCase("$,") || labelTag.equalsIgnoreCase("$("))))
			{

				// verbs
				if (labelTag.startsWith("v"))
				{
					foundAdjectiveParticle = false;
					adjectiveParticleBeginPosition = -1;

					// check verb clusters
					if (foundAdjacentVerb)
					{
						sizeVerbCluster++;
					} else
					{
						foundAdjacentVerb = true;
						verbClusterBeginPosition = label.beginPosition();
						sizeVerbCluster = 1;
						verbClusterString = new StringBuilder();
					}
					verbClusterString.append(labelWord);
					verbClusterString.append(" ");
					verbClusterEndPosition = label.endPosition();

					// verb types
					if (labelTag.startsWith("vm"))
					{
						addConstructionOccurrence(GrammaticalConstruction.VERBTYP_MODAL, label.beginPosition(),
								label.endPosition(), labelWord);
					} else if (labelTag.startsWith("va"))
					{
						addConstructionOccurrence(GrammaticalConstruction.VERBTYP_AUXILIARIES, label.beginPosition(),
								label.endPosition(), labelWord);
					} else if (labelTag.startsWith("vv"))
					{
						addConstructionOccurrence(GrammaticalConstruction.VERBTYP_MAIN, label.beginPosition(),
								label.endPosition(), labelWord);
					}

					// verb forms
					if (labelTag.endsWith("izu"))
					{
						addConstructionOccurrence(GrammaticalConstruction.VERBFORM_TO_INFINITIVE, label.beginPosition(),
								label.endPosition(), labelWord);
					} else if (labelTag.endsWith("inf"))
					{
						if (foundToParticle)
						{
							addConstructionOccurrence(GrammaticalConstruction.VERBFORM_TO_INFINITIVE,
									toParticleBeginPosition, label.endPosition(), labelWord);
							foundToParticle = false;
						} else
						{
							addConstructionOccurrence(GrammaticalConstruction.VERBFORM_INFINITIVE,
									label.beginPosition(), label.endPosition(), labelWord);
						}
					} else if (labelTag.endsWith("pp"))
					{
						addConstructionOccurrence(GrammaticalConstruction.VERBFORM_PARTICIPLE, label.beginPosition(),
								label.endPosition(), labelWord);
					} else if (labelTag.endsWith("imp"))
					{
						addConstructionOccurrence(GrammaticalConstruction.IMPERATIVES, label.beginPosition(),
								label.endPosition(), labelWord);
					}
				} else
				{

					// if you found a to particle, but the next thing wasn't a verb, it was probably mistagged
					if (foundToParticle)
					{
						foundToParticle = false;
						toParticleBeginPosition = -1;
					}
					// if you had a verb cluster and now there is something else, the cluster ended
					if (foundAdjacentVerb)
					{
						if (sizeVerbCluster > 1)
						{
							addConstructionOccurrence(GrammaticalConstruction.VERB_CLUSTER, verbClusterBeginPosition,
									verbClusterEndPosition, verbClusterString.toString());
						}
						foundAdjacentVerb = false;
					}

					// adjectives and adverbs
					if (labelTag.startsWith("adj"))
					{
						addConstructionOccurrence(GrammaticalConstruction.ADJECTIVE_POSITIVE, label.beginPosition(),
								label.endPosition(), labelWord);
						if (foundAdjectiveParticle)
						{
							addConstructionOccurrence(GrammaticalConstruction.PARTICLE_PLUS_ADJ_ADV,
									adjectiveParticleBeginPosition, label.endPosition(), labelWord);
						}
					} else if (labelTag.startsWith("adv"))
					{
						addConstructionOccurrence(GrammaticalConstruction.ADVERB_POSITIVE, label.beginPosition(),
								label.endPosition(), labelWord);
						if (foundAdjectiveParticle)
						{
							addConstructionOccurrence(GrammaticalConstruction.PARTICLE_PLUS_ADJ_ADV,
									adjectiveParticleBeginPosition, label.endPosition(), labelWord);
						}
					} else if (labelTag.startsWith("card"))
					{
						addConstructionOccurrence(GrammaticalConstruction.CARDINALS, label.beginPosition(),
								label.endPosition(), labelWord);
					} // prepositions
					else if (labelTag.startsWith("appr") && labelWord.matches(WORD_PATTERN))
					{
						addConstructionOccurrence(GrammaticalConstruction.PREPOSITIONS, label.beginPosition(),
								label.endPosition(), labelWord);
						addConstructionOccurrence(GrammaticalConstruction.PREPOSITIONS_SIMPLE, label.beginPosition(),
								label.endPosition(), labelWord);
					} else if (labelTag.equalsIgnoreCase("appo"))
					{
						addConstructionOccurrence(GrammaticalConstruction.PREPOSITIONS, label.beginPosition(),
								label.endPosition(), labelWord);
						addConstructionOccurrence(GrammaticalConstruction.POSTPOSITION, label.beginPosition(),
								label.endPosition(), labelWord);
					} else if (labelTag.startsWith("p"))
					{
						if (labelTag.startsWith("ptk"))
						{
							// zu infinitive
							if (labelWord.equals("ptkzu"))
							{
								foundToParticle = true;
								toParticleBeginPosition = label.beginPosition();
							} // adjective and adverb particle
							else if (labelTag.equalsIgnoreCase("ptka"))
							{
								foundAdjectiveParticle = true;
								adjectiveParticleBeginPosition = label.beginPosition();
							}

						} else
						{ // if it's not a particle, it is a pronoun

							addConstructionOccurrence(GrammaticalConstruction.PRONOUNS, label.beginPosition(),
									label.endPosition(), labelWord);

							if (labelTag.equalsIgnoreCase("pper"))
							{
								addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_PERSONAL,
										label.beginPosition(), label.endPosition(), labelWord);
							} else if (labelTag.equalsIgnoreCase("prf"))
							{
								addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_REFLEXIVE,
										label.beginPosition(), label.endPosition(), labelWord);
							} else if (labelTag.startsWith("ppos"))
							{
								// it can actually be either possessive or objective (stanford parser crashes on that!)
								addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_POSSESSIVE,
										label.beginPosition(), label.endPosition(), labelWord);
							} else if (labelTag.startsWith("pd"))
							{
								addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_DEMONSTRATIVE,
										label.beginPosition(), label.endPosition(), labelWord);
							} else if (labelTag.startsWith("prel"))
							{
								addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_RELATIVE,
										label.beginPosition(), label.endPosition(), labelWord);
							} else if (labelTag.startsWith("pi"))
							{
								addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_INDEFINITE,
										label.beginPosition(), label.endPosition(), labelWord);

								if (GermanGrammaticalConstants.SOME_DETERMINERS.contains(labelWord)
										|| labelWord.startsWith("manch") || labelWord.startsWith("einige"))
								{
									addConstructionOccurrence(GrammaticalConstruction.DETERMINER_SOME,
											label.beginPosition(), label.endPosition(), labelWord);
								} else if (labelWord.startsWith("irgend"))
								{
									addConstructionOccurrence(GrammaticalConstruction.DETERMINER_ANY,
											label.beginPosition(), label.endPosition(), labelWord);
								} else if (labelWord.startsWith("viel"))
								{
									addConstructionOccurrence(GrammaticalConstruction.DETERMINER_MANY,
											label.beginPosition(), label.endPosition(), labelWord);
								}
							} else if (labelTag.startsWith("pw"))
							{
								addConstructionOccurrence(GrammaticalConstruction.PRONOUNS_INTERROGATIVE,
										label.beginPosition(), label.endPosition(), labelWord);
							}
						}
					} // TODO: delete conjunctions!!!! ************************************************************************************************
						// conjunctions (all or simple): subjunction, coordination, prepositions, adverbs
						// @TODO extend list of conjunctions
						// if (labelTag.startsWith("app") || labelTag.startsWith("ko") || labelTag.equalsIgnoreCase("adv") || labelTag.equalsIgnoreCase("pav")
						// || labelTag.startsWith("adj")) {
						// if (GermanGrammaticalConstants.ADVANCED_CONJUNCTIONS.contains(labelWord)) {
						// addConstructionOccurrence(GrammaticalConstruction.CONJUNCTIONS_ADVANCED, label.beginPosition(), label.endPosition(), labelWord);
						// } else if (GermanGrammaticalConstants.SIMPLE_CONJUNCTIONS.contains(labelWord)) {
						// addConstructionOccurrence(GrammaticalConstruction.CONJUNCTIONS_SIMPLE, label.beginPosition(), label.endPosition(), labelWord);
						// }
						// if (labelTag.equalsIgnoreCase("adv")) {
						// addConstructionOccurrence(GrammaticalConstruction.ADVERB_POSITIVE, label.beginPosition(), label.endPosition(), labelWord);
						// }
						// }
						// TODO: delete adjectivesconjunctions ************************************************************************************************
						// determiners
					else if (labelTag.equalsIgnoreCase("art"))
					{
						if (labelWord.startsWith("ein"))
						{
							addConstructionOccurrence(GrammaticalConstruction.ARTICLES, label.beginPosition(),
									label.endPosition(), labelWord);
							addConstructionOccurrence(GrammaticalConstruction.ARTICLE_A, label.beginPosition(),
									label.endPosition(), labelWord);
						} else if (labelWord.equalsIgnoreCase("der") || labelWord.equalsIgnoreCase("den")
								|| labelWord.equalsIgnoreCase("dem") || labelWord.equalsIgnoreCase("des")
								|| labelWord.equalsIgnoreCase("die"))
						{
							addConstructionOccurrence(GrammaticalConstruction.ARTICLES, label.beginPosition(),
									label.endPosition(), labelWord);
							addConstructionOccurrence(GrammaticalConstruction.ARTICLE_THE, label.beginPosition(),
									label.endPosition(), labelWord);
						}
					} // nouns
					else if (labelTag.equalsIgnoreCase("nn") || labelTag.equalsIgnoreCase("ne"))
					{
						if (labelWord.endsWith("ungen") || labelWord.endsWith("ung"))
						{
							addConstructionOccurrence(GrammaticalConstruction.NOUNS_UNG, label.beginPosition(),
									label.endPosition(), labelWord);
						} else if (labelWord.endsWith("ismen") || labelWord.endsWith("ismus"))
						{
							addConstructionOccurrence(GrammaticalConstruction.NOUNS_ISMUS, label.beginPosition(),
									label.endPosition(), labelWord);
						} else if (labelWord.endsWith("turen") || labelWord.endsWith("tur"))
						{
							addConstructionOccurrence(GrammaticalConstruction.NOUNS_TUR, label.beginPosition(),
									label.endPosition(), labelWord);
						}
						// @TODO add more suffixes
						/**
						 * if (labelWord.endsWith("ant") || labelWord.endsWith("anten") || labelWord.endsWith("antin") || labelWord.endsWith("antinnen") || labelWord.endsWith("atur") || labelWord.endsWith("aturen") || labelWord.endsWith("ator") || labelWord.endsWith("atoren") || labelWord.endsWith("atorin") || labelWord.endsWith("atorinnen") || labelWord.endsWith("arium") || labelWord.endsWith("arien") || labelWord.endsWith("at") || labelWord.endsWith("ate") || labelWord.endsWith("eur") ||
						 * labelWord.endsWith("eure") || labelWord.endsWith("eurin") || labelWord.endsWith("eurinnen") || labelWord.endsWith("ent") || labelWord.endsWith("ents") || labelWord.endsWith("enz") || labelWord.endsWith("enzen") || labelWord.endsWith("ast") || labelWord.endsWith("asten") || labelWord.endsWith("astin") || labelWord.endsWith("astinnen") || labelWord.endsWith("ist") || labelWord.endsWith("isten") || labelWord.endsWith("istin") || labelWord.endsWith("istinnen") ||
						 * labelWord.endsWith("ität") || labelWord.endsWith("itäten") || labelWord.endsWith("ismus") || labelWord.endsWith("ismen") || labelWord.endsWith("ion") || labelWord.endsWith("ionen") || labelWord.endsWith("ur") || labelWord.endsWith("uren") || labelWord.endsWith("oid") || labelWord.endsWith("oiden") || labelWord.endsWith("istik") || labelWord.endsWith("istiken") || labelWord.endsWith("itis") || labelWord.endsWith("sis")) {
						 *
						 * }*
						 */
					}
				}

				// negation
				if (GermanGrammaticalConstants.NEGATION.contains(labelWord) || labelTag.equalsIgnoreCase("ptkneg"))
				{
					addConstructionOccurrence(GrammaticalConstruction.NEGATION_ALL, label.beginPosition(),
							label.endPosition(), labelWord);
					addConstructionOccurrence(GrammaticalConstruction.NEGATION_NO_NOT_NEVER, label.beginPosition(),
							label.endPosition(), labelWord);
				} else if (GermanGrammaticalConstants.PARTIAL_NEGATION.contains(labelWord))
				{
					addConstructionOccurrence(GrammaticalConstruction.NEGATION_ALL, label.beginPosition(),
							label.endPosition(), labelWord);
					addConstructionOccurrence(GrammaticalConstruction.NEGATION_PARTIAL, label.beginPosition(),
							label.endPosition(), labelWord);
				}
			}
		}
		// make shure to end verb cluster, if it was the last element of the sentence
		if (foundAdjacentVerb && sizeVerbCluster > 1)
		{
			addConstructionOccurrence(GrammaticalConstruction.VERB_CLUSTER, verbClusterBeginPosition,
					verbClusterEndPosition, verbClusterString.toString());
			// FLAIRLogger.get().info("BRACKET: " + verbClusterString.toString());
		}

	}

	private void inspectQuestion(List<CoreLabel> labeledWords, Tree tree) {
		int startInd = labeledWords.get(0).beginPosition();
		int endInd = labeledWords.get(labeledWords.size() - 1).endPosition();

		// direct question
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_DIRECT, startInd, endInd, labeledWords.toString());
		// tag questions
		int numTagQuestions = countMatches(GermanGrammaticalTreePatterns.patternTagQuestion, tree);
		if (numTagQuestions > 0)
		{
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_TAG, startInd, endInd, labeledWords.toString());
		}

		CoreLabel firstWord = null;
		for (CoreLabel w : labeledWords)
		{
			if (w.word() != null && w.word().toLowerCase().matches("[a-z]*"))
			{
				// first real word
				firstWord = w;
				break;
			}
		}
		if (firstWord == null)
		{
			return;
		}
		String firstWordTag = firstWord.tag();
		String firstWordWord = firstWord.word().toLowerCase();

		// wh-question
		// PWAT, PWAV, PWS may be German wh-questions
		if (firstWordTag.toLowerCase().startsWith("pw"))
		{
			addWhQuestion(firstWordWord, startInd, endInd);
		} // yesNoQuestions (starts with full, modal, or auxiliary verb and is finite or mislabeled as infinite)
		else if ((firstWordTag.toLowerCase().startsWith("vv") || firstWordTag.toLowerCase().startsWith("vm")
				|| firstWordTag.toLowerCase().startsWith("va"))
				|| (firstWordTag.toLowerCase().endsWith("fin") || firstWordTag.toLowerCase().endsWith("inf")))
		{
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_YESNO, startInd, endInd,
					labeledWords.toString());
		} else
		{
			// it can still be the case that it is a wh- or yes-no question but it doesn't start at the beginning of a sentence
			// e.g.: That person has to wonder 'What did I do wrong?'
			CoreLabel questionWord = null;
			for (CoreLabel aWord : labeledWords)
			{
				// look for a question word
				if ((questionWord == null) && (aWord.tag().toLowerCase().startsWith("pw")))
				{
					questionWord = aWord;
				} else if (questionWord != null)
				{
					addWhQuestion(questionWord.word().toLowerCase(), startInd, endInd);
				}
			}
		}
	}

	private void addWhQuestion(String qWord, int startInd, int endInd) {
		addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WH, startInd, endInd, qWord);
		switch (qWord.toLowerCase())
		{
		case "was":
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHAT, startInd, endInd, qWord);
			break;
		case "wie":
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_HOW, startInd, endInd, qWord);
			break;
		case "warum":
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHY, startInd, endInd, qWord);
			break;
		case "wer":
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHO, startInd, endInd, qWord);
			break;
		case "wo":
		case "woher":
		case "wohin":
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHERE, startInd, endInd, qWord);
			break;
		case "wann":
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHEN, startInd, endInd, qWord);
			break;
		case "wessen":
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHOSE, startInd, endInd, qWord);
			break;
		case "wem":
		case "wen":
		case "welchen":
		case "welchem":
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHOM, startInd, endInd, qWord);
			break;
		case "welche":
		case "welcher":
		case "welches":
			addConstructionOccurrence(GrammaticalConstruction.QUESTIONS_WHICH, startInd, endInd, qWord);
			break;
		default:
			System.out.println("Unbekanntes w-Fragewort: " + qWord);

		}
	}

	@Override
	public boolean isLanguageSupported(Language lang) {
		return lang == Language.GERMAN;
	}

	@Override
	public boolean apply(AbstractDocument docToParse) {
		assert docToParse != null;
		try
		{
			initializeState(docToParse);

			Annotation docAnnotation = new Annotation(workingDoc.getText());
			pipeline.annotate(docAnnotation);

			List<CoreMap> sentences = docAnnotation.get(CoreAnnotations.SentencesAnnotation.class);
			for (CoreMap itr : sentences)
			{
				if (itr.size() > 0)
				{
					Tree tree = itr.get(TreeCoreAnnotations.TreeAnnotation.class);
					List<CoreLabel> words = itr.get(CoreAnnotations.TokensAnnotation.class);
					// Collection<TypedDependency> dependencies = itr.get(SemanticGraphCoreAnnotations.CollapsedDependenciesAnnotation.class).typedDependencies();

					sentenceCount++;
					depthCount += tree.depth();

					// changed: only count words (no punctuation)
					for (CoreLabel cl : words)
					{
						tokenCount++;

						if (!cl.tag().startsWith("$"))
						{
							dependencyCount += 1;
						}

						if (cl.value().toLowerCase().matches(WORD_PATTERN))
						{
							wordCount++;
							characterCount += cl.word().length();
						}
					}

					// extract gram.structures
					inspectSentence(tree, words);
				}
			}

			// update doc properties
			workingDoc.setAvgSentenceLength((double) wordCount / (double) sentenceCount);
			workingDoc.setAvgTreeDepth((double) depthCount / (double) sentenceCount);
			workingDoc.setAvgWordLength((double) characterCount / (double) wordCount);
			workingDoc.setLength(wordCount);
			workingDoc.setNumDependencies(dependencyCount);
			workingDoc.setNumSentences(sentenceCount);
			workingDoc.setNumCharacters(characterCount);
			workingDoc.setNumWords(wordCount);
			workingDoc.setNumTokens(tokenCount);
			workingDoc.flagAsParsed();
		} finally
		{
			resetState();
		}

		return true;
	}
}
