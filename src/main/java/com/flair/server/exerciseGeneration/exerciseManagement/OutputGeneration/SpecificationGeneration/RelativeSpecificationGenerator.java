package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.SpecificationGeneration;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing.ExerciseConfigData;
import com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing.ExerciseItemConfigData;
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
		
		JSONArray exercises = new JSONArray();
		spec.put("exercises", exercises);
		
		for(ExerciseData ed : data) {
			ExerciseConfigData d = generateConfigData(parser, generator, lemmatizer, ed);
			
			JSONObject exercise = new JSONObject();
			exercises.add(exercise);
			
			JSONArray sentences = new JSONArray();
	        exercise.put("sentences", sentences);

			for(ExerciseItemConfigData s : d.getItemData()) {
				JSONObject sent = new JSONObject();
				sentences.add(sent);
		        RelativeExerciseItemConfigData item = (RelativeExerciseItemConfigData)s;
		        
		        sent.put("contextBefore", item.getContextBefore());
		        sent.put("contextAfter", item.getContextAfter());
		        
		        sent.put("clause1", item.getPositionsClause1().get(0).second); 
		        sent.put("clause2", item.getPositionsClause2().get(0).second); 

		        JSONArray pronouns = new JSONArray();
		        pronouns.add(item.getPronoun());
		        sent.put("pronouns", pronouns);
		        
		        JSONArray distractors = new JSONArray();
		        for(String distractor : item.getDistractors()) {
		        	distractors.add(distractor);
		        }
		        sent.put("distractors", distractors);
		        JSONArray relativeClauses = new JSONArray();
		        sent.put("relativeClauses", relativeClauses);
		        		        
		        for(RelativeSentence rs : item.getRelativeSentences()) {
		        	JSONObject relSent = new JSONObject();
		        	JSONArray chunks = new JSONArray();
		        	for(String chunk : rs.getChunks()) {
		        		chunks.add(chunk);
		        	}
		        	relSent.put("chunks", chunks);
		        	relSent.put("pronounIsOptional", rs.isPronounIsOptional());
		        	relSent.put("generateDistinctExercise", rs.isUseToGenerateExercise());
		        	relativeClauses.add(relSent);
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
		
		String stamp = nlpManager.getStamp(pronouns);
		if(stamp == null) {
			return null;
		}
		configData.setStamp(stamp);
				
		for(com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.RelativeSentence relativeSentence : relativeSentences) {
			RelativeExerciseItemConfigData item = new RelativeExerciseItemConfigData();
			configData.getItemData().add(item);
			item.setContextBefore(relativeSentence.contextBefore);
			item.setContextAfter(relativeSentence.contextAfter);	
			item.getPositionsClause1().add(new Pair<>(0, relativeSentence.clause1));
			item.getPositionsClause2().add(new Pair<>(0, relativeSentence.clause2));
			for(RelativeSentenceChunk chunk : relativeSentence.relativeSentences.get(0).getChunks()) {
				if(chunk.isPronoun()) {
					item.setPronoun(chunk.getValue());
					break;
				}
			}
			item.setDistractors(nlpManager.getDistractors(item.getPronoun(), stamp));
						
			for(RelativeSentenceAlternative rs : relativeSentence.relativeSentences) {
				RelativeSentence relativeClause = new RelativeSentence();
				item.getRelativeSentences().add(relativeClause);
				relativeClause.setPronounIsOptional(rs.isPronounIsOptional());
				relativeClause.setUseToGenerateExercise(rs.isUseToGenerateExercise());
				for(RelativeSentenceChunk c : rs.getChunks()) {
					relativeClause.getChunks().add(c.getValue());
				}
			}
		}
		
		return configData;
	}
}
