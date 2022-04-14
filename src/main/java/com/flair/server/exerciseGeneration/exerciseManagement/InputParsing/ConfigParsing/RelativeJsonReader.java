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
			ExerciseTypeSpec t = new ExerciseTypeSpec();
			String exerciseType = (String)type;
			t.setSubtopic(exerciseType.startsWith("Relativepronoun") ? "Relative pronoun" : "Contact clauses");

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
		for(Object exercise : (JSONArray)jsonObject.get("exercises")) {
			ExerciseConfigData data = new ExerciseConfigData();
			configData.add(data);
			data.setExerciseType(new ArrayList<>(types));
			String subtopic = (String)((JSONObject)exercise).get("subtopic");
			data.setStamp(subtopic.equals("Formation") ? "Conditional" : "Conditional Type"); 
			data.setActivity(k);

			JSONArray sentences = (JSONArray)((JSONObject)exercise).get("sentences");
			int n = 1;
			for(Object sentence : sentences) {
		        RelativeExerciseItemConfigData cd = new RelativeExerciseItemConfigData();
				cd.setContextBefore((String)((JSONObject)sentence).get("contextBefore"));
				cd.setContextAfter((String)((JSONObject)sentence).get("contextAfter"));
				cd.getPositionsClause1().add(new Pair<>(1, (String)((JSONObject)sentence).get("clause1")));
				cd.getPositionsClause2().add(new Pair<>(1, (String)((JSONObject)sentence).get("clause2")));
				for(Object pronoun : (JSONArray)((JSONObject)sentence).get("pronouns")) {
					cd.setPronoun((String) pronoun);	// TODO: deal with multiple pronouns
				}
				for(Object distractor : (JSONArray)((JSONObject)sentence).get("distractors")) {
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
				for(Object relativeClause : (JSONArray)((JSONObject)sentence).get("relativeClauses")) {
					RelativeSentence rs = new RelativeSentence();
					cd.getRelativeSentences().add(rs);
					for(Object chunk : (JSONArray)((JSONObject)relativeClause).get("chunks")) {
						rs.getChunks().add((String)chunk);
					}
					rs.setPronounIsOptional((boolean)((JSONObject)relativeClause).get("pronounIsOptional"));
					rs.setUseToGenerateExercise((boolean)((JSONObject)relativeClause).get("generateDistinctExercise"));
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
