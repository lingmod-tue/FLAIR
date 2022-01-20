package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.Distractor;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.HtmlTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.PlainTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.resourceManagement.ResourceLoader;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.ExerciseTopic;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.Pair;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

public class RelativeConfigParser extends ConfigParser {

	@Override
	public ArrayList<ExerciseData> parseConfigFile(InputStream inputStream) {
		ArrayList<ExerciseConfigData> configData = new RelativeExcelFileReader().readExcelFile(inputStream);

		List<String[]> exerciseConstellations = null;
		try (InputStream content = ResourceLoader.loadFile("feedbook_exercise_configurations.tsv");
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content))) {
			TsvParserSettings settings = new TsvParserSettings();
			settings.getFormat().setLineSeparator("\n");
			TsvParser parser = new TsvParser(settings);

			exerciseConstellations = parser.parseAll(bufferedReader);
		} catch (IOException e) {
			return null;
		}

		return generateExerciseForConfig(exerciseConstellations, configData);
	}

	/**
	 * Generates all exercises defined in the resource file for conditional exercises,
	 * for a single exercise defined in the uploaded file.
	 * @param exerciseConstellations	The exercise definitions defined in the resource file
	 * @param data						The exercise as defined in the uploaded file
	 * @return	The generated exercises in abstracted format
	 */
	private ArrayList<ExerciseData> generateExerciseForConfig(List<String[]> exerciseConstellations,
			ArrayList<ExerciseConfigData> data) {
		//TODO: only for debug output:
		for(ExerciseConfigData configData : data) {
			RelativeTargetAndClauseItems clauseItems = determineClauseItems((RelativeExerciseConfigData)configData, true);
			System.out.println(clauseItems.getSentenceClause1() + "\t" + clauseItems.getSentenceClause2());

			HashSet<String> output = new HashSet<>();
			if(!((RelativeExerciseConfigData)configData).getPronoun().equals("whose")) {
				output.add(getRelativeSentence((RelativeExerciseConfigData)configData, true, false));
				output.add(getRelativeSentence((RelativeExerciseConfigData)configData, true, true));
			}
			output.add(getRelativeSentence((RelativeExerciseConfigData)configData, false, false));
			output.add(getRelativeSentence((RelativeExerciseConfigData)configData, false, true));

			for(String s : output) {
				System.out.println(s);
			}
			System.out.println("\n");
		}
		// end TODO
		
		if(exerciseConstellations == null) {
			return null;
		}
		
		String lastBlockId = "";
		ArrayList<ExerciseData> exercises = new ArrayList<>();

		for (String[] configValues : exerciseConstellations) {
			try {
				if (configValues[0].equals("relatives")) {
					if (!lastBlockId.equals(configValues[2])) {
						lastBlockId = configValues[2];
					}
	
					ExerciseData d = null;
					if (configValues[3].equals("0")) {
						d = generateMemoryTask(data, configValues[8].equals("true"));
						d.setExerciseType(ExerciseType.MEMORY);
					} else if (configValues[3].equals("1")) {
						d = generateGapTask(data, configValues[8].equals("true"), false);
						d.setExerciseType(ExerciseType.MARK_THE_WORDS);
					} else if (configValues[3].equals("2")) {
						d = generateGapTask(data, configValues[8].equals("true"), true);
						d.setExerciseType(ExerciseType.SINGLE_CHOICE);
					} else if (configValues[3].equals("3")) {
						d = generateGapTask(data, configValues[8].equals("true"), false);
						d.setExerciseType(ExerciseType.FILL_IN_THE_BLANKS);
					} else if (configValues[3].equals("4")) {
						d = generatePromptedTask(data, configValues[8].equals("true"));
						d.setExerciseType(ExerciseType.FILL_IN_THE_BLANKS);
					} else if (configValues[3].equals("5")) {
						d = generateOpenTask(data);
						d.setExerciseType(ExerciseType.SHORT_ANSWER);
					} else if (configValues[3].equals("6")) {
						d = generateJSTask(data, configValues[8].equals("true"));
						d.setExerciseType(ExerciseType.JUMBLED_SENTENCES);
					} 
	
					if (d != null) {
	        			d.setExerciseTitle(configValues[2] + "/" + configValues[3]);
	        			d.setTopic(ExerciseTopic.RELATIVES);
						exercises.add(d);
					}
	
				}
			} catch(Exception e) {
				if(configValues != null && configValues.length > 3) {
					ServerLogger.get().error(e, "Exercise " + configValues[2] + "/" + configValues[3] + " could not be generated.\n" + e.toString());
				} else {
					ServerLogger.get().error(e, "An exercise could not be generated.\n" + e.toString());
				}
			}
		}

		return exercises;
	}
	
	/**
	 * Compiles the exercise information for Memory exercises.
	 * @param configData			The data of an exercise from the config file
	 * @param clause1IntoClause2	<code>true</code> if clause 1 is to be inserted into clause 2
	 * @return The exercise information
	 */
	private ExerciseData generateMemoryTask(ArrayList<ExerciseConfigData> configData, boolean clause1IntoClause2) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseConfigData id : configData) {
			RelativeExerciseConfigData itemData = (RelativeExerciseConfigData) id;
			RelativeTargetAndClauseItems clauseItems = determineClauseItems(itemData, clause1IntoClause2);
			
			ArrayList<RelativeClausePosition> positions = determineOrder(itemData, clause1IntoClause2, false);
						
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
	private ExerciseData generateGapTask(ArrayList<ExerciseConfigData> configData, boolean clause1IntoClause2, boolean addDistractors) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseConfigData id : configData) {
			RelativeExerciseConfigData itemData = (RelativeExerciseConfigData) id;

			if(!(itemData.getPronoun().equals("whose") && clause1IntoClause2)) {
				if (parts.size() > 0) {
					parts.add(new HtmlTextPart(" <br>", sentenceId));
				}
					
				ArrayList<RelativeClausePosition> positions = determineOrder(itemData, clause1IntoClause2, false);
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
	private ExerciseData generatePromptedTask(ArrayList<ExerciseConfigData> configData, boolean clause1IntoClause2) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseConfigData id : configData) {
			RelativeExerciseConfigData itemData = (RelativeExerciseConfigData) id;

			if (parts.size() > 0) {
				parts.add(new HtmlTextPart(" <br><br>", sentenceId));
			}
			
			RelativeTargetAndClauseItems clauseItems = determineClauseItems(itemData, clause1IntoClause2);

			parts.add(new PlainTextPart(clauseItems.getSentenceClause1() + " " + clauseItems.getSentenceClause2(), sentenceId));
			parts.add(new HtmlTextPart(" <br>", sentenceId));
			
			ArrayList<RelativeClausePosition> positions = determineOrder(itemData, clause1IntoClause2, false);
			ArrayList<Pair<Integer,String>> pos = new ArrayList<>();
			boolean lastWasPronoun = false;
			for(int i = 1; i <= positions.size(); i++) {
				RelativeClausePosition position = positions.get(i - 1);
				pos.add(new Pair<>(i, position.getValue()));

				if(lastWasPronoun && pos.size() > 0) {
					String text = generateSentencesFromPositions(pos);
					parts.add(new PlainTextPart(text, sentenceId));
					pos.clear();
				}
				
				lastWasPronoun = position.isPronoun();
			}	
			
			if(pos.size() > 0) {
				String text = generateSentencesFromPositions(pos);
				ConstructionTextPart c = new ConstructionTextPart(text, sentenceId);
				c.setConstructionType(itemData.getPronoun().equals("who") ? DetailedConstruction.WHO : 
					(itemData.getPronoun().equals("which") ? DetailedConstruction.WHICH : 
						(itemData.getPronoun().equals("that") ? DetailedConstruction.THAT : DetailedConstruction.OTHERPRN)));
				parts.add(c);				
			}
						
			sentenceId++;
		}

		ExerciseData data = new ExerciseData(parts);
		addPlainText(data);

		return data;
	}

	/**
	 * Compiles the exercise information for open exercises where only the 2 clauses are given.
	 * @param configData	The data of an exercise from the config file
	 * @return The exercise information
	 */
	private ExerciseData generateOpenTask(ArrayList<ExerciseConfigData> configData) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseConfigData id : configData) {
			RelativeExerciseConfigData itemData = (RelativeExerciseConfigData) id;
			RelativeTargetAndClauseItems clauseItems = determineClauseItems(itemData, true);

			if (parts.size() > 0) {
				parts.add(new HtmlTextPart(" <br><br>", sentenceId));
			}
			
			parts.add(new PlainTextPart(clauseItems.getSentenceClause1() + " " + clauseItems.getSentenceClause2(), sentenceId));
			parts.add(new HtmlTextPart(" <br>", sentenceId));
						
			HashSet<String> possibleSentences = new HashSet<>();
			possibleSentences.add(getRelativeSentence(itemData, false, false));
			possibleSentences.add(getRelativeSentence(itemData, false, true));
			if(!((RelativeExerciseConfigData)id).getPronoun().equals("whose")) {
				possibleSentences.add(getRelativeSentence(itemData, true, false));
				possibleSentences.add(getRelativeSentence(itemData, true, true));
			}
			
			ArrayList<String> alternatives = new ArrayList<>(possibleSentences);
			//for(int i = 0; i < possibleSentences.size(); i++) {
				ConstructionTextPart c = new ConstructionTextPart(alternatives.get(0), sentenceId);
				for(int i = 1; i < alternatives.size(); i++) {
					c.getTargetAlternatives().add(alternatives.get(i));
				}
				c.setConstructionType(itemData.getPronoun().equals("who") ? DetailedConstruction.WHO : 
					(itemData.getPronoun().equals("which") ? DetailedConstruction.WHICH : 
						(itemData.getPronoun().equals("that") ? DetailedConstruction.THAT : DetailedConstruction.OTHERPRN)));
				
				parts.add(c);
			//}

			//TODO: how can we make sure that the students can enter the sentences in arbitrary order 
			// but not enter the same sentence twice if we elicit all possible sentences?
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
	private ExerciseData generateJSTask(ArrayList<ExerciseConfigData> configData, boolean clause1IntoClause2) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseConfigData id : configData) {	
			RelativeExerciseConfigData itemData = (RelativeExerciseConfigData) id;
		
			ArrayList<ArrayList<String>> orders = new ArrayList<>();
			ArrayList<String> order1 = getJSOrder(itemData, clause1IntoClause2, false);
			if(!checkOrderAlreadyCovered(orders, order1)) {
				orders.add(order1);
			}
			ArrayList<String> order2 = getJSOrder(itemData, clause1IntoClause2, true);
			if(!checkOrderAlreadyCovered(orders, order2)) {
				orders.add(order2);
			}
			
			ArrayList<String> order3 = getJSOrder(itemData, !clause1IntoClause2, false);
			if(checkAlternativeCoveredByChunking(order1, order3) && !checkOrderAlreadyCovered(orders, order3)) {
				orders.add(order3);
			}
			
			ArrayList<String> order4 = getJSOrder(itemData, !clause1IntoClause2, true);
			if(checkAlternativeCoveredByChunking(order1, order4) && !checkOrderAlreadyCovered(orders, order4)) {
				orders.add(order4);
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
	 * Determines main clause and relative clause constituents based on the settings.
	 * @param itemData				The data of an exercise from the config file
	 * @param clause1IntoClause2	<code>true</code> if clause 1 is to be inserted into clause 2
	 * @return	The clause constituents of the main and relative clauses
	 */
	private RelativeTargetAndClauseItems determineClauseItems(RelativeExerciseConfigData itemData, boolean clause1IntoClause2) {
		String sentenceClause1 = StringUtils.capitalize(generateSentencesFromPositions(itemData.getPositionsClause1()));
		if(!sentenceClause1.endsWith(".")) {
			sentenceClause1 += ".";
		}
		String sentenceClause2 = StringUtils.capitalize(generateSentencesFromPositions(itemData.getPositionsClause2()));
		if(!sentenceClause2.endsWith(".")) {
			sentenceClause2 += ".";
		}
		
		if(clause1IntoClause2) {
			return new RelativeTargetAndClauseItems(new ArrayList<>(itemData.getPositionsClause2()),
					new ArrayList<>(itemData.getPositionsClause1()),
					itemData.getCommonReferenceClause2(),
					itemData.getCommonReferenceClause1(), sentenceClause1, sentenceClause2);
		} else {
			return new RelativeTargetAndClauseItems(new ArrayList<>(itemData.getPositionsClause1()),
					new ArrayList<>(itemData.getPositionsClause2()),
					itemData.getCommonReferenceClause1(),
					itemData.getCommonReferenceClause2(), sentenceClause1, sentenceClause2);
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
	private ArrayList<RelativeClausePosition> determineOrder(RelativeExerciseConfigData itemData, 
			boolean clause1IntoClause2, boolean extrapose) {
		RelativeTargetAndClauseItems clauseItems = determineClauseItems(itemData, clause1IntoClause2);
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
	private String getRelativeSentence(RelativeExerciseConfigData itemData, boolean clause1IntoClause2, boolean extrapose) {
		ArrayList<RelativeClausePosition> positions = determineOrder(itemData, clause1IntoClause2, extrapose);
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
	private ArrayList<String> getJSOrder(RelativeExerciseConfigData itemData, 
			boolean clause1IntoClause2, boolean extrapose) {
		ArrayList<RelativeClausePosition> positions = determineOrder(itemData, clause1IntoClause2, extrapose);
		
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
	
}
