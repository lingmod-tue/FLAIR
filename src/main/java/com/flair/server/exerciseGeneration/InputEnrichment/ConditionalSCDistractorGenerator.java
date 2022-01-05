package com.flair.server.exerciseGeneration.InputEnrichment;

import java.util.HashSet;

import com.flair.server.exerciseGeneration.ConstructionProperties;
import com.flair.server.exerciseGeneration.ConstructionTextPart;
import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.TenseSettings;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.DistractorProperties;

public class ConditionalSCDistractorGenerator extends SCDistractorGenerator {
	
	@Override
	protected void generateDistractorValues(NlpManager nlpManager, ConstructionTextPart construction, HashSet<String> options, 
			HashSet<String> incorrectFormOptions, ExerciseData data) {
        TenseSettings ownClauseSettings = nlpManager.getConditionalClauseSpecifics(construction.getIndicesInPlainText());
        
        if (data.getDistractorProperties().contains(DistractorProperties.WRONG_CLAUSE)) {
            TenseSettings otherClauseSettings = null;
        	if (construction.getIndicesRelatedConstruction() != null) {
                otherClauseSettings = nlpManager.getConditionalClauseSpecifics(construction.getIndicesRelatedConstruction());
            }
        	
            if(ownClauseSettings != null && otherClauseSettings != null) {
                options.add(nlpManager.generateCorrectForm(new TenseSettings(ownClauseSettings.getLemma(), ownClauseSettings.isInterrogative(),
                		ownClauseSettings.isNegated(), ownClauseSettings.isThirdSingular(), ownClauseSettings.getSubject(), otherClauseSettings.getTense(),
                		otherClauseSettings.isProgressive(), otherClauseSettings.isPerfect(), otherClauseSettings.getModal())));
            }
        }
        if (data.getDistractorProperties().contains(DistractorProperties.WRONG_CONDITIONAL) && ownClauseSettings != null) {
            if(construction.getConstructionProperties().contains(ConstructionProperties.CONDITIONAL_MAIN_CLAUSE)) {
                if(construction.getConstructionType() == DetailedConstruction.CONDREAL) {
                    if (ownClauseSettings.getModal() != null) {
                    	ownClauseSettings.setModal(nlpManager.getUnrealModal(ownClauseSettings.getModal()));
                    } else {
                    	ownClauseSettings.setModal("would");
                    	ownClauseSettings.setPerfect(!ownClauseSettings.getTense().equals("present"));
                    }
                } else {
                    if(ownClauseSettings.getModal() != null) {
                    	ownClauseSettings.setModal(nlpManager.getRealModal(ownClauseSettings.getModal()));
                    } else {
                    	ownClauseSettings.setPerfect(false);
                        if(!ownClauseSettings.getTense().equals("past")) {
                        	ownClauseSettings.setModal("will");
                        }
                    }
                }

                options.add(nlpManager.generateCorrectForm(ownClauseSettings));
            } else {
                if(construction.getConstructionType() == DetailedConstruction.CONDREAL) {
                    if (ownClauseSettings.getTense().equals("past")) {
                    	ownClauseSettings.setPerfect(true);
                    } else {
                    	ownClauseSettings.setTense("past");
                    	ownClauseSettings.setPerfect(false);
                    }
                } else {
                	ownClauseSettings.setPerfect(false);
                    if(!(ownClauseSettings.getTense().equals("past") && ownClauseSettings.isPerfect())) {
                    	ownClauseSettings.setTense("present");
                    }
                }

                options.add(nlpManager.generateCorrectForm(ownClauseSettings));
            }
        }
	}

}
