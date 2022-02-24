package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.Distractor;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.HtmlTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.PlainTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.ExerciseTopic;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.InstructionsProperties;
import com.flair.shared.exerciseGeneration.Pair;

public class ConditionalConfigParser extends ConfigParser {
	
	@Override
	protected ArrayList<ExerciseData> generateExerciseForConfig(List<String[]> exerciseConstellations, ExerciseConfigData data) {
		if(exerciseConstellations == null) {
			return null;
		}
				
		ArrayList<ExerciseData> exercises = new ArrayList<>();

		for (String[] configValues : exerciseConstellations) {
			try {
				if (data.getStamp().equals("Conditional Type") && configValues[0].equals("conditional_types")
						|| !data.getStamp().equals("Conditional Type") && configValues[0].equals("conditional_form")) {	
					ArrayList<ExerciseData> ed = new ArrayList<>();
					if (configValues[3].equals("0")) {
						ExerciseData d = generateMemoryTask(data, configValues[6].equals("true"));
						d.setExerciseType(ExerciseType.MEMORY);
						ed.add(d);
					} else if (configValues[3].equals("1")) {
						ExerciseData d = generateGapTask(data, configValues[4].equals("true"), configValues[6].equals("true"),
								configValues[7].equals("true"), 1, configValues[5].equals("true"), false, false, false,
								false, false);
						d.setExerciseType(ExerciseType.SINGLE_CHOICE);
						ed.add(d);
					} else if (configValues[3].equals("2")) {
						ExerciseData d = generateGapTask(data, configValues[4].equals("true"), configValues[6].equals("true"),
								configValues[7].equals("true"), 3, configValues[5].equals("true"), false, false, false,
								false, false);
						d.setExerciseType(ExerciseType.SINGLE_CHOICE);
						ed.add(d);
					} else if (configValues[3].equals("3")) {
						ExerciseData d = generateGapTask(data, configValues[4].equals("true"), configValues[6].equals("true"),
								configValues[7].equals("true"), 0, configValues[5].equals("true"), true, false, false,
								false, false);
						d.setExerciseType(ExerciseType.FILL_IN_THE_BLANKS);
						ed.add(d);
					} else if (configValues[3].equals("4")) {
						ExerciseData d = generateGapTask(data, configValues[4].equals("true"), configValues[6].equals("true"),
								configValues[7].equals("true"), 0, configValues[5].equals("true"), true, true, false, false,
								false);
						d.setExerciseType(ExerciseType.FILL_IN_THE_BLANKS);
						d.getBracketsProperties().add(BracketsProperties.DISTRACTOR_LEMMA);
						ed.add(d);
					} else if (configValues[3].equals("5")) {
						ExerciseData d = generateGapTask(data, configValues[4].equals("true"), configValues[6].equals("true"),
								configValues[7].equals("true"), 0, configValues[5].equals("true"), false, false, false,
								true, false);
						d.setExerciseType(ExerciseType.FILL_IN_THE_BLANKS);
						d.getInstructionProperties().add(InstructionsProperties.LEMMA);
						ed.add(d);
					} else if (configValues[3].equals("6")) {
						ExerciseData d = generateJSTask(data, configValues[4].equals("true"), configValues[6].equals("true"),
								configValues[7].equals("true"), configValues[5].equals("true"));
						d.setExerciseType(ExerciseType.JUMBLED_SENTENCES);
						ed.add(d);
					} else if (configValues[3].equals("7")) {
						ExerciseData d = generateCategorizationTask(data, configValues[4].equals("true"),
								configValues[5].equals("true"));
						d.setExerciseType(ExerciseType.CATEGORIZE);
						ed.add(d);
					} else if (configValues[3].equals("8")) {
						// The didi score mechanism can only correctly process a single item per underline, so we need to make a separate exercise for each sentence
						int k = 0;
						for(ExerciseItemConfigData line : data.getItemData()) {
							ExerciseConfigData dummyData = new ExerciseConfigData();
							dummyData.setActivity(data.getActivity());
							dummyData.setStamp(data.getStamp());
							ArrayList<ExerciseItemConfigData> lines = new ArrayList<>();
							lines.add(line);
							dummyData.setItemData(lines);
							ExerciseData d = generateGapTask(dummyData, configValues[4].equals("true"), configValues[6].equals("true"),
									configValues[7].equals("true"), 0, configValues[5].equals("true"), false, false, false,
									false, true);
							d.setExerciseType(ExerciseType.MARK_THE_WORDS);
							
							if (d != null) {
								ed.add(d);
							}
						}
					} else if (configValues[3].equals("9")) {
						ExerciseData d = generateGapTask(data, configValues[4].equals("true"), configValues[6].equals("true"),
								configValues[7].equals("true"), 0, configValues[5].equals("true"), true, false, true, false,
								false);
						d.setExerciseType(ExerciseType.HALF_OPEN);
						ed.add(d);
					}
	
					int k = 0;
					for(ExerciseData d : ed) {
						d.setExerciseTitle(data.getStamp().replace("/", "_") + "/" + configValues[2] + "/" + data.getActivity() + "/" + configValues[3] + (ed.size() > 1 ? "_" + ++k : ""));
	        			d.setTopic(ExerciseTopic.CONDITIONALS);
						exercises.add(d);
					}
	
				}
			} catch(Exception e) {
				if(configValues != null && configValues.length > 3) {
					ServerLogger.get().error(e, "Exercise " + configValues[2] + "/" + configValues[3] + "/" + data.getStamp().replace("/", "_") + "/" + data.getActivity() + " could not be generated.\n" + e.toString());
				} else {
					ServerLogger.get().error(e, "An exercise could not be generated.\n" + e.toString());
				}
			}
		}

		return exercises;
	}

