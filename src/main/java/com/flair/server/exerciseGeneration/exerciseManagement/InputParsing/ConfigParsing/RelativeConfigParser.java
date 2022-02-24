package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.util.ArrayList;
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
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.ExerciseTopic;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.Pair;

public class RelativeConfigParser extends ConfigParser {

	@Override
	protected ArrayList<ExerciseData> generateExerciseForConfig(List<String[]> exerciseConstellations,
			ExerciseConfigData data) {
		//TODO: only for debug output:
		if(!data.getStamp().equals("contact clauses")) {
			for(ExerciseItemConfigData configData : data.getItemData()) {
				RelativeTargetAndClauseItems clauseItems = determineClauseItems((RelativeExerciseItemConfigData)configData, true);
				StringBuilder sb = new StringBuilder();
				sb.append(clauseItems.getSentenceClause1() + " " + clauseItems.getSentenceClause2());
	
				if(!((RelativeExerciseItemConfigData)configData).getPronoun().equals("whose")) {
					String res = getRelativeSentence((RelativeExerciseItemConfigData)configData, true, false, data.getStamp(), data.getActivity());
					if(res != null) {
						sb.append(";").append(res);
					}
					res = getRelativeSentence((RelativeExerciseItemConfigData)configData, true, true, data.getStamp(), data.getActivity());
					if(res != null) {
						sb.append(";").append(res);
					}
				}
				String res = getRelativeSentence((RelativeExerciseItemConfigData)configData, false, false, data.getStamp(), data.getActivity());
				if(res != null) {
					sb.append(";").append(res);
				}
				res = getRelativeSentence((RelativeExerciseItemConfigData)configData, false, true, data.getStamp(), data.getActivity());
				if(res != null) {
					sb.append(";").append(res);
				}
	
				System.out.println(sb.toString());
			}
		}
		// end TODO
		
		if(exerciseConstellations == null) {
			return null;
		}
		
		ArrayList<ExerciseData> exercises = new ArrayList<>();

		for (String[] configValues : exerciseConstellations) {
			try {
				if (configValues[0].equals("relatives") && !data.getStamp().equals("contact clauses")) {	
					ExerciseData d = null;
					
					if (configValues[3].equals("0")) {
						d = generateMemoryTask(data, configValues[8] != null && configValues[8].equals("true"), configValues[8] == null || configValues[8].isEmpty());
						d.setExerciseType(ExerciseType.MEMORY);
					} else if (configValues[3].equals("1")) {
						d = generateGapTask(data, configValues[8] != null && configValues[8].equals("true"), false, configValues[8] == null ||configValues[8].isEmpty());
						if(d != null) {
							d.setExerciseType(ExerciseType.MARK_THE_WORDS);
						}
					} else if (configValues[3].equals("2")) {
						d = generateGapTask(data, configValues[8] != null && configValues[8].equals("true"), true, configValues[8] == null ||configValues[8].isEmpty());
						if(d != null) {
							d.setExerciseType(ExerciseType.SINGLE_CHOICE);
						}
					} else if (configValues[3].equals("3")) {
						d = generateGapTask(data, configValues[8] != null && configValues[8].equals("true"), false, configValues[8] == null ||configValues[8].isEmpty());
						if(d != null) {
							d.setExerciseType(ExerciseType.FILL_IN_THE_BLANKS);
						}
					} else if (configValues[3].equals("4")) {
						d = generatePromptedTask(data, configValues[8] != null && configValues[8].equals("true"), configValues[8] == null ||configValues[8].isEmpty());
						d.setExerciseType(ExerciseType.HALF_OPEN);
					} else if (configValues[3].equals("5")) {
						d = generateOpenTask(data);
						d.setExerciseType(ExerciseType.SHORT_ANSWER);
					} else if (configValues[3].equals("6")) {
						d = generateJSTask(data, configValues[8] != null && configValues[8].equals("true"), configValues[8] == null ||configValues[8].isEmpty());
						if(d != null) {
							d.setExerciseType(ExerciseType.JUMBLED_SENTENCES);
						}
					} 
	
					if (d != null) {
	        			d.setExerciseTitle(data.getStamp().replace("/", "_") + "/" + data.getActivity() + "/" + configValues[2] + "/" + configValues[3]);
	        			d.setTopic(ExerciseTopic.RELATIVES);
	        			d.setSubtopic(data.getStamp());
						exercises.add(d);
					}
				} else if(configValues[0].equals("relatives_contact") && data.getStamp().equals("contact clauses")) {
					ExerciseData d = null;
					
					if (configValues[3].equals("4")) {
						d = generatePromptedTaskContact(data);
						d.setExerciseType(ExerciseType.FILL_IN_THE_BLANKS);
					} else if (configValues[3].equals("5")) {
						d = generateOpenTaskContact(data);
						d.setExerciseType(ExerciseType.SHORT_ANSWER);
					} else if(configValues[3].equals("7")) {
						d = generateCategorizeTask(data);
						d.setExerciseType(ExerciseType.CATEGORIZE);
					}
					
					if (d != null) {
	        			d.setExerciseTitle(data.getStamp().replace("/", "_") + "/" + data.getActivity() + "/" + configValues[2] + "/" + configValues[3]);
	        			d.setTopic(ExerciseTopic.RELATIVES);
	        			d.setSubtopic(data.getStamp());
						exercises.add(d);
					}
				}
			} catch(Exception e) {
				if(configValues != null && configValues.length > 3) {
					ServerLogger.get().error(e, "Exercise " + data.getStamp().replace("/", "_") + "/" + data.getActivity() + "/" + configValues[2] + "/" + configValues[3] + " could not be generated.\n" + e.toString());
				} else {
					ServerLogger.get().error(e, "An exercise could not be generated.\n" + e.toString());
				}
			}
		}

		return exercises;
	}
	
