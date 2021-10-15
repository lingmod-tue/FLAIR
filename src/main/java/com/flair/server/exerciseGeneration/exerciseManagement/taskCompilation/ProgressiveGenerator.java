package com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation;

import simplenlg.features.*;
import simplenlg.framework.NLGElement;

import java.util.HashSet;

import com.flair.server.parser.SimpleNlgParser;

public abstract class ProgressiveGenerator extends TenseGenerator {

    public ProgressiveGenerator(SimpleNlgParser generator) {
        super(generator);
    }

    /**
     * Generates incorrect present participle forms for the given lemma.
     * @param lemma Verb lemma
     * @return      Incorrect present participle forms
     */
    protected HashSet<String> generateIncorrectPresentParticipleForms(String lemma) {
        HashSet<String> incorrectForms = new HashSet<>();

        incorrectForms.add(lemma + "ing");
        if(lemma.substring(lemma.length() - 1).matches("[^aeiouyw]")) {
            incorrectForms.add(lemma + lemma.substring(lemma.length() - 1) + "ing");   // incorrect if the trailing consonant shouldn't be doubled
        } else if(lemma.endsWith("e")) {
            incorrectForms.add(lemma.substring(0, lemma.length() - 1) + "ing");   // incorrect if the trailing 'e' shouldn't be dropped
        }

        return incorrectForms;
    }

    /**
     * Generates the correct present participle form for the given lemma.
     * @param lemma The verb lemma
     * @return      The correct present participle
     */
    protected String generateCorrectPresentParticipleForm(String lemma) {
        NLGElement p = generator.nlgFactory().createVerbPhrase(lemma);
        p.setFeature(Feature.TENSE, Tense.PRESENT);
        p.setFeature(Feature.PROGRESSIVE, true);

        return generator.realiser().realise(p).toString().substring(2);
    }
}
