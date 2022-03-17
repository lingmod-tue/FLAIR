package com.flair.server.exerciseGeneration.exerciseManagement;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;

import com.flair.server.exerciseGeneration.ZipManager;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.FeedbackGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.H5PGeneration.H5PConstantsManager;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.H5PGeneration.H5PGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.SpecificationGeneration.SpecificationGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration.SimpleExerciseXmlGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration.XmlGeneratorFactory;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.OutputFormat;
import com.flair.shared.exerciseGeneration.Pair;

public abstract class SimpleExerciseGenerator extends ExerciseGenerator {
	
	public SimpleExerciseGenerator(String topic) {
		super(topic);
	}

	protected FeedbackGenerator feedbackGenerator = new FeedbackGenerator();
	
	@Override
	public ResultComponents generateExercise(ExerciseGenerationMetadata settings, CoreNlpParser parser, SimpleNlgParser generator, 
			OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {		
		ArrayList<ExerciseData> exerciseData = generateExerciseData(parser, generator, lemmatizer, resourceDownloader, settings);

		if(exerciseData == null || exerciseData.size() == 0) {
			return null;
		}
	        
        HashMap<String, byte[]> xmlFiles = new HashMap<>();
    	HashMap<String, byte[]> h5PFiles = new HashMap<>();
    	HashMap<String,byte[]> specification = new HashMap<>();
    	
        if(settings.getExerciseSettings().getOutputFormats().contains(OutputFormat.FEEDBOOK_XML)) {
        	xmlFiles = generateFeedbookXml(exerciseData);
        }
		
        if (isCancelled) {
        	return null;
        }
        
        if(settings.getExerciseSettings().getOutputFormats().contains(OutputFormat.H5P)) {
    		h5PFiles = generateH5P(exerciseData, settings);
        }
        
        if (isCancelled) {
        	return null;
        }  
        
        HashMap<String, String> previews = new HashMap<>();
        if(settings.getExerciseSettings().getOutputFormats().contains(OutputFormat.SPECIFICATION)) {
    		specification = generateSpecification(parser, generator, lemmatizer, exerciseData, settings);
        } else {
            previews = generatePreview(exerciseData);
        }
        
        if (isCancelled) {
        	return null;
        }  
        
        return new ResultComponents(h5PFiles, previews, xmlFiles, specification);
    }
	
	@Override
	protected HashMap<String, byte[]> generateFeedbookXml(ArrayList<ExerciseData> data) {
        HashMap<String, byte[]> xmlFiles = new HashMap<>();

		for(ExerciseData exerciseData : data) {
			SimpleExerciseXmlGenerator g = XmlGeneratorFactory.getXmlGenerator(exerciseData.getExerciseType());
	        byte[] file = g.generateXMLFile(exerciseData);
	        if(file != null) {
	        	xmlFiles.put(exerciseData.getExerciseTitle(), file);    
	        }
		}
        
        return xmlFiles;
	}
	
	@Override
	protected HashMap<String, byte[]> generateH5P(ArrayList<ExerciseData> data, ExerciseGenerationMetadata settings) {
		HashMap<String, byte[]> h5PFiles = new HashMap<>();

		for(ExerciseData exerciseData : data) {
			ArrayList<Pair<String, byte[]>> relevantResources = getRelevantResources(settings.getResources());
	        ArrayList<ExerciseData> datas = new ArrayList<>();
	        datas.add(exerciseData);
	        ArrayList<JSONObject> jsonObject = H5PGeneratorFactory.getContentJsonGenerator(exerciseData.getExerciseType())
	        		.modifyJsonContent(datas);
	    	byte[] h5pFile = ZipManager.generateModifiedZipFile(H5PConstantsManager.getResourceFolder(exerciseData.getExerciseType()), jsonObject.get(0).toString(), relevantResources);
	    	h5PFiles.put(exerciseData.getExerciseTitle(), h5pFile);
		}
		
    	return h5PFiles;
	}
	
	@Override
	protected HashMap<String, byte[]> generateSpecification(CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer,
			ArrayList<ExerciseData> data, ExerciseGenerationMetadata settings) {
		HashMap<String, byte[]> specifications = new HashMap<>();

		for(ExerciseData exerciseData : data) {
	        ArrayList<ExerciseData> datas = new ArrayList<>();
	        datas.add(exerciseData);
	        byte[] specification = SpecificationGeneratorFactory.getGenerator(exerciseData.getTopic()).generateJsonSpecification(parser, generator, lemmatizer, datas);
	    	specifications.put(exerciseData.getExerciseTitle(), specification);
		}
		
    	return specifications;
	}
	
	@Override
	public void cancelGeneration() {
		isCancelled = true;
		feedbackGenerator.cancelGeneration();
	}

}