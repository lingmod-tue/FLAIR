package com.flair.server.exerciseGeneration.exerciseManagement;


import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;

import com.flair.server.exerciseGeneration.ZipManager;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.H5PGeneration.H5PConstantsManager;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.H5PGeneration.QuizContentJsonGenerator;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.OutputFormat;
import com.flair.shared.exerciseGeneration.Pair;

/**
 * Generates exercises bundled into a quiz
 * @author taheck
 *
 */
public class QuizGenerator extends ExerciseGenerator {
	
	public QuizGenerator(String topic) {
		super(topic);
	}

	private boolean isCancelled = false;
	private ExerciseGenerator currentGenerator = null;

    @Override
    public ResultComponents generateExercise(ExerciseGenerationMetadata settings,
    		CoreNlpParser parser, SimpleNlgParser g, OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
        ArrayList<ExerciseData> exerciseComponents = new ArrayList<>();
        for(ExerciseGenerationMetadata taskSettings : ((QuizGenerationMetadata)settings).getExercises()) {
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
    	HashMap<String, byte[]> specifications = new HashMap<>();
    	HashMap<String, String> previews = new HashMap<>();
    	
        if(settings.getExerciseSettings().getOutputFormats().contains(OutputFormat.FEEDBOOK_XML)) {
        	xmlFiles = generateFeedbookXml(exerciseComponents); 
        }

        if (isCancelled) {
        	return null;
        }
        
        if(settings.getExerciseSettings().getOutputFormats().contains(OutputFormat.SPECIFICATION)) {
    		specifications = generateSpecification(parser, g, lemmatizer, exerciseComponents, settings);
        }
        
        if (isCancelled) {
        	return null;
        }  
        
        if(settings.getExerciseSettings().getOutputFormats().contains(OutputFormat.H5P)) {
            h5PFiles = generateH5P(exerciseComponents, settings);
        }
        
        return new ResultComponents(h5PFiles, previews, xmlFiles, specifications);
    }
    
    @Override
    public ArrayList<ExerciseData> generateExerciseData(CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, 
			ResourceDownloader resourceDownloader, ExerciseGenerationMetadata settings) {
    	return null;
    }
    
    @Override
	protected HashMap<String, byte[]> generateFeedbookXml(ArrayList<ExerciseData> data) {
		if(data.size() > 0) {
			return new DocumentBasedExerciseGenerator(data.get(0).getTopic()).generateFeedbookXml(data);      
		} else {
			return null;
		}
	}
	
	@Override
	protected HashMap<String, byte[]> generateH5P(ArrayList<ExerciseData> data, ExerciseGenerationMetadata settings) {
		HashMap<String, byte[]> h5PFiles = new HashMap<>();
		
		ArrayList<Pair<String, byte[]>> relevantResources = new ArrayList<>();
        for(ExerciseGenerationMetadata contentTypeSettings : ((QuizGenerationMetadata)settings).getExercises()){
        	if(isCancelled) {
        		return null;
        	}
        	
        	for(Pair<String, byte[]> resource : getRelevantResources(contentTypeSettings.getResources())) {
        		if(!relevantResources.stream().anyMatch(r -> r.first.equals(resource.first))) {
        			relevantResources.add(resource);
        		}
        	}
        }

        ArrayList<JSONObject> jsonObject = new QuizContentJsonGenerator().modifyJsonContent(data);
    	byte[] h5pFile = ZipManager.generateModifiedZipFile(H5PConstantsManager.getResourceFolder(ExerciseType.QUIZ), jsonObject.toString(), relevantResources);
    	h5PFiles.put(settings.getExerciseSettings().getQuiz(), h5pFile);
		
    	return h5PFiles;
	}
	
	@Override
	protected HashMap<String, String> generatePreview(ArrayList<ExerciseData> data) {
		if(data.size() > 0) {
	        return new DocumentBasedExerciseGenerator(data.get(0).getTopic()).generatePreview(data);         
		} else {
			return null;
		}		
	}
	
	@Override
	protected HashMap<String, byte[]> generateSpecification(CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer,
			ArrayList<ExerciseData> data, ExerciseGenerationMetadata settings) {
		if(data.size() > 0) {
	        return new DocumentBasedExerciseGenerator(data.get(0).getTopic()).generateSpecification(parser, generator, lemmatizer, data, settings);         
		} else {
			return null;
		}
	}

	@Override
	public void cancelGeneration() {
		isCancelled = true;
		if(currentGenerator != null) {
			currentGenerator.cancelGeneration();
		}
	}

}