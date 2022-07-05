package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
	protected ArrayList<ExerciseData> generateExerciseForConfig(ExerciseConfigData data) {	
		ArrayList<ExerciseData> exercises = new ArrayList<>();
		for (ExerciseTypeSpec cv : data.getExerciseType()) {
			ConditionalExerciseTypeSpec configValues = (ConditionalExerciseTypeSpec)cv;
			try {
				ExerciseData d = null;
				System.out.println(data.getStamp() + ";" + configValues.getFeedbookType() + ";" + (configValues.isTargetIfClause() && configValues.isTargetMainClause() ? "both" : 
					(configValues.isTargetIfClause() ? "if" : (configValues.isTargetMainClause() ? "main" : "random"))));
				if (configValues.getFeedbookType().equals(FeedBookExerciseType.MEMORY)) {
					d = generateMemoryTask(data, configValues.isTargetIfClause(), configValues.isTargetMainClause(), 
							configValues.isRandomTargetClause());
					d.setExerciseType(ExerciseType.MEMORY);
				} else if (configValues.getFeedbookType().equals(FeedBookExerciseType.SINGLE_CHOICE_2D)) {
					d = generateGapTask(data, configValues.isIfClauseFirst(), configValues.isTargetIfClause(),
							configValues.isTargetMainClause(), 1, configValues.isRandomClauseOrder(), false, false, false,
							false, false, configValues.isRandomTargetClause());
					d.setExerciseType(ExerciseType.SINGLE_CHOICE);
				} else if (configValues.getFeedbookType().equals(FeedBookExerciseType.SINGLE_CHOICE_4D)) {
					d = generateGapTask(data, configValues.isIfClauseFirst(), configValues.isTargetIfClause(),
							configValues.isTargetMainClause(), 3, configValues.isRandomClauseOrder(), false, false, false,
							false, false, configValues.isRandomTargetClause());
					d.setExerciseType(ExerciseType.SINGLE_CHOICE);
				} else if (configValues.getFeedbookType().equals(FeedBookExerciseType.FIB_LEMMA_PARENTHESES)) {
					d = generateGapTask(data, configValues.isIfClauseFirst(), configValues.isTargetIfClause(),
							configValues.isTargetMainClause(), 0, configValues.isRandomClauseOrder(), true, false, false,
							false, false, configValues.isRandomTargetClause());
					d.setExerciseType(ExerciseType.FILL_IN_THE_BLANKS);
				} else if (configValues.getFeedbookType().equals(FeedBookExerciseType.FIB_LEMMA_DISTRACTOR_PARENTHESES)) {
					d = generateGapTask(data, configValues.isIfClauseFirst(), configValues.isTargetIfClause(),
							configValues.isTargetMainClause(), 0, configValues.isRandomClauseOrder(), true, true, false, false,
							false, configValues.isRandomTargetClause());
					d.setExerciseType(ExerciseType.FILL_IN_THE_BLANKS);
					d.getBracketsProperties().add(BracketsProperties.DISTRACTOR_LEMMA);
				} else if (configValues.getFeedbookType().equals(FeedBookExerciseType.FIB_LEMMA_INSTRUCTIONS)) {
					d = generateGapTask(data, configValues.isIfClauseFirst(), configValues.isTargetIfClause(),
							configValues.isTargetMainClause(), 0, configValues.isRandomClauseOrder(), false, false, false,
							true, false, configValues.isRandomTargetClause());
					d.setExerciseType(ExerciseType.FILL_IN_THE_BLANKS);
					d.getInstructionProperties().add(InstructionsProperties.LEMMA);
				} else if (configValues.getFeedbookType().equals(FeedBookExerciseType.JUMBLED_SENTENCES)) {
					d = generateJSTask(data, configValues.isTargetMainClause(), configValues.isRandomClauseOrder(),
							configValues.isTargetIfClause(), configValues.isTargetMainClause(), configValues.isRandomTargetClause());
					d.setExerciseType(ExerciseType.JUMBLED_SENTENCES);
				} else if (configValues.getFeedbookType().equals(FeedBookExerciseType.CATEGORIZATION)) {
					d = generateCategorizationTask(data, configValues.isIfClauseFirst(),
							configValues.isRandomClauseOrder());
					d.setExerciseType(ExerciseType.CATEGORIZE);
				} else if (configValues.getFeedbookType().equals(FeedBookExerciseType.UNDERLINE)) {
					d = generateGapTask(data, configValues.isIfClauseFirst(), configValues.isTargetIfClause(),
							configValues.isTargetMainClause(), 0, configValues.isRandomClauseOrder(), false, false, false,
							false, true, configValues.isRandomTargetClause());
					d.setExerciseType(ExerciseType.MARK_THE_WORDS);
				} else if (configValues.getFeedbookType().equals(FeedBookExerciseType.HALF_OPEN)) {
					d = generateGapTask(data, configValues.isIfClauseFirst(), configValues.isTargetIfClause(),
							configValues.isTargetMainClause(), 0, configValues.isRandomClauseOrder(), true, false, true, false,
							false, configValues.isRandomTargetClause());
					d.setExerciseType(ExerciseType.HALF_OPEN);
				}
				
				if(!data.getStamp().equals("conditinal 1 vs conditional 2")) {
					d.getBracketsProperties().add(BracketsProperties.CONDITIONAL_TYPE);
				}


				d.setExerciseTitle(data.getTocId() + FeedBookExerciseType.getFeedbookId(configValues.getFeedbookType()));
    			d.setTopic(ExerciseTopic.CONDITIONALS);
				exercises.add(d);
			} catch(Exception e) {
				ServerLogger.get().error(e, "Exercise " + data.getTocId() + FeedBookExerciseType.getFeedbookId(configValues.getFeedbookType()) + " could not be generated.\n" + e.toString());
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
				ConditionalTargetAndClauseItems targetAndClauseItems = getTargetAndClauseItems(itemData, randomizeClauseOrder,
						ifClauseFirst, false, false,
						itemData.getGapIfClause(),
						itemData.getGapMainClause(), true,
						randomizeClauseOrder, false);

				targetAndClauseItems.getPositions().add(new Pair<>(targetAndClauseItems.getPositions().size(), "."));
				String sentence = generateSentencesFromPositions(targetAndClauseItems.getPositions());
				String category = itemData.getConditionalType() == 1 ? "Type 1" : "Type 2";

				ConstructionTextPart c = new ConstructionTextPart(sentence, sentenceId++);
				c.setConstructionType(itemData.getConditionalType() == 1 ? DetailedConstruction.CONDREAL : DetailedConstruction.CONDUNREAL);
				c.setCategory(category);
				c.setFallbackFeedback(itemData.getFeedback());
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
	private ExerciseData generateMemoryTask(ExerciseConfigData configData, boolean useIfClause, boolean useMainClause, 
			boolean randomizeTargetClause) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseItemConfigData id : configData.getItemData()) {
			if (randomizeTargetClause) {
				useIfClause = rand.nextBoolean();
				useMainClause = !useIfClause;
			}
			
			ConditionalExerciseItemConfigData itemData = (ConditionalExerciseItemConfigData) id;
			if(useIfClause) {
				parts.add(getMemoryPair(itemData, useIfClause, sentenceId++));
			}
			if(useMainClause) {
				parts.add(getMemoryPair(itemData, !useMainClause, sentenceId++));
			}			
		}

		ExerciseData data = new ExerciseData(parts);
		addPlainText(data);

		return data;
	}
	
	private TextPart getMemoryPair(ConditionalExerciseItemConfigData itemData, boolean useIfClause, int sentenceId) {
		String targetClause = useIfClause ? generateSentencesFromPositions(itemData.getPositionsIfClause())
				: generateSentencesFromPositions(itemData.getPositionsMainClause());
		String translationTargetClause = useIfClause ? itemData.getTranslationIfClause()
				: itemData.getTranslationMainClause();

		ConstructionTextPart c = new ConstructionTextPart(targetClause, sentenceId);
		c.setConstructionType(itemData.getConditionalType() == 1 ? DetailedConstruction.CONDREAL : DetailedConstruction.CONDUNREAL);
		c.getDistractors().add(new Distractor(translationTargetClause));
		return c;
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
			boolean giveLemmasInInstructions, boolean isUnderline, boolean randomizeTargetClause) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		HashSet<String> allLemmas = new HashSet<>();
		HashSet<String> allDistractorLemmas = new HashSet<>();

		for (ExerciseItemConfigData id : configData.getItemData()) {
			ConditionalExerciseItemConfigData itemData = (ConditionalExerciseItemConfigData) id;

			if (parts.size() > 0) {
				parts.add(new HtmlTextPart(targetEntireClause ? " <br><br>" : " <br>", sentenceId));
			}

			ConditionalTargetAndClauseItems targetAndClauseItems = getTargetAndClauseItems(itemData, randomizeClauseOrder,
					ifClauseFirst, targetIfClause, targetMainClause,
					isUnderline ? itemData.getUnderlineIfClause() : itemData.getGapIfClause(),
					isUnderline ? itemData.getUnderlineMainClause() : itemData.getGapMainClause(), targetEntireClause,
							randomizeTargetClause, false);

			ArrayList<Pair<Integer, String>> positionParts = new ArrayList<>();
			boolean inConstruction = false;
			int constructionType = 0;
			for (Pair<Integer, String> position : targetAndClauseItems.getPositions()) {
				if (targetAndClauseItems.getTargetPositions().size() == 0
						|| position.first < targetAndClauseItems.getTargetPositions().get(0).first) {
					if (inConstruction) {
						addConstructionPart(positionParts, sentenceId, nDistractors, targetAndClauseItems,
								targetEntireClause, lemmasInBrackets, allLemmas, useDistractorLemma,
								giveLemmasInInstructions, allDistractorLemmas, parts, constructionType,
								itemData.getFeedback(), targetAndClauseItems.getAlternativeTarget());
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
						parts, constructionType, itemData.getFeedback(), targetAndClauseItems.getAlternativeTarget());
			}
			String punctuation = configData.getStamp().contains("question") ? "?" : ".";
			if(positionParts.size() == 0 || !positionParts.get(positionParts.size() - 1).second.endsWith(punctuation)) {
				positionParts.add(new Pair<>(positionParts.size() + 2, punctuation));
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
			boolean randomizeClauseOrder, boolean targetIfClause, boolean targetMainClause, 
			boolean randomizeTargetClause) {

		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseItemConfigData id : configData.getItemData()) {
			ConditionalExerciseItemConfigData itemData = (ConditionalExerciseItemConfigData) id;

			ConditionalTargetAndClauseItems targetAndClauseItems = getTargetAndClauseItems(itemData, randomizeClauseOrder,
					ifClauseFirst, targetIfClause, targetMainClause, itemData.getGapIfClause(),
					itemData.getGapMainClause(), false, randomizeTargetClause, true);

			for (Pair<Integer, String> position : targetAndClauseItems.getPositions()) {
				if(position.second.trim().length() > 0) {
					ConstructionTextPart c = new ConstructionTextPart(position.second, sentenceId);
					c.setConstructionType(itemData.getConditionalType() == 1 ? DetailedConstruction.CONDREAL : DetailedConstruction.CONDUNREAL);
					parts.add(c);
				}
			}
			String punctuation = configData.getStamp().contains("question") ? "?" : ".";
			if(!parts.get(parts.size() - 1).getValue().endsWith(punctuation)) {
				ConstructionTextPart c = new ConstructionTextPart(punctuation, sentenceId);
				c.setConstructionType(itemData.getConditionalType() == 1 ? DetailedConstruction.CONDREAL : DetailedConstruction.CONDUNREAL);
				c.setFallbackFeedback(itemData.getFeedback());
				parts.add(c);
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
			HashSet<String> allDistractorLemmas, ArrayList<TextPart> parts, int type, String feedback,
			String targetAlternative) {
		String constructionText = generateSentencesFromPositions(positionParts);
		ConstructionTextPart c = new ConstructionTextPart(constructionText, sentenceId);
		c.setConstructionType(type == 1 ? DetailedConstruction.CONDREAL : DetailedConstruction.CONDUNREAL);
		c.setFallbackFeedback(feedback);
		if(targetAlternative != null) {
			c.getTargetAlternatives().add(targetAlternative);
		}
		
		if (nDistractors > 0) {
			ArrayList<Distractor> distractors = new ArrayList<>();
			for (ArrayList<Pair<Integer, String>> distractorList : targetAndClauseItems.getTargetDistractors()) {
				Collections.shuffle(distractorList);
			}
			while (distractors.size() < nDistractors) {
				String distractor = targetAndClauseItems.getTargetDistractors().get(0).get(distractors.size()).second;
				if(Character.isUpperCase(constructionText.charAt(0))) {
					distractor = StringUtils.capitalize(distractor);
				}
				
				distractors.add(new Distractor(distractor));
			}
			targetAndClauseItems.getTargetDistractors().remove(0);

			Collections.shuffle(distractors);
			c.setDistractors(distractors);
			c.setTargetIndex(rand.nextInt(distractors.size() + 1));
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
			ArrayList<Pair<Integer, Integer>> constructionsMainClause, boolean targetEntireClause, boolean randomizeTargetClause,
			boolean concatenateNonTargetPositions) {
		ArrayList<Pair<Integer, String>> ifPositions = new ArrayList<>(itemData.getPositionsIfClause());
		ArrayList<Pair<Integer, String>> mainPositions = new ArrayList<>(itemData.getPositionsMainClause());

		ArrayList<Pair<Integer, String>> positions;
		ArrayList<Pair<Integer, Integer>> targetPositions = new ArrayList<>();
		ArrayList<ArrayList<Pair<Integer, String>>> targetDistractors = new ArrayList<>();
		ArrayList<String> distractorLemmas = new ArrayList<>();
		ArrayList<String> lemmas = new ArrayList<>();
		ArrayList<ArrayList<String>> givenLemmas = new ArrayList<>();

		if (randomizeClauseOrder) {
			ifClauseFirst = rand.nextBoolean();
		}
		if(itemData.isForceIfFirst()) {
			ifClauseFirst = true;
		}
		
		if (randomizeTargetClause) {
			targetIfClause = rand.nextBoolean();
			targetMainClause = !targetIfClause;
		}

		if (ifClauseFirst) {
			positions = new ArrayList<>(ifPositions);
			positions.add(new Pair<>(positions.size() + 1, ","));

			if(concatenateNonTargetPositions && !targetIfClause) {
				String clausePosition = generateSentencesFromPositions(positions);
				positions.clear();
				positions.add(new Pair<>(1, clausePosition));
			} 
			
			ArrayList<Pair<Integer, String>> positionsToAdd = new ArrayList<>();
			for (Pair<Integer, String> p : mainPositions) {
				positionsToAdd.add(new Pair<>(ifPositions.size() + 1 + p.first, p.second));
			}
			
			if(concatenateNonTargetPositions && !targetMainClause) {
				String clausePosition = generateSentencesFromPositions(positionsToAdd);
				positionsToAdd.clear();
				positionsToAdd.add(new Pair<>(1, clausePosition));
			} 
			
			positions.addAll(positionsToAdd);
	
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
			int k = 0;
			for(Pair<Integer, String> diffPos : itemData.getDifferingValuesIfClause()) {
				ifPositions.set(diffPos.first - 1, new Pair<>(ifPositions.get(diffPos.first - 1).first, itemData.getPositionsMainClause().get(itemData.getDifferingValuesMainClause().get(k).first - 1).second));
				k++;
			}
			k = 0;
			for(Pair<Integer, String> diffPos : itemData.getDifferingValuesMainClause()) {
				mainPositions.set(diffPos.first - 1, new Pair<>(mainPositions.get(diffPos.first - 1).first, itemData.getPositionsIfClause().get(itemData.getDifferingValuesIfClause().get(k).first - 1).second));
				k++;
			}
			
			positions = new ArrayList<>(mainPositions);
			if(concatenateNonTargetPositions && !targetMainClause) {
				String clausePosition = generateSentencesFromPositions(positions);
				positions.clear();
				positions.add(new Pair<>(1, clausePosition));
			} 
			
			ArrayList<Pair<Integer, String>> positionsToAdd = new ArrayList<>();
			for (Pair<Integer, String> p : ifPositions) {
				positionsToAdd.add(new Pair<>(mainPositions.size() + p.first, p.second));
			}
			
			if(concatenateNonTargetPositions && !targetIfClause) {
				String clausePosition = generateSentencesFromPositions(positionsToAdd);
				positionsToAdd.clear();
				positionsToAdd.add(new Pair<>(1, clausePosition));
			} 
			
			positions.addAll(positionsToAdd);

			if (targetMainClause) {
				if (targetEntireClause) {
					targetPositions.add(new Pair<>(1, mainPositions.size()));
				} else {
					targetPositions.addAll(constructionsMainClause);
					targetDistractors.add(itemData.getDistractorsMainClause());
				}
				distractorLemmas.add(itemData.getDistractorLemmaMainClause());
				lemmas.add(itemData.getLemmaMainClause());
				
				ArrayList<String> brackets = itemData.getBracketsMainClause();
				k = 0;
				for(Pair<Integer, String> diffPos : itemData.getDifferingValuesMainClause()) {
					for(int j = 0; j < brackets.size(); j++) {
						String bracket = brackets.get(j);
						if(bracket.equals(itemData.getPositionsMainClause().get(diffPos.first - 1).second)) {
							brackets.set(j, itemData.getPositionsIfClause().get(itemData.getDifferingValuesIfClause().get(k).first - 1).second);
						}
					}
					k++;
				}
				
				givenLemmas.add(brackets);
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
				
				ArrayList<String> brackets = itemData.getBracketsIfClause();
				k = 0;
				for(Pair<Integer, String> diffPos : itemData.getDifferingValuesIfClause()) {
					for(int j = 0; j < brackets.size(); j++) {
						String bracket = brackets.get(j);
						if(bracket.equals(itemData.getPositionsIfClause().get(diffPos.first - 1).second)) {
							brackets.set(j, itemData.getPositionsMainClause().get(itemData.getDifferingValuesMainClause().get(k).first - 1).second);
						}
					}
					k++;
				}
				
				givenLemmas.add(brackets);
				givenLemmas.add(itemData.getBracketsIfClause());
			}
		}

		positions.set(0, new Pair<>(positions.get(0).first, StringUtils.capitalize(positions.get(0).second)));

		String targetAlternatives = null;
		if(targetIfClause && itemData.getAlternativeTarget() != null) {
			targetAlternatives = itemData.getAlternativeTarget();
		}
		return new ConditionalTargetAndClauseItems(positions, targetPositions, targetDistractors, lemmas, distractorLemmas,
				givenLemmas, targetAlternatives);
	}
	
	private Random rand = new Random(1);
	
}