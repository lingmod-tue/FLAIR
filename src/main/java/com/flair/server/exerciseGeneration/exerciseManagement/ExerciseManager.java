package com.flair.server.exerciseGeneration.exerciseManagement;


import java.util.ArrayList;

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
    public ArrayList<ResultComponents> generateExercises(ContentTypeSettings settings, CoreNlpParser parser, SimpleNlgParser generator, 
    		OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
    	try {
    		ArrayList<ResultComponents> result = new ArrayList<>();
	        ArrayList<OutputComponents> generatedExercises = 
	        settings.getExerciseGenerator().generateExercise(settings, parser, generator, lemmatizer, resourceDownloader);  
	        if(generatedExercises == null || generatedExercises.size() == 0) {
	        	return null;
	        }
	        
	        for(OutputComponents generatedExercise : generatedExercises) {
		        if(generatedExercise == null) {
		        	result.add(new ResultComponents(settings.getName(), null, null, null));
		        } else {
		        	result.add(new ResultComponents(generatedExercise.getName(), generatedExercise.getH5pFile(), generatedExercise.getPreviews(), generatedExercise.getXmlFile()));
		        }
	        }
	        
    		return result;
    	} catch(Exception e) {
			ServerLogger.get().error(e, "Exercise could not be generated. Exception: " + e.toString());
    		return null;
    	}
    }

}