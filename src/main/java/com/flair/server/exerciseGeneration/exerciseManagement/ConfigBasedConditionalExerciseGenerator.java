package com.flair.server.exerciseGeneration.exerciseManagement;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.JSONObject;

import com.flair.server.exerciseGeneration.ZipManager;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.FeedbackGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing.ConditionalConfigParser;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.H5PGeneration.H5PGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration.SimpleExerciseXmlGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.feedbookXmlGeneration.XmlGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.NlpManager;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.ConfigExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.OutputFormat;
import com.flair.shared.exerciseGeneration.Pair;

public class ConfigBasedConditionalExerciseGenerator extends ExerciseGenerator {

	private boolean isCancelled = false;
	
	@Override
	public ResultComponents generateExercise(ContentTypeSettings settings,
		CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
		ArrayList<ExerciseData> exerciseData = generateExerciseData(parser, generator, lemmatizer, resourceDownloader, settings);
		
		if (isCancelled) {
        	return null;
        }
		
        FeedbackGenerator feedbackGenerator = new FeedbackGenerator();
        InstructionGenerator instructionGenerator = new InstructionGenerator();

    	for(ExerciseData exercise : exerciseData) { 
			exercise.setExerciseType(determineExerciseType(exercise.getExerciseTitle().charAt(exercise.getExerciseTitle().length() - 1) + ""));
			exercise.setContentTypeSettings(new ContentTypeSettings(exercise.getExerciseType()));
			exercise.setTopic(ExerciseTopic.CONDITIONALS);
			
	        NlpManager nlpManager = new NlpManager(parser, generator, exercise.getPlainText(), lemmatizer);

			feedbackGenerator.generateFeedback(settings.getExerciseSettings(), nlpManager, exercise, 
					exercise.getContentTypeSettings().getExerciseType(), exercise.getTopic());
			
			if (isCancelled) {
	        	return null;
	        }
			
			instructionGenerator.generateInstructions(exercise, exercise.getContentTypeSettings().getExerciseType());
			
			if (isCancelled) {
	        	return null;
	        }

    		// for underline exercises evtl. separate exercise per line
    		//TODO: all support texts?
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
        
        if (isCancelled) {
        	return null;
        }    
        
        return new ResultComponents(h5PFiles, previews, xmlFiles);
    }
	
	@Override
	public ArrayList<ExerciseData> generateExerciseData(CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, 
			ResourceDownloader resourceDownloader, ContentTypeSettings settings) {
		ConfigExerciseSettings exerciseSettings = (ConfigExerciseSettings)settings.getExerciseSettings();
		
		ConditionalConfigParser p = new ConditionalConfigParser();
		HashMap<String,ArrayList<HashMap<String,ExerciseData>>> exerciseData = p.parseConfigFile(new ByteArrayInputStream(exerciseSettings.getFileStream()));
	
		ArrayList<ExerciseData> exercises = new ArrayList<>();
		for(Entry<String, ArrayList<HashMap<String, ExerciseData>>> blocks : exerciseData.entrySet()) {
        	int i = 0;
        	for(HashMap<String, ExerciseData> block : blocks.getValue()) {
        		for(Entry<String, ExerciseData> entry : block.entrySet()) {
        			entry.getValue().setExerciseTitle(blocks.getKey() + "/" + i + "/" + entry.getKey());
        			exercises.add(entry.getValue());
            	}
        		i++;
        	}
    	}
		
		return exercises;
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
	protected HashMap<String, String> generatePreview(ArrayList<ExerciseData> data) {
		return new HashMap<>();        
	}
	
	@Override
	protected HashMap<String, byte[]> generateH5P(ArrayList<ExerciseData> data, ContentTypeSettings settings) {
		HashMap<String, byte[]> h5PFiles = new HashMap<>();

		for(ExerciseData exerciseData : data) {
			ArrayList<Pair<String, byte[]>> relevantResources = new ArrayList<>();
	        ArrayList<ExerciseData> datas = new ArrayList<>();
	        datas.add(exerciseData);
	        ArrayList<JSONObject> jsonObject = H5PGeneratorFactory.getContentJsonGenerator(exerciseData.getExerciseType())
	        		.modifyJsonContent(exerciseData.getContentTypeSettings(), datas);
	    	byte[] h5pFile = ZipManager.generateModifiedZipFile(exerciseData.getContentTypeSettings().getResourceFolder(), jsonObject.toString(), relevantResources);
	    	h5PFiles.put(exerciseData.getExerciseTitle(), h5pFile);
		}
    	
    	return h5PFiles;
	}
	
	private ExerciseType determineExerciseType(String exerciseType) {
		if(exerciseType.equals("0")) {
			return ExerciseType.MEMORY;
		} else if(exerciseType.equals("1") || exerciseType.equals("2")) {
			return ExerciseType.SINGLE_CHOICE;
		} else if(exerciseType.equals("3") || exerciseType.equals("4") || exerciseType.equals("5") || exerciseType.equals("9")) {
			return ExerciseType.FIB;
		} else if(exerciseType.equals("6")) {
			return ExerciseType.JUMBLED_SENTENCES;
		} else if(exerciseType.equals("7")) {
			return ExerciseType.CATEGORIZE;
		} else if(exerciseType.equals("8")) {
			return ExerciseType.MARK;
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	@Override
	public void cancelGeneration() {
		isCancelled = true;
	}
	
}