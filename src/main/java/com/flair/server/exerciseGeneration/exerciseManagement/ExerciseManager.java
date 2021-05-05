package com.flair.server.exerciseGeneration.exerciseManagement;


import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.QuizSettings;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.SimpleNlgParser;

import edu.stanford.nlp.util.Pair;

public class ExerciseManager {

	/**
	 * Generates a H5P exercise for the provided settings.
	 * @param settings	The exercise settings
	 * @return			The byte array of the generated H5P package
	 */
    public byte[] generateExercises(ContentTypeSettings settings, CoreNlpParser parser, SimpleNlgParser generator) {
    	try {
	        ArrayList<Pair<String, byte[]>> relevantResources = new ArrayList<>();
	    	if(settings instanceof QuizSettings) {
	            for(ContentTypeSettings contentTypeSettings : ((QuizSettings)settings).getExercises()){
	                relevantResources.addAll(getRelevantResources(contentTypeSettings.getResources()));
	            }
	        } else {
	            relevantResources.addAll(getRelevantResources(settings.getResources()));
	        }
	        
	        return settings.getExerciseGenerator().generateExercise(settings, relevantResources, parser, generator);       
    	} catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    /**
     * Retrieves the downloaded resources.
     * @param resources    The downloaded resources
     * @return             The file names and byte content of the downloaded resources
     */
    private ArrayList<Pair<String, byte[]>> getRelevantResources(ArrayList<DownloadedResource> resources) {
        ArrayList<Pair<String, byte[]>> relevantResources = new ArrayList<>();
        for(DownloadedResource downloadedResource : resources) {
        	relevantResources.add(new Pair<>(downloadedResource.getFileName(), downloadedResource.getFileContent()));
        }

        return relevantResources;
    }

}