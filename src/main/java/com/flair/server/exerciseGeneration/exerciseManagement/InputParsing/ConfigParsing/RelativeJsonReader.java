package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exerciseGeneration.Pair;

public class RelativeJsonReader extends JsonFileReader {
	
	@Override
	public ArrayList<ExerciseConfigData> parse(InputStream inputStream) {
        JSONObject jsonObject;
		try {
			jsonObject = (JSONObject)new JSONParser().parse(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		} catch (ParseException | IOException e) {
			ServerLogger.get().error(e, "Could not read JSON file: " + e.toString());
			return null;
		}
		
		ArrayList<ExerciseConfigData> configData = new ArrayList<>();
		
		JSONArray exerciseTypes = (JSONArray)jsonObject.get("exerciseTypes");
		ArrayList<ExerciseTypeSpec> types = new ArrayList<>();
		ArrayList<ExerciseTypeSpec> typesToAdd = new ArrayList<>();
		for(Object type : exerciseTypes) {
			RelativeExerciseTypeSpec t = new RelativeExerciseTypeSpec();
			String exerciseType = (String)type;
			t.setPracticeContactClauses(!exerciseType.startsWith("Relativepronoun"));
			t.setClause1First(exerciseType.contains("_Clause1clause2_"));
			t.setRandomClauseOrder(exerciseType.contains("_Randomclauseorder_"));

			if(exerciseType.equals("Relativepronoun_SingleChoice")) {
				typesToAdd.add(t);
			} else {
				if(exerciseType.equals("Relativepronoun_Fill-in-the-Blanks")) {
					t.setFeedbookType(FeedBookExerciseType.FIB_LEMMA_PARENTHESES);
				} else if(exerciseType.equals("Relativepronoun_Open")) {
					t.setFeedbookType(FeedBookExerciseType.HALF_OPEN);
				} else if(exerciseType.equals("Relativepronoun_Half-open")) {
					t.setFeedbookType(FeedBookExerciseType.FIB_LEMMA_DISTRACTOR_PARENTHESES);
				} else {
					t.setFeedbookType(FeedBookExerciseType.getContainedType(exerciseType));
				}
				
				types.add(t);
			}
		}

		int k = 1;
		for(Object exercise : (JSONArray)jsonObject.get("exerciseItemMap")) {
			ExerciseConfigData data = new ExerciseConfigData();
			configData.add(data);
			data.setExerciseType(new ArrayList<>(types));
			data.setActivity(k);
			data.setTitle((String)((JSONObject)exercise).get("Title"));

			JSONArray sentences = (JSONArray)((JSONObject)exercise).get("sentences");
			int n = 1;
			for(Object sentence : sentences) {
				JSONObject sent = null;
				JSONObject sentenceItem = null;
				for(Object item : (JSONArray)jsonObject.get("items")) {
					for(Object rc : (JSONArray)((JSONObject)item).get("relativeClauses")) {
						if(((JSONObject)rc).get("id").equals((String)sentence)) {
							sent = (JSONObject)rc;
							sentenceItem = (JSONObject)item;
							break;
						}
					}
					
					if(sent != null) {
						break;
					}
				}
				
				if(sent == null || sentenceItem == null) {
					System.out.println("Invalid exercise configuration: Id " + sentence);
					continue;
				}
				
		        RelativeExerciseItemConfigData cd = new RelativeExerciseItemConfigData();
				cd.setContextBefore((String)((JSONObject)sentenceItem).get("contextBefore"));
				cd.setContextAfter((String)((JSONObject)sentenceItem).get("contextAfter"));
				cd.setFeedback((String)((JSONObject)sentenceItem).get("feedback"));
				cd.setClause1((String)((JSONObject)sentenceItem).get("clause1"));
				cd.setClause2((String)((JSONObject)sentenceItem).get("clause2"));
				for(Object pronoun : (JSONArray)((JSONObject)sentenceItem).get("pronouns")) {
					cd.getPronouns().add((String) pronoun);
				}
				for(Object distractor : (JSONArray)((JSONObject)sentenceItem).get("distractors")) {
					cd.getDistractors().add((String) distractor);
				}
				for(ExerciseTypeSpec type : typesToAdd) {
					if(cd.getDistractors().size() >= 3) {
						type.setFeedbookType(FeedBookExerciseType.SINGLE_CHOICE_4D);
					} else {
						type.setFeedbookType(FeedBookExerciseType.SINGLE_CHOICE_2D);
					}
					data.getExerciseType().add(type);
				}
				for(Object relativeClause : (JSONArray)((JSONObject)sentenceItem).get("relativeClauses")) {
					RelativeSentence rs = new RelativeSentence();
					int j = 1;
					for(Object chunk : (JSONArray)((JSONObject)relativeClause).get("chunks")) {
						RelativeClausePosition position = new RelativeClausePosition((String)chunk);
						if((int)((JSONObject)relativeClause).get("pronounIndex") == j) {
							position.setPronoun(true);
							if(!cd.getPronouns().contains(position.getValue())) {
								cd.getPronouns().add(position.getValue());
							}
						}
						if((int)((JSONObject)relativeClause).get("prompt") == j) {
							position.setLastCommonReferent(true);
						}
						rs.getChunks().add(position);
						j++;
					}
					rs.setPronounIsOptional((boolean)((JSONObject)relativeClause).get("pronounIsOptional"));
					rs.setUseToGenerateExercise(relativeClause.equals(sent));
					cd.getRelativeSentences().add(rs);
				}
				
				cd.setItem(n);
				n++;

				data.getItemData().add(cd);
			}
			
			k++;
		}
		
		
		return configData;
	}

}
