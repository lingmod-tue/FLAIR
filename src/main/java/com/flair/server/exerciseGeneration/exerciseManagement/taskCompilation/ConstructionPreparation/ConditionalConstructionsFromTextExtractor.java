package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;
import java.util.Random;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConditionalConstruction;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.Pair;

import edu.stanford.nlp.ling.CoreLabel;

public class ConditionalConstructionsFromTextExtractor extends ConstructionsFromTextExtractor {
	
	public ConditionalConstructionsFromTextExtractor(NlpManager nlpManager) {
		super(nlpManager);
	}
	
    private ArrayList<ConditionalConstruction> newConstructions = new ArrayList<>();
    private Pair<Integer, Integer> mainClauseConstructionIndices = null;
    private Pair<Integer, Integer> ifClauseConstructionIndices = null;

	@Override
	protected void processConstructions(Construction construction, ArrayList<BracketsProperties> bracketsProperties,
			ExerciseType exerciseType) {
		newConstructions = new ArrayList<>();
		mainClauseConstructionIndices = null;
		ifClauseConstructionIndices = null;
				
		Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> clauses = 
				nlpManager.getConditionalClauses(construction.getConstructionIndices());
        if(clauses != null) {                	
            int r = 0;
            if (bracketsProperties.contains(BracketsProperties.EITHER_CLAUSE)) {
                r = new Random().nextInt(2) + 1;
            }
            
            if (clauses.first != null && (exerciseType.equals(ExerciseType.SINGLE_CHOICE) || 
            		bracketsProperties.contains(BracketsProperties.MAIN_CLAUSE) || r == 1)) {
                mainClauseConstructionIndices = nlpManager.extractVerbCluster(clauses.first);                        
            }
            if (clauses.second != null && (exerciseType.equals(ExerciseType.SINGLE_CHOICE) || 
            		bracketsProperties.contains(BracketsProperties.IF_CLAUSE) || r == 2)) {
                ifClauseConstructionIndices = nlpManager.extractVerbCluster(clauses.second);                        
            }
            
            if(mainClauseConstructionIndices != null) {
                newConstructions.add(new ConditionalConstruction(construction.getConstruction(), mainClauseConstructionIndices, true, ifClauseConstructionIndices));
            }
            if(ifClauseConstructionIndices != null) {
                newConstructions.add(new ConditionalConstruction(construction.getConstruction(), ifClauseConstructionIndices, false, mainClauseConstructionIndices));
            }
            
            for(Construction newConstruction : newConstructions) {
            	constructionsToAdd.add(newConstruction);
            }
        }
            
        constructionsToRemove.add(construction);
	}

	@Override
	protected void extractDDSingleConstructions(Construction construction, ArrayList<BracketsProperties> bracketsProperties) {
        for(ConditionalConstruction newConstruction : newConstructions) {                    	
        	// try to limit the construction to max. 30 characters
        	if(newConstruction.getConstructionIndices().second - newConstruction.getConstructionIndices().first > 30) {
        		if(newConstruction.isMainClause()) {
        			if(mainClauseConstructionIndices != null) {
        				newConstruction.setConstructionIndices(mainClauseConstructionIndices);  
        			}
        		} else {
        			if(ifClauseConstructionIndices != null) {
        				newConstruction.setConstructionIndices(ifClauseConstructionIndices);
        			}
        		}
        			
    			if(newConstruction.getConstructionIndices().second - newConstruction.getConstructionIndices().first > 30) {
					CoreLabel mainVerb = nlpManager.getMainVerb(newConstruction.getConstructionIndices());
					if(mainVerb != null) {
						newConstruction.setConstructionIndices(new Pair<>(mainVerb.beginPosition(), mainVerb.endPosition()));
					}
				}
        	}
        }
	}

	@Override
	protected void extractDDMultiConstructions(Construction construction, ArrayList<BracketsProperties> bracketsProperties) {
		if(newConstructions.size() < 2) {
        	// we can't use it for a multi-exercise Drag & Drop task if we don't have both clauses
			for(Construction newConstruction : newConstructions) {
				constructionsToAdd.remove(newConstruction);
			}
    	} else {
    		addConstructionsInSentence(new ArrayList<Construction>(newConstructions));                       	
        }
	}
	
}
