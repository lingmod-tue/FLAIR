package com.flair.server.exerciseGeneration.exerciseManagement;

import java.util.ArrayList;
import java.util.HashMap;

import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.DistractorSelector;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.DistractorGeneration.DistractorGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.DistractorGeneration.DistractorGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration.HintGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration.HintGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.InstructionGeneration.InstructionGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.ParenthesisGeneration.BracketsGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.InputEnrichment.ParenthesisGeneration.BracketsGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.DocumentParsing.DocumentParser;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration.PreviewGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.previewGeneration.PreviewGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.NlpManager;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.shared.exerciseGeneration.DocumentExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.OutputFormat;

public class DocumentBasedExerciseGenerator extends SimpleExerciseGenerator {
	
	public DocumentBasedExerciseGenerator(String topic) {
		super(topic);
	}
		
	@Override
	public ArrayList<ExerciseData> generateExerciseData(CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, 
			ResourceDownloader resourceDownloader, ExerciseGenerationMetadata settings) {
		DocumentParser p = new DocumentParser();
        NlpManager nlpManager = new NlpManager(parser, generator, ((DocumentExerciseSettings)settings.getExerciseSettings()).getPlainText(), lemmatizer);

		ExerciseData exerciseData = p.parseDocument(settings, nlpManager, ((DocumentExerciseSettings)settings.getExerciseSettings()).isWebPage(), 
				resourceDownloader, exerciseTopic);
		
		if (isCancelled) {
        	return null;
        }
		
		if(settings.getExerciseSettings().getOutputFormats().contains(OutputFormat.H5P) || 
				settings.getExerciseSettings().getOutputFormats().contains(OutputFormat.FEEDBOOK_XML)) {
	        if(!enrichWithHints(settings, nlpManager, exerciseData)) {
	        	return null;
	        }
		}
		
		if (isCancelled) {
        	return null;
        }
		
		ArrayList<ExerciseData> ret = new ArrayList<>();
		ret.add(exerciseData);
		return ret;
	}
	
	private boolean enrichWithHints(ExerciseGenerationMetadata settings, NlpManager nlpManager, ExerciseData exerciseData) {
		if(((DocumentExerciseSettings)settings.getExerciseSettings()).getContentType().equals(ExerciseType.FILL_IN_THE_BLANKS)){
        	BracketsGenerator bg = BracketsGeneratorFactory.getGenerator(exerciseData.getTopic());
        	if(bg != null) {
    			bg.generateBrackets(nlpManager, exerciseData);
        	}
		}
		
        if (isCancelled) {
        	return false;
        }
        
		DistractorGenerator distractorGenerator = DistractorGeneratorFactory.getGenerator(exerciseData.getTopic(), ((DocumentExerciseSettings)settings.getExerciseSettings()).getContentType());
		if(distractorGenerator != null) {
			boolean isValidExercise = distractorGenerator.generateDistractors(nlpManager, exerciseData);
			if(!isValidExercise) {
				return false;
			}
		}
		
		if (isCancelled) {
        	return false;
        }
		
		feedbackGenerator.generateFeedback(settings.getExerciseSettings(), nlpManager, exerciseData, 
				((DocumentExerciseSettings)settings.getExerciseSettings()).getContentType(), exerciseData.getTopic());
		
		if (isCancelled) {
        	return false;
        }
		
		if(((DocumentExerciseSettings)settings.getExerciseSettings()).getContentType().equals(ExerciseType.SINGLE_CHOICE)) {
			DistractorSelector.chooseDistractors(exerciseData, ((DocumentExerciseSettings)settings.getExerciseSettings()).getnDistractors());
		}
		
		if (isCancelled) {
        	return false;
        }
		
		InstructionGeneratorFactory.getGenerator(exerciseData.getTopic(), exerciseData.getExerciseType()).generateInstructions(exerciseData);
		HintGenerator hintGenerator = HintGeneratorFactory.getGenerator(exerciseData.getTopic());
		if(hintGenerator != null) {
			hintGenerator.generateHints(exerciseData);
		}
		
		return true;
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

}