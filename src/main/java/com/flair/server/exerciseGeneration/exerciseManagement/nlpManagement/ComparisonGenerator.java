package com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement;

import java.util.HashSet;

import com.flair.server.parser.SimpleNlgParser;

import simplenlg.features.Feature;
import simplenlg.framework.LexicalCategory;
import simplenlg.framework.NLGElement;

public class ComparisonGenerator extends FormGenerator {

    public ComparisonGenerator(SimpleNlgParser generator) {
        super(generator);
    }

    @Override
    public String generateCorrectForm(ParameterSettings settings) {
        if(((ComparisonSettings)settings).isSynthetic()) {
            NLGElement p = generator.nlgFactory().createInflectedWord(
                    ((ComparisonSettings)settings).getLemma(), ((ComparisonSettings)settings).isAdjective() ? LexicalCategory.ADJECTIVE : LexicalCategory.ADVERB);
            p.setFeature(Feature.IS_COMPARATIVE, ((ComparisonSettings)settings).isComparative());
            p.setFeature(Feature.IS_SUPERLATIVE, !((ComparisonSettings)settings).isComparative());
            return generator.realiser().realise(p).toString();
        } else {
            return (((ComparisonSettings)settings).isComparative() ? "more " : "most ") + ((ComparisonSettings)settings).getLemma();
        }
    }

    @Override
    public HashSet<String> generateIncorrectForms(ParameterSettings settings) {
        HashSet<String> incorrectForms = new HashSet<>();
        if(((ComparisonSettings)settings).isSynthetic()) {
            String defaultEnding = ((ComparisonSettings)settings).isComparative() ? "er" : "est";
            if(!((ComparisonSettings)settings).getCorrectForm().endsWith(defaultEnding)) {
                incorrectForms.add(((ComparisonSettings)settings).getLemma() + defaultEnding);
            }
            if(((ComparisonSettings)settings).getLemma().endsWith("y")) {
                incorrectForms.add(((ComparisonSettings)settings).getLemma().substring(0, ((ComparisonSettings)settings).getLemma().length() - 1) + "i" + defaultEnding);
            } else if(((ComparisonSettings)settings).getLemma().length() >= 3 && ((ComparisonSettings)settings).getLemma().substring(((ComparisonSettings)settings).getLemma().length() - 1).matches("[^aeiouyw]")) {
                // Add double and single consonant forms; if one of them is the correct form, it doesn't matter in our hasSet since we later filter out the correct form anyway
                incorrectForms.add(((ComparisonSettings)settings).getLemma() + ((ComparisonSettings)settings).getLemma().substring(((ComparisonSettings)settings).getLemma().length() - 1) + defaultEnding);
                incorrectForms.add(((ComparisonSettings)settings).getLemma() + defaultEnding);
            }
        } else {
            String defaultPrefix = ((ComparisonSettings)settings).isComparative() ? "more " : "most ";
            HashSet<String> incorrectSyntheticForms = generateIncorrectForms(new ComparisonSettings(true, ((ComparisonSettings)settings).isComparative(), ((ComparisonSettings)settings).getLemma(), ((ComparisonSettings)settings).getCorrectForm()));
            incorrectSyntheticForms.add(((ComparisonSettings)settings).getCorrectForm());
            for(String incorrectSyntheticForm : incorrectSyntheticForms) {
                incorrectForms.add(defaultPrefix + incorrectSyntheticForm);
            }
        }

        return incorrectForms;
    }

}
