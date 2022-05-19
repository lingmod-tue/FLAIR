package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.SpecificationGeneration;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing.ConditionalExerciseItemConfigData;
import com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing.ExerciseConfigData;
import com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing.ExerciseItemConfigData;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.ConditionalNlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.ConditionalSentence;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.Pair;

public class ConditionalSpecificationGenerator implements SpecificationGenerator {
	
	private static final String[] exerciseTypes = new String[] {
			"Type1vsType2_If-clausemainclause_SingleChoice2options_Targetonlyif-clause",
	          		"Type1vsType2_If-clausemainclause_SingleChoice2options_Targetonlymainclause",
	          		"Type1vsType2_If-clausemainclause_SingleChoice2options_Targetbothclauses",
	          		"Type1vsType2_If-clausemainclause_SingleChoice4options_Targetonlyif-clause",
	          		"Type1vsType2_If-clausemainclause_SingleChoice4options_Targetonlymainclause",
	          		"Type1vsType2_If-clausemainclause_SingleChoice4options_Targetbothclauses",
	          		"Type1vsType2_If-clausemainclause_Fill-in-the-Blankslemmainparentheses_Targetonlyif-clause",
	          		"Type1vsType2_If-clausemainclause_Fill-in-the-Blankslemmainparentheses_Targetonlymainclause",
	          		"Type1vsType2_If-clausemainclause_Fill-in-the-Blankslemmainparentheses_Targetbothclauses",
	          		"Type1vsType2_If-clausemainclause_Fill-in-the-Blankslemmadistractorinparentheses_Targetonlyif-clause",
	          		"Type1vsType2_If-clausemainclause_Fill-in-the-Blankslemmadistractorinparentheses_Targetonlymainclause",
	          		"Type1vsType2_If-clausemainclause_Fill-in-the-Blankslemmadistractorinparentheses_Targetbothclauses",
	          		"Type1vsType2_If-clausemainclause_Fill-in-the-Blankslemmasininstructions_Targetonlyif-clause",
	          		"Type1vsType2_If-clausemainclause_Fill-in-the-Blankslemmasininstructions_Targetonlymainclause",
	          		"Type1vsType2_If-clausemainclause_Fill-in-the-Blankslemmasininstructions_Targetbothclauses",
	          		"Type1vsType2_If-clausemainclause_Categorize",
	          		"Type1vsType2_Mainclauseif-clause_SingleChoice2options_Targetonlyif-clause",
	          		"Type1vsType2_Mainclauseif-clause_SingleChoice2options_Targetonlymainclause",
	          		"Type1vsType2_Mainclauseif-clause_SingleChoice2options_Targetbothclauses",
	          		"Type1vsType2_Mainclauseif-clause_SingleChoice4options_Targetonlyif-clause",
	          		"Type1vsType2_Mainclauseif-clause_SingleChoice4options_Targetonlymainclause",
	          		"Type1vsType2_Mainclauseif-clause_SingleChoice4options_Targetbothclauses",
	          		"Type1vsType2_Mainclauseif-clause_Fill-in-the-Blankslemmainparentheses_Targetonlyif-clause",
	          		"Type1vsType2_Mainclauseif-clause_Fill-in-the-Blankslemmainparentheses_Targetonlymainclause",
	          		"Type1vsType2_Mainclauseif-clause_Fill-in-the-Blankslemmainparentheses_Targetbothclauses",
	          		"Type1vsType2_Mainclauseif-clause_Fill-in-the-Blankslemmadistractorinparentheses_Targetonlyif-clause",
	          		"Type1vsType2_Mainclauseif-clause_Fill-in-the-Blankslemmadistractorinparentheses_Targetonlymainclause",
	          		"Type1vsType2_Mainclauseif-clause_Fill-in-the-Blankslemmadistractorinparentheses_Targetbothclauses",
	          		"Type1vsType2_Mainclauseif-clause_Fill-in-the-Blankslemmasininstructions_Targetonlyif-clause",
	          		"Type1vsType2_Mainclauseif-clause_Fill-in-the-Blankslemmasininstructions_Targetonlymainclause",
	          		"Type1vsType2_Mainclauseif-clause_Fill-in-the-Blankslemmasininstructions_Targetbothclauses",
	          		"Type1vsType2_Mainclauseif-clause_Categorize",
	          		"Type1vsType2_Randomclauseorder_SingleChoice2options_Targetonlyif-clause",
	          		"Type1vsType2_Randomclauseorder_SingleChoice2options_Targetonlymainclause",
	          		"Type1vsType2_Randomclauseorder_SingleChoice2options_Targetbothclauses",
	          		"Type1vsType2_Randomclauseorder_SingleChoice4options_Targetonlyif-clause",
	          		"Type1vsType2_Randomclauseorder_SingleChoice4options_Targetonlymainclause",
	          		"Type1vsType2_Randomclauseorder_SingleChoice4options_Targetbothclauses",
	          		"Type1vsType2_Randomclauseorder_Fill-in-the-Blankslemmainparentheses_Targetonlyif-clause",
	          		"Type1vsType2_Randomclauseorder_Fill-in-the-Blankslemmainparentheses_Targetonlymainclause",
	          		"Type1vsType2_Randomclauseorder_Fill-in-the-Blankslemmainparentheses_Targetbothclauses",
	          		"Type1vsType2_Randomclauseorder_Fill-in-the-Blankslemmadistractorinparentheses_Targetonlyif-clause",
	          		"Type1vsType2_Randomclauseorder_Fill-in-the-Blankslemmadistractorinparentheses_Targetonlymainclause",
	          		"Type1vsType2_Randomclauseorder_Fill-in-the-Blankslemmadistractorinparentheses_Targetbothclauses",
	          		"Type1vsType2_Randomclauseorder_Fill-in-the-Blankslemmasininstructions_Targetonlyif-clause",
	          		"Type1vsType2_Randomclauseorder_Fill-in-the-Blankslemmasininstructions_Targetonlymainclause",
	          		"Type1vsType2_Randomclauseorder_Fill-in-the-Blankslemmasininstructions_Targetbothclauses",
	          		"Type1vsType2_Randomclauseorder_Categorize",
	          		"Formation_If-clausemainclause_SingleChoice2options_Targetonlyif-clause",
	          		"Formation_If-clausemainclause_SingleChoice2options_Targetonlymainclause",
	          		"Formation_If-clausemainclause_SingleChoice2options_Targetbothclauses",
	          		"Formation_If-clausemainclause_SingleChoice4options_Targetonlyif-clause",
	          		"Formation_If-clausemainclause_SingleChoice4options_Targetonlymainclause",
	          		"Formation_If-clausemainclause_SingleChoice4options_Targetbothclauses",
	          		"Formation_If-clausemainclause_Fill-in-the-Blankslemmainparentheses_Targetonlyif-clause",
	          		"Formation_If-clausemainclause_Fill-in-the-Blankslemmainparentheses_Targetonlymainclause",
	          		"Formation_If-clausemainclause_Fill-in-the-Blankslemmainparentheses_Targetbothclauses",
	          		"Formation_If-clausemainclause_Fill-in-the-Blankslemmadistractorinparentheses_Targetonlyif-clause",
	          		"Formation_If-clausemainclause_Fill-in-the-Blankslemmadistractorinparentheses_Targetonlymainclause",
	          		"Formation_If-clausemainclause_Fill-in-the-Blankslemmadistractorinparentheses_Targetbothclauses",
	          		"Formation_If-clausemainclause_Half-open_Targetonlyif-clause",
	          		"Formation_If-clausemainclause_Half-open_Targetonlymainclause",
	          		"Formation_If-clausemainclause_Half-open_Targetbothclauses",
	          		"Formation_If-clausemainclause_Fill-in-the-Blankslemmasininstructions_Targetonlyif-clause",
	          		"Formation_If-clausemainclause_Fill-in-the-Blankslemmasininstructions_Targetonlymainclause",
	          		"Formation_If-clausemainclause_MarktheWords_Targetonlyif-clause",
	          		"Formation_If-clausemainclause_MarktheWords_Targetonlymainclause",
	          		"Formation_If-clausemainclause_Memory_Useif-clause",
	          		"Formation_If-clausemainclause_Memory_Usemainclause",
	          		"Formation_If-clausemainclause_JumbledSentences_Targetonlyif-clause",
	          		"Formation_If-clausemainclause_JumbledSentences_Targetonlymainclause",
	          		"Formation_If-clausemainclause_JumbledSentences_Targetbothclauses",
	          		"Formation_Mainclauseif-clause_SingleChoice2options_Targetonlyif-clause",
	          		"Formation_Mainclauseif-clause_SingleChoice2options_Targetonlymainclause",
	          		"Formation_Mainclauseif-clause_SingleChoice2options_Targetbothclauses",
	          		"Formation_Mainclauseif-clause_SingleChoice4options_Targetonlyif-clause",
	          		"Formation_Mainclauseif-clause_SingleChoice4options_Targetonlymainclause",
	          		"Formation_Mainclauseif-clause_SingleChoice4options_Targetbothclauses",
	          		"Formation_Mainclauseif-clause_Fill-in-the-Blankslemmainparentheses_Targetonlyif-clause",
	          		"Formation_Mainclauseif-clause_Fill-in-the-Blankslemmainparentheses_Targetonlymainclause",
	          		"Formation_Mainclauseif-clause_Fill-in-the-Blankslemmainparentheses_Targetbothclauses",
	          		"Formation_Mainclauseif-clause_Fill-in-the-Blankslemmadistractorinparentheses_Targetonlyif-clause",
	          		"Formation_Mainclauseif-clause_Fill-in-the-Blankslemmadistractorinparentheses_Targetonlymainclause",
	          		"Formation_Mainclauseif-clause_Fill-in-the-Blankslemmadistractorinparentheses_Targetbothclauses",
	          		"Formation_Mainclauseif-clause_Half-open_Targetonlyif-clause",
	          		"Formation_Mainclauseif-clause_Half-open_Targetonlymainclause",
	          		"Formation_Mainclauseif-clause_Half-open_Targetbothclauses",
	          		"Formation_Mainclauseif-clause_Fill-in-the-Blankslemmasininstructions_Targetonlyif-clause",
	          		"Formation_Mainclauseif-clause_Fill-in-the-Blankslemmasininstructions_Targetonlymainclause",
	          		"Formation_Mainclauseif-clause_MarktheWords_Targetonlyif-clause",
	          		"Formation_Mainclauseif-clause_MarktheWords_Targetonlymainclause",
	          		"Formation_Mainclauseif-clause_Memory_Useif-clause",
	          		"Formation_Mainclauseif-clause_Memory_Usemainclause",
	          		"Formation_Mainclauseif-clause_JumbledSentences_Targetonlyif-clause",
	          		"Formation_Mainclauseif-clause_JumbledSentences_Targetonlymainclause",
	          		"Formation_Randomclauseorder_SingleChoice2options_Targetonlyif-clause",
	          		"Formation_Randomclauseorder_SingleChoice2options_Targetonlymainclause",
	          		"Formation_Randomclauseorder_SingleChoice2options_Targetbothclauses",
	          		"Formation_Randomclauseorder_SingleChoice4options_Targetonlyif-clause",
	          		"Formation_Randomclauseorder_SingleChoice4options_Targetonlymainclause",
	          		"Formation_Randomclauseorder_SingleChoice4options_Targetbothclauses",
	          		"Formation_Randomclauseorder_Fill-in-the-Blankslemmainparentheses_Targetonlyif-clause",
	          		"Formation_Randomclauseorder_Fill-in-the-Blankslemmainparentheses_Targetonlymainclause",
	          		"Formation_Randomclauseorder_Fill-in-the-Blankslemmainparentheses_Targetbothclauses",
	          		"Formation_Randomclauseorder_Fill-in-the-Blankslemmadistractorinparentheses_Targetonlyif-clause",
	          		"Formation_Randomclauseorder_Fill-in-the-Blankslemmadistractorinparentheses_Targetonlymainclause",
	          		"Formation_Randomclauseorder_Fill-in-the-Blankslemmadistractorinparentheses_Targetbothclauses",
	          		"Formation_Randomclauseorder_Half-open_Targetonlyif-clause",
	          		"Formation_Randomclauseorder_Half-open_Targetonlymainclause",
	          		"Formation_Randomclauseorder_Half-open_Targetbothclauses",
	          		"Formation_Randomclauseorder_Fill-in-the-Blankslemmasininstructions_Targetonlyif-clause",
	          		"Formation_Randomclauseorder_Fill-in-the-Blankslemmasininstructions_Targetonlymainclause",
	          		"Formation_Randomclauseorder_MarktheWords_Targetonlyif-clause",
	          		"Formation_Randomclauseorder_MarktheWords_Targetonlymainclause",
	          		"Formation_Randomclauseorder_Memory_Useif-clause",
	          		"Formation_Randomclauseorder_Memory_Usemainclause",
	          		"Formation_Randomclauseorder_JumbledSentences_Targetonlyif-clause",
	          		"Formation_Randomclauseorder_JumbledSentences_Targetonlymainclause"
	};

