package com.flair.server.exerciseGeneration.exerciseManagement;


import java.util.ArrayList;
import java.util.HashMap;

import com.flair.server.exerciseGeneration.downloadManagement.DownloadedResource;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.Pair;

/**
 * Base class for all exercise generators.
 * @author taheck
 *
 */
public abstract class ExerciseGenerator {
	
	public ExerciseGenerator(String topic) {
		exerciseTopic = topic;
	}
	
	protected final String exerciseTopic;
	
	/**
	 * Indicates whether the exercise generation has been cancelled by the user.
	 */
	protected boolean isCancelled = false;
	
	/**
	 * Generates exercises in the requested output formats for the provided input.
	 * @param settings				The configuration settings
	 * @param parser				The CoreNlp parser
	 * @param generator				The SimpleNlg parser
	 * @param lemmatizer			The OpenNlp lemmatizer
	 * @param resourceDownloader	The resource downloader. Needs to be shared across all threads to make sure that resource names are unique.
	 * @return	The generated exercise(s) in the requested output format(s)
	 */
    public abstract ResultComponents generateExercise(ExerciseGenerationMetadata settings,
    		CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader);
    
    /**
     * Aborts the exercise generation when the user cancels it.
     */
    public abstract void cancelGeneration();
    
    /**
     * Generates an abstraction layer for exercises which contains all information on the generated exercises but is independent of the output format.
	 * @param parser				The CoreNlp parser
	 * @param generator				The SimpleNlg parser
	 * @param lemmatizer			The OpenNlp lemmatizer
	 * @param resourceDownloader	The resource downloader. Needs to be shared across all threads to make sure that resource names are unique.
	 * @param settings				The configuration settings
     * @return	The generated exercise(s) in abstracted format
     */
    protected abstract ArrayList<ExerciseData> generateExerciseData(CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, 
			ResourceDownloader resourceDownloader, ExerciseGenerationMetadata settings);

    /**
     * Generates a XML file for upload to the FeedBook from the abstracted exercise format.
     * @param data	The exercise in abstracted format
     * @return	The generated XML file(s) in byte format, mapped to the file name
     */
    protected abstract HashMap<String, byte[]> generateFeedbookXml(ArrayList<ExerciseData> data);
	
    /**
     * Generates a H5P package from the abstracted exercise format.
     * @param data		The exercise in abstracted format
	 * @param settings	The configuration settings
     * @return	The generated H5P package(s) in byte format, mapped to the file name
     */
	protected abstract HashMap<String, byte[]> generateH5P(ArrayList<ExerciseData> data, ExerciseGenerationMetadata settings);
	
	/**
     * Generates a HTML preview from the abstracted exercise format.
     * @param data		The exercise in abstracted format
     * @return	The generated HTML preview(s) as String, mapped to the file name
	 */
	protected abstract HashMap<String, String> generatePreview(ArrayList<ExerciseData> data);
	
	/**
	 * Generates a JSON specification of the exercise.
	 * @param parser		The CoreNLP parser
	 * @param generator		The SimpleNLG parser
	 * @param lemmatizer	The OpenNLP lemmatizer
	 * @param data			The exercise in abstracted format
	 * @param settings		The exercise metadata
	 * @return	The specification of exercises for further processing
	 */
	protected abstract HashMap<String, byte[]> generateSpecification(CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer,
			ArrayList<ExerciseData> data, ExerciseGenerationMetadata settings);

	/**
     * Retrieves the downloaded resources.
     * @param resources    The downloaded resources
     * @return	The file names and byte content of the downloaded resources
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