	private void addIfNotNull(HashSet<String> set, String element) {
		if(element != null) {
			set.add(element);
		}
	}
	
	/**
	 * Compiles the exercise information for Memory exercises.
	 * @param configData			The data of an exercise from the config file
	 * @param clause1IntoClause2	<code>true</code> if clause 1 is to be inserted into clause 2
	 * @return The exercise information
	 */
	private ExerciseData generateMemoryTask(ExerciseConfigData configData, boolean clause1IntoClause2,
			boolean randomizeClauseOrder) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseItemConfigData id : configData.getItemData()) {
			if(randomizeClauseOrder) {
				clause1IntoClause2 = Math.random() < 0.5;
			}
			RelativeExerciseItemConfigData itemData = (RelativeExerciseItemConfigData) id;
			RelativeTargetAndClauseItems clauseItems = determineClauseItems(itemData, clause1IntoClause2);
			
			ArrayList<RelativeClausePosition> positions = determineOrder(itemData, clause1IntoClause2, false, configData.getStamp(), configData.getActivity());
			if(positions == null) {
				continue;
			}
			
			ArrayList<Pair<Integer, String>> pos = new ArrayList<>();
			for(int i = 1; i <= positions.size(); i++) {
				pos.add(new Pair<>(i, positions.get(i - 1).getValue()));
			}
			
			String relativeSentence = generateSentencesFromPositions(pos);
			
			ConstructionTextPart c = new ConstructionTextPart(clauseItems.getSentenceClause1() + " " + clauseItems.getSentenceClause2(), sentenceId++);
			c.getDistractors().add(new Distractor(relativeSentence));
			parts.add(c);
		}

		ExerciseData data = new ExerciseData(parts);
		addPlainText(data);

		return data;
	}
	
	/**
	 * Compiles the exercise information for FiB, MC and MtW exercises.
	 * @param configData			The data of an exercise from the config file
	 * @param clause1IntoClause2	<code>true</code> if clause 1 is to be inserted into clause 2
	 * @param addDistractors		<code>true</code> for MC exercises
	 * @return The exercise information
	 */
	private ExerciseData generateGapTask(ExerciseConfigData configData, boolean clause1IntoClause2, 
			boolean addDistractors, boolean randomizeClauseOrder) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseItemConfigData id : configData.getItemData()) {
			if(randomizeClauseOrder) {
				clause1IntoClause2 = Math.random() < 0.5;
			}
			RelativeExerciseItemConfigData itemData = (RelativeExerciseItemConfigData) id;

			if(!(itemData.getPronoun().equals("whose") && clause1IntoClause2)) {				
				ArrayList<RelativeClausePosition> positions = determineOrder(itemData, clause1IntoClause2, false, configData.getStamp(), configData.getActivity());
				if(positions == null) {
					continue;
				}
				
				if (parts.size() > 0) {
					parts.add(new HtmlTextPart(" <br>", sentenceId));
				}
				
				ArrayList<Pair<Integer,String>> pos = new ArrayList<>();
				for(int i = 1; i <= positions.size(); i++) {
					RelativeClausePosition position = positions.get(i - 1);
					if(position.isPronoun()) {
						ConstructionTextPart c = new ConstructionTextPart(position.getValue(), sentenceId);
						c.setConstructionType(position.getValue().equals("who") ? DetailedConstruction.WHO : 
							(position.getValue().equals("which") ? DetailedConstruction.WHICH : 
								(position.getValue().equals("that") ? DetailedConstruction.THAT : DetailedConstruction.OTHERPRN)));
					
						if(addDistractors) {
							for(String distractor : itemData.getDistractors()) {
								c.getDistractors().add(new Distractor(distractor));
							}
							c.setTargetIndex(new Random().nextInt(c.getDistractors().size() + 1));
						}
						parts.add(c);
					} else {
						pos.add(new Pair<>(i, position.getValue()));
	
						if(position.isCommonReferent() && pos.size() > 0) {
							String text = generateSentencesFromPositions(pos);
							parts.add(new PlainTextPart(text, sentenceId));
							pos.clear();
						}
					}
				}	
				
				if(pos.size() > 0) {
					String text = generateSentencesFromPositions(pos);
					parts.add(new PlainTextPart(text, sentenceId));
				}
				
				sentenceId++;
			}
		}

		if(parts.size() == 0) {
			return null;
		}
		
		ExerciseData data = new ExerciseData(parts);
		addPlainText(data);

		return data;
	}

	/**
	 * Compiles the exercise information for FiB exercises where tehe 2 clauses and 
	 * the beginning of the sentence are given.
	 * @param configData			The data of an exercise from the config file
	 * @param clause1IntoClause2	<code>true</code> if clause 1 is to be inserted into clause 2
	 * @return The exercise information
	 */
	private ExerciseData generatePromptedTask(ExerciseConfigData configData, boolean clause1IntoClause2,
			boolean randomizeClauseOrder) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseItemConfigData id : configData.getItemData()) {
			if(randomizeClauseOrder) {
				clause1IntoClause2 = Math.random() < 0.5;
			}
			RelativeExerciseItemConfigData itemData = (RelativeExerciseItemConfigData) id;

			ArrayList<RelativeClausePosition> positions = determineOrder(itemData, clause1IntoClause2, false, configData.getStamp(), configData.getActivity());
			if(positions == null) {
				continue;
			}
			
			if (parts.size() > 0) {
				parts.add(new HtmlTextPart(" <br><br>", sentenceId));
			}
			
			RelativeTargetAndClauseItems clauseItems = determineClauseItems(itemData, clause1IntoClause2);

			parts.add(new PlainTextPart(clauseItems.getSentenceClause1() + " " + clauseItems.getSentenceClause2(), sentenceId));
			parts.add(new HtmlTextPart(" <br>", sentenceId));
						
			Pair<String, String> exerciseItems = generatePromptAndTarget(itemData, clause1IntoClause2, configData.getStamp(), configData.getActivity());
			parts.add(new PlainTextPart(exerciseItems.first, sentenceId));

			ConstructionTextPart c = new ConstructionTextPart(exerciseItems.second, sentenceId);
			c.setConstructionType(itemData.getPronoun().equals("who") ? DetailedConstruction.WHO : 
				(itemData.getPronoun().equals("which") ? DetailedConstruction.WHICH : 
					(itemData.getPronoun().equals("that") ? DetailedConstruction.THAT : DetailedConstruction.OTHERPRN)));
			
			// check if the prompt for alternative order of clauses would be identical
			Pair<String, String> alternativeOrdering = generatePromptAndTarget(itemData, !clause1IntoClause2, configData.getStamp(), configData.getActivity());
			
			if(alternativeOrdering.first.equals(exerciseItems.first)) {
				c.getTargetAlternatives().add(alternativeOrdering.second);
			}
			
			parts.add(c);				
									
			sentenceId++;
		}

		ExerciseData data = new ExerciseData(parts);
		addPlainText(data);

		return data;
	}
	
	/**
	 * Determines the prompt and target for half open exercises.
	 * @param itemData				The data of an exercise from the config file
	 * @param clause1IntoClause2	<code>true</code> if clause 1 is to be inserted into clause 2
	 * @return	The prompt and the target
	 */
	private Pair<String, String> generatePromptAndTarget(RelativeExerciseItemConfigData itemData, boolean clause1IntoClause2, String stamp, int activity) {
		ArrayList<RelativeClausePosition> positions = determineOrder(itemData, clause1IntoClause2, false, stamp, activity);

		ArrayList<Pair<Integer,String>> pos = new ArrayList<>();
		boolean lastWasPronoun = false;
		String prompt = null;
		for(int i = 1; i <= positions.size(); i++) {
			RelativeClausePosition position = positions.get(i - 1);
			pos.add(new Pair<>(i, position.getValue()));

			if(lastWasPronoun && pos.size() > 0) {
				prompt = generateSentencesFromPositions(pos);
				pos.clear();
			}
			
			lastWasPronoun = position.isPronoun();
		}
		
		String target = null;
		if(pos.size() > 0) {
			target = generateSentencesFromPositions(pos);
		}
		
		return new Pair<>(prompt, target);
	}

	/**
	 * Compiles the exercise information for open exercises where only the 2 clauses are given.
	 * @param configData	The data of an exercise from the config file
	 * @return The exercise information
	 */
	private ExerciseData generateOpenTask(ExerciseConfigData configData) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseItemConfigData id : configData.getItemData()) {
			RelativeExerciseItemConfigData itemData = (RelativeExerciseItemConfigData) id;
			RelativeTargetAndClauseItems clauseItems = determineClauseItems(itemData, true);
						
			HashSet<String> possibleSentences = new HashSet<>();
			addIfNotNull(possibleSentences, getRelativeSentence(itemData, false, false, configData.getStamp(), configData.getActivity()));
			addIfNotNull(possibleSentences, getRelativeSentence(itemData, false, true, configData.getStamp(), configData.getActivity()));
			if(!((RelativeExerciseItemConfigData)id).getPronoun().equals("whose")) {
				addIfNotNull(possibleSentences, getRelativeSentence(itemData, true, false, configData.getStamp(), configData.getActivity()));
				addIfNotNull(possibleSentences, getRelativeSentence(itemData, true, true, configData.getStamp(), configData.getActivity()));
			}
			
			if(possibleSentences.size() == 0) {
				continue;
			}
			
			if (parts.size() > 0) {
				parts.add(new HtmlTextPart(" <br><br>", sentenceId));
			}
			
			parts.add(new PlainTextPart(clauseItems.getSentenceClause1() + " " + clauseItems.getSentenceClause2(), sentenceId));
			parts.add(new HtmlTextPart(" <br>", sentenceId));
			
			ArrayList<String> alternatives = new ArrayList<>(possibleSentences);

			ConstructionTextPart c = new ConstructionTextPart(alternatives.get(0), sentenceId);
			for(int i = 1; i < alternatives.size(); i++) {
				c.getTargetAlternatives().add(alternatives.get(i));
			}
			c.setConstructionType(itemData.getPronoun().equals("who") ? DetailedConstruction.WHO : 
				(itemData.getPronoun().equals("which") ? DetailedConstruction.WHICH : 
					(itemData.getPronoun().equals("that") ? DetailedConstruction.THAT : DetailedConstruction.OTHERPRN)));
			
			parts.add(c);

			sentenceId++;
		}

		ExerciseData data = new ExerciseData(parts);
		addPlainText(data);

		return data;
	}

	/**
	 * Compiles the exercise information for Jumbled Sentences exercises.
	 * @param configData			The data of an exercise from the config file
	 * @param clause1IntoClause2	<code>true</code> if clause 1 is to be inserted into clause 2
	 * @return The exercise information
	 */
	private ExerciseData generateJSTask(ExerciseConfigData configData, boolean clause1IntoClause2,
			boolean randomizeClauseOrder) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseItemConfigData id : configData.getItemData()) {	
			if(randomizeClauseOrder) {
				clause1IntoClause2 = Math.random() < 0.5;
			}
			RelativeExerciseItemConfigData itemData = (RelativeExerciseItemConfigData) id;
		
			ArrayList<ArrayList<String>> orders = getOrders(itemData, configData.getStamp(), configData.getActivity());
			
			if(orders == null) {
				//TODO: we cannot use these exercises until we have the JS type with multiple correct answers in FeedBook
				// we could also perform the check in the XML generator, but since we had to modify the chunk assembly as well,
				// we need to change this class anyways when we can support multiple correct answers
				System.out.println("No unambiguous JS generation possible.");
				return null;
			}
			
			for(int i = 0; i < orders.get(0).size(); i++) {
				ConstructionTextPart c = new ConstructionTextPart(orders.get(0).get(i), sentenceId);
				for(int j = 1; j < orders.size(); j++) {
					c.getTargetAlternatives().add(orders.get(j).get(i));
				}
				parts.add(c);
			}
			
			sentenceId++;
		}

		ExerciseData data = new ExerciseData(parts);
		addPlainText(data);

		return data;
	}
	
	/**
	 * Compiles the exercise information for Categorize exercises.
	 * For contact clauses.
	 * @param configData			The data of an exercise from the config file
	 * @return The exercise information
	 */
	private ExerciseData generateCategorizeTask(ExerciseConfigData configData) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseItemConfigData id : configData.getItemData()) {
			RelativeExerciseItemConfigData itemData = (RelativeExerciseItemConfigData)id;
			String category = itemData.isContact() ? "You can leave out the relative pronoun" : "You cannot leave out the pronoun";

			ConstructionTextPart c = new ConstructionTextPart(itemData.getRelativeSentence(), sentenceId++);
			c.setCategory(category);
			c.setConstructionType(DetailedConstruction.REL_CLAUSE);
			Distractor d = new Distractor("");
			c.getDistractors().add(d);
			
			parts.add(c);
		}

		ExerciseData data = new ExerciseData(parts);
		addPlainText(data);

		return data;
	}
	
	/**
	 * Compiles the exercise information for FiB exercises where the 2 clauses and 
	 * the beginning of the sentence are given.
	 * For contact clauses.
	 * @return The exercise information
	 */
	private ExerciseData generatePromptedTaskContact(ExerciseConfigData configData) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseItemConfigData id : configData.getItemData()) {
			if (parts.size() > 0) {
				parts.add(new HtmlTextPart(" <br><br>", sentenceId));
			}
			
			RelativeExerciseItemConfigData itemData = (RelativeExerciseItemConfigData)id;
			
			parts.add(new PlainTextPart(itemData.getPositionsClause1().get(0).second + " " + itemData.getPositionsClause2().get(0).second, sentenceId));
			parts.add(new HtmlTextPart(" <br>", sentenceId));
						
			String[] words = itemData.getContactRelativeSentence().split(" ");
			String prompt = words[0] + " " + words[1];
			parts.add(new PlainTextPart(prompt, sentenceId));
			
			ConstructionTextPart c = new ConstructionTextPart(itemData.getContactRelativeSentence().substring(prompt.length()).trim(), sentenceId);
			c.setConstructionType(DetailedConstruction.REL_CLAUSE);
			Distractor d = new Distractor("");
			d.setFeedback(itemData.getFeedback());
			c.getDistractors().add(d);
			if(itemData.getAlternativeRelativeSentence().startsWith(prompt)) {
				c.getTargetAlternatives().add(itemData.getAlternativeRelativeSentence().substring(prompt.length()).trim());
			}
						
			parts.add(c);				
									
			sentenceId++;
		}

		ExerciseData data = new ExerciseData(parts);
		addPlainText(data);

		return data;
	}
	
	/**
	 * Compiles the exercise information for open exercises where only the 2 clauses are given.
	 * For contact clauses.
	 * @param configData	The data of an exercise from the config file
	 * @return The exercise information
	 */
	private ExerciseData generateOpenTaskContact(ExerciseConfigData configData) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseItemConfigData id : configData.getItemData()) {
			RelativeExerciseItemConfigData itemData = (RelativeExerciseItemConfigData) id;
			
			if (parts.size() > 0) {
				parts.add(new HtmlTextPart(" <br><br>", sentenceId));
			}
			
			parts.add(new PlainTextPart(itemData.getPositionsClause1().get(0).second + " " + itemData.getPositionsClause2().get(0).second, sentenceId));
			parts.add(new HtmlTextPart(" <br>", sentenceId));
			
			ConstructionTextPart c = new ConstructionTextPart(itemData.getContactRelativeSentence(), sentenceId);
			c.getTargetAlternatives().add(itemData.getAlternativeRelativeSentence());
			c.setConstructionType(DetailedConstruction.REL_CLAUSE);
			Distractor d = new Distractor("");
			d.setFeedback(itemData.getFeedback());
			c.getDistractors().add(d);
			
			parts.add(c);

			sentenceId++;
		}

		ExerciseData data = new ExerciseData(parts);
		addPlainText(data);

		return data;
	}
	
	ArrayList<ArrayList<String>> getOrders(RelativeExerciseItemConfigData itemData, String stamp, int activity) {
		for(int i = 1; i <=4; i++) {
			ArrayList<ArrayList<String>> orders = new ArrayList<>();
			addOrder1(true, false, itemData, orders, null, i, stamp, activity);
			addOrder1(true, true, itemData, orders, orders.get(0), i, stamp, activity);
			addOrder1(false, false, itemData, orders, orders.get(0), i, stamp, activity);
			addOrder1(false, true, itemData, orders, orders.get(0), i, stamp, activity);
			
			if(orders.size() == 1) {
				//System.out.println("Order " + i);
				return orders;
			}
		}
		
		return null;
	}
	
	private void addOrder1(boolean clause1IntoClause2, boolean extrapose, RelativeExerciseItemConfigData itemData,
			ArrayList<ArrayList<String>> orders, ArrayList<String> chunks, int i, String stamp, int activity) {
		ArrayList<String> order;
		if(i == 1) {
			order = getJSOrder1(itemData, clause1IntoClause2, extrapose, stamp, activity);
		} else if( i == 2) {
			order = getJSOrder3(itemData, clause1IntoClause2, extrapose, stamp, activity);
		} else if( i == 3) {
			order = getJSOrder4(itemData, clause1IntoClause2, extrapose, stamp, activity);
		} else {
			order = getJSOrder2(itemData, clause1IntoClause2, extrapose, stamp, activity);
		}
		if(order != null && (chunks == null || checkAlternativeCoveredByChunking(chunks, order)) && 
				!checkOrderAlreadyCovered(orders, order)) {
			orders.add(order);
		}
	}
	
	private void addOrder(boolean clause1IntoClause2, boolean extrapose, RelativeExerciseItemConfigData itemData,
			ArrayList<ArrayList<String>> orders, ArrayList<String> chunks, String stamp, int activity) {
		ArrayList<String> order = getJSOrder(itemData, clause1IntoClause2, extrapose, stamp, activity);
		if(order != null && (chunks == null || checkAlternativeCoveredByChunking(chunks, order)) && 
				!checkOrderAlreadyCovered(orders, order)) {
			orders.add(order);
		}
	}
		
	/**
	 * Determines main clause and relative clause constituents based on the settings.
	 * @param itemData				The data of an exercise from the config file
	 * @param clause1IntoClause2	<code>true</code> if clause 1 is to be inserted into clause 2
	 * @return	The clause constituents of the main and relative clauses
	 */
	private RelativeTargetAndClauseItems determineClauseItems(RelativeExerciseItemConfigData itemData, boolean clause1IntoClause2) {
		String sentenceClause1 = StringUtils.capitalize(generateSentencesFromPositions(itemData.getPositionsClause1()));
		if(!sentenceClause1.endsWith(".")) {
			sentenceClause1 += ".";
		}
		String sentenceClause2 = StringUtils.capitalize(generateSentencesFromPositions(itemData.getPositionsClause2()));
		if(!sentenceClause2.endsWith(".")) {
			sentenceClause2 += ".";
		}
		
		Pair<Integer, Integer> commonReferenceClause1 = null;
		Pair<Integer, Integer> commonReferenceClause2 = null;
		for(Pair<Integer, String> position : itemData.getPositionsClause1()) {
			for(Pair<Integer, String> position2 : itemData.getPositionsClause2()) {
				if(position.first != 2 && position2.first != 2 && (position.second.equals(position2.second) ||
						itemData.getPronoun().equals("whose") && 
						(((position2.second.endsWith("'") || position2.second.endsWith("’")) && position.second.equals(position2.second.substring(0, position2.second.length() - 1)) || 
								(position2.second.endsWith("'s")  || position2.second.endsWith("’s")) && position.second.equals(position2.second.substring(0, position2.second.length() - 2))) || 
								((position.second.endsWith("'") || position.second.endsWith("’")) && position2.second.equals(position.second.substring(0, position.second.length() - 1)) || 
										(position.second.endsWith("'s") || position.second.endsWith("’s")) && position2.second.equals(position.second.substring(0, position.second.length() - 2)))))) {
					commonReferenceClause1 = new Pair<>(position.first, position.first);
					commonReferenceClause2 = new Pair<>(position2.first, position2.first);
					break;
				}
			}
		}
		
		if(clause1IntoClause2) {
			return new RelativeTargetAndClauseItems(new ArrayList<>(itemData.getPositionsClause2()),
					new ArrayList<>(itemData.getPositionsClause1()), commonReferenceClause2,
					commonReferenceClause1, sentenceClause1, sentenceClause2);
		} else {
			return new RelativeTargetAndClauseItems(new ArrayList<>(itemData.getPositionsClause1()),
					new ArrayList<>(itemData.getPositionsClause2()), commonReferenceClause1,
					commonReferenceClause2, sentenceClause1, sentenceClause2);
		}
	}
	
	/**
	 * Assembles the positions of the two clauses into the order of the requested relative sentence.
	 * Enriches the positions with information on common referent and pronoun.
	 * @param itemData				The data of an exercise from the config file
	 * @param clause1IntoClause2	<code>true</code> if clause 1 is to be inserted into clause 2
	 * @param extrapose				<code>true</code> if the extraposed order is required
	 * @return	The positions of the relative sentence
	 */
	private ArrayList<RelativeClausePosition> determineOrder(RelativeExerciseItemConfigData itemData, 
			boolean clause1IntoClause2, boolean extrapose, String stamp, int activity) {
		RelativeTargetAndClauseItems clauseItems = determineClauseItems(itemData, clause1IntoClause2);
		if(clauseItems.getCommonReferentMainClause() == null || clauseItems.getCommonReferentRelativeClause() == null) {
			System.out.println("Line does not have common referent: " + stamp + ", " + activity + "." + itemData.getItem());
			return null;
		}
		
		ArrayList<RelativeClausePosition> positions = new ArrayList<>();
		
		if(extrapose) {
			// add everything in the main clause
			for(Pair<Integer, String> p : clauseItems.getPositionsMainClause()) {
				RelativeClausePosition position = new RelativeClausePosition(p.second);
				if(p.first >= clauseItems.getCommonReferentMainClause().first && 
						p.first <= clauseItems.getCommonReferentMainClause().second) {
					position.setCommonReferent(true);
				}
						
				positions.add(position);
			}
			
			// add the pronoun
			RelativeClausePosition position = new RelativeClausePosition(itemData.getPronoun());
			position.setPronoun(true);
			positions.add(position);
			
			// add everything in the relative clause except for the common referent
			for(Pair<Integer, String> p : clauseItems.getPositionsRelativeClause()) {
				if(!(p.first >= clauseItems.getCommonReferentRelativeClause().first && p.first <= clauseItems.getCommonReferentRelativeClause().second)) {
					positions.add(new RelativeClausePosition(p.second));
				}
			}
		} else {		
			// add everything in the main clause until after the common reference
			for(int i = 1; i <= clauseItems.getCommonReferentMainClause().second; i++) {
				Pair<Integer,String> p = clauseItems.getPositionsMainClause().get(i - 1);
				RelativeClausePosition position = new RelativeClausePosition(p.second);
				if(p.first >= clauseItems.getCommonReferentMainClause().first && 
						p.first <= clauseItems.getCommonReferentMainClause().second) {
					position.setCommonReferent(true);
				}
						
				positions.add(position);
			}
			
			// add the pronoun
			RelativeClausePosition position = new RelativeClausePosition(itemData.getPronoun());
			position.setPronoun(true);
			positions.add(position);
			
			// add everything from the relative clause except for the common reference
			for(Pair<Integer, String> p : clauseItems.getPositionsRelativeClause()) {
				if(!(p.first >= clauseItems.getCommonReferentRelativeClause().first && p.first <= clauseItems.getCommonReferentRelativeClause().second)) {
					positions.add(new RelativeClausePosition(p.second));
				}
			}
			
			// add the rest of the main clause
			for(int i = clauseItems.getCommonReferentMainClause().second; i < clauseItems.getPositionsMainClause().size(); i++) {
				positions.add(new RelativeClausePosition(clauseItems.getPositionsMainClause().get(i).second));
			}
		}
		
		if(!positions.get(positions.size() - 1).getValue().endsWith(".")) {
			positions.add(new RelativeClausePosition("."));
		}
		
		positions.get(0).setValue(StringUtils.capitalize(positions.get(0).getValue()));
		
		return positions;
	}

	/**
	 * Generates a string representation of the relative sentence from the config settings.
	 * @param itemData				The data of an exercise from the config file
	 * @param clause1IntoClause2	<code>true</code> if clause 1 is to be inserted into clause 2
	 * @param extrapose				<code>true</code> if the extraposed order is required
	 * @return	The relative sentence
	 */
	private String getRelativeSentence(RelativeExerciseItemConfigData itemData, boolean clause1IntoClause2, boolean extrapose, String stamp, int activity) {
		ArrayList<RelativeClausePosition> positions = determineOrder(itemData, clause1IntoClause2, extrapose, stamp, activity);
		if(positions == null) {
			return null;
		}
		
		ArrayList<Pair<Integer, String>> pos = new ArrayList<>();
		for(int i = 1; i <= positions.size(); i++) {
			pos.add(new Pair<>(i, positions.get(i - 1).getValue()));
		}
		
		return generateSentencesFromPositions(pos);		
	}

	/**
	 * Determines whether a Jumbled Sentences ordering is possible with the resulting chunking.
	 * This is relevant since we combine the relative pronoun with the succeeding position.
	 * If the 2 clauses have the same verb as element after the common referent, we need to allow both orderings.
	 * E.g.: The student|who is|new|is|from Syria|.
	 * 		 The student|who is|from Syria|is|new|.
	 * @param chunks	The chunks according to the desired ordering
	 * @param order		The chunks resulting from ordering with the other clause insertion
	 * @return	<code>true</code> if order can be covered by chunks
	 */
	private boolean checkAlternativeCoveredByChunking(ArrayList<String> chunks, ArrayList<String> order) {
		if(chunks.size() != order.size()) {
			return false;
		}
		
		ArrayList<String> possibleChunks = new ArrayList<>(chunks);
		for(String part : order) {
			if(possibleChunks.contains(part)) {
				possibleChunks.remove(part);
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Checks whether a resulting position ordering has already been compiled.
	 * @param orders	The already compiled orderings
	 * @param newOrder	The newly compiled ordering
	 * @return	<code>true</code> if the newly compiled ordering has already been compiled before; 
	 * 			otherwise <code>false</code>
	 */
	private boolean checkOrderAlreadyCovered(ArrayList<ArrayList<String>> orders, ArrayList<String> newOrder) {
		for(ArrayList<String> order : orders) {
			boolean orderIsSame = true;
			if(order.size() == newOrder.size()) {
				for(int i = 0; i < order.size(); i++) {
					if(!order.get(i).equals(newOrder.get(i))) {
						orderIsSame = false;
						break;
					}
				}
			} else {
				orderIsSame = false;
			}
			
			if(orderIsSame) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Compiles a position ordering for jumbled sentences.
	 * Concatenates the pronoun with the succeeding position.
	 * @param itemData				The data of an exercise from the config file
	 * @param clause1IntoClause2	<code>true</code> if clause 1 is to be inserted into clause 2
	 * @param extrapose				<code>true</code> if the extraposed order is required
	 * @return	The string values of the positions in the target order
	 */
	private ArrayList<String> getJSOrder(RelativeExerciseItemConfigData itemData, 
			boolean clause1IntoClause2, boolean extrapose, String stamp, int activity) {
		ArrayList<RelativeClausePosition> positions = determineOrder(itemData, clause1IntoClause2, extrapose, stamp, activity);
		if(positions == null) {
			return null;
		}
		
		ArrayList<String> pos = new ArrayList<>();
		for(int i = 1; i <= positions.size(); i++) {
			if(i > 1 && positions.get(i - 2).isPronoun()) {
				// concatenate the pronoun with the preceding and the succeeding position
				pos.add(positions.get(i - 3).getValue() + " " + positions.get(i - 2).getValue() + " " + positions.get(i - 1).getValue());
			} else if(!positions.get(i - 1).isPronoun() && i != positions.size() && !positions.get(i).isPronoun()) {
				pos.add(positions.get(i - 1).getValue());
			} 
		}
		
		// Add the sentence final punctuation to the word chunk.
		pos.set(pos.size() - 1, pos.get(pos.size() - 1) + positions.get(positions.size() - 1).getValue());
		
		return pos;
	}
	
	/**
	 * pronoun with succeeding item
	 * punctuation as separate item
	 */
	private ArrayList<String> getJSOrder1(RelativeExerciseItemConfigData itemData, 
			boolean clause1IntoClause2, boolean extrapose, String stamp, int activity) {
		ArrayList<RelativeClausePosition> positions = determineOrder(itemData, clause1IntoClause2, extrapose, stamp, activity);
		if(positions == null) {
			return null;
		}
		
		ArrayList<String> pos = new ArrayList<>();
		for(int i = 1; i <= positions.size(); i++) {
			if(i > 1 && positions.get(i - 2).isPronoun()) {
				// concatenate the pronoun with the succeeding position
				pos.add(positions.get(i - 2).getValue() + " " + positions.get(i - 1).getValue());
			} else if(!positions.get(i - 1).isPronoun()) {
				pos.add(positions.get(i - 1).getValue());
			} 
		}
		
		return pos;
	}
	
	/**
	 * pronoun with preceding and succeeding item
	 * punctuation with last item
	 */
	private ArrayList<String> getJSOrder2(RelativeExerciseItemConfigData itemData, 
			boolean clause1IntoClause2, boolean extrapose, String stamp, int activity) {
		ArrayList<RelativeClausePosition> positions = determineOrder(itemData, clause1IntoClause2, extrapose, stamp, activity);
		if(positions == null) {
			return null;
		}
		
		ArrayList<String> pos = new ArrayList<>();
		for(int i = 1; i <= positions.size(); i++) {
			if(i > 1 && positions.get(i - 2).isPronoun()) {
				// concatenate the pronoun with the preceding and the succeeding position
				pos.add(positions.get(i - 3).getValue() + " " + positions.get(i - 2).getValue() + " " + positions.get(i - 1).getValue());
			} else if(!positions.get(i - 1).isPronoun() && i != positions.size() && !positions.get(i).isPronoun()) {
				pos.add(positions.get(i - 1).getValue());
			} 
		}
		
		// Add the sentence final punctuation to the word chunk.
		pos.set(pos.size() - 1, pos.get(pos.size() - 1) + positions.get(positions.size() - 1).getValue());
		
		return pos;
	}
	
	/**
	 * pronoun with succeeding item
	 * punctuation with last item
	 */
	private ArrayList<String> getJSOrder3(RelativeExerciseItemConfigData itemData, 
			boolean clause1IntoClause2, boolean extrapose, String stamp, int activity) {
		ArrayList<RelativeClausePosition> positions = determineOrder(itemData, clause1IntoClause2, extrapose, stamp, activity);
		if(positions == null) {
			return null;
		}
		
		ArrayList<String> pos = new ArrayList<>();
		for(int i = 1; i <= positions.size(); i++) {
			if(i > 1 && positions.get(i - 2).isPronoun()) {
				// concatenate the pronoun with the succeeding position
				pos.add(positions.get(i - 2).getValue() + " " + positions.get(i - 1).getValue());
			} else if(!positions.get(i - 1).isPronoun() && i != positions.size()) {
				pos.add(positions.get(i - 1).getValue());
			} 
		}
		
		// Add the sentence final punctuation to the word chunk.
		pos.set(pos.size() - 1, pos.get(pos.size() - 1) + positions.get(positions.size() - 1).getValue());
		
		return pos;
	}
	
	/**
	 * pronoun with preceding and succeeding item
	 * punctuation as separate item
	 */
	private ArrayList<String> getJSOrder4(RelativeExerciseItemConfigData itemData, 
			boolean clause1IntoClause2, boolean extrapose, String stamp, int activity) {
		ArrayList<RelativeClausePosition> positions = determineOrder(itemData, clause1IntoClause2, extrapose, stamp, activity);
		if(positions == null) {
			return null;
		}
		
		ArrayList<String> pos = new ArrayList<>();
		for(int i = 1; i <= positions.size(); i++) {
			if(i > 1 && positions.get(i - 2).isPronoun()) {
				// concatenate the pronoun with the preceding and the succeeding position
				pos.add(positions.get(i - 3).getValue() + " " + positions.get(i - 2).getValue() + " " + positions.get(i - 1).getValue());
			} else if(!positions.get(i - 1).isPronoun() && (i == positions.size() || !positions.get(i).isPronoun())) {
				pos.add(positions.get(i - 1).getValue());
			} 
		}
		
		return pos;
	}
	
}