	public byte[] generateJsonSpecification(CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, ArrayList<ExerciseData> data) {
		JSONObject spec = new JSONObject();
		spec.put("topic", "Conditionals");
		JSONArray types = new JSONArray();
		for(String type : exerciseTypes) {
			types.add(type);
		}
		spec.put("exerciseTypes", types);
		
		JSONArray exerciseItemMap = new JSONArray();
		spec.put("exerciseItemMap", exerciseItemMap);
		
		JSONArray exercises = new JSONArray();
		spec.put("items", exercises);
		
		int id = 0;
		for(ExerciseData ed : data) {
			ExerciseConfigData d = generateConfigData(parser, generator, lemmatizer, ed);
			
			for(ExerciseItemConfigData s : d.getItemData()) {
				JSONObject sent = new JSONObject();
		        ConditionalExerciseItemConfigData item = (ConditionalExerciseItemConfigData)s;
		        
		        sent.put("condType", item.getConditionalType() == 1 ? "Type 1" : "Type 2");
		        sent.put("contextBefore", item.getContextBefore());
		        sent.put("contextAfter", item.getContextAfter());
		        sent.put("feedback", item.getFeedback());

		        JSONArray condSents = new JSONArray();
		        sent.put("conditionalSentences", condSents);

		        id++;
		        condSents.add(getChunks(item, id, true));
		        id++;
		        condSents.add(getChunks(item, id, false));
		        		        
		        JSONObject mainClause = new JSONObject();
		        sent.put("mainClause", mainClause);
		        mainClause.put("translation", item.getTranslationMainClause());
		        JSONArray distractors = new JSONArray();
		        for(Pair<Integer, String> distractor : item.getDistractorsMainClause()) {
		        	distractors.add(distractor.second);
		        }
		        mainClause.put("distractors", distractors);
		        mainClause.put("givenWord", item.getLemmaMainClause());
		        mainClause.put("semanticDistractor", item.getDistractorLemmaMainClause());
		        JSONArray givenWordsExtended = new JSONArray();
		        for(String gwe : item.getBracketsMainClause()) {
		        	givenWordsExtended.add(gwe);
		        }
		        mainClause.put("givenWordsExtended", givenWordsExtended);
		        
		        JSONObject ifClause = new JSONObject();
		        sent.put("ifClause", ifClause);
		        ifClause.put("translation", item.getTranslationIfClause());
		        distractors = new JSONArray();
		        for(Pair<Integer, String> distractor : item.getDistractorsIfClause()) {
		        	distractors.add(distractor.second);
		        }
		        ifClause.put("distractors", distractors);
		        ifClause.put("givenWord", item.getLemmaIfClause());
		        ifClause.put("semanticDistractor", item.getDistractorLemmaIfClause());
		        givenWordsExtended = new JSONArray();
		        for(String gwe : item.getBracketsIfClause()) {
		        	givenWordsExtended.add(gwe);
		        }
		        ifClause.put("givenWordsExtended", givenWordsExtended);
		        
		        
		        JSONArray exerciseSentences = null;
	        	for(Object mapItem : exerciseItemMap) {
	        		if(((JSONObject)mapItem).get("Title").equals("Exercise " + id)) {
	        			exerciseSentences = (JSONArray)((JSONObject)mapItem).get("sentences");
	        			break;
	        		}
	        	}
	        	if(exerciseSentences == null) {
	        		exerciseSentences = new JSONArray();
	        		JSONObject mapItem = new JSONObject();
	        		exerciseItemMap.add(mapItem);
	        		mapItem.put("Title", "Exercise " + id);
	        		mapItem.put("sentences", exerciseSentences);
	        	}
	        	
				exercises.add(sent);
	        	exerciseSentences.add("" + id);
			}
		}
		
		return spec.toJSONString().getBytes(StandardCharsets.UTF_8);
	}
	
