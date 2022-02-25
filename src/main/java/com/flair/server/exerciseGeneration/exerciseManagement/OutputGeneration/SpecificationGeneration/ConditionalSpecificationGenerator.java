package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.SpecificationGeneration;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing.ConditionalExerciseItemConfigData;
import com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing.ExerciseConfigData;
import com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing.ExerciseItemConfigData;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.ConditionalNlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.ConditionalNlpManager.ConditionalSentence;
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
		spec.put("exerciseTypes", exerciseTypes);	// TODO generate only those exercises which are listed in the specification
		
		JSONArray exercises = new JSONArray();
		spec.put("exercises", exercises);
		
		for(ExerciseData ed : data) {
			ExerciseConfigData d = generateConfigData(parser, generator, lemmatizer, ed);
			
			JSONObject exercise = new JSONObject();
			exercises.add(exercise);
			
			String stamp = d.getStamp();
			exercise.put("subtopic", d.getStamp().equals("Conditional") ? "Formation" : "Type 1 vs Type 2");
			JSONArray sentences = new JSONArray();
	        exercise.put("sentences", sentences);

			for(ExerciseItemConfigData s : d.getItemData()) {
				JSONObject sent = new JSONObject();
				sentences.add(sent);
		        ConditionalExerciseItemConfigData item = (ConditionalExerciseItemConfigData)s;
		        
		        sent.put("condType", item.getConditionalType() == 1 ? "Type 1" : "Type 2");
		        sent.put("contextBefore", item.getContextBefore());
		        sent.put("contextAfter", item.getContextAfter());
		        
		        JSONObject mainClause = new JSONObject();
		        sent.put("mainClause", mainClause);
		        JSONArray chunks = new JSONArray();
		        for(Pair<Integer, String> chunk : item.getPositionsMainClause()) {
		        	chunks.add(chunk.second);
		        }
		        mainClause.put("chunks", chunks);
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
		        JSONArray gaps = new JSONArray();
		        gaps.add(item.getGapMainClause().get(0).first);
		        gaps.add(item.getGapMainClause().get(0).second);
		        mainClause.put("gap", gaps);
		        gaps = new JSONArray();
		        gaps.add(item.getUnderlineMainClause().get(0).first);
		        gaps.add(item.getUnderlineMainClause().get(0).second);
		        mainClause.put("mtwTarget", gaps);

		        JSONObject ifClause = new JSONObject();
		        sent.put("ifClause", ifClause);
		        chunks = new JSONArray();
		        for(Pair<Integer, String> chunk : item.getPositionsIfClause()) {
		        	chunks.add(chunk.second);
		        }
		        ifClause.put("chunks", chunks);
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
		        gaps = new JSONArray();
		        gaps.add(item.getGapIfClause().get(0).first);
		        gaps.add(item.getGapIfClause().get(0).second);
		        mainClause.put("gap", gaps);
		        gaps = new JSONArray();
		        gaps.add(item.getUnderlineIfClause().get(0).first);
		        gaps.add(item.getUnderlineIfClause().get(0).second);
		        mainClause.put("mtwTarget", gaps);
			}
		}
		
		return spec.toJSONString().getBytes(StandardCharsets.UTF_8);
	}
	
	private ExerciseConfigData generateConfigData(CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, ExerciseData data) {
		ConditionalNlpManager nlpManager = new ConditionalNlpManager(parser, generator, data.getPlainText(), lemmatizer);
		
		ExerciseConfigData configData = new ExerciseConfigData();
		
		ArrayList<com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.ConditionalSentence> conditionalSentences = nlpManager.analyzeConditionalSentence();
		HashSet<String> types = new HashSet<>();
		for(com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.ConditionalSentence s : conditionalSentences) {
			types.add(s.conditionalType);
		}
		
		configData.setStamp(types.size() == 1 ? "Conditional" : "Conditional Type");
		//TODO iterate over sentences
		
		return new ExerciseConfigData();
	}
}
