package com.flair.server.exerciseGeneration.exerciseManagement;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration.InstructionGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration.InstructionGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing.ConfigParserFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.NlpManager;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.ConfigExerciseSettings;

public class ConfigBasedExerciseGenerator extends SimpleExerciseGenerator {
		
	public ConfigBasedExerciseGenerator(String topic) {
		super(topic);
	}
	
	@Override
	public ArrayList<ExerciseData> generateExerciseData(CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, 
			ResourceDownloader resourceDownloader, ExerciseGenerationMetadata settings) {
		ConfigExerciseSettings exerciseSettings = (ConfigExerciseSettings)settings.getExerciseSettings();
		
		ArrayList<ExerciseData> exerciseData = ConfigParserFactory.getParser(exerciseTopic).parseConfigFile(new ByteArrayInputStream(exerciseSettings.getFileStream()));
		
		if (isCancelled) {
        	return null;
        }
		
    	for(ExerciseData exercise : exerciseData) { 
			NlpManager nlpManager = new NlpManager(parser, generator, exercise.getPlainText(), lemmatizer);

			feedbackGenerator.generateFeedback(settings.getExerciseSettings(), nlpManager, exercise, 
					exercise.getExerciseType(), exercise.getTopic());
			
			if (isCancelled) {
	        	return null;
	        }
			
	        InstructionGenerator instructionGenerator = InstructionGeneratorFactory.getGenerator(exercise.getTopic(), exercise.getExerciseType());
			instructionGenerator.generateInstructions(exercise);
			
			if (isCancelled) {
	        	return null;
	        }

    		// for underline exercises evtl. separate exercise per line
    		//TODO: all support texts?
		}
    	
    	return exerciseData;
	}
	
	@Override
	protected HashMap<String, String> generatePreview(ArrayList<ExerciseData> data) {
		return new HashMap<>();        
	}

}