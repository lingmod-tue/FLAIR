package com.flair.server.exerciseGeneration.exerciseManagement;


import java.util.ArrayList;
import java.util.HashMap;

import com.flair.server.exerciseGeneration.downloadManagement.DownloadedResource;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.Pair;

public abstract class ExerciseGenerator {

	/**
     * Generates the H5P exercise for the specified content type settings.
     * @param settings	The content type settings
	 * @param resources	The downloaded resources of the web page for which the exercise is generated
	 * @return			The byte array of the generated H5P file
	 */
    public abstract ResultComponents generateExercise(ContentTypeSettings settings,
    		CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader);
    
    public abstract void cancelGeneration();
    
    protected abstract ArrayList<ExerciseData> generateExerciseData(CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, 
			ResourceDownloader resourceDownloader, ContentTypeSettings settings);

    protected abstract HashMap<String, byte[]> generateFeedbookXml(ArrayList<ExerciseData> data);
	
	protected abstract HashMap<String, byte[]> generateH5P(ArrayList<ExerciseData> data, ContentTypeSettings settings);
	
	protected abstract HashMap<String, String> generatePreview(ArrayList<ExerciseData> data);
        
    /**
     * Retrieves the downloaded resources.
     * @param resources    The downloaded resources
     * @return             The file names and byte content of the downloaded resources
     */
    protected ArrayList<Pair<String, byte[]>> getRelevantResources(ArrayList<DownloadedResource> resources) {
        ArrayList<Pair<String, byte[]>> relevantResources = new ArrayList<>();
        for(DownloadedResource downloadedResource : resources) {
        	if(downloadedResource.getFileContent() != null && !relevantResources.stream().anyMatch(r -> r.first.equals(downloadedResource.getFileName()))) {
        		relevantResources.add(new Pair<>(downloadedResource.getFileName(), downloadedResource.getFileContent()));
        	}
        }

        return relevantResources;
    }
    
}
