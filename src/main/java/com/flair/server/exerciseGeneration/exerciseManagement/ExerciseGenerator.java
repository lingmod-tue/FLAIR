package com.flair.server.exerciseGeneration.exerciseManagement;


import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.flair.server.exerciseGeneration.OutputComponents;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.domManipulation.ZipManager;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exerciseGeneration.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class ExerciseGenerator {

	/**
     * Generates the H5P exercise for the specified content type settings.
     * @param settings	The content type settings
	 * @param resources	The downloaded resources of the web page for which the exercise is generated
	 * @return			The byte array of the generated H5P file
	 */
    public abstract OutputComponents generateExercise(ContentTypeSettings settings,
    		CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader);
    
    public abstract void cancelGeneration();

    /**
     * Writes the extracted components to the JSON configuration file and zips everything into a H5P package.
     * @param settings 				The settings of the content type
     * @param exerciseComponents	The extracted exercise components which need to be specified in the JSON configuration
     * @param resources				The downloaded resources
     * @return						The byte array of the generated H5P file, the preview and the FeedBook input XML string
     */
    protected OutputComponents createH5pPackage(ContentTypeSettings settings, ArrayList<JsonComponents> exerciseComponents,
                                      ArrayList<Pair<String, byte[]>> resources) {
    	if(exerciseComponents.size() > 0) {
	        try {
	            OutputComponents output = settings.getJsonManager().modifyJsonContent(settings, exerciseComponents, settings.getResourceFolder());
	            output.setFeedBookXml(settings.getXmlManager()
	            		.generateFeedBookInputXml(settings.isEscapeAsterisksInHtml(), output.getDistractors(), settings.getIndex(), 
	            				output.getPlainText(), output.getHtmlElements(), output.getTaskDescription(), settings.getName(), output.getSimpleExercises()));
	            byte[] h5pFile = ZipManager.generateModifiedZipFile(settings.getResourceFolder(), output.getH5pJson().toString(), resources);
	            output.setH5pFile(h5pFile);
	            return output;
	        } catch (ParseException | IOException e) {
				ServerLogger.get().error(e, "Files could not be zipped. Exception: " + e.toString());
	            return null;
	        }
    	} else {
    		return null;
    	}
    }
        
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
