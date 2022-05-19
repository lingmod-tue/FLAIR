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

public class ConditionalJsonReader extends JsonFileReader {
	
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
		for(Object type : exerciseTypes) {
			ConditionalExerciseTypeSpec t = new ConditionalExerciseTypeSpec();
			String exerciseType = (String)type;
			t.setIfClauseFirst(exerciseType.contains("If-clausemainclause"));
			t.setRandomClauseOrder(exerciseType.contains("Randomclauseorder"));
			t.setTargetIfClause(exerciseType.contains("Targetonlyif-clause") || exerciseType.contains("Targetbothclauses") || exerciseType.contains("Useif-clause"));
			t.setTargetMainClause(exerciseType.contains("Targetonlymainclause") || exerciseType.contains("Targetbothclauses") || exerciseType.contains("Usemainclause"));
			t.setRandomTargetClause(exerciseType.contains("Targetrandomclause"));
			t.setFeedbookType(FeedBookExerciseType.getContainedType(exerciseType));
			types.add(t);
		}

		int k = 1;
		for(Object exercise : (JSONArray)jsonObject.get("exerciseItemMap")) {
			ExerciseConfigData data = new ExerciseConfigData();
			configData.add(data);
			data.setExerciseType(types);
			data.setActivity(k);
			data.setTitle((String)((JSONObject)exercise).get("Title"));

			JSONArray sentences = (JSONArray)((JSONObject)exercise).get("sentences");
			int n = 1;
			for(Object sentence : sentences) {
				JSONObject sent = null;
				JSONObject relevantSentence = null;
				for(Object item : (JSONArray)jsonObject.get("items")) {
					for(Object s : (JSONArray)((JSONObject)item).get("conditionalSentences")) {
						if(("" + (long)((JSONObject)s).get("id")).equals((String)sentence)) {
							sent = (JSONObject)item;
							relevantSentence = (JSONObject)s;
							break;
						}
					}
				}
				
				if(sent == null || relevantSentence == null) {
					System.out.println("Invalid exercise configuration: Id " + sentence);
					continue;
				}
			
		        ConditionalExerciseItemConfigData cd = new ConditionalExerciseItemConfigData();
		        cd.setConditionalType(((String)((JSONObject)sent).get("condType")).equals("Type 1") ? 1 : 2);
				cd.setContextBefore((String)((JSONObject)sent).get("contextBefore"));
				cd.setContextAfter((String)((JSONObject)sent).get("contextAfter"));
				cd.setFeedback((String)((JSONObject)sent).get("feedback"));
				
				JSONObject clause1 = (JSONObject)((JSONObject)relevantSentence).get("clause1");
				JSONArray chunks = (JSONArray)((JSONObject)clause1).get("chunks");
				ArrayList<Pair<Integer, String>> chunksDef = new ArrayList<>();
				int i = 0;
				for(Object chunk : chunks) {
					chunksDef.add(new Pair<>(++i, (String)((JSONObject)chunk).get("text")));
					
					if((boolean)((JSONObject)chunk).get("isTarget")) {
						ArrayList<Pair<Integer, Integer>> gaps = new ArrayList<>();
						gaps.add(new Pair<>(i, i));
						cd.setGapMainClause(gaps);
						
						ArrayList<Pair<Integer, Integer>> targetsMtw = new ArrayList<>();
						targetsMtw.add(new Pair<>(i, i));
						cd.setUnderlineMainClause(targetsMtw);
					}
				}
				if((boolean)((JSONObject)clause1).get("if")) {
					cd.setPositionsIfClause(chunksDef);
				} else {
					cd.setPositionsMainClause(chunksDef);
				}
				
				JSONObject clause2 = (JSONObject)((JSONObject)relevantSentence).get("clause2");
				chunks = (JSONArray)((JSONObject)clause2).get("chunks");
				chunksDef = new ArrayList<>();
				i = 0;
				for(Object chunk : chunks) {
					chunksDef.add(new Pair<>(++i, (String)((JSONObject)chunk).get("text")));
					
					if((boolean)((JSONObject)chunk).get("isTarget")) {
						ArrayList<Pair<Integer, Integer>> gaps = new ArrayList<>();
						gaps.add(new Pair<>(i, i));
						if((boolean)((JSONObject)clause2).get("if")) {
							cd.setGapIfClause(gaps);
						} else {
							cd.setGapMainClause(gaps);
						}
						
						ArrayList<Pair<Integer, Integer>> targetsMtw = new ArrayList<>();
						targetsMtw.add(new Pair<>(i, i));
						if((boolean)((JSONObject)clause2).get("if")) {
							cd.setUnderlineIfClause(targetsMtw);
						} else {
							cd.setUnderlineMainClause(targetsMtw);
						}
					}
				}
				if((boolean)((JSONObject)clause2).get("if")) {
					cd.setPositionsIfClause(chunksDef);
				} else {
					cd.setPositionsMainClause(chunksDef);
				}

				JSONObject mainClause = (JSONObject)((JSONObject)sent).get("mainClause");
				cd.setTranslationMainClause((String)((JSONObject)mainClause).get("translation"));
				JSONArray distractors = (JSONArray)((JSONObject)mainClause).get("distractors");
				ArrayList<Pair<Integer, String>> distractorsDef = new ArrayList<>();
				i = 0;
				for(Object distractor : distractors) {
					distractorsDef.add(new Pair<>(++i, (String)distractor));
				}
				cd.setDistractorsMainClause(distractorsDef);
				cd.setLemmaMainClause((String)((JSONObject)mainClause).get("givenWord"));
				cd.setDistractorLemmaMainClause((String)((JSONObject)mainClause).get("semanticDistractor"));
				
				ArrayList<String> brackets = new ArrayList<>();
				for(Object givenWord : (JSONArray)((JSONObject)mainClause).get("givenWordsExtended")) {
					brackets.add((String)givenWord);
				}
				cd.setBracketsMainClause(brackets);
				
				
				
				JSONObject ifClause = (JSONObject)((JSONObject)sent).get("ifClause");
				cd.setTranslationIfClause((String)((JSONObject)ifClause).get("translation"));
				distractors = (JSONArray)((JSONObject)ifClause).get("distractors");
				distractorsDef = new ArrayList<>();
				i = 0;
				for(Object distractor : distractors) {
					distractorsDef.add(new Pair<>(++i, (String)distractor));
				}
				cd.setDistractorsIfClause(distractorsDef);
				cd.setLemmaIfClause((String)((JSONObject)ifClause).get("givenWord"));
				cd.setDistractorLemmaIfClause((String)((JSONObject)ifClause).get("semanticDistractor"));
				
				brackets = new ArrayList<>();
				for(Object givenWord : (JSONArray)((JSONObject)ifClause).get("givenWordsExtended")) {
					brackets.add((String)givenWord);
				}
				cd.setBracketsIfClause(brackets);
				
				cd.setItem(n);
				n++;

				data.getItemData().add(cd);
			}
			
			k++;
		}
		
		
		return configData;
	}

}
