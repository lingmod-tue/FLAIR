package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.DocumentParsing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.ConditionalConstruction;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.SentenceAnnotations;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.DocumentExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.Pair;

import edu.stanford.nlp.ling.CoreLabel;

public class ConstructionPreparer {

	/**
	 * Modifies construction indices in-place if necessary.
	 * 
	 * @param exerciseSettings The exercise settings
	 * @param parser           The Stanford CoreNLP parser
	 * @param generator        The Simple NLG generator
	 */
	public static void prepareConstructions(DocumentExerciseSettings exerciseSettings, NlpManager nlpManager) {
		ArrayList<Construction> constructionsToAdd = new ArrayList<>();
		ArrayList<Construction> constructionsToRemove = new ArrayList<>();
		int sentenceIndex = 0;
		ArrayList<SentenceAnnotations> coveredSentences = new ArrayList<>();
		for (Construction construction : exerciseSettings.getConstructions()) {
			if (exerciseSettings.getContentType() == ExerciseType.JUMBLED_SENTENCES) {
				SentenceAnnotations sent = nlpManager.getRelevantSentence(construction.getConstructionIndices());
				if (sent != null && !coveredSentences.contains(sent)) {
					coveredSentences.add(sent);

					ArrayList<Construction> sentenceParts = nlpManager.getSentencesParts(sent);
					if (sentenceParts.size() > 1) {
						sentenceIndex++;
						for (Construction sentencePart : sentenceParts) {
							sentencePart.setSentenceIndex(sentenceIndex);
							sentencePart.setConstruction(construction.getConstruction());
						}
						constructionsToAdd.addAll(sentenceParts);
					}
				}

				constructionsToRemove.add(construction);
			} else if (construction.getConstruction().startsWith("cond")) {
				Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> clauses = nlpManager
						.getConditionalClauses(construction.getConstructionIndices());
				if (clauses != null) {
					int r = 0;
					if (exerciseSettings.getBrackets().contains(BracketsProperties.EITHER_CLAUSE)) {
						r = new Random().nextInt(2) + 1;
					}

					ArrayList<ConditionalConstruction> newConstructions = new ArrayList<>();
					Pair<Integer, Integer> mainClauseConstructionIndices = null;
					Pair<Integer, Integer> ifClauseConstructionIndices = null;

					if (clauses.first != null && (exerciseSettings.getContentType().equals(ExerciseType.SINGLE_CHOICE)
							|| exerciseSettings.getBrackets().contains(BracketsProperties.MAIN_CLAUSE) || r == 1)) {
						mainClauseConstructionIndices = nlpManager.extractVerbCluster(clauses.first);
					}
					if (clauses.second != null && (exerciseSettings.getContentType().equals(ExerciseType.SINGLE_CHOICE)
							|| exerciseSettings.getBrackets().contains(BracketsProperties.IF_CLAUSE) || r == 2)) {
						ifClauseConstructionIndices = nlpManager.extractVerbCluster(clauses.second);
					}

					if (mainClauseConstructionIndices != null) {
						newConstructions.add(new ConditionalConstruction(construction.getConstruction(),
								mainClauseConstructionIndices, true, ifClauseConstructionIndices));
					}
					if (ifClauseConstructionIndices != null) {
						newConstructions.add(new ConditionalConstruction(construction.getConstruction(),
								ifClauseConstructionIndices, false, mainClauseConstructionIndices));
					}

					if (exerciseSettings.getContentType().equals(ExerciseType.DRAG_AND_DROP_MULTI)) {
						if (newConstructions.size() < 2) {
							// we can't use it for a multi-exercise Drag & Drop task if we don't have both
							// clauses
							newConstructions.clear();
						} else {
							storeOtherConstructionsInSentence(exerciseSettings,
									new ArrayList<Construction>(newConstructions));
						}
					}

					for (ConditionalConstruction newConstruction : newConstructions) {
						// try to limit the construction to max. 30 characters
						if (exerciseSettings.getContentType().equals(ExerciseType.DRAG_AND_DROP_SINGLE)
								&& newConstruction.getConstructionIndices().second
										- newConstruction.getConstructionIndices().first > 30) {
							if (newConstruction.isMainClause()) {
								if (mainClauseConstructionIndices != null) {
									newConstruction.setConstructionIndices(mainClauseConstructionIndices);
								}
							} else {
								if (ifClauseConstructionIndices != null) {
									newConstruction.setConstructionIndices(ifClauseConstructionIndices);
								}
							}

							if (newConstruction.getConstructionIndices().second
									- newConstruction.getConstructionIndices().first > 30) {
								CoreLabel mainVerb = nlpManager.getMainVerb(newConstruction.getConstructionIndices());
								if (mainVerb != null) {
									newConstruction.setConstructionIndices(
											new Pair<>(mainVerb.beginPosition(), mainVerb.endPosition()));
								}
							}
						}
						constructionsToAdd.add(newConstruction);
					}
				}
				constructionsToRemove.add(construction);
			} else if (construction.getConstruction().startsWith("adj")
					|| construction.getConstruction().startsWith("adv")) {
				if (exerciseSettings.getContentType().equals(ExerciseType.MARK_THE_WORDS)) {
					// TODO: if we decide to allow the client to specify whether to split synthetic
					// forms for mark, we have to do that here
				} else if (exerciseSettings.getContentType().equals(ExerciseType.DRAG_AND_DROP_SINGLE)
						&& construction.getConstructionIndices().second
								- construction.getConstructionIndices().first > 30) {
					Pair<Integer, Integer> mainComparison = nlpManager
							.getMainComparison(construction.getConstructionIndices());
					if (mainComparison != null) {
						construction.setConstructionIndices(mainComparison);
					}
				}
			} else if (construction.getConstruction().startsWith("passive")
					|| construction.getConstruction().startsWith("active")) {
				if (exerciseSettings.getContentType().equals(ExerciseType.FILL_IN_THE_BLANKS)
						&& exerciseSettings.getBrackets().contains(BracketsProperties.ACTIVE_SENTENCE)) {
					Pair<Integer, Integer> sentenceIndices = nlpManager
							.getSentenceIndices(construction.getConstructionIndices());
					if (sentenceIndices != null) {
						construction.setConstructionIndices(sentenceIndices);
					} else {
						constructionsToRemove.add(construction);
					}
				} else if (exerciseSettings.getContentType().equals(ExerciseType.DRAG_AND_DROP_MULTI)
						|| exerciseSettings.getContentType().equals(ExerciseType.DRAG_AND_DROP_SINGLE)) {
					ArrayList<Pair<Integer, Integer>> components = nlpManager
							.getPassiveSentenceComponents(construction.getConstructionIndices());
					ArrayList<Construction> newConstructions = new ArrayList<>();
					if (components != null) {
						for (int i = 1; i <= 3; i++) {
							if (components.get(i) != null) {
								if (exerciseSettings.getBrackets().contains(BracketsProperties.VERB_SPLITTING)
										&& i == 2) {
									ArrayList<Pair<Integer, Integer>> parts = nlpManager
											.splitParticiple(components.get(i));
									if (parts != null) { // if the splitting wasn't successful, we keep it as entire
															// cluster
										for (Pair<Integer, Integer> part : parts) {
											newConstructions
													.add(new Construction(construction.getConstruction(), part));
										}
									}
								} else {
									newConstructions
											.add(new Construction(construction.getConstruction(), components.get(i)));
								}
							}
						}
					}

					if (exerciseSettings.getContentType().equals(ExerciseType.DRAG_AND_DROP_MULTI)) {
						storeOtherConstructionsInSentence(exerciseSettings, newConstructions);
					}

					constructionsToAdd.addAll(newConstructions);
					constructionsToRemove.add(construction);
				}
			} else if (construction.getConstruction().startsWith("present")) {
				// Check if the 3rd pers. is correct
				Boolean isThirdPerson = nlpManager.isThirdSingular(construction.getConstructionIndices());
				if (isThirdPerson == null) {
					constructionsToRemove.add(construction);
				} else {
					boolean isLabelledThirdPerson = construction.getConstruction().endsWith("-3");
					if (isThirdPerson != isLabelledThirdPerson) {
						// check if we actually want the construction
						if (isThirdPerson
								&& !exerciseSettings.getConstructions().stream()
										.anyMatch((c) -> c.getConstruction().endsWith("-3"))
								|| !isThirdPerson && !exerciseSettings.getConstructions().stream()
										.anyMatch((c) -> c.getConstruction().endsWith("-not3"))) {
							constructionsToRemove.add(construction);
						} else {
							String correctConstruction;
							if (isThirdPerson) {
								if (construction.getConstruction().contains("-question-")) {
									if (construction.getConstruction().contains("-neg-")) {
										correctConstruction = DetailedConstruction.QUEST_NEG_3;
									} else {
										correctConstruction = DetailedConstruction.QUEST_AFFIRM_3;
									}
								} else {
									if (construction.getConstruction().contains("-neg-")) {
										correctConstruction = DetailedConstruction.STMT_NEG_3;
									} else {
										correctConstruction = DetailedConstruction.STMT_AFFIRM_3;
									}
								}
							} else {
								if (construction.getConstruction().contains("-question-")) {
									if (construction.getConstruction().contains("-neg-")) {
										correctConstruction = DetailedConstruction.QUEST_NEG_NOT3;
									} else {
										correctConstruction = DetailedConstruction.QUEST_AFFIRM_NOT3;
									}
								} else {
									if (construction.getConstruction().contains("-neg-")) {
										correctConstruction = DetailedConstruction.STMT_NEG_NOT3;
									} else {
										correctConstruction = DetailedConstruction.STMT_AFFIRM_NOT3;
									}
								}
							}
							construction.setConstruction(correctConstruction);
						}
					}
				}
			} else if (construction.getConstruction().startsWith("TENSE_")
					&& exerciseSettings.getContentType().equals(ExerciseType.DRAG_AND_DROP_SINGLE)
					&& construction.getConstructionIndices().second
							- construction.getConstructionIndices().first > 30) {
				CoreLabel mainVerb = nlpManager.getMainVerb(construction.getConstructionIndices());
				if (mainVerb != null) {
					construction.setConstructionIndices(new Pair<>(mainVerb.beginPosition(), mainVerb.endPosition()));
				}
			} else if ((construction.getConstruction().equals(DetailedConstruction.WHICH)
					|| construction.getConstruction().equals(DetailedConstruction.WHO)
					|| construction.getConstruction().equals(DetailedConstruction.THAT)
					|| construction.getConstruction().equals(DetailedConstruction.OTHERPRN))
					&& exerciseSettings.getContentType().equals(ExerciseType.DRAG_AND_DROP_MULTI)) {
				ArrayList<Pair<Integer, Integer>> components = nlpManager
						.getRelativeClauseComponents(construction.getConstructionIndices());
				ArrayList<Construction> newConstructions = new ArrayList<>();
				for (Pair<Integer, Integer> component : components) {
					if (!exerciseSettings.getPlainText().substring(component.first, component.second)
							.matches("[\\p{Punct}\\s\\h]*")) {
						newConstructions.add(new Construction(construction.getConstruction(), component));
					}
				}

				storeOtherConstructionsInSentence(exerciseSettings, newConstructions);

				constructionsToAdd.addAll(newConstructions);
				constructionsToRemove.add(construction);
			}
		}

		for (Construction construction : constructionsToRemove) {
			exerciseSettings.getConstructions().remove(construction);
		}

		for (Construction construction : constructionsToAdd) {
			exerciseSettings.getConstructions().add(construction);
		}

		// Remove overlapping constructions (can occur e.g. with synthetic and analytic
		// comparatives)
		HashSet<Construction> activeConstructionsToRemove = new HashSet<>();
		for (Construction construction : exerciseSettings.getConstructions()) {
			for (Construction otherConstruction : exerciseSettings.getConstructions()) {
				if (construction != otherConstruction && (Math.max(construction.getConstructionIndices().first,
						otherConstruction.getConstructionIndices().first) < Math.min(
								construction.getConstructionIndices().second,
								otherConstruction.getConstructionIndices().second))) {
					if (construction.getConstructionIndices().second - construction
							.getConstructionIndices().first > otherConstruction.getConstructionIndices().second
									- otherConstruction.getConstructionIndices().first) {
						activeConstructionsToRemove.add(otherConstruction);
					} else {
						activeConstructionsToRemove.add(construction);
					}
				}

			}
		}

		for (Construction construction : activeConstructionsToRemove) {
			exerciseSettings.getConstructions().remove(construction);
		}
	}

	private static void storeOtherConstructionsInSentence(DocumentExerciseSettings exerciseSettings,
			ArrayList<Construction> newConstructions) {
		for (Construction newConstruction : newConstructions) {
			for (Construction otherConstruction : newConstructions) {
				if (newConstruction != otherConstruction) {
					newConstruction.getSentenceConstructions().add(otherConstruction);
				}
			}
		}
	}

}