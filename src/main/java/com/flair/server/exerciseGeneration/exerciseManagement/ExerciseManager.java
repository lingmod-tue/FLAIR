package com.flair.server.exerciseGeneration.exerciseManagement;


import java.util.HashMap;

import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exerciseGeneration.Pair;

public class ExerciseManager {

	private ExerciseGenerator exerciseGenerator;
	
	public ExerciseManager(ContentTypeSettings settings) {
		this.exerciseGenerator = settings.getExerciseGenerator();
	}
	
	/**
	 * Stops the execution if the task is interrupted.
	 */
	public void stopExecution() {
		exerciseGenerator.cancelGeneration();
	}
	
	/**
	 * Generates a H5P exercise for the provided settings.
	 * @param settings	The exercise settings
	 * @return			The byte array of the generated H5P package
	 */
    public ResultComponents generateExercises(ContentTypeSettings settings, CoreNlpParser parser, SimpleNlgParser generator, 
    		OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
    	try {
	        Pair<byte[], HashMap<String, String>> generatedExercise = 
	        settings.getExerciseGenerator().generateExercise(settings, parser, generator, lemmatizer, resourceDownloader);    
	        if(generatedExercise == null) {
	        	return new ResultComponents(settings.getName(), null, null);
	        }
    		return new ResultComponents(settings.getName(), generatedExercise.first, generatedExercise.second);
    	} catch(Exception e) {
			ServerLogger.get().error(e, "Exercise could not be generated. Exception: " + e.toString());
    		return null;
    	}
    }

}