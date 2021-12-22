package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.SentenceAnnotations;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DataStructures.ExerciseComponents;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.DataStructures.TargetConstruction;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.ExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseType;

public abstract class ConstructionsFromTextExtractor extends ConstructionsExtractor {

	public ConstructionsFromTextExtractor(NlpManager nlpManager) {
		this.nlpManager = nlpManager;
	}
	
    protected NlpManager nlpManager;
	protected ArrayList<Construction> constructionsToAdd = new ArrayList<>();
    protected ArrayList<Construction> constructionsToRemove = new ArrayList<>();
    
    /**
     * Keeps track of the sentences already processed.
     * Necessary for Jumbled Sentences.
     */
    protected int sentenceIndex = 0;
    private ArrayList<SentenceAnnotations> coveredSentences = new ArrayList<>();
    
	/**
	 * Modifies construction indices in-place if necessary.
	 */
    @Override
    public ExerciseComponents prepareConstructions(ExerciseSettings exerciseSettings) {    
    	ArrayList<Construction> constructions = exerciseSettings.getConstructions();
		ArrayList<BracketsProperties> bracketsProperties = exerciseSettings.getBrackets();
		ExerciseType exrerciseType = exerciseSettings.getContentType();
    	
        for(Construction construction : constructions) { 
        	processConstructions(construction, bracketsProperties, exrerciseType);
        	
        	if(exrerciseType == ExerciseType.JUMBLED_SENTENCES) {
        		extractJumbledSentencesConstructions(construction);
            } else if(exrerciseType == ExerciseType.MARK) {
        		extractMtWConstructions(construction);
            } else if(exrerciseType == ExerciseType.DRAG_SINGLE) {
        		extractDDSingleConstructions(construction, bracketsProperties);
            } else if(exrerciseType == ExerciseType.DRAG_MULTI) {
        		extractDDMultiConstructions(construction, bracketsProperties);
            } else if(exrerciseType == ExerciseType.FIB) {
        		extractFiBConstructions(construction, bracketsProperties);
            } else if(exrerciseType == ExerciseType.SINGLE_CHOICE) {
        		extractSCConstructions(construction);
            }
        	
        	/*
        	//TODO: use the correct constructionExtractor that's saved in the settings
            if(construction.getConstruction().toString().startsWith("COND")) {
            	new ConditionalConstructionExtractor().extractConstructions(construction);
            } else if(construction.getConstruction().toString().startsWith("ADJ") ||
                        construction.getConstruction().toString().startsWith("ADV")) {
            	new ComparisonConstrucitonExtractor().extractConstructions(construction);
            } else if(construction.getConstruction().toString().startsWith("PASSIVE") ||
                    construction.getConstruction().toString().startsWith("ACTIVE")) {
            	new PassiveConstructionExtractor().extractConstructions(construction);
            } else if(construction.getConstruction().toString().startsWith("QUEST") || 
            		construction.getConstruction().toString().startsWith("STMT")){
            	new PresentConstructionExtractor().extractConstructions(construction);
            } else if((construction.getConstruction().toString().startsWith("PAST") || 
            		construction.getConstruction().toString().startsWith("PRES"))) {                	
            	new PastConstructionExtractor().extractConstructions(construction);
        	} else if((construction.getConstruction() == DetailedConstruction.WHICH ||
            		construction.getConstruction() == DetailedConstruction.WHO ||
            		construction.getConstruction() == DetailedConstruction.THAT ||
            		construction.getConstruction() == DetailedConstruction.OTHERPRN)) {
            	new RelativeConstructionExtractor().extractConstructions(construction);
            }
            */
        }

        for(Construction construction : constructionsToRemove) {
            constructions.remove(construction);
        }

        for(Construction construction : constructionsToAdd) {
        	constructions.add(construction);
        }
                
        // Remove overlapping constructions (can occur e.g. with synthetic and analytic comparatives)
        HashSet<Construction> activeConstructionsToRemove = new HashSet<>();
        for(Construction construction : constructions) {
        	for(Construction otherConstruction : constructions) {
        		if(construction != otherConstruction && (Math.max(construction.getConstructionIndices().first, otherConstruction.getConstructionIndices().first) < 
						Math.min(construction.getConstructionIndices().second, otherConstruction.getConstructionIndices().second))) {
        					if(construction.getConstructionIndices().second - construction.getConstructionIndices().first > 
        							otherConstruction.getConstructionIndices().second - otherConstruction.getConstructionIndices().first) {
        						activeConstructionsToRemove.add(otherConstruction);
        					} else {
        						activeConstructionsToRemove.add(construction);
        					}
        		}

        	}
        }
    
        for(Construction construction : activeConstructionsToRemove) {
        	constructions.remove(construction);
        }
        
        HashMap<Integer, TargetConstruction> targetConstructions = new HashMap<>();
        //TODO: we only set a temporary index here, we don't know the position of the construction between plain text
        // and HTML elements yet
        int index = 0;
        for(Construction construction : constructions) {
        	TargetConstruction targetConstruction = new TargetConstruction(construction.getConstructionText(),
        			construction.getConstructionIndices().first, construction.getConstructionIndices().second,
        			construction.getConstruction());
        	targetConstructions.put(index++, targetConstruction);
        }
        
        ExerciseComponents exerciseComponents = new ExerciseComponents(targetConstructions);
        
        return exerciseComponents;
    }
    
    /**
     * Adds the constructions in the same sentence to the settings for further processing.
     * @param newConstructions	The settings of the constructions in the same sentence
     */
    protected void addConstructionsInSentence(ArrayList<Construction> newConstructions) {
    	for(Construction newConstruction : newConstructions) {
    		for(Construction otherConstruction : newConstructions) {
        		if(newConstruction != otherConstruction) {
        			newConstruction.getSentenceConstructions().add(otherConstruction);
        		}
    		}
    	}
    }
    
    protected void processConstructions(Construction construction, ArrayList<BracketsProperties> bracketsProperties,
			ExerciseType exerciseType) {}

    protected void extractJumbledSentencesConstructions(Construction construction) {
		SentenceAnnotations sent = nlpManager.getRelevantSentence(construction.getConstructionIndices());
        if(sent != null && !coveredSentences.contains(sent)) {
        	coveredSentences.add(sent);
        	
        	ArrayList<Construction> sentenceParts = nlpManager.getSentencesParts(sent);
        	if(sentenceParts.size() > 1) {
            	sentenceIndex++;
            	for(Construction sentencePart : sentenceParts) {
            		sentencePart.setSentenceIndex(sentenceIndex);
            	}
            	constructionsToAdd.addAll(sentenceParts);
        	}
        } 
        
        constructionsToRemove.add(construction);
	}
    
    protected void extractMtWConstructions(Construction construction) {}
    protected void extractDDSingleConstructions(Construction construction, ArrayList<BracketsProperties> bracketsProperties) {}
    protected void extractDDMultiConstructions(Construction construction, ArrayList<BracketsProperties> bracketsProperties) {}
    protected void extractFiBConstructions(Construction construction, ArrayList<BracketsProperties> bracketsProperties) {}
    protected void extractSCConstructions(Construction construction) {}

}
