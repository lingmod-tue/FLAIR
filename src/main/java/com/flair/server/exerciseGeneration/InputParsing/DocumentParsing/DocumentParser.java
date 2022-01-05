package com.flair.server.exerciseGeneration.InputParsing.DocumentParsing;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.ConstructionProperties;
import com.flair.server.exerciseGeneration.ConstructionTextPart;
import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.PlainTextPart;
import com.flair.server.exerciseGeneration.TextPart;
import com.flair.server.exerciseGeneration.InputParsing.DocumentParsing.WebpageParsing.WebpageParser;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConditionalConstruction;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.SentenceAnnotations;
import com.flair.server.exerciseGeneration.util.ExerciseTopic;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.Pair;

public class DocumentParser {

	public ExerciseData parseDocument(ContentTypeSettings settings, NlpManager nlpManager, boolean isWebpage, ResourceDownloader resourceDownloader) {
		ConstructionPreparer.prepareConstructions((ExerciseSettings)settings.getExerciseSettings(), nlpManager);	

		ExerciseData exerciseData;
		if(isWebpage) {
			exerciseData = new WebpageParser().parseWebpage(settings, nlpManager, resourceDownloader);
		} else {
			// split sentences
			String plainText = ((ExerciseSettings)settings.getExerciseSettings()).getPlainText();
			ArrayList<String> sentences = new ArrayList<>();
			ArrayList<Pair<Integer, Integer>> sentenceIndices = new ArrayList<>();
			int lastStartIndex = 0;
			for(SentenceAnnotations sent : nlpManager.getSentences()) {
				if(sent.getTokens().size() > 0) {
					int sentenceStartIndex = sent.getTokens().get(0).beginPosition();
					if(sentenceStartIndex > lastStartIndex) {
						sentences.add(((ExerciseSettings)settings.getExerciseSettings()).getPlainText().substring(lastStartIndex, sentenceStartIndex));
						sentenceIndices.add(new Pair<>(lastStartIndex, sentenceStartIndex));
						lastStartIndex = sentenceStartIndex;
					}
				}
			}
			if(lastStartIndex < plainText.length()) {
				sentenceIndices.add(new Pair<>(lastStartIndex, plainText.length()));
			}
			
			ArrayList<TextPart> parts = new ArrayList<>();
			int sentenceId = 1;
			int plainTextIndex = 0;
			ArrayList<Construction> constructions = ((ExerciseSettings)settings.getExerciseSettings()).getConstructions();
			for(Pair<Integer, Integer> sentence : sentenceIndices) {	    
				while(constructions.size() > 0 && constructions.get(0).getConstructionIndices().first < sentence.second) {
					Construction construction = constructions.get(0);
					if(construction.getConstructionIndices().first > plainTextIndex) {
	    	            parts.add(new PlainTextPart(plainText.substring(plainTextIndex, construction.getConstructionIndices().first), sentenceId));
					}
    	            ConstructionTextPart c = new ConstructionTextPart(plainText.substring(construction.getConstructionIndices().first, construction.getConstructionIndices().second), sentenceId);
    	            c.setIndicesInPlainText(construction.getConstructionIndices());
    	            c.setConstructionType(construction.getConstruction());

    	            if(construction instanceof ConditionalConstruction) {
    	            	c.setIndicesRelatedConstruction(((ConditionalConstruction)construction).getOtherClauseIndices());
    	            	
    	            	if(((ConditionalConstruction)construction).isMainClause()) {
        	            	c.getConstructionProperties().add(ConstructionProperties.CONDITIONAL_MAIN_CLAUSE);
        	            }
    	            }
    	            
    	            parts.add(c);
    	            
    	            constructions.remove(0);
    	            plainTextIndex = c.getIndicesInPlainText().second;
				}
				if(plainTextIndex < sentence.second) {
    	            parts.add(new PlainTextPart(plainText.substring(plainTextIndex, sentence.second), sentenceId));
				}
				
	            sentenceId++;
			}
			
			exerciseData = new ExerciseData(parts);
		}
		
		ExerciseTopic topic = getExerciseTopic(((ExerciseSettings)settings.getExerciseSettings()).getTopic());
		if(topic == null) {
			return null;
		} 
		exerciseData.setTopic(topic);
		exerciseData.setBracketsProperties(((ExerciseSettings)settings.getExerciseSettings()).getBrackets());
		exerciseData.setDistractorProperties(((ExerciseSettings)settings.getExerciseSettings()).getDistractors());

		exerciseData.setPlainText(((ExerciseSettings)settings.getExerciseSettings()).getPlainText());
		
		return exerciseData;
	}
	
	/**
	 * Determines the exercise topic from the DetailedConstructions
	 * @param data	The exercise data
	 * @return		The exercise topic
	 */
	private ExerciseTopic getExerciseTopic(String topic) {		
		if(topic.equals("'if'")) {
			return ExerciseTopic.CONDITIONALS;
		} else if(topic.equals("Compare")) {
			return ExerciseTopic.COMPARISON;
		} else if(topic.equals("Passive")) {
			return ExerciseTopic.PASSIVE;
		} else if(topic.equals("Present")) {
        	return ExerciseTopic.PRESENT;
		} else if(topic.equals("Past")) {
			return ExerciseTopic.PAST;
		} else if(topic.equals("Relatives")) {
			return ExerciseTopic.RELATIVES;
		} else {
			throw new IllegalArgumentException();
		}
	}
}
