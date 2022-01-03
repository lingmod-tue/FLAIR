package com.flair.server.exerciseGeneration.InputParsing.ConfigParsing;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.flair.server.exerciseGeneration.ConstructionTextPart;
import com.flair.server.exerciseGeneration.Distractor;
import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.HtmlTextPart;
import com.flair.server.exerciseGeneration.PlainTextPart;
import com.flair.server.exerciseGeneration.TargetAndClauseItems;
import com.flair.server.exerciseGeneration.TextPart;
import com.flair.shared.exerciseGeneration.Pair;

public class ConditionalConfigParser {

	/**
	 * Compiles the exercise information for Categorization exercises.
	 * 
	 * @param configData           The data from the config file
	 * @param ifClauseFirst        <code>true</code> if the if-clause is to be put before
	 *                             the main clause
	 * @param randomizeClauseOrder <code>true</code> if the order of if- and main clauses
	 *                             is to be determined randomly for each sentence.
	 * @return The exercise information
	 */
	private ExerciseData generateCategorizationTask(ArrayList<ExerciseConfigData> configData, boolean ifClauseFirst,
			boolean randomizeClauseOrder) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseConfigData id : configData) {
			ConditionalExerciseConfigData itemData = (ConditionalExerciseConfigData) id;
			if (itemData.getConditionalType() == 1 || itemData.getConditionalType() == 2) {
				String ifClause = generateSentencesFromPositions(itemData.getPositionsIfClause());
				String mainClause = generateSentencesFromPositions(itemData.getPositionsMainClause());

				if (randomizeClauseOrder) {
					ifClauseFirst = Math.random() > 0.5;
				}

				String firstClause = StringUtils.capitalize(ifClauseFirst ? ifClause : mainClause);
				String secondClause = ifClauseFirst ? mainClause : ifClause;
				String delimiter = ifClauseFirst ? ", " : " ";
				String sentence = firstClause + delimiter + secondClause + ".";
				String category = itemData.getConditionalType() == 1 ? "Type 1" : "Type 2";

				ConstructionTextPart c = new ConstructionTextPart(sentence, sentenceId++);
				c.setCategory(category);
				parts.add(c);
			}
		}

		return new ExerciseData(parts);
	}

	/**
	 * Compiles the exercise information for Memory exercises.
	 * 
	 * @param configData    The data from the config file
	 * @param ifClauseFirst <code>true</code> if the if-clause is to be put before the
	 *                      main clause
	 * @return The exercise information
	 */
	private ExerciseData generateMemoryTask(ArrayList<ExerciseConfigData> configData, boolean useIfClause) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseConfigData id : configData) {
			ConditionalExerciseConfigData itemData = (ConditionalExerciseConfigData) id;
			String targetClause = useIfClause ? generateSentencesFromPositions(itemData.getPositionsIfClause())
					: generateSentencesFromPositions(itemData.getPositionsMainClause());
			String translationTargetClause = useIfClause ? itemData.getTranslationIfClause()
					: itemData.getTranslationMainClause();

			ConstructionTextPart c = new ConstructionTextPart(targetClause, sentenceId++);
			c.setTranslation(translationTargetClause);
			parts.add(c);
		}

		return new ExerciseData(parts);
	}

	/**
	 * Compiles the exercise information for exercises with a target within the text
	 * flow.
	 * 
	 * @param configData               The data from the config file
	 * @param ifClauseFirst            <code>true</code> if the if-clause is to be put
	 *                                 before the main clause
	 * @param targetIfClause           <code>true</code> if the constructions in the if
	 *                                 clause are to be targeted
	 * @param targetMainClause         <code>true</code> if the constructions in the main
	 *                                 clause are to be targeted
	 * @param nDistractors             The number of distractors to use for the
	 *                                 exercise
	 * @param randomizeClauseOrder     <code>true</code> if the order of if- and main
	 *                                 clauses is to be determined randomly for each
	 *                                 sentence.
	 * @param lemmasInBrackets         <code>true</code> if the lemma of the target is to
	 *                                 be given in brackets
	 * @param useDistractorLemma       <code>true</code> if a semantic distractor is to be
	 *                                 given in brackets
	 * @param targetEntireClause       <code>true</code> if the entire target clause is to
	 *                                 be converted into constructions
	 * @param giveLemmasInInstructions <code>true</code> if the lemmas of all targets and
	 *                                 2 semantic distractors are to be given in the
	 *                                 instructions
	 * @return The exercise information
	 */
	private ExerciseData generateGapTask(ArrayList<ExerciseConfigData> configData, boolean ifClauseFirst,
			boolean targetIfClause, boolean targetMainClause, int nDistractors, boolean randomizeClauseOrder,
			boolean lemmasInBrackets, boolean useDistractorLemma, boolean targetEntireClause,
			boolean giveLemmasInInstructions, boolean isUnderline) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		HashSet<String> allLemmas = new HashSet<>();
		HashSet<String> allDistractorLemmas = new HashSet<>();

		for (ExerciseConfigData id : configData) {
			ConditionalExerciseConfigData itemData = (ConditionalExerciseConfigData) id;

			if (parts.size() > 0) {
				parts.add(new HtmlTextPart(" <br>", sentenceId));
			}

			TargetAndClauseItems targetAndClauseItems = getTargetAndClauseItems(itemData, randomizeClauseOrder,
					ifClauseFirst, targetIfClause, targetMainClause, 
					isUnderline ? itemData.getUnderlineIfClause() : itemData.getGapIfClause(),
					isUnderline ? itemData.getUnderlineMainClause() : itemData.getGapMainClause(), targetEntireClause);

			ArrayList<Pair<Integer, String>> positionParts = new ArrayList<>();
			boolean inConstruction = false;
			for (Pair<Integer, String> position : targetAndClauseItems.getPositions()) {
				if (targetAndClauseItems.getTargetPositions().size() == 0
						|| position.first < targetAndClauseItems.getTargetPositions().get(0).first) {
					if (inConstruction) {
						addConstructionPart(positionParts, sentenceId, nDistractors, targetAndClauseItems, targetEntireClause, 
								lemmasInBrackets, allLemmas, useDistractorLemma, giveLemmasInInstructions,
								allDistractorLemmas, parts);
					} 
					positionParts.add(position);
					inConstruction = false;
				} else {
					if (!inConstruction) {
						parts.add(new PlainTextPart(generateSentencesFromPositions(positionParts), sentenceId));
						positionParts.clear();
					}
					
					positionParts.add(position);


					if (position.first == targetAndClauseItems.getTargetPositions().get(0).second) {
						targetAndClauseItems.getTargetPositions().remove(0);
					}
					inConstruction = true;
				}
			}

			if (inConstruction) {
				addConstructionPart(positionParts, sentenceId, nDistractors, targetAndClauseItems, targetEntireClause, 
						lemmasInBrackets, allLemmas, useDistractorLemma, giveLemmasInInstructions,
						allDistractorLemmas, parts);
			}
			positionParts.add(new Pair<>(positionParts.size() + 2, "."));
			parts.add(new PlainTextPart(generateSentencesFromPositions(positionParts), sentenceId));
			positionParts.clear();
			sentenceId++;
		}

		ExerciseData data = new ExerciseData(parts);
		if (giveLemmasInInstructions) {
			ArrayList<String> lemmas = new ArrayList<String>(allLemmas);
			ArrayList<String> distractorLemmas = new ArrayList<String>(allDistractorLemmas);

			for (int i = 0; i < 2; i++) {
				Collections.shuffle(distractorLemmas);
				lemmas.add(distractorLemmas.get(0));
				distractorLemmas.remove(0);
			}
			Collections.shuffle(lemmas);
			data.setInstructionLemmas(lemmas);
		}

		return data;
	}
	
	/**
	 * Generates a construction element and adds it to the list of TextParts
	 * @param positionParts				The text items separated into position groups
	 * @param sentenceId				The index of the sentence
	 * @param nDistractors				The number of distractors to use for the
	 *                                 	exercise
	 * @param targetAndClauseItems		The positions in correct order and constructions targeted by the
	 *         							exercise 
	 * @param targetEntireClause		<code>true</code> if the entire target clause is to
	 *                                 	be converted into constructions
	 * @param lemmasInBrackets			<code>true</code> if the lemma of the target is to
	 *                                 	be given in brackets
	 * @param allLemmas					The lemmas of all targets constructions
	 * @param useDistractorLemma		<code>true</code> if a semantic distractor is to be
	 *                                 	given in brackets
	 * @param giveLemmasInInstructions	<code>true</code> if the lemmas of all targets and
	 *                                 	2 semantic distractors are to be given in the
	 * @param allDistractorLemmas		The distractor lemmas for all target constructions
	 * @param parts						The TextPart items
	 */
	private void addConstructionPart(ArrayList<Pair<Integer, String>> positionParts, int sentenceId, int nDistractors,
			TargetAndClauseItems targetAndClauseItems, boolean targetEntireClause, boolean lemmasInBrackets, 
			HashSet<String> allLemmas, boolean useDistractorLemma, boolean giveLemmasInInstructions,
			HashSet<String> allDistractorLemmas, ArrayList<TextPart> parts) {
		String constructionText = generateSentencesFromPositions(positionParts);
		ConstructionTextPart c = new ConstructionTextPart(constructionText, sentenceId);

		if (nDistractors > 0) {
			ArrayList<Distractor> distractors = new ArrayList<>();
			for(ArrayList<Pair<Integer, String>> distractorList : targetAndClauseItems.getTargetDistractors()) {
				Collections.shuffle(distractorList);
			}
			while (distractors.size() < nDistractors) {
				distractors.add(new Distractor(targetAndClauseItems.getTargetDistractors().get(0)
						.get(distractors.size()).second));
			}
			targetAndClauseItems.getTargetDistractors().remove(0);
			distractors.add(new Distractor(constructionText));

			c.setDistractors(distractors);
		}

		if(giveLemmasInInstructions) {
			allLemmas.add(targetAndClauseItems.getLemmas().get(0));
			allDistractorLemmas.add(targetAndClauseItems.getDistractorLemmas().get(0));
		}
		
		if (targetEntireClause) {
			c.getBrackets()
					.add(StringUtils.join(targetAndClauseItems.getGivenLemmas().get(0), ","));
			targetAndClauseItems.getGivenLemmas().remove(0);
		} else if (lemmasInBrackets) {
			ArrayList<String> lemmaComponents = new ArrayList<>();
			lemmaComponents.add(targetAndClauseItems.getLemmas().get(0));
			
			if (useDistractorLemma) {
				lemmaComponents.add(targetAndClauseItems.getDistractorLemmas().get(0));
				Collections.shuffle(lemmaComponents);
				c.getBrackets().add(StringUtils.join(lemmaComponents, "/"));
			} else {
				c.getBrackets().add(lemmaComponents.get(0));
			}
		}
		
		if(targetAndClauseItems.getLemmas().size() > 0) {
			targetAndClauseItems.getLemmas().remove(0);
		}
		if(targetAndClauseItems.getDistractorLemmas().size() > 0) {
			targetAndClauseItems.getDistractorLemmas().remove(0);
		}

		parts.add(c);
		positionParts.clear();
	}

	/**
	 * Compiles the exercise information for Jumbled Sentences exercises.
	 * 
	 * @param configData           The data from the config file
	 * @param ifClauseFirst        <code>true</code> if the if-clause is to be put before
	 *                             the main clause
	 * @param targetIfClause       <code>true</code> if the constructions in the if clause
	 *                             are to be targeted
	 * @param targetMainClause     <code>true</code> if the constructions in the main
	 *                             clause are to be targeted
	 * @param randomizeClauseOrder <code>true</code> if the order of if- and main clauses
	 *                             is to be determined randomly for each sentence.
	 * @return The exercise information
	 */
	private ExerciseData generateJSTask(ArrayList<ExerciseConfigData> configData, boolean ifClauseFirst,
			boolean targetIfClause, boolean targetMainClause, boolean randomizeClauseOrder) {

		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseConfigData id : configData) {
			ConditionalExerciseConfigData itemData = (ConditionalExerciseConfigData) id;

			TargetAndClauseItems targetAndClauseItems = getTargetAndClauseItems(itemData, randomizeClauseOrder,
					ifClauseFirst, targetIfClause, targetMainClause, itemData.getGapIfClause(),
					itemData.getGapMainClause(), false);

			for (Pair<Integer, String> position : targetAndClauseItems.getPositions()) {
				parts.add(new ConstructionTextPart(position.second, sentenceId));
			}
			parts.add(new ConstructionTextPart(".", sentenceId));

			sentenceId++;
		}

		return new ExerciseData(parts);
	}

	/**
	 * Extracts the clauses in the correct order and the constructions which are
	 * located in the clause to target.
	 * 
	 * @param itemData                The config data of the current sentence
	 * @param randomizeClauseOrder    <code>true</code> if the order of if- and main
	 *                                clauses is to be determined randomly for each
	 *                                sentence.
	 * @param ifClauseFirst           <code>true</code> if the if-clause is to be put
	 *                                before the main clause
	 * @param targetIfClause          <code>true</code> if the constructions in the if
	 *                                clause are to be targeted
	 * @param targetMainClause        <code>true</code> if the constructions in the main
	 *                                clause are to be targeted
	 * @param constructionsIfClause   The constructions in the if clause. Gaps for
	 *                                FiB and SC exercises, Underline for Underline
	 *                                exercises.
	 * @param constructionsMainClause The constructions in the main clause. Gaps for
	 *                                FiB and SC exercises, Underline for Underline
	 *                                exercises.
	 * @param targetEntireClause      <code>true</code> if the entire target clause is to
	 *                                be converted into constructions
	 * @return The positions in correct order and constructions targeted by the
	 *         exercise
	 */
	private TargetAndClauseItems getTargetAndClauseItems(ConditionalExerciseConfigData itemData,
			boolean randomizeClauseOrder, boolean ifClauseFirst, boolean targetIfClause, boolean targetMainClause,
			ArrayList<Pair<Integer, Integer>> constructionsIfClause,
			ArrayList<Pair<Integer, Integer>> constructionsMainClause, boolean targetEntireClause) {
		ArrayList<Pair<Integer, String>> ifPositions = new ArrayList<>(itemData.getPositionsIfClause());
		ArrayList<Pair<Integer, String>> mainPositions = new ArrayList<>(itemData.getPositionsMainClause());

		ArrayList<Pair<Integer, String>> positions;
		ArrayList<Pair<Integer, Integer>> targetPositions = new ArrayList<>();
		ArrayList<ArrayList<Pair<Integer, String>>> targetDistractors = new ArrayList<>();
		ArrayList<String> distractorLemmas = new ArrayList<>();
		ArrayList<String> lemmas = new ArrayList<>();
		ArrayList<ArrayList<String>> givenLemmas = new ArrayList<>();

		if (randomizeClauseOrder) {
			ifClauseFirst = Math.random() > 0.5;
		}

		if (ifClauseFirst) {
			positions = new ArrayList<>(ifPositions);
			positions.add(new Pair<>(positions.size() + 1, ","));
			for (Pair<Integer, String> p : mainPositions) {
				positions.add(new Pair<>(ifPositions.size() + 1 + p.first, p.second));
			}

			if (targetIfClause) {
				if (targetEntireClause) {
					targetPositions.add(new Pair<>(2, ifPositions.size()));
				} else {
					targetPositions.addAll(constructionsIfClause);
					targetDistractors.add(itemData.getDistractorsIfClause());
				}
				distractorLemmas.add(itemData.getDistractorLemmaIfClause());
				lemmas.add(itemData.getLemmaIfClause());
				givenLemmas.add(itemData.getBracketsIfClause());
			}
			if (targetMainClause) {
				if (targetEntireClause) {
					targetPositions
							.add(new Pair<>(ifPositions.size() + 2, ifPositions.size() + mainPositions.size() + 1));
				} else {
					for (Pair<Integer, Integer> tp : constructionsMainClause) {
						targetPositions
								.add(new Pair<>(ifPositions.size() + 1 + tp.first, ifPositions.size() + 1 + tp.second));
					}
					targetDistractors.add(itemData.getDistractorsMainClause());
				}
				distractorLemmas.add(itemData.getDistractorLemmaMainClause());
				lemmas.add(itemData.getLemmaMainClause());
				givenLemmas.add(itemData.getBracketsMainClause());
			}

		} else {
			positions = new ArrayList<>(mainPositions);
			for (Pair<Integer, String> p : ifPositions) {
				positions.add(new Pair<>(mainPositions.size() + p.first, p.second));
			}

			if (targetMainClause) {
				if (targetEntireClause) {
					targetPositions.add(new Pair<>(1, mainPositions.size()));
				} else {
					targetPositions.addAll(constructionsMainClause);
					targetDistractors.add(itemData.getDistractorsMainClause());
				}
				distractorLemmas.add(itemData.getDistractorLemmaMainClause());
				lemmas.add(itemData.getLemmaMainClause());
				givenLemmas.add(itemData.getBracketsMainClause());
			}

			if (targetIfClause) {
				if (targetEntireClause) {
					targetPositions
							.add(new Pair<>(mainPositions.size() + 2, mainPositions.size() + ifPositions.size() + 1));
				} else {
					for (Pair<Integer, Integer> tp : constructionsIfClause) {
						targetPositions.add(
								new Pair<>(mainPositions.size() + tp.first, mainPositions.size() + tp.second));
					}
					targetDistractors.add(itemData.getDistractorsIfClause());
				}
				distractorLemmas.add(itemData.getDistractorLemmaIfClause());
				lemmas.add(itemData.getLemmaIfClause());
				givenLemmas.add(itemData.getBracketsIfClause());
			}
		}

		positions.set(0, new Pair<>(positions.get(0).first, StringUtils.capitalize(positions.get(0).second)));

		return new TargetAndClauseItems(positions, targetPositions, targetDistractors, lemmas, distractorLemmas,
				givenLemmas);
	}

	/**
	 * Punctuation marks before which no space is to be inserted
	 */
	private static final String punctuations = ".,:;!?";

	/**
	 * Compiles a sentence from a list of positions with correct spacing.
	 * 
	 * @param positions The list of positions
	 * @return The sentences string with correct spacing
	 */
	private String generateSentencesFromPositions(ArrayList<Pair<Integer, String>> positions) {
		StringBuilder sb = new StringBuilder();
		for (Pair<Integer, String> position : positions) {
			if (!position.second.isEmpty()) {
				if (!(position.first == 1) && !punctuations.contains(position.second.charAt(0) + "")) {
					sb.append(" ");
				}
				sb.append(position.second);
			}
		}

		return sb.toString().trim();
	}

	/**
	 * Parses the input stream and generates a set of exercise data of varying complexities.
	 * @param inputStream	The input stream of the config file
	 * @return	The exercise data structured into exercise types and blocks of max. 10 items
	 */
	public HashMap<String, ArrayList<HashMap<String, ExerciseData>>> parseConfigFile(InputStream inputStream) {
		ArrayList<ExerciseConfigData> configData = new ConditionalExcelFileReader().readExcelFile(inputStream);

		HashMap<String, ArrayList<ExerciseConfigData>> configs = new HashMap<>();
		for (ExerciseConfigData ecd : configData) {
			ConditionalExerciseConfigData cd = (ConditionalExerciseConfigData) ecd;
			String key = cd.getActivity() + "_" + (cd.isType1VsType2() ? "condTypes" : "condForm");
			if (!configs.containsKey(key)) {
				configs.put(key, new ArrayList<>());
			}
			configs.get(key).add(cd);
		}

        HashMap<String, ArrayList<HashMap<String, ExerciseData>>> activities = new HashMap<>();
		for (Entry<String, ArrayList<ExerciseConfigData>> entry : configs.entrySet()) {
			ArrayList<HashMap<String, ExerciseData>> blocks = new ArrayList<>();
			HashMap<String, ExerciseData> exercises = new HashMap<>();

			if (entry.getKey().endsWith("condTypes")) {
				exercises = new HashMap<>();
				// categorization if first
				exercises.put("" + 7, generateCategorizationTask(entry.getValue(), true, false));
				// if-clause, if first
				// sc 1d
				exercises.put("" + 1,
						generateGapTask(entry.getValue(), true, true, false, 1, false, false, false, false, false, false));
				// sc 3d
				exercises.put("" + 2,
						generateGapTask(entry.getValue(), true, true, false, 3, false, false, false, false, false, false));
				// fib lemma blank
				exercises.put("" + 3,
						generateGapTask(entry.getValue(), true, true, false, 0, false, true, false, false, false, false));
				// fib lemma/distractor blank
				exercises.put("" + 4,
						generateGapTask(entry.getValue(), true, true, false, 0, false, true, true, false, false, false));
				// fib entire clause brackets
				exercises.put("" + 9,
						generateGapTask(entry.getValue(), true, true, false, 0, false, true, false, true, false, false));
				blocks.add(exercises);

				exercises = new HashMap<>();
				// categorization main first
				exercises.put("" + 7, generateCategorizationTask(entry.getValue(), false, false));
				// if-clause, main first
				// sc 1d
				exercises.put("" + 1,
						generateGapTask(entry.getValue(), false, true, false, 1, false, false, false, false, false, false));
				// sc 3d
				exercises.put("" + 2,
						generateGapTask(entry.getValue(), false, true, false, 3, false, false, false, false, false, false));
				// fib lemma blank
				exercises.put("" + 3,
						generateGapTask(entry.getValue(), false, true, false, 0, false, true, false, false, false, false));
				// fib lemma/distractor blank
				exercises.put("" + 4,
						generateGapTask(entry.getValue(), false, true, false, 0, false, true, true, false, false, false));
				// fib entire clause brackets
				exercises.put("" + 9,
						generateGapTask(entry.getValue(), false, true, false, 0, false, true, false, true, false, false));
				blocks.add(exercises);

				exercises = new HashMap<>();
				// categorization random first
				exercises.put("" + 7, generateCategorizationTask(entry.getValue(), false, true));
				// if-clause, random first
				// sc 1d
				exercises.put("" + 1,
						generateGapTask(entry.getValue(), false, true, false, 1, true, false, false, false, false, false));
				// sc 3d
				exercises.put("" + 2,
						generateGapTask(entry.getValue(), false, true, false, 3, true, false, false, false, false, false));
				// fib lemma blank
				exercises.put("" + 3,
						generateGapTask(entry.getValue(), false, true, false, 0, true, true, false, false, false, false));
				// fib lemma/distractor blank
				exercises.put("" + 4,
						generateGapTask(entry.getValue(), false, true, false, 0, true, true, true, false, false, false));
				// fib entire clause brackets
				exercises.put("" + 9,
						generateGapTask(entry.getValue(), false, true, false, 0, true, true, false, true, false, false));
				blocks.add(exercises);

				exercises = new HashMap<>();
				// main clause, if first
				// sc 1d
				exercises.put("" + 1,
						generateGapTask(entry.getValue(), true, false, true, 1, false, false, false, false, false, false));
				// sc 3d
				exercises.put("" + 2,
						generateGapTask(entry.getValue(), true, false, true, 3, false, false, false, false, false, false));
				// fib lemma blank
				exercises.put("" + 3,
						generateGapTask(entry.getValue(), true, false, true, 0, false, true, false, false, false, false));
				// fib lemma/distractor blank
				exercises.put("" + 4,
						generateGapTask(entry.getValue(), true, false, true, 0, false, true, true, false, false, false));
				// fib entire clause brackets
				exercises.put("" + 9,
						generateGapTask(entry.getValue(), true, false, true, 0, false, true, false, true, false, false));
				blocks.add(exercises);

				exercises = new HashMap<>();
				// main clause, main first
				// sc 1d
				exercises.put("" + 1,
						generateGapTask(entry.getValue(), false, false, true, 1, false, false, false, false, false, false));
				// sc 3d
				exercises.put("" + 2,
						generateGapTask(entry.getValue(), false, false, true, 3, false, false, false, false, false, false));
				// fib lemma blank
				exercises.put("" + 3,
						generateGapTask(entry.getValue(), false, false, true, 0, false, true, false, false, false, false));
				// fib lemma/distractor blank
				exercises.put("" + 4,
						generateGapTask(entry.getValue(), false, false, true, 0, false, true, true, false, false, false));
				// fib entire clause brackets
				exercises.put("" + 9,
						generateGapTask(entry.getValue(), false, false, true, 0, false, true, false, true, false, false));
				blocks.add(exercises);

				exercises = new HashMap<>();
				// main clause, random first
				// sc 1d
				exercises.put("" + 1,
						generateGapTask(entry.getValue(), false, false, true, 1, true, false, false, false, false, false));
				// sc 3d
				exercises.put("" + 2,
						generateGapTask(entry.getValue(), false, false, true, 3, true, false, false, false, false, false));
				// fib lemma blank
				exercises.put("" + 3,
						generateGapTask(entry.getValue(), false, false, true, 0, true, true, false, false, false, false));
				// fib lemma/distractor blank
				exercises.put("" + 4,
						generateGapTask(entry.getValue(), false, false, true, 0, true, true, true, false, false, false));
				// fib entire clause brackets
				exercises.put("" + 9,
						generateGapTask(entry.getValue(), false, false, true, 0, true, true, false, true, false, false));
				blocks.add(exercises);

				exercises = new HashMap<>();
				// both clauses, if first
				// sc 1d
				exercises.put("" + 1,
						generateGapTask(entry.getValue(), true, true, true, 1, false, false, false, false, false, false));
				// sc 3d
				exercises.put("" + 2,
						generateGapTask(entry.getValue(), true, true, true, 3, false, false, false, false, false, false));
				// fib lemma blank
				exercises.put("" + 3,
						generateGapTask(entry.getValue(), true, true, true, 0, false, true, false, false, false, false));
				// fib lemma/distractor blank
				exercises.put("" + 4,
						generateGapTask(entry.getValue(), true, true, true, 0, false, true, true, false, false, false));
				// fib entire clause brackets
				exercises.put("" + 9,
						generateGapTask(entry.getValue(), true, true, true, 0, false, true, false, true, false, false));
				blocks.add(exercises);

				exercises = new HashMap<>();
				// both clauses, main first
				// sc 1d
				exercises.put("" + 1,
						generateGapTask(entry.getValue(), false, true, true, 1, false, false, false, false, false, false));
				// sc 3d
				exercises.put("" + 2,
						generateGapTask(entry.getValue(), false, true, true, 3, false, false, false, false, false, false));
				// fib lemma blank
				exercises.put("" + 3,
						generateGapTask(entry.getValue(), false, true, true, 0, false, true, false, false, false, false));
				// fib lemma/distractor blank
				exercises.put("" + 4,
						generateGapTask(entry.getValue(), false, true, true, 0, false, true, true, false, false, false));
				// fib entire clause brackets
				exercises.put("" + 9,
						generateGapTask(entry.getValue(), false, true, true, 0, false, true, false, true, false, false));
				blocks.add(exercises);

				exercises = new HashMap<>();
				// both clauses, random first
				// sc 1d
				exercises.put("" + 1,
						generateGapTask(entry.getValue(), false, true, true, 1, true, false, false, false, false, false));
				// sc 3d
				exercises.put("" + 2,
						generateGapTask(entry.getValue(), false, true, true, 3, true, false, false, false, false, false));
				// fib lemma blank
				exercises.put("" + 3,
						generateGapTask(entry.getValue(), false, true, true, 0, true, true, false, false, false, false));
				// fib lemma/distractor blank
				exercises.put("" + 4,
						generateGapTask(entry.getValue(), false, true, true, 0, true, true, true, false, false, false));
				// fib entire clause brackets
				exercises.put("" + 9,
						generateGapTask(entry.getValue(), false, true, true, 0, true, true, false, true, false, false));
				blocks.add(exercises);
			} else {
				exercises = new HashMap<>();
				// memory if-clause
				exercises.put("" + 0, generateMemoryTask(entry.getValue(), true));
				// if-clause, if first
				// sc 1d
				exercises.put("" + 1,
						generateGapTask(entry.getValue(), true, true, false, 1, false, false, false, false, false, false));
				// sc 3d
				exercises.put("" + 2,
						generateGapTask(entry.getValue(), true, true, false, 3, false, false, false, false, false, false));
				// fib lemma blank
				exercises.put("" + 3,
						generateGapTask(entry.getValue(), true, true, false, 0, false, true, false, false, false, false));
				// fib lemma/distractor blank
				exercises.put("" + 4,
						generateGapTask(entry.getValue(), true, true, false, 0, false, true, true, false, false, false));
				// fib lemma instuctions
				exercises.put("" + 5,
						generateGapTask(entry.getValue(), true, true, false, 0, false, false, false, false, true, false));
				// jumbled sentences
				exercises.put("" + 6, generateJSTask(entry.getValue(), true, true, false, false));
				// underline
				exercises.put("" + 8,
						generateGapTask(entry.getValue(), true, true, false, 0, false, false, false, false, false, true));
				// fib entire clause brackets
				exercises.put("" + 9,
						generateGapTask(entry.getValue(), true, true, false, 0, false, true, false, true, false, false));
				blocks.add(exercises);

				exercises = new HashMap<>();
				// if-clause, main first
				// sc 1d
				exercises.put("" + 1,
						generateGapTask(entry.getValue(), false, true, false, 1, false, false, false, false, false, false));
				// sc 3d
				exercises.put("" + 2,
						generateGapTask(entry.getValue(), false, true, false, 3, false, false, false, false, false, false));
				// fib lemma blank
				exercises.put("" + 3,
						generateGapTask(entry.getValue(), false, true, false, 0, false, true, false, false, false, false));
				// fib lemma/distractor blank
				exercises.put("" + 4,
						generateGapTask(entry.getValue(), false, true, false, 0, false, true, true, false, false, false));
				// fib lemma instuctions
				exercises.put("" + 5,
						generateGapTask(entry.getValue(), false, true, false, 0, false, false, false, false, true, false));
				// jumbled sentences
				exercises.put("" + 6, generateJSTask(entry.getValue(), false, true, false, false));
				// underline
				exercises.put("" + 8,
						generateGapTask(entry.getValue(), false, true, false, 0, false, false, false, false, false, true));
				// fib entire clause brackets
				exercises.put("" + 9,
						generateGapTask(entry.getValue(), false, true, false, 0, false, true, false, true, false, false));
				blocks.add(exercises);

				exercises = new HashMap<>();
				// if-clause, random first
				// sc 1d
				exercises.put("" + 1,
						generateGapTask(entry.getValue(), false, true, false, 1, true, false, false, false, false, false));
				// sc 3d
				exercises.put("" + 2,
						generateGapTask(entry.getValue(), false, true, false, 3, true, false, false, false, false, false));
				// fib lemma blank
				exercises.put("" + 3,
						generateGapTask(entry.getValue(), false, true, false, 0, true, true, false, false, false, false));
				// fib lemma/distractor blank
				exercises.put("" + 4,
						generateGapTask(entry.getValue(), false, true, false, 0, true, true, true, false, false, false));
				// fib lemma instuctions
				exercises.put("" + 5,
						generateGapTask(entry.getValue(), false, true, false, 0, true, false, false, false, true, false));
				// jumbled sentences
				exercises.put("" + 6, generateJSTask(entry.getValue(), false, true, false, true));
				// underline
				exercises.put("" + 8,
						generateGapTask(entry.getValue(), false, true, false, 0, true, false, false, false, false, true));
				// fib entire clause brackets
				exercises.put("" + 9,
						generateGapTask(entry.getValue(), false, true, false, 0, true, true, false, true, false, false));
				blocks.add(exercises);

				exercises = new HashMap<>();
				// memory main clause
				exercises.put("" + 0, generateMemoryTask(entry.getValue(), false));
				// main clause, if first
				// sc 1d
				exercises.put("" + 1,
						generateGapTask(entry.getValue(), true, false, true, 1, false, false, false, false, false, false));
				// sc 3d
				exercises.put("" + 2,
						generateGapTask(entry.getValue(), true, false, true, 3, false, false, false, false, false, false));
				// fib lemma blank
				exercises.put("" + 3,
						generateGapTask(entry.getValue(), true, false, true, 0, false, true, false, false, false, false));
				// fib lemma/distractor blank
				exercises.put("" + 4,
						generateGapTask(entry.getValue(), true, false, true, 0, false, true, true, false, false, false));
				// fib lemma instuctions
				exercises.put("" + 5,
						generateGapTask(entry.getValue(), true, false, true, 0, false, false, false, false, true, false));
				// jumbled sentences
				exercises.put("" + 6, generateJSTask(entry.getValue(), true, false, true, false));
				// underline
				exercises.put("" + 8,
						generateGapTask(entry.getValue(), true, false, true, 0, false, false, false, false, false, true));
				// fib entire clause brackets
				exercises.put("" + 9,
						generateGapTask(entry.getValue(), true, false, true, 0, false, true, false, true, false, false));
				blocks.add(exercises);

				exercises = new HashMap<>();
				// main clause, main first
				// sc 1d
				exercises.put("" + 1,
						generateGapTask(entry.getValue(), false, false, true, 1, false, false, false, false, false, false));
				// sc 3d
				exercises.put("" + 2,
						generateGapTask(entry.getValue(), false, false, true, 3, false, false, false, false, false, false));
				// fib lemma blank
				exercises.put("" + 3,
						generateGapTask(entry.getValue(), false, false, true, 0, false, true, false, false, false, false));
				// fib lemma/distractor blank
				exercises.put("" + 4,
						generateGapTask(entry.getValue(), false, false, true, 0, false, true, true, false, false, false));
				// fib lemma instuctions
				exercises.put("" + 5,
						generateGapTask(entry.getValue(), false, false, true, 0, false, false, false, false, true, false));
				// jumbled sentences
				exercises.put("" + 6, generateJSTask(entry.getValue(), false, false, true, false));
				// underline
				exercises.put("" + 8,
						generateGapTask(entry.getValue(), false, false, true, 0, false, false, false, false, false, true));
				// fib entire clause brackets
				exercises.put("" + 9,
						generateGapTask(entry.getValue(), false, false, true, 0, false, true, false, true, false, false));
				blocks.add(exercises);

				exercises = new HashMap<>();
				// main clause, random first
				// sc 1d
				exercises.put("" + 1,
						generateGapTask(entry.getValue(), false, false, true, 1, true, false, false, false, false, false));
				// sc 3d
				exercises.put("" + 2,
						generateGapTask(entry.getValue(), false, false, true, 3, true, false, false, false, false, false));
				// fib lemma blank
				exercises.put("" + 3,
						generateGapTask(entry.getValue(), false, false, true, 0, true, true, false, false, false, false));
				// fib lemma/distractor blank
				exercises.put("" + 4,
						generateGapTask(entry.getValue(), false, false, true, 0, true, true, true, false, false, false));
				// fib lemma instuctions
				exercises.put("" + 5,
						generateGapTask(entry.getValue(), false, false, true, 0, true, false, false, false, true, false));
				// jumbled sentences
				exercises.put("" + 6, generateJSTask(entry.getValue(), false, false, true, true));
				// underline
				exercises.put("" + 8,
						generateGapTask(entry.getValue(), false, false, true, 0, true, false, false, false, false, true));
				// fib entire clause brackets
				exercises.put("" + 9,
						generateGapTask(entry.getValue(), false, false, true, 0, true, true, false, true, false, false));
				blocks.add(exercises);

				exercises = new HashMap<>();
				// both clauses, if first
				// sc 1d
				exercises.put("" + 1,
						generateGapTask(entry.getValue(), true, true, true, 1, false, false, false, false, false, false));
				// sc 3d
				exercises.put("" + 2,
						generateGapTask(entry.getValue(), true, true, true, 3, false, false, false, false, false, false));
				// fib lemma blank
				exercises.put("" + 3,
						generateGapTask(entry.getValue(), true, true, true, 0, false, true, false, false, false, false));
				// fib lemma/distractor blank
				exercises.put("" + 4,
						generateGapTask(entry.getValue(), true, true, true, 0, false, true, true, false, false, false));
				// jumbled sentences
				exercises.put("" + 6, generateJSTask(entry.getValue(), true, true, true, false));
				// fib entire clause brackets
				exercises.put("" + 9,
						generateGapTask(entry.getValue(), true, true, true, 0, false, true, false, true, false, false));
				blocks.add(exercises);

				exercises = new HashMap<>();
				// both clauses, main first
				// sc 1d
				exercises.put("" + 1,
						generateGapTask(entry.getValue(), false, true, true, 1, false, false, false, false, false, false));
				// sc 3d
				exercises.put("" + 2,
						generateGapTask(entry.getValue(), false, true, true, 3, false, false, false, false, false, false));
				// fib lemma blank
				exercises.put("" + 3,
						generateGapTask(entry.getValue(), false, true, true, 0, false, true, false, false, false, false));
				// fib lemma/distractor blank
				exercises.put("" + 4,
						generateGapTask(entry.getValue(), false, true, true, 0, false, true, true, false, false, false));
				// fib entire clause brackets
				exercises.put("" + 9,
						generateGapTask(entry.getValue(), false, true, true, 0, false, true, false, true, false, false));
				blocks.add(exercises);

				exercises = new HashMap<>();
				// both clauses, random first
				// sc 1d
				exercises.put("" + 1,
						generateGapTask(entry.getValue(), false, true, true, 1, true, false, false, false, false, false));
				// sc 3d
				exercises.put("" + 2,
						generateGapTask(entry.getValue(), false, true, true, 3, true, false, false, false, false, false));
				// fib lemma blank
				exercises.put("" + 3,
						generateGapTask(entry.getValue(), false, true, true, 0, true, true, false, false, false, false));
				// fib lemma/distractor blank
				exercises.put("" + 4,
						generateGapTask(entry.getValue(), false, true, true, 0, true, true, true, false, false, false));
				// fib entire clause brackets
				exercises.put("" + 9,
						generateGapTask(entry.getValue(), false, true, true, 0, true, true, false, true, false, false));
				blocks.add(exercises);
			}

			// TODO: generate feedback if requested

			activities.put(entry.getKey(), blocks);
		}
		
		return activities;
	}

}