	private JSONObject getChunks(ConditionalExerciseItemConfigData item, int id, boolean ifFirst) {
		JSONArray chunksMain = getChunksOfClause(item, ifFirst, false);
        JSONArray chunksIf = getChunksOfClause(item, ifFirst, true);
               
        JSONObject condSent = new JSONObject();
        
        JSONObject clause1 = new JSONObject();
        JSONObject clause2 = new JSONObject();
        
        JSONArray chunksC1;
        JSONArray chunksC2;
        if(ifFirst) {
        	chunksC1 = chunksIf;
        	chunksC2 = chunksMain;
        	clause1.put("if", true);
    	    clause2.put("if", false);
        } else {
        	chunksC1 = chunksMain;
        	chunksC2 = chunksIf;
        	clause1.put("if", false);
    	    clause2.put("if", true);
        }
        
        JSONObject dotChunk = new JSONObject();
        dotChunk.put("isTarget", false);
        dotChunk.put("text", item.getPunctuationMark());
        chunksC2.add(dotChunk);
        
        clause1.put("chunks", chunksC1);
	    clause2.put("chunks", chunksC2);
	    
        condSent.put("clause1", clause1);
	    condSent.put("clause2", clause2);
        
        condSent.put("id", id);
        
        return condSent;
	}
	
