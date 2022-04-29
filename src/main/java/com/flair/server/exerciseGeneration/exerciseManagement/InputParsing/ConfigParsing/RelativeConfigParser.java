package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

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
	protected ArrayList<ExerciseData> generateExerciseForConfig(ExerciseConfigData data) {
		ArrayList<ExerciseData> exercises = new ArrayList<>();

		for (ExerciseTypeSpec configValues : data.getExerciseType()) {
			try {
				boolean asContactClause = ((RelativeExerciseTypeSpec)configValues).isPracticeContactClauses();
				ExerciseData d = null;
				Collections.shuffle(data.getItemData());

				boolean clause1First = ((RelativeExerciseTypeSpec)configValues).isClause1First();
				if(((RelativeExerciseTypeSpec)configValues).isRandomClauseOrder()) {
					clause1First = Math.random() > 0.5;
				}
				
				if (configValues.getFeedbookType().equals(FeedBookExerciseType.MEMORY)) {
					d = generateTask("Memory", data, false, clause1First, asContactClause);
					d.setExerciseType(ExerciseType.MEMORY);
				} else if (configValues.getFeedbookType().equals(FeedBookExerciseType.UNDERLINE)) {
					d = generateTask("Gap", data, false, clause1First, asContactClause);
					if(d != null) {
						d.setExerciseType(ExerciseType.MARK_THE_WORDS);
					}
				} else if (configValues.getFeedbookType().equals(FeedBookExerciseType.SINGLE_CHOICE_2D) || configValues.getFeedbookType().equals(FeedBookExerciseType.SINGLE_CHOICE_4D)) {
					d = generateTask("Gap", data, true, clause1First, asContactClause);
					if(d != null) {
						d.setExerciseType(ExerciseType.SINGLE_CHOICE);
					}
				} else if (configValues.getFeedbookType().equals(FeedBookExerciseType.FIB_LEMMA_PARENTHESES)) {
					d = generateTask("Gap", data, false, clause1First, asContactClause);
					if(d != null) {
						d.setExerciseType(ExerciseType.FILL_IN_THE_BLANKS);
					}
				} else if (configValues.getFeedbookType().equals(FeedBookExerciseType.FIB_LEMMA_DISTRACTOR_PARENTHESES)) {
					d = generateTask("Prompted", data, false, clause1First, asContactClause);
					d.setExerciseType(ExerciseType.HALF_OPEN);
				} else if (configValues.getFeedbookType().equals(FeedBookExerciseType.HALF_OPEN)) {
					d = generateOpenTask(data, asContactClause);
					d.setExerciseType(ExerciseType.SHORT_ANSWER);
				} else if (configValues.getFeedbookType().equals(FeedBookExerciseType.JUMBLED_SENTENCES)) {
					d = generateJSTask(data);
					if(d != null) {
						d.setExerciseType(ExerciseType.JUMBLED_SENTENCES);
					}
				} else if (configValues.getFeedbookType().equals(FeedBookExerciseType.CATEGORIZATION)) {
					d = generateCategorizeTask(data);
					d.setExerciseType(ExerciseType.CATEGORIZE);
				}
				
				if(d != null) {
	    			d.setExerciseTitle(data.getTocId() + FeedBookExerciseType.getFeedbookId(configValues.getFeedbookType()));
	    			d.setTopic(ExerciseTopic.RELATIVES);
	    			d.setSubtopic(data.getStamp());
					exercises.add(d);
				}
			} catch(Exception e) {
				ServerLogger.get().error(e, "Exercise " + data.getTocId() + FeedBookExerciseType.getFeedbookId(configValues.getFeedbookType()) + " could not be generated.\n" + e.toString());
			}
		}

		return exercises;
	}
	
	/**
	 * Compiles the exercise information for exercises.
	 * @param taskType				The exercise type
	 * @param configData			The data of an exercise from the config file
	 * @param clause1AsRelativeClause	<code>true</code> if clause 1 is to be inserted into clause 2
	 * @param randomizeClauseOrder	<code>true</code> if the clause order is to be randomized for each sentence
	 * @return The exercise information
	 */
	private ExerciseData generateTask(String taskType, ExerciseConfigData configData, boolean addDistractors, 
			boolean clause1First, boolean asContactClause) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseItemConfigData id : configData.getItemData()) {					
			RelativeExerciseItemConfigData itemData = (RelativeExerciseItemConfigData) id;
			
			boolean success = false;
			if(taskType.equals("Memory")) {
				success = generateMemoryTask(itemData, parts, configData, clause1First, sentenceId);
			} else if(taskType.equals("Gap")) {
				success = generateGapTask(itemData, parts, configData, clause1First, sentenceId, addDistractors);
			} else if(taskType.equals("Prompted")) {
				success = generatePromptedTask(itemData, parts, configData, clause1First, sentenceId, asContactClause);
			}
			
			if(success) {
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
	 * Compiles the exercise information for Memory exercises.
	 * @param configData			The data of an exercise from the config file
	 * @param clause1AsRelativeClause	<code>true</code> if clause 1 is to be inserted into clause 2
	 * @return The exercise information
	 */
	private boolean generateMemoryTask(RelativeExerciseItemConfigData itemData, ArrayList<TextPart> parts,
			ExerciseConfigData configData, boolean clause1First, int sentenceId) {
		RelativeSentence relativeSentence = null;
		for(RelativeSentence rs : itemData.getRelativeSentences()) {
			if(rs.isUseToGenerateExercise()) {
				relativeSentence = rs;
			}
		}
		if(relativeSentence == null) {
			return false;
		}
								
		ArrayList<Pair<Integer, String>> pos = new ArrayList<>();
		for(int i = 1; i <= relativeSentence.getChunks().size(); i++) {
			pos.add(new Pair<>(i, relativeSentence.getChunks().get(i - 1).getValue()));
		}
		
		String rs = generateSentencesFromPositions(pos);
		
		ConstructionTextPart c = new ConstructionTextPart(getSentence(itemData, clause1First) + " " + getSentence(itemData, !clause1First), sentenceId);
		c.getDistractors().add(new Distractor(rs));
		parts.add(c);	
		
		return true;
	}
	
	/**
	 * Compiles the exercise information for FiB, MC and MtW exercises.
	 * @param configData			The data of an exercise from the config file
	 * @param clause1AsRelativeClause	<code>true</code> if clause 1 is to be inserted into clause 2
	 * @param addDistractors		<code>true</code> for MC exercises
	 * @return The exercise information
	 */
	private boolean generateGapTask(RelativeExerciseItemConfigData itemData, ArrayList<TextPart> parts,
			ExerciseConfigData configData, boolean clause1AsRelativeClause, int sentenceId, boolean addDistractors) {
		RelativeSentence relativeSentence = null;
		for(RelativeSentence rs : itemData.getRelativeSentences()) {
			if(rs.isUseToGenerateExercise()) {
				relativeSentence = rs;
			}
		}
		if(relativeSentence == null) {
			return false;
		}
		
		if (parts.size() > 0) {
			parts.add(new HtmlTextPart(" <br>", sentenceId));
		}
		
		ArrayList<Pair<Integer,String>> pos = new ArrayList<>();
		for(int i = 1; i <= relativeSentence.getChunks().size(); i++) {
			RelativeClausePosition position = relativeSentence.getChunks().get(i - 1);
			if(position.isPronoun()) {
				ConstructionTextPart c = new ConstructionTextPart(position.getValue(), sentenceId);
				for(String alternative : position.getAlternatives()) {
					c.getTargetAlternatives().add(alternative);
				}
				c.setConstructionType(position.getValue().equals("who") ? DetailedConstruction.WHO : 
					(position.getValue().equals("which") ? DetailedConstruction.WHICH : 
						(position.getValue().equals("that") ? DetailedConstruction.THAT : DetailedConstruction.OTHERPRN)));
			
				if(addDistractors) {
					for(String distractor : itemData.getDistractors()) {
						Distractor d = new Distractor(distractor);
						d.setFeedback(itemData.getFeedback());
						c.getDistractors().add(d);
					}
					c.setTargetIndex(new Random().nextInt(c.getDistractors().size() + 1));
				}
				c.setFallbackFeedback(itemData.getFeedback());
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
		
		return true;
	}

	/**
	 * Compiles the exercise information for FiB exercises where the 2 clauses and 
	 * the beginning of the sentence are given.
	 * Extraposition is not possible here since we always put the pronoun in the prompt.
	 * @param configData			The data of an exercise from the config file
	 * @param clause1AsRelativeClause	<code>true</code> if clause 1 is to be inserted into clause 2
	 * @return The exercise information
	 */
	private boolean generatePromptedTask(RelativeExerciseItemConfigData itemData, ArrayList<TextPart> parts,
			ExerciseConfigData configData, boolean clause1First, int sentenceId, boolean asContactClause) {

		RelativeSentence relativeSentence = null;
		for(RelativeSentence rs : itemData.getRelativeSentences()) {
			if(rs.isUseToGenerateExercise()) {
				relativeSentence = rs;
				break;
			}
		}
		if(relativeSentence == null) {
			return false;
		}		

		if (parts.size() > 0) {
			parts.add(new HtmlTextPart(" <br><br>", sentenceId));
		}
		
		parts.add(new PlainTextPart(getSentence(itemData, clause1First) + " " + getSentence(itemData, !clause1First), sentenceId));
		parts.add(new HtmlTextPart(" <br>", sentenceId));
					
		Pair<String, String> promptAndTarget = generatePromptAndTarget(relativeSentence, asContactClause && relativeSentence.isPronounIsOptional());

		parts.add(new PlainTextPart(promptAndTarget.first, sentenceId));

		ConstructionTextPart c = new ConstructionTextPart(promptAndTarget.second, sentenceId);
		c.setConstructionType((asContactClause || itemData.getPronouns().size() == 0) ? DetailedConstruction.REL_CLAUSE : (itemData.getPronouns().get(0).equals("who") ? DetailedConstruction.WHO : 
			(itemData.getPronouns().get(0).equals("which") ? DetailedConstruction.WHICH : 
				(itemData.getPronouns().get(0).equals("that") ? DetailedConstruction.THAT : DetailedConstruction.OTHERPRN))));

		// Check if the contact clause is also an alternative solution
		if(relativeSentence.isPronounIsOptional() && !asContactClause) {
			Pair<String, String> alternativePromptAndTarget = generatePromptAndTarget(relativeSentence, true);
			if(alternativePromptAndTarget.first.equals(promptAndTarget.first)) {
				c.getTargetAlternatives().add(alternativePromptAndTarget.second);
			}
		}
				
		for(RelativeSentence alternative : itemData.getRelativeSentences()) {
			Pair<String, String> alternativePromptAndTarget = generatePromptAndTarget(alternative, asContactClause && relativeSentence.isPronounIsOptional());
			if(alternativePromptAndTarget.first.equals(promptAndTarget.first)) {
				// same prompt of the alternative ordering, so it's an alternative solution
				c.getTargetAlternatives().add(alternativePromptAndTarget.second);
			}
			
			// Check if the contact clause is also an alternative solution
			if(alternative.isPronounIsOptional() && !asContactClause) {
				alternativePromptAndTarget = generatePromptAndTarget(alternative, true);
				if(alternativePromptAndTarget.first.equals(promptAndTarget.first)) {
					c.getTargetAlternatives().add(alternativePromptAndTarget.second);
				}
			}
		}
		
		c.setFallbackFeedback(itemData.getFeedback());
		
		parts.add(c);
		
		return true;
	}
	
	private Pair<String, String> generatePromptAndTarget(RelativeSentence relativeSentence, boolean asContactClause) {
		int i = 0;
		ArrayList<RelativeClausePosition> promptChunks = null;
		for(RelativeClausePosition alternativeChunk : relativeSentence.getChunks()) {
			i++;
			if(alternativeChunk.isLastCommonReferent()) {
				promptChunks = new ArrayList<>(relativeSentence.getChunks().subList(0, i));
				ArrayList<Pair<Integer, String>> positions = new ArrayList<>();
				int n = 1;
				for(RelativeClausePosition promptChunk : promptChunks) {
					if(!asContactClause || !promptChunk.isPronoun()) {
						positions.add(new Pair<>(n++, promptChunk.getValue()));
					}
				}
				
				String prompt = generateSentencesFromPositions(positions);
				
				ArrayList<RelativeClausePosition> targetChunks = new ArrayList<>(relativeSentence.getChunks().subList(i, relativeSentence.getChunks().size()));
				ArrayList<Pair<Integer, String>> targetPositions = new ArrayList<>();
				n = i + 1;
				for(RelativeClausePosition targetChunk : targetChunks) {
					if(!asContactClause || !targetChunk.isPronoun()) {
						targetPositions.add(new Pair<>(n++, targetChunk.getValue()));
					}
				}
				return new Pair<>(prompt, generateSentencesFromPositions(targetPositions));
			}
		}
		
		return null;
	}
	
	private String generateRelativeSentence(RelativeSentence rs, RelativeExerciseItemConfigData itemData, 
			boolean asContactClause, int pronounIndex) {
		ArrayList<Pair<Integer, String>> pos = new ArrayList<>();
		for(int i = 0; i < rs.getChunks().size(); i++) {
			if(rs.getChunks().get(i).isPronoun()) {
				if(!asContactClause) {
					pos.add(new Pair<>(i + 1, itemData.getPronouns().get(pronounIndex)));
				}
			} else {
				pos.add(new Pair<>(i + 1, rs.getChunks().get(i).getValue()));
			}
		}
		
		return generateSentencesFromPositions(pos);
	}
	
	/**
	 * Compiles the exercise information for open exercises where only the 2 clauses are given.
	 * @param configData	The data of an exercise from the config file
	 * @return The exercise information
	 */
	private ExerciseData generateOpenTask(ExerciseConfigData configData, boolean allowOnlyContactClauses) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseItemConfigData id : configData.getItemData()) {
			RelativeExerciseItemConfigData itemData = (RelativeExerciseItemConfigData) id;
						
			HashSet<String> possibleSentences = new HashSet<>();
			
			for(RelativeSentence rs : itemData.getRelativeSentences()) {
				for(int j = 0; j < itemData.getPronouns().size(); j++) {
					// We generate normal and contact clauses for all pronouns
					// it doesn't matter that we get the same contact clause for multiple pronoun options
					// because we use a set, so they are filtered out
					if(!allowOnlyContactClauses || !rs.isPronounIsOptional()) {
						possibleSentences.add(generateRelativeSentence(rs, itemData, false, j));
					}
					if(rs.isPronounIsOptional()) {
						possibleSentences.add(generateRelativeSentence(rs, itemData, true, j));
					}
				}
				// we need this for contact clauses of Excel files
				// we only want contact clauses as correct answers
				if(itemData.getPronouns().size() == 0 && rs.isPronounIsOptional()) {
					possibleSentences.add(generateRelativeSentence(rs, itemData, true, 0));
				}
			}
			
			if(possibleSentences.size() == 0) {
				continue;
			}
			
			if (parts.size() > 0) {
				parts.add(new HtmlTextPart(" <br><br>", sentenceId));
			}
			
			parts.add(new PlainTextPart(getSentence(itemData, true) + " " + getSentence(itemData, false), sentenceId));
			parts.add(new HtmlTextPart(" <br>", sentenceId));
			
			ArrayList<String> alternatives = new ArrayList<>(possibleSentences);

			ConstructionTextPart c = new ConstructionTextPart(alternatives.get(0), sentenceId);
			for(int i = 1; i < alternatives.size(); i++) {
				c.getTargetAlternatives().add(alternatives.get(i));
			}
			c.setConstructionType((allowOnlyContactClauses || itemData.getPronouns().size() == 0) ? DetailedConstruction.REL_CLAUSE : (itemData.getPronouns().get(0).equals("who") ? DetailedConstruction.WHO : 
				(itemData.getPronouns().get(0).equals("which") ? DetailedConstruction.WHICH : 
					(itemData.getPronouns().get(0).equals("that") ? DetailedConstruction.THAT : DetailedConstruction.OTHERPRN))));
			c.setFallbackFeedback(itemData.getFeedback());
			
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
	 * @param clause1AsRelativeClause	<code>true</code> if clause 1 is to be inserted into clause 2
	 * @return The exercise information
	 */
	private ExerciseData generateJSTask(ExerciseConfigData configData) {
		ArrayList<TextPart> parts = new ArrayList<>();
		int sentenceId = 1;

		for (ExerciseItemConfigData id : configData.getItemData()) {				
			RelativeExerciseItemConfigData itemData = (RelativeExerciseItemConfigData) id;
			
			RelativeSentence relativeSentence = null;
			for(RelativeSentence rs : itemData.getRelativeSentences()) {
				if(rs.isUseToGenerateExercise()) {
					relativeSentence = rs;
				}
			}
			if(relativeSentence == null) {
				continue;
			}
			
			ArrayList<RelativeSentence> orders = new ArrayList<>();
			orders.add(relativeSentence);
			
			for(RelativeSentence alternative : itemData.getRelativeSentences()) {
				if(!alternative.equals(relativeSentence) && alternative.getChunks().size() == relativeSentence.getChunks().size()) {
					boolean allMatch = true;
					for(RelativeClausePosition alternativeChunk : alternative.getChunks()) {
						if(!relativeSentence.getChunks().stream().anyMatch(c2 -> c2.getValue().equals(alternativeChunk.getValue()))) {
							allMatch = false;
							break;
						}
					}
					
					if(allMatch) {
						// they have identical chunks, only in a different order
						orders.add(alternative);
					}
				}
			}
			
			//TODO: we cannot deal with multiple correct solutions in feedbook yet, but we pretend that we can
			for(int i = 0; i < orders.get(0).getChunks().size(); i++) {
				ConstructionTextPart c = new ConstructionTextPart(orders.get(0).getChunks().get(i).getValue(), sentenceId);
				for(int j = 1; j < orders.size(); j++) {
					c.getTargetAlternatives().add(orders.get(j).getChunks().get(i).getValue());
				}
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
			String category = itemData.getRelativeSentences().get(0).isPronounIsOptional() ? "You can leave out the relative pronoun" : "You cannot leave out the pronoun";

			ArrayList<Pair<Integer, String>> pos = new ArrayList<>();
			for(int i = 1; i <= itemData.getRelativeSentences().get(0).getChunks().size(); i++) {
				pos.add(new Pair<>(i, itemData.getRelativeSentences().get(0).getChunks().get(i - 1).getValue()));
			}
			
			String rs = generateSentencesFromPositions(pos);
			
			ConstructionTextPart c = new ConstructionTextPart(rs, sentenceId++);
			c.setCategory(category);
			c.setConstructionType(DetailedConstruction.REL_CLAUSE);
			c.setFallbackFeedback(itemData.getFeedback());
			
			parts.add(c);
		}

		ExerciseData data = new ExerciseData(parts);
		addPlainText(data);

		return data;
	}
		
	private String getSentence(RelativeExerciseItemConfigData itemData, boolean clause1) {
		String sentence = clause1 ? itemData.getClause1() : itemData.getClause2();
		if(!sentence.endsWith(".")) {
			sentence += ".";
		}
		return sentence;
	}
	
}
