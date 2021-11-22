package com.flair.server.exerciseGeneration.exerciseManagement;


import com.flair.server.exerciseGeneration.OutputComponents;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.server.utilities.ServerLogger;

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
	        OutputComponents generatedExercise = 
	        settings.getExerciseGenerator().generateExercise(settings, parser, generator, lemmatizer, resourceDownloader);    
	        if(generatedExercise == null) {
	        	return new ResultComponents(settings.getName(), null, null, null);
	        }
    		return new ResultComponents(settings.getName(), generatedExercise.getH5pFile(), generatedExercise.getPreviews(), generatedExercise.getXmlFile());
    	} catch(Exception e) {
			ServerLogger.get().error(e, "Exercise could not be generated. Exception: " + e.toString());
    		return null;
    	}
    }

}