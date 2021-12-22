package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ConstructionPreparation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ComparisonSettings;
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

public class ComparisonDistractorsFromTextGenerator extends DistractorsFromTextGenerator {
	
	public ComparisonDistractorsFromTextGenerator(NlpManager nlpManager) {
		super(nlpManager);
	}
	
	@Override
	protected Pair<HashSet<String>, HashSet<String>> generateSCDistractors(Construction construction, ExerciseType exerciseType, 
			ArrayList<DistractorProperties> distractorProperties, String plainText) {
        HashSet<String> options = new HashSet<>();
        HashSet<String> incorrectFormOptions = new HashSet<>();

        boolean isComparative = construction.getConstruction().toString().contains("_COMP_");
        boolean isSynthetic = construction.getConstruction().toString().endsWith("SYN");
        boolean isAdjective = construction.getConstruction().toString().startsWith("ADJ");
        String lemma = nlpManager.getLemmaOfComparison(construction.getConstructionIndices());

        ArrayList<Pair<Boolean, Boolean>> parameterConstellations = new ArrayList<>();
        parameterConstellations.add(new Pair<>(isSynthetic, isComparative));

        if(distractorProperties.contains(DistractorProperties.OTHER_FORM)) {
        	ArrayList<Pair<Boolean, Boolean>> newParameterConstellations = new ArrayList<>();
            for(Pair<Boolean, Boolean> parameterConstellation : parameterConstellations) {
                newParameterConstellations.add(new Pair<>(parameterConstellation.first, !isComparative));
            }
            parameterConstellations.addAll(newParameterConstellations);
        }
        if(distractorProperties.contains(DistractorProperties.OTHER_VARIANT)) {
        	ArrayList<Pair<Boolean, Boolean>> newParameterConstellations = new ArrayList<>();
            for(Pair<Boolean, Boolean> parameterConstellation : parameterConstellations) {
                newParameterConstellations.add(new Pair<>(!isSynthetic, parameterConstellation.second));
            }
            parameterConstellations.addAll(newParameterConstellations);
        }

        for(int i = 0; i < parameterConstellations.size(); i++) {
            Pair<Boolean, Boolean> parameterConstellation = parameterConstellations.get(i);
            if(i != 0) {    // don't calculate the correct form, we already have it
                options.add(nlpManager.generateCorrectForm(new ComparisonSettings(parameterConstellation.first,
                        parameterConstellation.second, lemma, isAdjective)));
            }
            if(distractorProperties.contains(DistractorProperties.INCORRECT_FORMS)) {
            	String constructionText = plainText.substring(construction.getConstructionIndices().first, construction.getConstructionIndices().second);

                incorrectFormOptions.addAll(nlpManager.generateIncorrectForms(new ComparisonSettings(parameterConstellation.first,
                        parameterConstellation.second, lemma, constructionText)));
            }
        }
        
        return new Pair<>(options, incorrectFormOptions);
	}
	
}
