package com.flair.server.exerciseGeneration.exerciseManagement.nlpManagement;

import java.util.HashSet;

import com.flair.server.parser.SimpleNlgParser;

public class FutureSimpleGenerator extends TenseGenerator {

    public FutureSimpleGenerator(SimpleNlgParser generator) {
        super(generator);
    }

    @Override
    public String generateCorrectForm(ParameterSettings settings) {
        ((TenseSettings)settings).setTense("future");
        ((TenseSettings)settings).setProgressive(false);
        ((TenseSettings)settings).setPerfect(false);
        return super.generateCorrectTenseForm(((TenseSettings)settings));
    }

    @Override
    public HashSet<String> generateIncorrectForms(ParameterSettings settings) {
        // We are very likely to also generate the correct form at some point, but we will filter that out later
        HashSet<String> incorrectForms = new HashSet<>();

        SimplePresentGenerator g = new SimplePresentGenerator(generator);
        String simplePresentForm = g.generateCorrectForm(settings);

        if(((TenseSettings)settings).isInterrogative()) {
            if(((TenseSettings)settings).isNegated()) {
                incorrectForms.add(((TenseSettings)settings).getSubject() + " won't " + ((TenseSettings)settings).getLemma());
                incorrectForms.add(((TenseSettings)settings).getSubject() + " won't " + simplePresentForm);
                incorrectForms.add("won't " + ((TenseSettings)settings).getSubject() + simplePresentForm);
            } else {
                incorrectForms.add(((TenseSettings)settings).getSubject() + " will " + ((TenseSettings)settings).getLemma());
                incorrectForms.add(((TenseSettings)settings).getSubject() + " will " + simplePresentForm);
                incorrectForms.add("will " + ((TenseSettings)settings).getSubject() + simplePresentForm);
            }
        } else {
            if(((TenseSettings)settings).isNegated()) {
                incorrectForms.add("won't " + simplePresentForm);
            } else {
                incorrectForms.add("will " + simplePresentForm);
            }
        }

        return  incorrectForms;
    }

}
