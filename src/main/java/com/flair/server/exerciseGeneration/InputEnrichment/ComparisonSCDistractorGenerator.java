package com.flair.server.exerciseGeneration.InputEnrichment;

import java.util.ArrayList;
import java.util.HashSet;

import com.flair.server.exerciseGeneration.ConstructionTextPart;
import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.ComparisonSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.shared.exerciseGeneration.DistractorProperties;
import com.flair.shared.exerciseGeneration.Pair;

public class ComparisonSCDistractorGenerator extends SCDistractorGenerator {
	
	@Override
	protected void generateDistractorValues(NlpManager nlpManager, ConstructionTextPart construction, HashSet<String> options, 
			HashSet<String> incorrectFormOptions, ExerciseData data) {
    	boolean isComparative = construction.getConstructionType().toString().contains("_COMP_");
        boolean isSynthetic = construction.getConstructionType().toString().endsWith("SYN");
        boolean isAdjective = construction.getConstructionType().toString().startsWith("ADJ");
        String lemma = nlpManager.getLemmaOfComparison(construction.getIndicesInPlainText());

        ArrayList<Pair<Boolean, Boolean>> parameterConstellations = new ArrayList<>();
        parameterConstellations.add(new Pair<>(isSynthetic, isComparative));

        if(data.getDistractorProperties().contains(DistractorProperties.OTHER_FORM)) {
        	ArrayList<Pair<Boolean, Boolean>> newParameterConstellations = new ArrayList<>();
            for(Pair<Boolean, Boolean> parameterConstellation : parameterConstellations) {
                newParameterConstellations.add(new Pair<>(parameterConstellation.first, !isComparative));
            }
            parameterConstellations.addAll(newParameterConstellations);
        }
        if(data.getDistractorProperties().contains(DistractorProperties.OTHER_VARIANT)) {
        	ArrayList<Pair<Boolean, Boolean>> newParameterConstellations = new ArrayList<>();
            for(Pair<Boolean, Boolean> parameterConstellation : parameterConstellations) {
                newParameterConstellations.add(new Pair<>(!isSynthetic, parameterConstellation.second));
            }
            parameterConstellations.addAll(newParameterConstellations);
        }

        for(int j = 0; j < parameterConstellations.size(); j++) {
            Pair<Boolean, Boolean> parameterConstellation = parameterConstellations.get(j);
            if(j != 0) {    // don't calculate the correct form, we already have it
                options.add(nlpManager.generateCorrectForm(new ComparisonSettings(parameterConstellation.first,
                        parameterConstellation.second, lemma, isAdjective)));
            }
            if(data.getDistractorProperties().contains(DistractorProperties.INCORRECT_FORMS)) {
        		String constructionText = data.getPlainText().substring(construction.getIndicesInPlainText().first, construction.getIndicesInPlainText().second);
                incorrectFormOptions.addAll(nlpManager.generateIncorrectForms(new ComparisonSettings(parameterConstellation.first,
                        parameterConstellation.second, lemma, constructionText)));
            }
        }
	}
	
}
