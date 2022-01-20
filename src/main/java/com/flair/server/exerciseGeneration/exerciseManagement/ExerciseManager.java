package com.flair.server.exerciseGeneration.exerciseManagement;


import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.server.utilities.ServerLogger;

public class ExerciseManager {

	private ExerciseGenerator exerciseGenerator;
	
	public ExerciseManager(ExerciseGenerationMetadata settings) {
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
	
	
	/**
	 * Generates exercise(s) for the provided settings.
	 * @param settings				Settings from which to generate exercise(s)
	 * @param parser				The CoreNlp parser
	 * @param generator				The SimpleNlg parser for language generation
	 * @param lemmatizer			The OpenNlp parser for lemmatization
	 * @param resourceDownloader	The resource downloader; needs to be shared across all threads to ensure unique resource names
	 * @return	The generated exercise(s) in the requested output formats
	 */
    public ResultComponents generateExercises(ExerciseGenerationMetadata settings, CoreNlpParser parser, SimpleNlgParser generator, 
    		OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
    	try {
	        return exerciseGenerator.generateExercise(settings, parser, generator, lemmatizer, resourceDownloader);  
    	} catch(Exception e) {
			ServerLogger.get().error(e, "Exercise could not be generated. Exception: " + e.toString());
    		return null;
    	}
    }

}