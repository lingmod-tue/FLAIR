package com.flair.server.exerciseGeneration.exerciseManagement.temp;

import simplenlg.features.*;
import simplenlg.framework.NLGElement;

import java.util.HashSet;

import com.flair.server.parser.SimpleNlgParser;

public abstract class TenseGenerator extends FormGenerator {

    public TenseGenerator(SimpleNlgParser generator) {
        super(generator);
    }

    /**
     * Generates the correct form for the given parameters.
     * @param settings      The parameter settings
     * @return              The generated form
     */
    public String generateCorrectTenseForm(TenseSettings settings) {
        NLGElement p;
        if(settings.isInterrogative()) {
            if(settings.getSubject() == null) {
                settings.setSubject(settings.isThirdSingular() ? "it" : "they");
            }
            p = generator.nlgFactory().createClause(settings.getSubject(), settings.getLemma());
            p.setFeature(Feature.INTERROGATIVE_TYPE, InterrogativeType.YES_NO);
        } else {
            p = generator.nlgFactory().createVerbPhrase(settings.getLemma());
            p.setFeature(Feature.PERSON, Person.THIRD);
            p.setFeature(Feature.NUMBER, settings.isThirdSingular() ? NumberAgreement.SINGULAR : NumberAgreement.PLURAL);
        }
        if(settings.getSubject() != null && settings.getSubject().equals("I")) {
            p.setFeature(Feature.PERSON, Person.FIRST);
            p.setFeature(Feature.NUMBER, NumberAgreement.SINGULAR);
        }
        p.setFeature(Feature.TENSE, settings.getTense().equals("present") ? Tense.PRESENT : settings.getTense().equals("past") ? Tense.PAST : Tense.FUTURE);
        p.setFeature(Feature.PROGRESSIVE, settings.isProgressive());
        p.setFeature(Feature.PERFECT, settings.isPerfect());
        p.setFeature(Feature.NEGATED, settings.isNegated());
        if(settings.getModal() != null) {
            p.setFeature(Feature.MODAL, settings.getModal());
        }
        return generator.realiser().realise(p).toString();
    }

    /**
     * Generates incorrect past participle forms for the given lemma.
     * @param lemma Verb lemma
     * @return      Incorrect past participle forms
     */
    protected HashSet<String> generateIncorrectPastParticipleForms(String lemma) {
        HashSet<String> incorrectForms = new HashSet<>();

        incorrectForms.add(lemma + "t");
        if(lemma.endsWith("e")) {
            incorrectForms.add(lemma + "n");
            incorrectForms.add(lemma + "d");
        } else {
            incorrectForms.add(lemma + "en");
            incorrectForms.add(lemma + "ed");
        }

        return incorrectForms;
    }

}