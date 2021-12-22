package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConditionalConstruction;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.TenseSettings;
import com.flair.shared.exerciseGeneration.BracketsProperties;
import com.flair.shared.exerciseGeneration.Construction;
import com.flair.shared.exerciseGeneration.DetailedConstruction;
import com.flair.shared.exerciseGeneration.DistractorProperties;
import com.flair.shared.exerciseGeneration.ExerciseType;
import com.flair.shared.exerciseGeneration.Pair;

import edu.stanford.nlp.ling.CoreLabel;

public class ConditionalDistractorsFromTextGenerator extends DistractorsFromTextGenerator {
	
	public ConditionalDistractorsFromTextGenerator(NlpManager nlpManager) {
		super(nlpManager);
	}
	
	@Override
	protected Pair<HashSet<String>, HashSet<String>> generateSCDistractors(Construction construction, ExerciseType exerciseType, 
			ArrayList<DistractorProperties> distractorProperties, String plainText) {
		TenseSettings ownClauseSettings = nlpManager.getConditionalClauseSpecifics(construction.getConstructionIndices());
        HashSet<String> options = new HashSet<>();

        if (distractorProperties.contains(DistractorProperties.WRONG_CLAUSE)) {
            TenseSettings otherClauseSettings = null;
        	if (((ConditionalConstruction)construction).getOtherClauseIndices() != null) {
                otherClauseSettings = nlpManager.getConditionalClauseSpecifics(((ConditionalConstruction)construction).getOtherClauseIndices());
            }
        	
            if(ownClauseSettings != null && otherClauseSettings != null) {
                options.add(nlpManager.generateCorrectForm(new TenseSettings(ownClauseSettings.getLemma(), ownClauseSettings.isInterrogative(),
                		ownClauseSettings.isNegated(), ownClauseSettings.isThirdSingular(), ownClauseSettings.getSubject(), otherClauseSettings.getTense(),
                		otherClauseSettings.isProgressive(), otherClauseSettings.isPerfect(), otherClauseSettings.getModal())));
            }
        }
        if (distractorProperties.contains(DistractorProperties.WRONG_CONDITIONAL) && ownClauseSettings != null) {
            if(((ConditionalConstruction)construction).isMainClause()) {
                if(construction.getConstruction() == DetailedConstruction.CONDREAL) {
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
                if(construction.getConstruction() == DetailedConstruction.CONDREAL) {
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
        
        return new Pair<>(options, new HashSet<>());
	}
	
}
