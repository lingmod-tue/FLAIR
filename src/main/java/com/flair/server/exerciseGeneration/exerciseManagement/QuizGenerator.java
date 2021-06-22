package com.flair.server.exerciseGeneration.exerciseManagement;


import java.util.ArrayList;

import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.QuizSettings;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.Pair;

public class QuizGenerator extends ExerciseGenerator {
	
	private boolean isCancelled = false;
	private SimpleExerciseGenerator currentGenerator = null;

    @Override
    public byte[] generateExercise(ContentTypeSettings settings,
    		CoreNlpParser parser, SimpleNlgParser g, OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
        ArrayList<JsonComponents> exerciseComponents = new ArrayList<>();
        for(ContentTypeSettings taskSettings : ((QuizSettings)settings).getExercises()) {
        	if(isCancelled) {
        		return null;
        	}
        	
            currentGenerator = new SimpleExerciseGenerator();
            JsonComponents exercise = currentGenerator.prepareExercise(taskSettings, parser, g, lemmatizer, resourceDownloader);
            if(exercise != null) {
            	exerciseComponents.add(exercise);
            }
        }
        
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
        
        if(isCancelled) {
    		return null;
    	}
        
        return createH5pPackage(settings, exerciseComponents, relevantResources);
    }

	@Override
	public void cancelGeneration() {
		isCancelled = true;
		if(currentGenerator != null) {
			currentGenerator.cancelGeneration();
		}
	}

}