package com.flair.server.exerciseGeneration.exerciseManagement;


import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.server.utilities.ServerLogger;

import edu.stanford.nlp.util.Pair;

public class ExerciseManager {

	/**
	 * Generates a H5P exercise for the provided settings.
	 * @param settings	The exercise settings
	 * @return			The byte array of the generated H5P package
	 */
    public Pair<String, byte[]> generateExercises(ContentTypeSettings settings, CoreNlpParser parser, SimpleNlgParser generator, 
    		OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
    	try {
	        return new Pair<>(settings.getName(), 
	        		settings.getExerciseGenerator().generateExercise(settings, parser, generator, lemmatizer, resourceDownloader));       
    	} catch(Exception e) {
			ServerLogger.get().error(e, "Exercise could not be generated. Exception: " + e.toString());
    		return null;
    	}
    }

}