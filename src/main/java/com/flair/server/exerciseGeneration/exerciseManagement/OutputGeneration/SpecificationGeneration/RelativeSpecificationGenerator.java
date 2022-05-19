package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.SpecificationGeneration;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing.ExerciseConfigData;
import com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing.ExerciseItemConfigData;
import com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing.RelativeClausePosition;
import com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing.RelativeExerciseItemConfigData;
import com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing.RelativeSentence;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.RelativeNlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.RelativeSentenceAlternative;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.RelativeSentenceChunk;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.Pair;

public class RelativeSpecificationGenerator implements SpecificationGenerator {
	
	private static final String[] exerciseTypes = new String[] {
			"Relativepronoun_Memory",
			"Relativepronoun_MarktheWords",
			"Relativepronoun_SingleChoice",
			"Relativepronoun_Fill-in-the-Blanks",
			"Relativepronoun_Half-open",
			"Relativepronoun_Open",
			"Contactclauses_Half-open",
			"Contactclauses_Open",
			"Contactclauses_Categorize"
	};

	public byte[] generateJsonSpecification(CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, ArrayList<ExerciseData> data) {
		JSONObject spec = new JSONObject();
		spec.put("topic", "Relative pronouns");
		JSONArray types = new JSONArray();
		for(String type : exerciseTypes) {
			types.add(type);
		}
		spec.put("exerciseTypes", types);
				
		JSONArray exerciseItemMap = new JSONArray();
		spec.put("exerciseItemMap", exerciseItemMap);
		
		for(ExerciseData ed : data) {
			ExerciseConfigData d = generateConfigData(parser, generator, lemmatizer, ed);

			JSONArray sentences = new JSONArray();
			spec.put("items", sentences);

	        int counter = 0;
			for(ExerciseItemConfigData s : d.getItemData()) {
				JSONObject sent = new JSONObject();
				sentences.add(sent);
		        RelativeExerciseItemConfigData item = (RelativeExerciseItemConfigData)s;
		        
		        sent.put("contextBefore", item.getContextBefore());
		        sent.put("contextAfter", item.getContextAfter());
		        
		        sent.put("clause1", item.getClause1()); 
		        sent.put("clause2", item.getClause2()); 

		        JSONArray pronouns = new JSONArray();
		        pronouns.add(item.getPronouns());
		        sent.put("pronouns", pronouns);
		        
		        JSONArray distractors = new JSONArray();
		        for(String distractor : item.getDistractors()) {
		        	distractors.add(distractor);
		        }
		        sent.put("distractors", distractors);
		        JSONArray relativeClauses = new JSONArray();
		        sent.put("relativeClauses", relativeClauses);
		        		        
				int i = 0;
		        for(RelativeSentence rs : item.getRelativeSentences()) {
		        	i++;
		        	JSONObject relSent = new JSONObject();
		        	JSONArray chunks = new JSONArray();
		        	int j = 1;
		        	for(RelativeClausePosition chunk : rs.getChunks()) {
		        		chunks.add(chunk.getValue());
		        		if(chunk.isPronoun()) {
				        	relSent.put("pronounIndex", j);
		        		}
		        		if(chunk.isLastCommonReferent()) {
				        	relSent.put("prompt", j);
		        		}
		        		j++;
		        	}
		        	relSent.put("id", ++counter);
		        	relSent.put("chunks", chunks);
		        	
		        	relativeClauses.add(relSent);
		        	
		        	JSONArray exerciseSentences = null;
		        	for(Object mapItem : exerciseItemMap) {
		        		if(((JSONObject)mapItem).get("Title").equals("Exercise " + i)) {
		        			exerciseSentences = (JSONArray)((JSONObject)mapItem).get("sentences");
		        			break;
		        		}
		        	}
		        	if(exerciseSentences == null) {
		        		exerciseSentences = new JSONArray();
		        		JSONObject mapItem = new JSONObject();
		        		exerciseItemMap.add(mapItem);
		        		mapItem.put("Title", "Exercise " + i);
		        		mapItem.put("sentences", exerciseSentences);
		        	}
		        	
		        	exerciseSentences.add("" + counter);
		        }
			}
		}
		
		return spec.toJSONString().getBytes(StandardCharsets.UTF_8);
	}
	
	private ExerciseConfigData generateConfigData(CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, ExerciseData data) {
		RelativeNlpManager nlpManager = new RelativeNlpManager(parser, generator, data.getPlainText(), lemmatizer);
		
		ExerciseConfigData configData = new ExerciseConfigData();
		
		ArrayList<com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.RelativeSentence> relativeSentences = nlpManager.analyzeSentence(data);
		HashSet<String> pronouns = new HashSet<>();
		for(com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.RelativeSentence s : relativeSentences) {
			for(RelativeSentenceChunk chunk : s.relativeSentences.get(0).getChunks()) {
				if(chunk.isPronoun()) {
					pronouns.add(chunk.getValue());
					break;
				}
			}
		}
		
		HashSet<String> distinctPronouns = new HashSet<>();
		
		for(com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.RelativeSentence relativeSentence : relativeSentences) {
			RelativeExerciseItemConfigData item = new RelativeExerciseItemConfigData();
			configData.getItemData().add(item);
			item.setContextBefore(relativeSentence.contextBefore);
			item.setContextAfter(relativeSentence.contextAfter);	
			item.setClause1(relativeSentence.clause1);
			item.setClause2(relativeSentence.clause2);
			for(RelativeSentenceChunk chunk : relativeSentence.relativeSentences.get(0).getChunks()) {
				if(chunk.isPronoun()) {
					item.getPronouns().add(chunk.getValue());
					break;
				}
			}
			distinctPronouns.add(item.getPronouns().get(0));
						
			for(RelativeSentenceAlternative rs : relativeSentence.relativeSentences) {
				RelativeSentence relativeClause = new RelativeSentence();
				item.getRelativeSentences().add(relativeClause);
				relativeClause.setPronounIsOptional(rs.isPronounIsOptional());
				relativeClause.setUseToGenerateExercise(rs.isUseToGenerateExercise());
				int j = 1;
				for(RelativeSentenceChunk c : rs.getChunks()) {
					RelativeClausePosition position = new RelativeClausePosition(c.getValue());
					if(rs.getPronounIndex() == j) {
						position.setPronoun(true);
					}
					if(rs.getPromptEndIndex() == j) {
						position.setLastCommonReferent(true);
					}
					relativeClause.getChunks().add(position);
					j++;
				}
			}
		}
		
		for(ExerciseItemConfigData item : configData.getItemData()) {
			((RelativeExerciseItemConfigData)item).setDistractors(
					nlpManager.getDistractors(((RelativeExerciseItemConfigData)item).getPronouns().get(0), distinctPronouns));
		}

		
		return configData;
	}
}