	private JSONArray getChunksOfClause(ConditionalExerciseItemConfigData item, boolean ifFirst, boolean ifClause) {
		JSONArray chunks = new JSONArray();
		Pair<Integer, Integer> gap = ifClause ? item.getGapIfClause().get(0) : item.getGapMainClause().get(0);
		ArrayList<Pair<Integer, String>> positions = ifClause ? item.getPositionsIfClause() : item.getPositionsMainClause();
        int m = 1;
        for(Pair<Integer, String> chunk : positions) {
        	JSONObject chunkDef = new JSONObject();
        	chunkDef.put("isTarget", m >= gap.first && m <= gap.second);
        	String text = chunk.second;
        	if(ifClause == ifFirst && m == 1) {
        		text = StringUtils.capitalize(text);
        	}
        	chunkDef.put("text", text);
        	chunks.add(chunkDef);
        	m++;
        }
        
        if(ifFirst && ifClause) {
        	JSONObject commaChunk = new JSONObject();
        	commaChunk.put("isTarget", false);
        	commaChunk.put("text", ",");
        	chunks.add(commaChunk);
        }
        
        return chunks;
	}
	
	private ExerciseConfigData generateConfigData(CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, ExerciseData data) {
		ConditionalNlpManager nlpManager = new ConditionalNlpManager(parser, generator, data.getPlainText(), lemmatizer);
		
		ExerciseConfigData configData = new ExerciseConfigData();
		
		ArrayList<com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.ConditionalSentence> conditionalSentences = nlpManager.analyzeConditionalSentence(data);
		HashSet<String> types = new HashSet<>();
		for(com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.ConditionalSentence s : conditionalSentences) {
			types.add(s.conditionalType);
		}
						
		for(ConditionalSentence conditionalSentence : conditionalSentences) {
			ConditionalExerciseItemConfigData item = new ConditionalExerciseItemConfigData();
			item.setConditionalType(conditionalSentence.conditionalType.equals("Type 1") ? 1 : 2);
			item.setContextBefore(conditionalSentence.contextBefore);
			item.setContextAfter(conditionalSentence.contextAfter);	
			item.setPunctuationMark(conditionalSentence.punctuationMark);
			
			ArrayList<Pair<Integer, String>> mainChunks = new ArrayList<>();
			for(int i = 0; i < conditionalSentence.mainClause.chunks.size(); i++) {
				Pair<Integer, Integer> chunk = conditionalSentence.mainClause.chunks.get(i);
				String val = data.getPlainText().substring(chunk.first, chunk.second);
				if(i == 0 && Character.isUpperCase(val.charAt(0)) && !nlpManager.isUppercaseWord(chunk.first)) {
					val = (val.charAt(0) + "").toLowerCase() + val.substring(1);
				}
				mainChunks.add(new Pair<>(i + 1, val));
			}
			item.setPositionsMainClause(mainChunks);
			ArrayList<Pair<Integer, String>> ifChunks = new ArrayList<>();
			for(int i = 0; i < conditionalSentence.ifClause.chunks.size(); i++) {
				Pair<Integer, Integer> chunk = conditionalSentence.ifClause.chunks.get(i);
				String val = data.getPlainText().substring(chunk.first, chunk.second);
				if(i == 0 && Character.isUpperCase(val.charAt(0)) && !nlpManager.isUppercaseWord(chunk.first)) {
					val = (val.charAt(0) + "").toLowerCase() + val.substring(1);
				}
				ifChunks.add(new Pair<>(i + 1, val));
			}
			item.setPositionsIfClause(ifChunks);
			item.setTranslationMainClause(conditionalSentence.mainClause.translation);
			item.setTranslationIfClause(conditionalSentence.ifClause.translation);
			
			ArrayList<Pair<Integer, String>> mainDistractors = new ArrayList<>();
			for(int i = 0; i < conditionalSentence.mainClause.distractors.size(); i++) {
				mainDistractors.add(new Pair<>(i + 1, conditionalSentence.mainClause.distractors.get(i)));
			}
			item.setDistractorsMainClause(mainDistractors);
			ArrayList<Pair<Integer, String>> ifDistractors = new ArrayList<>();
			for(int i = 0; i < conditionalSentence.ifClause.distractors.size(); i++) {
				ifDistractors.add(new Pair<>(i + 1, conditionalSentence.ifClause.distractors.get(i)));
			}
			item.setDistractorsIfClause(ifDistractors);
			
			item.setLemmaMainClause(conditionalSentence.mainClause.mainVerb.lemma());
			item.setLemmaIfClause(conditionalSentence.ifClause.mainVerb.lemma());
			item.setDistractorLemmaMainClause(conditionalSentence.mainClause.semanticDistractor.get(0));
			item.setDistractorLemmaIfClause(conditionalSentence.ifClause.semanticDistractor.get(0));

			item.setBracketsMainClause(conditionalSentence.mainClause.lemmatizedClause);
			item.setBracketsIfClause(conditionalSentence.ifClause.lemmatizedClause);
			
			ArrayList<Pair<Integer, Integer>> gapMain = new ArrayList<>();
			gapMain.add(conditionalSentence.mainClause.targetPosition);
			item.setGapMainClause(gapMain);
			ArrayList<Pair<Integer, Integer>> gapIf = new ArrayList<>();
			gapIf.add(conditionalSentence.ifClause.targetPosition);
			item.setGapIfClause(gapIf);
			item.setUnderlineMainClause(new ArrayList<>(gapMain));
			item.setUnderlineIfClause(new ArrayList<>(gapIf));

			configData.getItemData().add(item);
		}
		
		return configData;
	}
}
