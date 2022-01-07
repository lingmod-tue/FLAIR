package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.DocumentParsing;

import java.util.ArrayList;
import java.util.Collections;

import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseTopic;
import com.flair.server.exerciseGeneration.exerciseManagement.PlainTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.DocumentParsing.WebpageParsing.WebpageParser;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.ConditionalConstruction;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement.SentenceAnnotations;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
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
			
			ArrayList<Pair<Integer, Integer>> removedParts = new ArrayList<>(((ExerciseSettings)settings.getExerciseSettings()).getRemovedParts());
			Collections.sort(removedParts, (i1, i2) -> i1.first < i2.first ? -1 : 1);			
			
			ArrayList<TextPart> parts = new ArrayList<>();
			int sentenceId = 1;
			int plainTextIndex = 0;
			ArrayList<Construction> constructions = ((ExerciseSettings)settings.getExerciseSettings()).getConstructions();
			for(Pair<Integer, Integer> sentence : sentenceIndices) {	    
				while(constructions.size() > 0 && constructions.get(0).getConstructionIndices().first < sentence.second) {
					Construction construction = constructions.get(0);
					
					int currentPlainTextIndex = plainTextIndex;
					while(construction.getConstructionIndices().first > currentPlainTextIndex) {
						if(removedParts.size() == 0 || removedParts.get(0).first >= construction.getConstructionIndices().first) {
							// no removed parts
		    	            parts.add(new PlainTextPart(plainText.substring(currentPlainTextIndex, construction.getConstructionIndices().first), sentenceId));
						} else {
							if(removedParts.get(0).first > currentPlainTextIndex) {
								// there is a part that we want to keep before the removed part
			    	            parts.add(new PlainTextPart(plainText.substring(currentPlainTextIndex, removedParts.get(0).first), sentenceId));
							}
							if(removedParts.get(0).second <= construction.getConstructionIndices().first) {
								removedParts.remove(0);
								currentPlainTextIndex += removedParts.get(0).second;
							} else {
								currentPlainTextIndex = construction.getConstructionIndices().first;
							}
						}						
					}
					
					if(removedParts.size() == 0 || removedParts.get(0).first >= construction.getConstructionIndices().second) {
						// no removed parts
						ConstructionTextPart c = new ConstructionTextPart(plainText.substring(construction.getConstructionIndices().first, construction.getConstructionIndices().second), sentenceId);
	    	            c.setIndicesInPlainText(construction.getConstructionIndices());
	    	            c.setConstructionType(construction.getConstruction());

	    	            if(construction instanceof ConditionalConstruction) {
	    	            	c.setIndicesRelatedConstruction(((ConditionalConstruction)construction).getOtherClauseIndices());
	    	            	
	    	            	if(((ConditionalConstruction)construction).isMainClause()) {
	    	            		if(c.getConstructionType() == DetailedConstruction.CONDREAL) {
	    		            		c.setConstructionType(DetailedConstruction.CONDREAL_MAIN);
	    	            		} else {
	    		            		c.setConstructionType(DetailedConstruction.CONDUNREAL_MAIN);
	    	            		}
	    	            	} else {
	    	            		if(c.getConstructionType() == DetailedConstruction.CONDREAL) {
	    		            		c.setConstructionType(DetailedConstruction.CONDREAL_IF);
	    	            		} else {
	    		            		c.setConstructionType(DetailedConstruction.CONDUNREAL_IF);
	    	            		}
	    	            	}
	    	            }
	    	            
	    	            parts.add(c);
					} else {
						int currentConstructionStartIndex = construction.getConstructionIndices().first;
						while(currentConstructionStartIndex < construction.getConstructionIndices().second) {
							if(removedParts.size() == 0 || removedParts.get(0).first >= construction.getConstructionIndices().second) {
			    	            parts.add(new PlainTextPart(plainText.substring(currentConstructionStartIndex, construction.getConstructionIndices().second), sentenceId));
			    	            currentConstructionStartIndex = construction.getConstructionIndices().second;
							} else {
								// there is a removed part in the construction, so we cannot use it as a target
								if(removedParts.get(0).first > currentConstructionStartIndex) {
									// there is a part that we want to keep before the removed part
				    	            parts.add(new PlainTextPart(plainText.substring(construction.getConstructionIndices().first, removedParts.get(0).first), sentenceId));
								}
								if(removedParts.get(0).second <= construction.getConstructionIndices().second) {
									removedParts.remove(0);
									currentConstructionStartIndex = removedParts.get(0).second;
								} else {
									currentConstructionStartIndex = construction.getConstructionIndices().second;
								}
							}
						}
					}						
    	            
    	            constructions.remove(0);
    	            plainTextIndex = construction.getConstructionIndices().second;
				}
				if(plainTextIndex < sentence.second) {
					int currentPlainTextIndex = plainTextIndex;
					while(sentence.second > currentPlainTextIndex) {
						if(removedParts.size() == 0 || removedParts.get(0).first >= sentence.second) {
							// no removed parts
		    	            parts.add(new PlainTextPart(plainText.substring(currentPlainTextIndex, sentence.second), sentenceId));
						} else {
							if(removedParts.get(0).first > currentPlainTextIndex) {
								// there is a part that we want to keep before the removed part
			    	            parts.add(new PlainTextPart(plainText.substring(currentPlainTextIndex, removedParts.get(0).first), sentenceId));
							}
							if(removedParts.get(0).second <= sentence.second) {
								removedParts.remove(0);
								currentPlainTextIndex += removedParts.get(0).second;
							} else {
								currentPlainTextIndex = sentence.second;
							}
						}						
					}					
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
		exerciseData.setInstructionProperties(((ExerciseSettings)settings.getExerciseSettings()).getInstructions());

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
