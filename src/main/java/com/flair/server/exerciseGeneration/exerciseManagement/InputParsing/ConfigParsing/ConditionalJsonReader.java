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
			ExerciseTypeSpec t = new ExerciseTypeSpec();
			String exerciseType = (String)type;
			t.setSubtopic(exerciseType.contains("Type1vsType2") ? "conditional_types" : "conditional_form");
			t.setIfClauseFirst(exerciseType.contains("If-clausemainclause"));
			t.setRandomClauseOrder(exerciseType.contains("Randomclauseorder"));
			t.setTargetIfClause(exerciseType.contains("Targetonlyif-clause") || exerciseType.contains("Targetbothclauses") || exerciseType.contains("Useif-clause"));
			t.setTargetMainClause(exerciseType.contains("Targetonlymainclause") || exerciseType.contains("Targetbothclauses") || exerciseType.contains("Usemainclause"));
			t.setFeedbookType(FeedBookExerciseType.getContainedType(exerciseType));
			types.add(t);
		}

		int k = 1;
		for(Object exercise : (JSONArray)jsonObject.get("exercises")) {
			ExerciseConfigData data = new ExerciseConfigData();
			configData.add(data);
			data.setExerciseType(types);
			String subtopic = (String)((JSONObject)exercise).get("subtopic");
			data.setStamp(subtopic.equals("Formation") ? "Conditional" : "Conditional Type"); 
			data.setActivity(k);

			JSONArray sentences = (JSONArray)((JSONObject)exercise).get("sentences");
			int n = 1;
			for(Object sentence : sentences) {
		        ConditionalExerciseItemConfigData cd = new ConditionalExerciseItemConfigData();
		        cd.setConditionalType(((String)((JSONObject)sentence).get("condType")).equals("Type 1") ? 1 : 2);
				cd.setContextBefore((String)((JSONObject)sentence).get("contextBefore"));
				cd.setContextAfter((String)((JSONObject)sentence).get("contextAfter"));
		        
				JSONObject mainClause = (JSONObject)((JSONObject)sentence).get("mainClause");
				JSONArray chunks = (JSONArray)((JSONObject)mainClause).get("chunks");
				ArrayList<Pair<Integer, String>> chunksDef = new ArrayList<>();
				int i = 0;
				for(Object chunk : chunks) {
					chunksDef.add(new Pair<>(++i, (String)chunk));
				}
				cd.setPositionsMainClause(chunksDef);

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
				
				JSONArray gapIndices = (JSONArray)((JSONObject)mainClause).get("gap");
				ArrayList<Pair<Integer, Integer>> gaps = new ArrayList<>();
				gaps.add(new Pair<>((int)((long)gapIndices.get(0)), (int)((long)gapIndices.get(1))));
				cd.setGapMainClause(gaps);
				
				JSONArray targetMtwIndices = (JSONArray)((JSONObject)mainClause).get("mtwTarget");
				ArrayList<Pair<Integer, Integer>> targetsMtw = new ArrayList<>();
				targetsMtw.add(new Pair<>((int)((long)targetMtwIndices.get(0)), (int)((long)targetMtwIndices.get(1))));
				cd.setUnderlineMainClause(targetsMtw);
				
				JSONObject ifClause = (JSONObject)((JSONObject)sentence).get("ifClause");
				chunks = (JSONArray)((JSONObject)ifClause).get("chunks");
				chunksDef = new ArrayList<>();
				i = 0;
				for(Object chunk : chunks) {
					chunksDef.add(new Pair<>(++i, (String)chunk));
				}
				cd.setPositionsIfClause(chunksDef);

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
				
				gapIndices = (JSONArray)((JSONObject)ifClause).get("gap");
				gaps = new ArrayList<>();
				gaps.add(new Pair<>((int)((long)gapIndices.get(0)), (int)((long)gapIndices.get(1))));
				cd.setGapIfClause(gaps);
				
				targetMtwIndices = (JSONArray)((JSONObject)ifClause).get("mtwTarget");
				targetsMtw = new ArrayList<>();
				targetsMtw.add(new Pair<>((int)((long)targetMtwIndices.get(0)), (int)((long)targetMtwIndices.get(1))));
				cd.setUnderlineIfClause(targetsMtw);
				
				cd.setItem(n);
				n++;

				data.getItemData().add(cd);
			}
			
			k++;
		}
		
		
		return configData;
	}
	
}
