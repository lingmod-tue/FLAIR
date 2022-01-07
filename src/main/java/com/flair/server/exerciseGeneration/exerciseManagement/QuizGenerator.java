package com.flair.server.exerciseGeneration.exerciseManagement;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;

import com.flair.server.exerciseGeneration.ZipManager;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.H5PGeneration.H5PGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.H5PGeneration.QuizContentJsonGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration.PreviewGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration.PreviewGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.QuizSettings;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.OutputFormat;
import com.flair.shared.exerciseGeneration.Pair;

public class QuizGenerator extends ExerciseGenerator {
	
	private boolean isCancelled = false;
	private ExerciseGenerator currentGenerator = null;

    @Override
    public ResultComponents generateExercise(ContentTypeSettings settings,
    		CoreNlpParser parser, SimpleNlgParser g, OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
        ArrayList<ExerciseData> exerciseComponents = new ArrayList<>();
        for(ContentTypeSettings taskSettings : ((QuizSettings)settings).getExercises()) {
        	if(isCancelled) {
        		return null;
        	}
        	
        	currentGenerator = taskSettings.getExerciseGenerator();
        	
            ArrayList<ExerciseData> exerciseData = currentGenerator.generateExerciseData(parser, g, lemmatizer, resourceDownloader, taskSettings);
            if(exerciseData != null && exerciseData.size() > 0) {
            	exerciseComponents.addAll(exerciseData);
            }
        }
        
        if(isCancelled) {
    		return null;
    	}
        
        HashMap<String, byte[]> xmlFiles = new HashMap<>();
    	HashMap<String, byte[]> h5PFiles = new HashMap<>();
    	HashMap<String, String> previews = new HashMap<>();
    	
        if(settings.getExerciseSettings().getOutputFormats().contains(OutputFormat.FEEDBOOK_XML)) {
        	xmlFiles = generateFeedbookXml(exerciseComponents); 
        }

        if (isCancelled) {
        	return null;
        }
        
        if(settings.getExerciseSettings().getOutputFormats().contains(OutputFormat.H5P)) {
            h5PFiles = generateH5P(exerciseComponents, settings);
        }
        
        return new ResultComponents(h5PFiles, previews, xmlFiles);
    }
    
    @Override
    public ArrayList<ExerciseData> generateExerciseData(CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, 
			ResourceDownloader resourceDownloader, ContentTypeSettings settings) {
    	return null;
    }
    
    @Override
	protected HashMap<String, byte[]> generateFeedbookXml(ArrayList<ExerciseData> data) {
        return new DocumentBasedExerciseGenerator().generateFeedbookXml(data);         
	}
	
	@Override
	protected HashMap<String, byte[]> generateH5P(ArrayList<ExerciseData> data, ContentTypeSettings settings) {
		HashMap<String, byte[]> h5PFiles = new HashMap<>();
		
		ArrayList<Pair<String, byte[]>> relevantResources = new ArrayList<>();
        for(ContentTypeSettings contentTypeSettings : ((QuizSettings)settings).getExercises()){
        	if(isCancelled) {
        		return null;
        	}
        	
        	for(Pair<String, byte[]> resource : getRelevantResources(contentTypeSettings.getResources())) {
        		if(!relevantResources.stream().anyMatch(r -> r.first.equals(resource.first))) {
        			relevantResources.add(resource);
        		}
        	}
        }

        ArrayList<JSONObject> jsonObject = new QuizContentJsonGenerator().modifyJsonContent(settings, data);
    	byte[] h5pFile = ZipManager.generateModifiedZipFile(settings.getResourceFolder(), jsonObject.toString(), relevantResources);
    	h5PFiles.put(((ExerciseSettings)settings.getExerciseSettings()).getTaskName(), h5pFile);
		
    	return h5PFiles;
	}
	
	@Override
	protected HashMap<String, String> generatePreview(ArrayList<ExerciseData> data) {
        return new DocumentBasedExerciseGenerator().generatePreview(data);         
	}

	@Override
	public void cancelGeneration() {
		isCancelled = true;
		if(currentGenerator != null) {
			currentGenerator.cancelGeneration();
		}
	}

}