	/**
	 * Compiles the exercise information for Categorization exercises.
	 * 
	 * @param configData           The data of an exercise from the config file
	 * @param ifClauseFirst        <code>true</code> if the if-clause is to be put
	 *                             before the main clause
	 * @param randomizeClauseOrder <code>true</code> if the order of if- and main
	 *                             clauses is to be determined randomly for each
	 *                             sentence.
	 * @return The exercise information
	 */
	private ExerciseData generateCategorizationTask(ExerciseConfigData configData, boolean ifClauseFirst,
			boolean randomizeClauseOrder) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseItemConfigData id : configData.getItemData()) {
			ConditionalExerciseItemConfigData itemData = (ConditionalExerciseItemConfigData) id;
			if (itemData.getConditionalType() == 1 || itemData.getConditionalType() == 2) {
				String ifClause = generateSentencesFromPositions(itemData.getPositionsIfClause());
				String mainClause = generateSentencesFromPositions(itemData.getPositionsMainClause());

				if (randomizeClauseOrder) {
					ifClauseFirst = Math.random() > 0.5;
				}

				String firstClause = StringUtils.capitalize(ifClauseFirst ? ifClause : mainClause);
				String secondClause = ifClauseFirst ? mainClause : ifClause;
				String delimiter = ifClauseFirst ? ", " : " ";
				String sentence = firstClause + delimiter + secondClause;
				if(!sentence.endsWith(".")) {
					sentence += ".";
				}
				String category = itemData.getConditionalType() == 1 ? "Type 1" : "Type 2";

				ConstructionTextPart c = new ConstructionTextPart(sentence, sentenceId++);
				c.setCategory(category);
				parts.add(c);
			}
		}

		ExerciseData data = new ExerciseData(parts);
		addPlainText(data);

		return data;
	}

	/**
	 * Compiles the exercise information for Memory exercises.
	 * 
	 * @param configData    The data of an exercise from the config file
	 * @param useIfClause 	<code>true</code> if the constructions in the if clause are to be targeted
	 * @return The exercise information
	 */
	private ExerciseData generateMemoryTask(ExerciseConfigData configData, boolean useIfClause) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseItemConfigData id : configData.getItemData()) {
			ConditionalExerciseItemConfigData itemData = (ConditionalExerciseItemConfigData) id;
			String targetClause = useIfClause ? generateSentencesFromPositions(itemData.getPositionsIfClause())
					: generateSentencesFromPositions(itemData.getPositionsMainClause());
			String translationTargetClause = useIfClause ? itemData.getTranslationIfClause()
					: itemData.getTranslationMainClause();

			ConstructionTextPart c = new ConstructionTextPart(targetClause, sentenceId++);
			c.getDistractors().add(new Distractor(translationTargetClause));
			parts.add(c);
		}

		ExerciseData data = new ExerciseData(parts);
		addPlainText(data);

		return data;
	}

	/**
	 * Compiles the exercise information for exercises with a target within the text
	 * flow: FiB, MC, MtW.
	 * 
	 * @param configData               The data of an exercise from the config file
	 * @param ifClauseFirst            <code>true</code> if the if-clause is to be
	 *                                 put before the main clause
	 * @param targetIfClause           <code>true</code> if the constructions in the
	 *                                 if clause are to be targeted
	 * @param targetMainClause         <code>true</code> if the constructions in the
	 *                                 main clause are to be targeted
	 * @param nDistractors             The number of distractors to use for the
	 *                                 exercise
	 * @param randomizeClauseOrder     <code>true</code> if the order of if- and
	 *                                 main clauses is to be determined randomly for
	 *                                 each sentence.
	 * @param lemmasInBrackets         <code>true</code> if the lemma of the target
	 *                                 is to be given in brackets
	 * @param useDistractorLemma       <code>true</code> if a semantic distractor is
	 *                                 to be given in brackets
	 * @param targetEntireClause       <code>true</code> if the entire target clause
	 *                                 is to be converted into constructions
	 * @param giveLemmasInInstructions <code>true</code> if the lemmas of all
	 *                                 targets and 2 semantic distractors are to be
	 *                                 given in the instructions
	 * @param isUnderline			   <code>true</code> if it is an underline task                                
	 * @return The exercise information
	 */
	private ExerciseData generateGapTask(ExerciseConfigData configData, boolean ifClauseFirst,
			boolean targetIfClause, boolean targetMainClause, int nDistractors, boolean randomizeClauseOrder,
			boolean lemmasInBrackets, boolean useDistractorLemma, boolean targetEntireClause,
			boolean giveLemmasInInstructions, boolean isUnderline) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		HashSet<String> allLemmas = new HashSet<>();
		HashSet<String> allDistractorLemmas = new HashSet<>();

		for (ExerciseItemConfigData id : configData.getItemData()) {
			ConditionalExerciseItemConfigData itemData = (ConditionalExerciseItemConfigData) id;

			if (parts.size() > 0) {
				parts.add(new HtmlTextPart(" <br>", sentenceId));
			}

			ConditionalTargetAndClauseItems targetAndClauseItems = getTargetAndClauseItems(itemData, randomizeClauseOrder,
					ifClauseFirst, targetIfClause, targetMainClause,
					isUnderline ? itemData.getUnderlineIfClause() : itemData.getGapIfClause(),
					isUnderline ? itemData.getUnderlineMainClause() : itemData.getGapMainClause(), targetEntireClause);

			ArrayList<Pair<Integer, String>> positionParts = new ArrayList<>();
			boolean inConstruction = false;
			int constructionType = 0;
			for (Pair<Integer, String> position : targetAndClauseItems.getPositions()) {
				if (targetAndClauseItems.getTargetPositions().size() == 0
						|| position.first < targetAndClauseItems.getTargetPositions().get(0).first) {
					if (inConstruction) {
						addConstructionPart(positionParts, sentenceId, nDistractors, targetAndClauseItems,
								targetEntireClause, lemmasInBrackets, allLemmas, useDistractorLemma,
								giveLemmasInInstructions, allDistractorLemmas, parts, constructionType);
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
					constructionType = ((ConditionalExerciseItemConfigData)id).getConditionalType();
				}
			}

			if (inConstruction) {
				addConstructionPart(positionParts, sentenceId, nDistractors, targetAndClauseItems, targetEntireClause,
						lemmasInBrackets, allLemmas, useDistractorLemma, giveLemmasInInstructions, allDistractorLemmas,
						parts, constructionType);
			}
			if(positionParts.size() > 0 && !positionParts.get(positionParts.size() - 1).second.endsWith(".")) {
				positionParts.add(new Pair<>(positionParts.size() + 2, "."));
			}
			parts.add(new PlainTextPart(generateSentencesFromPositions(positionParts), sentenceId));
			positionParts.clear();
			sentenceId++;
		}

		ExerciseData data = new ExerciseData(parts);
		if (giveLemmasInInstructions) {
			ArrayList<String> lemmas = new ArrayList<String>(allLemmas);
			ArrayList<String> distractorLemmas = new ArrayList<String>(allDistractorLemmas);

			for (int i = 0; i < Math.min(2, distractorLemmas.size()); i++) {
				Collections.shuffle(distractorLemmas);
				lemmas.add(distractorLemmas.get(0));
				distractorLemmas.remove(0);
			}
			Collections.shuffle(lemmas);
			data.setInstructionLemmas(lemmas);
		}

		addPlainText(data);

		return data;
	}

	/**
	 * Compiles the exercise information for Jumbled Sentences exercises.
	 * 
	 * @param configData           The data of an exercise from the config file
	 * @param ifClauseFirst        <code>true</code> if the if-clause is to be put
	 *                             before the main clause
	 * @param targetIfClause       <code>true</code> if the constructions in the if
	 *                             clause are to be targeted
	 * @param targetMainClause     <code>true</code> if the constructions in the
	 *                             main clause are to be targeted
	 * @param randomizeClauseOrder <code>true</code> if the order of if- and main
	 *                             clauses is to be determined randomly for each
	 *                             sentence.
	 * @return The exercise information
	 */
	private ExerciseData generateJSTask(ExerciseConfigData configData, boolean ifClauseFirst,
			boolean targetIfClause, boolean targetMainClause, boolean randomizeClauseOrder) {

		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseItemConfigData id : configData.getItemData()) {
			ConditionalExerciseItemConfigData itemData = (ConditionalExerciseItemConfigData) id;

			ConditionalTargetAndClauseItems targetAndClauseItems = getTargetAndClauseItems(itemData, randomizeClauseOrder,
					ifClauseFirst, targetIfClause, targetMainClause, itemData.getGapIfClause(),
					itemData.getGapMainClause(), false);

			for (Pair<Integer, String> position : targetAndClauseItems.getPositions()) {
				parts.add(new ConstructionTextPart(position.second, sentenceId));
			}
			if(!parts.get(parts.size() - 1).getValue().endsWith(".")) {
				parts.add(new ConstructionTextPart(".", sentenceId));
			}

			sentenceId++;
		}

		ExerciseData data = new ExerciseData(parts);
		addPlainText(data);

		return data;
	}

	/**
	 * Generates a construction element and adds it to the list of TextParts
	 * 
	 * @param positionParts            The text items separated into position groups
	 * @param sentenceId               The index of the sentence
	 * @param nDistractors             The number of distractors to use for the
	 *                                 exercise
	 * @param targetAndClauseItems     The positions in correct order and
	 *                                 constructions targeted by the exercise
	 * @param targetEntireClause       <code>true</code> if the entire target clause
	 *                                 is to be converted into constructions
	 * @param lemmasInBrackets         <code>true</code> if the lemma of the target
	 *                                 is to be given in brackets
	 * @param allLemmas                The lemmas of all targets constructions
	 * @param useDistractorLemma       <code>true</code> if a semantic distractor is
	 *                                 to be given in brackets
	 * @param giveLemmasInInstructions <code>true</code> if the lemmas of all
	 *                                 targets and 2 semantic distractors are to be
	 *                                 given in the
	 * @param allDistractorLemmas      The distractor lemmas for all target
	 *                                 constructions
	 * @param parts                    The TextPart items
	 */
	private void addConstructionPart(ArrayList<Pair<Integer, String>> positionParts, int sentenceId, int nDistractors,
			ConditionalTargetAndClauseItems targetAndClauseItems, boolean targetEntireClause, boolean lemmasInBrackets,
			HashSet<String> allLemmas, boolean useDistractorLemma, boolean giveLemmasInInstructions,
			HashSet<String> allDistractorLemmas, ArrayList<TextPart> parts, int type) {
		String constructionText = generateSentencesFromPositions(positionParts);
		ConstructionTextPart c = new ConstructionTextPart(constructionText, sentenceId);
		c.setConstructionType(type == 1 ? DetailedConstruction.CONDREAL : DetailedConstruction.CONDUNREAL);

		if (nDistractors > 0) {
			ArrayList<Distractor> distractors = new ArrayList<>();
			for (ArrayList<Pair<Integer, String>> distractorList : targetAndClauseItems.getTargetDistractors()) {
				Collections.shuffle(distractorList);
			}
			while (distractors.size() < nDistractors) {
				distractors.add(new Distractor(
						targetAndClauseItems.getTargetDistractors().get(0).get(distractors.size()).second));
			}
			targetAndClauseItems.getTargetDistractors().remove(0);

			Collections.shuffle(distractors);
			c.setDistractors(distractors);
			c.setTargetIndex(new Random().nextInt(distractors.size() + 1));
		}

		if (giveLemmasInInstructions) {
			allLemmas.add(targetAndClauseItems.getLemmas().get(0));
			allDistractorLemmas.add(targetAndClauseItems.getDistractorLemmas().get(0));
		}

		if (targetEntireClause) {
			c.getBrackets().add(StringUtils.join(targetAndClauseItems.getGivenLemmas().get(0), ","));
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

		if (targetAndClauseItems.getLemmas().size() > 0) {
			targetAndClauseItems.getLemmas().remove(0);
		}
		if (targetAndClauseItems.getDistractorLemmas().size() > 0) {
			targetAndClauseItems.getDistractorLemmas().remove(0);
		}

		parts.add(c);
		positionParts.clear();
	}

	/**
	 * Extracts the clauses in the correct order and the constructions which are
	 * located in the clause to target.
	 * 
	 * @param itemData                The config data of the current sentence
	 * @param randomizeClauseOrder    <code>true</code> if the order of if- and main
	 *                                clauses is to be determined randomly for each
	 *                                sentence.
	 * @param ifClauseFirst           <code>true</code> if the if-clause is to be
	 *                                put before the main clause
	 * @param targetIfClause          <code>true</code> if the constructions in the
	 *                                if clause are to be targeted
	 * @param targetMainClause        <code>true</code> if the constructions in the
	 *                                main clause are to be targeted
	 * @param constructionsIfClause   The constructions in the if clause. Gaps for
	 *                                FiB and SC exercises, Underline for Underline
	 *                                exercises.
	 * @param constructionsMainClause The constructions in the main clause. Gaps for
	 *                                FiB and SC exercises, Underline for Underline
	 *                                exercises.
	 * @param targetEntireClause      <code>true</code> if the entire target clause
	 *                                is to be converted into constructions
	 * @return The positions in correct order and constructions targeted by the
	 *         exercise
	 */
	private ConditionalTargetAndClauseItems getTargetAndClauseItems(ConditionalExerciseItemConfigData itemData,
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
						targetPositions
								.add(new Pair<>(mainPositions.size() + tp.first, mainPositions.size() + tp.second));
					}
					targetDistractors.add(itemData.getDistractorsIfClause());
				}
				distractorLemmas.add(itemData.getDistractorLemmaIfClause());
				lemmas.add(itemData.getLemmaIfClause());
				givenLemmas.add(itemData.getBracketsIfClause());
			}
		}

		positions.set(0, new Pair<>(positions.get(0).first, StringUtils.capitalize(positions.get(0).second)));

		return new ConditionalTargetAndClauseItems(positions, targetPositions, targetDistractors, lemmas, distractorLemmas,
				givenLemmas);
	}

}
