package com.flair.server.exerciseGeneration.exerciseManagement;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.simple.JSONObject;

import com.flair.server.exerciseGeneration.ZipManager;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.BracketsGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.DistractorGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.DistractorGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.DistractorSelector;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.FeedbackGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.DocumentParsing.DocumentParser;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.H5PGeneration.H5PGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration.SimpleExerciseXmlGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration.XmlGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration.PreviewGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration.PreviewGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.NlpManager;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.OutputFormat;
import com.flair.shared.exerciseGeneration.Pair;

public class DocumentBasedExerciseGenerator extends ExerciseGenerator {
	
	private boolean isCancelled = false;

	@Override
	public ResultComponents generateExercise(ContentTypeSettings settings, CoreNlpParser parser, SimpleNlgParser generator, 
			OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {		
		ArrayList<ExerciseData> exerciseData = generateExerciseData(parser, generator, lemmatizer, resourceDownloader, settings);

		if(exerciseData == null || exerciseData.size() == 0) {
			return null;
		}
	        
        HashMap<String, byte[]> xmlFiles = new HashMap<>();
    	HashMap<String, byte[]> h5PFiles = new HashMap<>();
    	HashMap<String, String> previews = new HashMap<>();
    	
        if(settings.getExerciseSettings().getOutputFormats().contains(OutputFormat.FEEDBOOK_XML)) {
        	xmlFiles = generateFeedbookXml(exerciseData);
        }
		
        if (isCancelled) {
        	return null;
        }
        
        if(settings.getExerciseSettings().getOutputFormats().contains(OutputFormat.H5P)) {
    		h5PFiles = generateH5P(exerciseData, null);
        }

        return new ResultComponents(h5PFiles, previews, xmlFiles);
    }
	
	@Override
	public ArrayList<ExerciseData> generateExerciseData(CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, 
			ResourceDownloader resourceDownloader, ContentTypeSettings settings) {
		DocumentParser p = new DocumentParser();
        NlpManager nlpManager = new NlpManager(parser, generator, ((ExerciseSettings)settings.getExerciseSettings()).getPlainText(), lemmatizer);

		ExerciseData exerciseData = p.parseDocument(settings, nlpManager, ((ExerciseSettings)settings.getExerciseSettings()).isWebPage(), resourceDownloader);
		
		if (isCancelled) {
        	return null;
        }
		
        if(((ExerciseSettings)settings.getExerciseSettings()).getContentType().equals(ExerciseType.FIB)){
			BracketsGeneratorFactory.getGenerator(exerciseData.getTopic()).generateBrackets(nlpManager, exerciseData);
		}
		
        if (isCancelled) {
        	return null;
        }
        
		DistractorGenerator distractorGenerator = DistractorGeneratorFactory.getGenerator(exerciseData.getTopic(), ((ExerciseSettings)settings.getExerciseSettings()).getContentType());
		if(distractorGenerator != null) {
			boolean isValidExercise = distractorGenerator.generateDistractors(nlpManager, exerciseData);
			if(!isValidExercise) {
				return null;
			}
		}
		
		if (isCancelled) {
        	return null;
        }
		
		new FeedbackGenerator().generateFeedback(settings.getExerciseSettings(), nlpManager, exerciseData, 
				((ExerciseSettings)settings.getExerciseSettings()).getContentType(), exerciseData.getTopic());
		
		if (isCancelled) {
        	return null;
        }
		
		if(((ExerciseSettings)settings.getExerciseSettings()).getContentType() == ExerciseType.SINGLE_CHOICE) {
			DistractorSelector.chooseDistractors(exerciseData, ((ExerciseSettings)settings.getExerciseSettings()).getnDistractors());
		}
		
		if (isCancelled) {
        	return null;
        }
		
		new InstructionGenerator().generateInstructions(exerciseData, ((ExerciseSettings)settings.getExerciseSettings()).getContentType());
		
		if (isCancelled) {
        	return null;
        }
		
		exerciseData.setContentTypeSettings(settings);
		exerciseData.setExerciseTitle(((ExerciseSettings)settings.getExerciseSettings()).getTaskName());
		exerciseData.setExerciseType(((ExerciseSettings)settings.getExerciseSettings()).getContentType());
		
		ArrayList<ExerciseData> ret = new ArrayList<>();
		ret.add(exerciseData);
		return ret;
	}
		
	@Override
	protected HashMap<String, byte[]> generateFeedbookXml(ArrayList<ExerciseData> data) {
        HashMap<String, byte[]> xmlFiles = new HashMap<>();

		for(ExerciseData exerciseData : data) {
			SimpleExerciseXmlGenerator g = XmlGeneratorFactory.getXmlGenerator(exerciseData.getExerciseType());
	        byte[] file = g.generateXMLFile(exerciseData);	
	        xmlFiles.put(exerciseData.getExerciseTitle(), file);    
		}
        
        return xmlFiles;
	}
	
	@Override
	protected HashMap<String, byte[]> generateH5P(ArrayList<ExerciseData> data, ContentTypeSettings settings) {
		HashMap<String, byte[]> h5PFiles = new HashMap<>();

		for(ExerciseData exerciseData : data) {
			ArrayList<Pair<String, byte[]>> relevantResources = getRelevantResources(exerciseData.getContentTypeSettings().getResources());
	        ArrayList<ExerciseData> datas = new ArrayList<>();
	        datas.add(exerciseData);
	        ArrayList<JSONObject> jsonObject = H5PGeneratorFactory.getContentJsonGenerator(exerciseData.getExerciseType())
	        		.modifyJsonContent(exerciseData.getContentTypeSettings(), datas);
	    	byte[] h5pFile = ZipManager.generateModifiedZipFile(exerciseData.getContentTypeSettings().getResourceFolder(), jsonObject.toString(), relevantResources);
	    	h5PFiles.put(exerciseData.getExerciseTitle(), h5pFile);
		}
		
    	return h5PFiles;
	}
	
	@Override
	protected HashMap<String, String> generatePreview(ArrayList<ExerciseData> data) {
		HashMap<String, String> previews = new HashMap<>();

		for(ExerciseData exerciseData : data) {
			PreviewGenerator g = PreviewGeneratorFactory.getPreviewGenerator(exerciseData.getExerciseType());
	        String preview = g.generatePreview(exerciseData);	
	        previews.put(exerciseData.getExerciseTitle(), preview);    
		}
        
        return previews;
	}
	
	@Override
	public void cancelGeneration() {
		isCancelled = true;
	}

}