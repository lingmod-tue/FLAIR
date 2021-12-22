package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.ExerciseType;

public class PresentConstructionsFromTextExtractor extends ConstructionsFromTextExtractor {
	
	public PresentConstructionsFromTextExtractor(NlpManager nlpManager, ArrayList<Construction> constructions) {
		super(nlpManager);
		
		contains3SgForms = constructions.stream()
				.anyMatch((c) -> c.getConstruction().toString().endsWith("_3"));
		containsNon3SgForms = constructions.stream()
				.anyMatch((c) -> c.getConstruction().toString().endsWith("_NOT3"));
	}
	
	private final boolean contains3SgForms;
	private final boolean containsNon3SgForms;

	@Override
	protected void processConstructions(Construction construction, ArrayList<BracketsProperties> bracketsProperties,
			ExerciseType exerciseType) {
		// Check if the 3rd pers. is correct
    	Boolean isThirdPerson = nlpManager.isThirdSingular(construction.getConstructionIndices());
    	if(isThirdPerson == null) {
    		constructionsToRemove.add(construction);
    	} else {
    		boolean isLabelledThirdPerson = construction.getConstruction().toString().endsWith("_3");
    		if(isThirdPerson != isLabelledThirdPerson) {
    			// check if we actually want the construction
    			if(isThirdPerson && !contains3SgForms || !isThirdPerson && !containsNon3SgForms) {
    				constructionsToRemove.add(construction);
    			} else {
    				DetailedConstruction correctConstruction;
    				if(isThirdPerson) {
    					if(construction.getConstruction().toString().startsWith("QUEST")) {
    						if(construction.getConstruction().toString().contains("_NEG_")) {
    							correctConstruction = DetailedConstruction.QUEST_NEG_3;
    						} else {
    							correctConstruction = DetailedConstruction.QUEST_AFFIRM_3;
    						}
    					} else {
    						if(construction.getConstruction().toString().contains("_NEG_")) {
    							correctConstruction = DetailedConstruction.STMT_NEG_3;
    						} else {
    							correctConstruction = DetailedConstruction.STMT_AFFIRM_3;
    						}	
    					}
    				} else {
    					if(construction.getConstruction().toString().startsWith("QUEST")) {
    						if(construction.getConstruction().toString().contains("_NEG_")) {
    							correctConstruction = DetailedConstruction.QUEST_NEG_NOT3;
    						} else {
    							correctConstruction = DetailedConstruction.QUEST_AFFIRM_NOT3;
    						}
    					} else {
    						if(construction.getConstruction().toString().contains("_NEG_")) {
    							correctConstruction = DetailedConstruction.STMT_NEG_NOT3;
    						} else {
    							correctConstruction = DetailedConstruction.STMT_AFFIRM_NOT3;
    						}	
    					}
    				}
    				construction.setConstruction(correctConstruction);
    			} 
    		}
    	}
	}
	
}
