package com.flair.server.exerciseGeneration.exerciseManagement;

import java.util.ArrayList;
import java.util.HashMap;

import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.OutputComponents;
import com.flair.server.exerciseGeneration.InputEnrichment.BracketsGeneratorFactory;
import com.flair.server.exerciseGeneration.InputEnrichment.DistractorGenerator;
import com.flair.server.exerciseGeneration.InputEnrichment.DistractorGeneratorFactory;
import com.flair.server.exerciseGeneration.InputEnrichment.DistractorSelector;
import com.flair.server.exerciseGeneration.InputEnrichment.FeedbackGenerator;
import com.flair.server.exerciseGeneration.InputParsing.DocumentParsing.DocumentParser;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement.SimpleExerciseXmlGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement.XmlGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.InstructionsProperties;

public class DocumentBasedExerciseGenerator extends ExerciseGenerator {
	
	@Override
	public ArrayList<OutputComponents> generateExercise(ContentTypeSettings settings,
		CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
		
		ExerciseSettings exerciseSettings = (ExerciseSettings)settings.getExerciseSettings();
		
		DocumentParser p = new DocumentParser();
        NlpManager nlpManager = new NlpManager(parser, generator, exerciseSettings.getPlainText(), lemmatizer);

		ExerciseData exerciseData = p.parseDocument(settings, nlpManager, exerciseSettings.isWebPage(), resourceDownloader);
		
        if(((ExerciseSettings)settings.getExerciseSettings()).getContentType().equals(ExerciseType.FIB)){
			BracketsGeneratorFactory.getGenerator(exerciseData.getTopic()).generateBrackets(nlpManager, exerciseData);
		}
		
		DistractorGenerator distractorGenerator = DistractorGeneratorFactory.getGenerator(exerciseData.getTopic(), ((ExerciseSettings)settings.getExerciseSettings()).getContentType());
		if(distractorGenerator != null) {
			boolean isValidExercise = distractorGenerator.generateDistractors(nlpManager, exerciseData);
			if(!isValidExercise) {
				return null;
			}
		}
		new FeedbackGenerator().generateFeedback(settings.getExerciseSettings(), nlpManager, exerciseData, 
				((ExerciseSettings)settings.getExerciseSettings()).getContentType(), exerciseData.getTopic());
		
		if(exerciseSettings.getContentType() == ExerciseType.SINGLE_CHOICE) {
			DistractorSelector.chooseDistractors(exerciseData, exerciseSettings.getnDistractors());
		}

		String exerciseType = getExerciseType(exerciseSettings.getContentType(), exerciseSettings.getInstructions(),
				exerciseSettings.getBrackets());
		SimpleExerciseXmlGenerator g = XmlGeneratorFactory.getXmlGenerator(exerciseSettings.getContentType());
        byte[] file = g.generateXMLFile(exerciseData, exerciseType);	

    	HashMap<String, byte[]> hm = new HashMap<>();
    	hm.put(exerciseSettings.getTaskName(), file);    	
    	
    	OutputComponents output = new OutputComponents(null, null, null, null, null, null, null, "feedbookExercises", null);
    	output.setXmlFile(hm);
        ArrayList<OutputComponents> res = new ArrayList<>();
        res.add(output);
        
        return res;
    }
	
	@Override
	public void cancelGeneration() {}
	
	private String getExerciseType(ExerciseType exerciseType, ArrayList<InstructionsProperties> instructionProperties, 
			ArrayList<BracketsProperties> bracketsProperties) {
		if(exerciseType == ExerciseType.MEMORY) {
			return "0";
		} else if(exerciseType == ExerciseType.SINGLE_CHOICE) {
			// It doesn't matter for the instructions whether we have 1 or 3 distractors
			return "1";
		} else if(exerciseType == ExerciseType.FIB) {
			if(instructionProperties.contains(InstructionsProperties.LEMMA)) {
				return "5";
			} else if(bracketsProperties.contains(BracketsProperties.DISTRACTOR_LEMMA)) {
				return "4";
			} else {
				return "3";
			}
		} else if(exerciseType == ExerciseType.JUMBLED_SENTENCES) {
			return "6";
		} else if(exerciseType == ExerciseType.CATEGORIZE) {
			return "7";
		} else if(exerciseType == ExerciseType.MARK) {
			return "8";
		} else if(exerciseType == ExerciseType.DRAG_SINGLE) {
			return null;
		} else if(exerciseType == ExerciseType.DRAG_MULTI) {
			return null;
		} else {
			throw new IllegalArgumentException();
		}
	}
	

}